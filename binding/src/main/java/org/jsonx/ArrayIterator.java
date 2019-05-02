/* Copyright (c) 2018 Jsonx
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

import org.jsonx.ArrayValidator.Relation;
import org.jsonx.ArrayValidator.Relations;
import org.openjax.ext.util.Strings;
import org.openjax.ext.util.function.TriPredicate;

abstract class ArrayIterator {
  static String preview(final Object current) {
    return Strings.truncate(String.valueOf(current), 16);
  }

  Object current;

  final Relation currentRelate(final Annotation annotation) {
    return new Relation(current, annotation);
  }

  final boolean nextIsNull() throws IOException {
    next();
    return current == null;
  }

  abstract boolean hasNext() throws IOException;
  abstract void next() throws IOException;
  abstract void previous();
  abstract int nextIndex() throws IOException;
  abstract Error validate(Annotation annotation, int index, Relations relations, IdToElement idToElement, Class<? extends Codec> codecType, boolean validate, TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException;
}