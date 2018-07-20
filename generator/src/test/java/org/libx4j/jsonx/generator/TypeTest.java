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
  private static Member toElement(final Registry.Type type) {
    return new Member(null, null, null, (Integer)null) {
      @Override
      protected Id id() {
        return null;
      }

      @Override
      protected Registry.Type type() {
        return type;
      }

      @Override
      protected String elementName() {
        return null;
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
      protected org.lib4j.xml.Element toXml(final Settings settings, final Element owner, final String packageName) {
        return null;
      }
    };
  }

  @Test
  public void testClass() {
    final Registry registry = new Registry();

    final Registry.Type type = registry.getType(Integer.class);
    Assert.assertEquals(Integer.class.getName(), type.toString());
    Assert.assertEquals(Number.class.getName(), type.getSuperType().toString());
    Assert.assertNull(type.getSuperType().getSuperType());
  }

  @Test
  public void testName() {
    final Registry registry = new Registry();

    final Registry.Type type = registry.getType(TypeTest.class);
    Assert.assertEquals(TypeTest.class.getName(), type.toString());
    Assert.assertEquals(Object.class.getName(), type.getSuperType().toString());
    Assert.assertNull(type.getSuperType().getSuperType());
  }

  @Test
  public void testNameSuperName() {
    final Registry registry = new Registry();

    final String packageName = "one";
    final String name = "One$Two$Three";

    final String superPackageName = "two";
    final String superName = "Foo$Bar";
    final Registry.Type type = registry.getType(packageName, name, superPackageName, superName, null);

    Assert.assertEquals(packageName + ".One$Two", type.getDeclaringType().toString());
    Assert.assertEquals(packageName + ".One", type.getDeclaringType().getDeclaringType().toString());
    Assert.assertNull(type.getDeclaringType().getDeclaringType().getDeclaringType());

    Assert.assertEquals(superPackageName + ".Foo", type.getSuperType().getDeclaringType().toString());
    Assert.assertNull(type.getSuperType().getDeclaringType().getDeclaringType());

    Assert.assertEquals(packageName + "." + name, type.toString());
    Assert.assertEquals(superPackageName + "." + superName, type.getSuperType().toString());
    Assert.assertNull(type.getSuperType().getSuperType());
  }

  @Test
  public void testGenerics() {
    final Registry registry = new Registry();

    final String packageName0 = "org.libx4j.jsonx.zero";
    final String name0 = "Zero";

    final String superPackageName0 = "org.libx4j.jsonx.superzero";
    final String superName0 = "SuperZero";
    final Registry.Type type0 = registry.getType(packageName0, name0, superPackageName0, superName0, null);

    final String packageName1 = "org.libx4j.jsonx.one";
    final String name1 = "One";

    final String superPackageName1 = "org.libx4j.jsonx.superone";
    final String superName1 = "SuperOne";
    final Registry.Type type1 = registry.getType(packageName1, name1, superPackageName1, superName1, null);

    final String packageName2 = "org.libx4j.jsonx.two";
    final String name2 = "Two";
    final Registry.Type type2 = registry.getType(packageName2, name2, type0.getPackage(), type0.getCompoundName(), null);

    final String packageName3 = "org.libx4j.jsonx.three";
    final String name3 = "Three";
    final Registry.Type type3 = registry.getType(packageName3, name3, type1.getPackage(), type1.getCompoundName(), null);

    Assert.assertEquals(registry.OBJECT, type0.getGreatestCommonSuperType(type1));
    Assert.assertEquals(type0, type0.getGreatestCommonSuperType(type2));
    Assert.assertEquals(registry.OBJECT, type0.getGreatestCommonSuperType(type3));

    Assert.assertEquals(registry.OBJECT, type1.getGreatestCommonSuperType(type2));
    Assert.assertEquals(type1, type1.getGreatestCommonSuperType(type3));

    Assert.assertEquals(registry.OBJECT, type2.getGreatestCommonSuperType(type3));

    Assert.assertEquals(registry.getType(List.class, type0), ArrayModel.getGreatestCommonSuperType(registry, Arrays.asList(toElement(type0))));

    Assert.assertEquals(registry.getType(List.class, type0), ArrayModel.getGreatestCommonSuperType(registry, Arrays.asList(toElement(type0), toElement(type2))));
    Assert.assertEquals(registry.getType(List.class, registry.getType(Object.class)), ArrayModel.getGreatestCommonSuperType(registry, Arrays.asList(toElement(type0), toElement(type1), toElement(type2))));

    Assert.assertEquals(registry.getType(List.class, type1), ArrayModel.getGreatestCommonSuperType(registry, Arrays.asList(toElement(type1), toElement(type3))));
    Assert.assertEquals(registry.getType(List.class, registry.getType(Object.class)), ArrayModel.getGreatestCommonSuperType(registry, Arrays.asList(toElement(type0), toElement(type1), toElement(type2), toElement(type3))));
  }
}