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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.libj.lang.ParseException;
import org.openjax.json.JsonParseException;

public class RangeTest {
  static void assertEquals(final String message, final Object expected, final Object actual) {
    Assert.assertEquals(message, expected, actual);
    if (expected != null)
      Assert.assertEquals(expected.hashCode(), actual.hashCode());
  }

  static void assertEquals(final Object expected, final Object actual) {
    assertEquals(null, expected, actual);
  }

  @Test
  public void test() throws ParseException {
    try {
      Range.from(null, null);
      fail("Expected NullPointerException");
    }
    catch (final NullPointerException e) {
    }

    try {
      Range.from("", null);
      fail("Expected IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
      assertEquals("Range min length is 4, but was 0", e.getMessage());
    }

    try {
      Range.from("4323", null);
      fail("Expected ParseException");
    }
    catch (final ParseException e) {
      assertEquals("Missing '[' or '(' in string: \"4323\" [errorOffset: 0]", e.getMessage());
    }

    try {
      Range.from("[4323", null);
      fail("Expected ParseException");
    }
    catch (final ParseException e) {
      assertEquals("Missing ',' in string: \"[4323\" [errorOffset: 5]", e.getMessage());
    }

    try {
      Range.from("[4323]", null);
      fail("Expected ParseException");
    }
    catch (final ParseException e) {
      assertEquals("Missing ',' in string: \"[4323]\" [errorOffset: 6]", e.getMessage());
    }

    try {
      Range.from("[,,4323]", null);
      fail("Expected ParseException");
    }
    catch (final ParseException e) {
      assertEquals("Illegal ',' in string: \"[,,4323]\" [errorOffset: 2]", e.getMessage());
    }

    try {
      Range.from("[10,1]", null);
      fail("Expected IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
      assertEquals("min=\"10\" > max=\"1\"", e.getMessage());
    }

    try {
      Range.from("(10,10]", null);
      fail("Expected IllegalArgumentException");
    }
    catch (final IllegalArgumentException e) {
      assertEquals("(10,10] defines an empty range", e.getMessage());
    }

    assertEquals(new Range(BigDecimal.ONE, true, BigDecimal.TEN, true, byte.class), Range.from("[1,10]", byte.class));
    assertEquals(new Range(BigDecimal.ONE, false, BigDecimal.TEN, true, short.class), Range.from("(1,10]", short.class));
    assertEquals(new Range(BigDecimal.ONE, true, BigDecimal.TEN, false, int.class), Range.from("[1,10)", int.class));
    assertEquals(new Range(BigDecimal.ONE, false, BigDecimal.TEN, false, BigInteger.class), Range.from("(1,10)", BigInteger.class));
    assertEquals(new Range(BigDecimal.TEN, true, BigDecimal.TEN, true, byte.class), Range.from("[10,10]", byte.class));

    assertEquals(new Range(null, true, BigDecimal.TEN, true, short.class), Range.from("[,10]", short.class));
    assertEquals(new Range(BigDecimal.TEN, true, null, true, int.class), Range.from("[10,]", int.class));

    final Range range = Range.from("[10,10]", byte.class);
    assertEquals(BigDecimal.valueOf(10), range.getMin());
    assertTrue(range.isMinInclusive());
    assertEquals(BigDecimal.valueOf(10), range.getMax());
    assertTrue(range.isMaxInclusive());
    assertEquals(range, range);
    assertNotEquals("", range);
  }

  private static void assertPass(final Class<? extends Number> type, final String ... values) throws ParseException {
    for (int i = 0, i$ = values.length; i < i$; ++i) // [A]
      Range.from(values[i], type);
  }

  @SafeVarargs
  private static void assertFail(final String[] values, final Class<? extends Number> type, final Class<? extends Exception> ... exceptions) throws ParseException {
    for (int i = 0, i$ = values.length; i < i$; ++i) { // [A]
      try {
        Range.from(values[i], type);
        fail("Expected " + Arrays.toString(exceptions) + ": " + values[i]);
      }
      catch (final Exception e) {
        for (final Class<? extends Exception> cls : exceptions) // [A]
          if (cls.isInstance(e))
            return;

        throw e;
      }
    }
  }

  private static void assertFailArgument(final String ... values) throws ParseException {
    assertFail(values, BigDecimal.class, IllegalArgumentException.class);
  }

  private static void assertFailParse(final String ... values) throws ParseException {
    assertFail(values, BigDecimal.class, ParseException.class, JsonParseException.class);
  }

  private static void assertFailType(final Class<? extends Number> type, final String ... values) throws ParseException {
    assertFail(values, type, IllegalArgumentException.class);
  }

  @Test
  public void testPass() throws ParseException {
    assertPass(byte.class, "[-0,1]", "(-2,-1)", "[0,1)", "(0,1]");
    assertPass(float.class, "[0.1,1.1]", "(0.1,1.1)", "[-2.1,-1.1)", "(0.1,1.1]");
    assertPass(double.class, "[0,1.1]", "(0,1.1)", "[0,1.1)", "(-2,-1.1]");
    assertPass(byte.class, "[0.1,1]", "(0.1,1)", "[0.1,1)", "(0.1,1]");

    assertPass(short.class, "[,-1]", "(,-1)", "[,1)", "(,1]");
    assertPass(int.class, "[,1.1]", "(,1.1)", "[,1.1)", "(,1.1]");

    assertPass(long.class, "[-0,]", "(-0,)", "[0,)", "(0,]");
    assertPass(BigInteger.class, "[-0.1,]", "(0.1,)", "[0.1,)", "(-0.1,]");

    assertPass(float.class, "[-0E1,1e2]", "(-1E3,-1e2)", "[0e1,1E2)", "(0e1,1E2]");
    assertPass(BigDecimal.class, "[0.1E-3,1.1]", "(0.1E-3,1.1)", "[-2.1e-3,-0.00001)", "(0.1e-3,1.1]");
    assertPass(BigInteger.class, "[0,1.1]", "(0,1.1)", "[0,1.1)", "(-2,-1.1]");
    assertPass(double.class, "[0.1,1]", "(0.1,1)", "[0.1,1)", "(0.1,1]");

    assertPass(float.class, "[,-1]", "(,-1)", "[,1)", "(,1]");
    assertPass(double.class, "[,1.1]", "(,1.1)", "[,1.1)", "(,1.1]");

    assertPass(short.class, "[-0,]", "(-0,)", "[0,)", "(0,]");
    assertPass(long.class, "[-0.1,]", "(0.1,)", "[0.1,)", "(-0.1,]");
  }

  @Test
  public void testFailArgument() throws ParseException {
    assertFailArgument("[", "]", "[,", ",]", "[1,", ",1]");
    assertFailArgument("(", "]", "(,", ",]", "(1,", ",1]");
    assertFailArgument("[", ")", "[,", ",)", "[1,", ",1)");
    assertFailArgument("[", ")", "[,", ",)", "[1,", ",1)");
    assertFailArgument("[0E0,0)", "(0E0,0]");
  }

  @Test
  public void testFailParse() throws ParseException {
    assertFailParse("[00,0]", "[0,00]", "[-.1,0]", "[-1.,0]", "[-0.1,-]", "[-,1]");
    assertFailParse("(00,0]", "(0,00]", "(-.1,0]", "(-1.,0]", "(-0.1,-]", "(-,1]");
    assertFailParse("[00,0)", "[0,00)", "[-.1,0)", "[1.,0)", "[-0.1,-)", "[-,1)");
    assertFailParse("[0,00)", "[-.1,0)", "[1.,0)", "[-0.1,-)", "[-,1)");
  }

  @Test
  public void testPassType() throws ParseException {
    assertPass(byte.class, "[-0,127]", "(-128,)", "[,127)", "(-128,1]");
    assertPass(short.class, "[-0,32767]", "(-32768,)", "[,32767)", "(-32768,1]");
    assertPass(int.class, "[-0,2147483647]", "(-2147483648,)", "[,2147483647)", "(-2147483648,1]");
    assertPass(long.class, "[-0,9223372036854775807]", "(-9223372036854775808,)", "[,9223372036854775807)", "(-9223372036854775808,1]");
  }

  @Test
  public void testFailType() throws ParseException {
    assertFailType(byte.class, "[-0,128]", "(-129,)", "[,128)", "(-129,1]");
    assertFailType(short.class, "[-0,32768]", "(-32769,)", "[,32768)", "(-32769,1]");
    assertFailType(int.class, "[-0,2147483648]", "(-2147483649,)", "[,2147483648)", "(-2147483649,1]");
    assertFailType(long.class, "[-0,9223372036854775808]", "(-9223372036854775809,)", "[,9223372036854775808)", "(-9223372036854775809,1]");
  }
}