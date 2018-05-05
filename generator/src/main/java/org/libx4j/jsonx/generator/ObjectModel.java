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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.lib4j.util.Iterators;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Array;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Boolean;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Member;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Number;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Object;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$ObjectMember;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Reference;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$String;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.Jsonx;
import org.libx4j.jsonx.runtime.JsonxObject;
import org.libx4j.jsonx.runtime.ObjectElement;
import org.libx4j.jsonx.runtime.ObjectProperty;
import org.libx4j.jsonx.runtime.Unknown;

class ObjectModel extends ComplexModel {
  public static ObjectModel declare(final Schema schema, final Registry registry, final Jsonx.Object binding) {
    return registry.declare(binding).value(new ObjectModel(schema, registry, binding), null);
  }

  public static ObjectModel declare(final Registry registry, final ObjectModel referrer, final $Object binding, final String superClassName) {
    if ("objectExtendsAbstract".equals(binding.getName$().text())) {
      int i = 0;
    }
    return registry.declare(binding).value(new ObjectModel(referrer, registry, binding, getParent(superClassName, registry)), referrer);
  }

  public static Element referenceOrDeclare(final Registry registry, final ComplexModel referrer, final ObjectProperty objectProperty, final Field field) {
    final ObjectModel objectModel = (ObjectModel)registry.getElement(objectProperty.type().getName());
    // FIXME: Can we get doc comments from code?
    return new Reference(referrer, getName(objectProperty.name(), field), objectProperty.required(), objectModel == null ? registry.declare(objectProperty.type()).value(new ObjectModel(referrer, registry, objectProperty, field), referrer) : registry.reference(objectModel, referrer), null);
  }

  public static Element referenceOrDeclare(final Registry registry, final Member referrer, final ObjectElement objectElement) {
    final ObjectModel objectModel = (ObjectModel)registry.getElement(objectElement.type().getName());
    // FIXME: Can we get doc comments from code?
    return new Reference(referrer, objectElement.minOccurs(), objectElement.maxOccurs(), objectModel == null ? registry.declare(objectElement.type()).value(new ObjectModel(referrer, registry, objectElement), referrer instanceof ComplexModel ? (ComplexModel)referrer : null) : registry.reference(objectModel, referrer instanceof ComplexModel ? (ComplexModel)referrer : null), null);
  }

  public static ObjectModel referenceOrDeclare(final Registry registry, final Member referrer, final Class<?> clazz) {
    return referenceOrDeclare(registry, referrer, clazz, checkJSObject(clazz));
  }

  private static ObjectModel referenceOrDeclare(final Registry registry, final Member referrer, final Class<?> clazz, final JsonxObject jsObject) {
    final ObjectModel objectModel = (ObjectModel)registry.getElement(clazz.getName());
    return objectModel != null ? registry.reference(objectModel, referrer instanceof ComplexModel ? (ComplexModel)referrer : null) : registry.declare(clazz).value(new ObjectModel(referrer, registry, null, clazz, jsObject), referrer instanceof ComplexModel ? (ComplexModel)referrer : null);
  }

  public static ObjectModel reference(final Registry registry, final ComplexModel referrer, final $Array.Object binding) {
    return registry.reference(new ObjectModel(referrer, registry, binding), referrer);
  }

  public static String getFullyQualifiedName(final $Object binding) {
    final StringBuilder builder = new StringBuilder();
    $Object owner = binding;
    do
      builder.insert(0, '.').insert(1, owner.getClass$().text());
    while (owner.owner() instanceof $Object && (owner = ($Object)owner.owner()) != null);
    return builder.insert(0, ((Jsonx.Object)owner.owner()).getClass$().text()).toString();
  }

  private static ObjectModel getParent(final String superClassName, final Registry registry) {
    if (superClassName == null)
      return null;

    final ObjectModel parent;
    try {
      parent = (ObjectModel)registry.getElement(superClassName);
    }
    catch (final ClassCastException e) {
      throw new IllegalStateException("Top-level object extends=\"" + superClassName + "\" in array points to a non-object");
    }

    if (parent == null)
      throw new IllegalStateException("Top-level object extends=\"" + superClassName + "\" in array not found");

    return parent;
  }

