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
import java.util.HashMap;
import java.util.Map;

import org.jaxsb.runtime.Bindings;
import org.jsonx.www.binding_0_5.xL1gluGCXAA.Binding;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.libj.jci.CompilationException;
import org.libj.jci.InMemoryCompiler;
import org.libj.lang.Classes;
import org.libj.lang.PackageNotFoundException;
import org.libj.lang.Strings;
import org.libj.net.MemoryURLStreamHandler;
import org.libj.net.URLs;
import org.libj.test.AssertXml;
import org.libj.util.StringPaths;
import org.openjax.json.JSON;
import org.openjax.json.JsonReader;
import org.openjax.xml.sax.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3.www._2001.XMLSchema.yAA.$AnyType;
import org.xml.sax.SAXException;

abstract class ModelTest {
  static final Logger logger = LoggerFactory.getLogger(ModelTest.class);
  private static final boolean testValidate = true;
  private static final boolean testJson = true;
  private static final String namespacePackage = "org.jsonx.test.";
  static final File generatedSourcesDir = new File("target/generated-test-sources/jsonx");
  private static final File generatedResourcesDir = new File("target/generated-test-resources");
  private static final File compiledClassesDir = new File("target/test-classes");
  private static final ArrayList<Integer> settings = new ArrayList<>();
  private static final boolean deleteAllBeforeTest = false;

  private static Settings getSettings(final String namespace, final String pkg, final int i) {
    return new Settings.Builder().withNamespacePackage(namespace, pkg).withTemplateThreshold(i).build();
  }

  @BeforeClass
  public static void beforeClass() {
    generatedSourcesDir.mkdirs();
    generatedResourcesDir.mkdirs();
    if (deleteAllBeforeTest) {
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
    }

    for (int i = 0; i < 10; i += 3) // [N]
      settings.add(i);

    settings.add(Integer.MAX_VALUE);
  }

  private static void validate(final String xml, final String fileName) throws IOException, SAXException {
    if (!testValidate)
      return;

    writeXmlFile(fileName, xml);
    try {
      Validator.validate(xml);
    }
    catch (final SAXException e) {
      System.err.println(xml);
      if (logger.isErrorEnabled()) { logger.error(xml); }
      throw e;
    }
  }

  private static SchemaElement testParseSchema(final URL location, final $AnyType<?> control, final String namespace, final String pkg, final String fileName) throws IOException, SAXException {
    if (logger.isInfoEnabled()) { logger.info("  Parse XML..."); }
    if (logger.isInfoEnabled()) { logger.info("    a) XML(1) -> Schema"); }
    final Settings settings = new Settings.Builder().withNamespacePackage(namespace, pkg).build();
    final SchemaElement model = new SchemaElement(new HashMap<>(), location, control, settings);

    if (logger.isInfoEnabled()) { logger.info("    b) Schema -> XML(2)"); }
    final String xml = model.toXml().toString();
    if (logger.isInfoEnabled()) { logger.info("    c) Validate XML: c-" + fileName); }
    validate(xml, "c-" + fileName);

    final $AnyType<?> test = Bindings.parse(xml);

    if (logger.isInfoEnabled()) { logger.info("    d) XML(1) == XML(2)"); }
    AssertXml.compare(control.toDOM(), test.toDOM()).assertEqual(true);

    return model;
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
    return new SchemaElement(new HashMap<>(), getPackage(classLoader, packageName), classLoader, c -> c.getClassLoader() == classLoader);
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
        if (logger.isErrorEnabled()) { logger.error(actual.toString()); }
      }
      else {
        expected.keySet().removeAll(actual.keySet());
        if (logger.isErrorEnabled()) { logger.error(expected.toString()); }
      }

