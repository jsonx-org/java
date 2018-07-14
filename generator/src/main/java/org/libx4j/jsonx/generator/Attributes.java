/* Copyright (c) 2018 lib4j
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

import java.util.Map;
import java.util.TreeMap;

public class Attributes {
  private final TreeMap<String,String> nameToValue = new TreeMap<>();

  public void put(final String name, final Object value) {
    final String encoded = String.valueOf(value);
    final Object existing = nameToValue.get(name);
    if (existing != null && !existing.equals(encoded))
      throw new IllegalStateException("Attempted overwrite of attribute: " + name);

    nameToValue.put(name, encoded);
  }

  public int size() {
    return nameToValue.size();
  }

  public String toAnnotation() {
    final StringBuilder builder = new StringBuilder();
    for (final Map.Entry<String,String> entry : nameToValue.entrySet())
      builder.append(", ").append(entry.getKey()).append('=').append(entry.getValue());

    return builder.length() > 2 ? builder.substring(2) : builder.toString();
  }
}