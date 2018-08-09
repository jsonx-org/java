package org.libx4j.jsonx.runtime;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class JsonxUtil {
  public static String getName(final String name, final Field field) {
    return name.length() > 0 ? name : field.getName();
  }

  public static int[] getElementIds(final Annotation[] annotations) {
    for (final Annotation annotation : annotations) {
      if (annotation instanceof ArrayType)
        return ((ArrayType)annotation).elementIds();

      if (annotation instanceof ArrayProperty)
        return ((ArrayProperty)annotation).elementIds();
    }

    return null;
  }

  public static IdToElement idToAnnotation(Annotation[] annotations) {
    annotations = JsonxUtil.flatten(annotations);
    final IdToElement idToAnnotation = new IdToElement();
    for (final Annotation annotation : annotations) {
      if (annotation instanceof ArrayElement)
        idToAnnotation.put(((ArrayElement)annotation).id(), annotation);
      else if (annotation instanceof BooleanElement)
        idToAnnotation.put(((BooleanElement)annotation).id(), annotation);
      else if (annotation instanceof NumberElement)
        idToAnnotation.put(((NumberElement)annotation).id(), annotation);
      else if (annotation instanceof ObjectElement)
        idToAnnotation.put(((ObjectElement)annotation).id(), annotation);
      else if (annotation instanceof StringElement)
        idToAnnotation.put(((StringElement)annotation).id(), annotation);
    }

    return idToAnnotation;
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