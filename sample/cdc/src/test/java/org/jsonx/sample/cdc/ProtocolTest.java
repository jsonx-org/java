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

package org.jsonx.sample.cdc;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jsonx.DecodeException;
import org.junit.Test;

public class ProtocolTest {
  @Test
  public void testConsumer1() throws DecodeException, IOException {
    final Product1 product = Consumer1.getProductFromProducer();
    assertEquals("v1", product.getVersion());
  }

  @Test
  public void testConsumer2() throws DecodeException, IOException {
    final Product2 product = Consumer2.getProductFromProducer();
    assertEquals("v2", product.getVersion());
    assertNotNull(product.getDescription());
  }

  @Test
  public void testBadVersion() {
    try {
      Producer.getProduct("v3");
      fail("Expected UnsupportedOperationException");
    }
    catch (final UnsupportedOperationException e) {
    }
  }
}