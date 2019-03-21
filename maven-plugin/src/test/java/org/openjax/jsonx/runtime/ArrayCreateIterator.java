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

import org.openjax.jsonx.runtime.ArrayTrial.TrialType;
import org.openjax.jsonx.runtime.ArrayValidator.Relations;
import org.openjax.standard.util.function.TriPredicate;

class ArrayCreateIterator extends ArrayIterator {
  private final TrialType trialType;
  private short cursor = 0;
  private Annotation annotation;
  private Annotation lastAnnotation;

  ArrayCreateIterator(final IdToElement idToElement, final int[] elementIds, final TrialType trialType) {
    this.trialType = trialType;
    this.lastAnnotation = idToElement.get(elementIds[elementIds.length - 1]);
  }

  @Override
  protected boolean hasNext() throws IOException {
    return lastAnnotation != annotation;
  }

  @Override
  protected int nextIndex() throws IOException {
    return cursor;
  }

  @Override
  protected void next() throws IOException {
    current = this;
    ++cursor;
  }

  @Override
  protected void previous() {
    --cursor;
  }

  @Override
  protected Error validate(final Annotation annotation, final int index, final Relations relations, final IdToElement idToElement, final Class<? extends Codec> codecType, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException {
    this.annotation = annotation;
    if (trialType == TrialType.NULLABLE) {
      current = null;
    }
    else if (annotation instanceof AnyElement) {
      final AnyElement element = (AnyElement)annotation;
      current = element.nullable() && Math.random() < 0.5 ? null : AnyTrial.createValid(element);
    }
    else if (annotation instanceof ArrayElement) {
      final ArrayElement element = (ArrayElement)annotation;
      current = element.nullable() && Math.random() < 0.5 ? null : ArrayTrial.createValid(element.type(), element.minIterate(), element.maxIterate(), element.elementIds(), idToElement);
    }
    else if (annotation instanceof BooleanElement) {
      final BooleanElement element = (BooleanElement)annotation;
      current = element.nullable() && Math.random() < 0.5 ? null : BooleanTrial.createValid();
    }
    else if (annotation instanceof NumberElement) {
      final NumberElement element = (NumberElement)annotation;
      current = element.nullable() && Math.random() < 0.5 ? null : NumberTrial.createValid(element.range(), element.form());
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

    if (current instanceof ArrayValidator.Relation)
      System.out.println();
    relations.set(index, currentRelate(annotation));
    return null;
  }
}