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

  public String getAnnotation() {
    return "@" + JsonxObject.class.getName() + (annotation.length() == 0 ? "" : "(" + annotation + ")");
  }

  @Override
  public String toString() {
    final StringBuilder string = new StringBuilder();
    string.append("class ").append(name);
    if (superName != null)
      string.append(" extends ").append(superName);

    string.append(" {");
    for (final ClassHolder memberClass : memberClasses)
      string.append("\n  ").append(memberClass.getAnnotation()).append("\n  public static ").append(memberClass.toString().replace("\n", "\n  ")).append("\n");

    if (code.length() > 0)
      string.append("\n  ").append(code.replace("\n", "\n  "));

    return string.append("\n}").toString();
  }

  public final List<ClassHolder> memberClasses = new ArrayList<ClassHolder>();
}