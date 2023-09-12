/* Copyright (c) 2023 JSONx
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

import java.io.IOException;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HrefTest {
  private static final Logger logger = LoggerFactory.getLogger(HrefTest.class);
  private static final String path = "href/";

  private static void test(final String name) throws IOException {
    final String namespace = "urn:test:" + name;
    final String packageName = "org.jsonx.href." + name;
    final Settings settings = new Settings.Builder().withNamespacePackage(namespace, packageName + ".").build();

    if (logger.isInfoEnabled()) logger.info(name + "...");

    final SchemaElement jsbxSchema = Generator.parse(settings, ClassLoader.getSystemClassLoader().getResource(path + name + ".jsbx"))[0];
    final Map<String,String> jsbxSources = jsbxSchema.toSource();

    final SchemaElement jsbSchema = Generator.parse(settings, ClassLoader.getSystemClassLoader().getResource(path + name + ".jsb"))[0];
    final Map<String,String> jsbSources = jsbSchema.toSource();

    assertEquals(jsbxSources, jsbSources);
  }

  @Test
  public void test() throws IOException {
    test("account");
  }
}