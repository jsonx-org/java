/* Copyright (c) 2017 JSONx
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

import org.junit.Test;

public class StrictDigraphTest {
  @Test
  public void testStrictDigraph() {
    final StrictDigraph<String> digraph = new StrictDigraph<>("hello");
    try {
      digraph.add("a", "a");
      fail("Expected ValidationException");
    }
    catch (final ValidationException e) {
    }
  }

  @Test
  public void testStrictRefDigraph() {
    final StrictRefDigraph<String,String> digraph = new StrictRefDigraph<>("hello", (final String s) -> s);
    try {
      digraph.add("a", "a");
      fail("Expected ValidationException");
    }
    catch (final ValidationException e) {
    }
  }
}