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

import java.util.ArrayList;
import java.util.List;

import org.libx4j.jsonx.runtime.JsonxObject;

public class ClassHolder {
  private final ObjectModel model;
  private final Type type;

  public ClassHolder(final ObjectModel model) {
    this.model = model;
    this.type = model.type();
  }

  public ClassHolder(final Type type) {
    this.model = null;
    this.type = type;
  }

  public String getAnnotation() {
    if (model == null)
      return null;

    final String annotation = model.toObjectAnnotation();
    return "@" + JsonxObject.class.getName() + (annotation.length() == 0 ? "" : "(" + annotation + ")");
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    if ("ObjectExtendsAbstract".equals(type.getSimpleName())) {
      int i = 0;
    }
    builder.append("class ").append(type.getSimpleName());
    if (type.getSuperType() != Type.OBJECT)
      builder.append(" extends ").append(type.getSuperType().getStrictName());

    builder.append(" {");
    boolean isFirst = true;
    for (final ClassHolder memberClass : memberClasses) {
      if (isFirst)
        isFirst = false;
      else
        builder.append('\n');

      final String annotation = memberClass.getAnnotation();
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

  public final List<ClassHolder> memberClasses = new ArrayList<ClassHolder>();
}