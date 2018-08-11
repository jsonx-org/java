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

package org.libx4j.jsonx.runtime;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class JsonxUtil {
  public static String getName(final String name, final Field field) {
    return name.length() > 0 ? name : field.getName();
  }

  public static void idToAnnotation(final IdToElement idToElement, Annotation[] annotations) {
    annotations = JsonxUtil.flatten(annotations);
    for (final Annotation annotation : annotations) {
      if (annotation instanceof ArrayElement)
        idToElement.put(((ArrayElement)annotation).id(), annotation);
      else if (annotation instanceof BooleanElement)
        idToElement.put(((BooleanElement)annotation).id(), annotation);
      else if (annotation instanceof NumberElement)
        idToElement.put(((NumberElement)annotation).id(), annotation);
      else if (annotation instanceof ObjectElement)
        idToElement.put(((ObjectElement)annotation).id(), annotation);
      else if (annotation instanceof StringElement)
        idToElement.put(((StringElement)annotation).id(), annotation);
    }
  }

  public static Annotation[] flatten(final Annotation[] annotations) {
    return flatten(annotations, 0, 0);
  }

  private static Annotation[] flatten(final Annotation[] annotations, final int index, final int depth) {
    if (index == annotations.length)
      return new Annotation[depth];

    final Annotation annotation = annotations[index];
    final Annotation[] repeatable;
    if (ArrayElements.class.equals(annotation.annotationType()))
      repeatable = ((ArrayElements)annotation).value();
    else if (BooleanElements.class.equals(annotation.annotationType()))
      repeatable = ((BooleanElements)annotation).value();
    else if (NumberElements.class.equals(annotation.annotationType()))
      repeatable = ((NumberElements)annotation).value();
    else if (ObjectElements.class.equals(annotation.annotationType()))
      repeatable = ((ObjectElements)annotation).value();
    else if (StringElements.class.equals(annotation.annotationType()))
      repeatable = ((StringElements)annotation).value();
    else
      repeatable = null;

    if (repeatable == null) {
      final Annotation[] flattened = flatten(annotations, index + 1, depth + 1);
      flattened[depth] = annotation;
      return flattened;
    }

    final Annotation[] flattened = flatten(annotations, index + 1, depth + repeatable.length);
    for (int i = 0; i < repeatable.length; i++)
      flattened[depth + i] = repeatable[i];

    return flattened;
  }
}