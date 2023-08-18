package org.jsonx;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class JsonPathTest {
  @Test
  public void test1() {
    final JsonPath path = new JsonPath("o.p.a");
    final Iterator<Object> i = path.iterator();
    assertEquals("o", i.next());
    assertEquals("p", i.next());
    assertEquals("a", i.next());
    assertFalse(i.hasNext());
  }

  @Test
  public void test2() {
    final JsonPath path = new JsonPath("o.p.a[4][4][2]");
    final Iterator<Object> i = path.iterator();
    assertEquals("o", i.next());
    assertEquals("p", i.next());
    assertEquals("a", i.next());
    assertEquals(4, i.next());
    assertEquals(4, i.next());
    assertEquals(2, i.next());
    assertFalse(i.hasNext());
  }
}