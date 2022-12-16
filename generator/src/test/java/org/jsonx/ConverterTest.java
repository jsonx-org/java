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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;

import org.junit.Test;
import org.libj.lang.Strings;
import org.libj.net.MemoryURLStreamHandler;

import com.github.stefanbirkner.systemlambda.SystemLambda;

public class ConverterTest {
  @Test
  public void testUsage() throws Exception {
    assertEquals(1, SystemLambda.catchSystemExit(() -> {
      Converter.main(Strings.EMPTY_ARRAY);
    }));
  }

  @Test
  public void testConvertSchema() throws IOException {
    Converter.convert(new URL("http://www.jsonx.org/schema.jsd"));
    Converter.convert(new URL("http://www.jsonx.org/schema.jsdx"));
  }

  @Test
  public void testMain() throws Exception {
    final File jsdFile = Files.createTempFile("jsd", null).toFile();
    jsdFile.deleteOnExit();
    Converter.main(new String[] {"src/test/resources/account.jsdx", jsdFile.getAbsolutePath()});
    final String jsd1 = new String(Files.readAllBytes(jsdFile.toPath()));
    final String jsdx = Converter.convert(jsdFile.toURI().toURL());
    final URL jsdxUrl = MemoryURLStreamHandler.createURL(jsdx.getBytes());
    assertEquals(jsd1, Converter.convert(jsdxUrl));

    final File jsdxFile = Files.createTempFile("jsdx", null).toFile();
    jsdxFile.deleteOnExit();
    Converter.main(new String[] {jsdFile.getAbsolutePath(), jsdxFile.getAbsolutePath()});
    final String jsdx1 = new String(Files.readAllBytes(jsdxFile.toPath()));
    final String jsd = Converter.convert(jsdxFile.toURI().toURL());
    final URL jsdUrl = MemoryURLStreamHandler.createURL(jsd.getBytes());
    assertEquals(jsdx1, Converter.convert(jsdUrl));
  }
}