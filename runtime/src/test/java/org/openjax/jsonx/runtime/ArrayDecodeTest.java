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

public class ArrayTest {
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

    test(array.setArray1d1(a((Boolean)null)));
    test(array.setArray1d1(a(true)));
    test(array.setArray1d1(a(true, null)));
    test(array.setArray1d1(a(true, null, true)));
    test(array.setArray1d1(a(true, null, true, null)), "Invalid content was found starting with member index=3: @" + BooleanElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=3, nullable=true): No members are expected at this point: null");
    test(array.setArray1d1(a(true, null, true, true, null)), "Invalid content was found starting with member index=3: @" + BooleanElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=3, nullable=true): No members are expected at this point: true");
  }

  @Test
  public void testArray1d2() throws DecodeException, IOException {
    final TestArray array = new TestArray();

    test(array.setArray1d2(a(null, "abc", null)));
    test(array.setArray1d2(a(null, "abc", BigInteger.ONE)));
    test(array.setArray1d2(a(true, "abc", null, BigInteger.ONE)));
    test(array.setArray1d2(a(true, null, "abc", BigDecimals.TWO)));
    test(array.setArray1d2(a(true, null, "abc", "abc", BigDecimals.TWO)));
    test(array.setArray1d2(a(true, null, "abc", "123", BigDecimals.TWO)));
    test(array.setArray1d2(a(null, null, null, null, null)));
    test(array.setArray1d2(a(null, null, null, null)));
    test(array.setArray1d2(a(null, null, null)));
    test(array.setArray1d2(a(null, null, BigDecimals.TWO)));
    test(array.setArray1d2(a(null, null, BigDecimal.TEN)));
    test(array.setArray1d2(a(null, null, null, BigDecimals.TWO)));
    test(array.setArray1d2(a(null, null, null, BigDecimals.TWO, BigDecimal.TEN)));
    test(array.setArray1d2(a(null, null, null, BigDecimals.PI, BigInteger.ONE)));
    test(array.setArray1d2(a(null, null, null, null, BigDecimals.TWO)));
    test(array.setArray1d2(a(null, null, null, null, null, BigDecimals.TWO)));
    test(array.setArray1d2(a(null, null, null, BigDecimals.PI, BigInteger.ONE)));
    test(array.setArray1d2(a(null, "abc", null, "abc", BigDecimals.PI, BigInteger.ONE)));
    test(array.setArray1d2(a(null, "abc", null, "123", BigDecimals.PI, BigInteger.ONE)));
    test(array.setArray1d2(a(null, null, "abc", "abc", BigDecimals.PI, BigInteger.ONE)));
    test(array.setArray1d2(a(null, null, "123", "abc", BigDecimals.PI, BigInteger.ONE)));
    test(a(null, BigDecimals.TWO), "Invalid content was found starting with member index=1: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", urlEncode=false, urlDecode=false, minOccurs=2, maxOccurs=3, nullable=true): Content is not expected: 2");
    test(a(true, "abc", true), "Invalid content was found starting with member index=2: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", urlEncode=false, urlDecode=false, minOccurs=2, maxOccurs=3, nullable=true): Content is not expected: true");
    test(a(true, "abc", "abc"), "Invalid content was found starting with member index=2: @" + NumberElement.class.getName() + "(id=4, form=REAL, range=\"[0,10]\", minOccurs=1, maxOccurs=2, nullable=true): Content is not complete");
    test(a(true, "abc", "abc", BigDecimals.PI, BigDecimal.TEN, null), "Invalid content was found starting with member index=3: @" + NumberElement.class.getName() + "(id=3, form=INTEGER, range=\"[0,4]\", minOccurs=0, maxOccurs=2, nullable=false): Illegal non-INTEGER value: 3.14159265358...");
    test(a(null, "111", null), "Invalid content was found starting with member index=1: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", urlEncode=false, urlDecode=false, minOccurs=2, maxOccurs=3, nullable=true): Pattern is not matched: \"111\"");
    test(a(true, true, true, true), "Invalid content was found starting with member index=3: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", urlEncode=false, urlDecode=false, minOccurs=2, maxOccurs=3, nullable=true): Content is not expected: true");
    test(a(null, "abc", "111", "abc"), "Invalid content was found starting with member index=2: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", urlEncode=false, urlDecode=false, minOccurs=2, maxOccurs=3, nullable=true): Pattern is not matched: \"111\"");
  }
}