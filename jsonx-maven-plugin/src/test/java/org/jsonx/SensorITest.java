/* Copyright (c) 2022 JSONx
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

import java.io.IOException;

import org.junit.Test;
import org.openjax.json.JsonReader;

public class SensorITest {
  private static final String json = "{\"sensors\":[{\"index\":0,\"mode\":\"auto\",\"facets\":5,\"x\":0.0,\"y\":0.0,\"z\":1.0},{\"index\":1,\"mode\":\"auto\",\"facets\":5}],\"sequences\":[[1619506964,60]],\"delay\":0}";

  @Test
  public void test() throws IOException, DecodeException {
    final stub.Assembly assembly;
    try (final JsonReader in = new JsonReader(json)) {
      assembly = JxDecoder.VALIDATING.parseObject(in, stub.Assembly.class);
    }

    System.out.println(assembly);
  }
}