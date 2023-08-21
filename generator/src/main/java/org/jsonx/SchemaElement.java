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
import java.util.Collections;
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
import org.jsonx.binding.TypeFieldBinding;
import org.jsonx.www.binding_0_4.xL1gluGCXAA;
import org.jsonx.www.binding_0_4.xL1gluGCXAA.$FieldBindings;
import org.jsonx.www.binding_0_4.xL1gluGCXAA.$TypeFieldBindings;
import org.jsonx.www.schema_0_4.xL0gluGCXAA;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Documented;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.Schema.TargetNamespace$;
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
import org.w3.www._2001.XMLSchema.yAA.$AnyType;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

/**
 * The root {@link Element} of a JSON Schema Document.
 */
public final class SchemaElement extends Element implements Declarer { // FIXME: Rename SchemaElement to something better
  private static final String GENERATED = "(value=\"" + Generator.class.getName() + "\", date=\"" + LocalDateTime.now().toString() + "\")";

  private static <T extends $FieldBindings>T jsdToXsbField(final T xsb, final String path, final Map<String,binding.FieldBinding> bindings) {
    xsb.setPath$(path);
    for (final Map.Entry<String,binding.FieldBinding> entry : bindings.entrySet()) { // [S]
      final $FieldBindings.Bind bind = new $FieldBindings.Bind();
      bind.setLang$(entry.getKey());
      bind.setField$(entry.getValue().getField());
      xsb.addBind(bind);
    }

    return xsb;
  }

  private static <T extends $TypeFieldBindings>T jsdToXsbTypeField(final T xsb, final String path, final Map<String,binding.TypeFieldBinding> bindings) {
    xsb.setPath$(path);
    for (final Map.Entry<String,binding.TypeFieldBinding> entry : bindings.entrySet()) { // [S]
      final $TypeFieldBindings.Bind bind = new $TypeFieldBindings.Bind();
      bind.setLang$(entry.getKey());
      final TypeFieldBinding value = entry.getValue();
      bind.setField$(value.getField());
      bind.setDecode$(value.getDecode());
      bind.setEncode$(value.getEncode());
      bind.setType$(value.getType());
      xsb.addBind(bind);
    }

    return xsb;
  }

  private static xL1gluGCXAA.Binding jxToXsb(final binding.Binding jsb) {
    final xL1gluGCXAA.Binding xsb = new xL1gluGCXAA.Binding();
    xsb.setJxSchema(jxToXsb(jsb.getSchema()));
    final LinkedHashMap<String,Object> bindings = jsb.getBindings().get5cS7c5cS2e2a5cS();
    for (final Map.Entry<String,Object> entry : bindings.entrySet()) { // [L]
      final String path = entry.getKey();
      final JxObject member = (JxObject)entry.getValue();
      if (member instanceof binding.Any)
        xsb.addAny(jsdToXsbField(new xL1gluGCXAA.Binding.Any(), path, ((binding.FieldBindings)member).get5cS7c5cS2e2a5cS()));
      else if (member instanceof binding.Reference)
        xsb.addReference(jsdToXsbField(new xL1gluGCXAA.Binding.Reference(), path, ((binding.FieldBindings)member).get5cS7c5cS2e2a5cS()));
      else if (member instanceof binding.Array)
        xsb.addArray(jsdToXsbField(new xL1gluGCXAA.Binding.Array(), path, ((binding.FieldBindings)member).get5cS7c5cS2e2a5cS()));
      else if (member instanceof binding.Object)
        xsb.addObject(jsdToXsbField(new xL1gluGCXAA.Binding.Object(), path, ((binding.FieldBindings)member).get5cS7c5cS2e2a5cS()));
      else if (member instanceof binding.Boolean)
        xsb.addBoolean(jsdToXsbTypeField(new xL1gluGCXAA.Binding.Boolean(), path, ((binding.TypeFieldBindings)member).get5cS7c5cS2e2a5cS()));
      else if (member instanceof binding.Number)
        xsb.addNumber(jsdToXsbTypeField(new xL1gluGCXAA.Binding.Number(), path, ((binding.TypeFieldBindings)member).get5cS7c5cS2e2a5cS()));
      else if (member instanceof binding.String)
        xsb.addString(jsdToXsbTypeField(new xL1gluGCXAA.Binding.String(), path, ((binding.TypeFieldBindings)member).get5cS7c5cS2e2a5cS()));
      else
        throw new UnsupportedOperationException("Unsupported type: " + member.getClass().getName());
    }

    return xsb;
  }

