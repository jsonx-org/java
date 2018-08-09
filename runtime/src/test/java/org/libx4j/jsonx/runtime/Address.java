/* Copyright (c) 2017 lib4j
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

package org.libx4j.jsonx.runtime;

import org.libx4j.jsonx.runtime.Form;
import org.libx4j.jsonx.runtime.JsonxObject;
import org.libx4j.jsonx.runtime.NumberProperty;
import org.libx4j.jsonx.runtime.StringProperty;

@JsonxObject
public class Address {
  @NumberProperty(form=Form.INTEGER, range="[0,]", use=Use.REQUIRED)
  private Integer number;

  public Integer getNumber() {
    return this.number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  @StringProperty(pattern="(\\S)|(\\S.*\\S)", urlEncode=true, urlDecode=true, use=Use.REQUIRED)
  private String street;

  public String getStreet() {
    return this.street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  @StringProperty(pattern="(\\S)|(\\S.*\\S)", urlEncode=true, urlDecode=true, use=Use.REQUIRED)
  private String city;

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @StringProperty(pattern="(\\S)|(\\S.*\\S)", urlEncode=true, urlDecode=true, use=Use.REQUIRED)
  private String postalCode;

  public String getPostalCode() {
    return this.postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  @StringProperty(pattern="(\\S)|(\\S.*\\S)", urlEncode=true, urlDecode=true, use=Use.REQUIRED)
  private String locality;

  public String getLocality() {
    return this.locality;
  }

  public void setLocality(String locality) {
    this.locality = locality;
  }

  @StringProperty(pattern="(\\S)|(\\S.*\\S)", urlEncode=true, urlDecode=true, use=Use.REQUIRED)
  private String country;

  public String getCountry() {
    return this.country;
  }

  public void setCountry(String country) {
    this.country = country;
  }
}