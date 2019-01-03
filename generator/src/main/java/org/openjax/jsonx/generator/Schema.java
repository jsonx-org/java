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
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.openjax.jsonx.jsonx_0_9_8.xL3gluGCXYYJc.$Member;
import org.openjax.jsonx.jsonx_0_9_8.xL3gluGCXYYJc.$ObjectMember;
import org.openjax.jsonx.jsonx_0_9_8.xL3gluGCXYYJc.$ReferenceMember;
import org.openjax.jsonx.jsonx_0_9_8.xL3gluGCXYYJc.Jsonx;
import org.openjax.jsonx.runtime.ArrayType;
import org.openjax.jsonx.runtime.JxObject;
import org.openjax.jsonx.runtime.ValidationException;
import org.openjax.standard.lang.PackageLoader;
import org.openjax.standard.lang.PackageNotFoundException;
import org.openjax.standard.util.FastCollections;
import org.openjax.standard.util.IdentityHashSet;
import org.openjax.standard.util.Iterators;
import org.openjax.xsb.runtime.Binding;

public final class Schema extends Element {
  private static void findInnerRelations(final StrictRefDigraph<$Member,String> digraph, final Registry registry, final $Member object, final $Member member) {
    final Iterator<? super Binding> iterator = Iterators.filter(member.elementIterator(), m -> m instanceof $Member);
    while (iterator.hasNext()) {
      final $Member next = ($Member)iterator.next();
      if (next instanceof $ObjectMember) {
        final $ObjectMember model = ($ObjectMember)next;
        if (model.getExtends$() != null)
          digraph.addEdgeRef(object, model.getExtends$().text());
      }
      else if (next instanceof $ReferenceMember) {
        digraph.addEdgeRef(object, (($ReferenceMember)next).getType$().text());
      }

      findInnerRelations(digraph, registry, object, next);
    }
  }

  private final Registry registry;

  public Schema(final Jsonx jsonx, final String packageName) {
    this.registry = new Registry(packageName);

    final StrictRefDigraph<$Member,String> digraph = new StrictRefDigraph<>("Object cannot inherit from itself", obj -> {
      if (obj instanceof Jsonx.ArrayType)
        return ((Jsonx.ArrayType)obj).getName$().text();

      if (obj instanceof Jsonx.BooleanType)
        return ((Jsonx.BooleanType)obj).getName$().text();

      if (obj instanceof Jsonx.NumberType)
        return ((Jsonx.NumberType)obj).getName$().text();

      if (obj instanceof Jsonx.StringType)
        return ((Jsonx.StringType)obj).getName$().text();

      if (obj instanceof Jsonx.ObjectType)
        return ((Jsonx.ObjectType)obj).getName$().text();

      throw new UnsupportedOperationException("Unsupported member type: " + obj.getClass().getName());
    });

    final Iterator<? super $Member> iterator = Iterators.filter(jsonx.elementIterator(), m -> m instanceof $Member);
    while (iterator.hasNext()) {
      final $Member member = ($Member)iterator.next();
      if (member instanceof Jsonx.ArrayType) {
        digraph.addVertex(member);
        findInnerRelations(digraph, registry, member, member);
      }
      else if (member instanceof Jsonx.ObjectType) {
        final Jsonx.ObjectType object = (Jsonx.ObjectType)member;
        if (object.getExtends$() != null)
          digraph.addEdgeRef(object, object.getExtends$().text());
        else
          digraph.addVertex(object);

        findInnerRelations(digraph, registry, member, member);
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
      if (member instanceof Jsonx.ArrayType)
        ArrayModel.declare(registry, (Jsonx.ArrayType)member);
      else if (member instanceof Jsonx.BooleanType)
        BooleanModel.declare(registry, (Jsonx.BooleanType)member);
      else if (member instanceof Jsonx.NumberType)
        NumberModel.declare(registry, (Jsonx.NumberType)member);
      else if (member instanceof Jsonx.StringType)
        StringModel.declare(registry, (Jsonx.StringType)member);
      else if (member instanceof Jsonx.ObjectType)
        ObjectModel.declare(registry, (Jsonx.ObjectType)member);
      else
        throw new UnsupportedOperationException("Unsupported member type: " + member.getClass().getName());
    }
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

  public Schema(final Package pkg, final ClassLoader classLoader, final Predicate<Class<?>> filter) throws IOException, PackageNotFoundException {
    this(findClasses(pkg, classLoader, filter));
  }

  public Schema(final Package pkg, final ClassLoader classLoader) throws IOException, PackageNotFoundException {
    this(findClasses(pkg, classLoader, null));
  }

  public Schema(final Package pkg, final Predicate<Class<?>> filter) throws IOException, PackageNotFoundException {
    this(pkg, Thread.currentThread().getContextClassLoader(), filter);
  }

  public Schema(final Package pkg) throws IOException, PackageNotFoundException {
    this(pkg, Thread.currentThread().getContextClassLoader());
  }

  public Schema(final Class<?> ... classes) {
    this(FastCollections.asCollection(new IdentityHashSet<Class<?>>(classes.length), classes));
  }

  public Schema(final Set<Class<?>> classes) {
    final Registry registry = new Registry(classes);
    this.registry = registry;
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
  org.openjax.standard.xml.api.Element toXml(final Settings settings, final Element owner, final String packageName) {
    final List<org.openjax.standard.xml.api.Element> elements;
    final List<Model> members = rootMembers(settings);
    if (members.size() > 0) {
      elements = new ArrayList<>();
      for (final Model member : members)
        elements.add(member.toXml(settings, this, packageName));
    }
    else {
      elements = null;
    }

    final Map<String,String> attributes = super.toXmlAttributes(owner, packageName);
    attributes.put("xmlns", "http://jsonx.openjax.org/jsonx-0.9.8.xsd");
    attributes.put("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
    attributes.put("xsi:schemaLocation", "http://jsonx.openjax.org/jsonx-0.9.8.xsd http://jsonx.openjax.org/jsonx-0.9.8.xsd");
    return new org.openjax.standard.xml.api.Element("jsonx", attributes, elements);
  }

  public org.openjax.standard.xml.api.Element toXml() {
    return toXml(null);
  }

  public org.openjax.standard.xml.api.Element toXml(final Settings settings) {
    return toXml(settings == null ? Settings.DEFAULT : settings, this, registry.packageName);
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