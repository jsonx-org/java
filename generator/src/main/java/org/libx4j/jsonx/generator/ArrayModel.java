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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.lib4j.lang.AnnotationParameterException;
import org.lib4j.lang.IllegalAnnotationException;
import org.lib4j.util.Iterators;
import org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.$Array;
import org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.$Element;
import org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.$Object;
import org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx;
import org.libx4j.jsonx.runtime.ArrayElement;
import org.libx4j.jsonx.runtime.ArrayProperty;
import org.libx4j.jsonx.runtime.BooleanElement;
import org.libx4j.jsonx.runtime.NumberElement;
import org.libx4j.jsonx.runtime.ObjectElement;
import org.libx4j.jsonx.runtime.StringElement;
import org.libx4j.xsb.runtime.Bindings;

class ArrayModel extends ComplexModel {
  private static StringBuilder writeElementIdsClause(final StringBuilder string, final int numElements, final int offset) {
    string.append("elementIds=");
    if (numElements == 0)
      return string.append("{}");

    if (numElements == 1)
      return string.append(offset);

    string.append("{");
    for (int i = 0; i < numElements - 1; i++)
      string.append(i + offset).append(", ");

    return string.append(numElements - 1 + offset).append("}");
  }

  private static List<Object> getGreatestCommonSuperObject(final List<Element> elements) {
    if (elements.size() == 1)
      return Collections.singletonList(elements.get(0).className());

    List<Object> gcc = getGreatestCommonSuperObject(elements.get(0), elements.get(1));
    for (int i = 2; i < elements.size() && gcc != null; i++) {
      if (gcc instanceof Element)
        gcc = getGreatestCommonSuperObject((Element)gcc, elements.get(i));
      else if (Number.class.toString().equals(gcc) && !(elements.get(i) instanceof NumberModel))
        gcc = Collections.singletonList(Object.class.getName());
      else if (Object.class.getName().equals(gcc))
        return gcc;
    }

    return gcc;
  }

  private static List<Object> getGreatestCommonSuperObject(final Element a, final Element b) {
    if (a.className().equals(b.className()))
      return Collections.singletonList(a);

    if (a instanceof ObjectModel)
      return Collections.singletonList(b instanceof ObjectModel ? getGreatestCommonSuperObject((ObjectModel)a, (ObjectModel)b) : Object.class.getName());

    if (b instanceof ObjectModel)
      return Collections.singletonList(Object.class.getName());

    if (a instanceof NumberModel && b instanceof NumberModel)
      return Collections.singletonList(Number.class.toString());

    if (a instanceof ArrayModel && b instanceof ArrayModel) {
      final List<Element> all = new ArrayList<Element>(((ArrayModel)a).members());
      all.addAll(((ArrayModel)b).members());
      final List<Object> gcc = getGreatestCommonSuperObject(all);
      gcc.set(0, List.class.getName());
      return gcc;
    }

    if (a instanceof ArrayModel || b instanceof ArrayModel)
      return Collections.singletonList(Object.class.getName());

    return Collections.singletonList(Object.class.getName());
  }

  private static Object getGreatestCommonSuperObject(ObjectModel a, ObjectModel b) {
    do {
      do
        if (a.className().equals(b.className()))
          return a;
      while ((b = b.superObject()) != null);
    }
    while ((a = a.superObject()) != null);
    return Object.class.getName();
  }

