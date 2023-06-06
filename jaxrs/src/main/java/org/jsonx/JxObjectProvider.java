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

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.channels.Channels;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

import javax.inject.Singleton;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.libj.io.Streams;
import org.openjax.json.JsonParseException;
import org.openjax.json.JsonReader;

/**
 * A JAX-RS {@link Provider} that implements {@link MessageBodyReader} and {@link MessageBodyWriter} support for reading and writing
 * JSON documents with the JSONX API.
 */
@Provider
@Singleton
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class JxObjectProvider implements MessageBodyReader<Object>, MessageBodyWriter<Object> {
  private static final int bufferSize = Streams.DEFAULT_SOCKET_BUFFER_SIZE;
  private static final Charset defaultCharset = StandardCharsets.UTF_8;

  protected static Charset getCharset(final MediaType mediaType) {
    if (mediaType == null)
      return defaultCharset;

    final String charsetParameter = mediaType.getParameters().get(MediaType.CHARSET_PARAMETER);
    return charsetParameter != null && Charset.isSupported(charsetParameter) ? Charset.forName(charsetParameter) : defaultCharset;
  }

  private final JxEncoder encoder;
  private final JxDecoder decoder;

  /**
   * Creates a new {@link JxObjectProvider} with the specified {@link JxEncoder} instance to be used for encoding bindings to JSON
   * documents.
   *
   * @param encoder The {@link JxEncoder} instance.
   * @param decoder The {@link JxDecoder} instance.
   * @throws NullPointerException If {@code encoder} or {@code decoder} is null.
   */
  public JxObjectProvider(final JxEncoder encoder, final JxDecoder decoder) {
    this.encoder = Objects.requireNonNull(encoder);
    this.decoder = Objects.requireNonNull(decoder);
  }

  /**
   * Creates a new {@link JxObjectProvider} with a null encoder and decoder.
   */
  protected JxObjectProvider() {
    this.encoder = null;
    this.decoder = null;
  }

  public JxEncoder getEncoder() {
    return encoder;
  }

  public JxDecoder getDecoder() {
    return decoder;
  }

  @Override
  public boolean isReadable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
    if (JxObject.class.isAssignableFrom(type))
      return true;

    if (!List.class.isAssignableFrom(type))
      return false;

    for (final Annotation annotation : annotations) { // [A]
      final Class<? extends Annotation> annotationType = annotation.annotationType();
      if (ArrayProperty.class.equals(annotationType) || annotationType.getDeclaredAnnotation(ArrayType.class) != null)
        return true;
    }

    return false;
  }

  @Override
  public boolean isWriteable(final Class<?> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
    return isReadable(type, genericType, annotations, mediaType);
  }

  @Override
  @SuppressWarnings({"rawtypes", "resource", "unchecked"})
  public Object readFrom(final Class<Object> type, final Type genericType, final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String,String> httpHeaders, final InputStream entityStream) throws IOException {
    try {
      if (JxObject.class.isAssignableFrom(type))
        return getDecoder().parseObject(new JsonReader(new InputStreamReader(entityStream)), (Class)type);

      for (final Annotation annotation : annotations) { // [A]
        final Class<? extends Annotation> annotationType = annotation.annotationType();
        if (ArrayProperty.class.equals(annotationType) || annotationType.getDeclaredAnnotation(ArrayType.class) != null)
          return getDecoder().parseArray(new JsonReader(new InputStreamReader(entityStream)), annotationType);
      }
    }
    catch (final JsonParseException | DecodeException e) {
      throw new BadRequestException(e);
    }

    throw new ProcessingException("Unknown type: " + type.getName());
  }

  @Override
  public long getSize(final Object t, final Class<?> rawType, final Type genericType, final Annotation[] annotations, final MediaType mediaType) {
    return -1;
  }

  private String marshal(final Object t, final Annotation[] annotations) {
    if (t instanceof JxObject)
      return getEncoder().toString((JxObject)t);

    if (t instanceof List) {
      final JxEncoder encoder = getEncoder();
      for (final Annotation annotation : annotations) { // [A]
        final Class<? extends Annotation> annotationType = annotation.annotationType();
        if (ArrayProperty.class.equals(annotationType) || annotationType.getDeclaredAnnotation(ArrayType.class) != null)
          return encoder.toString((List<?>)t, annotationType);
      }
    }

    return null;
  }

  @Override
  public void writeTo(final Object t, final Class<?> rawType, final Type genericType, final Annotation[] annotations, final MediaType mediaType, final MultivaluedMap<String,Object> httpHeaders, final OutputStream entityStream) throws IOException {
    final String json = marshal(t, annotations);
    if (json == null)
      throw new ProcessingException("Unknown type: " + rawType.getName());

    final Charset charset = getCharset(mediaType);
    if (bufferSize < json.length()) {
      final Writer writer = Channels.newWriter(Channels.newChannel(entityStream), charset.newEncoder(), bufferSize);
      writer.write(json);
      writer.flush();
    }
    else {
      final byte[] bytes = json.getBytes(charset);
      httpHeaders.putSingle(HttpHeaders.CONTENT_LENGTH, bytes.length);
      entityStream.write(bytes);
    }
  }
}