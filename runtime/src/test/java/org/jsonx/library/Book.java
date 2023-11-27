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

import org.jsonx.ArrayElement;
import org.jsonx.ArrayProperty;
import org.jsonx.NumberElement;
import org.jsonx.StringElement;
import org.jsonx.StringProperty;

@SuppressWarnings("javadoc")
public class Book extends Publication {
  private String isbn;

  @StringProperty(name = "isbn", pattern = "\\d{3}-\\d-\\d{2}-\\d{6}-\\d")
  public String getIsbn() {
    return isbn;
  }

  public void setIsbn(final String isbn) {
    this.isbn = isbn;
  }

  private List<Object> index;

  /**
   * [[1, "Part 1, Chapter 1"], [2, "Part 1, Chapter 2"], [3, "Part 1, Chapter 3"], [1, "Part 2, Chapter 1"], [2, "Part 2, Chapter
   * 2"], [3, "Part 2, Chapter 3"]...]
   */
  @StringElement(id = 3, pattern = "\\S|\\S.*\\S", nullable = false, maxOccurs = 1)
  @NumberElement(id = 2, range = "[1,]", nullable = false, maxOccurs = 1)
  @ArrayElement(id = 1, nullable = false, elementIds = {2, 3})
  @ArrayProperty(name = "index", elementIds = {1})
  public List<Object> getIndex() {
    return index;
  }

  public void setIndex(final List<Object> index) {
    this.index = index;
  }
}