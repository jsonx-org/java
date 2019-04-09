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

package org.openjax.jsonx;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.openjax.standard.json.JsonReader;

/**
 * A JAX-RS {@link Provider} that implements {@link MessageBodyReader} and
 * {@link MessageBodyWriter} support for reading and writing JSON documents with
 * the JSONX API.
 */
@Provider
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JxObjectProvider implements MessageBodyReader<Object>, MessageBodyWriter<Object> {
  protected final JxEncoder encoder;

  /**
   * Creates a new {@code JxObjectProvider} with the specified {@code JxEncoder}
   * instance to be used for encoding bindings to JSON documents.
   *
   * @param encoder The {@code JxEncoder} instance.
   */
  public JxObjectProvider(final JxEncoder encoder) {
    this.encoder = encoder;
  }

  @Override
  public boolean isReadable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
    if (JxObject.class.isAssignableFrom(type))
      return true;

    if (!List.class.isAssignableFrom(type))
      return false;

    for (final Annotation annotation : annotations) {
      if (ArrayProperty.class.equals(annotation.annotationType()))
        return true;

      final ArrayType arrayType = annotation.annotationType().getDeclaredAnnotation(ArrayType.class);
      if (arrayType != null)
        return true;
    }

    return false;
  }

  @Override
  public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
    return isReadable(type, genericType, annotations, mediaType);
  }

  @Override
  @SuppressWarnings({"rawtypes", "unchecked"})
  public Object readFrom(final Class<Object> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String,String> httpHeaders, final InputStream entityStream) throws IOException {
    try {
      if (JxObject.class.isAssignableFrom(type))
        return JxDecoder.parseObject((Class)type, new JsonReader(new InputStreamReader(entityStream)));

      for (final Annotation annotation : annotations) {
        final Class<? extends Annotation> annotationType;
        if (ArrayProperty.class.equals(annotation.annotationType())) {
          annotationType = annotation.annotationType();
        }
        else {
          final ArrayType arrayType = annotation.annotationType().getDeclaredAnnotation(ArrayType.class);
          annotationType = arrayType == null ? null : annotation.annotationType();
        }

        if (annotationType != null)
          return JxDecoder.parseArray(annotationType, new JsonReader(new InputStreamReader(entityStream)));
      }

      throw new IllegalArgumentException("Illegal type: " + type.getName());
    }
    catch (final DecodeException e) {
      throw new BadRequestException(e);
    }
  }

  @Override
  public long getSize(final Object t, final Class<?> rawType, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
    return -1;
  }

  @Override
  public void writeTo(final Object t, final Class<?> rawType, final Type genericType, final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String,Object> httpHeaders, final OutputStream entityStream) throws IOException {
    byte[] bytes = null;
    if (t instanceof JxObject) {
      bytes = encoder.marshal((JxObject)t).getBytes();
    }
    else if (t instanceof List) {
      for (final Annotation annotation : annotations) {
        final Class<? extends Annotation> annotationType;
        if (ArrayProperty.class.equals(annotation.annotationType())) {
          annotationType = annotation.annotationType();
        }
        else {
          final ArrayType arrayType = annotation.annotationType().getDeclaredAnnotation(ArrayType.class);
          annotationType = arrayType == null ? null : annotation.annotationType();
        }

        if (annotationType != null) {
          bytes = encoder.marshal((List<?>)t, annotationType).getBytes();
          break;
        }
      }
    }

    if (bytes == null)
      throw new IllegalArgumentException("Illegal type: " + rawType.getName());

    entityStream.write(bytes);
  }
}