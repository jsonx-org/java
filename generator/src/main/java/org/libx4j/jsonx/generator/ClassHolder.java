/* Copyright (c) 2018 lib4j
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

import java.util.Iterator;
import java.util.TreeMap;

import org.libx4j.jsonx.runtime.JsonxObject;

public class ClassHolder {
  private final TreeMap<String,ClassHolder> memberClasses = new TreeMap<>();

  private final ObjectModel model;
  private final Registry.Type type;

  public ClassHolder(final ObjectModel model) {
    this.model = model;
    this.type = model.type();
  }

  public ClassHolder(final Registry.Type type) {
    this.model = null;
    this.type = type;
  }

  public AnnotationSpec getAnnotation() {
    return model == null ? null : new AnnotationSpec(JsonxObject.class, model.toObjectAnnotation());
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    if (model != null && model.isAbstract())
      builder.append("abstract ");

    builder.append("class ").append(type.getSimpleName());
    if (type.getSuperType() != null)
      builder.append(" extends ").append(type.getSuperType().getCanonicalName());

    builder.append(" {");
    final Iterator<ClassHolder> iterator = memberClasses.values().iterator();
    for (int i = 0; iterator.hasNext(); i++) {
      final ClassHolder memberClass = iterator.next();
      if (i > 0)
        builder.append('\n');

      final AnnotationSpec annotation = memberClass.getAnnotation();
      if (annotation != null)
        builder.append("\n  ").append(annotation);

      builder.append("\n  public static ").append(memberClass.toString().replace("\n", "\n  "));
    }

    if (model != null) {
      final String code = model.toJava();
      if (code.length() > 0)
        builder.append("\n  ").append(code.replace("\n", "\n  "));
    }

    return builder.append("\n}").toString();
  }

  public void add(final ClassHolder classHolder) {
    memberClasses.put(classHolder.type.toString(), classHolder);
  }
}