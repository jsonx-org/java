/* Copyright (c) 2016 JSONx
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
import java.util.function.Function;

import org.libj.io.Readers;
import org.libj.lang.ParseException;
import org.openjax.json.JsonReader;

/**
 * Signals that an error has occurred while decoding a JSON document to binding
 * classes.
 */
public class DecodeException extends ParseException {
  private static final long serialVersionUID = -8659795467901573156L;

  private static int getErrorOffset(final JsonReader reader) {
    return reader != null ? reader.getPosition() - 1 : -1;
  }

  private static String readerToString(final JsonReader reader) {
    final int index = reader.getIndex();
    reader.setIndex(-1);
    final StringBuilder builder = new StringBuilder();
    try {
      Readers.readFully(reader, builder);
    }
    catch (final IOException e) {
    }

    reader.setIndex(index);
    return builder.toString();
  }

  private final JsonReader reader;
  private String message;

  /**
   * Creates a new {@link DecodeException} with the specified detail message,
   * offset, cause, and message function.
   *
   * @param message The detail message that describes this particular exception.
   * @param reader The {@link JsonReader} in which the error was found while parsing.
   * @param cause The cause.
   * @param messageFunction The {@link Function
   *          Function&lt;DecodeException,String&gt;} that is to be used by the
   *          {@link DecodeException} class for the construction of each new
   *          instance's detail {@linkplain DecodeException#getMessage()
   *          message}.
   */
  DecodeException(final String message, final JsonReader reader, final Throwable cause, final Function<DecodeException,String> messageFunction) {
    super(message, getErrorOffset(reader), cause);
    this.message = message;
    this.reader = reader;
    if (messageFunction != null)
      this.message = messageFunction.apply(this);
  }

  /**
   * Creates a new {@link DecodeException} with the specified {@link Error}, and
   * message function.
   *
   * @param error The {@link Error}.
   * @param reader The {@link JsonReader} in which the error was found while
   *          parsing.
   * @param messageFunction The {@link Function
   *          Function&lt;DecodeException,String&gt;} that is to be used by the
   *          {@link DecodeException} class for the construction of each new
   *          instance's detail {@linkplain DecodeException#getMessage()
   *          message}.
   */
  DecodeException(final Error error, final JsonReader reader, final Function<DecodeException,String> messageFunction) {
    this(error.toString(), reader, null, messageFunction);
  }

  /**
   * Creates a new {@link DecodeException} with the specified detail message and
   * offset.
   *
   * @param message The detail message that describes this particular exception.
   * @param reader The {@link JsonReader} in which the error was found while
   *          parsing.
   */
  public DecodeException(final String message, final JsonReader reader) {
    this(message, reader, null);
  }

  /**
   * Creates a new {@link DecodeException} with the specified detail message,
   * offset, and cause.
   *
   * @param message The detail message that describes this particular exception.
   * @param reader The {@link JsonReader} in which the error was found while
   *          parsing.
   * @param cause The cause.
   */
  public DecodeException(final String message, final JsonReader reader, final Throwable cause) {
    this(message, reader, cause, null);
  }

  @Override
  public String getMessage() {
    return message;
  }

  /**
   * Returns the {@link JsonReader} which was being read that instigated the
   * exception.
   *
   * @return The {@link JsonReader} which was being read that instigated the
   *         exception.
   */
  public JsonReader getReader() {
    return this.reader;
  }

  /**
   * Returns the JSON which was being read that instigated the exception.
   *
   * @return The JSON which was being read that instigated the exception.
   */
  public String getContent() {
    return reader == null ? null : readerToString(reader);
  }
}