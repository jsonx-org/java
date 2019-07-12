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

import org.junit.Test;
import org.libj.io.Streams;
import org.xml.sax.SAXException;

public class SchemaSpecTest {
  public static void test(String version) throws IOException, SAXException {
    if (version.length() > 0)
      version = "-" + version;

    final URL testJsdUrl = new URL("http://www.jsonx.org/schema" + version + ".jsdx");
    final String testJsd = Converter.jsdxToJsd(testJsdUrl);

    final URL controlJsdUrl = new URL("http://www.jsonx.org/schema" + version + ".jsd");
    final String controlJsd = new String(Streams.readBytes(controlJsdUrl.openStream()));

    assertEquals(controlJsd, testJsd);
  }

  @Test
  public void test() throws IOException, SAXException {
    test("");
  }

  @Test
  public void test023() throws IOException, SAXException {
    test("0.2.3");
  }
}