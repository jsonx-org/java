/* Copyright (c) 2017 JSONx
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jaxsb.runtime.Bindings;
import org.jaxsb.runtime.QName;
import org.jsonx.www.schema_0_3.xL0gluGCXAA;
import org.libj.lang.PackageLoader;
import org.libj.lang.PackageNotFoundException;
import org.libj.net.URLs;
import org.libj.util.CollectionUtil;
import org.libj.util.IdentityHashSet;
import org.libj.util.Iterators;
import org.openjax.json.JsonReader;
import org.openjax.xml.api.XmlElement;
import org.openjax.xml.sax.SilentErrorHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

/**
 * The root {@link Element} of a JSON Schema Document.
 */
public final class SchemaElement extends Element implements Declarer {
  private static xL0gluGCXAA.Schema jsdToXsb(final schema.Schema jsd) {
    final xL0gluGCXAA.Schema xsb = new xL0gluGCXAA.Schema();
    for (final Map.Entry<String,Object> entry : jsd._5ba_2dZA_2dZ___5d_5b_2dA_2dZA_2dZ_5cD___5d_2a.entrySet()) {
      if (entry.getValue() instanceof schema.Array)
        xsb.addArray((xL0gluGCXAA.Schema.Array)ArrayModel.jsdToXsb((schema.Array)entry.getValue(), entry.getKey()));
      else if (entry.getValue() instanceof schema.Boolean)
        xsb.addBoolean((xL0gluGCXAA.Schema.Boolean)BooleanModel.jsdToXsb((schema.Boolean)entry.getValue(), entry.getKey()));
      else if (entry.getValue() instanceof schema.Number)
        xsb.addNumber((xL0gluGCXAA.Schema.Number)NumberModel.jsdToXsb((schema.Number)entry.getValue(), entry.getKey()));
      else if (entry.getValue() instanceof schema.ObjectType)
        xsb.addObject((xL0gluGCXAA.Schema.Object)ObjectModel.jsdToXsb((schema.Object)entry.getValue(), entry.getKey()));
      else if (entry.getValue() instanceof schema.String)
        xsb.addString((xL0gluGCXAA.Schema.String)StringModel.jsdToXsb((schema.String)entry.getValue(), entry.getKey()));
      else
        throw new UnsupportedOperationException("Unsupported type: " + entry.getValue().getClass().getName());
    }

    if (jsd.getDoc() != null && jsd.getDoc().length() > 0)
      xsb.setDoc$(new xL0gluGCXAA.$Documented.Doc$(jsd.getDoc()));

    return xsb;
  }

  /**
   * Creates a {@code SchemaElement} from the content of the specified file that
   * is expected to be in JSD format.
   *
   * @param url The {@code URL} of the content to parse.
   * @param prefix The class name prefix to be prepended to the names of
   *          generated JSD bindings.
   * @return The {@code SchemaElement} instance.
   * @throws IOException If an I/O error has occurred.
   * @throws DecodeException If a decode error has occurred.
   * @throws ValidationException If a validation error has occurred.
   * @throws NullPointerException If {@code url} of {@code prefix} is null.
   */
  public static SchemaElement parseJsd(final URL url, final String prefix) throws DecodeException, IOException {
    try (final InputStream in = url.openStream()) {
      final schema.Schema schema = JxDecoder.parseObject(schema.Schema.class, new JsonReader(new InputStreamReader(in)));
      return new SchemaElement(schema, prefix);
    }
  }

  private static ErrorHandler errorHandler;

  private static ErrorHandler getErrorHandler() {
    return errorHandler == null ? errorHandler = new SilentErrorHandler() : errorHandler;
  }

