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
import java.lang.reflect.Executable;
import java.lang.reflect.Method;

import org.jsonx.ArrayValidator.Relations;
import org.libj.lang.Classes;
import org.openjax.json.JsonReader;

class BooleanCodec extends PrimitiveCodec {
  static Object decodeArray(final Class<?> type, final String decode, final String token) {
    return decodeObject(type, decode == null || decode.length() == 0 ? null : getMethod(decodeToMethod, decode, String.class), token, NULL);
  }

  private static Object decodeObject(final Class<?> type, final Executable decode, final String json, final Object nullValue) {
    final char ch = json.charAt(0);
    if (ch != 't' && ch != 'f')
      return nullValue;

    return decode != null ? JsdUtil.invoke(decode, json) : Classes.isAssignableFrom(type, String.class) ? json : "true".equals(json) ? Boolean.TRUE : "false".equals(json) ? Boolean.FALSE : nullValue;
  }

  static Error encodeArray(final Annotation annotation, final Class<?> type, final String encode, Object object, final int index, final Relations relations) {
    if (!Classes.isInstance(type, object))
      return Error.CONTENT_NOT_EXPECTED(object, null, null);

    final Executable method = getMethod(encodeToMethod, encode, object.getClass());
    if (method != null)
      object = JsdUtil.invoke(method, object);

    relations.set(index, object, annotation);
    return null;
  }

  static Error encodeObject(final Appendable out, final Class<?> type, final String encode, Object object) throws EncodeException, IOException, ValidationException {
    if (!Classes.isInstance(type, object))
      return Error.CONTENT_NOT_EXPECTED(object, null, null);

    return encodeObjectUnsafe(out, type, encode, object);
  }

  static Error encodeObjectUnsafe(final Appendable out, final Class<?> type, final String encode, final Object object) throws EncodeException, IOException, ValidationException {
    if (!Classes.isInstance(type, object))
      return Error.CONTENT_NOT_EXPECTED(object, null, null);

    final Executable method = getMethod(encodeToMethod, encode, object.getClass());
    if (method != null)
      out.append(JsdUtil.invoke(method, object).toString());
    else if (object instanceof Boolean)
      out.append(String.valueOf(object));
    else if (object instanceof String)
      out.append((String)object);
    else
      throw new IllegalArgumentException("Illegal argument class: " + object.getClass().getName());

    return null;
  }

  BooleanCodec(final BooleanProperty property, final Method getMethod, final Method setMethod) {
    super(getMethod, setMethod, property.name(), property.nullable(), property.use(), property.decode());
  }

  @Override
  boolean test(final char firstChar) {
    return firstChar == 't' || firstChar == 'f';
  }

  @Override
  Object parseAndValidate(final String json, final JsonReader reader) {
    return !"true".equals(json) && !"false".equals(json) ? Error.BOOLEAN_NOT_VALID(json, reader) : decodeObject(type(), decode(), json, null);
  }

  @Override
  String elementName() {
    return "boolean";
  }
}