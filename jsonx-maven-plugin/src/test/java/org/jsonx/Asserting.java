/* Copyright (c) 2019 JSONx
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

import org.junit.Assert;

class Asserting {
  static void assertEquals(final String message, final Object expected, final Object actual) {
    final boolean equals = expected == null ? actual == null : expected.equals(actual);
    if (!equals)
      Assert.assertEquals(message, expected, actual);
  }

  static void assertEquals(final Object expected, final Object actual) {
    assertEquals(null, expected, actual);
  }

  static void assertNotEquals(final String message, final Object expected, final Object actual) {
    final boolean equals = expected == null ? actual == null : expected.equals(actual);
    if (equals)
      Assert.assertNotEquals(message, expected, actual);
  }

  static void assertNotEquals(final Object expected, final Object actual) {
    assertNotEquals(null, expected, actual);
  }

  static void assertNull(final String message, final Object object) {
    if (object != null)
      Assert.assertNull(message, object);
  }

  static void assertNull(final Object actual) {
    assertNull(null, actual);
  }

  static void assertNotNull(final String message, final Object object) {
    if (object == null)
      Assert.assertNotNull(message, object);
  }

  static void assertNotNull(final Object object) {
    assertNotNull(null, object);
  }

  static void assertTrue(final String message, final boolean condition) {
    if (!condition)
      Assert.assertTrue(message, condition);
  }

  static void assertTrue(final boolean condition) {
    assertTrue(null, condition);
  }

  static void assertFalse(final String message, final boolean condition) {
    if (condition)
      Assert.assertFalse(message, condition);
  }

  static void assertFalse(final boolean condition) {
    assertFalse(null, condition);
  }
}