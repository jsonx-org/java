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
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.jsonx.www.schema_0_2_3.xL0gluGCXYYJc;
import org.libj.lang.IllegalAnnotationException;
import org.libj.util.Classes;
import org.libj.util.Iterators;
import org.libj.util.Strings;
import org.openjax.xml.api.XmlElement;

final class ObjectModel extends Referrer<ObjectModel> {
  private static xL0gluGCXYYJc.Schema.Object type(final schema.ObjectType jsd, final String name) {
    final xL0gluGCXYYJc.Schema.Object xsb = new xL0gluGCXYYJc.Schema.Object();
    if (name != null)
      xsb.setName$(new xL0gluGCXYYJc.Schema.Object.Name$(name));

    if (jsd.getJsd_3aAbstract() != null)
      xsb.setAbstract$(new xL0gluGCXYYJc.Schema.Object.Abstract$(jsd.getJsd_3aAbstract()));

    return xsb;
  }

  private static xL0gluGCXYYJc.$Object property(final schema.ObjectProperty jsd, final String name) {
    final xL0gluGCXYYJc.$Object xsb = new xL0gluGCXYYJc.$Object() {
      private static final long serialVersionUID = 5201562440101597524L;

      @Override
      protected xL0gluGCXYYJc.$Member inherits() {
        return new xL0gluGCXYYJc.$ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setName$(new xL0gluGCXYYJc.$Object.Name$(name));

    if (jsd.getJsd_3aNullable() != null)
      xsb.setNullable$(new xL0gluGCXYYJc.$Object.Nullable$(jsd.getJsd_3aNullable()));

    if (jsd.getJsd_3aUse() != null)
      xsb.setUse$(new xL0gluGCXYYJc.$Object.Use$(xL0gluGCXYYJc.$Object.Use$.Enum.valueOf(jsd.getJsd_3aUse())));

    if (jsd.getJsd_3aExtends() != null)
      xsb.setExtends$(new xL0gluGCXYYJc.$ObjectMember.Extends$(jsd.getJsd_3aExtends()));

    return xsb;
  }

  static xL0gluGCXYYJc.$ObjectMember jsdToXsb(final schema.Object jsd, final String name) {
    final xL0gluGCXYYJc.$ObjectMember xsb;
    if (jsd instanceof schema.ObjectType)
      xsb = type((schema.ObjectType)jsd, name);
    else if (jsd instanceof schema.ObjectProperty)
      xsb = property((schema.ObjectProperty)jsd, name);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsd.getClass().getName());

    if (jsd.getJsd_3aDoc() != null && jsd.getJsd_3aDoc().length() > 0)
      xsb.setDoc$(new xL0gluGCXYYJc.$Documented.Doc$(jsd.getJsd_3aDoc()));

    if (jsd.getJsd_3aExtends() != null)
      xsb.setExtends$(new xL0gluGCXYYJc.$ObjectMember.Extends$(jsd.getJsd_3aExtends()));

    if (jsd.getJsd_3aProperties() != null) {
      for (final Map.Entry<String,Object> property : jsd.getJsd_3aProperties()._2e_2a.entrySet()) {
        if (property.getValue() instanceof schema.AnyProperty)
          xsb.addProperty(AnyModel.jsdToXsb((schema.AnyProperty)property.getValue(), property.getKey()));
        else if (property.getValue() instanceof schema.ArrayProperty)
          xsb.addProperty(ArrayModel.jsdToXsb((schema.ArrayProperty)property.getValue(), property.getKey()));
        else if (property.getValue() instanceof schema.BooleanProperty)
          xsb.addProperty(BooleanModel.jsdToXsb((schema.BooleanProperty)property.getValue(), property.getKey()));
        else if (property.getValue() instanceof schema.NumberProperty)
          xsb.addProperty(NumberModel.jsdToXsb((schema.NumberProperty)property.getValue(), property.getKey()));
        else if (property.getValue() instanceof schema.ReferenceProperty)
          xsb.addProperty(Reference.jsdToXsb((schema.ReferenceProperty)property.getValue(), property.getKey()));
        else if (property.getValue() instanceof schema.StringProperty)
          xsb.addProperty(StringModel.jsdToXsb((schema.StringProperty)property.getValue(), property.getKey()));
        else if (property.getValue() instanceof schema.ObjectProperty)
          xsb.addProperty(ObjectModel.jsdToXsb((schema.ObjectProperty)property.getValue(), property.getKey()));
        else
          throw new UnsupportedOperationException("Unsupported type: " + property.getValue().getClass().getName());
      }
    }

    return xsb;
  }

  static ObjectModel declare(final Registry registry, final Declarer declarer, final xL0gluGCXYYJc.Schema.Object binding) {
    return registry.declare(binding).value(new ObjectModel(registry, declarer, binding), null);
  }

  static ObjectModel declare(final Registry registry, final ObjectModel referrer, final xL0gluGCXYYJc.$Object binding) {
    return registry.declare(binding).value(new ObjectModel(registry, referrer, binding), referrer);
  }

  static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final ObjectProperty property, final Field field) {
    if (!isAssignable(field, JxObject.class, false, property.nullable(), property.use()))
      throw new IllegalAnnotationException(property, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + ObjectProperty.class.getSimpleName() + " can only be applied to required or not-nullable fields of JxObject type, or optional and nullable fields of Optional<? extends JxObject> type");

    final Id id = Id.named(getRealType(field));
    if (registry.isPending(id))
      return Reference.defer(registry, referrer, JsdUtil.getName(property.name(), field), property.nullable(), property.use(), () -> registry.reference(registry.getModel(id), referrer));

    final ObjectModel model = (ObjectModel)registry.getModel(id);
    return new Reference(registry, referrer, JsdUtil.getName(property.name(), field), property.nullable(), property.use(), model == null ? registry.declare(id).value(new ObjectModel(registry, referrer, field, property), referrer) : registry.reference(model, referrer));
  }

