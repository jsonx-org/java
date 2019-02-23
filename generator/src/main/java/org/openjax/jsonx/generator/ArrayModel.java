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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.openjax.jsonx.generator.Reference.Deferred;
import org.openjax.jsonx.runtime.ArrayElement;
import org.openjax.jsonx.runtime.ArrayProperty;
import org.openjax.jsonx.runtime.ArrayType;
import org.openjax.jsonx.runtime.BooleanElement;
import org.openjax.jsonx.runtime.JxUtil;
import org.openjax.jsonx.runtime.NumberElement;
import org.openjax.jsonx.runtime.ObjectElement;
import org.openjax.jsonx.runtime.StringElement;
import org.openjax.jsonx.runtime.ValidationException;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc.$Array;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc.$ArrayMember;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc.$Member;
import org.openjax.standard.lang.AnnotationParameterException;
import org.openjax.standard.lang.IllegalAnnotationException;
import org.openjax.standard.util.FastArrays;
import org.openjax.standard.util.FastCollections;
import org.openjax.standard.util.Iterators;
import org.openjax.xsb.runtime.Bindings;

final class ArrayModel extends Referrer<ArrayModel> {
  static ArrayModel declare(final Registry registry, final xL4gluGCXYYJc.Schema.ArrayType binding) {
    return registry.declare(binding).value(new ArrayModel(registry, binding), null);
  }

