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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.openjax.jsonx.runtime.ArrayValidator.Relation;
import org.openjax.jsonx.runtime.ArrayValidator.Relations;
import org.openjax.standard.util.Annotations;
import org.openjax.standard.util.Strings;

class NumberCodec extends PrimitiveCodec<Number> {
  static Number decodeArray(final Form form, final String token) {
    final char ch;
    if ((ch = token.charAt(0)) != '-' && (ch < '0' || '9' < ch))
      return null;

    return NumberCodec.decode(form, token);
  }

  static StringBuilder encodeArray(final Annotation annotation, final Form form, final String range, final Object object, final int index, final Relations relations, final boolean validate) {
    if (!(object instanceof Number))
      return contentNotExpected(ArrayIterator.preview(object));

    if (validate) {
      final StringBuilder error = NumberCodec.validate(annotation, (Number)object, form, range);
      if (error != null)
        return error;
    }

    relations.set(index, new Relation(object, annotation));
    return null;
  }

  static Object encode(final Annotation annotation, final Form form, final String range, final Number object, final boolean validate) throws EncodeException, ValidationException {
    if (validate) {
      final StringBuilder error = validate(annotation, object, form, range);
      if (error != null)
        return error;
    }

    return String.valueOf(object);
  }

  static StringBuilder validate(final Annotation annotation, final Number object, final Form form, final String range) {
    if (form == Form.INTEGER && object.longValue() != object.doubleValue())
      return new StringBuilder("Illegal ").append(Form.class.getSimpleName()).append(".INTEGER value: ").append(Strings.truncate(String.valueOf(object), 16));;

    if (range.length() > 0) {
      try {
        if (!new Range(range).isValid(object))
          return new StringBuilder("Range ").append(range).append(" is not matched: ").append(Strings.truncate(String.valueOf(object), 16));
      }
      catch (final ParseException e) {
        throw new ValidationException("Invalid range attribute: " + Annotations.toSortedString(annotation, JxUtil.ATTRIBUTES), e);
      }
    }

    return null;
  }

  static Number decode(final Form form, final String json) {
    try {
      return form == Form.INTEGER ? new BigInteger(json) : new BigDecimal(json);
    }
    catch (final NumberFormatException e) {
      return null;
    }
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
        throw new ValidationException("Invalid range attribute: " + Annotations.toSortedString(property, JxUtil.ATTRIBUTES));
      }
    }
  }

  @Override
  boolean test(final char firstChar) {
    return firstChar == '-' || '0' <= firstChar && firstChar <= '9';
  }

  @Override
  StringBuilder validate(final String json) {
    if (form != Form.REAL) {
      final int dot = json.indexOf('.');
      if (dot != -1)
        return new StringBuilder("Illegal ").append(Form.class.getSimpleName()).append(".INTEGER value");
    }

    // FIXME: decode() is done here and in the caller's scope
    return range != null && !range.isValid(parse(json)) ? new StringBuilder("Range ").append(range).append(" is not matched: ").append(json) : null;
  }

  @Override
  Number parse(final String json) {
    return decode(form, json);
  }

  @Override
  String elementName() {
    return "number";
  }
}