  private static xL0gluGCXAA.Schema jxToXsb(final schema.Schema jsd) {
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
  public static SchemaElement parseJson(final URL url, final Settings settings) throws DecodeException, IOException {
    try (final JsonReader in = new JsonReader(new InputStreamReader(url.openStream()))) {
      return new SchemaElement(JxDecoder.VALIDATING.parseObject(in, schema.Schema.class, binding.Binding.class), settings);
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
  private static SchemaElement parseJsb(final URL url, final Settings settings) throws DecodeException, IOException {
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

    binding.setSchema(schema);
    final xL1gluGCXAA.Binding xsb = jxToXsb(binding);
    return new SchemaElement(xsb.getJxSchema(), xsb, settings);
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
  public static SchemaElement parseXml(final URL url, final Settings settings) throws IOException, SAXException {
    return new SchemaElement(Bindings.parse(url, getErrorHandler()), settings);
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
        return parseJson(url, settings);
      }
      catch (final DecodeException e0) {
        try {
          return parseXml(url, settings);
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
      return parseXml(url, settings);
    }
    catch (final SAXException e0) {
      try {
        return parseJson(url, settings);
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

  private static IdentityHashMap<Binding,xL1gluGCXAA.$FieldBinding> initBindingMap(final xL0gluGCXAA.Schema schema, final xL1gluGCXAA.Binding binding) {
    final IdentityHashMap<Binding,xL1gluGCXAA.$FieldBinding> xsbToBinding = new IdentityHashMap<>();
    final Iterator<$AnyType> iterator = binding.elementIterator();
    iterator.next(); // Skip schema element
    while (iterator.hasNext()) {
      final xL1gluGCXAA.$Path bindings = (xL1gluGCXAA.$Path)iterator.next();
      final $Documented element = new JsonPath(bindings.getPath$().text()).resolve(schema);
      if (bindings instanceof $FieldBindings) {
        final BindingList<xL1gluGCXAA.$FieldBinding> b = (($FieldBindings)bindings).getBind();
        for (int i = 0, i$ = b.size(); i < i$; ++i) { // [RA]
          final xL1gluGCXAA.$FieldBinding fieldBinding = b.get(i);
          if ("java".equals(fieldBinding.getLang$().text())) {
            xsbToBinding.put(element, fieldBinding);
            break;
          }
        }
      }
      else if (bindings instanceof $TypeFieldBindings) {
        final BindingList<xL1gluGCXAA.$TypeFieldBinding> b = (($TypeFieldBindings)bindings).getBind();
        for (int i = 0, i$ = b.size(); i < i$; ++i) { // [RA]
          final xL1gluGCXAA.$FieldBinding fieldBinding = b.get(i);
          if ("java".equals(fieldBinding.getLang$().text())) {
            xsbToBinding.put(element, fieldBinding);
            break;
          }
        }
      }
      else {
        throw new ValidationException("Unexpected element type: " + bindings.name());
      }
    }

    return xsbToBinding;
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
    this(binding.getJxSchema(), binding, settings);
  }

  private SchemaElement(xL0gluGCXAA.Schema schema, final xL1gluGCXAA.Binding binding, final Settings settings) throws ValidationException {
    super(null, (schema == null ? schema = binding.getJxSchema() : schema).getDoc$());
    final IdentityHashMap<Binding,xL1gluGCXAA.$FieldBinding> xsbToBinding = binding == null ? null : initBindingMap(schema, binding);
    final TargetNamespace$ targetNamespace = schema.getTargetNamespace$();
    this.registry = new Registry(targetNamespace == null ? null : targetNamespace.text(), settings);
    final String namespaceURI = schema.name().getNamespaceURI();
    this.version = namespaceURI.substring(namespaceURI.lastIndexOf('-') + 1, namespaceURI.lastIndexOf('.'));

    assertNoCycle(schema);

    final Iterator<? super xL0gluGCXAA.$Member> elementIterator = Iterators.filter(schema.elementIterator(), m -> m instanceof xL0gluGCXAA.$Member);
    while (elementIterator.hasNext()) {
      final xL0gluGCXAA.$Member member = (xL0gluGCXAA.$Member)elementIterator.next();
      if (member instanceof xL0gluGCXAA.Schema.Array)
        ArrayModel.declare(registry, this, (xL0gluGCXAA.Schema.Array)member, xsbToBinding);
      else if (member instanceof xL0gluGCXAA.Schema.Boolean)
        BooleanModel.declare(registry, this, (xL0gluGCXAA.Schema.Boolean)member, xsbToBinding);
      else if (member instanceof xL0gluGCXAA.Schema.Number)
        NumberModel.declare(registry, this, (xL0gluGCXAA.Schema.Number)member, xsbToBinding);
      else if (member instanceof xL0gluGCXAA.Schema.String)
        StringModel.declare(registry, this, (xL0gluGCXAA.Schema.String)member, xsbToBinding);
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
    this(jxToXsb(schema), null, settings);
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
    this(null, jxToXsb(binding), settings);
  }

  public SchemaElement(final $AnyType<?> object, final Settings settings) {
    this(object instanceof xL0gluGCXAA.Schema ? (xL0gluGCXAA.Schema)object : null, object instanceof xL1gluGCXAA.Binding ? (xL1gluGCXAA.Binding)object : null, settings);
  }

  public SchemaElement(final JxObject object, final Settings settings) {
    this(object instanceof schema.Schema ? jxToXsb((schema.Schema)object) : null, object instanceof binding.Binding ? jxToXsb((binding.Binding)object) : null, settings);
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
    final String namespaceURI = name.namespaceURI();
    this.version = namespaceURI.substring(namespaceURI.lastIndexOf('-') + 1, name.namespaceURI().lastIndexOf('.'));

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
  XmlElement toXml(final Element owner, final String packageName, final JsonPath.Cursor cursor, final HashMap<String,Map<String,Object>> pathToBinding) {
    final ArrayList<XmlElement> schemaElems;
    final ArrayList<Model> members = rootMembers();
    final int i$ = members.size();
    if (i$ > 0) {
      schemaElems = new ArrayList<>();
      int i = 0; do // [RA]
        schemaElems.add(members.get(i).toXml(this, packageName, cursor, pathToBinding));
      while (++i < i$);
    }
    else {
      schemaElems = null;
    }

    final Map<String,Object> schemaAttrs = super.toXmlAttributes(owner, packageName);
    schemaAttrs.put("xmlns", "http://www.jsonx.org/schema-" + version + ".xsd");
    schemaAttrs.put("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
    schemaAttrs.put("xsi:schemaLocation", "http://www.jsonx.org/schema-" + version + ".xsd http://www.jsonx.org/schema-" + version + ".xsd");
    final String targetNamespace = registry.targetNamespace;
    if (targetNamespace != null)
      schemaAttrs.put("targetNamespace", targetNamespace.endsWith(".jsd") ? targetNamespace + "x" : targetNamespace);

    final int noBindings = pathToBinding.size();
    final XmlElement schema = new XmlElement("schema", schemaAttrs, schemaElems);
    if (noBindings == 0)
      return schema;

    final LinkedHashMap<String,Object> bindingAttrs = new LinkedHashMap<>();
    bindingAttrs.put("xmlns", "http://www.jsonx.org/binding-" + version + ".xsd");

    schemaAttrs.remove("xmlns:xsi");
    bindingAttrs.put("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");

    schemaAttrs.remove("xsi:schemaLocation");
    bindingAttrs.put("xsi:schemaLocation", "http://www.jsonx.org/binding-" + version + ".xsd http://www.jsonx.org/binding-" + version + ".xsd");

    final ArrayList<XmlElement> bindingElems = new ArrayList<>(noBindings + 1);
    bindingElems.add(schema);

    for (final Map.Entry<String,Map<String,Object>> entry : pathToBinding.entrySet()) { // [S]
      final LinkedHashMap<String,Object> bind = new LinkedHashMap<>(entry.getValue());
      bindingElems.add(new XmlElement((String)bind.remove("jx:type"), Collections.singletonMap("path", entry.getKey()), Collections.singletonList(new XmlElement("bind", bind, null))));
    }

    return new XmlElement("binding", bindingAttrs, bindingElems);
  }

  public XmlElement toXml() {
    return toXml(this, registry.packageName, new JsonPath.Cursor(), new LinkedHashMap<>());
  }

  @Override
  Map<String,Object> toJson(final Element owner, final String packageName, final JsonPath.Cursor cursor, final HashMap<String,Map<String,Object>> pathToBinding) {
    final List<Model> members = rootMembers();
    final int size = members.size();
    if (size == 0)
      return null;

    final Map<String,Object> schema = new LinkedHashMap<>();
    schema.put("jx:ns", "http://www.jsonx.org/schema-" + version + ".jsd");
    schema.put("jx:schemaLocation", "http://www.jsonx.org/schema-" + version + ".jsd http://www.jsonx.org/schema-" + version + ".jsd");
    schema.putAll(toXmlAttributes(owner, packageName));
    final String targetNamespace = registry.targetNamespace;
    if (targetNamespace != null)
      schema.put("jx:targetNamespace", targetNamespace.endsWith(".jsdx") ? targetNamespace.substring(0, targetNamespace.length() - 1) : targetNamespace);

    final int len = packageName.length();
    for (int i = 0; i < size; ++i) { // [RA]
      final Model member = members.get(i);
      final String id = member.id().toString();
      final String name = len > 0 && id.startsWith(packageName) ? id.substring(len + 1) : id;
      schema.put(name, member.toJson(this, packageName, cursor, pathToBinding));
    }

    if (pathToBinding.size() == 0)
      return schema;

    schema.remove("jx:schemaLocation");
    final Map<String,Object> binding = new LinkedHashMap<>();
    binding.put("jx:ns", "http://www.jsonx.org/binding-" + version + ".jsd");
    binding.put("jx:schemaLocation", "http://www.jsonx.org/binding-" + version + ".jsd http://www.jsonx.org/binding-" + version + ".jsd");
    binding.put("schema", schema);
    final Map<String,Object> bindings = new LinkedHashMap<>();
    binding.put("bindings", bindings);
    for (final Map.Entry<String,Map<String,Object>> entry : pathToBinding.entrySet()) { // [S]
      final LinkedHashMap<String,Object> bind = new LinkedHashMap<>(entry.getValue());
      final LinkedHashMap<String,Object> value = new LinkedHashMap<>();
      value.put("jx:type", bind.remove("jx:type"));
      value.put((String)bind.remove("lang"), bind);
      bindings.put(entry.getKey(), value);
    }

    return binding;
  }

  public Map<String,Object> toJson() {
    return toJson(this, registry.packageName, new JsonPath.Cursor(), new LinkedHashMap<>());
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