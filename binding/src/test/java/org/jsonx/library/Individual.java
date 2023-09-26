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

package org.jsonx.library;

import org.jsonx.JxEncoder;
import org.jsonx.JxObject;
import org.jsonx.ObjectProperty;
import org.jsonx.StringProperty;

public class Individual implements JxObject {
  private String name;

  @StringProperty(name = "name", pattern = "\\S+ \\S+")
  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  private String gender;

  @StringProperty(name = "gender", pattern = "[MF]")
  public String getGender() {
    return this.gender;
  }

  public void setGender(final String gender) {
    this.gender = gender;
  }

  private Address address;

  @ObjectProperty(name = "address")
  public Address getAddress() {
    return this.address;
  }

  public void setAddress(final Address address) {
    this.address = address;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this)
      return true;

    if (!(obj instanceof Individual))
      return false;

    final Individual that = (Individual)obj;
    if (name != null ? !name.equals(that.name) : that.name != null)
      return false;

    if (address != null ? !address.equals(that.address) : that.address != null)
      return false;

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = super.hashCode();
    if (name != null)
      hashCode = 31 * hashCode + name.hashCode();

    if (address != null)
      hashCode = 31 * hashCode + address.hashCode();

    return hashCode;
  }

  @Override
  public String toString() {
    return JxEncoder.VALIDATING._2.toString(this);
  }
}