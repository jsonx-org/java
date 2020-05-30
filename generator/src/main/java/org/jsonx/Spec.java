/* Copyright (c) 2020 JSONx
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

class Spec<T> {
  static <T>Spec<T> from(final T spec, final T value) {
    return new Spec<>(spec, value);
  }

  final T set;
  final T get;

  /**
   * @param set The raw value set from the specification.
   * @param get The calculated value to be gotten from this {@link Spec}.
   */
  private Spec(final T set, final T get) {
    this.set = set;
    this.get = get;
  }

  @Override
  public String toString() {
    return "set: " + set + "\nget: " + get;
  }
}