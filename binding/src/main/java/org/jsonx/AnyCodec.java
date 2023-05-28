/* Copyright (c) 2019 JSONx
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
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Pattern;

import org.jsonx.ArrayValidator.Relations;
import org.libj.lang.Classes;
import org.libj.util.Patterns;
import org.libj.util.function.TriPredicate;
import org.openjax.json.JsonReader;

class AnyCodec extends Codec {
  static Object decode(final Annotation annotation, final String token, final JsonReader reader, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException {
    final t[] types;
    if (annotation.annotationType() == AnyElement.class)
      types = ((AnyElement)annotation).types();
    else if (annotation.annotationType() == AnyProperty.class)
      types = ((AnyProperty)annotation).types();
    else
      throw new UnsupportedOperationException("Unsupported annotation type: " + annotation.annotationType().getName());

    final char firstChar = token.charAt(0);
    Error error = null;
    final int len = types.length;
    if (firstChar == '[') {
      t type = len > 0 ? types[0] : AnyType.arrays;
      int i = 0; do {
        final Class<? extends Annotation> arrays = type.arrays();
        if (AnyType.isEnabled(arrays)) {
          final int index = reader.getIndex();
          final Object value = ArrayCodec.decodeArray(null, arrays, null, token, reader, validate, onPropertyDecode);
          if (!(value instanceof Error))
            return value;

          if (error == null || error.isBefore((Error)value))
            error = (Error)value;

          reader.setIndex(index);
        }

        if (++i == len)
          break;

        type = types[i];
      }
      while (true);
    }
    else if (firstChar == '{') {
      t type = len > 0 ? types[0] : AnyType.objects;
      int i = 0; do {
        final Class<? extends JxObject> objects = type.objects();
        if (AnyType.isEnabled(objects)) {
          final Object value = ObjectCodec.decodeArray(objects, token, reader, validate, onPropertyDecode);
          if (!(value instanceof Error))
            return value;

          if (error == null || error.isBefore((Error)value))
            error = (Error)value;
        }

        if (++i == len)
          break;

        type = types[i];
      }
      while (true);
    }
    else {
      t type = len > 0 ? types[0] : AnyType.fromToken(token);
      int i = 0; do {
        final BooleanType booleans;
        final NumberType numbers;
        final StringType strings;
        if (AnyType.isEnabled(strings = type.strings())) {
          final Object value = StringCodec.decodeArray(strings.type(), strings.decode(), token);
          if (value != null)
            return value;
        }
        else if (AnyType.isEnabled(numbers = type.numbers())) {
          final Object value = NumberCodec.decodeArray(numbers.type(), numbers.scale(), numbers.decode(), token, reader.isStrict());
          if (value != null)
            return value;
        }
        else if (AnyType.isEnabled(booleans = type.booleans())) {
          final Object value = BooleanCodec.decodeArray(booleans.type(), booleans.decode(), token);
          if (value != null)
            return value;
        }

        if (++i == len)
          break;

        type = types[i];
      }
      while (true);
    }

    return error != null ? error : Error.CONTENT_NOT_EXPECTED(token, reader, null);
  }

  static Error encodeArray(final AnyElement annotation, final Object object, final int index, final Relations relations, final IdToElement idToElement, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) {
    final t[] types = annotation.types();
    Error error = Error.NULL;
    final int len = types.length;
    t type = len > 0 ? types[0] : AnyType.fromObject(object);
    int i = 0; do {
      final StringType strings;
      final NumberType numbers;
      final BooleanType booleans;
      final Class<? extends Annotation> arrays;
      if (AnyType.isEnabled(strings = type.strings())) {
        error = StringCodec.encodeArray(annotation, strings.pattern(), strings.type(), strings.encode(), object, index, relations, validate);
        if (error == null)
          return null;
      }
      else if (AnyType.isEnabled(numbers = type.numbers())) {
        error = NumberCodec.encodeArray(annotation, numbers.scale(), numbers.range(), numbers.type(), numbers.encode(), object, index, relations, validate);
        if (error == null)
          return null;
      }
      else if (AnyType.isEnabled(booleans = type.booleans())) {
        error = BooleanCodec.encodeArray(annotation, booleans.type(), booleans.encode(), object, index, relations);
        if (error == null)
          return null;
      }
      else if (AnyType.isEnabled(type.objects())) {
        error = ObjectCodec.encodeArray(annotation, object, index, relations);
        if (error == null)
          return null;
      }
      else if (AnyType.isEnabled(arrays = type.arrays())) {
        error = ArrayCodec.encodeArray(annotation, arrays, object, index, relations, idToElement, validate, onPropertyDecode);
        if (error == null)
          return null;
      }

      if (++i == len)
        break;

      type = types[i];
    }
    while (true);

    if (error == Error.NULL)
      throw new IllegalStateException();

    return error;
  }

  static Object encodeObject(final Annotation annotation, final Method getMethod, final t[] types, final Object object, final JxEncoder jxEncoder, final int depth, final boolean validate) throws EncodeException, ValidationException {
    Error error = null;
    if (object == null)
      return !validate || JsdUtil.isNullable(annotation) ? "null" : Error.ILLEGAL_VALUE_NULL();

    Relations relations = null;
    StringBuilder builder = null;
    final int len = types.length;
    t type = len > 0 ? types[0] : AnyType.fromObject(object);
    int i = 0; do {
      final StringType strings;
      final NumberType numbers;
      final BooleanType booleans;
      final Class<? extends Annotation> arrays;
      if (AnyType.isEnabled(strings = type.strings()) && Classes.isAssignableFrom(strings.type(), object.getClass())) {
        final Object encoded = StringCodec.encodeObject(annotation, getMethod, strings.pattern(), strings.type(), strings.encode(), object, validate);
        if (encoded instanceof Error)
          error = (Error)encoded;
        else
          return encoded;
      }
      else if (AnyType.isEnabled(numbers = type.numbers()) && Classes.isAssignableFrom(numbers.type(), object.getClass())) {
        final Object encoded = NumberCodec.encodeObject(annotation, numbers.scale(), numbers.range(), numbers.type(), numbers.encode(), object, validate);
        if (encoded instanceof Error)
          error = (Error)encoded;
        else
          return encoded;
      }
      else if (AnyType.isEnabled(booleans = type.booleans()) && Classes.isAssignableFrom(booleans.type(), object.getClass())) {
        final Object encoded = BooleanCodec.encodeObject(booleans.type(), booleans.encode(), object);
        if (encoded instanceof Error)
          error = (Error)encoded;
        else
          return encoded;
      }
      else if (object instanceof JxObject && AnyType.isEnabled(type.objects())) {
        if (builder == null)
          builder = new StringBuilder();
        else
          builder.setLength(0);

        if ((error = jxEncoder.toString((JxObject)object, null, builder, depth)) == null)
          return builder.toString();
      }
      else if (object instanceof List && AnyType.isEnabled(arrays = type.arrays())) {
        if (relations == null)
          relations = new Relations();
        else
          relations.clear();

        if ((error = ArrayCodec.encodeArray(null, arrays, object, arrays.getAnnotation(ArrayType.class).elementIds()[0], relations, null, validate, null)) == null)
          return relations;
      }

      if (++i == len)
        break;

      type = types[i];
    }
    while (true);

    if (error == null)
      throw new IllegalStateException();

    return error;
  }

  final AnyProperty property;
  private Pattern pattern;

  AnyCodec(final AnyProperty property, final Method getMethod, final Method setMethod) {
    super(getMethod, setMethod, property.name(), property.nullable(), property.use());
    this.property = property;
  }

  Pattern pattern() {
    return pattern == null ? pattern = Patterns.compile(name, Pattern.DOTALL) : pattern;
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
    return "any";
  }
}