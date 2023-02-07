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
import java.util.HashMap;
import java.util.Objects;

import org.libj.lang.Numbers;
import org.libj.lang.ParseException;
import org.openjax.json.JsonUtil;

public class Range implements Serializable {
  private static final HashMap<String,Range> instances = new HashMap<>();

  public static Range from(final String string, final Class<?> type) throws ParseException {
    Range range = instances.get(string);
    if (range == null)
      instances.put(string, range = new Range(string, type));

    return range;
  }

  private static BigDecimal parseNumber(final StringBuilder builder, final String string, final int start, final boolean commaOk) throws ParseException {
    try {
      for (int i = start, end = string.length() - 1; i < end; ++i) { // [N]
        final char ch = string.charAt(i);
        if (ch == ',') {
          if (commaOk)
            break;

          throw new ParseException("Illegal ',' in string: \"" + string + '"', i);
        }

        builder.append(ch);
      }

      return builder.length() == 0 ? null : JsonUtil.parseNumber(BigDecimal.class, builder);
    }
    catch (final NumberFormatException e) {
      final ParseException pe = new ParseException(string, start);
      pe.initCause(e);
      throw pe;
    }
  }

  private static void checkType(final String string, final Number value, final Class<?> type) {
    final int signum;
    if (!type.isPrimitive() && !Number.class.isAssignableFrom(type) || (signum = Numbers.signum(value)) == 0)
      return;

    final long limit;
    if (type == Long.class || type == long.class)
      limit = signum == -1 ? Long.MIN_VALUE : Long.MAX_VALUE;
    else if (type == Integer.class || type == int.class)
      limit = signum == -1 ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    else if (type == Short.class || type == short.class)
      limit = signum == -1 ? Short.MIN_VALUE : Short.MAX_VALUE;
    else if (type == Byte.class || type == byte.class)
      limit = signum == -1 ? Byte.MIN_VALUE : Byte.MAX_VALUE;
    else
      return;

    if (Numbers.compare(value, limit) == signum)
      throw new IllegalArgumentException(string + " defines a range that cannot be represented by " + type.getCanonicalName());
  }

  private void checkMinMax(final String string, final Class<?> type) {
    if (min == null || max == null)
      return;

    final int compare = min.compareTo(max);
    if (compare > 0)
      throw new IllegalArgumentException("min=\"" + min + "\" > max=\"" + max + '"');

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
  private final String toString;

  Range(final BigDecimal min, final boolean minInclusive, final BigDecimal max, final boolean maxInclusive, final Class<?> type) {
    this.min = min;
    this.minInclusive = minInclusive;
    this.max = max;
    this.maxInclusive = maxInclusive;
    this.toString = getString();
    checkMinMax(toString(), type);
  }

  private Range(final String string, final Class<?> type) throws ParseException {
    if (string.length() < 4)
      throw new IllegalArgumentException("Range min length is 4, but was " + string.length() + (string.length() > 0 ? ": " + string : ""));

    char ch = string.charAt(0);
    if (!(this.minInclusive = ch == '[') && ch != '(')
      throw new ParseException("Missing '[' or '(' in string: \"" + string + '"', 0);

    final StringBuilder builder = new StringBuilder();
    this.min = parseNumber(builder, string, 1, true);
    final int length = builder.length() + 1;
    if (string.charAt(length) != ',')
      throw new ParseException("Missing ',' in string: \"" + string + '"', length + 1);

    builder.setLength(0);
    this.max = parseNumber(builder, string, length + 1, false);

    ch = string.charAt(string.length() - 1);
    if (!(this.maxInclusive = ch == ']') && ch != ')')
      throw new ParseException("Missing ']' or ')' in string: \"" + string + '"', 0);

    this.toString = getString();
    checkMinMax(string, type);
  }

  private String getString() {
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

  public BigDecimal getMin() {
    return min;
  }

  public boolean isMinInclusive() {
    return minInclusive;
  }

  public BigDecimal getMax() {
    return max;
  }

  public boolean isMaxInclusive() {
    return maxInclusive;
  }

  public boolean isValid(final Number value) {
    final boolean minValid = min == null || Integer.compare(Numbers.compare(min, value), 0) < (minInclusive ? 1 : 0);
    if (!minValid)
      return false;

    return max == null || Integer.compare(Numbers.compare(value, max), 0) < (maxInclusive ? 1 : 0);
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
    return toString;
  }
}