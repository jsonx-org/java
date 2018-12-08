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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

import org.fastjax.net.URIComponent;

import com.mifmif.common.regex.Generex;

class StringTrial extends PropertyTrial<String> {
  private static final int stringLength = 12;
  private static final char[] ascii = new char[95];

  static {
    for (int i = 0; i < 95; ++i)
      ascii[i] = (char)(i + 32);
  }

  static void add(final List<PropertyTrial<?>> trials, final Field field, final Object object, final StringProperty property) {
    final String valid = createValid(property.pattern(), property.urlDecode(), property.urlEncode());

    trials.add(new StringTrial(ValidCase.CASE, field, object, valid, property));

    if (property.pattern().length() > 0)
      trials.add(new StringTrial(PatternCase.CASE, field, object, createInvalid(property.pattern(), property.urlEncode()), property));

    if (property.urlDecode() || property.urlEncode())
      trials.add(new StringTrial(UrlCodecCase.CASE, field, object, valid, property));

    if (property.use() == Use.REQUIRED) {
      if (property.nullable()) {
        trials.add(new StringTrial(RequiredNullableCase.CASE, field, object, null, property));
      }
      else {
        trials.add(new StringTrial(RequiredNotNullableCase.CASE, field, object, null, property));
      }
    }
    else {
      if (property.nullable()) {
        trials.add(new StringTrial(OptionalNullableCase.CASE, field, object, null, property));
        trials.add(new StringTrial(OptionalNullableCase.CASE, field, object, Optional.ofNullable(null), property));
      }
      else {
        trials.add(new StringTrial(OptionalNotNullableCase.CASE, field, object, null, property));
      }
    }
  }

  private static class StringGen {
    private final Generex generex;

    private StringGen(final String pattern) {
      if (pattern.length() == 0)
        this.generex = null;
      else if (!Generex.isValidPattern(pattern))
        throw new UnsupportedOperationException("Regex pattern \"" + pattern + "\" is not supported");
      else
        this.generex = new Generex(pattern);
    }

    String random() {
      return generex == null ? randomString(stringLength) : generex.random(stringLength);
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

  private static String urlDecode(final String s) {
    try {
      return URLDecoder.decode(s, "UTF-8");
    }
    catch (final Exception e) {
      return null;
    }
  }

  private static String urlEncode(final String s) {
    try {
      return URLEncoder.encode(s, "UTF-8");
    }
    catch (final Exception e) {
      return null;
    }
  }

  static String createValid(final String pattern, final boolean urlDecode, final boolean urlEncode) {
    final StringGen strGen = new StringGen(pattern);
    if (!urlDecode && !urlEncode)
      return strGen.random();

    String s = null;
    int i = 0;
    if (urlDecode && urlEncode)
      for (; urlDecode(s = strGen.random()) == null || urlEncode(s) == null && i < 1000; ++i);
    else if (urlDecode)
      for (; urlDecode(s = strGen.random()) == null && i < 1000; ++i);
    else if (urlEncode)
      for (; urlEncode(s = strGen.random()) == null && i < 1000; ++i);

    if (s == null || i == 1000)
      throw new RuntimeException("Could not generate a urlDecode = " + urlDecode + ", urlEncode = " + urlEncode + " success string for regex: " + pattern);

    if (pattern.length() > 0 && !s.matches(pattern))
      throw new RuntimeException("String " + s + " does not match regex: " + pattern);

    return s;
  }

  private static String createInvalid(final String regex, final boolean urlEncode) {
    String fail = null;
    for (int i = 0; i < 1000; ++i) {
      fail = randomString(stringLength);
      if (!(urlEncode ? URIComponent.encode(fail) : fail).matches(regex))
        break;
    }

    if (fail.matches(regex))
      throw new RuntimeException("Could not generate a fail string for regex: " + regex);

    return fail;
  }

  final String pattern;
  final boolean urlEncode;
  final boolean urlDecode;

  private StringTrial(final Case<? extends PropertyTrial<? super String>> kase, final Field field, final Object object, final Object value, final StringProperty property) {
    super(kase, field, object, value, property.name(), property.use());
    this.pattern = property.pattern();
    this.urlEncode = property.urlEncode();
    this.urlDecode = property.urlDecode();
  }
}