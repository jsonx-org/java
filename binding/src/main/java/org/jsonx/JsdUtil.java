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
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.libj.util.Annotations;
import org.libj.util.FixedOrderComparator;
import org.libj.util.Identifiers;
import org.libj.util.Strings;

final class JsdUtil {
  static final FixedOrderComparator<String> ATTRIBUTES = new FixedOrderComparator<>("id", "name", "names", "xsi:type", "abstract", "extends", "type", "types", "booleans", "numbers", "objects", "strings", "elementIds", "scale", "range", "pattern", "use", "minIterate", "maxIterate", "minOccurs", "maxOccurs", "nullable");
  private static final char prefix = '\0';
  private static final Function<Character,String> substitutions = c -> c == null ? "_" : c != '_' ? "_" + Integer.toHexString(c) : "__";
  private static final Function<Character,String> substitutions2 = c -> c == null ? "_" : c == '-' ? "-" : c != '_' ? "_" + Integer.toHexString(c) : "__";

  /**
   * @param classCase Whether to return class-case (true), instance-case
   *          (false), or with case unchanged (null).
   * @return The name of this member as a valid Java Identifier in:
   *         <ul>
   *         <li>Class-Case: upper-CamelCase</li>
   *         <li>Instance-Case: lower-camelCase</li>
   *         <li>{@code name.length() == 0}: "_$"</li>
   *         </ul>
   */
  private static String toIdentifier(final String name, final Boolean classCase) {
    return name.length() == 0 ? "_$" : classCase == null ? Identifiers.toCamelCase(name, prefix, substitutions2) : classCase ? Identifiers.toClassCase(name, prefix, substitutions) : Identifiers.toInstanceCase(name, prefix, substitutions);
  }

  static String toIdentifier(final String name) {
    return toIdentifier(name, null);
  }

  static String toClassName(final String name) {
    return toIdentifier(name, true);
  }

  static String toInstanceName(final String name) {
    return toIdentifier(name, false);
  }

  static String flipName(String name) {
    int i = name.lastIndexOf('$');
    if (i != -1)
      name = name.replace('$', '-');
    else if ((i = name.lastIndexOf('-')) != -1)
      name = name.replace('-', '$');
    else
      i = name.lastIndexOf('.');

    return i == -1 ? Strings.flipFirstCap(name) : name.substring(0, i + 1) + Strings.flipFirstCap(name.substring(i + 1));
  }

  static Method getGetMethod(final Class<?> cls, final String propertyName) {
    return getMethod(cls, propertyName, null);
  }

  static Method getSetMethod(final Field field, final String propertyName) {
    return getMethod(field.getDeclaringClass(), propertyName, field.getType());
  }

  static String fixReserved(final String name) {
    return "Class".equalsIgnoreCase(name) ? "0lass" : name;
  }

  private static Method getMethod(final Class<?> cls, final String propertyName, final Class<?> parameterType) {
    try {
      return cls.getMethod((parameterType == null ? "get" : "set") + fixReserved(toClassName(propertyName)), parameterType == null ? null : new Class<?> [] {parameterType});
    }
    catch (final NoSuchMethodException e) {
      return null;
    }
  }

  static List<Class<?>> getDeclaredObjectTypes(final Field field) {
    final IdToElement idToElement = new IdToElement();
    final int[] elementIds = JsdUtil.digest(field, idToElement);
    final Annotation[] annotations = idToElement.get(elementIds);
    final List<Class<?>> types = new ArrayList<>();
    getDeclaredObjectTypes(annotations, types);
    return types;
  }

  private static void getDeclaredObjectTypes(final Annotation[] annotations, final List<Class<?>> types) {
    for (final Annotation annotation : annotations) {
      if (annotation instanceof ArrayElement) {
        final ArrayElement element = (ArrayElement)annotation;
        if (element.type() != ArrayType.class)
          getDeclaredObjectTypes(element.type().getAnnotations(), types);
      }
      else if (annotation instanceof ObjectElement) {
        types.add(((ObjectElement)annotation).type());
      }
    }
  }

  static String getFullyQualifiedFieldName(final Field field) {
    return field.getDeclaringClass().getName() + "#" + field.getName();
  }

  static int[] digest(final Field field, final IdToElement idToElement) {
    final ArrayProperty property = field.getAnnotation(ArrayProperty.class);
    if (property == null)
      throw new IllegalArgumentException("@" + ArrayProperty.class.getSimpleName() + " not found on: " + getFullyQualifiedFieldName(field));

    if (property.type() != ArrayType.class)
      return digest(property.type().getAnnotations(), property.type().getName(), idToElement);

    return digest(field.getAnnotations(), getFullyQualifiedFieldName(field), idToElement);
  }

  static int[] digest(Annotation[] annotations, final String declarerName, final IdToElement idToElement) {
    annotations = JsdUtil.flatten(annotations);
    JsdUtil.fillIdToElement(idToElement, annotations);
    Annotation annotation = null;
    int[] elementIds = null;
    for (int i = 0; i < annotations.length; ++i) {
      if (annotations[i] instanceof ArrayType) {
        final ArrayType arrayType = (ArrayType)annotations[i];
        elementIds = arrayType.elementIds();
        idToElement.setMinIterate(arrayType.minIterate());
        idToElement.setMaxIterate(arrayType.maxIterate());
        annotation = arrayType;
        break;
      }

      if (annotations[i] instanceof ArrayProperty) {
        final ArrayProperty arrayProperty = (ArrayProperty)annotations[i];
        elementIds = arrayProperty.elementIds();
        idToElement.setMinIterate(arrayProperty.minIterate());
        idToElement.setMaxIterate(arrayProperty.maxIterate());
        annotation = arrayProperty;
        break;
      }
    }

    if (annotation == null)
      throw new ValidationException(declarerName + " does not declare @" + ArrayType.class.getSimpleName() + " or @" + ArrayProperty.class.getSimpleName());

    if (elementIds.length == 0)
      throw new ValidationException("elementIds property cannot be empty: " + declarerName + ": " + Annotations.toSortedString(annotation, ATTRIBUTES));

    return elementIds;
  }

