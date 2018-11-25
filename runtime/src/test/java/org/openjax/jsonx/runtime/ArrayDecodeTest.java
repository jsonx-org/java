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

import static org.junit.Assert.*;
import static org.openjax.jsonx.runtime.TestUtil.*;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;

import org.fastjax.json.JsonReader;
import org.fastjax.math.BigDecimals;
import org.junit.Test;

public class ArrayDecodeTest {
  private static void test(final Object in) throws DecodeException, IOException {
    test(in, null);
  }

  private static void test(final Object in, final String error) throws DecodeException, IOException {
    try {
      final String inJson = in.toString();
      final Object out = JxDecoder.parseObject(in.getClass(), new JsonReader(new StringReader(inJson)));
      final String outJson = out.toString();
      assertEquals(inJson, outJson);
    }
    catch (final Exception e) {
      if (error == null)
        throw e;

      assertEquals(error, e.getMessage());
    }
  }

  @Test
  public void testArray1d1() throws DecodeException, IOException {
    final TestArray array = new TestArray();

    test(array.setArray1d1(l((Boolean)null)));
    test(array.setArray1d1(l(true)));
    test(array.setArray1d1(l(true, null)));
    test(array.setArray1d1(l(true, null, true)));
    test(array.setArray1d1(l(true, null, true, null)), "Invalid content was found starting with member index=3: @" + BooleanElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=3, nullable=true): No members are expected at this point: null");
    test(array.setArray1d1(l(true, null, true, true, null)), "Invalid content was found starting with member index=3: @" + BooleanElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=3, nullable=true): No members are expected at this point: true");
  }

