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

import java.io.IOException;
import java.lang.annotation.Annotation;

import org.fastjax.util.Annotations;
import org.fastjax.util.Strings;
import org.openjax.jsonx.runtime.ArrayValidator.Relation;
import org.openjax.jsonx.runtime.ArrayValidator.Relations;

public abstract class ArrayIterator<T> {
  public static <T>String validatePrimitive(final Annotation annotation, final T member, final int i, final Relations relations) {
    if (annotation instanceof BooleanElement)
      return validate((BooleanElement)annotation, member, i, relations);

    if (annotation instanceof NumberElement)
      return validate((NumberElement)annotation, member, i, relations);

    if (annotation instanceof StringElement)
      return validate((StringElement)annotation, member, i, relations);

    throw new UnsupportedOperationException("Unsupported annotation type " + annotation.annotationType().getName());
  }

  private static String validate(final BooleanElement element, final Object member, final int i, final Relations relations) {
    relations.set(i, new Relation(member, element));
    return null;
  }

  private static String validate(final NumberElement element, final Object member, final int i, final Relations relations) {
    final Number number = (Number)member;
    if (element.form() == Form.INTEGER && number.longValue() != number.doubleValue())
      return "Illegal non-INTEGER value: " + Strings.abbreviate(String.valueOf(member), 16);

    try {
      if (element.range().length() > 0 && !new Range(element.range()).isValid(number))
        return "Range is not matched: " + Strings.abbreviate(String.valueOf(member), 16);
    }
    catch (final ParseException e) {
      throw new ValidationException("Invalid range attribute: " + Annotations.toSortedString(element, AttributeComparator.instance));
    }

    relations.set(i, new Relation(member, element));
    return null;
  }

  private static String validate(final StringElement element, final Object member, final int i, final Relations relations) {
    final String string = (String)member;
    if (element.pattern().length() != 0 && !string.matches(element.pattern()))
      return "Pattern is not matched: \"" + Strings.abbreviate(string, 16) + "\"";

    relations.set(i, new Relation(member, element));
    return null;
  }

  protected T current;

  public abstract String currentIsValid(final int i, final Annotation annotation, final IdToElement idToElement, final Relations relations);

  protected final Relation currentRelate(final Annotation annotation) {
    return new Relation(current, annotation);
  }

  protected final String currentPreview() {
    return Strings.abbreviate(String.valueOf(current), 16);
  }

  protected abstract void next() throws IOException;
  protected abstract void previous();
  protected abstract boolean hasNext() throws IOException;
  protected abstract int nextIndex() throws IOException;
  protected abstract boolean nextIsNull() throws IOException;
  protected abstract boolean currentMatchesType(final Class<?> type, final Annotation annotation, final IdToElement idToElement) throws DecodeException, IOException;
}