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

import org.jsonx.ArrayValidator.Relations;
import org.libj.lang.Strings;
import org.libj.util.function.TriPredicate;

abstract class ArrayIterator {
  Object offLen0;

  final boolean nextIsNull() throws IOException {
    next();
    return offLen0 == null;
  }

  Object preview(final Object object) {
    return Strings.truncate(String.valueOf(object), 16);
  }

  abstract boolean hasNext() throws IOException;
  abstract void next() throws IOException;
  abstract void next(int index) throws IOException;
  abstract int previous();
  abstract int nextIndex() throws IOException;
  abstract Error validate(Annotation annotation, int index, Relations relations, IdToElement idToElement, Class<? extends Codec> codecType, boolean validate, TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException;
}