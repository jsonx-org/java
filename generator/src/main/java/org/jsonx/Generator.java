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

import static org.libj.lang.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.jaxsb.runtime.Bindings;
import org.jsonx.www.binding_0_4.xL1gluGCXAA.Binding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.Schema;
import org.libj.lang.Strings;
import org.libj.net.URLs;
import org.libj.util.CollectionUtil;
import org.libj.util.StringPaths;
import org.openjax.json.JsonReader;
import org.openjax.xml.sax.SilentErrorHandler;
import org.w3.www._2001.XMLSchema.yAA.$AnyType;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

/**
 * Utility for generating Java source code from JSD or JSDx schema files.
 */
public final class Generator {
  private Generator() {
  }

  private static void trapPrintUsage() {
    System.err.println("Usage: Generator <-p [NAMESPACE] PREFIX>... <-d DEST_DIR> <SCHEMA.jsd|SCHEMA.jsb|SCHEMA.jsdx|SCHEMA.jsbx>...");
    System.err.println("  -p [NAMESPACE] <PREFIX>  Package prefix of generated classes for provided namespace, recurrable.");
    System.err.println("  -d <DEST_DIR>            The destination directory.");
    System.exit(1);
  }

  public static void main(final String[] args) throws IOException {
    final int len;;
    if (args == null || (len = args.length) < 5) {
      trapPrintUsage();
      return;
    }

    final Settings.Builder settings = new Settings.Builder();
    File destDir = null;
    URL[] schemaFiles = null;
    for (int i = 0; i < len; ++i) { // [A]
      if ("-p".equals(args[i])) {
        if (schemaFiles != null)
          trapPrintUsage();

        final String namespace = args[++i];
        final String prefix = ++i < len ? args[i] : null;
        if (prefix == null) {
          settings.withDefaultPrefix(namespace);
        }
        else if (prefix.startsWith("-")) {
          settings.withDefaultPrefix(namespace);
          --i;
        }
        else {
          settings.withPrefix(namespace, prefix);
        }
      }
      else if ("-d".equals(args[i])) {
        if (++i == len)
          trapPrintUsage();

        destDir = new File(args[i]).getAbsoluteFile();
        if (schemaFiles != null)
          trapPrintUsage();
      }
      // FIXME: Remove these...
      else if ("--defaultIntegerPrimitive".equals(args[i]))
        settings.withIntegerPrimitive(args[++i]);
      else if ("--defaultIntegerObject".equals(args[i]))
        settings.withIntegerObject(args[++i]);
      else if ("--defaultRealPrimitive".equals(args[i]))
        settings.withRealPrimitive(args[++i]);
      else if ("--defaultRealObject".equals(args[i]))
        settings.withRealObject(args[++i]);
      else {
        if (schemaFiles == null)
          schemaFiles = new URL[len - i];

        schemaFiles[schemaFiles.length - len + i] = URLs.fromStringPath(args[i]);
      }
    }

    if (schemaFiles == null)
      trapPrintUsage();

    generate(destDir, settings.build(), schemaFiles);
  }

  /**
   * Creates a {@link SchemaElement} from the content of the specified file that is expected to be in JSD format.
   *
   * @param url The {@link URL} of the content to parse.
   * @param settings The {@link Settings} to be used for the parsed {@link SchemaElement}.
   * @return The {@link SchemaElement} instance.
   * @throws IOException If an I/O error has occurred.
   * @throws DecodeException If a decode error has occurred.
   * @throws ValidationException If a validation error has occurred.
   * @throws NullPointerException If {@code url} of {@code settings} is null.
   */
  static JxObject parseJson(final URL url) throws DecodeException, IOException {
    final JxObject obj;
    try (final JsonReader in = new JsonReader(new InputStreamReader(url.openStream()))) {
      obj = JxDecoder.VALIDATING.parseObject(in, schema.Schema.class, binding.Binding.class);
    }

    return obj;
  }

  /**
   * Creates a {@link SchemaElement} from the content of the specified file that is expected to be in JSB format.
   *
   * @param url The {@link URL} of the content to parse.
   * @return The {@link SchemaElement} instance.
   * @throws IOException If an I/O error has occurred.
   * @throws DecodeException If a decode error has occurred.
   * @throws ValidationException If a validation error has occurred.
   * @throws NullPointerException If {@code url} of {@code settings} is null.
   */
  private static binding.Binding getSchema(final URL url, final binding.Binding binding) throws DecodeException, IOException {
    final String pathOfSchemaFromInclude = null;
    final URL schemaUrl = StringPaths.isAbsolute(pathOfSchemaFromInclude) ? URLs.create(pathOfSchemaFromInclude) : URLs.toCanonicalURL(StringPaths.getCanonicalParent(url.toString()));
    try (final JsonReader in = new JsonReader(new InputStreamReader(schemaUrl.openStream()))) {
      binding.set40schema(JxDecoder.VALIDATING.parseObject(in, schema.Schema.class));
    }

    return binding;
  }

  private static ErrorHandler errorHandler;

  private static ErrorHandler getErrorHandler() {
    return errorHandler == null ? errorHandler = new SilentErrorHandler() : errorHandler;
  }

