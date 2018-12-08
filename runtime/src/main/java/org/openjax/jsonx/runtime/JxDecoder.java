/* Copyright (c) 2015 OpenJAX
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

import org.fastjax.json.JsonReader;
import org.fastjax.util.function.TriPredicate;

public final class JxDecoder {
  public static List<?> parseArray(final Class<? extends Annotation> annotationType, final JsonReader reader, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws DecodeException, IOException {
    final String token = reader.readToken();
    if (!"[".equals(token))
      throw new DecodeException("Expected '[', but got '" + token + "'", reader.getPosition() - 1);

    final IdToElement idToElement = new IdToElement();
    final int[] elementIds = JxUtil.digest(annotationType.getAnnotations(), annotationType.getName(), idToElement);
    final int position = reader.getPosition();
    final Object array = ArrayCodec.decode(idToElement.get(elementIds), idToElement, reader, onPropertyDecode);
    if (array instanceof String)
      throw new DecodeException((String)array, position);

    return (List<?>)array;
  }

  public static List<?> parseArray(final Class<? extends Annotation> annotationType, final JsonReader reader) throws DecodeException, IOException {
    return parseArray(annotationType, reader, null);
  }

  @SuppressWarnings("unchecked")
  public static <T extends JxObject>T parseObject(final Class<T> type, final JsonReader reader, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws DecodeException, IOException {
    final String token = reader.readToken();
    if (!"{".equals(token))
      throw new DecodeException("Expected '{', but got '" + token + "'", reader.getPosition() - 1);

    // FIXME: This position is incorrect for the DecodeException
    final int position = reader.getPosition();
    final Object object = ObjectCodec.decode(type, reader, onPropertyDecode);
    if (object instanceof String)
      throw new DecodeException((String)object, position);

    return (T)object;
  }

  public static <T extends JxObject>T parseObject(final Class<T> type, final JsonReader reader) throws DecodeException, IOException {
    return parseObject(type, reader, null);
  }

  private JxDecoder() {
  }
}