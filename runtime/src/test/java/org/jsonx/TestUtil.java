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
import java.util.Arrays;
import java.util.List;

import org.libj.lang.Strings;

final class TestUtil {
  static Method getMethod(final Class<?> cls, final String propertyName) throws NoSuchMethodException {
    return cls.getMethod("get" + Strings.flipFirstCap(propertyName));
  }

  @SafeVarargs
  static <T> List<T> l(final T ... members) {
    return Arrays.asList(members);
  }

  @SafeVarargs
  static <T> T[] a(final T ... members) {
    return members;
  }

  private TestUtil() {
  }
}