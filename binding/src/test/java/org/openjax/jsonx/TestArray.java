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
import java.util.Optional;

public class TestArray implements JxObject {
  @AnyElement(id=2, types={@t(strings=".*")}, minOccurs=0, nullable=false)
  @AnyElement(id=1, types={@t(numbers=@NumberType)}, minOccurs=0, nullable=false)
  @AnyElement(id=0, types={@t(booleans=true)}, minOccurs=0, nullable=false)
  @ArrayType(elementIds={0, 1, 2}, minIterate=0, maxIterate=Integer.MAX_VALUE)
  @interface ArrayAny {
  }

  @ArrayProperty(type=ArrayAny.class, use=Use.OPTIONAL)
  private Optional<List<Boolean>> arrayAny;

  public Optional<List<Boolean>> getArrayAny() {
    return this.arrayAny;
  }

  public TestArray setArrayAny(final Optional<List<Boolean>> arrayAny) {
    this.arrayAny = arrayAny;
    return this;
  }

  public TestArray setArrayAny(final List<Boolean> arrayAny) {
    this.arrayAny = Optional.ofNullable(arrayAny);
    return this;
  }

  @StringElement(id=2, minOccurs=0, nullable=false)
  @NumberElement(id=1, minOccurs=0, nullable=false)
  @BooleanElement(id=0, minOccurs=0, nullable=false)
  @ArrayType(elementIds={0, 1, 2}, minIterate=0, maxIterate=Integer.MAX_VALUE)
  @interface ArrayLoop {
  }

  @ArrayProperty(type=ArrayLoop.class, use=Use.OPTIONAL)
  private Optional<List<Boolean>> arrayLoop;

  public Optional<List<Boolean>> getArrayLoop() {
    return this.arrayLoop;
  }

  public TestArray setArrayLoop(final Optional<List<Boolean>> arrayLoop) {
    this.arrayLoop = arrayLoop;
    return this;
  }

  public TestArray setArrayLoop(final List<Boolean> arrayLoop) {
    this.arrayLoop = Optional.ofNullable(arrayLoop);
    return this;
  }

  @BooleanElement(id=0, maxOccurs=2)
  @ArrayType(elementIds={0}, minIterate=0, maxIterate=2)
  @interface Array1d1 {
  }

  @ArrayProperty(type=Array1d1.class, use=Use.OPTIONAL)
  private Optional<List<Boolean>> array1d1;

  public Optional<List<Boolean>> getArray1d1() {
    return this.array1d1;
  }

  public TestArray setArray1d1(final Optional<List<Boolean>> array1d1) {
    this.array1d1 = array1d1;
    return this;
  }

  public TestArray setArray1d1(final List<Boolean> array1d1) {
    this.array1d1 = Optional.ofNullable(array1d1);
    return this;
  }

  @BooleanElement(id=1, maxOccurs=1, nullable=false)
  @BooleanElement(id=0, minOccurs=0, maxOccurs=2)
  @ArrayType(elementIds={0, 1}, minIterate=3, maxIterate=Integer.MAX_VALUE)
  @interface Array1d2 {
  }

  @ArrayProperty(type=Array1d2.class, use=Use.OPTIONAL)
  private Optional<List<Boolean>> array1d2;

  public Optional<List<Boolean>> getArray1d2() {
    return this.array1d2;
  }

  public TestArray setArray1d2(final Optional<List<Boolean>> array1d2) {
    this.array1d2 = array1d2;
    return this;
  }

  public TestArray setArray1d2(final List<Boolean> array1d2) {
    this.array1d2 = Optional.ofNullable(array1d2);
    return this;
  }

  @NumberElement(id=4, minOccurs=1, maxOccurs=2, range="[0,10]")
  @NumberElement(id=3, minOccurs=0, maxOccurs=2, form=Form.INTEGER, range="[0,4]", nullable=false)
  @StringElement(id=2, minOccurs=0, maxOccurs=2, pattern="[a-z0-9]+")
  @StringElement(id=1, minOccurs=2, maxOccurs=3, pattern="[a-z]+")
  @BooleanElement(id=0, minOccurs=0, maxOccurs=3)
  @ArrayType(elementIds={0, 1, 2, 3, 4}, minIterate=0, maxIterate=Integer.MAX_VALUE)
  @interface Array1d3 {
  }

