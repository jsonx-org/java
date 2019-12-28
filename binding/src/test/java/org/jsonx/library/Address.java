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

public class Address implements JxObject {
  @NumberProperty(scale=0, range="[0,]")
  private BigInteger number;

  public BigInteger getNumber() {
    return this.number;
  }

  public void setNumber(BigInteger number) {
    this.number = number;
  }

  @StringProperty(pattern="(\\S)|(\\S.*\\S)")
  private String street;

  public String getStreet() {
    return this.street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  @StringProperty(pattern="(\\S)|(\\S.*\\S)")
  private String city;

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @StringProperty(pattern="(\\S)|(\\S.*\\S)")
  private String postalCode;

  public String getPostalCode() {
    return this.postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  @StringProperty(pattern="(\\S)|(\\S.*\\S)")
  private String locality;

  public String getLocality() {
    return this.locality;
  }

  public void setLocality(String locality) {
    this.locality = locality;
  }

  @StringProperty(pattern="(\\S)|(\\S.*\\S)")
  private String country;

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
    hashCode = 31 * hashCode + (number == null ? 0 : number.hashCode());
    hashCode = 31 * hashCode + (street == null ? 0 : street.hashCode());
    hashCode = 31 * hashCode + (city == null ? 0 : city.hashCode());
    hashCode = 31 * hashCode + (postalCode == null ? 0 : postalCode.hashCode());
    hashCode = 31 * hashCode + (locality == null ? 0 : locality.hashCode());
    hashCode = 31 * hashCode + (country == null ? 0 : country.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return JxEncoder._2.marshal(this);
  }
}