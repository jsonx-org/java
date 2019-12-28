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
import java.lang.reflect.Field;
import java.util.Arrays;

import org.libj.lang.Strings;
import org.libj.util.Annotations;

final class Error {
  static final Error NULL = new Error(null);
  static final Error LOOP = new Error("Loop detected");
  static final Error NOT_A_STRING = new Error("Is not a string");
  static final Error ILLEGAL_VALUE_NULL = new Error("Illegal value: null");

  static Error INVALID_FIELD(final Field field, final Error error) {
    return new Error("%s: %s", field, error);
  }

  static Error EXPECTED_ARRAY(final String token, final int offset) {
    return new Error(offset, "Expected ']', but got '%s'", token);
  }

  static Error CONTENT_NOT_EXPECTED(final Object content, final int offset) {
    return new Error(offset, "Content is not expected: %s", content);
  }

  static Error EXPECTED_TYPE(final String name, final String elementName, final String token, final int offset) {
    return new Error(offset, "Expected \"%s\" to be a \"%s\", but got: %s", name, elementName, token);
  }

  static Error EXPECTED_TOKEN(final String name, final String elementName, final String token, final int offset) {
    return new Error(offset, "Expected \"%s\" to be a \"%s\", but got token: \"%s\"", name, elementName, token);
  }

  static Error UNKNOWN_PROPERTY(final String propertyName, final int offset) {
    return new Error(offset, "Unknown property: \"%s\"", propertyName);
  }

  static Error RANGE_NOT_MATCHED(final Object range, final Object object, final int offset) {
    return new Error(offset, "Range %s does not match: %s", range, object);
  }

  static Error SCALE_NOT_VALID(final int scale, final Object object, final int offset) {
    return new Error(offset, "Unsatisfied scale=\"%d\": %s", scale, object);
  }

  static Error BOOLEAN_NOT_VALID(final String token, final int offset) {
    return new Error(offset, "Not a valid boolean token: %s", token);
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

  static Error PATTERN_NOT_MATCHED(final String pattern, final String string, final int offset) {
    return new Error(offset, "Pattern \"%s\" does not match: \"%s\"", pattern, string);
  }

  static Error PATTERN_NOT_MATCHED_ANNOTATION(final Annotation annotation, final String string) {
    return new Error("%s: Pattern does not match: \"%s\"", annotation, string);
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

  private final Object[] args;
  private final String message;
  final int offset;
  private Error next;
  private String rendered;

  private Error(final String message) {
    this.offset = -1;
    this.message = message;
    this.args = null;
  }

  private Error(final int offset, final String message, final Object ... args) {
    this.offset = offset;
    this.message = message;
    this.args = args;
  }

  private Error(final String message, final Object ... args) {
    this.offset = -1;
    this.message = message;
    this.args = args;
  }

  Error append(final Error error) {
    this.next = error;
    return this;
  }

  @Override
  public String toString() {
    if (rendered != null)
      return rendered;

    final String str;
    if (args != null) {
      for (int i = 0; i < args.length; ++i) {
        final Object arg = args[i];
        final Object obj;
        if (arg instanceof Number || arg instanceof Boolean)
          obj = arg;
        else if (arg instanceof Annotation)
          obj = Annotations.toSortedString((Annotation)arg, JsdUtil.ATTRIBUTES);
        else if (arg instanceof Field)
          obj = ((Field)arg).getDeclaringClass().getName() + "#" + ((Field)arg).getName();
        else if (arg instanceof Class)
          obj = ((Class<?>)arg).getSimpleName();
        else if (arg instanceof Error)
          obj = arg.toString();
        else
          obj = Strings.truncate(String.valueOf(arg), 128);

        args[i] = obj;
      }

      str = String.format(message, args);
      Arrays.fill(args, null);
    }
    else {
      str = message;
    }

    if (next == null)
      return rendered = str;

    rendered = str + next.toString();
    next = null;
    return rendered;
  }
}