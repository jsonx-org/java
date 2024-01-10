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
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.IdentityHashMap;

import org.jsonx.ArrayValidator.Relations;
import org.libj.lang.Classes;
import org.libj.lang.Numbers.Composite;
import org.libj.lang.Throwables;
import org.libj.util.Comparators;
import org.libj.util.function.TriPredicate;
import org.openjax.json.JsonReader;

class ObjectCodec extends Codec {
  private static final ClassToGetMethods classToGetMethods = new ClassToGetMethods() {
    @Override
    Method[] getMethods(final Class<? extends JxObject> cls) {
      return cls.getMethods();
    }

    @Override
    public boolean test(final Method method) {
      return !method.isSynthetic() && method.getReturnType() != void.class && method.getParameterCount() == 0;
    }
  };

  static Object decodeArray(final Class<? extends JxObject> type, final String token, final JsonReader reader, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException {
    return !"{".equals(token) ? null : ObjectCodec.decodeObject(type, reader, validate, onPropertyDecode);
  }

  static Object decodeObject(final Class<? extends JxObject> type, final JsonReader reader, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException {
    final int index = reader.getIndex();
    try {
      final JxObject object = type.getConstructor().newInstance();
      final PropertyToCodec propertyToCodec = getPropertyCodec(type);
      Codec[] unvisitedCodecs = null;
      for (long point; (point = reader.readToken()) != '}';) { // [ST]
        int off = Composite.decodeInt(point, 0);
        int len = Composite.decodeInt(point, 1);
        char c0 = reader.bufToChar(off);
        if (c0 == ',') {
          point = reader.readToken();
          off = Composite.decodeInt(point, 0);
          len = Composite.decodeInt(point, 1);
        }

        if (c0 == '}')
          break;

        point = reader.readToken();
        assert (reader.bufToChar(Composite.decodeInt(point, 0)) == ':'); // Should have been caught by JsonReader

        final char[] buf = reader.buf();
        final String propertyName = new String(buf, off + 1, len - 2);

        point = reader.readToken();
        if (point == -1)
          return abort(Error.UNEXPECTED_END_OF_DOCUMENT(reader), reader, index);

        off = Composite.decodeInt(point, 0);
        len = Composite.decodeInt(point, 1);

        final Codec codec = propertyToCodec.get(propertyName);
        if (codec == null) {
          if (onPropertyDecode != null && onPropertyDecode.test(object, propertyName, new String(buf, off, len)))
            continue;

          return abort(Error.UNKNOWN_PROPERTY(propertyName, reader), reader, index);
        }

        final Object value;
        if (len == 4 && reader.bufToChar(off) == 'n' && reader.bufToChar(off + 1) == 'u' && reader.bufToChar(off + 2) == 'l' && reader.bufToChar(off + 3) == 'l') {
          if (!codec.nullable)
            return abort(Error.PROPERTY_NOT_NULLABLE(propertyName, null), reader, index);

          value = codec.decode() == null ? codec.toNull() : JsdUtil.invoke(codec.decode(), null);
        }
        else if (codec instanceof PrimitiveCodec) {
          final PrimitiveCodec primitiveCodec = (PrimitiveCodec)codec;
          final Error error = validate ? primitiveCodec.matches(new String(buf, off, len), reader) : null;
          if (error != null)
            return abort(error, reader, index);

          value = primitiveCodec.parse(new String(buf, off, len), reader.isStrict());
        }
        else if (codec instanceof AnyCodec) {
          final AnyCodec anyCodec = (AnyCodec)codec;
          final String token = new String(buf, off, len);
          value = AnyCodec.decode(anyCodec.property, token, reader, validate, null);
        }
        else {
          c0 = reader.bufToChar(off);
          if (codec instanceof ObjectCodec) {
            if (c0 != '{')
              return abort(Error.EXPECTED_TOKEN(codec.name, codec.elementName(), new String(buf, off, len), reader), reader, index);

            final ObjectCodec objectCodec = (ObjectCodec)codec;
            value = decodeObject(objectCodec.type, reader, validate, null); // NOTE: Setting `onPropertyDecode = null` here on purpose!
          }
          else if (codec instanceof ArrayCodec) {
            if (c0 != '[')
              return abort(Error.EXPECTED_TOKEN(codec.name, codec.elementName(), new String(buf, off, len), reader), reader, index);

            final ArrayCodec arrayCodec = (ArrayCodec)codec;
            final IdToElement idToElement = arrayCodec.idToElement;
            value = ArrayCodec.decodeObject(arrayCodec.annotations, idToElement.getMinIterate(), idToElement.getMaxIterate(), idToElement, reader, validate, onPropertyDecode);
          }
          else {
            throw new UnsupportedOperationException("Unsupported " + Codec.class.getSimpleName() + " type: " + codec.getClass().getName());
          }
        }

        if (value instanceof Error)
          return abort((Error)value, reader, index);

        codec.set(object, propertyName, value, onPropertyDecode);
        if (unvisitedCodecs == null)
          unvisitedCodecs = propertyToCodec.allCodecs.clone();

        unvisitedCodecs[Arrays.binarySearch(propertyToCodec.allCodecs, codec, Comparators.IDENTITY_HASHCODE_COMPARATOR)] = null;
      }

      // If this {...} contained properties, ensure that no properties are missing (i.e. use=required).
      if (unvisitedCodecs != null) {
        for (final Codec unvisitedCodec : unvisitedCodecs) // [A]
          if (unvisitedCodec != null && unvisitedCodec.use == Use.REQUIRED)
            return abort(Error.PROPERTY_REQUIRED(unvisitedCodec.name, null), reader, index);
      }
      else {
        for (final Codec unvisitedCodec : propertyToCodec.allCodecs) // [A]
          if (unvisitedCodec.use == Use.REQUIRED)
            return abort(Error.PROPERTY_REQUIRED(unvisitedCodec.name, null), reader, index);
      }

      return object;
    }
    catch (final RuntimeException e) {
      return abort(Error.DECODE_EXCEPTION(reader, e), reader, index);
    }
    catch (final InvocationTargetException e) {
      final Throwable cause = e.getCause();
      if (cause instanceof IOException)
        throw (IOException)cause;

      return abort(Error.DECODE_EXCEPTION(reader, cause), reader, index);
    }
    catch (final InstantiationException e) {
      throw new RuntimeException(Throwables.copy(e, new InstantiationException(type.getName())));
    }
    catch (final IllegalAccessException | NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
  }

  private static Error abort(final Error error, final JsonReader reader, final int index) {
    reader.setIndex(index);
    return error;
  }

  static Error encodeArray(final Annotation annotation, final Object object, final int index, final Relations relations) {
    if (!(object instanceof JxObject))
      return Error.CONTENT_NOT_EXPECTED(object, null, null);

    relations.set(index, object, annotation);
    return null;
  }

  private static final IdentityHashMap<Class<?>,PropertyToCodec> typeToCodecs = new IdentityHashMap<>();

  private static PropertyToCodec getPropertyCodec(final Class<? extends JxObject> cls) {
    PropertyToCodec propertyToCodec = typeToCodecs.get(cls);
    if (propertyToCodec != null)
      return propertyToCodec;

    final Method[] methods = cls.getMethods();
    final Method[] getMethods = classToGetMethods.get(cls);
    propertyToCodec = getPropertyCodec(methods, getMethods, getMethods.length, 0, 0, 0);
    Arrays.sort(propertyToCodec.allCodecs, Comparators.IDENTITY_HASHCODE_COMPARATOR);
    typeToCodecs.put(cls, propertyToCodec);
    return propertyToCodec;
  }

  private static PropertyToCodec getPropertyCodec(final Method[] methods, final Method[] getMethods, final int length, final int index, final int depthAll, final int depthAny) {
    if (index == length)
      return new PropertyToCodec(depthAll, depthAny);

    final Method getMethod = getMethods[index];
    final Codec codec;
    int depthAny1 = depthAny;
    for (final Annotation annotation : Classes.getAnnotations(getMethod)) { // [A]
      if (annotation instanceof StringProperty) {
        codec = new StringCodec((StringProperty)annotation, getMethod, findSetMethod(methods, getMethod));
      }
      else if (annotation instanceof NumberProperty) {
        codec = new NumberCodec((NumberProperty)annotation, getMethod, findSetMethod(methods, getMethod));
      }
      else if (annotation instanceof ObjectProperty) {
        codec = new ObjectCodec((ObjectProperty)annotation, getMethod, findSetMethod(methods, getMethod));
      }
      else if (annotation instanceof ArrayProperty) {
        codec = new ArrayCodec((ArrayProperty)annotation, getMethod, findSetMethod(methods, getMethod));
      }
      else if (annotation instanceof BooleanProperty) {
        codec = new BooleanCodec((BooleanProperty)annotation, getMethod, findSetMethod(methods, getMethod));
      }
      else if (annotation instanceof AnyProperty) {
        codec = new AnyCodec((AnyProperty)annotation, getMethod, findSetMethod(methods, getMethod));
        ++depthAny1;
      }
      else {
        continue;
      }

      final PropertyToCodec propertyToCodec = getPropertyCodec(methods, getMethods, length, index + 1, depthAll + 1, depthAny1);
      propertyToCodec.set(codec, depthAll, depthAny);
      return propertyToCodec;
    }

    return getPropertyCodec(methods, getMethods, length, index + 1, depthAll, depthAny);
  }

  private final Class<? extends JxObject> type;

  @SuppressWarnings("unchecked")
  ObjectCodec(final ObjectProperty property, final Method getMethod, final Method setMethod) {
    super(getMethod, setMethod, property.name(), property.nullable(), property.use());
    this.type = (Class<? extends JxObject>)(isOptional ? Classes.getGenericParameters(getMethod)[0] : getMethod.getReturnType());
  }

  @Override
  Class<?> type() {
    return null;
  }

  @Override
  Executable decode() {
    return null;
  }

  @Override
  String elementName() {
    return "object";
  }
}