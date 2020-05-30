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
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.Optional;

import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Object;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.Schema;
import org.libj.lang.Classes;
import org.libj.lang.Strings;

class Registry {
  private static final String[] primitiveTypeNames = {"boolean", "byte", "char", "double", "float", "int", "long", "short"};
  private static final Class<?>[] wrapperTypes = {Boolean.class, Byte.class, Character.class, Double.class, Float.class, Integer.class, Long.class, Short.class};

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

  enum Wildcard {
    EXTENDS("? extends "),
    SUPER("? super ");

    private String name;

    private Wildcard(final String name) {
      this.name = name;
    }

    @Override
    public String toString() {
      return name;
    }
  }

  final class Type {
    final class Generic {
      private final Type type = Type.this;
      private final Wildcard wildcard;

      private Generic(final Wildcard wildcard) {
        // Remove "? extends" if the target class is final
        if (wildcard == Wildcard.EXTENDS && type.cls != null && Modifier.isFinal(type.cls.getModifiers()))
          this.wildcard = null;
        else
          this.wildcard = wildcard;
      }

      private String toString(final boolean canonical) {
        return wildcard != null ? wildcard + type.toString(canonical) : type.toString(canonical);
      }

      @Override
      public String toString() {
        return toString(false);
      }
    }

    private final Class<?> cls;
    private final Kind kind;
    private final boolean isArray;
    private final boolean isPrimitive;
    private final Type wrapper;
    private final String packageName;
    private final String canonicalPackageName;
    private final String simpleName;
    private final String compoundName;
    private final String canonicalCompoundName;
    private final String name;
    private final String canonicalName;
    private final Type superType;
    private final Generic[] genericTypes;

    Type(final Kind kind, final String packageName, String compoundName, Type superType, final Generic[] genericTypes) {
      this.kind = kind;
      final boolean defaultPackage = packageName.length() == 0;
      final int dot = compoundName.lastIndexOf('.');
      this.packageName = packageName;

      this.isArray = compoundName.endsWith("[]");
      if (isArray)
        compoundName = compoundName.substring(0, compoundName.length() - 2);

      this.canonicalPackageName = dot == -1 ? packageName : defaultPackage ? compoundName.substring(0, dot) : packageName + "." + compoundName.substring(0, dot);
      this.compoundName = compoundName;
      this.name = defaultPackage ? compoundName : packageName + "." + compoundName;
      this.canonicalCompoundName = Classes.toCanonicalClassName(compoundName);
      this.simpleName = canonicalCompoundName.substring(canonicalCompoundName.lastIndexOf('.') + 1);
      this.canonicalName = defaultPackage ? canonicalCompoundName : packageName + "." + canonicalCompoundName;
      this.cls = Classes.forNameOrNull(name, false, ClassLoader.getSystemClassLoader());
      if (superType == null && this.cls != null && this.cls != Object.class) {
        superType = cls.getSuperclass() == null ? null : getType(cls.getSuperclass());
        this.superType = OBJECT.equals(superType) ? null : superType;
      }
      else {
        this.superType = superType;
      }

      this.genericTypes = genericTypes == null || genericTypes.length == 0 || genericTypes.length == 1 && genericTypes[0] == null ? null : genericTypes;
      final int primitiveIndex = kind != Kind.CLASS || !packageName.isEmpty() || superType != null || this.genericTypes != null ? -1 : Arrays.binarySearch(primitiveTypeNames, compoundName);
      this.isPrimitive = primitiveIndex > -1;
      this.wrapper = isPrimitive ? getType(wrapperTypes[primitiveIndex]) : null;
      qualifiedNameToType.put(toCanonicalString(), this);
    }

    private Type(final Class<?> cls, final Generic[] genericTypes) {
      this(cls.isAnnotation() ? Kind.ANNOTATION : Kind.CLASS, cls.getPackage().getName(), cls.getSimpleName(), cls.getSuperclass() == null ? null : cls.getSuperclass() == Object.class ? null : new Type(cls.getSuperclass(), (Generic[])null), genericTypes);
    }

    Type getSuperType() {
      return superType;
    }

    Type getDeclaringType() {
      final String declaringClassName = Classes.getDeclaringClassName(compoundName);
      return compoundName.length() == declaringClassName.length() ? null : getType(Kind.CLASS, packageName, declaringClassName, null, null, (Generic[])null);
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
      return toString(true);
    }

