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

import java.math.BigDecimal;
import java.util.List;

public class Invalid {
  public static class InvalidName {
    @BooleanProperty(use=Use.OPTIONAL, name="foo")
    private Boolean invalidName;

    public Boolean getInvalidName() {
      return this.invalidName;
    }

    public void setInvalidName(final Boolean invalidName) {
      this.invalidName = invalidName;
    }
  }

  public static class Bool {
    @BooleanProperty(use=Use.OPTIONAL)
    private Number invalidType;

    public Number getInvalidType() {
      return this.invalidType;
    }

    public void setInvalidType(final Number invalidType) {
      this.invalidType = invalidType;
    }

    @NumberProperty(use=Use.OPTIONAL)
    private Boolean invalidAnnotation;

    public Boolean getInvalidAnnotation() {
      return this.invalidAnnotation;
    }

    public void setInvalidAnnotation(final Boolean invalidAnnotation) {
      this.invalidAnnotation = invalidAnnotation;
    }
  }

  public static class NumRange {
    @NumberProperty(use=Use.OPTIONAL, form=Form.INTEGER, range="[-3,")
    private Byte invalidRange;

    public Byte getInvalidRange() {
      return this.invalidRange;
    }

    public void setInvalidRange(final Byte invalidRange) {
      this.invalidRange = invalidRange;
    }
  }

  public static class Num {
    @NumberProperty(use=Use.OPTIONAL)
    private Boolean invalidType;

    public Boolean getInvalidType() {
      return this.invalidType;
    }

    public void setInvalidType(final Boolean invalidType) {
      this.invalidType = invalidType;
    }

    @StringProperty(use=Use.OPTIONAL)
    private Number invalidAnnotation;

    public Number getInvalidAnnotation() {
      return this.invalidAnnotation;
    }

    public void setInvalidAnnotation(final Number invalidAnnotation) {
      this.invalidAnnotation = invalidAnnotation;
    }

    @NumberProperty(use=Use.OPTIONAL, form=Form.INTEGER)
    private BigDecimal invalidForm;

    public BigDecimal getInvalidForm() {
      return this.invalidForm;
    }

    public void setInvalidForm(final BigDecimal invalidForm) {
      this.invalidForm = invalidForm;
    }
  }

  public static class Str {
    @StringProperty(use=Use.OPTIONAL)
    private Number invalidType;

    public Number getInvalidType() {
      return this.invalidType;
    }

    public void setInvalidType(final Number invalidType) {
      this.invalidType = invalidType;
    }

    @BooleanProperty(use=Use.OPTIONAL)
    private String invalidAnnotation;

    public String getInvalidAnnotation() {
      return this.invalidAnnotation;
    }

    public void setInvalidAnnotation(final String invalidAnnotation) {
      this.invalidAnnotation = invalidAnnotation;
    }

    @StringProperty(use=Use.OPTIONAL, pattern="[0-9]{{2,4}")
    private String invalidPattern;

    public String getInvalidPattern() {
      return this.invalidPattern;
    }

    public void setInvalidPattern(final String invalidPattern) {
      this.invalidPattern = invalidPattern;
    }
  }

  public static class Arr {
    @ArrayProperty(use=Use.OPTIONAL)
    private Number invalidType;

    public Number getInvalidType() {
      return this.invalidType;
    }

    public void setInvalidType(final Number invalidType) {
      this.invalidType = invalidType;
    }

    @BooleanProperty(use=Use.OPTIONAL)
    private List<?> invalidAnnotation;

    public List<?> getInvalidAnnotation() {
      return this.invalidAnnotation;
    }

    public void setInvalidAnnotation(final List<?> invalidAnnotation) {
      this.invalidAnnotation = invalidAnnotation;
    }

    @ArrayProperty(use=Use.OPTIONAL, type=Override.class)
    private List<?> invalidAnnotationType;

    public List<?> getInvalidAnnotationType() {
      return this.invalidAnnotationType;
    }

    public void setInvalidAnnotationType(final List<?> invalidAnnotationType) {
      this.invalidAnnotationType = invalidAnnotationType;
    }
  }
}