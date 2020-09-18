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
import java.lang.reflect.Executable;
import java.lang.reflect.Method;

import org.jsonx.ArrayValidator.Relation;
import org.jsonx.ArrayValidator.Relations;
import org.libj.lang.Classes;
import org.openjax.json.JsonReader;

class BooleanCodec extends PrimitiveCodec {
  static Object decodeArray(final Class<?> type, final String decode, final String token) {
    return decodeObject(type, decode == null || decode.isEmpty() ? null : getMethod(decodeToMethod, decode, String.class), token);
  }

  private static Object decodeObject(final Class<?> type, final Executable decode, final String json) {
    return decode != null ? JsdUtil.invoke(decode, json) : Classes.isAssignableFrom(type, String.class) ? json : "true".equals(json) ? Boolean.TRUE : "false".equals(json) ? Boolean.FALSE : null;
  }

  static Error encodeArray(final Annotation annotation, final Class<?> type, final String encode, Object object, final int index, final Relations relations) {
    if (!Classes.isInstance(type, object))
      return Error.CONTENT_NOT_EXPECTED(object, null);

    final Executable method = getMethod(encodeToMethod, encode, object.getClass());
    if (method != null)
      object = JsdUtil.invoke(method, object);

    relations.set(index, new Relation(object, annotation));
    return null;
  }

  static Object encodeObject(final Class<?> type, final String encode, Object object) throws EncodeException, ValidationException {
    if (!Classes.isInstance(type, object))
      return Error.CONTENT_NOT_EXPECTED(object, null);

    final Executable method = getMethod(encodeToMethod, encode, object.getClass());
    if (method != null)
      return JsdUtil.invoke(method, object);

    if (object instanceof Boolean)
      return String.valueOf(object);

    if (object instanceof String)
      return object;

    throw new IllegalArgumentException("Illegal argument class: " + object.getClass());
  }

  BooleanCodec(final BooleanProperty property, final Method getMethod, final Method setMethod) {
    super(getMethod, setMethod, property.name(), property.nullable(), property.use(), property.decode());
  }

  @Override
  boolean test(final char firstChar) {
    return firstChar == 't' || firstChar == 'f';
  }

  @Override
  Error validate(final String json, final JsonReader reader) {
    return !"true".equals(json) && !"false".equals(json) ? Error.BOOLEAN_NOT_VALID(json, reader) : null;
  }

  @Override
  Object parseValue(final String json) {
    return decodeObject(type(), decode(), json);
  }

  @Override
  String elementName() {
    return "boolean";
  }
}