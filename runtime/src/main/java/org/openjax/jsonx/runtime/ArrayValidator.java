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
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.openjax.standard.util.Annotations;
import org.openjax.standard.util.DelegateList;
import org.openjax.standard.util.function.TriPredicate;

public class ArrayValidator {
  static class Relation {
    final Object member;
    final Annotation annotation;

    Relation(final Object member, final Annotation annotation) {
      this.member = member;
      this.annotation = annotation;
    }

    private Object deflate() {
      return member instanceof Relations ? ((Relations)member).deflate() : member;
    }

    @Override
    public String toString() {
      return String.valueOf(JxUtil.getId(annotation));
    }
  }

  static class Relations extends DelegateList<Relation> {
    public Relations() {
      super(new ArrayList<Relation>());
    }

    private Relations(final List<Relation> target) {
      super(target);
    }

    @Override
    public Relation set(final int index, final Relation element) {
      if (index == size())
        super.add(element);
      else
        super.set(index, element);

      return element;
    }

    List<Object> deflate() {
      for (int i = 0; i < size(); ++i) {
        final Object member = get(i);
        target.set(i, member instanceof Relation ? ((Relation)member).deflate() : member);
      }

      return target;
    }

    @Override
    public Relations subList(final int fromIndex, final int toIndex) {
      return new Relations(target.subList(fromIndex, toIndex));
    }

    @Override
    public String toString() {
      if (size() == 0)
        return "[]";

      final StringBuilder builder = new StringBuilder("[");
      for (final Object relation : this)
        builder.append(relation).append(", ");

      builder.setLength(builder.length() - 2);
      return builder.append("]").toString();
    }
  }

  private static int getNextRequiredElement(final Annotation[] annotations, final int a, int occurrence) {
    for (int i = a; i < annotations.length; ++i) {
      final Annotation annotation = annotations[i];
      final int minOccurs = JxUtil.getMinOccurs(annotation);
      if (occurrence <= minOccurs)
        return i;

      occurrence = 1;
    }

    return -1;
  }

