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
import org.openjax.json.JsonReader;

public class DecodeExceptionTest {
  private static final String message = "hello world";
  private static final int offset = 37;
  private static final JsonReader reader = new JsonReader(message) {
    @Override
    public int getPosition() {
      return offset + 1;
    }
  };

  @Test
  public void testError() {
    final Error error = Error.ILLEGAL_VALUE_NULL();
    final DecodeException e = new DecodeException(error, null, null);
    assertTrue(e.getMessage().startsWith(error.toString()));
    assertEquals(-1, error.getOffset());
  }

  @Test
  public void testMessageOffset() {
    final String message = "hello world";
    final int offset = 37;
    final JsonReader reader = new JsonReader(message) {
      @Override
      public int getPosition() {
        return offset + 1;
      }
    };
    final DecodeException e = new DecodeException(message, reader);
    assertTrue(e.getMessage().startsWith(message));
    assertEquals(offset, e.getErrorOffset());
  }

  @Test
  public void testMessageOffsetCause() {
    final Exception cause = new NullPointerException();
    final DecodeException e = new DecodeException(message, reader, cause);
    assertTrue(e.getMessage().startsWith(message));
    assertEquals(offset, e.getErrorOffset());
    assertEquals(cause, e.getCause());
  }
}