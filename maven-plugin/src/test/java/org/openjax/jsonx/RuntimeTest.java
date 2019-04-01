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

import org.junit.AfterClass;
import org.junit.Test;
import org.openjax.jsonx.generator.datatype.ObjArr;
import org.openjax.jsonx.generator.datatype.ObjBool;
import org.openjax.jsonx.generator.datatype.ObjNum;
import org.openjax.jsonx.generator.datatype.ObjObj;
import org.openjax.jsonx.generator.datatype.ObjStr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RuntimeTest {
  private static final Logger logger = LoggerFactory.getLogger(RuntimeTest.class);
  private static int count;

  private static void test(final Class<? extends JxObject> cls) throws Exception {
    for (int i = 0; i < 1000; ++i)
      count += new ClassTrial(cls).run();
  }

  @AfterClass
  public static void afterClass() {
    logger.info("Successful tests: " + count);
  }

  @Test
  public void testObjBool() throws Exception {
    test(ObjBool.class);
  }

  @Test
  public void testObjNum() throws Exception {
    test(ObjNum.class);
  }

  @Test
  public void testObjStr() throws Exception {
    test(ObjStr.class);
  }

  @Test
  public void testObjArr() throws Exception {
    test(ObjArr.class);
  }

  @Test
  public void testObjObj() throws Exception {
    test(ObjObj.class);
  }
}