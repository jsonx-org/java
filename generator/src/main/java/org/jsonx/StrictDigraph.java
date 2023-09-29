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

import org.libj.util.Digraph;

class StrictDigraph<T> extends Digraph<T> {
  private final String selfLinkErrorHeading;

  StrictDigraph(final String selfLinkErrorHeading) {
    this.selfLinkErrorHeading = selfLinkErrorHeading;
  }

  @Override
  public boolean add(final T from, final T to) {
    if (from.equals(to))
      throw new ValidationException(selfLinkErrorHeading + ": " + from + " -> " + to);

    return super.add(from, to);
  }

  @Override
  public StrictDigraph<T> clone() {
    return (StrictDigraph<T>)super.clone();
  }
}