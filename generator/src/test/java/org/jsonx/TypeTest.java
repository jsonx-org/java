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

import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.util.HashMap;

import org.jsonx.Registry.Kind;
import org.junit.Test;
import org.libj.lang.WrappedArrayList;
import org.openjax.json.JSON;
import org.openjax.xml.api.XmlElement;

public class TypeTest {
  private static Member toElement(final Registry.Type type) {
    return new Member
        (null, null, false, null, null, null, null, null, null, null, null, null) {
      @Override
      Registry.Type typeDefault(final boolean primitive) {
        return type;
      }

      @Override
      String elementName() {
        return null;
      }

      @Override
      Class<?> defaultClass() {
        return null;
      }

      @Override
      String isValid(final Bind.Type typeBinding) {
        throw new UnsupportedOperationException();
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
      Class<? extends Annotation> typeAnnotation() {
        return null;
      }

      @Override
      XmlElement toXml(final Element owner, final String packageName, final JsonPath.Cursor cursor, final PropertyMap<AttributeMap> pathToBinding) {
        return null;
      }

      @Override
      JSON toJson(final Element owner, final String packageName, final JsonPath.Cursor cursor, final PropertyMap<AttributeMap> pathToBinding) {
        return null;
      }
    };
  }

  @Test
  public void testClass() {
    final Registry registry = new Registry(new HashMap<>(), null, "", Settings.DEFAULT);

    final Registry.Type type = registry.getType(Integer.class);
    assertEquals(Integer.class.getName(), type.toString());
    assertEquals(Number.class.getName(), type.superType.toString());
    assertNull(type.superType.superType);
  }

  @Test
  public void testName() {
    final Registry registry = new Registry(new HashMap<>(), null, "", Settings.DEFAULT);

    final Registry.Type type = registry.getType(TypeTest.class);
    assertEquals(TypeTest.class.getName(), type.toString());
    assertEquals(Object.class.getName(), type.superType.toString());
    assertNull(type.superType.superType);
  }

  @Test
  public void testNameSuperName() {
    final Registry registry = new Registry(new HashMap<>(), null, "", Settings.DEFAULT);

    final String packageName = "one";
    final String name = "One$Two$Three";

    final String superPackageName = "two";
    final String superName = "Foo$Bar";
    final Registry.Type type = registry.getType(Kind.CLASS, packageName, name, superPackageName, superName);
    assertEquals(Kind.CLASS, type.kind);
    assertEquals(name, type.compositeName);
    assertEquals(name.replace('$', '.'), type.canonicalCompositeName);
    assertEquals("Three", type.simpleName);
    assertEquals(packageName + "." + name, type.name);
    assertEquals(packageName + "." + name, type.getRelativeName(""));
    assertEquals(name, type.getRelativeName(packageName));
    assertEquals("Two$Three", type.getSubName(packageName + ".One"));

    assertEquals(packageName + ".One$Two", type.getDeclaringType().toString());
    assertEquals(packageName + ".One", type.getDeclaringType().getDeclaringType().toString());
    assertNull(type.getDeclaringType().getDeclaringType().getDeclaringType());

    assertEquals(superPackageName + ".Foo", type.superType.getDeclaringType().toString());
    assertNull(type.superType.getDeclaringType().getDeclaringType());

    assertEquals(packageName + "." + name, type.toString());
    assertEquals(superPackageName + "." + superName, type.superType.toString());
    assertNull(type.superType.superType);
  }

  @Test
  public void testGenerics() {
    final Registry registry = new Registry(new HashMap<>(), null, "", Settings.DEFAULT);

    final String packageName0 = "org.jsonx.zero";
    final String name0 = "Zero";

    final String superPackageName0 = "org.jsonx.superzero";
    final String superName0 = "SuperZero";
    final Registry.Type type0 = registry.getType(Kind.CLASS, packageName0, name0, superPackageName0, superName0);
    assertEquals(packageName0, type0.packageName);
    assertEquals(packageName0, type0.canonicalPackageName);
    assertEquals(name0, type0.simpleName);
    assertEquals(packageName0 + "." + name0, type0.name);
    assertEquals(packageName0 + "." + name0, type0.canonicalName);

    final String packageName1 = "org.jsonx.one";
    final String name1 = "One";

    final String superPackageName1 = "org.jsonx.superone";
    final String superName1 = "SuperOne";
    final Registry.Type type1 = registry.getType(Kind.CLASS, packageName1, name1, superPackageName1, superName1);
    final Registry.Type type1Copy = registry.getType(Kind.CLASS, packageName1, name1, superPackageName1, superName1);
    assertEquals(type1, type1Copy);

    final String packageName2 = "org.jsonx.two";
    final String name2 = "Two";
    final Registry.Type type2 = registry.getType(Kind.CLASS, packageName2, name2, type0.packageName, type0.compositeName);

    final String packageName3 = "org.jsonx.three";
    final String name3 = "Three";
    final Registry.Type type3 = registry.getType(Kind.CLASS, packageName3, name3, type1.packageName, type1.compositeName);

    assertEquals(registry.OBJECT, type0.getGreatestCommonSuperType(type1));
    assertEquals(type0, type0.getGreatestCommonSuperType(type2));
    assertEquals(registry.OBJECT, type0.getGreatestCommonSuperType(type3));

    assertEquals(registry.OBJECT, type1.getGreatestCommonSuperType(type2));
    assertEquals(type1, type1.getGreatestCommonSuperType(type3));

    assertEquals(registry.OBJECT, type2.getGreatestCommonSuperType(type3));

    assertEquals(type0, ArrayModel.getGreatestCommonSuperType(new WrappedArrayList<>(toElement(type0))));
    assertEquals(type0, ArrayModel.getGreatestCommonSuperType(new WrappedArrayList<>(toElement(type0), toElement(type2))));
    assertEquals(registry.getType(Object.class), ArrayModel.getGreatestCommonSuperType(new WrappedArrayList<>(toElement(type0), toElement(type1), toElement(type2))));

    assertEquals(type1, ArrayModel.getGreatestCommonSuperType(new WrappedArrayList<>(toElement(type1), toElement(type3))));
    assertEquals(registry.getType(Object.class), ArrayModel.getGreatestCommonSuperType(new WrappedArrayList<>(toElement(type0), toElement(type1), toElement(type2), toElement(type3))));
  }
}