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

package org.jsonx;

/**
 * Signals that an error has been reached unexpectedly while parsing.
 */
public class ParseException extends Exception {
  private static final long serialVersionUID = -4391452229717188054L;

  /**
   * The zero-based character offset into the string being parsed at which the
   * error was found during parsing.
   */
  private final int errorOffset;

  /**
   * Constructs a {@code ParseException} with the specified detail message and
   * offset. A detail message is a String that describes this particular
   * exception.
   *
   * @param string The detail message.
   * @param errorOffset The position where the error is found while parsing.
   */
  public ParseException(final String string, final int errorOffset) {
    super(string + " [errorOffset: " + errorOffset + "]");
    this.errorOffset = errorOffset;
  }

  /**
   * @return The position where the error was found.
   */
  public int getErrorOffset() {
    return errorOffset;
  }
}