  @Test
  public void testArray1d2() throws DecodeException, IOException {
    final TestArray array = new TestArray();

    test(array.setArray1d2(l(null, "abc", null)));
    test(array.setArray1d2(l(null, "abc", BigInteger.ONE)));
    test(array.setArray1d2(l(true, "abc", null, BigInteger.ONE)));
    test(array.setArray1d2(l(true, null, "abc", BigDecimals.TWO)));
    test(array.setArray1d2(l(true, null, "abc", "abc", BigDecimals.TWO)));
    test(array.setArray1d2(l(true, null, "abc", "123", BigDecimals.TWO)));
    test(array.setArray1d2(l(null, null, null, null, null)));
    test(array.setArray1d2(l(null, null, null, null)));
    test(array.setArray1d2(l(null, null, null)));
    test(array.setArray1d2(l(null, null, BigDecimals.TWO)));
    test(array.setArray1d2(l(null, null, BigDecimal.TEN)));
    test(array.setArray1d2(l(null, null, null, BigDecimals.TWO)));
    test(array.setArray1d2(l(null, null, null, BigDecimals.TWO, BigDecimal.TEN)));
    test(array.setArray1d2(l(null, null, null, BigDecimals.PI, BigInteger.ONE)));
    test(array.setArray1d2(l(null, null, null, null, BigDecimals.TWO)));
    test(array.setArray1d2(l(null, null, null, null, null, BigDecimals.TWO)));
    test(array.setArray1d2(l(null, null, null, BigDecimals.PI, BigInteger.ONE)));
    test(array.setArray1d2(l(null, "abc", null, "abc", BigDecimals.PI, BigInteger.ONE)));
    test(array.setArray1d2(l(null, "abc", null, "123", BigDecimals.PI, BigInteger.ONE)));
    test(array.setArray1d2(l(null, null, "abc", "abc", BigDecimals.PI, BigInteger.ONE)));
    test(array.setArray1d2(l(null, null, "123", "abc", BigDecimals.PI, BigInteger.ONE)));
    test(array.setArray1d2(l(null, BigDecimals.TWO)), "Invalid content was found starting with member index=1: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", urlEncode=false, urlDecode=false, minOccurs=2, maxOccurs=3, nullable=true): Content is not expected: 2");
    test(array.setArray1d2(l(true, "abc", true)), "Invalid content was found starting with member index=2: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", urlEncode=false, urlDecode=false, minOccurs=2, maxOccurs=3, nullable=true): Content is not expected: true");
    test(array.setArray1d2(l(true, "abc", "abc")), "Invalid content was found starting with member index=2: @" + NumberElement.class.getName() + "(id=4, form=REAL, range=\"[0,10]\", minOccurs=1, maxOccurs=2, nullable=true): Content is not complete");
    test(array.setArray1d2(l(true, "abc", "abc", BigDecimals.PI, BigDecimal.TEN, null)), "Invalid content was found starting with member index=3: @" + NumberElement.class.getName() + "(id=3, form=INTEGER, range=\"[0,4]\", minOccurs=0, maxOccurs=2, nullable=false): Illegal non-INTEGER value: 3.14159265358...");
    test(array.setArray1d2(l(null, "111", null)), "Invalid content was found starting with member index=1: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", urlEncode=false, urlDecode=false, minOccurs=2, maxOccurs=3, nullable=true): Pattern is not matched: \"111\"");
    test(array.setArray1d2(l(true, true, true, true)), "Invalid content was found starting with member index=3: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", urlEncode=false, urlDecode=false, minOccurs=2, maxOccurs=3, nullable=true): Content is not expected: true");
    test(array.setArray1d2(l(null, "abc", "111", "abc")), "Invalid content was found starting with member index=2: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", urlEncode=false, urlDecode=false, minOccurs=2, maxOccurs=3, nullable=true): Pattern is not matched: \"111\"");
  }

  @Test
  public void testArray2d1() throws DecodeException, IOException {
    final TestArray array = new TestArray();

    test(array.setArray2d1(l(l(null, "abc", null))));
    test(array.setArray2d1(l(l(null, "abc", BigInteger.ONE))));
    test(array.setArray2d1(l(l(true, "abc", null, BigInteger.ONE))));
    test(array.setArray2d1(l(l(true, null, "abc", BigDecimals.TWO))));
    test(array.setArray2d1(l(l(true, null, "abc", "abc", BigDecimals.TWO))));
    test(array.setArray2d1(l(l(true, null, "abc", "123", BigDecimals.TWO))));
    test(array.setArray2d1(l(l(null, null, null, null, null))));
    test(array.setArray2d1(l(l(null, null, null, null))));
    test(array.setArray2d1(l(l(null, null, null))));
    test(array.setArray2d1(l(l(null, null, BigDecimals.TWO))));
    test(array.setArray2d1(l(l(null, null, BigDecimal.TEN))));
    test(array.setArray2d1(l(l(null, null, null, BigDecimals.TWO))));
    test(array.setArray2d1(l(l(null, null, null, BigDecimals.TWO, BigDecimal.TEN))));
    test(array.setArray2d1(l(l(null, null, null, BigDecimals.PI, BigInteger.ONE))));
    test(array.setArray2d1(l(l(null, null, null, null, BigDecimals.TWO))));
    test(array.setArray2d1(l(l(null, null, null, null, null, BigDecimals.TWO))));
    test(array.setArray2d1(l(l(null, null, null, BigDecimals.PI, BigInteger.ONE))));
    test(array.setArray2d1(l(l(null, "abc", null, "abc", BigDecimals.PI, BigInteger.ONE))));
    test(array.setArray2d1(l(l(null, "abc", null, "123", BigDecimals.PI, BigInteger.ONE))));
    test(array.setArray2d1(l(l(null, null, "abc", "abc", BigDecimals.PI, BigInteger.ONE))));
    test(array.setArray2d1(l(l(null, null, "123", "abc", BigDecimals.PI, BigInteger.ONE))));
    test(array.setArray2d1(l(l())), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + TestArray.Array1d2.class.getName() + ".class, elementIds={}, minOccurs=1, maxOccurs=2147483647, nullable=false): Invalid content was found in empty array: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", urlEncode=false, urlDecode=false, minOccurs=2, maxOccurs=3, nullable=true): Content is not complete");
    test(array.setArray2d1(l(l(true, "abc", null, BigInteger.ZERO, BigDecimal.valueOf(100)))), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + TestArray.Array1d2.class.getName() + ".class, elementIds={}, minOccurs=1, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=4: @" + NumberElement.class.getName() + "(id=3, form=INTEGER, range=\"[0,4]\", minOccurs=0, maxOccurs=2, nullable=false): Range is not matched: 100");
    test(array.setArray2d1(l(l(true, "abc", BigDecimals.PI, BigDecimal.TEN, null))), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + TestArray.Array1d2.class.getName() + ".class, elementIds={}, minOccurs=1, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=2: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", urlEncode=false, urlDecode=false, minOccurs=2, maxOccurs=3, nullable=true): Content is not expected: 3.14159265358...");
    test(array.setArray2d1(l(l(null, "111", null))), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + TestArray.Array1d2.class.getName() + ".class, elementIds={}, minOccurs=1, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=1: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", urlEncode=false, urlDecode=false, minOccurs=2, maxOccurs=3, nullable=true): Pattern is not matched: \"111\"");
    test(array.setArray2d1(l(l("abc"))), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + TestArray.Array1d2.class.getName() + ".class, elementIds={}, minOccurs=1, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=0: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", urlEncode=false, urlDecode=false, minOccurs=2, maxOccurs=3, nullable=true): Content is not complete");
    test(array.setArray2d1(l(l(true))), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + TestArray.Array1d2.class.getName() + ".class, elementIds={}, minOccurs=1, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=0: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", urlEncode=false, urlDecode=false, minOccurs=2, maxOccurs=3, nullable=true): Content is not complete");
    test(array.setArray2d1(l(l(null, "abc", "111", "abc"))), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + TestArray.Array1d2.class.getName() + ".class, elementIds={}, minOccurs=1, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=2: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", urlEncode=false, urlDecode=false, minOccurs=2, maxOccurs=3, nullable=true): Pattern is not matched: \"111\"");
    test(array.setArray2d1(l(l(null, "111", null))), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + TestArray.Array1d2.class.getName() + ".class, elementIds={}, minOccurs=1, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=1: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", urlEncode=false, urlDecode=false, minOccurs=2, maxOccurs=3, nullable=true): Pattern is not matched: \"111\"");
  }

  @Test
  public void testArray2d2() throws DecodeException, IOException {
    final TestArray array = new TestArray();

    test(array.setArray2d2(l(l(null, "abc", null))));
    test(array.setArray2d2(l(l(null, "abc", BigInteger.ONE))));
    test(array.setArray2d2(l(l(true, "abc", null, BigInteger.ONE))));
    test(array.setArray2d2(l(l(true, null, "abc", BigDecimals.TWO))));
    test(array.setArray2d2(l(l(true, null, "abc", "abc", BigDecimals.TWO))));
    test(array.setArray2d2(l(l(true, null, "abc", "123", BigDecimals.TWO))));
    test(array.setArray2d2(l(l(null, null, null, null, null))));
    test(array.setArray2d2(l(l(null, null, null, null))));
    test(array.setArray2d2(l(l(null, null, null))));
    test(array.setArray2d2(l(l(null, null, BigDecimals.TWO))));
    test(array.setArray2d2(l(l(null, null, BigDecimal.TEN))));
    test(array.setArray2d2(l(l(null, null, null, BigDecimals.TWO))));
    test(array.setArray2d2(l(l(null, null, null, BigDecimals.TWO, BigDecimal.TEN))));
    test(array.setArray2d2(l(l(null, null, null, BigDecimals.PI, BigInteger.ONE))));
    test(array.setArray2d2(l(l(null, null, null, null, BigDecimals.TWO))));
    test(array.setArray2d2(l(l(null, null, null, null, null, BigDecimals.TWO))));
    test(array.setArray2d2(l(l(null, null, null, BigDecimals.PI, BigInteger.ONE))));
    test(array.setArray2d2(l(l(null, "abc", null, "abc", BigDecimals.PI, BigInteger.ONE))));
    test(array.setArray2d2(l(l(null, "abc", null, "123", BigDecimals.PI, BigInteger.ONE))));
    test(array.setArray2d2(l(l(null, null, "abc", "abc", BigDecimals.PI, BigInteger.ONE))));
    test(array.setArray2d2(l(l(null, null, "123", "abc", BigDecimals.PI, BigInteger.ONE))));

    test(array.setArray2d2(l(l(null, "ABC", null))));
    test(array.setArray2d2(l(l(null, "ABC", BigInteger.ONE))));
    test(array.setArray2d2(l(l(true, "ABC", null, BigInteger.ONE))));
    test(array.setArray2d2(l(l(true, null, "ABC", BigDecimals.TWO))));
    test(array.setArray2d2(l(l(true, null, "ABC", "ABC", BigDecimals.TWO))));
    test(array.setArray2d2(l(l(true, null, "ABC", "123", BigDecimals.TWO))));
    test(array.setArray2d2(l(l(null, "ABC", null, "ABC", BigDecimals.PI, BigInteger.ONE))));
    test(array.setArray2d2(l(l(null, "ABC", null, "123", BigDecimals.PI, BigInteger.ONE))));
    test(array.setArray2d2(l(l(null, null, "ABC", "ABC", BigDecimals.PI, BigInteger.ONE))));
    test(array.setArray2d2(l(l(null, null, "123", "ABC", BigDecimals.PI, BigInteger.ONE))));

    test(array.setArray2d2(l(l(null, "abc", null), l(null, "ABC", null))));
    test(array.setArray2d2(l(l(null, "abc", null), null, l(null, "ABC", null))));
    test(array.setArray2d2(l(l(null, "abc", null), null, l(null, "ABC", null), BigDecimal.TEN)));
    test(array.setArray2d2(l(l(null, "abc", null), null, l(null, "ABC", null), BigDecimal.ONE)));

    test(array.setArray2d2(l(true, l(), BigInteger.ZERO, BigInteger.ONE, BigInteger.TWO)), "Invalid content was found starting with member index=1: @" + ArrayElement.class.getName() + "(id=2, type=" + ArrayType.class.getName() + ".class, elementIds={3, 4, 5, 6, 7}, minOccurs=0, maxOccurs=2147483647, nullable=false): Invalid content was found in empty array: @" + StringElement.class.getName() + "(id=4, pattern=\"[A-Z]+\", urlEncode=false, urlDecode=false, minOccurs=2, maxOccurs=3, nullable=true): Content is not complete");
    test(array.setArray2d2(l(true, l(null, "abc"), "abc", BigInteger.ZERO, BigInteger.ONE)), "Invalid content was found starting with member index=1: @" + ArrayElement.class.getName() + "(id=2, type=" + ArrayType.class.getName() + ".class, elementIds={3, 4, 5, 6, 7}, minOccurs=0, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=1: @" + StringElement.class.getName() + "(id=4, pattern=\"[A-Z]+\", urlEncode=false, urlDecode=false, minOccurs=2, maxOccurs=3, nullable=true): Pattern is not matched: \"abc\"");
    test(array.setArray2d2(l(true, l(null, "ABC", "ABC", BigInteger.TEN), "abc", BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE)), "Invalid content was found starting with member index=2: @" + NumberElement.class.getName() + "(id=9, form=INTEGER, range=\"[0,5]\", minOccurs=0, maxOccurs=1, nullable=false): No members are expected at this point: abc");
    test(array.setArray2d2(l(true, l(null, "ABC", "ABC", BigInteger.TEN), BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE)), "Invalid content was found starting with member index=2: @" + NumberElement.class.getName() + "(id=8, form=INTEGER, range=\"[5,10]\", minOccurs=0, maxOccurs=1, nullable=false): Range is not matched: 0");
    test(array.setArray2d2(l(l(null, "abc", "abc", BigInteger.TEN), false, l(BigInteger.ZERO, "123"))), "Invalid content was found starting with member index=2: @" + ArrayElement.class.getName() + "(id=2, type=" + ArrayType.class.getName() + ".class, elementIds={3, 4, 5, 6, 7}, minOccurs=0, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=0: @" + StringElement.class.getName() + "(id=4, pattern=\"[A-Z]+\", urlEncode=false, urlDecode=false, minOccurs=2, maxOccurs=3, nullable=true): Content is not expected: 0");
    test(array.setArray2d2(l(l(null, "abc", null, BigDecimal.TEN), false, l("ABC", "ABC", null, BigDecimals.PI, "abc"))), "Invalid content was found starting with member index=2: @" + ArrayElement.class.getName() + "(id=2, type=" + ArrayType.class.getName() + ".class, elementIds={3, 4, 5, 6, 7}, minOccurs=0, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=3: @" + NumberElement.class.getName() + "(id=6, form=INTEGER, range=\"[0,4]\", minOccurs=0, maxOccurs=2, nullable=false): Illegal non-INTEGER value: 3.14159265358...");
    test(array.setArray2d2(l(l(null, "abc", null, BigDecimal.TEN), false, l("ABC", "ABC", null, BigDecimal.ZERO, null, null, null))), "Invalid content was found starting with member index=2: @" + ArrayElement.class.getName() + "(id=2, type=" + ArrayType.class.getName() + ".class, elementIds={3, 4, 5, 6, 7}, minOccurs=0, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=4: @" + NumberElement.class.getName() + "(id=6, form=INTEGER, range=\"[0,4]\", minOccurs=0, maxOccurs=2, nullable=false): Illegal value: null");
  }

  @Test
  public void testArray3d() throws DecodeException, IOException {
    final TestArray array = new TestArray();

    test(array.setArray3d(l(l(null, "abc", null), l(l(null, "abc", null)))));
    test(array.setArray3d(l(l(null, "abc", BigInteger.ONE), l(l(null, "abc", BigInteger.ONE)))));
    test(array.setArray3d(l(l(true, "abc", null, BigInteger.ONE), l(l(true, "abc", null, BigInteger.ONE)))));
    test(array.setArray3d(l(l(true, null, "abc", BigDecimals.TWO), l(l(true, null, "abc", BigDecimals.TWO)))));
    test(array.setArray3d(l(l(true, null, "abc", "abc", BigDecimals.TWO), l(l(true, null, "abc", "abc", BigDecimals.TWO)))));
    test(array.setArray3d(l(l(true, null, "abc", "123", BigDecimals.TWO), l(l(true, null, "abc", "123", BigDecimals.TWO)))));
    test(array.setArray3d(l(l(null, null, null, null, null), l(l(null, null, null, null, null)))));
    test(array.setArray3d(l(l(null, null, null, null), l(l(null, null, null, null)))));
    test(array.setArray3d(l(l(null, null, null), l(l(null, null, null)))));
    test(array.setArray3d(l(l(null, null, BigDecimals.TWO), l(l(null, null, BigDecimals.TWO)))));
    test(array.setArray3d(l(l(null, null, BigDecimal.TEN), l(l(null, null, BigDecimal.TEN)))));
    test(array.setArray3d(l(l(null, null, null, BigDecimals.TWO), l(l(null, null, null, BigDecimals.TWO)))));
    test(array.setArray3d(l(l(null, null, null, BigDecimals.TWO, BigDecimal.TEN), l(l(null, null, null, BigDecimals.TWO, BigDecimal.TEN)))));
    test(array.setArray3d(l(l(null, null, null, BigDecimals.PI, BigInteger.ONE), l(l(null, null, null, BigDecimals.PI, BigInteger.ONE)))));
    test(array.setArray3d(l(l(null, null, null, null, BigDecimals.TWO), l(l(null, null, null, null, BigDecimals.TWO)))));
    test(array.setArray3d(l(l(null, null, null, null, null, BigDecimals.TWO), l(l(null, null, null, null, null, BigDecimals.TWO)))));
    test(array.setArray3d(l(l(null, null, null, BigDecimals.PI, BigInteger.ONE), l(l(null, null, null, BigDecimals.PI, BigInteger.ONE)))));
    test(array.setArray3d(l(l(null, "abc", null, "abc", BigDecimals.PI, BigInteger.ONE), l(l(null, "abc", null, "abc", BigDecimals.PI, BigInteger.ONE)))));
    test(array.setArray3d(l(l(null, "abc", null, "123", BigDecimals.PI, BigInteger.ONE), l(l(null, "abc", null, "123", BigDecimals.PI, BigInteger.ONE)))));
    test(array.setArray3d(l(l(null, null, "abc", "abc", BigDecimals.PI, BigInteger.ONE), l(l(null, null, "abc", "abc", BigDecimals.PI, BigInteger.ONE)))));
    test(array.setArray3d(l(l(null, null, "123", "abc", BigDecimals.PI, BigInteger.ONE), l(l(null, null, "123", "abc", BigDecimals.PI, BigInteger.ONE)))));

    test(array.setArray3d(l(l(null, "abc", null), l(l(null, "ABC", null)))));
    test(array.setArray3d(l(l(null, "abc", BigInteger.ONE), l(l(null, "ABC", BigInteger.ONE)))));
    test(array.setArray3d(l(l(true, "abc", null, BigInteger.ONE), l(l(true, "ABC", null, BigInteger.ONE)))));
    test(array.setArray3d(l(l(true, null, "abc", BigDecimals.TWO), l(l(true, null, "ABC", BigDecimals.TWO)))));
    test(array.setArray3d(l(l(true, null, "abc", "abc", BigDecimals.TWO), l(l(true, null, "ABC", "ABC", BigDecimals.TWO)))));
    test(array.setArray3d(l(l(true, null, "abc", "123", BigDecimals.TWO), l(l(true, null, "ABC", "123", BigDecimals.TWO)))));
    test(array.setArray3d(l(l(null, "abc", null, "abc", BigDecimals.PI, BigInteger.ONE), l(l(null, "ABC", null, "ABC", BigDecimals.PI, BigInteger.ONE)))));
    test(array.setArray3d(l(l(null, "abc", null, "123", BigDecimals.PI, BigInteger.ONE), l(l(null, "ABC", null, "123", BigDecimals.PI, BigInteger.ONE)))));
    test(array.setArray3d(l(l(null, null, "abc", "abc", BigDecimals.PI, BigInteger.ONE), l(l(null, null, "ABC", "ABC", BigDecimals.PI, BigInteger.ONE)))));
    test(array.setArray3d(l(l(null, null, "123", "abc", BigDecimals.PI, BigInteger.ONE), l(l(null, null, "123", "ABC", BigDecimals.PI, BigInteger.ONE)))));

    test(array.setArray3d(l(true)), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=1, type=" + TestArray.Array2d2.class.getName() + ".class, elementIds={}, minOccurs=1, maxOccurs=2147483647, nullable=false): Content is not expected: true");
    test(array.setArray3d(l(l(true, "abc", "abc", BigDecimals.PI), l(l(null, "abc"), false, l(BigInteger.ZERO, "abc")))), "Invalid content was found starting with member index=1: @" + ArrayElement.class.getName() + "(id=0, type=" + TestArray.Array1d2.class.getName() + ".class, elementIds={}, minOccurs=0, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=0: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", urlEncode=false, urlDecode=false, minOccurs=2, maxOccurs=3, nullable=true): Content is not expected: [null, abc]");
  }
}