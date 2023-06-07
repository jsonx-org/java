/* Copyright (c) 2015 JSONx
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

import static org.libj.lang.Assertions.*;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

import org.libj.lang.Numbers.Composite;
import org.libj.lang.Throwables;
import org.libj.util.function.TriPredicate;
import org.openjax.json.JsonParseException;
import org.openjax.json.JsonReader;

/**
 * Decoder that deserializes JSON documents to objects of {@link JxObject} classes, or to lists conforming to a provided annotation
 * class that declares an {@link ArrayType} annotation.
 */
public final class JxDecoder {
  public static final class Builder {
    private boolean validate;

    /**
     * Sets the "validation" option for the {@link JxDecoder}, specifying whether validation is to occur while decoding JSON
     * documents.
     *
     * @param validation The "validation" option for the {@link JxDecoder}, specifying whether validation is to occur while decoding
     *          JSON documents.
     * @return This {@link Builder}.
     */
    public Builder withValidation(final boolean validation) {
      this.validate = validation;
      return this;
    }

    private Function<DecodeException,String> messageFunction;

    /**
     * Sets a {@link Function Function&lt;DecodeException,String&gt;} that is to be used by the {@link DecodeException} class for
     * the construction of each new instance's detail {@linkplain DecodeException#getMessage() message}.
     *
     * @param messageFunction The {@link Function Function&lt;DecodeException,String&gt;} that is to be used by the
     *          {@link DecodeException} class for the construction of each new instance's detail
     *          {@linkplain DecodeException#getMessage() message}.
     * @return This {@link Builder}.
     */
    public Builder withDecodeExceptionMessageFunction(final Function<DecodeException,String> messageFunction) {
      this.messageFunction = messageFunction;
      return this;
    }

    /**
     * Returns a new instance of {@link JxDecoder} with the options specified in this {@link Builder}.
     *
     * @return A new instance of {@link JxDecoder} with the options specified in this {@link Builder}.
     */
    public JxDecoder build() {
      return new JxDecoder(validate, messageFunction);
    }
  }

  public static JxDecoder VALIDATING = new JxDecoder(true, null);
  public static JxDecoder NON_VALIDATING = new JxDecoder(false, null);

  private static JxDecoder global = new JxDecoder(false);

  /**
   * Returns a new {@link Builder JxDecoder.Builder}.
   *
   * @return A new {@link Builder JxDecoder.Builder}.
   */
  public static Builder newBuilder() {
    return new Builder();
  }

  /**
   * Returns the global {@link JxDecoder}.
   *
   * @return The global {@link JxDecoder}.
   * @see #set(JxDecoder)
   */
  public static JxDecoder get() {
    return global;
  }

  /**
   * Set the global {@link JxDecoder}.
   *
   * @param decoder The {@link JxDecoder}.
   * @return The provided {@link JxDecoder}.
   * @see #get()
   */
  public static JxDecoder set(final JxDecoder decoder) {
    return global = decoder;
  }

  private final boolean validate;
  private final Function<DecodeException,String> messageFunction;

  private JxDecoder(final boolean validate, final Function<DecodeException,String> messageFunction) {
    this.validate = validate;
    this.messageFunction = messageFunction;
  }

  private JxDecoder(final boolean validate) {
    this(validate, null);
  }

  private Object parseObject(final JsonReader reader, final TriPredicate<JxObject,String,Object> onPropertyDecode, final Class<? extends JxObject> type, final DecodeException exception) throws IOException, JsonParseException {
    reader.setIndex(-1);
    final long point = reader.readToken();
    final int off = Composite.decodeInt(point, 0);
    final char c0 = reader.bufToChar(off);
    if (c0 != '{')
      return Throwables.addSuppressed(exception, new DecodeException("Expected '{', but got '" + point + "\"", reader, null, messageFunction));

    final Object object = ObjectCodec.decodeObject(type, reader, validate, onPropertyDecode);
    if (!(object instanceof Error))
      return object;

    final Error error = (Error)object;
    return Throwables.addSuppressed(exception, new DecodeException(error, reader, error.getException(), messageFunction));
  }

