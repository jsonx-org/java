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
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.lib4j.lang.Strings;
import org.lib4j.util.Collections;
import org.lib4j.util.Iterators;
import org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.$Element;

class Jsonx extends Factor {
  private final Registry registry;
  private final org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx jsonx;
  private final String packageName;

  public Jsonx(final org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx jsonx) {
    this.jsonx = jsonx;
    this.packageName = (String)jsonx.getPackage$().text();
    final Iterator<? extends $Element> elements = Iterators.filter(jsonx.elementIterator(), $Element.class);
    final StrictRefDigraph<org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx.Object,String> digraph = new StrictRefDigraph<org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx.Object,String>("Object cannot inherit from itself", obj -> obj.getClass$().text());
    final Registry registry = new Registry();
    while (elements.hasNext()) {
      final $Element element = elements.next();
      if (element instanceof org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx.Boolean)
        BooleanModel.declare(registry, (org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx.Boolean)element);
      else if (element instanceof org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx.Number)
        NumberModel.declare(registry, (org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx.Number)element);
      else if (element instanceof org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx.String)
        StringModel.declare(registry, (org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx.String)element);
      else if (element instanceof org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx.Array)
        ArrayModel.declare(registry, (org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx.Array)element);
      else if (element instanceof org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx.Object) {
        final org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx.Object object = (org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx.Object)element;
        digraph.addEdgeRef(object, object.getExtends$().text());
      }
      else {
        throw new UnsupportedOperationException("Unsupported " + jsonx.getClass().getSimpleName() + " member type: " + element.getClass().getName());
      }
    }

    final List<org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx.Object> cycle = digraph.getCycle();
    if (cycle != null)
      throw new ValidationException("Inheritance cycle detected in object hierarchy: " + Collections.toString(digraph.getCycle(), " -> "));

    final ListIterator<org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx.Object> topologicalOrder = digraph.getTopologicalOrder().listIterator(digraph.getSize());
    while (topologicalOrder.hasPrevious())
      ObjectModel.declare(registry, topologicalOrder.previous());

    this.registry = registry;
  }

  public Jsonx(final Class<?> ... classes) {
    final Registry registry = new Registry();
    this.jsonx = null;
    for (final Class<?> clazz : classes)
      ObjectModel.referenceOrDeclare(registry, null, clazz);

    this.registry = registry.normalize();

    final String classPrefix = getClassPrefix();
    this.packageName = classPrefix == null ? null : classPrefix.substring(0, classPrefix.length() - 1);
  }

  public final org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx jsonx() {
    return this.jsonx;
  }

  public final String packageName() {
    return this.packageName;
  }

  public final Collection<Model> members() {
    return this.registry.elements();
  }

  @Override
  protected final void collectClassNames(final List<String> classNames) {
    if (members() != null)
      for (final Model member : members())
        member.collectClassNames(classNames);
  }

  @Override
  protected final String toJSON(final String pacakgeName) {
    final StringBuilder string = new StringBuilder();
    string.append("{\n").append("  package: \"").append(packageName() == null ? "" : packageName()).append('"');
    for (final Model member : members())
      if (!(member instanceof ObjectModel) || registry.getNumReferrers(member) != 1 || ((ObjectModel)member).isAbstract())
        string.append(",\n  \"").append(member.ref()).append("\": ").append(member.toJSON(pacakgeName).replace("\n", "\n  "));

    string.append("\n}");
    return string.toString();
  }

  @Override
  protected final String toJSONX(final String pacakgeName) {
    final StringBuilder string = new StringBuilder("<jsonx\n  package=\"" + (pacakgeName == null ? "" : pacakgeName) + "\"\n  xmlns=\"http://jjb.libx4j.org/jsonx.xsd\"\n  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n  xsi:schemaLocation=\"http://jjb.libx4j.org/jsonx.xsd /Users/seva/Work/SevaSafris/java/libx4j/jjb/generator/src/main/resources/jsonx.xsd\"");
    if (this.registry.size() > 0) {
      string.append(">");
      for (final Model member : members())
        if (!(member instanceof ObjectModel) || registry.getNumReferrers(member) != 1 || ((ObjectModel)member).isAbstract())
          string.append("\n  ").append(member.toJSONX(pacakgeName).replace("\n", "\n  "));

      string.append("\n</jsonx>");
    }
    else {
      string.append("/>");
    }

    return string.toString();
  }

  private final String getClassPrefix() {
    final List<String> classNames = new LinkedList<String>();
    collectClassNames(classNames);
    final String classPrefix = Strings.getCommonPrefix(classNames);
    if (classPrefix == null)
      return null;

    final int index = classPrefix.lastIndexOf('.');
    if (index < 0)
      return null;

    return classPrefix.substring(0, index + 1);
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
    final Map<String,ClassHolder> map = new HashMap<String,ClassHolder>();
    for (final Model member : members()) {
      if (member instanceof ObjectModel && (registry.getNumReferrers(member) <= 1 || ((ObjectModel)member).isAbstract())) {
        final ObjectModel objectModel = (ObjectModel)member;
        final String code = objectModel.toJava();
        final ClassHolder classHolder = new ClassHolder(objectModel.classSimpleName(), objectModel.superObject() == null ? null : objectModel.superObject().className(), objectModel.toObjectAnnotation(), code);
        if (objectModel.className().contains("$"))
          map.get(objectModel.className().substring(0, objectModel.className().lastIndexOf('$'))).memberClasses.add(classHolder);
        else
          map.put(objectModel.className(), classHolder);
      }
    }

    for (final Map.Entry<String,ClassHolder> entry : map.entrySet()) {
      final String className = entry.getKey();
      final ClassHolder holder = entry.getValue();
      final File file = new File(dir, className.replace('.', '/') + ".java");
      file.getParentFile().mkdirs();
      try (final FileOutputStream out = new FileOutputStream(file)) {
        final String string = holder.getAnnotation() + "\npublic " + holder.toString();
        out.write((packageName != null && packageName.length() > 0 ? "package " + packageName + ";\n\n" + string : string).getBytes());
      }
    }
  }
}