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
import java.util.Optional;

import org.openjax.standard.util.Classes;

class NumberTrial extends PropertyTrial<Number> {
  static void add(final List<PropertyTrial<?>> trials, final Field field, final Object object, final NumberProperty property) {
    final Range range = property.range().length() == 0 ? null : new Range(property.range());
    trials.add(new NumberTrial(ValidCase.CASE, field, object, toProperForm(field, property.form(), makeValid(range)), property));

    if (property.range().length() > 0)
      trials.add(new NumberTrial(RangeCase.CASE, field, object, toProperForm(field, property.form(), makeInvalid(range)), property));

    if (property.form() == Form.INTEGER && BigDecimal.class.isAssignableFrom(field.getType()))
      trials.add(new NumberTrial(FormCase.CASE, field, object, makeValid(range), property));

    if (property.use() == Use.REQUIRED) {
      trials.add(new NumberTrial(getNullableCase(property.nullable()), field, object, null, property));
    }
    else if (property.nullable()) {
      trials.add(new NumberTrial(OptionalNullableCase.CASE, field, object, null, property));
      trials.add(new NumberTrial(OptionalNullableCase.CASE, field, object, Optional.ofNullable(null), property));
    }
    else {
      trials.add(new NumberTrial(OptionalNotNullableCase.CASE, field, object, null, property));
    }
  }

  static Number createValid(final Field field, final String range, final Form form) {
    return toProperForm(field, form, range.length() == 0 ? null : makeValid(new Range(range)));
  }

  static Number createValid(final String range, final Form form) {
    return toProperForm(form, range.length() == 0 ? BigDecimal.valueOf(PropertyTrial.random.nextDouble() * PropertyTrial.random.nextLong()) : makeValid(new Range(range)));
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
    final boolean isOptional = Optional.class.isAssignableFrom(field.getType());
    final Class<?> type = isOptional ? Classes.getGenericTypes(field)[0] : field.getType();
    if (BigDecimal.class.isAssignableFrom(type)) {
      final BigDecimal decimal = value == null ? BigDecimal.valueOf(random.nextDouble() * random.nextLong()) : value;
      return form == Form.INTEGER ? decimal.setScale(0, RoundingMode.DOWN) : decimal;
    }

    if (BigInteger.class.isAssignableFrom(type))
      return value == null ? BigInteger.valueOf(random.nextLong()) : value.toBigInteger();

    if (Long.class.isAssignableFrom(type))
      return value == null ? random.nextLong() : value.longValue();

    if (Integer.class.isAssignableFrom(type))
      return value == null ? random.nextInt() : value.intValue();

    if (Short.class.isAssignableFrom(type))
      return value == null ? (short)random.nextInt() : value.shortValue();

    if (Byte.class.isAssignableFrom(type))
      return value == null ? (byte)random.nextInt() : value.byteValue();

    if (Double.class.isAssignableFrom(type))
      return value == null ? random.nextDouble() * random.nextLong() : value.doubleValue();

    if (Float.class.isAssignableFrom(type))
      return value == null ? random.nextFloat() * random.nextInt() : value.floatValue();

    throw new UnsupportedOperationException("Number type is not supported: " + field.getType().getName());
  }

  final Form form;
  final Range range;

  private NumberTrial(final Case<? extends PropertyTrial<? super Number>> kase, final Field field, final Object object, final Object value, final NumberProperty property) {
    super(kase, field, object, value, property.name(), property.use());
    this.form = property.form();
    this.range = property.range().length() == 0 ? null : new Range(property.range());
  }
}