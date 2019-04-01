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

package org.openjax.jsonx.generator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

import org.openjax.jsonx.schema;
import org.openjax.jsonx.runtime.DecodeException;
import org.openjax.jsonx.runtime.JxDecoder;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc;
import org.openjax.standard.json.JSON;
import org.openjax.standard.json.JsonReader;
import org.openjax.standard.xml.api.ValidationException;
import org.openjax.standard.xml.api.XmlElement;
import org.openjax.xsb.runtime.Bindings;

public class Converter {
  enum Type {
    JSDX, JSD
  }

  private static void trapPrintUsage() {
    System.err.println("Usage: Converter <IN> <OUT>");
    System.err.println();
    System.err.println("Supported extensions:");
    System.err.println("               <.jsd|.jsdx>");
    System.exit(1);
  }

  private static Type getType(final File file) {
    if (file.getName().endsWith(".jsd"))
      return Type.JSD;

    if (file.getName().endsWith(".jsdx"))
      return Type.JSDX;

    throw new UnsupportedOperationException("Unsupported file extension: " + file.getName().substring(file.getName().lastIndexOf('.')));
  }

  public static void main(final String[] args) throws DecodeException, IOException, ValidationException {
    if (args.length != 2)
      trapPrintUsage();

    final File inFile = new File(args[0]).getAbsoluteFile();
    final File outFile = new File(args[1]).getAbsoluteFile();

    final Type inType = getType(inFile);
    final Type outType = getType(outFile);
    if (inType == outType) {
      Files.copy(inFile.toPath(), outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
    else {
      final Schema schema;
      try (final InputStream in = inFile.toURI().toURL().openStream()) {
        if (inType == Type.JSDX)
          schema = new Schema((xL4gluGCXYYJc.Schema)Bindings.parse(in), "");
        else
          schema = new Schema(JxDecoder.parseObject(schema.Schema.class, new JsonReader(new InputStreamReader(in))), "");
      }

      final String out;
      if (outType == Type.JSDX) {
        final XmlElement xml = schema.toXml();
        xml.getAttributes().put("xsi:schemaLocation", "http://jsonx.openjax.org/schema-0.9.8.xsd http://jsonx.openjax.org/schema-0.9.8.xsd");
        out = xml.toString();
      }
      else {
        out = JSON.toString(schema.toJson());
      }

      Files.write(outFile.toPath(), out.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
  }
}