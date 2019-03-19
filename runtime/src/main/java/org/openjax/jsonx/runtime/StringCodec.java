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
import java.util.regex.PatternSyntaxException;

import org.openjax.jsonx.runtime.ArrayValidator.Relation;
import org.openjax.jsonx.runtime.ArrayValidator.Relations;
import org.openjax.standard.json.JsonStrings;
import org.openjax.standard.util.Annotations;
import org.openjax.standard.util.Strings;

class StringCodec extends PrimitiveCodec<String> {
  static String decodeArray(final String token) {
    return token.charAt(0) == '"' && token.charAt(token.length() - 1) == '"' ? StringCodec.decode(token) : null;
  }

  static StringBuilder encodeArray(final Annotation annotation, final String pattern, final Object object, final int index, final Relations relations, final boolean validate) {
    if (!(object instanceof String))
      return contentNotExpected(ArrayIterator.preview(object));

    final String string = (String)object;
    if (validate) {
      if (pattern.length() != 0 && !string.matches(pattern))
        return new StringBuilder("Pattern is not matched: \"").append(Strings.truncate(string, 16)).append("\"");
    }

    relations.set(index, new Relation(false ? StringCodec.decode("\"" + string + "\"") : object, annotation));
    return null;
  }

  static Object encode(final Annotation annotation, final String pattern, final String object, final boolean validate) throws EncodeException {
    if (validate) {
      final StringBuilder error = validate(annotation, object, pattern);
      if (error != null)
        return error;
    }

    return encode(object);
  }

  private static StringBuilder validate(final Annotation annotation, final String object, final String pattern) {
    if (pattern.length() > 0 && !object.matches(pattern))
      return new StringBuilder(Annotations.toSortedString(annotation, JxUtil.ATTRIBUTES) + ": pattern is not matched: \"" + Strings.truncate(object, 16) + "\"");

    return null;
  }

  static String encode(final String string) throws EncodeException {
    return JsonStrings.escape(string).insert(0, '"').append('"').toString();
  }

  static String decode(final String json) {
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

    return unescaped.toString();
  }

  private final String pattern;

  StringCodec(final StringProperty property, final Field field) {
    super(field, property.name(), property.nullable(), property.use());
    this.pattern = property.pattern().length() == 0 ? null : property.pattern();
  }

  @Override
  boolean test(final char firstChar) {
    return firstChar == '"';
  }

  @Override
  StringBuilder validate(final String token) {
    if ((token.charAt(0) != '"' || token.charAt(token.length() - 1) != '"') && (token.charAt(0) != '\'' || token.charAt(token.length() - 1) != '\''))
      return new StringBuilder("Is not a string");

    final String value = token.substring(1, token.length() - 1);
    if (pattern != null) {
      try {
        if (!value.matches(pattern))
          return new StringBuilder("Pattern (").append(pattern).append(") is not matched: ").append(token);
      }
      catch (final PatternSyntaxException e) {
        throw new ValidationException("Malformed pattern: " + pattern);
      }
    }

    // FIXME: decode() is done here and in the caller's scope
    final String decoded = parse(token);
    if (decoded == null)
      return new StringBuilder("Invalid URL encoding: \"").append(token).append("\"");

    return null;
  }

  @Override
  String parse(final String json) {
    return decode(json);
  }

  @Override
  String elementName() {
    return "string";
  }
}