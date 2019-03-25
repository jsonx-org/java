/* Copyright (c) 2017 OpenJAX
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

package org.openjax.jsonx.generator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.openjax.jsonx.schema;
import org.openjax.jsonx.runtime.ArrayType;
import org.openjax.jsonx.runtime.JxObject;
import org.openjax.jsonx.runtime.ValidationException;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc.$Member;
import org.openjax.standard.lang.PackageLoader;
import org.openjax.standard.lang.PackageNotFoundException;
import org.openjax.standard.util.FastCollections;
import org.openjax.standard.util.IdentityHashSet;
import org.openjax.standard.util.Iterators;
import org.openjax.standard.xml.api.XmlElement;

/**
 * The {@code Schema} is the root {@code Element} of a JSONx Schema Document.
 */
public final class Schema extends Element {
  private static xL4gluGCXYYJc.Schema jsonxToXsb(final schema.Schema jsonx) {
    final xL4gluGCXYYJc.Schema schema = new xL4gluGCXYYJc.Schema();
    for (final Map.Entry<java.lang.String,java.lang.Object> entry : jsonx._2e_2a.entrySet()) {
      if (entry.getValue() instanceof schema.ArrayType)
        schema.addArrayType((xL4gluGCXYYJc.Schema.ArrayType)ArrayModel.jsonxToXsb((schema.ArrayType)entry.getValue(), entry.getKey()));
      else if (entry.getValue() instanceof schema.BooleanType)
        schema.addBooleanType((xL4gluGCXYYJc.Schema.BooleanType)BooleanModel.jsonxToXsb((schema.BooleanType)entry.getValue(), entry.getKey()));
      else if (entry.getValue() instanceof schema.NumberType)
        schema.addNumberType((xL4gluGCXYYJc.Schema.NumberType)NumberModel.jsonxToXsb((schema.NumberType)entry.getValue(), entry.getKey()));
      else if (entry.getValue() instanceof schema.ObjectType)
        schema.addObjectType((xL4gluGCXYYJc.Schema.ObjectType)ObjectModel.jsonxToXsb((schema.ObjectType)entry.getValue(), entry.getKey()));
      else if (entry.getValue() instanceof schema.StringType)
        schema.addStringType((xL4gluGCXYYJc.Schema.StringType)StringModel.jsonxToXsb((schema.StringType)entry.getValue(), entry.getKey()));
      else
        throw new UnsupportedOperationException("Unsupported type: " + entry.getValue().getClass().getName());
    }

    return schema;
  }

  private final Registry registry;

  /**
   * Creates a new {@code Schema} from the specified XML binding, and with the
   * provided package / class name prefix string.
   *
   * @param schema The XML binding (XSB).
   * @param prefix The class name prefix to be prepended to the names of
   *          generated JSONx bindings.
   */
  public Schema(final xL4gluGCXYYJc.Schema schema, final String prefix) {
    this.registry = new Registry(prefix);

    final StrictRefDigraph<$Member,String> digraph = new StrictRefDigraph<>("Object cannot inherit from itself", obj -> {
      if (obj instanceof xL4gluGCXYYJc.Schema.ArrayType)
        return ((xL4gluGCXYYJc.Schema.ArrayType)obj).getName$().text();

      if (obj instanceof xL4gluGCXYYJc.Schema.BooleanType)
        return ((xL4gluGCXYYJc.Schema.BooleanType)obj).getName$().text();

      if (obj instanceof xL4gluGCXYYJc.Schema.NumberType)
        return ((xL4gluGCXYYJc.Schema.NumberType)obj).getName$().text();

      if (obj instanceof xL4gluGCXYYJc.Schema.StringType)
        return ((xL4gluGCXYYJc.Schema.StringType)obj).getName$().text();

      if (obj instanceof xL4gluGCXYYJc.Schema.ObjectType)
        return ((xL4gluGCXYYJc.Schema.ObjectType)obj).getName$().text();

      throw new UnsupportedOperationException("Unsupported member type: " + obj.getClass().getName());
    });

    final Iterator<? super $Member> iterator = Iterators.filter(schema.elementIterator(), m -> m instanceof $Member);
    while (iterator.hasNext()) {
      final $Member member = ($Member)iterator.next();
      if (member instanceof xL4gluGCXYYJc.Schema.ObjectType) {
        final xL4gluGCXYYJc.Schema.ObjectType object = (xL4gluGCXYYJc.Schema.ObjectType)member;
        if (object.getExtends$() != null)
          digraph.addEdgeRef(object, object.getExtends$().text());
        else
          digraph.addVertex(object);
      }
      else {
        digraph.addVertex(member);
      }
    }

    final List<String> cycle = digraph.getCycleRef();
    if (cycle != null)
      throw new ValidationException("Cycle detected in object hierarchy: " + FastCollections.toString(cycle, " -> "));

    final ListIterator<$Member> topologicalOrder = digraph.getTopologicalOrder().listIterator(digraph.getSize());
    while (topologicalOrder.hasPrevious()) {
      final $Member member = topologicalOrder.previous();
      if (member instanceof xL4gluGCXYYJc.Schema.ArrayType)
        ArrayModel.declare(registry, (xL4gluGCXYYJc.Schema.ArrayType)member);
      else if (member instanceof xL4gluGCXYYJc.Schema.BooleanType)
        BooleanModel.declare(registry, (xL4gluGCXYYJc.Schema.BooleanType)member);
      else if (member instanceof xL4gluGCXYYJc.Schema.NumberType)
        NumberModel.declare(registry, (xL4gluGCXYYJc.Schema.NumberType)member);
      else if (member instanceof xL4gluGCXYYJc.Schema.StringType)
        StringModel.declare(registry, (xL4gluGCXYYJc.Schema.StringType)member);
      else if (member instanceof xL4gluGCXYYJc.Schema.ObjectType)
        ObjectModel.declare(registry, (xL4gluGCXYYJc.Schema.ObjectType)member);
      else
        throw new UnsupportedOperationException("Unsupported member type: " + member.getClass().getName());
    }

    for (final Model model : registry.getModels())
      if (model instanceof Referrer)
        ((Referrer<?>)model).resolveReferences();

    registry.resolveReferences();
  }

