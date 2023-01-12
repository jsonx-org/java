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

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

import org.jsonx.ArrayValidator.Relation;
import org.jsonx.ArrayValidator.Relations;

final class ArrayTrial<T> extends PropertyTrial<T> {
  static Object createValid(final Class<? extends Annotation> arrayAnnotationType, final int minIterate, final int maxIterate, final int[] elementIds, final IdToElement idToElement) {
    return createArray(arrayAnnotationType, minIterate, maxIterate, elementIds, idToElement, null);
  }

  enum TrialType {
    NULLABLE,
    MAX_OCCURS,
    MIN_OCCURS
  }

  private static Object createArray(final Class<? extends Annotation> arrayAnnotationType, int minIterate, int maxIterate, int[] elementIds, IdToElement idToElement, final TrialType trialType) {
    if (arrayAnnotationType != ArrayType.class) {
      elementIds = JsdUtil.digest(arrayAnnotationType.getAnnotations(), arrayAnnotationType.getName(), idToElement = new IdToElement());
      minIterate = idToElement.getMinIterate();
      maxIterate = idToElement.getMaxIterate();
    }

    if (trialType == TrialType.MIN_OCCURS) {
      final Annotation[] annotations = idToElement.get(elementIds);
      boolean hasMinOccurs = false;
      for (int i = 0; !hasMinOccurs && i < annotations.length; ++i) // [A]
        hasMinOccurs = JsdUtil.getMinOccurs(annotations[i]) > 0;

      return hasMinOccurs ? new ArrayList<>() : null;
    }

    if (trialType == TrialType.NULLABLE) {
      final Annotation[] annotations = idToElement.get(elementIds);
      boolean hasNullable = false;
      for (int i = 0; !hasNullable && i < annotations.length; ++i) // [A]
        hasNullable = !JsdUtil.isNullable(annotations[i]);

      if (!hasNullable)
        return null;
    }
    else if (trialType == TrialType.MAX_OCCURS) {
      final Annotation[] annotations = idToElement.get(elementIds);
      boolean hasMaxOccurs = false;
      for (int i = 0; !hasMaxOccurs && i < annotations.length; ++i) // [A]
        hasMaxOccurs = JsdUtil.getMaxOccurs(annotations[i]) < Integer.MAX_VALUE;

      if (!hasMaxOccurs)
        return null;
    }

    final Relations relations = new Relations();
    try {
      ArrayValidator.validate(new ArrayCreateIterator(idToElement, trialType), 1, false, idToElement.get(elementIds), 0, minIterate, maxIterate, 1, idToElement, relations, true, null, -1);
    }
    catch (final IOException e) {
      throw new UncheckedIOException(e);
    }

    if (trialType == TrialType.MAX_OCCURS) {
      final HashMap<Annotation,Integer> annotationToCount = new HashMap<>();
      ListIterator<Relation> iterator = relations.listIterator();
      while (iterator.hasNext()) {
        final Relation relation = iterator.next();
        if (relation.annotation instanceof AnyElement)
          continue;

        final int maxOccurs = JsdUtil.getMaxOccurs(relation.annotation);
        if (maxOccurs == Integer.MAX_VALUE)
          continue;

        final Integer count = annotationToCount.get(relation.annotation);
        annotationToCount.put(relation.annotation, (count == null ? 0 : count) + 1);
      }

      if (annotationToCount.size() == 0) {
        if (logger.isWarnEnabled()) logger.warn("Could not create TrialType.MAX_OCCURS");
        return null;
      }

      iterator = relations.listIterator(relations.size());
      while (iterator.hasPrevious()) {
        final Relation relation = iterator.previous();
        Integer count = annotationToCount.get(relation.annotation);
        if (count == null)
          continue;

        final int maxOccurs = JsdUtil.getMaxOccurs(relation.annotation);
        for (; count <= maxOccurs; ++count) { // [N]
          final Object validNonNullMember = createValidNonNullArrayMember(relation.annotation);
          assertNotNull(validNonNullMember);
          iterator.add(new Relation(validNonNullMember, relation.annotation));
        }

        break;
      }
    }

    return relations.deflate();
  }

  static void add(final List<? super PropertyTrial<?>> trials, final Method getMethod, final Method setMethod, final Object object, final ArrayProperty property) {
    if (logger.isDebugEnabled()) logger.debug("Adding: " + getMethod.getDeclaringClass() + "." + getMethod.getName() + "()");
    trials.add(new ArrayTrial<>(ValidCase.CASE, getMethod, setMethod, object, createValid(property.type(), property.minIterate(), property.maxIterate(), property.elementIds(), new IdToElement()), property));
    final Object testNullable = createArray(property.type(), property.minIterate(), property.maxIterate(), property.elementIds(), new IdToElement(), TrialType.NULLABLE);
    if (testNullable != null)
      trials.add(new ArrayTrial<>(NullableCase.CASE, getMethod, setMethod, object, testNullable, property));

    final Object testMinOccurs = createArray(property.type(), property.minIterate(), property.maxIterate(), property.elementIds(), new IdToElement(), TrialType.MIN_OCCURS);
    if (testMinOccurs != null)
      trials.add(new ArrayTrial<>(MinOccursCase.CASE, getMethod, setMethod, object, testMinOccurs, property));

    final Object testMaxOccurs = createArray(property.type(), property.minIterate(), property.maxIterate(), property.elementIds(), new IdToElement(), TrialType.MAX_OCCURS);
    if (testMaxOccurs != null)
      trials.add(new ArrayTrial<>(MaxOccursCase.CASE, getMethod, setMethod, object, testMaxOccurs, property));

    if (getMethod.getReturnType().isPrimitive())
      return;

    if (property.use() == Use.REQUIRED) {
      trials.add(new ArrayTrial<>(getNullableCase(property.nullable()), getMethod, setMethod, object, null, property));
    }
    else if (property.nullable()) {
      trials.add(new ArrayTrial<>(OptionalNullableCase.CASE, getMethod, setMethod, object, null, property));
      trials.add(new ArrayTrial<>(OptionalNullableCase.CASE, getMethod, setMethod, object, Optional.ofNullable(null), property));
    }
    else {
      trials.add(new ArrayTrial<>(OptionalNotNullableCase.CASE, getMethod, setMethod, object, null, property));
    }
  }

  private ArrayTrial(final Case<? extends PropertyTrial<? super T>> kase, final Method getMethod, final Method setMethod, final Object object, final T value, final ArrayProperty property) {
    super(kase, JsdUtil.getRealType(getMethod), getMethod, setMethod, object, value, property.name(), property.use(), null, null, false);
  }
}