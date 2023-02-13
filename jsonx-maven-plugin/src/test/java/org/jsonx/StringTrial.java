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
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.libj.lang.Characters;
import org.libj.lang.Strings;

import com.mifmif.common.regex.Generex;

final class StringTrial extends PropertyTrial<String> {
  private static final int stringLength = 12;
  private static final char[] ascii = new char[95];

  static {
    for (int i = 0, i$ = ascii.length; i < i$; ++i) // [A]
      ascii[i] = (char)(i + 32);
  }

  private static String filterPrintable(final String string) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0, i$ = string.length(); i < i$; ++i) { // [N]
      final char ch = string.charAt(i);
      if (ch < 32532 && ch != '"' && ch != '\\' && ch != '\n' && ch != '\r' && Characters.isPrintable(ch))
        builder.append(ch);
    }

    return builder.toString();
  }

  private static final class StringGen {
    private final String pattern;
    private final Generex generex;

    private StringGen(String pattern) {
      if (pattern.isEmpty()) {
        this.generex = null;
      }
      else {
        if (Strings.startsWith(pattern, '^'))
          pattern = pattern.substring(1);

        if (Strings.endsWith(pattern, '$'))
          pattern = pattern.substring(0, pattern.length() - 1);

        if (!Generex.isValidPattern(pattern))
          throw new UnsupportedOperationException("Regex pattern \"" + pattern + "\" is not supported");

        this.generex = new Generex(pattern);
      }

      this.pattern = pattern;
    }

    String random() {
      if (generex == null)
        return filterPrintable(randomString(stringLength));

      for (int i = 0; i < 1000; ++i) { // [N]
        final String string = filterPrintable(generex.random(stringLength));
        if (string.matches(pattern))
          return string;
      }

      throw new IllegalArgumentException("Cannot create string for pattern: " + pattern);
    }
  }

  static void add(final List<? super PropertyTrial<?>> trials, final Method getMethod, final Method setMethod, final Object object, final StringProperty property) {
    if (logger.isDebugEnabled()) logger.debug("Adding: " + getMethod.getDeclaringClass() + "." + getMethod.getName() + "()");
    trials.add(new StringTrial(ValidCase.CASE, getMethod, setMethod, object, createValid(JsdUtil.getRealType(getMethod), property.decode(), property.pattern()), property));
    if (property.pattern().length() > 0)
      trials.add(new StringTrial(PatternCase.CASE, getMethod, setMethod, object, createInvalid(JsdUtil.getRealType(getMethod), property.decode(), property.pattern()), property));

    if (getMethod.getReturnType().isPrimitive())
      return;

    if (property.use() == Use.REQUIRED) {
      trials.add(new StringTrial(getNullableCase(property.nullable()), getMethod, setMethod, object, null, property));
    }
    else if (property.nullable()) {
      trials.add(new StringTrial(OptionalNullableCase.CASE, getMethod, setMethod, object, null, property));
      trials.add(new StringTrial(OptionalNullableCase.CASE, getMethod, setMethod, object, Optional.ofNullable(null), property));
    }
    else {
      trials.add(new StringTrial(OptionalNotNullableCase.CASE, getMethod, setMethod, object, null, property));
    }
  }

  private static String randomString(final int len) {
    final char[] chars = new char[len];
    int j = 0;
    for (int i = 0; i < len;) { // [N]
      final char ch = ascii[(int)(Math.random() * ascii.length)];
      if (j > 0) {
        if (ch < '0' || '9' < ch)
          continue;

        --j;
      }
      else if (ch == '%') {
        j = 2;
      }

      chars[i++] = ch;
    }

    return new String(chars);
  }

  static Object createValid(final Class<?> type, final String decode, String pattern) {
    String prefix = "";
    if (type == URL.class) {
      prefix = "https://jsonx.org/";
      pattern = "([a-zA-Z0-9]+/)+";
    }

    String pass = null;
    for (int i = 0; pass == null && i < 1000; ++i) // [N]
      pass = new StringGen(pattern).random();

    if (pass == null)
      throw new IllegalStateException("Could not generate a pass string for regex: " + pattern);

    return convertToDesiredType(decode, prefix + pass);
  }

  @SuppressWarnings("null")
  private static Object createInvalid(final Class<?> type, final String decode, final String regex) {
    String prefix = "";
    String fail = null;
    for (int i = 0; i < 1000; ++i) { // [N]
      if (type == UUID.class) {
        fail = UUID.randomUUID().toString();
      }
      else if (type == URL.class) {
        fail = "https://google.com/" + Strings.getRandomAlphaNumeric(stringLength) + "/" + Strings.getRandomAlphaNumeric(stringLength);
      }
      else if (Number.class.isAssignableFrom(type)) {
        fail = String.valueOf(-Math.random());
      }
      else {
        fail = randomString(stringLength);
      }

      if (!fail.matches(regex))
        break;
    }

    if (fail.matches(regex))
      throw new IllegalStateException("Could not generate a fail string for regex: " + regex);

    return convertToDesiredType(decode, prefix + fail);
  }

  private static Object convertToDesiredType(final String decode, final String value) {
    return decode == null || decode.isEmpty() ? value : JsdUtil.invoke(JsdUtil.parseExecutable(decode, String.class), value);
  }

  final String pattern;

  private StringTrial(final Case<? extends PropertyTrial<? super String>> kase, final Method getMethod, final Method setMethod, final Object object, final Object value, final StringProperty property) {
    super(kase, JsdUtil.getRealType(getMethod), getMethod, setMethod, object, value, property.name(), property.use(), property.decode(), property.encode(), true);
    this.pattern = property.pattern();
  }
}