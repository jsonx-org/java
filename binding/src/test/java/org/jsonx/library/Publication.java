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

import org.jsonx.ArrayProperty;
import org.jsonx.JxEncoder;
import org.jsonx.JxObject;
import org.jsonx.ObjectElement;
import org.jsonx.StringElement;
import org.jsonx.StringProperty;
import org.jsonx.Use;

public abstract class Publication implements JxObject {
  private String title;

  @StringProperty(name="title")
  public String getTitle() {
    return this.title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  private Optional<List<String>> authors;

  @StringElement(id=1, pattern="\\S+ \\S+", nullable=false)
  @ArrayProperty(name="authors", use=Use.OPTIONAL, elementIds=1)
  public Optional<List<String>> getAuthors() {
    return this.authors;
  }

  public void setAuthors(final Optional<List<String>> authors) {
    this.authors = authors;
  }

  private Optional<List<String>> editors;

  @StringElement(id=1, pattern="\\S+ \\S+", nullable=false)
  @ArrayProperty(name="editors", use=Use.OPTIONAL, elementIds=1)
  public Optional<List<String>> getEditors() {
    return this.editors;
  }

  public void setEditors(final Optional<List<String>> editors) {
    this.editors = editors;
  }

  private List<Publishing> publishings;

  @ObjectElement(id=1, type=Publishing.class, nullable=false)
  @ArrayProperty(name="publishings", elementIds=1)
  public List<Publishing> getPublishings() {
    return this.publishings;
  }

  public void setPublishings(final List<Publishing> publishings) {
    this.publishings = publishings;
  }

  @Override
  public String toString() {
    return JxEncoder.VALIDATING._2.toString(this);
  }
}