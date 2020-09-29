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

import static org.jsonx.TestUtil.*;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.List;

import org.jsonx.Invalid.ArrAnnotationType;
import org.jsonx.Invalid.Bool;
import org.jsonx.Invalid.InvalidName;
import org.jsonx.Invalid.NoProperty;
import org.jsonx.Invalid.Num;
import org.jsonx.TestArray.Array2d1;
import org.jsonx.TestArray.ArrayAny;
import org.jsonx.TestArray.ArrayLoop;
import org.jsonx.library.Library;
import org.jsonx.library.Library.Staff;
import org.junit.Test;

public class JsdUtilTest {
  @SuppressWarnings("unused")
  private static class TestObject implements JxObject {
    public Object _any;

    @AnyProperty(name="any")
    public Object getAny() {
      return this._any;
    }

    public void setAny(final Object _any) {
      this._any = _any;
    }

    public List<?> _array;

    @ArrayProperty(name="array")
    public List<?> getArray() {
      return this._array;
    }

    public void setArray(final List<?> _array) {
      this._array = _array;
    }

    public Boolean _boolean;

    @BooleanProperty(name="boolean")
    public Boolean getBoolean() {
      return this._boolean;
    }

    public void setBoolean(final Boolean _boolean) {
      this._boolean = _boolean;
    }

    public BigInteger _number;

    @NumberProperty(name="number")
    public BigInteger getNumber() {
      return this._number;
    }

    public void setNumber(final BigInteger _number) {
      this._number = _number;
    }

    public TestObject _object;

    @ObjectProperty(name="object")
    public TestObject getObject() {
      return this._object;
    }

    public void setObject(final TestObject _object) {
      this._object = _object;
    }

    public String _string;

    @StringProperty(name="string")
    public String getString() {
      return this._string;
    }

    public void setString(final String _string) {
      this._string = _string;
    }
  }

  private static void testGetField(final String name) throws NoSuchMethodException {
    final Method getMethod = getMethod(TestObject.class, name);
    final String propertyName = JsdUtil.getName(getMethod);
    assertEquals(name, propertyName);
  }

  @Test
  public void testToIdentifier() {
    assertEquals("_$", JsdUtil.toIdentifier(""));
    assertEquals("helloWorld", JsdUtil.toIdentifier("helloWorld"));
    assertEquals("_2HelloWorld", JsdUtil.toIdentifier("2HelloWorld"));
  }

  @Test
  public void testToInstanceName() {
    assertEquals("_$", JsdUtil.toInstanceName(""));
    assertEquals("_com", JsdUtil.toInstanceName("com"));
    assertEquals("_java", JsdUtil.toInstanceName("java"));
    assertEquals("_org", JsdUtil.toInstanceName("org"));
    assertEquals("helloWorld", JsdUtil.toInstanceName("HelloWorld"));
    assertEquals("_2HelloWorld", JsdUtil.toInstanceName("2HelloWorld"));
  }

  @Test
  public void testToClassName() {
    assertEquals("_$", JsdUtil.toClassName(""));
    assertEquals("HelloWorld", JsdUtil.toClassName("helloWorld"));
    assertEquals("2helloWorld", JsdUtil.toClassName("2helloWorld"));
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
  public void testGetField() throws NoSuchMethodException {
    testGetField("any");
    testGetField("array");
    testGetField("boolean");
    testGetField("number");
    testGetField("object");
    testGetField("string");
  }

  @Test
  public void testGetMaxOccurs() {
    try {
      JsdUtil.getMaxOccurs(ArrayLoop.class.getAnnotation(ArrayType.class));
      fail("Expected UnsupportedOperationException");
    }
    catch (final UnsupportedOperationException e) {
    }

    assertEquals(Integer.MAX_VALUE, JsdUtil.getMaxOccurs(Staff.class.getAnnotation(ObjectElement.class)));

    assertEquals(1, JsdUtil.getMaxOccurs(Array2d1.class.getAnnotation(ArrayElement.class)));
    assertEquals(Integer.MAX_VALUE, JsdUtil.getMaxOccurs(ArrayLoop.class.getAnnotation(StringElement.class)));
    assertEquals(Integer.MAX_VALUE, JsdUtil.getMaxOccurs(ArrayLoop.class.getAnnotation(NumberElement.class)));
    assertEquals(Integer.MAX_VALUE, JsdUtil.getMaxOccurs(ArrayLoop.class.getAnnotation(BooleanElement.class)));
    assertEquals(Integer.MAX_VALUE, JsdUtil.getMaxOccurs(ArrayAny.class.getAnnotation(AnyElements.class).value()[0]));
  }

  @Test
  public void testGetName() throws NoSuchMethodException {
    assertNull(JsdUtil.getName(getMethod(NoProperty.class, "noProperty")));
    assertEquals("foo", JsdUtil.getName(getMethod(InvalidName.class, "invalidName")));
    assertEquals("invalidAnnotation", JsdUtil.getName(getMethod(Bool.class, "invalidAnnotation")));
    assertEquals("invalidAnnotation", JsdUtil.getName(getMethod(Num.class, "invalidAnnotation")));
    assertEquals("invalidAnnotationType", JsdUtil.getName(getMethod(ArrAnnotationType.class, "invalidAnnotationType")));
    assertEquals("address", JsdUtil.getName(getMethod(Library.class, "address")));
  }
}