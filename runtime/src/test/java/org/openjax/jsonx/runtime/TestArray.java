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

import java.util.List;

public class TestArray {
  @BooleanElement(id=0, maxOccurs=3, nullable=true)
  @ArrayType(elementIds={0})
  static @interface Array1d1 {
  }

  @ArrayProperty(use=Use.OPTIONAL, type=Array1d1.class)
  private List<Boolean> array1d1;

  public List<Boolean> getArray1d1() {
    return this.array1d1;
  }

  public TestArray setArray1d1(final List<Boolean> array1d1) {
    this.array1d1 = array1d1;
    return this;
  }

  @NumberElement(id=4, minOccurs=1, maxOccurs=2, range="[0,10]", nullable=true)
  @NumberElement(id=3, minOccurs=0, maxOccurs=2, form=Form.INTEGER, range="[0,4]")
  @StringElement(id=2, minOccurs=0, maxOccurs=2, pattern="[a-z0-9]+", nullable=true)
  @StringElement(id=1, minOccurs=2, maxOccurs=3, pattern="[a-z]+", nullable=true)
  @BooleanElement(id=0, minOccurs=0, maxOccurs=3, nullable=true)
  @ArrayType(elementIds={0, 1, 2, 3, 4})
  static @interface Array1d2 {
  }

  @ArrayProperty(use=Use.OPTIONAL, type=Array1d2.class)
  private List<Object> array1d2;

  public List<Object> getArray1d2() {
    return this.array1d2;
  }

  public TestArray setArray1d2(final List<Object> array1d2) {
    this.array1d2 = array1d2;
    return this;
  }

  @ArrayElement(id=0, type=Array1d2.class)
  @ArrayType(elementIds={0})
  static @interface Array2d1 {
  }

  @ArrayProperty(use=Use.OPTIONAL, type=Array2d1.class)
  private List<Object> array2d1;

  public List<Object> getArray2d1() {
    return this.array2d1;
  }

  public TestArray setArray2d1(final List<Object> array2d1) {
    this.array2d1 = array2d1;
    return this;
  }

  @NumberElement(id=9, form=Form.INTEGER, range="[0,5]", minOccurs=0, maxOccurs=1)
  @NumberElement(id=8, form=Form.INTEGER, range="[5,10]", minOccurs=0, maxOccurs=1)
  @NumberElement(id=7, minOccurs=1, maxOccurs=2, range="[0,10]", nullable=true)
  @NumberElement(id=6, minOccurs=0, maxOccurs=2, form=Form.INTEGER, range="[0,4]")
  @StringElement(id=5, minOccurs=0, maxOccurs=2, pattern="[A-Z0-9]+", nullable=true)
  @StringElement(id=4, minOccurs=2, maxOccurs=3, pattern="[A-Z]+", nullable=true)
  @BooleanElement(id=3, minOccurs=0, maxOccurs=3, nullable=true)
  @ArrayElement(id=2, elementIds={3, 4, 5, 6, 7}, minOccurs=0)
  @BooleanElement(id=1, minOccurs=0, nullable=true)
  @ArrayElement(id=0, type=Array1d2.class, minOccurs=0)
  @ArrayType(elementIds={0, 1, 2, 8, 9})
  static @interface Array2d2 {
  }

  @ArrayProperty(use=Use.OPTIONAL, type=Array2d2.class)
  private List<Object> array2d2;

  public List<Object> getArray2d2() {
    return this.array2d2;
  }

  public TestArray setArray2d2(final List<Object> array2d2) {
    this.array2d2 = array2d2;
    return this;
  }

  @ArrayElement(id=1, type=Array2d2.class)
  @ArrayElement(id=0, type=Array1d2.class, minOccurs=0)
  @ArrayType(elementIds={0, 1})
  static @interface Array3d {
  }

  @ArrayProperty(use=Use.OPTIONAL, type=Array3d.class)
  private List<Object> array3d;

  public List<Object> getArray3d() {
    return this.array3d;
  }

  public TestArray setArray3d(final List<Object> array3d) {
    this.array3d = array3d;
    return this;
  }

  @Override
  public String toString() {
    return new JxEncoder(2).toString(this);
  }
}