/* Copyright (c) 2018 lib4j
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

@ObjectType
public class Individual {
  @StringProperty(pattern="\\S+ \\S+")
  private String name;

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @StringProperty(pattern="[MF]")
  private String gender;

  public String getGender() {
    return this.gender;
  }

  public void setGender(final String gender) {
    this.gender = gender;
  }

  @ObjectProperty
  private Address address;

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
    int hashCode = 0;
    if (name != null)
      hashCode = hashCode * 31 + name.hashCode();

    if (address != null)
      hashCode = hashCode * 31 + address.hashCode();

    return hashCode;
  }

  @Override
  public String toString() {
    return new JxEncoder(2).toString(this);
  }
}