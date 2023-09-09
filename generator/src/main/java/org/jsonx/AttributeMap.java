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

import static org.libj.lang.Assertions.*;

import java.util.TreeMap;

import org.libj.util.ObservableMap;

/**
 * Map of attributes that guarantees attribute order as per {@link JsdUtil#ATTRIBUTES}.
 */
class AttributeMap extends ObservableMap<String,Object> {
  private final String prefix;

  /**
   * Creates a new {@link AttributeMap} with the specified prefix. For each invocation of {@link #put(String,Object)} and
   * {@link #remove(Object)}, the provided {@code key} is prepended with {@code prefix} (if {@code prefix} is not null).
   *
   * @param prefix The prefix to prepend to each key.
   */
  AttributeMap(final String prefix) {
    super(new TreeMap<>(JsdUtil.ATTRIBUTES));
    this.prefix = prefix;
  }

  /**
   * Creates a new {@link AttributeMap} with a null prefix.
   */
  AttributeMap() {
    this(null);
  }

  @Override
  protected boolean beforeRemove(final Object key, final Object value) {
    target.remove(prefix != null ? prefix + key : key);
    return false;
  }

  @Override
  protected Object beforePut(final String key, final Object oldValue, final Object newValue, final Object preventDefault) {
    // Commented out, because <binding field="..."> may end up being overwritten for non-root-based reference/model pairs
    // if (oldValue != null && !oldValue.equals(newValue) && !"xsi:schemaLocation".equals(key))
    //   throw new IllegalArgumentException("Attribute overwrite: [" + key + "] from [" + oldValue + "] to [" + newValue + "]");
    assertNotNull(key, "key cannot be null");
    assertNotNull(newValue, "value cannot be null");
    target.put(prefix != null ? prefix + key : key, newValue);
    return preventDefault;
  }
}