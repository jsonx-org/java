/* Copyright (c) 2020 JSONx
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

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;

import org.libj.lang.BigDecimals;

public final class TestHelper {
  public static Long stringToLongOrNull(final String str) {
    return str == null ? null : Long.valueOf(str);
  }

  public static BigDecimal stringToBigDecimalOrNull(final String str) {
    return str == null ? null : BigDecimals.intern(str);
  }

  public static URL stringToUrlOrNull(final String str) throws MalformedURLException {
    return str == null ? null : new URL(str);
  }

  public static Boolean toNullBoolean(final Object obj) {
    return null;
  }

  public static Number toNullNumber(final Object obj) {
    return null;
  }

  public static String toNullString(final Object obj) {
    return null;
  }

  private TestHelper() {
  }
}