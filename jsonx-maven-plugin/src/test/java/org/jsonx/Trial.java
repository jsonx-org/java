/* Copyright (c) 2019 JSONx
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

abstract class Trial extends Asserting {
  static Object createValidNonNullArrayMember(final Annotation annotation) {
    if (annotation instanceof StringElement) {
      final StringElement element = (StringElement)annotation;
      return StringTrial.createValid(element.type(), element.decode(), element.pattern());
    }

    if (annotation instanceof NumberElement) {
      final NumberElement element = (NumberElement)annotation;
      return NumberTrial.createValid(element.type(), element.decode(), element.range(), element.scale());
    }

    if (annotation instanceof ObjectElement) {
      final ObjectElement element = (ObjectElement)annotation;
      return ObjectTrial.createValid(element.type());
    }

    if (annotation instanceof ArrayElement) {
      final ArrayElement element = (ArrayElement)annotation;
      return ArrayTrial.createValid(element.type(), element.minIterate(), element.maxIterate(), element.elementIds(), new IdToElement());
    }

    if (annotation instanceof BooleanElement) {
      final BooleanElement element = (BooleanElement)annotation;
      return BooleanTrial.createValid(element.type(), element.decode());
    }

    if (annotation instanceof AnyElement)
      throw new UnsupportedOperationException();

    throw new UnsupportedOperationException("Unsupported annotation type: " + annotation.annotationType().getName());
  }
}