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

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.ListIterator;

import org.openjax.jsonx.runtime.ArrayValidator.Relation;
import org.openjax.jsonx.runtime.ArrayValidator.Relations;

public class ValidatingIterator extends ArrayIterator<Object> {
  private static <T>String validate(final Annotation annotation, final T member, final int i, final IdToElement idToElement, final Relations relations) {
    if (annotation instanceof ArrayElement)
      return validate((ArrayElement)annotation, (List<T>)member, i, idToElement, relations);

    if (annotation instanceof ObjectElement)
      return validate((ObjectElement)annotation, member, i, relations);

    return validatePrimitive(annotation, member, i, relations);
  }

  private static <T>String validate(final ArrayElement element, final List<T> member, final int i, IdToElement idToElement, final Relations relations) {
    final int[] elementIds;
    if (element.type() != ArrayType.class)
      elementIds = JsonxUtil.digest(element.type().getAnnotations(), element.type().getName(), idToElement = new IdToElement());
    else
      elementIds = element.elementIds();

    final Relations subRelations = new Relations();
    final String subError = ArrayValidator.validate(member, idToElement, elementIds, subRelations);
    if (subError != null)
      return subError;

    relations.set(i, new Relation(subRelations, element));
    return null;
  }

  private static String validate(final ObjectElement element, final Object member, final int i, final Relations relations) {
    if (!member.getClass().isAnnotationPresent(ObjectType.class))
      return "@" + ObjectType.class.getSimpleName() + " not found on: " + member.getClass().getName();

    relations.set(i, new Relation(member, element));
    return null;
  }

  private final ListIterator<Object> listIterator;

  public ValidatingIterator(final ListIterator<Object> listIterator) {
    this.listIterator = listIterator;
  }

  @Override
  protected void next() {
    current = listIterator.next();
  }

  @Override
  protected void previous() {
    current = listIterator.previous();
  }

  @Override
  public boolean hasNext() {
    return listIterator.hasNext();
  }

  @Override
  public int nextIndex() {
    return listIterator.nextIndex();
  }

  @Override
  protected boolean nextIsNull() {
    next();
    return current == null;
  }

  @Override
  protected boolean currentMatchesType(final Class<?> type, final Annotation annotation, final IdToElement idToElement) {
    final Class<?> cls = current.getClass();
    return type != Object.class ? type.isAssignableFrom(cls) : !cls.isArray() && !Boolean.class.isAssignableFrom(cls) && !List.class.isAssignableFrom(cls) && !Number.class.isAssignableFrom(cls) && !String.class.isAssignableFrom(cls);
  }

  @Override
  public String currentIsValid(final int i, final Annotation annotation, final IdToElement idToElement, final Relations relations) {
    return validate(annotation, current, i, idToElement, relations);
  }
}