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
import java.math.BigDecimal;
import java.util.List;
import java.util.function.BiConsumer;

import org.fastjax.json.JsonReader;
import org.fastjax.util.ArrayIntList;

class ArrayDecodeIterator extends ArrayIterator {
  private final JsonReader reader;
  private final ArrayIntList indexes = new ArrayIntList();
  private int cursor = -1;

  public ArrayDecodeIterator(final JsonReader reader) {
    this.reader = reader;
  }

  @Override
  protected void next() throws IOException {
    if (++cursor == indexes.size())
      indexes.add(reader.getIndex());

    current = reader.readToken();
    if ("null".equals(current))
      current = null;
  }

  @Override
  protected void previous() {
    reader.setIndex(indexes.get(cursor--));
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
    return hasNext() ? cursor + 1 : cursor;
  }

  @Override
  protected boolean nextIsNull() throws IOException {
    next();
    return current == null;
  }

  @Override
  protected String currentMatchesType(final Class<?> type, final Annotation annotation, IdToElement idToElement, final BiConsumer<Field,Object> callback) throws IOException {
    final String token = (String)current;
    final Object value;
    if (Boolean.class.equals(type)) {
      value = "true".equals(token) ? Boolean.TRUE : "false".equals(token) ? Boolean.FALSE : null;
    }
    else if (Number.class.equals(type)) {
      final char ch = token.charAt(0);
      value = ch == '-' || '0' <= ch && ch <= '9' ? new BigDecimal(token) : null;
    }
    else if (String.class.equals(type)) {
      value = token.charAt(0) == '"' && token.charAt(token.length() - 1) == '"' ? token.substring(1, token.length() - 1) : null;
    }
    else if (List.class.equals(type)) {
      if (!"[".equals(token))
        return "Content is not expected: " + token;

      final ArrayElement element = (ArrayElement)annotation;
      final int[] elementIds;
      if (element.type() != ArrayType.class)
        elementIds = JsonxUtil.digest(element.type().getAnnotations(), element.type().getName(), idToElement = new IdToElement());
      else
        elementIds = element.elementIds();

      final Annotation[] annotations = idToElement.get(elementIds);
      final Object array = ArrayCodec.decode(annotations, idToElement, reader, callback);
      if (array instanceof String)
        return (String)array;

      value = array;
    }
    else if (Object.class.equals(type)) {
      if (!"{".equals(token))
        return "Content is not expected: " + token;

      final ObjectElement element = (ObjectElement)annotation;
      final Object object = ObjectCodec.decode(element.type(), reader, callback);
      if (object instanceof String)
        return (String)object;

      value = object;
    }
    else {
      throw new UnsupportedOperationException("Unsupported type: " + type.getName());
    }

    if (value == null)
      return "Content is not expected: " + currentPreview();

    current = value;
    return null;
  }
}