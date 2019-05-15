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

import java.math.BigInteger;

public class Producer {
  public static String getProduct(final String version) {
    final Product product;
    if ("v1".equals(version)) {
      final Product1 product1 = new Product1();
      product1.setVersion(version);
      product = product1;
    }
    else if ("v2".equals(version)) {
      final Product2 product2 = new Product2();
      product2.setVersion(version);
      product2.setDescription("An amazing pocket protector.");
      product = product2;
    }
    else {
      throw new UnsupportedOperationException("Unsupported product version: " + version);
    }

    product.setCatalogueID(BigInteger.valueOf((long)(Math.random() * 100000)));
    product.setName("Pocket Protector");
    product.setManufacturer("ACME");
    product.setInStock(Math.random() < .5);
    product.setPrice("$14.99");
    return product.toString();
  }
}