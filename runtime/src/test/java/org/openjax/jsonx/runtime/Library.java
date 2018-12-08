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

import java.util.List;
import java.util.Optional;

public class Library implements JxObject {
  public static class Journal extends Publication {
    @StringProperty
    private Optional<String> subject;

    public Optional<String> getSubject() {
      return this.subject;
    }

    public void setSubject(final Optional<String> subject) {
      this.subject = subject;
    }

    @BooleanProperty(use=Use.OPTIONAL)
    private Optional<Boolean> openAccess;

    public Optional<Boolean> getOpenAccess() {
      return this.openAccess;
    }

    public void setOpenAccess(final Optional<Boolean> openAccess) {
      this.openAccess = openAccess;
    }
  }

  @ObjectProperty(use=Use.OPTIONAL)
  private Optional<Address> address;

  public Optional<Address> getAddress() {
    return this.address;
  }

  public void setAddress(final Optional<Address> address) {
    this.address = address;
  }

  @BooleanProperty(name="handicap", use=Use.OPTIONAL, nullable=false)
  private Boolean _handicap;

  public Boolean getHandicap() {
    return this._handicap;
  }

  public void setHandicap(final Boolean handicap) {
    this._handicap = handicap;
  }

  @StringElement(id=2, pattern="\\d{2}:\\d{2}", nullable=false, minOccurs=2, maxOccurs=2)
  @ArrayElement(id=1, nullable=false, elementIds=2, minOccurs=7, maxOccurs=7)
  @ArrayProperty(elementIds=1)
  private List<List<String>> schedule;

  public List<List<String>> getSchedule() {
    return this.schedule;
  }

  public void setSchedule(final List<List<String>> schedule) {
    this.schedule = schedule;
  }

  @ObjectElement(id=1, type=Book.class, nullable=false)
  @ArrayProperty(elementIds=1)
  private List<Book> books;

  public List<Book> getBooks() {
    return this.books;
  }

  public void setBooks(final List<Book> books) {
    this.books = books;
  }

  @ObjectElement(id=1, type=OnlineArticle.class, nullable=false)
  @ArrayProperty(elementIds=1)
  private List<OnlineArticle> articles;

  public List<OnlineArticle> getArticles() {
    return this.articles;
  }

  public void setArticles(final List<OnlineArticle> articles) {
    this.articles = articles;
  }

  @ObjectElement(id=1, type=Journal.class, nullable=false)
  @ArrayProperty(elementIds=1)
  private List<Journal> journals;

  public List<Journal> getJournals() {
    return this.journals;
  }

  public void setJournals(final List<Journal> journals) {
    this.journals = journals;
  }

  @ObjectElement(id=0, type=Employee.class)
  @ArrayType(elementIds={0})
  public static @interface Staff {
  }

  @ArrayProperty(type=Staff.class)
  private List<Employee> staff;

  public List<Employee> getStaff() {
    return this.staff;
  }

  public void setStaff(final List<Employee> staff) {
    this.staff = staff;
  }

  @Override
  public String toString() {
    return new JxEncoder(2).marshal(this);
  }
}