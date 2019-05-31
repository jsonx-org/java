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

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.List;

import org.junit.Test;

public class JsdUtilTest {
  private static class TestBinding implements JxObject {
    @AnyProperty(name="any")
    public Object _any;

    @ArrayProperty(name="array")
    public List<?> _array;

    @BooleanProperty(name="boolean")
    public Boolean _boolean;

    @NumberProperty(name="number")
    public BigInteger _number;

    @ObjectProperty(name="object")
    public TestBinding _object;

    @StringProperty(name="string")
    public String _string;
  }

  private static void testGetField(final String name) throws NoSuchFieldException {
    final String fieldName = "_" + name;
    final Field field = TestBinding.class.getField(fieldName);
    final String propertyName = JsdUtil.getName(field);
    assertEquals(name, propertyName);
  }

  @Test
  public void testToIdentifier() {
    assertEquals("_$", JsdUtil.toIdentifier(""));
    assertEquals("helloWorld", JsdUtil.toIdentifier("helloWorld"));
    assertEquals("_32HelloWorld", JsdUtil.toIdentifier("2HelloWorld"));
  }

  @Test
  public void testToInstanceName() {
    assertEquals("_$", JsdUtil.toInstanceName(""));
    assertEquals("helloWorld", JsdUtil.toInstanceName("HelloWorld"));
    assertEquals("_32HelloWorld", JsdUtil.toInstanceName("2HelloWorld"));
  }

  @Test
  public void testToClassName() {
    assertEquals("_$", JsdUtil.toClassName(""));
    assertEquals("HelloWorld", JsdUtil.toClassName("helloWorld"));
    assertEquals("_32helloWorld", JsdUtil.toClassName("2helloWorld"));
  }

  @Test
  public void testFlipName() {
    assertEquals("Cat", JsdUtil.flipName("cat"));
    assertEquals("Cat", JsdUtil.flipName("cat"));
    assertEquals("Cat", JsdUtil.flipName("cat"));

    assertEquals("CAT", JsdUtil.flipName("CAT"));
    assertEquals("CAT", JsdUtil.flipName("CAT"));
    assertEquals("CAT", JsdUtil.flipName("CAT"));

    assertEquals("a.b.Cat", JsdUtil.flipName("a.b.cat"));
    assertEquals("a.b-Cat", JsdUtil.flipName("a.b$cat"));
    assertEquals("a.b$Cat", JsdUtil.flipName("a.b-cat"));

    assertEquals("a.b.CAT", JsdUtil.flipName("a.b.CAT"));
    assertEquals("a.b-CAT", JsdUtil.flipName("a.b$CAT"));
    assertEquals("a.b$CAT", JsdUtil.flipName("a.b-CAT"));
  }

  @Test
  public void testGetField() throws NoSuchFieldException {
    testGetField("any");
    testGetField("array");
    testGetField("boolean");
    testGetField("number");
    testGetField("object");
    testGetField("string");
  }
}