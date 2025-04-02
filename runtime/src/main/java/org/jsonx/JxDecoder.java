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
import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Function;

import org.libj.lang.Numbers.Composite;
import org.libj.util.function.ThrowingFunction;
import org.libj.util.function.TriPredicate;
import org.openjax.asm.AnnotationUtil;
import org.openjax.json.JsonParseException;
import org.openjax.json.JsonReader;
import org.openjax.json.JsonUtil;

/**
 * Decoder that deserializes JSON documents to objects of {@link JxObject} classes, or to lists conforming to a provided annotation
 * class that declares an {@link ArrayType} annotation.
 */
public final class JxDecoder {
  public static final class Builder {
    private boolean validate;

    /**
     * Sets the "validation" option for the {@link JxDecoder}, specifying whether validation is to occur while decoding JSON documents.
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
     * Sets a {@link Function Function&lt;DecodeException,String&gt;} that is to be used by the {@link DecodeException} class for the
     * construction of each new instance's detail {@linkplain DecodeException#getMessage() message}.
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

  public static final JxDecoder VALIDATING = new JxDecoder(true, null);
  public static final JxDecoder NON_VALIDATING = new JxDecoder(false, null);

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

  private Object parseObject0(final JsonReader reader, final TriPredicate<JxObject,String,Object> onPropertyDecode, final Class<? extends JxObject> type) throws IOException, JsonParseException {
    final long offLen = reader.readToken();
    if (offLen == -1)
      return null;

    final int off = Composite.decodeInt(offLen, 0);
    final int len = Composite.decodeInt(offLen, 1);
    final char c0 = reader.bufToChar(off);
    if (len == 1) {
      if (c0 != '{')
        return new DecodeException("Expected '{', but got '" + c0 + "'", reader, null, messageFunction);

      final Object object = ObjectCodec.decodeObject(type, reader, validate, onPropertyDecode);
      if (!(object instanceof Error))
        return object;

      final Error error = (Error)object;
      return new DecodeException(error, reader, error.getException(), messageFunction);
    }

    if (len == 4 && c0 == 'n' && reader.bufToChar(off + 1) == 'u' && reader.bufToChar(off + 2) == 'l' && reader.bufToChar(off + 3) == 'l')
      return null;

    return new DecodeException("Expected '{', but got '" + reader.bufToString(off, len) + "'", reader, null, messageFunction);
  }

  /**
   * Parses a JSON object from the provided {@link JsonReader} supplying the JSON document as per the specification of the provided
   * {@link JxObject} class defining the schema of the document. This method attempts to parse the supplied JSON document as per the
   * specification of the provided {@link JxObject} class, and returns the parsed JSON object, or throws a {@link DecodeException} if
   * the provided {@link JxObject} class could parse the supplied document.
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
  public final <T extends JxObject> T parseObject(final JsonReader reader, final TriPredicate<JxObject,String,Object> onPropertyDecode, final Class<? extends T> type) throws DecodeException, IOException, JsonParseException {
    final Object result = parseObject0(reader, onPropertyDecode, type);
    if (result instanceof DecodeException)
      throw (DecodeException)result;

    return (T)result;
  }

  /**
   * Parses a JSON object from the provided string supplying the JSON document as per the specification of the provided
   * {@link JxObject} class defining the schema of the document. This method attempts to parse the supplied JSON document as per the
   * specification of the provided {@link JxObject} class, and returns the parsed JSON object, or throws a {@link DecodeException} if
   * the provided {@link JxObject} class could parse the supplied document.
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
  public final <T extends JxObject> T parseObject(final String json, final TriPredicate<JxObject,String,Object> onPropertyDecode, final Class<? extends T> type) throws DecodeException, IOException, JsonParseException {
    try (final JsonReader in = new JsonReader(json)) {
      return parseObject(in, onPropertyDecode, type);
    }
  }

  /**
   * Parses a JSON object from the provided {@link JsonReader} supplying the JSON document as per the specification of the provided
   * {@link JxObject} class defining the schema of the document. This method attempts to parse the supplied JSON document as per the
   * specification of the provided {@link JxObject} class, and returns the parsed JSON object, or throws a {@link DecodeException} if
   * the provided {@link JxObject} class could parse the supplied document.
   *
   * @param <T> The type parameter for the return object of {@link JxObject} class.
   * @param reader The {@link JsonReader} containing the JSON object.
   * @param type The {@link JxObject} class defining the schema for the return object.
   * @return A {@link JxObject} of one of the specified {@code types} representing the parsed JSON object.
   * @throws DecodeException If an exception has occurred while decoding the JSON object.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code reader} or {@code type} is null.
   */
  public final <T extends JxObject> T parseObject(final JsonReader reader, final Class<? extends T> type) throws DecodeException, IOException {
    return parseObject(reader, null, type);
  }

