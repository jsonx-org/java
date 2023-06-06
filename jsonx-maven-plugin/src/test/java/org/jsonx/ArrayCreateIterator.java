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
import java.lang.annotation.Annotation;

import org.jsonx.ArrayTrial.TrialType;
import org.jsonx.ArrayValidator.Relations;
import org.libj.util.function.PentaConsumer;
import org.libj.util.function.TriPredicate;

class ArrayCreateIterator extends ArrayIterator {
  private final TrialType trialType;
  private int cursor = 0;
  private int lastIndex;
  private final int size;

  ArrayCreateIterator(final IdToElement idToElement, final TrialType trialType) {
    this.trialType = trialType;
    this.size = idToElement.size();
  }

  @Override
  boolean hasNext() throws IOException {
    return lastIndex < size;
  }

  @Override
  int nextIndex() throws IOException {
    return cursor;
  }

  @Override
  void next() throws IOException {
    current = this;
    ++cursor;
  }

  @Override
  void next(final int index) throws IOException {
    cursor = index;
    next();
  }

  @Override
  int previous() {
    final int index = cursor;
    --cursor;
    return index;
  }

  @Override
  Error validate(final Annotation annotation, final int index, final Relations relations, final IdToElement idToElement, final Class<? extends Codec> codecType, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException {
    lastIndex = index;
    if (trialType == TrialType.NULLABLE) {
      current = null;
    }
    else if (annotation instanceof StringElement) {
      final StringElement element = (StringElement)annotation;
      current = element.nullable() && Math.random() < 0.5 ? null : StringTrial.createValid(element.type(), element.decode(), element.pattern());
    }
    else if (annotation instanceof NumberElement) {
      final NumberElement element = (NumberElement)annotation;
      current = element.nullable() && Math.random() < 0.5 ? null : NumberTrial.createValid(element.type(), element.decode(), element.range(), element.scale());
    }
    else if (annotation instanceof ObjectElement) {
      final ObjectElement element = (ObjectElement)annotation;
      current = element.nullable() && Math.random() < 0.5 ? null : ObjectTrial.createValid(element.type());
    }
    else if (annotation instanceof ArrayElement) {
      final ArrayElement element = (ArrayElement)annotation;
      current = element.nullable() && Math.random() < 0.5 ? null : ArrayTrial.createValid(element.type(), element.minIterate(), element.maxIterate(), element.elementIds(), idToElement);
    }
    else if (annotation instanceof BooleanElement) {
      final BooleanElement element = (BooleanElement)annotation;
      current = element.nullable() && Math.random() < 0.5 ? null : BooleanTrial.createValid(element.type(), element.decode());
    }
    else if (annotation instanceof AnyElement) {
      final AnyElement element = (AnyElement)annotation;
      if (element.nullable() && Math.random() < 0.5) {
        current = null;
      }
      else {
        AnyTrial.createValid(element, new PentaConsumer<Object,Class<?>,String,String,Annotation>() {
          @Override
          public void accept(final Object value, final Class<?> type, final String decode, final String encode, final Annotation typeAnnotation) {
            ArrayCreateIterator.this.current = value;
          }
        });
      }
    }
    else {
      throw new UnsupportedOperationException("Unsupported annotation type: " + annotation.annotationType().getName());
    }

    relations.set(index, currentRelate(annotation));
    return null;
  }
}