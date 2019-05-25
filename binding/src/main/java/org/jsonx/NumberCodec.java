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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.jsonx.ArrayValidator.Relation;
import org.jsonx.ArrayValidator.Relations;
import org.libj.util.Annotations;
import org.openjax.json.JsonParseException;
import org.openjax.json.JsonTypes;

class NumberCodec extends PrimitiveCodec<Number> {
  static Number decodeArray(final int scale, final String token) {
    final char ch;
    if ((ch = token.charAt(0)) != '-' && (ch < '0' || '9' < ch))
      return null;

    return NumberCodec.decodeObject(scale, token);
  }

  static Number decodeObject(final int scale, final String json) {
    try {
      return scale == 0 ? JsonTypes.parseInteger(json) : JsonTypes.parseDecimal(json);
    }
    catch (final JsonParseException e) {
      return null;
    }
  }

  static Error encodeArray(final Annotation annotation, final int scale, final String range, final Object object, final int index, final Relations relations, final boolean validate) {
    if (!(object instanceof Number))
      return Error.CONTENT_NOT_EXPECTED(object, -1);

    if (validate) {
      final Error error = NumberCodec.validate(annotation, (Number)object, scale, range);
      if (error != null)
        return error;
    }

    relations.set(index, new Relation(object, annotation));
    return null;
  }

  static Object encodeObject(final Annotation annotation, final int scale, final String range, final Number object, final boolean validate) throws EncodeException, ValidationException {
    if (validate) {
      final Error error = validate(annotation, object, scale, range);
      if (error != null)
        return error;
    }

    return String.valueOf(object);
  }

  private static Error validate(final Annotation annotation, final Number object, final int scale, final String range) {
    if (scale != 0) {
      final Error error = isScaleValid(object.toString(), scale, -1);
      if (error != null)
        return error;
    }
    else if (object.longValue() != object.doubleValue()) {
      return Error.SCALE_NOT_VALID(scale, object, -1);
    }

    if (range.length() > 0) {
      try {
        if (!new Range(range).isValid(object))
          return Error.RANGE_NOT_MATCHED(range, object, -1);
      }
      catch (final ParseException e) {
        throw new ValidationException("Invalid range attribute: " + Annotations.toSortedString(annotation, JsdUtil.ATTRIBUTES), e);
      }
    }

    return null;
  }

  private final int scale;
  private final Range range;

  NumberCodec(final NumberProperty property, final Field field) {
    super(field, property.name(), property.nullable(), property.use());
    this.scale = property.scale();
    if (property.range().length() == 0) {
      this.range = null;
    }
    else {
      try {
        this.range = new Range(property.range());
      }
      catch (final ParseException e) {
        throw new ValidationException("Invalid range attribute: " + Annotations.toSortedString(property, JsdUtil.ATTRIBUTES));
      }
    }
  }

  @Override
  boolean test(final char firstChar) {
    return firstChar == '-' || '0' <= firstChar && firstChar <= '9';
  }

  private static Error isScaleValid(final String value, final int scale, final int offset) {
    if (scale == Integer.MAX_VALUE)
      return null;

    final int dot = value.indexOf('.');
    if (scale != 0 ? value.length() - 1 - dot > scale : dot != -1)
      return Error.SCALE_NOT_VALID(scale, value, offset);

    return null;
  }

  @Override
  Error validate(final String json, final int offset) {
    final Error error = isScaleValid(json, scale, offset);
    if (error != null)
      return error;

    // FIXME: decode() is done here and in the caller's scope
    if (range != null && !range.isValid(parse(json)))
      return Error.RANGE_NOT_MATCHED(range, json, offset);

    return null;
  }

  @Override
  Number parse(final String json) {
    return decodeObject(scale, json);
  }

  @Override
  String elementName() {
    return "number";
  }
}