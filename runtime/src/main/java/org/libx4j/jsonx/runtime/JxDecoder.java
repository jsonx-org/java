/* Copyright (c) 2015 lib4j
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

package org.libx4j.jsonx.runtime;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fastjax.json.JsonReader;
import org.fastjax.util.Classes;
import org.libx4j.jsonx.runtime.ArrayValidator.Relations;

public abstract class JxDecoder implements Cloneable {
  private static final Map<Class<?>,Map<String,Spec>> typeToSpecs = new HashMap<>();

  private static Spec getPropertySpec(final Class<?> cls, String propertyName) {
    final char ch = propertyName.charAt(0);
    if (ch == '"' || ch == '\'')
      propertyName = propertyName.substring(1, propertyName.length() - 1);

    Map<String,Spec> propertyToSpec = typeToSpecs.get(cls);
    if (propertyToSpec == null) {
      typeToSpecs.put(cls, propertyToSpec = new HashMap<>());
      for (final Field field : Classes.getDeclaredFieldsDeep(cls)) {
        for (final Annotation annotation : field.getAnnotations()) {
          if (annotation instanceof ArrayProperty) {
            final Spec spec = new ArraySpec(field, (ArrayProperty)annotation);
            propertyToSpec.put(JsonxUtil.getName(spec.getName(), field), spec);
          }
          else if (annotation instanceof BooleanProperty) {
            final Spec spec = new BooleanSpec(field, (BooleanProperty)annotation);
            propertyToSpec.put(JsonxUtil.getName(spec.getName(), field), spec);
          }
          else if (annotation instanceof NumberProperty) {
            final Spec spec = new NumberSpec(field, (NumberProperty)annotation);
            propertyToSpec.put(JsonxUtil.getName(spec.getName(), field), spec);
          }
          else if (annotation instanceof ObjectProperty) {
            final Spec spec = new ObjectSpec(field, (ObjectProperty)annotation);
            propertyToSpec.put(JsonxUtil.getName(spec.getName(), field), spec);
          }
          else if (annotation instanceof StringProperty) {
            final Spec spec = new StringSpec(field, (StringProperty)annotation);
            propertyToSpec.put(JsonxUtil.getName(spec.getName(), field), spec);
          }
        }
      }
    }

    return propertyToSpec.get(propertyName);
  }

  public static List<?> parseArray(final Class<? extends Annotation> annotationType, final JsonReader reader) throws DecodeException, IOException {
    final IdToElement idToElement = new IdToElement();
    final int[] elementIds = JsonxUtil.digest(annotationType.getAnnotations(), annotationType.getName(), idToElement);
    final Object array = parse0(idToElement.get(elementIds), idToElement, reader);
    if (array instanceof String)
      throw new DecodeException((String)array, reader);

    return (List<?>)array;
  }

  public static Object parse0(final Annotation[] annotations, final IdToElement idToElement, final JsonReader reader) throws DecodeException, IOException {
    final String token = reader.readToken();
    if (!"[".equals(token))
      throw new DecodeException("Expected '[', but got '" + token + "'", reader);

    final ParsingIterator iterator = new ParsingIterator(reader);
    final Relations relations = new Relations();
    ArrayValidator.validate(iterator, 0, annotations, 0, idToElement, relations);
    return relations.toList();
  }

  public static <T>T parseObject(final Class<T> type, final JsonReader reader) throws DecodeException, IOException {
    final String token = reader.readToken();
    if (!"{".equals(token))
      throw new DecodeException("Expected '{', but got '" + token + "'", reader);

    final Object object = parse0(type, reader);
    if (object instanceof String)
      throw new DecodeException((String)object, reader);

    return (T)object;
  }

  public static Object parse0(final Class<?> type, final JsonReader reader) throws DecodeException, IOException {
    final Object object;
    try {
      object = type.getConstructor().newInstance();
    }
    catch (final IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
      throw new DecodeException(reader, e);
    }

    try {
      for (String token; !"}".equals(token = reader.readToken());) {
        if (",".equals(token))
          token = reader.readToken();

        final Spec spec = getPropertySpec(type, token);
        if (spec == null)
          return "Unknown property: " + token;

        if (!":".equals(reader.readToken()))
          throw new DecodeException("Should have been caught by JsonReader", reader);

        token = reader.readToken();
        final Object value;
        if ("null".equals(token)) {
          if (spec.getUse() == Use.REQUIRED)
            return spec.getName() + " is required";

          value = null;
        }
        else if (spec instanceof PrimitiveSpec) {
          final PrimitiveSpec<?> primitiveSpec = (PrimitiveSpec<?>)spec;
          final String error = primitiveSpec.matches(token);
          if (error != null)
            return error;

          value = primitiveSpec.decode(token);
        }
        else {
          final char firstChar = token.charAt(0);
          if (spec instanceof ObjectSpec) {
            if (firstChar != '{')
              return "Expected \"" + spec.getName() + "\" to be " + spec.elementName() + ", but json contains object";

            value = parse0(((ObjectSpec)spec).getType(), reader);
            if (value instanceof String)
              return value;
          }
          else if (spec instanceof ArraySpec) {
            if (firstChar != '[')
              return "Expected \"" + spec.getName() + "\" to be " + spec.elementName() + ", but json contains object";

            final ArraySpec arraySpec = (ArraySpec)spec;
            value = parse0(arraySpec.getAnnotations(), arraySpec.getIdToElement(), reader);
            if (value instanceof String)
              return value;
          }
          else {
            throw new UnsupportedOperationException("Unsupported Spec type: " + spec.getClass().getName());
          }
        }

        if (value != null)
          spec.set(object, value);
      }
    }
    catch (final InvocationTargetException e) {
      throw new DecodeException(reader, e);
    }

    return object;
  }
}