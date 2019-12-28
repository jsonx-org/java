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
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;

import org.libj.util.ArrayUtil;
import org.libj.util.Classes;
import org.libj.util.function.Throwing;
import org.libj.util.function.TriConsumer;
import org.libj.util.function.TriPredicate;

abstract class Codec {
  final Field field;
  private final Method setMethod;
  private final TriConsumer<JxObject,String,Object> putMethod;
  final boolean optional;
  final String name;
  private final boolean nullable;
  private final Use use;
  private final Class<?> genericType;

  @SuppressWarnings("unchecked")
  Codec(final Field field, final String name, final boolean nullable, final Use use) {
    this.field = field;
    this.name = JsdUtil.getName(name, field);
    if (Map.class.isAssignableFrom(field.getType())) {
      this.setMethod = null;
      this.putMethod = Throwing.rethrow((o, n, v) -> ((Map<String,Object>)field.get(o)).put(n, v));
      this.optional = use == Use.OPTIONAL;
      this.genericType = Classes.getGenericClasses(field)[1];
    }
    else {
      this.setMethod = JsdUtil.getSetMethod(field, this.name);
      this.putMethod = null;
      this.optional = Optional.class.isAssignableFrom(field.getType());
      this.genericType = optional ? Classes.getGenericClasses(field)[0] : null;
    }

    this.nullable = nullable;
    this.use = use;
    if (nullable && use == Use.OPTIONAL && !optional)
      throw new ValidationException("Invalid field: " + JsdUtil.getFullyQualifiedFieldName(field) + ": Field with (nullable=true & use=" + Use.class.getSimpleName() + ".OPTIONAL) must be of type: " + Optional.class.getName());
  }

  abstract String elementName();

  final Error validateUse(final Object value) {
    return value == null && !nullable && use == Use.REQUIRED ? Error.PROPERTY_REQUIRED(name, value) : null;
  }

  final Object toNull() {
    return use == Use.OPTIONAL && nullable ? Optional.empty() : null;
  }

  final void set(final JxObject object, final String name, final Object value, final TriPredicate<? super JxObject,? super String,Object> onPropertyDecode) throws InvocationTargetException {
    try {
      if (setMethod != null) {
        if (!optional || value instanceof Optional)
          setMethod.invoke(object, value);
        else if (value != null && !genericType.isInstance(value))
          throw new ValidationException(object.getClass().getName() + "#" + setMethod.getName() + "(" + (setMethod.getParameterTypes().length > 0 ? ArrayUtil.toString(setMethod.getParameterTypes(), ',', Class::getName) : "") + ") is not compatible with property \"" + name + "\" of type \"" + elementName() + "\" of value: " + value);
        else
          setMethod.invoke(object, Optional.ofNullable(value));
      }
      else {
        if (value != null && !genericType.isInstance(value))
          throw new ValidationException(object.getClass().getName() + "#" + field.getName() + " is not compatible with property \"" + name + "\" of type \"" + elementName() + "\" of value: " + value);

        putMethod.accept(object, name, value);
      }

      if (onPropertyDecode != null)
        onPropertyDecode.test(object, name, value);
    }
    catch (final IllegalAccessException e) {
      throw new IllegalStateException(e);
    }
    catch (final IllegalArgumentException e) {
      if (e.getMessage() != null && "argument type mismatch".equals(e.getMessage()))
        throw new ValidationException(object.getClass().getName() + "#" + setMethod.getName() + "(" + (setMethod.getParameterTypes().length > 0 ? ArrayUtil.toString(setMethod.getParameterTypes(), ',', Class::getName) : "") + ") is not compatible with property \"" + name + "\" of type \"" + elementName() + "\" with value: " + value);

      throw new UnsupportedOperationException(e);
    }
  }
}