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

import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

abstract class PropertyTrial<T> extends Trial {
  @SuppressWarnings("unchecked")
  static void setField(final Method getMethod, final Method setMethod, final Object object, final String name, final Object value) {
    try {
      if (Map.class.isAssignableFrom(getMethod.getReturnType())) {
        Map<Object,Object> map = (Map<Object,Object>)getMethod.invoke(object);
        if (map == null)
          setMethod.invoke(object, map = new LinkedHashMap<>());

        map.put(name, value);
      }
      else if (getMethod.getReturnType() == Optional.class && value != null && !(value instanceof Optional)) {
        setMethod.invoke(object, Optional.ofNullable(value));
      }
      else {
        setMethod.invoke(object, value);
      }
    }
    catch (final IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    catch (final InvocationTargetException e) {
      if (e.getCause() instanceof RuntimeException)
        throw (RuntimeException)e.getCause();

      throw new RuntimeException(e.getCause());
    }
  }

  static Case<? extends PropertyTrial<? super Object>> getNullableCase(final boolean nullable) {
    return nullable ? RequiredNullableCase.CASE : RequiredNotNullableCase.CASE;
  }

  static final SecureRandom random = new SecureRandom();

  final Case<? extends PropertyTrial<? super T>> kase;
  final Method getMethod;
  final Method setMethod;
  final Object object;
  private final Object value;
  private final Object encodedValue;
  final String name;
  final Use use;
  final Class<?> type;
  final Executable decode;
  final Executable encode;
  final boolean isStringDefaultType;

  PropertyTrial(final Case<? extends PropertyTrial<? super T>> kase, final Class<?> type, final Method getMethod, final Method setMethod, final Object object, final Object value, final String name, final Use use, final String decode, final String encode, final boolean isStringDefaultType) {
    this.kase = kase;
    this.getMethod = getMethod;
    this.setMethod = setMethod;
    this.object = object;
    this.name = name;
    this.use = use;
    this.isStringDefaultType = isStringDefaultType;

    this.type = type;
    this.decode = decode == null || decode.length() == 0 ? null : JsdUtil.parseExecutable(decode, String.class);
    this.encode = encode == null || encode.length() == 0 ? null : JsdUtil.parseExecutable(encode, type);
    this.value = value;
    if (value == null || this.encode == null) {
      this.encodedValue = value;
    }
    else {
      if (!(value instanceof Optional))
        this.encodedValue = JsdUtil.invoke(this.encode, value);
      else if (((Optional<?>)value).isPresent())
        this.encodedValue = JsdUtil.invoke(this.encode, ((Optional<?>)value).get());
      else
        this.encodedValue = value;
    }
  }

  final Object encodedValue() {
    return encodedValue;
  }

  final Object rawValue() {
    return value;
  }

  @SuppressWarnings("unchecked")
  final T value() {
    return (T)(value instanceof Optional ? ((Optional<T>)value).orElse(null) : value);
  }
}