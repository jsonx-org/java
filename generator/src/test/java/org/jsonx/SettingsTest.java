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

import static org.junit.Assert.*;

import org.junit.Test;

@SuppressWarnings("unused")
public class SettingsTest {
  @Test
  public void test() {
    try {
      new Settings(null, -1, true, long.class, Long.class, double.class, Double.class);
      fail("Expected IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      new Settings(null, 1, true, Long.class, Long.class, double.class, Double.class);
      fail("Expected IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      new Settings(null, 1, true, long.class, long.class, double.class, Double.class);
      fail("Expected IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      new Settings(null, 1, true, long.class, Long.class, Double.class, Double.class);
      fail("Expected IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }

    try {
      new Settings(null, 1, true, long.class, Long.class, double.class, double.class);
      fail("Expected IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
    }
  }
}