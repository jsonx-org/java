/* Copyright (c) 2018 Jsonx
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

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.openjax.json.JsonStrings;
import org.jsonx.ArrayValidator.Relations;
import org.openjax.util.FastCollections;

import com.google.common.base.Strings;

class ValidCase<T> extends SuccessCase<PropertyTrial<T>> {
  static final ValidCase<Object> CASE = new ValidCase<>();
  private static final String listDelimiter = "," + Strings.repeat(" ", JxEncoder.get().indent);

  private static List<Object> format(final List<?> list, final Relations relations, final boolean escape) {
    final List<Object> out = new ArrayList<>(list.size());
    for (int i = 0; i < list.size(); ++i) {
      final Object member = list.get(i);
      if (member instanceof String) {
        if (escape) {
          out.add("\"" + JsonStrings.escape((String)member) + "\"");
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
      expected = "[" + FastCollections.toString(list, listDelimiter) + "]";
    }
    else if (trial instanceof StringTrial || trial instanceof AnyTrial && trial.value() instanceof String) {
      expected = StringCodec.encodeObject((String)trial.value()).toString();
    }
    else {
      expected = String.valueOf(trial.value());
    }

    final String actual;
    if (value == null) {
      actual = null;
    }
    else if (trial instanceof ObjectTrial) {
      final String json = String.valueOf(value);
      if (!json.endsWith("}"))
        throw new IllegalStateException("Expected a '}' character at the end of the encoding of a binding object: " + json);

      final int nl = json.lastIndexOf('\n');
      actual = json.replace("\n" + Strings.repeat(" ", json.length() - nl - 2), "\n");
    }
    else {
      actual = String.valueOf(value);
    }

    if (!expected.equals(actual))
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

    if (!expected.equals(value))
      return false;

    assertEquals(expected, value);
    return true;
  }

  private ValidCase() {
  }
}