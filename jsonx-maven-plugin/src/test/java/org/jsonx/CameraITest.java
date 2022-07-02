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
import java.io.StringReader;

import org.junit.Test;
import org.openjax.json.JsonReader;

public class CameraITest {
  private static final String json = "{\"cameras\":[{\"ordinal\":0,\"state\":\"manual\",\"dois\":5,\"pan\":0.0,\"tilt\":0.0,\"zoom\":1.0},{\"ordinal\":1,\"state\":\"manual\",\"dois\":5}],\"periods\":[[1619506964,60]],\"captureDelay\":0}";

  @Test
  public void test() throws IOException, DecodeException {
    JxEncoder.set(JxEncoder.VALIDATING._2);
    JxDecoder.set(JxDecoder.VALIDATING);
    final oz.Capture capture;
    try (final JsonReader in = new JsonReader(new StringReader(json))) {
      capture = JxDecoder.get().parseObject(oz.Capture.class, in);
    }

    System.out.println(capture);
  }
}