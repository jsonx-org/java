/* Copyright (c) 2018 lib4j
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

package org.libx4j.jsonx.generator;

import java.util.HashMap;

class Type {
  private static final HashMap<String,Type> qualifiedNameToType = new HashMap<String,Type>();
  static final Type OBJECT = new Type(Object.class, null);

  static Type get(final String className, final String superTypeName, final Type genericType) {
    if (className == null)
      throw new NullPointerException("className == null");

    if (superTypeName == null)
      throw new NullPointerException("superTypeName == null");

    Type type = qualifiedNameToType.get(className);
    if (type == null)
      qualifiedNameToType.put(className, type = new Type(className, superTypeName, genericType));

    return type;
  }

  public static Type get(final String className, final Type superType, final Type genericType) {
    return get(className, superType.name, genericType);
  }

  public static Type get(final String className, final Class<?> superType, final Type genericType) {
    return get(className, superType.getName(), genericType);
  }

  private static Type get(final Class<?> clazz) {
    if (clazz == null)
      return null;

    Type type = qualifiedNameToType.get(clazz.getName());
    if (type == null)
      qualifiedNameToType.put(clazz.getName(), type = new Type(clazz, null));

    return type;
  }

  public static Type get(final Class<?> clazz, final Type generic) {
    final String name = clazz.getName() + " " + generic.toString();
    Type type = qualifiedNameToType.get(name);
    if (type == null)
      qualifiedNameToType.put(name, type = new Type(clazz, generic));

    return type;
  }

  public static Type get(final Class<?> ... generics) {
    return get(0, generics);
  }

  private static Type get(final int index, final Class<?> ... generics) {
    return index == generics.length - 1 ? get(generics[index]) : get(generics[index], get(index + 1, generics));
  }

  public static Type get(final String className) {
    return get(className, Object.class, null);
  }

  public static Type get(final String packageName, final String compoundClassName) {
    return get(packageName != null ? packageName + "." + compoundClassName : compoundClassName, Object.class, null);
  }

  public static String getSubName(final String name, final String pacakgeName) {
    return pacakgeName != null && name.startsWith(pacakgeName) ? name.substring(pacakgeName.length() + 1) : name;
  }

  private final String packageName;
  private final String simpleName;
  private final String compoundClassName;
  private final String strictCompoundClassName;
  private final String name;
  private final String strictName;
  private final Type superType;
  private final Type genericType;

  private Type(final String className, final Type superType, final Type genericType) {
    final int lastDot = className.lastIndexOf('.');
    if (lastDot > -1) {
      this.packageName = className.substring(0, lastDot);
      this.compoundClassName = className.substring(lastDot + 1);
    }
    else {
      this.packageName = null;
      this.compoundClassName = className;
    }

    this.name = className;
    this.strictCompoundClassName = compoundClassName.replace('$', '.');
    this.simpleName = compoundClassName.substring(compoundClassName.lastIndexOf('$') + 1);
    this.strictName = packageName + "." + strictCompoundClassName;
    qualifiedNameToType.put(name, this);
    this.superType = superType;
    this.genericType = genericType;
  }

  private Type(final String className, final String superTypeName, final Type genericType) {
    this(className, superTypeName == null ? null : get(superTypeName), genericType);
  }

  private Type(final Class<?> clazz, final Type genericType) {
    this(clazz.getName(), clazz.getSuperclass() == null ? null : clazz.getSuperclass() == Object.class ? OBJECT : new Type(clazz.getSuperclass(), null), genericType);
  }

  public Type getSuperType() {
    return superType;
  }

  public Type getDeclaringType() {
    final int del = name.lastIndexOf('$');
    return del < 0 ? null : get(name.substring(0, del));
  }

  public Type getGreatestCommonSuperType(final Type type) {
    Type a = this;
    do {
      Type b = type;
      do {
        if (a.getName().equals(b.getName()))
          return a;

        b = b.superType;
      }
      while (b != null);
      a = a.superType;
    }
    while (a != null);
    return OBJECT;
  }

  public String getPackage() {
    return packageName;
  }

  public String getSimpleName() {
    return simpleName;
  }

  public String getCompoundClassName() {
    return compoundClassName;
  }

  public String getStrictCompoundClassName() {
    return strictCompoundClassName;
  }

  public String getName() {
    return name;
  }

  public String getStrictName() {
    return strictName;
  }

  public String getSubName(final String pacakgeName) {
    return getSubName(name, pacakgeName);
  }

  public String toStrictString() {
    final StringBuilder builder = new StringBuilder(getStrictName());
    if (genericType != null)
      builder.append('<').append(genericType.toStrictString()).append('>');

    return builder.toString();
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder(getName());
    if (genericType != null)
      builder.append('<').append(genericType.toString()).append('>');

    return builder.toString();
  }
}