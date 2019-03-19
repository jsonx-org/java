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

package org.openjax.jsonx.runtime;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.openjax.jsonx.runtime.ArrayValidator.Relation;
import org.openjax.jsonx.runtime.ArrayValidator.Relations;
import org.openjax.standard.json.JsonReader;
import org.openjax.standard.util.Classes;
import org.openjax.standard.util.function.TriPredicate;

class ObjectCodec extends Codec {
  static Object decodeArray(final Class<? extends JxObject> type, final String token, final JsonReader reader, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException {
    return !"{".equals(token) ? null : ObjectCodec.decode(type, reader, onPropertyDecode);
  }

  static StringBuilder encodeArray(final Annotation annotation, final Object object, final int index, final Relations relations) {
    if (!(object instanceof JxObject))
      return contentNotExpected(ArrayIterator.preview(object));

    relations.set(index, new Relation(object, annotation));
    return null;
  }

  private static final Map<Class<?>,PropertyToCodec> typeToCodecs = new HashMap<>();

  private static PropertyToCodec getPropertyCodec(final Class<?> cls) {
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

  public static Object decode(final Class<? extends JxObject> type, final JsonReader reader, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException {
    try {
      final JxObject object = type.getConstructor().newInstance();
      for (String token; !"}".equals(token = reader.readToken());) {
        if (",".equals(token))
          token = reader.readToken();

        if (!":".equals(reader.readToken()))
          throw new IllegalStateException("Should have been caught by JsonReader");

        final String propertyName = token.substring(1, token.length() - 1);
        token = reader.readToken();
        final Codec codec = getPropertyCodec(type).nameToCodec.get(propertyName);
        if (codec == null) {
          if (onPropertyDecode != null && onPropertyDecode.test(object, propertyName, token))
            continue;

          return new StringBuilder("Unknown property: \"").append(propertyName).append("\"");
        }

        final Object value;
        if ("null".equals(token)) {
          final StringBuilder error = codec.validateUse(null);
          if (error != null)
            return error;

          value = codec.toNull();
        }
        else if (codec instanceof PrimitiveCodec) {
          final PrimitiveCodec<?> primitiveCodec = (PrimitiveCodec<?>)codec;
          final StringBuilder error = primitiveCodec.matches(token);
          if (error != null)
            return error;

          value = primitiveCodec.parse(token);
        }
        else {
          if (codec instanceof AnyCodec) {
            final AnyCodec anyCodec = (AnyCodec)codec;
            value = AnyCodec.decode(anyCodec.property, token, reader, null);
          }
          else {
            final char firstChar = token.charAt(0);
            if (codec instanceof ObjectCodec) {
              if (firstChar != '{')
                return new StringBuilder("Expected \"").append(codec.name).append("\" to be a \"").append(codec.elementName()).append("\", but got token: \"").append(token).append('"');

              final ObjectCodec objectCodec = (ObjectCodec)codec;
              value = decode(objectCodec.type, reader, null);
              if (value instanceof StringBuilder)
                return value;
            }
            else if (codec instanceof ArrayCodec) {
              if (firstChar != '[')
                return new StringBuilder("Expected \"").append(codec.name).append("\" to be a \"").append(codec.elementName()).append("\", but got token: \"").append(token).append('"');

              final ArrayCodec arrayCodec = (ArrayCodec)codec;
              value = ArrayCodec.decode(arrayCodec.annotations, arrayCodec.idToElement.getMinIterate(), arrayCodec.idToElement.getMaxIterate(), arrayCodec.idToElement, reader, onPropertyDecode);
              if (value instanceof StringBuilder)
                return value;
            }
            else {
              throw new UnsupportedOperationException("Unsupported " + Codec.class.getSimpleName() + " type: " + codec.getClass().getName());
            }
          }
        }

        if (value != null)
          codec.set(object, value, onPropertyDecode);
      }

      for (final Field field : Classes.getDeclaredFieldsDeep(object.getClass())) {
        field.setAccessible(true);
        if (field.get(object) == null) {
          final Codec codec = getPropertyCodec(type).fieldToCodec.get(field);
          if (codec == null)
            throw new IllegalStateException("Missing codec for field : " + type.getName() + "#" + field.getName());

          final StringBuilder error = codec.validateUse(null);
          if (error != null)
            return error;
        }
      }

      return object;
    }
    catch (final IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
      throw new IllegalStateException(e);
    }
  }

  private final Class<? extends JxObject> type;

  @SuppressWarnings("unchecked")
  ObjectCodec(final ObjectProperty property, final Field field) {
    super(field, property.name(), property.nullable(), property.use());
    this.type = (Class<? extends JxObject>)(optional ? Classes.getGenericTypes(field)[0] : field.getType());
  }

  @Override
  String elementName() {
    return "object";
  }
}