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
import java.util.function.Function;

import org.lib4j.lang.AnnotationParameterException;
import org.lib4j.lang.IllegalAnnotationException;
import org.lib4j.util.Iterators;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Array;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$ArrayMember;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Member;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.Jsonx;
import org.libx4j.jsonx.runtime.ArrayElement;
import org.libx4j.jsonx.runtime.ArrayProperty;
import org.libx4j.jsonx.runtime.BooleanElement;
import org.libx4j.jsonx.runtime.NumberElement;
import org.libx4j.jsonx.runtime.ObjectElement;
import org.libx4j.jsonx.runtime.StringElement;
import org.libx4j.xsb.runtime.Bindings;

class ArrayModel extends ComplexModel {
  public static ArrayModel declare(final Registry registry, final Jsonx.Array binding) {
    return registry.declare(binding).value(new ArrayModel(registry, binding), null);
  }

  public static Member referenceOrDeclare(final Registry registry, final ComplexModel referrer, final ArrayProperty property, final Field field) {
    final ArrayModel model = new ArrayModel(registry, property, field);
    final Id id = model.id();

    final ArrayModel registered = (ArrayModel)registry.getElement(id);
    return new Template(registry, getName(property.name(), field), property.nullable(), property.required(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  private static Member referenceOrDeclare(final Registry registry, final ComplexModel referrer, final ArrayElement element, final Field field, final Map<Integer,Annotation> annotations) {
    final ArrayModel model = new ArrayModel(registry, element, field, annotations);
    final Id id = model.id();

    final ArrayModel registered = (ArrayModel)registry.getElement(id);
    return new Template(registry, element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  public static ArrayModel reference(final Registry registry, final ComplexModel referrer, final $Array.Array binding) {
    return registry.reference(new ArrayModel(registry, binding), referrer);
  }

  public static ArrayModel reference(final Registry registry, final ComplexModel referrer, final $Array binding) {
    return registry.reference(new ArrayModel(registry, binding), referrer);
  }

  protected static Registry.Type getGreatestCommonSuperType(final Registry registry, final List<Member> elements) {
    if (elements.size() == 0)
      return registry.getType(Void.class);

    if (elements.size() == 1)
      return registry.getType(List.class, elements.get(0).type());

    Registry.Type gcc = elements.get(0).type().getGreatestCommonSuperType(elements.get(1).type());
    for (int i = 2; i < elements.size() && gcc != null; i++)
      gcc = gcc.getGreatestCommonSuperType(elements.get(i).type());

    return registry.getType(List.class, gcc);
  }

  private static void writeElementIdsClause(final AttributeMap attributes, final int[] indices) {
    final StringBuilder builder = new StringBuilder("{");
    if (indices.length == 0) {
      builder.append("}");
    }
    else {
      for (int i = 0; i < indices.length - 1; i++)
        builder.append(indices[i]).append(", ");

      builder.append(indices[indices.length - 1]).append('}');
    }

    attributes.put("elementIds", builder);
  }

  private static List<Member> parseMembers(final Registry registry, final ArrayModel referrer, final $ArrayMember binding) {
    final List<Member> members = new ArrayList<>();
    final Iterator<? super $Member> iterator = Iterators.filter(binding.elementIterator(), m -> $Member.class.isInstance(m));
    while (iterator.hasNext()) {
      final $Member member = ($Member)iterator.next();
      if (member instanceof $Array.Boolean) {
        members.add(BooleanModel.reference(registry, referrer, ($Array.Boolean)member));
      }
      else if (member instanceof $Array.Number) {
        members.add(NumberModel.reference(registry, referrer, ($Array.Number)member));
      }
      else if (member instanceof $Array.String) {
        members.add(StringModel.reference(registry, referrer, ($Array.String)member));
      }
      else if (member instanceof $Array.Array) {
        members.add(ArrayModel.reference(registry, referrer, ($Array.Array)member));
      }
      else if (member instanceof $Array.Object) {
        members.add(ObjectModel.reference(registry, referrer, ($Array.Object)member));
      }
      else if (member instanceof $Array.Template) {
        final $Array.Template template = ($Array.Template)member;
        final Member reference = registry.getElement(new Id(template.getReference$()));
        if (reference == null)
          throw new IllegalStateException("Template reference=\"" + template.getReference$().text() + "\" in array not found");

        members.add(new Template(registry, template, registry.reference(reference, referrer)));
      }
      else {
        throw new UnsupportedOperationException("Unsupported " + member.getClass().getSimpleName() + " member type: " + member.getClass().getName());
      }
    }

    return Collections.unmodifiableList(members);
  }

  private static List<Member> parseMembers(final Registry registry, final ArrayModel referrer, final Field field, final Annotation annotation, final int[] elementIds, final Map<Integer,Annotation> annotations) {
    final List<Member> elements = new ArrayList<>();
    for (final Integer elementId : elementIds) {
      final Annotation elementAnnotation = annotations.get(elementId);
      if (elementAnnotation == null)
        throw new AnnotationParameterException(annotation, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + annotation.annotationType().getName() + " specifies non-existent element with id=" + elementId);

      if (elementAnnotation instanceof BooleanElement)
        elements.add(BooleanModel.referenceOrDeclare(registry, referrer, (BooleanElement)elementAnnotation));
      else if (elementAnnotation instanceof NumberElement)
        elements.add(NumberModel.referenceOrDeclare(registry, referrer, (NumberElement)elementAnnotation));
      else if (elementAnnotation instanceof StringElement)
        elements.add(StringModel.referenceOrDeclare(registry, referrer, (StringElement)elementAnnotation));
      else if (elementAnnotation instanceof ObjectElement)
        elements.add(ObjectModel.referenceOrDeclare(registry, referrer, (ObjectElement)elementAnnotation));
      else if (elementAnnotation instanceof ArrayElement)
        elements.add(ArrayModel.referenceOrDeclare(registry, referrer, (ArrayElement)elementAnnotation, field, annotations));
    }

    return Collections.unmodifiableList(elements);
  }

  private static final Function<Annotation,Integer> reference = new Function<>() {
    @Override
    public Integer apply(final Annotation t) {
      if (t instanceof BooleanElement)
        return ((BooleanElement)t).id();

      if (t instanceof NumberElement)
        return ((NumberElement)t).id();

      if (t instanceof StringElement)
        return ((StringElement)t).id();

      if (t instanceof ArrayElement)
        return ((ArrayElement)t).id();

      if (t instanceof ObjectElement)
        return ((ObjectElement)t).id();

      throw new UnsupportedOperationException("Unsupported Annotation type: " + t.getClass().getName());
    }
  };

  private static void addElementAnnotation(final Map<Integer,Annotation> map, final StrictDigraph<Integer> digraph, final Annotation annotation, final int id, final Field field) {
    if (map.containsKey(id))
      throw new AnnotationParameterException(annotation, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + BooleanElement.class.getName() + "(id=" + id + ") cannot share the same id value with another @*Element(id=?) annotation on the same field.");

    digraph.addVertex(reference.apply(annotation));
    map.put(id, annotation);
  }

  private final Id id;
  private final List<Member> members;

  private ArrayModel(final Registry registry, final Jsonx.Array binding) {
    super(registry, null);
    this.members = parseMembers(registry, this, binding);
    this.id = new Id(binding.getTemplate$());
  }

  private ArrayModel(final Registry registry, final $Array binding) {
    super(registry, binding.getName$(), binding.getNullable$(), binding.getRequired$());
    this.members = parseMembers(registry, this, binding);
    this.id = new Id(this);
  }

  private ArrayModel(final Registry registry, final $Array.Array binding) {
    super(registry, binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    if (this.maxOccurs() != null && this.minOccurs() != null && this.minOccurs() > this.maxOccurs())
      throw new ValidationException(Bindings.getXPath(binding, elementXPath) + ": minOccurs=\"" + this.minOccurs() + "\" > maxOccurs=\"" + this.maxOccurs() + "\"");

    this.members = parseMembers(registry, this, binding);
    this.id = new Id(this);
  }

  private ArrayModel(final Registry registry, final ArrayProperty property, final Field field) {
    super(registry, property.nullable());
    if (field.getType() != List.class && !field.getType().isArray() && !Void.class.equals(field.getType()))
      throw new IllegalAnnotationException(property, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + ArrayProperty.class.getSimpleName() + " can only be applied to fields of array or List types.");

    final Map<Integer,Annotation> map = new HashMap<>();
    final StrictDigraph<Integer> digraph = new StrictDigraph<>("Element cannot include itself as a member");
    final BooleanElement[] booleanElements = field.getAnnotationsByType(BooleanElement.class);
    if (booleanElements != null)
      for (final BooleanElement booleanElement : booleanElements)
        addElementAnnotation(map, digraph, booleanElement, booleanElement.id(), field);

    final NumberElement[] numberElements = field.getAnnotationsByType(NumberElement.class);
    if (numberElements != null)
      for (final NumberElement numberElement : numberElements)
        addElementAnnotation(map, digraph, numberElement, numberElement.id(), field);

    final StringElement[] stringElements = field.getAnnotationsByType(StringElement.class);
    if (stringElements != null)
      for (final StringElement stringElement : stringElements)
        addElementAnnotation(map, digraph, stringElement, stringElement.id(), field);

    final ObjectElement[] objectElements = field.getAnnotationsByType(ObjectElement.class);
    if (objectElements != null)
      for (final ObjectElement objectElement : objectElements)
        addElementAnnotation(map, digraph, objectElement, objectElement.id(), field);

    final ArrayElement[] arrayElements = field.getAnnotationsByType(ArrayElement.class);
    if (arrayElements != null) {
      for (final ArrayElement arrayElement : arrayElements) {
        addElementAnnotation(map, digraph, arrayElement, arrayElement.id(), field);
        for (final Integer arrayElementId : arrayElement.elementIds())
          digraph.addEdge(arrayElement.id(), arrayElementId);
      }
    }

    final List<Integer> cycle = digraph.getCycle();
    if (cycle != null)
      throw new ValidationException("Cycle detected in element index dependency graph: " + org.lib4j.util.Collections.toString(digraph.getCycle(), " -> "));

    final LinkedHashMap<Integer,Annotation> topologicalOrder = new LinkedHashMap<>(map.size());
    for (final Integer elementId : digraph.getTopologicalOrder())
      topologicalOrder.put(elementId, map.get(elementId));

    this.members = parseMembers(registry, this, field, property, property.elementIds(), topologicalOrder);
    this.id = new Id(this);
  }

  private ArrayModel(final Registry registry, final ArrayElement element, final Field field, final Map<Integer,Annotation> annotations) {
    super(registry, element.nullable());
    this.members = parseMembers(registry, this, field, element, element.elementIds(), annotations);
    this.id = new Id(this);
  }

  @Override
  public final Id id() {
    return id;
  }

  public final List<Member> members() {
    return this.members;
  }

  @Override
  protected final Registry.Type type() {
    return getGreatestCommonSuperType(registry, members);
  }

  @Override
  protected final Class<? extends Annotation> propertyAnnotation() {
    return ArrayProperty.class;
  }

  @Override
  protected final Class<? extends Annotation> elementAnnotation() {
    return ArrayElement.class;
  }

  @Override
  protected final void getDeclaredTypes(final Set<Registry.Type> types) {
    if (members != null)
      for (final Member member : members)
        member.getDeclaredTypes(types);
  }

  private List<org.lib4j.xml.Element> membersToXml(final String packageName) {
    if (members.size() == -1)
      return null;

    final List<org.lib4j.xml.Element> elements = new ArrayList<>();
    for (final Member member : this.members)
      elements.add(member.toXml(this, packageName));

    return elements;
  }

  @Override
  protected final org.lib4j.xml.Element toXml(final Element owner, final String packageName) {
    final Map<String,String> attributes = super.toAttributes(owner, packageName);
    if (!(owner instanceof ObjectModel))
      return new org.lib4j.xml.Element("array", attributes, membersToXml(packageName));

    final List<org.lib4j.xml.Element> elements;
    if (registry.isRegistered(id())) {
      attributes.put("xsi:type", "template");
      attributes.put("reference", id().toString());
      elements = null;
    }
    else {
      attributes.put("xsi:type", "array");
      elements = membersToXml(packageName);
    }

    return new org.lib4j.xml.Element("property", attributes, elements);
  }

  @Override
  protected void toAnnotation(final AttributeMap attributes) {
    super.toAnnotation(attributes);
    final int[] indices = new int[members().size()];
    final StringBuilder temp = new StringBuilder();
    int index = 0;
    for (int i = 0; i < members().size(); i++) {
      indices[i] = index;
      index += writeElementAnnotations(temp, members().get(i), index);
    }

    writeElementIdsClause(attributes, indices);
  }

  protected final void toAnnotation(final AttributeMap attributes, final int ... indices) {
    super.toAnnotation(attributes);
    writeElementIdsClause(attributes, indices);
  }

  private static int writeElementAnnotations(final StringBuilder builder, final Member member, final int index) {
    final AttributeMap attributes = new AttributeMap();
    attributes.put("id", index);

    final Member reference = member instanceof Template ? ((Template)member).reference() : member;
    if (reference instanceof ArrayModel) {
      final ArrayModel arrayModel = (ArrayModel)reference;
      int offset = 1;
      final StringBuilder inner = new StringBuilder();
      final int[] indices = new int[arrayModel.members().size()];
      for (int i = 0; i < arrayModel.members().size(); i++) {
        indices[i] = index + offset;
        offset += writeElementAnnotations(inner, arrayModel.members().get(i), index + offset);
      }

      // FIXME: Can this be abstracted better? minOccurs, maxOccurs and nullable are rendered here and in Element.toAnnotation()
      arrayModel.toAnnotation(attributes, indices);
      if (member.minOccurs() != null)
        attributes.put("minOccurs", member.minOccurs());

      if (member.maxOccurs() != null)
        attributes.put("maxOccurs", member.maxOccurs());

      if (member.nullable() != null)
        attributes.put("nullable", member.nullable());

      builder.insert(0, "@" + member.elementAnnotation().getName() + "(" + attributes.toAnnotation() + ")\n");
      builder.insert(0, inner);
      return offset;
    }

    member.toAnnotation(attributes);
    builder.insert(0, "@" + member.elementAnnotation().getName() + "(" + attributes.toAnnotation() + ")\n");
    return 1;
  }

  @Override
  protected final String toElementAnnotations() {
    final StringBuilder builder = new StringBuilder();
    int index = 0;
    for (final Member member : members())
      index += writeElementAnnotations(builder, member, index);

    return builder.toString();
  }
}