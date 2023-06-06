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

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

import org.libj.lang.Classes;
import org.libj.lang.ParseException;
import org.libj.math.SafeMath;

final class NumberTrial extends PropertyTrial<Number> {
  static void add(final List<? super PropertyTrial<?>> trials, final Method getMethod, final Method setMethod, final Object object, final NumberProperty property) {
    try {
      if (logger.isDebugEnabled()) logger.debug("Adding: " + getMethod.getDeclaringClass() + "." + getMethod.getName() + "()");
      final Range range = property.range().length() == 0 ? null : Range.from(property.range(), JsdUtil.getRealType(getMethod));
      trials.add(new NumberTrial(ValidCase.CASE, getMethod, setMethod, object, toProperForm(JsdUtil.getRealType(getMethod), property.decode(), property.scale(), makeValid(range)), property));

      if (property.range().length() > 0)
        trials.add(new NumberTrial(RangeCase.CASE, getMethod, setMethod, object, toProperForm(JsdUtil.getRealType(getMethod), property.decode(), property.scale(), makeInvalid(range)), property));

      if (property.scale() != Integer.MAX_VALUE && Double.class.isAssignableFrom(JsdUtil.getRealType(getMethod))) {
        final int invalidScale = property.scale() + 1;
        final Object value = toProperForm(JsdUtil.getRealType(getMethod), property.decode(), property.scale() + 1, makeValid(range));
        final String str = value.toString();
        assertEquals(invalidScale, str.length() - str.indexOf('.') - 1);
        trials.add(new NumberTrial(ScaleCase.CASE, getMethod, setMethod, object, value, property));
      }

      if (getMethod.getReturnType().isPrimitive())
        return;

      if (property.use() == Use.REQUIRED) {
        trials.add(new NumberTrial(getNullableCase(property.nullable()), getMethod, setMethod, object, null, property));
      }
      else if (property.nullable()) {
        trials.add(new NumberTrial(OptionalNullableCase.CASE, getMethod, setMethod, object, null, property));
        trials.add(new NumberTrial(OptionalNullableCase.CASE, getMethod, setMethod, object, Optional.ofNullable(null), property));
      }
      else {
        trials.add(new NumberTrial(OptionalNotNullableCase.CASE, getMethod, setMethod, object, null, property));
      }
    }
    catch (final ParseException e) {
      throw new IllegalArgumentException(e);
    }
  }

  static Object createValid(final Class<?> type, final String decode, final String range, final int scale) {
    try {
      return toProperForm(type, decode, scale, range.length() == 0 ? null : makeValid(Range.from(range, type)));
    }
    catch (final ParseException e) {
      throw new IllegalArgumentException(e);
    }
  }

  private static Double makeValid(final Range range) {
    if (range == null)
      return null;

    if (range.getMax() == null) {
      final double x = range.getMin().doubleValue() + 1d;
      return x;
    }

    if (range.getMin() == null)
      return range.getMax().doubleValue() - 1d;

    return ((range.getMax().doubleValue() - range.getMin().doubleValue()) * random.nextDouble()) + range.getMin().doubleValue();
  }

  private static Double makeInvalid(final Range range) {
    return range.getMin() != null ? range.getMin().doubleValue() - 1d : range.getMax().doubleValue() + 1d;
  }

  private static Number setScale(final Double value, final int scale) {
    return scale == Integer.MAX_VALUE ? value : scale == 0 ? value.longValue() : SafeMath.round(value, scale, RoundingMode.FLOOR);
  }

  private static Number setScale(final BigDecimal value, final int scale) {
    return scale == Integer.MAX_VALUE ? value : scale == 0 ? value.longValue() : value.setScale(scale, RoundingMode.FLOOR);
  }

  private static Object toProperForm(final Class<?> type, final String decode, final int scale, final Double value) {
    final Number result;
    if (BigInteger.class.isAssignableFrom(type))
      result = BigInteger.valueOf(value == null ? random.nextLong() : value.longValue());
    else if (type == Long.class || type == long.class)
      result = value == null ? random.nextLong() : value.longValue();
    else if (type == Integer.class || type == int.class)
      result = value == null ? random.nextInt() : value.intValue();
    else if (type == Short.class || type == short.class)
      result = value == null ? (short)random.nextInt() : value.shortValue();
    else if (type == Byte.class || type == byte.class)
      result = value == null ? (byte)random.nextInt() : value.byteValue();
    else if (type == Float.class || type == float.class)
      result = value == null ? random.nextFloat() * random.nextInt() : value.floatValue();
    else if (BigDecimal.class.isAssignableFrom(type))
      result = setScale(value == null ? BigDecimal.valueOf(random.nextDouble() * random.nextLong()) : BigDecimal.valueOf(value), scale);
    else
      result = setScale(value == null ? random.nextDouble() * random.nextLong() : value, scale);

    if (decode != null && decode.length() > 0)
      return JsdUtil.invoke(JsdUtil.parseExecutable(decode, String.class), String.valueOf(result));

    if (Classes.isAssignableFrom(type, result.getClass()))
      return result;

    if (type.isAssignableFrom(String.class))
      return result.toString();

    throw new IllegalArgumentException();
  }

  final int scale;
  final Range range;

  private NumberTrial(final Case<? extends PropertyTrial<? super Number>> kase, final Method getMethod, final Method setMethod, final Object object, final Object value, final NumberProperty property) throws ParseException {
    super(kase, JsdUtil.getRealType(getMethod), getMethod, setMethod, object, value, property.name(), property.use(), property.decode(), property.encode(), false);
    this.scale = property.scale();
    this.range = property.range().length() == 0 ? null : Range.from(property.range(), JsdUtil.getRealType(getMethod));
  }
}