  private static JsonxObject checkJSObject(final Class<?> clazz) {
    final JsonxObject jsObject = clazz.getDeclaredAnnotation(JsonxObject.class);
    if (jsObject == null)
      throw new IllegalArgumentException("Class " + clazz.getName() + " goes not specify the @" + JsonxObject.class.getSimpleName() + " annotation.");

    return jsObject;
  }

  private static void recurseInnerClasses(final Schema schema, final Registry registry, final Class<?> clazz) {
    for (final Class<?> innerClass : clazz.getClasses()) {
      final JsonxObject innerJSObject = innerClass.getDeclaredAnnotation(JsonxObject.class);
      if (innerJSObject == null)
        recurseInnerClasses(schema, registry, innerClass);
      else
        referenceOrDeclare(registry, schema, innerClass, innerJSObject);
    }
  }

  private static Map<String,Element> parseMembers(final Registry registry, final $ObjectMember binding, final ObjectModel model) {
    final LinkedHashMap<String,Element> members = new LinkedHashMap<String,Element>();
    final Iterator<? super $Member> iterator = Iterators.filter(binding.elementIterator(), m -> $Member.class.isInstance(m));
    while (iterator.hasNext()) {
      final $Member member = ($Member)iterator.next();
      if (member instanceof $Boolean) {
        final $Boolean bool = ($Boolean)member;
        members.put(bool.getName$().text(), BooleanModel.reference(model, bool));
      }
      else if (member instanceof $Number) {
        final $Number number = ($Number)member;
        members.put(number.getName$().text(), NumberModel.reference(model, number));
      }
      else if (member instanceof $String) {
        final $String string = ($String)member;
        members.put(string.getName$().text(), StringModel.reference(model, string));
      }
      else if (member instanceof $Array) {
        final $Array array = ($Array)member;
        final ArrayModel child = ArrayModel.reference(model, registry, array);
        members.put(array.getName$().text(), child);
      }
      else if (member instanceof $Reference) {
        final $Reference reference = ($Reference)member;
        final Element ref = registry.getElement(reference.getTemplate$().text());
        if (ref == null)
          throw new IllegalStateException("Referece \"" + reference.getName$().text() + "\" -> template=\"" + reference.getTemplate$().text() + "\" not found");

        members.put(reference.getName$().text(), ref instanceof Model ? new Reference(model, reference, (Model)ref) : ref);
      }
      else if (member instanceof $Object) {
        final $Object object = ($Object)member;
        final ObjectModel child = ObjectModel.declare(registry, model, object, object.getExtends$() == null ? null : object.getExtends$().text());
        members.put(object.getName$().text(), child);
      }
      else {
        throw new UnsupportedOperationException("Unsupported " + member.getClass().getSimpleName() + " member type: " + member.getClass().getName());
      }
    }

    return Collections.unmodifiableMap(members);
  }

  private final Map<String,Element> members;
  private final String compoundClassName;
  private final ObjectModel superObject;
  private final Boolean isAbstract;
  private final Unknown unknown;

  private ObjectModel(final Schema schema, final Registry registry, final Jsonx.Object binding) {
    super(schema, null, binding.getNullable$().text(), null, null, null, binding.getDoc$() == null ? null : binding.getDoc$().text());
    this.compoundClassName = binding.getClass$().text();
    this.isAbstract = binding.getAbstract$().text();
    this.unknown = Unknown.valueOf(binding.getUnknown$().text().toUpperCase());
    this.superObject = binding.getExtends$() == null ? null : getParent(binding.getExtends$().text(), registry);
    this.members = Collections.unmodifiableMap(parseMembers(registry, binding, this));
  }

  private ObjectModel(final Member owner, final Registry registry, final ObjectProperty objectProperty, final Field field) {
    this(owner, registry, getName(objectProperty.name(), field), objectProperty.type(), checkJSObject(objectProperty.type()));
  }

  private ObjectModel(final Member owner, final Registry registry, final ObjectElement objectElement) {
    this(owner, registry, null, objectElement.type(), checkJSObject(objectElement.type()));
  }

