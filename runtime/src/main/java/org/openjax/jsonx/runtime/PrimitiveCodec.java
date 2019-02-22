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

import java.lang.reflect.Field;

abstract class PrimitiveCodec<T> extends Codec {
  PrimitiveCodec(final Field field, final String name, final boolean property, final Use use) {
    super(field, name, property, use);
  }

  final StringBuilder matches(final String json) {
    if (!test(json.charAt(0)))
      return new StringBuilder("Expected \"").append(name).append("\" to be a \"").append(elementName()).append("\", but got: ").append(json);

    return validate(json);
  }

  abstract boolean test(char firstChar);
  abstract StringBuilder validate(String json);
  abstract T parse(String json);
}