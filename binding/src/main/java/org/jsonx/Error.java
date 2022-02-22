/* Copyright (c) 2019 JSONx
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.libj.lang.Annotations;
import org.libj.lang.Strings;
import org.libj.lang.Throwables;
import org.openjax.json.JsonReader;

final class Error {
  static final Error NULL = new Error(null);

  static Error LOOP() {
    return new Error("Loop detected");
  }

  static Error NOT_A_STRING() {
    return new Error("Is not a string");
  }

  static Error ILLEGAL_VALUE_NULL() {
    return new Error("Illegal value: null");
  }

  static Error INVALID_FIELD(final Method getMethod, final Error error) {
    return new Error("%s: %s", getMethod, error);
  }

  static Error EXPECTED_ARRAY(final String token, final JsonReader reader) {
    return new Error(reader, "Expected ']', but got '%s'", token);
  }

  static Error CONTENT_NOT_EXPECTED(final Object content, final JsonReader reader, final Throwable e) {
    return new Error(reader, "Content is not expected: %s", e, content);
  }

  static Error DECODE_EXCEPTION(final JsonReader reader, final Throwable e) {
    return new Error(reader, "Excepiton while decoding", e);
  }

  static Error EXPECTED_TYPE(final String name, final String elementName, final String token, final JsonReader reader) {
    return new Error(reader, "Expected \"%s\" to be a \"%s\", but got: %s", name, elementName, token);
  }

  static Error EXPECTED_TOKEN(final String name, final String elementName, final String token, final JsonReader reader) {
    return new Error(reader, "Expected \"%s\" to be a \"%s\", but got token: \"%s\"", name, elementName, token);
  }

  static Error UNKNOWN_PROPERTY(final String propertyName, final JsonReader reader) {
    return new Error(reader, "Unknown property: \"%s\"", propertyName);
  }

  static Error RANGE_NOT_MATCHED(final Object range, final Object object, final JsonReader reader) {
    return new Error(reader, "Range %s does not match: %s", range, object);
  }

  static Error SCALE_NOT_VALID(final int scale, final Object object, final JsonReader reader) {
    return new Error(reader, "Unsatisfied scale=\"%d\": %s", scale, object);
  }

  static Error BOOLEAN_NOT_VALID(final String token, final JsonReader reader) {
    return new Error(reader, "Not a valid boolean token: %s", token);
  }

  static Error PROPERTY_NOT_NULLABLE(final String name, final Annotation annotation) {
    return new Error("Property \"%s\" is not nullable: %s", name, annotation);
  }

  static Error MEMBER_NOT_NULLABLE(final Annotation annotation) {
    return new Error("Member is not nullable: %s", annotation);
  }

  static Error PROPERTY_REQUIRED(final String name, final Object value) {
    return new Error("Property \"%s\" is required: %s", name, value);
  }

  static Error PATTERN_NOT_MATCHED(final String pattern, final String string, final JsonReader reader) {
    return new Error(reader, "Pattern \"%s\" does not match: \"%s\"", pattern, string);
  }

  static Error PATTERN_NOT_MATCHED_ANNOTATION(final Annotation annotation, final Method getMethod, final String string) {
    return new Error("%s: %s: Pattern does not match: \"%s\"", annotation, getMethod, string);
  }

  static Error UNEXPECTED_END_OF_DOCUMENT(final JsonReader reader) {
    return new Error(reader, "Unexpected end of document");
  }

  static Error INVALID_CONTENT_WAS_FOUND(final int index, final Annotation annotation) {
    return new Error("Invalid content was found starting with member index=%d: %s: ", index, annotation);
  }

  static Error INVALID_CONTENT_NOT_COMPLETE(final int index, final Annotation annotation) {
    return new Error("Invalid content was found starting with member index=%d: %s: Content is not complete", index, annotation);
  }

  static Error INVALID_CONTENT_IN_EMPTY_NOT_COMPLETE(final Annotation annotation) {
    return new Error("Invalid content was found in empty array: %s: Content is not complete", annotation);
  }

  static Error INVALID_CONTENT_MEMBERS_NOT_EXPECTED(final int index, final Annotation annotation, final Object object) {
    return new Error("Invalid content was found starting with member index=%d: %s: No members are expected at this point: %s", index, annotation, object);
  }

  private final int offset;
  private final String message;
  private final Throwable e;
  private final Object[] args;
  private Error next;
  private String rendered;

  boolean isBefore(final Error error) {
    return offset < error.offset;
  }

  private Error(final String message) {
    this(null, message, (Object[])null);
  }

  private Error(final String message, final Object ... args) {
    this(null, message, args);
  }

  private Error(final JsonReader reader, final String message, final Object ... args) {
    this(reader, message, null, args);
  }

  private Error(final JsonReader reader, final String message, final Throwable e, final Object ... args) {
    this.offset = reader != null ? reader.getPosition() - 1 : -1;
    this.message = message;
    this.e = e;
    this.args = args;
  }

  Error append(final Error error) {
    this.next = error;
    return this;
  }

  public int getOffset() {
    return this.offset;
  }

  public Throwable getException() {
    return e;
  }

  @Override
  public String toString() {
    if (rendered != null)
      return rendered;

    final StringBuilder str = new StringBuilder();
    if (args != null) {
      for (int i = 0; i < args.length; ++i) {
        final Object arg = args[i];
        final Object obj;
        if (arg instanceof Number || arg instanceof Boolean)
          obj = arg;
        else if (arg instanceof Annotation)
          obj = Annotations.toSortedString((Annotation)arg, JsdUtil.ATTRIBUTES, true);
        else if (arg instanceof Method)
          obj = ((Method)arg).getDeclaringClass().getName() + "." + ((Method)arg).getName() + "()";
        else if (arg instanceof Class)
          obj = ((Class<?>)arg).getSimpleName();
        else if (arg instanceof Error)
          obj = arg.toString();
        else
          obj = Strings.truncate(String.valueOf(arg), 128);

        args[i] = obj;
      }

      str.append(String.format(message, args));
      Arrays.fill(args, null);
    }
    else {
      str.append(message);
    }

    if (e != null)
      str.append(": ").append(Throwables.toString(e));

    if (next == null)
      return rendered = str.toString();

    rendered = str.append(next.toString()).toString();
    next = null;
    return rendered;
  }
}