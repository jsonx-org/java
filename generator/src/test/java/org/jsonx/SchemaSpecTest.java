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
import org.openjax.xml.api.ValidationException;

public class SchemaSpecTest {
  public static void test(final String version) throws IOException, ValidationException {
    final URL controlJsdUrl = ClassLoader.getSystemClassLoader().getResource("schema-" + version + ".jsd");
    final String controlJsd = new String(Streams.readBytes(controlJsdUrl.openStream()));
    final URL testJsdUrl = ClassLoader.getSystemClassLoader().getResource("schema-" + version + ".jsdx");
    final String testJsd = Converter.jsdxToJsd(testJsdUrl);
    assertEquals(controlJsd, testJsd);
  }

  @Test
  public void test() throws IOException, ValidationException {
    test("0.2.2");
  }
}