  /**
   * Parses a JSON object from the provided string supplying the JSON document as per the specification of the provided
   * {@link JxObject} class defining the schema of the document. This method attempts to parse the supplied JSON document as per the
   * specification of the provided {@link JxObject} class, and returns the parsed JSON object, or throws a {@link DecodeException} if
   * the provided {@link JxObject} class could parse the supplied document.
   *
   * @param <T> The type parameter for the return object of {@link JxObject} class.
   * @param json The string document containing a JSON object.
   * @param type The {@link JxObject} class defining the schema for the return object.
   * @return A {@link JxObject} of one of the specified {@code types} representing the parsed JSON object.
   * @throws DecodeException If an exception has occurred while decoding the JSON object.
   * @throws IOException If an I/O error has occurred.
   * @throws NullPointerException If {@code json} or {@code type} is null.
   */
  public final <T extends JxObject> T parseObject(final String json, final Class<? extends T> type) throws DecodeException, IOException {
    try (final JsonReader in = new JsonReader(json)) {
      return parseObject(in, type);
    }
  }

  /**
   * Parses a JSON object from the provided {@link JsonReader} supplying the JSON document as per the specification of the provided
   * {@link JxObject} classes defining the schema of the document. This method attempts to parse the supplied JSON document as per the
   * specification of the provided {@link JxObject} classes in order, and returns the first successfully parsed JSON object, or throws
   * a {@link DecodeException} if none of the provided {@link JxObject} classes could parse the supplied document.
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
  public final <T extends JxObject> T parseObject(final JsonReader reader, final TriPredicate<JxObject,String,Object> onPropertyDecode, final Class<? extends T> ... types) throws DecodeException, IOException, JsonParseException {
    assertNotEmpty(types);
    final int index = reader.getIndex();
    DecodeException exception = null;
    for (int i = 0, i$ = types.length; i < i$; ++i) { // [A]
      if (i > 0)
        reader.setIndex(index);

      final Class<? extends T> type = types[i];
      final Object result = parseObject0(reader, onPropertyDecode, type);
      if (result instanceof DecodeException) {
        if (exception == null)
          exception = (DecodeException)result;
        else if (exception != result)
          exception.addSuppressed((DecodeException)result);
      }
      else {
        return (T)result;
      }
    }

    throw exception;
  }

  /**
   * Parses a JSON object from the provided {@link JsonReader} supplying the JSON document as per the specification of the provided
   * {@link JxObject} classes defining the schema of the document. This method attempts to parse the supplied JSON document as per the
   * specification of the provided {@link JxObject} classes in order, and returns the first successfully parsed JSON object, or throws
   * a {@link DecodeException} if none of the provided {@link JxObject} classes could parse the supplied document.
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
  public final <T extends JxObject> T parseObject(final JsonReader reader, final TriPredicate<JxObject,String,Object> onPropertyDecode, final Collection<Class<? extends T>> types) throws DecodeException, IOException, JsonParseException {
    assertNotEmpty(types);
    final int index = reader.getIndex();
    DecodeException exception = null;
    final Iterator<Class<? extends T>> it = types.iterator();
    for (int i = 0; it.hasNext(); ++i) { // [A]
      if (i > 0)
        reader.setIndex(index);

      final Class<? extends T> type = it.next();
      final Object result = parseObject0(reader, onPropertyDecode, type);
      if (result instanceof DecodeException) {
        if (exception == null)
          exception = (DecodeException)result;
        else if (exception != result)
          exception.addSuppressed((DecodeException)result);
      }
      else {
        return (T)result;
      }
    }

    throw exception;
  }

  /**
   * Parses a JSON object from the provided string supplying the JSON document as per the specification of the provided
   * {@link JxObject} classes defining the schema of the document. This method attempts to parse the supplied JSON document as per the
   * specification of the provided {@link JxObject} classes in order, and returns the first successfully parsed JSON object, or throws
   * a {@link DecodeException} if none of the provided {@link JxObject} classes could parse the supplied document.
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
  public final <T extends JxObject> T parseObject(final String json, final TriPredicate<JxObject,String,Object> onPropertyDecode, final Class<? extends T> ... types) throws DecodeException, IOException, JsonParseException {
    try (final JsonReader in = new JsonReader(json)) {
      return parseObject(in, onPropertyDecode, types);
    }
  }

  /**
   * Parses a JSON object from the provided string supplying the JSON document as per the specification of the provided
   * {@link JxObject} classes defining the schema of the document. This method attempts to parse the supplied JSON document as per the
   * specification of the provided {@link JxObject} classes in order, and returns the first successfully parsed JSON object, or throws
   * a {@link DecodeException} if none of the provided {@link JxObject} classes could parse the supplied document.
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
  public final <T extends JxObject> T parseObject(final String json, final TriPredicate<JxObject,String,Object> onPropertyDecode, final Collection<Class<? extends T>> types) throws DecodeException, IOException, JsonParseException {
    try (final JsonReader in = new JsonReader(json)) {
      return parseObject(in, onPropertyDecode, types);
    }
  }

  /**
   * Parses a JSON object from the provided {@link JsonReader} supplying the JSON document as per the specification of the provided
   * {@link JxObject} classes defining the schema of the document. This method attempts to parse the supplied JSON document as per the
   * specification of the provided {@link JxObject} classes in order, and returns the first successfully parsed JSON object, or throws
   * a {@link DecodeException} if none of the provided {@link JxObject} classes could parse the supplied document.
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
  public final <T extends JxObject> T parseObject(final JsonReader reader, final Class<? extends T> ... types) throws DecodeException, IOException {
    return parseObject(reader, (TriPredicate<JxObject,String,Object>)null, types);
  }

  /**
   * Parses a JSON object from the provided {@link JsonReader} supplying the JSON document as per the specification of the provided
   * {@link JxObject} classes defining the schema of the document. This method attempts to parse the supplied JSON document as per the
   * specification of the provided {@link JxObject} classes in order, and returns the first successfully parsed JSON object, or throws
   * a {@link DecodeException} if none of the provided {@link JxObject} classes could parse the supplied document.
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
  public final <T extends JxObject> T parseObject(final JsonReader reader, final Collection<Class<? extends T>> types) throws DecodeException, IOException {
    return parseObject(reader, null, types);
  }

  /**
   * Parses a JSON object from the provided string supplying the JSON document as per the specification of the provided
   * {@link JxObject} classes defining the schema of the document. This method attempts to parse the supplied JSON document as per the
   * specification of the provided {@link JxObject} classes in order, and returns the first successfully parsed JSON object, or throws
   * a {@link DecodeException} if none of the provided {@link JxObject} classes could parse the supplied document.
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
  public final <T extends JxObject> T parseObject(final String json, final Class<? extends T> ... types) throws DecodeException, IOException {
    try (final JsonReader in = new JsonReader(json)) {
      return parseObject(in, types);
    }
  }

  /**
   * Parses a JSON object from the provided string supplying the JSON document as per the specification of the provided
   * {@link JxObject} classes defining the schema of the document. This method attempts to parse the supplied JSON document as per the
   * specification of the provided {@link JxObject} classes in order, and returns the first successfully parsed JSON object, or throws
   * a {@link DecodeException} if none of the provided {@link JxObject} classes could parse the supplied document.
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
  public final <T extends JxObject> T parseObject(final String json, final Collection<Class<? extends T>> types) throws DecodeException, IOException {
    try (final JsonReader in = new JsonReader(json)) {
      return parseObject(in, types);
    }
  }

  private Object parseArray0(final JsonReader reader, final Class<? extends Annotation> annotationType) throws JsonParseException, IOException {
    reader.setIndex(-1);
    final long offLen = reader.readToken();
    if (offLen == -1)
      return null;

    final int off = Composite.decodeInt(offLen, 0);
    final int len = Composite.decodeInt(offLen, 1);
    final char c0 = reader.bufToChar(off);
    if (len == 1) {
      if (c0 != '[')
        return new DecodeException("Expected '[', but got '" + c0 + "'", reader, null, messageFunction);

      final IdToElement idToElement = new IdToElement();
      final int[] elementIds = JsdUtil.digest(annotationType.getAnnotations(), annotationType.getName(), idToElement);
      return parseArray(reader, idToElement, elementIds);
    }

    if (len == 4 && c0 == 'n' && reader.bufToChar(off + 1) == 'u' && reader.bufToChar(off + 2) == 'l' && reader.bufToChar(off + 3) == 'l')
      return null;

    return new DecodeException("Expected '[', but got '" + reader.bufToString(off, len) + "'", reader, null, messageFunction);
  }

  Object parseArray(final JsonReader reader, final IdToElement idToElement, final int[] elementIds) throws JsonParseException, IOException {
    final Object array = ArrayCodec.decodeObject(idToElement.get(elementIds), idToElement.getMinIterate(), idToElement.getMaxIterate(), idToElement, reader, validate, null);
    if (!(array instanceof Error))
      return array;

    final Error error = (Error)array;
    return new DecodeException(error, reader, error.getException(), messageFunction);
  }

  /**
   * Parses a JSON array from the provided {@link JsonReader} supplying the JSON document as per the specification of the provided
   * annotation class that declares an {@link ArrayType} annotation defining the schema of the document. This method attempts to parse
   * the supplied JSON document as per the specification of the provided annotation class, and returns the parsed JSON array, or
   * throws a {@link DecodeException} if the provided annotation class could parse the supplied document.
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
    final Object result = parseArray0(reader, annotationType);
    if (result instanceof DecodeException)
      throw (DecodeException)result;

    return (ArrayList<?>)result;
  }

  /**
   * Parses a JSON array from the provided string supplying the JSON document as per the specification of the provided annotation
   * class that declares an {@link ArrayType} annotation defining the schema of the document. This method attempts to parse the
   * supplied JSON document as per the specification of the provided annotation class, and returns the parsed JSON array, or throws a
   * {@link DecodeException} if the provided annotation class could parse the supplied document.
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
   * annotation classes that declare an {@link ArrayType} annotation defining the schema of the document. This method attempts to
   * parse the supplied JSON document as per the specification of the provided annotation classes in order, and returns the first
   * successfully parsed JSON array, or throws a {@link DecodeException} if none of the provided annotation classes could parse the
   * supplied document.
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
    assertNotEmpty(annotationTypes);
    final int index = reader.getIndex();
    DecodeException exception = null;
    for (int i = 0, i$ = annotationTypes.length; i < i$; ++i) { // [A]
      if (i > 0)
        reader.setIndex(index);

      final Class<? extends Annotation> annotationType = annotationTypes[i];
      final Object result = parseArray0(reader, annotationType);
      if (result instanceof DecodeException) {
        if (exception == null)
          exception = (DecodeException)result;
        else if (exception != result)
          exception.addSuppressed((DecodeException)result);
      }
      else {
        return (ArrayList<?>)result;
      }
    }

    throw exception;
  }

  /**
   * Parses a JSON array from the provided {@link JsonReader} supplying the JSON document as per the specification of the provided
   * annotation classes that declare an {@link ArrayType} annotation defining the schema of the document. This method attempts to
   * parse the supplied JSON document as per the specification of the provided annotation classes in order, and returns the first
   * successfully parsed JSON array, or throws a {@link DecodeException} if none of the provided annotation classes could parse the
   * supplied document.
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
    assertNotEmpty(annotationTypes);
    final int index = reader.getIndex();
    DecodeException exception = null;
    final Iterator<Class<? extends Annotation>> it = annotationTypes.iterator();
    for (int i = 0; it.hasNext(); ++i) { // [A]
      if (i > 0)
        reader.setIndex(index);

      final Class<? extends Annotation> annotationType = it.next();
      final Object result = parseArray0(reader, annotationType);
      if (result instanceof DecodeException) {
        if (exception == null)
          exception = (DecodeException)result;
        else if (exception != result)
          exception.addSuppressed((DecodeException)result);
      }
      else {
        return (ArrayList<?>)result;
      }
    }

    throw exception;
  }

  /**
   * Parses a JSON array from the provided string supplying the JSON document as per the specification of the provided annotation
   * classes that declare an {@link ArrayType} annotation defining the schema of the document. This method attempts to parse the
   * supplied JSON document as per the specification of the provided annotation classes in order, and returns the first successfully
   * parsed JSON array, or throws a {@link DecodeException} if none of the provided annotation classes could parse the supplied
   * document.
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
   * classes that declare an {@link ArrayType} annotation defining the schema of the document. This method attempts to parse the
   * supplied JSON document as per the specification of the provided annotation classes in order, and returns the first successfully
   * parsed JSON array, or throws a {@link DecodeException} if none of the provided annotation classes could parse the supplied
   * document.
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

  public final Object parse(final JsonReader reader, final Class cls) throws DecodeException, IOException {
    final Object result = parse0(reader, cls);
    if (result instanceof DecodeException)
      throw (DecodeException)result;

    return result;
  }

  private final Object parse0(final JsonReader reader, final Class cls) throws DecodeException, IOException {
    if (JxObject.class.isAssignableFrom(cls))
      return parseObject0(reader, null, (Class<? extends JxObject>)cls);

    if (Annotation.class.isAssignableFrom(cls))
      return parseArray0(reader, (Class<? extends Annotation>)cls);

    if (CharSequence.class.isAssignableFrom(cls))
      return parseString(reader);

    if (Number.class.isAssignableFrom(cls))
      return parseNumber(reader, (Class<? extends Number>)cls);

    if (Boolean.class.isAssignableFrom(cls))
      return parseBoolean(reader);

    throw new IllegalArgumentException("Illegal JSON type: " + cls.getName());
  }

  @SafeVarargs
  @SuppressWarnings("null")
  public final <T>T parse(final JsonReader reader, final Class<T> ... types) throws DecodeException, IOException {
    assertNotEmpty(types);
    final int index = reader.getIndex();
    RuntimeException exception = null;
    for (int i = 0, i$ = types.length; i < i$; ++i) { // [A]
      if (i > 0)
        reader.setIndex(index);

      final Class<?> type = types[i];
      Object result = null;
      try {
        result = parse0(reader, type);
        if (!(result instanceof DecodeException) && !(result instanceof JsonParseException))
          return (T)result;
      }
      catch (final DecodeException | JsonParseException e) {
        result = e;
      }

      if (exception == null)
        exception = (RuntimeException)result;
      else if (exception != result)
        exception.addSuppressed((RuntimeException)result);
    }

    throw exception;
  }

  @SuppressWarnings("null")
  public final Object parse(final JsonReader reader, final Collection<? extends Class<?>> types) throws DecodeException, IOException {
    assertNotEmpty(types);
    final int index = reader.getIndex();
    RuntimeException exception = null;
    final Iterator<? extends Class<?>> it = types.iterator();
    for (int i = 0; it.hasNext(); ++i) { // [A]
      if (i > 0)
        reader.setIndex(index);

      final Class<?> type = it.next();
      Object result = null;
      try {
        result = parse0(reader, type);
        if (!(result instanceof DecodeException) && !(result instanceof JsonParseException))
          return result;
      }
      catch (final DecodeException | JsonParseException e) {
        result = e;
      }

      if (exception == null)
        exception = (RuntimeException)result;
      else if (exception != result)
        exception.addSuppressed((RuntimeException)result);
    }

    throw exception;
  }

  private static String toString(final JsonReader reader) throws IOException {
    final long offLen = reader.readToken();
    if (offLen == -1)
      return null;

    final int off = Composite.decodeInt(offLen, 0);
    final int len = Composite.decodeInt(offLen, 1);
    if (len == 4 && reader.bufToChar(off) == 'n' && reader.bufToChar(off + 1) == 'u' && reader.bufToChar(off + 2) == 'l' && reader.bufToChar(off + 3) == 'l')
      return null;

    return new String(reader.buf(), off, len);
  }

  public static Boolean parseBoolean(final JsonReader reader) throws JsonParseException, IOException {
    final String token = toString(reader);
    return token == null ? null : "true".equals(token) ? Boolean.TRUE : "false".equals(token) ? Boolean.FALSE : null;
  }

  public static <T extends Number> T parseNumber(final JsonReader reader, final Class<? extends T> cls) throws JsonParseException, IOException {
    final String token = toString(reader);
    return token == null ? null : JsonUtil.parseNumber((Class<T>)cls, token, false);
  }

  public static String parseString(final JsonReader reader) throws JsonParseException, IOException {
    final long offLen = reader.readToken();
    if (offLen == -1)
      return null;

    final int off = Composite.decodeInt(offLen, 0);
    final int len = Composite.decodeInt(offLen, 1);
    if (len == 4 && reader.bufToChar(off) == 'n' && reader.bufToChar(off + 1) == 'u' && reader.bufToChar(off + 2) == 'l' && reader.bufToChar(off + 3) == 'l')
      return null;

    final char[] buf = reader.buf();
    if (buf[off] != '"' || buf[off + len - 1] != '"')
      throw new DecodeException(Error.NOT_A_STRING().toString() + ": " + new String(buf, off, len), reader, null);

    return new String(buf, off + 1, len - 2);
  }

  private static boolean matches(final char[] buf, final int start, final int len, final String name) {
    if (name.length() != len)
      return false;

    for (int i = 0; i < len; ++i)
      if (name.charAt(i) != buf[start + i])
        return false;

    return true;
  }

  private static int matches(final int start, final int len, final char[] buf, final String[] names) {
    for (int i = 0, i$ = names.length; i < i$; ++i) // [A]
      if (matches(buf, start, len, names[i]))
        return i;

    return -1;
  }

  private static void debug(final long startLen, final JsonReader reader) {
    final int start = Composite.decodeInt(startLen, 0);
    final int len = Composite.decodeInt(startLen, 1);
    System.err.println(new String(reader.buf(), start, len));
  }

  public static Object parseObject(final JsonReader reader, final String[] propertyNames, final ThrowingFunction[] propertyHandlers) throws IOException {
    long startLen = reader.readToken(); // '{'
    int start = Composite.decodeInt(startLen, 0);
    int len = Composite.decodeInt(startLen, 1);
    assertTrue(len == 1);
    assertTrue(reader.buf()[start] == '{');

    Object obj = null;
    for (int i = 0; i < propertyNames.length; ++i) {
      startLen = reader.readToken();
      if (startLen == -1)
        break;

      start = Composite.decodeInt(startLen, 0);
      len = Composite.decodeInt(startLen, 1);
      // debug(startLen, reader);
      final int j = matches(start, len, reader.buf(), propertyNames);
      if (j == -1)
        throw new ValidationException("Unexpected JSON: " + reader.bufToString(startLen));

      startLen = reader.readToken();
      start = Composite.decodeInt(startLen, 0);
      len = Composite.decodeInt(startLen, 1);
      assertTrue(len == 1);
      assertTrue(reader.buf()[start] == ':');

      obj = propertyHandlers[j].apply(obj);
      reader.readToken(); /* ',' or '}' */
    }