  /**
   * Parses a JSON object from the provided {@link JsonReader} supplying the JSON document as per the specification of the provided
   * {@link JxObject} class defining the schema of the document. This method attempts to parse the supplied JSON document as per the
   * specification of the provided {@link JxObject} class, and returns the parsed JSON object, or throws a {@link DecodeException}
   * if the provided {@link JxObject} class could parse the supplied document.
   *
   * @param <T> The type parameter for the return object of {@link JxObject} class.
   * @param reader The {@link JsonReader} containing the JSON object.
   * @param onPropertyDecode Callback predicate to be called for each decoded JSON properties, accepting arguments of:
   *          <ol>
   *          <li>The {@link JxObject}.</li>
   *          <li>The property name.</li>
   *          <li>The property value.</li>
   *          </ol>
   * @param type The {@link JxObject} class defining the schema for the return object.
   * @return A {@link JxObject} of one of the specified {@code types} representing the parsed JSON object.
   * @throws DecodeException If an exception has occurred while decoding the JSON object.
   * @throws JsonParseException If the content is not well formed.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code reader} or {@code type} is null.
   */
  @SuppressWarnings("unchecked")
  public final <T extends JxObject>T parseObject(final JsonReader reader, final TriPredicate<JxObject,String,Object> onPropertyDecode, final Class<? extends T> type) throws DecodeException, IOException, JsonParseException {
    final Object result = parseObject(reader, onPropertyDecode, type, null);
    if (result instanceof DecodeException)
      throw (DecodeException)result;

    return (T)result;
  }

  /**
   * Parses a JSON object from the provided string supplying the JSON document as per the specification of the provided
   * {@link JxObject} class defining the schema of the document. This method attempts to parse the supplied JSON document as per the
   * specification of the provided {@link JxObject} class, and returns the parsed JSON object, or throws a {@link DecodeException}
   * if the provided {@link JxObject} class could parse the supplied document.
   *
   * @param <T> The type parameter for the return object of {@link JxObject} class.
   * @param json The string document containing a JSON object.
   * @param onPropertyDecode Callback predicate to be called for each decoded JSON properties, accepting arguments of:
   *          <ol>
   *          <li>The {@link JxObject}.</li>
   *          <li>The property name.</li>
   *          <li>The property value.</li>
   *          </ol>
   * @param type The {@link JxObject} class defining the schema for the return object.
   * @return A {@link JxObject} of one of the specified {@code types} representing the parsed JSON object.
   * @throws DecodeException If an exception has occurred while decoding the JSON object.
   * @throws JsonParseException If the content is not well formed.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code json} or {@code type} is null.
   */
  public final <T extends JxObject>T parseObject(final String json, final TriPredicate<JxObject,String,Object> onPropertyDecode, final Class<? extends T> type) throws DecodeException, IOException, JsonParseException {
    try (final JsonReader in = new JsonReader(json)) {
      return parseObject(in, onPropertyDecode, type);
    }
  }

  /**
   * Parses a JSON object from the provided {@link JsonReader} supplying the JSON document as per the specification of the provided
   * {@link JxObject} class defining the schema of the document. This method attempts to parse the supplied JSON document as per the
   * specification of the provided {@link JxObject} class, and returns the parsed JSON object, or throws a {@link DecodeException}
   * if the provided {@link JxObject} class could parse the supplied document.
   *
   * @param <T> The type parameter for the return object of {@link JxObject} class.
   * @param reader The {@link JsonReader} containing the JSON object.
   * @param type The {@link JxObject} class defining the schema for the return object.
   * @return A {@link JxObject} of one of the specified {@code types} representing the parsed JSON object.
   * @throws DecodeException If an exception has occurred while decoding the JSON object.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code reader} or {@code type} is null.
   */
  public final <T extends JxObject>T parseObject(final JsonReader reader, final Class<? extends T> type) throws DecodeException, IOException {
    return parseObject(reader, null, type);
  }

