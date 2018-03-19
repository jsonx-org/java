/* Copyright (c) 2017 lib4j
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

package org.libx4j.jsonx.generator;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.Ignore;
import org.junit.Test;
import org.lib4j.xml.validate.ValidationException;
import org.libx4j.jsonx.runtime.Book;
import org.libx4j.jsonx.runtime.Library;
import org.libx4j.jsonx.runtime.OnlineArticle;
import org.libx4j.xsb.runtime.Bindings;
import org.libx4j.xsb.runtime.ParseException;

public class ConvTest {
  @Test
//  @Ignore
  public void testFromClass() throws IOException {
    final Jsonx jsonx = new Jsonx(Library.class, Book.class, OnlineArticle.class);
    System.out.println(jsonx.toJSONX());
//    final File target = new File("/Users/seva/Work/SevaSafris/java/libx4j/jjb/generator/target/generated-sources/jsonx");
//    jsonx.toJava(target);
    // System.out.println(jsonx.toJSON());
  }
}