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
import java.util.Optional;

import org.openjax.classic.util.Classes;
import org.openjax.classic.util.FastArrays;
import org.openjax.classic.util.function.TriPredicate;

abstract class Codec {
  final Field field;
  private final Method setMethod;
  final boolean optional;
  final String name;
  private final boolean nullable;
  private final Use use;
  private final Class<?> genericType;

  Codec(final Field field, final String name, final boolean nullable, final Use use) {
    this.field = field;
    this.name = JxUtil.getName(name, field);
    this.setMethod = JxUtil.getSetMethod(field, this.name);
    this.optional = Optional.class.isAssignableFrom(field.getType());
    this.genericType = optional ? Classes.getGenericTypes(field)[0] : null;
    this.nullable = nullable;
    this.use = use;
    if (nullable && use == Use.OPTIONAL && !optional)
      throw new ValidationException("Invalid field: " + JxUtil.getFullyQualifiedFieldName(field) + ": Field with (nullable=true & use=Use.OPTIONAL) must be of type: " + Optional.class.getName());
  }

  abstract String elementName();

  String validateUse(final Object value) {
    return value == null && !nullable && use == Use.REQUIRED ? "Property \"" + name + "\" is required: " + value : null;
  }

  Object toNull() {
    return use == Use.OPTIONAL && nullable ? Optional.empty() : null;
  }

  void set(final JxObject object, final Object value, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws InvocationTargetException {
    try {
      if (!optional || value instanceof Optional)
        setMethod.invoke(object, value);
      else if (value != null && !genericType.isInstance(value))
        throw new ValidationException(object.getClass().getName() + "#" + setMethod.getName() + "(" + (setMethod.getParameterTypes().length > 0 ? FastArrays.toString(setMethod.getParameterTypes(), ',', c -> c.getName()) : "") + ") is not compatible with property \"" + name + "\" of type \"" + elementName() + "\" with value: " + value);
      else
        setMethod.invoke(object, Optional.ofNullable(value));

      if (onPropertyDecode != null)
        onPropertyDecode.test(object, name, value);
    }
    catch (final IllegalAccessException e) {
      throw new UnsupportedOperationException(e);
    }
    catch (final IllegalArgumentException e) {
      if (e.getMessage() != null && "argument type mismatch".equals(e.getMessage()))
        throw new ValidationException(object.getClass().getName() + "#" + setMethod.getName() + "(" + (setMethod.getParameterTypes().length > 0 ? FastArrays.toString(setMethod.getParameterTypes(), ',', c -> c.getName()) : "") + ") is not compatible with property \"" + name + "\" of type \"" + elementName() + "\" with value: " + value);

      throw new UnsupportedOperationException(e);
    }
  }
}