  /**
   * Parses a JSON object from the provided string supplying the JSON document as per the specification of the provided
   * {@link JxObject} class defining the schema of the document. This method attempts to parse the supplied JSON document as per the
   * specification of the provided {@link JxObject} class, and returns the parsed JSON object, or throws a {@link DecodeException}
   * if the provided {@link JxObject} class could parse the supplied document.
   *
   * @param <T> The type parameter for the return object of {@link JxObject} class.
   * @param json The string document containing a JSON object.
   * @param type The {@link JxObject} class defining the schema for the return object.
   * @return A {@link JxObject} of one of the specified {@code types} representing the parsed JSON object.
   * @throws DecodeException If an exception has occurred while decoding the JSON object.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code json} or {@code type} is null.
   */
  public final <T extends JxObject>T parseObject(final String json, final Class<? extends T> type) throws DecodeException, IOException {
    try (final JsonReader in = new JsonReader(json)) {
      return parseObject(in, type);
    }
  }

  /**
   * Parses a JSON object from the provided {@link JsonReader} supplying the JSON document as per the specification of the provided
   * {@link JxObject} classes defining the schema of the document. This method attempts to parse the supplied JSON document as per
   * the specification of the provided {@link JxObject} classes in order, and returns the first successfully parsed JSON object, or
   * throws a {@link DecodeException} if none of the provided {@link JxObject} classes could parse the supplied document.
   *
   * @param <T> The type parameter for the return object of {@link JxObject} class.
   * @param reader The {@link JsonReader} containing the JSON object.
   * @param onPropertyDecode Callback predicate to be called for each decoded JSON properties, accepting arguments of:
   *          <ol>
   *          <li>The {@link JxObject}.</li>
   *          <li>The property name.</li>
   *          <li>The property value.</li>
   *          </ol>
   * @param types The {@link JxObject} classes defining the schema for the return object.
   * @return A {@link JxObject} of one of the specified {@code types} representing the parsed JSON object.
   * @throws DecodeException If an exception has occurred while decoding the JSON object.
   * @throws JsonParseException If the content is not well formed.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code reader} is null.
   * @throws IllegalArgumentException If {@code types} is null or empty.
   */
  @SafeVarargs
  @SuppressWarnings({"null", "unchecked"})
  public final <T extends JxObject>T parseObject(final JsonReader reader, final TriPredicate<JxObject,String,Object> onPropertyDecode, final Class<? extends T> ... types) throws DecodeException, IOException, JsonParseException {
    DecodeException exception = null;
    for (final Class<? extends T> type : assertNotEmpty(types)) {
      final Object result = parseObject(reader, onPropertyDecode, type, exception);
      if (result instanceof DecodeException)
        exception = (DecodeException)result;
      else
        return (T)result;
    }

    throw exception;
  }

  /**
   * Parses a JSON object from the provided {@link JsonReader} supplying the JSON document as per the specification of the provided
   * {@link JxObject} classes defining the schema of the document. This method attempts to parse the supplied JSON document as per
   * the specification of the provided {@link JxObject} classes in order, and returns the first successfully parsed JSON object, or
   * throws a {@link DecodeException} if none of the provided {@link JxObject} classes could parse the supplied document.
   *
   * @param <T> The type parameter for the return object of {@link JxObject} class.
   * @param reader The {@link JsonReader} containing the JSON object.
   * @param onPropertyDecode Callback predicate to be called for each decoded JSON properties, accepting arguments of:
   *          <ol>
   *          <li>The {@link JxObject}.</li>
   *          <li>The property name.</li>
   *          <li>The property value.</li>
   *          </ol>
   * @param types The {@link JxObject} classes defining the schema for the return object.
   * @return A {@link JxObject} of one of the specified {@code types} representing the parsed JSON object.
   * @throws DecodeException If an exception has occurred while decoding the JSON object.
   * @throws JsonParseException If the content is not well formed.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code reader} is null.
   * @throws IllegalArgumentException If {@code types} is null or empty.
   */
  @SuppressWarnings({"null", "unchecked"})
  public final <T extends JxObject>T parseObject(final JsonReader reader, final TriPredicate<JxObject,String,Object> onPropertyDecode, final Collection<Class<? extends T>> types) throws DecodeException, IOException, JsonParseException {
    DecodeException exception = null;
    for (final Class<? extends T> type : assertNotEmpty(types)) {
      final Object result = parseObject(reader, onPropertyDecode, type, exception);
      if (result instanceof DecodeException)
        exception = (DecodeException)result;
      else
        return (T)result;
    }

    throw exception;
  }

