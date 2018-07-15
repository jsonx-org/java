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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
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
  private static final URL jsonxXsd = Thread.currentThread().getContextClassLoader().getResource("jsonx.xsd");

  private static xL2gluGCXYYJc.Jsonx newControlBinding(final String fileName) throws IOException, MalformedURLException, ValidationException {
    try (final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
      return (xL2gluGCXYYJc.Jsonx)Bindings.parse(in);
    }
  }

  private static org.lib4j.xml.Element toXml(final Schema schema) {
    final org.lib4j.xml.Element xml = schema.toXml();
    xml.getAttributes().put("xsi:schemaLocation", "http://jsonx.libx4j.org/jsonx-0.9.8.xsd " + jsonxXsd);
    return xml;
  }

  private static Schema testParseJsonx(final xL2gluGCXYYJc.Jsonx controlBinding) throws IOException, ParseException, ValidationException {
    logger.info("  a) XML(1) -> JSONX");
    final Schema controlSchema = new Schema(controlBinding);
    logger.info("  b) JSONX -> XML(2)");
    final xL2gluGCXYYJc.Jsonx testBinding = (xL2gluGCXYYJc.Jsonx)Bindings.parse(toXml(controlSchema).toString());
    logger.info("  c) XML(1) == XML(2)");
    AssertXml.compare(controlBinding.toDOM(), testBinding.toDOM()).assertEqual();
    return controlSchema;
  }

  public static void test(final String fileName) throws ClassNotFoundException, CompilationException, IOException, MalformedURLException, PackageNotFoundException, ValidationException {
    logger.info(fileName + "...");
    final xL2gluGCXYYJc.Jsonx controlBinding = newControlBinding(fileName);
    final Schema controlSchema = testParseJsonx(controlBinding);

    final File generatedSourcesDir = new File("target/generated-test-sources/jsonx");
    generatedSourcesDir.mkdirs();
    Files.walk(generatedSourcesDir.toPath()).sorted(Comparator.reverseOrder()).map(Path::toFile).filter(f -> !f.equals(generatedSourcesDir)).forEach(File::delete);
    logger.info("4) JSONX -> Java(1)");
    final Map<String,String> sources = controlSchema.toJava(generatedSourcesDir);
    final InMemoryCompiler compiler = new InMemoryCompiler();
    for (final Map.Entry<String,String> entry : sources.entrySet())
      compiler.addSource(entry.getValue());

    logger.info("5) -- Java(1) Compile --");
    final ClassLoader classLoader = compiler.compile();

    logger.info("6) Java(1) -> JSONX");
    final Schema testSchema = new Schema(classLoader.getDefinedPackage((String)controlBinding.getPackage$().text()), classLoader, c -> c.getClassLoader() == classLoader);
    final File generatedResourcesDir = new File("target/generated-test-resources");
    generatedResourcesDir.mkdirs();
    final String schema = toXml(testSchema).toString();
    try (final OutputStreamWriter out = new FileWriter(new File(generatedResourcesDir, "out-" + fileName))) {
      out.write("<!--\n  Copyright (c) 2017 lib4j\n\n  Permission is hereby granted, free of charge, to any person obtaining a copy\n");
      out.write("of this software and associated documentation files (the \"Software\"), to deal\n");
      out.write("  in the Software without restriction, including without limitation the rights\n");
      out.write("  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n");
      out.write("  copies of the Software, and to permit persons to whom the Software is\n");
      out.write("  furnished to do so, subject to the following conditions:\n\n");
      out.write("  The above copyright notice and this permission notice shall be included in\n");
      out.write("  all copies or substantial portions of the Software.\n\n");
      out.write("  You should have received a copy of The MIT License (MIT) along with this\n");
      out.write("  program. If not, see <http://opensource.org/licenses/MIT/>.\n-->\n");
      out.write(schema);
    }

    final xL2gluGCXYYJc.Jsonx reParsedBinding = (xL2gluGCXYYJc.Jsonx)Bindings.parse(schema);
    final Schema reParsedSchema = testParseJsonx(reParsedBinding);
    logger.info("7) JSONX -> Java(2)");
    final Map<String,String> reParsedSources = reParsedSchema.toJava();
    logger.info("8) Java(1) == Java(2)");
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