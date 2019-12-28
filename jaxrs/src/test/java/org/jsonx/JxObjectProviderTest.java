/* Copyright (c) 2018 JSONx
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

import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.MultivaluedHashMap;

import org.junit.Test;
import org.openjax.json.JsonParseException;

public class JxObjectProviderTest {
  private static final JxObjectProvider provider = new JxObjectProvider(JxEncoder._0);

  public static class Message implements JxObject {
    @StringProperty
    private String content;

    public String getContent() {
      return this.content;
    }

    public void setContent(String content) {
      this.content = content;
    }
  }

  @ObjectElement(id=0, type=Message.class)
  @ArrayType(elementIds={0})
  @Retention(RetentionPolicy.RUNTIME)
  public @interface Messages {
  }

  @Messages
  private static final List<Message> messages = null;

  @Test
  public void testFailureConditions() {
    assertFalse(provider.isReadable(String.class, String.class, null, null));
    assertFalse(provider.isWriteable(String.class, String.class, null, null));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testObject() throws IOException, ClassNotFoundException {
    final String data = "{\"content\":\"hello\"}";
    assertTrue(provider.isReadable(Message.class, Message.class, null, null));
    final JxObject jxObject = (JxObject)provider.readFrom((Class<Object>)Class.forName(Message.class.getName()), null, null, null, null, new ByteArrayInputStream(data.getBytes()));

    assertTrue(provider.isWriteable(Message.class, Message.class, null, null));
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    assertEquals(-1, provider.getSize(jxObject, Message.class, Message.class, null, null));
    provider.writeTo(jxObject, Message.class, Message.class, null, null, new MultivaluedHashMap<>(), out);

    assertEquals(data, new String(out.toByteArray()));
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testArrayType() throws IOException, ClassNotFoundException, NoSuchFieldException {
    final String data = "[{\"content\":\"hello\"}]";
    final Annotation[] annotations = getClass().getDeclaredField("messages").getDeclaredAnnotations();
    assertTrue(provider.isReadable(List.class, List.class, annotations, null));
    try {
      provider.readFrom((Class<Object>)Class.forName(List.class.getName()), null, annotations, null, null, new ByteArrayInputStream("nf989349".getBytes()));
      fail("Expected JsonParseException");
    }
    catch (final JsonParseException e) {
    }

    try {
      provider.readFrom((Class<Object>)Class.forName(List.class.getName()), null, annotations, null, null, new ByteArrayInputStream("[]".getBytes()));
      fail("Expected DecodeException");
    }
    catch (final RuntimeException e) {
      // FIXME: System.setProperty(RuntimeDelegate.JAXRS_RUNTIME_DELEGATE_PROPERTY, ??.class.getName());
      assertSame(ClassNotFoundException.class, e.getCause().getClass());
      boolean pass = false;
      for (final StackTraceElement el : e.getStackTrace()) {
        if (BadRequestException.class.getName().equals(el.getClassName())) {
          pass = true;
          break;
        }
      }

      assertTrue(pass);
    }

    final List<?> jxObject = (List<?>)provider.readFrom((Class<Object>)Class.forName(List.class.getName()), null, annotations, null, null, new ByteArrayInputStream(data.getBytes()));

    assertTrue(provider.isWriteable(List.class, Message.class, annotations, null));
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    provider.writeTo(jxObject, List.class, List.class, annotations, null, new MultivaluedHashMap<>(), out);

    assertEquals(data, new String(out.toByteArray()));
  }
}