/* Copyright (c) 2018 OpenJAX
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

package org.openjax.jsonx.runtime;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import org.fastjax.json.JsonReader;
import org.junit.Test;

public class InvalidTest {
  private static final JxEncoder validEncoder = new JxEncoder(2, true);
  private static final JxEncoder invalidEncoder = new JxEncoder(2, false);

  @Test
  public void test() throws IOException, NoSuchFieldException {
    final Invalid.Bool i = new Invalid.Bool();
    i.setInvalidType(true);

    try {
      validEncoder.encode(i);
    }
    catch (final ValidationException e) {
      assertEquals("Invalid field: " + JsonxUtil.getFullyQualifiedFieldName(Invalid.Bool.class.getDeclaredField("invalidType")), e.getMessage());
    }

    try {
      final String json = "{\"invalidType\": true}";
      JxDecoder.parseObject(Invalid.Bool.class, new JsonReader(new StringReader(json)));
    }
    catch (final DecodeException e) {
      assertTrue(e.getMessage().startsWith("Expected \"invalidType\" to be a \"number\", but got: true"));
    }
  }
}