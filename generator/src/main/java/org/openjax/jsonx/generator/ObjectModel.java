/* Copyright (c) 2017 OpenJAX
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

package org.openjax.jsonx.generator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc.$Array;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc.$Boolean;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc.$Member;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc.$Number;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc.$Object;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc.$ObjectMember;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc.$Reference;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc.$String;
import org.openjax.jsonx.runtime.JxEncoder;
import org.openjax.jsonx.runtime.JxObject;
import org.openjax.jsonx.runtime.JxUtil;
import org.openjax.jsonx.runtime.ObjectElement;
import org.openjax.jsonx.runtime.ObjectProperty;
import org.openjax.jsonx.runtime.Use;
import org.openjax.standard.lang.IllegalAnnotationException;
import org.openjax.standard.util.Classes;
import org.openjax.standard.util.Iterators;
import org.openjax.standard.util.Strings;

final class ObjectModel extends Referrer<ObjectModel> {
  static ObjectModel declare(final Registry registry, final xL4gluGCXYYJc.Schema.ObjectType binding) {
    return registry.declare(binding).value(new ObjectModel(registry, binding), null);
  }

  static ObjectModel declare(final Registry registry, final ObjectModel referrer, final $Object binding) {
    return registry.declare(binding).value(new ObjectModel(registry, binding), referrer);
  }

  static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final ObjectProperty property, final Field field) {
    if (!isAssignable(field, JxObject.class, property.nullable(), property.use()))
      throw new IllegalAnnotationException(property, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + ObjectProperty.class.getSimpleName() + " can only be applied to required or not-nullable fields of JxObject type, or optional and nullable fields of Optional<? extends JxObject> type");

    final Id id = new Id(getRealType(field));
    final ObjectModel model = (ObjectModel)registry.getModel(id);
    return new Reference(registry, JxUtil.getName(property.name(), field), property.nullable(), property.use(), model == null ? registry.declare(id).value(new ObjectModel(registry, field, property), referrer) : registry.reference(model, referrer));
  }

  static Member referenceOrDeclare(final Registry registry, final Element referrer, final ObjectElement element) {
    final Id id = new Id(element.type());
    final ObjectModel model = (ObjectModel)registry.getModel(id);
    return new Reference(registry, element.nullable(), element.minOccurs(), element.maxOccurs(), model == null ? registry.declare(id).value(new ObjectModel(registry, element), referrer instanceof Referrer ? (Referrer<?>)referrer : null) : registry.reference(model, referrer instanceof Referrer ? (Referrer<?>)referrer : null));
  }

  static ObjectModel referenceOrDeclare(final Registry registry, final Class<?> cls) {
    checkJSObject(cls);
    final Id id = new Id(cls);
    final ObjectModel model = (ObjectModel)registry.getModel(id);
    return model != null ? registry.reference(model, null) : registry.declare(id).value(new ObjectModel(registry, cls, null, null), null);
  }

  static String getFullyQualifiedName(final $Object binding) {
    final StringBuilder builder = new StringBuilder();
    $Object owner = binding;
    do
      builder.insert(0, '$').insert(1, Strings.flipFirstCap(owner.getName$().text()));
    while (owner.owner() instanceof $Object && (owner = ($Object)owner.owner()) != null);
    return builder.insert(0, JxUtil.flipName(((xL4gluGCXYYJc.Schema.ObjectType)owner.owner()).getName$().text())).toString();
  }

  private static void checkJSObject(final Class<?> cls) {
    if (!JxObject.class.isAssignableFrom(cls))
      throw new IllegalArgumentException("Class " + cls.getName() + " does not implement " + JxObject.class.getName());
  }

  private static void recurseInnerClasses(final Registry registry, final Class<?> cls) {
    for (final Class<?> innerClass : cls.getClasses()) {
      if (!JxObject.class.isAssignableFrom(innerClass))
        recurseInnerClasses(registry, innerClass);
      else
        referenceOrDeclare(registry, innerClass);
    }
  }

  private Map<String,Member> parseMembers(final $ObjectMember binding, final ObjectModel objectModel) {
    final LinkedHashMap<String,Member> members = new LinkedHashMap<>();
    final Iterator<? super $Member> iterator = Iterators.filter(binding.elementIterator(), m -> m instanceof $Member);
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

  private static Class<?> getRealType(final Field field) {
    return Optional.class.isAssignableFrom(field.getType()) ? Classes.getGenericTypes(field)[0] : field.getType();
  }

  private final Id id;
  final Map<String,Member> members;
  private final Registry.Type type;
  final ObjectModel superObject;
  final boolean isAbstract;

  private ObjectModel(final Registry registry, final xL4gluGCXYYJc.Schema.ObjectType binding) {
    super(registry);
    this.type = registry.getType(registry.packageName, registry.classPrefix + JxUtil.flipName(binding.getName$().text()), binding.getExtends$() != null ? registry.classPrefix + JxUtil.flipName(binding.getExtends$().text()) : null);
    this.isAbstract = binding.getAbstract$().text();
    this.superObject = getReference(binding.getExtends$());
    this.members = Collections.unmodifiableMap(parseMembers(binding, this));
    this.id = new Id(this);
  }

  private ObjectModel(final Registry registry, final Field field, final ObjectProperty property) {
    this(registry, getRealType(field), property.nullable(), property.use());
  }

  private ObjectModel(final Registry registry, final ObjectElement element) {
    this(registry, element.type(), element.nullable(), null);
  }

  private ObjectModel(final Registry registry, final $Object binding) {
    super(registry, binding.getName$(), binding.getNullable$(), binding.getUse$());
    this.type = registry.getType(registry.packageName, registry.classPrefix + getFullyQualifiedName(binding), binding.getExtends$() != null ? registry.classPrefix + JxUtil.flipName(binding.getExtends$().text()) : null);
    this.superObject = getReference(binding.getExtends$());
    this.isAbstract = false;
    this.members = Collections.unmodifiableMap(parseMembers(binding, this));
    this.id = new Id(this);
  }

  private ObjectModel(final Registry registry, final Class<?> cls, final Boolean nullable, final Use use) {
    super(registry, nullable, use);
    final Class<?> superClass = cls.getSuperclass();
    this.type = registry.getType(cls);
    this.isAbstract = Modifier.isAbstract(cls.getModifiers());
    this.superObject = superClass == null || !JxObject.class.isAssignableFrom(superClass) ? null : referenceOrDeclare(registry, superClass);

    final LinkedHashMap<String,Member> members = new LinkedHashMap<>();
    for (final Field field : cls.getDeclaredFields()) {
      final Member member = Member.toMember(registry, this, field);
      if (member != null)
        members.put(member.name, member);
    }

    this.members = Collections.unmodifiableMap(members);
    recurseInnerClasses(registry, cls);
    this.id = new Id(this);
  }

  @Override
  Id id() {
    return id;
  }

  @Override
  Registry.Type type() {
    return type;
  }

  @Override
  Registry.Type classType() {
    return type;
  }

  @Override
  String elementName() {
    return "object";
  }

  @Override
  String sortKey() {
    return "z" + type().getName();
  }

  @Override
  Class<? extends Annotation> propertyAnnotation() {
    return ObjectProperty.class;
  }

  @Override
  Class<? extends Annotation> elementAnnotation() {
    return ObjectElement.class;
  }

  @Override
  void getDeclaredTypes(final Set<Registry.Type> types) {
    types.add(type());
    if (superObject != null)
      superObject.getDeclaredTypes(types);

    if (members != null)
      for (final Member member : members.values())
        member.getDeclaredTypes(types);
  }

  @Override
  Map<String,String> toXmlAttributes(final Element owner, final String packageName) {
    final Map<String,String> attributes = super.toXmlAttributes(owner, packageName);
    attributes.put(owner instanceof ArrayModel ? "type" : "name", JxUtil.flipName(owner instanceof ObjectModel ? type.getSubName(((ObjectModel)owner).type().getName()) : type.getSubName(packageName)));

    if (superObject != null)
      attributes.put("extends", JxUtil.flipName(superObject.type().getRelativeName(packageName)));

    if (isAbstract)
      attributes.put("abstract", String.valueOf(isAbstract));

    return attributes;
  }

  @Override
  org.openjax.standard.xml.api.Element toXml(final Settings settings, final Element owner, final String packageName) {
    final org.openjax.standard.xml.api.Element element = super.toXml(settings, owner, packageName);
    if (members == null || members.size() == 0)
      return element;

    final List<org.openjax.standard.xml.api.Element> elements = new ArrayList<>();
    for (final Member member : this.members.values())
      elements.add(member.toXml(settings, this, packageName));

    element.setElements(elements);
    return element;
  }

  @Override
  void toAnnotationAttributes(final AttributeMap attributes, final Member owner) {
    super.toAnnotationAttributes(attributes, owner);
    if (owner instanceof ArrayModel)
      attributes.put("type", type.getCanonicalName() + ".class");
  }

  @Override
  List<AnnotationSpec> getClassAnnotation() {
    return null;
  }

  @Override
  String toSource(final Settings settings) {
    final StringBuilder builder = new StringBuilder();
    if (members != null && members.size() > 0) {
      final Iterator<Member> iterator = members.values().iterator();
      for (int i = 0; iterator.hasNext(); ++i) {
        if (i > 0)
          builder.append("\n\n");

        builder.append(iterator.next().toField());
      }
    }

    builder.append("\n\n@").append(Override.class.getName());
    builder.append("\npublic boolean equals(final ").append(Object.class.getName()).append(" obj) {");
    builder.append("\n  if (obj == this)");
    builder.append("\n    return true;");
    builder.append("\n\n  if (!(obj instanceof ").append(type.getCanonicalName()).append(")").append((type.getSuperType() != null ? " || !super.equals(obj)" : "")).append(")");
    builder.append("\n    return false;\n");
    if (members != null && members.size() > 0) {
      builder.append("\n  final ").append(type.getCanonicalName()).append(" that = (").append(type.getCanonicalName()).append(")obj;");
      for (final Member member : members.values()) {
        final String instanceName = member.toInstanceName();
        builder.append("\n  if (that.").append(instanceName).append(" != null ? !that.").append(instanceName).append(".equals(").append(instanceName).append(") : ").append(instanceName).append(" != null)");
        builder.append("\n    return false;\n");
      }
    }
    builder.append("\n  return true;");
    builder.append("\n}");

    builder.append("\n\n@").append(Override.class.getName());
    builder.append("\npublic int hashCode() {");
    if (members != null && members.size() > 0) {
      builder.append("\n  int hashCode = ").append(type.getName().hashCode()).append((type.getSuperType() != null ? " * 31 + super.hashCode()" : "")).append(";");
      for (final Member member : members.values()) {
        final String instanceName = member.toInstanceName();
        builder.append("\n  hashCode = 31 * hashCode + (").append(instanceName).append(" == null ? 0 : ").append(instanceName).append(".hashCode());");
      }

      builder.append("\n  return hashCode;");
    }
    else {
      builder.append("\n  return ").append(type.getName().hashCode()).append((type.getSuperType() != null ? " * 31 + super.hashCode()" : "")).append(";");
    }
    builder.append("\n}");

    builder.append("\n\n@").append(Override.class.getName());
    builder.append("\npublic ").append(String.class.getName()).append(" toString() {");
    builder.append("\n  return new ").append(JxEncoder.class.getName()).append("(").append(settings.getToStringIndent()).append(").marshal(this);");
    builder.append("\n}");

    return builder.toString();
  }
}