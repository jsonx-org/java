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

import org.openjax.json.JsonReader;
import org.jsonx.ArrayValidator.Relation;
import org.jsonx.ArrayValidator.Relations;
import org.libj.util.ArrayIntList;
import org.libj.util.function.TriPredicate;

class ArrayDecodeIterator extends ArrayIterator {
  private final JsonReader reader;
  private final ArrayIntList indexes = new ArrayIntList();
  private short cursor = 0;

  ArrayDecodeIterator(final JsonReader reader) {
    this.reader = reader;
  }

  @Override
  protected boolean hasNext() throws IOException {
    final int index = reader.getIndex();
    final String token = reader.readToken();
    // If the token is ",", then advance and check if there is another token following it
    if (",".equals(token))
      return hasNext();

    final boolean hasNext = !"]".equals(token);
    reader.setIndex(index);
    return hasNext;
  }

  @Override
  protected int nextIndex() throws IOException {
    return cursor;
  }

  @Override
  protected void next() throws IOException {
    if (cursor++ == indexes.size())
      indexes.add(reader.getIndex());

    current = reader.readToken();
    if ("null".equals(current))
      current = null;
  }

  @Override
  protected void previous() {
    reader.setIndex(indexes.get(--cursor));
  }

  @Override
  protected Error validate(final Annotation annotation, final int index, final Relations relations, final IdToElement idToElement, final Class<? extends Codec> codecType, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException {
    final String token = (String)current;
    final Object value;
    if (codecType == AnyCodec.class)
      value = AnyCodec.decode(annotation, token, reader, onPropertyDecode);
    else if (codecType == ArrayCodec.class)
      value = ArrayCodec.decodeArray((ArrayElement)annotation, ((ArrayElement)annotation).type(), token, reader, idToElement, onPropertyDecode);
    else if (codecType == BooleanCodec.class)
      value = BooleanCodec.decodeArray(token);
    else if (codecType == NumberCodec.class)
      value = NumberCodec.decodeArray(((NumberElement)annotation).scale(), token);
    else if (codecType == ObjectCodec.class)
      value = ObjectCodec.decodeArray(((ObjectElement)annotation).type(), token, reader, onPropertyDecode);
    else if (codecType == StringCodec.class)
      value = StringCodec.decodeArray(token);
    else
      throw new UnsupportedOperationException("Unsupported " + Codec.class.getSimpleName() + " type: " + codecType.getName());

    if (value == null)
      return Error.CONTENT_NOT_EXPECTED(token, -1);

    if (value instanceof Error)
      return (Error)value;

    current = value;
    relations.set(index, new Relation(current, annotation));
    return null;
  }
}