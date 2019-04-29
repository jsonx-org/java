/* Copyright (c) 2018 OpenJAX
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

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.openjax.ext.json.JsonReader;
import org.jsonx.ArrayValidator.Relation;
import org.jsonx.ArrayValidator.Relations;
import org.openjax.ext.util.Classes;
import org.openjax.ext.util.function.TriPredicate;

class ObjectCodec extends Codec {
  static Object decodeArray(final Class<? extends JxObject> type, final String token, final JsonReader reader, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException {
    return !"{".equals(token) ? null : ObjectCodec.decodeObject(type, reader, onPropertyDecode);
  }

  static Object decodeObject(final Class<? extends JxObject> type, final JsonReader reader, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException {
    final int index = reader.getIndex();
    try {
      final JxObject object = type.getConstructor().newInstance();
      for (String token; !"}".equals(token = reader.readToken());) {
        if (",".equals(token))
          token = reader.readToken();

        if (!":".equals(reader.readToken()))
          throw new IllegalStateException("Should have been caught by JsonReader: " + token);

        final String propertyName = token.substring(1, token.length() - 1);
        token = reader.readToken();
        final Codec codec = getPropertyCodec(object).get(propertyName);
        if (codec == null) {
          if (onPropertyDecode != null && onPropertyDecode.test(object, propertyName, token))
            continue;

          return abort(Error.UNKNOWN_PROPERTY(propertyName, reader.getPosition()), reader, index);
        }

        final Object value;
        if ("null".equals(token)) {
          final Error error = codec.validateUse(null);
          if (error != null)
            return abort(error, reader, index);

          value = codec.toNull();
        }
        else if (codec instanceof PrimitiveCodec) {
          final PrimitiveCodec<?> primitiveCodec = (PrimitiveCodec<?>)codec;
          final Error error = primitiveCodec.matches(token, reader.getPosition());
          if (error != null)
            return abort(error, reader, index);

          value = primitiveCodec.parse(token);
        }
        else {
          if (codec instanceof AnyCodec) {
            final AnyCodec anyCodec = (AnyCodec)codec;
            value = AnyCodec.decode(anyCodec.property, token, reader, null);
            if (value instanceof Error)
              return abort((Error)value, reader, index);
          }
          else {
            final char firstChar = token.charAt(0);
            if (codec instanceof ObjectCodec) {
              if (firstChar != '{')
                return abort(Error.EXPECTED_TOKEN(codec.name, codec.elementName(), token, reader.getPosition()), reader, index);

              final ObjectCodec objectCodec = (ObjectCodec)codec;
              value = decodeObject(objectCodec.type, reader, null);
              if (value instanceof Error)
                return abort((Error)value, reader, index);
            }
            else if (codec instanceof ArrayCodec) {
              if (firstChar != '[')
                return abort(Error.EXPECTED_TOKEN(codec.name, codec.elementName(), token, reader.getPosition()), reader, index);

              final ArrayCodec arrayCodec = (ArrayCodec)codec;
              value = ArrayCodec.decodeObject(arrayCodec.annotations, arrayCodec.idToElement.getMinIterate(), arrayCodec.idToElement.getMaxIterate(), arrayCodec.idToElement, reader, onPropertyDecode);
              if (value instanceof Error)
                return abort((Error)value, reader, index);
            }
            else {
              throw new UnsupportedOperationException("Unsupported " + Codec.class.getSimpleName() + " type: " + codec.getClass().getName());
            }
          }
        }

        if (value != null)
          codec.set(object, propertyName, value, onPropertyDecode);
      }

      for (final Field field : Classes.getDeclaredFieldsDeep(object.getClass())) {
        field.setAccessible(true);
        if (field.get(object) == null) {
          final Codec codec = getPropertyCodec(object).fieldToCodec.get(field);
          if (codec == null)
            throw new IllegalStateException("Missing codec for field : " + type.getName() + "#" + field.getName());

          final Error error = codec.validateUse(null);
          if (error != null)
            return abort(error, reader, index);
        }
      }

      return object;
    }
    catch (final IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
      throw new IllegalStateException(e);
    }
  }

  private static Error abort(final Error error, final JsonReader reader, final int index) {
    reader.setIndex(index);
    return error;
  }

  static Error encodeArray(final Annotation annotation, final Object object, final int index, final Relations relations) {
    if (!(object instanceof JxObject))
      return Error.CONTENT_NOT_EXPECTED(object, -1);

    relations.set(index, new Relation(object, annotation));
    return null;
  }

  private static final Map<Class<?>,PropertyToCodec> typeToCodecs = new HashMap<>();

  private static PropertyToCodec getPropertyCodec(final JxObject object) {
    final Class<?> cls = object.getClass();
    PropertyToCodec propertyToCodec = typeToCodecs.get(cls);
    if (propertyToCodec != null)
      return propertyToCodec;

    propertyToCodec = new PropertyToCodec();
    for (final Field field : Classes.getDeclaredFieldsDeep(cls)) {
      for (final Annotation annotation : field.getAnnotations()) {
        if (annotation instanceof AnyProperty)
          propertyToCodec.add(new AnyCodec((AnyProperty)annotation, field));
        else if (annotation instanceof ArrayProperty)
          propertyToCodec.add(new ArrayCodec((ArrayProperty)annotation, field));
        else if (annotation instanceof BooleanProperty)
          propertyToCodec.add(new BooleanCodec((BooleanProperty)annotation, field));
        else if (annotation instanceof NumberProperty)
          propertyToCodec.add(new NumberCodec((NumberProperty)annotation, field));
        else if (annotation instanceof ObjectProperty)
          propertyToCodec.add(new ObjectCodec((ObjectProperty)annotation, field));
        else if (annotation instanceof StringProperty)
          propertyToCodec.add(new StringCodec((StringProperty)annotation, field));
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
  ObjectCodec(final ObjectProperty property, final Field field) {
    super(field, property.name(), property.nullable(), property.use());
    this.type = (Class<? extends JxObject>)(optional ? Classes.getGenericClasses(field)[0] : field.getType());
  }

  @Override
  String elementName() {
    return "object";
  }
}