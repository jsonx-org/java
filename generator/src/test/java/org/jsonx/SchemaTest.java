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
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;

import org.jaxsb.runtime.Bindings;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.Schema;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.libj.jci.CompilationException;
import org.libj.jci.InMemoryCompiler;
import org.libj.lang.Classes;
import org.libj.lang.PackageNotFoundException;
import org.libj.lang.Strings;
import org.libj.net.MemoryURLStreamHandler;
import org.libj.net.URLs;
import org.libj.test.AssertXml;
import org.libj.test.JUnitUtil;
import org.libj.util.StringPaths;
import org.openjax.json.JSON;
import org.openjax.json.JsonReader;
import org.openjax.xml.api.XmlElement;
import org.openjax.xml.sax.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

@RunWith(Parameterized.class)
public class SchemaTest {
  private static final Logger logger = LoggerFactory.getLogger(SchemaTest.class);
  private static final boolean testJson = true;
  private static final URL schemaXsd = URLs.create("http://www.jsonx.org/schema.xsd");
  private static final File generatedSourcesDir = new File("target/generated-test-sources/jsonx");
  private static final File generatedResourcesDir = new File("target/generated-test-resources");
  private static final File compiledClassesDir = new File("target/test-classes");
  private static final ArrayList<Integer> settings = new ArrayList<>();

  private static Settings getSettings(final String prefix, final int i) {
    return new Settings.Builder().withPrefix(prefix).withTemplateThreshold(i).build();
  }

  @BeforeClass
  public static void beforeClass() {
    generatedSourcesDir.mkdirs();
    generatedResourcesDir.mkdirs();
    try {
      Files
        .walk(generatedSourcesDir.toPath())
        .sorted(Comparator.reverseOrder())
        .map(Path::toFile)
        .filter(f -> !f.equals(generatedSourcesDir))
        .forEach(File::delete);
    }
    catch (final IOException e) {
      throw new ExceptionInInitializerError(e);
    }

    for (int i = 0; i < 10; i += 3) // [N]
      settings.add(i);

    settings.add(Integer.MAX_VALUE);
  }

  private static XmlElement toXml(final SchemaElement schema) {
    final XmlElement xml = schema.toXml();
    xml.getAttributes().put("xsi:schemaLocation", "http://www.jsonx.org/schema-0.4.xsd " + schemaXsd);
    return xml;
  }

  private static void validate(final String xml, final String fileName) throws IOException, SAXException {
    writeXmlFile(fileName, xml);
    try {
      Validator.validate(xml);
    }
    catch (final SAXException e) {
      if (logger.isErrorEnabled()) logger.error(xml);
      throw e;
    }
  }

  private static SchemaElement testParseSchema(final Schema controlBinding, final String prefix, final String fileName) throws IOException, SAXException {
    if (logger.isInfoEnabled()) logger.info("  Parse XML...");
    if (logger.isInfoEnabled()) logger.info("    a) XML(1) -> Schema");
    final SchemaElement controlSchema = new SchemaElement(controlBinding, new Settings.Builder().withPrefix(prefix).build());
    if (logger.isInfoEnabled()) logger.info("    b) Schema -> XML(2)");
    final String xml = toXml(controlSchema).toString();
    if (logger.isInfoEnabled()) logger.info("    c) Validate XML: c-" + fileName);
    validate(xml, "c-" + fileName);

    final Schema testBinding = (Schema)Bindings.parse(xml);
    if (logger.isInfoEnabled()) logger.info("    d) XML(1) == XML(2)");
    AssertXml.compare(controlBinding.toDOM(), testBinding.toDOM()).assertEqual(true);
    return controlSchema;
  }

