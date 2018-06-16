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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.Map;

import org.junit.Test;
import org.lib4j.jci.CompilationException;
import org.lib4j.jci.InMemoryCompiler;
import org.lib4j.lang.PackageNotFoundException;
import org.lib4j.test.AssertXml;
import org.lib4j.xml.ValidationException;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc;
import org.libx4j.xsb.runtime.Bindings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonxTest {
  private static final Logger logger = LoggerFactory.getLogger(JsonxTest.class);

  private static xL2gluGCXYYJc.Jsonx newControlBinding() throws IOException, MalformedURLException, ValidationException {
    try (final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("test1.jsonx")) {
      return (xL2gluGCXYYJc.Jsonx)Bindings.parse(in);
    }
  }

  @Test
  public void test() throws ClassNotFoundException, CompilationException, IOException, MalformedURLException, PackageNotFoundException, ValidationException {
    final xL2gluGCXYYJc.Jsonx controlBinding = newControlBinding();
    final Schema controlSchema = new Schema(controlBinding);
    final String controlJsonx = controlSchema.toJSONX();
    final xL2gluGCXYYJc.Jsonx testBinding = (xL2gluGCXYYJc.Jsonx)Bindings.parse(new ByteArrayInputStream(controlJsonx.getBytes()));
    AssertXml.compare(controlBinding.toDOM(), testBinding.toDOM()).assertEqual();

    final File destDir = new File("target/generated-test-sources/jsonx");
    Files.list(destDir.toPath()).forEach(p -> {
      try {
        org.lib4j.io.Files.deleteAll(p);
      }
      catch (final IOException e) {
        throw new RuntimeException(e);
      }
    });
    final Map<Type,String> sources = controlSchema.toJava(destDir);
    final InMemoryCompiler compiler = new InMemoryCompiler();
    for (final Map.Entry<Type,String> entry : sources.entrySet())
      compiler.addSource(entry.getValue());

    final ClassLoader classLoader = compiler.compile();
    final Schema testSchema = new Schema(classLoader.getDefinedPackage(controlBinding.getPackage$().text()), classLoader);
    final String testJsonx = testSchema.toJSONX();
    logger.info(testJsonx);
  }
}