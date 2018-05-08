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
  public static ArrayModel declare(final Schema schema, final Registry registry, final Jsonx.Array binding) {
    return registry.declare(binding).value(new ArrayModel(schema, registry, binding, binding.getDoc$() == null ? null : binding.getDoc$().text()), null);
  }

  public static ArrayModel referenceOrDeclare(final Member owner, final Registry registry, final ArrayProperty arrayProperty, final Field field) {
    return new ArrayModel(owner, registry, arrayProperty, field);
  }

  private static ArrayModel referenceOrDeclare(final Member owner, final Registry registry, final ArrayElement arrayElement, final Field field, final Map<Integer,Annotation> annotations) {
    return new ArrayModel(owner, registry, arrayElement, field, annotations);
  }

  public static ArrayModel reference(final Member owner, final Registry registry, final $Array.Array binding) {
    return new ArrayModel(owner, registry, binding);
  }

  public static ArrayModel reference(final Member owner, final Registry registry, final $Array binding) {
    return new ArrayModel(owner, registry, binding);
  }

  static Type getGreatestCommonSuperType(final List<Element> elements) {
    if (elements.size() == 0)
      throw new IllegalArgumentException("elements.size() == 0");

    if (elements.size() == 1)
      return Type.get(List.class, elements.get(0).type());

    Type gcc = elements.get(0).type().getGreatestCommonSuperType(elements.get(1).type());
    for (int i = 2; i < elements.size() && gcc != Type.OBJECT; i++)
      gcc = gcc.getGreatestCommonSuperType(elements.get(i).type());

    return Type.get(List.class, gcc);
  }

  private static StringBuilder writeElementIdsClause(final StringBuilder builder, final int[] indices) {
    builder.append("elementIds={");
    if (indices.length == 0)
      return builder.append("}");

    for (int i = 0; i < indices.length - 1; i++)
      builder.append(indices[i]).append(", ");

    return builder.append(indices[indices.length - 1]).append('}');
  }

  private static List<Element> parseMembers(final Registry registry, final ArrayModel referrer, final $ArrayMember binding) {
    final List<Element> members = new ArrayList<Element>();
    final Iterator<? super $Member> iterator = Iterators.filter(binding.elementIterator(), m -> $Member.class.isInstance(m));
    while (iterator.hasNext()) {
      final $Member member = ($Member)iterator.next();
      if (member instanceof $Array.Boolean) {
        members.add(BooleanModel.reference(referrer, ($Array.Boolean)member));
      }
      else if (member instanceof $Array.Number) {
        members.add(NumberModel.reference(referrer, ($Array.Number)member));
      }
      else if (member instanceof $Array.String) {
        members.add(StringModel.reference(referrer, ($Array.String)member));
      }
      else if (member instanceof $Array.Array) {
        members.add(ArrayModel.reference(referrer, registry, ($Array.Array)member));
      }
      else if (member instanceof $Array.Object) {
        members.add(ObjectModel.reference(registry, referrer, ($Array.Object)member));
      }
      else if (member instanceof $ArrayMember.Template) {
        final $ArrayMember.Template template = ($ArrayMember.Template)member;
        final Element element = registry.getElement(template.getReference$().text());
        if (element == null)
          throw new IllegalStateException("Template reference=\"" + template.getReference$().text() + "\" in array not found");

        members.add(element instanceof Model ? new Template(referrer, template, (Model)element) : element);
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
        elements.add(BooleanModel.referenceOrDeclare(referrer, (BooleanElement)elementAnnotation));
      else if (elementAnnotation instanceof NumberElement)
        elements.add(NumberModel.referenceOrDeclare(referrer, (NumberElement)elementAnnotation));
      else if (elementAnnotation instanceof StringElement)
        elements.add(StringModel.referenceOrDeclare(referrer, (StringElement)elementAnnotation));
      else if (elementAnnotation instanceof ObjectElement)
        elements.add(ObjectModel.referenceOrDeclare(registry, referrer, (ObjectElement)elementAnnotation));
      else if (elementAnnotation instanceof ArrayElement)
        elements.add(ArrayModel.referenceOrDeclare(referrer, registry, (ArrayElement)elementAnnotation, field, annotations));
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

      throw new UnsupportedOperationException("Unsupported Annotation type: " + t.getClass());
    }
  };

  private static void addElementAnnotation(final Map<Integer,Annotation> map, final StrictDigraph<Integer> digraph, final Annotation annotation, final int id, final Field field) {
    if (map.containsKey(id))
      throw new AnnotationParameterException(annotation, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + BooleanElement.class.getName() + "(id=" + id + ") cannot share the same id value with another @*Element(id=?) annotation on the same field.");

    digraph.addVertex(reference.apply(annotation));
    map.put(id, annotation);
  }

  private ArrayModel(final Schema schema, final Registry registry, final Jsonx.Array binding, final String doc) {
    super(schema, binding.getTemplate$().text(), binding.getNullable$().text(), null, null, null, doc);
    this.members = parseMembers(registry, this, binding);
  }

  private ArrayModel(final Member owner, final Registry registry, final $Array binding) {
    super(owner, binding, binding.getName$().text(), binding.getNullable$().text(), binding.getRequired$().text());
    this.members = parseMembers(registry, this, binding);
  }

  private ArrayModel(final Member owner, final Registry registry, final $Array.Array binding) {
    super(owner, binding, binding.getNullable$().text(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    if (this.maxOccurs() != null && this.minOccurs() > this.maxOccurs())
      throw new ValidationException(Bindings.getXPath(binding, elementXPath) + ": minOccurs=\"" + this.minOccurs() + "\" > maxOccurs=\"" + this.maxOccurs() + "\"");

    this.members = parseMembers(registry, this, binding);
  }

  private ArrayModel(final Member owner, final Registry registry, final ArrayProperty arrayProperty, final Field field) {
    // FIXME: Can we get doc comments from code?
    super(owner, getName(arrayProperty.name(), field), arrayProperty.nullable(), arrayProperty.required(), null, null, null);
    if (field.getType() != List.class && !field.getType().isArray())
      throw new IllegalAnnotationException(arrayProperty, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + ArrayProperty.class.getSimpleName() + " can only be applied to fields of array or List types.");

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

    this.members = parseMembers(registry, this, field, arrayProperty, arrayProperty.elementIds(), topologicalOrder);
  }

  private final List<Element> members;

  private ArrayModel(final Member owner, final Registry registry, final ArrayElement arrayElement, final Field field, final Map<Integer,Annotation> annotations) {
    // FIXME: Can we get doc comments from code?
    super(owner, null, arrayElement.nullable(), null, arrayElement.minOccurs(), arrayElement.maxOccurs(), null);
    this.members = parseMembers(registry, this, field, arrayElement, arrayElement.elementIds(), annotations);
  }

  private ArrayModel(final Element element, final List<Element> members) {
    super(element);
    this.members = Collections.unmodifiableList(members);
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
  protected final ArrayModel normalize(final Registry registry) {
    return new ArrayModel(this, normalize(registry, members));
  }

  @Override
  protected ArrayModel merge(final Template reference) {
    return new ArrayModel(reference, members);
  }

  @Override
  protected final void collectClassNames(final List<Type> types) {
    if (members != null)
      for (final Element member : members)
        member.collectClassNames(types);
  }

  @Override
  protected final String toJSON(final String pacakgeName) {
    final StringBuilder builder = new StringBuilder(super.toJSON(pacakgeName));
    if (builder.length() > 0)
      builder.insert(0, ",\n");

    if (members != null) {
      builder.append(",\n  members: ");
      final StringBuilder members = new StringBuilder();
      for (final Element member : this.members)
        members.append(", ").append(member.toJSON(pacakgeName).replace("\n", "\n  "));

      builder.append('[').append(members.length() > 0 ? members.substring(2) : members.toString()).append(']');
    }

    return "{\n" + (builder.length() > 0 ? builder.substring(2) : builder.toString()) + "\n}";
  }

  @Override
  protected final String toJSONX(final String pacakgeName) {
    final StringBuilder builder = new StringBuilder(kind() == Kind.PROPERTY ? "<property xsi:type=\"array\"" : "<array");
    if (members.size() > 0) {
      builder.append(super.toJSONX(pacakgeName)).append('>');
      for (final Element member : this.members)
        builder.append("\n  ").append(member.toJSONX(pacakgeName).replace("\n", "\n  "));

      builder.append('\n');
      builder.append(kind() == Kind.PROPERTY ? "</property>" : "</array>");
    }
    else {
      builder.append(super.toJSONX(pacakgeName)).append("/>");
    }

    return builder.toString();
  }

  @Override
  protected final String toAnnotation(final boolean full) {
    final StringBuilder builder = full ? new StringBuilder(super.toAnnotation(full)) : new StringBuilder();
    if (builder.length() > 0)
      builder.append(", ");

    final int[] indices = new int[members().size()];
    final StringBuilder foo = new StringBuilder();
    int index = 0;
    for (int i = 0; i < members().size(); i++) {
      indices[i] = index;
      index += writeElementAnnotations(foo, members().get(i), index);
    }

    return writeElementIdsClause(builder, indices).toString();
  }

  protected final StringBuilder toAnnotation(final StringBuilder builder, final int ... indices) {
    builder.append(super.toAnnotation(true));
    if (builder.length() > 0)
      builder.append(", ");

    return writeElementIdsClause(builder, indices);
  }

  private static int writeElementAnnotations(final StringBuilder builder, final Element element, final int index) {
    final StringBuilder outer = new StringBuilder("@").append(element.elementAnnotation().getName()).append("(id=").append(index).append(", ");
    if (element instanceof ArrayModel) {
      final ArrayModel arrayModel = (ArrayModel)element;
      int offset = 1;
      final StringBuilder inner = new StringBuilder();
      final int[] indices = new int[arrayModel.members().size()];
      for (int i = 0; i < arrayModel.members().size(); i++) {
        indices[i] = index + offset;
        offset += writeElementAnnotations(inner, arrayModel.members().get(i), index + offset);
      }

      builder.insert(0, arrayModel.toAnnotation(outer, indices).append(")\n"));
      builder.insert(0, inner);
      return offset;
    }

    final String annotation = element.toAnnotation(true);
    if (annotation.length() > 0)
      builder.insert(0, outer.append(annotation).append(")\n"));
    else
      builder.append(outer.replace(outer.length() - 2, outer.length(), ")\n"));

    return 1;
  }

  @Override
  protected final String toField() {
    final StringBuilder builder = new StringBuilder();
    int index = 0;
    for (final Element element : members())
      index += writeElementAnnotations(builder, element, index);

    return builder.append(super.toField()).toString();
  }
}