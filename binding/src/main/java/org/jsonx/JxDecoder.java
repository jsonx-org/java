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

import java.io.IOException;
import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.function.Function;

import org.libj.lang.Assertions;
import org.libj.lang.Numbers.Composite;
import org.libj.util.function.TriPredicate;
import org.openjax.json.JsonParseException;
import org.openjax.json.JsonReader;

/**
 * Decoder that deserializes JSON documents to objects of {@link JxObject}
 * classes, or to lists conforming to a provided annotation class that declares
 * an {@link ArrayType} annotation.
 */
public final class JxDecoder {
  public static final class Builder {
    private boolean validate;

    /**
     * Sets the "validation" option for the {@link JxDecoder}, specifying
     * whether validation is to occur while decoding JSON documents.
     *
     * @param validation The "validation" option for the {@link JxDecoder}, specifying
     * whether validation is to occur while decoding JSON documents.
     * @return This {@link Builder}.
     */
    public Builder withValidation(final boolean validation) {
      this.validate = validation;
      return this;
    }

    private Function<DecodeException,String> messageFunction;

    /**
     * Sets a {@link Function Function&lt;DecodeException,String&gt;} that is to
     * be used by the {@link DecodeException} class for the construction of each
     * new instance's detail {@linkplain DecodeException#getMessage() message}.
     *
     * @param messageFunction The {@link Function
     *          Function&lt;DecodeException,String&gt;} that is to be used by
     *          the {@link DecodeException} class for the construction of each
     *          new instance's detail {@linkplain DecodeException#getMessage()
     *          message}.
     * @return This {@link Builder}.
     */
    public Builder withDecodeExceptionMessageFunction(final Function<DecodeException,String> messageFunction) {
      this.messageFunction = messageFunction;
      return this;
    }

