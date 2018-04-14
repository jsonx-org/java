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
  private static final Type OBJECT = new Type(Object.class);

  public static Type get(final String packageName, final String simpleName, final String superTypePackage, final String superTypeSimpleName) {
    if (packageName == null)
      throw new NullPointerException("packageName == null");

    if (simpleName == null)
      throw new NullPointerException("simpleName == null");

    if (superTypePackage == null)
      throw new NullPointerException("superTypePackage == null");

    if (superTypeSimpleName == null)
      throw new NullPointerException("superTypeSimpleName == null");

    Type type = qualifiedNameToType.get(packageName + "." + simpleName);
    if (type == null)
      qualifiedNameToType.put(packageName + "." + simpleName, type = new Type(packageName, simpleName, superTypePackage, superTypeSimpleName));

    return type;
  }

  public static Type get(final String packageName, final String simpleName, final Class<?> superType) {
    return get(packageName, simpleName, superType.getPackage().getName(), superType.getSimpleName());
  }

  public static Type get(final Class<?> clazz) {
    if (clazz == null)
      return null;

    Type type = qualifiedNameToType.get(clazz.getName());
    if (type == null)
      qualifiedNameToType.put(clazz.getName(), type = new Type(clazz));

    return type;
  }

  public static Type get(final String packageName, final String simpleName) {
    return get(packageName, simpleName, Object.class);
  }

  private final String packageName;
  private final String simpleName;
  private final String superTypePackage;
  private final String superTypeName;
  private final Type superType;

  private Type(final String packageName, final String simpleName, final String superTypePackage, final String superTypeSimpleName) {
    this.packageName = packageName;
    this.simpleName = simpleName;
    this.superTypePackage = superTypePackage;
    this.superTypeName = superTypeSimpleName;
    qualifiedNameToType.put(packageName + "." + simpleName, this);
    this.superType = superTypeSimpleName == null ? null : get(superTypePackage, superTypeSimpleName);
  }

  private Type(final String packageName, final String simpleName, final Type superType) {
    this.packageName = packageName;
    this.simpleName = simpleName;
    if (superType != null) {
      this.superTypePackage = superType.superTypePackage;
      this.superTypeName = superType.getName();
    }
    else {
      this.superTypePackage = null;
      this.superTypeName = null;
    }

    qualifiedNameToType.put(packageName + "." + simpleName, this);
    this.superType = superType;
  }

  private Type(final Class<?> clazz) {
    this(clazz.getPackage().getName(), clazz.getSimpleName(), clazz.getSuperclass() == null ? null : clazz.getSuperclass() == Object.class ? OBJECT : new Type(clazz.getSuperclass()));
  }

  public String getName() {
    return packageName + "." + simpleName;
  }

  public Type getSuperType() {
    return superType;
  }

  public Type getContainerType() {
    int dot = simpleName.lastIndexOf('.');
    return dot < 0 ? null : get(packageName, simpleName.substring(0, dot));
  }
}