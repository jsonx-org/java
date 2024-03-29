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

import org.libj.net.URLs;
import org.xml.sax.SAXException;

abstract class SpecTest {
  public static void test(final String name, String version) throws IOException, SAXException {
    if (version.length() > 0)
      version = "-" + version;

    final URL testJsdUrl = new URL("http://www.jsonx.org/" + name + version + ".jsdx");
    final String testJsd = Converter.xmlToJson(testJsdUrl);

    final URL controlJsdUrl = new URL("http://www.jsonx.org/" + name + version + ".jsd");
    final String controlJsd = new String(URLs.readBytes(controlJsdUrl));

    assertEquals(controlJsd, testJsd);
  }
}