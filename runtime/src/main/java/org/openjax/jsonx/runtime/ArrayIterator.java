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

package org.openjax.jsonx.runtime;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.function.BiConsumer;

import org.fastjax.util.Annotations;
import org.fastjax.util.Strings;
import org.openjax.jsonx.runtime.ArrayValidator.Relation;
import org.openjax.jsonx.runtime.ArrayValidator.Relations;

public abstract class ArrayIterator {
  private static <T>String validate(final ArrayElement element, final List<T> member, final int i, IdToElement idToElement, final Relations relations, final boolean validate, final BiConsumer<Field,Object> callback) {
    final int[] elementIds;
    if (element.type() != ArrayType.class)
      elementIds = JsonxUtil.digest(element.type().getAnnotations(), element.type().getName(), idToElement = new IdToElement());
    else
      elementIds = element.elementIds();

    final Relations subRelations = new Relations();
    final String subError = ArrayValidator.validate(member, idToElement, elementIds, subRelations, validate, callback);
    if (validate && subError != null)
      return subError;

    relations.set(i, new Relation(subRelations, element));
    return null;
  }

  @SuppressWarnings("unchecked")
  private static <T>String validate(final Annotation annotation, final T member, final int i, final IdToElement idToElement, final Relations relations, final boolean validate, final BiConsumer<Field,Object> callback) {
    if (annotation instanceof ArrayElement)
      return validate((ArrayElement)annotation, (List<T>)member, i, idToElement, relations, validate, callback);

    if (annotation instanceof ObjectElement)
      return validate((ObjectElement)annotation, member, i, relations, validate);

    return validatePrimitive(annotation, member, i, relations, validate);
  }

  private static String validate(final ObjectElement element, final Object member, final int i, final Relations relations, final boolean validate) {
    if (validate && !member.getClass().isAnnotationPresent(ObjectType.class))
      return "@" + ObjectType.class.getSimpleName() + " not found on: " + member.getClass().getName();

    relations.set(i, new Relation(member, element));
    return null;
  }

  public static <T>String validatePrimitive(final Annotation annotation, final T member, final int i, final Relations relations, final boolean validate) {
    if (annotation instanceof BooleanElement)
      return validate((BooleanElement)annotation, member, i, relations);

    if (annotation instanceof NumberElement)
      return validate((NumberElement)annotation, member, i, relations, validate);

    if (annotation instanceof StringElement)
      return validate((StringElement)annotation, member, i, relations, validate);

    throw new UnsupportedOperationException("Unsupported annotation type " + annotation.annotationType().getName());
  }

  private static String validate(final BooleanElement element, final Object member, final int i, final Relations relations) {
    relations.set(i, new Relation(member, element));
    return null;
  }

  private static String validate(final NumberElement element, final Object member, final int i, final Relations relations, final boolean validate) {
    final Number number = (Number)member;
    if (validate) {
      if (element.form() == Form.INTEGER && number.longValue() != number.doubleValue())
        return "Illegal non-INTEGER value: " + Strings.truncate(String.valueOf(member), 16);

      try {
        if (element.range().length() > 0 && !new Range(element.range()).isValid(number))
          return "Range is not matched: " + Strings.truncate(String.valueOf(member), 16);
      }
      catch (final ParseException e) {
        throw new ValidationException("Invalid range attribute: " + Annotations.toSortedString(element, AttributeComparator.instance));
      }
    }

    relations.set(i, new Relation(member, element));
    return null;
  }

  private static String validate(final StringElement element, final Object member, final int i, final Relations relations, final boolean validate) {
    final String string = (String)member;
    if (validate && element.pattern().length() != 0 && !string.matches(element.pattern()))
      return "Pattern is not matched: \"" + Strings.truncate(string, 16) + "\"";

    relations.set(i, new Relation(member, element));
    return null;
  }

  protected Object current;

  protected final String currentIsValid(final int i, final Annotation annotation, final IdToElement idToElement, final Relations relations, final boolean validate, final BiConsumer<Field,Object> callback) {
    return validate(annotation, current, i, idToElement, relations, validate, callback);
  }

  protected final Relation currentRelate(final Annotation annotation) {
    return new Relation(current, annotation);
  }

  protected final String currentPreview() {
    return Strings.truncate(String.valueOf(current), 16);
  }

  protected abstract void next() throws IOException;
  protected abstract void previous();
  protected abstract boolean hasNext() throws IOException;
  protected abstract int nextIndex() throws IOException;
  protected abstract boolean nextIsNull() throws IOException;
  protected abstract String currentMatchesType(final Class<?> type, final Annotation annotation, final IdToElement idToElement, final BiConsumer<Field,Object> callback) throws IOException;
}