  private static void writeJsonFile(final String fileName, final String data) throws IOException {
    Files.write(new File(generatedResourcesDir, fileName).toPath(), data.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
  }

  private static void writeXmlFile(final String fileName, final String data) throws IOException {
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

  private static Boolean isJdk9;

  private static boolean isJdk9() {
    if (isJdk9 == null) {
      try {
        ClassLoader.class.getMethod("getDefinedPackages");
        isJdk9 = true;
      }
      catch (final NoSuchMethodException e) {
        isJdk9 = false;
      }
    }

    return isJdk9;
  }

  private static Method getPackage;

  private static Package getPackage(final ClassLoader classLoader, final String packageName) {
    if (getPackage == null) {
      if (isJdk9()) {
        getPackage = Classes.getMethod(ClassLoader.class, "getDefinedPackage", String.class);
      }
      else {
        getPackage = Classes.getDeclaredMethodDeep(classLoader.getClass(), "getPackage", String.class);
        getPackage.setAccessible(true);
      }
    }

    try {
      return (Package)getPackage.invoke(classLoader, packageName);
    }
    catch (final IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    catch (final InvocationTargetException e) {
      final Throwable cause = e.getCause();
      if (cause instanceof RuntimeException)
        throw (RuntimeException)cause;

      throw new RuntimeException(cause);
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
    final StringBuilder b = new StringBuilder(source);
    for (int end = source.length(), start; (end = b.lastIndexOf(" **/\n", end)) != -1;) { // [N]
      start = Strings.lastIndexOf(b, '\n', end - 5);
      b.delete(start, end + 4);
      end = start;
    }

    return b.toString();
  }

  private static void assertSources(final Map<String,String> expected, final Map<String,String> actual, final boolean withComments) {
    if (expected.size() > 0)
      for (final Map.Entry<String,String> entry : expected.entrySet()) // [S]
        assertEquals(entry.getKey(), withComments ? entry.getValue() : removeComments(entry.getValue()), actual.get(entry.getKey()));

    try {
      assertEquals(expected.size(), actual.size());
    }
    catch (final AssertionError e) {
      if (expected.size() < actual.size()) {
        actual.keySet().removeAll(expected.keySet());
        if (logger.isErrorEnabled()) logger.error(actual.toString());
      }
      else {
        expected.keySet().removeAll(actual.keySet());
        if (logger.isErrorEnabled()) logger.error(expected.toString());
      }

      throw e;
    }
  }

  private static final String packagePrefix = "org.jsonx.test.";

  private static void test(final URL resource) throws CompilationException, DecodeException, IOException, PackageNotFoundException, SAXException {
    final String fileName = URLs.getName(resource);
    final String packageName = packagePrefix + StringPaths.getSimpleName(fileName);
    final String prefix = packageName + ".";

    if (logger.isInfoEnabled()) logger.info(fileName + "...");
    final Schema controlBinding = (Schema)Bindings.parse(resource);
    if (testJson) {
      final String jsd = testJson(fileName, controlBinding, new Settings.Builder().withPrefix(prefix).build());
      testConverter(jsd);
    }

    if (logger.isInfoEnabled()) logger.info("  4) Schema -> Java(1)");
    final SchemaElement controlSchema = testParseSchema(controlBinding, prefix, fileName);
    final Map<String,String> test1Sources = controlSchema.toSource(generatedSourcesDir);
    if (test1Sources.size() > 0) {
      final InMemoryCompiler compiler = new InMemoryCompiler();
      for (final Map.Entry<String,String> entry : test1Sources.entrySet()) // [S]
        compiler.addSource(entry.getValue());

      if (logger.isInfoEnabled()) logger.info("  5) -- Java(1) Compile --");
      final ClassLoader classLoader = compiler.compile(compiledClassesDir, "-g");

      if (logger.isInfoEnabled()) logger.info("  6) Java(1) -> Schema");
      final SchemaElement test1Schema = newSchema(classLoader, packageName);
      if (logger.isInfoEnabled()) logger.info("  7) Schema -> XML");
      final String xml = toXml(test1Schema).toString();
      if (logger.isInfoEnabled()) logger.info("  8) Validate XML: 8-" + fileName);
      validate(xml, "8-" + fileName);

      final SchemaElement test2Schema = testParseSchema((Schema)Bindings.parse(xml), prefix, fileName);
      if (logger.isInfoEnabled()) logger.info("  9) Schema -> Java(2)");
      final Map<String,String> test2Sources = test2Schema.toSource();
      if (logger.isInfoEnabled()) logger.info("  10) Java(1) == Java(2)");
      assertSources(test1Sources, test2Sources, false);
    }

    testSettings(resource, fileName, packageName, test1Sources);
  }

  private static void testSettings(final URL resource, final String fileName, final String packageName, final Map<String,String> originalSources) throws CompilationException, DecodeException, IOException, PackageNotFoundException, SAXException {
    final String prefix = packageName + ".";

    for (int i = 0, i$ = SchemaTest.settings.size(); i < i$; ++i) { // [RA]
      final Settings settings = getSettings(prefix, SchemaTest.settings.get(i));
      final int templateThreshold = settings.getTemplateThreshold();

      final String outFile = templateThreshold + "-" + fileName;
      final String outFile1 = "sa-" + outFile;
      final String outFile2 = "sb-" + outFile;
      if (logger.isInfoEnabled()) logger.info("   testSettings(\"" + fileName + "\", new Settings(" + templateThreshold + ")): " + outFile1 + " " + outFile2);
      final Schema controlBinding = (Schema)Bindings.parse(resource);
      final SchemaElement controlSchema = new SchemaElement(controlBinding, settings);
      final String xml1 = toXml(controlSchema).toString();
      validate(xml1, outFile1);

      final Map<String,String> test1Sources = controlSchema.toSource(generatedSourcesDir);
      if (test1Sources.size() > 0) {
        final InMemoryCompiler compiler = new InMemoryCompiler();
        for (final Map.Entry<String,String> entry : test1Sources.entrySet()) // [S]
          compiler.addSource(entry.getValue());

        assertSources(originalSources, test1Sources, true);

        final ClassLoader classLoader = compiler.compile();
        final SchemaElement test1Schema = newSchema(classLoader, packageName);
        final String xml2 = toXml(test1Schema).toString();
        validate(xml2, outFile2);

        final SchemaElement test2Schema = new SchemaElement((Schema)Bindings.parse(xml2), settings);
        final Map<String,String> test2Sources = test2Schema.toSource();
        assertSources(test1Sources, test2Sources, false);
      }

      if (testJson)
        testJson(fileName, controlBinding, settings);
    }
  }

  static void assertEquals(final String message, final Object expected, final Object actual) {
    Assert.assertNotNull(message, actual);
    Assert.assertEquals(message, expected, actual);
    if (expected != null)
      Assert.assertEquals(message, expected.hashCode(), actual.hashCode());
  }

  static void assertEquals(final Object expected, final Object actual) {
    assertEquals(null, expected, actual);
  }

  private static String testJson(final String fileName, final Schema controlBinding, final Settings settings) throws DecodeException, IOException, SAXException {
    final SchemaElement controlSchema = new SchemaElement(controlBinding, settings);
    if (logger.isInfoEnabled()) logger.info("     testJson...");
    final String outFile = fileName + ".jsd";
    if (logger.isInfoEnabled()) logger.info("       a) Schema -> JSON " + outFile);
    final String jsd = JSON.toString(controlSchema.toJson(), 2);
    writeJsonFile(outFile, jsd);
    if (logger.isInfoEnabled()) logger.info("       b) JSON -> Schema");
    final SchemaElement schema = new SchemaElement(testParseSchema(jsd, true), settings);
    if (logger.isInfoEnabled()) logger.info("       c) Schema -> XML(3)");
    final String jsdx = toXml(schema).toString();
    final Schema jsonBinding = (Schema)Bindings.parse(jsdx);
    AssertXml.compare(controlBinding.toDOM(), jsonBinding.toDOM()).assertEqual(true);
    return jsd;
  }

  private static schema.Schema testParseSchema(final String jsd, final boolean test) throws DecodeException, IOException {
    try (final JsonReader reader = new JsonReader(jsd)) {
      final schema.Schema schema1 = JxDecoder.VALIDATING.parseObject(reader, schema.Schema.class);
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

  @Parameterized.Parameters(name = "{0}")
  public static URL[] resources() throws IOException {
    return JUnitUtil.sortBySize(JUnitUtil.getResources("", ".*\\.jsdx"));
  }

  @Parameterized.Parameter(0)
  public URL resource;

  @Test
  public void test() throws CompilationException, DecodeException, IOException, PackageNotFoundException, SAXException {
    test(resource);
  }
}