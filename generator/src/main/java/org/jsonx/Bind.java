/* Copyright (c) 2020 JSONx
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

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.jsonx.www.schema_0_4.xL0gluGCXAA.$TypeBinding;
import org.libj.lang.Identifiers;
import org.libj.lang.ObjectUtil;
import org.libj.util.ArrayUtil;

final class Bind {
  static class Type {
    static Type from(final Registry registry, final $TypeBinding.Type$ type, final $TypeBinding.Decode$ decode, final $TypeBinding.Encode$ encode) {
      return type == null && decode == null && encode == null ? null : new Type(registry, type == null ? null : type.text(), decode == null ? null : decode.text(), encode == null ? null : encode.text());
    }

    static Type from(final Registry registry, final Method getMethod, final boolean nullable, final Use use, final String decode, final String encode, final Class<?> defaultClass) {
      final String typeName = Model.isAssignable(getMethod, false, defaultClass, false, nullable, use) ? null : getClassName(getMethod, nullable, use);
      return typeName == null && (encode == null || encode.length() == 0) && (decode == null || decode.length() == 0) ? null : new Type(registry, typeName, decode, encode);
    }

    static Type from(final Registry registry, final Class<?> type, final String decode, final String encode, final Class<?> ... defaultClasses) {
      final String typeName = ArrayUtil.contains(defaultClasses, type) ? null : getClassName(type);
      return typeName == null && (encode == null || encode.length() == 0) && (decode == null || decode.length() == 0) ? null : new Type(registry, typeName, decode, encode);
    }

    static String getClassName(final Method getMethod, final boolean nullable, final Use use) {
      final boolean expectOptional = nullable && use == Use.OPTIONAL;
      final Class<?> returnType = getMethod.getReturnType();
      if (returnType.isArray())
        return getClassName(returnType);

      final java.lang.reflect.Type genericType = getMethod.getGenericReturnType();
      if (!expectOptional)
        return genericType.getTypeName();

      if (!(genericType instanceof ParameterizedType))
        throw new ValidationException("Expected " + Optional.class.getName() + " return type from method: " + getMethod.getName());

      final java.lang.reflect.Type[] genericTypes = ((ParameterizedType)genericType).getActualTypeArguments();
      if (genericTypes.length != 1)
        throw new ValidationException("Expected " + Optional.class.getName() + " return type from method: " + getMethod.getName());

      return getClassName((Class<?>)genericTypes[0]);
    }

    static String getClassName(final Class<?> cls) {
      return cls.isArray() ? cls.getComponentType().getName() + "[]" : cls.getName();
    }

    final Registry.Type type;
    final String decode;
    final String encode;
    private final Id id;

    private Type(final Registry registry, final String typeName, final String decode, final String encode) {
      this.type = typeName == null ? null : registry.getType(Registry.Kind.CLASS, typeName);
      this.decode = decode == null || decode.length() == 0 ? null : decode;
      this.encode = encode == null || encode.length() == 0 ? null : encode;
      if (type == null && decode == null && encode == null)
        throw new IllegalArgumentException();

      this.id = Id.hashed("!", String.valueOf(type), decode, encode);
    }

    private Type(final Type binding, final $TypeBinding.Decode$ decode, final $TypeBinding.Encode$ encode) {
      this.type = binding == null ? null : binding.type;
      this.decode = decode == null ? null : decode.text();
      this.encode = encode == null ? null : encode.text();
      if (type == null && decode == null && encode == null)
        throw new IllegalArgumentException();

      this.id = Id.hashed("!", String.valueOf(type), decode, encode);
    }

    @Override
    public int hashCode() {
      int hashCode = 1;
      if (type != null)
        hashCode = hashCode * 31 + type.hashCode();

      if (decode != null)
        hashCode = hashCode * 31 + decode.hashCode();

      if (encode != null)
        hashCode = hashCode * 31 + encode.hashCode();

      return hashCode;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof Type))
        return false;

      final Type that = (Type)obj;
      if (!Objects.equals(type, that.type))
        return false;

      if (!Objects.equals(decode, that.decode))
        return false;

      if (!Objects.equals(encode, that.encode))
        return false;

      return true;
    }

    @Override
    public String toString() {
      return id.toString();
    }
  }

  static class Field {
    static Field from(final String propertyName, final String fieldName) {
      return new Field(propertyName, fieldName);
    }

    final String instanceCase;
    final String classCase;
    final String field;

    private Field(final String propertyName, final String fieldName) {
      this.field = fieldName;
      if (propertyName == null) {
        this.instanceCase = null;
        this.classCase = null;
      }
      else {
        final String classCase;
        final String instanceCase = JsdUtil.toInstanceName(propertyName);
        if (fieldName == null || fieldName.equals(instanceCase) || fieldName.equals(JsdUtil.toInstanceName(JsdUtil.fixReserved(propertyName)))) {
          this.instanceCase = instanceCase;
          classCase = JsdUtil.toClassName(propertyName);
        }
        else if (Identifiers.isValid(fieldName, false)) {
          this.instanceCase = fieldName;
          classCase = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        }
        else {
          throw new ValidationException("\"" + fieldName + "\" is not a valid identifier as defined in the Java Language Specification");
        }

        this.classCase = JsdUtil.fixReserved(classCase);
      }
    }

    public boolean isAssignableFrom(final Field t) {
      if (t == null)
        return false;

      if (t.field != null && field != null && !t.field.equals(field))
        return false;

      return true;
    }

    @Override
    public int hashCode() {
      int hashCode = 1;
      if (instanceCase != null)
        hashCode = hashCode * 31 + instanceCase.hashCode();

      if (classCase != null)
        hashCode = hashCode * 31 + classCase.hashCode();

      if (field != null)
        hashCode = hashCode * 31 + field.hashCode();

      return hashCode;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof Field))
        return false;

      final Field that = (Field)obj;
      if (!Objects.equals(instanceCase, that.instanceCase))
        return false;

      if (!Objects.equals(classCase, that.classCase))
        return false;

      if (!Objects.equals(field, that.field))
        return false;

      return true;
    }

    @Override
    public String toString() {
      return ObjectUtil.simpleIdentityString(this) + "\n  field: " + field + "\n  instanceCase: " + instanceCase + "\n  classCase: " + classCase;
    }
  }

  public static Map<String,Object> toXmlAttributes(final Element owner, final Type type, final Field field) {
    final AttributeMap attributes = new AttributeMap();
    if (type != null) {
      if (type.type != null)
        attributes.put("type", type.type.toString());

      if (type.decode != null)
        attributes.put("decode", type.decode);

      if (type.encode != null)
        attributes.put("encode", type.encode);
    }

    if (!(owner instanceof SchemaElement) && !(owner instanceof ArrayModel) && field != null && field.field != null)
      attributes.put("field", field.field);

    if (attributes.size() == 0)
      return null;

    attributes.put("lang", "java");
    return attributes;
  }

  private Bind() {
  }
}