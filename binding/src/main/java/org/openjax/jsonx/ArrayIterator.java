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

import org.openjax.jsonx.runtime.ArrayValidator.Relation;
import org.openjax.jsonx.runtime.ArrayValidator.Relations;
import org.openjax.standard.util.Strings;
import org.openjax.standard.util.function.TriPredicate;

public abstract class ArrayIterator {
  static String preview(final Object current) {
    return Strings.truncate(String.valueOf(current), 16);
  }

  protected Object current;

  protected final Relation currentRelate(final Annotation annotation) {
    return new Relation(current, annotation);
  }

  protected final boolean nextIsNull() throws IOException {
    next();
    return current == null;
  }

  protected abstract boolean hasNext() throws IOException;
  protected abstract void next() throws IOException;
  protected abstract void previous();
  protected abstract int nextIndex() throws IOException;
  protected abstract Error validate(Annotation annotation, int index, Relations relations, IdToElement idToElement, Class<? extends Codec> codecType, boolean validate, TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException;
}