    /**
     * Returns a new instance of {@link JxDecoder} with the options specified in
     * this {@link Builder}.
     *
     * @return A new instance of {@link JxDecoder} with the options specified in
     *         this {@link Builder}.
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

  /**
   * Parses a JSON object from the supplied {@link JsonReader} as per the
   * specification of the provided {@link JxObject} class.
   *
   * @param <T> The type parameter for the return object of {@link JxObject}
   *          class.
   * @param type The class for the return object.
   * @param reader The {@link JsonReader} containing the JSON object.
   * @param onPropertyDecode Callback predicate to be called for each decoded
   *          JSON properties, accepting arguments of:
   *          <ol>
   *          <li>The {@link JxObject}.</li>
   *          <li>The property name.</li>
   *          <li>The property value.</li>
   *          </ol>
   * @return A {@link JxObject} of the specified type representing the parsed
   *         JSON object.
   * @throws DecodeException If an exception has occurred while decoding the
   *           JSON object.
   * @throws IOException If an I/O error has occurred.
   * @throws IllegalArgumentException If {@code type} or {@code reader} is null.
   */
  @SuppressWarnings("unchecked")
  public <T extends JxObject>T parseObject(final Class<T> type, final JsonReader reader, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws DecodeException, IOException {
    Assertions.assertNotNull(type);
    Assertions.assertNotNull(reader);
    final long point = reader.readToken();
    final int off = Composite.decodeInt(point, 0);
    final char c0 = reader.bufToChar(off);
    if (c0 != '{')
      throw new DecodeException("Expected '{', but got '" + point + "'", reader, null, messageFunction);

    final Object object = ObjectCodec.decodeObject(type, reader, validate, onPropertyDecode);
    if (object instanceof Error)
      throw new DecodeException((Error)object, reader, messageFunction);

    return (T)object;
  }

  /**
   * Parses a JSON object from the supplied {@link JsonReader} as per the
   * specification of the provided {@link JxObject} class.
   *
   * @param <T> The type parameter for the return object of {@link JxObject}
   *          class.
   * @param type The class for the return object.
   * @param json The string document containing a JSON object.
   * @param onPropertyDecode Callback predicate to be called for each decoded
   *          JSON properties, accepting arguments of:
   *          <ol>
   *          <li>The {@link JxObject}.</li>
   *          <li>The property name.</li>
   *          <li>The property value.</li>
   *          </ol>
   * @return A {@link JxObject} of the specified type representing the parsed
   *         JSON object.
   * @throws DecodeException If an exception has occurred while decoding the
   *           JSON object.
   * @throws IOException If an I/O error has occurred.
   * @throws IllegalArgumentException If {@code type} or {@code json} is null.
   */
  public <T extends JxObject>T parseObject(final Class<T> type, final String json, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws DecodeException, IOException {
    try (final JsonReader in = new JsonReader(new StringReader(Assertions.assertNotNull(json)))) {
      return parseObject(type, in, onPropertyDecode);
    }
  }

  /**
   * Parses a JSON object at the supplied {@link JsonReader} as per the
   * specification of the provided {@link JxObject} class.
   *
   * @param <T> The type parameter for the return object of {@link JxObject}
   *          class.
   * @param type The class for the return object.
   * @param reader The {@link JsonReader} containing the JSON object.
   * @return A {@link JxObject} of the specified type representing the parsed
   *         JSON object.
   * @throws DecodeException If an exception has occurred while decoding the
   *           JSON object.
   * @throws IOException If an I/O error has occurred.
   * @throws IllegalArgumentException If {@code type} or {@code reader} is null.
   */
  public <T extends JxObject>T parseObject(final Class<T> type, final JsonReader reader) throws DecodeException, IOException {
    return parseObject(type, reader, null);
  }

  /**
   * Parses a JSON object at the supplied {@link JsonReader} as per the
   * specification of the provided {@link JxObject} class.
   *
   * @param <T> The type parameter for the return object of {@link JxObject}
   *          class.
   * @param type The class for the return object.
   * @param json The string document containing a JSON object.
   * @return A {@link JxObject} of the specified type representing the parsed
   *         JSON object.
   * @throws DecodeException If an exception has occurred while decoding the
   *           JSON object.
   * @throws IOException If an I/O error has occurred.
   * @throws IllegalArgumentException If {@code type} or {@code json} is null.
   */
  public <T extends JxObject>T parseObject(final Class<T> type, final String json) throws DecodeException, IOException {
    try (final JsonReader in = new JsonReader(new StringReader(Assertions.assertNotNull(json)))) {
      return parseObject(type, in);
    }
  }

  /**
   * Parses a JSON array from the supplied {@link JsonReader} as per the
   * specification of the provided annotation class that declares an
   * {@link ArrayType} annotation.
   *
   * @param annotationType The annotation class that declares an
   *          {@link ArrayType} annotation.
   * @param reader The {@link JsonReader} containing the JSON array.
   * @return A {@link List} representing the parsed JSON array.
   * @throws DecodeException If an exception has occurred while decoding the
   *           JSON array.
   * @throws JsonParseException If the content is not well formed.
   * @throws IOException If an I/O error has occurred.
   * @throws IllegalArgumentException If {@code annotationType} or
   *           {@code reader} is null.
   */
  public List<?> parseArray(final Class<? extends Annotation> annotationType, final JsonReader reader) throws DecodeException, JsonParseException, IOException {
    Assertions.assertNotNull(annotationType);
    Assertions.assertNotNull(reader);
    final long point = reader.readToken();
    final int off = Composite.decodeInt(point, 0);
    final char c0 = reader.bufToChar(off);
    if (c0 != '[')
      throw new DecodeException("Expected '[', but got '" + reader.bufToString(off, Composite.decodeInt(point, 1)) + "'", reader, null, messageFunction);

    final IdToElement idToElement = new IdToElement();
    final int[] elementIds = JsdUtil.digest(annotationType.getAnnotations(), annotationType.getName(), idToElement);
    final Object array = ArrayCodec.decodeObject(idToElement.get(elementIds), idToElement.getMinIterate(), idToElement.getMaxIterate(), idToElement, reader, validate, null);
    if (array instanceof Error)
      throw new DecodeException((Error)array, reader, messageFunction);

    return (List<?>)array;
  }

  /**
   * Parses a JSON array from the supplied string document as per the
   * specification of the provided annotation class that declares an
   * {@link ArrayType} annotation.
   *
   * @param annotationType The annotation class that declares an
   *          {@link ArrayType} annotation.
   * @param json The string document containing a JSON array.
   * @return A {@link List} representing the parsed JSON array.
   * @throws DecodeException If an exception has occurred while decoding the
   *           JSON array.
   * @throws JsonParseException If the content is not well formed.
   * @throws IOException If an I/O error has occurred.
   * @throws IllegalArgumentException If {@code annotationType} or {@code json}
   *           is null.
   */
  public List<?> parseArray(final Class<? extends Annotation> annotationType, final String json) throws DecodeException, JsonParseException, IOException {
    try (final JsonReader in = new JsonReader(new StringReader(Assertions.assertNotNull(json)))) {
      return parseArray(annotationType, in);
    }
  }
}