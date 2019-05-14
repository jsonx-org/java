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
import java.math.BigDecimal;
import java.math.BigInteger;

import org.jsonx.ArrayValidator.Relation;
import org.jsonx.ArrayValidator.Relations;
import org.libj.util.Annotations;

class NumberCodec extends PrimitiveCodec<Number> {
  static Number decodeArray(final Form form, final String token) {
    final char ch;
    if ((ch = token.charAt(0)) != '-' && (ch < '0' || '9' < ch))
      return null;

    return NumberCodec.decodeObject(form, token);
  }

  static Number decodeObject(final Form form, final String json) {
    try {
      return form == Form.INTEGER ? new BigInteger(json) : new BigDecimal(json);
    }
    catch (final NumberFormatException e) {
      return null;
    }
  }

  static Error encodeArray(final Annotation annotation, final Form form, final String range, final Object object, final int index, final Relations relations, final boolean validate) {
    if (!(object instanceof Number))
      return Error.CONTENT_NOT_EXPECTED(object, -1);

    if (validate) {
      final Error error = NumberCodec.validate(annotation, (Number)object, form, range);
      if (error != null)
        return error;
    }

    relations.set(index, new Relation(object, annotation));
    return null;
  }

  static Object encodeObject(final Annotation annotation, final Form form, final String range, final Number object, final boolean validate) throws EncodeException, ValidationException {
    if (validate) {
      final Error error = validate(annotation, object, form, range);
      if (error != null)
        return error;
    }

    return String.valueOf(object);
  }

  private static Error validate(final Annotation annotation, final Number object, final Form form, final String range) {
    if (form == Form.INTEGER && object.longValue() != object.doubleValue())
      return Error.INTEGER_NOT_VALID(Form.class, object, -1);

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

  private final Form form;
  private final Range range;

  NumberCodec(final NumberProperty property, final Field field) {
    super(field, property.name(), property.nullable(), property.use());
    this.form = property.form();
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

  @Override
  Error validate(final String json, final int offset) {
    if (form != Form.REAL) {
      final int dot = json.indexOf('.');
      if (dot != -1)
        return Error.INTEGER_NOT_VALID(Form.class, json, offset);
    }

    // FIXME: decode() is done here and in the caller's scope
    return range != null && !range.isValid(parse(json)) ? Error.RANGE_NOT_MATCHED(range, json, offset) : null;
  }

  @Override
  Number parse(final String json) {
    return decodeObject(form, json);
  }

  @Override
  String elementName() {
    return "number";
  }
}