  static boolean isNullable(final Annotation annotation) {
    if (annotation instanceof AnyElement)
      return ((AnyElement)annotation).nullable();

    if (annotation instanceof ArrayElement)
      return ((ArrayElement)annotation).nullable();

    if (annotation instanceof BooleanElement)
      return ((BooleanElement)annotation).nullable();

    if (annotation instanceof NumberElement)
      return ((NumberElement)annotation).nullable();

    if (annotation instanceof ObjectElement)
      return ((ObjectElement)annotation).nullable();

    if (annotation instanceof StringElement)
      return ((StringElement)annotation).nullable();

    throw new UnsupportedOperationException("Unsupported annotation type: " + annotation.annotationType().getName());
  }

  static int getMinOccurs(final Annotation annotation) {
    if (annotation instanceof AnyElement)
      return ((AnyElement)annotation).minOccurs();

    if (annotation instanceof ArrayElement)
      return ((ArrayElement)annotation).minOccurs();

    if (annotation instanceof BooleanElement)
      return ((BooleanElement)annotation).minOccurs();

    if (annotation instanceof NumberElement)
      return ((NumberElement)annotation).minOccurs();

    if (annotation instanceof ObjectElement)
      return ((ObjectElement)annotation).minOccurs();

    if (annotation instanceof StringElement)
      return ((StringElement)annotation).minOccurs();

    throw new UnsupportedOperationException("Unsupported annotation type: " + annotation.annotationType().getName());
  }

  static int getId(final Annotation annotation) {
    if (annotation instanceof AnyElement)
      return ((AnyElement)annotation).id();

    if (annotation instanceof ArrayElement)
      return ((ArrayElement)annotation).id();

    if (annotation instanceof BooleanElement)
      return ((BooleanElement)annotation).id();

    if (annotation instanceof NumberElement)
      return ((NumberElement)annotation).id();

    if (annotation instanceof ObjectElement)
      return ((ObjectElement)annotation).id();

    if (annotation instanceof StringElement)
      return ((StringElement)annotation).id();

    throw new UnsupportedOperationException("Unsupported annotation type: " + annotation.annotationType().getName());
  }

  static int getMaxOccurs(final Annotation annotation) {
    if (annotation instanceof AnyElement)
      return ((AnyElement)annotation).maxOccurs();

    if (annotation instanceof ArrayElement)
      return ((ArrayElement)annotation).maxOccurs();

    if (annotation instanceof BooleanElement)
      return ((BooleanElement)annotation).maxOccurs();

    if (annotation instanceof NumberElement)
      return ((NumberElement)annotation).maxOccurs();

    if (annotation instanceof ObjectElement)
      return ((ObjectElement)annotation).maxOccurs();

    if (annotation instanceof StringElement)
      return ((StringElement)annotation).maxOccurs();

    throw new UnsupportedOperationException("Unsupported annotation type: " + annotation.annotationType().getName());
  }

  static String getName(final Field field) {
    for (final Annotation annotation : field.getAnnotations()) {
      if (annotation instanceof AnyProperty)
        return getName(((AnyProperty)annotation).name(), field);

      if (annotation instanceof ArrayProperty)
        return getName(((ArrayProperty)annotation).name(), field);

      if (annotation instanceof BooleanProperty)
        return getName(((BooleanProperty)annotation).name(), field);

      if (annotation instanceof NumberProperty)
        return getName(((NumberProperty)annotation).name(), field);

      if (annotation instanceof ObjectProperty)
        return getName(((ObjectProperty)annotation).name(), field);

      if (annotation instanceof StringProperty)
        return getName(((StringProperty)annotation).name(), field);
    }

    return null;
  }

  static String getName(final String name, final Field field) {
    return name.length() > 0 ? name : field.getName();
  }

  static void fillIdToElement(final IdToElement idToElement, Annotation[] annotations) {
    annotations = JsdUtil.flatten(annotations);
    for (final Annotation annotation : annotations) {
      if (annotation instanceof AnyElement)
        idToElement.put(((AnyElement)annotation).id(), annotation);
      else if (annotation instanceof ArrayElement)
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

  static Annotation[] flatten(final Annotation[] annotations) {
    return flatten(annotations, 0, 0);
  }

  private static Annotation[] flatten(final Annotation[] annotations, final int index, final int depth) {
    if (index == annotations.length)
      return new Annotation[depth];

    final Annotation annotation = annotations[index];
    final Annotation[] repeatable;
    if (AnyElements.class.equals(annotation.annotationType()))
      repeatable = ((AnyElements)annotation).value();
    else if (ArrayElements.class.equals(annotation.annotationType()))
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
    for (int i = 0; i < repeatable.length; ++i)
      flattened[depth + i] = repeatable[i];

    return flattened;
  }

  private JsdUtil() {
  }
}