/* Copyright (c) 2019 OpenJAX
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

import java.util.LinkedHashMap;

public class AnyObject implements JxObject {
  @AnyProperty(name=".*", types={@t(arrays=AnyArray.class), @t(booleans=true), @t(numbers=@NumberType), @t(objects=AnyObject.class), @t(strings=".*")})
  public final LinkedHashMap<String,Object> properties = new LinkedHashMap<>();

  @Override
  public boolean equals(final Object obj) {
    return obj == this || obj instanceof AnyObject && properties.equals(((AnyObject)obj).properties);
  }

  @Override
  public int hashCode() {
    return properties.hashCode();
  }

  @Override
  public String toString() {
    return JxEncoder.get().marshal(this);
  }
}