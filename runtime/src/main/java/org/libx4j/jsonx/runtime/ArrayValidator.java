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
import java.util.Comparator;
import java.util.List;

import org.lib4j.lang.Annotations;
import org.lib4j.lang.Strings;

public class ArrayValidator {
  private static final Comparator<String> comparator = new AttributeComparator();

  private static int[] digest(Annotation[] annotations, final String declarerName, final IdToElement idToElement) {
    annotations = JsonxUtil.flatten(annotations);
    JsonxUtil.idToAnnotation(idToElement, annotations);
    Annotation arrayAnnotation = null;
    int[] elementIds = null;
    for (final Annotation annotation : annotations) {
      if (annotation instanceof ArrayType) {
        arrayAnnotation = annotation;
        elementIds = ((ArrayType)annotation).elementIds();
        break;
      }

      if (annotation instanceof ArrayProperty) {
        arrayAnnotation = annotation;
        elementIds = ((ArrayProperty)annotation).elementIds();
        break;
      }
    }

    if (arrayAnnotation == null)
      throw new IllegalStateException(declarerName + " does not declare @" + ArrayType.class.getSimpleName() + " or @" + ArrayProperty.class.getSimpleName());

    if (elementIds.length == 0)
      throw new IllegalStateException("elementIds property cannot be empty: " + declarerName + ": " + Annotations.toSortedString(arrayAnnotation, comparator));

    return elementIds;
  }

  private static int getNextRequiredElement(final Annotation[] annotations, final int fromIndex, int count) {
    for (int i = fromIndex; i < annotations.length; ++i) {
      final Annotation annotation = annotations[i];
      if (annotation instanceof ArrayElement) {
        final ArrayElement element = (ArrayElement)annotation;
        if (count < element.minOccurs())
          return i;
      }
      else if (annotation instanceof BooleanElement) {
        final BooleanElement element = (BooleanElement)annotation;
        if (count < element.minOccurs())
          return i;
      }
      else if (annotation instanceof NumberElement) {
        final NumberElement element = (NumberElement)annotation;
        if (count < element.minOccurs())
          return i;
      }
      else if (annotation instanceof ObjectElement) {
        final ObjectElement element = (ObjectElement)annotation;
        if (count < element.minOccurs())
          return i;
      }
      else if (annotation instanceof StringElement) {
        final StringElement element = (StringElement)annotation;
        if (count < element.minOccurs())
          return i;
      }
      else {
        throw new UnsupportedOperationException("Unsupported annotation type " + annotation.annotationType().getName());
      }

      count = 0;
    }

    return -1;
  }

  private static boolean matchesType(final Class<?> cls, final Class<?> type) {
    return type != Object.class ? type.isAssignableFrom(cls) : !cls.isArray() && !Boolean.class.isAssignableFrom(cls) && !List.class.isAssignableFrom(cls) && !Number.class.isAssignableFrom(cls) && !String.class.isAssignableFrom(cls);
  }

  private static String validate(final ArrayElement element, final Object member, IdToElement idToElement) {
    final int[] elementIds;
    if (element.type() != ArrayType.class)
      elementIds = digest(element.type().getAnnotations(), element.type().getName(), idToElement = new IdToElement());
    else
      elementIds = element.elementIds();

    return validate(idToElement, elementIds, ((List<?>)member).toArray());
  }

  private static String validate(final NumberElement element, final Object member) {
    final Number number = (Number)member;
    if (element.form() == Form.INTEGER && number.longValue() != number.doubleValue())
      return "Illegal non-INTEGER value: " + Strings.toTruncatedString(member, 16);

    try {
      if (element.range().length() > 0 && !new Range(element.range()).isValid(number))
        return "Range is not matched: " + Strings.toTruncatedString(member, 16);
    }
    catch (final ParseException e) {
      throw new IllegalStateException("range is invalid: " + Annotations.toSortedString(element, comparator));
    }

    return null;
  }

  private static String validate(final Object member) {
    return member.getClass().isAnnotationPresent(JsonxObject.class) ? null : "Does not allow type without @" + JsonxObject.class.getSimpleName() + " annotation: " + member.getClass().getName();
  }

  private static String validate(final StringElement element, final Object member) {
    final String string = (String)member;
    return element.pattern().length() == 0 || string.matches(element.pattern()) ? null : "Pattern is not matched: \"" + Strings.toTruncatedString(string, 16) + "\"";
  }

  private static String validate(final Annotation annotation, final Object member, IdToElement idToElement) {
    if (annotation instanceof ArrayElement)
      return validate((ArrayElement)annotation, member, idToElement);

    if (annotation instanceof BooleanElement)
      return null;

    if (annotation instanceof NumberElement)
      return validate((NumberElement)annotation, member);

    if (annotation instanceof ObjectElement)
      return validate(member);

    if (annotation instanceof StringElement)
      return validate((StringElement)annotation, member);

    throw new UnsupportedOperationException("Unsupported annotation type " + annotation.annotationType().getName());
  }

