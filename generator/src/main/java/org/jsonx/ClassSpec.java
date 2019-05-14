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

import java.util.Iterator;
import java.util.TreeMap;

class ClassSpec {
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

  String getAnnotation() {
    if (referrer == null)
      return null;

    if (referrer.getClassAnnotation() == null)
      return null;

    final StringBuilder builder = new StringBuilder();
    final Iterator<AnnotationSpec> iterator = referrer.getClassAnnotation().iterator();
    for (int i = 0; iterator.hasNext(); ++i) {
      if (i > 0)
        builder.append('\n');

      builder.append(iterator.next());
    }

    return builder.toString();
  }

  void add(final ClassSpec classSpec) {
    nameToClassSpec.put(classSpec.type.getName(), classSpec);
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    if (referrer instanceof ObjectModel && ((ObjectModel)referrer).isAbstract)
      builder.append("abstract ");

    builder.append(type.getKind()).append(' ').append(type.getSimpleName());
    if (type.getKind() == Registry.Kind.CLASS && referrer != null) {
      if (type.getSuperType() != null)
        builder.append(" extends ").append(type.getSuperType().getCanonicalName());
      else
        builder.append(" implements ").append(JxObject.class.getCanonicalName());
    }

    builder.append(" {");
    final Iterator<ClassSpec> iterator = nameToClassSpec.values().iterator();
    for (int i = 0; iterator.hasNext(); ++i) {
      final ClassSpec memberClass = iterator.next();
      if (i > 0)
        builder.append('\n');

      final String annotation = memberClass.getAnnotation();
      if (annotation != null)
        builder.append("\n  ").append(annotation.replace("\n", "\n  "));

      builder.append("\n  public static ").append(memberClass.toString().replace("\n", "\n  "));
    }

    if (referrer != null) {
      final String code = referrer.toSource(settings);
      if (code != null && code.length() > 0)
        builder.append("\n  ").append(code.replace("\n", "\n  "));
    }

    return builder.append("\n}").toString();
  }
}