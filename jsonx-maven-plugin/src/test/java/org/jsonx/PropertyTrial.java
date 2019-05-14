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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Optional;

abstract class PropertyTrial<T> {
  static void setField(final Field field, final Object object, final String name, final Object value) {
    try {
      if (Map.class.isAssignableFrom(field.getType()))
        field.getType().getMethod("put", Object.class, Object.class).invoke(field.get(object), name, value);
      else if (Optional.class.equals(field.getType()) && value != null && !(value instanceof Optional))
        field.set(object, Optional.ofNullable(value));
      else
        field.set(object, value);
    }
    catch (final IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      throw new IllegalStateException(e);
    }
  }

  static Case<? extends PropertyTrial<? super Object>> getNullableCase(final boolean nullable) {
    return nullable ? RequiredNullableCase.CASE : RequiredNotNullableCase.CASE;
  }

  static final SecureRandom random = new SecureRandom();

  final Case<? extends PropertyTrial<? super T>> kase;
  final Field field;
  final Object object;
  private final Object value;
  final String name;
  final Use use;

  PropertyTrial(final Case<? extends PropertyTrial<? super T>> kase, final Field field, final Object object, final Object value, final String name, final Use use) {
    this.kase = kase;
    this.field = field;
    this.object = object;
    this.value = value;
    this.name = JsdUtil.getName(name, field);
    this.use = use;

    field.setAccessible(true);
  }

  final Object rawValue() {
    return value;
  }

  @SuppressWarnings("unchecked")
  final T value() {
    return (T)(value instanceof Optional ? ((Optional<T>)value).orElse(null) : value);
  }
}