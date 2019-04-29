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

package org.openjax.jsonx;

import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import org.junit.Test;
import org.openjax.ext.json.JSON;
import org.openjax.ext.xml.api.XmlElement;
import org.openjax.jsonx.Registry.Kind;

public class TypeTest {
  private static Member toElement(final Registry.Type type) {
    return new Member(null, null, null, null, null, null, null) {
      @Override
      Registry.Type type() {
        return type;
      }

      @Override
      String elementName() {
        return null;
      }

      @Override
      Class<? extends Annotation> propertyAnnotation() {
        return null;
      }

      @Override
      Class<? extends Annotation> elementAnnotation() {
        return null;
      }

      @Override
      XmlElement toXml(final Settings settings, final Element owner, final String prefix, final String packageName) {
        return null;
      }

      @Override
      JSON toJson(final Settings settings, final Element owner, final String packageName) {
        return null;
      }
    };
  }

  @Test
  public void testClass() {
    final Registry registry = new Registry("");

    final Registry.Type type = registry.getType(Integer.class);
    assertEquals(Integer.class.getName(), type.toString());
    assertEquals(Number.class.getName(), type.getSuperType().toString());
    assertNull(type.getSuperType().getSuperType());
  }

  @Test
  public void testName() {
    final Registry registry = new Registry("");

    final Registry.Type type = registry.getType(TypeTest.class);
    assertEquals(TypeTest.class.getName(), type.toString());
    assertEquals(Object.class.getName(), type.getSuperType().toString());
    assertNull(type.getSuperType().getSuperType());
  }

  @Test
  public void testNameSuperName() {
    final Registry registry = new Registry("");

    final String packageName = "one";
    final String name = "One$Two$Three";

    final String superPackageName = "two";
    final String superName = "Foo$Bar";
    final Registry.Type type = registry.getType(Kind.CLASS, packageName, name, superPackageName, superName, null);

    assertEquals(packageName + ".One$Two", type.getDeclaringType().toString());
    assertEquals(packageName + ".One", type.getDeclaringType().getDeclaringType().toString());
    assertNull(type.getDeclaringType().getDeclaringType().getDeclaringType());

    assertEquals(superPackageName + ".Foo", type.getSuperType().getDeclaringType().toString());
    assertNull(type.getSuperType().getDeclaringType().getDeclaringType());

    assertEquals(packageName + "." + name, type.toString());
    assertEquals(superPackageName + "." + superName, type.getSuperType().toString());
    assertNull(type.getSuperType().getSuperType());
  }

  @Test
  public void testGenerics() {
    final Registry registry = new Registry("");

    final String packageName0 = "org.openjax.jsonx.zero";
    final String name0 = "Zero";

    final String superPackageName0 = "org.openjax.jsonx.superzero";
    final String superName0 = "SuperZero";
    final Registry.Type type0 = registry.getType(Kind.CLASS, packageName0, name0, superPackageName0, superName0, null);

    final String packageName1 = "org.openjax.jsonx.one";
    final String name1 = "One";

    final String superPackageName1 = "org.openjax.jsonx.superone";
    final String superName1 = "SuperOne";
    final Registry.Type type1 = registry.getType(Kind.CLASS, packageName1, name1, superPackageName1, superName1, null);

    final String packageName2 = "org.openjax.jsonx.two";
    final String name2 = "Two";
    final Registry.Type type2 = registry.getType(Kind.CLASS, packageName2, name2, type0.getPackage(), type0.getCompoundName(), null);

    final String packageName3 = "org.openjax.jsonx.three";
    final String name3 = "Three";
    final Registry.Type type3 = registry.getType(Kind.CLASS, packageName3, name3, type1.getPackage(), type1.getCompoundName(), null);

    assertEquals(registry.OBJECT, type0.getGreatestCommonSuperType(type1));
    assertEquals(type0, type0.getGreatestCommonSuperType(type2));
    assertEquals(registry.OBJECT, type0.getGreatestCommonSuperType(type3));

    assertEquals(registry.OBJECT, type1.getGreatestCommonSuperType(type2));
    assertEquals(type1, type1.getGreatestCommonSuperType(type3));

    assertEquals(registry.OBJECT, type2.getGreatestCommonSuperType(type3));

    assertEquals(type0, ArrayModel.getGreatestCommonSuperType(Arrays.asList(toElement(type0))));
    assertEquals(type0, ArrayModel.getGreatestCommonSuperType(Arrays.asList(toElement(type0), toElement(type2))));
    assertEquals(registry.getType(Object.class), ArrayModel.getGreatestCommonSuperType(Arrays.asList(toElement(type0), toElement(type1), toElement(type2))));

    assertEquals(type1, ArrayModel.getGreatestCommonSuperType(Arrays.asList(toElement(type1), toElement(type3))));
    assertEquals(registry.getType(Object.class), ArrayModel.getGreatestCommonSuperType(Arrays.asList(toElement(type0), toElement(type1), toElement(type2), toElement(type3))));
  }
}