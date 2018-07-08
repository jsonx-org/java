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
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$String;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Template;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.Jsonx;
import org.libx4j.jsonx.runtime.JsonxObject;
import org.libx4j.jsonx.runtime.ObjectElement;
import org.libx4j.jsonx.runtime.ObjectProperty;
import org.libx4j.jsonx.runtime.Unknown;

class ObjectModel extends ComplexModel {
  public static ObjectModel declare(final Registry registry, final Jsonx.Object binding) {
    return registry.declare(binding).value(new ObjectModel(registry, binding), null);
  }

  public static ObjectModel declare(final Registry registry, final ObjectModel referrer, final $Object binding, final String superClassName) {
    return registry.declare(binding).value(new ObjectModel(registry, binding, getParent(superClassName, registry)), referrer);
  }

  public static Element referenceOrDeclare(final Registry registry, final ComplexModel referrer, final ObjectProperty property, final Field field) {
    final ObjectModel model = (ObjectModel)registry.getElement(property.type().getName());
    return new Template(getName(property.name(), field), property.nullable(), property.required(), model == null ? registry.declare(property.type()).value(new ObjectModel(registry, property, field), referrer) : registry.reference(model, referrer));
  }

  public static Element referenceOrDeclare(final Registry registry, final Member referrer, final ObjectElement element) {
    final ObjectModel model = (ObjectModel)registry.getElement(element.type().getName());
    return new Template(element.nullable(), element.minOccurs(), element.maxOccurs(), model == null ? registry.declare(element.type()).value(new ObjectModel(registry, element), referrer instanceof ComplexModel ? (ComplexModel)referrer : null) : registry.reference(model, referrer instanceof ComplexModel ? (ComplexModel)referrer : null));
  }

  public static ObjectModel referenceOrDeclare(final Registry registry, final Class<?> clazz) {
    return referenceOrDeclare(registry, clazz, checkJSObject(clazz));
  }