  private static String validate(final Annotation[] annotations, int i, int count, final Object[] members, final int j, final IdToElement idToElement) {
    if (j == members.length) {
      final int nextRequiredIndex = getNextRequiredElement(annotations, i, count);
      return nextRequiredIndex == -1 ? null : "Invalid content was found starting with member index=" + j + ": " + Annotations.toSortedString(annotations[nextRequiredIndex], comparator) + ": Content is not complete";
    }

    final Object member = members[j];
    if (i == annotations.length)
      return j == members.length ? null : "Invalid content was found starting with member index=" + j + ": " + Annotations.toSortedString(annotations[i - 1], comparator) + ": No members are expected at this point: " + Strings.toTruncatedString(member, 16);

    final Annotation annotation = annotations[i];
    final int minOccurs;
    final int maxOccurs;
    final boolean nullable;
    final Class<?> type;
    if (annotation instanceof ArrayElement) {
      final ArrayElement element = (ArrayElement)annotation;
      minOccurs = element.minOccurs();
      maxOccurs = element.maxOccurs();
      nullable = element.nullable();
      type = List.class;
    }
    else if (annotation instanceof BooleanElement) {
      final BooleanElement element = (BooleanElement)annotation;
      minOccurs = element.minOccurs();
      maxOccurs = element.maxOccurs();
      nullable = element.nullable();
      type = Boolean.class;
    }
    else if (annotation instanceof NumberElement) {
      final NumberElement element = (NumberElement)annotation;
      minOccurs = element.minOccurs();
      maxOccurs = element.maxOccurs();
      nullable = element.nullable();
      type = Number.class;
    }
    else if (annotation instanceof ObjectElement) {
      final ObjectElement element = (ObjectElement)annotation;
      minOccurs = element.minOccurs();
      maxOccurs = element.maxOccurs();
      nullable = element.nullable();
      type = Object.class;
    }
    else if (annotation instanceof StringElement) {
      final StringElement element = (StringElement)annotation;
      minOccurs = element.minOccurs();
      maxOccurs = element.maxOccurs();
      nullable = element.nullable();
      type = String.class;
    }
    else {
      throw new UnsupportedOperationException("Unsupported annotation type " + annotation.annotationType().getName());
    }

    if (minOccurs < 0)
      throw new IllegalStateException("minOccurs must be a non-negative integer: " + Annotations.toSortedString(annotation, comparator));

    if (maxOccurs < 0)
      throw new IllegalStateException("maxOccurs must be a non-negative integer: " + Annotations.toSortedString(annotation, comparator));

    if (maxOccurs < minOccurs)
      throw new IllegalStateException("minOccurs must be less than or equal to maxOccurs: " + Annotations.toSortedString(annotation, comparator));

    final String error;
    if (member == null)
      error = nullable ? null : "Illegal value: null";
    else if (matchesType(member.getClass(), type))
      error = validate(annotation, member, idToElement);
    else if (count < minOccurs || maxOccurs < count)
      error = "Content is not complete";
    else
      return validate(annotations, i + 1, 0, members, j, idToElement);

    if (error != null)
      return count < minOccurs || maxOccurs < count || validate(annotations, i + 1, 0, members, j, idToElement) != null ? "Invalid content was found starting with member index=" + j + ": " + Annotations.toSortedString(annotation, comparator) + ": " + error : null;

    if (++count == maxOccurs) {
      ++i;
      count = 0;
    }

    return validate(annotations, i, count, members, j + 1, idToElement);
  }

  private static String validate(final IdToElement idToElement, final int[] elementIds, final Object[] members) {
    final Annotation[] annotations = idToElement.get(elementIds);
    return validate(annotations, 0, 0, members, 0, idToElement);
  }

  protected static String validate(final Field field, final Object[] members) {
    final ArrayProperty property = field.getAnnotation(ArrayProperty.class);
    if (property == null)
      throw new IllegalArgumentException(field.getDeclaringClass().getName() + "." + field.getName() + " does not declare @" + ArrayProperty.class.getSimpleName());

    final IdToElement idToElement = new IdToElement();
    final int[] elementIds;
    if (property.type() != ArrayType.class)
      elementIds = digest(property.type().getAnnotations(), property.type().getName(), idToElement);
    else
      elementIds = digest(field.getAnnotations(), field.getDeclaringClass().getName() + "." + field.getName(), idToElement);

    return validate(idToElement, elementIds, members);
  }

  protected static String validate(final Class<? extends Annotation> annotationType, final Object[] members) {
    final IdToElement idToElement = new IdToElement();
    final int[] elementIds = digest(annotationType.getAnnotations(), annotationType.getName(), idToElement);
    return validate(idToElement, elementIds, members);
  }

  private ArrayValidator() {
  }
}