  private static ArrayModel referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final Annotation annotation, final int minIterate, final int maxIterate, final Class<? extends Annotation> annotationType, final Id id, final Registry.Type type) {
    final ArrayModel registered = (ArrayModel)registry.getModel(id);
    if (registered != null)
      return registry.reference(registered, referrer);

    final ArrayType arrayType = annotationType.getAnnotation(ArrayType.class);
    if (arrayType == null)
      throw new IllegalAnnotationException(annotation, annotationType.getName() + " does not specify the required @" + ArrayType.class.getSimpleName() + " annotation");

    return registry.declare(id).value(new ArrayModel(registry, arrayType, minIterate, maxIterate, arrayType.elementIds(), annotationType.getAnnotations(), type, annotationType.getName()), referrer);
  }

  static ArrayModel referenceOrDeclare(final Registry registry, final Class<?> cls) {
    final Id id = new Id(cls);
    final ArrayModel model = (ArrayModel)registry.getModel(id);
    if (model != null)
      return registry.reference(model, null);

    final ArrayType arrayType = cls.getAnnotation(ArrayType.class);
    if (arrayType == null)
      throw new IllegalArgumentException("Class " + cls.getName() + " does not specify the @" + ArrayType.class.getSimpleName() + " annotation");

    return registry.declare(id).value(new ArrayModel(registry, arrayType, arrayType.minIterate(), arrayType.maxIterate(), arrayType.elementIds(), cls.getAnnotations(), registry.getType(cls), cls.getName()), null);
  }

  static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final ArrayProperty arrayProperty, final Field field) {
    if (!isAssignable(field, List.class, arrayProperty.nullable(), arrayProperty.use()))
      throw new IllegalAnnotationException(arrayProperty, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + ArrayProperty.class.getSimpleName() + " can only be applied to required or not-nullable fields of List<?> types, or optional and nullable fields of Optional<? extends List<?>> type");

    if (ArrayType.class.equals(arrayProperty.type())) {
      final ArrayModel model = new ArrayModel(registry, arrayProperty, arrayProperty.minIterate(), arrayProperty.maxIterate(), arrayProperty.elementIds(), field.getAnnotations(), null, field.getDeclaringClass().getName() + "." + field.getName());
      final Id id = model.id();

      final ArrayModel registered = (ArrayModel)registry.getModel(id);
      return new Reference(registry, JxUtil.getName(arrayProperty.name(), field), arrayProperty.nullable(), arrayProperty.use(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
    }

    final Registry.Type type = registry.getType(arrayProperty.type());
    final Id id = new Id(type);

    return new Reference(registry, JxUtil.getName(arrayProperty.name(), field), arrayProperty.nullable(), arrayProperty.use(), referenceOrDeclare(registry, referrer, arrayProperty, arrayProperty.minIterate(), arrayProperty.maxIterate(), arrayProperty.type(), id, type));
  }

  private static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final ArrayElement arrayElement, final Map<Integer,Annotation> idToElement, final String declaringTypeName) {
    if (ArrayType.class.equals(arrayElement.type())) {
      final ArrayModel model = new ArrayModel(registry, arrayElement, idToElement, declaringTypeName);
      final Id id = model.id();

      final ArrayModel registered = (ArrayModel)registry.getModel(id);
      return new Reference(registry, arrayElement.nullable(), arrayElement.minOccurs(), arrayElement.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
    }

    final Registry.Type type = registry.getType(arrayElement.type());
    final Id id = new Id(type);

    return new Reference(registry, arrayElement.nullable(), arrayElement.minOccurs(), arrayElement.maxOccurs(), referenceOrDeclare(registry, referrer, arrayElement, arrayElement.minIterate(), arrayElement.maxIterate(), arrayElement.type(), id, type));
  }

  static ArrayModel reference(final Registry registry, final Referrer<?> referrer, final $Array.Array binding) {
    return registry.reference(new ArrayModel(registry, binding), referrer);
  }

  static ArrayModel reference(final Registry registry, final Referrer<?> referrer, final $Array binding) {
    return registry.reference(new ArrayModel(registry, binding), referrer);
  }

  static Registry.Type getGreatestCommonSuperType(final Registry registry, final List<Member> members) {
    if (members.size() == 0)
      throw new IllegalArgumentException("members.size() == 0");

    if (members.size() == 1)
      return registry.getType(List.class, members.get(0).type());

    Registry.Type gct = members.get(0).type().getGreatestCommonSuperType(members.get(1).type());
    for (int i = 2; i < members.size() && gct != null; ++i)
      gct = gct.getGreatestCommonSuperType(members.get(i).type());

    return registry.getType(List.class, gct);
  }

  private void writeElementIdsClause(final AttributeMap attributes, final int[] indices) {
    writeIterateClauses(attributes);
    attributes.put("elementIds", indices.length == 0 ? "{}" : "{" + FastArrays.toString(indices, ", ") + "}");
  }

  private static List<Member> parseMembers(final Registry registry, final ArrayModel referrer, final $ArrayMember binding) {
    final List<Member> members = new ArrayList<>();
    final Iterator<? super $Member> iterator = Iterators.filter(binding.elementIterator(), m -> m instanceof $Member);
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
        members.add(Reference.defer(registry, reference, () -> {
          final Member model = registry.getModel(new Id(reference.getType$()));
          if (model == null)
            throw new IllegalStateException("Template type=\"" + reference.getType$().text() + "\" in array not found");

          return registry.reference(model, referrer);
        }));
      }
      else {
        throw new UnsupportedOperationException("Unsupported " + member.getClass().getSimpleName() + " member type: " + member.getClass().getName());
      }
    }

    return members;
  }

  private static List<Member> parseMembers(final Registry registry, final ArrayModel referrer, final Annotation annotation, final int[] elementIds, final Map<Integer,Annotation> idToElement, final String declaringTypeName) {
    final List<Member> elements = new ArrayList<>();
    for (final int elementId : elementIds) {
      final Annotation element = idToElement.get(elementId);
      if (element == null)
        throw new AnnotationParameterException(annotation, declaringTypeName + ": @" + annotation.annotationType().getName() + " specifies non-existent element with id=" + elementId);

      if (element instanceof BooleanElement)
        elements.add(BooleanModel.referenceOrDeclare(registry, referrer, (BooleanElement)element));
      else if (element instanceof NumberElement)
        elements.add(NumberModel.referenceOrDeclare(registry, referrer, (NumberElement)element));
      else if (element instanceof StringElement)
        elements.add(StringModel.referenceOrDeclare(registry, referrer, (StringElement)element));
      else if (element instanceof ObjectElement)
        elements.add(ObjectModel.referenceOrDeclare(registry, referrer, (ObjectElement)element));
      else if (element instanceof ArrayElement)
        elements.add(ArrayModel.referenceOrDeclare(registry, referrer, (ArrayElement)element, idToElement, declaringTypeName));
      else
        throw new UnsupportedOperationException("Unsupported annotation type: " + element.annotationType().getName());
    }

    return Collections.unmodifiableList(elements);
  }

  private Id id;
  private final Registry.Type type;
  final List<Member> members;
  final Integer minIterate;
  final Integer maxIterate;

  private static Integer parseIterate(final Integer minIterate) {
    return minIterate == null || minIterate == 1 ? null : minIterate;
  }

  private ArrayModel(final Registry registry, final xL4gluGCXYYJc.Schema.ArrayType binding) {
    super(registry);
    this.members = parseMembers(registry, this, binding);
    this.type = registry.getType(Registry.Kind.ANNOTATION, registry.packageName, registry.classPrefix + JxUtil.flipName(binding.getName$().text()));
    this.minIterate = parseIterate(binding.getMinIterate$().text());
    this.maxIterate = parseIterate(parseMaxCardinality(binding.getMinIterate$().text(), binding.getMaxIterate$(), "Iterate", 1));
    this.id = new Id(binding.getName$());
  }

  private ArrayModel(final Registry registry, final $Array binding) {
    super(registry, binding.getName$(), binding.getNullable$(), binding.getUse$());
    this.members = parseMembers(registry, this, binding);
    this.type = null;
    this.minIterate = parseIterate(binding.getMinIterate$().text());
    this.maxIterate = parseIterate(parseMaxCardinality(binding.getMinIterate$().text(), binding.getMaxIterate$(), "Iterate", 1));
//    this.id = new Id(this);
  }

  private ArrayModel(final Registry registry, final $Array.Array binding) {
    super(registry, binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    if (this.maxOccurs != null && this.minOccurs != null && this.minOccurs > this.maxOccurs)
      throw new ValidationException(Bindings.getXPath(binding, elementXPath) + ": minOccurs=\"" + this.minOccurs + "\" > maxOccurs=\"" + this.maxOccurs + "\"");

    this.members = parseMembers(registry, this, binding);
    this.type = null;
    this.minIterate = parseIterate(binding.getMinIterate$().text());
    this.maxIterate = parseIterate(parseMaxCardinality(binding.getMinIterate$().text(), binding.getMaxIterate$(), "Iterate", 1));
//    this.id = new Id(this);
  }

  private ArrayModel(final Registry registry, final Annotation arrayAnnotation, final int minIterate, final int maxIterate, final int[] elementIds, final Annotation[] annotations, final Registry.Type type, final String declaringTypeName) {
    super(registry);
    final Map<Integer,Annotation> idToElement = new HashMap<>();
    final StrictDigraph<Integer> digraph = new StrictDigraph<>("Element cannot include itself as a member");
    for (final Annotation elementAnnotation : JxUtil.flatten(annotations)) {
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
      throw new ValidationException("Cycle detected in element index dependency graph: " + FastCollections.toString(digraph.getCycle(), " -> "));

    final LinkedHashMap<Integer,Annotation> topologicalOrder = new LinkedHashMap<>(idToElement.size());
    for (final Integer elementId : digraph.getTopologicalOrder())
      topologicalOrder.put(elementId, idToElement.get(elementId));

    this.type = type;
    this.members = parseMembers(registry, this, arrayAnnotation, elementIds, topologicalOrder, declaringTypeName);
    this.minIterate = parseIterate(minIterate);
    this.maxIterate = parseIterate(maxIterate);
    this.id = type != null ? new Id(type) : new Id(this);
  }

  private ArrayModel(final Registry registry, final ArrayElement arrayElement, final Map<Integer,Annotation> idToElement, final String declaringTypeName) {
    super(registry, arrayElement.nullable(), null);
    if (arrayElement.type() != ArrayType.class)
      throw new IllegalArgumentException("This constructor is only for elementIds");

    this.type = null;
    this.members = parseMembers(registry, this, arrayElement, arrayElement.elementIds(), idToElement, declaringTypeName);
    this.minIterate = parseIterate(arrayElement.minIterate());
    this.maxIterate = parseIterate(arrayElement.maxIterate());
    this.id = new Id(this);
  }

  private ArrayModel(final Registry registry, final ArrayProperty arrayProperty, final Map<Integer,Annotation> idToElement, final String declaringTypeName) {
    super(registry, arrayProperty.nullable(), arrayProperty.use());
    if (arrayProperty.type() != ArrayType.class)
      throw new IllegalArgumentException("This constructor is only for elementIds");

    this.type = null;
    this.members = parseMembers(registry, this, arrayProperty, arrayProperty.elementIds(), idToElement, declaringTypeName);
    this.minIterate = parseIterate(arrayProperty.minIterate());
    this.maxIterate = parseIterate(arrayProperty.maxIterate());
    this.id = new Id(this);
  }

  @Override
  Id id() {
    return id;
  }

  @Override
  Registry.Type type() {
    return getGreatestCommonSuperType(registry, members);
  }

  @Override
  Registry.Type classType() {
    return type;
  }

  @Override
  String elementName() {
    return "array";
  }

  @Override
  Class<? extends Annotation> propertyAnnotation() {
    return ArrayProperty.class;
  }

  @Override
  Class<? extends Annotation> elementAnnotation() {
    return ArrayElement.class;
  }

  private boolean referencesResolved = false;

  @Override
  void resolveReferences() {
    if (referencesResolved)
      return;

    final ListIterator<Member> iterator = members.listIterator();
    while (iterator.hasNext()) {
      final Member member = iterator.next();
      if (member instanceof Deferred)
        iterator.set(((Deferred)member).resolve());
    }

    if (id == null)
      id = new Id(this);

    referencesResolved = true;
  }

  @Override
  void getDeclaredTypes(final Set<Registry.Type> types) {
    if (type != null)
      types.add(type);

    if (members != null)
      for (final Member member : members)
        member.getDeclaredTypes(types);
  }

  @Override
  Map<String,String> toXmlAttributes(final Element owner, final String packageName) {
    final Map<String,String> attributes = super.toXmlAttributes(owner, packageName);
    if (owner instanceof Schema)
      attributes.put("name", type != null ? JxUtil.flipName(type.getSubName(packageName)) : id().toString());

    if (minIterate != null)
      attributes.put("minIterate", String.valueOf(minIterate));

    if (maxIterate != null)
      attributes.put("maxIterate", maxIterate == Integer.MAX_VALUE ? "unbounded" : String.valueOf(maxIterate));

    return attributes;
  }

  @Override
  org.openjax.standard.xml.api.Element toXml(final Settings settings, final Element owner, final String packageName) {
    final org.openjax.standard.xml.api.Element element = super.toXml(settings, owner, packageName);
    if (members.size() == 0)
      return element;

    final List<org.openjax.standard.xml.api.Element> elements = new ArrayList<>();
    for (final Member member : members)
      elements.add(member.toXml(settings, this, packageName));

    element.setElements(elements);
    return element;
  }

  private void renderAnnotations(final AttributeMap attributes, final List<AnnotationSpec> annotations) {
    final int[] indices = new int[members.size()];
    for (int i = 0, index = 0; i < members.size(); ++i) {
      indices[i] = index;
      index += writeElementAnnotations(annotations, members.get(i), index, this);
    }

    writeElementIdsClause(attributes, indices);
  }

  @Override
  void toAnnotationAttributes(final AttributeMap attributes, final Member owner) {
    super.toAnnotationAttributes(attributes, owner);
    if (type != null) {
      attributes.put("type", type.getCanonicalName() + ".class");
      writeIterateClauses(attributes);
    }
    else {
      renderAnnotations(attributes, new ArrayList<>());
    }
  }

  private void toAnnotationAttributes(final AttributeMap attributes, final int[] indices, final Member owner) {
    super.toAnnotationAttributes(attributes, owner);
    writeElementIdsClause(attributes, indices);
  }

  private void writeIterateClauses(final AttributeMap attributes) {
    if (minIterate != null)
      attributes.put("minIterate", minIterate);

    if (maxIterate != null)
      attributes.put("maxIterate", maxIterate);
  }

  private static int writeElementAnnotations(final List<AnnotationSpec> annotations, final Member member, final int index, final Member owner) {
    final AttributeMap attributes = new AttributeMap();
    attributes.put("id", index);

    final AnnotationSpec annotationSpec = new AnnotationSpec(member.elementAnnotation(), attributes);
    final Member reference = member instanceof Reference ? ((Reference)member).model : member;
    if (reference instanceof ArrayModel) {
      final ArrayModel arrayModel = (ArrayModel)reference;
      int offset = 1;
      final List<AnnotationSpec> inner = new ArrayList<>();
      if (arrayModel.type == null) {
        final int[] indices = new int[arrayModel.members.size()];
        for (int i = 0; i < arrayModel.members.size(); ++i) {
          indices[i] = index + offset;
          offset += writeElementAnnotations(inner, arrayModel.members.get(i), index + offset, owner);
        }

        // FIXME: Can this be abstracted better? minOccurs, maxOccurs and nullable are rendered here and in Member#toAnnotationAttributes()
        arrayModel.toAnnotationAttributes(attributes, indices, owner);
      }
      else {
        arrayModel.toAnnotationAttributes(attributes, owner);
      }

      if (member.minOccurs != null)
        attributes.put("minOccurs", member.minOccurs);

      if (member.maxOccurs != null)
        attributes.put("maxOccurs", member.maxOccurs);

      if (member.nullable != null)
        attributes.put("nullable", member.nullable);

      inner.add(annotationSpec);
      annotations.addAll(0, inner);
      return offset;
    }

    member.toAnnotationAttributes(attributes, owner);
    annotations.add(0, annotationSpec);
    return 1;
  }

  @Override
  List<AnnotationSpec> toElementAnnotations() {
    if (type != null)
      return null;

    final List<AnnotationSpec> annotations = new ArrayList<>();
    int index = 0;
    for (final Member member : members)
      index += writeElementAnnotations(annotations, member, index, this);

    return annotations;
  }

  @Override
  List<AnnotationSpec> getClassAnnotation() {
    final AttributeMap attributes = new AttributeMap();
    final List<AnnotationSpec> annotations = new ArrayList<>();
    renderAnnotations(attributes, annotations);
    annotations.add(new AnnotationSpec(ArrayType.class, attributes));
    return annotations;
  }

  @Override
  String toSource(final Settings settings) {
    return null;
  }
}