  /**
   * Parses a JSON object from the provided string supplying the JSON document as per the specification of the provided
   * {@link JxObject} classes defining the schema of the document. This method attempts to parse the supplied JSON document as per
   * the specification of the provided {@link JxObject} classes in order, and returns the first successfully parsed JSON object, or
   * throws a {@link DecodeException} if none of the provided {@link JxObject} classes could parse the supplied document.
   *
   * @param <T> The type parameter for the return object of {@link JxObject} class.
   * @param json The string document containing a JSON object.
   * @param onPropertyDecode Callback predicate to be called for each decoded JSON properties, accepting arguments of:
   *          <ol>
   *          <li>The {@link JxObject}.</li>
   *          <li>The property name.</li>
   *          <li>The property value.</li>
   *          </ol>
   * @param types The {@link JxObject} classes defining the schema for the return object.
   * @return A {@link JxObject} of one of the specified {@code types} representing the parsed JSON object.
   * @throws DecodeException If an exception has occurred while decoding the JSON object.
   * @throws JsonParseException If the content is not well formed.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code json} is null.
   * @throws IllegalArgumentException If {@code types} is null or empty.
   */
  @SafeVarargs
  public final <T extends JxObject>T parseObject(final String json, final TriPredicate<JxObject,String,Object> onPropertyDecode, final Class<? extends T> ... types) throws DecodeException, IOException, JsonParseException {
    try (final JsonReader in = new JsonReader(json)) {
      return parseObject(in, onPropertyDecode, types);
    }
  }

  /**
   * Parses a JSON object from the provided string supplying the JSON document as per the specification of the provided
   * {@link JxObject} classes defining the schema of the document. This method attempts to parse the supplied JSON document as per
   * the specification of the provided {@link JxObject} classes in order, and returns the first successfully parsed JSON object, or
   * throws a {@link DecodeException} if none of the provided {@link JxObject} classes could parse the supplied document.
   *
   * @param <T> The type parameter for the return object of {@link JxObject} class.
   * @param json The string document containing a JSON object.
   * @param onPropertyDecode Callback predicate to be called for each decoded JSON properties, accepting arguments of:
   *          <ol>
   *          <li>The {@link JxObject}.</li>
   *          <li>The property name.</li>
   *          <li>The property value.</li>
   *          </ol>
   * @param types The {@link JxObject} classes defining the schema for the return object.
   * @return A {@link JxObject} of one of the specified {@code types} representing the parsed JSON object.
   * @throws DecodeException If an exception has occurred while decoding the JSON object.
   * @throws JsonParseException If the content is not well formed.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code json} is null.
   * @throws IllegalArgumentException If {@code types} is null or empty.
   */
  public final <T extends JxObject>T parseObject(final String json, final TriPredicate<JxObject,String,Object> onPropertyDecode, final Collection<Class<? extends T>> types) throws DecodeException, IOException, JsonParseException {
    try (final JsonReader in = new JsonReader(json)) {
      return parseObject(in, onPropertyDecode, types);
    }
  }

  /**
   * Parses a JSON object from the provided {@link JsonReader} supplying the JSON document as per the specification of the provided
   * {@link JxObject} classes defining the schema of the document. This method attempts to parse the supplied JSON document as per
   * the specification of the provided {@link JxObject} classes in order, and returns the first successfully parsed JSON object, or
   * throws a {@link DecodeException} if none of the provided {@link JxObject} classes could parse the supplied document.
   *
   * @param <T> The type parameter for the return object of {@link JxObject} class.
   * @param reader The {@link JsonReader} containing the JSON object.
   * @param types The {@link JxObject} classes defining the schema for the return object.
   * @return A {@link JxObject} of one of the specified {@code types} representing the parsed JSON object.
   * @throws DecodeException If an exception has occurred while decoding the JSON object.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code reader} is null.
   * @throws IllegalArgumentException If {@code types} is null or empty.
   */
  @SafeVarargs
  public final <T extends JxObject>T parseObject(final JsonReader reader, final Class<? extends T> ... types) throws DecodeException, IOException {
    return parseObject(reader, null, types);
  }

