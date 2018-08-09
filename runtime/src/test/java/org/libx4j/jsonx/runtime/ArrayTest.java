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
import java.util.List;

import org.junit.Test;
import org.lib4j.math.BigDecimals;

public class ArrayTest {
  private static void test(final String expected, final Class<? extends Annotation> annotationType, final Object ... members) {
    final List<String> errors = ArrayUtil.validate(annotationType, members);
    assertEquals(String.valueOf(expected), String.valueOf(errors));
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

  @BooleanElement(id=0, maxOccurs=3, nullable=true)
  @ArrayType(elementIds={0})
  private static @interface Array1d1 {
  }

  @StringElement(id=1, maxOccurs=2, pattern="[a-z]+")
  @BooleanElement(id=0, maxOccurs=3, nullable=true)
  @ArrayType(elementIds={0, 1})
  private static @interface Array1d2 {
  }

  @NumberElement(id=2, minOccurs=0, maxOccurs=2, form=Form.INTEGER, range="[0,4]")
  @StringElement(id=1, maxOccurs=2, pattern="[a-z]+")
  @BooleanElement(id=0, maxOccurs=3, nullable=true)
  @ArrayType(elementIds={0, 1, 2})
  private static @interface Array1d3 {
  }

  @Test
  public void testArrayError() {
    try {
      test(null, ArrayError1.class, BigDecimals.PI);
      fail("Expected IllegalStateException");
    }
    catch (final IllegalStateException e) {
      assertEquals("@NumberElement(id=0, range=\"xxxx\") range is invalid", e.getMessage());
    }

    try {
      test(null, ArrayError2.class, null, true);
      fail("Expected IllegalStateException");
    }
    catch (final IllegalStateException e) {
      assertEquals("@BooleanElement(id=0, maxOccurs=-1) maxOccurs must be a non-negative integer", e.getMessage());
    }

    try {
      test(null, ArrayError3.class, null, true);
      fail("Expected IllegalStateException");
    }
    catch (final IllegalStateException e) {
      assertEquals("@BooleanElement(id=0, minOccurs=1, maxOccurs=0) minOccurs must be less than or equal to maxOccurs", e.getMessage());
    }

    try {
      test(null, ArrayError4.class, null, true);
      fail("Expected IllegalStateException");
    }
    catch (final IllegalStateException e) {
      assertEquals("@?Element(id=-1) was not found in annotation spec", e.getMessage());
    }

    try {
      test(null, ArrayError5.class, null, true);
      fail("Expected IllegalStateException");
    }
    catch (final IllegalStateException e) {
      assertEquals(ArrayError5.class.getName() + ": @" + ArrayType.class.getSimpleName() + "(elementIds={}) elementIds cannot be empty", e.getMessage());
    }
  }

  @Test
  public void testArray1d1() {
    test(null, Array1d1.class, (Object)null);
    test(null, Array1d1.class, true);
    test(null, Array1d1.class, true, null);
    test(null, Array1d1.class, true, null, true);
    test("[Invalid content was found starting with member index=3 for element id=0]", Array1d1.class, true, null, true, null);
    test("[Invalid content was found starting with member index=3 for element id=0]", Array1d1.class, true, null, true, null, true);
  }

  @Test
  public void testArray1d3() {
    test(null, Array1d3.class, null, "string");
    test(null, Array1d3.class, true, "string", BigInteger.ONE);
    test(null, Array1d3.class, true, "string", BigDecimals.TWO);
    test("[Invalid content was found at member index=2 for element id=2: 3.141592653589793 is not form=INTEGER]", Array1d3.class, true, "string", BigDecimals.PI);
    test("[Invalid content was found at member index=2 for element id=2: 3.141592653589793 is not form=INTEGER, Invalid content was found starting with member index=3 for element id=2]", Array1d3.class, true, "string", BigDecimals.PI, null);
    test("[Invalid content was found at member index=2 for element id=2: 3.141592653589793 is not form=INTEGER, Invalid content was found at member index=3 for element id=2: 10 does not match range=\"[0,4]\"]", Array1d3.class, true, "string", BigDecimals.PI, BigDecimal.TEN);
    test("[Invalid content was found at member index=2 for element id=2: 3.141592653589793 is not form=INTEGER, Invalid content was found at member index=3 for element id=2: 10 does not match range=\"[0,4]\", Invalid content was found starting with member index=4 for element id=2]", Array1d3.class, true, "string", BigDecimals.PI, BigDecimal.TEN, null);
    test("[Invalid content was found at member index=1 for element id=1: \"111\" does not match pattern=\"[a-z]+\", Invalid content was found starting with member index=2 for element id=2]", Array1d3.class, null, "111", null);
    test("[Invalid content was found starting with member index=3 for element id=1]", Array1d3.class, null, null, null, null, null);
    test("[Invalid content was found starting with member index=0 for element id=0]", Array1d3.class, "string");
    test("[Invalid content was found at member index=1 for element id=1: \"111\" does not match pattern=\"[a-z]+\", Invalid content was found starting with member index=3 for element id=2]", Array1d3.class, null, "111", "string", "string");
  }
}