  @ArrayProperty(type=Array1d3.class, use=Use.OPTIONAL)
  private Optional<List<Object>> array1d3;

  public Optional<List<Object>> getArray1d3() {
    return this.array1d3;
  }

  public TestArray setArray1d3(final Optional<List<Object>> array1d3) {
    this.array1d3 = array1d3;
    return this;
  }

  public TestArray setArray1d3(final List<Object> array1d3) {
    this.array1d3 = Optional.ofNullable(array1d3);
    return this;
  }

  @ArrayElement(id=0, type=Array1d3.class, nullable=false, maxOccurs=1)
  @ArrayType(elementIds={0}, minIterate=0, maxIterate=2)
  @interface Array2d1 {
  }

  @ArrayProperty(type=Array2d1.class, use=Use.OPTIONAL)
  private Optional<List<Object>> array2d1;

  public Optional<List<Object>> getArray2d1() {
    return this.array2d1;
  }

  public TestArray setArray2d1(final Optional<List<Object>> array2d1) {
    this.array2d1 = array2d1;
    return this;
  }

  public TestArray setArray2d1(final List<Object> array2d1) {
    this.array2d1 = Optional.ofNullable(array2d1);
    return this;
  }

  @NumberElement(id=9, form=Form.INTEGER, range="[0,5]", minOccurs=0, maxOccurs=1, nullable=false)
  @NumberElement(id=8, form=Form.INTEGER, range="[5,10]", minOccurs=0, maxOccurs=1, nullable=false)
  @NumberElement(id=7, minOccurs=1, maxOccurs=2, range="[0,10]")
  @NumberElement(id=6, minOccurs=0, maxOccurs=2, form=Form.INTEGER, range="[0,4]", nullable=false)
  @StringElement(id=5, minOccurs=0, maxOccurs=2, pattern="[A-Z0-9]+")
  @StringElement(id=4, minOccurs=2, maxOccurs=3, pattern="[A-Z]+")
  @BooleanElement(id=3, minOccurs=0, maxOccurs=3)
  @ArrayElement(id=2, elementIds={3, 4, 5, 6, 7}, minOccurs=0, maxIterate=2, nullable=false)
  @BooleanElement(id=1, minOccurs=0)
  @ArrayElement(id=0, type=Array1d3.class, minOccurs=0, nullable=false)
  @ArrayType(elementIds={0, 1, 2, 8, 9})
  @interface Array2d2 {
  }

  @ArrayProperty(type=Array2d2.class, use=Use.OPTIONAL)
  private Optional<List<Object>> array2d2;

  public Optional<List<Object>> getArray2d2() {
    return this.array2d2;
  }

  public TestArray setArray2d2(final Optional<List<Object>> array2d2) {
    this.array2d2 = array2d2;
    return this;
  }

  public TestArray setArray2d2(final List<Object> array2d2) {
    this.array2d2 = Optional.ofNullable(array2d2);
    return this;
  }

  @ArrayElement(id=1, type=Array2d2.class, nullable=false)
  @ArrayElement(id=0, type=Array1d3.class, minOccurs=0, nullable=false)
  @ArrayType(elementIds={0, 1})
  @interface Array3d {
  }

  @ArrayProperty(type=Array3d.class, use=Use.OPTIONAL)
  private Optional<List<Object>> array3d;

  public Optional<List<Object>> getArray3d() {
    return this.array3d;
  }

  public TestArray setArray3d(final Optional<List<Object>> array3d) {
    this.array3d = array3d;
    return this;
  }

  public TestArray setArray3d(final List<Object> array3d) {
    this.array3d = Optional.ofNullable(array3d);
    return this;
  }

  @Override
  public String toString() {
    return JxEncoder._2.marshal(this);
  }
}