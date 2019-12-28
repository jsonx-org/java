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
import java.util.List;

import org.jsonx.ArrayValidator.Relations;
import org.libj.util.CollectionUtil;
import org.openjax.json.JsonUtil;

import com.google.common.base.Strings;

final class ValidCase<T> extends SuccessCase<PropertyTrial<T>> {
  static final ValidCase<Object> CASE = new ValidCase<>();
  private static final String listDelimiter = "," + Strings.repeat(" ", JxEncoder.get().indent);

  private static List<Object> format(final List<?> list, final Relations relations, final boolean escape) {
    final List<Object> out = new ArrayList<>(list.size());
    for (int i = 0; i < list.size(); ++i) {
      final Object member = list.get(i);
      if (member instanceof String) {
        if (escape) {
          out.add("\"" + JsonUtil.escape((String)member) + "\"");
        }
        else {
          out.add(member);
        }
      }
      else if (member instanceof List) {
        out.add(format((List<?>)member, (Relations)relations.get(i).member, escape));
      }
      else {
        out.add(member);
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
    else if (trial instanceof StringTrial || trial instanceof AnyTrial && trial.value() instanceof String) {
      expected = StringCodec.encodeObject((String)trial.value());
    }
    else {
      expected = String.valueOf(trial.value());
    }

    final String actual;
    if (value == null) {
      actual = null;
    }
    else if (trial instanceof ObjectTrial) {
      if (!value.endsWith("}"))
        throw new IllegalStateException("Expected a '}' character at the end of the encoding of a binding object: " + value);

      final int nl = value.lastIndexOf('\n');
      actual = value.replace("\n" + Strings.repeat(" ", value.length() - nl - 2), "\n");
    }
    else {
      actual = value;
    }

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