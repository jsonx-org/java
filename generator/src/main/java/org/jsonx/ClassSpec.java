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

    public static Scope[] values = values();
  }

  private final TreeMap<String,ClassSpec> nameToClassSpec = new TreeMap<>();

  private final Settings settings;
  private final Referrer<?> referrer;
  private final Registry.Type type;

  ClassSpec(final Referrer<?> referrer, final Settings settings) {
    this.settings = settings;
    this.referrer = referrer;
    this.type = referrer.classType();
  }

  ClassSpec(final Registry.Type type, final Settings settings) {
    this.settings = settings;
    this.referrer = null;
    this.type = type;
  }

  String getDoc() {
    return referrer != null && referrer.doc != null ? "/** " + referrer.doc.trim() + " **/" : null;
  }

  StringBuilder getAnnotation() {
    StringBuilder builder = null;
    if (type.getKind() == Registry.Kind.ANNOTATION) {
      builder = new StringBuilder();
      builder.append('@').append(Retention.class.getName()).append('(').append(RetentionPolicy.class.getName()).append('.').append(RetentionPolicy.RUNTIME).append(')');
    }

    if (referrer == null || referrer.getClassAnnotation() == null || referrer.getClassAnnotation().size() == 0)
      return builder;

    if (builder == null)
      builder = new StringBuilder();

    final ArrayList<AnnotationType> annotations = referrer.getClassAnnotation();
    for (int i = 0, i$ = annotations.size(); i < i$; ++i) { // [RA]
      if (builder.length() > 0)
        builder.append('\n');

      builder.append(annotations.get(i));
    }

    return builder.length() == 0 ? null : builder;
  }

  void add(final ClassSpec classSpec) {
    nameToClassSpec.put(classSpec.type.getName(), classSpec);
  }

  private String toString(final ClassSpec parent) {
    final StringBuilder b = new StringBuilder();
    if (referrer instanceof ObjectModel && ((ObjectModel)referrer).isAbstract)
      b.append("abstract ");

    if (parent != null)
      b.append("static ");

    b.append(type.getKind()).append(' ').append(type.getSimpleName());
    if (type.getKind() == Registry.Kind.CLASS && referrer != null) {
      final Type superType = type.getSuperType();
      if (superType != null)
        b.append(" extends ").append(superType.getCanonicalName());
      else
        b.append(" implements ").append(JxObject.class.getCanonicalName());
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
        final StringBuilder annotation = memberClass.getAnnotation();
        if (annotation != null)
          b.append("\n  ").append(Strings.indent(annotation, 2));

        final String memberDoc = memberClass.getDoc();
        if (memberDoc != null)
          b.append("\n  ").append(memberDoc);

        b.append("\n  public ").append(Strings.indent(memberClass.toString(this), 2));
      }
    }

    if (referrer != null) {
      final String code = referrer.toSource(settings);
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