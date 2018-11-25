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

@ObjectType
public abstract class Publication {
  @StringProperty(use=Use.REQUIRED)
  private String title;

  public String getTitle() {
    return this.title;
  }

  public void setTitle(final String title) {
    this.title = title;
  }

  @StringElement(id=1, pattern="\\S+ \\S+", nullable=false)
  @ArrayProperty(use=Use.REQUIRED, elementIds=1)
  private List<String> authors;

  public List<String> getAuthors() {
    return this.authors;
  }

  public void setAuthors(final List<String> authors) {
    this.authors = authors;
  }

  @StringElement(id=1, pattern="\\S+ \\S+", nullable=false)
  @ArrayProperty(use=Use.REQUIRED, elementIds=1)
  private List<String> editors;

  public List<String> getEditors() {
    return this.editors;
  }

  public void setEditors(final List<String> editors) {
    this.editors = editors;
  }

  @ObjectElement(id=1, type=Publishing.class, nullable=false)
  @ArrayProperty(use=Use.REQUIRED, elementIds=1)
  private List<Publishing> publishings;

  public List<Publishing> getPublishings() {
    return this.publishings;
  }

  public void setPublishings(final List<Publishing> publishings) {
    this.publishings = publishings;
  }

  @Override
  public String toString() {
    return new JxEncoder(2).toString(this);
  }
}