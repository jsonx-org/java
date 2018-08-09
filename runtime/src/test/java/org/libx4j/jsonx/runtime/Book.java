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

@JsonxObject(unknown=Unknown.IGNORE)
public class Book extends Publication {
  @StringProperty(pattern="\\d{3}-\\d-\\d{2}-\\d{6}-\\d", use=Use.REQUIRED)
  private String isbn;

  public String getIsbn() {
    return this.isbn;
  }

  public void setIsbn(final String isbn) {
    this.isbn = isbn;
  }

  /**
   * [1, [1, "Part 1, Chapter 1"], [2, "Part 1, Chapter 2"], [3, "Part 1, Chapter 3"],
   *  2, [1, "Part 2, Chapter 1"], [2, "Part 2, Chapter 2"], [3, "Part 2, Chapter 3"]...]
   */
  @StringElement(id=4, pattern="(\\S)|(\\S.*\\S)", nullable=false, minOccurs=1, maxOccurs=1)
  @NumberElement(id=3, range="[1,]", nullable=false, minOccurs=1, maxOccurs=1)
  @ArrayElement(id=2, nullable=false, elementIds={3,4})
  @NumberElement(id=1, form=Form.INTEGER, range="[1,]", nullable=false)
  @ArrayProperty(use=Use.REQUIRED, elementIds={1,2})
  private List<Object> index;

  public List<Object> getIndex() {
    return this.index;
  }

  public void setIndex(final List<Object> index) {
    this.index = index;
  }
}