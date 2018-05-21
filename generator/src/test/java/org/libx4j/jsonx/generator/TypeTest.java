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

package org.libx4j.jsonx.generator;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TypeTest {
  private static Element toElement(final Type type) {
    return new Element(null, null, null, null, null, (String)null) {
      @Override
      protected Type type() {
        return type;
      }

      @Override
      protected Class<? extends Annotation> propertyAnnotation() {
        return null;
      }

      @Override
      protected Class<? extends Annotation> elementAnnotation() {
        return null;
      }

      @Override
      protected Element normalize(Registry registry) {
        return null;
      }
    };
  }

  @Test
  public void testClass() {
    final Type type = Type.get(Integer.class);
    Assert.assertEquals(Integer.class.getName(), type.toString());
    Assert.assertEquals(Number.class.getName(), type.getSuperType().toString());
    Assert.assertEquals(Object.class.getName(), type.getSuperType().getSuperType().toString());
    Assert.assertNull(type.getSuperType().getSuperType().getSuperType());
  }

  @Test
  public void testName() {
    final Type type = Type.get(TypeTest.class.getSimpleName(), null);
    Assert.assertEquals(TypeTest.class.getSimpleName(), type.toString());
    Assert.assertEquals(Object.class.getName(), type.getSuperType().toString());
    Assert.assertNull(type.getSuperType().getSuperType());
  }

  @Test
  public void testNameSuperName() {
    final String name = "one.One$Two$Three";

    final String superName = "two.Foo$Bar";
    final Type type = Type.get(name, superName, null);

    Assert.assertEquals("one.One$Two", type.getDeclaringType().toString());
    Assert.assertEquals("one.One", type.getDeclaringType().getDeclaringType().toString());
    Assert.assertNull(type.getDeclaringType().getDeclaringType().getDeclaringType());

    Assert.assertEquals("two.Foo", type.getSuperType().getDeclaringType().toString());
    Assert.assertNull(type.getSuperType().getDeclaringType().getDeclaringType());

    Assert.assertEquals(name, type.toString());
    Assert.assertEquals(superName, type.getSuperType().toString());
    Assert.assertEquals(Object.class.getName(), type.getSuperType().getSuperType().toString());
    Assert.assertNull(type.getSuperType().getSuperType().getSuperType());
  }

  @Test
  public void testGenerics() {
    final String packageName0 = "org.libx4j.jsonx.zero";
    final String name0 = "Zero";

    final String superPackageName0 = "org.libx4j.jsonx.superzero";
    final String superName0 = "SuperZero";
    final Type type0 = Type.get(packageName0 + "." + name0, superPackageName0 + "." + superName0, null);

    final String packageName1 = "org.libx4j.jsonx.one";
    final String name1 = "One";

    final String superPackageName1 = "org.libx4j.jsonx.superone";
    final String superName1 = "SuperOne";
    final Type type1 = Type.get(packageName1 + "." + name1, superPackageName1 + "." + superName1, null);

    final String packageName2 = "org.libx4j.jsonx.two";
    final String name2 = "Two";
    final Type type2 = Type.get(packageName2 + "." + name2, type0, null);

    final String packageName3 = "org.libx4j.jsonx.three";
    final String name3 = "Three";
    final Type type3 = Type.get(packageName3 + "." + name3, type1, null);

    Assert.assertEquals(Type.OBJECT, type0.getGreatestCommonSuperType(type1));
    Assert.assertEquals(type0, type0.getGreatestCommonSuperType(type2));
    Assert.assertEquals(Type.OBJECT, type0.getGreatestCommonSuperType(type3));

    Assert.assertEquals(Type.OBJECT, type1.getGreatestCommonSuperType(type2));
    Assert.assertEquals(type1, type1.getGreatestCommonSuperType(type3));

    Assert.assertEquals(Type.OBJECT, type2.getGreatestCommonSuperType(type3));

    Assert.assertEquals(Type.get(List.class, type0), ArrayModel.getGreatestCommonSuperType(Arrays.asList(toElement(type0))));

    Assert.assertEquals(Type.get(List.class, type0), ArrayModel.getGreatestCommonSuperType(Arrays.asList(toElement(type0), toElement(type2))));
    Assert.assertEquals(Type.get(List.class, Type.OBJECT), ArrayModel.getGreatestCommonSuperType(Arrays.asList(toElement(type0), toElement(type1), toElement(type2))));

    Assert.assertEquals(Type.get(List.class, type1), ArrayModel.getGreatestCommonSuperType(Arrays.asList(toElement(type1), toElement(type3))));
    Assert.assertEquals(Type.get(List.class, Type.OBJECT), ArrayModel.getGreatestCommonSuperType(Arrays.asList(toElement(type0), toElement(type1), toElement(type2), toElement(type3))));
  }
}