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

package org.jsonx;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;

import org.jsonx.invoice.Address;
import org.jsonx.invoice.Invoice;
import org.jsonx.invoice.Item;
import org.junit.Test;
import org.openjax.json.JsonReader;

public class InvoiceTest {
  static {
    JxEncoder.set(JxEncoder._2);
  }

  @Test
  public void test() throws DecodeException, IOException {
    final Address address = new Address();
    address.setName("John Doe");
    address.setAddress("111 Wall St.");
    address.setCity("New York");
    address.setPostalCode("10043");
    address.setCountry("USA");

    final Item item = new Item();
    item.setCode(BigInteger.valueOf(123));
    item.setDescription("Pocket Protector");
    item.setPrice(new BigDecimal("14.99"));
    item.setQuantity(BigInteger.valueOf(5));

    final Invoice invoice = new Invoice();
    invoice.setNumber(BigInteger.valueOf(14738));
    invoice.setDate("2019-05-13");
    invoice.setBillingAddress(address);
    invoice.setShippingAddress(address);
    invoice.setBilledItems(Collections.singletonList(item));

    final String json = invoice.toString();
    assertEquals(invoice, JxDecoder.parseObject(Invoice.class, new JsonReader(new StringReader(json))));
  }
}