  /**
   * Creates a new {@code Schema} from the specified JSONx binding, and with the
   * provided package / class name prefix string.
   *
   * @param schema The XML binding (JSONx).
   * @param prefix The class name prefix to be prepended to the names of
   *          generated JSONx bindings.
   */
  public Schema(final schema.Schema schema, final String prefix) {
    this(jsonxToXsb(schema), prefix);
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
   * Creates a new {@code Schema} by scanning the specified package in the
   * provided class loader, filtered with the given class predicate.
   *
   * @param pkg The package to be scanned for JSONx bindings.
   * @param classLoader The {@code ClassLoader} containing the defined package.
   * @param filter The class {@code Predicate} allowing filtration of scanned
   *          classes.
   * @throws IOException If an I/O error has occurred.
   * @throws PackageNotFoundException If the specified package is not found.
   */
  public Schema(final Package pkg, final ClassLoader classLoader, final Predicate<Class<?>> filter) throws IOException, PackageNotFoundException {
    this(findClasses(pkg, classLoader, filter));
  }

  /**
   * Creates a new {@code Schema} by scanning the specified package in the
   * provided class loader.
   * <p>
   * This constructor is equivalent to calling:
   * <p>
   * <blockquote>
   * {@code new Schema(pkg, classLoader, null)}
   * </blockquote>
   *
   * @param pkg The package to be scanned for JSONx bindings.
   * @param classLoader The {@code ClassLoader} containing the defined package.
   * @throws IOException If an I/O error has occurred.
   * @throws PackageNotFoundException If the specified package is not found.
   */
  public Schema(final Package pkg, final ClassLoader classLoader) throws IOException, PackageNotFoundException {
    this(findClasses(pkg, classLoader, null));
  }

  /**
   * Creates a new {@code Schema} by scanning the specified package, filtered
   * with the given class predicate.
   * <p>
   * This constructor is equivalent to calling:
   * <p>
   * <blockquote>
   * {@code new Schema(pkg, Thread.currentThread().getContextClassLoader(), filter)}
   * </blockquote>
   *
   * @param pkg The package to be scanned for JSONx bindings.
   * @param filter The class {@code Predicate} allowing filtration of scanned
   *          classes.
   * @throws IOException If an I/O error has occurred.
   * @throws PackageNotFoundException If the specified package is not found.
   */
  public Schema(final Package pkg, final Predicate<Class<?>> filter) throws IOException, PackageNotFoundException {
    this(pkg, Thread.currentThread().getContextClassLoader(), filter);
  }

  /**
   * Creates a new {@code Schema} by scanning the specified package.
   * <p>
   * This constructor is equivalent to calling:
   * <p>
   * <blockquote>
   * {@code new Schema(pkg, Thread.currentThread().getContextClassLoader(), null)}
   * </blockquote>
   *
   * @param pkg The package to be scanned for JSONx bindings.
   * @throws IOException If an I/O error has occurred.
   * @throws PackageNotFoundException If the specified package is not found.
   */
  public Schema(final Package pkg) throws IOException, PackageNotFoundException {
    this(pkg, Thread.currentThread().getContextClassLoader(), null);
  }

  /**
   * Creates a new {@code Schema} by scanning the specified classes.
   *
   * @param classes The classes to scan.
   */
  public Schema(final Class<?> ... classes) {
    this(FastCollections.asCollection(new IdentityHashSet<Class<?>>(classes.length), classes));
  }

  /**
   * Creates a new {@code Schema} by scanning the specified classes.
   *
   * @param classes The classes to scan.
   */
  public Schema(final Collection<Class<?>> classes) {
    final Registry registry = new Registry(classes);
    this.registry = registry;
    registry.resolveReferences();
  }

  private List<Model> rootMembers(final Settings settings) {
    final List<Model> members = new ArrayList<>();
    for (final Model model : registry.getModels())
      if (registry.isRootMember(model, settings))
        members.add(model);

    members.sort(new Comparator<Model>() {
      @Override
      public int compare(final Model o1, final Model o2) {
        return o1.compareTo(o2);
      }
    });

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
    attributes.put("xmlns", "http://jsonx.openjax.org/schema-0.9.8.xsd");
    attributes.put("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
    attributes.put("xsi:schemaLocation", "http://jsonx.openjax.org/schema-0.9.8.xsd http://jsonx.openjax.org/schema-0.9.8.xsd");
    return new XmlElement("schema", attributes, elements);
  }

  public XmlElement toXml() {
    return toXml(null);
  }

  public XmlElement toXml(final Settings settings) {
    return toXml(settings == null ? Settings.DEFAULT : settings, this, registry.packageName);
  }

  @Override
  Map<String,Map<String,Object>> toJson(final Settings settings, final Element owner, final String packageName) {
    final Map<String,Map<String,Object>> types;
    final List<Model> members = rootMembers(settings);
    if (members.size() > 0) {
      types = new LinkedHashMap<>();
      for (final Model member : members) {
        final String name = member.id.toString();
        types.put(name.startsWith(packageName) ? name.substring(packageName.length() + 1) : name, member.toJson(settings, this, packageName));
      }
    }
    else {
      types = null;
    }

    return types;
  }

  public Map<String,Map<String,Object>> toJson() {
    return toJson(null);
  }

  public Map<String,Map<String,Object>> toJson(final Settings settings) {
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