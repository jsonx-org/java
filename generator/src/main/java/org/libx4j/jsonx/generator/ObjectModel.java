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
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Reference;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$String;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.Jsonx;
import org.libx4j.jsonx.runtime.JsonxObject;
import org.libx4j.jsonx.runtime.JsonxUtil;
import org.libx4j.jsonx.runtime.ObjectElement;
import org.libx4j.jsonx.runtime.ObjectProperty;
import org.libx4j.jsonx.runtime.Unknown;
import org.libx4j.jsonx.runtime.Use;
import org.w3.www._2001.XMLSchema.yAA.$AnySimpleType;

final class ObjectModel extends Referrer<ObjectModel> {
  public static ObjectModel declare(final Registry registry, final Jsonx.Object binding) {
    return registry.declare(binding).value(new ObjectModel(registry, binding), null);
  }

  public static ObjectModel declare(final Registry registry, final ObjectModel referrer, final $Object binding) {
    return registry.declare(binding).value(new ObjectModel(registry, binding), referrer);
  }

  public static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final ObjectProperty property, final Field field) {
    final Id id = new Id(field.getType());
    final ObjectModel model = (ObjectModel)registry.getModel(id);
    return new Reference(registry, JsonxUtil.getName(property.name(), field), property.use(), model == null ? registry.declare(id).value(new ObjectModel(registry, field, property), referrer) : registry.reference(model, referrer));
  }

  public static Member referenceOrDeclare(final Registry registry, final Element referrer, final ObjectElement element) {
    final Id id = new Id(element.type());
    final ObjectModel model = (ObjectModel)registry.getModel(id);
    return new Reference(registry, element.nullable(), element.minOccurs(), element.maxOccurs(), model == null ? registry.declare(id).value(new ObjectModel(registry, element), referrer instanceof Referrer ? (Referrer<?>)referrer : null) : registry.reference(model, referrer instanceof Referrer ? (Referrer<?>)referrer : null));
  }

  public static ObjectModel referenceOrDeclare(final Registry registry, final Class<?> cls) {
    return referenceOrDeclare(registry, cls, checkJSObject(cls));
  }

  private static ObjectModel referenceOrDeclare(final Registry registry, final Class<?> cls, final JsonxObject jsObject) {
    final Id id = new Id(cls);
    final ObjectModel model = (ObjectModel)registry.getModel(id);
    return model != null ? registry.reference(model, null) : registry.declare(id).value(new ObjectModel(registry, cls, jsObject, null, null), null);
  }

  public static String getFullyQualifiedName(final $Object binding) {
    final StringBuilder builder = new StringBuilder();
    $Object owner = binding;
    do
      builder.insert(0, '$').insert(1, owner.getClass$().text());
    while (owner.owner() instanceof $Object && (owner = ($Object)owner.owner()) != null);
    return builder.insert(0, ((Jsonx.Object)owner.owner()).getClass$().text()).toString();
  }

  private static JsonxObject checkJSObject(final Class<?> cls) {
    final JsonxObject jsObject = cls.getDeclaredAnnotation(JsonxObject.class);
    if (jsObject == null)
      throw new IllegalArgumentException("Class " + cls.getName() + " does not specify the @" + JsonxObject.class.getSimpleName() + " annotation.");

    return jsObject;
  }

  private static void recurseInnerClasses(final Registry registry, final Class<?> cls) {
    for (final Class<?> innerClass : cls.getClasses()) {
      final JsonxObject innerJSObject = innerClass.getDeclaredAnnotation(JsonxObject.class);
      if (innerJSObject == null)
        recurseInnerClasses(registry, innerClass);
      else
        referenceOrDeclare(registry, innerClass, innerJSObject);
    }
  }

  private Map<String,Member> parseMembers(final $ObjectMember binding, final ObjectModel objectModel) {
    final LinkedHashMap<String,Member> members = new LinkedHashMap<>();
    final Iterator<? super $Member> iterator = Iterators.filter(binding.elementIterator(), m -> $Member.class.isInstance(m));
    while (iterator.hasNext()) {
      final $Member member = ($Member)iterator.next();
      if (member instanceof $Boolean) {
        final $Boolean bool = ($Boolean)member;
        members.put(bool.getName$().text(), BooleanModel.reference(registry, objectModel, bool));
      }
      else if (member instanceof $Number) {
        final $Number number = ($Number)member;
        members.put(number.getName$().text(), NumberModel.reference(registry, objectModel, number));
      }
      else if (member instanceof $String) {
        final $String string = ($String)member;
        members.put(string.getName$().text(), StringModel.reference(registry, objectModel, string));
      }
      else if (member instanceof $Array) {
        final $Array array = ($Array)member;
        final ArrayModel child = ArrayModel.reference(registry, objectModel, array);
        members.put(array.getName$().text(), child);
      }
      else if (member instanceof $Reference) {
        final $Reference reference = ($Reference)member;
        final Member model = registry.getModel(new Id(reference.getType$()));
        if (model == null)
          throw new IllegalStateException("Template \"" + reference.getName$().text() + "\" -> reference=\"" + reference.getType$().text() + "\" not found");

        members.put(reference.getName$().text(), model instanceof Model ? new Reference(registry, reference, registry.reference((Model)model, objectModel)) : model);
      }
      else if (member instanceof $Object) {
        final $Object object = ($Object)member;
        final ObjectModel child = declare(registry, objectModel, object);
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
    super(registry);
    this.type = registry.getType((String)binding.owner().getPackage$().text(), binding.getClass$().text(), binding.getExtends$() == null ? null : binding.getExtends$().text());
    this.isAbstract = binding.getAbstract$().text();
    this.unknown = Unknown.valueOf(binding.getUnknown$().text().toUpperCase());
    this.superObject = binding.getExtends$() == null ? null : getReference(binding.getExtends$());
    this.members = Collections.unmodifiableMap(parseMembers(binding, this));
    this.id = new Id(this);
  }

  private ObjectModel(final Registry registry, final Field field, final ObjectProperty property) {
    this(registry, field.getType(), checkJSObject(field.getType()), null, property.use());
  }

  private ObjectModel(final Registry registry, final ObjectElement element) {
    this(registry, element.type(), checkJSObject(element.type()), element.nullable(), null);
  }

  private static Jsonx getJsonx($AnySimpleType member) {
    do
      if (member instanceof Jsonx)
        return (Jsonx)member;
    while ((member = member.owner()) != null);
    return null;
  }

  private ObjectModel(final Registry registry, final $Object binding) {
    super(registry, binding.getName$(), binding.getUse$());
    this.type = registry.getType((String)getJsonx(binding).getPackage$().text(), getFullyQualifiedName(binding), binding.getExtends$() == null ? null : binding.getExtends$().text());
    this.superObject = binding.getExtends$() == null ? null : getReference(binding.getExtends$());
    this.isAbstract = null;
    this.unknown = Unknown.valueOf(binding.getUnknown$().text().toUpperCase());
    this.members = Collections.unmodifiableMap(parseMembers(binding, this));
    this.id = new Id(this);
  }

  private ObjectModel(final Registry registry, final Class<?> cls, final JsonxObject jsObject, final Boolean nullable, final Use use) {
    super(registry, nullable, use);
    final Class<?> superClass = cls.getSuperclass();
    this.type = registry.getType(cls);
    this.isAbstract = Modifier.isAbstract(cls.getModifiers());
    this.unknown = jsObject.unknown();
    if (superClass != null) {
      final JsonxObject superObject = superClass.getDeclaredAnnotation(JsonxObject.class);
      this.superObject = superObject == null ? null : referenceOrDeclare(registry, superClass);
    }
    else {
      this.superObject = null;
    }

    final LinkedHashMap<String,Member> members = new LinkedHashMap<>();
    for (final Field field : cls.getDeclaredFields()) {
      final Member member = Member.toMember(registry, this, field);
      if (member != null)
        members.put(member.name(), member);
    }

    this.members = Collections.unmodifiableMap(members);
    recurseInnerClasses(registry, cls);
    this.id = new Id(this);
  }

  @Override
  protected Id id() {
    return id;
  }

  public Map<String,Member> members() {
    return this.members;
  }

  @Override
  public Registry.Type type() {
    return type;
  }

  @Override
  protected Registry.Type classType() {
    return type;
  }

  public ObjectModel superObject() {
    return this.superObject;
  }

  public boolean isAbstract() {
    return this.isAbstract != null && this.isAbstract;
  }

  public Unknown unknown() {
    return this.unknown;
  }

  @Override
  protected String elementName() {
    return "object";
  }

  @Override
  protected Class<? extends Annotation> propertyAnnotation() {
    return ObjectProperty.class;
  }

  @Override
  protected Class<? extends Annotation> elementAnnotation() {
    return ObjectElement.class;
  }

  @Override
  protected void getDeclaredTypes(final Set<Registry.Type> types) {
    types.add(type());
    if (superObject != null)
      superObject.getDeclaredTypes(types);

    if (members != null)
      for (final Member member : members.values())
        member.getDeclaredTypes(types);
  }

  @Override
  protected Map<String,String> toXmlAttributes(final Element owner, final String packageName) {
    final Map<String,String> attributes = super.toXmlAttributes(owner, packageName);
    attributes.put(owner instanceof ArrayModel ? "type" : "class", owner instanceof ObjectModel ? type.getSubName(((ObjectModel)owner).type().getName()) : type.getSubName(packageName));

    if (superObject != null)
      attributes.put("extends", superObject.type().getRelativeName(packageName));

    if (isAbstract != null && isAbstract)
      attributes.put("abstract", String.valueOf(isAbstract));

    if (unknown != Unknown.ERROR)
      attributes.put("unknown", unknown.toString().toLowerCase());

    return attributes;
  }

  @Override
  protected org.lib4j.xml.Element toXml(final Settings settings, final Element owner, final String packageName) {
    final org.lib4j.xml.Element element = super.toXml(settings, owner, packageName);
    if (members == null || members.size() == 0)
      return element;

    final List<org.lib4j.xml.Element> elements = new ArrayList<>();
    for (final Member member : this.members.values())
      elements.add(member.toXml(settings, this, packageName));

    element.setElements(elements);
    return element;
  }

  @Override
  protected void toAnnotationAttributes(final AttributeMap attributes, final Member owner) {
    super.toAnnotationAttributes(attributes, owner);
    if (owner instanceof ArrayModel)
      attributes.put("type", type.getCanonicalName() + ".class");
  }

  @Override
  protected List<AnnotationSpec> getClassAnnotation() {
    final AttributeMap attributes = new AttributeMap();
    if (unknown() != Unknown.ERROR)
      attributes.put("unknown", Unknown.class.getName() + '.' + unknown());

    return Collections.singletonList(new AnnotationSpec(JsonxObject.class, attributes));
  }

  @Override
  protected String toSource() {
    final StringBuilder builder = new StringBuilder();
    if (members != null && members.size() > 0) {
      final Iterator<Member> iterator = members.values().iterator();
      for (int i = 0; iterator.hasNext(); i++) {
        if (i > 0)
          builder.append("\n\n");

        builder.append(iterator.next().toField());
      }
    }

    return builder.toString();
  }
}