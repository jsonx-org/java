/* Copyright (c) 2017 JSONx
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

package org.jsonx.library;

import java.math.BigInteger;

import org.jsonx.JxEncoder;
import org.jsonx.JxObject;
import org.jsonx.NumberProperty;
import org.jsonx.StringProperty;
import org.jsonx.Use;

public class Address implements JxObject {
  private BigInteger number;

  @NumberProperty(name = "number", scale = 0, range = "[0,]")
  public BigInteger getNumber() {
    return this.number;
  }

  public void setNumber(BigInteger number) {
    this.number = number;
  }

  private String street;

  @StringProperty(name = "street", pattern = "\\S|\\S.*\\S", nullable = false)
  public String getStreet() {
    return this.street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  private String city;

  @StringProperty(name = "city", pattern = "(\\S)|(\\S.*\\S)", nullable = false)
  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  private String postalCode;

  @StringProperty(name = "postalCode", pattern = "(\\S)|(\\S.*\\S)")
  public String getPostalCode() {
    return this.postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  private String locality;

  @StringProperty(name = "locality", pattern = "(\\S)|(\\S.*\\S)")
  public String getLocality() {
    return this.locality;
  }

  public void setLocality(String locality) {
    this.locality = locality;
  }

  private String country;

  @StringProperty(name = "country", pattern = "(\\S)|(\\S.*\\S)", nullable = false, use = Use.OPTIONAL)
  public String getCountry() {
    return this.country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this)
      return true;

    if (!(obj instanceof Address))
      return false;

    final Address that = (Address)obj;
    if (number != null ? !number.equals(that.number) : that.number != null)
      return false;

    if (street != null ? !street.equals(that.street) : that.street != null)
      return false;

    if (city != null ? !city.equals(that.city) : that.city != null)
      return false;

    if (postalCode != null ? !postalCode.equals(that.postalCode) : that.postalCode != null)
      return false;

    if (locality != null ? !locality.equals(that.locality) : that.locality != null)
      return false;

    if (country != null ? !country.equals(that.country) : that.country != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;
    if (number != null)
      hashCode = 31 * hashCode + number.hashCode();

    if (street != null)
      hashCode = 31 * hashCode + street.hashCode();

    if (city != null)
      hashCode = 31 * hashCode + city.hashCode();

    if (postalCode != null)
      hashCode = 31 * hashCode + postalCode.hashCode();

    if (locality != null)
      hashCode = 31 * hashCode + locality.hashCode();

    if (country != null)
      hashCode = 31 * hashCode + country.hashCode();

    return hashCode;
  }

  @Override
  public String toString() {
    return JxEncoder.VALIDATING._2.toString(this);
  }
}