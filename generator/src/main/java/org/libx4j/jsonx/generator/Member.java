/* Copyright (c) 2017 lib4j
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

package org.libx4j.jsonx.generator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

abstract class Member {
  @SuppressWarnings("unchecked")
  protected static <T extends Element>List<T> normalize(final Registry registry, final List<T> members) {
    final List<T> clone = new ArrayList<T>(members);
    for (final ListIterator<T> iterator = clone.listIterator(); iterator.hasNext();) {
      final Element entry = iterator.next();
      final Element normalized = entry.normalize(registry);
      if (normalized != entry)
        iterator.set((T)normalized);
      else if (normalized == null)
        iterator.remove();
    }

    return clone;
  }

  @SuppressWarnings("unchecked")
  protected static <T extends Element>Map<String,T> normalize(final Registry registry, final Map<String,T> members) {
    final Map<String,T> clone = new LinkedHashMap<String,T>(members);
    for (final Iterator<? extends Map.Entry<String,T>> iterator = clone.entrySet().iterator(); iterator.hasNext();) {
      final Map.Entry<String,T> entry = iterator.next();
      final Element normalized = entry.getValue().normalize(registry);
      if (normalized != entry)
        entry.setValue((T)normalized);
      else if (normalized == null)
        iterator.remove();
    }

    return clone;
  }

  protected void collectClassNames(final List<Type> types) {
  }

  protected abstract Schema getSchema();

  protected abstract String toJSON(final String pacakgeName);
  protected abstract String toJSONX(final String pacakgeName);

  public abstract String toJSON();
  public abstract String toJSONX();
}