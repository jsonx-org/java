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
import org.w3.www._2001.XMLSchema.yAA;
import org.w3.www._2001.XMLSchema.yAA.$AnySimpleType;

class JsonPath implements Iterable<Object> {
  static class Cursor {
    private final LinkedList<Object> path = new LinkedList<>();
    private int index = -1;

    void pushName(final String name) {
      if (index == -1 && name != null)
        path.add(name);
    }

    void popName() {

    }

    void pushIndex() {
      path.add(++index);
    }

    @Override
    public String toString() {
      final StringBuilder b = new StringBuilder();
      for (final Object term : path) { // [L]
        if (term instanceof String) {
          if (b.length() > 0)
            b.append('.');

          b.append(term);
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
    for (int i = 0, i$ = str.length(); i < i$; ++i) {
      final char ch = str.charAt(i);
      if (ch == '\\') {
        escape = !escape;
      }
      else if (escape) {
        escape = false;
      }
      else if (ch == '.') {
        path.add(str.substring(start, i));
        start = ++i;
      }
      else if (ch == '[') {
        path.add(str.substring(start, i));
        start = ++i;
        indexes = true;
      }
      else if (ch == ']') {
        path.add(Integer.valueOf(str.substring(start, i)));
        start = i += 2;
      }
    }

    if (!indexes)
      path.add(str.substring(start));
  }

//  private static LinkedList<Object> xxx(final $Documented b, final boolean isInArray) {
//    if (b == null)
//      return new LinkedList<>();
//
//    final $Documented owner = ($Documented)b.owner();
//    final Iterator<$AnySimpleType> attributeIterator = owner.attributeIterator();
//    while (attributeIterator.hasNext()) {
//      final $AnySimpleType attribute = attributeIterator.next();
//      if ("name".equals(attribute.name().getLocalPart())) {
//        final String name = (String)attribute.text();
//        final LinkedList<Object> xxx = xxx(b, false);
//        xxx.add(name);
//        return xxx;
//      }
//    }
//
//  }

  $Documented resolve($Documented element) {
    final Iterator<Object> pathIterator = iterator();
    Iterator<yAA.$AnyType> xsbIterator = element.elementIterator();
    OUT:
    while (pathIterator.hasNext()) {
      final Object term = pathIterator.next();
      if (term instanceof String) {
        while (xsbIterator.hasNext()) {
          element = ($Documented)xsbIterator.next();
          final Iterator<$AnySimpleType> attributeIterator = element.attributeIterator();
          while (attributeIterator.hasNext()) {
            final $AnySimpleType attribute = attributeIterator.next();
            if ("name".equals(attribute.name().getLocalPart()) && attribute.text().equals(term)) {
              xsbIterator = element.elementIterator();
              continue OUT;
            }
          }
        }
      }
      else if (term instanceof Integer) {
        for (int index = 0; xsbIterator.hasNext();) {
          element = ($Documented)xsbIterator.next();
          if (index == (int)term)
            break OUT;
        }
      }
      else {
        throw new IllegalStateException("Unknown term class: " + term.getClass().getName());
      }
    }

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