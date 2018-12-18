/* Copyright (c) 2018 OpenJAX
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

package org.openjax.jsonx.generator;

import java.util.TreeMap;

import org.fastjax.util.ObservableMap;
import org.openjax.jsonx.runtime.JxUtil;

class AttributeMap extends ObservableMap<String,String> {
  AttributeMap() {
    super(new TreeMap<String,String>(JxUtil.ATTRIBUTES));
  }

  @Override
  protected boolean beforePut(final String key, final String oldValue, final String newValue) {
    if (oldValue == null || oldValue.equals(newValue) || "xsi:schemaLocation".equals(key))
      return true;

    throw new IllegalStateException("Attribute overwrite: [" + key + "] from [" + oldValue + "] to [" + newValue + "]");
  }

  String put(final String key, final Object value) {
    return super.put(key, value == null ? null : String.valueOf(value));
  }
}