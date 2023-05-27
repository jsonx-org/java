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
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jaxsb.runtime.Bindings;
import org.jaxsb.runtime.QName;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Documented;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Member;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$ObjectMember.Extends$;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.Schema;
import org.libj.lang.PackageLoader;
import org.libj.lang.PackageNotFoundException;
import org.libj.lang.WrappedArrayList;
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
  private static Schema jsdToXsb(final schema.Schema jsd) {
    final Schema xsb = new Schema();
    final LinkedHashMap<String,? extends schema.Member> declarations = jsd.getDeclarations();
    if (declarations != null && declarations.size() > 0) {
      for (final Map.Entry<String,? extends schema.Member> entry : declarations.entrySet()) { // [S]
        final String name = entry.getKey();
        final schema.Member declaration = entry.getValue();
        if (declaration instanceof schema.Array)
          xsb.addArray((Schema.Array)ArrayModel.jsdToXsb((schema.Array)declaration, name));
        else if (declaration instanceof schema.Boolean)
          xsb.addBoolean((Schema.Boolean)BooleanModel.jsdToXsb((schema.Boolean)declaration, name));
        else if (declaration instanceof schema.Number)
          xsb.addNumber((Schema.Number)NumberModel.jsdToXsb((schema.Number)declaration, name));
        else if (declaration instanceof schema.ObjectType)
          xsb.addObject((Schema.Object)ObjectModel.jsdToXsb((schema.Object)declaration, name));
        else if (declaration instanceof schema.String)
          xsb.addString((Schema.String)StringModel.jsdToXsb((schema.String)declaration, name));
        else
          throw new UnsupportedOperationException("Unsupported type: " + declaration.getClass().getName());
      }
    }

    final String doc = jsd.getDoc();
    if (doc != null && doc.length() > 0)
      xsb.setDoc$(new $Documented.Doc$(doc));

    return xsb;
  }

  /**
   * Creates a {@link SchemaElement} from the content of the specified file that is expected to be in JSD format.
   *
   * @param url The {@link URL} of the content to parse.
   * @param prefix The class name prefix to be prepended to the names of generated JSD bindings.
   * @return The {@link SchemaElement} instance.
   * @throws IOException If an I/O error has occurred.
   * @throws DecodeException If a decode error has occurred.
   * @throws ValidationException If a validation error has occurred.
   * @throws NullPointerException If {@code url} of {@code prefix} is null.
   */
  public static SchemaElement parseJsd(final URL url, final String prefix) throws DecodeException, IOException {
    try (final JsonReader in = new JsonReader(new InputStreamReader(url.openStream()))) {
      return new SchemaElement(JxDecoder.VALIDATING.parseObject(in, schema.Schema.class), prefix);
    }
  }

  private static ErrorHandler errorHandler;

  private static ErrorHandler getErrorHandler() {
    return errorHandler == null ? errorHandler = new SilentErrorHandler() : errorHandler;
  }

  /**
   * Creates a {@link SchemaElement} from the content of the specified file that is expected to be in JSDx format.
   *
   * @param url The {@link URL} of the content to parse.
   * @param prefix The class name prefix to be prepended to the names of generated JSD bindings.
   * @return The {@link SchemaElement} instance.
   * @throws IOException If an I/O error has occurred.
   * @throws SAXException If a parse error has occurred.
   * @throws IllegalArgumentException If a {@link org.xml.sax.SAXParseException} has occurred.
   * @throws ValidationException If a validation error has occurred.
   * @throws NullPointerException If {@code url} of {@code prefix} is null.
   */
  public static SchemaElement parseJsdx(final URL url, final String prefix) throws IOException, SAXException {
    final Schema schema = (Schema)Bindings.parse(url, getErrorHandler());
    return new SchemaElement(schema, prefix);
  }

  /**
   * Creates a {@link SchemaElement} from the contents of the specified {@code file}. The supported content formats are JSDx and
   * JSD. If the supplied file is not in one of the supported formats, an {@link IllegalArgumentException} is thrown.
   *
   * @param url The file from which to read the contents.
   * @param prefix The class name prefix to be prepended to the names of generated JSD bindings.
   * @return A {@link SchemaElement} from the contents of the specified {@code file}.
   * @throws IllegalArgumentException If the format of the content of the specified file is malformed, or is not JSDx or JSD.
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
   * Creates a new {@link SchemaElement} from the specified XML binding, and with the provided package / class name prefix string.
   *
   * @param schema The XML binding (XSB).
   * @param prefix The class name prefix to be prepended to the names of generated JSD bindings.
   * @throws ValidationException If a cycle is detected in the object hierarchy.
   * @throws NullPointerException If {@code schema} or {@code prefix} is null.
   */
  public SchemaElement(final Schema schema, final String prefix) throws ValidationException {
    super(null, schema.getDoc$());
    this.registry = new Registry(prefix);
    this.version = schema.name().getNamespaceURI().substring(0, schema.name().getNamespaceURI().lastIndexOf('.'));

    assertNoCycle(schema);

    final Iterator<? super $Member> elementIterator = Iterators.filter(schema.elementIterator(), m -> m instanceof $Member);
    while (elementIterator.hasNext()) {
      final $Member member = ($Member)elementIterator.next();
      if (member instanceof Schema.Array)
        ArrayModel.declare(registry, this, (Schema.Array)member);
      else if (member instanceof Schema.Boolean)
        BooleanModel.declare(registry, this, (Schema.Boolean)member);
      else if (member instanceof Schema.Number)
        NumberModel.declare(registry, this, (Schema.Number)member);
      else if (member instanceof Schema.String)
        StringModel.declare(registry, this, (Schema.String)member);
      else if (member instanceof Schema.Object)
        ObjectModel.declare(registry, this, (Schema.Object)member);
      else
        throw new UnsupportedOperationException("Unsupported member type: " + member.getClass().getName());
    }

    final Collection<Model> models = registry.getModels();
    final int size = models.size();
    if (size > 0)
      for (final Model model : models) // [C]
        if (model instanceof Referrer)
          ((Referrer<?>)model).resolveReferences();

    registry.resolveReferences();

    if (size > 0)
      for (final Model model : models) // [C]
        if (model instanceof Referrer)
          ((Referrer<?>)model).resolveOverrides();
  }

  private static String memberToName(final $Member obj) {
    if (obj instanceof Schema.Array)
      return ((Schema.Array)obj).getName$().text();

    if (obj instanceof Schema.Boolean)
      return ((Schema.Boolean)obj).getName$().text();

    if (obj instanceof Schema.Number)
      return ((Schema.Number)obj).getName$().text();

    if (obj instanceof Schema.String)
      return ((Schema.String)obj).getName$().text();

    if (obj instanceof Schema.Object)
      return ((Schema.Object)obj).getName$().text();

    throw new UnsupportedOperationException("Unsupported member type: " + obj.getClass().getName());
  };

  private static void assertNoCycle(final Schema schema) throws ValidationException {
    final StrictRefDigraph<$Member,String> digraph = new StrictRefDigraph<>("Object cannot inherit from itself", SchemaElement::memberToName);

    final Iterator<? super $Member> elementIterator = Iterators.filter(schema.elementIterator(), m -> m instanceof $Member);
    while (elementIterator.hasNext()) {
      final $Member member = ($Member)elementIterator.next();
      if (member instanceof Schema.Object) {
        final Schema.Object object = (Schema.Object)member;
        final Extends$ extends$ = object.getExtends$();
        if (extends$ != null)
          digraph.add(object, extends$.text());
        else
          digraph.add(object);
      }
      else {
        digraph.add(member);
      }
    }

    final List<$Member> cycle = digraph.getCycle();
    if (cycle != null)
      throw new ValidationException("Cycle detected in object hierarchy: " + cycle.stream().map(SchemaElement::memberToName).collect(Collectors.joining(" -> ")));
  }

  /**
   * Creates a new {@link SchemaElement} from the specified JSD binding, and with the provided package / class name prefix string.
   *
   * @param schema The JSD binding.
   * @param prefix The class name prefix to be prepended to the names of generated JSD bindings.
   * @throws ValidationException If a validation error has occurred.
   * @throws NullPointerException If {@code schema} or {@code prefix} is null.
   */
  public SchemaElement(final schema.Schema schema, final String prefix) throws ValidationException {
    this(jsdToXsb(schema), prefix);
  }

  private static Set<Class<?>> findClasses(final Package pkg, final ClassLoader classLoader, final Predicate<? super Class<?>> filter) throws IOException, PackageNotFoundException {
    final HashSet<Class<?>> classes = new HashSet<>();
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
   * Creates a new {@link SchemaElement} by scanning the specified package in the provided class loader, filtered with the given
   * class predicate.
   *
   * @param pkg The package to be scanned for JSD bindings.
   * @param classLoader The {@link ClassLoader} containing the defined package.
   * @param filter The class {@link Predicate} allowing filtration of scanned classes.
   * @throws IOException If an I/O error has occurred.
   * @throws PackageNotFoundException If the specified package is not found.
   * @throws NullPointerException If {@code pkg} or {@code prefix} is null.
   */
  public SchemaElement(final Package pkg, final ClassLoader classLoader, final Predicate<? super Class<?>> filter) throws IOException, PackageNotFoundException {
    this(findClasses(pkg, classLoader, filter));
  }

  /**
   * Creates a new {@link SchemaElement} by scanning the specified package in the provided class loader.
   * <p>
   * This constructor is equivalent to calling:
   *
   * <pre>
   * {@code
   * new Schema(pkg, classLoader, null)
   * }
   * </pre>
   *
   * @param pkg The package to be scanned for JSD bindings.
   * @param classLoader The {@link ClassLoader} containing the defined package.
   * @throws IOException If an I/O error has occurred.
   * @throws PackageNotFoundException If the specified package is not found.
   * @throws NullPointerException If {@code pkg} or {@code prefix} is null.
   */
  public SchemaElement(final Package pkg, final ClassLoader classLoader) throws IOException, PackageNotFoundException {
    this(findClasses(pkg, classLoader, null));
  }

  /**
   * Creates a new {@link SchemaElement} by scanning the specified package, filtered with the given class predicate.
   * <p>
   * This constructor is equivalent to calling:
   *
   * <pre>
   * {@code
   * new Schema(pkg, Thread.currentThread().getContextClassLoader(), filter)
   * }
   * </pre>
   *
   * @param pkg The package to be scanned for JSD bindings.
   * @param filter The class {@link Predicate} allowing filtration of scanned classes.
   * @throws IOException If an I/O error has occurred.
   * @throws PackageNotFoundException If the specified package is not found.
   */
  public SchemaElement(final Package pkg, final Predicate<Class<?>> filter) throws IOException, PackageNotFoundException {
    this(pkg, Thread.currentThread().getContextClassLoader(), filter);
  }

  /**
   * Creates a new {@link SchemaElement} by scanning the specified package.
   * <p>
   * This constructor is equivalent to calling:
   *
   * <pre>
   * {@code
   * new Schema(pkg, Thread.currentThread().getContextClassLoader(), null)
   * }
   * </pre>
   *
   * @param pkg The package to be scanned for JSD bindings.
   * @throws IOException If an I/O error has occurred.
   * @throws PackageNotFoundException If the specified package is not found.
   */
  public SchemaElement(final Package pkg) throws IOException, PackageNotFoundException {
    this(pkg, Thread.currentThread().getContextClassLoader(), null);
  }

  /**
   * Creates a new {@link SchemaElement} by scanning the provided classes.
   *
   * @param classes The classes to scan.
   */
  public SchemaElement(final Class<?> ... classes) {
    this(CollectionUtil.asCollection(new IdentityHashSet<>(classes.length), classes));
  }

  /**
   * Creates a new {@link SchemaElement} by scanning the provided classes.
   *
   * @param classes The classes to scan.
   */
  public SchemaElement(final Collection<Class<?>> classes) {
    super(null, null);
    this.registry = new Registry(this, classes);
    final QName name = Schema.class.getAnnotation(QName.class);
    this.version = name.namespaceURI().substring(0, name.namespaceURI().lastIndexOf('.'));

    this.registry.resolveReferences();
    final int len = registry.getModels().size();
    final Model[] models = registry.getModels().toArray(new Model[len]);
    for (int i = 0; i < len; ++i) // [A]
      if (models[i] instanceof Referrer)
        ((Referrer<?>)models[i]).resolveOverrides();

    this.registry.resolveReferences();
  }

  private ArrayList<Model> rootMembers(final Settings settings) {
    final Collection<Model> models = registry.getModels();
    if (models.size() == 0)
      return WrappedArrayList.EMPTY_LIST;

    final ArrayList<Model> members = new ArrayList<>();
    for (final Model model : models) // [C]
      if (registry.isRootMember(model, settings))
        members.add(model);

    if (!registry.isFromJsd)
      members.sort(Comparator.naturalOrder());

    return members;
  }

  private static final Id ID = Id.named(SchemaElement.class);

  @Override
  public Id id() {
    return ID;
  }

  @Override
  XmlElement toXml(final Settings settings, final Element owner, final String packageName) {
    final List<XmlElement> elements;
    final ArrayList<Model> members = rootMembers(settings);
    final int i$ = members.size();
    if (i$ > 0) {
      elements = new ArrayList<>();
      int i = 0; do // [RA]
        elements.add(members.get(i).toXml(settings, this, packageName));
      while (++i < i$);
    }
    else {
      elements = null;
    }

    final Map<String,Object> attributes = super.toXmlAttributes(owner, packageName);
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
    final int i$ = members.size();
    if (i$ == 0)
      return null;

    final Map<String,Object> properties = new LinkedHashMap<>(toXmlAttributes(owner, packageName));
    properties.put("jx:ns", version + ".jsd");
    properties.put("jx:schemaLocation", version + ".jsd " + version + ".jsd");
    for (int i = 0; i < i$; ++i) { // [RA]
      final Model member = members.get(i);
      final String id = member.id().toString();
      final String name = packageName.length() > 0 && id.startsWith(packageName) ? id.substring(packageName.length() + 1) : id;
      properties.put(name, member.toJson(settings, this, packageName));
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

  private static void addParents(final Registry.Type type, final ClassSpec classSpec, final Settings settings, final Map<Registry.Type,ClassSpec> typeToJavaClass, final Map<Registry.Type,ClassSpec> all) {
    final Registry.Type declaringType = type.getDeclaringType();
    if (declaringType == null) {
      typeToJavaClass.put(type, classSpec);
      return;
    }

    ClassSpec parent = all.get(declaringType);
    if (parent == null) {
      parent = new ClassSpec(declaringType, settings);
      addParents(declaringType, parent, settings, typeToJavaClass, all);
      all.put(declaringType, parent);
    }

    parent.add(classSpec);
  }

  public Map<String,String> toSource(final Settings settings) {
    final HashMap<Registry.Type,ClassSpec> all = new HashMap<>();
    final HashMap<Registry.Type,ClassSpec> typeToJavaClass = new HashMap<>();
    final Collection<Model> models = registry.getModels();
    if (models.size() > 0) {
      for (final Model member : models) { // [C]
        final Referrer<?> referrer;
        final Registry.Type type;
        if (member instanceof Referrer && (type = (referrer = (Referrer<?>)member).classType()) != null) {
          final ClassSpec classSpec = new ClassSpec(referrer, settings);
          addParents(type, classSpec, settings, typeToJavaClass, all);
          all.put(type, classSpec);
        }
      }
    }

    final HashMap<String,String> sources = new HashMap<>();
    final StringBuilder b = new StringBuilder();
    if (typeToJavaClass.size() > 0) {
      for (final Map.Entry<Registry.Type,ClassSpec> entry : typeToJavaClass.entrySet()) { // [S]
        final Registry.Type type = entry.getKey();
        final ClassSpec classSpec = entry.getValue();
        final String canonicalPackageName = type.getCanonicalPackage();
        if (canonicalPackageName != null)
          b.append("package ").append(canonicalPackageName).append(";\n");

        final StringBuilder annotation = classSpec.getAnnotation();
        if (annotation != null)
          b.append('\n').append(annotation);

        final String doc = classSpec.getDoc();
        if (doc != null)
          b.append('\n').append(doc);

        if (canonicalPackageName != null)
          b.append("\n@").append(SuppressWarnings.class.getName()).append("(\"all\")");

        b.append("\npublic ").append(classSpec);
        sources.put(type.getName(), b.toString());
        b.setLength(0);
      }
    }

    return sources;
  }

  @Override
  public Registry.Type classType() {
    throw new UnsupportedOperationException();
  }

  public Map<String,String> toSource(final File dir) throws IOException {
    return toSource(dir, Settings.DEFAULT);
  }

  public Map<String,String> toSource(final File dir, final Settings settings) throws IOException {
    final Map<String,String> sources = toSource(settings);
    if (sources.size() > 0) {
      for (final Map.Entry<String,String> entry : sources.entrySet()) { // [S]
        final File file = new File(dir, entry.getKey().replace('.', '/') + ".java");
        file.getParentFile().mkdirs();
        Files.write(file.toPath(), entry.getValue().getBytes());
      }
    }

    return sources;
  }

  @Override
  public String elementName() {
    return "schema";
  }

  @Override
  public Declarer declarer() {
    return null;
  }

  @Override
  public String displayName() {
    return "";
  }
}