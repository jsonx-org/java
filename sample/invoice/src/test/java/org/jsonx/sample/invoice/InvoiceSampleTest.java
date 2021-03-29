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

package org.jsonx.sample.invoice;

import static org.junit.Assert.*;

import java.io.IOException;

import org.jsonx.DecodeException;
import org.jsonx.JxDecoder;
import org.jsonx.JxEncoder;
import org.junit.Test;

public class InvoiceSampleTest {
  @Test
  public void test() throws DecodeException, IOException {
    final Invoice invoice = InvoiceSample.createInvoice();
    final String json = JxEncoder.VALIDATING._2.toString(invoice);
    assertEquals(invoice, JxDecoder.VALIDATING.parseObject(Invoice.class, json));
  }
}