  static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final ObjectElement element) {
    final Id id = Id.named(element.type());
    if (registry.isPending(id))
      return Reference.defer(registry, referrer, element.nullable(), element.minOccurs(), element.maxOccurs(), () -> registry.reference(registry.getModel(id), referrer));

    final ObjectModel model = (ObjectModel)registry.getModel(id);
    return new Reference(registry, referrer, element.nullable(), element.minOccurs(), element.maxOccurs(), model == null ? registry.declare(id).value(new ObjectModel(registry, referrer, element), referrer) : registry.reference(model, referrer));
  }

  static Member referenceOrDeclare(final Registry registry, final Declarer declarer, final Class<?> cls) {
    checkJSObject(cls);
    final Id id = Id.named(cls);
    if (registry.isPending(id))
      return new Deferred<>(null, () -> registry.reference(registry.getModel(id), null));

    final ObjectModel model = (ObjectModel)registry.getModel(id);
    return model != null ? registry.reference(model, null) : registry.declare(id).value(new ObjectModel(registry, declarer, cls, null, null), null);
  }

  static String getFullyQualifiedName(final xL0gluGCXYYJc.$Object binding) {
    final StringBuilder builder = new StringBuilder();
    xL0gluGCXYYJc.$Object owner = binding;
    do
      builder.insert(0, '$').insert(1, JsdUtil.toIdentifier(Strings.flipFirstCap(owner.getName$().text())));
    while (owner.owner() instanceof xL0gluGCXYYJc.$Object && (owner = (xL0gluGCXYYJc.$Object)owner.owner()) != null);
    return builder.insert(0, JsdUtil.toIdentifier(JsdUtil.flipName(((xL0gluGCXYYJc.Schema.Object)owner.owner()).getName$().text()))).toString();
  }

  private static Member getReference(final Registry registry, final Referrer<?> referrer, final Field field) {
    Member reference = null;
    for (final Annotation annotation : field.getAnnotations()) {
      Member next = null;
      if (AnyProperty.class.equals(annotation.annotationType()))
        next = AnyModel.referenceOrDeclare(registry, referrer, (AnyProperty)annotation, field);
      else if (ArrayProperty.class.equals(annotation.annotationType()))
        next = ArrayModel.referenceOrDeclare(registry, referrer, (ArrayProperty)annotation, field);
      else if (BooleanProperty.class.equals(annotation.annotationType()))
        next = BooleanModel.referenceOrDeclare(registry, referrer, (BooleanProperty)annotation, field);
      else if (NumberProperty.class.equals(annotation.annotationType()))
        next = NumberModel.referenceOrDeclare(registry, referrer, (NumberProperty)annotation, field);
      else if (ObjectProperty.class.equals(annotation.annotationType()))
        next = ObjectModel.referenceOrDeclare(registry, referrer, (ObjectProperty)annotation, field);
      else if (StringProperty.class.equals(annotation.annotationType()))
        next = StringModel.referenceOrDeclare(registry, referrer, (StringProperty)annotation, field);

      if (reference == null)
        reference = next;
      else if (next != null)
        throw new ValidationException(field.getDeclaringClass().getName() + "." + field.getName() + " specifies multiple parameter annotations: [" + reference.elementName() + ", " + next.elementName() + "]");
    }

    return reference;
  }

  private static void checkJSObject(final Class<?> cls) {
    if (!JxObject.class.isAssignableFrom(cls))
      throw new IllegalArgumentException("Class " + cls.getName() + " does not implement " + JxObject.class.getName());
  }

  private void recurseInnerClasses(final Registry registry, final Class<?> cls) {
    for (final Class<?> innerClass : cls.getClasses()) {
      if (!JxObject.class.isAssignableFrom(innerClass))
        recurseInnerClasses(registry, innerClass);
      else
        referenceOrDeclare(registry, this, innerClass);
    }
  }

  private Map<String,Member> parseMembers(final xL0gluGCXYYJc.$ObjectMember binding, final ObjectModel objectModel) {
    final LinkedHashMap<String,Member> members = new LinkedHashMap<>();
    final Iterator<? super xL0gluGCXYYJc.$Member> iterator = Iterators.filter(binding.elementIterator(), m -> m instanceof xL0gluGCXYYJc.$Member);
    while (iterator.hasNext()) {
      final xL0gluGCXYYJc.$Member member = (xL0gluGCXYYJc.$Member)iterator.next();
      if (member instanceof xL0gluGCXYYJc.$Any) {
        final xL0gluGCXYYJc.$Any any = (xL0gluGCXYYJc.$Any)member;
        members.put(any.getNames$().text(), AnyModel.reference(registry, objectModel, any));
      }
      else if (member instanceof xL0gluGCXYYJc.$Array) {
        final xL0gluGCXYYJc.$Array array = (xL0gluGCXYYJc.$Array)member;
        final ArrayModel child = ArrayModel.reference(registry, objectModel, array);
        members.put(array.getName$().text(), child);
      }
      else if (member instanceof xL0gluGCXYYJc.$Boolean) {
        final xL0gluGCXYYJc.$Boolean bool = (xL0gluGCXYYJc.$Boolean)member;
        members.put(bool.getName$().text(), BooleanModel.reference(registry, objectModel, bool));
      }
      else if (member instanceof xL0gluGCXYYJc.$Number) {
        final xL0gluGCXYYJc.$Number number = (xL0gluGCXYYJc.$Number)member;
        members.put(number.getName$().text(), NumberModel.reference(registry, objectModel, number));
      }
      else if (member instanceof xL0gluGCXYYJc.$Object) {
        final xL0gluGCXYYJc.$Object object = (xL0gluGCXYYJc.$Object)member;
        final ObjectModel child = declare(registry, objectModel, object);
        members.put(object.getName$().text(), child);
      }
      else if (member instanceof xL0gluGCXYYJc.$Reference) {
        final xL0gluGCXYYJc.$Reference reference = (xL0gluGCXYYJc.$Reference)member;
        final Id id = Id.named(reference.getType$());
        final Member child = registry.getModel(id);
        members.put(reference.getName$().text(), child instanceof Reference ? child : Reference.defer(registry, objectModel, reference, () -> registry.reference(registry.getModel(id), objectModel)));
      }
      else if (member instanceof xL0gluGCXYYJc.$String) {
        final xL0gluGCXYYJc.$String string = (xL0gluGCXYYJc.$String)member;
        members.put(string.getName$().text(), StringModel.reference(registry, objectModel, string));
      }
      else {
        throw new UnsupportedOperationException("Unsupported " + member.getClass().getSimpleName() + " member type: " + member.getClass().getName());
      }
    }

    return members;
  }

  private static Class<?> getRealType(final Field field) {
    return Optional.class.isAssignableFrom(field.getType()) ? Classes.getGenericClasses(field)[0] : field.getType();
  }

  final Map<String,Member> members;
  private Member superObject;
  final boolean isAbstract;

  private ObjectModel(final Registry registry, final Declarer declarer, final xL0gluGCXYYJc.Schema.Object binding) {
    super(registry, declarer, registry.getType(registry.packageName, registry.classPrefix + JsdUtil.flipName(binding.getName$().text()), binding.getExtends$() != null ? registry.classPrefix + JsdUtil.flipName(binding.getExtends$().text()) : null), binding.getDoc$());
    this.isAbstract = binding.getAbstract$().text();
    this.superObject = getReference(binding.getExtends$());
    this.members = parseMembers(binding, this);
  }

  private ObjectModel(final Registry registry, final Declarer declarer, final Field field, final ObjectProperty property) {
    this(registry, declarer, getRealType(field), property.nullable(), property.use());
  }

  private ObjectModel(final Registry registry, final Declarer declarer, final ObjectElement element) {
    this(registry, declarer, element.type(), element.nullable(), null);
  }

  private ObjectModel(final Registry registry, final Declarer declarer, final xL0gluGCXYYJc.$Object binding) {
    super(registry, declarer, binding.getDoc$(), binding.getName$(), binding.getNullable$(), binding.getUse$(), registry.getType(registry.packageName, registry.classPrefix + getFullyQualifiedName(binding), binding.getExtends$() != null ? registry.classPrefix + JsdUtil.flipName(binding.getExtends$().text()) : null));
    this.superObject = getReference(binding.getExtends$());
    this.isAbstract = false;
    this.members = parseMembers(binding, this);
  }

  private ObjectModel(final Registry registry, final Declarer declarer, final Class<?> cls, final Boolean nullable, final Use use) {
    super(registry, declarer, nullable, use, registry.getType(cls));
    final Class<?> superClass = cls.getSuperclass();
    this.isAbstract = Modifier.isAbstract(cls.getModifiers());
    this.superObject = superClass == null || !JxObject.class.isAssignableFrom(superClass) ? null : referenceOrDeclare(registry, declarer, superClass);

    final LinkedHashMap<String,Member> members = new LinkedHashMap<>();
    for (final Field field : cls.getDeclaredFields()) {
      final Member reference = getReference(registry, this, field);
      if (reference != null)
        members.put(reference.name, reference);
    }

    this.members = members;
    recurseInnerClasses(registry, cls);
  }

  @Override
  Registry.Type type() {
    return classType();
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

  private boolean referencesResolved = false;

  @Override
  void resolveReferences() {
    if (referencesResolved)
      return;

    if (superObject instanceof Deferred)
      superObject = ((Deferred<Model>)superObject).resolve();

    for (final Map.Entry<String,Member> member : members.entrySet())
      if (member.getValue() instanceof Deferred)
        member.setValue(((Deferred<?>)member.getValue()).resolve());

    referencesResolved = true;
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
  Map<String,Object> toAttributes(final Element owner, final String prefix, final String packageName) {
    final Map<String,Object> attributes = super.toAttributes(owner, prefix, packageName);
    attributes.put(owner instanceof ArrayModel ? "type" : "name", name != null ? name : JsdUtil.flipName(owner instanceof ObjectModel ? classType().getSubName(((ObjectModel)owner).type().getName()) : classType().getSubName(packageName)));

    if (superObject != null)
      attributes.put("extends", JsdUtil.flipName(superObject.type().getRelativeName(packageName)));

    if (isAbstract)
      attributes.put("abstract", isAbstract);

    return attributes;
  }

  @Override
  XmlElement toXml(final Settings settings, final Element owner, final String prefix, final String packageName) {
    final XmlElement element = super.toXml(settings, owner, prefix, packageName);
    if (members == null || members.size() == 0)
      return element;

    final List<XmlElement> elements = new ArrayList<>();
    for (final Member member : members.values())
      elements.add(member.toXml(settings, this, prefix, packageName));

    element.setElements(elements);
    return element;
  }

  @Override
  Map<String,Object> toJson(final Settings settings, final Element owner, final String packageName) {
    final Map<String,Object> element = super.toJson(settings, owner, packageName);
    if (members == null || members.size() == 0)
      return element;

    final Map<String,Object> properties = new LinkedHashMap<>();
    for (final Member member : members.values())
      properties.put(member.name, member.toJson(settings, this, packageName));

    element.put("jsd:properties", properties);
    return element;
  }

  @Override
  void toAnnotationAttributes(final AttributeMap attributes, final Member owner) {
    super.toAnnotationAttributes(attributes, owner);
    if (owner instanceof ArrayModel)
      attributes.put("type", classType().getCanonicalName() + ".class");
  }

  @Override
  List<AnnotationType> getClassAnnotation() {
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

    final Registry.Type type = classType();
    if (members.size() > 0)
      builder.append("\n\n");

    builder.append('@').append(Override.class.getName());
    builder.append("\npublic boolean equals(final ").append(Object.class.getName()).append(" obj) {");
    builder.append("\n  if (obj == this)");
    builder.append("\n    return true;");
    builder.append("\n\n  if (!(obj instanceof ").append(type.getCanonicalName()).append(')').append((type.getSuperType() != null ? " || !super.equals(obj)" : "")).append(')');
    builder.append("\n    return false;\n");
    if (members != null && members.size() > 0) {
      builder.append("\n  final ").append(type.getCanonicalName()).append(" that = (").append(type.getCanonicalName()).append(")obj;");
      for (final Member member : members.values()) {
        final String instanceName = JsdUtil.toInstanceName(member.name);
        builder.append("\n  if (that.").append(instanceName).append(" != null ? !that.").append(instanceName).append(".equals(").append(instanceName).append(") : ").append(instanceName).append(" != null)");
        builder.append("\n    return false;\n");
      }
    }
    builder.append("\n  return true;");
    builder.append("\n}");

    builder.append("\n\n@").append(Override.class.getName());
    builder.append("\npublic int hashCode() {");
    if (members != null && members.size() > 0) {
      builder.append("\n  int hashCode = ").append(type.getName().hashCode()).append((type.getSuperType() != null ? " * 31 + super.hashCode()" : "")).append(';');
      for (final Member member : members.values()) {
        final String instanceName = JsdUtil.toInstanceName(member.name);
        builder.append("\n  hashCode = 31 * hashCode + (").append(instanceName).append(" == null ? 0 : ").append(instanceName).append(".hashCode());");
      }

      builder.append("\n  return hashCode;");
    }
    else {
      builder.append("\n  return ").append(type.getName().hashCode()).append((type.getSuperType() != null ? " * 31 + super.hashCode()" : "")).append(';');
    }
    builder.append("\n}");

    builder.append("\n\n@").append(Override.class.getName());
    builder.append("\npublic ").append(String.class.getName()).append(" toString() {");
    builder.append("\n  return ").append(JxEncoder.class.getName()).append(".get().marshal(this);");
    builder.append("\n}");

    return builder.toString();
  }
}