  /**
   * Parses a JSON object from the provided {@link JsonReader} supplying the JSON document as per the specification of the provided
   * {@link JxObject} classes defining the schema of the document. This method attempts to parse the supplied JSON document as per
   * the specification of the provided {@link JxObject} classes in order, and returns the first successfully parsed JSON object, or
   * throws a {@link DecodeException} if none of the provided {@link JxObject} classes could parse the supplied document.
   *
   * @param <T> The type parameter for the return object of {@link JxObject} class.
   * @param reader The {@link JsonReader} containing the JSON object.
   * @param types The {@link JxObject} classes defining the schema for the return object.
   * @return A {@link JxObject} of one of the specified {@code types} representing the parsed JSON object.
   * @throws DecodeException If an exception has occurred while decoding the JSON object.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code reader} is null.
   * @throws IllegalArgumentException If {@code types} is null or empty.
   */
  public final <T extends JxObject>T parseObject(final JsonReader reader, final Collection<Class<? extends T>> types) throws DecodeException, IOException {
    return parseObject(reader, null, types);
  }

  /**
   * Parses a JSON object from the provided string supplying the JSON document as per the specification of the provided
   * {@link JxObject} classes defining the schema of the document. This method attempts to parse the supplied JSON document as per
   * the specification of the provided {@link JxObject} classes in order, and returns the first successfully parsed JSON object, or
   * throws a {@link DecodeException} if none of the provided {@link JxObject} classes could parse the supplied document.
   *
   * @param <T> The type parameter for the return object of {@link JxObject} class.
   * @param json The string document containing a JSON object.
   * @param types The {@link JxObject} classes defining the schema for the return object.
   * @return A {@link JxObject} of one of the specified {@code types} representing the parsed JSON object.
   * @throws DecodeException If an exception has occurred while decoding the JSON object.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code json} is null.
   * @throws IllegalArgumentException If {@code types} is null or empty.
   */
  @SafeVarargs
  public final <T extends JxObject>T parseObject(final String json, final Class<? extends T> ... types) throws DecodeException, IOException {
    try (final JsonReader in = new JsonReader(json)) {
      return parseObject(in, types);
    }
  }

  /**
   * Parses a JSON object from the provided string supplying the JSON document as per the specification of the provided
   * {@link JxObject} classes defining the schema of the document. This method attempts to parse the supplied JSON document as per
   * the specification of the provided {@link JxObject} classes in order, and returns the first successfully parsed JSON object, or
   * throws a {@link DecodeException} if none of the provided {@link JxObject} classes could parse the supplied document.
   *
   * @param <T> The type parameter for the return object of {@link JxObject} class.
   * @param json The string document containing a JSON object.
   * @param types The {@link JxObject} classes defining the schema for the return object.
   * @return A {@link JxObject} of one of the specified {@code types} representing the parsed JSON object.
   * @throws DecodeException If an exception has occurred while decoding the JSON object.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code json} is null.
   * @throws IllegalArgumentException If {@code types} is null or empty.
   */
  public final <T extends JxObject>T parseObject(final String json, final Collection<Class<? extends T>> types) throws DecodeException, IOException {
    try (final JsonReader in = new JsonReader(json)) {
      return parseObject(in, types);
    }
  }