  private static ObjectModel referenceOrDeclare(final Registry registry, final Class<?> clazz, final JsonxObject jsObject) {
    final ObjectModel model = (ObjectModel)registry.getElement(clazz.getName());
    return model != null ? registry.reference(model, null) : registry.declare(clazz).value(new ObjectModel(registry, null, clazz, jsObject, null, null, null, null), null);
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

  private static Map<String,Element> parseMembers(final Registry registry, final $ObjectMember binding, final ObjectModel model) {
    final LinkedHashMap<String,Element> members = new LinkedHashMap<String,Element>();
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
        final Element element = registry.getElement(template.getReference$().text());
        if (element == null)
          throw new IllegalStateException("Template \"" + template.getName$().text() + "\" -> reference=\"" + template.getReference$().text() + "\" not found");

        members.put(template.getName$().text(), element instanceof Model ? new Template(template, registry.reference((Model)element, model)) : element);
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
  private final Type type;
  private final ObjectModel superObject;
  private final Boolean isAbstract;
  private final Unknown unknown;

  private ObjectModel(final Registry registry, final Jsonx.Object binding) {
    super(null, binding.getNullable$().text(), null, null, null);
    this.type = Type.get(binding.getClass$().text(), binding.getExtends$() == null ? null : binding.getExtends$().text());
    this.isAbstract = binding.getAbstract$().text();
    this.unknown = Unknown.valueOf(binding.getUnknown$().text().toUpperCase());
    this.superObject = binding.getExtends$() == null ? null : getParent(binding.getExtends$().text(), registry);
    this.members = Collections.unmodifiableMap(parseMembers(registry, binding, this));
  }

  private ObjectModel(final Registry registry, final ObjectProperty objectProperty, final Field field) {
    this(registry, getName(objectProperty.name(), field), objectProperty.type(), checkJSObject(objectProperty.type()), objectProperty.nullable(), objectProperty.required(), null, null);
  }

  private ObjectModel(final Registry registry, final ObjectElement objectElement) {
    this(registry, null, objectElement.type(), checkJSObject(objectElement.type()), objectElement.nullable(), null, objectElement.minOccurs(), objectElement.maxOccurs());
  }

  private ObjectModel(final Registry registry, final $Object binding, final ObjectModel superObject) {
    super(binding.getName$().text(), binding.getNullable$().text(), binding.getRequired$().text());
    this.type = Type.get(getFullyQualifiedName(binding), binding.getExtends$() == null ? null : binding.getExtends$().text());
    this.superObject = superObject;
    this.isAbstract = null;
    this.unknown = Unknown.valueOf(binding.getUnknown$().text().toUpperCase());
    this.members = Collections.unmodifiableMap(parseMembers(registry, binding, this));
  }

  private ObjectModel(final Registry registry, final $Array.Object binding) {
    super(binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.type = Type.get(binding.getClass$().text(), binding.getExtends$() == null ? null : binding.getExtends$().text());
    this.superObject = null;
    this.isAbstract = null;
    this.unknown = Unknown.valueOf(binding.getUnknown$().text().toUpperCase());
    this.members = Collections.unmodifiableMap(parseMembers(registry, binding, this));
  }

  private ObjectModel(final Registry registry, final String name, final Class<?> clazz, final JsonxObject jsObject, final Boolean nullable, final Boolean required, final Integer minOccurs, final Integer maxOccurs) {
    super(name, nullable, required, minOccurs, maxOccurs);
    this.type = Type.get(clazz.getName(), clazz.getName());
    this.isAbstract = Modifier.isAbstract(clazz.getModifiers());
    this.unknown = jsObject.unknown();
    final Class<?> superClass = clazz.getSuperclass();
    if (superClass != null) {
      final JsonxObject superObject = superClass.getDeclaredAnnotation(JsonxObject.class);
      this.superObject = superObject == null ? null : referenceOrDeclare(registry, superClass);
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

    final LinkedHashMap<String,Element> members = new LinkedHashMap<String,Element>();
    for (final Field field : clazz.getDeclaredFields()) {
      final Element member = Element.toElement(registry, this, field, object);
      if (member != null)
        members.put(member.name(), member);
    }

    this.members = Collections.unmodifiableMap(members);
    recurseInnerClasses(registry, clazz);
  }

  @Override
  public final Id id() {
    return null;
  }

  public final Map<String,Element> members() {
    return this.members;
  }

  @Override
  public final Type type() {
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
  protected final void collectClassNames(final List<Type> types) {
    types.add(type());
    if (superObject != null)
      superObject.collectClassNames(types);

    if (members != null)
      for (final Element member : members.values())
        member.collectClassNames(types);
  }

  @Override
  protected String key() {
    return type().toString();
  }

  @Override
  protected final String toJSON(final String packageName) {
    final StringBuilder builder = new StringBuilder(super.toJSON(packageName));
    if (builder.length() > 0)
      builder.insert(0, ",\n");

    builder.append(",\n  class: \"").append(type().getCompoundClassName()).append('"');

    if (superObject != null)
      builder.append(",\n  extends: \"").append(superObject.type().getCompoundClassName()).append('"');

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
  protected final String toJSONX(final Registry registry, final Member owner, final String packageName) {
    final StringBuilder builder = new StringBuilder(owner instanceof ObjectModel ? "<property xsi:type=\"" + (registry.hasRegistry(id()) ? "template\" reference=\"" + id() + "\"" : "object\"") : "<object");
    builder.append(" class=\"").append(owner instanceof ObjectModel ? type.getSubName(((ObjectModel)owner).type().getName(null)) : type.getSubName(packageName)).append('"');

    if (superObject != null)
      builder.append(" extends=\"").append(superObject.type().getCompoundClassName()).append('"');

    if (isAbstract != null && isAbstract)
      builder.append(" abstract=\"").append(isAbstract).append('"');

    if (unknown != Unknown.ERROR)
      builder.append(" unknown=\"").append(unknown.toString().toLowerCase()).append('"');

    if (members != null && members.size() > 0) {
      final StringBuilder members = new StringBuilder();
      for (final Element member : this.members.values())
        members.append("\n  ").append(member.toJSONX(registry, this, packageName).replace("\n", "\n  "));

      builder.append(super.toJSONX(registry, owner, packageName)).append('>').append(members).append('\n').append(owner instanceof ObjectModel ? "</property>" : "</object>");
    }
    else {
      builder.append(super.toJSONX(registry, owner, packageName)).append("/>");
    }

    return builder.toString();
  }

  protected final String toObjectAnnotation() {
    final StringBuilder builder = new StringBuilder();
    if (unknown() != Unknown.ERROR)
      builder.append("unknown=").append(Unknown.class.getName()).append('.').append(unknown());

    return builder.toString();
  }

  @Override
  protected final void toAnnotation(final Attributes attributes, final String packageName) {
    super.toAnnotation(attributes, packageName);
    attributes.put("type", type.getCanonicalName(packageName) + ".class");
  }

  protected final String toJava(final String packageName) {
    final StringBuilder builder = new StringBuilder();
    if (members != null && members.size() > 0)
      for (final Element member : members.values())
        builder.append("\n\n").append(member.toField(packageName));

    return builder.length() > 1 ? builder.substring(2).toString() : builder.toString();
  }
}