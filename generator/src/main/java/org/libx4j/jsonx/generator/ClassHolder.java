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
  private final Type type;
  private final String annotation;
  private final String code;

  public ClassHolder(final Type type, final String annotation, final String code) {
    this.type = type;
    this.annotation = annotation;
    this.code = code;
  }

  public ClassHolder(final Type type) {
    this.type = type;
    this.annotation = "";
    this.code = "";
  }

  public String getAnnotation() {
    return "@" + JsonxObject.class.getName() + (annotation.length() == 0 ? "" : "(" + annotation + ")");
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("class ").append(type.getSimpleName());
    if (type.getSuperType() != Type.OBJECT)
      builder.append(" extends ").append(type.getSuperType().getName());

    builder.append(" {");
    for (final ClassHolder memberClass : memberClasses)
      builder.append("\n  ").append(memberClass.getAnnotation()).append("\n  public static ").append(memberClass.toString().replace("\n", "\n  ")).append("\n");

    if (code.length() > 0)
      builder.append("\n  ").append(code.replace("\n", "\n  "));

    return builder.append("\n}").toString();
  }

  public final List<ClassHolder> memberClasses = new ArrayList<ClassHolder>();
}