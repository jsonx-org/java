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

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.libj.util.Classes;

class NumberTrial extends PropertyTrial<Number> {
  static void add(final List<PropertyTrial<?>> trials, final Field field, final Object object, final NumberProperty property) {
    final Range range = property.range().length() == 0 ? null : new Range(property.range());
    trials.add(new NumberTrial(ValidCase.CASE, field, object, toProperForm(field, property.scale(), makeValid(range)), property));

    if (property.range().length() > 0)
      trials.add(new NumberTrial(RangeCase.CASE, field, object, toProperForm(field, property.scale(), makeInvalid(range)), property));

    if (property.scale() != Integer.MAX_VALUE && BigDecimal.class.isAssignableFrom(field.getType()))
      trials.add(new NumberTrial(ScaleCase.CASE, field, object, setScale(makeValid(range), property.scale() + 1), property));

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

  static Number createValid(final Field field, final String range, final int scale) {
    return toProperForm(field, scale, range.length() == 0 ? null : makeValid(new Range(range)));
  }

  static Number createValid(final String range, final int scale) {
    return toProperForm(scale, range.length() == 0 ? BigDecimal.valueOf(PropertyTrial.random.nextDouble() * PropertyTrial.random.nextLong()) : makeValid(new Range(range)));
  }

  private static BigDecimal makeValid(final Range range) {
    if (range == null)
      return null;

    if (range.getMax() == null)
      return range.getMin().add(BigDecimal.ONE);

    if (range.getMin() == null)
      return range.getMax().subtract(BigDecimal.ONE);

    return range.getMax().subtract(range.getMin()).multiply(BigDecimal.valueOf(random.nextDouble())).add(range.getMin());
  }

  private static BigDecimal makeInvalid(final Range range) {
    return range.getMin() != null ? range.getMin().subtract(BigDecimal.ONE) : range.getMax().add(BigDecimal.ONE);
  }

  private static Number toProperForm(final int scale, final BigDecimal value) {
    return scale == 0 ? value.toBigInteger() : value;
  }

  private static Number setScale(final BigDecimal value, final int scale) {
//    if (scale == Integer.MIN_VALUE)
//      System.out.println();
//    System.out.println(value + " " + scale);
    return value == null ? null : scale == Integer.MAX_VALUE ? value : value.setScale(scale, RoundingMode.HALF_EVEN);
  }

  private static Number toProperForm(final Field field, final int scale, final BigDecimal value) {
    final boolean isOptional = Optional.class.isAssignableFrom(field.getType());
    final Class<?> type = isOptional ? Classes.getGenericClasses(field)[0] : field.getType();
    if (BigDecimal.class.isAssignableFrom(type))
      return setScale(value == null ? BigDecimal.valueOf(random.nextDouble() * random.nextLong()) : value, scale);

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

    throw new UnsupportedOperationException("Unsupported type: " + field.getType().getName());
  }

  final int scale;
  final Range range;

  private NumberTrial(final Case<? extends PropertyTrial<? super Number>> kase, final Field field, final Object object, final Object value, final NumberProperty property) {
    super(kase, field, object, value, property.name(), property.use());
    this.scale = property.scale();
    this.range = property.range().length() == 0 ? null : new Range(property.range());
  }
}