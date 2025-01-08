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

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.libj.net.URLs;
import org.libj.test.JUnitUtil;
import org.openjax.json.JSON;

@RunWith(Parameterized.class)
public class IncludeModelTest extends ModelTest {
  private static void test(final String name, final URL jsbx, final URL jsb) throws IOException {
    final String namespace = "urn:test:" + name;
    final String packageName = "org.jsonx.test.include." + name;
    final Settings settings = new Settings.Builder().withNamespacePackage(namespace, packageName + ".").build();

    if (logger.isInfoEnabled()) logger.info(name + "...");

    final SchemaElement jsbxSchema = Generator.parse(settings, jsbx)[0];
    System.err.println(jsb);
    final SchemaElement jsbSchema = Generator.parse(settings, jsb)[0];

    assertEquals(jsbxSchema.toXml().toString(), jsbSchema.toXml().toString());
    assertEquals(JSON.toString(jsbxSchema.toJson(), 2), JSON.toString(jsbSchema.toJson(), 2));

    final Map<String,String> jsbxSources = jsbxSchema.toSource();
    final Map<String,String> jsbSources = jsbSchema.toSource();

    assertEquals(jsbxSources, jsbSources);
  }

  @Parameterized.Parameters(name = "{0}")
  public static URL[] resources() throws IOException {
    return JUnitUtil.sortBySize(JUnitUtil.getResources("include", ".*\\.jsbx"));
  }

  @Parameterized.Parameter(0)
  public URL resource;

  @Test
  public void test() throws IOException {
    final String name = URLs.getNameWithoutExtension(resource);
    final URL jsb = URLs.create(URLs.getCanonicalParent(resource), name + ".jsb");
    test(name, resource, jsb);
  }
}