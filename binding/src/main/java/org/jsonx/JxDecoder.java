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

import org.libj.util.function.TriPredicate;
import org.openjax.json.JsonParseException;
import org.openjax.json.JsonReader;

/**
 * Decoder that deserializes JSON documents to objects of {@link JxObject}
 * classes, or to lists conforming to a provided annotation class that declares
 * an {@link ArrayType} annotation.
 */
public final class JxDecoder {
  public static final JxDecoder VALIDATING = new JxDecoder(true);
  public static final JxDecoder NON_VALIDATING = new JxDecoder(false);

  private static JxDecoder global = NON_VALIDATING;

  /**
   * Returns the {@link JxDecoder} for the provided validation boolean,
   * specifying whether the {@link JxDecoder} is to validate JSON documents
   * while parsing.
   *
   * @param validating Whether the {@link JxDecoder} is to validate JSON
   *          documents while parsing.
   * @return The {@link JxDecoder} for the provided validation boolean,
   *         specifying whether the {@link JxDecoder} is to validate JSON
   *         documents while parsing.
   */
  public static JxDecoder get(final boolean validating) {
    return validating ? VALIDATING : NON_VALIDATING;
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

  private JxDecoder(final boolean validate) {
    this.validate = validate;
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
   * @throws DecodeException If an exception has occurred while decoding a JSON
   *           document.
   * @throws IOException If an I/O error has occurred.
   */
  @SuppressWarnings("unchecked")
  public <T extends JxObject>T parseObject(final Class<T> type, final JsonReader reader, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws DecodeException, IOException {
    final String token = reader.readToken();
    if (!"{".equals(token))
      throw new DecodeException("Expected '{', but got '" + token + "'", reader);

    final Object object = ObjectCodec.decodeObject(type, reader, validate, onPropertyDecode);
    if (object instanceof Error)
      throw new DecodeException(((Error)object).setReader(reader));

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
   * @throws DecodeException If an exception has occurred while decoding a JSON
   *           document.
   * @throws IOException If an I/O error has occurred.
   */
  public <T extends JxObject>T parseObject(final Class<T> type, final String json, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws DecodeException, IOException {
    try (final JsonReader in = new JsonReader(new StringReader(json))) {
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
   * @throws DecodeException If an exception has occurred while decoding a JSON
   *           document.
   * @throws IOException If an I/O error has occurred.
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
   * @throws DecodeException If an exception has occurred while decoding a JSON
   *           document.
   * @throws IOException If an I/O error has occurred.
   */
  public <T extends JxObject>T parseObject(final Class<T> type, final String json) throws DecodeException, IOException {
    try (final JsonReader in = new JsonReader(new StringReader(json))) {
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
   * @throws DecodeException If an exception has occurred while decoding a JSON
   *           document.
   * @throws JsonParseException If the content is not well formed.
   * @throws NullPointerException If @{@code reader} or {@code annotationType}
   *           is null.
   * @throws IOException If an I/O error has occurred.
   */
  public List<?> parseArray(final Class<? extends Annotation> annotationType, final JsonReader reader) throws DecodeException, JsonParseException, IOException {
    final String token = reader.readToken();
    if (!"[".equals(token))
      throw new DecodeException("Expected '[', but got '" + token + "'", reader);

    final IdToElement idToElement = new IdToElement();
    final int[] elementIds = JsdUtil.digest(annotationType.getAnnotations(), annotationType.getName(), idToElement);
    final Object array = ArrayCodec.decodeObject(idToElement.get(elementIds), idToElement.getMinIterate(), idToElement.getMaxIterate(), idToElement, reader, validate, null);
    if (array instanceof Error)
      throw new DecodeException(((Error)array).setReader(reader));

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
   * @throws DecodeException If an exception has occurred while decoding a JSON
   *           document.
   * @throws JsonParseException If the content is not well formed.
   * @throws NullPointerException If @{@code reader} or {@code annotationType}
   *           is null.
   * @throws IOException If an I/O error has occurred.
   */
  public List<?> parseArray(final Class<? extends Annotation> annotationType, final String json) throws DecodeException, JsonParseException, IOException {
    try (final JsonReader in = new JsonReader(new StringReader(json))) {
      return parseArray(annotationType, in);
    }
  }
}