      throw e;
    }
  }

  static void test(final URL resource) throws CompilationException, DecodeException, IOException, PackageNotFoundException, SAXException {
    final String fileName = URLs.getName(resource);
    final String namespace = "urn:test:" + StringPaths.getSimpleName(fileName);
    final String packageName = namespacePackage + StringPaths.getSimpleName(fileName);
    final String pkg = packageName + ".";

    final $AnyType<?> control = Bindings.parse(resource);
    if (logger.isInfoEnabled()) { logger.info(fileName + "..."); }

    if (testJson)
      testConverter(testJson(resource, fileName, control, new Settings.Builder().withNamespacePackage(namespace, pkg).build()));

    if (logger.isInfoEnabled()) { logger.info("  4) Schema -> Java(1)"); }
    final SchemaElement schema = testParseSchema(resource, control, namespace, pkg, fileName);
    final Map<String,String> sources1 = schema.toSource(generatedSourcesDir);
    if (sources1.size() > 0) {
      final InMemoryCompiler compiler = new InMemoryCompiler();
      for (final Map.Entry<String,String> entry : sources1.entrySet()) // [S]
        compiler.addSource(entry.getValue());

      if (logger.isInfoEnabled()) { logger.info("  5) -- Java(1) Compile --"); }
      final ClassLoader classLoader = compiler.compile(compiledClassesDir, "-g");

      if (logger.isInfoEnabled()) { logger.info("  6) Java(1) -> Schema"); }
      final SchemaElement schema1 = newSchema(classLoader, packageName);
      if (logger.isInfoEnabled()) { logger.info("  7) Schema -> XML"); }
      final String xml = schema1.toXml().toString();
      if (logger.isInfoEnabled()) { logger.info("  8) Validate XML: 8-" + fileName); }
      validate(xml, "8-" + fileName);

      final SchemaElement schema2 = testParseSchema(resource, Bindings.parse(xml), namespace, pkg, fileName);

      if (logger.isInfoEnabled()) { logger.info("  9) Schema -> Java(2)"); }
      final Map<String,String> sources2 = schema2.toSource();
      if (logger.isInfoEnabled()) { logger.info("  10) Java(1) == Java(2)"); }
      assertSources(sources1, sources2, false);
    }

    testSettings(resource, fileName, namespace, packageName, sources1);
  }

  private static void testSettings(final URL resource, final String fileName, final String namespace, final String packageName, final Map<String,String> originalSources) throws CompilationException, DecodeException, IOException, PackageNotFoundException, SAXException {
    final String pkg = packageName + ".";

    for (int i = 0, i$ = ModelTest.settings.size(); i < i$; ++i) { // [RA]
      final Settings settings = getSettings(namespace, pkg, ModelTest.settings.get(i));
      final int templateThreshold = settings.getTemplateThreshold();

      final String outFile = templateThreshold + "-" + fileName;
      final String outFile1 = "sa-" + outFile;
      final String outFile2 = "sb-" + outFile;
      if (logger.isInfoEnabled()) { logger.info("   testSettings(\"" + fileName + "\", new Settings(" + templateThreshold + ")): " + outFile1 + " " + outFile2); }
      final $AnyType<?> control = Bindings.parse(resource);
      final SchemaElement schema = new SchemaElement(new HashMap<>(), resource, control, settings);

      final String xml1 = schema.toXml().toString();
      validate(xml1, outFile1);

      final Map<String,String> test1Sources = schema.toSource(generatedSourcesDir);
      if (test1Sources.size() > 0) {
        final InMemoryCompiler compiler = new InMemoryCompiler();
        for (final Map.Entry<String,String> entry : test1Sources.entrySet()) // [S]
          compiler.addSource(entry.getValue());

        assertSources(originalSources, test1Sources, true);

        final ClassLoader classLoader = compiler.compile();
        final SchemaElement test1Schema = newSchema(classLoader, packageName);
        final String xml2 = test1Schema.toXml().toString();
        validate(xml2, outFile2);

        final SchemaElement test2Schema = new SchemaElement(new HashMap<>(), resource, Bindings.parse(xml2), settings);
        final Map<String,String> test2Sources = test2Schema.toSource();
        assertSources(test1Sources, test2Sources, false);
      }

      if (testJson)
        testJson(resource, fileName, control, settings);
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

  private static String testJson(final URL location, final String fileName, final $AnyType<?> control, final Settings settings) throws DecodeException, IOException, SAXException {
    final SchemaElement schema = new SchemaElement(new HashMap<>(), location, control, settings);
    if (logger.isInfoEnabled()) { logger.info("     testJson..."); }
    final String outFile = fileName + (control instanceof Binding ? ".jsb" : ".jsd");

    if (logger.isInfoEnabled()) { logger.info("       a) Schema -> JSON " + outFile); }
    final String json = JSON.toString(schema.toJson(), 2);
    writeJsonFile(outFile, json);

    if (logger.isInfoEnabled()) { logger.info("       b) JSON -> Schema"); }
    final JxObject binding = testXml(control instanceof Binding ? binding.Binding.class : schema.Schema.class, json, true);

    if (logger.isInfoEnabled()) { logger.info("       c) Schema -> XML(3)"); }
    final String xml = new SchemaElement(new HashMap<>(), location, binding, settings).toXml().toString();
    final $AnyType<?> xsb = Bindings.parse(xml);
    AssertXml.compare(control.toDOM(), xsb.toDOM()).assertEqual(true);

    return json;
  }

  private static <T extends JxObject>T testXml(final Class<? extends T> type, final String json, final boolean test) throws DecodeException, IOException {
    try (final JsonReader reader = new JsonReader(json)) {
      final T binding1 = JxDecoder.VALIDATING.parseObject(reader, type);
      if (test) {
        final String jsb1 = binding1.toString();
        final T binding2 = testXml(type, jsb1, false);
        final String jsb2 = binding2.toString();

        assertEquals(binding1, binding2);
        assertEquals(jsb1, jsb2);
      }

      return binding1;
    }
  }

  private static void testConverter(final String json) throws DecodeException, IOException, SAXException {
    final URL jsonUrl = MemoryURLStreamHandler.createURL(json.getBytes());
    final String xml = Converter.jsonToXml(jsonUrl);
    final URL xmlUrl = MemoryURLStreamHandler.createURL(xml.getBytes());
    final String json2 = Converter.xmlToJson(xmlUrl);
    assertEquals(json, json2);

    final URL jsd2Url = MemoryURLStreamHandler.createURL(json2.getBytes());
    final String jsdx2 = Converter.jsonToXml(jsd2Url);
    assertEquals(xml, jsdx2);
  }
}