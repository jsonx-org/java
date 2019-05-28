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
import java.util.List;
import java.util.Optional;

import org.libj.util.Characters;

import com.mifmif.common.regex.Generex;

class StringTrial extends PropertyTrial<String> {
  private static final int stringLength = 12;
  private static final char[] ascii = new char[95];

  static {
    for (int i = 0; i < ascii.length; ++i)
      ascii[i] = (char)(i + 32);
  }

  private static String filterPrintable(final String string) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < string.length(); ++i) {
      final char ch = string.charAt(i);
      if (ch < 32532 && ch != '"' && ch != '\\' && ch != '\n' && ch != '\r' && Characters.isPrintable(ch))
        builder.append(ch);
    }

    return builder.toString();
  }

  private static class StringGen {
    private final String pattern;
    private final Generex generex;

    private StringGen(String pattern) {
      if (pattern.length() == 0) {
        this.generex = null;
      }
      else {
        if (pattern.startsWith("^"))
          pattern = pattern.substring(1);

        if (pattern.endsWith("$"))
          pattern = pattern.substring(0, pattern.length() - 1);

        if (!Generex.isValidPattern(pattern))
          throw new UnsupportedOperationException("Regex pattern \"" + pattern + "\" is not supported");

        this.generex = new Generex(pattern);
      }

      this.pattern = pattern;
    }

    String random() {
      if (generex == null)
        return randomString(stringLength);

      for (int i = 0;; ++i) {
        if (i == 1000)
          throw new IllegalArgumentException("Cannot create string for pattern: " + pattern);

        final String string = filterPrintable(generex.random(stringLength));
        if (string.matches(pattern))
          return string;
      }
    }
  }

  static void add(final List<PropertyTrial<?>> trials, final Field field, final Object object, final StringProperty property) {
    logger.debug("Adding: " + field.getDeclaringClass() + "#" + field.getName());
    trials.add(new StringTrial(ValidCase.CASE, field, object, createValid(property.pattern()), property));
    if (property.pattern().length() > 0)
      trials.add(new StringTrial(PatternCase.CASE, field, object, createInvalid(property.pattern()), property));

    if (property.use() == Use.REQUIRED) {
      trials.add(new StringTrial(getNullableCase(property.nullable()), field, object, null, property));
    }
    else if (property.nullable()) {
      trials.add(new StringTrial(OptionalNullableCase.CASE, field, object, null, property));
      trials.add(new StringTrial(OptionalNullableCase.CASE, field, object, Optional.ofNullable(null), property));
    }
    else {
      trials.add(new StringTrial(OptionalNotNullableCase.CASE, field, object, null, property));
    }
  }

  private static String randomString(final int len) {
    final char[] chars = new char[len];
    int j = 0;
    for (int i = 0; i < len;) {
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

  static String createValid(final String pattern) {
    return new StringGen(pattern).random();
  }

  private static String createInvalid(final String regex) {
    String fail = null;
    for (int i = 0; i < 1000; ++i) {
      fail = randomString(stringLength);
      if (!fail.matches(regex))
        break;
    }

    if (fail.matches(regex))
      throw new IllegalStateException("Could not generate a fail string for regex: " + regex);

    return fail;
  }

  final String pattern;

  private StringTrial(final Case<? extends PropertyTrial<? super String>> kase, final Field field, final Object object, final Object value, final StringProperty property) {
    super(kase, field, object, value, property.name(), property.use());
    this.pattern = property.pattern();
  }
}