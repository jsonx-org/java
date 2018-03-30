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
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Element;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Object;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.Jsonx;
import org.libx4j.jsonx.runtime.JsonxObject;
import org.libx4j.jsonx.runtime.ObjectElement;
import org.libx4j.jsonx.runtime.ObjectProperty;
import org.libx4j.jsonx.runtime.Unknown;

class ObjectModel extends ComplexModel {
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

  protected static Element referenceOrDeclare(final Schema schema, final Registry registry, final ComplexModel referrer, final ObjectProperty objectProperty, final Field field) {
    final ObjectModel objectModel = (ObjectModel)registry.getElement(objectProperty.type().getName());
    // FIXME: Can we get doc comments from code?
    return new RefElement(schema, getName(objectProperty.name(), field), objectProperty.required(), objectProperty.nullable(), objectModel == null ? registry.declare(objectProperty.type()).value(new ObjectModel(schema, registry, objectProperty, field), referrer) : registry.reference(objectModel, referrer), null);
  }

  protected static Element referenceOrDeclare(final Schema schema, final Registry registry, final ComplexModel referrer, final ObjectElement objectElement) {
    final ObjectModel objectModel = (ObjectModel)registry.getElement(objectElement.type().getName());
    // FIXME: Can we get doc comments from code?
    return new RefElement(schema, objectElement.nullable(), objectElement.minOccurs(), objectElement.maxOccurs(), objectModel == null ? registry.declare(objectElement.type()).value(new ObjectModel(schema, registry, objectElement), referrer) : registry.reference(objectModel, referrer), null);
  }

  public static ObjectModel reference(final Schema schema, final Registry registry, final ComplexModel referrer, final $Array.Object binding, final String superClassName) {
    return registry.reference(new ObjectModel(schema, registry, binding, getParent(superClassName, registry)), referrer);
  }

  public static ObjectModel reference(final Schema schema, final Registry registry, final ComplexModel referrer, final $Object.Object binding, final String superClassName) {
    return registry.reference(new ObjectModel(schema, registry, binding, getParent(superClassName, registry)), referrer);
  }

  public static ObjectModel declare(final Schema schema, final Registry registry, final Jsonx.Object binding) {
    return registry.declare(binding).value(new ObjectModel(schema, registry, binding), null);
  }

  public static ObjectModel referenceOrDeclare(final Schema schema, final Registry registry, final ComplexModel referrer, final Class<?> clazz) {
    return referenceOrDeclare(schema, registry, referrer, clazz, checkJSObject(clazz));
  }

