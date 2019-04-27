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

package org.openjax.jsonx;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.openjax.jsonx.schema_0_2_2.xL4gluGCXYYJc;
import org.openjax.standard.lang.AnnotationParameterException;
import org.openjax.standard.lang.IllegalAnnotationException;
import org.openjax.standard.util.FastArrays;
import org.openjax.standard.util.FastCollections;
import org.openjax.standard.util.Iterators;
import org.openjax.standard.util.Strings;
import org.openjax.standard.xml.api.XmlElement;
import org.openjax.xsb.runtime.Bindings;

final class ArrayModel extends Referrer<ArrayModel> {
  private static xL4gluGCXYYJc.Schema.Array type(final String name) {
    final xL4gluGCXYYJc.Schema.Array xsb = new xL4gluGCXYYJc.Schema.Array();
    if (name != null)
      xsb.setName$(new xL4gluGCXYYJc.Schema.Array.Name$(name));

    return xsb;
  }

  private static xL4gluGCXYYJc.$Array property(final schema.ArrayProperty jsonx, final String name) {
    final xL4gluGCXYYJc.$Array xsb = new xL4gluGCXYYJc.$Array() {
      private static final long serialVersionUID = 3936180512257992902L;

      @Override
      protected xL4gluGCXYYJc.$Member inherits() {
        return new xL4gluGCXYYJc.$ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setName$(new xL4gluGCXYYJc.$Array.Name$(name));

    if (jsonx.getJsd_3aNullable() != null)
      xsb.setNullable$(new xL4gluGCXYYJc.$Array.Nullable$(jsonx.getJsd_3aNullable()));

    if (jsonx.getJsd_3aUse() != null)
      xsb.setUse$(new xL4gluGCXYYJc.$Array.Use$(xL4gluGCXYYJc.$Array.Use$.Enum.valueOf(jsonx.getJsd_3aUse())));

    return xsb;
  }

  private static xL4gluGCXYYJc.$ArrayMember.Array element(final schema.ArrayElement jsonx) {
    final xL4gluGCXYYJc.$ArrayMember.Array xsb = new xL4gluGCXYYJc.$ArrayMember.Array();

    if (jsonx.getJsd_3aNullable() != null)
      xsb.setNullable$(new xL4gluGCXYYJc.$ArrayMember.Array.Nullable$(jsonx.getJsd_3aNullable()));

    if (jsonx.getJsd_3aMinOccurs() != null)
      xsb.setMinOccurs$(new xL4gluGCXYYJc.$ArrayMember.Array.MinOccurs$(Integer.parseInt(jsonx.getJsd_3aMinOccurs())));

    if (jsonx.getJsd_3aMaxOccurs() != null)
      xsb.setMaxOccurs$(new xL4gluGCXYYJc.$ArrayMember.Array.MaxOccurs$(jsonx.getJsd_3aMaxOccurs()));

    return xsb;
  }

  static xL4gluGCXYYJc.$ArrayMember jsdToXsb(final schema.Array jsd, final String name) {
    final xL4gluGCXYYJc.$ArrayMember xsb;
    if (jsd instanceof schema.ArrayProperty)
      xsb = property((schema.ArrayProperty)jsd, name);
    else if (jsd instanceof schema.ArrayElement)
      xsb = element((schema.ArrayElement)jsd);
    else if (name != null)
      xsb = type(name);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsd.getClass().getName());

    if (jsd.getJsd_3aMinIterate() != null)
      xsb.setMinIterate$(new xL4gluGCXYYJc.$ArrayMember.MinIterate$(Integer.parseInt(jsd.getJsd_3aMinIterate())));

    if (jsd.getJsd_3aMaxIterate() != null)
      xsb.setMaxIterate$(new xL4gluGCXYYJc.$ArrayMember.MaxIterate$(jsd.getJsd_3aMaxIterate()));

    for (final Object element : jsd.getJsd_3aElements()) {
      if (element instanceof schema.AnyElement)
        xsb.addAny((xL4gluGCXYYJc.$ArrayMember.Any)AnyModel.jsdToXsb((schema.AnyElement)element, null));
      else if (element instanceof schema.ArrayElement)
        xsb.addArray((xL4gluGCXYYJc.$ArrayMember.Array)ArrayModel.jsdToXsb((schema.ArrayElement)element, null));
      else if (element instanceof schema.BooleanElement)
        xsb.addBoolean((xL4gluGCXYYJc.$ArrayMember.Boolean)BooleanModel.jsdToXsb((schema.BooleanElement)element, null));
      else if (element instanceof schema.NumberElement)
        xsb.addNumber((xL4gluGCXYYJc.$ArrayMember.Number)NumberModel.jsdToXsb((schema.NumberElement)element, null));
      else if (element instanceof schema.ReferenceElement)
        xsb.addReference((xL4gluGCXYYJc.$ArrayMember.Reference)Reference.jsdToXsb((schema.ReferenceElement)element, null));
      else if (element instanceof schema.StringElement)
        xsb.addString((xL4gluGCXYYJc.$ArrayMember.String)StringModel.jsdToXsb((schema.StringElement)element, null));
      else
        throw new UnsupportedOperationException("Unsupported JSONx type: " + element.getClass().getName());
    }

    return xsb;
  }

  static ArrayModel declare(final Registry registry, final xL4gluGCXYYJc.Schema.Array binding) {
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

  static Member referenceOrDeclare(final Registry registry, final Class<? extends Annotation> cls) {
    final Id id = Id.named(cls);
    if (registry.isPending(id))
      return new Deferred<>(null, () -> registry.reference(registry.getModel(id), null));

    final ArrayModel model = (ArrayModel)registry.getModel(id);
    if (model != null)
      return registry.reference(model, null);

    final ArrayType arrayType = cls.getAnnotation(ArrayType.class);
    if (arrayType == null)
      throw new IllegalArgumentException("Class " + cls.getName() + " does not specify the @" + ArrayType.class.getSimpleName() + " annotation");

    return registry.declare(id).value(new ArrayModel(registry, arrayType, arrayType.minIterate(), arrayType.maxIterate(), arrayType.elementIds(), cls.getAnnotations(), registry.getType(cls), cls.getName()), null);
  }

  static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final ArrayProperty arrayProperty, final Field field) {
    if (!isAssignable(field, List.class, Strings.isRegex(JsdUtil.getName(field)), arrayProperty.nullable(), arrayProperty.use()))
      throw new IllegalAnnotationException(arrayProperty, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + ArrayProperty.class.getSimpleName() + " can only be applied to required or not-nullable fields of List<?> types, or optional and nullable fields of Optional<? extends List<?>> type");

    if (ArrayType.class.equals(arrayProperty.type())) {
      final ArrayModel model = new ArrayModel(registry, arrayProperty, arrayProperty.minIterate(), arrayProperty.maxIterate(), arrayProperty.elementIds(), field.getAnnotations(), null, field.getDeclaringClass().getName() + "." + field.getName());
      final Id id = model.id;

      final ArrayModel registered = (ArrayModel)registry.getModel(id);
      return new Reference(registry, JsdUtil.getName(arrayProperty.name(), field), arrayProperty.nullable(), arrayProperty.use(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
    }

    final Registry.Type type = registry.getType(arrayProperty.type());
    final Id id = Id.named(type);

    if (registry.isPending(id))
      return Reference.defer(registry, JsdUtil.getName(arrayProperty.name(), field), arrayProperty.nullable(), arrayProperty.use(), () -> registry.getModel(id));

    final ArrayModel model = referenceOrDeclare(registry, referrer, arrayProperty, arrayProperty.minIterate(), arrayProperty.maxIterate(), arrayProperty.type(), id, type);
    return new Reference(registry, JsdUtil.getName(arrayProperty.name(), field), arrayProperty.nullable(), arrayProperty.use(), model);
  }

  private static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final ArrayElement arrayElement, final Map<Integer,Annotation> idToElement, final String declaringTypeName) {
    if (ArrayType.class.equals(arrayElement.type())) {
      final ArrayModel model = new ArrayModel(registry, arrayElement, idToElement, declaringTypeName);
      final Id id = model.id;

      final ArrayModel registered = (ArrayModel)registry.getModel(id);
      return new Reference(registry, arrayElement.nullable(), arrayElement.minOccurs(), arrayElement.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
    }

    final Registry.Type type = registry.getType(arrayElement.type());
    final Id id = Id.named(type);

    if (registry.isPending(id))
      return Reference.defer(registry, arrayElement.nullable(), arrayElement.minOccurs(), arrayElement.maxOccurs(), () -> registry.getModel(id));

    final ArrayModel model = referenceOrDeclare(registry, referrer, arrayElement, arrayElement.minIterate(), arrayElement.maxIterate(), arrayElement.type(), id, type);
    return new Reference(registry, arrayElement.nullable(), arrayElement.minOccurs(), arrayElement.maxOccurs(), model);
  }

  static ArrayModel reference(final Registry registry, final Referrer<?> referrer, final xL4gluGCXYYJc.$Array.Array binding) {
    return registry.reference(new ArrayModel(registry, binding), referrer);
  }

  static ArrayModel reference(final Registry registry, final Referrer<?> referrer, final xL4gluGCXYYJc.$Array binding) {
    return registry.reference(new ArrayModel(registry, binding), referrer);
  }

  private void writeElementIdsClause(final AttributeMap attributes, final int[] indices) {
    writeIterateClauses(attributes);
    attributes.put("elementIds", indices.length == 0 ? "{}" : "{" + FastArrays.toString(indices, ", ") + "}");
  }

  private static List<Member> parseMembers(final Registry registry, final ArrayModel referrer, final xL4gluGCXYYJc.$ArrayMember binding) {
    final List<Member> members = new ArrayList<>();
    final Iterator<? super xL4gluGCXYYJc.$Member> iterator = Iterators.filter(binding.elementIterator(), m -> m instanceof xL4gluGCXYYJc.$Member);
    while (iterator.hasNext()) {
      final xL4gluGCXYYJc.$Member member = (xL4gluGCXYYJc.$Member)iterator.next();
      if (member instanceof xL4gluGCXYYJc.$Array.Any) {
        members.add(AnyModel.reference(registry, referrer, (xL4gluGCXYYJc.$Array.Any)member));
      }
      else if (member instanceof xL4gluGCXYYJc.$Array.Array) {
        members.add(ArrayModel.reference(registry, referrer, (xL4gluGCXYYJc.$Array.Array)member));
      }
      else if (member instanceof xL4gluGCXYYJc.$Array.Boolean) {
        members.add(BooleanModel.reference(registry, referrer, (xL4gluGCXYYJc.$Array.Boolean)member));
      }
      else if (member instanceof xL4gluGCXYYJc.$Array.Number) {
        members.add(NumberModel.reference(registry, referrer, (xL4gluGCXYYJc.$Array.Number)member));
      }
      else if (member instanceof xL4gluGCXYYJc.$Array.String) {
        members.add(StringModel.reference(registry, referrer, (xL4gluGCXYYJc.$Array.String)member));
      }
      else if (member instanceof xL4gluGCXYYJc.$Array.Reference) {
        final xL4gluGCXYYJc.$Array.Reference reference = (xL4gluGCXYYJc.$Array.Reference)member;
        members.add(Reference.defer(registry, reference, () -> {
          final Model model = registry.getModel(Id.named(reference.getType$()));
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
    final List<Member> members = new ArrayList<>();
    for (final int elementId : elementIds) {
      final Annotation element = idToElement.get(elementId);
      if (element == null)
        throw new AnnotationParameterException(annotation, declaringTypeName + ": @" + annotation.annotationType().getName() + " specifies non-existent element with id=" + elementId);

      if (element instanceof AnyElement)
        members.add(AnyModel.referenceOrDeclare(registry, referrer, (AnyElement)element));
      else if (element instanceof ArrayElement)
        members.add(ArrayModel.referenceOrDeclare(registry, referrer, (ArrayElement)element, idToElement, declaringTypeName));
      else if (element instanceof BooleanElement)
        members.add(BooleanModel.referenceOrDeclare(registry, referrer, (BooleanElement)element));
      else if (element instanceof NumberElement)
        members.add(NumberModel.referenceOrDeclare(registry, referrer, (NumberElement)element));
      else if (element instanceof ObjectElement)
        members.add(ObjectModel.referenceOrDeclare(registry, referrer, (ObjectElement)element));
      else if (element instanceof StringElement)
        members.add(StringModel.referenceOrDeclare(registry, referrer, (StringElement)element));
      else
        throw new UnsupportedOperationException("Unsupported annotation type: " + element.annotationType().getName());
    }

    return members;
  }

  final List<Member> members;
  final Integer minIterate;
  final Integer maxIterate;

  private static Integer parseIterate(final Integer minIterate) {
    return minIterate == null || minIterate == 1 ? null : minIterate;
  }

  private ArrayModel(final Registry registry, final xL4gluGCXYYJc.Schema.Array binding) {
    super(registry, registry.getType(Registry.Kind.ANNOTATION, registry.packageName, registry.classPrefix + JsdUtil.flipName(binding.getName$().text())));
    this.members = parseMembers(registry, this, binding);
    this.minIterate = parseIterate(binding.getMinIterate$().text());
    this.maxIterate = parseIterate(parseMaxCardinality(binding.getMinIterate$().text(), binding.getMaxIterate$(), "Iterate", 1));
  }

  private ArrayModel(final Registry registry, final xL4gluGCXYYJc.$Array binding) {
    super(registry, binding.getName$(), binding.getNullable$(), binding.getUse$(), null);
    this.members = parseMembers(registry, this, binding);
    this.minIterate = parseIterate(binding.getMinIterate$().text());
    this.maxIterate = parseIterate(parseMaxCardinality(binding.getMinIterate$().text(), binding.getMaxIterate$(), "Iterate", 1));
  }

  private ArrayModel(final Registry registry, final xL4gluGCXYYJc.$Array.Array binding) {
    super(registry, binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$(), null);
    if (this.maxOccurs != null && this.minOccurs != null && this.minOccurs > this.maxOccurs)
      throw new ValidationException(Bindings.getXPath(binding, elementXPath) + ": minOccurs=\"" + this.minOccurs + "\" > maxOccurs=\"" + this.maxOccurs + "\"");

    this.members = parseMembers(registry, this, binding);
    this.minIterate = parseIterate(binding.getMinIterate$().text());
    this.maxIterate = parseIterate(parseMaxCardinality(binding.getMinIterate$().text(), binding.getMaxIterate$(), "Iterate", 1));
  }

  private ArrayModel(final Registry registry, final Annotation arrayAnnotation, final int minIterate, final int maxIterate, final int[] elementIds, final Annotation[] annotations, final Registry.Type type, final String declaringTypeName) {
    super(registry, type);
    final Map<Integer,Annotation> idToElement = new HashMap<>();
    final StrictDigraph<Integer> digraph = new StrictDigraph<>("Element cannot include itself as a member");
    for (final Annotation elementAnnotation : JsdUtil.flatten(annotations)) {
      if (elementAnnotation instanceof ArrayProperty || elementAnnotation instanceof ArrayType)
        continue;

      final int id;
      if (elementAnnotation instanceof AnyElement) {
        id = ((AnyElement)elementAnnotation).id();
      }
      else if (elementAnnotation instanceof ArrayElement) {
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
        throw new UnsupportedOperationException("Unsupported annotation type: " + elementAnnotation.annotationType().getName());
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

    this.members = parseMembers(registry, this, arrayAnnotation, elementIds, topologicalOrder, declaringTypeName);
    this.minIterate = parseIterate(minIterate);
    this.maxIterate = parseIterate(maxIterate);
  }

  private ArrayModel(final Registry registry, final ArrayElement arrayElement, final Map<Integer,Annotation> idToElement, final String declaringTypeName) {
    super(registry, arrayElement.nullable(), null, null);
    if (arrayElement.type() != ArrayType.class)
      throw new IllegalArgumentException("This constructor is only for elementIds");

    this.members = parseMembers(registry, this, arrayElement, arrayElement.elementIds(), idToElement, declaringTypeName);
    this.minIterate = parseIterate(arrayElement.minIterate());
    this.maxIterate = parseIterate(arrayElement.maxIterate());
  }

  @Override
  Registry.Type type() {
    // type can be null if there is a loop in the type graph
    final Registry.Type type = getGreatestCommonSuperType(members);
    return registry.getType(List.class, type != null ? type : registry.OBJECT);
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
        iterator.set(((Deferred<?>)member).resolve());
    }

    referencesResolved = true;
  }

  @Override
  void getDeclaredTypes(final Set<Registry.Type> types) {
    if (classType() != null)
      types.add(classType());

    if (members != null)
      for (final Member member : members)
        member.getDeclaredTypes(types);
  }

  @Override
  Map<String,Object> toAttributes(final Element owner, final String prefix, final String packageName) {
    final Map<String,Object> attributes = super.toAttributes(owner, prefix, packageName);
    if (owner instanceof SchemaElement)
      attributes.put("name", classType() != null ? JsdUtil.flipName(classType().getSubName(packageName)) : id.toString());

    if (minIterate != null)
      attributes.put(prefix + "minIterate", String.valueOf(minIterate));

    if (maxIterate != null)
      attributes.put(prefix + "maxIterate", maxIterate == Integer.MAX_VALUE ? "unbounded" : String.valueOf(maxIterate));

    return attributes;
  }

  @Override
  XmlElement toXml(final Settings settings, final Element owner, final String prefix, final String packageName) {
    final XmlElement element = super.toXml(settings, owner, prefix, packageName);
    if (members.size() == 0)
      return element;

    final List<XmlElement> elements = new ArrayList<>();
    for (final Member member : members)
      elements.add(member.toXml(settings, this, prefix, packageName));

    element.setElements(elements);
    return element;
  }

  @Override
  Map<String,Object> toJson(final Settings settings, final Element owner, final String packageName) {
    final Map<String,Object> element = super.toJson(settings, owner, packageName);
    if (members.size() == 0)
      return element;

    final List<Object> elements = new ArrayList<>();
    for (final Member member : members)
      elements.add(member.toJson(settings, this, packageName));

    element.put("jsd:elements", elements);
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
    if (classType() != null) {
      attributes.put("type", classType().getCanonicalName() + ".class");
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
      attributes.put("minIterate", String.valueOf(minIterate));

    if (maxIterate != null)
      attributes.put("maxIterate", String.valueOf(maxIterate));
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
      if (arrayModel.classType() == null) {
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
        attributes.put("minOccurs", String.valueOf(member.minOccurs));

      if (member.maxOccurs != null)
        attributes.put("maxOccurs", String.valueOf(member.maxOccurs));

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
    if (classType() != null)
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