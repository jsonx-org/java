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

import org.jsonx.ArrayValidator.Relations;
import org.libj.lang.Classes;
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
    if (firstChar == '[') {
      for (final t type : types.length > 0 ? types : new t[] {AnyType.arrays}) {
        if (AnyType.isEnabled(type.arrays())) {
          final Object value = ArrayCodec.decodeArray(null, type.arrays(), null, token, reader, validate, onPropertyDecode);
          if (!(value instanceof Error))
            return value;

          if (error == null || error.isBefore((Error)value))
            error = (Error)value;
        }
      }
    }
    else if (firstChar == '{') {
      for (final t type : types.length > 0 ? types : new t[] {AnyType.objects}) {
        if (AnyType.isEnabled(type.objects())) {
          final Object value = ObjectCodec.decodeArray(type.objects(), token, reader, validate, onPropertyDecode);
          if (!(value instanceof Error))
            return value;

          if (error == null || error.isBefore((Error)value))
            error = (Error)value;
        }
      }
    }
    else {
      for (final t type : types.length > 0 ? types : new t[] {AnyType.fromToken(token)}) {
        if (AnyType.isEnabled(type.booleans())) {
          final Object value = BooleanCodec.decodeArray(type.booleans().type(), type.booleans().decode(), token);
          if (value != null)
            return value;
        }
        else if (AnyType.isEnabled(type.numbers())) {
          final Object value = NumberCodec.decodeArray(type.numbers().type(), type.numbers().scale(), type.numbers().decode(), token);
          if (value != null)
            return value;
        }
        else if (AnyType.isEnabled(type.strings())) {
          final Object value = StringCodec.decodeArray(type.strings().type(), type.strings().decode(), token);
          if (value != null)
            return value;
        }
      }
    }

    return error != null ? error : Error.CONTENT_NOT_EXPECTED(token, reader);
  }

  static Error encodeArray(final AnyElement annotation, final Object object, final int index, final Relations relations, final IdToElement idToElement, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) {
    final t[] types = annotation.types();
    Error error = Error.NULL;
    for (final t type : types.length > 0 ? types : new t[] {AnyType.fromObject(object)}) {
      if (AnyType.isEnabled(type.arrays())) {
        error = ArrayCodec.encodeArray(annotation, type.arrays(), object, index, relations, idToElement, validate, onPropertyDecode);
        if (error == null)
          return null;
      }
      else if (AnyType.isEnabled(type.booleans())) {
        final BooleanType booleans = type.booleans();
        error = BooleanCodec.encodeArray(annotation, booleans.type(), booleans.encode(), object, index, relations);
        if (error == null)
          return null;
      }
      else if (AnyType.isEnabled(type.numbers())) {
        final NumberType numbers = type.numbers();
        error = NumberCodec.encodeArray(annotation, numbers.scale(), numbers.range(), numbers.type(), numbers.encode(), object, index, relations, validate);
        if (error == null)
          return null;
      }
      else if (AnyType.isEnabled(type.objects())) {
        error = ObjectCodec.encodeArray(annotation, object, index, relations);
        if (error == null)
          return null;
      }
      else if (AnyType.isEnabled(type.strings())) {
        final StringType strings = type.strings();
        error = StringCodec.encodeArray(annotation, strings.pattern(), strings.type(), strings.encode(), object, index, relations, validate);
        if (error == null)
          return null;
      }
    }

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
    for (final t type : types.length > 0 ? types : new t[] {AnyType.fromObject(object)}) {
      if (object instanceof List && AnyType.isEnabled(type.arrays())) {
        if (relations == null)
          relations = new Relations();
        else
          relations.clear();

        if ((error = ArrayCodec.encodeArray(null, type.arrays(), object, type.arrays().getAnnotation(ArrayType.class).elementIds()[0], relations, null, validate, null)) == null)
          return relations;
      }
      else if (object instanceof JxObject && AnyType.isEnabled(type.objects())) {
        if (builder == null)
          builder = new StringBuilder();
        else
          builder.setLength(0);

        if ((error = jxEncoder.marshal((JxObject)object, null, builder, depth)) == null)
          return builder.toString();
      }
      else if (AnyType.isEnabled(type.booleans()) && Classes.isAssignableFrom(type.booleans().type(), object.getClass())) {
        final Object encoded = BooleanCodec.encodeObject(type.booleans().type(), type.booleans().encode(), object);
        if (encoded instanceof Error)
          error = (Error)encoded;
        else
          return encoded;
      }
      else if (AnyType.isEnabled(type.numbers()) && Classes.isAssignableFrom(type.numbers().type(), object.getClass())) {
        final Object encoded = NumberCodec.encodeObject(annotation, type.numbers().scale(), type.numbers().range(), type.numbers().type(), type.numbers().encode(), object, validate);
        if (encoded instanceof Error)
          error = (Error)encoded;
        else
          return encoded;
      }
      else if (AnyType.isEnabled(type.strings()) && Classes.isAssignableFrom(type.strings().type(), object.getClass())) {
        final Object encoded = StringCodec.encodeObject(annotation, getMethod, type.strings().pattern(), type.strings().type(), type.strings().encode(), object, validate);
        if (encoded instanceof Error)
          error = (Error)encoded;
        else
          return encoded;
      }
    }

    if (error == null)
      throw new IllegalStateException();

    return error;
  }

  final AnyProperty property;

  AnyCodec(final AnyProperty property, final Method getMethod, final Method setMethod) {
    super(getMethod, setMethod, property.name(), property.nullable(), property.use());
    this.property = property;
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