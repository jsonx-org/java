/* Copyright (c) 2016 JSONx
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
 * Signals that an error has occurred while decoding a JSON document to binding
 * classes.
 */
public class DecodeException extends Exception {
  private static final long serialVersionUID = 7087309932016830988L;

  /**
   * The zero-based character offset into the string being parsed at which the
   * error was found during parsing.
   */
  private final int errorOffset;

  DecodeException(final Error error) {
    this(error.toString(), error.offset, null);
  }

  /**
   * Creates a new {@link DecodeException} with the specified detail message and
   * offset.
   *
   * @param message The detail message that describes this particular exception.
   * @param errorOffset The position where the error is found while parsing.
   */
  public DecodeException(final String message, final int errorOffset) {
    this(message, errorOffset, null);
  }

  /**
   * Creates a new {@link DecodeException} with the specified detail message,
   * offset, and cause.
   *
   * @param message The detail message that describes this particular exception.
   * @param errorOffset The position where the error is found while parsing.
   * @param cause The cause.
   */
  public DecodeException(final String message, final int errorOffset, final Throwable cause) {
    super(message != null ? message + " [errorOffset: " + errorOffset + "]" : "[errorOffset: " + errorOffset + "]", cause);
    this.errorOffset = errorOffset;
  }

  /**
   * Returns the position where the error was found.
   *
   * @return The position where the error was found.
   */
  public int getErrorOffset() {
    return errorOffset;
  }
}