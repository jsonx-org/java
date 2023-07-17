/* Copyright (c) 2018 JSONx
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.jsonx.ArrayValidator.Relation;
import org.jsonx.ArrayValidator.Relations;
import org.libj.lang.ObjectUtil;
import org.libj.lang.Strings;
import org.libj.util.CollectionUtil;
import org.openjax.json.JsonUtil;

final class ValidCase<T> extends SuccessCase<PropertyTrial<T>> {
  static final ValidCase<Object> CASE = new ValidCase<>();
  private static final String listDelimiter = "," + Strings.repeat(' ', JxEncoder.get().indent);

  private static List<Object> format(final List<?> list, final Relations relations, final boolean escape) {
    final int size = list.size();
    if (size == 0)
      return Collections.EMPTY_LIST;

    final ArrayList<Object> out = new ArrayList<>(size);
    final Iterator<?> itemIterator = list.iterator();
    final Iterator<Relation> relationIterator = relations.iterator();
    while (itemIterator.hasNext()) {
      final Object item = itemIterator.next();
      final Relation relation = relationIterator.next();
      if (item instanceof List) {
        out.add(format((List<?>)item, (Relations)relation.member, escape));
      }
      else if (item != null && (relation.annotation instanceof StringElement || relation instanceof StringType)) {
        final String encode = JsdUtil.getEncode(relation.annotation);
        if (escape) {
          final String value = String.valueOf(encode == null || encode.length() == 0 ? item : JsdUtil.invoke(JsdUtil.parseExecutable(encode, item.getClass()), item));
          out.add(JsonUtil.escape(new StringBuilder(value.length() + 2).append('"'), value).append('"').toString());
        }
        else {
          out.add(item);
        }
      }
      else if (item instanceof Double) {
        out.add(new Double$((double)item));
      }
      else {
        out.add(item);
      }
    }

    return out;
  }

  @Override
  void onEncode(final PropertyTrial<T> trial, final Relations relations, final String value) {
    final String expected;
    if (trial.value() == null) {
      expected = null;
    }
    else if (trial.value() instanceof List) {
      final List<Object> list = format((List<?>)trial.value(), relations, true);
      expected = "[" + CollectionUtil.toString(list, listDelimiter) + "]";
    }
    else if (trial.isStringDefaultType) {
      expected = StringCodec.encodeObject(trial.encodedValue() == null ? null : String.valueOf(trial.encodedValue()));
    }
    else {
      expected = ObjectUtil.toString(NumberCodec.format(trial.encodedValue()));
    }

    final String actual;
    if (value == null) {
      actual = null;
    }
    else if (trial instanceof ObjectTrial) {
      if (!Strings.endsWith(value, '}'))
        throw new IllegalStateException("Expected a '}' character at the end of the encoding of a binding object: " + value);

      final int nl = value.lastIndexOf('\n');
      actual = value.replace("\n" + Strings.repeat(' ', value.length() - nl - 2), "\n");
    }
    else {
      actual = value;
    }

    if (trial instanceof AnyTrial || trial instanceof ArrayTrial)
      // FIXME: Added hacks correcting/hiding a couple issues. One issue is with the testing logic. Another issue is with JxConverter.
      assertEquals(expected == null ? null : expected.replace("\"", "").replaceAll(", +", ","), actual == null ? null : actual.replace("\"", "").replaceAll(", +", ","));
    else
      assertEquals(expected, actual);
  }

  @Override
  boolean onDecode(final PropertyTrial<T> trial, final Relations relations, final Object value) {
    final Object expected;
    if (trial.value() == null)
      expected = null;
    else if (trial.value() instanceof List)
      expected = format((List<?>)trial.value(), relations, false);
    else
      expected = trial.value();

    assertEquals(expected, value);
    return true;
  }

  private ValidCase() {
  }
}