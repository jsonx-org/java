/* Copyright (c) 2019 OpenJAX
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

package org.openjax.jsonx;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.net.URL;

import org.junit.Test;
import org.openjax.standard.io.Streams;
import org.openjax.standard.json.JsonReader;
import org.openjax.standard.net.MemoryURLStreamHandler;
import org.xml.sax.SAXException;

public class JxConverterTest {
  private static void test(final String fileName) throws IOException, SAXException {
    final String control = new String(Streams.readBytes(ClassLoader.getSystemClassLoader().getResourceAsStream(fileName)));
    final String jsonx = JxConverter.jsonToJsonx(new JsonReader(new StringReader(control), false), true);
    final URL url = MemoryURLStreamHandler.createURL(jsonx.getBytes());
    final String test = JxConverter.jsonxToJson(url.openStream(), true);
    if (!control.equals(test)) {
      System.err.println(jsonx);
      System.out.println(test);
      assertEquals(control, test);
    }
  }

  @Test
  public void testDataType() throws IOException, SAXException {
    test("datatype.json");
  }

  @Test
  public void testPayPal() throws IOException, SAXException {
    test("paypal.json");
  }
}