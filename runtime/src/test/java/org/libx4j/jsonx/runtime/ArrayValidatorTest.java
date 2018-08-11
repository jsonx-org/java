/* Copyright (c) 2018 lib4j
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

package org.libx4j.jsonx.runtime;

import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Test;
import org.lib4j.lang.Strings;
import org.lib4j.math.BigDecimals;

public class ArrayValidatorTest {
  private static boolean debug = false;

  private static void test(final String expected, final Class<? extends Annotation> annotationType, final Object ... members) {
    final String error = ArrayValidator.validate(annotationType, members);
    if (expected != null && !expected.equals(error)) {
      String msg = "\"" + Strings.escapeForJava(error) + "\"";
      msg = msg.replace('$', '.');
      msg = msg.replace(".class", "%class");
      msg = msg.replaceAll("org\\.libx4j\\.jsonx\\.[\\.a-zA-Z]+\\.([a-zA-Z0-9]+)", "\" + $1.class.getName() + \"");
      msg = msg.replace("%class", ".class");
      System.err.println(msg);
    }

    if (debug)
      assertEquals(expected == null, error == null);
    else
      assertEquals(expected, error);
  }

  @NumberElement(id=0, maxOccurs=1, range="xxxx", nullable=true)
  @ArrayType(elementIds={0})
  private static @interface ArrayError1 {
  }

  @BooleanElement(id=0, maxOccurs=-1)
  @ArrayType(elementIds={0})
  private static @interface ArrayError2 {
  }

  @BooleanElement(id=0, minOccurs=1, maxOccurs=0)
  @ArrayType(elementIds={0})
  private static @interface ArrayError3 {
  }

  @BooleanElement(id=0, maxOccurs=-1)
  @ArrayType(elementIds={-1})
  private static @interface ArrayError4 {
  }

  @BooleanElement(id=0, maxOccurs=-1)
  @ArrayType(elementIds={})
  private static @interface ArrayError5 {
  }

  @BooleanElement(id=0)
  @BooleanElement(id=0)
  @ArrayType(elementIds={0})
  private static @interface ArrayError6 {
  }

  @Test
  public void testArrayError() {
    try {
      test(null, ArrayError1.class, BigDecimals.PI);
      fail("Expected IllegalStateException");
    }
    catch (final IllegalStateException e) {
      assertEquals("range is invalid: @" + NumberElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=1, form=REAL, nullable=true, range=\"xxxx\")", e.getMessage());
    }

    try {
      test(null, ArrayError2.class, null, true);
      fail("Expected IllegalStateException");
    }
    catch (final IllegalStateException e) {
      assertEquals("maxOccurs must be a non-negative integer: @" + BooleanElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=-1, nullable=false)", e.getMessage());
    }

    try {
      test(null, ArrayError3.class, null, true);
      fail("Expected IllegalStateException");
    }
    catch (final IllegalStateException e) {
      assertEquals("minOccurs must be less than or equal to maxOccurs: @" + BooleanElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=0, nullable=false)", e.getMessage());
    }

    try {
      test(null, ArrayError4.class, null, true);
      fail("Expected IllegalStateException");
    }
    catch (final IllegalStateException e) {
      assertEquals("@?Element(id=-1) not found in annotations array", e.getMessage());
    }

    try {
      test(null, ArrayError5.class, null, true);
      fail("Expected IllegalStateException");
    }
    catch (final IllegalStateException e) {
      assertEquals("elementIds property cannot be empty: " + ArrayError5.class.getName() + ": @" + ArrayType.class.getName() + "(elementIds={})", e.getMessage());
    }

    try {
      test(null, ArrayError6.class, null, true);
      fail("Expected IllegalStateException");
    }
    catch (final IllegalStateException e) {
      assertTrue(e.getMessage().startsWith("Duplicate id=0 found in"));
    }
  }

  @BooleanElement(id=0, maxOccurs=3, nullable=true)
  @ArrayType(elementIds={0})
  private static @interface Array1d1 {
  }

  @Test
  public void testArray1d1() {
    test(null, Array1d1.class, (Object)null);
    test(null, Array1d1.class, true);
    test(null, Array1d1.class, true, null);
    test(null, Array1d1.class, true, null, true);
    test("Invalid content was found starting with member index=3: @" + BooleanElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=3, nullable=true): No members are expected at this point: null", Array1d1.class, true, null, true, null);
    test("Invalid content was found starting with member index=3: @" + BooleanElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=3, nullable=true): No members are expected at this point: true", Array1d1.class, true, null, true, true, null);
  }

  @NumberElement(id=2, minOccurs=0, maxOccurs=2, form=Form.INTEGER, range="[0,4]")
  @StringElement(id=1, maxOccurs=2, pattern="[a-z]+")
  @BooleanElement(id=0, maxOccurs=3, nullable=true)
  @ArrayType(elementIds={0, 1, 2})
  private static @interface Array1d2 {
  }

  @Test
  public void testArray1d3() {
    test(null, Array1d2.class, null, "string");
    test(null, Array1d2.class, true, "string", BigInteger.ONE);
    test(null, Array1d2.class, true, "string", BigDecimals.TWO);
    test("Invalid content was found starting with member index=2: @" + StringElement.class.getName() + "(id=1, minOccurs=1, maxOccurs=2, urlEncode=false, urlDecode=false, nullable=false, pattern=\"[a-z]+\"): Illegal value: null", Array1d2.class, true, "string", null);
    test("Invalid content was found starting with member index=3: @" + NumberElement.class.getName() + "(id=2, minOccurs=0, maxOccurs=2, form=INTEGER, nullable=false, range=\"[0,4]\"): Range is not matched: 10", Array1d2.class, true, "string", BigInteger.ZERO, BigDecimal.TEN);
    test("Invalid content was found starting with member index=2: @" + NumberElement.class.getName() + "(id=2, minOccurs=0, maxOccurs=2, form=INTEGER, nullable=false, range=\"[0,4]\"): Illegal non-INTEGER value: 3.14159265358...", Array1d2.class, true, "string", BigDecimals.PI, BigDecimal.TEN, null);
    test("Invalid content was found starting with member index=1: @" + StringElement.class.getName() + "(id=1, minOccurs=1, maxOccurs=2, urlEncode=false, urlDecode=false, nullable=false, pattern=\"[a-z]+\"): Pattern is not matched: \"111\"", Array1d2.class, null, "111", null);
    test("Invalid content was found starting with member index=3: @" + StringElement.class.getName() + "(id=1, minOccurs=1, maxOccurs=2, urlEncode=false, urlDecode=false, nullable=false, pattern=\"[a-z]+\"): Illegal value: null", Array1d2.class, null, null, null, null, null);
    test("Invalid content was found starting with member index=0: @" + BooleanElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=3, nullable=true): Content is not complete", Array1d2.class, "string");
    test("Invalid content was found starting with member index=1: @" + StringElement.class.getName() + "(id=1, minOccurs=1, maxOccurs=2, urlEncode=false, urlDecode=false, nullable=false, pattern=\"[a-z]+\"): Content is not complete", Array1d2.class, true);
    test("Invalid content was found starting with member index=2: @" + StringElement.class.getName() + "(id=1, minOccurs=1, maxOccurs=2, urlEncode=false, urlDecode=false, nullable=false, pattern=\"[a-z]+\"): Pattern is not matched: \"111\"", Array1d2.class, null, "string", "111", "string");
  }

  @ArrayElement(id=0, type=Array1d2.class)
  @ArrayType(elementIds={0})
  private static @interface Array2d1 {
  }

  @Test
  public void testArray2d1() {
    test("Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=2147483647, elementIds={}, nullable=false, type=" + Array1d2.class.getName() + ".class): Invalid content was found starting with member index=0: @" + BooleanElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=3, nullable=true): Content is not complete", Array2d1.class, Arrays.asList());

    test(null, Array2d1.class, Arrays.asList(null, "string"));
    test(null, Array2d1.class, Arrays.asList(true, "string", BigInteger.ONE));
    test(null, Array2d1.class, Arrays.asList(true, "string", BigDecimals.TWO));
    test("Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=2147483647, elementIds={}, nullable=false, type=" + Array1d2.class.getName() + ".class): Invalid content was found starting with member index=2: @" + StringElement.class.getName() + "(id=1, minOccurs=1, maxOccurs=2, urlEncode=false, urlDecode=false, nullable=false, pattern=\"[a-z]+\"): Illegal value: null", Array2d1.class, Arrays.asList(true, "string", null));
    test("Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=2147483647, elementIds={}, nullable=false, type=" + Array1d2.class.getName() + ".class): Invalid content was found starting with member index=3: @" + NumberElement.class.getName() + "(id=2, minOccurs=0, maxOccurs=2, form=INTEGER, nullable=false, range=\"[0,4]\"): Range is not matched: 10", Array2d1.class, Arrays.asList(true, "string", BigInteger.ZERO, BigDecimal.TEN));
    test("Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=2147483647, elementIds={}, nullable=false, type=" + Array1d2.class.getName() + ".class): Invalid content was found starting with member index=2: @" + NumberElement.class.getName() + "(id=2, minOccurs=0, maxOccurs=2, form=INTEGER, nullable=false, range=\"[0,4]\"): Illegal non-INTEGER value: 3.14159265358...", Array2d1.class, Arrays.asList(true, "string", BigDecimals.PI, BigDecimal.TEN, null));
    test("Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=2147483647, elementIds={}, nullable=false, type=" + Array1d2.class.getName() + ".class): Invalid content was found starting with member index=1: @" + StringElement.class.getName() + "(id=1, minOccurs=1, maxOccurs=2, urlEncode=false, urlDecode=false, nullable=false, pattern=\"[a-z]+\"): Pattern is not matched: \"111\"", Array2d1.class, Arrays.asList(null, "111", null));
    test("Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=2147483647, elementIds={}, nullable=false, type=" + Array1d2.class.getName() + ".class): Invalid content was found starting with member index=3: @" + StringElement.class.getName() + "(id=1, minOccurs=1, maxOccurs=2, urlEncode=false, urlDecode=false, nullable=false, pattern=\"[a-z]+\"): Illegal value: null", Array2d1.class, Arrays.asList(null, null, null, null, null));
    test("Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=2147483647, elementIds={}, nullable=false, type=" + Array1d2.class.getName() + ".class): Invalid content was found starting with member index=0: @" + BooleanElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=3, nullable=true): Content is not complete", Array2d1.class, Arrays.asList("string"));
    test("Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=2147483647, elementIds={}, nullable=false, type=" + Array1d2.class.getName() + ".class): Invalid content was found starting with member index=1: @" + StringElement.class.getName() + "(id=1, minOccurs=1, maxOccurs=2, urlEncode=false, urlDecode=false, nullable=false, pattern=\"[a-z]+\"): Content is not complete", Array2d1.class, Arrays.asList(true));
    test("Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=2147483647, elementIds={}, nullable=false, type=" + Array1d2.class.getName() + ".class): Invalid content was found starting with member index=2: @" + StringElement.class.getName() + "(id=1, minOccurs=1, maxOccurs=2, urlEncode=false, urlDecode=false, nullable=false, pattern=\"[a-z]+\"): Pattern is not matched: \"111\"", Array2d1.class, Arrays.asList(null, "string", "111", "string"));
    test("Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=2147483647, elementIds={}, nullable=false, type=" + Array1d2.class.getName() + ".class): Invalid content was found starting with member index=1: @" + StringElement.class.getName() + "(id=1, minOccurs=1, maxOccurs=2, urlEncode=false, urlDecode=false, nullable=false, pattern=\"[a-z]+\"): Pattern is not matched: \"111\"", Array2d1.class, Arrays.asList(null, "111", null));
    test("Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=2147483647, elementIds={}, nullable=false, type=" + Array1d2.class.getName() + ".class): Invalid content was found starting with member index=3: @" + StringElement.class.getName() + "(id=1, minOccurs=1, maxOccurs=2, urlEncode=false, urlDecode=false, nullable=false, pattern=\"[a-z]+\"): Illegal value: null", Array2d1.class, Arrays.asList(null, null, null, null, null));
    test("Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=2147483647, elementIds={}, nullable=false, type=" + Array1d2.class.getName() + ".class): Invalid content was found starting with member index=0: @" + BooleanElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=3, nullable=true): Content is not complete", Array2d1.class, Arrays.asList("string"));
    test("Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=2147483647, elementIds={}, nullable=false, type=" + Array1d2.class.getName() + ".class): Invalid content was found starting with member index=1: @" + StringElement.class.getName() + "(id=1, minOccurs=1, maxOccurs=2, urlEncode=false, urlDecode=false, nullable=false, pattern=\"[a-z]+\"): Pattern is not matched: \"111\"", Array2d1.class, Arrays.asList(null, "111", "string", "string"));
  }

  @NumberElement(id=6, form=Form.INTEGER, minOccurs=0, maxOccurs=1)
  @NumberElement(id=5, form=Form.INTEGER, minOccurs=0, maxOccurs=1)
  @StringElement(id=4, pattern="[a-z]+", minOccurs=0)
  @NumberElement(id=3, form=Form.INTEGER, minOccurs=0, maxOccurs=1)
  @ArrayElement(id=2, elementIds={3, 4})
  @BooleanElement(id=1, minOccurs=0)
  @ArrayElement(id=0, type=Array1d2.class, minOccurs=0)
  @ArrayType(elementIds={0, 1, 2, 5, 6})
  private static @interface Array2d2 {
  }

  @Test
  public void testArray2d2() {
    test(null, Array2d2.class, Arrays.asList());
    test(null, Array2d2.class, true, Arrays.asList());
    test(null, Array2d2.class, true, Arrays.asList(), BigInteger.ZERO);
    test(null, Array2d2.class, true, Arrays.asList(), BigInteger.ZERO, BigInteger.ONE);
    test("Invalid content was found starting with member index=4: @" + NumberElement.class.getName() + "(id=6, minOccurs=0, maxOccurs=1, form=INTEGER, nullable=false, range=\"\"): No members are expected at this point: 2", Array2d2.class, true, Arrays.asList(), BigInteger.ZERO, BigInteger.ONE, BigInteger.TWO);
    test("Invalid content was found starting with member index=2: @" + NumberElement.class.getName() + "(id=6, minOccurs=0, maxOccurs=1, form=INTEGER, nullable=false, range=\"\"): No members are expected at this point: string", Array2d2.class, true, Arrays.asList(), "string", BigInteger.ZERO, BigInteger.ONE);
    test(null, Array2d2.class, Arrays.asList(null, "string"), Arrays.asList());
    test(null, Array2d2.class, Arrays.asList(null, "string"), false, Arrays.asList());
    test(null, Array2d2.class, Arrays.asList(null, "string"), false, Arrays.asList(BigInteger.ZERO, "string"));
    test("Invalid content was found starting with member index=2: @" + ArrayElement.class.getName() + "(id=2, minOccurs=1, maxOccurs=2147483647, elementIds={3, 4}, nullable=false, type=" + ArrayType.class.getName() + ".class): Invalid content was found starting with member index=1: @" + StringElement.class.getName() + "(id=4, minOccurs=0, maxOccurs=2147483647, urlEncode=false, urlDecode=false, nullable=false, pattern=\"[a-z]+\"): Pattern is not matched: \"123\"", Array2d2.class, Arrays.asList(null, "string"), false, Arrays.asList(BigInteger.ZERO, "123"));
    test("Invalid content was found starting with member index=2: @" + ArrayElement.class.getName() + "(id=2, minOccurs=1, maxOccurs=2147483647, elementIds={3, 4}, nullable=false, type=" + ArrayType.class.getName() + ".class): Invalid content was found starting with member index=0: @" + NumberElement.class.getName() + "(id=3, minOccurs=0, maxOccurs=1, form=INTEGER, nullable=false, range=\"\"): Illegal non-INTEGER value: 3.14159265358...", Array2d2.class, Arrays.asList(null, "string"), false, Arrays.asList(BigDecimals.PI, "string"));
    test(null, Array2d2.class, Arrays.asList(true, "string", BigDecimals.TWO), false, Arrays.asList(BigInteger.ZERO, "string"));
    test("Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, minOccurs=0, maxOccurs=2147483647, elementIds={}, nullable=false, type=" + Array1d2.class.getName() + ".class): Invalid content was found starting with member index=2: @" + NumberElement.class.getName() + "(id=2, minOccurs=0, maxOccurs=2, form=INTEGER, nullable=false, range=\"[0,4]\"): Illegal non-INTEGER value: 3.14159265358...", Array2d2.class, Arrays.asList(true, "string", BigDecimals.PI), false, Arrays.asList(BigInteger.ZERO, "string"));
  }

  @ArrayElement(id=1, type=Array2d2.class)
  @ArrayElement(id=0, type=Array1d2.class, minOccurs=0)
  @ArrayType(elementIds={0, 1})
  private static @interface Array3d {
  }

  @Test
  public void testArray3d() {
    test(null, Array3d.class, Arrays.asList(Arrays.asList()));
    test("Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, minOccurs=0, maxOccurs=2147483647, elementIds={}, nullable=false, type=" + Array1d2.class.getName() + ".class): Invalid content was found starting with member index=0: @" + BooleanElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=3, nullable=true): Content is not complete", Array3d.class, Arrays.asList());
    test(null, Array3d.class, Arrays.asList(Arrays.asList(null, "string"), false, Arrays.asList(BigInteger.ZERO, "string")));
    test(null, Array3d.class, Arrays.asList(true, "string", BigDecimals.TWO), Arrays.asList(Arrays.asList(null, "string"), false, Arrays.asList(BigInteger.ZERO, "string")));
    test("Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, minOccurs=0, maxOccurs=2147483647, elementIds={}, nullable=false, type=" + Array1d2.class.getName() + ".class): Invalid content was found starting with member index=2: @" + NumberElement.class.getName() + "(id=2, minOccurs=0, maxOccurs=2, form=INTEGER, nullable=false, range=\"[0,4]\"): Illegal non-INTEGER value: 3.14159265358...", Array3d.class, Arrays.asList(true, "string", BigDecimals.PI), Arrays.asList(Arrays.asList(null, "string"), false, Arrays.asList(BigInteger.ZERO, "string")));
  }
}