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

import org.openjax.classic.util.Annotations;
import org.openjax.classic.util.Strings;
import org.openjax.classic.util.function.TriPredicate;
import org.openjax.jsonx.runtime.ArrayValidator.Relation;
import org.openjax.jsonx.runtime.ArrayValidator.Relations;

public abstract class ArrayIterator {
  protected static String validate(final ObjectElement element, final Object member, final int i, final Relations relations, final boolean validate) {
    if (validate && !JxObject.class.isAssignableFrom(member.getClass()))
      return member.getClass().getName() + " does not implement " + JxObject.class.getName();

    relations.set(i, new Relation(member, element));
    return null;
  }

  protected static String validate(final BooleanElement element, final Object member, final int i, final Relations relations) {
    relations.set(i, new Relation(member, element));
    return null;
  }

  protected static String validate(final NumberElement element, final Object member, final int i, final Relations relations, final boolean validate) {
    final Number number = (Number)member;
    if (validate) {
      if (element.form() == Form.INTEGER && number.longValue() != number.doubleValue())
        return "Illegal " + Form.class.getSimpleName() + ".INTEGER value: " + Strings.truncate(String.valueOf(member), 16);

      try {
        if (element.range().length() > 0 && !new Range(element.range()).isValid(number))
          return "Range is not matched: " + Strings.truncate(String.valueOf(member), 16);
      }
      catch (final ParseException e) {
        throw new ValidationException("Invalid range attribute: " + Annotations.toSortedString(element, JxUtil.ATTRIBUTES));
      }
    }

    relations.set(i, new Relation(member, element));
    return null;
  }

  protected static String validate(final StringElement element, final Object member, final int i, final Relations relations, final boolean decode, final boolean validate) {
    final String string = (String)member;
    if (validate && element.pattern().length() != 0 && !string.matches(element.pattern()))
      return "Pattern is not matched: \"" + Strings.truncate(string, 16) + "\"";

    relations.set(i, new Relation(decode ? StringCodec.decode("\"" + string + "\"") : member, element));
    return null;
  }

  protected Object current;

  protected final Relation currentRelate(final Annotation annotation) {
    return new Relation(current, annotation);
  }

  protected final String currentPreview() {
    return Strings.truncate(String.valueOf(current), 16);
  }

  protected abstract boolean hasNext() throws IOException;
  protected abstract void next() throws IOException;
  protected abstract void previous();
  protected abstract int nextIndex() throws IOException;
  protected abstract boolean nextIsNull() throws IOException;
  protected abstract String currentMatchesType(final Class<?> type, final Annotation annotation, final IdToElement idToElement, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException;
  protected abstract String currentIsValid(final int i, final Annotation annotation, final IdToElement idToElement, final Relations relations, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode);
}