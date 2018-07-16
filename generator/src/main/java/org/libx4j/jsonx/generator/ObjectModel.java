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
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lib4j.util.Iterators;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Array;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Boolean;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Member;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Number;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Object;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$ObjectMember;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$String;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Template;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.Jsonx;
import org.libx4j.jsonx.runtime.JsonxObject;
import org.libx4j.jsonx.runtime.ObjectElement;
import org.libx4j.jsonx.runtime.ObjectProperty;
import org.libx4j.jsonx.runtime.Unknown;
import org.w3.www._2001.XMLSchema.yAA.$AnySimpleType;

class ObjectModel extends ComplexModel {
  public static ObjectModel declare(final Registry registry, final Jsonx.Object binding) {
    return registry.declare(binding).value(new ObjectModel(registry, binding), null);
  }

  public static ObjectModel declare(final Registry registry, final ObjectModel referrer, final $Object binding, final String superClassName) {
    return registry.declare(binding).value(new ObjectModel(registry, binding, getParent(superClassName, registry)), referrer);
  }

  public static Member referenceOrDeclare(final Registry registry, final ComplexModel referrer, final ObjectProperty property, final Field field) {
    final Id id = new Id(property);
    final ObjectModel model = (ObjectModel)registry.getElement(id);
    return new Template(registry, getName(property.name(), field), property.nullable(), property.required(), model == null ? registry.declare(id).value(new ObjectModel(registry, property), referrer) : registry.reference(model, referrer));
  }

  public static Member referenceOrDeclare(final Registry registry, final Element referrer, final ObjectElement element) {
    final Id id = new Id(element);
    final ObjectModel model = (ObjectModel)registry.getElement(id);
    return new Template(registry, element.nullable(), element.minOccurs(), element.maxOccurs(), model == null ? registry.declare(id).value(new ObjectModel(registry, element), referrer instanceof ComplexModel ? (ComplexModel)referrer : null) : registry.reference(model, referrer instanceof ComplexModel ? (ComplexModel)referrer : null));
  }

  public static ObjectModel referenceOrDeclare(final Registry registry, final Class<?> clazz) {
    return referenceOrDeclare(registry, clazz, checkJSObject(clazz));
  }

  private static ObjectModel referenceOrDeclare(final Registry registry, final Class<?> clazz, final JsonxObject jsObject) {
    final Id id = new Id(clazz);
    final ObjectModel model = (ObjectModel)registry.getElement(id);
    return model != null ? registry.reference(model, null) : registry.declare(id).value(new ObjectModel(registry, clazz, jsObject, null), null);
  }

  public static ObjectModel reference(final Registry registry, final ComplexModel referrer, final $Array.Object binding) {
    return registry.reference(new ObjectModel(registry, binding), referrer);
  }

  public static String getFullyQualifiedName(final $Object binding) {
    final StringBuilder builder = new StringBuilder();
    $Object owner = binding;
    do
      builder.insert(0, '$').insert(1, owner.getClass$().text());
    while (owner.owner() instanceof $Object && (owner = ($Object)owner.owner()) != null);
    return builder.insert(0, ((Jsonx.Object)owner.owner()).getClass$().text()).toString();
  }