  private ObjectModel(final Member owner, final Registry registry, final $Object binding, final ObjectModel superObject) {
    super(owner, binding, binding.getName$().text(), binding.getNullable$().text(), binding.getRequired$().text());
    this.compoundClassName = getFullyQualifiedName(binding);
    this.superObject = superObject;
    this.isAbstract = null;
    this.unknown = Unknown.valueOf(binding.getUnknown$().text().toUpperCase());
    this.members = Collections.unmodifiableMap(parseMembers(registry, binding, this));
  }

  private ObjectModel(final Member owner, final Registry registry, final $Array.Object binding) {
    super(owner, binding, null, binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.compoundClassName = binding.getClass$().text();
    this.superObject = null;
    this.isAbstract = null;
    this.unknown = Unknown.valueOf(binding.getUnknown$().text().toUpperCase());
    this.members = Collections.unmodifiableMap(parseMembers(registry, binding, this));
  }

  private ObjectModel(final Member owner, final Registry registry, final String name, final Class<?> clazz, final JsonxObject jsObject) {
    // FIXME: Can we get doc comments from code?
    super(owner, name, null, null, null, null, null);
    this.compoundClassName = clazz.getName();
    this.isAbstract = Modifier.isAbstract(clazz.getModifiers());
    this.unknown = jsObject.unknown();
    final LinkedHashMap<String,Element> members = new LinkedHashMap<String,Element>();
    final Class<?> superClass = clazz.getSuperclass();
    if (superClass != null) {
      final JsonxObject superObject = superClass.getDeclaredAnnotation(JsonxObject.class);
      this.superObject = superObject == null ? null : referenceOrDeclare(registry, this, superClass);
    }
    else {
      this.superObject = null;
    }

    Object object;
    try {
      object = clazz.getDeclaredConstructor().newInstance();
    }
    catch (final IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
      object = null;
    }

    for (final Field field : clazz.getDeclaredFields()) {
      final Element member = Element.toElement(registry, this, field, object);
      if (member != null)
        members.put(member.name(), member);
    }

    this.members = Collections.unmodifiableMap(members);
    recurseInnerClasses(getSchema(), registry, clazz);
  }

  private ObjectModel(final Element element, final ObjectModel model) {
    super(element);
    this.compoundClassName = model.compoundClassName;
    this.superObject = model.superObject;
    this.isAbstract = model.isAbstract;
    this.unknown = model.unknown;
    this.members = Collections.unmodifiableMap(model.members);
  }

  private ObjectModel(final ObjectModel copy, final ObjectModel superObject, final Map<String,Element> members) {
    super(copy);
    this.compoundClassName = copy.compoundClassName;
    this.superObject = superObject;
    this.isAbstract = copy.isAbstract;
    this.unknown = copy.unknown;
    this.members = Collections.unmodifiableMap(members);
  }

  public final Map<String,Element> members() {
    return this.members;
  }

  @Override
  public final Type type() {
    return Type.get(getSchema().packageName(), compoundClassName);
  }

  public final String classSimpleName() {
    final int from = compoundClassName.lastIndexOf('.');
    final int to = compoundClassName.lastIndexOf('$');
    return to > 0 ? compoundClassName.substring(to + 1) : from > 0 ? compoundClassName.substring(from + 1) : compoundClassName;
  }

  public final ObjectModel superObject() {
    return this.superObject;
  }

  public final boolean isAbstract() {
    return this.isAbstract != null && this.isAbstract;
  }

  public final Unknown unknown() {
    return this.unknown;
  }

  @Override
  protected final Class<? extends Annotation> propertyAnnotation() {
    return ObjectProperty.class;
  }

  @Override
  protected final Class<? extends Annotation> elementAnnotation() {
    return ObjectElement.class;
  }

  @Override
  protected ObjectModel merge(final Reference reference) {
    return new ObjectModel(reference, this);
  }

  @Override
  protected final Element normalize(final Registry registry) {
    final Element element = registry.getElement(reference());
    if (element instanceof Reference)
      return element.normalize(registry);

    final Map<String,Element> members = normalize(registry, this.members);
    final ObjectModel superObject;
    if (superObject() == null) {
      superObject = null;
    }
    else {
      final Element superElement = superObject().normalize(registry);
      superObject = (ObjectModel)(superElement instanceof Reference ? ((Reference)superElement).template() : superElement);
    }

    final ObjectModel clone = new ObjectModel(this, superObject, members);
    final ObjectModel objectModel = (ObjectModel)element;
    if (registry.getNumReferrers(this) != 1 || objectModel.isAbstract())
      return clone;

    return objectModel == this ? clone : new ObjectModel(objectModel, clone);
  }

  @Override
  protected final void collectClassNames(final List<Type> types) {
    types.add(type());
    if (superObject != null)
      superObject.collectClassNames(types);

    if (members != null)
      for (final Element member : members.values())
        member.collectClassNames(types);
  }

  @Override
  protected String reference() {
    return type().toString();
  }

  @Override
  protected final String toJSON(final String packageName) {
    final StringBuilder builder = new StringBuilder(super.toJSON(packageName));
    if (builder.length() > 0)
      builder.insert(0, ",\n");

    builder.append(",\n  class: \"").append(getShortName(compoundClassName, packageName)).append('"');

    if (superObject != null)
      builder.append(",\n  extends: \"").append(getShortName(superObject.type().toString(), packageName)).append('"');

    if (isAbstract != null)
      builder.append(",\n  abstract: ").append(isAbstract);

    if (unknown != null)
      builder.append(",\n  unknown: \"").append(unknown.toString().toLowerCase()).append('"');

    if (members != null && members.size() > 0) {
      builder.append(",\n  members: ");
      final StringBuilder members = new StringBuilder();
      for (final Map.Entry<String,Element> entry : this.members.entrySet())
        members.append(",\n    \"").append(entry.getKey()).append("\": ").append(entry.getValue().toJSON(packageName).replace("\n", "\n    "));

      builder.append('{');
      if (members.length() > 0)
        builder.append("\n").append(members.substring(2)).append("\n  ");

      builder.append('}');
    }

    return "{\n" + (builder.length() > 0 ? builder.substring(2) : builder.toString()) + "\n}";
  }

  @Override
  protected final String toJSONX(final String pacakgeName) {
    final StringBuilder builder = new StringBuilder(kind() == Kind.PROPERTY ? "<property xsi:type=\"object\"" : "<object");
    if ("objectExtendsAbstract".equals(name())) {
      int i = 0;
    }
    builder.append(" class=\"").append(getShortName(owner() instanceof ObjectModel ? compoundClassName.substring(((ObjectModel)owner()).compoundClassName.length() + 1) : compoundClassName, pacakgeName)).append('"');

    if (superObject != null)
      builder.append(" extends=\"").append(getShortName(superObject.type().toString(), pacakgeName)).append('"');

    if (isAbstract != null && isAbstract)
      builder.append(" abstract=\"").append(isAbstract).append('"');

    if (unknown != Unknown.ERROR)
      builder.append(" unknown=\"").append(unknown.toString().toLowerCase()).append('"');

    if (members != null && members.size() > 0) {
      final StringBuilder members = new StringBuilder();
      for (final Element member : this.members.values())
        members.append("\n  ").append(member.toJSONX(pacakgeName).replace("\n", "\n  "));

      builder.append(super.toJSONX(pacakgeName)).append('>').append(members).append('\n').append(kind() == Kind.PROPERTY ? "</property>" : "</object>");
    }
    else {
      builder.append(super.toJSONX(pacakgeName)).append("/>");
    }

    return builder.toString();
  }

  protected final String toObjectAnnotation() {
    return unknown() == Unknown.ERROR ? "" : "unknown=" + Unknown.class.getName() + "." + unknown();
  }

  @Override
  protected final String toAnnotation(final boolean full) {
    final StringBuilder builder = full ? new StringBuilder(super.toAnnotation(full)) : new StringBuilder();
    if (builder.length() > 0)
      builder.append(", ");

    builder.append("type=").append(compoundClassName.replace('$', '.')).append(".class");
    return builder.toString();
  }

  protected final String toJava() {
    final StringBuilder builder = new StringBuilder();
    if (members != null && members.size() > 0)
      for (final Element member : members.values())
        builder.append("\n\n").append(member.toField());

    return builder.length() > 1 ? builder.substring(2).toString() : builder.toString();
  }
}