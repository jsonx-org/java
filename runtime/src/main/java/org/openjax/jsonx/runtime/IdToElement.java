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
import java.util.HashMap;

import org.openjax.standard.util.ObservableMap;

public class IdToElement extends ObservableMap<Integer,Annotation> {
  private int minIterate;
  private int maxIterate;

  public IdToElement() {
    super(new HashMap<>());
  }

  public int getMinIterate() {
    return this.minIterate;
  }

  public void setMinIterate(final int minIterate) {
    this.minIterate = minIterate;
  }

  public int getMaxIterate() {
    return this.maxIterate;
  }

  public void setMaxIterate(final int maxIterate) {
    this.maxIterate = maxIterate;
  }

  @Override
  protected boolean beforePut(final Integer key, final Annotation oldValue, final Annotation newValue) {
    if (oldValue != null)
      throw new ValidationException("Duplicate id=" + key + " found in {" + oldValue + ", " + newValue + "}");

    return true;
  }

  public Annotation[] get(final int[] ids) {
    final Annotation[] annotations = new Annotation[ids.length];
    for (int i = 0; i < ids.length; ++i) {
      annotations[i] = get(ids[i]);
      if (annotations[i] == null)
        throw new ValidationException("@<Annotation>(id=" + ids[i] + ") not found in annotations array");
    }

    return annotations;
  }
}