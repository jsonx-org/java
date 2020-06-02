/* Copyright (c) 2018 JSONx
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * You should have received a copy of The MIT License (MIT) along with this
 * program. If not, see <http://opensource.org/licenses/MIT/>.
 */

package org.jsonx;

import static org.jsonx.JsdUtil.*;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.IdentityHashMap;

import org.jsonx.ArrayValidator.Relation;
import org.jsonx.ArrayValidator.Relations;
import org.libj.lang.Classes;
import org.libj.util.function.TriPredicate;
import org.openjax.json.JsonReader;

class ObjectCodec extends Codec {
  static Object decodeArray(final Class<? extends JxObject> type, final String token, final JsonReader reader, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException {
    return !"{".equals(token) ? null : ObjectCodec.decodeObject(type, reader, validate, onPropertyDecode);
  }

  static Object decodeObject(final Class<? extends JxObject> type, final JsonReader reader, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException {
    final int index = reader.getIndex();
    try {
      final JxObject object = type.getConstructor().newInstance();
      final PropertyToCodec propertyToCodec = getPropertyCodec(type);
      for (String token; !"}".equals(token = reader.readToken());) {
        if (",".equals(token))
          token = reader.readToken();

        if ("}".equals(token))
          break;

        if (!":".equals(reader.readToken()))
          throw new IllegalStateException("Should have been caught by JsonReader: " + token);

        final String propertyName = token.substring(1, token.length() - 1);
        token = reader.readToken();

        final Codec codec = propertyToCodec.get(propertyName);
        if (codec == null) {
          if (onPropertyDecode != null && onPropertyDecode.test(object, propertyName, token))
            continue;

          return abort(Error.UNKNOWN_PROPERTY(propertyName, reader), reader, index);
        }

        final Object value;
        if ("null".equals(token)) {
          final Error error = codec.validateUse(null);
          if (error != null)
            return abort(error, reader, index);

          value = codec.toNull();
        }
        else if (codec instanceof PrimitiveCodec) {
          final PrimitiveCodec primitiveCodec = (PrimitiveCodec)codec;
          final Error error = validate ? primitiveCodec.matches(token, reader) : null;
          if (error != null)
            return abort(error, reader, index);

          value = primitiveCodec.parse(token);
        }
        else {
          if (codec instanceof AnyCodec) {
            final AnyCodec anyCodec = (AnyCodec)codec;
            value = AnyCodec.decode(anyCodec.property, token, reader, validate, null);
            if (value instanceof Error)
              return abort((Error)value, reader, index);
          }
          else {
            final char firstChar = token.charAt(0);
            if (codec instanceof ObjectCodec) {
              if (firstChar != '{')
                return abort(Error.EXPECTED_TOKEN(codec.name, codec.elementName(), token, reader), reader, index);

              final ObjectCodec objectCodec = (ObjectCodec)codec;
              value = decodeObject(objectCodec.type, reader, validate, null); // NOTE: Setting `onPropertyDecode = null` here on purpose!
              if (value instanceof Error)
                return abort((Error)value, reader, index);
            }
            else if (codec instanceof ArrayCodec) {
              if (firstChar != '[')
                return abort(Error.EXPECTED_TOKEN(codec.name, codec.elementName(), token, reader), reader, index);

              final ArrayCodec arrayCodec = (ArrayCodec)codec;
              value = ArrayCodec.decodeObject(arrayCodec.annotations, arrayCodec.idToElement.getMinIterate(), arrayCodec.idToElement.getMaxIterate(), arrayCodec.idToElement, reader, validate, onPropertyDecode);
              if (value instanceof Error)
                return abort((Error)value, reader, index);
            }
            else {
              throw new UnsupportedOperationException("Unsupported " + Codec.class.getSimpleName() + " type: " + codec.getClass().getName());
            }
          }
        }

        codec.set(object, propertyName, value, onPropertyDecode);
      }

      // If this {...} contained properties, check each method of the target object
      // to ensure that no properties are missing (i.e. use=required).
      final Method[] methods = type.getMethods();
      for (final Method getMethod : methods) {
        if (getMethod.isSynthetic() || getMethod.getReturnType() == void.class || getMethod.getParameterCount() > 0)
          continue;

        final Codec codec = propertyToCodec.getMethodToCodec.get(getMethod);
        if (codec == null)
          continue;

        if (getMethod.invoke(object) == null) {
          final Error error = codec.validateUse(null);
          if (error != null)
            return abort(error, reader, index);
        }
      }

      return object;
    }
    catch (final IllegalAccessException | InstantiationException | NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
    catch (final InvocationTargetException e) {
      if (e.getCause() instanceof RuntimeException)
        throw (RuntimeException)e.getCause();

      if (e.getCause() instanceof IOException)
        throw (IOException)e.getCause();

      throw new RuntimeException(e.getCause());
    }
  }

  private static Error abort(final Error error, final JsonReader reader, final int index) {
    reader.setIndex(index);
    return error;
  }

  static Error encodeArray(final Annotation annotation, final Object object, final int index, final Relations relations) {
    if (!(object instanceof JxObject))
      return Error.CONTENT_NOT_EXPECTED(object, null);

    relations.set(index, new Relation(object, annotation));
    return null;
  }

  private static final IdentityHashMap<Class<?>,PropertyToCodec> typeToCodecs = new IdentityHashMap<>();

  private static PropertyToCodec getPropertyCodec(final Class<? extends JxObject> cls) {
    PropertyToCodec propertyToCodec = typeToCodecs.get(cls);
    if (propertyToCodec != null)
      return propertyToCodec;

    propertyToCodec = new PropertyToCodec();
    final Method[] methods = cls.getMethods();
    for (final Method getMethod : methods) {
      if (getMethod.isSynthetic() || getMethod.getReturnType() == void.class || getMethod.getParameterCount() > 0)
        continue;

      for (final Annotation annotation : getMethod.getAnnotations()) {
        if (annotation instanceof AnyProperty)
          propertyToCodec.add(new AnyCodec((AnyProperty)annotation, getMethod, findSetMethod(methods, getMethod)));
        else if (annotation instanceof ArrayProperty)
          propertyToCodec.add(new ArrayCodec((ArrayProperty)annotation, getMethod, findSetMethod(methods, getMethod)));
        else if (annotation instanceof BooleanProperty)
          propertyToCodec.add(new BooleanCodec((BooleanProperty)annotation, getMethod, findSetMethod(methods, getMethod)));
        else if (annotation instanceof NumberProperty)
          propertyToCodec.add(new NumberCodec((NumberProperty)annotation, getMethod, findSetMethod(methods, getMethod)));
        else if (annotation instanceof ObjectProperty)
          propertyToCodec.add(new ObjectCodec((ObjectProperty)annotation, getMethod, findSetMethod(methods, getMethod)));
        else if (annotation instanceof StringProperty)
          propertyToCodec.add(new StringCodec((StringProperty)annotation, getMethod, findSetMethod(methods, getMethod)));
        else
          continue;

        break;
      }
    }

    typeToCodecs.put(cls, propertyToCodec);
    return propertyToCodec;
  }

  private final Class<? extends JxObject> type;

  @SuppressWarnings("unchecked")
  ObjectCodec(final ObjectProperty property, final Method getMethod, final Method setMethod) {
    super(getMethod, setMethod, property.name(), property.nullable(), property.use());
    this.type = (Class<? extends JxObject>)(optional ? Classes.getGenericParameters(getMethod)[0] : getMethod.getReturnType());
  }

  @Override
  String elementName() {
    return "object";
  }
}