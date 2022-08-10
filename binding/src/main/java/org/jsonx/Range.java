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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import org.libj.lang.Numbers;
import org.libj.lang.ParseException;
import org.openjax.json.JsonUtil;

public class Range implements Cloneable, Serializable {
  private static BigDecimal parseNumber(final StringBuilder builder, final String string, final int start, final boolean commaOk) throws ParseException {
    try {
      for (int i = start, end = string.length() - 1; i < end; ++i) { // [N]
        final char ch = string.charAt(i);
        if (ch == ',') {
          if (commaOk)
            break;

          throw new ParseException("Illegal ',' in string: \"" + string + "\"", i);
        }

        builder.append(ch);
      }

      return builder.length() == 0 ? null : JsonUtil.parseNumber(BigDecimal.class, builder.toString());
    }
    catch (final NumberFormatException e) {
      final ParseException pe = new ParseException(string, start);
      pe.initCause(e);
      throw pe;
    }
  }

  private static void checkType(final String string, final BigDecimal value, final Class<?> type) {
    if (value.signum() == 0 || !type.isPrimitive() && !Number.class.isAssignableFrom(type))
      return;

    long limit;
    if (type == Long.class || type == long.class)
      limit = Long.MAX_VALUE;
    else if (type == Integer.class || type == int.class)
      limit = Integer.MAX_VALUE;
    else if (type == Short.class || type == short.class)
      limit = Short.MAX_VALUE;
    else if (type == Byte.class || type == byte.class)
      limit = Byte.MAX_VALUE;
    else
      return;

    limit *= value.signum();
    if (value.signum() == -1)
      limit -= 1;

    if (Numbers.compare(value, limit) == value.signum())
      throw new IllegalArgumentException(string + " defines a range that cannot be represented by " + type.getCanonicalName());
  }

  private void checkMinMax(final String string, final Class<?> type) {
    if (min == null || max == null)
      return;

    final int compare = min.compareTo(max);
    if (compare > 0)
      throw new IllegalArgumentException("min=\"" + min + "\" > max=\"" + max + "\"");

    if (compare == 0 && minInclusive != maxInclusive)
      throw new IllegalArgumentException(string + " defines an empty range");

    if (type == null)
      return;

    if (min != null)
      checkType(string, min, type);

    if (max != null)
      checkType(string, max, type);
  }

  private final BigDecimal min;
  private final boolean minInclusive;
  private final BigDecimal max;
  private final boolean maxInclusive;

  public Range(final BigDecimal min, final boolean minInclusive, final BigDecimal max, final boolean maxInclusive, final Class<?> type) {
    this.min = min;
    this.minInclusive = minInclusive;
    this.max = max;
    this.maxInclusive = maxInclusive;
    checkMinMax(toString(), type);
  }

  public Range(final String string, final Class<?> type) throws ParseException {
    if (string.length() < 4)
      throw new IllegalArgumentException("Range min length is 4, but was " + string.length() + (string.length() > 0 ? ": " + string : ""));

    char ch = string.charAt(0);
    if (!(this.minInclusive = ch == '[') && ch != '(')
      throw new ParseException("Missing '[' or '(' in string: \"" + string + "\"", 0);

    final StringBuilder builder = new StringBuilder();
    this.min = parseNumber(builder, string, 1, true);
    final int length = builder.length() + 1;
    if (string.charAt(length) != ',')
      throw new ParseException("Missing ',' in string: \"" + string + "\"", length + 1);

    builder.setLength(0);
    this.max = parseNumber(builder, string, length + 1, false);

    ch = string.charAt(string.length() - 1);
    if (!(this.maxInclusive = ch == ']') && ch != ')')
      throw new ParseException("Missing ']' or ')' in string: \"" + string + "\"", 0);

    checkMinMax(string, type);
  }

  public BigDecimal getMin() {
    return this.min;
  }

  public boolean isMinInclusive() {
    return this.minInclusive;
  }

  public BigDecimal getMax() {
    return this.max;
  }

  public boolean isMaxInclusive() {
    return this.maxInclusive;
  }

  public boolean isValid(final Number value) {
    final boolean minValid = min == null || Integer.compare(Numbers.compare(min, value), 0) < (minInclusive ? 1 : 0);
    final boolean maxValid = max == null || Integer.compare(Numbers.compare(value, max), 0) < (maxInclusive ? 1 : 0);
    return minValid && maxValid;
  }

  @Override
  public Range clone() {
    try {
      return (Range)super.clone();
    }
    catch (final CloneNotSupportedException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this)
      return true;

    if (!(obj instanceof Range))
      return false;

    final Range that = (Range)obj;
    if (!Objects.equals(min, that.min))
      return false;

    if (!Objects.equals(max, that.max))
      return false;

    if (minInclusive != that.minInclusive)
      return false;

    if (maxInclusive != that.maxInclusive)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;
    if (min != null)
      hashCode = 31 * hashCode + min.hashCode();

    hashCode = 31 * hashCode + Boolean.hashCode(minInclusive);
    if (max != null)
      hashCode = 31 * hashCode + max.hashCode();

    hashCode = 31 * hashCode + Boolean.hashCode(maxInclusive);
    return hashCode;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append(minInclusive ? '[' : '(');
    if (min != null)
      builder.append(min);

    builder.append(',');
    if (max != null)
      builder.append(max);

    builder.append(maxInclusive ? ']' : ')');
    return builder.toString();
  }
}