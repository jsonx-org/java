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

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.openjax.classic.math.BigDecimals;
import org.openjax.classic.math.BigIntegers;
import org.openjax.classic.util.FastCollections;
import org.openjax.classic.util.Strings;
import org.openjax.jsonx.runtime.ArrayValidator.Relation;
import org.openjax.jsonx.runtime.ArrayValidator.Relations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArrayEncodeTest {
  private static final Logger logger = LoggerFactory.getLogger(ArrayEncodeTest.class);

  private static final Map<Class<? extends Annotation>,IdToElement> classToIdToElement = new HashMap<>();
  private static boolean debug = false;

  private static IdToElement getIdToElement(final Class<? extends Annotation> annotationType) {
    IdToElement idToElement = classToIdToElement.get(annotationType);
    if (idToElement == null) {
      idToElement = new IdToElement();
      JxUtil.fillIdToElement(idToElement, annotationType.getAnnotations());
      classToIdToElement.put(annotationType, idToElement);
    }

    return idToElement;
  }

  private static Annotation getAnnotation(final IdToElement idToElement, final List<String> index) {
    final int i = Integer.parseInt(index.remove(0));
    final Annotation annotation = idToElement.get(i);
    if (index.size() > 0) {
      assertEquals(String.valueOf(i), ArrayElement.class, annotation.annotationType());
      return getAnnotation(getIdToElement(((ArrayElement)annotation).type()), index);
    }

    return annotation;
  }

  private static void test(final Class<? extends Annotation> annotationType, final List<Object> members, final String[] expected) {
    final IdToElement idToElement = getIdToElement(annotationType);
    final List<Annotation> annotations = new ArrayList<>();
    for (final String term : expected) {
      final List<String> index = new ArrayList<>(Arrays.asList(term.split("\\.")));
      annotations.add(getAnnotation(idToElement, index));
    }

    final Annotation[] selected = annotations.toArray(new Annotation[annotations.size()]);
    test(selected, annotationType, members, null);
  }

  private static void test(final Class<? extends Annotation> annotationType, final List<Object> members, final String expected) {
    test(null, annotationType, members, expected);
  }

  private static void test(final Annotation[] annotations, final Class<? extends Annotation> annotationType, final List<Object> members, final String expected) {
    final Relations relations = new Relations();
    final String error = ArrayValidator.validate(annotationType, members, relations, true, null);
    final Relations flatRelations = FastCollections.flatten(relations, new Relations(), m -> m.member instanceof Relations ? (Relations)m.member : null, true);
    if (expected != null && !expected.equals(error)) {
      String msg = "\"" + Strings.escapeForJava(error) + "\"";
      msg = msg.replace('$', '.');
      msg = msg.replace(".class", "%class");
      msg = msg.replaceAll("org\\.openjax\\.[.a-zA-Z]+\\.([a-zA-Z0-9]+)", "\" + $1.class.getName() + \"");
      msg = msg.replace("%class", ".class");
      logger.error(msg);
    }

    if (debug)
      assertEquals(expected == null, error == null);
    else
      assertEquals(expected, error);

    if (error == null) {
      final List<Object> flatMembers = FastCollections.flatten(members, new ArrayList<>(), true);
      assertEquals(flatMembers.size(), flatRelations.size());
      assertEquals(flatMembers.toString(), annotations.length, flatMembers.size());
      for (int i = 0; i < annotations.length; i++) {
        final Relation relation = flatRelations.get(i);
        assertEquals(String.valueOf(i), annotations[i], relation.annotation);
        if (relation.member instanceof Relations) {
          assertTrue(flatMembers.get(i) instanceof List);
          assertMembersEqual((List<?>)flatMembers.get(i), (Relations)relation.member);
        }
        else {
          assertEquals(flatMembers.get(i), relation.member);
        }
      }
    }
  }

  private static void assertMembersEqual(final List<?> expected, final Relations actual) {
    assertEquals(expected.size(), actual.size());
    for (int i = 0; i < expected.size(); i++) {
      final Object member = expected.get(i);
      final Relation relation = actual.get(i);
      if (member instanceof List) {
        assertTrue(relation.member instanceof Relations);
        assertMembersEqual((List<?>)member, (Relations)relation.member);
      }
      else {
        assertEquals(member, relation.member);
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
  public void testArrayError() {
    try {
      test(ArrayError1.class, l(BigDecimals.PI), "");
      fail("Expected ValidationException");
    }
    catch (final ValidationException e) {
      assertEquals("Invalid range attribute: @" + NumberElement.class.getName() + "(id=0, form=REAL, range=\"xxxx\", minOccurs=1, maxOccurs=1, nullable=true)", e.getMessage());
    }

    try {
      test(ArrayError2.class, l(null, true), "");
      fail("Expected ValidationException");
    }
    catch (final ValidationException e) {
      assertEquals("minOccurs must be less than or equal to maxOccurs: @" + BooleanElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=0, nullable=false)", e.getMessage());
    }

    try {
      test(ArrayError3.class, l(null, true), "");
      fail("Expected ValidationException");
    }
    catch (final ValidationException e) {
      assertEquals("@<Annotation>(id=-1) not found in annotations array", e.getMessage());
    }

    try {
      test(ArrayError4.class, l(null, true), "");
      fail("Expected ValidationException");
    }
    catch (final ValidationException e) {
      assertEquals("elementIds property cannot be empty: " + ArrayError4.class.getName() + ": @" + ArrayType.class.getName() + "(elementIds={})", e.getMessage());
    }

    try {
      test(ArrayError5.class, l(null, true), "");
      fail("Expected ValidationException");
    }
    catch (final ValidationException e) {
      assertTrue(e.getMessage().startsWith("Duplicate id=0 found in"));
    }
  }

  @Test
  public void testArray1d1() {
    test(TestArray.Array1d1.class, l((Object)null), a("0"));
    test(TestArray.Array1d1.class, l(true), a("0"));
    test(TestArray.Array1d1.class, l(true, null), a("0", "0"));
    test(TestArray.Array1d1.class, l(true, null, true), a("0", "0", "0"));
    test(TestArray.Array1d1.class, l(true, null, true, null), "Invalid content was found starting with member index=3: @" + BooleanElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=3, nullable=true): No members are expected at this point: null");
    test(TestArray.Array1d1.class, l(true, null, true, true, null), "Invalid content was found starting with member index=3: @" + BooleanElement.class.getName() + "(id=0, minOccurs=1, maxOccurs=3, nullable=true): No members are expected at this point: true");
  }

  @Test
  public void testArray1d2() {
    test(TestArray.Array1d2.class, l(null, "abc", null), a("1", "1", "4"));
    test(TestArray.Array1d2.class, l(null, "abc", BigInteger.ONE), a("1", "1", "4"));
    test(TestArray.Array1d2.class, l(true, "abc", null, BigInteger.ONE), a("0", "1", "1", "4"));
    test(TestArray.Array1d2.class, l(true, null, "abc", BigDecimals.TWO), a("0", "1", "1", "4"));
    test(TestArray.Array1d2.class, l(true, null, "abc", "abc", BigDecimals.TWO), a("0", "0", "1", "1", "4"));
    test(TestArray.Array1d2.class, l(true, null, "abc", "123", BigDecimals.TWO), a("0", "1", "1", "2", "4"));
    test(TestArray.Array1d2.class, l(null, null, null, null, null), a("0", "0", "1", "1", "4"));
    test(TestArray.Array1d2.class, l(null, null, null, null), a("0", "1", "1", "4"));
    test(TestArray.Array1d2.class, l(null, null, null), a("1", "1", "4"));
    test(TestArray.Array1d2.class, l(null, null, BigDecimals.TWO), a("1", "1", "4"));
    test(TestArray.Array1d2.class, l(null, null, BigDecimal.TEN), a("1", "1", "4"));
    test(TestArray.Array1d2.class, l(null, null, null, BigDecimals.TWO), a("0", "1", "1", "4"));
    test(TestArray.Array1d2.class, l(null, null, null, BigDecimals.TWO, BigDecimal.TEN), a("0", "1", "1", "3", "4"));
    test(TestArray.Array1d2.class, l(null, null, null, BigDecimals.PI, BigInteger.ONE), a("0", "1", "1", "4", "4"));
    test(TestArray.Array1d2.class, l(null, null, null, null, BigDecimals.TWO), a("0", "0", "1", "1", "4"));
    test(TestArray.Array1d2.class, l(null, null, null, null, null, BigDecimals.TWO), a("0", "0", "0", "1", "1", "4"));
    test(TestArray.Array1d2.class, l(null, null, null, BigDecimals.PI, BigInteger.ONE), a("0", "1", "1", "4", "4"));
    test(TestArray.Array1d2.class, l(null, "abc", null, "abc", BigDecimals.PI, BigInteger.ONE), a("0", "1", "1", "1", "4", "4"));
    test(TestArray.Array1d2.class, l(null, "abc", null, "123", BigDecimals.PI, BigInteger.ONE), a("0", "1", "1", "2", "4", "4"));
    test(TestArray.Array1d2.class, l(null, null, "abc", "abc", BigDecimals.PI, BigInteger.ONE), a("0", "0", "1", "1", "4", "4"));
    test(TestArray.Array1d2.class, l(null, null, "123", "abc", BigDecimals.PI, BigInteger.ONE), a("1", "1", "2", "2", "4", "4"));
    test(TestArray.Array1d2.class, l(null, BigDecimals.TWO), "Invalid content was found starting with member index=1: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", minOccurs=2, maxOccurs=3, nullable=true): Content is not expected: 2");
    test(TestArray.Array1d2.class, l(true, "abc", true), "Invalid content was found starting with member index=2: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", minOccurs=2, maxOccurs=3, nullable=true): Content is not expected: true");
    test(TestArray.Array1d2.class, l(true, "abc", "abc"), "Invalid content was found starting with member index=2: @" + NumberElement.class.getName() + "(id=4, form=REAL, range=\"[0,10]\", minOccurs=1, maxOccurs=2, nullable=true): Content is not complete");
    test(TestArray.Array1d2.class, l(true, "abc", "abc", BigDecimals.PI, BigDecimal.TEN, null), "Invalid content was found starting with member index=3: @" + NumberElement.class.getName() + "(id=3, form=INTEGER, range=\"[0,4]\", minOccurs=0, maxOccurs=2, nullable=false): Illegal Form.INTEGER value: 3.14159265358...");
    test(TestArray.Array1d2.class, l(null, "111", null), "Invalid content was found starting with member index=1: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", minOccurs=2, maxOccurs=3, nullable=true): Pattern is not matched: \"111\"");
    test(TestArray.Array1d2.class, l(true, true, true, true), "Invalid content was found starting with member index=3: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", minOccurs=2, maxOccurs=3, nullable=true): Content is not expected: true");
    test(TestArray.Array1d2.class, l(null, "abc", "111", "abc"), "Invalid content was found starting with member index=2: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", minOccurs=2, maxOccurs=3, nullable=true): Pattern is not matched: \"111\"");
  }

  @Test
  public void testArray2d1() {
    test(TestArray.Array2d1.class, l(l(null, "abc", null)), a("0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d1.class, l(l(null, "abc", BigInteger.ONE)), a("0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d1.class, l(l(true, "abc", null, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d1.class, l(l(true, null, "abc", BigDecimals.TWO)), a("0", "0.0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d1.class, l(l(true, null, "abc", "abc", BigDecimals.TWO)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d1.class, l(l(true, null, "abc", "123", BigDecimals.TWO)), a("0", "0.0", "0.1", "0.1", "0.2", "0.4"));
    test(TestArray.Array2d1.class, l(l(null, null, null, null, null)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d1.class, l(l(null, null, null, null)), a("0", "0.0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d1.class, l(l(null, null, null)), a("0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d1.class, l(l(null, null, BigDecimals.TWO)), a("0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d1.class, l(l(null, null, BigDecimal.TEN)), a("0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d1.class, l(l(null, null, null, BigDecimals.TWO)), a("0", "0.0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d1.class, l(l(null, null, null, BigDecimals.TWO, BigDecimal.TEN)), a("0", "0.0", "0.1", "0.1", "0.3", "0.4"));
    test(TestArray.Array2d1.class, l(l(null, null, null, BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.4", "0.4"));
    test(TestArray.Array2d1.class, l(l(null, null, null, null, BigDecimals.TWO)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d1.class, l(l(null, null, null, null, null, BigDecimals.TWO)), a("0", "0.0", "0.0", "0.0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d1.class, l(l(null, null, null, BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.4", "0.4"));
    test(TestArray.Array2d1.class, l(l(null, "abc", null, "abc", BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.1", "0.4", "0.4"));
    test(TestArray.Array2d1.class, l(l(null, "abc", null, "123", BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.2", "0.4", "0.4"));
    test(TestArray.Array2d1.class, l(l(null, null, "abc", "abc", BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "0.4"));
    test(TestArray.Array2d1.class, l(l(null, null, "123", "abc", BigDecimals.PI, BigInteger.ONE)), a("0", "0.1", "0.1", "0.2", "0.2", "0.4", "0.4"));
    test(TestArray.Array2d1.class, l(l()), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + TestArray.Array1d2.class.getName() + ".class, elementIds={}, minOccurs=1, maxOccurs=2147483647, nullable=false): Invalid content was found in empty array: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", minOccurs=2, maxOccurs=3, nullable=true): Content is not complete");
    test(TestArray.Array2d1.class, l(l(true, "abc", null, BigInteger.ZERO, BigDecimal.valueOf(100))), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + TestArray.Array1d2.class.getName() + ".class, elementIds={}, minOccurs=1, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=4: @" + NumberElement.class.getName() + "(id=3, form=INTEGER, range=\"[0,4]\", minOccurs=0, maxOccurs=2, nullable=false): Range is not matched: 100");
    test(TestArray.Array2d1.class, l(l(true, "abc", BigDecimals.PI, BigDecimal.TEN, null)), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + TestArray.Array1d2.class.getName() + ".class, elementIds={}, minOccurs=1, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=2: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", minOccurs=2, maxOccurs=3, nullable=true): Content is not expected: 3.14159265358...");
    test(TestArray.Array2d1.class, l(l(null, "111", null)), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + TestArray.Array1d2.class.getName() + ".class, elementIds={}, minOccurs=1, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=1: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", minOccurs=2, maxOccurs=3, nullable=true): Pattern is not matched: \"111\"");
    test(TestArray.Array2d1.class, l(l("abc")), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + TestArray.Array1d2.class.getName() + ".class, elementIds={}, minOccurs=1, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=0: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", minOccurs=2, maxOccurs=3, nullable=true): Content is not complete");
    test(TestArray.Array2d1.class, l(l(true)), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + TestArray.Array1d2.class.getName() + ".class, elementIds={}, minOccurs=1, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=0: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", minOccurs=2, maxOccurs=3, nullable=true): Content is not complete");
    test(TestArray.Array2d1.class, l(l(null, "abc", "111", "abc")), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + TestArray.Array1d2.class.getName() + ".class, elementIds={}, minOccurs=1, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=2: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", minOccurs=2, maxOccurs=3, nullable=true): Pattern is not matched: \"111\"");
    test(TestArray.Array2d1.class, l(l(null, "111", null)), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=0, type=" + TestArray.Array1d2.class.getName() + ".class, elementIds={}, minOccurs=1, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=1: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", minOccurs=2, maxOccurs=3, nullable=true): Pattern is not matched: \"111\"");
  }

  @Test
  public void testArray2d2() {
    test(TestArray.Array2d2.class, l(l(null, "abc", null)), a("0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d2.class, l(l(null, "abc", BigInteger.ONE)), a("0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d2.class, l(l(true, "abc", null, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d2.class, l(l(true, null, "abc", BigDecimals.TWO)), a("0", "0.0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d2.class, l(l(true, null, "abc", "abc", BigDecimals.TWO)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d2.class, l(l(true, null, "abc", "123", BigDecimals.TWO)), a("0", "0.0", "0.1", "0.1", "0.2", "0.4"));
    test(TestArray.Array2d2.class, l(l(null, null, null, null, null)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d2.class, l(l(null, null, null, null)), a("0", "0.0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d2.class, l(l(null, null, null)), a("0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d2.class, l(l(null, null, BigDecimals.TWO)), a("0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d2.class, l(l(null, null, BigDecimal.TEN)), a("0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d2.class, l(l(null, null, null, BigDecimals.TWO)), a("0", "0.0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d2.class, l(l(null, null, null, BigDecimals.TWO, BigDecimal.TEN)), a("0", "0.0", "0.1", "0.1", "0.3", "0.4"));
    test(TestArray.Array2d2.class, l(l(null, null, null, BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.4", "0.4"));
    test(TestArray.Array2d2.class, l(l(null, null, null, null, BigDecimals.TWO)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d2.class, l(l(null, null, null, null, null, BigDecimals.TWO)), a("0", "0.0", "0.0", "0.0", "0.1", "0.1", "0.4"));
    test(TestArray.Array2d2.class, l(l(null, null, null, BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.4", "0.4"));
    test(TestArray.Array2d2.class, l(l(null, "abc", null, "abc", BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.1", "0.4", "0.4"));
    test(TestArray.Array2d2.class, l(l(null, "abc", null, "123", BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.1", "0.1", "0.2", "0.4", "0.4"));
    test(TestArray.Array2d2.class, l(l(null, null, "abc", "abc", BigDecimals.PI, BigInteger.ONE)), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "0.4"));
    test(TestArray.Array2d2.class, l(l(null, null, "123", "abc", BigDecimals.PI, BigInteger.ONE)), a("0", "0.1", "0.1", "0.2", "0.2", "0.4", "0.4"));

    test(TestArray.Array2d2.class, l(l(null, "ABC", null)), a("2", "4", "4", "7"));
    test(TestArray.Array2d2.class, l(l(null, "ABC", BigInteger.ONE)), a("2", "4", "4", "7"));
    test(TestArray.Array2d2.class, l(l(true, "ABC", null, BigInteger.ONE)), a("2", "3", "4", "4", "7"));
    test(TestArray.Array2d2.class, l(l(true, null, "ABC", BigDecimals.TWO)), a("2", "3", "4", "4", "7"));
    test(TestArray.Array2d2.class, l(l(true, null, "ABC", "ABC", BigDecimals.TWO)), a("2", "3", "3", "4", "4", "7"));
    test(TestArray.Array2d2.class, l(l(true, null, "ABC", "123", BigDecimals.TWO)), a("2", "3", "4", "4", "5", "7"));
    test(TestArray.Array2d2.class, l(l(null, "ABC", null, "ABC", BigDecimals.PI, BigInteger.ONE)), a("2", "3", "4", "4", "4", "7", "7"));
    test(TestArray.Array2d2.class, l(l(null, "ABC", null, "123", BigDecimals.PI, BigInteger.ONE)), a("2", "3", "4", "4", "5", "7", "7"));
    test(TestArray.Array2d2.class, l(l(null, null, "ABC", "ABC", BigDecimals.PI, BigInteger.ONE)), a("2", "3", "3", "4", "4", "7", "7"));
    test(TestArray.Array2d2.class, l(l(null, null, "123", "ABC", BigDecimals.PI, BigInteger.ONE)), a("2", "4", "4", "5", "5", "7", "7"));

    test(TestArray.Array2d2.class, l(l(null, "abc", null), l(null, "ABC", null)), a("0", "0.1", "0.1", "0.4", "2", "4", "4", "7"));
    test(TestArray.Array2d2.class, l(l(null, "abc", null), null, l(null, "ABC", null)), a("0", "0.1", "0.1", "0.4", "1", "2", "4", "4", "7"));
    test(TestArray.Array2d2.class, l(l(null, "abc", null), null, l(null, "ABC", null), BigDecimal.TEN), a("0", "0.1", "0.1", "0.4", "1", "2", "4", "4", "7", "8"));
    test(TestArray.Array2d2.class, l(l(null, "abc", null), null, l(null, "ABC", null), BigDecimal.ONE), a("0", "0.1", "0.1", "0.4", "1", "2", "4", "4", "7", "9"));

    test(TestArray.Array2d2.class, l(true, l(), BigInteger.ZERO, BigInteger.ONE, BigIntegers.TWO), "Invalid content was found starting with member index=1: @" + ArrayElement.class.getName() + "(id=2, type=" + ArrayType.class.getName() + ".class, elementIds={3, 4, 5, 6, 7}, minOccurs=0, maxOccurs=2147483647, nullable=false): Invalid content was found in empty array: @" + StringElement.class.getName() + "(id=4, pattern=\"[A-Z]+\", minOccurs=2, maxOccurs=3, nullable=true): Content is not complete");
    test(TestArray.Array2d2.class, l(true, l(null, "abc"), "abc", BigInteger.ZERO, BigInteger.ONE), "Invalid content was found starting with member index=1: @" + ArrayElement.class.getName() + "(id=2, type=" + ArrayType.class.getName() + ".class, elementIds={3, 4, 5, 6, 7}, minOccurs=0, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=1: @" + StringElement.class.getName() + "(id=4, pattern=\"[A-Z]+\", minOccurs=2, maxOccurs=3, nullable=true): Pattern is not matched: \"abc\"");
    test(TestArray.Array2d2.class, l(true, l(null, "ABC", "ABC", BigInteger.TEN), "abc", BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE), "Invalid content was found starting with member index=2: @" + NumberElement.class.getName() + "(id=9, form=INTEGER, range=\"[0,5]\", minOccurs=0, maxOccurs=1, nullable=false): No members are expected at this point: abc");
    test(TestArray.Array2d2.class, l(true, l(null, "ABC", "ABC", BigInteger.TEN), BigInteger.ZERO, BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE), "Invalid content was found starting with member index=2: @" + NumberElement.class.getName() + "(id=8, form=INTEGER, range=\"[5,10]\", minOccurs=0, maxOccurs=1, nullable=false): Range is not matched: 0");
    test(TestArray.Array2d2.class, l(l(null, "abc", "abc", BigInteger.TEN), false, l(BigInteger.ZERO, "123")), "Invalid content was found starting with member index=2: @" + ArrayElement.class.getName() + "(id=2, type=" + ArrayType.class.getName() + ".class, elementIds={3, 4, 5, 6, 7}, minOccurs=0, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=0: @" + StringElement.class.getName() + "(id=4, pattern=\"[A-Z]+\", minOccurs=2, maxOccurs=3, nullable=true): Content is not expected: 0");
    test(TestArray.Array2d2.class, l(l(null, "abc", null, BigDecimal.TEN), false, l("ABC", "ABC", null, BigDecimals.PI, "abc")), "Invalid content was found starting with member index=2: @" + ArrayElement.class.getName() + "(id=2, type=" + ArrayType.class.getName() + ".class, elementIds={3, 4, 5, 6, 7}, minOccurs=0, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=3: @" + NumberElement.class.getName() + "(id=6, form=INTEGER, range=\"[0,4]\", minOccurs=0, maxOccurs=2, nullable=false): Illegal Form.INTEGER value: 3.14159265358...");
    test(TestArray.Array2d2.class, l(l(null, "abc", null, BigDecimal.TEN), false, l("ABC", "ABC", null, BigDecimal.ZERO, null, null, null)), "Invalid content was found starting with member index=2: @" + ArrayElement.class.getName() + "(id=2, type=" + ArrayType.class.getName() + ".class, elementIds={3, 4, 5, 6, 7}, minOccurs=0, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=4: @" + NumberElement.class.getName() + "(id=6, form=INTEGER, range=\"[0,4]\", minOccurs=0, maxOccurs=2, nullable=false): Illegal value: null");
  }

  @Test
  public void testArray3d() {
    test(TestArray.Array3d.class, l(l(null, "abc", null), l(l(null, "abc", null))), a("0", "0.1", "0.1", "0.4", "1", "1.0", "1.0.1", "1.0.1", "1.0.4"));
    test(TestArray.Array3d.class, l(l(null, "abc", BigInteger.ONE), l(l(null, "abc", BigInteger.ONE))), a("0", "0.1", "0.1", "0.4", "1", "1.0", "1.0.1", "1.0.1", "1.0.4"));
    test(TestArray.Array3d.class, l(l(true, "abc", null, BigInteger.ONE), l(l(true, "abc", null, BigInteger.ONE))), a("0", "0.0", "0.1", "0.1", "0.4", "1", "1.0", "1.0.0", "1.0.1", "1.0.1", "1.0.4"));
    test(TestArray.Array3d.class, l(l(true, null, "abc", BigDecimals.TWO), l(l(true, null, "abc", BigDecimals.TWO))), a("0", "0.0", "0.1", "0.1", "0.4", "1", "1.0", "1.0.0", "1.0.1", "1.0.1", "1.0.4"));
    test(TestArray.Array3d.class, l(l(true, null, "abc", "abc", BigDecimals.TWO), l(l(true, null, "abc", "abc", BigDecimals.TWO))), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "1", "1.0", "1.0.0", "1.0.0", "1.0.1", "1.0.1", "1.0.4"));
    test(TestArray.Array3d.class, l(l(true, null, "abc", "123", BigDecimals.TWO), l(l(true, null, "abc", "123", BigDecimals.TWO))), a("0", "0.0", "0.1", "0.1", "0.2", "0.4", "1", "1.0", "1.0.0", "1.0.1", "1.0.1", "1.0.2", "1.0.4"));
    test(TestArray.Array3d.class, l(l(null, null, null, null, null), l(l(null, null, null, null, null))), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "1", "1.0", "1.0.0", "1.0.0", "1.0.1", "1.0.1", "1.0.4"));
    test(TestArray.Array3d.class, l(l(null, null, null, null), l(l(null, null, null, null))), a("0", "0.0", "0.1", "0.1", "0.4", "1", "1.0", "1.0.0", "1.0.1", "1.0.1", "1.0.4"));
    test(TestArray.Array3d.class, l(l(null, null, null), l(l(null, null, null))), a("0", "0.1", "0.1", "0.4", "1", "1.0", "1.0.1", "1.0.1", "1.0.4"));
    test(TestArray.Array3d.class, l(l(null, null, BigDecimals.TWO), l(l(null, null, BigDecimals.TWO))), a("0", "0.1", "0.1", "0.4", "1", "1.0", "1.0.1", "1.0.1", "1.0.4"));
    test(TestArray.Array3d.class, l(l(null, null, BigDecimal.TEN), l(l(null, null, BigDecimal.TEN))), a("0", "0.1", "0.1", "0.4", "1", "1.0", "1.0.1", "1.0.1", "1.0.4"));
    test(TestArray.Array3d.class, l(l(null, null, null, BigDecimals.TWO), l(l(null, null, null, BigDecimals.TWO))), a("0", "0.0", "0.1", "0.1", "0.4", "1", "1.0", "1.0.0", "1.0.1", "1.0.1", "1.0.4"));
    test(TestArray.Array3d.class, l(l(null, null, null, BigDecimals.TWO, BigDecimal.TEN), l(l(null, null, null, BigDecimals.TWO, BigDecimal.TEN))), a("0", "0.0", "0.1", "0.1", "0.3", "0.4", "1", "1.0", "1.0.0", "1.0.1", "1.0.1", "1.0.3", "1.0.4"));
    test(TestArray.Array3d.class, l(l(null, null, null, BigDecimals.PI, BigInteger.ONE), l(l(null, null, null, BigDecimals.PI, BigInteger.ONE))), a("0", "0.0", "0.1", "0.1", "0.4", "0.4", "1", "1.0", "1.0.0", "1.0.1", "1.0.1", "1.0.4", "1.0.4"));
    test(TestArray.Array3d.class, l(l(null, null, null, null, BigDecimals.TWO), l(l(null, null, null, null, BigDecimals.TWO))), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "1", "1.0", "1.0.0", "1.0.0", "1.0.1", "1.0.1", "1.0.4"));
    test(TestArray.Array3d.class, l(l(null, null, null, null, null, BigDecimals.TWO), l(l(null, null, null, null, null, BigDecimals.TWO))), a("0", "0.0", "0.0", "0.0", "0.1", "0.1", "0.4", "1", "1.0", "1.0.0", "1.0.0", "1.0.0", "1.0.1", "1.0.1", "1.0.4"));
    test(TestArray.Array3d.class, l(l(null, null, null, BigDecimals.PI, BigInteger.ONE), l(l(null, null, null, BigDecimals.PI, BigInteger.ONE))), a("0", "0.0", "0.1", "0.1", "0.4", "0.4", "1", "1.0", "1.0.0", "1.0.1", "1.0.1", "1.0.4", "1.0.4"));
    test(TestArray.Array3d.class, l(l(null, "abc", null, "abc", BigDecimals.PI, BigInteger.ONE), l(l(null, "abc", null, "abc", BigDecimals.PI, BigInteger.ONE))), a("0", "0.0", "0.1", "0.1", "0.1", "0.4", "0.4", "1", "1.0", "1.0.0", "1.0.1", "1.0.1", "1.0.1", "1.0.4", "1.0.4"));
    test(TestArray.Array3d.class, l(l(null, "abc", null, "123", BigDecimals.PI, BigInteger.ONE), l(l(null, "abc", null, "123", BigDecimals.PI, BigInteger.ONE))), a("0", "0.0", "0.1", "0.1", "0.2", "0.4", "0.4", "1", "1.0", "1.0.0", "1.0.1", "1.0.1", "1.0.2", "1.0.4", "1.0.4"));
    test(TestArray.Array3d.class, l(l(null, null, "abc", "abc", BigDecimals.PI, BigInteger.ONE), l(l(null, null, "abc", "abc", BigDecimals.PI, BigInteger.ONE))), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "0.4", "1", "1.0", "1.0.0", "1.0.0", "1.0.1", "1.0.1", "1.0.4", "1.0.4"));
    test(TestArray.Array3d.class, l(l(null, null, "123", "abc", BigDecimals.PI, BigInteger.ONE), l(l(null, null, "123", "abc", BigDecimals.PI, BigInteger.ONE))), a("0", "0.1", "0.1", "0.2", "0.2", "0.4", "0.4", "1", "1.0", "1.0.1", "1.0.1", "1.0.2", "1.0.2", "1.0.4", "1.0.4"));

    test(TestArray.Array3d.class, l(l(null, "abc", null), l(l(null, "ABC", null))), a("0", "0.1", "0.1", "0.4", "1", "1.2", "1.4", "1.4", "1.7"));
    test(TestArray.Array3d.class, l(l(null, "abc", BigInteger.ONE), l(l(null, "ABC", BigInteger.ONE))), a("0", "0.1", "0.1", "0.4", "1", "1.2", "1.4", "1.4", "1.7"));
    test(TestArray.Array3d.class, l(l(true, "abc", null, BigInteger.ONE), l(l(true, "ABC", null, BigInteger.ONE))), a("0", "0.0", "0.1", "0.1", "0.4", "1", "1.2", "1.3", "1.4", "1.4", "1.7"));
    test(TestArray.Array3d.class, l(l(true, null, "abc", BigDecimals.TWO), l(l(true, null, "ABC", BigDecimals.TWO))), a("0", "0.0", "0.1", "0.1", "0.4", "1", "1.2", "1.3", "1.4", "1.4", "1.7"));
    test(TestArray.Array3d.class, l(l(true, null, "abc", "abc", BigDecimals.TWO), l(l(true, null, "ABC", "ABC", BigDecimals.TWO))), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "1", "1.2", "1.3", "1.3", "1.4", "1.4", "1.7"));
    test(TestArray.Array3d.class, l(l(true, null, "abc", "123", BigDecimals.TWO), l(l(true, null, "ABC", "123", BigDecimals.TWO))), a("0", "0.0", "0.1", "0.1", "0.2", "0.4", "1", "1.2", "1.3", "1.4", "1.4", "1.5", "1.7"));
    test(TestArray.Array3d.class, l(l(null, "abc", null, "abc", BigDecimals.PI, BigInteger.ONE), l(l(null, "ABC", null, "ABC", BigDecimals.PI, BigInteger.ONE))), a("0", "0.0", "0.1", "0.1", "0.1", "0.4", "0.4", "1", "1.2", "1.3", "1.4", "1.4", "1.4", "1.7", "1.7"));
    test(TestArray.Array3d.class, l(l(null, "abc", null, "123", BigDecimals.PI, BigInteger.ONE), l(l(null, "ABC", null, "123", BigDecimals.PI, BigInteger.ONE))), a("0", "0.0", "0.1", "0.1", "0.2", "0.4", "0.4", "1", "1.2", "1.3", "1.4", "1.4", "1.5", "1.7", "1.7"));
    test(TestArray.Array3d.class, l(l(null, null, "abc", "abc", BigDecimals.PI, BigInteger.ONE), l(l(null, null, "ABC", "ABC", BigDecimals.PI, BigInteger.ONE))), a("0", "0.0", "0.0", "0.1", "0.1", "0.4", "0.4", "1", "1.2", "1.3", "1.3", "1.4", "1.4", "1.7", "1.7"));
    test(TestArray.Array3d.class, l(l(null, null, "123", "abc", BigDecimals.PI, BigInteger.ONE), l(l(null, null, "123", "ABC", BigDecimals.PI, BigInteger.ONE))), a("0", "0.1", "0.1", "0.2", "0.2", "0.4", "0.4", "1", "1.2", "1.4", "1.4", "1.5", "1.5", "1.7", "1.7"));

    test(TestArray.Array3d.class, l(true), "Invalid content was found starting with member index=0: @" + ArrayElement.class.getName() + "(id=1, type=" + TestArray.Array2d2.class.getName() + ".class, elementIds={}, minOccurs=1, maxOccurs=2147483647, nullable=false): Content is not expected: true");
    test(TestArray.Array3d.class, l(l(true, "abc", "abc", BigDecimals.PI), l(l(null, "abc"), false, l(BigInteger.ZERO, "abc"))), "Invalid content was found starting with member index=1: @" + ArrayElement.class.getName() + "(id=0, type=" + TestArray.Array1d2.class.getName() + ".class, elementIds={}, minOccurs=0, maxOccurs=2147483647, nullable=false): Invalid content was found starting with member index=0: @" + StringElement.class.getName() + "(id=1, pattern=\"[a-z]+\", minOccurs=2, maxOccurs=3, nullable=true): Content is not expected: [null, abc]");
  }
}