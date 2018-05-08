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
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.lib4j.lang.Strings;
import org.lib4j.util.Collections;
import org.lib4j.util.Iterators;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Member;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.Jsonx;

public class Schema extends Member {
  private final Registry registry;
  private final Jsonx jsonx;
  private final String packageName;

  public Schema(final Jsonx jsonx) {
    this.jsonx = jsonx;
    this.packageName = jsonx.getPackage$().text() == null || jsonx.getPackage$().text().length() == 0 ? null : jsonx.getPackage$().text();
    final Iterator<? super $Member> iterator = Iterators.filter(jsonx.elementIterator(), m -> $Member.class.isInstance(m));
    final StrictRefDigraph<Jsonx.Object,String> digraph = new StrictRefDigraph<Jsonx.Object,String>("Object cannot inherit from itself", obj -> obj.getClass$().text());
    final Registry registry = new Registry();
    while (iterator.hasNext()) {
      final $Member member = ($Member)iterator.next();
      if (member instanceof Jsonx.Boolean)
        BooleanModel.declare(this, registry, (Jsonx.Boolean)member);
      else if (member instanceof Jsonx.Number)
        NumberModel.declare(this, registry, (Jsonx.Number)member);
      else if (member instanceof Jsonx.String)
        StringModel.declare(this, registry, (Jsonx.String)member);
      else if (member instanceof Jsonx.Array)
        ArrayModel.declare(this, registry, (Jsonx.Array)member);
      else if (member instanceof Jsonx.Object) {
        final Jsonx.Object object = (Jsonx.Object)member;
        if (object.getExtends$() != null)
          digraph.addEdgeRef(object, object.getExtends$().text());
        else
          digraph.addVertex(object);
      }
      else {
        throw new UnsupportedOperationException("Unsupported " + jsonx.getClass().getSimpleName() + " member type: " + member.getClass().getName());
      }
    }

    final List<Jsonx.Object> cycle = digraph.getCycle();
    if (cycle != null)
      throw new ValidationException("Inheritance cycle detected in object hierarchy: " + Collections.toString(digraph.getCycle(), " -> "));

    final ListIterator<Jsonx.Object> topologicalOrder = digraph.getTopologicalOrder().listIterator(digraph.getSize());
    while (topologicalOrder.hasPrevious())
      ObjectModel.declare(this, registry, topologicalOrder.previous());

    this.registry = registry;
  }

  public Schema(final Class<?> ... classes) {
    final Registry registry = new Registry();
    this.jsonx = null;
    for (final Class<?> clazz : classes)
      ObjectModel.referenceOrDeclare(registry, this, clazz);

    this.registry = registry.normalize();
    this.packageName = getClassPrefix();
  }

  public final Jsonx jsonx() {
    return this.jsonx;
  }

  public final String packageName() {
    return this.packageName;
  }

  public final Collection<Model> members() {
    return this.registry.elements();
  }

  @Override
  protected final void collectClassNames(final List<Type> types) {
    if (members() != null)
      for (final Model member : members())
        member.collectClassNames(types);
  }

  @Override
  protected final String toJSON(final String pacakgeName) {
    final StringBuilder builder = new StringBuilder();
    builder.append("{\n").append("  package: \"").append(packageName() == null ? "" : packageName()).append('"');
    for (final Model member : members())
      if (!(member instanceof ObjectModel) || registry.getNumReferrers(member) != 1 || ((ObjectModel)member).isAbstract())
        builder.append(",\n  \"").append(member.reference()).append("\": ").append(member.toJSON(pacakgeName).replace("\n", "\n  "));

    builder.append("\n}");
    return builder.toString();
  }

  @Override
  protected final String toJSONX(final String pacakgeName) {
    final StringBuilder builder = new StringBuilder("<jsonx\n  package=\"" + (pacakgeName == null ? "" : pacakgeName) + "\"\n  xmlns=\"http://jsonx.libx4j.org/jsonx-0.9.8.xsd\"\n  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n  xsi:schemaLocation=\"http://jsonx.libx4j.org/jsonx-0.9.8.xsd /Users/seva/Work/SevaSafris/java/libx4j/jsonx/generator/src/main/resources/jsonx.xsd\"");
    if (this.registry.size() > 0) {
      builder.append('>');
      for (final Model member : members())
        if (!(member instanceof ObjectModel) || member.owner() instanceof Schema)
          builder.append("\n  ").append(member.toJSONX(pacakgeName).replace("\n", "\n  "));

      builder.append("\n</jsonx>");
    }
    else {
      builder.append("/>");
    }

    return builder.toString();
  }

  private String getClassPrefix() {
    final List<Type> types = new ArrayList<Type>();
    collectClassNames(types);
    final String classPrefix = Strings.getCommonPrefix(types.stream().map(t -> t.toString()).toArray(String[]::new));
    if (classPrefix == null)
      return null;

    final int index = classPrefix.lastIndexOf('.');
    if (index < 0)
      return null;

    return classPrefix.substring(0, index + 1);
  }

  @Override
  protected Schema getSchema() {
    return this;
  }

  @Override
  public final String toJSON() {
    return toJSON(packageName);
  }

  @Override
  public final String toJSONX() {
    return toJSONX(packageName);
  }

  public void toJava(final File dir) throws IOException {
    final Map<Type,ClassHolder> all = new HashMap<Type,ClassHolder>();
    final Map<Type,ClassHolder> typeToClassHolder = new HashMap<Type,ClassHolder>();
    for (final Model member : members()) {
      if (member instanceof ObjectModel) {
        final ObjectModel model = (ObjectModel)member;
        final ClassHolder classHolder = new ClassHolder(model.type(), model.toObjectAnnotation(), model.toJava());
        if (model.type().getDeclaringType() != null) {
          final Type declaringType = model.type().getDeclaringType();
          ClassHolder parent = all.get(declaringType);
          if (parent == null) {
            parent = new ClassHolder(declaringType);
            typeToClassHolder.put(declaringType, parent);
            all.put(declaringType, parent);
          }

          parent.memberClasses.add(classHolder);
        }
        else {
          typeToClassHolder.put(model.type(), classHolder);
        }

        all.put(model.type(), classHolder);
      }
    }

    for (final Map.Entry<Type,ClassHolder> entry : typeToClassHolder.entrySet()) {
      final Type type = entry.getKey();
      final ClassHolder holder = entry.getValue();
      final File file = new File(dir, type.getName().replace('.', '/') + ".java");
      file.getParentFile().mkdirs();
      try (final FileOutputStream out = new FileOutputStream(file)) {
        final String string = holder.getAnnotation() + "\npublic " + holder.toString();
        out.write((type.getPackage() != null ? "package " + type.getPackage() + ";\n\n" + string : string).getBytes());
      }
    }
  }
}