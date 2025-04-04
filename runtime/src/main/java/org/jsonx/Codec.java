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

import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.libj.lang.Classes;
import org.libj.util.function.TriPredicate;

abstract class Codec {
  static final Number NULL = new Number() {
    @Override
    public int intValue() {
      throw new UnsupportedOperationException();
    }

    @Override
    public long longValue() {
      throw new UnsupportedOperationException();
    }

    @Override
    public float floatValue() {
      throw new UnsupportedOperationException();
    }

    @Override
    public double doubleValue() {
      throw new UnsupportedOperationException();
    }
  };

  private final boolean isMap;
  final Method getMethod;
  final Method setMethod;
  final boolean isOptional;
  final String name;
  final boolean nullable;
  final Use use;
  final Class<?> genericType;

  Codec(final Method getMethod, final Method setMethod, final String name, final boolean nullable, final Use use) {
    this.getMethod = assertNotNull(getMethod);
    this.setMethod = assertNotNull(setMethod);
    this.name = assertNotNull(name);
    final Class<?> returnType = getMethod.getReturnType();
    if (isMap = Map.class.isAssignableFrom(returnType)) {
      this.isOptional = use == Use.OPTIONAL;
      this.genericType = assertNotNull(Classes.getGenericParameters(getMethod)[1]);
    }
    else {
      this.isOptional = returnType == Optional.class;
      this.genericType = isOptional ? Classes.getGenericParameters(getMethod)[0] : null;
    }

    this.nullable = nullable;
    this.use = use;
    if (nullable && use == Use.OPTIONAL && !isOptional)
      throw new ValidationException("Invalid field: " + JsdUtil.getFullyQualifiedMethodName(getMethod) + ": Field with (nullable=true & use=" + Use.class.getSimpleName() + ".OPTIONAL) must be of type: " + Optional.class.getName());
  }

  @SuppressWarnings("unchecked")
  final void set(final JxObject object, final String name, final Object value, final TriPredicate<? super JxObject,? super String,Object> onPropertyDecode) {
    try {
      if (isMap) {
        if (value != null && !genericType.isInstance(value))
          throw new ValidationException(object.getClass().getName() + "." + getMethod.getName() + "() is not compatible with property \"" + name + "\" of type \"" + elementName() + "\" with value: " + value);

        Map<String,Object> map = (Map<String,Object>)getMethod.invoke(object);
        if (map == null)
          setMethod.invoke(object, map = new LinkedHashMap<>());

        map.put(name, value);
      }
      else if (!isOptional || value instanceof Optional)
        setMethod.invoke(object, value);
      else if (value != null && !genericType.isInstance(value))
        throw new ValidationException(object.getClass().getName() + ": " + getMethod + "() is not compatible with property \"" + name + "\" of type \"" + elementName() + "\" with value: " + value);
      else
        setMethod.invoke(object, Optional.ofNullable(value));

      if (onPropertyDecode != null)
        onPropertyDecode.test(object, name, value);
    }
    catch (final IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    catch (final IllegalArgumentException e) {
      if (e.getMessage() != null && "argument type mismatch".equals(e.getMessage()))
        throw new ValidationException(object.getClass().getName() + "." + getMethod.getName() + "():" + getMethod.getReturnType().getName() + " is not compatible with property \"" + name + "\" of type \"" + elementName() + "\" with value: " + value, e);

      throw new UnsupportedOperationException(e);
    }
    catch (final InvocationTargetException e) {
      final Throwable cause = e.getCause();
      if (cause instanceof RuntimeException)
        throw (RuntimeException)cause;

      throw new RuntimeException(cause);
    }
  }

  abstract Class<?> type();
  abstract Executable decode();
  abstract String elementName();
}