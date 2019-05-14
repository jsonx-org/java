/* Copyright (c) 2017 JSONx
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

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

import org.jsonx.www.schema_0_2_2.xL0gluGCXYYJc;
import org.libj.util.Classes;
import org.libj.util.Strings;

class Registry {
  final class Value {
    private final String name;

    private Value(final String name) {
      if (refToModel.containsKey(name))
        throw new IllegalArgumentException("Value name=\"" + name + "\" already registered");

      refToModel.put(name, null);
      this.name = name;
    }

    <T extends Model>T value(final T model, final Referrer<?> referrer) {
      refToModel.put(name, model);
      return reference(model, referrer);
    }
  }

  enum Kind {
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
    private final Type[] genericTypes;

    private Type(final Kind kind, final String packageName, final String compoundName, final Type superType, final Type[] genericTypes) {
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
      this.genericTypes = genericTypes;
      qualifiedNameToType.put(toCanonicalString(), this);
    }

    private Type(final Class<?> cls, final Type ... genericTypes) {
      this(cls.isAnnotation() ? Kind.ANNOTATION : Kind.CLASS, cls.getPackage().getName(), cls.getSimpleName(), cls.getSuperclass() == null ? null : cls.getSuperclass() == Object.class ? null : new Type(cls.getSuperclass(), (Type[])null), genericTypes);
    }

    Type getSuperType() {
      return superType;
    }

    Type getDeclaringType() {
      final String declaringClassName = Classes.getDeclaringClassName(compoundName);
      return compoundName.length() == declaringClassName.length() ? null : getType(Kind.CLASS, packageName, declaringClassName, null, null, null);
    }

    Type getGreatestCommonSuperType(final Type type) {
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

    Kind getKind() {
      return this.kind;
    }

    String getPackage() {
      return packageName;
    }

    String getCanonicalPackage() {
      return canonicalPackageName;
    }

    String getName() {
      return name;
    }

    String getSimpleName() {
      return simpleName;
    }

    String getCanonicalName() {
      return canonicalName;
    }

    String getCompoundName() {
      return compoundName;
    }

    String getCanonicalCompoundName() {
      return canonicalCompoundName;
    }

    String getRelativeName(final String packageName) {
      return packageName.length() == 0 ? name : name.substring(packageName.length() + 1);
    }

    String getSubName(final String superName) {
      return Registry.getSubName(name, superName);
    }

    String toCanonicalString() {
      final StringBuilder builder = new StringBuilder(canonicalName);
      if (genericTypes != null) {
        builder.append('<');
        for (final Type genericType : genericTypes)
          builder.append(genericType.toCanonicalString()).append(',');

        builder.setCharAt(builder.length() - 1, '>');
      }

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
      if (genericTypes != null) {
        builder.append('<');
        for (final Type genericType : genericTypes)
          builder.append(genericType.toString()).append(',');

        builder.setCharAt(builder.length() - 1, '>');
      }

      return builder.toString();
    }
  }

  static String getSubName(final String name, final String superName) {
    return superName != null && superName.length() > 0 && name.startsWith(superName) ? name.substring(superName.length() + 1) : name;
  }

  Type getType(final Kind kind, final String packageName, final String compoundName) {
    return getType(kind, packageName, compoundName, null, null, null);
  }

  Type getType(final String packageName, final String compoundName, final String superCompoundName) {
    return getType(Kind.CLASS, packageName, compoundName, packageName, superCompoundName, (Type)null);
  }

  Type getType(final Kind kind, final String packageName, final String compoundName, final String superPackageName, final String superCompoundName, final Type genericType) {
    final StringBuilder className = new StringBuilder();
    if (packageName.length() > 0)
      className.append(packageName).append('.');

    className.append(compoundName);
    if (genericType != null)
      className.append('<').append(genericType.toCanonicalString()).append('>');

    final Type type = qualifiedNameToType.get(className.toString());
    return type != null ? type : new Type(kind, packageName, compoundName, superCompoundName == null ? null : getType(Kind.CLASS, superPackageName, superCompoundName, null, null, null), genericType == null ? null : new Type[] {genericType});
  }

  Type getType(final Class<?> cls, final Type ... genericTypes) {
    final StringBuilder builder = new StringBuilder(cls.getName());
    if (genericTypes != null) {
      builder.append('<');
      for (final Type genericType : genericTypes)
        builder.append(genericType.toCanonicalString());

      builder.append('>');
    }

    final Type type = qualifiedNameToType.get(builder.toString());
    return type != null ? type : new Type(cls, genericTypes);
  }

  Type getType(final Class<?> cls) {
    return getType(cls.isAnnotation() ? Kind.ANNOTATION : Kind.CLASS, cls.getPackage().getName(), Classes.getCompoundName(cls), cls.getSuperclass() == null ? null : cls.getSuperclass().getPackage().getName(), cls.getSuperclass() == null ? null : Classes.getCompoundName(cls.getSuperclass()), (Type)null);
  }

  Type getType(final Class<?> ... genericClasses) {
    return getType(0, genericClasses);
  }

  private Type getType(final int index, final Class<?> ... genericClasses) {
    return index == genericClasses.length - 1 ? getType(genericClasses[index]) : getType(genericClasses[index], getType(index + 1, genericClasses));
  }

  private final LinkedHashMap<String,Model> refToModel = new LinkedHashMap<>();
  private final LinkedHashMap<String,ReferrerManifest> refToReferrers = new LinkedHashMap<>();

  final String packageName;
  final String classPrefix;

  Registry(final String prefix) {
    if (prefix.length() > 0) {
      final char lastChar = prefix.charAt(prefix.length() - 1);
      if (lastChar == '.') {
        this.packageName = prefix.substring(0, prefix.length() - 1);
        this.classPrefix = "";
      }
      else {
        final int index = prefix.lastIndexOf('.', prefix.length() - 1);
        this.packageName = prefix.substring(0, index);
        this.classPrefix = prefix.substring(index + 1);
      }
    }
    else {
      this.packageName = "";
      this.classPrefix = "";
    }
  }

  @SuppressWarnings("unchecked")
  Registry(final Collection<Class<?>> classes) {
    for (final Class<?> cls : classes) {
      if (cls.isAnnotation())
        ArrayModel.referenceOrDeclare(this, (Class<? extends Annotation>)cls);
      else
        ObjectModel.referenceOrDeclare(this, cls);
    }

    this.packageName = getClassPrefix();
    this.classPrefix = "";
  }

  private String getClassPrefix() {
    final HashSet<Registry.Type> types = new HashSet<>();
    if (getModels() != null)
      for (final Model member : getModels())
        member.getDeclaredTypes(types);

    final String classPrefix = Strings.getCommonPrefix(types.stream().map(t -> t.getPackage()).toArray(String[]::new));
    if (classPrefix == null)
      return null;

    return classPrefix;
  }

  private static class ReferrerManifest {
    final ArrayList<Referrer<?>> referrers = new ArrayList<>();
    final HashSet<Class<?>> referrerTypes = new HashSet<>();

    void add(final Referrer<?> referrer) {
      referrers.add(referrer);
      referrerTypes.add(referrer.getClass());
    }

    int getNumReferrers() {
      return referrers.size();
    }

    boolean hasReferrerType(final Class<?> type) {
      return referrerTypes.contains(type);
    }
  }

  private final HashMap<String,Type> qualifiedNameToType = new HashMap<>();
  private final ArrayList<Runnable> deferredReferences = new ArrayList<>();
  final Type OBJECT = getType(Object.class);

  Value declare(final xL0gluGCXYYJc.Schema.Boolean binding) {
    return new Value(binding.getName$().text());
  }

  Value declare(final xL0gluGCXYYJc.Schema.Number binding) {
    return new Value(binding.getName$().text());
  }

  Value declare(final xL0gluGCXYYJc.Schema.String binding) {
    return new Value(binding.getName$().text());
  }

  Value declare(final xL0gluGCXYYJc.Schema.Array binding) {
    return new Value(binding.getName$().text());
  }

  Value declare(final xL0gluGCXYYJc.Schema.Object binding) {
    return new Value(binding.getName$().text());
  }

  Value declare(final Id id) {
    return new Value(id.toString());
  }

  Value declare(final xL0gluGCXYYJc.$Object binding) {
    return new Value(ObjectModel.getFullyQualifiedName(binding));
  }

  <T extends Member>T reference(final T model, final Referrer<?> referrer) {
    final Runnable runnable = () -> {
      if (model instanceof Referrer)
        ((Referrer<?>)model).resolveReferences();

      final String key = model.id.toString();
      ReferrerManifest referrers = refToReferrers.get(key);
      if (referrers == null)
        refToReferrers.put(key, referrers = new ReferrerManifest());

      if (referrer != null)
        referrers.add(referrer);
    };

    deferredReferences.add(runnable);
    return model;
  }

  void resolveReferences() {
    for (int i = 0; i < deferredReferences.size(); ++i)
      deferredReferences.get(i).run();

    deferredReferences.clear();
  }

  boolean isPending(final Id id) {
    return refToModel.get(id.toString()) == null && refToModel.containsKey(id.toString());
  }

  Model getModel(final Id id) {
    return refToModel.get(id.toString());
  }

  Collection<Model> getModels() {
    return refToModel.values();
  }

  boolean isRootMember(final Member member, final Settings settings) {
    if (deferredReferences.size() > 0)
      throw new IllegalStateException("Deferred references have not been resolved");

    if (member instanceof AnyModel)
      return false;

    final ReferrerManifest referrers = refToReferrers.get(member.id.toString());
    final int numReferrers = referrers == null ? 0 : referrers.getNumReferrers();
    if (member instanceof ArrayModel)
      return ((ArrayModel)member).classType() != null;

    if (member instanceof ObjectModel)
      return numReferrers == 0 || numReferrers > 1 || referrers.hasReferrerType(AnyModel.class) || referrers.hasReferrerType(ArrayModel.class);

    return numReferrers >= settings.getTemplateThreshold() || referrers.hasReferrerType(AnyModel.class);
  }
}