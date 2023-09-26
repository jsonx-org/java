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

import static org.libj.lang.Assertions.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.libj.lang.Classes;
import org.libj.lang.Numbers;
import org.libj.lang.ParseException;
import org.openjax.json.JsonUtil;

public class Range implements Serializable {
  private static final ConcurrentHashMap<Class<?>,ConcurrentHashMap<String,Range>> instances = new ConcurrentHashMap<>();

  @SuppressWarnings("unchecked")
  public static Range from(final String string, final int scale, Class<?> type) throws ParseException {
    assertNotNegative(scale, () -> "scale (" + scale + ") must be positive");
    final Class<? extends Number> numberType;
    if (type == null || type == Number.class || !Number.class.isAssignableFrom(type = Classes.box(type)))
      numberType = NumberCodec.getDefaultClass(scale);
    else
      numberType = (Class<? extends Number>)type;

    Range range;
    ConcurrentHashMap<String,Range> stringToRange = instances.get(numberType);
    if (stringToRange == null) {
      instances.put(numberType, stringToRange = new ConcurrentHashMap<>());
    }
    else {
      range = stringToRange.get(string);
      if (range != null)
        return range;
    }

    stringToRange.put(string, range = new Range(string, numberType));
    return range;
  }

  private static <N extends Number> N parseNumber(final StringBuilder b, final String string, final int start, final boolean commaOk, final Class<N> type) throws ParseException {
    try {
      for (int i = start, end = string.length() - 1; i < end; ++i) { // [N]
        final char ch = string.charAt(i);
        if (ch == ',') {
          if (commaOk)
            break;

          throw new ParseException("Illegal ',' in string: \"" + string + "\"", i);
        }

        b.append(ch);
      }

      return b.length() == 0 ? null : JsonUtil.parseNumber(type, b, true);
    }
    catch (final NumberFormatException e) {
      throw new ParseException(string, start, e);
    }
  }

  private void checkMinMax(final String string) {
    if (min == null || max == null)
      return;

    final int compare = Numbers.compare(min, max);
    if (compare > 0)
      throw new IllegalArgumentException("min=\"" + minStr + "\" > max=\"" + maxStr + "\"");

    if (compare == 0 && minInclusive != maxInclusive)
      throw new IllegalArgumentException(string + " defines an empty range");
  }

  private static String toString(final Number number) {
    if (number == null)
      return "null";

    if (Double.class.equals(number.getClass()))
      return NumberCodec.format(number.doubleValue());

    return number.toString();
  }

  private final Number min;
  private final String minStr;
  private final boolean minInclusive;
  private final Number max;
  private final String maxStr;
  private final boolean maxInclusive;
  private final int hashCode;
  private final String toString;

  <N extends Number> Range(final N min, final boolean minInclusive, final N max, final boolean maxInclusive) {
    this.min = min;
    this.minStr = toString(min);
    this.minInclusive = minInclusive;
    this.max = max;
    this.maxStr = toString(max);
    this.maxInclusive = maxInclusive;
    this.hashCode = getHashCode();
    this.toString = getString();
    checkMinMax(this.toString);
  }

  private Range(final String string, final Class<? extends Number> type) throws ParseException {
    final int len = string.length();
    if (len < 4)
      throw new IllegalArgumentException("Range min length is 4, but was " + len + (len > 0 ? ": " + string : ""));

    char ch = string.charAt(0);
    if (!(this.minInclusive = ch == '[') && ch != '(')
      throw new ParseException("Missing '[' or '(' in string: \"" + string + "\"", 0);

    final StringBuilder b = new StringBuilder();
    this.min = parseNumber(b, string, 1, true, type);
    this.minStr = toString(min);
    final int length = b.length() + 1;
    if (string.charAt(length) != ',')
      throw new ParseException("Missing ',' in string: \"" + string + "\"", length + 1);

    b.setLength(0);
    this.max = parseNumber(b, string, length + 1, false, type);
    this.maxStr = toString(max);

    ch = string.charAt(len - 1);
    if (!(this.maxInclusive = ch == ']') && ch != ')')
      throw new ParseException("Missing ']' or ')' in string: \"" + string + "\"", 0);

    this.hashCode = getHashCode();
    this.toString = getString();
    checkMinMax(string);
  }

  private int getHashCode() {
    int hashCode = 1;
    if (min != null)
      hashCode = 31 * hashCode + min.hashCode();

    hashCode = 31 * hashCode + Boolean.hashCode(minInclusive);
    if (max != null)
      hashCode = 31 * hashCode + max.hashCode();

    hashCode = 31 * hashCode + Boolean.hashCode(maxInclusive);
    return hashCode;
  }

  private String getString() {
    final StringBuilder b = new StringBuilder();
    b.append(minInclusive ? '[' : '(');
    if (min != null)
      b.append(minStr);

    b.append(',');
    if (max != null)
      b.append(maxStr);

    b.append(maxInclusive ? ']' : ')');
    return b.toString();
  }

  public Number getMin() {
    return min;
  }

  public boolean isMinInclusive() {
    return minInclusive;
  }

  public Number getMax() {
    return max;
  }

  public boolean isMaxInclusive() {
    return maxInclusive;
  }

  public boolean isValid(final Number value) {
    return (min == null || Integer.compare(Numbers.compare(min, value), 0) < (minInclusive ? 1 : 0))
        && (max == null || Integer.compare(Numbers.compare(value, max), 0) < (maxInclusive ? 1 : 0));
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
    return hashCode;
  }

  @Override
  public String toString() {
    return toString;
  }
}