  private Object parseArray(final JsonReader reader, final Class<? extends Annotation> annotationType, final DecodeException exception) throws JsonParseException, IOException {
    reader.setIndex(-1);
    final long point = reader.readToken();
    final int off = Composite.decodeInt(point, 0);
    final char c0 = reader.bufToChar(off);
    if (c0 != '[')
      return Throwables.addSuppressed(exception, new DecodeException("Expected '[', but got '" + reader.bufToString(off, Composite.decodeInt(point, 1)) + "'", reader, null, messageFunction));

    final IdToElement idToElement = new IdToElement();
    final int[] elementIds = JsdUtil.digest(annotationType.getAnnotations(), annotationType.getName(), idToElement);
    final Object array = ArrayCodec.decodeObject(idToElement.get(elementIds), idToElement.getMinIterate(), idToElement.getMaxIterate(), idToElement, reader, validate, null);
    if (!(array instanceof Error))
      return array;

    final Error error = (Error)array;
    return Throwables.addSuppressed(exception, new DecodeException(error, reader, error.getException(), messageFunction));
  }

  /**
   * Parses a JSON array from the provided {@link JsonReader} supplying the JSON document as per the specification of the provided
   * annotation class that declares an {@link ArrayType} annotation defining the schema of the document. This
   * method attempts to parse the supplied JSON document as per the specification of the provided annotation class, and
   * returns the parsed JSON array, or throws a {@link DecodeException} if the provided annotation
   * class could parse the supplied document.
   *
   * @param reader The {@link JsonReader} containing the JSON array.
   * @param annotationType The annotation class declaring an {@link ArrayType} annotation defining the schema for the return
   *          {@link ArrayList}.
   * @return An {@link ArrayList} representing the parsed JSON array.
   * @throws DecodeException If an exception has occurred while decoding the JSON array.
   * @throws JsonParseException If the content is not well formed.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code reader} or {@code type} is null.
   */
  public final ArrayList<?> parseArray(final JsonReader reader, final Class<? extends Annotation> annotationType) throws DecodeException, JsonParseException, IOException {
    final Object result = parseArray(reader, annotationType, null);
    if (result instanceof DecodeException)
      throw (DecodeException)result;

    return (ArrayList<?>)result;
  }

  /**
   * Parses a JSON array from the provided string supplying the JSON document as per the specification of the provided annotation
   * class that declares an {@link ArrayType} annotation defining the schema of the document. This method attempts to parse the
   * supplied JSON document as per the specification of the provided annotation class, and returns the parsed JSON array, or throws
   * a {@link DecodeException} if the provided annotation class could parse the supplied document.
   *
   * @param json The string document containing a JSON array.
   * @param annotationType The annotation class declaring an {@link ArrayType} annotation defining the schema for the return
   *          {@link ArrayList}.
   * @return An {@link ArrayList} representing the parsed JSON array.
   * @throws DecodeException If an exception has occurred while decoding the JSON array.
   * @throws JsonParseException If the content is not well formed.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code json} or {@code annotationType} is null.
   */
  public final ArrayList<?> parseArray(final String json, final Class<? extends Annotation> annotationType) throws DecodeException, JsonParseException, IOException {
    try (final JsonReader in = new JsonReader(json)) {
      return parseArray(in, annotationType);
    }
  }

  /**
   * Parses a JSON array from the provided {@link JsonReader} supplying the JSON document as per the specification of the provided
   * annotation classes that declare an {@link ArrayType} annotation defining the schema of the document. This
   * method attempts to parse the supplied JSON document as per the specification of the provided annotation classes in order, and
   * returns the first successfully parsed JSON array, or throws a {@link DecodeException} if none of the provided annotation
   * classes could parse the supplied document.
   *
   * @param reader The {@link JsonReader} containing the JSON array.
   * @param annotationTypes The annotation classes declaring an {@link ArrayType} annotation defining the schema for the return
   *          {@link ArrayList}.
   * @return An {@link ArrayList} representing the parsed JSON array.
   * @throws DecodeException If an exception has occurred while decoding the JSON array.
   * @throws JsonParseException If the content is not well formed.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code reader} is null.
   * @throws IllegalArgumentException If {@code annotationTypes} is null or empty.
   */
  @SafeVarargs
  @SuppressWarnings("null")
  public final ArrayList<?> parseArray(final JsonReader reader, final Class<? extends Annotation> ... annotationTypes) throws DecodeException, JsonParseException, IOException {
    DecodeException exception = null;
    for (final Class<? extends Annotation> annotationType : assertNotEmpty(annotationTypes)) {
      final Object result = parseArray(reader, annotationType, exception);
      if (result instanceof DecodeException)
        exception = (DecodeException)result;
      else
        return (ArrayList<?>)result;
    }

    throw exception;
  }

