/* Copyright (c) 2018 lib4j
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

package org.libx4j.jsonx.runtime;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.List;

import org.lib4j.json.JsonReader;
import org.fastjax.util.ArrayIntList;
import org.libx4j.jsonx.runtime.ArrayValidator.Relations;

public class ParsingIterator extends ArrayIterator<Object> {
  private final JsonReader reader;
  private final ArrayIntList positions = new ArrayIntList();
  private int index = -1;

  public ParsingIterator(final JsonReader reader) {
    this.reader = reader;
  }

  @Override
  protected void next() throws IOException {
    this.positions.add(reader.getIndex());
    current = reader.readToken();
    ++index;
  }

  @Override
  protected void previous() {
//    this.reader.setPosition(positions.get(index--));
  }

  @Override
  protected boolean hasNext() throws IOException {
    final int start = reader.getIndex();
    final String token = reader.readToken();
    final boolean hasNext = !"]".equals(token);
//    reader.setPosition(start);
    return hasNext;
  }

  @Override
  protected int nextIndex() throws IOException {
    return hasNext() ? index + 1 : index;
  }

  @Override
  protected boolean nextIsNull() throws IOException {
    return "null".equals(current);
  }

  private String error;

  @Override
  protected boolean currentMatchesType(final Class<?> type, final Annotation annotation, IdToElement idToElement) throws DecodeException, IOException {
    error = null;
    final String token = (String)current;
    final Object value;
    if (Boolean.class.equals(type)) {
      value = "true".equals(token) ? true : "false".equals(token) ? false : null;
    }
    else if (Number.class.equals(type)) {
      final char ch = token.charAt(0);
      value = ch == '-' || '0' <= ch && ch <= '9' ? new BigDecimal(token) : null;
    }
    else if (String.class.equals(type)) {
      value = token.charAt(0) == '"' && token.charAt(token.length() - 1) == '"' || token.charAt(0) == '\'' && token.charAt(token.length() - 1) == '\'' ? token.substring(1, token.length() - 1) : null;
    }
    else if (List.class.equals(type)) {
      if ("[".equals(token)) {
        final ArrayElement element = (ArrayElement)annotation;
        final int[] elementIds;
        if (element.type() != ArrayType.class)
          elementIds = JsonxUtil.digest(element.type().getAnnotations(), element.type().getName(), idToElement = new IdToElement());
        else
          elementIds = element.elementIds();

        final Annotation[] annotations = idToElement.get(elementIds);
        final Object array = JxDecoder.parse0(annotations, idToElement, reader);
        if (array instanceof String) {
          error = (String)array;
          value = null;
        }
        else {
          value = array;
        }
      }
      else {
        value = null;
      }
    }
    else if (Object.class.equals(type)) {
      if ("{".equals(token)) {
        final Object object = JxDecoder.parse0(((ObjectElement)annotation).type(), reader);
        if (object instanceof String) {
          error = (String)object;
          value = null;
        }
        else {
          value = object;
        }
      }
      else {
        value = null;
      }
    }
    else {
      throw new UnsupportedOperationException("Unsupported type: " + type.getName());
    }

    if (value == null)
      return false;

    current = value;
    return true;
  }

  @Override
  public String currentIsValid(final int i, final Annotation annotation, final IdToElement idToElement, final Relations relations) {
    return error != null ? null : validatePrimitive(annotation, current, i, relations);
  }
}