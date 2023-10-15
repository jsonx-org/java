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
import java.util.HashMap;

import org.libj.util.ObservableMap;

class IdToElement extends ObservableMap<Integer,Annotation> {
  private int minIterate;
  private int maxIterate;

  IdToElement() {
    super(new HashMap<>());
  }

  void putAll(final Annotation[] annotations) {
    JsdUtil.forEach(annotations, this::put);
  }

  void put(final Annotation annotation) {
    if (annotation instanceof StringElement)
      put(((StringElement)annotation).id(), annotation);
    else if (annotation instanceof NumberElement)
      put(((NumberElement)annotation).id(), annotation);
    else if (annotation instanceof ObjectElement)
      put(((ObjectElement)annotation).id(), annotation);
    else if (annotation instanceof ArrayElement)
      put(((ArrayElement)annotation).id(), annotation);
    else if (annotation instanceof BooleanElement)
      put(((BooleanElement)annotation).id(), annotation);
    else if (annotation instanceof AnyElement)
      put(((AnyElement)annotation).id(), annotation);
  }

  int getMinIterate() {
    return this.minIterate;
  }

  void setMinIterate(final int minIterate) {
    this.minIterate = minIterate;
  }

  int getMaxIterate() {
    return this.maxIterate;
  }

  void setMaxIterate(final int maxIterate) {
    this.maxIterate = maxIterate;
  }

  @Override
  protected Object beforePut(final Integer key, final Annotation oldValue, final Annotation newValue, final Object preventDefault) {
    if (oldValue != null)
      throw new ValidationException("Duplicate id=" + key + " found in {" + oldValue + ", " + newValue + "}");

    return newValue;
  }

  Annotation[] get(final int[] ids) {
    final int len = ids.length;
    final Annotation[] annotations = new Annotation[len];
    for (int i = 0; i < len; ++i) // [A]
      if ((annotations[i] = get(ids[i])) == null)
        throw new ValidationException("@<Annotation>(id=" + ids[i] + ") not found in annotations array");

    return annotations;
  }
}