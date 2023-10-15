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

import org.jsonx.ObjectProperty;

public class Employee extends Individual {
  private Individual emergencyContact;

  @ObjectProperty(name = "emergencyContact")
  public Individual getEmergencyContact() {
    return this.emergencyContact;
  }

  public void setEmergencyContact(final Individual emergencyContact) {
    this.emergencyContact = emergencyContact;
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj == this)
      return true;

    if (!(obj instanceof Employee))
      return false;

    final Employee that = (Employee)obj;
    if (emergencyContact != null ? !emergencyContact.equals(that.emergencyContact) : that.emergencyContact != null)
      return false;

    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    int hashCode = super.hashCode();
    if (emergencyContact != null)
      hashCode = 31 * hashCode + emergencyContact.hashCode();

    return hashCode;
  }
}