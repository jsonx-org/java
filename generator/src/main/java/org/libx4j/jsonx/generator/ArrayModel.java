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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lib4j.lang.AnnotationParameterException;
import org.lib4j.lang.IllegalAnnotationException;
import org.lib4j.util.Arrays;
import org.lib4j.util.Iterators;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Array;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$ArrayMember;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Member;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.Jsonx;
import org.libx4j.jsonx.runtime.ArrayElement;
import org.libx4j.jsonx.runtime.ArrayProperty;
import org.libx4j.jsonx.runtime.ArrayType;
import org.libx4j.jsonx.runtime.BooleanElement;
import org.libx4j.jsonx.runtime.JsonxUtil;
import org.libx4j.jsonx.runtime.NumberElement;
import org.libx4j.jsonx.runtime.ObjectElement;
import org.libx4j.jsonx.runtime.StringElement;
import org.libx4j.jsonx.runtime.ValidationException;
import org.libx4j.xsb.runtime.Bindings;

final class ArrayModel extends Referrer<ArrayModel> {
  public static ArrayModel declare(final Registry registry, final Jsonx.Array binding) {
    return registry.declare(binding).value(new ArrayModel(registry, binding), null);
  }

  private static ArrayModel referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final Annotation annotation, final Class<? extends Annotation> annotationType, final Id id, final Registry.Type type) {
    final ArrayModel registered = (ArrayModel)registry.getModel(id);
    if (registered != null)
      return registry.reference(registered, referrer);

    final ArrayType arrayType = annotationType.getAnnotation(ArrayType.class);
    if (arrayType == null)
      throw new IllegalAnnotationException(annotation, annotationType.getName() + " does not specify the required @" + ArrayType.class.getSimpleName() + " annotation");

    return registry.declare(id).value(new ArrayModel(registry, arrayType, arrayType.elementIds(), annotationType.getAnnotations(), type, annotationType.getName()), referrer);
  }

  public static ArrayModel referenceOrDeclare(final Registry registry, final Class<?> cls) {
    final Id id = new Id(cls);
    final ArrayModel model = (ArrayModel)registry.getModel(id);
    if (model != null)
      return registry.reference(model, null);

    final ArrayType arrayType = cls.getAnnotation(ArrayType.class);
    if (arrayType == null)
      throw new IllegalArgumentException("Class " + cls.getName() + " does not specify the @" + ArrayType.class.getSimpleName() + " annotation.");

    return registry.declare(id).value(new ArrayModel(registry, arrayType, arrayType.elementIds(), cls.getAnnotations(), registry.getType(cls), cls.getName()), null);
  }

  public static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final ArrayProperty property, final Field field) {
    if (ArrayType.class.equals(property.type())) {
      final ArrayModel model = new ArrayModel(registry, property, property.elementIds(), field.getAnnotations(), null, field.getDeclaringClass().getName() + "." + field.getName());
      final Id id = model.id();

      final ArrayModel registered = (ArrayModel)registry.getModel(id);
      return new Reference(registry, JsonxUtil.getName(property.name(), field), property.use(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
    }

    final Registry.Type type = registry.getType(property.type());
    final Id id = new Id(type);

    return new Reference(registry, JsonxUtil.getName(property.name(), field), property.use(), referenceOrDeclare(registry, referrer, property, property.type(), id, type));
  }

  private static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final ArrayElement element, final Map<Integer,Annotation> idToElement, final String declaringTypeName) {
    if (ArrayType.class.equals(element.type())) {
      final ArrayModel model = new ArrayModel(registry, element, idToElement, declaringTypeName);
      final Id id = model.id();

      final ArrayModel registered = (ArrayModel)registry.getModel(id);
      return new Reference(registry, element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
    }

    final Registry.Type type = registry.getType(element.type());
    final Id id = new Id(type);

    return new Reference(registry, element.nullable(), element.minOccurs(), element.maxOccurs(), referenceOrDeclare(registry, referrer, element, element.type(), id, type));
  }

  public static ArrayModel reference(final Registry registry, final Referrer<?> referrer, final $Array.Array binding) {
    return registry.reference(new ArrayModel(registry, binding), referrer);
  }

  public static ArrayModel reference(final Registry registry, final Referrer<?> referrer, final $Array binding) {
    return registry.reference(new ArrayModel(registry, binding), referrer);
  }

  protected static Registry.Type getGreatestCommonSuperType(final Registry registry, final List<Member> members) {
    if (members.size() == 0)
      throw new IllegalArgumentException("members.size() == 0");

    if (members.size() == 1)
      return registry.getType(List.class, members.get(0).type());

    Registry.Type gct = members.get(0).type().getGreatestCommonSuperType(members.get(1).type());
    for (int i = 2; i < members.size() && gct != null; i++)
      gct = gct.getGreatestCommonSuperType(members.get(i).type());

    return registry.getType(List.class, gct);
  }

  private static void writeElementIdsClause(final AttributeMap attributes, final int[] indices) {
    attributes.put("elementIds", indices.length == 0 ? "{}" : "{" + Arrays.toString(indices, ", ") + "}");
  }

  private static List<Member> parseMembers(final Registry registry, final ArrayModel referrer, final $ArrayMember binding) {
    final List<Member> members = new ArrayList<>();
    final Iterator<? super $Member> iterator = Iterators.filter(binding.elementIterator(), m -> $Member.class.isInstance(m));
    while (iterator.hasNext()) {
      final $Member member = ($Member)iterator.next();
      if (member instanceof $Array.Array) {
        members.add(ArrayModel.reference(registry, referrer, ($Array.Array)member));
      }
      else if (member instanceof $Array.Boolean) {
        members.add(BooleanModel.reference(registry, referrer, ($Array.Boolean)member));
      }
      else if (member instanceof $Array.Number) {
        members.add(NumberModel.reference(registry, referrer, ($Array.Number)member));
      }
      else if (member instanceof $Array.String) {
        members.add(StringModel.reference(registry, referrer, ($Array.String)member));
      }
      else if (member instanceof $Array.Reference) {
        final $Array.Reference reference = ($Array.Reference)member;
        final Member model = registry.getModel(new Id(reference.getType$()));
        if (model == null)
          throw new IllegalStateException("Template type=\"" + reference.getType$().text() + "\" in array not found");

        members.add(new Reference(registry, reference, registry.reference(model, referrer)));
      }
      else {
        throw new UnsupportedOperationException("Unsupported " + member.getClass().getSimpleName() + " member type: " + member.getClass().getName());
      }
    }

    return Collections.unmodifiableList(members);
  }

  private static List<Member> parseMembers(final Registry registry, final ArrayModel referrer, final Annotation annotation, final int[] elementIds, final Map<Integer,Annotation> idToElement, final String declaringTypeName) {
    final List<Member> elements = new ArrayList<>();
    for (final Integer elementId : elementIds) {
      final Annotation elementAnnotation = idToElement.get(elementId);
      if (elementAnnotation == null)
        throw new AnnotationParameterException(annotation, declaringTypeName + ": @" + annotation.annotationType().getName() + " specifies non-existent element with id=" + elementId);

      if (elementAnnotation instanceof BooleanElement)
        elements.add(BooleanModel.referenceOrDeclare(registry, referrer, (BooleanElement)elementAnnotation));
      else if (elementAnnotation instanceof NumberElement)
        elements.add(NumberModel.referenceOrDeclare(registry, referrer, (NumberElement)elementAnnotation));
      else if (elementAnnotation instanceof StringElement)
        elements.add(StringModel.referenceOrDeclare(registry, referrer, (StringElement)elementAnnotation));
      else if (elementAnnotation instanceof ObjectElement)
        elements.add(ObjectModel.referenceOrDeclare(registry, referrer, (ObjectElement)elementAnnotation));
      else if (elementAnnotation instanceof ArrayElement)
        elements.add(ArrayModel.referenceOrDeclare(registry, referrer, (ArrayElement)elementAnnotation, idToElement, declaringTypeName));
    }

    return Collections.unmodifiableList(elements);
  }

  private final Id id;
  private final Registry.Type type;
  private final List<Member> members;

  private ArrayModel(final Registry registry, final Jsonx.Array binding) {
    super(registry);
    this.members = parseMembers(registry, this, binding);
    if (binding.getClass$() != null) {
      this.type = registry.getType(Registry.Kind.ANNOTATION, (String)binding.owner().getPackage$().text(), binding.getClass$().text());
      this.id = new Id(binding.getClass$());
    }
    else {
      this.type = null;
      this.id = new Id(binding.getTemplate$());
    }
  }

  private ArrayModel(final Registry registry, final $Array binding) {
    super(registry, binding.getName$(), binding.getUse$());
    this.type = null;
    this.members = parseMembers(registry, this, binding);

    this.id = new Id(this);
  }

  private ArrayModel(final Registry registry, final $Array.Array binding) {
    super(registry, binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    if (this.maxOccurs() != null && this.minOccurs() != null && this.minOccurs() > this.maxOccurs())
      throw new ValidationException(Bindings.getXPath(binding, elementXPath) + ": minOccurs=\"" + this.minOccurs() + "\" > maxOccurs=\"" + this.maxOccurs() + "\"");

    this.type = null;
    this.members = parseMembers(registry, this, binding);

    this.id = new Id(this);
  }

  private ArrayModel(final Registry registry, final Annotation annotation, final int[] elementIds, final Annotation[] annotations, final Registry.Type type, final String declaringTypeName) {
    super(registry);
    final Map<Integer,Annotation> idToElement = new HashMap<>();
    final StrictDigraph<Integer> digraph = new StrictDigraph<>("Element cannot include itself as a member");
    for (final Annotation elementAnnotation : JsonxUtil.flatten(annotations)) {
      if (elementAnnotation instanceof ArrayProperty || elementAnnotation instanceof ArrayType)
        continue;

      final int id;
      if (elementAnnotation instanceof ArrayElement) {
        id = ((ArrayElement)elementAnnotation).id();
        for (final Integer elementId : ((ArrayElement)elementAnnotation).elementIds())
          digraph.addEdge(id, elementId);
      }
      else if (elementAnnotation instanceof BooleanElement) {
        id = ((BooleanElement)elementAnnotation).id();
      }
      else if (elementAnnotation instanceof NumberElement) {
        id = ((NumberElement)elementAnnotation).id();
      }
      else if (elementAnnotation instanceof ObjectElement) {
        id = ((ObjectElement)elementAnnotation).id();
      }
      else if (elementAnnotation instanceof StringElement) {
        id = ((StringElement)elementAnnotation).id();
      }
      else {
        throw new UnsupportedOperationException("Unsupported annotation type: " + elementAnnotation.getClass().getName());
      }

      if (idToElement.containsKey(id))
        throw new AnnotationParameterException(elementAnnotation, declaringTypeName + ": @" + elementAnnotation.annotationType().getName() + "(id=" + id + ") cannot share the same id value with another @*Element(id=?) annotation on the same field");

      digraph.addVertex(id);
      idToElement.put(id, elementAnnotation);
    }

    final List<Integer> cycle = digraph.getCycle();
    if (cycle != null)
      throw new ValidationException("Cycle detected in element index dependency graph: " + org.lib4j.util.Collections.toString(digraph.getCycle(), " -> "));

    final LinkedHashMap<Integer,Annotation> topologicalOrder = new LinkedHashMap<>(idToElement.size());
    for (final Integer elementId : digraph.getTopologicalOrder())
      topologicalOrder.put(elementId, idToElement.get(elementId));

    this.type = type;
    this.members = parseMembers(registry, this, annotation, elementIds, topologicalOrder, declaringTypeName);
    this.id = type != null ? new Id(type) : new Id(this);
  }

  private ArrayModel(final Registry registry, final ArrayElement element, final Map<Integer,Annotation> idToElement, final String declaringTypeName) {
    super(registry, element.nullable(), null);
    if (element.type() != ArrayType.class)
      throw new IllegalArgumentException("This constructor is only for elementIds");

    this.type = null;
    this.members = parseMembers(registry, this, element, element.elementIds(), idToElement, declaringTypeName);
    this.id = new Id(this);
  }

  private ArrayModel(final Registry registry, final ArrayProperty property, final Map<Integer,Annotation> idToElement, final String declaringTypeName) {
    super(registry, null, property.use());
    if (property.type() != ArrayType.class)
      throw new IllegalArgumentException("This constructor is only for elementIds");

    this.type = null;
    this.members = parseMembers(registry, this, property, property.elementIds(), idToElement, declaringTypeName);
    this.id = new Id(this);
  }

  @Override
  protected Id id() {
    return id;
  }

  public List<Member> members() {
    return this.members;
  }

  @Override
  protected Registry.Type type() {
    return getGreatestCommonSuperType(registry, members);
  }

  @Override
  protected Registry.Type classType() {
    return type;
  }

  @Override
  protected String elementName() {
    return "array";
  }

  @Override
  protected Class<? extends Annotation> propertyAnnotation() {
    return ArrayProperty.class;
  }

  @Override
  protected Class<? extends Annotation> elementAnnotation() {
    return ArrayElement.class;
  }

  @Override
  protected void getDeclaredTypes(final Set<Registry.Type> types) {
    if (type != null)
      types.add(type);

    if (members != null)
      for (final Member member : members)
        member.getDeclaredTypes(types);
  }

  @Override
  protected Map<String,String> toXmlAttributes(final Element owner, final String packageName) {
    final Map<String,String> attributes = super.toXmlAttributes(owner, packageName);
    if (owner instanceof Schema) {
      if (type != null)
        attributes.put("class", type.getSubName(packageName));
      else
        attributes.put("template", id().toString());
    }

    return attributes;
  }

  @Override
  protected org.lib4j.xml.Element toXml(final Settings settings, final Element owner, final String packageName) {
    final org.lib4j.xml.Element element = super.toXml(settings, owner, packageName);
    if (owner instanceof ObjectModel && registry.isTemplateReference(this, settings) || members.size() == 0)
      return element;

    final List<org.lib4j.xml.Element> elements = new ArrayList<>();
    for (final Member member : members)
      elements.add(member.toXml(settings, this, packageName));

    element.setElements(elements);
    return element;
  }

  private void renderAnnotations(final AttributeMap attributes, final List<AnnotationSpec> annotations) {
    final int[] indices = new int[members.size()];
    int index = 0;
    for (int i = 0; i < members.size(); i++) {
      indices[i] = index;
      index += writeElementAnnotations(annotations, members.get(i), index, this);
    }

    writeElementIdsClause(attributes, indices);
  }

  @Override
  protected void toAnnotationAttributes(final AttributeMap attributes, final Member owner) {
    super.toAnnotationAttributes(attributes, owner);
    if (type != null)
      attributes.put("type", type.getCanonicalName() + ".class");
    else
      renderAnnotations(attributes, new ArrayList<>());
  }

  private void toAnnotationAttributes(final AttributeMap attributes, final int[] indices, final Member owner) {
    super.toAnnotationAttributes(attributes, owner);
    writeElementIdsClause(attributes, indices);
  }

  private static int writeElementAnnotations(final List<AnnotationSpec> annotations, final Member member, final int index, final Member owner) {
    final AttributeMap attributes = new AttributeMap();
    attributes.put("id", index);

    final AnnotationSpec annotationSpec = new AnnotationSpec(member.elementAnnotation(), attributes);
    final Member reference = member instanceof Reference ? ((Reference)member).reference() : member;
    if (reference instanceof ArrayModel) {
      final ArrayModel arrayModel = (ArrayModel)reference;
      int offset = 1;
      final List<AnnotationSpec> inner = new ArrayList<>();
      final int[] indices = new int[arrayModel.members().size()];
      for (int i = 0; i < arrayModel.members().size(); i++) {
        indices[i] = index + offset;
        offset += writeElementAnnotations(inner, arrayModel.members().get(i), index + offset, owner);
      }

      // FIXME: Can this be abstracted better? minOccurs, maxOccurs and nullable are rendered here and in Element.toAnnotation()
      arrayModel.toAnnotationAttributes(attributes, indices, owner);
      if (member.minOccurs() != null)
        attributes.put("minOccurs", member.minOccurs());

      if (member.maxOccurs() != null)
        attributes.put("maxOccurs", member.maxOccurs());

      if (member.nullable() != null)
        attributes.put("nullable", member.nullable());

      inner.add(annotationSpec);
      annotations.addAll(0, inner);
      return offset;
    }

    member.toAnnotationAttributes(attributes, owner);
    annotations.add(0, annotationSpec);
    return 1;
  }

  @Override
  protected List<AnnotationSpec> toElementAnnotations() {
    if (type != null)
      return null;

    final List<AnnotationSpec> annotations = new ArrayList<>();
    int index = 0;
    for (final Member member : members)
      index += writeElementAnnotations(annotations, member, index, this);

    return annotations;
  }

  @Override
  protected List<AnnotationSpec> getClassAnnotation() {
    final AttributeMap attributes = new AttributeMap();
    final List<AnnotationSpec> annotations = new ArrayList<>();
    renderAnnotations(attributes, annotations);
    annotations.add(new AnnotationSpec(ArrayType.class, attributes));
    return annotations;
  }

  @Override
  protected String toSource() {
    return null;
  }
}