  /**
   * Creates a {@link SchemaElement} from the content of the specified file that is expected to be in JSDx format.
   *
   * @param url The {@link URL} of the content to parse.
   * @param settings The {@link Settings} to be used for the parsed {@link SchemaElement}.
   * @return The {@link SchemaElement} instance.
   * @throws IOException If an I/O error has occurred.
   * @throws SAXException If a parse error has occurred.
   * @throws IllegalArgumentException If a {@link org.xml.sax.SAXParseException} has occurred.
   * @throws ValidationException If a validation error has occurred.
   * @throws NullPointerException If {@code url} of {@code settings} is null.
   */
  static $AnyType<?> parseXml(final URL url) throws IOException, SAXException {
    return Bindings.parse(url, getErrorHandler());
  }

  private static Object parse(final URL url) throws IOException { // [A]
    final String name = URLs.getName(url);
    if (name.endsWith(".jsd") || name.endsWith(".jsb") || name.endsWith(".json")) {
      try {
        return parseJson(url);
      }
      catch (final DecodeException e0) {
        try {
          return parseXml(url);
        }
        catch (final IOException | RuntimeException e1) {
          e1.addSuppressed(e0);
          throw e1;
        }
        catch (final Exception e1) {
          final IllegalArgumentException e = new IllegalArgumentException(e0);
          e.addSuppressed(e1);
          throw e;
        }
      }
    }

    try {
      return parseXml(url);
    }
    catch (final SAXException e0) {
      try {
        return parseJson(url);
      }
      catch (final IOException | RuntimeException e1) {
        e1.addSuppressed(e0);
        throw e1;
      }
      catch (final Exception e1) {
        final IllegalArgumentException e = new IllegalArgumentException(e1);
        e.addSuppressed(e0);
        throw e;
      }
    }
  }

  private static String[] getImports(final Schema schema) {
    final List<Schema.Import> imports = schema.getImport();
    if (imports.size() == 0)
      return Strings.EMPTY_ARRAY;

    final String[] namespaces = new String[imports.size()];
    for (int i = 0, i$ = imports.size(); i < i$; ++i) // [RA]
      namespaces[i] = imports.get(i).getNamespace$().text().toString();

    return namespaces;
  }

  private static String[] getImports(final schema.Schema schema) {
    final List<schema.Import> imports = schema.get40imports();
    if (imports.size() == 0)
      return Strings.EMPTY_ARRAY;

    final String[] namespaces = new String[imports.size()];
    final Iterator<schema.Import> iterator = imports.iterator();
    for (int i = 0, i$ = imports.size(); i < i$; ++i) // [RA]
      namespaces[i] = iterator.next().get40namespace();

    return namespaces;
  }

  private static String[] getImports(final Object o) {
    if (o instanceof Schema)
      return getImports((Schema)o);

    if (o instanceof Binding)
      return getImports(((Binding)o).getJxSchema());

    if (o instanceof schema.Schema)
      return getImports((schema.Schema)o);

    if (o instanceof binding.Binding)
      return getImports(((binding.Binding)o).get40schema());

    throw new IllegalArgumentException("Unsupported object of class: " + o.getClass().getName());
  }

  /**
   * Creates a {@link SchemaElement} from the contents of the specified {@code file}. The supported content formats are JSDx and
   * JSD. If the supplied file is not in one of the supported formats, an {@link IllegalArgumentException} is thrown.
   *
   * @param url The file from which to read the contents.
   * @param settings The {@link Settings} to be used for the parsed {@link SchemaElement}.
   * @return A {@link SchemaElement} from the contents of the specified {@code file}.
   * @throws IOException If an I/O error has occurred.
   * @throws IllegalArgumentException If the format of the content of the specified file is malformed, or is not JSDx or JSD.
   * @throws IllegalArgumentException If {@code urls} is an empty array.
   * @throws NullPointerException If {@code url} of {@code settings} is null.
   */
  public static void generate(final File destDir, final Settings settings, final URL ... urls) throws IOException {
    assertNotEmpty(urls);
    if (urls.length == 1) {
      SchemaElement.parse(new HashMap<>(1), parse(urls[0]), settings);
      return;
    }

    final StrictRefDigraph<Object,String> digraph = new StrictRefDigraph<>("Schema cannot include itself", o -> {
      if (o instanceof Schema)
        return ((Schema)o).getTargetNamespace$().text();

      if (o instanceof Binding)
        return ((Binding)o).getJxSchema().getTargetNamespace$().text();

      if (o instanceof schema.Schema)
        return ((schema.Schema)o).get40targetNamespace();

      if (o instanceof binding.Binding)
        return ((binding.Binding)o).get40schema().get40targetNamespace();

      throw new IllegalArgumentException("Unsupported object of class: " + o.getClass().getName());
    });

    for (int i = 0, i$ = urls.length; i < i$; ++i) { // [A]
      final Object schema = parse(urls[i]);
      for (final String _import : getImports(schema))
        digraph.add(schema, _import);
    }

    if (digraph.hasCycle())
      throw new IllegalStateException("Cycle exists in schema hierarchy: " + CollectionUtil.toString(digraph.getCycle(), " -> "));

    final HashMap<String,Registry> namespaceToRegistry = new HashMap<>();
    final ArrayList<Object> topologicalOrder = digraph.getTopologicalOrder();
    for (int i = topologicalOrder.size() - 1; i >= 0; --i) // [RA]
      SchemaElement.parse(namespaceToRegistry, topologicalOrder.get(i), settings).toSource(destDir);
  }
}