    private String toString(final boolean canonical) {
      final StringBuilder builder = new StringBuilder(canonical ? canonicalName : name);
      if (genericTypes != null) {
        builder.append('<');
        for (final Generic genericType : genericTypes) {
          builder.append(canonical ? genericType.toString(canonical) : genericType.toString()).append(',');
        }

        builder.setCharAt(builder.length() - 1, '>');
      }

      if (isArray)
        builder.append("[]");

      return builder.toString();
    }

    String getNativeName() {
      if (!isArray)
        return isPrimitive() ? getWrapper().getName() : getName();

      if (!isPrimitive)
        return "[L" + getName() + ";";

      return "[" + getName().substring(0, 1).toUpperCase();
    }

    boolean isPrimitive() {
      return isPrimitive;
    }

    public boolean isArray() {
      return this.isArray;
    }

    public Type getWrapper() {
      return this.wrapper;
    }

    public boolean isAssignableFrom(Type type) {
      if (isArray != type.isArray)
        return false;

      if (isPrimitive && type.isPrimitive)
        return this == type;

      Type target = isPrimitive ? wrapper : this;
      if (type.isPrimitive)
        type = type.wrapper;

      final String str = target.toString();
      for (; type != null; type = type.superType)
        if (str.equals(type.toString()))
          return true;

      return false;
    }

    private IdentityHashMap<Wildcard,Generic> wildcardToGeneric;

    public Generic asGeneric(final Wildcard wildcard) {
      if (wildcardToGeneric == null)
        wildcardToGeneric = new IdentityHashMap<>(Wildcard.values().length);

      Generic generic = wildcardToGeneric.get(wildcard);
      if (generic == null)
        wildcardToGeneric.put(wildcard, generic = new Generic(wildcard));

      return generic;
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
      int hashCode = 31 + name.hashCode();
      hashCode = 31 * hashCode + Arrays.hashCode(genericTypes);
      return hashCode;
    }

    @Override
    public String toString() {
      return toString(false);
    }
  }

  static String getSubName(final String name, final String superName) {
    return superName != null && superName.length() > 0 && name.startsWith(superName) ? name.substring(superName.length() + 1) : name;
  }

  Type getType(final Kind kind, final String className) {
    final int lt;
    // FIXME: What about "? extends" and "? super"?
    final int gt = className.lastIndexOf('>');
    final Type.Generic genericType;
    if (gt > -1) {
      int i = lt = className.indexOf('<');
      // FIXME: This does not handle multiple generic type parameters (i.e. with `,`).
      final Wildcard wildcard = className.charAt(lt + 1) != '?' ? null : className.charAt(lt + 3) == 'e' ? Wildcard.EXTENDS : Wildcard.SUPER;
      if (wildcard != null)
        i = className.indexOf(' ', i + 7);

      genericType = getType(Kind.CLASS, className.substring(i + 1, gt), null).asGeneric(wildcard);
    }
    else {
      lt = className.length();
      genericType = null;
    }

    final int dot = className.lastIndexOf('.', lt);
    return dot == -1 ? getType(kind, "", className, genericType) : getType(kind, className.substring(0, dot), className.substring(dot + 1, lt), genericType);
  }

  Type getType(final Kind kind, final String packageName, final String compoundName, final Type.Generic ... genericTypes) {
    return getType(kind, packageName, compoundName, null, null, genericTypes);
  }

  Type getType(final String packageName, final String compoundName, final String superCompoundName, final Type.Generic ... genericTypes) {
    return getType(Kind.CLASS, packageName, compoundName, packageName, superCompoundName, genericTypes);
  }

  Type getType(final Kind kind, final String packageName, final String compoundName, final String superPackageName, final String superCompoundName) {
    return getType(kind, packageName, compoundName, superPackageName, superCompoundName, (Type.Generic[])null);
  }

  Type getType(final Kind kind, final String packageName, final String compoundName, final String superPackageName, final String superCompoundName, final Type.Generic ... genericTypes) {
    final StringBuilder className = new StringBuilder();
    if (packageName.length() > 0)
      className.append(packageName).append('.');

    className.append(compoundName);
    final Type type = qualifiedNameToType.get(className.toString());
    return type != null ? type : new Type(kind, packageName, compoundName, superCompoundName == null ? null : getType(Kind.CLASS, superPackageName, superCompoundName, null, null, (Type.Generic[])null), genericTypes);
  }