  private static ObjectModel getParent(final String superClassName, final Registry registry) {
    if (superClassName == null)
      return null;

    final ObjectModel parent;
    try {
      parent = (ObjectModel)registry.getElement(new Id(superClassName));
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
      throw new IllegalArgumentException("Class " + clazz.getName() + " does not specify the @" + JsonxObject.class.getSimpleName() + " annotation.");

    return jsObject;
  }

  private static void recurseInnerClasses(final Registry registry, final Class<?> clazz) {
    for (final Class<?> innerClass : clazz.getClasses()) {
      final JsonxObject innerJSObject = innerClass.getDeclaredAnnotation(JsonxObject.class);
      if (innerJSObject == null)
        recurseInnerClasses(registry, innerClass);
      else
        referenceOrDeclare(registry, innerClass, innerJSObject);
    }
  }

  private static Map<String,Member> parseMembers(final Registry registry, final $ObjectMember binding, final ObjectModel model) {
    final LinkedHashMap<String,Member> members = new LinkedHashMap<>();
    final Iterator<? super $Member> iterator = Iterators.filter(binding.elementIterator(), m -> $Member.class.isInstance(m));
    while (iterator.hasNext()) {
      final $Member member = ($Member)iterator.next();
      if (member instanceof $Boolean) {
        final $Boolean bool = ($Boolean)member;
        members.put(bool.getName$().text(), BooleanModel.reference(registry, model, bool));
      }
      else if (member instanceof $Number) {
        final $Number number = ($Number)member;
        members.put(number.getName$().text(), NumberModel.reference(registry, model, number));
      }
      else if (member instanceof $String) {
        final $String string = ($String)member;
        members.put(string.getName$().text(), StringModel.reference(registry, model, string));
      }
      else if (member instanceof $Array) {
        final $Array array = ($Array)member;
        final ArrayModel child = ArrayModel.reference(registry, model, array);
        members.put(array.getName$().text(), child);
      }
      else if (member instanceof $Template) {
        final $Template template = ($Template)member;
        final Member reference = registry.getElement(new Id(template.getReference$()));
        if (reference == null)
          throw new IllegalStateException("Template \"" + template.getName$().text() + "\" -> reference=\"" + template.getReference$().text() + "\" not found");

        members.put(template.getName$().text(), reference instanceof Model ? new Template(registry, template, registry.reference((Model)reference, model)) : reference);
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

  private final Id id;
  private final Map<String,Member> members;
  private final Registry.Type type;
  private final ObjectModel superObject;
  private final Boolean isAbstract;
  private final Unknown unknown;

  private ObjectModel(final Registry registry, final Jsonx.Object binding) {
    super(registry, null);
    this.type = registry.getType((String)binding.owner().getPackage$().text(), binding.getClass$().text(), binding.getExtends$() == null ? null : binding.getExtends$().text());
    this.isAbstract = binding.getAbstract$().text();
    this.unknown = Unknown.valueOf(binding.getUnknown$().text().toUpperCase());
    this.superObject = binding.getExtends$() == null ? null : getParent(binding.getExtends$().text(), registry);
    this.members = Collections.unmodifiableMap(parseMembers(registry, binding, this));
    this.id = new Id(this);
  }

  private ObjectModel(final Registry registry, final ObjectProperty property) {
    this(registry, property.type(), checkJSObject(property.type()), property.nullable());
  }

  private ObjectModel(final Registry registry, final ObjectElement element) {
    this(registry, element.type(), checkJSObject(element.type()), element.nullable());
  }

  private static Jsonx getJsonx($AnySimpleType member) {
    do
      if (member instanceof Jsonx)
        return (Jsonx)member;
    while ((member = member.owner()) != null);
    return null;
  }

  private ObjectModel(final Registry registry, final $Object binding, final ObjectModel superObject) {
    super(registry, binding.getName$(), binding.getNullable$(), binding.getRequired$());
    this.type = registry.getType((String)getJsonx(binding).getPackage$().text(), getFullyQualifiedName(binding), binding.getExtends$() == null ? null : binding.getExtends$().text());
    this.superObject = superObject;
    this.isAbstract = null;
    this.unknown = Unknown.valueOf(binding.getUnknown$().text().toUpperCase());
    this.members = Collections.unmodifiableMap(parseMembers(registry, binding, this));
    this.id = new Id(this);
  }

  private ObjectModel(final Registry registry, final $Array.Object binding) {
    super(registry, binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.type = registry.getType((String)getJsonx(binding).getPackage$().text(), binding.getClass$().text(), binding.getExtends$() == null ? null : binding.getExtends$().text());
    this.superObject = null;
    this.isAbstract = null;
    this.unknown = Unknown.valueOf(binding.getUnknown$().text().toUpperCase());
    this.members = Collections.unmodifiableMap(parseMembers(registry, binding, this));
    this.id = new Id(this);
  }

  private ObjectModel(final Registry registry, final Class<?> clazz, final JsonxObject jsObject, final Boolean nullable) {
    super(registry, nullable);
    final Class<?> superClass = clazz.getSuperclass();
    this.type = registry.getType(clazz);
    this.isAbstract = Modifier.isAbstract(clazz.getModifiers());
    this.unknown = jsObject.unknown();
    if (superClass != null) {
      final JsonxObject superObject = superClass.getDeclaredAnnotation(JsonxObject.class);
      this.superObject = superObject == null ? null : referenceOrDeclare(registry, superClass);
    }
    else {
      this.superObject = null;
    }

    final LinkedHashMap<String,Member> members = new LinkedHashMap<>();
    for (final Field field : clazz.getDeclaredFields()) {
      final Member member = Member.toMember(registry, this, field);
      if (member != null)
        members.put(member.name(), member);
    }

    this.members = Collections.unmodifiableMap(members);
    recurseInnerClasses(registry, clazz);
    this.id = new Id(this);
  }

  @Override
  public final Id id() {
    return id;
  }

  public final Map<String,Member> members() {
    return this.members;
  }

  @Override
  public final Registry.Type type() {
    return type;
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
  protected final void getDeclaredTypes(final Set<Registry.Type> types) {
    types.add(type());
    if (superObject != null)
      superObject.getDeclaredTypes(types);

    if (members != null)
      for (final Member member : members.values())
        member.getDeclaredTypes(types);
  }

  @Override
  protected final Map<String,String> toAnnotationAttributes(final Element owner, final String packageName) {
    final Map<String,String> attributes = super.toAnnotationAttributes(owner, packageName);
    attributes.put("class", owner instanceof ObjectModel ? type.getSubName(((ObjectModel)owner).type().getName()) : type.getSubName(packageName));

    if (superObject != null)
      attributes.put("extends", superObject.type().getRelativeName(packageName));

    if (isAbstract != null && isAbstract)
      attributes.put("abstract", String.valueOf(isAbstract));

    if (unknown != Unknown.ERROR)
      attributes.put("unknown", unknown.toString().toLowerCase());

    return attributes;
  }

  @Override
  protected final org.lib4j.xml.Element toXml(final Element owner, final String packageName) {
    final List<org.lib4j.xml.Element> elements;
    if (members != null && members.size() > 0) {
      elements = new ArrayList<>();
      for (final Member member : this.members.values())
        elements.add(member.toXml(this, packageName));
    }
    else {
      elements = null;
    }

    final Map<String,String> attributes;
    if (!(owner instanceof ObjectModel)) {
      attributes = toAnnotationAttributes(owner, packageName);
      return new org.lib4j.xml.Element("object", attributes, elements);
    }

    if (registry.getNumReferrers(this) > 1) {
      attributes = super.toAnnotationAttributes(owner, packageName);
      attributes.put("xsi:type", "template");
      attributes.put("reference", id().toString());
    }
    else {
      attributes = toAnnotationAttributes(owner, packageName);
      attributes.put("xsi:type", "object");
    }

    return new org.lib4j.xml.Element("property", attributes, elements);
  }

  protected final AttributeMap toObjectAnnotation() {
    final AttributeMap attributes = new AttributeMap();
    if (unknown() != Unknown.ERROR)
      attributes.put("unknown", Unknown.class.getName() + '.' + unknown());

    return attributes;
  }

  @Override
  protected final void toAnnotationAttributes(final AttributeMap attributes) {
    super.toAnnotationAttributes(attributes);
    attributes.put("type", type.getCanonicalName() + ".class");
  }

  protected final String toJava() {
    final StringBuilder builder = new StringBuilder();
    if (members != null && members.size() > 0) {
      final Iterator<Member> iterator = members.values().iterator();
      for (int i = 0; iterator.hasNext(); i++) {
        final Member member = iterator.next();
        if (i > 0)
          builder.append("\n\n");

        builder.append(member.toField());
      }
    }

    return builder.toString();
  }
}