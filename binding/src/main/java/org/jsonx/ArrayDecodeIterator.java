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

import org.jsonx.ArrayValidator.Relation;
import org.jsonx.ArrayValidator.Relations;
import org.libj.lang.Numbers.Composite;
import org.libj.util.function.TriPredicate;
import org.libj.util.primitive.ArrayIntList;
import org.openjax.json.JsonReader;

class ArrayDecodeIterator extends ArrayIterator {
  private final JsonReader reader;
  private final ArrayIntList indexes = new ArrayIntList();
  private int cursor;

  ArrayDecodeIterator(final JsonReader reader) {
    this.reader = reader;
  }

  @Override
  protected boolean hasNext() throws IOException {
    final int index = reader.getIndex();
    final long point = reader.readToken();
    final int off = Composite.decodeInt(point, 0);
    final char c0 = reader.bufToChar(off);
    // If the token is ',', then advance and check if there is another token following it
    if (c0 == ',')
      return hasNext();

    final boolean hasNext = c0 != ']';
    reader.setIndex(index);
    return hasNext;
  }

  @Override
  protected int nextIndex() {
    return cursor;
  }

  @Override
  protected void next() throws IOException {
    if (cursor++ == indexes.size())
      indexes.add(reader.getIndex());

    final long point = reader.readToken();
    final int off = Composite.decodeInt(point, 0);
    final int len = Composite.decodeInt(point, 1);
    if (len == 4 && reader.bufToChar(off) == 'n' && reader.bufToChar(off + 1) == 'u' && reader.bufToChar(off + 2) == 'l' && reader.bufToChar(off + 3) == 'l')
      current = null;
    else
      current = point;
  }

  @Override
  protected void previous() {
    reader.setIndex(indexes.get(--cursor));
  }

  @Override
  protected Error validate(final Annotation annotation, final int index, final Relations relations, final IdToElement idToElement, final Class<? extends Codec> codecType, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException {
    final long token = (long)current;
    final int off = Composite.decodeInt(token, 0);
    final int len = Composite.decodeInt(token, 1);
    final char c0 = reader.bufToChar(off);
    final String xxx = new String(reader.buf(), off, len);
    final Object value;
    if (codecType == AnyCodec.class) {
      value = AnyCodec.decode(annotation, xxx, reader, validate, onPropertyDecode);
    }
    else if (codecType == ArrayCodec.class) {
      value = ArrayCodec.decodeArray((ArrayElement)annotation, ((ArrayElement)annotation).type(), idToElement, xxx, reader, validate, onPropertyDecode);
    }
    else if (codecType == BooleanCodec.class) {
      final BooleanElement element = (BooleanElement)annotation;
      value = BooleanCodec.decodeArray(element.type(), element.decode(), xxx);
    }
    else if (codecType == NumberCodec.class) {
      final NumberElement element = (NumberElement)annotation;
      value = NumberCodec.decodeArray(element.type(), element.scale(), element.decode(), xxx);
    }
    else if (codecType == ObjectCodec.class) {
      value = ObjectCodec.decodeArray(((ObjectElement)annotation).type(), xxx, reader, validate, onPropertyDecode);
    }
    else if (codecType == StringCodec.class) {
      final StringElement element = (StringElement)annotation;
      value = StringCodec.decodeArray(element.type(), element.decode(), xxx);
    }
    else {
      throw new UnsupportedOperationException("Unsupported " + Codec.class.getSimpleName() + " type: " + codecType.getName());
    }

    if (value == null)
      return Error.CONTENT_NOT_EXPECTED(xxx, null);

    if (value instanceof Error)
      return (Error)value;

    current = value;
    relations.set(index, new Relation(current, annotation));
    return null;
  }
}