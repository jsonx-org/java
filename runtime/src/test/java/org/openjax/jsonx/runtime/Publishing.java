/* Copyright (c) 2017 OpenJAX
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

import java.math.BigInteger;

@ObjectType
public class Publishing {
  @NumberProperty(form=Form.INTEGER, use=Use.REQUIRED)
  private BigInteger year;

  public BigInteger getYear() {
    return this.year;
  }

  public void setYear(final BigInteger year) {
    this.year = year;
  }

  @StringProperty(pattern="\\S+ \\S+", use=Use.REQUIRED)
  private String publisher;

  public String getPublisher() {
    return this.publisher;
  }

  public void setPublisher(final String publisher) {
    this.publisher = publisher;
  }
}