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
import java.util.List;
import java.util.ListIterator;

class EncodeIterator extends ArrayIterator {
  private final ListIterator<Object> listIterator;

  public EncodeIterator(final ListIterator<Object> listIterator) {
    this.listIterator = listIterator;
  }

  @Override
  protected void next() {
    current = listIterator.next();
  }

  @Override
  protected void previous() {
    current = listIterator.previous();
  }

  @Override
  public boolean hasNext() {
    return listIterator.hasNext();
  }

  @Override
  public int nextIndex() {
    return listIterator.nextIndex();
  }

  @Override
  protected boolean nextIsNull() {
    next();
    return current == null;
  }

  @Override
  protected boolean currentMatchesType(final Class<?> type, final Annotation annotation, final IdToElement idToElement) {
    final Class<?> cls = current.getClass();
    return type != Object.class ? type.isAssignableFrom(cls) : !cls.isArray() && !Boolean.class.isAssignableFrom(cls) && !List.class.isAssignableFrom(cls) && !Number.class.isAssignableFrom(cls) && !String.class.isAssignableFrom(cls);
  }
}