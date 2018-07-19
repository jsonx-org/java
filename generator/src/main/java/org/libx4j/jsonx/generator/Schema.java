/* Copyright (c) 2017 lib4j
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

package org.libx4j.jsonx.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import org.lib4j.lang.PackageLoader;
import org.lib4j.lang.PackageNotFoundException;
import org.lib4j.lang.Strings;
import org.lib4j.util.Collections;
import org.lib4j.util.IdentityHashSet;
import org.lib4j.util.Iterators;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Member;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$ObjectMember;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$TemplateMember;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.Jsonx;
import org.libx4j.jsonx.runtime.JsonxObject;
import org.libx4j.xsb.runtime.Binding;

public final class Schema extends Element {
  private static void findInnerRelations(final StrictRefDigraph<$Member,String> digraph, final Registry registry, final $Member object, final $Member member) {
    final Iterator<? super Binding> iterator = Iterators.filter(member.elementIterator(), m -> $Member.class.isInstance(m));
    while (iterator.hasNext()) {
      final $Member next = ($Member)iterator.next();
      if (next instanceof $ObjectMember) {
        final $ObjectMember model = ($ObjectMember)next;
        if (model.getExtends$() != null)
          digraph.addEdgeRef(object, model.getExtends$().text());
      }
      else if (next instanceof $TemplateMember) {
        digraph.addEdgeRef(object, (($TemplateMember)next).getReference$().text());
      }

      findInnerRelations(digraph, registry, object, next);
    }
  }

  private final Registry registry;
  private final String packageName;

  public Schema(final Jsonx jsonx) {
    this.packageName = (String)jsonx.getPackage$().text();
    final Iterator<? super $Member> iterator = Iterators.filter(jsonx.elementIterator(), m -> $Member.class.isInstance(m));
    final StrictRefDigraph<$Member,String> digraph = new StrictRefDigraph<>("Object cannot inherit from itself", obj -> {
      if (obj instanceof Jsonx.Array)
        return ((Jsonx.Array)obj).getTemplate$().text();

      if (obj instanceof Jsonx.Boolean)
        return ((Jsonx.Boolean)obj).getTemplate$().text();

      if (obj instanceof Jsonx.Number)
        return ((Jsonx.Number)obj).getTemplate$().text();

      if (obj instanceof Jsonx.String)
        return ((Jsonx.String)obj).getTemplate$().text();

      if (obj instanceof Jsonx.Object)
        return ((Jsonx.Object)obj).getClass$().text();

      throw new UnsupportedOperationException("Unsupported member type: " + obj.getClass().getName());
    });

    this.registry = new Registry();
    while (iterator.hasNext()) {
      final $Member member = ($Member)iterator.next();
      if (member instanceof Jsonx.Object) {
        final Jsonx.Object object = (Jsonx.Object)member;
        if (object.getExtends$() != null)
          digraph.addEdgeRef(object, object.getExtends$().text());
        else
          digraph.addVertex(object);

        findInnerRelations(digraph, registry, member, member);
      }
      else if (member instanceof Jsonx.Array) {
        digraph.addVertex(member);
        findInnerRelations(digraph, registry, member, member);
      }
      else {
        digraph.addVertex(member);
      }
    }

    final List<$Member> cycle = digraph.getCycle();
    if (cycle != null)
      throw new ValidationException("Inheritance cycle detected in object hierarchy: " + Collections.toString(digraph.getCycle(), " -> "));

    final ListIterator<$Member> topologicalOrder = digraph.getTopologicalOrder().listIterator(digraph.getSize());
    while (topologicalOrder.hasPrevious()) {
      final $Member member = topologicalOrder.previous();
      if (member instanceof Jsonx.Array)
        ArrayModel.declare(registry, (Jsonx.Array)member);
      else if (member instanceof Jsonx.Boolean)
        BooleanModel.declare(registry, (Jsonx.Boolean)member);
      else if (member instanceof Jsonx.Number)
        NumberModel.declare(registry, (Jsonx.Number)member);
      else if (member instanceof Jsonx.String)
        StringModel.declare(registry, (Jsonx.String)member);
      else if (member instanceof Jsonx.Object)
        ObjectModel.declare(registry, (Jsonx.Object)member);
      else
        throw new UnsupportedOperationException("Unsupported member type: " + member.getClass().getName());
    }
  }

  public Schema(final Package pkg, final ClassLoader classLoader, final Predicate<Class<?>> filter) throws PackageNotFoundException {
    this(PackageLoader.getPackageLoader(classLoader).loadPackage(pkg, c -> c.isAnnotationPresent(JsonxObject.class) && filter.test(c)));
  }

  public Schema(final Package pkg, final ClassLoader classLoader) throws PackageNotFoundException {
    this(PackageLoader.getPackageLoader(classLoader).loadPackage(pkg, c -> c.isAnnotationPresent(JsonxObject.class)));
  }

  public Schema(final Package pkg, final Predicate<Class<?>> filter) throws PackageNotFoundException {
    this(pkg, Thread.currentThread().getContextClassLoader(), filter);
  }

  public Schema(final Package pkg) throws PackageNotFoundException {
    this(pkg, Thread.currentThread().getContextClassLoader());
  }

  public Schema(final Class<?> ... classes) {
    this(Collections.asCollection(new IdentityHashSet<Class<?>>(classes.length), classes));
  }

  public Schema(final Set<Class<?>> classes) {
    final Registry registry = new Registry();
    for (final Class<?> cls : classes)
      ObjectModel.referenceOrDeclare(registry, cls);

    this.registry = registry;
    this.packageName = getClassPrefix();
  }

  private String getClassPrefix() {
    final Set<Registry.Type> types = new HashSet<>();
    getDeclaredTypes(types);
    final String classPrefix = Strings.getCommonPrefix(types.stream().map(t -> t.getPackage()).toArray(String[]::new));
    if (classPrefix == null)
      return null;

    final int index = classPrefix.lastIndexOf('.');
    return index == -1 ? "" : classPrefix.substring(0, index);
  }

  private Collection<Model> rootMembers(final Settings settings) {
    final List<Model> members = new ArrayList<>();
    for (final Model model : registry.rootElements())
      if (registry.writeRootMember(model, settings))
        members.add(model);

    members.sort(new Comparator<Model>() {
      @Override
      public int compare(final Model o1, final Model o2) {
        if (o1 instanceof ObjectModel)
          return o2 instanceof ObjectModel ? o1.type().getName().compareTo(o2.type().getName()) : 1;

        return o2 instanceof ObjectModel ? -1 : (o1.getClass().getSimpleName() + o1.id() + o1.name()).compareTo(o2.getClass().getSimpleName() + o2.id() + o2.name());
      }
    });

    return members;
  }

  private Collection<Model> members() {
    return registry.rootElements();
  }

  @Override
  protected void getDeclaredTypes(final Set<Registry.Type> types) {
    if (members() != null)
      for (final Model member : members())
        member.getDeclaredTypes(types);
  }

  @Override
  protected org.lib4j.xml.Element toXml(final Settings settings, final Element owner, final String packageName) {
    final List<org.lib4j.xml.Element> elements;
    final Collection<Model> members = rootMembers(settings);
    if (members.size() > 0) {
      elements = new ArrayList<>();
      for (final Model member : members)
        elements.add(member.toXml(settings, this, packageName));
    }
    else {
      elements = null;
    }

    final Map<String,String> attributes = super.toAnnotationAttributes(owner, packageName);
    if (packageName.length() > 0)
      attributes.put("package", packageName);

    attributes.put("xmlns", "http://jsonx.libx4j.org/jsonx-0.9.8.xsd");
    attributes.put("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
    attributes.put("xsi:schemaLocation", "http://jsonx.libx4j.org/jsonx-0.9.8.xsd http://jsonx.libx4j.org/jsonx-0.9.8.xsd");
    return new org.lib4j.xml.Element("jsonx", attributes, elements);
  }

  public org.lib4j.xml.Element toXml() {
    return toXml(null);
  }

  public org.lib4j.xml.Element toXml(final Settings settings) {
    return toXml(settings == null ? Settings.DEFAULT : settings, this, packageName);
  }

  public Map<String,String> toJava() {
    final Map<Registry.Type,JavaClass> all = new HashMap<>();
    final Map<Registry.Type,JavaClass> typeToJavaClass = new HashMap<>();
    for (final Model member : members()) {
      if (member instanceof ObjectModel) {
        final ObjectModel model = (ObjectModel)member;
        final JavaClass javaClass = new JavaClass(model);
        if (model.type().getDeclaringType() != null) {
          final Registry.Type declaringType = model.type().getDeclaringType();
          JavaClass parent = all.get(declaringType);
          if (parent == null) {
            parent = new JavaClass(declaringType);
            typeToJavaClass.put(declaringType, parent);
            all.put(declaringType, parent);
          }

          parent.add(javaClass);
        }
        else {
          typeToJavaClass.put(model.type(), javaClass);
        }

        all.put(model.type(), javaClass);
      }
    }

    final Map<String,String> sources = new HashMap<>();
    for (final Map.Entry<Registry.Type,JavaClass> entry : typeToJavaClass.entrySet()) {
      final Registry.Type type = entry.getKey();
      final JavaClass holder = entry.getValue();
      final StringBuilder builder = new StringBuilder();
      final String canonicalPackageName = type.getCanonicalPackage();
      if (canonicalPackageName != null)
        builder.append("package ").append(canonicalPackageName).append(";\n");

      final AnnotationSpec annotation = holder.getAnnotation();
      if (annotation != null)
        builder.append('\n').append(annotation);

      builder.append("\npublic ").append(holder);
      sources.put(type.getName(), builder.toString());
    }

    return sources;
  }

  public Map<String,String> toJava(final File dir) throws IOException {
    final Map<String,String> sources = toJava();
    for (final Map.Entry<String,String> entry : sources.entrySet()) {
      final File file = new File(dir, entry.getKey().replace('.', '/') + ".java");
      file.getParentFile().mkdirs();
      try (final OutputStreamWriter out = new FileWriter(file)) {
        out.write(entry.getValue());
      }
    }

    return sources;
  }
}