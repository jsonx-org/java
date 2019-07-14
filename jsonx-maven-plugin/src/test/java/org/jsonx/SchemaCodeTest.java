/* Copyright (c) 2019 JSONx
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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import org.junit.Test;

public class SchemaCodeTest {
  private static void test(final String version) throws IOException {
    final File destDir = new File("target/generated-sources/jsonx");
    final URL testJsdUrl = new URL("http://www.jsonx.org/schema-" + version + ".jsdx");
    SchemaElement.parse(testJsdUrl, "org.jsonx.schema$").toSource(destDir);

    final File controlJavaFile = new File("../generator/src/main/java/org/jsonx/schema.java");
    final File testJavaFile = new File(destDir, "org/jsonx/schema.java");
    final String controlJava = new String(Files.readAllBytes(controlJavaFile.toPath()));
    final String testJava = new String(Files.readAllBytes(testJavaFile.toPath()));
    assertEquals(controlJava, testJava);
  }

  @Test
  public void test() throws IOException {
    test("0.3.1");
  }
}