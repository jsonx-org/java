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

import java.io.File;
import java.io.IOException;

/**
 * Utility for generating Java source code from JSD or JSDX schema files.
 */
public class Generator {
  private static void trapPrintUsage() {
    System.err.println("Usage: Generator [OPTIONS] <-d DEST_DIR> <SCHEMA_FILE>");
    System.err.println();
    System.err.println("Mandatory arguments:");
    System.err.println("  -d <destDir>       Specify the destination directory.");
    System.err.println();
    System.err.println("Optional arguments:");
    System.err.println("  --prefix <PREFIX>  Package prefix for generated classes.");
    System.err.println();
    System.err.println("Supported SCHEMA_FILE formats:");
    System.err.println("                 <JSD|JSDX>");
    System.exit(1);
  }

  public static void main(final String[] args) throws IOException {
    if (args.length == 0 || args[0] == null || args[0].length() == 0)
      trapPrintUsage();

    String prefix = null;
    File destDir = null;
    File schemaFile = null;
    for (int i = 0; i < args.length; ++i) {
      if ("--prefix".equals(args[i]))
        prefix = args[++i];
      else if ("-d".equals(args[i]) && i < args.length)
        destDir = new File(args[++i]).getAbsoluteFile();
      else
        schemaFile = new File(args[i]).getAbsoluteFile();
    }

    SchemaElement.parse(schemaFile.toURI().toURL(), prefix).toSource(destDir);
  }
}