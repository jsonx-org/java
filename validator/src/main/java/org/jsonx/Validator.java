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

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.libj.jci.CompilationException;
import org.libj.jci.InMemoryCompiler;
import org.libj.lang.Numbers;
import org.libj.lang.PackageLoader;
import org.libj.lang.PackageNotFoundException;
import org.libj.net.URLs;
import org.openjax.json.JsonReader;

/**
 * Utility for validating JSON documents to a JSD or JSDx schema.
 */
public class Validator {
  private static void trapPrintUsage() {
    System.err.println("Usage: Validator <SCHEMA.jsd|SCHEMA.jsdx> <JSON>...");
    System.exit(1);
  }

  private static void validate(final JsonReader reader, final Set<Class<? extends JxObject>> objectClasses, final Set<Class<? extends Annotation>> arrayClasses, final ArrayList<String> errors) throws IOException {
    final long i = reader.readToken();
    final int off = Numbers.Composite.decodeInt(i, 0);
    final char token = reader.bufToChar(off);
    try {
      if (token == '{') {
        if (objectClasses.size() > 0)
          JxDecoder.VALIDATING.parseObject(reader, objectClasses);
        else
          errors.add("No object definition present in schema");
      }
      else if (token == '[') {
        if (arrayClasses.size() > 0)
          JxDecoder.VALIDATING.parseArray(reader, arrayClasses);
        else
          errors.add("No array definition present in schema");
      }
      else {
        throw new IllegalArgumentException("Unable to parse JSON document");
      }
    }
    catch (final DecodeException e) {
      errors.add(e.getMessage());
    }
  }

  public static void main(final String[] args) throws CompilationException, IOException, PackageNotFoundException {
    if (args.length < 2)
      trapPrintUsage();

    if (validate(args) == 1)
      System.exit(1);
  }

  @SuppressWarnings("unchecked")
  static int validate(final String[] args) throws CompilationException, IOException, PackageNotFoundException {
    final String prefix = "jsonx";
    final Map<String,String> source = SchemaElement.parse(URLs.fromStringPath(args[0]), new Settings.Builder().withPrefix(prefix + ".").build()).toSource();
    final InMemoryCompiler compiler = new InMemoryCompiler();
    for (final Map.Entry<String,String> entry : source.entrySet()) // [S]
      compiler.addSource(entry.getValue());

    final ClassLoader classLoader = compiler.compile();

    final HashSet<Class<? extends JxObject>> objectClasses = new HashSet<>();
    final HashSet<Class<? extends Annotation>> arrayClasses = new HashSet<>();
    PackageLoader.getPackageLoader(classLoader).loadPackage(prefix, cls -> {
      if (cls.getDeclaringClass() == null) {
        if (Annotation.class.isAssignableFrom(cls))
          arrayClasses.add((Class<? extends Annotation>)cls);
        else if (!Modifier.isAbstract(cls.getModifiers()) && JxObject.class.isAssignableFrom(cls))
          objectClasses.add((Class<? extends JxObject>)cls);
      }

      return true;
    });

    final ArrayList<String> errors = new ArrayList<>();
    for (int i = 1, i$ = args.length; i < i$; ++i) {// [A]
      try (final JsonReader reader = new JsonReader(new InputStreamReader(URLs.fromStringPath(args[i]).openStream()))) {
        validate(reader, objectClasses, arrayClasses, errors);
      }
    }

    for (int i = 0, i$ = errors.size(); i < i$; ++i) // [RA]
      System.err.println(errors.get(i));

    return errors.size() == 0 ? 0 : 1;
  }
}