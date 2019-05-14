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
import java.lang.reflect.Field;

import org.jsonx.ArrayValidator.Relation;
import org.jsonx.ArrayValidator.Relations;

class BooleanCodec extends PrimitiveCodec<Boolean> {
  static Boolean decodeArray(final String token) {
    return "true".equals(token) ? Boolean.TRUE : "false".equals(token) ? Boolean.FALSE : null;
  }

  static Error encodeArray(final Annotation annotation, final Object object, final int index, final Relations relations) {
    if (!(object instanceof Boolean))
      return Error.CONTENT_NOT_EXPECTED(object, -1);

    relations.set(index, new Relation(object, annotation));
    return null;
  }

  static String encodeObject(final Boolean object) throws EncodeException, ValidationException {
    return String.valueOf(object);
  }

  BooleanCodec(final BooleanProperty property, final Field field) {
    super(field, property.name(), property.nullable(), property.use());
  }

  @Override
  boolean test(final char firstChar) {
    return firstChar == 't' || firstChar == 'f';
  }

  @Override
  Error validate(final String json, final int offset) {
    return !"true".equals(json) && !"false".equals(json) ? Error.BOOLEAN_NOT_VALID(json, offset) : null;
  }

  @Override
  Boolean parse(final String json) {
    return decodeArray(json);
  }

  @Override
  String elementName() {
    return "boolean";
  }
}