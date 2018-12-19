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
import java.lang.reflect.Field;

class BooleanCodec extends PrimitiveCodec<Boolean> {
  static String encode(final Annotation annotation, final Boolean object) throws EncodeException, ValidationException {
    if (!(annotation instanceof BooleanProperty) && !(annotation instanceof BooleanElement))
        throw new IllegalArgumentException("Illegal annotation type for \"boolean\": " + annotation.annotationType().getName());

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
  String validate(final String json) {
    return !"true".equals(json) && !"false".equals(json) ? "Not a valid boolean token: " + json : null;
  }

  @Override
  Boolean parse(final String json) {
    return Boolean.valueOf(json);
  }

  @Override
  String elementName() {
    return "boolean";
  }
}