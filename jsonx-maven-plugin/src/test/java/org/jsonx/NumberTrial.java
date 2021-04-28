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

final class NumberTrial extends PropertyTrial<Number> {
  static void add(final List<? super PropertyTrial<?>> trials, final Method getMethod, final Method setMethod, final Object object, final NumberProperty property) {
    try {
      logger.debug("Adding: " + getMethod.getDeclaringClass() + "." + getMethod.getName() + "()");
      final Range range = property.range().length() == 0 ? null : new Range(property.range(), JsdUtil.getRealType(getMethod));
      trials.add(new NumberTrial(ValidCase.CASE, getMethod, setMethod, object, toProperForm(JsdUtil.getRealType(getMethod), property.decode(), property.scale(), makeValid(range)), property));

      if (property.range().length() > 0)
        trials.add(new NumberTrial(RangeCase.CASE, getMethod, setMethod, object, toProperForm(JsdUtil.getRealType(getMethod), property.decode(), property.scale(), makeInvalid(range)), property));

      if (property.scale() != Integer.MAX_VALUE && BigDecimal.class.isAssignableFrom(JsdUtil.getRealType(getMethod)))
        trials.add(new NumberTrial(ScaleCase.CASE, getMethod, setMethod, object, toProperForm(JsdUtil.getRealType(getMethod), property.decode(), property.scale() + 1, makeValid(range)), property));

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
      return toProperForm(type, decode, scale, range.length() == 0 ? null : makeValid(new Range(range, type)));
    }
    catch (final ParseException e) {
      throw new IllegalArgumentException(e);
    }
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

  private static Number setScale(final BigDecimal value, final int scale) {
    return scale == Integer.MAX_VALUE ? value : scale == 0 ? value.toBigInteger() : value.setScale(scale, RoundingMode.HALF_EVEN);
  }

  private static Object toProperForm(final Class<?> type, final String decode, final int scale, final BigDecimal value) {
    final Number result;
    if (BigInteger.class.isAssignableFrom(type))
      result = value == null ? BigInteger.valueOf(random.nextLong()) : value.toBigInteger();
    else if (type == Long.class || type == long.class)
      result = value == null ? random.nextLong() : value.longValue();
    else if (type == Integer.class || type == int.class)
      result = value == null ? random.nextInt() : value.intValue();
    else if (type == Short.class || type == short.class)
      result = value == null ? (short)random.nextInt() : value.shortValue();
    else if (type == Byte.class || type == byte.class)
      result = value == null ? (byte)random.nextInt() : value.byteValue();
    else if (type == Double.class || type == double.class)
      result = value == null ? random.nextDouble() * random.nextLong() : value.doubleValue();
    else if (type == Float.class || type == float.class)
      result = value == null ? random.nextFloat() * random.nextInt() : value.floatValue();
    else
      result = setScale(value == null ? BigDecimal.valueOf(random.nextDouble() * random.nextLong()) : value, scale);

    if (decode != null && !decode.isEmpty())
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
    this.range = property.range().length() == 0 ? null : new Range(property.range(), JsdUtil.getRealType(getMethod));
  }
}