  static StringBuilder validate(final ArrayIterator iterator, final int occurrence, final Annotation[] annotations, final int a, final int minIterate, final int maxIterate, final int iteration, final IdToElement idToElement, final Relations relations, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException {
    if (occurrence == 0 || iteration == 0)
      throw new IllegalStateException();

    final int index = iterator.nextIndex();
    if (!iterator.hasNext()) {
      if (minIterate == 0 && iteration == 1 && index == 0)
        return null;

      int nextRequiredIndex = getNextRequiredElement(annotations, a, occurrence);
      if (nextRequiredIndex == -1 && iteration < minIterate)
        nextRequiredIndex = 0;

      if (nextRequiredIndex == -1)
        return null;

      return new StringBuilder("Invalid content was found " + (index == 0 ? "in empty array" : "starting with member index=" + (index - 1)) + ": " + Annotations.toSortedString(annotations[nextRequiredIndex], JxUtil.ATTRIBUTES) + ": Content is not complete");
    }

    if (a == annotations.length) {
      if (iteration < maxIterate)
        return validate(iterator, 1, annotations, 0, minIterate, maxIterate, iteration + 1, idToElement, relations, validate, onPropertyDecode);

      iterator.next();
      final String preview = iterator.currentPreview();
      iterator.previous();
      return new StringBuilder("Invalid content was found starting with member index=" + index + ": " + Annotations.toSortedString(annotations[a - 1], JxUtil.ATTRIBUTES) + ": No members are expected at this point: " + preview);
    }

    final int minOccurs;
    final int maxOccurs;
    final boolean nullable;
    final Class<?> type;
    final Annotation annotation = annotations[a];
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
      throw new ValidationException("minOccurs must be a non-negative integer: " + Annotations.toSortedString(annotation, JxUtil.ATTRIBUTES));

    if (maxOccurs < minOccurs)
      throw new ValidationException("minOccurs must be less than or equal to maxOccurs: " + Annotations.toSortedString(annotation, JxUtil.ATTRIBUTES));

    int before = iterator.nextIndex();

    // If `minOccurs` has already been satisfied, then let's first try to skip the next member
    if (minOccurs < occurrence && r(iterator, iterator.nextIndex(), relations, validate(iterator, 1, annotations, a + 1, minIterate, maxIterate, iteration, idToElement, relations, validate, onPropertyDecode)) == null)
      return null;

    assertIndex(before, iterator);

    StringBuilder error;
    if (iterator.nextIsNull()) {
      if (nullable || !validate) {
        error = null;
        relations.set(index, iterator.currentRelate(annotation));
      }
      else {
        error = new StringBuilder("Illegal value: null");
      }
    }
    else if ((error = iterator.currentMatchesType(type, annotation, idToElement, onPropertyDecode)) == null || !validate) {
      error = iterator.currentIsValid(index, annotation, idToElement, relations, validate, onPropertyDecode);
    }

//    System.err.println("m[" + index + "], a[" + a + "], o(" + minOccurs + ", " + maxOccurs + ")[" + occurrence + "], i(" + minIterate + ", " + maxIterate + ")[" + iteration + "]");

    if (error != null) {
      error.insert(0, "Invalid content was found starting with member index=" + index + ": " + Annotations.toSortedString(annotation, JxUtil.ATTRIBUTES) + ": ");
      return error;
    }

    // If `minOccurs` is not yet satisfied, require increment `occurrence`
    if (occurrence < minOccurs)
      return r(iterator, iterator.nextIndex(), relations, validate(iterator, occurrence + 1, annotations, a, minIterate, maxIterate, iteration, idToElement, relations, validate, onPropertyDecode));

    before = iterator.nextIndex();
    // If `occurrence` is under `maxOccurs`, optional increment `occurrence`.
    final StringBuilder error2;
    if (occurrence >= maxOccurs)
      error2 = null;
    else if ((error2 = r(iterator, iterator.nextIndex(), relations, validate(iterator, occurrence + 1, annotations, a, minIterate, maxIterate, iteration, idToElement, relations, validate, onPropertyDecode))) == null)
      return null;

    if (error2 != null)
      assertIndex(before, iterator);
    else
      before = iterator.nextIndex();

    final StringBuilder error3 = r(iterator, iterator.nextIndex(), relations, validate(iterator, 1, annotations, a + 1, minIterate, maxIterate, iteration, idToElement, relations, validate, onPropertyDecode));

    if (error3 != null)
      assertIndex(before, iterator);

    return error3 == null ? null : error2 != null ? error2 : error3;
  }

  private static StringBuilder r(final ArrayIterator iterator, final int index, final Relations relations, final StringBuilder error) throws IOException {
    if (error == null)
      return null;

    for (int i = iterator.nextIndex(); i > index; --i)
      iterator.previous();

    if (rewind(error)) {
//      System.err.println(" " + relations + " back: " + (iterator.nextIndex() - 1));
//      iterator.previous();
    }

    return error;
  }

  private static boolean rewind(final StringBuilder error) {
    final int lastChar = error.length() - 1;
    if (error.charAt(lastChar) != '\0')
      return true;

    error.setLength(lastChar);
    return false;
  }

  private static void assertIndex(final int before, final ArrayIterator iterator) throws IOException {
    if (iterator.nextIndex() != before)
      throw new IllegalStateException(iterator.nextIndex() + " != " + before);
  }

  @SuppressWarnings("unchecked")
  static StringBuilder validate(final List<?> members, final IdToElement idToElement, final int[] elementIds, final Relations relations, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) {
    final Annotation[] annotations = idToElement.get(elementIds);
    try {
      return validate(new ArrayEncodeIterator((ListIterator<Object>)members.listIterator()), 1, annotations, 0, idToElement.getMinIterate(), idToElement.getMaxIterate(), 1, idToElement, relations, validate, onPropertyDecode);
    }
    catch (final IOException e) {
      throw new IllegalStateException("Should not happen, as this method is only called for encode", e);
    }
  }

  static String validate(final Class<? extends Annotation> annotationType, final List<?> members, final Relations relations, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) {
    final IdToElement idToElement = new IdToElement();
    final int[] elementIds = JxUtil.digest(annotationType.getAnnotations(), annotationType.getName(), idToElement);
    final StringBuilder error = validate(members, idToElement, elementIds, relations, validate, onPropertyDecode);
    if (error == null)
      return null;

    rewind(error);
    return error.toString();
  }

  private ArrayValidator() {
  }
}