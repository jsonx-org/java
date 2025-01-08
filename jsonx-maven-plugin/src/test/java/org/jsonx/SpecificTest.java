/* Copyright (c) 2023 JSONx
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

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.jsonx.test.array.ArrayNull;
import org.jsonx.test.array.Geometry;
import org.junit.Test;
import org.openjax.json.JsonReader;

public class SpecificTest {
  @Test
  public void testAny() throws IOException, DecodeException {
    JxDecoder.VALIDATING.parseObject("{\"coordinates\": [[3186,4096],[3197,0]]}", Geometry.class);
    JxDecoder.VALIDATING.parseObject("{\"coordinates\": [[[3186,4096],[3197,0]]]}", Geometry.class);
  }

  @Test
  public void testArrayNull() throws DecodeException, IOException {
    try (final JsonReader in = new JsonReader(new StringReader("null"))) {
      assertNull(JxDecoder.VALIDATING.parseArray(in, ArrayNull.class));
    }
  }

  @Test
  public void testArrayNulls() throws DecodeException, IOException {
    try (final JsonReader in = new JsonReader(new StringReader("[false,0,\"a\",true,[true,1,\"b\",2],[false,3,\"c\",\"d\",[true,4,\"e\",true,[false,5,\"f\",1,[true,6,\"g\",\"h\"]]],[false,7,\"i\",true]]]"))) {
      final ArrayList<?> array = JxDecoder.VALIDATING.parseArray(in, ArrayNull.class);
      assertEquals("[null, null, null, null, [null, null, null, null], [null, null, null, null, [null, null, null, null, [null, null, null, null, [null, null, null, null]]], [null, null, null, null]]]", array.toString());
    }
  }
}