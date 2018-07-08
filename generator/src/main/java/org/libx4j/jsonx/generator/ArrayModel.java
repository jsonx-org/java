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

  public static Element referenceOrDeclare(final Registry registry, final ComplexModel referrer, final ArrayProperty property, final Field field) {
    final ArrayModel model = new ArrayModel(registry, property, field);
    final Id id = model.id();

    final ArrayModel registered = (ArrayModel)registry.getElement(id);
    return new Template(getName(property.name(), field), property.nullable(), property.required(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  private static Element referenceOrDeclare(final Registry registry, final ComplexModel referrer, final ArrayElement element, final Field field, final Map<Integer,Annotation> annotations) {
    final ArrayModel model = new ArrayModel(registry, element, field, annotations);
    final Id id = model.id();

    if (id.toString().contains("af1e0f831")) {
      System.out.println();
    }

    final ArrayModel registered = (ArrayModel)registry.getElement(id);
    return new Template(element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  public static ArrayModel reference(final Registry registry, final ComplexModel referrer, final $Array.Array binding) {
    return registry.reference(new ArrayModel(registry, binding), referrer);
  }

  public static ArrayModel reference(final Registry registry, final ComplexModel referrer, final $Array binding) {
    return registry.reference(new ArrayModel(registry, binding), referrer);
  }

  static Type getGreatestCommonSuperType(final List<Element> elements) {
    if (elements.size() == 0)
      return Type.VOID;

    if (elements.size() == 1)
      return Type.get(List.class, elements.get(0).type());

    Type gcc = elements.get(0).type().getGreatestCommonSuperType(elements.get(1).type());
    for (int i = 2; i < elements.size() && gcc != Type.OBJECT; i++)
      gcc = gcc.getGreatestCommonSuperType(elements.get(i).type());

    return Type.get(List.class, gcc);
  }

  private static void writeElementIdsClause(final Attributes attributes, final int[] indices) {
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

  private static List<Element> parseMembers(final Registry registry, final ArrayModel referrer, final $ArrayMember binding) {
    final List<Element> members = new ArrayList<Element>();
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
      else if (member instanceof $ArrayMember.Template) {
        final $ArrayMember.Template template = ($ArrayMember.Template)member;
        final Element reference = registry.getElement(template.getReference$().text());
        if (reference == null)
          throw new IllegalStateException("Template reference=\"" + template.getReference$().text() + "\" in array not found");

        members.add(new Template(template, registry.reference(reference, referrer)));
      }
      else {
        throw new UnsupportedOperationException("Unsupported " + member.getClass().getSimpleName() + " member type: " + member.getClass().getName());
      }
    }

    return Collections.unmodifiableList(members);
  }

  private static List<Element> parseMembers(final Registry registry, final ArrayModel referrer, final Field field, final Annotation annotation, final int[] elementIds, final Map<Integer,Annotation> annotations) {
    final List<Element> elements = new ArrayList<Element>();
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

  private static final Function<Annotation,Integer> reference = new Function<Annotation,Integer>() {
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
  private final List<Element> members;

  private ArrayModel(final Registry registry, final Jsonx.Array binding) {
    super(null, binding.getNullable$().text(), null, null, null);
    this.members = parseMembers(registry, this, binding);
    this.id = new Id(binding.getTemplate$().text());
  }

  private ArrayModel(final Registry registry, final $Array binding) {
    super(binding.getName$().text(), binding.getNullable$().text(), binding.getRequired$().text());
    this.members = parseMembers(registry, this, binding);
    this.id = new Id(this);
  }

  private ArrayModel(final Registry registry, final $Array.Array binding) {
    super(binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    if (this.maxOccurs() != null && this.minOccurs() != null && this.minOccurs() > this.maxOccurs())
      throw new ValidationException(Bindings.getXPath(binding, elementXPath) + ": minOccurs=\"" + this.minOccurs() + "\" > maxOccurs=\"" + this.maxOccurs() + "\"");

    this.members = parseMembers(registry, this, binding);
    this.id = new Id(this);
  }

  private ArrayModel(final Registry registry, final ArrayProperty property, final Field field) {
    super(null, property.nullable(), null, null, null);
    if (field.getType() != List.class && !field.getType().isArray() && !Void.class.equals(field.getType()))
      throw new IllegalAnnotationException(property, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + ArrayProperty.class.getSimpleName() + " can only be applied to fields of array or List types.");

    final Map<Integer,Annotation> map = new HashMap<Integer,Annotation>();
    final StrictDigraph<Integer> digraph = new StrictDigraph<Integer>("Element cannot include itself as a member");
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

    final LinkedHashMap<Integer,Annotation> topologicalOrder = new LinkedHashMap<Integer,Annotation>(map.size());
    for (final Integer elementId : digraph.getTopologicalOrder())
      topologicalOrder.put(elementId, map.get(elementId));

    this.members = parseMembers(registry, this, field, property, property.elementIds(), topologicalOrder);
    this.id = new Id(this);
  }

  private ArrayModel(final Registry registry, final ArrayElement element, final Field field, final Map<Integer,Annotation> annotations) {
    super(null, element.nullable(), null, null, null);
    this.members = parseMembers(registry, this, field, element, element.elementIds(), annotations);
    this.id = new Id(this);
  }

  @Override
  public final Id id() {
    return id;
  }

  public final List<Element> members() {
    return this.members;
  }

  @Override
  protected final Type type() {
    return getGreatestCommonSuperType(members);
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
  protected final void collectClassNames(final List<Type> types) {
    if (members != null)
      for (final Element member : members)
        member.collectClassNames(types);
  }

  @Override
  protected final String toJSON(final String packageName) {
    final StringBuilder builder = new StringBuilder(super.toJSON(packageName));
    if (builder.length() > 0)
      builder.insert(0, ",\n");

    if (members != null) {
      builder.append(",\n  members: ");
      final StringBuilder members = new StringBuilder();
      for (final Element member : this.members)
        members.append(", ").append(member.toJSON(packageName).replace("\n", "\n  "));

      builder.append('[').append(members.length() > 0 ? members.substring(2) : members.toString()).append(']');
    }

    return "{\n" + (builder.length() > 0 ? builder.substring(2) : builder.toString()) + "\n}";
  }

  @Override
  protected final String toJSONX(final Registry registry, final Member owner, final String packageName) {
    final boolean skipMembers;
    final StringBuilder builder;
    if (owner instanceof ObjectModel) {
      builder = new StringBuilder("<property xsi:type=\"");
      if (skipMembers = registry.hasRegistry(id()))
        builder.append("template\" reference=\"").append(id()).append("\"");
      else
        builder.append("array\"");
    }
    else {
      builder = new StringBuilder("<array");
      skipMembers = false;
    }

    if (!skipMembers && members.size() > 0) {
      builder.append(super.toJSONX(registry, owner, packageName)).append('>');
      for (final Element member : this.members)
        builder.append("\n  ").append(member.toJSONX(registry, this, packageName).replace("\n", "\n  "));

      builder.append('\n');
      builder.append(owner instanceof ObjectModel ? "</property>" : "</array>");
    }
    else {
      builder.append(super.toJSONX(registry, owner, packageName)).append("/>");
    }

    return builder.toString();
  }

  @Override
  protected void toAnnotation(final Attributes attributes, final String packageName) {
    super.toAnnotation(attributes, packageName);
    final int[] indices = new int[members().size()];
    final StringBuilder temp = new StringBuilder();
    int index = 0;
    for (int i = 0; i < members().size(); i++) {
      indices[i] = index;
      index += writeElementAnnotations(packageName, temp, members().get(i), index);
    }

    writeElementIdsClause(attributes, indices);
  }

  protected final void toAnnotation(final String packageName, final Attributes attributes, final int ... indices) {
    super.toAnnotation(attributes, packageName);
    writeElementIdsClause(attributes, indices);
  }

  private static int writeElementAnnotations(final String packageName, final StringBuilder builder, final Element element, final int index) {
    final Attributes outer = new Attributes();
    outer.put("id", index);

    final Element reference = element instanceof Template ? ((Template)element).reference() : element;
    if (reference instanceof ArrayModel) {
      final ArrayModel arrayModel = (ArrayModel)reference;
      int offset = 1;
      final StringBuilder inner = new StringBuilder();
      final int[] indices = new int[arrayModel.members().size()];
      for (int i = 0; i < arrayModel.members().size(); i++) {
        indices[i] = index + offset;
        offset += writeElementAnnotations(packageName, inner, arrayModel.members().get(i), index + offset);
      }

      // FIXME: Can this be abstracted better? minOccurs and maxOccurs are rendered here and in Element.toAnnotation()
      arrayModel.toAnnotation(packageName, outer, indices);
      if (element.minOccurs() != null)
        outer.put("minOccurs", element.minOccurs());

      if (element.maxOccurs() != null)
        outer.put("maxOccurs", element.maxOccurs());

      builder.insert(0, "@" + element.elementAnnotation().getName() + "(" + outer.toAnnotation() + ")\n");
      builder.insert(0, inner);
      return offset;
    }

    element.toAnnotation(outer, packageName);
    builder.insert(0, "@" + element.elementAnnotation().getName() + "(" + outer.toAnnotation() + ")\n");
    return 1;
  }

  @Override
  protected final String toElementAnnotations(final String packageName) {
    final StringBuilder builder = new StringBuilder();
    int index = 0;
    for (final Element element : members())
      index += writeElementAnnotations(packageName, builder, element, index);

    return builder.toString();
  }
}