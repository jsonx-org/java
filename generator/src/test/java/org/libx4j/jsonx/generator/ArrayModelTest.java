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

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Number;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Object;

public class ArrayModelTest {
  @Test
  @Ignore
  public void testGreatestCommonSuperObject() {
    xL2gluGCXYYJc.Jsonx jsonx = new xL2gluGCXYYJc.Jsonx();
    jsonx.setPackage$(new xL2gluGCXYYJc.Jsonx.Package$("org.libx4j.jsonx.generator"));
    final Schema schema = new Schema(jsonx);
    final $Object.Number integer1 = new $Object.Number();
    integer1.setName$(new $Object.Number.Name$("integer1"));
    integer1.setForm$(new $Number.Form$($Number.Form$.integer));
    final NumberModel number1 = new NumberModel(schema, integer1);

    final $Object.Number integer2 = new $Object.Number();
    integer2.setName$(new $Object.Number.Name$("integer2"));
    integer2.setForm$(new $Number.Form$($Number.Form$.integer));
    final NumberModel number2 = new NumberModel(schema, integer2);
    Assert.assertEquals(Arrays.asList(Integer.class), ArrayModel.getGreatestCommonSuperObject(Arrays.asList(new Element[] {number1, number2})));
  }
}