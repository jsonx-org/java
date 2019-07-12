/* Copyright (c) 2018 JSONx
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.jaxsb.runtime.Bindings;
import org.jsonx.www.schema_0_2_3.xL0gluGCXYYJc;
import org.junit.Assert;
import org.junit.Test;
import org.libj.jci.CompilationException;
import org.libj.jci.InMemoryCompiler;
import org.libj.lang.PackageNotFoundException;
import org.libj.net.MemoryURLStreamHandler;
import org.libj.test.AssertXml;
import org.libj.util.Classes;
import org.openjax.json.JSON;
import org.openjax.json.JsonReader;
import org.openjax.xml.api.XmlElement;
import org.openjax.xml.sax.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class SchemaTest {
  private static final Logger logger = LoggerFactory.getLogger(SchemaTest.class);
  private static final URL schemaXsd;
  private static final File generatedSourcesDir = new File("target/generated-test-sources/jsonx");
  private static final File generatedResourcesDir = new File("target/generated-test-resources");
  private static final File compiledClassesDir = new File("target/test-classes");
  private static final List<Settings> settings = new ArrayList<>();

  static {
    generatedSourcesDir.mkdirs();
    generatedResourcesDir.mkdirs();
    try {
      schemaXsd = new URL("http://www.jsonx.org/schema.xsd");
      Files.walk(generatedSourcesDir.toPath()).sorted(Comparator.reverseOrder()).map(Path::toFile).filter(f -> !f.equals(generatedSourcesDir)).forEach(File::delete);
    }
    catch (final IOException e) {
      throw new ExceptionInInitializerError(e);
    }

    for (int i = 0; i < 10; ++i)
      settings.add(new Settings(i));

    settings.add(new Settings(Integer.MAX_VALUE));
  }

  private static xL0gluGCXYYJc.Schema newControlBinding(final String fileName) throws IOException, SAXException {
    try (final InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName)) {
      return (xL0gluGCXYYJc.Schema)Bindings.parse(in);
    }
  }

  private static XmlElement toXml(final SchemaElement schema, final Settings settings) {
    final XmlElement xml = schema.toXml(settings);
    xml.getAttributes().put("xsi:schemaLocation", "http://www.jsonx.org/schema-0.2.3.xsd " + schemaXsd);
    return xml;
  }

  private static SchemaElement testParseSchema(final xL0gluGCXYYJc.Schema controlBinding, final String prefix) throws IOException, SAXException {
    logger.info("  Parse XML...");
    logger.info("    a) XML(1) -> Schema");
    final SchemaElement controlSchema = new SchemaElement(controlBinding, prefix);
    logger.info("    b) Schema -> XML(2)");
    final String xml = toXml(controlSchema, Settings.DEFAULT).toString();
    final xL0gluGCXYYJc.Schema testBinding = (xL0gluGCXYYJc.Schema)Bindings.parse(xml);
    logger.info("    c) XML(1) == XML(2)");
    AssertXml.compare(controlBinding.toDOM(), testBinding.toDOM()).assertEqual(true);
    return controlSchema;
  }

  private static void writeFile(final String fileName, final String data) throws IOException {
    try (final OutputStreamWriter out = new FileWriter(new File(generatedResourcesDir, fileName))) {
      out.write("<!--\n");
      out.write("  Copyright (c) 2017 JSONx\n\n");
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

  // This is necessary for jdk1.8, and is replaced with ClassLoader#getDeclaredPackage() in jdk9
  private static Package getPackage(final ClassLoader classLoader, final String packageName) {
    try {
      final Method method = Classes.getDeclaredMethodDeep(classLoader.getClass(), "getPackage", String.class);
      method.setAccessible(true);
      return (Package)method.invoke(classLoader, packageName);
    }
    catch (final IllegalAccessException | InvocationTargetException e) {
      throw new IllegalStateException(e);
    }
  }

  private static SchemaElement newSchema(final ClassLoader classLoader, final String packageName) throws IOException, PackageNotFoundException {
    return new SchemaElement(getPackage(classLoader, packageName), classLoader, c -> c.getClassLoader() == classLoader);
  }

  /**
   * Removes the comments from the specified Java source.
   *
   * @param source The source.
   * @return The source without comments.
   */
  private static String removeComments(final String source) {
    final StringBuilder builder = new StringBuilder(source);
    for (int end = source.length(), start; (end = builder.lastIndexOf(" **/\n", end)) != -1;) {
      start = builder.lastIndexOf("\n", end - 5);
      builder.delete(start, end + 4);
      end = start;
    }

    return builder.toString();
  }

  private static void assertSources(final Map<String,String> expected, final Map<String,String> actual, final boolean withComments) {
    for (final Map.Entry<String,String> entry : expected.entrySet())
      assertEquals(withComments ? entry.getValue() : removeComments(entry.getValue()), actual.get(entry.getKey()));

    try {
      assertEquals(expected.size(), actual.size());
    }
    catch (final AssertionError e) {
      if (expected.size() < actual.size()) {
        actual.keySet().removeAll(expected.keySet());
        logger.error(actual.toString());
      }
      else {
        expected.keySet().removeAll(actual.keySet());
        logger.error(expected.toString());
      }

      throw e;
    }
  }

  public static void test(final String fileName, final String packageName) throws ClassNotFoundException, CompilationException, DecodeException, IOException, PackageNotFoundException, SAXException {
    final String prefix = packageName + ".";

    logger.info(fileName + "...");
    final xL0gluGCXYYJc.Schema controlBinding = newControlBinding(fileName);
    final String jsd = testJson(controlBinding, prefix);
    testConverter(jsd);

    logger.info("  4) Schema -> Java(1)");
    final SchemaElement controlSchema = testParseSchema(controlBinding, prefix);
    final Map<String,String> test1Sources = controlSchema.toSource(generatedSourcesDir);
    final InMemoryCompiler compiler = new InMemoryCompiler();
    for (final Map.Entry<String,String> entry : test1Sources.entrySet())
      compiler.addSource(entry.getValue());

    logger.info("  5) -- Java(1) Compile --");
    final ClassLoader classLoader = compiler.compile(compiledClassesDir, "-g");

    logger.info("  6) Java(1) -> Schema");
    final SchemaElement test1Schema = newSchema(classLoader, packageName);
    final String xml = toXml(test1Schema, Settings.DEFAULT).toString();
    logger.info("  7) Validate XML");
    writeFile("out-" + fileName, xml);
    try {
      Validator.validate(xml, false);
    }
    catch (final SAXException e) {
      logger.error(xml);
      throw e;
    }

    final SchemaElement test2Schema = testParseSchema((xL0gluGCXYYJc.Schema)Bindings.parse(xml), prefix);
    logger.info("  8) Schema -> Java(2)");
    final Map<String,String> test2Sources = test2Schema.toSource();
    logger.info("  9) Java(1) == Java(2)");
    assertSources(test1Sources, test2Sources, false);

    testSettings(fileName, packageName, test1Sources);
  }

  private static void testSettings(final String fileName, final String packageName, final Map<String,String> originalSources) throws ClassNotFoundException, CompilationException, DecodeException, IOException, PackageNotFoundException, SAXException {
    for (final Settings settings : SchemaTest.settings) {
      final String prefix = packageName + ".";

      logger.info("   testSettings(\"" + fileName + "\", new Settings(" + settings.getTemplateThreshold() + "))");
      final xL0gluGCXYYJc.Schema controlBinding = newControlBinding(fileName);
      final SchemaElement controlSchema = new SchemaElement(controlBinding, prefix);
      writeFile("a" + settings.getTemplateThreshold() + fileName, toXml(controlSchema, settings).toString());
      final Map<String,String> test1Sources = controlSchema.toSource(generatedSourcesDir);
      final InMemoryCompiler compiler = new InMemoryCompiler();
      for (final Map.Entry<String,String> entry : test1Sources.entrySet())
        compiler.addSource(entry.getValue());

      assertSources(originalSources, test1Sources, true);

      final ClassLoader classLoader = compiler.compile();
      final SchemaElement test1Schema = newSchema(classLoader, packageName);
      final String schema = toXml(test1Schema, settings).toString();
      writeFile("b" + settings.getTemplateThreshold() + fileName, schema);
      final SchemaElement test2Schema = new SchemaElement((xL0gluGCXYYJc.Schema)Bindings.parse(schema), prefix);
      final Map<String,String> test2Sources = test2Schema.toSource();
      assertSources(test1Sources, test2Sources, false);

      testJson(controlBinding, prefix);
    }
  }

  static void assertEquals(final String message, final Object expected, final Object actual) {
    Assert.assertEquals(message, expected, actual);
    if (expected != null && actual != null)
      Assert.assertEquals(message, expected.hashCode(), actual.hashCode());
  }

  static void assertEquals(final Object expected, final Object actual) {
    assertEquals(null, expected, actual);
  }

  private static String testJson(final xL0gluGCXYYJc.Schema controlBinding, final String prefix) throws DecodeException, IOException, SAXException {
    final SchemaElement controlSchema = new SchemaElement(controlBinding, prefix);
    logger.info("     testJson...");
    logger.info("       a) Schema -> JSON");
    final String jsd = JSON.toString(controlSchema.toJson(), 2);
    logger.info("       b) JSON -> Schema");
    final SchemaElement schema = new SchemaElement(testParseSchema(jsd, true), prefix);
    logger.info("       c) Schema -> XML(3)");
    final String jsdx = toXml(schema, Settings.DEFAULT).toString();
    final xL0gluGCXYYJc.Schema jsonBinding = (xL0gluGCXYYJc.Schema)Bindings.parse(jsdx);
    AssertXml.compare(controlBinding.toDOM(), jsonBinding.toDOM()).assertEqual(true);
    return jsd;
  }

  private static schema.Schema testParseSchema(final String jsd, final boolean test) throws DecodeException, IOException {
    try (final JsonReader reader = new JsonReader(new StringReader(jsd))) {
      final schema.Schema schema1 = JxDecoder.parseObject(schema.Schema.class, reader);
      if (test) {
        final String jsd1 = schema1.toString();
        final schema.Schema schema2 = testParseSchema(jsd1, false);
        final String jsd2 = schema2.toString();

        assertEquals(schema1, schema2);
        assertEquals(jsd1, jsd2);
      }

      return schema1;
    }
  }

  private static void testConverter(final String jsd) throws DecodeException, IOException, SAXException {
    final URL jsdUrl = MemoryURLStreamHandler.createURL(jsd.getBytes());
    final String jsdx = Converter.jsdToJsdx(jsdUrl);
    final URL jsdxUrl = MemoryURLStreamHandler.createURL(jsdx.getBytes());
    final String jsd2 = Converter.jsdxToJsd(jsdxUrl);
    assertEquals(jsd, jsd2);

    final URL jsd2Url = MemoryURLStreamHandler.createURL(jsd2.getBytes());
    final String jsdx2 = Converter.jsdToJsdx(jsd2Url);
    assertEquals(jsdx, jsdx2);
  }

  @Test
  public void testArray() throws ClassNotFoundException, CompilationException, DecodeException, IOException, PackageNotFoundException, SAXException {
    test("array.jsdx", "org.jsonx");
  }

  @Test
  public void testDataType() throws ClassNotFoundException, CompilationException, DecodeException, IOException, PackageNotFoundException, SAXException {
    test("datatype.jsdx", "org.jsonx.datatype");
  }

  @Test
  public void testStructure() throws ClassNotFoundException, CompilationException, DecodeException, IOException, PackageNotFoundException, SAXException {
    test("structure.jsdx", "org.jsonx.structure");
  }

  @Test
  public void testTemplate() throws ClassNotFoundException, CompilationException, DecodeException, IOException, PackageNotFoundException, SAXException {
    test("template.jsdx", "org.jsonx");
  }

  @Test
  public void testReference() throws ClassNotFoundException, CompilationException, DecodeException, IOException, PackageNotFoundException, SAXException {
    test("reference.jsdx", "org.jsonx.reference");
  }

  @Test
  public void testReserved() throws ClassNotFoundException, CompilationException, DecodeException, IOException, PackageNotFoundException, SAXException {
    test("reserved.jsdx", "org.jsonx.reserved");
  }

  @Test
  public void testComplete() throws ClassNotFoundException, CompilationException, DecodeException, IOException, PackageNotFoundException, SAXException {
    test("complete.jsdx", "org.jsonx.complete");
  }
}