  private static ObjectModel referenceOrDeclare(final Schema schema, final Registry registry, final ComplexModel referrer, final Class<?> clazz, final JsonxObject jsObject) {
    final ObjectModel objectModel = (ObjectModel)registry.getElement(clazz.getName());
    return objectModel == null ? registry.declare(clazz).value(new ObjectModel(schema, registry, null, clazz, jsObject), referrer) : registry.reference(objectModel, referrer);
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
        referenceOrDeclare(schema, registry, null, innerClass, innerJSObject);
    }
  }

  private final Map<String,Element> members;
  private final String className;
  private final ObjectModel superObject;
  private final Boolean isAbstract;
  private final Unknown unknown;

  private ObjectModel(final Schema schema, final Registry registry, final ObjectProperty objectProperty, final Field field) {
    this(schema, registry, getName(objectProperty.name(), field), objectProperty.type(), checkJSObject(objectProperty.type()));
  }

  private ObjectModel(final Schema schema, final Registry registry, final ObjectElement objectElement) {
    this(schema, registry, null, objectElement.type(), checkJSObject(objectElement.type()));
  }

  private static void x() {
    int i = 0;
  }

  // Annullable, Recurrable
  private ObjectModel(final Schema schema, final Registry registry, final $Array.Object binding, final ObjectModel superObject) {
    super(schema, binding, binding.getNullable$().text(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.className = binding.getClass$().text();
    if ("org.libx4j.jjb.runtime.Publishing".equals(this.className))
      x();

    this.superObject = superObject;
    this.isAbstract = null;
    this.unknown = Unknown.valueOf(binding.getUnknown$().text().toUpperCase());
    this.members = Collections.unmodifiableMap(parseMembers(schema, registry, binding, this));
  }

  // Nameable, Annullable, Requirable
  private ObjectModel(final Schema schema, final Registry registry, final $Object.Object binding, final ObjectModel superObject) {
    super(schema, binding, binding.getName$().text(), binding.getRequired$().text(), binding.getNullable$().text());
    this.className = binding.getClass$().text();
    if ("org.libx4j.jjb.runtime.Publishing".equals(this.className))
      x();

    this.superObject = superObject;
    this.isAbstract = null;
    this.unknown = Unknown.valueOf(binding.getUnknown$().text().toUpperCase());
    this.members = Collections.unmodifiableMap(parseMembers(schema, registry, binding, this));
  }

  // Nameable
  private ObjectModel(final Schema schema, final Registry registry, final Jsonx.Object binding) {
    super(schema, null, null, null, null, null, binding.getDoc$() == null ? null : binding.getDoc$().text());
    this.className = binding.getClass$().text();
    if ("org.libx4j.jjb.runtime.Publishing".equals(this.className))
      x();

    this.isAbstract = binding.getAbstract$().text();
    this.unknown = Unknown.valueOf(binding.getUnknown$().text().toUpperCase());
    this.superObject = binding.getExtends$() == null ? null : getParent(binding.getExtends$().text(), registry);
    this.members = Collections.unmodifiableMap(parseMembers(schema, registry, binding, this));
  }

  private static Map<String,Element> parseMembers(final Schema schema, final Registry registry, final $Object binding, final ObjectModel model) {
    final LinkedHashMap<String,Element> members = new LinkedHashMap<String,Element>();
    final Iterator<? extends $Element> elements = Iterators.filter(binding.elementIterator(), $Element.class);
    while (elements.hasNext()) {
      final $Element element = elements.next();
      if (element instanceof $Object.Boolean) {
        final $Object.Boolean member = ($Object.Boolean)element;
        members.put(member.getName$().text(), BooleanModel.reference(schema, registry, model, member));
      }
      else if (element instanceof $Object.Number) {
        final $Object.Number member = ($Object.Number)element;
        members.put(member.getName$().text(), NumberModel.reference(schema, registry, model, member));
      }
      else if (element instanceof $Object.String) {
        final $Object.String member = ($Object.String)element;
        members.put(member.getName$().text(), StringModel.reference(schema, registry, model, member));
      }
      else if (element instanceof $Object.Array) {
        final $Object.Array member = ($Object.Array)element;
        final ArrayModel child = ArrayModel.reference(schema, registry, model, member);
        members.put(member.getName$().text(), child);
      }
      else if (element instanceof $Object.Object) {
        final $Object.Object member = ($Object.Object)element;
        final ObjectModel child = ObjectModel.reference(schema, registry, model, member, member.getExtends$() == null ? null : member.getExtends$().text());
        members.put(member.getName$().text(), child);
      }
      else if (element instanceof $Object.Ref) {
        final $Object.Ref member = ($Object.Ref)element;
        final Element ref = registry.getElement(member.getProperty$().text());
        if (ref == null)
          throw new IllegalStateException("Top-level element ref=\"" + member.getProperty$().text() + "\" on " + member.getName$().text() + " not found");

        members.put(member.getName$().text(), ref instanceof Model ? new RefElement(schema, member, (Model)ref) : ref);
      }
      else {
        throw new UnsupportedOperationException("Unsupported " + element.getClass().getSimpleName() + " member type: " + element.getClass().getName());
      }
    }

    return Collections.unmodifiableMap(members);
  }

  private ObjectModel(final Schema schema, final Registry registry, final String name, final Class<?> clazz, final JsonxObject jsObject) {
    // FIXME: Can we get doc comments from code?
    super(schema, name, null, null, null, null, null);
    this.className = clazz.getName();
    if ("org.libx4j.jjb.runtime.Publishing".equals(this.className))
      x();

    this.isAbstract = Modifier.isAbstract(clazz.getModifiers());
    this.unknown = jsObject.unknown();
    final LinkedHashMap<String,Element> members = new LinkedHashMap<String,Element>();
    final Class<?> superClass = clazz.getSuperclass();
    if (superClass != null) {
      final JsonxObject superObject = superClass.getDeclaredAnnotation(JsonxObject.class);
      this.superObject = superObject == null ? null : referenceOrDeclare(schema, registry, this, superClass);
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
      final Element member = Element.toElement(schema, registry, this, field, object);
      if (member != null)
        members.put(member.name(), member);
    }

    this.members = Collections.unmodifiableMap(members);
    recurseInnerClasses(schema, registry, clazz);
  }

  private ObjectModel(final Element element, final ObjectModel model) {
    super(element);
    this.className = model.className;
    if ("org.libx4j.jjb.runtime.Publishing".equals(this.className))
      x();

    this.superObject = model.superObject;
    this.isAbstract = model.isAbstract;
    this.unknown = model.unknown;
    this.members = Collections.unmodifiableMap(model.members);
  }

  private ObjectModel(final ObjectModel copy, final ObjectModel superObject, final Map<String,Element> members) {
    super(copy);
    this.className = copy.className;
    if ("org.libx4j.jjb.runtime.Publishing".equals(this.className))
      x();

    this.superObject = superObject;
    this.isAbstract = copy.isAbstract;
    this.unknown = copy.unknown;
    this.members = Collections.unmodifiableMap(members);
  }

  public final Map<String,Element> members() {
    return this.members;
  }

  @Override
  public final Type className() {
    return superObject == null ? Type.get(getSchema().packageName(), className) : Type.get(className, superObject.className);
  }

  public final String classSimpleName() {
    final int from = className.lastIndexOf('.');
    final int to = className.lastIndexOf('$');
    return to > 0 ? className.substring(to + 1) : from > 0 ? className.substring(from + 1) : className;
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
  protected ObjectModel merge(final RefElement propertyElement) {
    return new ObjectModel(propertyElement, this);
  }

  @Override
  protected final Element normalize(final Registry registry) {
    final Element element = registry.getElement(ref());
    if (element instanceof RefElement)
      return element.normalize(registry);

    final Map<String,Element> members = normalize(registry, this.members);
    final ObjectModel superObject;
    if (superObject() == null) {
      superObject = null;
    }
    else {
      final Element superElement = superObject().normalize(registry);
      superObject = (ObjectModel)(superElement instanceof RefElement ? ((RefElement)superElement).ref() : superElement);
    }

    final ObjectModel clone = new ObjectModel(this, superObject, members);
    final ObjectModel objectModel = (ObjectModel)element;
    if (registry.getNumReferrers(this) != 1 || objectModel.isAbstract())
      return clone;

    return objectModel == this ? clone : new ObjectModel(objectModel, clone);
  }

  @Override
  protected final void collectClassNames(final List<Type> types) {
    types.add(className());
    if (superObject != null)
      superObject.collectClassNames(types);

    if (members != null)
      for (final Element member : members.values())
        member.collectClassNames(types);
  }

  @Override
  protected String ref() {
    return className().getName();
  }

  @Override
  protected final String toJSON(final String packageName) {
    final StringBuilder builder = new StringBuilder(super.toJSON(packageName));
    if (builder.length() > 0)
      builder.insert(0, ",\n");

    builder.append(",\n  class: \"").append(getShortName(className, packageName)).append('"');

    if (superObject != null)
      builder.append(",\n  extends: \"").append(getShortName(superObject.className().getName(), packageName)).append('"');

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
    final StringBuilder builder = new StringBuilder("<object");
    builder.append(" class=\"").append(getShortName(className, pacakgeName)).append('"');

    if (superObject != null)
      builder.append(" extends=\"").append(getShortName(superObject.className().getName(), pacakgeName)).append('"');

    if (isAbstract != null && isAbstract)
      builder.append(" abstract=\"").append(isAbstract).append('"');

    if (unknown != Unknown.ERROR)
      builder.append(" unknown=\"").append(unknown.toString().toLowerCase()).append('"');

    if (members != null && members.size() > 0) {
      final StringBuilder members = new StringBuilder();
      for (final Element member : this.members.values())
        members.append("\n  ").append(member.toJSONX(pacakgeName).replace("\n", "\n  "));

      builder.append(super.toJSONX(pacakgeName)).append('>').append(members).append("\n</object>");
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

    builder.append("type=").append(className.replace('$', '.')).append(".class");
    return builder.toString();
  }

  protected final String toJava() {
    final StringBuilder builder = new StringBuilder();
    if (members != null && members.size() > 0)
      for (final Element member : this.members.values())
        builder.append("\n\n").append(member.toField());

    return builder.length() > 1 ? builder.substring(2).toString() : builder.toString();
  }
}