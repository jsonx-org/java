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

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Invalid {
  public static class NoProperty implements JxObject {
    private Boolean noProperty;

    public Boolean getNoProperty() {
      return this.noProperty;
    }

    public void setNoProperty(final Boolean noProperty) {
      this.noProperty = noProperty;
    }
  }

  public static class InvalidName implements JxObject {
    private Boolean invalidName;

    @BooleanProperty(name = "foo")
    public Boolean getInvalidName() {
      return this.invalidName;
    }

    public void setInvalidName(final Boolean invalidName) {
      this.invalidName = invalidName;
    }
  }

  public static class InvalidType implements JxObject {
    private Boolean invalidType;

    @BooleanProperty(name = "invalidType", use = Use.OPTIONAL)
    public Boolean getInvalidType() {
      return this.invalidType;
    }

    public void setInvalidType(final Boolean invalidType) {
      this.invalidType = invalidType;
    }
  }

  public static class Bool implements JxObject {
    private Number invalidType;

    @BooleanProperty(name = "invalidType", use = Use.OPTIONAL, nullable = false)
    public Number getInvalidType() {
      return this.invalidType;
    }

    public void setInvalidType(final Number invalidType) {
      this.invalidType = invalidType;
    }

    private Boolean invalidAnnotation;

    @NumberProperty(name = "invalidAnnotation", use = Use.OPTIONAL, nullable = false)
    public Boolean getInvalidAnnotation() {
      return this.invalidAnnotation;
    }

    public void setInvalidAnnotation(final Boolean invalidAnnotation) {
      this.invalidAnnotation = invalidAnnotation;
    }
  }

  public static class NumRange implements JxObject {
    private Optional<Byte> invalidRange;

    @NumberProperty(name = "invalidRange", use = Use.OPTIONAL, scale = 0, range = "[-3,")
    public Optional<Byte> getInvalidRange() {
      return this.invalidRange;
    }

    public void setInvalidRange(final Optional<Byte> invalidRange) {
      this.invalidRange = invalidRange;
    }
  }

  public static class NumPrimitiveUse implements JxObject {
    private byte number;

    @NumberProperty(name = "number", use = Use.OPTIONAL, nullable = false)
    public byte getNumber() {
      return this.number;
    }

    public void setNumber(final byte number) {
      this.number = number;
    }
  }

  public static class NumPrimitiveNullable implements JxObject {
    private int number;

    @NumberProperty(name = "number")
    public int getNumber() {
      return this.number;
    }

    public void setNumber(final int number) {
      this.number = number;
    }
  }

  public static class NumPrimitiveScale implements JxObject {
    private long number;

    @NumberProperty(name = "number", nullable = false, scale = 1)
    public long getNumber() {
      return this.number;
    }

    public void setNumber(final long number) {
      this.number = number;
    }
  }

  public static class Num implements JxObject {
    private Boolean invalidType;

    @NumberProperty(name = "invalidType", use = Use.OPTIONAL, nullable = false)
    public Boolean getInvalidType() {
      return this.invalidType;
    }

    public void setInvalidType(final Boolean invalidType) {
      this.invalidType = invalidType;
    }

    private Number invalidAnnotation;

    @StringProperty(name = "invalidAnnotation", use = Use.OPTIONAL, nullable = false)
    public Number getInvalidAnnotation() {
      return this.invalidAnnotation;
    }

    public void setInvalidAnnotation(final Number invalidAnnotation) {
      this.invalidAnnotation = invalidAnnotation;
    }

    private Optional<BigDecimal> invalidScale;

    @NumberProperty(name = "invalidScale", use = Use.OPTIONAL, scale = -1)
    public Optional<BigDecimal> getInvalidScale() {
      return this.invalidScale;
    }

    public void setInvalidScale(final Optional<BigDecimal> invalidScale) {
      this.invalidScale = invalidScale;
    }
  }

  public static class Str implements JxObject {
    private Optional<Number> invalidType;

    @StringProperty(name = "invalidType", use = Use.OPTIONAL)
    public Optional<Number> getInvalidType() {
      return this.invalidType;
    }

    public void setInvalidType(final Optional<Number> invalidType) {
      this.invalidType = invalidType;
    }

    private Optional<String> invalidAnnotation;

    @BooleanProperty(name = "invalidAnnotation", use = Use.OPTIONAL)
    public Optional<String> getInvalidAnnotation() {
      return this.invalidAnnotation;
    }

    public void setInvalidAnnotation(final Optional<String> invalidAnnotation) {
      this.invalidAnnotation = invalidAnnotation;
    }

    private Optional<String> invalidPattern;

    @StringProperty(name = "invalidPattern", use = Use.OPTIONAL, pattern = "[0-9]{{2,4}")
    public Optional<String> getInvalidPattern() {
      return this.invalidPattern;
    }

    public void setInvalidPattern(final Optional<String> invalidPattern) {
      this.invalidPattern = invalidPattern;
    }
  }

  public static class ArrAnnotationType implements JxObject {
    private Optional<List<?>> invalidAnnotationType;

    @ArrayProperty(name = "invalidAnnotationType", use = Use.OPTIONAL, type = Override.class)
    public Optional<List<?>> getInvalidAnnotationType() {
      return this.invalidAnnotationType;
    }

    public void setInvalidAnnotationType(final Optional<List<?>> invalidAnnotationType) {
      this.invalidAnnotationType = invalidAnnotationType;
    }
  }

  public static class Arr implements JxObject {
    private Optional<Number> invalidType;

    @StringElement(id = 0)
    @ArrayProperty(name = "invalidType", use = Use.OPTIONAL, elementIds = {0})
    public Optional<Number> getInvalidType() {
      return this.invalidType;
    }

    public void setInvalidType(final Optional<Number> invalidType) {
      this.invalidType = invalidType;
    }

    private List<?> invalidAnnotation;

    @BooleanProperty(name = "invalidAnnotation", use = Use.OPTIONAL, nullable = false)
    public List<?> getInvalidAnnotation() {
      return this.invalidAnnotation;
    }

    public void setInvalidAnnotation(final List<?> invalidAnnotation) {
      this.invalidAnnotation = invalidAnnotation;
    }
  }
}