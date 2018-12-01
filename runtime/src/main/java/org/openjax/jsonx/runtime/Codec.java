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

package org.openjax.jsonx.runtime;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.BiConsumer;

abstract class Codec {
  final Field field;
  private final Method setMethod;
  final String name;
  private final Use use;

  Codec(final Field field, final String name, final Use use) {
    this.field = field;
    this.name = JsonxUtil.getName(name, field);
    this.setMethod = JsonxUtil.getSetMethod(field, this.name);
    this.use = use;
  }

  abstract String elementName();

  String validateUse(final Object value) {
    return value == null && use == Use.REQUIRED ? "Property \"" + name + "\" is required: " + value : null;
  }

  void set(final Object object, final Object value, final BiConsumer<Field,Object> callback) throws InvocationTargetException {
    try {
      setMethod.invoke(object, value);
      if (callback != null)
        callback.accept(field, value);
    }
    catch (final IllegalAccessException e) {
      throw new UnsupportedOperationException(e);
    }
  }
}