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
import java.math.BigDecimal;

import org.openjax.standard.util.function.TriPredicate;
import org.openjax.jsonx.runtime.ArrayTrial.TrialType;
import org.openjax.jsonx.runtime.ArrayValidator.Relations;

class ArrayCreateIterator extends ArrayIterator {
  private final IdToElement idToElement;
  private final int[] elementIds;
  private int index = -1;
  private final TrialType trialType;

  ArrayCreateIterator(final IdToElement idToElement, final int[] elementIds, final TrialType trialType) {
    this.idToElement = idToElement;
    this.elementIds = elementIds;
    this.trialType = trialType;
  }

  @Override
  protected boolean hasNext() throws IOException {
    return index < elementIds.length - 1;
  }

  @Override
  protected int nextIndex() throws IOException {
    return hasNext() ? index + 1 : index;
  }

  @Override
  protected void next() throws IOException {
    current = idToElement.get(elementIds[++index]);
  }

  @Override
  protected boolean nextIsNull() throws IOException {
    next();
    return current == null;
  }

  @Override
  protected void previous() {
    --index;
  }

  @Override
  protected String currentMatchesType(final Class<?> type, final Annotation annotation, IdToElement idToElement, final TriPredicate<JxObject,String,Object> callback) throws IOException {
    if (trialType == TrialType.NULLABLE) {
      current = null;
    }
    else if (annotation instanceof ArrayElement) {
      final ArrayElement element = (ArrayElement)annotation;
      current = element.nullable() && Math.random() < 0.5 ? null : ArrayTrial.createValid(element.type(), element.elementIds(), idToElement);
    }
    else if (annotation instanceof BooleanElement) {
      final BooleanElement element = (BooleanElement)annotation;
      current = element.nullable() && Math.random() < 0.5 ? null : Math.random() < 0.5 ? Boolean.TRUE : Boolean.FALSE;
    }
    else if (annotation instanceof NumberElement) {
      final NumberElement element = (NumberElement)annotation;
      current = element.nullable() && Math.random() < 0.5 ? null : element.range().length() == 0 ? NumberTrial.toProperForm(element.form(), BigDecimal.valueOf(PropertyTrial.random.nextDouble() * PropertyTrial.random.nextLong())) : NumberTrial.toProperForm(element.form(), NumberTrial.makeValid(new Range(element.range())));
    }
    else if (annotation instanceof ObjectElement) {
      final ObjectElement element = (ObjectElement)annotation;
      current = element.nullable() && Math.random() < 0.5 ? null : ObjectTrial.createValid(element.type());
    }
    else if (annotation instanceof StringElement) {
      final StringElement element = (StringElement)annotation;
      current = element.nullable() && Math.random() < 0.5 ? null : StringTrial.createValid(element.pattern());
    }
    else {
      throw new UnsupportedOperationException("Unsupported annotation type: " + annotation.annotationType().getName());
    }

    return null;
  }

  @Override
  protected String currentIsValid(final int i, final Annotation annotation, final IdToElement idToElement, final Relations relations, final boolean validate, final TriPredicate<JxObject,String,Object> callback) {
    relations.set(i, currentRelate(annotation));
    return null;
  }
}