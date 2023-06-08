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
 * Utility for generating Java source code from JSD or JSDx schema files.
 */
public final class Generator {
  private Generator() {
  }

  private static void trapPrintUsage() {
    System.err.println("Usage: Generator [OPTIONS] <-d DEST_DIR> <SCHEMA.jsd|SCHEMA.jsdx>");
    System.err.println();
    System.err.println("Mandatory arguments:");
    System.err.println("  -d <DEST_DIR>                      Specify the destination directory.");
    System.err.println();
    System.err.println("Optional arguments:");
    System.err.println("  --prefix <PREFIX>                  Package prefix for generated classes.");
    System.err.println("  --defaultIntegerPrimitive <CLASS>  Name of primitive type to be used as default type binding for");
    System.err.println("                                     \"number\" types with scale=0 && Use=REQUIRED && nullable=false.");
    System.err.println("  --defaultIntegerObject <CLASS>     Name of non-primitive type to be used as default type binding for");
    System.err.println("                                     \"number\" types with scale=0 && (Use=OPTIONAL || nullable=true).");
    System.err.println("  --defaultRealPrimitive <CLASS>     Name of primitive type to be used as default type binding for");
    System.err.println("                                     \"number\" types with scale>0 && Use=REQUIRED && nullable=false.");
    System.err.println("  --defaultRealObject <CLASS>        Name of non-primitive type to be used as default type binding for");
    System.err.println("                                     \"number\" types with scale>0 && (Use=OPTIONAL || nullable=true).");
    System.exit(1);
  }

  @SuppressWarnings("null")
  public static void main(final String[] args) throws IOException {
    if (args.length == 0 || args[0] == null || args[0].length() == 0)
      trapPrintUsage();

    final Settings.Builder settings = new Settings.Builder();
    File destDir = null;
    File schemaFile = null;
    for (int i = 0, i$ = args.length; i < i$; ++i) { // [A]
      if ("--prefix".equals(args[i]))
        settings.withPrefix(args[++i]);
      else if ("--defaultIntegerPrimitive".equals(args[i]))
        settings.withIntegerPrimitive(args[++i]);
      else if ("--defaultIntegerObject".equals(args[i]))
        settings.withIntegerObject(args[++i]);
      else if ("--defaultRealPrimitive".equals(args[i]))
        settings.withRealPrimitive(args[++i]);
      else if ("--defaultRealObject".equals(args[i]))
        settings.withRealObject(args[++i]);
      else if ("-d".equals(args[i]))
        destDir = new File(args[++i]).getAbsoluteFile();
      else
        schemaFile = new File(args[i]).getAbsoluteFile();
    }

    if (schemaFile == null)
      trapPrintUsage();

    SchemaElement.parse(schemaFile.toURI().toURL(), settings.build()).toSource(destDir);
  }
}