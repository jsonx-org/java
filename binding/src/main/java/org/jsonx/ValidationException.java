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

package org.jsonx;

/**
 * Signals that an error has occurred while evaluating a JSD model.
 */
public class ValidationException extends RuntimeException {
  private static final long serialVersionUID = 2984407300739435993L;

  /**
   * Creates a new {@link ValidationException} with the specified detail
   * message.
   *
   * @param message The detail message.
   */
  public ValidationException(final String message) {
    super(message);
  }

  /**
   * Creates a new {@link ValidationException} with the specified cause.
   *
   * @param cause The cause.
   */
  public ValidationException(final Throwable cause) {
    super(cause);
  }

  /**
   * Creates a new {@link ValidationException} with the specified detail message
   * and cause.
   *
   * @param message The detail message.
   * @param cause The cause.
   */
  public ValidationException(final String message, final Throwable cause) {
    super(message, cause);
  }
}