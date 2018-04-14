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

import org.junit.Assert;
import org.junit.Test;

public class TypeTest {
  @Test
  public void testClass() {
    final Type type = Type.get(Integer.class);
    Assert.assertEquals(Integer.class.getName(), type.getName());
    Assert.assertEquals(Number.class.getName(), type.getSuperType().getName());
    Assert.assertEquals(Object.class.getName(), type.getSuperType().getSuperType().getName());
    Assert.assertNull(type.getSuperType().getSuperType().getSuperType());
  }

  @Test
  public void testName() {
    final Type type = Type.get(TypeTest.class.getPackage().getName(), TypeTest.class.getSimpleName());
    Assert.assertEquals(TypeTest.class.getName(), type.getName());
    Assert.assertEquals(Object.class.getName(), type.getSuperType().getName());
    Assert.assertNull(type.getSuperType().getSuperType());
  }

  @Test
  public void testNameSuperName() {
    final String packageName1 = "org.libx4j.jsonx.one";
    final String packageName2 = "org.libx4j.jsonx.two";
    final String name = "One.Two.Three";
    final String superName = "Foo.Bar";
    final Type type = Type.get(packageName1, name, packageName2, superName);

    Assert.assertEquals(packageName1 + "." + "One.Two", type.getContainerType().getName());
    Assert.assertEquals(packageName1 + "." + "One", type.getContainerType().getContainerType().getName());
    Assert.assertNull(type.getContainerType().getContainerType().getContainerType());

    Assert.assertEquals(packageName2 + "." + "Foo", type.getSuperType().getContainerType().getName());
    Assert.assertNull(type.getSuperType().getContainerType().getContainerType());

    Assert.assertEquals(packageName1 + "." + name, type.getName());
    Assert.assertEquals(packageName2 + "." + superName, type.getSuperType().getName());
    Assert.assertEquals(Object.class.getName(), type.getSuperType().getSuperType().getName());
    Assert.assertNull(type.getSuperType().getSuperType().getSuperType());
  }
}