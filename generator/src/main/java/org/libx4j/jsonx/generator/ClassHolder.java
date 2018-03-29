package org.libx4j.jsonx.generator;

import java.util.ArrayList;
import java.util.List;

import org.libx4j.jsonx.runtime.JsonxObject;

public class ClassHolder {
  private final String name;
  private final String superName;
  private final String annotation;
  private final String code;

  public ClassHolder(final String name, final String superName, final String annotation, final String code) {
    this.name = name;
    this.superName = superName;
    if (superName != null && superName.contains("Publication")) {
      int i = 0;
    }
    this.annotation = annotation;
    this.code = code;
  }

  public ClassHolder(final String name) {
    this.name = name;
    this.superName = null;
    this.annotation = "";
    this.code = "";
  }

  public String getAnnotation() {
    return "@" + JsonxObject.class.getName() + (annotation.length() == 0 ? "" : "(" + annotation + ")");
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("class ").append(name);
    if (superName != null)
      builder.append(" extends ").append(superName.replaceAll("([^\\\\])\\$", "$1."));

    builder.append(" {");
    for (final ClassHolder memberClass : memberClasses)
      builder.append("\n  ").append(memberClass.getAnnotation()).append("\n  public static ").append(memberClass.toString().replace("\n", "\n  ")).append("\n");

    if (code.length() > 0)
      builder.append("\n  ").append(code.replace("\n", "\n  "));

    return builder.append("\n}").toString();
  }

  public final List<ClassHolder> memberClasses = new ArrayList<ClassHolder>();
}