  private static List<Element> parseMembers(final Registry registry, final ArrayModel referrer, final $Array binding) {
    final List<Element> members = new LinkedList<Element>();
    final Iterator<? extends $Element> elements = Iterators.filter(binding.elementIterator(), $Element.class);
    while (elements.hasNext()) {
      final $Element element = elements.next();
      if (element instanceof $Array.Boolean) {
        members.add(BooleanModel.reference(registry, referrer, ($Array.Boolean)element));
      }
      else if (element instanceof $Array.Number) {
        members.add(NumberModel.reference(registry, referrer, ($Array.Number)element));
      }
      else if (element instanceof $Array.String) {
        members.add(StringModel.reference(registry, referrer, ($Array.String)element));
      }
      else if (element instanceof $Array.Array) {
        final $Array.Array member = ($Array.Array)element;
        final ArrayModel child = ArrayModel.reference(registry, referrer, member);
        members.add(child);
      }
      else if (element instanceof $Array.Object) {
        final $Array.Object member = ($Array.Object)element;
        final ObjectModel child = ObjectModel.reference(registry, referrer, member, member.getExtends$().text());
        members.add(child);
      }
      else if (element instanceof $Array.Ref) {
        final $Array.Ref member = ($Array.Ref)element;
        final Element ref = registry.getElement(member.getProperty$().text());
        if (ref == null)
          throw new IllegalStateException("Top-level element ref=\"" + member.getProperty$().text() + "\" in array not found");

        members.add(ref instanceof Model ? new RefElement(member, (Model)ref) : ref);
      }
      else {
        throw new UnsupportedOperationException("Unsupported " + element.getClass().getSimpleName() + " member type: " + element.getClass().getName());
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

  public static ArrayModel reference(final Registry registry, final ComplexModel referrer, final $Array.Array binding) {
    return new ArrayModel(registry, binding);
  }

  // Annullable, Recurrable
  private ArrayModel(final Registry registry, final $Array.Array binding) {
    super(binding, binding.getNullable$().text(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    if (this.maxOccurs() != null && this.minOccurs() > this.maxOccurs())
      throw new ValidationException(Bindings.getXPath(binding, elementXPath) + ": minOccurs=\"" + this.minOccurs() + "\" > maxOccurs=\"" + this.maxOccurs() + "\"");

    this.members = parseMembers(registry, this, binding);
  }

  public static ArrayModel reference(final Registry registry, final ComplexModel referrer, final $Object.Array binding) {
    return new ArrayModel(registry, binding);
  }

  // Nameable, Annullable, Requirable
  private ArrayModel(final Registry registry, final $Object.Array binding) {
    super(binding, binding.getName$().text(), binding.getRequired$().text(), binding.getNullable$().text());
    this.members = parseMembers(registry, this, binding);
  }

  // Nameable
  public static ArrayModel declare(final Registry registry, final Jsonx.Array binding) {
    return registry.declare(binding).value(new ArrayModel(registry, binding), null);
  }

  private ArrayModel(final Registry registry, final Jsonx.Array binding) {
    super(binding.getName$().text(), null, null, null, null);
    this.members = parseMembers(registry, this, binding);
  }

  public static ArrayModel referenceOrDeclare(final Registry registry, final ComplexModel referrer, final ArrayProperty arrayProperty, final Field field) {
    return new ArrayModel(registry, arrayProperty, field);
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

  private ArrayModel(final Registry registry, final ArrayProperty arrayProperty, final Field field) {
    super(getName(arrayProperty.name(), field), arrayProperty.required(), arrayProperty.nullable(), null, null);
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

  private static ArrayModel referenceOrDeclare(final Registry registry, final ComplexModel referrer, final ArrayElement arrayElement, final Field field, final Map<Integer,Annotation> annotations) {
    return new ArrayModel(registry, arrayElement, field, annotations);
  }

  private ArrayModel(final Registry registry, final ArrayElement arrayElement, final Field field, final Map<Integer,Annotation> annotations) {
    super(null, null, arrayElement.nullable(), arrayElement.minOccurs(), arrayElement.maxOccurs());
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
  protected final String className() {
    final List<Object> cls = getGreatestCommonSuperObject(members);
    final StringBuilder string = new StringBuilder(List.class.getName());
    for (final Object part : cls)
      string.append("<").append(part instanceof Element ? ((Element)part).className() : (String)part);

    for (final Object part : cls)
      string.append(">");

    return string.toString().replace('$', '.');
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
  protected ArrayModel merge(final RefElement propertyElement) {
    return new ArrayModel(propertyElement, members);
  }

  @Override
  protected final void collectClassNames(final List<String> classNames) {
    if (members != null)
      for (final Element member : members)
        member.collectClassNames(classNames);
  }

  @Override
  protected final String toJSON(final String pacakgeName) {
    final StringBuilder string = new StringBuilder(super.toJSON(pacakgeName));
    if (string.length() > 0)
      string.insert(0, ",\n");

    if (members != null) {
      string.append(",\n  members: ");
      final StringBuilder members = new StringBuilder();
      for (final Element member : this.members)
        members.append(", ").append(member.toJSON(pacakgeName).replace("\n", "\n  "));

      string.append("[").append(members.length() > 0 ? members.substring(2) : members.toString()).append("]");
    }

    return "{\n" + (string.length() > 0 ? string.substring(2) : string.toString()) + "\n}";
  }

  @Override
  protected final String toJSONX(final String pacakgeName) {
    final StringBuilder string = new StringBuilder("<array");
    if (members.size() > 0) {
      string.append(super.toJSONX(pacakgeName)).append(">");
      for (final Element member : this.members)
        string.append("\n  ").append(member.toJSONX(pacakgeName).replace("\n", "\n  "));

      string.append("\n</array>");
    }
    else {
      string.append(super.toJSONX(pacakgeName)).append("/>");
    }

    return string.toString();
  }

  @Override
  protected final String toAnnotation(final boolean full) {
    final StringBuilder string = full ? new StringBuilder(super.toAnnotation(full)) : new StringBuilder();
    if (string.length() > 0)
      string.append(", ");

    return writeElementIdsClause(string, members.size(), 0).toString();
  }

  protected final StringBuilder toAnnotation(final StringBuilder string, final int numElements, final int offset) {
    string.append(super.toAnnotation(true));
    if (string.length() > 0)
      string.append(", ");

    return writeElementIdsClause(string, members.size(), offset);
  }

  private static int writeElementAnnotations(final StringBuilder string, final Element element, final int index) {
    final StringBuilder params = new StringBuilder("@").append(element.elementAnnotation().getName()).append("(id=").append(index).append(", ");
    if (element instanceof ArrayModel) {
      final ArrayModel arrayModel = (ArrayModel)element;
      int diff = arrayModel.members().size();
      string.insert(0, arrayModel.toAnnotation(params, arrayModel.members().size(), index + arrayModel.members().size()).append(")\n"));
      for (int i = 0; i < arrayModel.members().size(); i++) {
        final Element member = arrayModel.members().get(i);
        diff += writeElementAnnotations(string, member, i + arrayModel.members().size());
      }

      return diff;
    }

    string.insert(0, params.append(element.toAnnotation(true)).append(")\n"));
    return 1;
  }

  @Override
  protected final String toField() {
    final StringBuilder string = new StringBuilder();
    int diff = 0;
    for (int i = 0; i < members.size(); i++)
      diff += writeElementAnnotations(string, members.get(i), diff);

    return string.append(super.toField()).toString();
  }
}