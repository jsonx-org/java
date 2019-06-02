/* Copyright (c) 2019 JSONx
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

import java.io.StringReader;
import java.net.URL;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;
import org.libj.io.Streams;
import org.libj.net.MemoryURLStreamHandler;
import org.libj.util.function.Throwing;
import org.openjax.json.JsonReader;

public class JxConverterTest {
  private static final int count = 100;

  private static void test(final String fileName) throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(count);
    for (int i = 0; i < count; ++i) {
      new Thread(Throwing.rethrow(() -> {
        final String control = new String(Streams.readBytes(ClassLoader.getSystemClassLoader().getResourceAsStream(fileName)));
        final String jsonx = JxConverter.jsonToJsonx(new JsonReader(new StringReader(control), false), true);
        final URL url = MemoryURLStreamHandler.createURL(jsonx.getBytes());
        final String test = JxConverter.jsonxToJson(url.openStream(), true);
        if (!control.equals(test)) {
          System.err.println(jsonx);
          System.out.println(test);
          assertEquals(control, test);
        }

        latch.countDown();
      })).start();
    }

    latch.await();
  }

  @Test
  public void testDataType() throws InterruptedException {
    test("datatype.json");
  }

  @Test
  public void testPayPal() throws InterruptedException {
    test("paypal.json");
  }
}