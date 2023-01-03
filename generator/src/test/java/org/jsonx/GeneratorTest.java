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

import org.junit.AfterClass;
import org.junit.Test;
import org.libj.lang.Strings;
import org.libj.util.function.Throwing;

public class GeneratorTest {
  private static final File destDir = new File("target/generated-test-sources/jsonx");

  private static File getFile(final String fileName) {
    final URL url = ClassLoader.getSystemClassLoader().getResource(fileName);
    return new File(url.getPath());
  }

  private static Runnable onFinished;

  @AfterClass
  public static void afterClass() {
    onFinished.run();
  }

  @Test
  public void testUsage() throws Throwable {
    onFinished = RuntimeUtil.onExit(Throwing.rethrow(() -> {
      Generator.main(Strings.EMPTY_ARRAY);
      fail("Expected System.exit()");
    }));
  }

  @Test
  public void test() throws IOException {
    Generator.main(new String[] {"--prefix", "org.jsonx.invoice", "-d", destDir.getAbsolutePath(), getFile("account.jsdx").getAbsolutePath()});
  }
}