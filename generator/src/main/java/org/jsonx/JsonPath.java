/* Copyright (c) 2023 JSONx
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

import java.util.Iterator;
import java.util.LinkedList;

import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Documented;
import org.libj.lang.Strings;
import org.libj.util.primitive.ArrayBooleanList;
import org.libj.util.primitive.ArrayIntList;
import org.openjax.json.JsonUtil;
import org.w3.www._2001.XMLSchema.yAA;
import org.w3.www._2001.XMLSchema.yAA.$AnySimpleType;

class JsonPath implements Iterable<Object> {
  private static String escapeTerm(final String term) {
    final StringBuilder b = JsonUtil.escape(term);
    Strings.replace(b, "\\", "\\\\");
    Strings.replace(b, ".", "\\.");
    return b.toString();
  }

  private static String unescapeTerm(final String term) {
    final StringBuilder b = new StringBuilder(term);
    Strings.replace(b, "\\.", ".");
    Strings.replace(b, "\\\\", "\\");
    return JsonUtil.unescape(b).toString();
  }

  static class Cursor {
    private final LinkedList<Object> terms = new LinkedList<>();
    private final ArrayIntList nextIndex = new ArrayIntList(new int[] {0});
    private final ArrayBooleanList nextWillBeIndex = new ArrayBooleanList(new boolean[] {false});

    void pushName(final String name) {
      final boolean nextWillBeIndex = this.nextWillBeIndex.peek();
      if (nextWillBeIndex || name == null) // Name can be null for <any>
        terms.add(nextIndex.peek());
      else
        terms.add(name);

      nextIndex.push(0);
      this.nextWillBeIndex.push(nextWillBeIndex);
    }

    void popName() {
      terms.removeLast();
      nextIndex.pop();
      nextWillBeIndex.pop();
      nextIndex.set(nextIndex.size() - 1, nextIndex.peek() + 1);
    }

    void inArray() {
      if (terms.size() == 0)
        throw new IllegalStateException();

      nextWillBeIndex.set(nextIndex.size() - 1, true);
    }

    @Override
    public String toString() {
      if (terms.size() == 0)
        return "";

      final StringBuilder b = new StringBuilder();
      for (final Object term : terms) { // [L]
        if (term instanceof String) {
          if (b.length() > 0)
            b.append('.');

          b.append(escapeTerm((String)term));
        }
        else if (term instanceof Integer) {
          b.append('[').append(term).append(']');
        }
        else {
          throw new IllegalStateException("Unknown term class: " + term.getClass().getName());
        }
      }

      return b.toString();
    }
  }

  private final LinkedList<Object> path = new LinkedList<>();
  private final String str;

  JsonPath(final String str) {
    this.str = str;
    boolean escape = false;
    boolean indexes = false;
    int start = 0;
    for (int i = 0, i$ = str.length(); i < i$; ++i) { // [ST]
      final char ch = str.charAt(i);
      if (ch == '\\') {
        escape = !escape;
      }
      else if (escape) {
        escape = false;
        if (ch == '\\')
          ++i;
      }
      else if (ch == '.') {
        path.add(unescapeTerm(str.substring(start, i)).toString());
        start = ++i;
      }
      else if (ch == '[') {
        path.add(unescapeTerm(str.substring(start, i)).toString());
        start = ++i;
        indexes = true;
      }
      else if (ch == ']') {
        path.add(Integer.valueOf(str.substring(start, i)));
        start = i += 2;
      }
    }

    if (!indexes)
      path.add(unescapeTerm(str.substring(start)).toString());
  }

  $Documented resolve($Documented element) {
    final Iterator<Object> pathIterator = iterator();
    Iterator<yAA.$AnyType> xsbIterator;

    OUT:
    while (pathIterator.hasNext()) {
      xsbIterator = element.elementIterator();
      element = null;
      final Object term = pathIterator.next();
      if (term instanceof String) {
        final String name = (String)term;
        while (xsbIterator.hasNext()) {
          element = ($Documented)xsbIterator.next();
          final Iterator<$AnySimpleType> attributeIterator = element.attributeIterator();
          while (attributeIterator.hasNext()) {
            final $AnySimpleType attribute = attributeIterator.next();
            if ("name".equals(attribute.name().getLocalPart()) && attribute.text().equals(name))
              continue OUT;
          }
        }
      }
      else if (term instanceof Integer) {
        final int index = (int)term;
        for (int i = 0; xsbIterator.hasNext(); ++i) { // [I]
          element = ($Documented)xsbIterator.next();
          if (i == index)
            continue OUT;
        }
      }
      else {
        throw new IllegalStateException("Unknown term class: " + term.getClass().getName());
      }
    }

    if (element == null)
      throw new IllegalArgumentException("JsonPath \"" + toString() + "\" not found");

    return element;
  }

  @Override
  public Iterator<Object> iterator() {
    return path.iterator();
  }

  @Override
  public String toString() {
    return str;
  }
}