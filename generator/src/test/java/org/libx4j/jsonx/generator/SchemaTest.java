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

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;

import org.junit.Test;
import org.lib4j.jci.CompilationException;
import org.lib4j.jci.InMemoryCompiler;
import org.lib4j.lang.PackageNotFoundException;
import org.lib4j.test.AssertXml;
import org.lib4j.xml.ValidationException;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc;
import org.libx4j.xsb.runtime.Bindings;
import org.libx4j.xsb.runtime.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchemaTest {
  private static final Logger logger = LoggerFactory.getLogger(SchemaTest.class);

  private static xL2gluGCXYYJc.Jsonx newControlBinding(final String fileName) throws IOException, MalformedURLException, ValidationException {
    try (final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
      return (xL2gluGCXYYJc.Jsonx)Bindings.parse(in);
    }
  }

  private static Schema testParseJsonx(final xL2gluGCXYYJc.Jsonx controlBinding) throws IOException, ParseException, ValidationException {
    final Schema controlSchema = new Schema(controlBinding);
    final String xml = controlSchema.toSchema().toString();
    final xL2gluGCXYYJc.Jsonx testBinding = (xL2gluGCXYYJc.Jsonx)Bindings.parse(xml);
    AssertXml.compare(controlBinding.toDOM(), testBinding.toDOM()).assertEqual();
    return controlSchema;
  }

  public static void test(final String fileName) throws ClassNotFoundException, CompilationException, IOException, MalformedURLException, PackageNotFoundException, ValidationException {
    final xL2gluGCXYYJc.Jsonx controlBinding = newControlBinding(fileName);
    final Schema controlSchema = testParseJsonx(controlBinding);

    final File generatedSourcesDir = new File("target/generated-test-sources/jsonx");
    generatedSourcesDir.mkdirs();
    Files.walk(generatedSourcesDir.toPath()).sorted(Comparator.reverseOrder()).map(Path::toFile).filter(f -> !f.equals(generatedSourcesDir)).forEach(File::delete);
    final Map<String,String> sources = controlSchema.toJava(generatedSourcesDir);
    final InMemoryCompiler compiler = new InMemoryCompiler();
    for (final Map.Entry<String,String> entry : sources.entrySet())
      compiler.addSource(entry.getValue());

    System.err.println("----------------------------------");
    final ClassLoader classLoader = compiler.compile();
    final Schema testSchema = new Schema(classLoader.getDefinedPackage((String)controlBinding.getPackage$().text()), classLoader);

    final File generatedResourcesDir = new File("target/generated-test-resources");
    generatedResourcesDir.mkdirs();
    final org.lib4j.xml.Element schema = testSchema.toSchema();
    try (final FileOutputStream out = new FileOutputStream(new File(generatedResourcesDir, "out-" + fileName))) {
      out.write("<!--\n  Copyright (c) 2017 lib4j\n\n  Permission is hereby granted, free of charge, to any person obtaining a copy\n  of this software and associated documentation files (the \"Software\"), to deal\n  in the Software without restriction, including without limitation the rights\n  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n  copies of the Software, and to permit persons to whom the Software is\n  furnished to do so, subject to the following conditions:\n\n  The above copyright notice and this permission notice shall be included in\n  all copies or substantial portions of the Software.\n\n  You should have received a copy of The MIT License (MIT) along with this\n  program. If not, see <http://opensource.org/licenses/MIT/>.\n-->\n".getBytes());
      out.write(schema.toString().getBytes());
    }

    final xL2gluGCXYYJc.Jsonx reParsedBinding = (xL2gluGCXYYJc.Jsonx)Bindings.parse(schema.toString());
    final Schema reParsedSchema = testParseJsonx(reParsedBinding);
    final Map<String,String> reParsedSources = reParsedSchema.toJava();
    assertEquals(sources.size(), reParsedSources.size());
    for (final Map.Entry<String,String> entry : sources.entrySet())
      assertEquals(entry.getValue(), reParsedSources.get(entry.getKey()));
  }

  @Test
  public void testTemplate() throws ClassNotFoundException, CompilationException, IOException, MalformedURLException, PackageNotFoundException, ValidationException {
    test("template.jsonx");
  }

  @Test
  public void testComplete() throws ClassNotFoundException, CompilationException, IOException, MalformedURLException, PackageNotFoundException, ValidationException {
    test("complete.jsonx");
  }
}