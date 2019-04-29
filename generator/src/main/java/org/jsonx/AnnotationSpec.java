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

package org.jsonx;

import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

class AnnotationSpec {
  @SuppressWarnings("unchecked")
  private static StringBuilder toAnnotation(final Map<String,Object> attributes) {
    final StringBuilder builder = new StringBuilder();
    final Iterator<Map.Entry<String,Object>> iterator = attributes.entrySet().iterator();
    for (int i = 0; iterator.hasNext(); ++i) {
      if (i > 0)
        builder.append(", ");

      final Map.Entry<String,Object> entry = iterator.next();
      builder.append(entry.getKey()).append('=');
      if (entry.getValue() instanceof List) {
        final List<Object> items = (List<Object>)entry.getValue();
        if (items.size() == 1) {
          builder.append(items.get(0));
        }
        else {
          builder.append('{');
          for (int j = 0; j < items.size(); ++j) {
            if (j > 0)
              builder.append(", ");

            builder.append(items.get(j));
          }

          builder.append('}');
        }
      }
      else {
        builder.append(entry.getValue());
      }
    }

    return builder;
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