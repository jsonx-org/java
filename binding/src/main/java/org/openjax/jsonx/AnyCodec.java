/* Copyright (c) 2019 OpenJAX
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

package org.openjax.jsonx;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import org.openjax.jsonx.ArrayValidator.Relations;
import org.openjax.standard.json.JsonReader;
import org.openjax.standard.util.function.TriPredicate;

class AnyCodec extends Codec {
  static Object decode(final Annotation annotation, final String token, final JsonReader reader, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException {
    final t[] types = annotation.annotationType() == AnyElement.class ? ((AnyElement)annotation).types() : annotation.annotationType() == AnyProperty.class ? ((AnyProperty)annotation).types() : null;
    final char firstChar = token.charAt(0);
    Error error = null;
    if (firstChar == '[') {
      for (final t type : types.length > 0 ? types : new t[] {AnyType.arrays}) {
        if (AnyType.isEnabled(type.arrays())) {
          final Object value = ArrayCodec.decodeArray(null, type.arrays(), token, reader, null, onPropertyDecode);
          if (!(value instanceof Error))
            return value;

          if (error == null || error.offset < ((Error)value).offset)
            error = (Error)value;
        }
      }
    }
    else if (firstChar == '{') {
      for (final t type : types.length > 0 ? types : new t[] {AnyType.objects}) {
        if (AnyType.isEnabled(type.objects())) {
          final Object value = ObjectCodec.decodeArray(type.objects(), token, reader, onPropertyDecode);
          if (!(value instanceof Error))
            return value;

          if (error == null || error.offset < ((Error)value).offset)
            error = (Error)value;
        }
      }
    }
    else {
      for (final t type : types.length > 0 ? types : new t[] {AnyType.fromToken(token)}) {
        if (type.booleans()) {
          final Boolean value = BooleanCodec.decodeArray(token);
          if (value != null)
            return value;
        }
        else if (AnyType.isEnabled(type.numbers())) {
          final Number value = NumberCodec.decodeArray(type.numbers().form(), token);
          if (value != null)
            return value;
        }
        else if (AnyType.isEnabled(type.strings())) {
          final String value = StringCodec.decodeArray(token);
          if (value != null)
            return value;
        }
      }
    }

    return error != null ? error : Error.CONTENT_NOT_EXPECTED(token, reader.getPosition());
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
      else if (type.booleans()) {
        error = BooleanCodec.encodeArray(annotation, object, index, relations);
        if (error == null)
          return null;
      }
      else if (AnyType.isEnabled(type.numbers())) {
        error = NumberCodec.encodeArray(annotation, type.numbers().form(), type.numbers().range(), object, index, relations, validate);
        if (error == null)
          return null;
      }
      else if (AnyType.isEnabled(type.objects())) {
        error = ObjectCodec.encodeArray(annotation, object, index, relations);
        if (error == null)
          return null;
      }
      else if (AnyType.isEnabled(type.strings())) {
        error = StringCodec.encodeArray(annotation, type.strings(), object, index, relations, validate);
        if (error == null)
          return null;
      }
    }

    if (error == Error.NULL)
      throw new IllegalStateException();

    return error;
  }

  static Object encodeObject(final Annotation annotation, final t[] types, final Object object, final JxEncoder jxEncoder, final int depth, final boolean validate) throws EncodeException, ValidationException {
    Error error = null;
    if (object == null)
      return JsdUtil.isNullable(annotation) ? "null" : Error.ILLEGAL_VALUE_NULL;

    for (final t type : types.length > 0 ? types : new t[] {AnyType.fromObject(object)}) {
      if (object instanceof List && AnyType.isEnabled(type.arrays())) {
        final Relations relations = new Relations();
        if ((error = ArrayCodec.encodeArray(null, type.arrays(), object, type.arrays().getAnnotation(ArrayType.class).elementIds()[0], relations, null, validate, null)) == null)
          return relations;
      }
      else if (object instanceof Boolean && type.booleans()) {
        return BooleanCodec.encodeObject((Boolean)object);
      }
      else if (object instanceof Number && AnyType.isEnabled(type.numbers())) {
        final Object encoded = NumberCodec.encodeObject(annotation, type.numbers().form(), type.numbers().range(), (Number)object, validate);
        if (encoded instanceof Error)
          error = (Error)encoded;
        else
          return encoded;
      }
      else if (object instanceof JxObject && AnyType.isEnabled(type.objects())) {
        final StringBuilder builder = new StringBuilder();
        if ((error = jxEncoder.marshal((JxObject)object, null, builder, depth)) == null)
          return builder.toString();
      }
      else if (object instanceof String && AnyType.isEnabled(type.strings())) {
        final Object encoded = StringCodec.encodeObject(annotation, type.strings(), (String)object, validate);
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

  AnyCodec(final AnyProperty property, final Field field) {
    super(field, property.name(), property.nullable(), property.use());
    this.property = property;
  }

  @Override
  String elementName() {
    return "any";
  }
}