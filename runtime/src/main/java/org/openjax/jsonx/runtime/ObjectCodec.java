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
import java.util.function.BiConsumer;

import org.fastjax.json.JsonReader;
import org.fastjax.util.Classes;

class ObjectCodec extends Codec {
  private static final Map<Class<?>,PropertyToCodec> typeToCodecs = new HashMap<>();

  private static PropertyToCodec getPropertyCodec(final Class<?> cls) {
    PropertyToCodec propertyToCodec = typeToCodecs.get(cls);
    if (propertyToCodec != null)
      return propertyToCodec;

    typeToCodecs.put(cls, propertyToCodec = new PropertyToCodec());
    for (final Field field : Classes.getDeclaredFieldsDeep(cls)) {
      for (final Annotation annotation : field.getAnnotations()) {
        if (annotation instanceof ArrayProperty)
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

    return propertyToCodec;
  }

  static Object decode(final Class<?> type, final JsonReader reader, final BiConsumer<Field,Object> callback) throws IOException {
    final Object object;
    try {
      object = type.getConstructor().newInstance();
    }
    catch (final IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
      throw new IllegalStateException(e);
    }

    try {
      for (String token; !"}".equals(token = reader.readToken());) {
        if (",".equals(token))
          token = reader.readToken();

        final Codec codec = getPropertyCodec(type).nameToCodec.get(token.substring(1, token.length() - 1));
        if (codec == null)
          return "Unknown property: " + token;

        if (!":".equals(reader.readToken()))
          throw new IllegalStateException("Should have been caught by JsonReader");

        token = reader.readToken();
        final Object value;
        if ("null".equals(token)) {
          final String error = codec.validateUse(null);
          if (error != null)
            return error;

          value = null;
        }
        else if (codec instanceof PrimitiveCodec) {
          final PrimitiveCodec<?> primitiveCodec = (PrimitiveCodec<?>)codec;
          final String error = primitiveCodec.matches(token);
          if (error != null)
            return error;

          value = primitiveCodec.decode(token);
        }
        else {
          final char firstChar = token.charAt(0);
          if (codec instanceof ObjectCodec) {
            if (firstChar != '{')
              return "Expected \"" + codec.name + "\" to be a \"" + codec.elementName() + "\", but json contains object";

            value = decode(((ObjectCodec)codec).getType(), reader, callback);
            if (value instanceof String)
              return value;
          }
          else if (codec instanceof ArrayCodec) {
            if (firstChar != '[')
              return "Expected \"" + codec.name + "\" to be a \"" + codec.elementName() + "\", but json contains object";

            final ArrayCodec arrayCodec = (ArrayCodec)codec;
            value = ArrayCodec.decode(arrayCodec.getAnnotations(), arrayCodec.getIdToElement(), reader, callback);
            if (value instanceof String)
              return value;
          }
          else {
            throw new UnsupportedOperationException("Unsupported " + Codec.class.getSimpleName() + " type: " + codec.getClass().getName());
          }
        }

        if (value != null)
          codec.set(object, value, callback);
      }

      for (final Field field : Classes.getDeclaredFieldsDeep(object.getClass())) {
        field.setAccessible(true);
        if (field.get(object) == null) {
          final Codec codec = getPropertyCodec(type).fieldToCodec.get(field);
          final String error = codec.validateUse(null);
          if (error != null)
            return error;
        }
      }
    }
    catch (final IllegalAccessException | InvocationTargetException e) {
      throw new IllegalStateException(e);
    }

    return object;
  }

  private final Class<?> type;

  ObjectCodec(final ObjectProperty property, final Field field) {
    super(field, property.name(), property.use());
    this.type = field.getType();
  }

  Class<?> getType() {
    return type;
  }

  @Override
  String elementName() {
    return "object";
  }
}