/* Copyright (c) 2023 JSONx
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

import java.util.Iterator;

import org.junit.Test;

public class JsonPathTest {
  @Test
  public void test1() {
    final String str = "o.p.a";
    final JsonPath path = new JsonPath(str);
    assertEquals(str, path.toString());

    final Iterator<Object> i = path.iterator();
    assertEquals("o", i.next());
    assertEquals("p", i.next());
    assertEquals("a", i.next());
    assertFalse(i.hasNext());
  }

  @Test
  public void test2() {
    final String str = "o.p.a[4][4][2]";
    final JsonPath path = new JsonPath(str);
    assertEquals(str, path.toString());

    final Iterator<Object> i = path.iterator();
    assertEquals("o", i.next());
    assertEquals("p", i.next());
    assertEquals("a", i.next());
    assertEquals(4, i.next());
    assertEquals(4, i.next());
    assertEquals(2, i.next());
    assertFalse(i.hasNext());
  }

  @Test
  public void test3() {
    final String str = "bo\\.ol.bo\\.ol";
    final JsonPath path = new JsonPath(str);
    assertEquals(str, path.toString());

    final Iterator<Object> i = path.iterator();
    assertEquals("bo.ol", i.next());
    assertEquals("bo.ol", i.next());
    assertFalse(i.hasNext());
  }

  @Test
  public void testCursor() {
    final JsonPath.Cursor c = new JsonPath.Cursor();

    try {
      c.inArray();
      fail("Expected IllegalStateException");
    }
    catch (final IllegalStateException e) {
    }

    c.pushName("a.a");
    c.pushName("b");
    assertEquals("a\\.a.b", c.toString());

    c.inArray();

    c.pushName("0");
    assertEquals("a\\.a.b[0]", c.toString());

    c.popName();
    assertEquals("a\\.a.b", c.toString());

    c.pushName("1");
    assertEquals("a\\.a.b[1]", c.toString());

    c.popName();
    assertEquals("a\\.a.b", c.toString());

    c.pushName("2");
    assertEquals("a\\.a.b[2]", c.toString());

    c.pushName("0");
    assertEquals("a\\.a.b[2][0]", c.toString());

    c.popName();
    assertEquals("a\\.a.b[2]", c.toString());

    c.pushName("1");
    assertEquals("a\\.a.b[2][1]", c.toString());

    c.pushName("0");
    assertEquals("a\\.a.b[2][1][0]", c.toString());

    c.pushName("0");
    assertEquals("a\\.a.b[2][1][0][0]", c.toString());

    c.popName();
    assertEquals("a\\.a.b[2][1][0]", c.toString());

    c.popName();
    assertEquals("a\\.a.b[2][1]", c.toString());

    c.pushName("1");
    assertEquals("a\\.a.b[2][1][1]", c.toString());

    c.popName();
    assertEquals("a\\.a.b[2][1]", c.toString());

    c.popName();
    assertEquals("a\\.a.b[2]", c.toString());

    c.pushName("2");
    assertEquals("a\\.a.b[2][2]", c.toString());

    c.popName();
    assertEquals("a\\.a.b[2]", c.toString());

    c.popName();
    assertEquals("a\\.a.b", c.toString());

    c.pushName("3");
    assertEquals("a\\.a.b[3]", c.toString());

    c.popName();
    assertEquals("a\\.a.b", c.toString());

    c.popName();
    assertEquals("a\\.a", c.toString());

    c.pushName("c");
    assertEquals("a\\.a.c", c.toString());

    c.pushName("d");
    assertEquals("a\\.a.c.d", c.toString());

    c.inArray();

    c.pushName("0");
    assertEquals("a\\.a.c.d[0]", c.toString());

    c.pushName("0");
    assertEquals("a\\.a.c.d[0][0]", c.toString());

    c.popName();
    assertEquals("a\\.a.c.d[0]", c.toString());

    c.pushName("1");
    assertEquals("a\\.a.c.d[0][1]", c.toString());

    c.popName();
    assertEquals("a\\.a.c.d[0]", c.toString());

    c.popName();
    assertEquals("a\\.a.c.d", c.toString());

    c.pushName("1");
    assertEquals("a\\.a.c.d[1]", c.toString());

    c.popName();
    assertEquals("a\\.a.c.d", c.toString());

    c.popName();
    assertEquals("a\\.a.c", c.toString());

    c.pushName("e");
    assertEquals("a\\.a.c.e", c.toString());

    c.popName();
    assertEquals("a\\.a.c", c.toString());

    c.popName();
    assertEquals("a\\.a", c.toString());

    c.popName();
    assertEquals("", c.toString());

    c.pushName("f");
    assertEquals("f", c.toString());

    c.popName();
    assertEquals("", c.toString());

    try {
      c.inArray();
      fail("Expected IllegalStateException");
    }
    catch (final IllegalStateException e) {
    }
  }
}