    return obj;
  }

  @SafeVarargs
  @SuppressWarnings("unchecked")
  public final <T> ArrayList<T> parseArray(final JsonReader reader, final boolean nullable, final int minOccurs, final int maxOccurs, final Class<? extends T> ... classes) throws JsonParseException, IOException {
    long offLen = reader.readToken();
    int off = Composite.decodeInt(offLen, 0);
    int len = Composite.decodeInt(offLen, 1);
    assertTrue(len == 1);
    assertTrue(reader.buf()[off] == '[');

    final int index = reader.getIndex();
    DecodeException exception = null;
    final int[] elementIds = new int[] {0};
    final HashMap<String,Object> map = new HashMap<>();
    map.put("id", 0);
    map.put("nullable", nullable);
    map.put("minOccurs", minOccurs);
    map.put("maxOccurs", maxOccurs);
    final IdToElement idToElement = new IdToElement();
    int j = 0;
    Class<? extends T> cls;
    final int length = classes.length;
    do {
      cls = classes[j++];

      map.put("type", cls);
      if (JxObject.class.isAssignableFrom(cls)) {
        idToElement.put(AnnotationUtil.annotationForMap(ObjectElement.class, map));
      }
      else {
        map.put("encode", "");
        map.put("decode", "");
        if (Number.class.isAssignableFrom(cls)) {
          map.put("pattern", "");
          map.put("scale", Integer.MAX_VALUE);
          map.put("range", "");
          idToElement.put(AnnotationUtil.annotationForMap(NumberElement.class, map));
        }
        else if (CharSequence.class.isAssignableFrom(cls)) {
          map.put("pattern", "");
          idToElement.put(AnnotationUtil.annotationForMap(StringElement.class, map));
        }
        else if (Boolean.class.isAssignableFrom(cls)) {
          idToElement.put(AnnotationUtil.annotationForMap(BooleanElement.class, map));
        }
      }

      final Object result = parseArray(reader, idToElement, elementIds);
      if (result instanceof DecodeException) {
        idToElement.clear();

        if (exception == null)
          exception = (DecodeException)result;
        else if (exception != result)
          exception.addSuppressed((DecodeException)result);

        if (j == length)
          throw exception;

        reader.setIndex(index);
      }
      else {
        return (ArrayList<T>)result;
      }
    }
    while (true);
  }
}