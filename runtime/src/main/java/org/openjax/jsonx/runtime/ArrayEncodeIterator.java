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
import org.openjax.standard.util.function.TriPredicate;

class ArrayEncodeIterator extends ArrayIterator {
  static <T>Error validateArray(final ArrayElement element, final List<T> member, final int i, IdToElement idToElement, final Relations relations, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) {
    final int[] elementIds;
    if (element.type() != ArrayType.class) {
      elementIds = JxUtil.digest(element.type().getAnnotations(), element.type().getName(), idToElement = new IdToElement());
    }
    else {
      elementIds = element.elementIds();
      idToElement.setMinIterate(element.minIterate());
      idToElement.setMaxIterate(element.maxIterate());
    }

    final Relations subRelations = new Relations();
    final Error subError = ArrayValidator.validate(member, idToElement, elementIds, subRelations, validate, onPropertyDecode);
    if (validate && subError != null)
      return subError;

    relations.set(i, new Relation(subRelations, element));
    return null;
  }

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
  protected void next() {
    current = listIterator.next();
  }

  @Override
  protected void previous() {
    current = listIterator.previous();
  }

  @Override
  protected Error validate(final Annotation annotation, final int index, final Relations relations, final IdToElement idToElement, final Class<? extends Codec> codecType, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) {
    if (codecType == AnyCodec.class)
      return AnyCodec.encodeArray((AnyElement)annotation, current, index, relations, idToElement, validate, onPropertyDecode);

    if (codecType == ArrayCodec.class)
      return ArrayCodec.encodeArray(annotation, ((ArrayElement)annotation).type(), current, index, relations, idToElement, validate, onPropertyDecode);

    if (codecType == BooleanCodec.class)
      return BooleanCodec.encodeArray(annotation, current, index, relations);

    if (codecType == NumberCodec.class)
      return NumberCodec.encodeArray(annotation, ((NumberElement)annotation).form(), ((NumberElement)annotation).range(), current, index, relations, validate);

    if (codecType == ObjectCodec.class)
      return ObjectCodec.encodeArray(annotation, current, index, relations);

    if (codecType == StringCodec.class)
      return StringCodec.encodeArray(annotation, ((StringElement)annotation).pattern(), current, index, relations, validate);

    throw new UnsupportedOperationException("Unsupported " + Codec.class.getSimpleName() + " type: " + codecType.getName());
  }
}