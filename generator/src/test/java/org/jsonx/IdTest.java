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

import org.junit.Test;

public class IdTest {
  @Test
  public void test() {
    final Id id1 = Id.named(Class.class);
    final Id id2 = Id.named(Class.class);
    assertEquals(id1, id1);
    assertEquals(id1, id2);
    assertEquals(id1.hashCode(), id2.hashCode());
  }
}