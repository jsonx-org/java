package org.libx4j.jsonx.runtime;

import java.math.BigDecimal;
import java.math.BigInteger;

public enum Form {
  INTEGER(BigInteger.class),
  REAL(BigDecimal.class);

  public Class<?> type;

  Form(final Class<?> type) {
    this.type = type;
  }
}