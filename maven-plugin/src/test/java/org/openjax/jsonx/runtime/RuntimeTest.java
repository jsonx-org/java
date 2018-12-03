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

import org.junit.Test;
import org.openjax.jsonx.generator.datatype.ObjArr;
import org.openjax.jsonx.generator.datatype.ObjBool;
import org.openjax.jsonx.generator.datatype.ObjNum;
import org.openjax.jsonx.generator.datatype.ObjObj;
import org.openjax.jsonx.generator.datatype.ObjStr;

public class RuntimeTest {
  private static final int count = 100;

  public void test(final Class<?> cls) throws Exception {
    new ClassTrial(cls).invoke();
  }

  @Test
  public void testObjBool() throws Exception {
    for (int i = 0; i < count; ++i)
      test(ObjBool.class);
  }

  @Test
  public void testObjNum() throws Exception {
    for (int i = 0; i < count; ++i)
      test(ObjNum.class);
  }

  @Test
  public void testObjStr() throws Exception {
    for (int i = 0; i < count; ++i)
      test(ObjStr.class);
  }

  @Test
  public void testObjArr() throws Exception {
    for (int i = 0; i < count; ++i)
      test(ObjArr.class);
  }

  @Test
  public void testObjObj() throws Exception {
    for (int i = 0; i < count; ++i)
      test(ObjObj.class);
  }
}