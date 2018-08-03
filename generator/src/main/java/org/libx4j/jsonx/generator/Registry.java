/* Copyright (c) 2017 lib4j
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.lib4j.lang.Classes;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Object;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.Jsonx;

class Registry {
  final class Value {
    private final String name;

    private Value(final String name) {
      if (refToModel.containsKey(name))
        throw new IllegalArgumentException("Value name=\"" + name + "\" already registered");

      refToModel.put(name, null);
      this.name = name;
    }

    public <T extends Model>T value(final T model, final Referrer<?> referrer) {
      refToModel.put(name, model);
      return reference(model, referrer);
    }
  }

  static enum Kind {
    CLASS("class"),
    ANNOTATION("@interface");

    private final String value;

    Kind(final String value) {
      this.value = value;
    }

    @Override
    public String toString() {
      return value;
    }
  }

  final class Type {
    private final Kind kind;
    private final String packageName;
    private final String canonicalPackageName;
    private final String simpleName;
    private final String compoundName;
    private final String canonicalCompoundName;
    private final String name;
    private final String canonicalName;
    private final Type superType;
    private final Type genericType;

    private Type(final Kind kind, final String packageName, final String compoundName, final Type superType, final Type genericType) {
      this.kind = kind;
      final boolean defaultPackage = packageName.length() == 0;
      final int dot = compoundName.lastIndexOf('.');
      this.packageName = packageName;
      this.canonicalPackageName = dot == -1 ? packageName : defaultPackage ? compoundName.substring(0, dot) : packageName + "." + compoundName.substring(0, dot);
      this.compoundName = compoundName;
      this.name = defaultPackage ? compoundName : packageName + "." + compoundName;
      this.canonicalCompoundName = Classes.toCanonicalClassName(compoundName);
      this.simpleName = canonicalCompoundName.substring(canonicalCompoundName.lastIndexOf('.') + 1);
      this.canonicalName = defaultPackage ? canonicalCompoundName : packageName + "." + canonicalCompoundName;
      this.superType = superType;
      this.genericType = genericType;
      qualifiedNameToType.put(toCanonicalString(), this);
    }

    private Type(final Class<?> clazz, final Type genericType) {
      this(clazz.isAnnotation() ? Kind.ANNOTATION : Kind.CLASS, clazz.getPackage().getName(), clazz.getSimpleName(), clazz.getSuperclass() == null ? null : clazz.getSuperclass() == Object.class ? null : new Type(clazz.getSuperclass(), null), genericType);
    }

    public Type getSuperType() {
      return superType;
    }

    public Type getDeclaringType() {
      final String declaringClassName = Classes.getDeclaringClassName(compoundName);
      return compoundName.length() == declaringClassName.length() ? null : getType(Kind.CLASS, packageName, declaringClassName, null, null, null);
    }

    public Type getGreatestCommonSuperType(final Type type) {
      Type a = this;
      do {
        Type b = type;
        do {
          if (a.name.equals(b.name))
            return a;

          b = b.superType;
        }
        while (b != null);
        a = a.superType;
      }
      while (a != null);
      return OBJECT;
    }

    public Kind getKind() {
      return this.kind;
    }

    public String getPackage() {
      return packageName;
    }

    public String getCanonicalPackage() {
      return canonicalPackageName;
    }

    public String getName() {
      return name;
    }

    public String getSimpleName() {
      return simpleName;
    }

    public String getCanonicalName() {
      return canonicalName;
    }

    public String getCompoundName() {
      return compoundName;
    }

    public String getCanonicalCompoundName() {
      return canonicalCompoundName;
    }

    public String getRelativeName(final String packageName) {
      return packageName.length() == 0 ? name : name.substring(packageName.length() + 1);
    }

    public String getSubName(final String superName) {
      return Registry.getSubName(name, superName);
    }

    public String toCanonicalString() {
      final StringBuilder builder = new StringBuilder(canonicalName);
      if (genericType != null)
        builder.append('<').append(genericType.toCanonicalString()).append('>');

      return builder.toString();
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this)
        return true;

      if (!(obj instanceof Type))
        return false;

      return toString().equals(obj.toString());
    }

    @Override
    public int hashCode() {
      return toString().hashCode();
    }

    @Override
    public String toString() {
      final StringBuilder builder = new StringBuilder(name);
      if (genericType != null)
        builder.append('<').append(genericType.toString()).append('>');

      return builder.toString();
    }
  }

  public static String getSubName(final String name, final String superName) {
    return superName != null && name.startsWith(superName) ? name.substring(superName.length() + 1) : name;
  }

  protected Type getAnnotation(final String packageName, final String compoundName) {
    return getType(Kind.ANNOTATION, packageName, compoundName, null, null, null);
  }

  protected Type getType(final Kind kind, final String packageName, final String compoundName, final String superPackageName, final String superCompoundName, final Type genericType) {
    final StringBuilder className = new StringBuilder();
    if (packageName.length() > 0)
      className.append(packageName).append(".");

    className.append(compoundName);
    if (genericType != null)
      className.append('<').append(genericType.toCanonicalString()).append('>');

    final Type type = qualifiedNameToType.get(className.toString());
    return type != null ? type : new Type(kind, packageName, compoundName, superCompoundName == null ? null : getType(Kind.CLASS, superPackageName, superCompoundName, null, null, null), genericType);
  }

  public Type getType(final Class<?> clazz, final Type generic) {
    final String name = clazz.getName() + "<" + (generic != null ? generic.toCanonicalString() : Object.class.getName()) + ">";
    final Type type = qualifiedNameToType.get(name);
    return type != null ? type : new Type(clazz, generic);
  }

  public Type getType(final Class<?> cls) {
    return getType(cls.isAnnotation() ? Kind.ANNOTATION : Kind.CLASS, cls.getPackageName(), Classes.getCompoundName(cls), cls.getSuperclass() == null ? null : cls.getSuperclass().getPackageName(), cls.getSuperclass() == null ? null : Classes.getCompoundName(cls.getSuperclass()), (Type)null);
  }

  public Type getType(final Class<?> ... generics) {
    return getType(0, generics);
  }

  private Type getType(final int index, final Class<?> ... generics) {
    return index == generics.length - 1 ? getType(generics[index]) : getType(generics[index], getType(index + 1, generics));
  }

  public Type getType(final String packageName, final String compoundName, final String superCompoundName) {
    return getType(Kind.CLASS, packageName, compoundName, packageName, superCompoundName, (Type)null);
  }

  private final HashMap<String,Type> qualifiedNameToType = new HashMap<>();
  private final LinkedHashMap<String,Model> refToModel;
  private final LinkedHashMap<String,List<Referrer<?>>> refToReferrers;
  protected final Type OBJECT = getType(Object.class);

  private Registry(final LinkedHashMap<String,Model> refToModel, final LinkedHashMap<String,List<Referrer<?>>> references) {
    this.refToModel = refToModel;
    this.refToReferrers = references;
  }

  public Registry() {
    this(new LinkedHashMap<String,Model>(), new LinkedHashMap<String,List<Referrer<?>>>());
  }

  public Value declare(final Jsonx.Boolean binding) {
    return new Value(binding.getTemplate$().text());
  }

  public Value declare(final Jsonx.Number binding) {
    return new Value(binding.getTemplate$().text());
  }

  public Value declare(final Jsonx.String binding) {
    return new Value(binding.getTemplate$().text());
  }

  public Value declare(final Jsonx.Array binding) {
    return new Value(binding.getTemplate$() != null ? binding.getTemplate$().text() : binding.getClass$().text());
  }

  public Value declare(final Jsonx.Object binding) {
    return new Value(binding.getClass$().text());
  }

  public Value declare(final Class<?> clazz) {
    return new Value(clazz.getName());
  }

  public Value declare(final Id id) {
    return new Value(id.toString());
  }

  public Value declare(final $Object binding) {
    return new Value(ObjectModel.getFullyQualifiedName(binding));
  }

  public <T extends Member>T reference(final T model, final Referrer<?> referrer) {
    if (referrer == null)
      return model;

    final String key = model.id().toString();
    List<Referrer<?>> referrers = refToReferrers.get(key);
    if (referrers == null)
      refToReferrers.put(key, referrers = new ArrayList<>());

    referrers.add(referrer);
    return model;
  }

  public Member getElement(final $Object binding) {
    return refToModel.get(ObjectModel.getFullyQualifiedName(binding));
  }

  public Member getElement(final Id id) {
    return refToModel.get(id.toString());
  }

  public boolean shouldWriteRootMember(final Member member, final Settings settings) {
    final int numReferrers = getNumReferrers(member);
    return member instanceof ArrayModel && ((ArrayModel)member).classType() != null || (member instanceof ObjectModel ? numReferrers == 0 || numReferrers > 1 : numReferrers >= settings.getTemplateThreshold());
  }

  public boolean shouldWriteDirect(final Member member, final Settings settings) {
    final int numReferrers = getNumReferrers(member);
    if (member instanceof ArrayModel && ((ArrayModel)member).classType() != null)
      return false;

    return member instanceof ObjectModel ? numReferrers == 1 : numReferrers < settings.getTemplateThreshold();
  }

  public boolean shouldWriteAsTemplate(final Member member, final Settings settings) {
    if (getElement(member.id()) == null)
      return false;

    if (member instanceof ArrayModel && ((ArrayModel)member).classType() != null)
      return false;

    final int numReferrers = getNumReferrers(member);
    if (member instanceof ObjectModel)
      return numReferrers == 0 || numReferrers > 1;

    return numReferrers >= settings.getTemplateThreshold();
  }

  public Collection<Model> rootElements() {
    return refToModel.values();
  }

  public int size() {
    return refToModel.size();
  }

  public int getNumReferrers(final Member member) {
    final List<Referrer<?>> referrers = refToReferrers.get(member.id().toString());
    return referrers == null ? 0 : referrers.size();
  }
}