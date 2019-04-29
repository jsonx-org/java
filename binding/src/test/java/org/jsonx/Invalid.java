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

package org.jsonx;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class Invalid {
  public static class InvalidName implements JxObject {
    @BooleanProperty(name="foo")
    private Boolean invalidName;

    public Boolean getInvalidName() {
      return this.invalidName;
    }

    public void setInvalidName(final Boolean invalidName) {
      this.invalidName = invalidName;
    }
  }

  public static class InvalidType implements JxObject {
    @BooleanProperty(use=Use.OPTIONAL)
    private Boolean invalidType;

    public Boolean getInvalidType() {
      return this.invalidType;
    }

    public void setInvalidType(final Boolean invalidType) {
      this.invalidType = invalidType;
    }
  }

  public static class Bool implements JxObject {
    @BooleanProperty(use=Use.OPTIONAL, nullable=false)
    private Number invalidType;

    public Number getInvalidType() {
      return this.invalidType;
    }

    public void setInvalidType(final Number invalidType) {
      this.invalidType = invalidType;
    }

    @NumberProperty(use=Use.OPTIONAL, nullable=false)
    private Boolean invalidAnnotation;

    public Boolean getInvalidAnnotation() {
      return this.invalidAnnotation;
    }

    public void setInvalidAnnotation(final Boolean invalidAnnotation) {
      this.invalidAnnotation = invalidAnnotation;
    }
  }

  public static class NumRange implements JxObject {
    @NumberProperty(use=Use.OPTIONAL, form=Form.INTEGER, range="[-3,")
    private Optional<Byte> invalidRange;

    public Optional<Byte> getInvalidRange() {
      return this.invalidRange;
    }

    public void setInvalidRange(final Optional<Byte> invalidRange) {
      this.invalidRange = invalidRange;
    }
  }

  public static class Num implements JxObject {
    @NumberProperty(use=Use.OPTIONAL, nullable=false)
    private Boolean invalidType;

    public Boolean getInvalidType() {
      return this.invalidType;
    }

    public void setInvalidType(final Boolean invalidType) {
      this.invalidType = invalidType;
    }

    @StringProperty(use=Use.OPTIONAL, nullable=false)
    private Number invalidAnnotation;

    public Number getInvalidAnnotation() {
      return this.invalidAnnotation;
    }

    public void setInvalidAnnotation(final Number invalidAnnotation) {
      this.invalidAnnotation = invalidAnnotation;
    }

    @NumberProperty(use=Use.OPTIONAL, form=Form.INTEGER)
    private Optional<BigDecimal> invalidForm;

    public Optional<BigDecimal> getInvalidForm() {
      return this.invalidForm;
    }

    public void setInvalidForm(final Optional<BigDecimal> invalidForm) {
      this.invalidForm = invalidForm;
    }
  }

  public static class Str implements JxObject {
    @StringProperty(use=Use.OPTIONAL)
    private Optional<Number> invalidType;

    public Optional<Number> getInvalidType() {
      return this.invalidType;
    }

    public void setInvalidType(final Optional<Number> invalidType) {
      this.invalidType = invalidType;
    }

    @BooleanProperty(use=Use.OPTIONAL)
    private Optional<String> invalidAnnotation;

    public Optional<String> getInvalidAnnotation() {
      return this.invalidAnnotation;
    }

    public void setInvalidAnnotation(final Optional<String> invalidAnnotation) {
      this.invalidAnnotation = invalidAnnotation;
    }

    @StringProperty(use=Use.OPTIONAL, pattern="[0-9]{{2,4}")
    private Optional<String> invalidPattern;

    public Optional<String> getInvalidPattern() {
      return this.invalidPattern;
    }

    public void setInvalidPattern(final Optional<String> invalidPattern) {
      this.invalidPattern = invalidPattern;
    }
  }

  public static class ArrAnnotationType implements JxObject {
    @ArrayProperty(use=Use.OPTIONAL, type=Override.class)
    private Optional<List<?>> invalidAnnotationType;

    public Optional<List<?>> getInvalidAnnotationType() {
      return this.invalidAnnotationType;
    }

    public void setInvalidAnnotationType(final Optional<List<?>> invalidAnnotationType) {
      this.invalidAnnotationType = invalidAnnotationType;
    }
  }

  public static class Arr implements JxObject {
    @StringElement(id=0)
    @ArrayProperty(use=Use.OPTIONAL, elementIds={0})
    private Optional<Number> invalidType;

    public Optional<Number> getInvalidType() {
      return this.invalidType;
    }

    public void setInvalidType(final Optional<Number> invalidType) {
      this.invalidType = invalidType;
    }

    @BooleanProperty(use=Use.OPTIONAL, nullable=false)
    private List<?> invalidAnnotation;

    public List<?> getInvalidAnnotation() {
      return this.invalidAnnotation;
    }

    public void setInvalidAnnotation(final List<?> invalidAnnotation) {
      this.invalidAnnotation = invalidAnnotation;
    }
  }
}