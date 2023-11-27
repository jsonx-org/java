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

import java.util.List;
import java.util.Optional;

import org.jsonx.ArrayElement;
import org.jsonx.ArrayProperty;
import org.jsonx.ArrayType;
import org.jsonx.BooleanProperty;
import org.jsonx.JxEncoder;
import org.jsonx.JxObject;
import org.jsonx.ObjectElement;
import org.jsonx.ObjectProperty;
import org.jsonx.StringElement;
import org.jsonx.StringProperty;
import org.jsonx.Use;

public class Library implements JxObject {
  public static class Journal extends Publication {
    private Optional<String> subject;

    @StringProperty(name = "subject")
    public Optional<String> getSubject() {
      return subject;
    }

    public void setSubject(final Optional<String> subject) {
      this.subject = subject;
    }

    private Optional<Boolean> openAccess;

    @BooleanProperty(name = "openAccess", use = Use.OPTIONAL)
    public Optional<Boolean> getOpenAccess() {
      return openAccess;
    }

    public void setOpenAccess(final Optional<Boolean> openAccess) {
      this.openAccess = openAccess;
    }
  }

  private Optional<Address> address;

  @ObjectProperty(name = "address", use = Use.OPTIONAL)
  public Optional<Address> getAddress() {
    return address;
  }

  public void setAddress(final Optional<Address> address) {
    this.address = address;
  }

  private Boolean _handicap;

  @BooleanProperty(name = "handicap", use = Use.OPTIONAL, nullable = false)
  public Boolean getHandicap() {
    return _handicap;
  }

  public void setHandicap(final Boolean handicap) {
    this._handicap = handicap;
  }

  private List<List<String>> schedule;

  @StringElement(id = 2, pattern = "\\d{2}:\\d{2}", nullable = false, minOccurs = 2, maxOccurs = 2)
  @ArrayElement(id = 1, nullable = false, elementIds = 2, minOccurs = 7, maxOccurs = 7)
  @ArrayProperty(name = "schedule", elementIds = 1)
  public List<List<String>> getSchedule() {
    return schedule;
  }

  public void setSchedule(final List<List<String>> schedule) {
    this.schedule = schedule;
  }

  private List<Book> books;

  @ObjectElement(id = 1, type = Book.class, nullable = false)
  @ArrayProperty(name = "books", elementIds = 1)
  public List<Book> getBooks() {
    return books;
  }

  public void setBooks(final List<Book> books) {
    this.books = books;
  }

  private List<OnlineArticle> articles;

  @ObjectElement(id = 1, type = OnlineArticle.class, nullable = false)
  @ArrayProperty(name = "article", elementIds = 1)
  public List<OnlineArticle> getArticles() {
    return articles;
  }

  public void setArticles(final List<OnlineArticle> articles) {
    this.articles = articles;
  }

  private List<Journal> journals;

  @ObjectElement(id = 1, type = Journal.class, nullable = false)
  @ArrayProperty(name = "journals", elementIds = 1)
  public List<Journal> getJournals() {
    return journals;
  }

  public void setJournals(final List<Journal> journals) {
    this.journals = journals;
  }

  @ObjectElement(id = 0, type = Employee.class)
  @ArrayType(elementIds = {0})
  public @interface Staff {
  }

  private List<Employee> staff;

  @ArrayProperty(name = "staff", type = Staff.class)
  public List<Employee> getStaff() {
    return staff;
  }

  public void setStaff(final List<Employee> staff) {
    this.staff = staff;
  }

  @Override
  public String toString() {
    return JxEncoder.VALIDATING._2.toString(this);
  }
}