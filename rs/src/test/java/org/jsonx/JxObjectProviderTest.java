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

package org.jsonx;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import javax.ws.rs.core.MultivaluedHashMap;

import org.junit.Test;

public class JxObjectProviderTest {
  private static final JxObjectProvider provider = new JxObjectProvider(JxEncoder._0);

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

  @ObjectElement(id=0, type=Foo.class)
  @ArrayType(elementIds={0})
  @Retention(RetentionPolicy.RUNTIME)
  public @interface Foos {
  }

  @Foos
  private static final List<Foo> foos = null;

  @Test
  public void testFailureConditions() {
    assertFalse(provider.isReadable(String.class, String.class, null, null));
    assertFalse(provider.isWriteable(String.class, String.class, null, null));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testObject() throws IOException, ClassNotFoundException {
    final String data = "{\"bar\":\"hello\"}";
    assertTrue(provider.isReadable(Foo.class, Foo.class, null, null));
    final JxObject jxObject = (JxObject)provider.readFrom((Class<Object>)Class.forName(Foo.class.getName()), null, null, null, null, new ByteArrayInputStream(data.getBytes()));

    assertTrue(provider.isWriteable(Foo.class, Foo.class, null, null));
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    provider.writeTo(jxObject, Foo.class, Foo.class, null, null, new MultivaluedHashMap<String,Object>(), out);

    assertEquals(data, new String(out.toByteArray()));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testArrayType() throws IOException, ClassNotFoundException, NoSuchFieldException {
    final String data = "[{\"bar\":\"hello\"}]";
    final Annotation[] annotations = getClass().getDeclaredField("foos").getDeclaredAnnotations();
    assertTrue(provider.isReadable(List.class, List.class, annotations, null));
    final List<?> jxObject = (List<?>)provider.readFrom((Class<Object>)Class.forName(List.class.getName()), null, annotations, null, null, new ByteArrayInputStream(data.getBytes()));

    assertTrue(provider.isWriteable(List.class, Foo.class, annotations, null));
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    provider.writeTo(jxObject, List.class, List.class, annotations, null, new MultivaluedHashMap<String,Object>(), out);

    assertEquals(data, new String(out.toByteArray()));
  }
}