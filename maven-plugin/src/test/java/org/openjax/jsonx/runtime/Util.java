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

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;

import org.fastjax.net.URIComponent;

import com.mifmif.common.regex.Generex;

public final class Util {
  public static String palindrome(final String s) {
    if (s.length() < 2)
      throw new IllegalArgumentException("s.length() must be greater than 2: " + s.length());

    final StringBuilder builder = new StringBuilder(s);
    for (int i = 0; i < s.length();) {
      final int i1 = (int)(Math.random() * s.length());
      final int i2 = (int)(Math.random() * s.length());
      if (i1 != i2) {
        final char c1 = builder.charAt(i1);
        final char c2 = builder.charAt(i2);
        builder.setCharAt(i1, c2);
        builder.setCharAt(i2, c1);
        ++i;
      }
    }

    return builder.toString();
  }

  private static final char[] ascii = new char[95];

  static {
    for (int i = 0; i < 95; ++i)
      ascii[i] = (char)(i + 32);
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

  private static class StringGen {
    private final Generex generex;

    private StringGen(final String pattern) {
      if (pattern == null)
        this.generex = null;
      else if (!Generex.isValidPattern(pattern))
        throw new UnsupportedOperationException("Regex pattern \"" + pattern + "\" is not supported");
      else
        this.generex = new Generex(pattern);
    }

    String random() {
      return generex == null ? randomString(12) : generex.random(12);
    }
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

  public static String createPassString(final String pattern, final boolean urlDecode, final boolean urlEncode) {
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

    if (pattern != null && !s.matches(pattern))
      throw new RuntimeException("String " + s + " does not match regex: " + pattern);

    return s;
  }

  public static String createFailString(final String regex, final boolean urlEncode, final String pass) {
    String fail = null;
    for (int i = 0; i < 1000; ++i) {
      fail = randomString(pass.length());
      if (!(urlEncode ? URIComponent.encode(fail) : fail).matches(regex))
        break;
    }

    if (fail.matches(regex))
      throw new RuntimeException("Could not generate a fail string for regex: " + regex);

    return fail;
  }

  public static Object arrayify(final Object value, final int minOccurs, final int maxOccurs) {
    if (minOccurs == 0 && maxOccurs == 0)
      return value;

    final int s = (int)((maxOccurs - minOccurs) * Math.random()) + 1;
    final Object[] a = new Object[s];
    Arrays.fill(a, value);
    return a;
  }

  private Util() {
  }
}