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

import static org.libj.lang.Assertions.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Map of properties that preserves order of insertion and disallows null keys or values.
 *
 * @param <V> The type parameter of the map's values.
 */
class PropertyMap<V> extends LinkedHashMap<String,V> {
  /**
   * Creates a new {@link PropertyMap} instance with the provided {@code initialCapacity}.
   *
   * @param initialCapacity The initial capacity.
   * @throws IllegalArgumentException If the initial capacity is negative.
   */
  PropertyMap(final int initialCapacity) {
    super(initialCapacity);
  }

  /**
   * Creates a new {@link PropertyMap} instance with the same mappings as the specified map.
   *
   * @param m The map whose mappings are to be placed in this map.
   * @throws NullPointerException If the specified map is null.
   * @throws IllegalArgumentException If any key or value contained in the specified map is null.
   */
  PropertyMap(final Map<String,V> m) {
    if (m.size() > 0)
      for (final Map.Entry<String,V> entry : m.entrySet()) // [S]
        put(entry.getKey(), entry.getValue());
  }

  /**
   * Creates a new {@link PropertyMap} instance with the default initial capacity (16) and load factor (0.75).
   */
  PropertyMap() {
    super();
  }

  @Override
  public V put(final String key, final V value) {
    assertPositive(assertNotNull(key, "key cannot be null").length(), "key cannot be empty");
    assertNotNull(value, "value cannot be null");
    return super.put(key, value);
  }
}