  Type getOptionalType(final Type.Generic genericType) {
    return getType(Optional.class, genericType);
  }

  Type getType(final Class<?> cls, final Type.Generic ... genericTypes) {
    final StringBuilder builder = new StringBuilder(cls.getName());
    if (genericTypes != null) {
      builder.append('<');
      for (final Type.Generic genericType : genericTypes)
        builder.append(genericType.toString(true));

      builder.append('>');
    }

    final Type type = qualifiedNameToType.get(builder.toString());
    return type != null ? type : new Type(cls, genericTypes);
  }

  Type getType(final Class<?> cls) {
    return getType(cls.isAnnotation() ? Kind.ANNOTATION : Kind.CLASS, cls.getPackage().getName(), Classes.getCompoundName(cls), cls.getSuperclass() == null ? null : cls.getSuperclass().getPackage().getName(), cls.getSuperclass() == null ? null : Classes.getCompoundName(cls.getSuperclass()));
  }

  private final LinkedHashMap<String,Model> refToModel = new LinkedHashMap<>();
  private final LinkedHashMap<String,ReferrerManifest> refToReferrers = new LinkedHashMap<>();

  final boolean isFromJsd;
  final String packageName;
  final String classPrefix;

  Registry(final String prefix) {
    this.isFromJsd = true;
    if (prefix.length() > 0) {
      final char lastChar = prefix.charAt(prefix.length() - 1);
      if (lastChar == '.') {
        this.packageName = prefix.substring(0, prefix.length() - 1);
        this.classPrefix = "";
      }
      else {
        final int index = prefix.lastIndexOf('.');
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
  Registry(final Declarer declarer, final Collection<Class<?>> classes) {
    this.isFromJsd = false;
    for (final Class<?> cls : classes) {
      if (cls.isAnnotation())
        ArrayModel.referenceOrDeclare(this, declarer, (Class<? extends Annotation>)cls);
      else
        ObjectModel.referenceOrDeclare(this, declarer, cls);
    }

    this.packageName = getClassPrefix();
    this.classPrefix = "";
  }

  private String getClassPrefix() {
    final HashSet<Registry.Type> types = new HashSet<>();
    if (getModels() != null)
      for (final Model member : getModels())
        member.getDeclaredTypes(types);

    return Strings.getCommonPrefix(types.stream().map(Registry.Type::getPackage).toArray(String[]::new));
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

  Value declare(final Schema.Boolean xsb) {
    return new Value(xsb.getName$().text());
  }

  Value declare(final Schema.Number xsb) {
    return new Value(xsb.getName$().text());
  }

  Value declare(final Schema.String xsb) {
    return new Value(xsb.getName$().text());
  }

  Value declare(final Schema.Array xsb) {
    return new Value(xsb.getName$().text());
  }

  Value declare(final Schema.Object xsb) {
    return new Value(xsb.getName$().text());
  }

  Value declare(final Id id) {
    return new Value(id.toString());
  }

  Value declare(final $Object xsb) {
    return new Value(ObjectModel.getFullyQualifiedName(xsb));
  }

  <T extends Member>T reference(final T model, final Referrer<?> referrer) {
    deferredReferences.add(() -> {
      if (model instanceof Referrer)
        ((Referrer<?>)model).resolveReferences();

      final String key = model.id().toString();
      ReferrerManifest referrers = refToReferrers.get(key);
      if (referrers == null)
        refToReferrers.put(key, referrers = new ReferrerManifest());

      if (referrer != null)
        referrers.add(referrer);
    });
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
    if (isFromJsd)
      return member.declarer instanceof SchemaElement;

    if (deferredReferences.size() > 0)
      throw new IllegalStateException("Deferred references have not been resolved");

    if (member instanceof ObjectModel)
      return true;

    if (member instanceof AnyModel)
      return false;

    final ReferrerManifest referrers = refToReferrers.get(member.id().toString());
    final int numReferrers = referrers != null ? referrers.getNumReferrers() : 0;
    if (member instanceof ArrayModel)
      return ((ArrayModel)member).classType() != null;

    return numReferrers >= settings.getTemplateThreshold() || referrers != null && referrers.hasReferrerType(AnyModel.class);
  }
}