  /**
   * Creates a {@code SchemaElement} from the content of the specified file that
   * is expected to be in JSDx format.
   *
   * @param url The {@code URL} of the content to parse.
   * @param prefix The class name prefix to be prepended to the names of
   *          generated JSD bindings.
   * @return The {@code SchemaElement} instance.
   * @throws IOException If an I/O error has occurred.
   * @throws SAXException If a parse error has occurred.
   * @throws IllegalArgumentException If a {@link org.xml.sax.SAXParseException}
   *           has occurred.
   * @throws ValidationException If a validation error has occurred.
   * @throws NullPointerException If {@code url} of {@code prefix} is null.
   */
  public static SchemaElement parseJsdx(final URL url, final String prefix) throws IOException, SAXException {
    try (final InputStream in = url.openStream()) {
      final xL0gluGCXAA.Schema schema = (xL0gluGCXAA.Schema)Bindings.parse(in, getErrorHandler());
      return new SchemaElement(schema, prefix);
    }
  }

  /**
   * Creates a {@code SchemaElement} from the contents of the specified
   * {@code file}. The supported content formats are JSDx and JSD. If the
   * supplied file is not in one of the supported formats, an
   * {@code IllegalArgumentException} is thrown.
   *
   * @param url The file from which to read the contents.
   * @param prefix The class name prefix to be prepended to the names of
   *          generated JSD bindings.
   * @return A {@code SchemaElement} from the contents of the specified
   *         {@code file}.
   * @throws IllegalArgumentException If the format of the content of the
   *           specified file is malformed, or is not JSDx or JSD.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code url} of {@code prefix} is null.
   */
  public static SchemaElement parse(final URL url, final String prefix) throws IOException {
    if (URLs.getName(url).endsWith(".jsd")) {
      try {
        return parseJsd(url, prefix);
      }
      catch (final DecodeException e0) {
        try {
          return parseJsdx(url, prefix);
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
      return parseJsdx(url, prefix);
    }
    catch (final SAXException e0) {
      try {
        return parseJsd(url, prefix);
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

  private final Registry registry;
  private final String version;

  /**
   * Creates a new {@code SchemaElement} from the specified XML binding, and
   * with the provided package / class name prefix string.
   *
   * @param schema The XML binding (XSB).
   * @param prefix The class name prefix to be prepended to the names of
   *          generated JSD bindings.
   * @throws ValidationException If a cycle is detected in the object hierarchy.
   * @throws NullPointerException If {@code schema} or {@code prefix} is null.
   */
  public SchemaElement(final xL0gluGCXAA.Schema schema, final String prefix) throws ValidationException {
    super(schema.getDoc$());
    this.registry = new Registry(prefix);
    this.version = schema.name().getNamespaceURI().substring(0, schema.name().getNamespaceURI().lastIndexOf('.'));

    assertNoCycle(schema);

    final Iterator<? super xL0gluGCXAA.$Member> elementIterator = Iterators.filter(schema.elementIterator(), m -> m instanceof xL0gluGCXAA.$Member);
    while (elementIterator.hasNext()) {
      final xL0gluGCXAA.$Member member = (xL0gluGCXAA.$Member)elementIterator.next();
      if (member instanceof xL0gluGCXAA.Schema.Array)
        ArrayModel.declare(registry, this, (xL0gluGCXAA.Schema.Array)member);
      else if (member instanceof xL0gluGCXAA.Schema.Boolean)
        BooleanModel.declare(registry, this, (xL0gluGCXAA.Schema.Boolean)member);
      else if (member instanceof xL0gluGCXAA.Schema.Number)
        NumberModel.declare(registry, this, (xL0gluGCXAA.Schema.Number)member);
      else if (member instanceof xL0gluGCXAA.Schema.String)
        StringModel.declare(registry, this, (xL0gluGCXAA.Schema.String)member);
      else if (member instanceof xL0gluGCXAA.Schema.Object)
        ObjectModel.declare(registry, this, (xL0gluGCXAA.Schema.Object)member);
      else
        throw new UnsupportedOperationException("Unsupported member type: " + member.getClass().getName());
    }

    for (final Model model : registry.getModels())
      if (model instanceof Referrer)
        ((Referrer<?>)model).resolveReferences();

    registry.resolveReferences();
  }

  private static void assertNoCycle(final xL0gluGCXAA.Schema schema) throws ValidationException {
    final Function<xL0gluGCXAA.$Member,String> memberToName = obj -> {
      if (obj instanceof xL0gluGCXAA.Schema.Array)
        return ((xL0gluGCXAA.Schema.Array)obj).getName$().text();

      if (obj instanceof xL0gluGCXAA.Schema.Boolean)
        return ((xL0gluGCXAA.Schema.Boolean)obj).getName$().text();

      if (obj instanceof xL0gluGCXAA.Schema.Number)
        return ((xL0gluGCXAA.Schema.Number)obj).getName$().text();

      if (obj instanceof xL0gluGCXAA.Schema.String)
        return ((xL0gluGCXAA.Schema.String)obj).getName$().text();

      if (obj instanceof xL0gluGCXAA.Schema.Object)
        return ((xL0gluGCXAA.Schema.Object)obj).getName$().text();

      throw new UnsupportedOperationException("Unsupported member type: " + obj.getClass().getName());
    };
    final StrictRefDigraph<xL0gluGCXAA.$Member,String> digraph = new StrictRefDigraph<>("Object cannot inherit from itself", memberToName);

    final Iterator<? super xL0gluGCXAA.$Member> elementIterator = Iterators.filter(schema.elementIterator(), m -> m instanceof xL0gluGCXAA.$Member);
    while (elementIterator.hasNext()) {
      final xL0gluGCXAA.$Member member = (xL0gluGCXAA.$Member)elementIterator.next();
      if (member instanceof xL0gluGCXAA.Schema.Object) {
        final xL0gluGCXAA.Schema.Object object = (xL0gluGCXAA.Schema.Object)member;
        if (object.getExtends$() != null)
          digraph.add(object, object.getExtends$().text());
        else
          digraph.add(object);
      }
      else {
        digraph.add(member);
      }
    }

    final List<xL0gluGCXAA.$Member> cycle = digraph.getCycle();
    if (cycle != null)
      throw new ValidationException("Cycle detected in object hierarchy: " + cycle.stream().map(memberToName).collect(Collectors.joining(" -> ")));
  }

  /**
   * Creates a new {@code SchemaElement} from the specified JSD binding, and with the
   * provided package / class name prefix string.
   *
   * @param schema The JSD binding.
   * @param prefix The class name prefix to be prepended to the names of
   *          generated JSD bindings.
   * @throws ValidationException If a validation error has occurred.
   * @throws NullPointerException If {@code schema} or {@code prefix} is null.
   */
  public SchemaElement(final schema.Schema schema, final String prefix) throws ValidationException {
    this(jsdToXsb(schema), prefix);
  }

  private static Set<Class<?>> findClasses(final Package pkg, final ClassLoader classLoader, final Predicate<Class<?>> filter) throws IOException, PackageNotFoundException {
    final Set<Class<?>> classes = new HashSet<>();
    PackageLoader.getPackageLoader(classLoader).loadPackage(pkg, c -> {
      if ((JxObject.class.isAssignableFrom(c) || c.isAnnotationPresent(ArrayType.class)) && (filter == null || filter.test(c))) {
        classes.add(c);
        return true;
      }

      return false;
    });

    return classes;
  }

  /**
   * Creates a new {@code SchemaElement} by scanning the specified package in the
   * provided class loader, filtered with the given class predicate.
   *
   * @param pkg The package to be scanned for JSD bindings.
   * @param classLoader The {@code ClassLoader} containing the defined package.
   * @param filter The class {@code Predicate} allowing filtration of scanned
   *          classes.
   * @throws IOException If an I/O error has occurred.
   * @throws PackageNotFoundException If the specified package is not found.
   * @throws NullPointerException If {@code pkg} or {@code prefix} is null.
   */
  public SchemaElement(final Package pkg, final ClassLoader classLoader, final Predicate<Class<?>> filter) throws IOException, PackageNotFoundException {
    this(findClasses(pkg, classLoader, filter));
  }

  /**
   * Creates a new {@code SchemaElement} by scanning the specified package in the
   * provided class loader.
   * <p>
   * This constructor is equivalent to calling:
   * <p>
   * <blockquote>
   * {@code new Schema(pkg, classLoader, null)}
   * </blockquote>
   *
   * @param pkg The package to be scanned for JSD bindings.
   * @param classLoader The {@code ClassLoader} containing the defined package.
   * @throws IOException If an I/O error has occurred.
   * @throws PackageNotFoundException If the specified package is not found.
   */
  public SchemaElement(final Package pkg, final ClassLoader classLoader) throws IOException, PackageNotFoundException {
    this(findClasses(pkg, classLoader, null));
  }

  /**
   * Creates a new {@code SchemaElement} by scanning the specified package, filtered
   * with the given class predicate.
   * <p>
   * This constructor is equivalent to calling:
   * <p>
   * <blockquote>
   * {@code new Schema(pkg, Thread.currentThread().getContextClassLoader(), filter)}
   * </blockquote>
   *
   * @param pkg The package to be scanned for JSD bindings.
   * @param filter The class {@code Predicate} allowing filtration of scanned
   *          classes.
   * @throws IOException If an I/O error has occurred.
   * @throws PackageNotFoundException If the specified package is not found.
   */
  public SchemaElement(final Package pkg, final Predicate<Class<?>> filter) throws IOException, PackageNotFoundException {
    this(pkg, Thread.currentThread().getContextClassLoader(), filter);
  }

  /**
   * Creates a new {@code SchemaElement} by scanning the specified package.
   * <p>
   * This constructor is equivalent to calling:
   * <p>
   * <blockquote>
   * {@code new Schema(pkg, Thread.currentThread().getContextClassLoader(), null)}
   * </blockquote>
   *
   * @param pkg The package to be scanned for JSD bindings.
   * @throws IOException If an I/O error has occurred.
   * @throws PackageNotFoundException If the specified package is not found.
   */
  public SchemaElement(final Package pkg) throws IOException, PackageNotFoundException {
    this(pkg, Thread.currentThread().getContextClassLoader(), null);
  }

  /**
   * Creates a new {@code SchemaElement} by scanning the specified classes.
   *
   * @param classes The classes to scan.
   */
  public SchemaElement(final Class<?> ... classes) {
    this(CollectionUtil.asCollection(new IdentityHashSet<Class<?>>(classes.length), classes));
  }

  /**
   * Creates a new {@code SchemaElement} by scanning the specified classes.
   *
   * @param classes The classes to scan.
   */
  public SchemaElement(final Collection<Class<?>> classes) {
    super(null);
    final Registry registry = new Registry(this, classes);
    this.registry = registry;
    final QName name = xL0gluGCXAA.Schema.class.getAnnotation(QName.class);
    this.version = name.namespaceURI().substring(0, name.namespaceURI().lastIndexOf('.'));
    registry.resolveReferences();
  }

  private List<Model> rootMembers(final Settings settings) {
    final List<Model> members = new ArrayList<>();
    for (final Model model : registry.getModels())
      if (registry.isRootMember(model, settings))
        members.add(model);

    if (!registry.isFromJsd) {
      members.sort(new Comparator<Model>() {
        @Override
        public int compare(final Model o1, final Model o2) {
          return o1.compareTo(o2);
        }
      });
    }

    return members;
  }

  @Override
  XmlElement toXml(final Settings settings, final Element owner, final String packageName) {
    final List<XmlElement> elements;
    final List<Model> members = rootMembers(settings);
    if (members.size() > 0) {
      elements = new ArrayList<>();
      for (final Model member : members)
        elements.add(member.toXml(settings, this, packageName));
    }
    else {
      elements = null;
    }

    final Map<String,Object> attributes = super.toAttributes(owner, packageName);
    attributes.put("xmlns", version + ".xsd");
    attributes.put("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
    attributes.put("xsi:schemaLocation", version + ".xsd " + version + ".xsd");
    return new XmlElement("schema", attributes, elements);
  }

  public XmlElement toXml() {
    return toXml(null);
  }

  public XmlElement toXml(final Settings settings) {
    return toXml(settings == null ? Settings.DEFAULT : settings, this, registry.packageName);
  }

  @Override
  Map<String,Object> toJson(final Settings settings, final Element owner, final String packageName) {
    final List<Model> members = rootMembers(settings);
    if (members.size() == 0)
      return null;

    final Map<String,Object> properties = new LinkedHashMap<>(toAttributes(owner, packageName));
    properties.put("jx:ns", version + ".jsd");
    properties.put("jx:schemaLocation", version + ".jsd " + version + ".jsd");
    for (final Model member : members) {
      final String name = member.id.toString();
      properties.put(packageName.length() > 0 && name.startsWith(packageName) ? name.substring(packageName.length() + 1) : name, member.toJson(settings, this, packageName));
    }

    return properties;
  }

  public Map<String,Object> toJson() {
    return toJson(null);
  }

  public Map<String,Object> toJson(final Settings settings) {
    return toJson(settings == null ? Settings.DEFAULT : settings, this, registry.packageName);
  }

  public Map<String,String> toSource() {
    return toSource(Settings.DEFAULT);
  }

  public Map<String,String> toSource(final Settings settings) {
    final Map<Registry.Type,ClassSpec> all = new HashMap<>();
    final Map<Registry.Type,ClassSpec> typeToJavaClass = new HashMap<>();
    for (final Model member : registry.getModels()) {
      if (member instanceof Referrer && ((Referrer<?>)member).classType() != null) {
        final Referrer<?> model = (Referrer<?>)member;
        final ClassSpec classSpec = new ClassSpec(model, settings);
        if (model.classType().getDeclaringType() != null) {
          final Registry.Type declaringType = model.classType().getDeclaringType();
          ClassSpec parent = all.get(declaringType);
          if (parent == null) {
            parent = new ClassSpec(declaringType, settings);
            typeToJavaClass.put(declaringType, parent);
            all.put(declaringType, parent);
          }

          parent.add(classSpec);
        }
        else {
          typeToJavaClass.put(model.classType(), classSpec);
        }

        all.put(model.classType(), classSpec);
      }
    }

    final Map<String,String> sources = new HashMap<>();
    for (final Map.Entry<Registry.Type,ClassSpec> entry : typeToJavaClass.entrySet()) {
      final Registry.Type type = entry.getKey();
      final ClassSpec classSpec = entry.getValue();
      final StringBuilder builder = new StringBuilder();
      final String canonicalPackageName = type.getCanonicalPackage();
      if (canonicalPackageName != null)
        builder.append("package ").append(canonicalPackageName).append(";\n");

      final String annotation = classSpec.getAnnotation();
      if (annotation != null)
        builder.append('\n').append(annotation);

      final String doc = classSpec.getDoc();
      if (doc != null)
        builder.append('\n').append(doc);

      builder.append("\npublic ").append(classSpec);
      sources.put(type.getName(), builder.toString());
    }

    return sources;
  }

  public Map<String,String> toSource(final File dir) throws IOException {
    return toSource(dir, Settings.DEFAULT);
  }

  public Map<String,String> toSource(final File dir, final Settings settings) throws IOException {
    final Map<String,String> sources = toSource(settings);
    for (final Map.Entry<String,String> entry : sources.entrySet()) {
      final File file = new File(dir, entry.getKey().replace('.', '/') + ".java");
      file.getParentFile().mkdirs();
      Files.write(file.toPath(), entry.getValue().getBytes());
    }

    return sources;
  }
}