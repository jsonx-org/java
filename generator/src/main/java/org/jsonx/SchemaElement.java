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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Generated;

import org.jaxsb.runtime.Binding;
import org.jaxsb.runtime.BindingList;
import org.jaxsb.runtime.Bindings;
import org.jaxsb.runtime.QName;
import org.jsonx.www.binding_0_4.xL1gluGCXAA;
import org.jsonx.www.schema_0_4.xL0gluGCXAA;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Documented;
import org.libj.lang.PackageLoader;
import org.libj.lang.PackageNotFoundException;
import org.libj.lang.WrappedArrayList;
import org.libj.net.URLs;
import org.libj.util.CollectionUtil;
import org.libj.util.IdentityHashSet;
import org.libj.util.Iterators;
import org.libj.util.StringPaths;
import org.openjax.json.JsonReader;
import org.openjax.xml.api.XmlElement;
import org.openjax.xml.sax.SilentErrorHandler;
import org.w3.www._2001.XMLSchema.yAA;
import org.w3.www._2001.XMLSchema.yAA.$AnySimpleType;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

/**
 * The root {@link Element} of a JSON Schema Document.
 */
public final class SchemaElement extends Element implements Declarer { // FIXME: Rename SchemaElement to something better
  private static final String GENERATED = "(value=\"" + Generator.class.getName() + "\", date=\"" + LocalDateTime.now().toString() + "\")";

  private static <T extends xL1gluGCXAA.$FieldBindings>T jsdToXsbField(final T xsb, final String path, final List<binding.FieldBinding> bindings) {
    xsb.setPath$(path);
    for (int i = 0, i$ = bindings.size(); i < i$; ++i) { // [L]
      final binding.FieldBinding fieldBinding = bindings.get(i);
      final xL1gluGCXAA.$FieldBindings.Bind bind = new xL1gluGCXAA.$FieldBindings.Bind();
      bind.setLang$(fieldBinding.getLang());
      bind.setField$(fieldBinding.getField());
      xsb.addBind(bind);
    }

    return xsb;
  }

  private static <T extends xL1gluGCXAA.$TypeFieldBindings>T jsdToXsbTypeField(final T xsb, final String path, final List<binding.TypeFieldBinding> bindings) {
    xsb.setPath$(path);
    for (int i = 0, i$ = bindings.size(); i < i$; ++i) { // [L]
      final binding.TypeFieldBinding fieldBinding = bindings.get(i);
      final xL1gluGCXAA.$TypeFieldBindings.Bind bind = new xL1gluGCXAA.$TypeFieldBindings.Bind();
      bind.setLang$(fieldBinding.getLang());
      bind.setField$(fieldBinding.getField());
      xsb.addBind(bind);
    }

    return xsb;
  }

  private static xL1gluGCXAA.Binding jsdToXsb(final binding.Binding jsb) {
    final xL1gluGCXAA.Binding xsb = new xL1gluGCXAA.Binding();
    xsb.setJxSchema(jsdToXsb(jsb.getSchema()));
    final List<Object> bindings = jsb.getBindings();
    for (int i = 0, i$ = bindings.size(); i < i$; ++i) { // [L]
      final binding.Path member = (binding.Path)bindings.get(i);
      if (member instanceof binding.Any)
        xsb.addAny(jsdToXsbField(new xL1gluGCXAA.Binding.Any(), member.getPath(), ((binding.FieldBindings)member).getBindings()));
      else if (member instanceof binding.Reference)
        xsb.addReference(jsdToXsbField(new xL1gluGCXAA.Binding.Reference(), member.getPath(), ((binding.FieldBindings)member).getBindings()));
      else if (member instanceof binding.Array)
        xsb.addArray(jsdToXsbField(new xL1gluGCXAA.Binding.Array(), member.getPath(), ((binding.FieldBindings)member).getBindings()));
      else if (member instanceof binding.Object)
        xsb.addObject(jsdToXsbField(new xL1gluGCXAA.Binding.Object(), member.getPath(), ((binding.FieldBindings)member).getBindings()));
      else if (member instanceof binding.Boolean)
        xsb.addBoolean(jsdToXsbTypeField(new xL1gluGCXAA.Binding.Boolean(), member.getPath(), ((binding.TypeFieldBindings)member).getBindings()));
      else if (member instanceof binding.Number)
        xsb.addNumber(jsdToXsbTypeField(new xL1gluGCXAA.Binding.Number(), member.getPath(), ((binding.TypeFieldBindings)member).getBindings()));
      else if (member instanceof binding.String)
        xsb.addString(jsdToXsbTypeField(new xL1gluGCXAA.Binding.String(), member.getPath(), ((binding.TypeFieldBindings)member).getBindings()));
      else
        throw new UnsupportedOperationException("Unsupported type: " + member.getClass().getName());
    }

    return xsb;
  }

