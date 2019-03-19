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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;

import org.openjax.jsonx.runtime.ArrayValidator.Relation;
import org.openjax.jsonx.runtime.ArrayValidator.Relations;

class ArrayTrial<T> extends PropertyTrial<T> {
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
      elementIds = JxUtil.digest(arrayAnnotationType.getAnnotations(), arrayAnnotationType.getName(), idToElement = new IdToElement());
      minIterate = idToElement.getMinIterate();
      maxIterate = idToElement.getMaxIterate();
    }

    if (trialType == TrialType.MIN_OCCURS) {
      final Annotation[] annotations = idToElement.get(elementIds);
      boolean hasMinOccurs = false;
      for (int i = 0; !hasMinOccurs && i < annotations.length; ++i)
        hasMinOccurs = JxUtil.getMinOccurs(annotations[i]) > 0;

      return hasMinOccurs ? new ArrayList<>() : null;
    }

    if (trialType == TrialType.NULLABLE) {
      final Annotation[] annotations = idToElement.get(elementIds);
      boolean hasNullable = false;
      for (int i = 0; !hasNullable && i < annotations.length; ++i)
        hasNullable = !JxUtil.isNullable(annotations[i]);

      if (!hasNullable)
        return null;
    }
    else if (trialType == TrialType.MAX_OCCURS) {
      final Annotation[] annotations = idToElement.get(elementIds);
      boolean hasMaxOccurs = false;
      for (int i = 0; !hasMaxOccurs && i < annotations.length; ++i)
        hasMaxOccurs = JxUtil.getMaxOccurs(annotations[i]) < Integer.MAX_VALUE;

      if (!hasMaxOccurs)
        return null;
    }

    final Relations relations = new Relations();
    try {
      ArrayValidator.validate(new ArrayCreateIterator(idToElement, elementIds, trialType), 1, idToElement.get(elementIds), 0, minIterate, maxIterate, 1, idToElement, relations, true, null, -1);
    }
    catch (final IOException e) {
      throw new IllegalStateException(e);
    }

    if (trialType == TrialType.MAX_OCCURS) {
      final Map<Annotation,Integer> annotationToCount = new HashMap<>();
      ListIterator<Relation> iterator = relations.listIterator();
      while (iterator.hasNext()) {
        final Relation relation = iterator.next();
        final Integer count = annotationToCount.get(relation.annotation);
        annotationToCount.put(relation.annotation, (count == null ? 0 : count) + 1);
      }

      iterator = relations.listIterator();
      while (iterator.hasNext()) {
        final Relation relation = iterator.next();
        final Integer count = annotationToCount.get(relation.annotation);
        final int maxOccurs = JxUtil.getMaxOccurs(relation.annotation);
        if (count >= maxOccurs) {
          iterator.remove();
          annotationToCount.put(relation.annotation, count - 1);
        }
      }
    }

    return relations.deflate();
  }

  static void add(final List<PropertyTrial<?>> trials, final Field field, final Object object, final ArrayProperty property) {
    trials.add(new ArrayTrial<>(ValidCase.CASE, field, object, createValid(property.type(), property.minIterate(), property.maxIterate(), property.elementIds(), new IdToElement()), property));
    final Object testNullable = createArray(property.type(), property.minIterate(), property.maxIterate(), property.elementIds(), new IdToElement(), TrialType.NULLABLE);
    if (testNullable != null)
      trials.add(new ArrayTrial<>(NullableCase.CASE, field, object, testNullable, property));

    final Object testMinOccurs = createArray(property.type(), property.minIterate(), property.maxIterate(), property.elementIds(), new IdToElement(), TrialType.MIN_OCCURS);
    if (testMinOccurs != null)
      trials.add(new ArrayTrial<>(MinOccursCase.CASE, field, object, testMinOccurs, property));

    final Object testMaxOccurs = createArray(property.type(), property.minIterate(), property.maxIterate(), property.elementIds(), new IdToElement(), TrialType.MAX_OCCURS);
    if (testMaxOccurs != null)
      trials.add(new ArrayTrial<>(MaxOccursCase.CASE, field, object, testMaxOccurs, property));

    if (property.use() == Use.REQUIRED) {
      trials.add(new ArrayTrial<>(getNullableCase(property.nullable()), field, object, null, property));
    }
    else if (property.nullable()) {
      trials.add(new ArrayTrial<>(OptionalNullableCase.CASE, field, object, null, property));
      trials.add(new ArrayTrial<>(OptionalNullableCase.CASE, field, object, Optional.ofNullable(null), property));
    }
    else {
      trials.add(new ArrayTrial<>(OptionalNotNullableCase.CASE, field, object, null, property));
    }
  }

  private ArrayTrial(final Case<? extends PropertyTrial<? super T>> kase, final Field field, final Object object, final T value, final ArrayProperty property) {
    super(kase, field, object, value, property.name(), property.use());
  }
}