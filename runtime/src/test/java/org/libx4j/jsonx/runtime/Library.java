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

import java.util.List;

@JsonxObject
public class Library {
  @JsonxObject
  public static class Journal extends Publication {
    @StringProperty(use=Use.REQUIRED)
    private String subject;

    public String getSubject() {
      return this.subject;
    }

    public void setSubject(final String subject) {
      this.subject = subject;
    }

    @BooleanProperty(use=Use.REQUIRED)
    private Boolean openAccess;

    public Boolean getOpenAccess() {
      return this.openAccess;
    }

    public void setOpenAccess(final Boolean openAccess) {
      this.openAccess = openAccess;
    }
  }

  @JsonxObject
  public static class StreetSheet extends Publication {
  }

  @ObjectProperty(use=Use.REQUIRED)
  private Address address;

  public Address getAddress() {
    return this.address;
  }

  public void setAddress(final Address address) {
    this.address = address;
  }

  @BooleanProperty(name="handicap", use=Use.REQUIRED)
  private Boolean _handicap;

  public Boolean getHandicap() {
    return this._handicap;
  }

  public void setHandicap(final Boolean handicap) {
    this._handicap = handicap;
  }

  @StringElement(id=2, pattern="\\d{2}:\\d{2}", nullable=false, minOccurs=2, maxOccurs=2)
  @ArrayElement(id=1, nullable=false, elementIds=2, minOccurs=7, maxOccurs=7)
  @ArrayProperty(use=Use.REQUIRED, elementIds=1)
  private List<List<String>> schedule;

  public List<List<String>> getSchedule() {
    return this.schedule;
  }

  public void setSchedule(final List<List<String>> schedule) {
    this.schedule = schedule;
  }

  @ObjectElement(id=1, type=Publication.class, nullable=false)
  @ArrayProperty(use=Use.REQUIRED, elementIds=1)
  private List<Publication> publications;

  public List<Publication> getPublications() {
    return this.publications;
  }

  public void setPublications(final List<Publication> publications) {
    this.publications = publications;
  }

  @ObjectElement(id=1, type=StreetSheet.class, nullable=false)
  @ArrayProperty(use=Use.REQUIRED, elementIds=1)
  private List<StreetSheet> streetSheets;

  public List<StreetSheet> getStreetSheets() {
    return this.streetSheets;
  }

  public void setStreetSheets(final List<StreetSheet> streetSheets) {
    this.streetSheets = streetSheets;
  }
}