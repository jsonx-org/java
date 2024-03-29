/* Copyright (c) 2018 JSONx
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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import org.jsonx.Registry.Type;
import org.libj.lang.Strings;

class ClassSpec {
  enum Scope {
    FIELD,
    GET,
    SET;

    public static final Scope[] values = values();
  }

  private final TreeMap<String,ClassSpec> nameToClassSpec = new TreeMap<>();

  final Referrer<?> referrer;
  private final Registry registry;
  private final Registry.Type type;
  private final Bind.Type typeBinding;

  ClassSpec(final Referrer<?> referrer) {
    this.referrer = referrer;
    this.registry = referrer.registry;
    this.type = referrer.classType();
    this.typeBinding = referrer.typeBinding;
  }

  ClassSpec(final ClassSpec parent, final Registry.Type type) {
    this.referrer = null;
    this.registry = parent.registry;
    this.type = type;
    this.typeBinding = null;
  }

  String getDoc() {
    return referrer != null && referrer.doc != null ? "/** " + referrer.doc.trim() + " **/" : null;
  }

  StringBuilder getAnnotation() {
    StringBuilder b = null;
    if (type.kind == Registry.Kind.ANNOTATION) {
      b = new StringBuilder();
      b.append('@').append(Retention.class.getName()).append('(').append(RetentionPolicy.class.getName()).append('.').append(RetentionPolicy.RUNTIME).append(')');
    }

    if (referrer == null || referrer.getClassAnnotation() == null || referrer.getClassAnnotation().size() == 0)
      return b;

    if (b == null)
      b = new StringBuilder();

    final ArrayList<AnnotationType> annotations = referrer.getClassAnnotation();
    for (int i = 0, i$ = annotations.size(); i < i$; ++i) { // [RA]
      if (b.length() > 0)
        b.append('\n');

      b.append(annotations.get(i));
    }

    return b.length() == 0 ? null : b;
  }

  void add(final ClassSpec classSpec) {
    nameToClassSpec.put(classSpec.type.name, classSpec);
  }

  private String toString(final ClassSpec parent) {
    final StringBuilder b = new StringBuilder();
    if (referrer instanceof ObjectModel && ((ObjectModel)referrer).isAbstract)
      b.append("abstract ");

    if (parent != null)
      b.append("static ");

    b.append(type.kind).append(' ').append(type.simpleName);
    if (type.kind == Registry.Kind.CLASS && referrer != null) {
      final Type superType = type.superType;
      if (type.hasJxSuperType()) {
        b.append(" extends ").append(superType.canonicalName);
        if (typeBinding != null)
          b.append(" implements ").append(typeBinding.type.canonicalName);
      }
      else {
        if (typeBinding != null)
          b.append(" extends ").append(typeBinding.type.canonicalName);

        b.append(" implements ").append(JxObject.class.getCanonicalName());
      }
    }

    b.append(" {");
    final Collection<ClassSpec> classSpecs = nameToClassSpec.values();
    final int size = classSpecs.size();
    if (size > 0) {
      final Iterator<ClassSpec> iterator = classSpecs.iterator();
      for (int i = 0; iterator.hasNext(); ++i) { // [I]
        if (i > 0)
          b.append('\n');

        final ClassSpec memberClass = iterator.next();

        final String memberDoc = memberClass.getDoc();
        if (memberDoc != null)
          b.append("\n  ").append(memberDoc);

        b.append("\n  @").append(JxBinding.class.getName()).append("(targetNamespace = \"").append(registry.targetNamespace).append("\")");

        final StringBuilder annotation = memberClass.getAnnotation();
        if (annotation != null)
          b.append("\n  ").append(Strings.indent(annotation, 2));

        b.append("\n  public ").append(Strings.indent(memberClass.toString(this), 2));
      }
    }

    if (referrer != null) {
      final String code = referrer.toSource(registry.settings);
      if (code != null && code.length() > 0) {
        if (size > 0)
          b.append('\n');

        b.append("\n  ").append(Strings.indent(code, 2));
      }
    }

    return b.append("\n}").toString();
  }

  @Override
  public String toString() {
    return toString(null);
  }
}