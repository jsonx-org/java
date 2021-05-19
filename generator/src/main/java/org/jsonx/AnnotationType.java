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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.libj.lang.Classes;

class AnnotationType {
  @SuppressWarnings("unchecked")
  private StringBuilder render() {
    final StringBuilder builder = new StringBuilder();
    for (final Map.Entry<String,Object> entry : attributes.entrySet()) {
      if (builder.length() > 0)
        builder.append(", ");

      final Method method = Classes.getMethod(annotationType, entry.getKey());
      final Object defaultValue = method.getDefaultValue();
      if (entry.getValue() instanceof List) {
        final Object[] defaultArray = (Object[])defaultValue;
        final List<Object> items = (List<Object>)entry.getValue();
        if (defaultArray != null) {
          if (items.size() == 0 && defaultArray.length == 0)
            continue;

          if (defaultArray.equals(items.toArray()))
            continue;
        }

        builder.append(entry.getKey()).append('=');
        if (items.size() == 1) {
          builder.append(items.get(0));
        }
        else {
          builder.append('{');
          for (int j = 0, len = items.size(); j < len; ++j) {
            if (j > 0)
              builder.append(", ");

            builder.append(items.get(j));
          }

          builder.append('}');
        }
      }
      else {
        final Object fixedDefaultValue = defaultValue != null && entry.getValue() instanceof String ? "\"" + defaultValue + "\"" : defaultValue;
        if (!Objects.equals(fixedDefaultValue, entry.getValue()))
          builder.append(entry.getKey()).append('=').append(entry.getValue());
      }
    }

    return builder;
  }

  private final Class<? extends Annotation> annotationType;
  private final AttributeMap attributes;

  AnnotationType(final Class<? extends Annotation> annotationType, final AttributeMap attributes) {
    this.annotationType = annotationType;
    this.attributes = attributes;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append('@').append(annotationType.getName());
    if (attributes.size() > 0)
      builder.append('(').append(render()).append(')');

    return builder.toString();
  }
}