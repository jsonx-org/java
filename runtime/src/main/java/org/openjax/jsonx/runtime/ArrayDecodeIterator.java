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
import java.util.List;

import org.openjax.jsonx.runtime.ArrayValidator.Relation;
import org.openjax.jsonx.runtime.ArrayValidator.Relations;
import org.openjax.standard.json.JsonReader;
import org.openjax.standard.util.ArrayIntList;
import org.openjax.standard.util.function.TriPredicate;

class ArrayDecodeIterator extends ArrayIterator {
  private final JsonReader reader;
  private final ArrayIntList indexes = new ArrayIntList();
  private int cursor = 0;

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
  protected StringBuilder currentMatchesType(final Class<?> type, final Annotation annotation, IdToElement idToElement, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException {
    final String token = (String)current;
    final Object value;
    if (Boolean.class.equals(type)) {
      value = "true".equals(token) ? Boolean.TRUE : "false".equals(token) ? Boolean.FALSE : null;
    }
    else if (Number.class.equals(type)) {
      final char ch = token.charAt(0);
      value = ch == '-' || '0' <= ch && ch <= '9' ? NumberCodec.decode(((NumberElement)annotation).form(), token) : null;
    }
    else if (String.class.equals(type)) {
      value = token.charAt(0) == '"' && token.charAt(token.length() - 1) == '"' ? token.substring(1, token.length() - 1) : null;
    }
    else if (List.class.equals(type)) {
      if (!"[".equals(token))
        return new StringBuilder("Content is not expected: ").append(token);

      final ArrayElement element = (ArrayElement)annotation;
      final int[] elementIds;
      final int minIterate;
      final int maxIterate;
      if (element.type() != ArrayType.class) {
        elementIds = JxUtil.digest(element.type().getAnnotations(), element.type().getName(), idToElement = new IdToElement());
        minIterate = idToElement.getMinIterate();
        maxIterate = idToElement.getMaxIterate();
      }
      else {
        minIterate = element.minIterate();
        maxIterate = element.maxIterate();
        elementIds = element.elementIds();
      }

      final Annotation[] annotations = idToElement.get(elementIds);
      final Object array = ArrayCodec.decode(annotations, minIterate, maxIterate, idToElement, reader, onPropertyDecode);
      if (array instanceof StringBuilder)
        return (StringBuilder)array;

      value = array;
    }
    else if (Object.class.equals(type)) {
      if (!"{".equals(token))
        return new StringBuilder("Content is not expected: ").append(token);

      final ObjectElement element = (ObjectElement)annotation;
      final Object object = ObjectCodec.decode(element.type(), reader, onPropertyDecode);
      if (object instanceof StringBuilder)
        return (StringBuilder)object;

      value = object;
    }
    else {
      throw new UnsupportedOperationException("Unsupported type: " + type.getName());
    }

    if (value == null)
      return new StringBuilder("Content is not expected: ").append(currentPreview());

    current = value;
    return null;
  }

  @Override
  protected final StringBuilder currentIsValid(final int i, final Annotation annotation, final IdToElement idToElement, final Relations relations, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) {
    if (annotation instanceof StringElement)
      return validate((StringElement)annotation, current, i, relations, true, validate);

    if (annotation instanceof NumberElement)
      return validate((NumberElement)annotation, current, i, relations, validate);

    if (annotation instanceof BooleanElement)
      return validate((BooleanElement)annotation, current, i, relations);

    if (annotation instanceof ArrayElement) {
      relations.set(i, new Relation(current, annotation));
      return null;
    }

    if (annotation instanceof ObjectElement)
      return validate((ObjectElement)annotation, current, i, relations, validate);

    throw new UnsupportedOperationException("Unsupported annotation type: " + annotation.annotationType().getName());
  }
}