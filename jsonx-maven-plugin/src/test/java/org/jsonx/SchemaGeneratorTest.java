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

public class SchemaGeneratorTest {
  private static final String javaPath = schema.class.getName().replace('.', '/') + ".java";
  private static final File destDir = new File("target/generated-sources/jsonx");

  private static void test(final String version) throws IOException {
    final URL testJsdUrl = new URL("http://www.jsonx.org/schema-" + version + ".jsdx");
    SchemaElement.parse(testJsdUrl, new Settings.Builder().withPrefix("org.jsonx.schema$").build()).toSource(destDir);

    final File controlJavaFile = new File("../generator/src/main/java", javaPath);
    final File testJavaFile = new File(destDir, javaPath);
    final String controlSource = new String(Files.readAllBytes(controlJavaFile.toPath())).replaceFirst(", date=\"[^\"]+\"", ", date=\"\"");
    final String testSource = new String(Files.readAllBytes(testJavaFile.toPath())).replaceFirst(", date=\"[^\"]+\"", ", date=\"\"");
    if (!controlSource.equals(testSource)) {
      System.out.println(testSource);
      fail();
    }
  }

  @Test
  public void test() throws IOException {
    test("0.4");
  }
}