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

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.Ignore;
import org.junit.Test;
import org.libj.net.MemoryURLStreamHandler;
import org.libj.net.URLs;
import org.libj.util.function.Throwing;
import org.openjax.json.JsonReader;
import org.xml.sax.SAXException;

public class JxConverterTest {
  private static final int count = 100;

  private static void concurrentTest(final String json) throws InterruptedException {
    final CountDownLatch latch = new CountDownLatch(count);
    final AtomicBoolean failed = new AtomicBoolean();
    final ExecutorService executor = Executors.newFixedThreadPool(count);
    for (int i = 0; i < count; ++i) // [N]
      executor.execute(Throwing.rethrow(() -> singleTest(json, latch, failed)));

    executor.shutdown();
    latch.await();
    assertFalse(failed.get());
  }

  private static void singleTest(final String json) throws IOException, SAXException {
    singleTest(json, null, null);
  }

  private static void singleTest(final String json, final CountDownLatch latch, final AtomicBoolean failed) throws IOException, SAXException {
    final String jsonx = JxConverter.jsonToJsonx(new JsonReader(json, false), true);
    final URL url = MemoryURLStreamHandler.createURL(jsonx.getBytes());
    try {
      final String test = JxConverter.jsonxToJson(url.openStream(), true);
      if (latch != null) {
        latch.countDown();
        if (failed.get())
          return;
      }

      if (!json.equals(test)) {
        if (failed != null)
          failed.set(true);

        System.err.println(jsonx);
        System.err.flush();
        System.out.println(test);
        System.out.flush();
        assertEquals(json, test);
      }
    }
    catch (final SAXException e) {
      System.err.println(new String(jsonx.getBytes()));
      throw e;
    }
  }

  // FIXME: This is failing
  @Test
  @Ignore("FIXME: This is failing")
  public void testDataType() throws Exception {
    singleTest(new String(URLs.readBytes(ClassLoader.getSystemClassLoader().getResource("datatype.json"))));
  }

  @Test
  public void test() throws IOException, SAXException {
    singleTest("{\"arrayNum\":[[{\"abc\":false},[[null,null],null]],null,16,null,10000000001,null,5125505215420994600.00,null,-39,10000000001,null,1.000]}");
    singleTest("{\"arrayNum\":[{\"abc\":-3.4737577752778189E+18},null,21,null,10000000001,2,4011107250433342000.00,null,-59,10000000001,2,null]}");
  }

  @Test
  public void testPayPal() throws Exception {
    concurrentTest(new String(URLs.readBytes(ClassLoader.getSystemClassLoader().getResource("paypal.json"))));
  }
}