/* Copyright (c) 2017 JSONx
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

import java.util.function.Function;

import org.libj.util.RefDigraph;

class StrictRefDigraph<T,R> extends RefDigraph<T,R> {
  private final String selfLinkErrorHeading;

  StrictRefDigraph(final String selfLinkErrorHeading, final Function<T,R> reference) {
    super(reference);
    this.selfLinkErrorHeading = selfLinkErrorHeading;
  }

  @Override
  public boolean add(final T from, final R to) {
    if (reference.apply(from).equals(to))
      throw new ValidationException(selfLinkErrorHeading + ": " + reference.apply(from) + " -> " + to);

    return super.add(from, to);
  }

  @Override
  public StrictRefDigraph<T,R> clone() {
    return (StrictRefDigraph<T,R>)super.clone();
  }
}