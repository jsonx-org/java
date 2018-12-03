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

package org.openjax.jsonx.runtime;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.fastjax.json.JsonStrings;
import org.fastjax.net.URIComponent;
import org.fastjax.util.Annotations;
import org.fastjax.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class StringCodec extends PrimitiveCodec<String> {
  private static final Logger logger = LoggerFactory.getLogger(StringCodec.class);

  static StringBuilder encode(final Annotation annotation, final String object, final boolean validate) throws EncodeException {
    final boolean urlEncode;
    final String pattern;
    if (annotation instanceof StringProperty) {
      final StringProperty property = (StringProperty)annotation;
      pattern = property.pattern();
      urlEncode = property.urlEncode();
    }
    else if (annotation instanceof StringElement) {
      final StringElement element = (StringElement)annotation;
      pattern = element.pattern();
      urlEncode = element.urlEncode();
    }
    else {
      throw new IllegalArgumentException("Illegal annotation type for \"string\": " + annotation.annotationType().getName());
    }

    if (validate && pattern.length() > 0 && !object.matches(pattern))
      throw new EncodeException(Annotations.toSortedString(annotation, AttributeComparator.instance) + ": pattern is not matched: \"" + Strings.truncate(object, 16) + "\"");

    return encode(urlEncode, object);
  }

  static StringBuilder encode(final boolean urlEncode, final String string) throws EncodeException {
    final StringBuilder encoded = JsonStrings.escape(urlEncode ? URIComponent.encode(string) : string);
    return encoded.insert(0, '"').append('"');
  }

  static String decode(final boolean urlDecode, final String json) {
    final StringBuilder unescaped = new StringBuilder(json.length() - 2);
    for (int i = 1, len = json.length() - 1; i < len; ++i) {
      char ch = json.charAt(i);
      if (ch == '\\') {
        ch = json.charAt(++i);
        if (ch != '"' && ch != '\\')
          unescaped.append('\\');
      }

      unescaped.append(ch);
    }

    try {
      return urlDecode ? URIComponent.decode(unescaped.toString()) : unescaped.toString();
    }
    catch (final Exception e) {
      logger.debug(e.getMessage(), e);
      return null;
    }
  }

  private final String pattern;
  private final boolean urlDecode;

  StringCodec(final StringProperty property, final Field field) {
    super(field, property.name(), property.use());
    this.pattern = property.pattern().length() == 0 ? null : property.pattern();
    this.urlDecode = property.urlDecode();
  }

  @Override
  boolean test(final char firstChar) {
    return firstChar == '"';
  }

  @Override
  String validate(final String token) {
    if ((token.charAt(0) != '"' || token.charAt(token.length() - 1) != '"') && (token.charAt(0) != '\'' || token.charAt(token.length() - 1) != '\''))
      return "Is not a string";

    final String value = token.substring(1, token.length() - 1);
    if (pattern != null && !value.matches(pattern))
      return "Pattern (" + pattern + ") is not matched: " + token;

    // FIXME: decode() is done here and in the caller's scope
    final String decoded = decode(token);
    if (decoded == null)
      return "Invalid URL encoding: \"" + token + "\"";

    return null;
  }

  @Override
  String decode(final String json) {
    return decode(urlDecode, json);
  }

  @Override
  String elementName() {
    return "string";
  }
}