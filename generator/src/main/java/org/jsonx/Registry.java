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

import static org.libj.lang.Assertions.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Optional;

import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Object;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.Schema;
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

    <T extends Model> T value(final T model, final Referrer<?> referrer) {
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

  final class GenericType extends Type {
    private final Wildcard wildcard;

    private GenericType(final Type type, final Wildcard wildcard) {
      super(type, type.genericTypes);
      // Remove "? extends" if the target class is final
      if (wildcard == Wildcard.EXTENDS && cls != null && Modifier.isFinal(cls.getModifiers()))
        this.wildcard = null;
      else
        this.wildcard = wildcard;
    }

    @Override
    String toString(final boolean canonical) {
      return wildcard != null ? wildcard + super.toString(canonical) : super.toString(canonical);
    }

    @Override
    public String toString() {
      return toString(false);
    }
  }

  class Type {
    final Class<?> cls;
    final Kind kind;
    final boolean isArray;
    final boolean isPrimitive;
    final Type wrapper;
    final String packageName;
    final String canonicalPackageName;
    final String simpleName;
    final String compositeName;
    final String canonicalCompositeName;
    final String name;
    final String canonicalName;
    final Type superType;
    final Type[] genericTypes;

    Type(final Type type, final Type[] genericTypes) {
      this.cls = type.cls;
      this.kind = type.kind;
      this.isArray = type.isArray;
      this.isPrimitive = type.isPrimitive;
      this.wrapper = type.wrapper;
      this.packageName = type.packageName;
      this.canonicalPackageName = type.canonicalPackageName;
      this.simpleName = type.simpleName;
      this.compositeName = type.compositeName;
      this.canonicalCompositeName = type.canonicalCompositeName;
      this.name = type.name;
      this.canonicalName = type.canonicalName;
      this.superType = type.superType;
      this.genericTypes = genericTypes;
    }

    Type(final Kind kind, final String packageName, String compositeName, Type superType, final GenericType[] genericTypes) {
      this.kind = kind;
      final boolean isDefaultPackage = packageName.length() == 0;
      final int dot = compositeName.lastIndexOf('.');
      this.packageName = packageName;

      this.isArray = compositeName.endsWith("[]");
      if (isArray)
        compositeName = compositeName.substring(0, compositeName.length() - 2);

      final String canonicalPackageName = dot == -1 ? packageName : isDefaultPackage ? compositeName.substring(0, dot) : packageName + "." + compositeName.substring(0, dot);
      this.canonicalPackageName = canonicalPackageName.length() == 0 ? null : canonicalPackageName;
      this.compositeName = compositeName;
      this.name = isDefaultPackage ? compositeName : packageName + "." + compositeName;

      this.canonicalCompositeName = Classes.toCanonicalClassName(compositeName);
      this.simpleName = canonicalCompositeName.substring(canonicalCompositeName.lastIndexOf('.') + 1);
      this.canonicalName = isDefaultPackage ? canonicalCompositeName : packageName + "." + canonicalCompositeName;
      this.cls = Classes.forNameOrNull(name, false, Thread.currentThread().getContextClassLoader());
      if (superType == null && this.cls != null && this.cls != Object.class) {
        superType = cls.getSuperclass() == null ? null : getType(cls.getSuperclass());
        this.superType = OBJECT.equals(superType) ? null : superType;
      }
      else {
        this.superType = superType;
      }

      this.genericTypes = genericTypes == null || genericTypes.length == 0 || genericTypes.length == 1 && genericTypes[0] == null ? null : genericTypes;
      final int primitiveIndex = kind != Kind.CLASS || !packageName.isEmpty() || superType != null || this.genericTypes != null ? -1 : Arrays.binarySearch(primitiveTypeNames, compositeName);
      this.isPrimitive = primitiveIndex > -1;
      this.wrapper = isPrimitive ? getType(wrapperTypes[primitiveIndex]) : null;
      qualifiedNameToType.put(toCanonicalString(), this);
    }

    Class<?> getJavaClass() {
      return cls;
    }

    private Type(final Class<?> cls, final GenericType[] genericTypes) {
      this(cls.isAnnotation() ? Kind.ANNOTATION : Kind.CLASS, getPackageName(cls), cls.getSimpleName(), cls.getSuperclass() == null ? null : cls.getSuperclass() == Object.class ? null : new Type(cls.getSuperclass(), (GenericType[])null), genericTypes);
    }

    boolean hasJxSuperType() {
      return superType != null && (superType.cls == null || JxObject.class.isAssignableFrom(superType.cls));
    }

    Type getDeclaringType() {
      final String declaringClassName = Classes.getDeclaringClassName(compositeName);
      return compositeName.length() == declaringClassName.length() ? null : getType(Kind.CLASS, packageName, declaringClassName, (GenericType[])null);
    }

    Type getGreatestCommonSuperType(final Type type) {
      Type a = this;
      do {
        Type b = type;
        do {
          if (a.name.equals(b.name))
            return a.withCommonGeneric(b);

          b = b.superType;
        }
        while (b != null);
        a = a.superType;
      }
      while (a != null);
      return OBJECT;
    }

    Type filterJxObjectType(final boolean isObjectModel) {
      return Object.class != cls ? this : isObjectModel ? JX_OBJECT : OBJECT;
    }

    private Type withCommonGeneric(final Type b) {
      if (genericTypes == null)
        return this;

      if (b.genericTypes == null)
        return b;

      final int len = genericTypes.length;
      if (len != b.genericTypes.length)
        throw new IllegalArgumentException("Incompatible types: " + this + " " + b);

      final Type[] result = new Type[len];
      for (int i = 0; i < len; ++i)  // [A]
        result[i] = genericTypes[i].getGreatestCommonSuperType(b.genericTypes[i]).asGeneric(Wildcard.EXTENDS);

      return new Type(this, result);
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

    String toString(final boolean canonical) {
      final StringBuilder b = new StringBuilder(canonical ? canonicalName : name);
      if (genericTypes != null) {
        b.append('<');
        for (final Type genericType : genericTypes) // [A]
          b.append(canonical ? genericType.toString(canonical) : genericType.toString()).append(',');

        b.setCharAt(b.length() - 1, '>');
      }

      if (isArray)
        b.append("[]");

      return b.toString();
    }

    String getNativeName() {
      if (!isArray)
        return isPrimitive ? wrapper.name : name;

      if (!isPrimitive)
        return "[L" + name + ";";

      return "[" + name.substring(0, 1).toUpperCase();
    }

    public boolean isAssignableFrom(Type type) {
      if (isArray != type.isArray)
        return false;

      if (isPrimitive && type.isPrimitive)
        return this == type;

      Type target = isPrimitive ? wrapper : this;
      if (type.isPrimitive)
        type = type.wrapper;

      for (final String str = target.toString(); type != null; type = type.superType) // [X]
        if (str.equals(type.toString()))
          return true;

      return false;
    }

    private IdentityHashMap<Wildcard,GenericType> wildcardToGeneric;

    public GenericType asGeneric() {
      return asGeneric(null);
    }

    public GenericType asGeneric(final Wildcard wildcard) {
      if (wildcardToGeneric == null)
        wildcardToGeneric = new IdentityHashMap<>(2);

      GenericType generic = wildcardToGeneric.get(wildcard);
      if (generic == null)
        wildcardToGeneric.put(wildcard, generic = new GenericType(this, wildcard));

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
    final int len;
    return superName != null && (len = superName.length()) > 0 && name.startsWith(superName) ? name.substring(len + 1) : name;
  }

  Type getType(final Kind kind, final String className) {
    final int lt;
    final int gt = className.lastIndexOf('>');
    final GenericType genericType;
    if (gt > -1) {
      final int i = lt = className.indexOf('<');
      // FIXME: This does not handle multiple generic type parameters (i.e. with `,`).
      final Wildcard wildcard;
      final String genericClassName;
      if (className.charAt(lt + 1) != '?') {
        wildcard = null;
        genericClassName = className.substring(i + 1, gt);
      }
      else if (className.length() > lt + 3) {
        wildcard = className.charAt(lt + 3) == 'e' ? Wildcard.EXTENDS : Wildcard.SUPER;
        genericClassName = className.substring(className.indexOf(' ', i + 7) + 1, gt);
      }
      else {
        wildcard = Wildcard.EXTENDS;
        genericClassName = Object.class.getName();
      }

      genericType = getType(Kind.CLASS, "", genericClassName).asGeneric(wildcard);
    }
    else {
      lt = className.length();
      genericType = null;
    }

    final int dot = className.lastIndexOf('.', lt);
    return dot == -1 ? getType(kind, "", className, genericType) : getType(kind, className.substring(0, dot), className.substring(dot + 1, lt), genericType);
  }

  Type getType(final Kind kind, final String packageName, final String compositeName, final GenericType ... genericTypes) {
    return getType(kind, packageName, compositeName, null, null, genericTypes);
  }

  Type getType(final String packageName, final String compositeName, final String superCompositeName, final GenericType ... genericTypes) {
    return getType(Kind.CLASS, packageName, compositeName, packageName, superCompositeName, genericTypes);
  }

  Type getType(final Kind kind, final String packageName, final String compositeName, final String superPackageName, final String supercompositeName) {
    return getType(kind, packageName, compositeName, superPackageName, supercompositeName, (GenericType[])null);
  }

  Type getType(final Kind kind, final String packageName, final String compositeName, final String superPackageName, final String superCompositeName, final GenericType ... genericTypes) {
    final StringBuilder className = new StringBuilder();
    if (packageName.length() > 0)
      className.append(packageName).append('.');

    className.append(compositeName);
    final Type type = qualifiedNameToType.get(className.toString());
    return type != null ? type : new Type(kind, packageName, compositeName, superCompositeName == null ? null : getType(Kind.CLASS, superPackageName, superCompositeName, null, null, (GenericType[])null), genericTypes);
  }

  Type getOptionalType(final GenericType genericType) {
    return getType(Optional.class, genericType);
  }

  Type getType(final Class<?> cls, final GenericType ... genericTypes) {
    final StringBuilder b = new StringBuilder(cls.getName());
    if (genericTypes != null) {
      b.append('<');
      for (int i = 0, i$ = genericTypes.length; i < i$; ++i) { // [A]
        if (i > 0)
          b.append(',');

        b.append(genericTypes[i].toString(true));
      }

      b.append('>');
    }

    final Type type = qualifiedNameToType.get(b.toString());
    return type != null ? type : new Type(cls, genericTypes);
  }

  private static String getPackageName(final Class<?> cls) {
    return cls.isPrimitive() ? "" : cls.getPackage().getName();
  }

  Type getType(final Class<?> cls) {
    return getType(cls.isAnnotation() ? Kind.ANNOTATION : Kind.CLASS, getPackageName(cls), Classes.getCompositeName(cls), cls.getSuperclass() == null ? null : cls.getSuperclass().getPackage().getName(), cls.getSuperclass() == null ? null : Classes.getCompositeName(cls.getSuperclass()));
  }

  private final HashMap<String,Registry> namespaceToRegistry;
  private final HashMap<String,String> prefixToNamespace;
  private final LinkedHashMap<String,Model> refToModel = new LinkedHashMap<>(); // FIXME: Does this need to be a LinkedHashMap?
  private final LinkedHashMap<String,ReferrerManifest> refToReferrers = new LinkedHashMap<>(); // FIXME: Does this need to be a LinkedHashMap?

  final String targetNamespace;
  final Settings settings;
  final boolean isFromJsd;
  final String packageName;
  final String classBasePath;

  Registry(final HashMap<String,Registry> namespaceToRegistry, final HashMap<String,String> prefixToNamespace, final String targetNamespace, final Settings settings) {
    this.namespaceToRegistry = namespaceToRegistry;
    this.prefixToNamespace = prefixToNamespace;
    this.targetNamespace = assertNotNull(targetNamespace);
    if (namespaceToRegistry.put(targetNamespace, this) != null)
      throw new IllegalStateException("TargetNamespace specified multiple times: " + targetNamespace);

    this.settings = settings;
    this.isFromJsd = true;

    final String basePath = settings.getPackage(targetNamespace);
    final int length = basePath.length();
    if (length > 0) {
      final char lastChar = basePath.charAt(length - 1);
      if (lastChar == '.') {
        this.packageName = basePath.substring(0, length - 1);
        this.classBasePath = "";
      }
      else {
        final int index = basePath.lastIndexOf('.');
        this.packageName = basePath.substring(0, index);
        this.classBasePath = basePath.substring(index + 1);
      }
    }
    else {
      this.packageName = "";
      this.classBasePath = "";
    }
  }

  private static String detectTargetNamespace(final Collection<Class<?>> classes) {
    if (classes.size() == 0)
      return null;

    String targetNamespace = null;
    for (final Class<?> cls : classes) { // [C]
      final JxBinding annotation = cls.getAnnotation(JxBinding.class);
      if (annotation != null) {
        final String value = annotation.targetNamespace();
        if (targetNamespace == null)
          targetNamespace = value;
        else if (!targetNamespace.equals(value))
          throw new ValidationException("Conflicting targetNamespace declarations: " + targetNamespace + " and " + value);
      }
    }

    return targetNamespace;
  }

  @SuppressWarnings("unchecked")
  Registry(final HashMap<String,Registry> namespaceToRegistry, final Declarer declarer, final Collection<Class<?>> classes) {
    this.namespaceToRegistry = namespaceToRegistry;
    this.prefixToNamespace = null;
    this.targetNamespace = detectTargetNamespace(classes);
    if (namespaceToRegistry.put(targetNamespace, this) != null)
      throw new IllegalStateException("TargetNamespace specified multiple times: " + targetNamespace);

    this.settings = Settings.DEFAULT;
    this.isFromJsd = false;
    if (classes.size() > 0) {
      for (final Class<?> cls : classes) { // [C]
        if (cls.isAnnotation())
          ArrayModel.referenceOrDeclare(this, declarer, (Class<? extends Annotation>)cls);
        else
          ObjectModel.referenceOrDeclare(this, declarer, cls);
      }
    }

    this.packageName = getPackageName();
    this.classBasePath = "";
  }

  private String getPackageName() {
    final Collection<Model> models = getModels();
    if (models == null || models.size() == 0)
      return null;

    final HashSet<Registry.Type> types = new HashSet<>();
    for (final Model model : models) // [C]
      model.getDeclaredTypes(types);

    final int size = types.size();
    final String[] packages = new String[size];
    final Iterator<Registry.Type> iterator = types.iterator();
    for (int i = 0; i < size; ++i) // [S]
      packages[i] = iterator.next().packageName;

    return Strings.getCommonPrefix(packages);
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
  private final Type JX_OBJECT = getType(JxObject.class);

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

  <T extends Member> T reference(final T model, final Referrer<?> referrer) {
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
    for (int i = 0, i$ = deferredReferences.size(); i < i$; ++i) // [RA]
      deferredReferences.get(i).run();

    deferredReferences.clear();
  }

  boolean isPending(final Id id) {
    final String prefix = id.getPrefix();
    final String localName = id.toString();
    if (prefix == null)
      return refToModel.get(localName) == null && refToModel.containsKey(localName);

    final String namespace = prefixToNamespace.get(prefix);
    if (namespace == null)
      throw new IllegalStateException("Namespace is null for prefix: " + prefix);

    final Registry registry = namespaceToRegistry.get(namespace);
    return registry.refToModel.get(localName) == null && registry.refToModel.containsKey(localName);
  }

  Model getModel(final Id id) {
    final String prefix = id.getPrefix();
    if (prefix == null)
      return refToModel.get(id.toString());

    final String namespace = prefixToNamespace.get(prefix);
    if (namespace == null)
      throw new IllegalStateException("Namespace is null for prefix: " + prefix);

    final Registry registry = namespaceToRegistry.get(namespace);
    if (registry == null)
      throw new IllegalStateException("Unable to find Registry for namespace \"" + namespace + "\"");

    return registry.refToModel.get(id.getLocalName());
  }

  Collection<Model> getModels() {
    return refToModel.values();
  }

  boolean isRootMember(final Member member) {
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