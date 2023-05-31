/* Copyright (c) 2023 JSONx
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

class Double$ extends Number {
  private final double v;

  Double$(final double v) {
    this.v = v;
  }

  @Override
  public int intValue() {
    return (int)v;
  }

  @Override
  public long longValue() {
    return (long)v;
  }

  @Override
  public float floatValue() {
    return (float)v;
  }

  @Override
  public double doubleValue() {
    return v;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this)
      return true;

    if (obj instanceof Double$)
      return v == ((Double$)obj).v;

    if (obj instanceof Double)
      return v == (double)obj;

    return false;
  }

  @Override
  public int hashCode() {
    return Double.hashCode(v);
  }

  @Override
  public String toString() {
    return NumberCodec.format(v);
  }
}