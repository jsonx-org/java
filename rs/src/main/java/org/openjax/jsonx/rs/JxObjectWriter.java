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

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.openjax.jsonx.runtime.JxEncoder;
import org.openjax.jsonx.runtime.JxObject;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JxObjectWriter implements MessageBodyWriter<JxObject> {
  protected final JxEncoder encoder;

  public JxObjectWriter(final JxEncoder encoder) {
    this.encoder = encoder;
  }

  @Override
  public long getSize(final JxObject t, final Class<?> rawType, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
    return -1;
  }

  @Override
  public boolean isWriteable(final Class<?> rawType, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
    return JxObject.class.isAssignableFrom(rawType);
  }

  @Override
  public void writeTo(final JxObject t, final Class<?> rawType, final Type genericType, final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String,Object> httpHeaders, final OutputStream entityStream) throws IOException {
    final byte[] bytes = encoder.marshal(t).getBytes();
    entityStream.write(bytes);
    httpHeaders.putSingle(HttpHeaders.CONTENT_LENGTH, bytes.length);
  }
}