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

import java.math.BigDecimal;

import org.fastjax.util.Numbers;

public class Range {
  private static BigDecimal parseNumber(final StringBuilder builder, final String string, final int index, final boolean commaOk) throws ParseException {
    try {
      for (int i = index; i < string.length() - 1; i++) {
        final char ch = string.charAt(i);
        if (ch == ',') {
          if (commaOk)
            break;

          throw new ParseException("Illegal ',' in string: \"" + string + "\"", i);
        }

        builder.append(ch);
      }

      return builder.length() == 0 ? null : new BigDecimal(builder.toString());
    }
    catch (final NumberFormatException e) {
      final ParseException pe = new ParseException(string, index);
      pe.initCause(e);
      throw pe;
    }
  }

  private void checkMinMax(final String string) {
    if (min == null || max == null)
      return;

    final int compare = min.compareTo(max);
    if (compare > 0)
      throw new IllegalArgumentException("min=\"" + min + "\" > max=\"" + max + "\"");

    if (compare == 0 && (!minInclusive || !maxInclusive))
      throw new IllegalArgumentException(string + " defines an empty range");
  }

  private final BigDecimal min;
  private final boolean minInclusive;
  private final BigDecimal max;
  private final boolean maxInclusive;

  public Range(final BigDecimal min, final boolean minInclusive, final BigDecimal max, final boolean maxInclusive) {
    this.min = min;
    this.minInclusive = minInclusive;
    this.max = max;
    this.maxInclusive = maxInclusive;
    checkMinMax(toString());
  }

  public Range(final String string) throws ParseException {
    if (string.length() < 4)
      throw new IllegalArgumentException("string.length() < 4");

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

    checkMinMax(string);
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
  public boolean equals(final Object obj) {
    if (obj == this)
      return true;

    if (!(obj instanceof Range))
      return false;

    final Range that = (Range)obj;
    return (min != null ? min.equals(that.min) : that.min == null) && (max != null ? max.equals(that.max) : that.max == null) && minInclusive == that.minInclusive && maxInclusive == that.maxInclusive;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;
    hashCode = 31 * hashCode + min.hashCode() ^ 7;
    hashCode = 31 * hashCode + Boolean.hashCode(minInclusive);
    hashCode = 31 * hashCode + max.hashCode() ^ 7;
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