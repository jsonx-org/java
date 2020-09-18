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
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;

import org.libj.math.BigDecimals;
import org.libj.math.BigIntegers;

public final class TestHelper {
  public static BigInteger stringToBigIntegerOrNull(final String str) {
    return str == null ? null : BigIntegers.intern(str);
  }

  public static BigDecimal stringToBigDecimalOrNull(final String str) {
    return str == null ? null : BigDecimals.intern(str);
  }

  public static URL stringToUrlOrNull(final String str) throws MalformedURLException {
    return str == null ? null : new URL(str);
  }

  private TestHelper() {
  }
}