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
import java.util.Map;
import java.util.Optional;

import org.libj.lang.Classes;
import org.openjax.json.JsonReader;

abstract class PrimitiveCodec extends Codec {
  static Executable getMethod(final Map<String,Executable> codecToMethod, final String identifier, final Class<?> parameterType) {
    if (identifier.isEmpty())
      return null;

    final String key = identifier + "(" + parameterType.getName() + ")";
    Executable method = codecToMethod.get(key);
    if (method == null)
      codecToMethod.put(key, method = JsdUtil.parseExecutable(identifier, parameterType));

    return method;
  }

  static final Map<String,Executable> decodeToMethod = new HashMap<>();
  static final Map<String,Executable> encodeToMethod = new HashMap<>();

  private final Class<?> type;
  private final Executable decode;

  PrimitiveCodec(final Method getMethod, final Method setMethod, final String name, final boolean property, final Use use, final String decode) {
    super(getMethod, setMethod, name, property, use);
    this.decode = getMethod(decodeToMethod, decode, String.class);
    this.type = getMethod.getReturnType() == Optional.class ? Classes.getGenericParameters(getMethod)[0] : getMethod.getReturnType();
  }

  @Override
  Class<?> type() {
    return this.type;
  }

  @Override
  Executable decode() {
    return this.decode;
  }

  final Error matches(final String json, final JsonReader reader) {
    return test(json.charAt(0)) ? validate(json, reader) : Error.EXPECTED_TYPE(name, elementName(), json, reader);
  }

  final Object parse(final String json) {
    if (getMethod.getReturnType().isPrimitive() && (nullable || use == Use.OPTIONAL) && decode() == null)
      throw new ValidationException("Invalid field: " + JsdUtil.getFullyQualifiedMethodName(getMethod) + ": Field with (nullable=true || use=Use.OPTIONAL) and primitive type: " + getMethod.getReturnType() + " must declare a \"decode\" binding to handle null values");

    return parseValue(json);
  }

  abstract Object parseValue(String json);
  abstract Error validate(String json, JsonReader reader);
  abstract boolean test(char firstChar);
}