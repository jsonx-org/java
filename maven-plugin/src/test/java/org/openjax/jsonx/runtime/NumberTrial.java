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

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

class NumberTrial extends PropertyTrial<Number> {
  static void add(final List<PropertyTrial<?>> trials, final Field field, final Object object, final NumberProperty property) {
    final Range range = property.range().length() == 0 ? null : new Range(property.range());
    trials.add(new NumberTrial(ValidCase.CASE, field, object, toProperForm(field, property.form(), makeValid(range)), property));

    if (property.range().length() > 0)
      trials.add(new NumberTrial(RangeCase.CASE, field, object, toProperForm(field, property.form(), makeInvalid(range)), property));

    if (property.form() == Form.INTEGER && BigDecimal.class.isAssignableFrom(field.getType()))
      trials.add(new NumberTrial(FormCase.CASE, field, object, makeValid(range), property));

    if (property.use() == Use.REQUIRED)
      trials.add(new NumberTrial(UseCase.CASE, field, object, null, property));
  }

  static BigDecimal makeValid(final Range range) {
    return range == null ? null : range.getMax() == null ? range.getMin().add(BigDecimal.ONE) : range.getMin() == null ? range.getMax().subtract(BigDecimal.ONE) : range.getMax().subtract(range.getMin()).multiply(BigDecimal.valueOf(random.nextDouble())).add(range.getMin());
  }

  private static BigDecimal makeInvalid(final Range range) {
    return range.getMin() != null ? range.getMin().subtract(BigDecimal.ONE) : range.getMax().add(BigDecimal.ONE);
  }

  static Number toProperForm(final Form form, final BigDecimal value) {
    return form == Form.REAL ? value : value.toBigInteger();
  }

  static Number toProperForm(final Field field, final Form form, final BigDecimal value) {
    if (BigDecimal.class.isAssignableFrom(field.getType())) {
      final BigDecimal decimal = value == null ? BigDecimal.valueOf(random.nextDouble() * random.nextLong()) : value;
      return form == Form.INTEGER ? decimal.setScale(0, RoundingMode.DOWN) : decimal;
    }

    if (BigInteger.class.isAssignableFrom(field.getType()))
      return value == null ? BigInteger.valueOf(random.nextLong()) : value.toBigInteger();

    if (Long.class.isAssignableFrom(field.getType()))
      return value == null ? random.nextLong() : value.longValue();

    if (Integer.class.isAssignableFrom(field.getType()))
      return value == null ? random.nextInt() : value.intValue();

    if (Short.class.isAssignableFrom(field.getType()))
      return value == null ? (short)random.nextInt() : value.shortValue();

    if (Byte.class.isAssignableFrom(field.getType()))
      return value == null ? (byte)random.nextInt() : value.byteValue();

    if (Double.class.isAssignableFrom(field.getType()))
      return value == null ? random.nextDouble() * random.nextLong() : value.doubleValue();

    if (Float.class.isAssignableFrom(field.getType()))
      return value == null ? random.nextFloat() * random.nextInt() : value.floatValue();

    throw new UnsupportedOperationException("Number type is not supported: " + field.getType().getName());
  }

  final Form form;
  final Range range;

  private NumberTrial(final Case<? extends PropertyTrial<? super Number>> kase, final Field field, final Object object, final Number value, final NumberProperty property) {
    super(kase, field, object, value, property.name(), property.use());
    this.form = property.form();
    this.range = property.range().length() == 0 ? null : new Range(property.range());
  }
}