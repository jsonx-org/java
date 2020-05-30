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

import java.io.IOException;
import java.io.StringReader;

import org.jsonx.DecodeException;
import org.jsonx.JxDecoder;
import org.openjax.json.JsonReader;

public class Consumer1 {
  public static Product1 getProductFromProducer() throws DecodeException, IOException {
    final String response = Producer.getProduct("v1");
    try (final JsonReader reader = new JsonReader(new StringReader(response))) {
      return JxDecoder.VALIDATING.parseObject(Product1.class, reader);
    }
  }
}