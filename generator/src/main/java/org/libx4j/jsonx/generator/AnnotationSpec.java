package org.libx4j.jsonx.generator;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Map;

public class AnnotationSpec {
  private static String toAnnotation(final Map<String,String> attributes) {
    final StringBuilder builder = new StringBuilder();
    final Iterator<Map.Entry<String,String>> iterator = attributes.entrySet().iterator();
    for (int i = 0; iterator.hasNext(); i++) {
      final Map.Entry<String,String> entry = iterator.next();
      if (i > 0)
        builder.append(", ");

      builder.append(entry.getKey()).append('=').append(entry.getValue());
    }

    return builder.toString();
  }

  private final Class<? extends Annotation> annotation;
  private final AttributeMap attributes;

  public AnnotationSpec(final Class<? extends Annotation> annotation, final AttributeMap attributes) {
    this.annotation = annotation;
    this.attributes = attributes;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append('@').append(annotation.getName());
    if (attributes.size() > 0)
      builder.append('(').append(toAnnotation(attributes)).append(')');

    return builder.toString();
  }
}