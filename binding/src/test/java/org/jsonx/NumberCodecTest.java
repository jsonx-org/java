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
import org.libj.lang.Numbers;

public class NumberCodecTest {
  @Test
  public void testPerformance() {
    long t0 = 0;
    long t1 = 0;
    for (int i = 0; i < 1000000; ++i) {
      final double d = Math.random();
      long t = System.nanoTime();
      final String expected = NumberCodec.decimalFormatLocal.get().format(d);
      t0 += System.nanoTime() - t;
      t = System.nanoTime();
      final String actual = Numbers.stripTrailingZeros(String.format("%.23f", d));
      t1 += System.nanoTime() - t;
      assertEquals(expected, actual);
    }

    System.err.println("DecimalFormat.format(d): " + t0 + "\n     printf(\"%.23f\", d): " + t1);
  }
}