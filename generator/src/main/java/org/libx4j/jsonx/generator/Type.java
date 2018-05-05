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
import java.util.Map;

public class Type {
  private static final Map<String,Type> qualifiedNameToType = new HashMap<String,Type>();
  static final Type OBJECT = new Type(Object.class, null);

  static Type get(final String packageName, final String compoundClassName, final String superTypePackage, final String superTypeCompoundClassName, final Type genericType) {
    if (packageName == null)
      throw new NullPointerException("packageName == null");

    if (compoundClassName == null)
      throw new NullPointerException("compoundClassName == null");

    if (superTypePackage == null)
      throw new NullPointerException("superTypePackage == null");

    if (superTypeCompoundClassName == null)
      throw new NullPointerException("superTypeCompoundClassName == null");

    Type type = qualifiedNameToType.get(packageName + "." + compoundClassName);
    if (type == null)
      qualifiedNameToType.put(packageName + "." + compoundClassName, type = new Type(packageName, compoundClassName, superTypePackage, superTypeCompoundClassName, genericType));

    return type;
  }

  public static Type get(final String packageName, final String compoundClassName, final Type superType, final Type genericType) {
    return get(packageName, compoundClassName, superType.packageName, superType.compoundClassName, genericType);
  }

  public static Type get(final String packageName, final String compoundClassName, final Class<?> superType, final Type genericType) {
    return get(packageName, compoundClassName, superType.getPackage().getName(), superType.getSimpleName(), genericType);
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

  public static Type get(final String packageName, final String compoundClassName) {
    return get(packageName, compoundClassName, Object.class, null);
  }

  private final String packageName;
  private final String simpleName;
  private final String compoundClassName;
  private final Type superType;
  private final Type genericType;

  private Type(final String packageName, final String compoundClassName, final Type superType, final Type genericType) {
    this.packageName = packageName;
    this.compoundClassName = compoundClassName;
    this.simpleName = compoundClassName.substring(compoundClassName.lastIndexOf('.') + 1);
    qualifiedNameToType.put(packageName + "." + compoundClassName, this);
    this.superType = superType;
    this.genericType = genericType;
  }

  private Type(final String packageName, final String compoundClassName, final String superTypePackage, final String superTypeCompoundClassName, final Type genericType) {
    this(packageName, compoundClassName, superTypeCompoundClassName == null ? null : get(superTypePackage, superTypeCompoundClassName), genericType);
  }

  private Type(final Class<?> clazz, final Type genericType) {
    this(clazz.getPackage().getName(), clazz.getSimpleName(), clazz.getSuperclass() == null ? null : clazz.getSuperclass() == Object.class ? OBJECT : new Type(clazz.getSuperclass(), null), genericType);
  }

  public Type getSuperType() {
    return superType;
  }

  public Type getDeclaringType() {
    final int dot = compoundClassName.lastIndexOf('.');
    return dot < 0 ? null : get(packageName, compoundClassName.substring(0, dot));
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

  public String getSimpleName() {
    return simpleName;
  }

  public String getCompoundClassName() {
    return compoundClassName;
  }

  public String getName() {
    return packageName + "." + compoundClassName;
  }

  @Override
  public String toString() {
    final StringBuilder builder = new StringBuilder(getName());
    if (genericType != null)
      builder.append('<').append(genericType.toString()).append('>');

    return builder.toString();
  }
}