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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.fastjax.json.JsonReader;
import org.openjax.jsonx.runtime.DecodeException;
import org.openjax.jsonx.runtime.JxDecoder;
import org.openjax.jsonx.runtime.JxObject;

@Provider
@Consumes(MediaType.APPLICATION_JSON)
public class JxObjectReader implements MessageBodyReader<JxObject> {
  @Override
  public boolean isReadable(final Class<?> rawType, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
    return JxObject.class.isAssignableFrom(rawType);
  }

  @Override
  public JxObject readFrom(final Class<JxObject> rawType, final Type genericType, final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String,String> httpHeaders, final InputStream entityStream) throws IOException {
    try {
      return JxDecoder.parseObject(rawType, new JsonReader(new InputStreamReader(entityStream)));
    }
    catch (final DecodeException e) {
      throw new BadRequestException(e);
    }
  }
}