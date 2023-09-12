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
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jaxsb.runtime.Bindings;
import org.jsonx.www.binding_0_4.xL1gluGCXAA.Binding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.Schema;
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
    System.err.println("Usage: Generator <-p [NAMESPACE] PACKAGE>... <-d DEST_DIR> <SCHEMA.jsd|SCHEMA.jsdx|BINDING.jsb|BINDING.jsbx>...");
    System.err.println("  -p [NAMESPACE] <PACKAGE>  Package prefix of generated classes for provided namespace, recurrable.");
    System.err.println("  -d <DEST_DIR>             The destination directory.");
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
        final String pkg = ++i < len ? args[i] : null;
        if (pkg == null) {
          settings.withDefaultPackage(namespace);
        }
        else if (pkg.startsWith("-")) {
          settings.withDefaultPackage(namespace);
          --i;
        }
        else {
          settings.withNamespacePackage(namespace, pkg);
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
   * @return The {@link JxObject} representation of the parsed file.
   * @throws IOException If an I/O error has occurred.
   * @throws DecodeException If a decode error has occurred.
   * @throws ValidationException If a validation error has occurred.
   * @throws NullPointerException If {@code url} or {@code settings} is null.
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
   * @throws NullPointerException If {@code url} or {@code settings} is null.
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
   * @return The {@link $AnyType} representation of the parsed file.
   * @throws IOException If an I/O error has occurred.
   * @throws SAXException If a parse error has occurred.
   * @throws IllegalArgumentException If a {@link org.xml.sax.SAXParseException} has occurred.
   * @throws ValidationException If a validation error has occurred.
   * @throws NullPointerException If {@code url} or {@code settings} is null.
   */
  static $AnyType<?> parseXml(final URL url) throws IOException, SAXException {
    return Bindings.parse(url, getErrorHandler());
  }

  private static $AnyType<?> parse(final URL url) throws IOException { // [A]
    final String name = URLs.getName(url);
    if (name.endsWith(".jsd") || name.endsWith(".jsb") || name.endsWith(".json")) {
      try {
        return SchemaElement.jxToXsb(url, parseJson(url));
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
        return SchemaElement.jxToXsb(url, parseJson(url));
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

  private static HashMap<String,URL> getImports(final URL location, final Schema schema) {
    final List<Schema.Import> imports = schema.getImport();
    if (imports == null || imports.size() == 0)
      return null;

    final HashMap<String,URL> namespaces = new HashMap<>(imports.size());
    for (int i = 0, i$ = imports.size(); i < i$; ++i) { // [RA]
      final Schema.Import import1 = imports.get(i);
      namespaces.put(import1.getNamespace$().text().toString(), getLocation(location, import1.getSchemaLocation$().text().toString()));
    }

    return namespaces;
  }

  private static HashMap<String,URL> getImports(final URL location, final $AnyType<?> xsb) {
    if (xsb instanceof Schema)
      return getImports(location, (Schema)xsb);

    if (xsb instanceof Binding)
      return getImports(location, ((Binding)xsb).getJxSchema());

    throw new IllegalArgumentException("Unsupported object of class: " + xsb.getClass().getName());
  }

  static URL getLocation(final URL referer, final String path) {
    return StringPaths.isAbsolute(path) ? URLs.toCanonicalURL(path) : URLs.toCanonicalURL(URLs.getCanonicalParent(referer) + "/" + path);
  }

  private static String getTargetNamespace(final $AnyType<?> xsb) {
    final Schema.TargetNamespace$ targetNamespace;
    if (xsb instanceof Schema)
      targetNamespace = ((Schema)xsb).getTargetNamespace$();
    else if (xsb instanceof Binding)
      targetNamespace = ((Binding)xsb).getJxSchema().getTargetNamespace$();
    else
      throw new IllegalArgumentException("Unsupported object of class: " + xsb.getClass().getName());

    return targetNamespace != null ? targetNamespace.text() : "";
  }

  private static void addImport(final StrictRefDigraph<$AnyType<?>,String> digraph, final ArrayList<$AnyType<?>> schemasWithoutTargetNamespace, final IdentityHashMap<$AnyType<?>,URL> schemaToLocation, final Set<URL> addedLocations, final HashMap<String,URL> namespaceToLocation, final URL location) throws IOException {
    if (!addedLocations.add(location))
      return;

    final $AnyType<?> schema = parse(location);
    schemaToLocation.put(schema, location);
    final String targetNamespace = getTargetNamespace(schema);
    if (targetNamespace.length() == 0) {
      if (schemasWithoutTargetNamespace == null)
        throw new ValidationException("Imported schema does not declare a targetNamespace: " + location);

      schemasWithoutTargetNamespace.add(schema);
      return;
    }

    namespaceToLocation.put(targetNamespace, location);
    final HashMap<String,URL> imports = getImports(location, schema);
    if (imports == null) {
      digraph.add(schema);
    }
    else {
      for (final Map.Entry<String,URL> entry : imports.entrySet()) { // [S]
        final String namespace = entry.getKey();
        final URL newLocation = entry.getValue();
        namespaceToLocation.put(namespace, newLocation);
        addImport(digraph, null, schemaToLocation, addedLocations, namespaceToLocation, newLocation);
        digraph.add(schema, namespace);
      }
    }
  }

  static SchemaElement[] parse(final Settings settings, final URL ... urls) throws IOException {
    assertNotEmpty(urls);

    final StrictRefDigraph<$AnyType<?>,String> digraph = new StrictRefDigraph<>("Schema cannot include itself", Generator::getTargetNamespace);

    final IdentityHashMap<$AnyType<?>,URL> schemaToLocation = new IdentityHashMap<>();
    final ArrayList<$AnyType<?>> schemasWithoutTargetNamespace = new ArrayList<>();
    for (int i = 0, i$ = urls.length; i < i$; ++i) // [A]
      addImport(digraph, schemasWithoutTargetNamespace, schemaToLocation, new HashSet<>(), new HashMap<String,URL>() {
        @Override
        public URL put(final String key, final URL value) {
          final URL old = super.put(key, value);
          if (old != null && !old.equals(value))
            throw new IllegalArgumentException("Imports for namespace \"" + key + "\" conflicting locations: {\"" + old + "\", \"" + value + "\"}");

          return old;
        }
      }, urls[i]);

    if (digraph.hasCycle())
      throw new IllegalStateException("Cycle exists in schema hierarchy: " + CollectionUtil.toString(digraph.getCycle(), " -> "));

    final ArrayList<$AnyType<?>> topologicalOrder = digraph.getTopologicalOrder();
    final int sizeDigraph = topologicalOrder.size();
    final int sizeNoNs = schemasWithoutTargetNamespace.size();
    final SchemaElement[] schemas = new SchemaElement[sizeDigraph + sizeNoNs];
    int i = sizeDigraph - 1;
    int j = 0;

    if (sizeDigraph > 0) {
      final HashMap<String,Registry> namespaceToRegistry = new HashMap<>();
      do { // [A]
        final $AnyType<?> schema = topologicalOrder.get(i);
        schemas[j++] = SchemaElement.parse(namespaceToRegistry, schemaToLocation.get(schema), schema, settings);
      }
      while (--i >= 0);
    }

    if (sizeNoNs > 0) {
      i = 0; do { // [RA]
        final $AnyType<?> schema = schemasWithoutTargetNamespace.get(i);
        schemas[j++] = SchemaElement.parse(new HashMap<>(), schemaToLocation.get(schema), schema, settings);
      }
      while (++i < sizeNoNs);
    }

    return schemas;
  }

  /**
   * Generates the sources in the specified {@code destDir}, utilizing the given {@link Settings}, for the provided file
   * {@code urls}.
   *
   * @param destDir The destination directory of the generated sources.
   * @param settings The {@link Settings} to be used when constructing {@link SchemaElement}s for the provided {@code urls}.
   * @param urls The {@link URL}s of the JSDx, JSBx, JSD or JSB documents to generate.
   * @throws IOException If an I/O error has occurred.
   * @throws IllegalArgumentException If the format of the content of the specified file is malformed, or is not JSDx, JSBx, JSD, or
   *           JSB.
   * @throws IllegalArgumentException If {@code urls} is an empty array.
   * @throws NullPointerException If {@code urls} or {@code settings} is null.
   */
  public static void generate(final File destDir, final Settings settings, final URL ... urls) throws IOException {
    final SchemaElement[] schemas = parse(settings, urls);
    for (final SchemaElement schema : schemas) // [A]
      schema.toSource(destDir);
  }
}