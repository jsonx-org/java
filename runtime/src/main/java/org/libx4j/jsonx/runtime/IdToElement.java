/* Copyright (c) 2018 lib4j
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

package org.libx4j.jsonx.runtime;

import java.lang.annotation.Annotation;
import java.util.HashMap;

import org.lib4j.util.WrappedMap;

public class IdToElement extends WrappedMap<Integer,Annotation> {
  public IdToElement() {
    super(new HashMap<>());
  }

  public Annotation[] get(final int[] ids) {
    final Annotation[] annotations = new Annotation[ids.length];
    for (int i = 0; i < ids.length; i++) {
      annotations[i] = get(ids[i]);
      if (annotations[i] == null)
        throw new IllegalStateException("@?Element(id=" + ids[i] + ") was not found in annotation spec");
    }

    return annotations;
  }
}