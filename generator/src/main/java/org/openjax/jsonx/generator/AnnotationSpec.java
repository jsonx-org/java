/* Copyright (c) 2018 OpenJAX
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

package org.openjax.jsonx.generator;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Map;

class AnnotationSpec {
  private static String toAnnotation(final Map<String,String> attributes) {
    final StringBuilder builder = new StringBuilder();
    final Iterator<Map.Entry<String,String>> iterator = attributes.entrySet().iterator();
    for (int i = 0; iterator.hasNext(); ++i) {
      if (i > 0)
        builder.append(", ");

      final Map.Entry<String,String> entry = iterator.next();
      builder.append(entry.getKey()).append('=').append(entry.getValue());
    }

    return builder.toString();
  }

  private final Class<? extends Annotation> annotationType;
  private final AttributeMap attributes;

  AnnotationSpec(final Class<? extends Annotation> annotationType, final AttributeMap attributes) {
    this.annotationType = annotationType;
    this.attributes = attributes;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append('@').append(annotationType.getName());
    if (attributes.size() > 0)
      builder.append('(').append(toAnnotation(attributes)).append(')');

    return builder.toString();
  }
}