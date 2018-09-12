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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.fastjax.lang.PackageNotFoundException;
import org.fastjax.test.AssertXml;
import org.junit.Test;
import org.fastjax.jci.CompilationException;
import org.fastjax.jci.InMemoryCompiler;
import org.fastjax.xml.ValidationException;
import org.fastjax.xml.sax.Validator;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.Jsonx;
import org.libx4j.xsb.runtime.Bindings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class SchemaTest {
  private static final Logger logger = LoggerFactory.getLogger(SchemaTest.class);
  private static final URL jsonxXsd = Thread.currentThread().getContextClassLoader().getResource("jsonx.xsd");
  private static final File generatedSourcesDir = new File("target/generated-test-sources/jsonx");
  private static final File generatedResourcesDir = new File("target/generated-test-resources");
  private static final List<Settings> settings = new ArrayList<>();

  static {
    generatedSourcesDir.mkdirs();
    generatedResourcesDir.mkdirs();
    try {
      Files.walk(generatedSourcesDir.toPath()).sorted(Comparator.reverseOrder()).map(Path::toFile).filter(f -> !f.equals(generatedSourcesDir)).forEach(File::delete);
    }
    catch (final IOException e) {
      throw new ExceptionInInitializerError(e);
    }

    for (int i = 0; i < 10; i++)
      settings.add(new Settings(i));

    settings.add(new Settings(Integer.MAX_VALUE));
  }

  private static Jsonx newControlBinding(final String fileName) throws IOException, MalformedURLException, ValidationException {
    try (final InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
      return (Jsonx)Bindings.parse(in);
    }
  }

  private static org.fastjax.xml.Element toXml(final Schema schema, final Settings settings) {
    final org.fastjax.xml.Element xml = schema.toXml(settings);
    xml.getAttributes().put("xsi:schemaLocation", "http://jsonx.libx4j.org/jsonx-0.9.8.xsd " + jsonxXsd);
    return xml;
  }

  private static Schema testParseJsonx(final Jsonx controlBinding) throws IOException, SAXException {
    logger.info("  Parse XML...");
    logger.info("    a) XML(1) -> JSONX");
    final Schema controlSchema = new Schema(controlBinding);
    logger.info("    b) JSONX -> XML(2)");
    final String xml = toXml(controlSchema, Settings.DEFAULT).toString();
    final Jsonx testBinding = (Jsonx)Bindings.parse(xml);
    logger.info("    c) XML(1) == XML(2)");
    AssertXml.compare(controlBinding.toDOM(), testBinding.toDOM()).assertEqual();
    return controlSchema;
  }

  private static void writeFile(final String fileName, final String data) throws IOException {
    try (final OutputStreamWriter out = new FileWriter(new File(generatedResourcesDir, fileName))) {
      out.write("<!--\n");
      out.write("  Copyright (c) 2017 lib4j\n\n");
      out.write("  Permission is hereby granted, free of charge, to any person obtaining a copy\n");
      out.write("  of this software and associated documentation files (the \"Software\"), to deal\n");
      out.write("  in the Software without restriction, including without limitation the rights\n");
      out.write("  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell\n");
      out.write("  copies of the Software, and to permit persons to whom the Software is\n");
      out.write("  furnished to do so, subject to the following conditions:\n\n");
      out.write("  The above copyright notice and this permission notice shall be included in\n");
      out.write("  all copies or substantial portions of the Software.\n\n");
      out.write("  You should have received a copy of The MIT License (MIT) along with this\n");
      out.write("  program. If not, see <http://opensource.org/licenses/MIT/>.\n");
      out.write("-->\n");
      out.write(data);
    }
  }

  private static Schema newSchema(final ClassLoader classLoader, final String packageName) throws PackageNotFoundException {
    return new Schema(classLoader.getDefinedPackage(packageName), classLoader, c -> c.getClassLoader() == classLoader);
  }

  private static void assertSources(final Map<String,String> expected, final Map<String,String> actual) {
    assertEquals(expected.size(), actual.size());
    for (final Map.Entry<String,String> entry : expected.entrySet())
      assertEquals(entry.getValue(), actual.get(entry.getKey()));
  }

  public static void test(final String fileName) throws ClassNotFoundException, CompilationException, IOException, MalformedURLException, PackageNotFoundException, SAXException {
    logger.info(fileName + "...");
    final Jsonx controlBinding = newControlBinding(fileName);
    final Schema controlSchema = testParseJsonx(controlBinding);

    logger.info("  4) JSONX -> Java(1)");
    final Map<String,String> test1Sources = controlSchema.toSource(generatedSourcesDir);
    final InMemoryCompiler compiler = new InMemoryCompiler();
    for (final Map.Entry<String,String> entry : test1Sources.entrySet())
      compiler.addSource(entry.getValue());

    logger.info("  5) -- Java(1) Compile --");
    final ClassLoader classLoader = compiler.compile();

    logger.info("  6) Java(1) -> JSONX");
    final Schema test1Schema = newSchema(classLoader, (String)controlBinding.getPackage$().text());
    final String xml = toXml(test1Schema, Settings.DEFAULT).toString();
    logger.info("  7) Validate JSONX");
    writeFile("out-" + fileName, xml);
    Validator.validate(xml, false);

    final Schema test2Schema = testParseJsonx((Jsonx)Bindings.parse(xml));
    logger.info("  8) JSONX -> Java(2)");
    final Map<String,String> test2Sources = test2Schema.toSource();
    logger.info("  9) Java(1) == Java(2)");
    assertSources(test1Sources, test2Sources);

    testSettings(fileName, test1Sources);
  }

  private static void testSettings(final String fileName, final Map<String,String> originalSources) throws ClassNotFoundException, CompilationException, IOException, MalformedURLException, PackageNotFoundException, ValidationException {
    for (final Settings settings : SchemaTest.settings) {
      logger.info("   testSettings(\"" + fileName + "\", new Settings(" + settings.getTemplateThreshold() + "))");
      final Jsonx controlBinding = newControlBinding(fileName);
      final Schema controlSchema = new Schema(controlBinding);
      writeFile("a" + settings.getTemplateThreshold() + fileName, toXml(controlSchema, settings).toString());
      final Map<String,String> test1Sources = controlSchema.toSource(generatedSourcesDir);
      final InMemoryCompiler compiler = new InMemoryCompiler();
      for (final Map.Entry<String,String> entry : test1Sources.entrySet())
        compiler.addSource(entry.getValue());

      assertSources(originalSources, test1Sources);

      final ClassLoader classLoader = compiler.compile();
      final Schema test1Schema = newSchema(classLoader, (String)controlBinding.getPackage$().text());
      final String schema = toXml(test1Schema, settings).toString();
      writeFile("b" + settings.getTemplateThreshold() + fileName, schema);
      final Schema test2Schema = new Schema((Jsonx)Bindings.parse(schema));
      final Map<String,String> test2Sources = test2Schema.toSource();
      assertSources(test1Sources, test2Sources);
    }
  }

  @Test
  public void testArray() throws ClassNotFoundException, CompilationException, IOException, MalformedURLException, PackageNotFoundException, SAXException {
    test("array.jsonx");
  }

  @Test
  public void testDataType() throws ClassNotFoundException, CompilationException, IOException, MalformedURLException, PackageNotFoundException, SAXException {
    test("datatype.jsonx");
  }

  @Test
  public void testTemplate() throws ClassNotFoundException, CompilationException, IOException, MalformedURLException, PackageNotFoundException, SAXException {
    test("template.jsonx");
  }

  @Test
  public void testReference() throws ClassNotFoundException, CompilationException, IOException, MalformedURLException, PackageNotFoundException, SAXException {
    test("reference.jsonx");
  }

  @Test
  public void testReserved() throws ClassNotFoundException, CompilationException, IOException, MalformedURLException, PackageNotFoundException, SAXException {
    test("reserved.jsonx");
  }

  @Test
  public void testComplete() throws ClassNotFoundException, CompilationException, IOException, MalformedURLException, PackageNotFoundException, SAXException {
    test("complete.jsonx");
  }
}