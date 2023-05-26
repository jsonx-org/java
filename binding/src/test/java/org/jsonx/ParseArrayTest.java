/* Copyright (c) 2020 JSONx
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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Ignore;
import org.junit.Test;
import org.openjax.json.JsonReader;

public class ParseArrayTest {
  @NumberElement(id=0, minOccurs=1, nullable=false)
  @ArrayType(elementIds={0})
  @interface LongArray {
  }

  public static void assertLength(final int length) throws DecodeException, IOException {
    final int limit = length * 2;
    try (final JsonReader reader = new JsonReader(new InputStreamReader(new InputStream() {
      private int i = -2;

      @Override
      public int read() throws IOException {
        ++i;
        if (i == -1)
          return '[';

        if (i == limit)
          return ']';

        if (i > limit)
          return -1;

        final int mod = i % 2;
        if (mod == 0)
          return '0';

        return ',';
      }
    }))) {
      JxDecoder.VALIDATING.parseArray(reader, LongArray.class);
    }
  }

  @Test
  public void test100() throws DecodeException, IOException {
    assertLength(100);
  }

  @Test
  public void test1000() throws DecodeException, IOException {
    assertLength(1000);
  }

  @Test
  public void test10000() throws DecodeException, IOException {
    assertLength(10000);
  }

  @Test
  public void test100000() throws DecodeException, IOException {
    assertLength(100000);
  }

  @Test
  public void test1000000() throws DecodeException, IOException {
    assertLength(1000000);
  }

  @Test
  @Ignore("Causes java.lang.OutOfMemoryError: Java heap space in Travis CI")
  public void test10000000() throws DecodeException, IOException {
    assertLength(10000000);
  }
}