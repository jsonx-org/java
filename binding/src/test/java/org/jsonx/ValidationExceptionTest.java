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

import static org.junit.Assert.*;

import org.junit.Test;

public class ValidationExceptionTest {
  @Test
  public void testMessage() {
    final String message = "hello world";
    final ValidationException e = new ValidationException(message);
    assertEquals(message, e.getMessage());
  }

  @Test
  public void testCause() {
    final Exception cause = new NullPointerException();
    final ValidationException e = new ValidationException(cause);
    assertEquals(cause, e.getCause());
  }

  @Test
  public void testMessageCause() {
    final String message = "hello world";
    final Exception cause = new NullPointerException();
    final ValidationException e = new ValidationException(message, cause);
    assertEquals(message, e.getMessage());
    assertEquals(cause, e.getCause());
  }
}