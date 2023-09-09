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

/**
 * Map of attributes that guarantees attribute order as per {@link JsdUtil#ATTRIBUTES}, and disallows null keys or values.
 */
class AttributeMap extends TreeMap<String,Object> {
  /**
   * Creates a new {@link AttributeMap} instance.
   */
  AttributeMap() {
    super(JsdUtil.ATTRIBUTES);
  }

  @Override
  public Object put(final String key, final Object value) {
    assertPositive(assertNotNull(key, "key cannot be null").length(), "key cannot be empty");
    assertNotNull(value, "value cannot be null");
    return super.put(key, value);
  }
}