/* Copyright (c) 2018 lib4j
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

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.fastjax.util.Annotations;

public class NumberSpec extends PrimitiveSpec<Number> {
  private final Form form;
  private final Range range;

  public NumberSpec(final Field field, final NumberProperty property) {
    super(field, property.name(), property.use());
    this.form = property.form();
    try {
      this.range = new Range(property.range());
    }
    catch (final ParseException e) {
      throw new ValidationException("Invalid range attribute: " + Annotations.toSortedString(property, AttributeComparator.instance));
    }
  }

  @Override
  public boolean test(final char firstChar) {
    return firstChar == '-' || '0' <= firstChar && firstChar <= '9';
  }

  @Override
  public String validate(final String json) {
    if (form == Form.REAL)
      return null;

    final int dot = json.indexOf('.');
    if (dot != -1)
      return "Illegal non-INTEGER value";

    // FIXME: decode() is done here and in the caller's scope
    return range.isValid(decode(json)) ? null : "Range is not matched";
  }

  @Override
  public Number decode(final String json) {
    return form == Form.INTEGER ? new BigInteger(json) : new BigDecimal(json);
  }

  @Override
  public String elementName() {
    return "number";
  }
}