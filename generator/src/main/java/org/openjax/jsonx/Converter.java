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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import org.openjax.standard.json.JSON;
import org.openjax.standard.net.URLs;
import org.openjax.standard.xml.api.ValidationException;
import org.openjax.standard.xml.api.XmlElement;

/**
 * Utility for converting JSD files to JSDX, and vice versa.
 */
public final class Converter {
  private static void trapPrintUsage() {
    System.err.println("Usage: Converter <SCHEMA_IN> [SCHEMA_OUT]");
    System.err.println();
    System.err.println("Supported SCHEMA_IN|OUT formats:");
    System.err.println("                 <JSD|JSDX>");
    System.exit(1);
  }

  public static void main(final String[] args) throws IOException {
    if (args.length != 1 && args.length != 2)
      trapPrintUsage();

    final String converted = convert(new File(args[0]).getAbsoluteFile().toURI().toURL());
    if (args.length == 1)
      System.out.println(converted);
    else
      Files.write(new File(args[1]).toPath(), converted.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
  }

  /**
   * Converts a JDS file to the JSDX format.
   *
   * @param url The {@code URL} of the content to convert.
   * @return The converted file in JSDX format.
   * @throws IOException If an I/O error has occurred.
   * @throws DecodeException If a decode error has occurred.
   * @throws ValidationException If a validation error has occurred.
   */
  public static String jsdToJsdx(final URL url) throws IOException, DecodeException, ValidationException {
    final XmlElement xml = SchemaElement.parseJsd(url, "").toXml();
    xml.getAttributes().put("xsi:schemaLocation", "http://jsonx.openjax.org/schema-0.2.2.xsd http://jsonx.openjax.org/schema-0.2.2.xsd");
    return xml.toString();
  }

  /**
   * Converts a JDSX file to the JSD format.
   *
   * @param url The {@code URL} of the content to convert.
   * @return The converted file in JSD format.
   * @throws IOException If an I/O error has occurred.
   * @throws ValidationException If a validation error has occurred.
   */
  public static String jsdxToJsd(final URL url) throws IOException, ValidationException {
    return JSON.toString(SchemaElement.parseJsdx(url, "").toJson());
  }

  /**
   * Converts a JDS or JSDX file to the other format.
   *
   * @param url The {@code URL} of the content to convert.
   * @return The converted format.
   * @throws IllegalArgumentException If the format of the content of the
   *           specified file is malformed, or is not JSDX or JSD.
   * @throws IOException If an I/O error has occurred.
   */
  public static String convert(final URL url) throws IOException {
    if (URLs.getName(url).endsWith(".jsd")) {
      try {
        return jsdToJsdx(url);
      }
      catch (final DecodeException | ValidationException de) {
        try {
          return jsdxToJsd(url);
        }
        catch (final Exception e) {
          e.addSuppressed(de);
          throw new IllegalArgumentException(e);
        }
      }
    }

    try {
      return jsdxToJsd(url);
    }
    catch (final ValidationException ve) {
      try {
        return jsdToJsdx(url);
      }
      catch (final DecodeException | ValidationException e) {
        e.addSuppressed(ve);
        throw new IllegalArgumentException(e);
      }
    }
  }

  private Converter() {
  }
}