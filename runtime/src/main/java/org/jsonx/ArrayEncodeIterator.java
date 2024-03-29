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

import java.lang.annotation.Annotation;
import java.util.ListIterator;

import org.jsonx.ArrayValidator.Relations;
import org.libj.util.function.TriPredicate;

class ArrayEncodeIterator extends ArrayIterator {
  private final ListIterator<Object> listIterator;

  ArrayEncodeIterator(final ListIterator<Object> listIterator) {
    this.listIterator = listIterator;
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
  void next() {
    current = listIterator.next();
  }

  @Override
  void next(final int index) {
    for (; listIterator.nextIndex() < index; listIterator.next()); // [I]
    next();
  }

  @Override
  int previous() {
    current = listIterator.previous();
    return listIterator.nextIndex();
  }

  @Override
  Error validate(final Annotation annotation, final int index, final Relations relations, final IdToElement idToElement, final Class<? extends Codec> codecType, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) {
    if (codecType == BooleanCodec.class) {
      final BooleanElement element = (BooleanElement)annotation;
      return BooleanCodec.encodeArray(annotation, element.type(), element.encode(), current, index, relations);
    }

    if (codecType == NumberCodec.class) {
      final NumberElement element = (NumberElement)annotation;
      return NumberCodec.encodeArray(annotation, element.scale(), element.range(), element.type(), element.encode(), current, index, relations, validate);
    }

    if (codecType == StringCodec.class) {
      final StringElement element = (StringElement)annotation;
      return StringCodec.encodeArray(annotation, element.pattern(), element.type(), element.encode(), current, index, relations, validate);
    }

    if (codecType == AnyCodec.class)
      return AnyCodec.encodeArray((AnyElement)annotation, current, index, relations, idToElement, validate, onPropertyDecode);

    if (codecType == ArrayCodec.class)
      return ArrayCodec.encodeArray(annotation, ((ArrayElement)annotation).type(), current, index, relations, idToElement, validate, onPropertyDecode);

    if (codecType == ObjectCodec.class)
      return ObjectCodec.encodeArray(annotation, current, index, relations);

    throw new UnsupportedOperationException("Unsupported " + Codec.class.getSimpleName() + " type: " + codecType.getName());
  }
}