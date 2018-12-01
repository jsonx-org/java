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

import org.fastjax.net.URIComponent;
import org.fastjax.util.Annotations;
import org.fastjax.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class StringCodec extends PrimitiveCodec<String> {
  private static final Logger logger = LoggerFactory.getLogger(StringCodec.class);

  static StringBuilder encode(final Annotation annotation, final String string, final boolean validate) throws EncodeException {
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
      throw new UnsupportedOperationException("Unsupported annotation type " + annotation.annotationType().getName());
    }

    if (validate && pattern.length() > 0 && !string.matches(pattern))
      throw new EncodeException(Annotations.toSortedString(annotation, AttributeComparator.instance) + ": pattern is not matched: \"" + Strings.truncate(string, 16) + "\"");

    final StringBuilder encoded = escapeString(urlEncode ? URIComponent.encode(string) : string);
    return encoded.insert(0, '"').append('"');
  }

  private static StringBuilder escapeString(final String string) {
    final StringBuilder builder = new StringBuilder(string.length());
    for (int i = 0, len = string.length(); i < len; ++i) {
      final char ch = string.charAt(i);
      /*
       * From RFC 4627, "All Unicode characters may be placed within the
       * quotation marks except for the characters that must be escaped:
       * quotation mark, reverse solidus, and the control characters (U+0000
       * through U+001F)."
       */
      switch (ch) {
        case '"':
        case '\\':
          builder.append('\\').append(ch);
          break;
        case '\n':
          builder.append("\\n");
          break;
        case '\r':
          builder.append("\\r");
          break;
        case '\t':
          builder.append("\\t");
          break;
        case '\b':
          builder.append("\\b");
          break;
        case '\f':
          builder.append("\\f");
          break;
        default:
          if (ch <= 0x1F)
            builder.append(String.format("\\u%04x", (int)ch));
          else
            builder.append(ch);
      }
    }

    return builder;
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

  @Override
  String elementName() {
    return "string";
  }
}