  private static xL0gluGCXAA.Schema jsdToXsb(final schema.Schema jsd) {
    final xL0gluGCXAA.Schema xsb = new xL0gluGCXAA.Schema();
    final LinkedHashMap<String,? extends schema.Member> declarations = jsd.get5ba2dZA2dZ__5d5b2dA2dZA2dZ5cD__5d2a();
    if (declarations != null && declarations.size() > 0) {
      for (final Map.Entry<String,? extends schema.Member> entry : declarations.entrySet()) { // [S]
        final String name = entry.getKey();
        final schema.Member declaration = entry.getValue();
        if (declaration instanceof schema.Array)
          xsb.addArray((xL0gluGCXAA.Schema.Array)ArrayModel.jsdToXsb((schema.Array)declaration, name));
        else if (declaration instanceof schema.Boolean)
          xsb.addBoolean((xL0gluGCXAA.Schema.Boolean)BooleanModel.jsdToXsb((schema.Boolean)declaration, name));
        else if (declaration instanceof schema.Number)
          xsb.addNumber((xL0gluGCXAA.Schema.Number)NumberModel.jsdToXsb((schema.Number)declaration, name));
        else if (declaration instanceof schema.ObjectType)
          xsb.addObject((xL0gluGCXAA.Schema.Object)ObjectModel.jsdToXsb((schema.Object)declaration, name));
        else if (declaration instanceof schema.String)
          xsb.addString((xL0gluGCXAA.Schema.String)StringModel.jsdToXsb((schema.String)declaration, name));
        else
          throw new UnsupportedOperationException("Unsupported type: " + declaration.getClass().getName());
      }
    }

    final String targetNamespace = jsd.getJx3aTargetNamespace();
    if (targetNamespace != null && targetNamespace.length() > 0)
      xsb.setTargetNamespace$(targetNamespace);

    final String doc = jsd.getDoc();
    if (doc != null && doc.length() > 0)
      xsb.setDoc$(new xL0gluGCXAA.$Documented.Doc$(doc));

    return xsb;
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
  public static SchemaElement parseJsd(final URL url, final Settings settings) throws DecodeException, IOException {
    try (final JsonReader in = new JsonReader(new InputStreamReader(url.openStream()))) {
      return new SchemaElement(JxDecoder.VALIDATING.parseObject(in, schema.Schema.class), settings);
    }
  }

  /**
   * Creates a {@link SchemaElement} from the content of the specified file that is expected to be in JSB format.
   *
   * @param url The {@link URL} of the content to parse.
   * @param settings The {@link Settings} to be used for the parsed {@link SchemaElement}.
   * @return The {@link SchemaElement} instance.
   * @throws IOException If an I/O error has occurred.
   * @throws DecodeException If a decode error has occurred.
   * @throws ValidationException If a validation error has occurred.
   * @throws NullPointerException If {@code url} of {@code settings} is null.
   */
  public static SchemaElement parseJsb(final URL url, final Settings settings) throws DecodeException, IOException {
    final binding.Binding binding;
    try (final JsonReader in = new JsonReader(new InputStreamReader(url.openStream()))) {
      binding = JxDecoder.VALIDATING.parseObject(in, binding.Binding.class);
    }

    final String pathOfSchemaFromInclude = null;
    final URL schemaUrl = StringPaths.isAbsolute(pathOfSchemaFromInclude) ? URLs.create(pathOfSchemaFromInclude) : URLs.toCanonicalURL(StringPaths.getCanonicalParent(url.toString()));
    final schema.Schema schema;
    try (final JsonReader in = new JsonReader(new InputStreamReader(schemaUrl.openStream()))) {
      schema = JxDecoder.VALIDATING.parseObject(in, schema.Schema.class);
    }

    return new SchemaElement(jsdToXsb(schema), initBindingMap(jsdToXsb(binding)), settings);
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
  public static SchemaElement parseJsdx(final URL url, final Settings settings) throws IOException, SAXException {
    final xL0gluGCXAA.Schema schema = (xL0gluGCXAA.Schema)Bindings.parse(url, getErrorHandler());
    return new SchemaElement(schema, null, settings);
  }

  /**
   * Creates a {@link SchemaElement} from the content of the specified file that is expected to be in JSBx format.
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
  public static SchemaElement parseJsbx(final URL url, final Settings settings) throws IOException, SAXException {
    return new SchemaElement((xL1gluGCXAA.Binding)Bindings.parse(url, getErrorHandler()), settings);
  }

  /**
   * Creates a {@link SchemaElement} from the contents of the specified {@code file}. The supported content formats are JSDx and
   * JSD. If the supplied file is not in one of the supported formats, an {@link IllegalArgumentException} is thrown.
   *
   * @param url The file from which to read the contents.
   * @param settings The {@link Settings} to be used for the parsed {@link SchemaElement}.
   * @return A {@link SchemaElement} from the contents of the specified {@code file}.
   * @throws IllegalArgumentException If the format of the content of the specified file is malformed, or is not JSDx or JSD.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code url} of {@code settings} is null.
   */
  public static SchemaElement parse(final URL url, final Settings settings) throws IOException {
    if (URLs.getName(url).endsWith(".jsd")) {
      try {
        return parseJsd(url, settings);
      }
      catch (final DecodeException e0) {
        try {
          return parseJsdx(url, settings);
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
      return parseJsdx(url, settings);
    }
    catch (final SAXException e0) {
      try {
        return parseJsd(url, settings);
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

  private static $Documented resolve($Documented element, final JsonPath path) {
    final Iterator<Object> pathIterator = path.iterator();
    Iterator<yAA.$AnyType> xsbIterator = element.elementIterator();
    OUT:
    while (pathIterator.hasNext()) {
      final Object term = pathIterator.next();
      if (term instanceof String) {
        while (xsbIterator.hasNext()) {
          element = ($Documented)xsbIterator.next();
          final Iterator<$AnySimpleType> attributeIterator = element.attributeIterator();
          while (attributeIterator.hasNext()) {
            final $AnySimpleType attribute = attributeIterator.next();
            if ("name".equals(attribute.name().getLocalPart()) && attribute.text().equals(term)) {
              xsbIterator = element.elementIterator();
              break OUT;
            }
          }
        }
      }
      else if (term instanceof Integer) {
        for (int index = 0; xsbIterator.hasNext();) {
          element = ($Documented)xsbIterator.next();
          if (index == (int)term)
            break OUT;
        }
      }
      else {
        throw new IllegalStateException("Unknown term class: " + term.getClass().getName());
      }
    }

    return element;
  }

  private static IdentityHashMap<Binding,xL1gluGCXAA.$FieldBinding> initBindingMap(final xL1gluGCXAA.Binding binding) {
    final xL0gluGCXAA.Schema schema = binding.getJxSchema();
    final IdentityHashMap<Binding,xL1gluGCXAA.$FieldBinding> elementToBinding = new IdentityHashMap<>();
    final Iterator<yAA.$AnyType> iterator = binding.elementIterator();
    iterator.next(); // Skip schema element
    while (iterator.hasNext()) {
      final xL1gluGCXAA.$Path bindings = (xL1gluGCXAA.$Path)iterator.next();
      final $Documented element = resolve(schema, new JsonPath(bindings.getPath$().text()));
      if (bindings instanceof xL1gluGCXAA.$FieldBindings) {
        final BindingList<xL1gluGCXAA.$FieldBinding> b = ((xL1gluGCXAA.$FieldBindings)bindings).getBind();
        for (int i = 0, i$ = b.size(); i < i$; ++i) { // [RA]
          final xL1gluGCXAA.$FieldBinding fieldBinding = b.get(i);
          if ("java".equals(fieldBinding.getLang$().text())) {
            elementToBinding.put(element, fieldBinding);
            break;
          }
        }
      }
      else if (bindings instanceof xL1gluGCXAA.$TypeFieldBindings) {
        final BindingList<xL1gluGCXAA.$TypeFieldBinding> b = ((xL1gluGCXAA.$TypeFieldBindings)bindings).getBind();
        for (int i = 0, i$ = b.size(); i < i$; ++i) { // [RA]
          final xL1gluGCXAA.$FieldBinding fieldBinding = b.get(i);
          if ("java".equals(fieldBinding.getLang$().text())) {
            elementToBinding.put(element, fieldBinding);
            break;
          }
        }
      }
      else {
        throw new ValidationException("Unexpected element type: " + bindings.name());
      }
    }

    return elementToBinding;
  }

  private final Registry registry;
  private final String version;

  /**
   * Creates a new {@link SchemaElement} from the specified XML binding, and with the provided package / class name prefix string.
   *
   * @param schema JSDx as a JAX-SB object.
   * @param settings The {@link Settings} to be used for the parsed {@link SchemaElement}.
   * @throws ValidationException If a cycle is detected in the object hierarchy.
   * @throws NullPointerException If {@code schema} or {@code settings} is null.
   */
  public SchemaElement(final xL0gluGCXAA.Schema schema, final Settings settings) throws ValidationException {
    this(schema, null, settings);
  }

  /**
   * Creates a new {@link SchemaElement} from the specified XML binding, and with the provided package / class name prefix string.
   *
   * @param binding JSBx as a JAX-SB object.
   * @param settings The {@link Settings} to be used for the parsed {@link SchemaElement}.
   * @throws ValidationException If a cycle is detected in the object hierarchy.
   * @throws NullPointerException If {@code schema} or {@code settings} is null.
   */
  public SchemaElement(final xL1gluGCXAA.Binding binding, final Settings settings) throws ValidationException {
    this(binding.getJxSchema(), initBindingMap(binding), settings);
  }

  private SchemaElement(final xL0gluGCXAA.Schema schema, final IdentityHashMap<Binding,xL1gluGCXAA.$FieldBinding> xsbToBinding, final Settings settings) throws ValidationException {
    super(null, schema.getDoc$());
    this.registry = new Registry(schema.getTargetNamespace$() == null ? null : schema.getTargetNamespace$().text(), settings);
    this.version = schema.name().getNamespaceURI().substring(0, schema.name().getNamespaceURI().lastIndexOf('.'));

    assertNoCycle(schema);

    final Iterator<? super xL0gluGCXAA.$Member> elementIterator = Iterators.filter(schema.elementIterator(), m -> m instanceof xL0gluGCXAA.$Member);
    while (elementIterator.hasNext()) {
      final xL0gluGCXAA.$Member member = (xL0gluGCXAA.$Member)elementIterator.next();
      if (member instanceof xL0gluGCXAA.Schema.Array)
        ArrayModel.declare(registry, this, (xL0gluGCXAA.Schema.Array)member, xsbToBinding);
      else if (member instanceof xL0gluGCXAA.Schema.Boolean)
        BooleanModel.declare(registry, this, (xL0gluGCXAA.Schema.Boolean)member, (xL1gluGCXAA.$TypeFieldBinding)xsbToBinding.get(member));
      else if (member instanceof xL0gluGCXAA.Schema.Number)
        NumberModel.declare(registry, this, (xL0gluGCXAA.Schema.Number)member, (xL1gluGCXAA.$TypeFieldBinding)xsbToBinding.get(member));
      else if (member instanceof xL0gluGCXAA.Schema.String)
        StringModel.declare(registry, this, (xL0gluGCXAA.Schema.String)member, (xL1gluGCXAA.$TypeFieldBinding)xsbToBinding.get(member));
      else if (member instanceof xL0gluGCXAA.Schema.Object)
        ObjectModel.declare(registry, this, (xL0gluGCXAA.Schema.Object)member, xsbToBinding);
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

  private static String memberToName(final xL0gluGCXAA.$Member obj) {
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

  private static void assertNoCycle(final xL0gluGCXAA.Schema schema) throws ValidationException {
    final StrictRefDigraph<xL0gluGCXAA.$Member,String> digraph = new StrictRefDigraph<>("Object cannot inherit from itself", SchemaElement::memberToName);

    final Iterator<? super xL0gluGCXAA.$Member> elementIterator = Iterators.filter(schema.elementIterator(), m -> m instanceof xL0gluGCXAA.$Member);
    while (elementIterator.hasNext()) {
      final xL0gluGCXAA.$Member member = (xL0gluGCXAA.$Member)elementIterator.next();
      if (member instanceof xL0gluGCXAA.Schema.Object) {
        final xL0gluGCXAA.Schema.Object object = (xL0gluGCXAA.Schema.Object)member;
        final xL0gluGCXAA.$ObjectMember.Extends$ extends$ = object.getExtends$();
        if (extends$ != null)
          digraph.add(object, extends$.text());
        else
          digraph.add(object);
      }
      else {
        digraph.add(member);
      }
    }

    final List<xL0gluGCXAA.$Member> cycle = digraph.getCycle();
    if (cycle != null)
      throw new ValidationException("Cycle detected in object hierarchy: " + cycle.stream().map(SchemaElement::memberToName).collect(Collectors.joining(" -> ")));
  }

  /**
   * Creates a new {@link SchemaElement} from the specified JSD binding, and with the provided package / class name prefix string.
   *
   * @param schema JSD as a JSONx object.
   * @param settings The {@link Settings} to be used for the generated bindings.
   * @throws ValidationException If a validation error has occurred.
   * @throws NullPointerException If {@code schema} or {@code settings} is null.
   */
  public SchemaElement(final schema.Schema schema, final Settings settings) throws ValidationException {
    this(jsdToXsb(schema), null, settings);
  }

  /**
   * Creates a new {@link SchemaElement} from the specified JSD binding, and with the provided package / class name prefix string.
   *
   * @param binding JSB as a JSONx object.
   * @param settings The {@link Settings} to be used for the generated bindings.
   * @throws ValidationException If a validation error has occurred.
   * @throws NullPointerException If {@code schema} or {@code settings} is null.
   */
  public SchemaElement(final binding.Binding binding, final Settings settings) throws ValidationException {
    this(jsdToXsb(binding.getSchema()), initBindingMap(jsdToXsb(binding)), settings);
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
   * @throws NullPointerException If {@code pkg} or {@code settings} is null.
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
   * @throws NullPointerException If {@code pkg} or {@code settings} is null.
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
    final QName name = xL0gluGCXAA.Schema.class.getAnnotation(QName.class);
    this.version = name.namespaceURI().substring(0, name.namespaceURI().lastIndexOf('.'));

    this.registry.resolveReferences();
    final Collection<Model> models = registry.getModels();
    final int len = models.size();
    final Model[] array = models.toArray(new Model[len]);
    for (int i = 0; i < len; ++i) // [A]
      if (array[i] instanceof Referrer)
        ((Referrer<?>)array[i]).resolveOverrides();

    this.registry.resolveReferences();
  }

  private ArrayList<Model> rootMembers() {
    final Collection<Model> models = registry.getModels();
    if (models.size() == 0)
      return WrappedArrayList.EMPTY_LIST;

    final ArrayList<Model> members = new ArrayList<>();
    for (final Model model : models) // [C]
      if (registry.isRootMember(model))
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
  XmlElement toXml(final Element owner, final String packageName) {
    final List<XmlElement> elements;
    final ArrayList<Model> members = rootMembers();
    final int i$ = members.size();
    if (i$ > 0) {
      elements = new ArrayList<>();
      int i = 0; do // [RA]
        elements.add(members.get(i).toXml(this, packageName));
      while (++i < i$);
    }
    else {
      elements = null;
    }

    final Map<String,Object> attributes = super.toXmlAttributes(owner, packageName);
    attributes.put("xmlns", version + ".xsd");
    attributes.put("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
    attributes.put("xsi:schemaLocation", version + ".xsd " + version + ".xsd");
    final String targetNamespace = registry.targetNamespace;
    if (targetNamespace != null)
      attributes.put("targetNamespace", targetNamespace.endsWith(".jsd") ? targetNamespace + "x" : targetNamespace);

    return new XmlElement("schema", attributes, elements);
  }

  public XmlElement toXml() {
    return toXml(this, registry.packageName);
  }

  @Override
  Map<String,Object> toJson(final Element owner, final String packageName) {
    final List<Model> members = rootMembers();
    final int size = members.size();
    if (size == 0)
      return null;

    final Map<String,Object> properties = new LinkedHashMap<>(toXmlAttributes(owner, packageName));
    properties.put("jx:ns", version + ".jsd");
    properties.put("jx:schemaLocation", version + ".jsd " + version + ".jsd");
    final String targetNamespace = registry.targetNamespace;
    if (targetNamespace != null)
      properties.put("jx:targetNamespace", targetNamespace.endsWith(".jsdx") ? targetNamespace.substring(0, targetNamespace.length() - 1) : targetNamespace);

    final int len = packageName.length();
    for (int i = 0; i < size; ++i) { // [RA]
      final Model member = members.get(i);
      final String id = member.id().toString();
      final String name = len > 0 && id.startsWith(packageName) ? id.substring(len + 1) : id;
      properties.put(name, member.toJson(this, packageName));
    }

    return properties;
  }

  public Map<String,Object> toJson() {
    return toJson(this, registry.packageName);
  }

  private static void addParents(final Registry.Type type, final ClassSpec classSpec, final Map<Registry.Type,ClassSpec> typeToJavaClass, final Map<Registry.Type,ClassSpec> all) {
    final Registry.Type declaringType = type.getDeclaringType();
    if (declaringType == null) {
      typeToJavaClass.put(type, classSpec);
      return;
    }

    ClassSpec parent = all.get(declaringType);
    if (parent == null) {
      parent = new ClassSpec(classSpec, declaringType);
      addParents(declaringType, parent, typeToJavaClass, all);
      all.put(declaringType, parent);
    }

    parent.add(classSpec);
  }

  public Map<String,String> toSource() {
    final HashMap<Registry.Type,ClassSpec> all = new HashMap<>();
    final HashMap<Registry.Type,ClassSpec> typeToJavaClass = new HashMap<>();
    final Collection<Model> models = registry.getModels();
    if (models.size() > 0) {
      for (final Model member : models) { // [C]
        final Referrer<?> referrer;
        final Registry.Type type;
        if (member instanceof Referrer && (type = (referrer = (Referrer<?>)member).classType()) != null) {
          final ClassSpec classSpec = new ClassSpec(referrer);
          addParents(type, classSpec, typeToJavaClass, all);
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

        if (classSpec.referrer != null)
          b.append("\n@").append(JxBinding.class.getName()).append("(targetNamespace=\"").append(registry.targetNamespace).append("\")");

        b.append("\n@").append(Generated.class.getName()).append(GENERATED);
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
    final Map<String,String> sources = toSource();
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