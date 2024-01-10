/* Copyright (c) 2019 LibJ
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

import java.lang.reflect.Method;

import org.jsonx.ArrayValidator.Relations;

/**
 * Interface to be used as a callback during the encoding of JSON form Jx objects.
 */
@FunctionalInterface
interface OnEncode {
  /**
   * Performs the callback.
   *
   * @param getMethod The "get" method whose return value is to be encoded.
   * @param name The name of the property, or {@code null} for array members.
   * @param relations The {@link Relations} if encoded in an array.
   * @param start The starting position of the encoded value in the JSON document.
   * @param end The ending position of the encoded value in the JSON document.
   */
  void accept(Method getMethod, String name, Relations relations, long start, long end);
}