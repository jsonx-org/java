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

/**
 * Signals that an error has occurred while evaluating a JSONx model.
 */
public class ValidationException extends RuntimeException {
  private static final long serialVersionUID = 2984407300739435993L;

  public ValidationException(final String message) {
    super(message);
    if (message.startsWith("Invalid content"))
      System.out.println();
  }

  public ValidationException(final Throwable cause) {
    super(cause);
  }

  public ValidationException(final String message, final Throwable cause) {
    super(message, cause);
  }
}