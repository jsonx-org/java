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

import static org.jsonx.TestUtil.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsonx.ArrayValidator.Relation;
import org.jsonx.ArrayValidator.Relations;
import org.jsonx.TestArray.Array1d1;
import org.jsonx.TestArray.Array1d2;
import org.jsonx.TestArray.Array1d3;
import org.jsonx.TestArray.Array2d1;
import org.jsonx.TestArray.Array2d2;
import org.jsonx.TestArray.Array3d;
import org.jsonx.TestArray.ArrayAny;
import org.jsonx.TestArray.ArrayLoop;
import org.junit.Test;
import org.libj.lang.BigDecimals;
import org.libj.lang.BigIntegers;
import org.libj.lang.Strings;
import org.libj.util.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArrayCodecTest {
  private static final Logger logger = LoggerFactory.getLogger(ArrayCodecTest.class);

  private static final Map<Class<? extends Annotation>,IdToElement> classToIdToElement = new HashMap<>();
  private static final boolean debugPass = false;
  private static final boolean debugFail = false;
  private static final boolean skipDecode = false;

  private static void testDecode(final Class<? extends Annotation> annotationType, final JxObject jxObject, final List<Object> members, final String expected) throws DecodeException, IOException {
    if (skipDecode)
      return;

    final JxObject in;
    try {
      final Method method = jxObject.getClass().getMethod("set" + annotationType.getSimpleName(), List.class);
      in = (JxObject)method.invoke(jxObject, members);
    }
    catch (final IllegalAccessException | NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
    catch (final InvocationTargetException e) {
      if (e.getCause() instanceof RuntimeException)
        throw (RuntimeException)e.getCause();

      throw new RuntimeException(e.getCause());
    }

    try {
      final String inJson = in.toString();
      final Object out = JxDecoder.VALIDATING.parseObject(in.getClass(), inJson);
      final String outJson = out.toString();
      assertEquals(inJson, outJson);
      if (expected != null)
        fail("Expected: " + expected);
    }
    catch (final Exception e) {
      if (expected == null)
        throw e;

      if (!debugFail) {
        final boolean endsWith = e.getMessage().endsWith(expected);
        if (!endsWith) {
          e.printStackTrace();
          assertTrue(e.getMessage() + " <> " + expected, endsWith);
        }
      }
    }
  }

  private static IdToElement getIdToElement(final Class<? extends Annotation> annotationType) {
    IdToElement idToElement = classToIdToElement.get(annotationType);
    if (idToElement == null) {
      idToElement = new IdToElement();
      JsdUtil.fillIdToElement(idToElement, annotationType.getAnnotations());
      classToIdToElement.put(annotationType, idToElement);
    }

    return idToElement;
  }

  private static Annotation getAnnotation(final IdToElement idToElement, final List<String> index) {
    final int i = Integer.parseInt(index.remove(0));
    final Annotation annotation = idToElement.get(i);
    if (index.size() > 0) {
      assertSame(String.valueOf(i), ArrayElement.class, annotation.annotationType());
      return getAnnotation(getIdToElement(((ArrayElement)annotation).type()), index);
    }

    return annotation;
  }

  private static void test(final Class<? extends Annotation> annotationType, final JxObject jxObject, final List<Object> members, final String[] expected) throws DecodeException, IOException {
    final IdToElement idToElement = getIdToElement(annotationType);
    final ArrayList<Annotation> annotations = new ArrayList<>();
    for (final String term : expected) {
      final ArrayList<String> index = new ArrayList<>(Arrays.asList(term.split("\\.")));
      annotations.add(getAnnotation(idToElement, index));
    }

    final Annotation[] selected = annotations.toArray(new Annotation[annotations.size()]);
    test(selected, annotationType, members, null);
    testDecode(annotationType, jxObject, members, null);
  }

  private static void test(final Class<? extends Annotation> annotationType, final JxObject jxObject, final List<Object> members, final String expected) throws DecodeException, IOException {
    test(null, annotationType, members, expected);
    testDecode(annotationType, jxObject, members, expected);
  }

  @SuppressWarnings("unused")
  private static void test(final Annotation[] annotations, final Class<? extends Annotation> annotationType, final List<Object> members, final String expected) {
    final Relations relations = new Relations();
    final Error error = ArrayValidator.validate(annotationType, members, relations, true, null);
    final Relations flatRelations = CollectionUtil.flatten(relations, new Relations(), m -> m.member instanceof Relations ? (Relations)m.member : null, true);
    final String errorString = error == null ? null : error.toString();
    if (expected != null && errorString != null && !expected.equals(errorString)) {
      String msg = "\"" + Strings.escapeForJava(errorString) + "\"";
      msg = msg.replace('$', '.');
      msg = msg.replace(".class", "%class");
      msg = msg.replaceAll("org\\.openjax\\.[.a-zA-Z]+\\.([a-zA-Z0-9]+)", "\" + $1.class.getName() + \"");
      msg = msg.replace("%class", ".class");
      final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
      logger.error(stackTrace[3].getLineNumber() + ": " + msg);
    }

    if (expected == null ? debugPass : debugFail)
      assertEquals(flatRelations.toString(), expected == null, errorString == null);
    else
      assertEquals(expected, errorString);

    if (errorString == null) {
      final ArrayList<Object> flatMembers = CollectionUtil.flatten(members, new ArrayList<>(), true);
      assertEquals("Number of members not matched", flatMembers.size(), flatRelations.size());
      assertEquals(flatMembers.toString(), annotations.length, flatMembers.size());
      if (!debugPass) {
        for (int i = 0; i < annotations.length; ++i) {
          final Relation relation = flatRelations.get(i);
          assertEquals(i + ": " + flatRelations.toString(), annotations[i], relation.annotation);
          final Object member = flatMembers.get(i);
          if (relation.member instanceof Relations) {
            assertTrue(member instanceof List);
            assertMembersEqual((List<?>)member, (Relations)relation.member);
          }
          else {
            assertEquals(member instanceof String ? "\"" + member + "\"" : member, relation.member);
          }
        }
      }
    }
  }

  private static void assertMembersEqual(final List<?> expected, final Relations actual) {
    assertEquals(expected.size(), actual.size());
    for (int i = 0; i < expected.size(); ++i) {
      final Object member = expected.get(i);
      final Relation relation = actual.get(i);
      if (member instanceof List) {
        assertTrue(relation.member instanceof Relations);
        assertMembersEqual((List<?>)member, (Relations)relation.member);
      }
      else {
        assertEquals(member instanceof String ? "\"" + member + "\"" : member, relation.member);
      }
    }
  }

  @NumberElement(id=0, maxOccurs=1, range="xxxx")
  @ArrayType(elementIds={0})
  private @interface ArrayError1 {
  }

  @BooleanElement(id=0, maxOccurs=0, nullable=false)
  @ArrayType(elementIds={0})
  private @interface ArrayError2 {
  }

  @BooleanElement(id=0, maxOccurs=-1, nullable=false)
  @ArrayType(elementIds={-1})
  private @interface ArrayError3 {
  }

  @BooleanElement(id=0, maxOccurs=-1, nullable=false)
  @ArrayType(elementIds={})
  private @interface ArrayError4 {
  }

  @BooleanElement(id=0, nullable=false)
  @BooleanElement(id=0, nullable=false)
  @ArrayType(elementIds={0})
  private @interface ArrayError5 {
  }

  @Test
  public void testArrayError() throws DecodeException, IOException {
    try {
      test(ArrayError1.class, null, l(BigDecimals.PI), "");
      fail("Expected ValidationException");
    }
    catch (final ValidationException e) {
      assertEquals("Invalid range attribute: @" + NumberElement.class.getName() + "(id=0, range=\"xxxx\", maxOccurs=1)", e.getMessage());
    }

    try {
      test(ArrayError2.class, null, l(null, true), "");
      fail("Expected ValidationException");
    }
    catch (final ValidationException e) {
      assertEquals("minOccurs must be less than or equal to maxOccurs: @" + BooleanElement.class.getName() + "(id=0, maxOccurs=0, nullable=false)", e.getMessage());
    }

    try {
      test(ArrayError3.class, null, l(null, true), "");
      fail("Expected ValidationException");
    }
    catch (final ValidationException e) {
      assertEquals("@<Annotation>(id=-1) not found in annotations array", e.getMessage());
    }

    try {
      test(ArrayError4.class, null, l(null, true), "");
      fail("Expected ValidationException");
    }
    catch (final ValidationException e) {
      assertEquals("elementIds property cannot be empty: " + ArrayError4.class.getName() + ": @" + ArrayType.class.getName() + "(elementIds={})", e.getMessage());
    }

    try {
      test(ArrayError5.class, null, l(null, true), "");
      fail("Expected ValidationException");
    }
    catch (final ValidationException e) {
      assertTrue(e.getMessage().startsWith("Duplicate id=0 found in"));
    }
  }

  @Test
  public void testArrayAny() throws DecodeException, IOException {
    final TestArray array = new TestArray();

    test(ArrayAny.class, array, l(), a());
    test(ArrayAny.class, array, l(Boolean.TRUE), a("0"));
    test(ArrayAny.class, array, l(Boolean.TRUE, BigInteger.ONE), a("0", "1"));
    test(ArrayAny.class, array, l(Boolean.TRUE, BigInteger.ONE, "string"), a("0", "1", "2"));
    test(ArrayAny.class, array, l((Boolean)null), "Invalid content was found starting with member index=0: @org.jsonx.AnyElement(id=0, types={@org.jsonx.t(booleans=@org.jsonx.BooleanType())}, minOccurs=0, nullable=false): Illegal value: null");

    test(ArrayAny.class, array, l(BigInteger.ONE), a("1"));
    test(ArrayAny.class, array, l(BigInteger.ONE, Boolean.TRUE), a("1", "0"));
    test(ArrayAny.class, array, l(BigInteger.ONE, "string", Boolean.TRUE), a("1", "2", "0"));

    test(ArrayAny.class, array, l("string"), a("2"));
    test(ArrayAny.class, array, l(BigInteger.ONE, "string"), a("1", "2"));
    test(ArrayAny.class, array, l("string", BigInteger.ONE, Boolean.TRUE, "string", BigInteger.ONE, Boolean.TRUE), a("2", "1", "0", "2", "1", "0"));
  }

  @Test
  public void testArrayLoop() throws DecodeException, IOException {
    final TestArray array = new TestArray();

    test(ArrayLoop.class, array, l(), a());
    test(ArrayLoop.class, array, l(Boolean.TRUE), a("0"));
    test(ArrayLoop.class, array, l(Boolean.TRUE, BigInteger.ONE), a("0", "1"));
    test(ArrayLoop.class, array, l(Boolean.TRUE, BigInteger.ONE, "string"), a("0", "1", "2"));
    test(ArrayLoop.class, array, l((Boolean)null), "Invalid content was found starting with member index=0: @" + BooleanElement.class.getName() + "(id=0, minOccurs=0, nullable=false): Illegal value: null");

    test(ArrayLoop.class, array, l(BigInteger.ONE), a("1"));
    test(ArrayLoop.class, array, l(BigInteger.ONE, Boolean.TRUE), a("1", "0"));
    test(ArrayLoop.class, array, l(BigInteger.ONE, "string", Boolean.TRUE), a("1", "2", "0"));

    test(ArrayLoop.class, array, l("string"), a("2"));
    test(ArrayLoop.class, array, l(BigInteger.ONE, "string"), a("1", "2"));
    test(ArrayLoop.class, array, l("string", BigInteger.ONE, Boolean.TRUE, "string", BigInteger.ONE, Boolean.TRUE), a("2", "1", "0", "2", "1", "0"));
  }

  @Test
  public void testArray1d1() throws DecodeException, IOException {
    final TestArray array = new TestArray();

    test(Array1d1.class, array, l(), a());
    test(Array1d1.class, array, l((Object)null), a("0"));
    test(Array1d1.class, array, l(true), a("0"));
    test(Array1d1.class, array, l(true, null), a("0", "0"));
    test(Array1d1.class, array, l(true, null, true), a("0", "0", "0"));
    test(Array1d1.class, array, l(true, null, true, null), a("0", "0", "0", "0"));
    test(Array1d1.class, array, l(true, null, true, null, true), "Invalid content was found starting with member index=4: @" + BooleanElement.class.getName() + "(id=0, maxOccurs=2): No members are expected at this point: true");
    test(Array1d1.class, array, l(true, null, true, true, null, true), "Invalid content was found starting with member index=4: @" + BooleanElement.class.getName() + "(id=0, maxOccurs=2): No members are expected at this point: null");
  }

  @Test
  public void testArray1d2() throws DecodeException, IOException {
    final TestArray array = new TestArray();

    test(Array1d2.class, array, l(), "Invalid content was found in empty array: @" + BooleanElement.class.getName() + "(id=1, maxOccurs=1, nullable=false): Content is not complete");
    test(Array1d2.class, array, l(true), "Invalid content was found starting with member index=0: @" + BooleanElement.class.getName() + "(id=1, maxOccurs=1, nullable=false): Content is not complete");
    test(Array1d2.class, array, l(true, false), "Invalid content was found starting with member index=1: @" + BooleanElement.class.getName() + "(id=1, maxOccurs=1, nullable=false): Content is not complete");
    test(Array1d2.class, array, l(true, false, true), a("1", "1", "1"));
    test(Array1d2.class, array, l(true, false, null), "Invalid content was found starting with member index=2: @" + BooleanElement.class.getName() + "(id=1, maxOccurs=1, nullable=false): Illegal value: null");
    test(Array1d2.class, array, l(true, null, false), "Invalid content was found starting with member index=2: @" + BooleanElement.class.getName() + "(id=0, minOccurs=0, maxOccurs=2): Content is not complete");
    test(Array1d2.class, array, l(null, false, true), "Invalid content was found starting with member index=2: @" + BooleanElement.class.getName() + "(id=0, minOccurs=0, maxOccurs=2): Content is not complete");
    test(Array1d2.class, array, l(true, false, null, true), a("1", "1", "0", "1"));
    test(Array1d2.class, array, l(true, null, false, true), a("1", "0", "1", "1"));
    test(Array1d2.class, array, l(null, true, false, true), a("0", "1", "1", "1"));
    test(Array1d2.class, array, l(true, false, true, false), a("0", "1", "1", "1"));
    test(Array1d2.class, array, l(true, false, true, null), "Invalid content was found starting with member index=3: @" + BooleanElement.class.getName() + "(id=1, maxOccurs=1, nullable=false): Content is not complete");
    test(Array1d2.class, array, l(null, null, null, true, false, true), "Invalid content was found starting with member index=2: @" + BooleanElement.class.getName() + "(id=1, maxOccurs=1, nullable=false): Illegal value: null");
    test(Array1d2.class, array, l(null, true, null, false, true), a("0", "1", "0", "1", "1"));
    test(Array1d2.class, array, l(null, true, null, false, null, true), a("0", "1", "0", "1", "0", "1"));
    test(Array1d2.class, array, l(null, true, null, false, null, null, true), a("0", "1", "0", "1", "0", "0", "1"));
    test(Array1d2.class, array, l(null, true, null, null, false, null, true), a("0", "1", "0", "0", "1", "0", "1"));
    test(Array1d2.class, array, l(null, true, null, null, false, null, null, true), a("0", "1", "0", "0", "1", "0", "0", "1"));
    test(Array1d2.class, array, l(null, null, true, null, null, false, null, null, true), a("0", "0", "1", "0", "0", "1", "0", "0", "1"));
    test(Array1d2.class, array, l(null, null, true, null, null, false, null, null, true, null), "Invalid content was found starting with member index=9: @" + BooleanElement.class.getName() + "(id=1, maxOccurs=1, nullable=false): Content is not complete");
    test(Array1d2.class, array, l(null, null, true, null, null, false, null, null, true, false), a("0", "0", "1", "0", "0", "1", "0", "0", "1", "1"));
  }

  @Test
  public void testArray1d3() throws DecodeException, IOException {
    final TestArray array = new TestArray();

    test(Array1d3.class, array, l(), a());

    test(Array1d3.class, array, l(null, "abc", null), a("1", "1", "4"));
    test(Array1d3.class, array, l(null, "abc", null, null, "abc", null), a("0", "1", "1", "2", "2", "4"));

    test(Array1d3.class, array, l(null, "abc", 4), a("1", "1", "4"));
    test(Array1d3.class, array, l(null, "abc", 4, null, "abc", null), a("1", "1", "4", "1", "1", "4"));

    test(Array1d3.class, array, l(null, "abc", BigInteger.ONE), a("1", "1", "4"));
    test(Array1d3.class, array, l(null, "abc", BigInteger.ONE, null, "abc", BigInteger.ONE), a("1", "1", "4", "1", "1", "4"));

    test(Array1d3.class, array, l(true, null, "abc", BigDecimals.TWO), a("0", "1", "1", "4"));
    test(Array1d3.class, array, l(true, null, "abc", BigDecimals.TWO, true, null, "abc", BigDecimals.TWO), a("0", "1", "1", "4", "0", "1", "1", "4"));

    test(Array1d3.class, array, l(true, "abc", null, BigInteger.ONE), a("0", "1", "1", "4"));
    test(Array1d3.class, array, l(true, "abc", null, BigInteger.ONE, true, "abc", null, BigInteger.ONE), a("0", "1", "1", "4", "0", "1", "1", "4"));

    test(Array1d3.class, array, l(true, null, "abc", "abc", BigDecimals.TWO), a("0", "0", "1", "1", "4"));
    test(Array1d3.class, array, l(true, null, "abc", "abc", BigDecimals.TWO, true, null, "abc", "abc", BigDecimals.TWO), a("0", "0", "1", "1", "4", "0", "0", "1", "1", "4"));

    test(Array1d3.class, array, l(true, null, "abc", "123", BigDecimals.TWO), a("0", "1", "1", "2", "4"));
    test(Array1d3.class, array, l(true, null, "abc", "123", BigDecimals.TWO, true, null, "abc", "123", BigDecimals.TWO), a("0", "1", "1", "2", "4", "0", "1", "1", "2", "4"));

    test(Array1d3.class, array, l(null, null, null), a("1", "1", "4"));
    test(Array1d3.class, array, l(null, null, null, null), a("0", "1", "1", "4"));
    test(Array1d3.class, array, l(null, null, null, null, null), a("0", "0", "1", "1", "4"));
    test(Array1d3.class, array, l(null, null, null, null, null, null), a("0", "0", "1", "1", "2", "4"));
    test(Array1d3.class, array, l(null, null, null, null, null, null, null), a("0", "0", "1", "1", "2", "4", "4"));
    test(Array1d3.class, array, l(null, null, null, null, null, null, null, null), a("0", "0", "1", "1", "2", "2", "4", "4"));
    test(Array1d3.class, array, l(null, null, null, null, null, null, null, null, null), a("0", "0", "1", "1", "2", "4", "1", "1", "4"));
    test(Array1d3.class, array, l(null, null, null, null, null, null, null, null, null, null), a("0", "0", "1", "1", "2", "4", "0", "1", "1", "4"));
    test(Array1d3.class, array, l(null, null, null, null, null, null, null, null, null, null, null), a("0", "0", "1", "1", "2", "4", "0", "0", "1", "1", "4"));
    test(Array1d3.class, array, l(null, null, null, null, null, null, null, null, null, null, null, null), a("0", "0", "1", "1", "2", "4", "0", "0", "1", "1", "2", "4"));
    test(Array1d3.class, array, l(null, null, null, null, null, null, null, null, null, null, null, null, null), a("0", "0", "1", "1", "2", "4", "0", "0", "1", "1", "2", "4", "4"));
    test(Array1d3.class, array, l(null, null, null, null, null, null, null, null, null, null, null, null, null, null), a("0", "0", "1", "1", "2", "4", "0", "0", "1", "1", "2", "2", "4", "4"));
    test(Array1d3.class, array, l(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), a("0", "0", "1", "1", "2", "4", "0", "0", "1", "1", "2", "4", "1", "1", "4"));
    test(Array1d3.class, array, l(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), a("0", "0", "1", "1", "2", "4", "0", "0", "1", "1", "2", "4", "0", "1", "1", "4"));
    test(Array1d3.class, array, l(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), a("0", "0", "1", "1", "2", "4", "0", "0", "1", "1", "2", "4", "0", "0", "1", "1", "4"));
    test(Array1d3.class, array, l(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), a("0", "0", "1", "1", "2", "4", "0", "0", "1", "1", "2", "4", "0", "0", "1", "1", "2", "4"));
    test(Array1d3.class, array, l(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), a("0", "0", "1", "1", "2", "4", "0", "0", "1", "1", "2", "4", "0", "0", "1", "1", "2", "4", "4"));
    test(Array1d3.class, array, l(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null), a("0", "0", "1", "1", "2", "4", "0", "0", "1", "1", "2", "4", "0", "0", "1", "1", "2", "2", "4", "4"));
    test(Array1d3.class, array, l(null, null, BigDecimals.TWO), a("1", "1", "4"));
    test(Array1d3.class, array, l(null, null, BigDecimal.TEN), a("1", "1", "4"));
    test(Array1d3.class, array, l(null, null, null, BigDecimals.TWO), a("0", "1", "1", "4"));
    test(Array1d3.class, array, l(null, null, null, BigDecimals.TWO, BigDecimal.TEN), a("0", "1", "1", "3", "4"));
    test(Array1d3.class, array, l(null, null, null, BigDecimals.PI, BigInteger.ONE), a("0", "1", "1", "4", "4"));
    test(Array1d3.class, array, l(null, null, null, null, BigDecimals.TWO), a("0", "0", "1", "1", "4"));
    test(Array1d3.class, array, l(null, null, null, null, null, BigDecimals.TWO), a("0", "0", "1", "1", "2", "4"));
    test(Array1d3.class, array, l(null, null, null, BigDecimals.PI, BigInteger.ONE), a("0", "1", "1", "4", "4"));
    test(Array1d3.class, array, l(null, "abc", null, "abc", BigDecimals.PI, BigInteger.ONE), a("0", "1", "1", "2", "4", "4"));
    test(Array1d3.class, array, l(null, "abc", null, "123", BigDecimals.PI, BigInteger.ONE), a("0", "1", "1", "2", "4", "4"));
    test(Array1d3.class, array, l(null, null, "abc", "abc", BigDecimals.PI, BigInteger.ONE), a("0", "0", "1", "1", "4", "4"));
    test(Array1d3.class, array, l(null, null, "123", "abc", BigDecimals.PI, BigInteger.ONE), a("1", "1", "2", "2", "4", "4"));
    test(Array1d3.class, array, l(null, BigDecimals.TWO), "Invalid content was found starting with member index=1: @" + BooleanElement.class.getName() + "(id=0, minOccurs=0, maxOccurs=3): Content is not expected: 2");
    test(Array1d3.class, array, l(null, "abc", BigInteger.valueOf(5), null, BigDecimals.TWO), "Invalid content was found starting with member index=1: @" + BooleanElement.class.getName() + "(id=0, minOccurs=0, maxOccurs=3): Content is not expected: abc");
    test(Array1d3.class, array, l(true, "abc", true), "Invalid content was found starting with member index=1: @" + BooleanElement.class.getName() + "(id=0, minOccurs=0, maxOccurs=3): Content is not expected: abc");
    test(Array1d3.class, array, l(true, "abc", "abc"), "Invalid content was found starting with member index=1: @" + BooleanElement.class.getName() + "(id=0, minOccurs=0, maxOccurs=3): Content is not expected: abc");
    test(Array1d3.class, array, l(true, "abc", "abc", BigDecimals.PI, BigDecimal.TEN, null), "Invalid content was found starting with member index=1: @" + BooleanElement.class.getName() + "(id=0, minOccurs=0, maxOccurs=3): Content is not expected: abc");
    test(Array1d3.class, array, l(null, "111", null), "Invalid content was found starting with member index=1: @" + BooleanElement.class.getName() + "(id=0, minOccurs=0, maxOccurs=3): Content is not expected: 111");
    test(Array1d3.class, array, l(true, true, true, true), "Invalid content was found starting with member index=3: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", minOccurs=2, maxOccurs=3): Content is not expected: true");
    test(Array1d3.class, array, l(null, "abc", "111", "abc"), "Invalid content was found starting with member index=1: @" + BooleanElement.class.getName() + "(id=0, minOccurs=0, maxOccurs=3): Content is not expected: abc");
  }

  @Test
  public void testArray2d1() throws DecodeException, IOException {
    final TestArray array = new TestArray();

    test(Array2d1.class, array, l(l()), a("0"));
    test(Array2d1.class, array, l(l(), l()), a("0", "0"));
    test(Array2d1.class, array, l(l(), l(), l()), "Invalid content was found starting with member index=2: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, maxOccurs=1, nullable=false): No members are expected at this point: []");
    test(Array2d1.class, array, l(l(null, "abc", null)), a("0", "0.1", "0.1", "0.4"));
    test(Array2d1.class, array, l(l(null, "abc", null), l(null, "abc", null)), a("0", "0.1", "0.1", "0.4", "0", "0.1", "0.1", "0.4"));
    test(Array2d1.class, array, l(l(null, "abc", null), l(null, "abc", null), l(null, "abc", null)), "Invalid content was found starting with member index=2: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, maxOccurs=1, nullable=false): No members are expected at this point: [null, abc, n...");
    test(Array2d1.class, array, l(l(null, "abc", BigInteger.ONE)), a("0", "0.1", "0.1", "0.4"));
    test(Array2d1.class, array, l(l(null, "abc", BigInteger.ONE), l(null, "abc", BigInteger.ONE)), a("0", "0.1", "0.1", "0.4", "0", "0.1", "0.1", "0.4"));
    test(Array2d1.class, array, l(l(null, "abc", BigInteger.ONE), l(null, "abc", BigInteger.ONE), l(null, "abc", BigInteger.ONE)), "Invalid content was found starting with member index=2: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, maxOccurs=1, nullable=false): No members are expected at this point: [null, abc, 1]");
    test(Array2d1.class, array, l(l(true, "abc", null, BigInteger.ONE), l(true, "abc", null, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.4", "0", "0.0", "0.1", "0.1", "0.4"));
    test(Array2d1.class, array, l(l(true, "abc", null, BigInteger.ONE), l(true, "abc", null, BigInteger.ONE), l(true, "abc", null, BigInteger.ONE)), "Invalid content was found starting with member index=2: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, maxOccurs=1, nullable=false): No members are expected at this point: [true, abc, n...");
    test(Array2d1.class, array, l(l(true, null, "abc", BigDecimals.TWO)), a("0", "0.0", "0.1", "0.1", "0.4"));
    test(Array2d1.class, array, l(l(true, null, "abc", BigDecimals.TWO), l(true, null, "abc", BigDecimals.TWO)), a("0", "0.0", "0.1", "0.1", "0.4", "0", "0.0", "0.1", "0.1", "0.4"));
    test(Array2d1.class, array, l(l(true, null, "abc", BigDecimals.TWO), l(true, null, "abc", BigDecimals.TWO), l(true, null, "abc", BigDecimals.TWO)), "Invalid content was found starting with member index=2: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, maxOccurs=1, nullable=false): No members are expected at this point: [true, null, ...");
    test(Array2d1.class, array, l(l(true, null, "abc", "abc", BigDecimals.TWO)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4"));
    test(Array2d1.class, array, l(l(true, null, "abc", "abc", BigDecimals.TWO), l(true, null, "abc", "abc", BigDecimals.TWO)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "0", "0.0", "0.0", "0.1", "0.1", "0.4"));
    test(Array2d1.class, array, l(l(true, null, "abc", "abc", BigDecimals.TWO), l(true, null, "abc", "abc", BigDecimals.TWO), l(true, null, "abc", "abc", BigDecimals.TWO)), "Invalid content was found starting with member index=2: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, maxOccurs=1, nullable=false): No members are expected at this point: [true, null, ...");
    test(Array2d1.class, array, l(l(true, null, "abc", "123", BigDecimals.TWO)), a("0", "0.0", "0.1", "0.1", "0.2", "0.4"));
    test(Array2d1.class, array, l(l(true, null, "abc", "123", BigDecimals.TWO), l(true, null, "abc", "123", BigDecimals.TWO)), a("0", "0.0", "0.1", "0.1", "0.2", "0.4", "0", "0.0", "0.1", "0.1", "0.2", "0.4"));
    test(Array2d1.class, array, l(l(true, null, "abc", "123", BigDecimals.TWO), l(true, null, "abc", "123", BigDecimals.TWO), l(true, null, "abc", "123", BigDecimals.TWO)), "Invalid content was found starting with member index=2: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, maxOccurs=1, nullable=false): No members are expected at this point: [true, null, ...");
    test(Array2d1.class, array, l(l(null, null, null, null, null)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4"));
    test(Array2d1.class, array, l(l(null, null, null, null, null), l(null, null, null, null, null)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "0", "0.0", "0.0", "0.1", "0.1", "0.4"));
    test(Array2d1.class, array, l(l(null, null, null, null, null), l(null, null, null, null, null), l(null, null, null, null, null)), "Invalid content was found starting with member index=2: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, maxOccurs=1, nullable=false): No members are expected at this point: [null, null, ...");
    test(Array2d1.class, array, l(l(null, null, null, null)), a("0", "0.0", "0.1", "0.1", "0.4"));
    test(Array2d1.class, array, l(l(null, null, null, null), l(null, null, null, null)), a("0", "0.0", "0.1", "0.1", "0.4", "0", "0.0", "0.1", "0.1", "0.4"));
    test(Array2d1.class, array, l(l(null, null, null, null), l(null, null, null, null), l(null, null, null, null)), "Invalid content was found starting with member index=2: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, maxOccurs=1, nullable=false): No members are expected at this point: [null, null, ...");
    test(Array2d1.class, array, l(l(null, null, null)), a("0", "0.1", "0.1", "0.4"));
    test(Array2d1.class, array, l(l(null, null, null), l(null, null, null)), a("0", "0.1", "0.1", "0.4", "0", "0.1", "0.1", "0.4"));
    test(Array2d1.class, array, l(l(null, null, null), l(null, null, null), l(null, null, null)), "Invalid content was found starting with member index=2: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, maxOccurs=1, nullable=false): No members are expected at this point: [null, null, ...");
    test(Array2d1.class, array, l(l(null, null, BigDecimals.TWO)), a("0", "0.1", "0.1", "0.4"));
    test(Array2d1.class, array, l(l(null, null, BigDecimals.TWO), l(null, null, BigDecimals.TWO)), a("0", "0.1", "0.1", "0.4", "0", "0.1", "0.1", "0.4"));
    test(Array2d1.class, array, l(l(null, null, BigDecimals.TWO), l(null, null, BigDecimals.TWO), l(null, null, BigDecimals.TWO)), "Invalid content was found starting with member index=2: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, maxOccurs=1, nullable=false): No members are expected at this point: [null, null, 2]");
    test(Array2d1.class, array, l(l(null, null, BigDecimal.TEN)), a("0", "0.1", "0.1", "0.4"));
    test(Array2d1.class, array, l(l(null, null, BigDecimal.TEN), l(null, null, BigDecimal.TEN)), a("0", "0.1", "0.1", "0.4", "0", "0.1", "0.1", "0.4"));
    test(Array2d1.class, array, l(l(null, null, BigDecimal.TEN), l(null, null, BigDecimal.TEN), l(null, null, BigDecimal.TEN)), "Invalid content was found starting with member index=2: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, maxOccurs=1, nullable=false): No members are expected at this point: [null, null, 10]");
    test(Array2d1.class, array, l(l(null, null, null, BigDecimals.TWO)), a("0", "0.0", "0.1", "0.1", "0.4"));
    test(Array2d1.class, array, l(l(null, null, null, BigDecimals.TWO, BigDecimal.TEN)), a("0", "0.0", "0.1", "0.1", "0.3", "0.4"));
    test(Array2d1.class, array, l(l(null, null, null, BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.4", "0.4"));
    test(Array2d1.class, array, l(l(null, null, null, null, BigDecimals.TWO)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4"));
    test(Array2d1.class, array, l(l(null, null, null, null, null, BigDecimals.TWO)), a("0", "0.0", "0.0", "0.1", "0.1", "0.2", "0.4"));
    test(Array2d1.class, array, l(l(null, null, null, BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.4", "0.4"));
    test(Array2d1.class, array, l(l(null, "abc", null, "abc", BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.2", "0.4", "0.4"));
    test(Array2d1.class, array, l(l(null, "abc", null, "123", BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.2", "0.4", "0.4"));
    test(Array2d1.class, array, l(l(null, null, "abc", "abc", BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "0.4"));
    test(Array2d1.class, array, l(l(null, null, "123", "abc", BigDecimals.PI, BigInteger.ONE)), a("0", "0.1", "0.1", "0.2", "0.2", "0.4", "0.4"));
    test(Array2d1.class, array, l(l(true, "abc", null, BigInteger.ZERO, BigDecimal.valueOf(100))), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, maxOccurs=1, nullable=false): Invalid content was found starting with member index=1: @" + BooleanElement.class.getName() + "(id=0, minOccurs=0, maxOccurs=3): Content is not expected: abc");
    test(Array2d1.class, array, l(l(true, "abc", BigDecimals.PI, BigDecimal.TEN, null)), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, maxOccurs=1, nullable=false): Invalid content was found starting with member index=1: @" + BooleanElement.class.getName() + "(id=0, minOccurs=0, maxOccurs=3): Content is not expected: abc");
    test(Array2d1.class, array, l(l(null, "111", null)), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, maxOccurs=1, nullable=false): Invalid content was found starting with member index=1: @" + BooleanElement.class.getName() + "(id=0, minOccurs=0, maxOccurs=3): Content is not expected: 111");
    test(Array2d1.class, array, l(l("abc")), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, maxOccurs=1, nullable=false): Invalid content was found starting with member index=0: @" + BooleanElement.class.getName() + "(id=0, minOccurs=0, maxOccurs=3): Content is not expected: abc");
    test(Array2d1.class, array, l(l(true)), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, maxOccurs=1, nullable=false): Invalid content was found starting with member index=0: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", minOccurs=2, maxOccurs=3): Content is not complete");
    test(Array2d1.class, array, l(l(null, "abc", "111", "abc")), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, maxOccurs=1, nullable=false): Invalid content was found starting with member index=1: @" + BooleanElement.class.getName() + "(id=0, minOccurs=0, maxOccurs=3): Content is not expected: abc");
    test(Array2d1.class, array, l(l(null, "111", null)), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, maxOccurs=1, nullable=false): Invalid content was found starting with member index=1: @" + BooleanElement.class.getName() + "(id=0, minOccurs=0, maxOccurs=3): Content is not expected: 111");
  }

  @Test
  public void testArray2d2() throws DecodeException, IOException {
    final TestArray array = new TestArray();

    test(Array2d2.class, array, l(l(null, "abc", null)), a("0", "0.1", "0.1", "0.4"));
    test(Array2d2.class, array, l(l(null, "abc", null), l(null, "abc", null)), a("0", "0.1", "0.1", "0.4", "0", "0.1", "0.1", "0.4"));
    test(Array2d2.class, array, l(l(null, "abc", null, null, "abc", null)), a("0", "0.0", "0.1", "0.1", "0.2", "0.2", "0.4"));

    test(Array2d2.class, array, l(l(null, "abc", BigInteger.ONE)), a("0", "0.1", "0.1", "0.4"));
    test(Array2d2.class, array, l(l(null, "abc", BigInteger.ONE), l(null, "abc", BigInteger.ONE)), a("0", "0.1", "0.1", "0.4", "0", "0.1", "0.1", "0.4"));
    test(Array2d2.class, array, l(l(null, "abc", BigInteger.ONE, null, "abc", BigInteger.ONE)), a("0", "0.1", "0.1", "0.4", "0.1", "0.1", "0.4"));

    test(Array2d2.class, array, l(l(true, "abc", null, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.4"));
    test(Array2d2.class, array, l(l(true, "abc", null, BigInteger.ONE), l(true, null, "abc", BigDecimals.TWO)), a("0", "0.0", "0.1", "0.1", "0.4", "0", "0.0", "0.1", "0.1", "0.4"));
    test(Array2d2.class, array, l(l(true, "abc", null, BigInteger.ONE, true, null, "abc", BigDecimals.TWO)), a("0", "0.0", "0.1", "0.1", "0.4", "0.0", "0.1", "0.1", "0.4"));

    test(Array2d2.class, array, l(l(true, null, "abc", "abc", BigDecimals.TWO)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4"));
    test(Array2d2.class, array, l(l(true, null, "abc", "abc", BigDecimals.TWO), l(true, null, "abc", "123", BigDecimals.TWO)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "0", "0.0", "0.1", "0.1", "0.2", "0.4"));
    test(Array2d2.class, array, l(l(true, null, "abc", "abc", BigDecimals.TWO, true, null, "abc", "123", BigDecimals.TWO)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "0.0", "0.1", "0.1", "0.2", "0.4"));

    test(Array2d2.class, array, l(l(null, null, null, null, null)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4"));
    test(Array2d2.class, array, l(l(null, null, null, null, null), l(null, null, null, null)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "0", "0.0", "0.1", "0.1", "0.4"));
    test(Array2d2.class, array, l(l(null, null, null, null, null, null, null, null, null)), a("0", "0.0", "0.0", "0.1", "0.1", "0.2", "0.4", "0.1", "0.1", "0.4"));

    test(Array2d2.class, array, l(l(null, null, null), l(null, null, BigDecimals.TWO)), a("0", "0.1", "0.1", "0.4", "0", "0.1", "0.1", "0.4"));
    test(Array2d2.class, array, l(l(null, null, BigDecimal.TEN), l(null, null, null, BigDecimals.TWO)), a("0", "0.1", "0.1", "0.4", "0", "0.0", "0.1", "0.1", "0.4"));
    test(Array2d2.class, array, l(l(null, null, BigDecimal.TEN, null, null, null, BigDecimals.TWO)), a("0", "0.1", "0.1", "0.4", "0.0", "0.1", "0.1", "0.4"));

    test(Array2d2.class, array, l(l(null, null, null, BigDecimals.TWO, BigDecimal.TEN)), a("0", "0.0", "0.1", "0.1", "0.3", "0.4"));
    test(Array2d2.class, array, l(l(null, null, null, BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.4", "0.4"));
    test(Array2d2.class, array, l(l(null, null, null, BigDecimals.TWO, BigDecimal.TEN), l(null, null, null, BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.3", "0.4", "0", "0.0", "0.1", "0.1", "0.4", "0.4"));
    test(Array2d2.class, array, l(l(null, null, null, BigDecimals.TWO, BigDecimal.TEN, null, null, null, BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.3", "0.4", "0.0", "0.1", "0.1", "0.4", "0.4"));

    test(Array2d2.class, array, l(l(null, null, null, null, BigDecimals.TWO)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4"));
    test(Array2d2.class, array, l(l(null, null, null, null, null, BigDecimals.TWO)), a("0", "0.0", "0.0", "0.1", "0.1", "0.2", "0.4"));
    test(Array2d2.class, array, l(l(null, null, null, null, BigDecimals.TWO), l(null, null, null, null, null, BigDecimals.TWO)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "0", "0.0", "0.0", "0.1", "0.1", "0.2", "0.4"));
    test(Array2d2.class, array, l(l(null, null, null, null, BigDecimals.TWO, null, null, null, null, null, BigDecimals.TWO)), a("0", "0.0", "0.0", "0.1", "0.1", "0.3", "0.4", "0.0", "0.0", "0.1", "0.1", "0.4"));

    test(Array2d2.class, array, l(l(null, "abc", null, "abc", BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.2", "0.4", "0.4"));
    test(Array2d2.class, array, l(l(null, "abc", null, "123", BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.2", "0.4", "0.4"));
    test(Array2d2.class, array, l(l(null, "abc", null, "abc", BigDecimals.PI, BigInteger.ONE), l(null, "abc", null, "123", BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.2", "0.4", "0.4", "0", "0.0", "0.1", "0.1", "0.2", "0.4", "0.4"));
    test(Array2d2.class, array, l(l(null, "abc", null, "abc", BigDecimals.PI, BigInteger.ONE, null, "abc", null, "123", BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.2", "0.4", "0.4", "0.0", "0.1", "0.1", "0.2", "0.4", "0.4"));

    test(Array2d2.class, array, l(l(null, null, "abc", "abc", BigDecimals.PI, BigIntegers.TWO)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "0.4"));
    test(Array2d2.class, array, l(l(null, null, "abc", "abc", BigDecimals.PI, BigIntegers.TWO), l(null, null, "123", "abc", BigDecimals.PI, BigIntegers.TWO)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "0.4", "0", "0.1", "0.1", "0.2", "0.2", "0.4", "0.4"));
    test(Array2d2.class, array, l(l(null, null, "abc", "abc", BigDecimals.PI, BigIntegers.TWO, null, null, "123", "abc", BigDecimals.PI, BigIntegers.TWO)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "0.4", "0.1", "0.1", "0.2", "0.2", "0.4", "0.4"));

    test(Array2d2.class, array, l(l(null, "ABC", null)), a("2", "4", "4", "7"));
    test(Array2d2.class, array, l(l(null, "ABC", null), l(null, "ABC", null)), a("2", "4", "4", "7", "2", "4", "4", "7"));
    test(Array2d2.class, array, l(l(null, "ABC", null, null, "ABC", null)), a("2", "3", "4", "4", "5", "5", "7"));

    test(Array2d2.class, array, l(l(null, "ABC", BigInteger.ONE)), a("2", "4", "4", "7"));
    test(Array2d2.class, array, l(l(null, "ABC", BigInteger.ONE), l(null, "ABC", BigInteger.ONE)), a("2", "4", "4", "7", "2", "4", "4", "7"));
    test(Array2d2.class, array, l(l(null, "ABC", BigInteger.ONE, null, "ABC", BigInteger.ONE)), a("2", "4", "4", "7", "4", "4", "7"));

    test(Array2d2.class, array, l(l(true, "ABC", null, BigInteger.ONE)), a("2", "3", "4", "4", "7"));
    test(Array2d2.class, array, l(l(true, "ABC", null, BigInteger.ONE), l(true, "ABC", null, BigInteger.ONE)), a("2", "3", "4", "4", "7", "2", "3", "4", "4", "7"));
    test(Array2d2.class, array, l(l(true, "ABC", null, BigInteger.ONE, true, "ABC", null, BigInteger.ONE)), a("2", "3", "4", "4", "7", "3", "4", "4", "7"));

    test(Array2d2.class, array, l(l(true, null, "ABC", BigDecimals.TWO)), a("2", "3", "4", "4", "7"));
    test(Array2d2.class, array, l(l(true, null, "ABC", BigDecimals.TWO), l(true, null, "ABC", BigDecimals.TWO)), a("2", "3", "4", "4", "7", "2", "3", "4", "4", "7"));
    test(Array2d2.class, array, l(l(true, null, "ABC", BigDecimals.TWO, true, null, "ABC", BigDecimals.TWO)), a("2", "3", "4", "4", "7", "3", "4", "4", "7"));

    test(Array2d2.class, array, l(l(true, null, "ABC", "ABC", BigDecimals.TWO)), a("2", "3", "3", "4", "4", "7"));
    test(Array2d2.class, array, l(l(true, null, "ABC", "ABC", BigDecimals.TWO), l(true, null, "ABC", "ABC", BigDecimals.TWO)), a("2", "3", "3", "4", "4", "7", "2", "3", "3", "4", "4", "7"));
    test(Array2d2.class, array, l(l(true, null, "ABC", "ABC", BigDecimals.TWO, true, null, "ABC", "ABC", BigDecimals.TWO)), a("2", "3", "3", "4", "4", "7", "3", "3", "4", "4", "7"));

    test(Array2d2.class, array, l(l(true, null, "ABC", "123", BigDecimals.TWO)), a("2", "3", "4", "4", "5", "7"));
    test(Array2d2.class, array, l(l(true, null, "ABC", "123", BigDecimals.TWO), l(true, null, "ABC", "123", BigDecimals.TWO)), a("2", "3", "4", "4", "5", "7", "2", "3", "4", "4", "5", "7"));
    test(Array2d2.class, array, l(l(true, null, "ABC", "123", BigDecimals.TWO, true, null, "ABC", "123", BigDecimals.TWO)), a("2", "3", "4", "4", "5", "7", "3", "4", "4", "5", "7"));

    test(Array2d2.class, array, l(l(null, "ABC", null, "ABC", BigDecimals.PI, BigInteger.ONE)), a("2", "3", "4", "4", "5", "7", "7"));
    test(Array2d2.class, array, l(l(null, "ABC", null, "ABC", BigDecimals.PI, BigInteger.ONE), l(null, "ABC", null, "ABC", BigDecimals.PI, BigInteger.ONE)), a("2", "3", "4", "4", "5", "7", "7", "2", "3", "4", "4", "5", "7", "7"));
    test(Array2d2.class, array, l(l(null, "ABC", null, "ABC", BigDecimals.PI, BigInteger.ONE, null, "ABC", null, "ABC", BigDecimals.PI, BigInteger.ONE)), a("2", "3", "4", "4", "5", "7", "7", "3", "4", "4", "5", "7", "7"));

    test(Array2d2.class, array, l(l(null, "ABC", null, "123", BigDecimals.PI, BigInteger.ONE)), a("2", "3", "4", "4", "5", "7", "7"));
    test(Array2d2.class, array, l(l(null, "ABC", null, "123", BigDecimals.PI, BigInteger.ONE), l(null, "ABC", null, "123", BigDecimals.PI, BigInteger.ONE)), a("2", "3", "4", "4", "5", "7", "7", "2", "3", "4", "4", "5", "7", "7"));
    test(Array2d2.class, array, l(l(null, "ABC", null, "123", BigDecimals.PI, BigInteger.ONE, null, "ABC", null, "123", BigDecimals.PI, BigInteger.ONE)), a("2", "3", "4", "4", "5", "7", "7", "3", "4", "4", "5", "7", "7"));

    test(Array2d2.class, array, l(l(null, null, "ABC", "ABC", BigDecimals.PI, BigInteger.ONE)), a("2", "3", "3", "4", "4", "7", "7"));
    test(Array2d2.class, array, l(l(null, null, "ABC", "ABC", BigDecimals.PI, BigInteger.ONE), l(null, null, "ABC", "ABC", BigDecimals.PI, BigInteger.ONE)), a("2", "3", "3", "4", "4", "7", "7", "2", "3", "3", "4", "4", "7", "7"));
    test(Array2d2.class, array, l(l(null, null, "ABC", "ABC", BigDecimals.PI, BigInteger.ONE, null, null, "ABC", "ABC", BigDecimals.PI, BigInteger.ONE)), a("2", "3", "3", "4", "4", "7", "7", "3", "3", "4", "4", "7", "7"));

    test(Array2d2.class, array, l(l(null, null, "123", "ABC", BigDecimals.PI, BigInteger.ONE)), a("2", "4", "4", "5", "5", "7", "7"));
    test(Array2d2.class, array, l(l(null, null, "123", "ABC", BigDecimals.PI, BigInteger.ONE), l(null, null, "123", "ABC", BigDecimals.PI, BigInteger.ONE)), a("2", "4", "4", "5", "5", "7", "7", "2", "4", "4", "5", "5", "7", "7"));

    test(Array2d2.class, array, l(l(null, "abc", null), l(null, "ABC", null)), a("0", "0.1", "0.1", "0.4", "2", "4", "4", "7"));
    test(Array2d2.class, array, l(l(null, "abc", null), null, l(null, "ABC", null)), a("0", "0.1", "0.1", "0.4", "1", "2", "4", "4", "7"));
    test(Array2d2.class, array, l(l(null, "abc", null), null, l(null, "ABC", null), BigDecimal.TEN), a("0", "0.1", "0.1", "0.4", "1", "2", "4", "4", "7", "8"));
    test(Array2d2.class, array, l(l(null, "abc", null), null, l(null, "ABC", null), BigDecimal.ONE), a("0", "0.1", "0.1", "0.4", "1", "2", "4", "4", "7", "9"));

    test(Array2d2.class, array, l(true, l(), BigInteger.ZERO, BigInteger.ONE, BigIntegers.TWO), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, minOccurs=0, nullable=false): Content is not expected: true");
    test(Array2d2.class, array, l(true, l(null, "abc"), "abc", BigInteger.ZERO, BigInteger.ONE), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, minOccurs=0, nullable=false): Content is not expected: true");
    test(Array2d2.class, array, l(true, l(null, "ABC", "ABC", BigInteger.TEN), "abc", BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, minOccurs=0, nullable=false): Content is not expected: true");
    test(Array2d2.class, array, l(true, l(null, "ABC", "ABC", BigInteger.TEN), BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, minOccurs=0, nullable=false): Content is not expected: true");
    test(Array2d2.class, array, l(l(null, "abc", "abc", BigInteger.TEN), false, l(BigInteger.ZERO, "123")), "Invalid content was found starting with member index=1: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, minOccurs=0, nullable=false): Content is not expected: false");
    test(Array2d2.class, array, l(l(null, "abc", null, BigDecimal.TEN), false, l("ABC", "ABC", null, BigDecimals.PI, "abc")), "Invalid content was found starting with member index=1: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, minOccurs=0, nullable=false): Content is not expected: false");

    test(Array2d2.class, array, l(l(null, "abc", null, BigDecimal.TEN), false, l("ABC", "ABC", null, BigDecimal.ZERO, null, null, "abc")), "Invalid content was found starting with member index=1: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, minOccurs=0, nullable=false): Content is not expected: false");
  }

  @Test
  public void testArray3d() throws DecodeException, IOException {
    final TestArray array = new TestArray();

    test(Array3d.class, array, l(l(), l(), l(l()), l()), a("0", "0", "1", "1.0", "1"));

    test(Array3d.class, array, l(l(null, "abc", null), l(l(null, "abc", null))), a("0", "0.1", "0.1", "0.4", "1", "1.0", "1.0.1", "1.0.1", "1.0.4"));

    test(Array3d.class, array, l(l(null, "abc", BigInteger.ONE), l(l(null, "abc", BigInteger.ONE))), a("0", "0.1", "0.1", "0.4", "1", "1.0", "1.0.1", "1.0.1", "1.0.4"));
    test(Array3d.class, array, l(l(null, "abc", BigInteger.ONE), l(null, "abc", BigInteger.ONE), l(l(null, "abc", BigInteger.ONE), l(null, "abc", BigInteger.ONE))), a("0", "0.1", "0.1", "0.4", "0", "0.1", "0.1", "0.4", "1", "1.0", "1.0.1", "1.0.1", "1.0.4", "1.0", "1.0.1", "1.0.1", "1.0.4"));
    test(Array3d.class, array, l(l(null, "abc", BigInteger.ONE, null, "abc", BigInteger.ONE), l(l(null, "abc", BigInteger.ONE, null, "abc", BigInteger.ONE))), a("0", "0.1", "0.1", "0.4", "0.1", "0.1", "0.4", "1", "1.0", "1.0.1", "1.0.1", "1.0.4", "1.0.1", "1.0.1", "1.0.4"));

    test(Array3d.class, array, l(l(true, "abc", null, BigInteger.ONE), l(l(true, "abc", null, BigInteger.ONE))), a("0", "0.0", "0.1", "0.1", "0.4", "1", "1.0", "1.0.0", "1.0.1", "1.0.1", "1.0.4"));
    test(Array3d.class, array, l(l(true, null, "abc", BigDecimals.TWO), l(l(true, null, "abc", BigDecimals.TWO))), a("0", "0.0", "0.1", "0.1", "0.4", "1", "1.0", "1.0.0", "1.0.1", "1.0.1", "1.0.4"));
    test(Array3d.class, array, l(l(true, null, "abc", "abc", BigDecimals.TWO), l(l(true, null, "abc", "abc", BigDecimals.TWO))), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "1", "1.0", "1.0.0", "1.0.0", "1.0.1", "1.0.1", "1.0.4"));
    test(Array3d.class, array, l(l(true, null, "abc", "123", BigDecimals.TWO), l(l(true, null, "abc", "123", BigDecimals.TWO))), a("0", "0.0", "0.1", "0.1", "0.2", "0.4", "1", "1.0", "1.0.0", "1.0.1", "1.0.1", "1.0.2", "1.0.4"));
    test(Array3d.class, array, l(l(null, null, null, null, null), l(l(null, null, null, null, null))), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "1", "1.0", "0.0", "0.0", "0.1", "0.1", "0.4"));
    test(Array3d.class, array, l(l(null, null, null, null), l(l(null, null, null, null))), a("0", "0.0", "0.1", "0.1", "0.4", "1", "1.0", "0.0", "0.1", "0.1", "0.4"));
    test(Array3d.class, array, l(l(null, null, null), l(l(null, null, null))), a("0", "0.1", "0.1", "0.4", "1", "1.0", "0.1", "0.1", "0.4"));
    test(Array3d.class, array, l(l(null, null, BigDecimals.TWO), l(l(null, null, BigDecimals.TWO))), a("0", "0.1", "0.1", "0.4", "1", "1.0", "0.1", "0.1", "0.4"));
    test(Array3d.class, array, l(l(null, null, BigDecimal.TEN), l(l(null, null, BigDecimal.TEN))), a("0", "0.1", "0.1", "0.4", "1", "1.0", "0.1", "0.1", "0.4"));
    test(Array3d.class, array, l(l(null, null, null, BigDecimals.TWO), l(l(null, null, null, BigDecimals.TWO))), a("0", "0.0", "0.1", "0.1", "0.4", "1", "1.0", "0.0", "0.1", "0.1", "0.4"));
    test(Array3d.class, array, l(l(null, null, null, BigDecimals.TWO, BigDecimal.TEN), l(l(null, null, null, BigDecimals.TWO, BigDecimal.TEN))), a("0", "0.0", "0.1", "0.1", "0.3", "0.4", "1", "1.0", "0.0", "0.1", "0.1", "0.3", "0.4"));
    test(Array3d.class, array, l(l(null, null, null, BigDecimals.PI, BigInteger.ONE), l(l(null, null, null, BigDecimals.PI, BigInteger.ONE))), a("0", "0.0", "0.1", "0.1", "0.4", "0.4", "1", "1.0", "0.0", "0.1", "0.1", "0.4", "0.4"));
    test(Array3d.class, array, l(l(null, null, null, null, BigDecimals.TWO), l(l(null, null, null, null, BigDecimals.TWO))), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "1", "1.0", "0.0", "0.0", "0.1", "0.1", "0.4"));
    test(Array3d.class, array, l(l(null, null, null, null, null, BigDecimals.TWO), l(l(null, null, null, null, null, BigDecimals.TWO))), a("0", "0.0", "0.0", "0.1", "0.1", "0.2", "0.4", "1", "1.0", "0.0", "0.0", "0.1", "0.1", "0.2", "0.4"));
    test(Array3d.class, array, l(l(null, null, null, BigDecimals.PI, BigInteger.ONE), l(l(null, null, null, BigDecimals.PI, BigInteger.ONE))), a("0", "0.0", "0.1", "0.1", "0.4", "0.4", "1", "1.0", "0.0", "0.1", "0.1", "0.4", "0.4"));
    test(Array3d.class, array, l(l(null, "abc", null, "abc", BigDecimals.PI, BigInteger.ONE), l(l(null, "abc", null, "abc", BigDecimals.PI, BigInteger.ONE))), a("0", "0.0", "0.1", "0.1", "0.2", "0.4", "0.4", "1", "1.0", "1.0.0", "1.0.1", "1.0.1", "1.0.2", "1.0.4", "1.0.4"));
    test(Array3d.class, array, l(l(null, "abc", null, "123", BigDecimals.PI, BigInteger.ONE), l(l(null, "abc", null, "123", BigDecimals.PI, BigInteger.ONE))), a("0", "0.0", "0.1", "0.1", "0.2", "0.4", "0.4", "1", "1.0", "1.0.0", "1.0.1", "1.0.1", "1.0.2", "1.0.4", "1.0.4"));
    test(Array3d.class, array, l(l(null, null, "abc", "abc", BigDecimals.PI, BigInteger.ONE), l(l(null, null, "abc", "abc", BigDecimals.PI, BigInteger.ONE))), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "0.4", "1", "1.0", "1.0.0", "1.0.0", "1.0.1", "1.0.1", "1.0.4", "1.0.4"));
    test(Array3d.class, array, l(l(null, null, "123", "abc", BigDecimals.PI, BigInteger.ONE), l(l(null, null, "123", "abc", BigDecimals.PI, BigInteger.ONE))), a("0", "0.1", "0.1", "0.2", "0.2", "0.4", "0.4", "1", "1.0", "1.0.1", "1.0.1", "1.0.2", "1.0.2", "1.0.4", "1.0.4"));

    test(Array3d.class, array, l(l(null, "abc", null), l(l(null, "ABC", null))), a("0", "0.1", "0.1", "0.4", "1", "1.2", "1.4", "1.4", "1.7"));
    test(Array3d.class, array, l(l(null, "abc", BigInteger.ONE), l(l(null, "ABC", BigInteger.ONE))), a("0", "0.1", "0.1", "0.4", "1", "1.2", "1.4", "1.4", "1.7"));
    test(Array3d.class, array, l(l(true, "abc", null, BigInteger.ONE), l(l(true, "ABC", null, BigInteger.ONE))), a("0", "0.0", "0.1", "0.1", "0.4", "1", "1.2", "1.3", "1.4", "1.4", "1.7"));
    test(Array3d.class, array, l(l(true, null, "abc", BigDecimals.TWO), l(l(true, null, "ABC", BigDecimals.TWO))), a("0", "0.0", "0.1", "0.1", "0.4", "1", "1.2", "1.3", "1.4", "1.4", "1.7"));
    test(Array3d.class, array, l(l(true, null, "abc", "abc", BigDecimals.TWO), l(l(true, null, "ABC", "ABC", BigDecimals.TWO))), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "1", "1.2", "1.3", "1.3", "1.4", "1.4", "1.7"));
    test(Array3d.class, array, l(l(true, null, "abc", "123", BigDecimals.TWO), l(l(true, null, "ABC", "123", BigDecimals.TWO))), a("0", "0.0", "0.1", "0.1", "0.2", "0.4", "1", "1.2", "1.3", "1.4", "1.4", "1.5", "1.7"));
    test(Array3d.class, array, l(l(null, "abc", null, "abc", BigDecimals.PI, BigInteger.ONE), l(l(null, "ABC", null, "ABC", BigDecimals.PI, BigInteger.ONE))), a("0", "0.0", "0.1", "0.1", "0.2", "0.4", "0.4", "1", "1.2", "1.3", "1.4", "1.4", "1.5", "1.7", "1.7"));
    test(Array3d.class, array, l(l(null, "abc", null, "123", BigDecimals.PI, BigInteger.ONE), l(l(null, "ABC", null, "123", BigDecimals.PI, BigInteger.ONE))), a("0", "0.0", "0.1", "0.1", "0.2", "0.4", "0.4", "1", "1.2", "1.3", "1.4", "1.4", "1.5", "1.7", "1.7"));
    test(Array3d.class, array, l(l(null, null, "abc", "abc", BigDecimals.PI, BigInteger.ONE), l(l(null, null, "ABC", "ABC", BigDecimals.PI, BigInteger.ONE))), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "0.4", "1", "1.2", "1.3", "1.3", "1.4", "1.4", "1.7", "1.7"));
    test(Array3d.class, array, l(l(null, null, "123", "abc", BigDecimals.PI, BigInteger.ONE), l(l(null, null, "123", "ABC", BigDecimals.PI, BigInteger.ONE))), a("0", "0.1", "0.1", "0.2", "0.2", "0.4", "0.4", "1", "1.2", "1.4", "1.4", "1.5", "1.5", "1.7", "1.7"));

    test(Array3d.class, array, l(true), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, minOccurs=0, nullable=false): Content is not expected: true");
    test(Array3d.class, array, l(l(true, "abc", "abc", BigDecimals.PI), l(l(null, "abc"), false, l(BigInteger.ZERO, "abc"))), "Invalid content was found starting with member index=1: @" + ArrayElement.class.getName() + "(id=0, type=" + Array1d3.class.getName() + ".class, elementIds={}, minOccurs=0, nullable=false): Invalid content was found starting with member index=0: @" + BooleanElement.class.getName() + "(id=0, minOccurs=0, maxOccurs=3): Content is not expected: [null, abc]");
  }
}