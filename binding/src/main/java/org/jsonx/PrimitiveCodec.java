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
import java.lang.reflect.Method;
import java.util.HashMap;

import org.openjax.json.JsonReader;

abstract class PrimitiveCodec extends Codec {
  static Executable getMethod(final HashMap<String,HashMap<String,Executable>> codecToMethod, final String identifier, final Class<?> parameterType) {
    if (identifier.length() == 0)
      return null;

    Executable method;
    final String parameterClassName = parameterType.getName();
    HashMap<String,Executable> parameterTypeToMethod = codecToMethod.get(identifier);
    if (parameterTypeToMethod == null) {
      codecToMethod.put(identifier, parameterTypeToMethod = new HashMap<>(3));
    }
    else if ((method = parameterTypeToMethod.get(parameterClassName)) != null) {
      return method;
    }

    parameterTypeToMethod.put(parameterClassName, method = JsdUtil.parseExecutable(identifier, parameterType));
    return method;
  }

  static final HashMap<String,HashMap<String,Executable>> decodeToMethod = new HashMap<>();
  static final HashMap<String,HashMap<String,Executable>> encodeToMethod = new HashMap<>();

  private final Class<?> type;
  private final Executable decode;

  PrimitiveCodec(final Method getMethod, final Method setMethod, final String name, final boolean property, final Use use, final String decode) {
    super(getMethod, setMethod, name, property, use);
    this.decode = getMethod(decodeToMethod, decode, String.class);
    this.type = JsdUtil.getRealType(getMethod);
  }

  @Override
  Class<?> type() {
    return type;
  }

  @Override
  Executable decode() {
    return decode;
  }

  final Error matches(final String json, final JsonReader reader) {
    return test(json.charAt(0)) ? validate(json, reader) : Error.EXPECTED_TYPE(name, elementName(), json, reader);
  }

  final Object parse(final String json, final boolean strict) {
    if (getMethod.getReturnType().isPrimitive() && (nullable || use == Use.OPTIONAL) && decode() == null)
      throw new ValidationException("Invalid field: " + JsdUtil.getFullyQualifiedMethodName(getMethod) + ": Field with (nullable=true || use=Use.OPTIONAL) and primitive type: " + getMethod.getReturnType() + " must declare a \"decode\" binding to handle null values");

    return parseValue(json, strict);
  }

  abstract Object parseValue(String json, boolean strict);
  abstract Error validate(String json, JsonReader reader);
  abstract boolean test(char firstChar);
}