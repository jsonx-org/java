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

package org.openjax.jsonx.rs;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.ws.rs.core.MultivaluedHashMap;

import org.junit.Test;
import org.openjax.jsonx.runtime.JxEncoder;
import org.openjax.jsonx.runtime.JxObject;
import org.openjax.jsonx.runtime.StringProperty;

public class JxObjectReaderWriterTest {
  public static class Foo implements JxObject {
    @StringProperty
    private String bar;

    public String getBar() {
      return this.bar;
    }

    public void setBar(String bar) {
      this.bar = bar;
    }
  }

  @Test
  @SuppressWarnings("unchecked")
  public void test() throws IOException, ClassNotFoundException {
    final String data = "{\"bar\":\"hello\"}";
    final JxObjectReader reader = new JxObjectReader();
    assertFalse(reader.isReadable(String.class, String.class, null, null));
    assertTrue(reader.isReadable(Foo.class, Foo.class, null, null));
    final JxObject jxObject = reader.readFrom((Class<JxObject>)Class.forName(Foo.class.getName()), null, null, null, null, new ByteArrayInputStream(data.getBytes()));

    final JxObjectWriter writer = new JxObjectWriter(new JxEncoder(0));
    assertFalse(writer.isWriteable(String.class, String.class, null, null));
    assertTrue(writer.isWriteable(Foo.class, Foo.class, null, null));
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    writer.writeTo(jxObject, Foo.class, Foo.class, null, null, new MultivaluedHashMap<String,Object>(), out);

    assertEquals(data, new String(out.toByteArray()));
  }
}