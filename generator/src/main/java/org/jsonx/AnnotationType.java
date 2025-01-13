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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.libj.lang.Classes;
import org.libj.util.CollectionUtil;

class AnnotationType {
  static boolean appendAttribute(final StringBuilder b, final String key, final Object value, final Object defaultValue, final boolean addComma) {
    final Object fixedDefaultValue = defaultValue != null && value instanceof String ? "\"" + defaultValue + "\"" : defaultValue;
    if (Objects.equals(fixedDefaultValue, value))
      return false;

    if (addComma)
      b.append(", ");

    b.append(key).append(" = ").append(value instanceof Class ? ((Class<?>)value).getName() + ".class" : value);
    return true;
  }

  @SuppressWarnings("unchecked")
  private void render(final StringBuilder b) {
    boolean addComma = false;
    for (final Map.Entry<String,Object> entry : attributes.entrySet()) { // [S]
      final String key = entry.getKey();
      final Object value = entry.getValue();

      final Object defaultValue = Classes.getMethod(annotationType, key).getDefaultValue();
      if (value instanceof List) {
        final Object[] defaultArray = (Object[])defaultValue;
        final List<Object> items = (List<Object>)value;
        final int i$ = items.size();
        if (defaultArray != null) {
          if (i$ == 0 && defaultArray.length == 0)
            continue;

          if (defaultArray.equals(items.toArray()))
            continue;
        }

        if (addComma)
          b.append(", ");

        addComma = true;
        b.append(key).append(" = ");
        if (i$ == 1) {
          b.append(items.get(0));
        }
        else {
          b.append('{');
          int i = 0;
          if (CollectionUtil.isRandomAccess(items)) {
            do { // [RA]
              if (i > 0)
                b.append(", ");

              b.append(items.get(i));
            }
            while (++i < i$);
          }
          else {
            final Iterator<Object> it = items.iterator();
            do { // [I]
              if (++i > 1)
                b.append(", ");

              b.append(it.next());
            }
            while (it.hasNext());
          }

          b.append('}');
        }
      }
      else {
        addComma = appendAttribute(b, key, value, defaultValue, addComma);
      }
    }
  }

  private final Class<? extends Annotation> annotationType;
  private final AttributeMap attributes;

  AnnotationType(final Class<? extends Annotation> annotationType, final AttributeMap attributes) {
    this.annotationType = annotationType;
    this.attributes = attributes;
  }

  @Override
  public String toString() {
    final StringBuilder b = new StringBuilder();
    b.append('@').append(annotationType.getName());
    if (attributes.size() > 0) {
      b.append('(');
      render(b);
      b.append(')');
    }

    return b.toString();
  }
}