/* Copyright (c) 2018 JSONx
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
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.junit.AfterClass;
import org.junit.Test;
import org.libj.jci.CompilationException;
import org.libj.lang.PackageNotFoundException;
import org.libj.lang.Strings;
import org.libj.util.function.Throwing;

public class ValidatorTest {
  private static File getFile(final String fileName) {
    final File dir = new File("target/generated-test-resources");
    dir.mkdirs();
    final File jsdFile = new File(dir, "account.jsdx");
    try (final InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName)) {
      Files.copy(in, jsdFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
      return jsdFile;
    }
    catch (final IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  private static File schemaFile = getFile("account.jsdx");
  private static Runnable onFinished;

  @AfterClass
  public static void afterClass() {
    onFinished.run();
  }

  @Test
  public void testUsage() throws Throwable {
    onFinished = RuntimeUtil.onExit(Throwing.rethrow(() -> {
      Validator.main(Strings.EMPTY_ARRAY);
      fail("Expected System.exit()");
    }));
  }

  private static void testFile(final File file, final boolean isValid) throws CompilationException, IOException, PackageNotFoundException {
    final boolean success = Validator.validate(new String[] {schemaFile.getAbsolutePath(), file.getAbsolutePath()}) == 0;
    if (isValid != success)
      fail(file.getName());
  }

  private static void testDir(final File dir, final boolean isValid) throws CompilationException, IOException, PackageNotFoundException {
    for (final File file : dir.listFiles()) // [A]
      testFile(file, isValid);
  }

  @Test
  public void testValid() throws CompilationException, IOException, PackageNotFoundException {
    testDir(new File("src/test/resources/valid"), true);
  }

  @Test
  public void testInvalid() throws CompilationException, IOException, PackageNotFoundException {
    testDir(new File("src/test/resources/invalid"), false);
  }
}