  /**
   * Parses a JSON array from the provided {@link JsonReader} supplying the JSON document as per the specification of the provided
   * annotation classes that declare an {@link ArrayType} annotation defining the schema of the document. This
   * method attempts to parse the supplied JSON document as per the specification of the provided annotation classes in order, and
   * returns the first successfully parsed JSON array, or throws a {@link DecodeException} if none of the provided annotation
   * classes could parse the supplied document.
   *
   * @param reader The {@link JsonReader} containing the JSON array.
   * @param annotationTypes The annotation classes declaring an {@link ArrayType} annotation defining the schema for the return
   *          {@link ArrayList}.
   * @return An {@link ArrayList} representing the parsed JSON array.
   * @throws DecodeException If an exception has occurred while decoding the JSON array.
   * @throws JsonParseException If the content is not well formed.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code reader} is null.
   * @throws IllegalArgumentException If {@code annotationTypes} is null or empty.
   */
  @SuppressWarnings("null")
  public final ArrayList<?> parseArray(final JsonReader reader, final Collection<Class<? extends Annotation>> annotationTypes) throws DecodeException, JsonParseException, IOException {
    DecodeException exception = null;
    for (final Class<? extends Annotation> annotationType : assertNotEmpty(annotationTypes)) {
      final Object result = parseArray(reader, annotationType, exception);
      if (result instanceof DecodeException)
        exception = (DecodeException)result;
      else
        return (ArrayList<?>)result;
    }

    throw exception;
  }

  /**
   * Parses a JSON array from the provided string supplying the JSON document as per the specification of the provided annotation
   * classes that declare an {@link ArrayType} annotation defining the schema of the document. This method attempts
   * to parse the supplied JSON document as per the specification of the provided annotation classes in order, and returns the first
   * successfully parsed JSON array, or throws a {@link DecodeException} if none of the provided annotation classes could parse the
   * supplied document.
   *
   * @param json The string document containing a JSON array.
   * @param annotationTypes The annotation classes declaring an {@link ArrayType} annotation defining the schema for the return
   *          {@link ArrayList}.
   * @return An {@link ArrayList} representing the parsed JSON array.
   * @throws DecodeException If an exception has occurred while decoding the JSON array.
   * @throws JsonParseException If the content is not well formed.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code json} is null.
   * @throws IllegalArgumentException If {@code annotationTypes} is null or empty.
   */
  @SafeVarargs
  public final ArrayList<?> parseArray(final String json, final Class<? extends Annotation> ... annotationTypes) throws DecodeException, JsonParseException, IOException {
    try (final JsonReader in = new JsonReader(json)) {
      return parseArray(in, annotationTypes);
    }
  }

  /**
   * Parses a JSON array from the provided string supplying the JSON document as per the specification of the provided annotation
   * classes that declare an {@link ArrayType} annotation defining the schema of the document. This method attempts
   * to parse the supplied JSON document as per the specification of the provided annotation classes in order, and returns the first
   * successfully parsed JSON array, or throws a {@link DecodeException} if none of the provided annotation classes could parse the
   * supplied document.
   *
   * @param json The string document containing a JSON array.
   * @param annotationTypes The annotation classes declaring an {@link ArrayType} annotation defining the schema for the return
   *          {@link ArrayList}.
   * @return An {@link ArrayList} representing the parsed JSON array.
   * @throws DecodeException If an exception has occurred while decoding the JSON array.
   * @throws JsonParseException If the content is not well formed.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code json} is null.
   * @throws IllegalArgumentException If {@code annotationTypes} is null or empty.
   */
  public final ArrayList<?> parseArray(final String json, final Collection<Class<? extends Annotation>> annotationTypes) throws DecodeException, JsonParseException, IOException {
    try (final JsonReader in = new JsonReader(json)) {
      return parseArray(in, annotationTypes);
    }
  }
}