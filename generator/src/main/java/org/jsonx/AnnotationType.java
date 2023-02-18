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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.libj.lang.Classes;
import org.libj.util.CollectionUtil;

class AnnotationType {
  @SuppressWarnings("unchecked")
  private StringBuilder render() {
    final StringBuilder builder = new StringBuilder();
    if (attributes.size() > 0) {
      for (final Map.Entry<String,Object> entry : attributes.entrySet()) { // [S]
        if (builder.length() > 0)
          builder.append(", ");

        final Method method = Classes.getMethod(annotationType, entry.getKey());
        final Object defaultValue = method.getDefaultValue();
        if (entry.getValue() instanceof List) {
          final Object[] defaultArray = (Object[])defaultValue;
          final List<Object> items = (List<Object>)entry.getValue();
          final int i$ = items.size();
          if (defaultArray != null) {
            if (i$ == 0 && defaultArray.length == 0)
              continue;

            if (defaultArray.equals(items.toArray()))
              continue;
          }

          builder.append(entry.getKey()).append('=');
          if (i$ == 1) {
            builder.append(items.get(0));
          }
          else {
            builder.append('{');
            if (CollectionUtil.isRandomAccess(items)) {
              int i = 0; do { // [RA]
                if (i > 0)
                  builder.append(", ");

                builder.append(items.get(i));
              }
              while (++i < i$);
            }
            else {
              final Iterator<Object> it = items.iterator();
              int j = 0; do { // [RA]
                if (j > 0)
                  builder.append(", ");

                builder.append(it.next());
              }
              while (it.hasNext());
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