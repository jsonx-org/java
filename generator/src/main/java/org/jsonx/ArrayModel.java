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
import java.lang.annotation.Retention;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.RandomAccess;
import java.util.Set;

import org.jaxsb.runtime.Bindings;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Array;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$ArrayMember;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$ArrayMember.MinIterate$;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Binding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Documented;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$FieldBinding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Member;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$ObjectMember;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.Schema;
import org.libj.lang.AnnotationParameterException;
import org.libj.lang.Classes;
import org.libj.lang.IllegalAnnotationException;
import org.libj.util.ArrayUtil;
import org.libj.util.CollectionUtil;
import org.libj.util.Iterators;
import org.openjax.xml.api.XmlElement;

final class ArrayModel extends Referrer<ArrayModel> {
  private static Schema.Array type(final String name) {
    final Schema.Array xsb = new Schema.Array();
    if (name != null)
      xsb.setName$(new Schema.Array.Name$(name));

    return xsb;
  }

  private static $Array property(final schema.ArrayProperty jsd, final String name) {
    final $Array xsb = new $Array() {
      @Override
      protected $Member inherits() {
        return new $ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setName$(new $Array.Name$(name));

    final Boolean nullable = jsd.getNullable();
    if (nullable != null)
      xsb.setNullable$(new $Array.Nullable$(nullable));

    final String use = jsd.getUse();
    if (use != null)
      xsb.setUse$(new $Array.Use$($Array.Use$.Enum.valueOf(use)));

    final List<schema.FieldBinding> bindings = jsd.getBindings();
    final int i$;
    if (bindings != null && (i$ = bindings.size()) > 0) {
      if (bindings instanceof RandomAccess)
        for (int i = 0; i < i$; ++i) // [RA]
          addBinding(xsb, bindings.get(i));
      else
        for (final schema.FieldBinding binding : bindings) // [L]
          addBinding(xsb, binding);
    }

    return xsb;
  }

  private static void addBinding(final $Array xsb, final schema.FieldBinding binding) {
    final $Array.Binding bin = new $Array.Binding();
    bin.setLang$(new $Binding.Lang$(binding.getLang()));
    bin.setField$(new $Array.Binding.Field$(binding.getField()));
    xsb.addBinding(bin);
  }

  private static $ArrayMember.Array element(final schema.ArrayElement jsd) {
    final $ArrayMember.Array xsb = new $ArrayMember.Array();

    final Boolean nullable = jsd.getNullable();
    if (nullable != null)
      xsb.setNullable$(new $ArrayMember.Array.Nullable$(nullable));

    final String minOccurs = jsd.getMinOccurs();
    if (minOccurs != null)
      xsb.setMinOccurs$(new $ArrayMember.Array.MinOccurs$(new BigInteger(minOccurs)));

    final String maxOccurs = jsd.getMaxOccurs();
    if (maxOccurs != null)
      xsb.setMaxOccurs$(new $ArrayMember.Array.MaxOccurs$(maxOccurs));

    // There is no element binding for "array"

    return xsb;
  }

  static $ArrayMember jsdToXsb(final schema.Array jsd, final String name) {
    final $ArrayMember xsb;
    if (jsd instanceof schema.ArrayProperty)
      xsb = property((schema.ArrayProperty)jsd, name);
    else if (jsd instanceof schema.ArrayElement)
      xsb = element((schema.ArrayElement)jsd);
    else if (name != null)
      xsb = type(name);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsd.getClass().getName());

    final String doc = jsd.getDoc();
    if (doc != null && doc.length() > 0)
      xsb.setDoc$(new $Documented.Doc$(doc));

    final String minIterate = jsd.getMinIterate();
    if (minIterate != null)
      xsb.setMinIterate$(new $ArrayMember.MinIterate$(new BigInteger(minIterate)));

    final String maxIterate = jsd.getMaxIterate();
    if (maxIterate != null)
      xsb.setMaxIterate$(new $ArrayMember.MaxIterate$(maxIterate));

    final List<org.jsonx.schema.Member> elements = jsd.getElements();
    final int i$;
    if (elements != null && (i$ = elements.size()) > 0) {
      if (elements instanceof RandomAccess)
        for (int i = 0; i < i$; ++i) // [RA]
          addElement(xsb, elements.get(i));
      else
        for (final Object element : elements) // [L]
          addElement(xsb, element);
    }

    return xsb;
  }

  private static void addElement(final $ArrayMember xsb, final Object element) {
    if (element instanceof schema.AnyElement)
      xsb.addAny(($ArrayMember.Any)AnyModel.jsdToXsb((schema.AnyElement)element, null));
    else if (element instanceof schema.ArrayElement)
      xsb.addArray(($ArrayMember.Array)ArrayModel.jsdToXsb((schema.ArrayElement)element, null));
    else if (element instanceof schema.BooleanElement)
      xsb.addBoolean(($ArrayMember.Boolean)BooleanModel.jsdToXsb((schema.BooleanElement)element, null));
    else if (element instanceof schema.NumberElement)
      xsb.addNumber(($ArrayMember.Number)NumberModel.jsdToXsb((schema.NumberElement)element, null));
    else if (element instanceof schema.ReferenceElement)
      xsb.addReference(($ArrayMember.Reference)Reference.jsdToXsb((schema.ReferenceElement)element, null));
    else if (element instanceof schema.StringElement)
      xsb.addString(($ArrayMember.String)StringModel.jsdToXsb((schema.StringElement)element, null));
    else
      throw new UnsupportedOperationException("Unsupported JSONx type: " + element.getClass().getName());
  }

  static ArrayModel declare(final Registry registry, final Declarer declarer, final Schema.Array xsb) {
    return registry.declare(xsb).value(new ArrayModel(registry, declarer, xsb), null);
  }

  private static ArrayModel referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final Annotation annotation, final int minIterate, final int maxIterate, final Class<? extends Annotation> annotationType, final Id id, final Registry.Type type) {
    final ArrayModel registered = (ArrayModel)registry.getModel(id);
    if (registered != null)
      return registry.reference(registered, referrer);

    final ArrayType arrayType = annotationType.getAnnotation(ArrayType.class);
    if (arrayType == null)
      throw new IllegalAnnotationException(annotation, annotationType.getName() + " does not specify the required @" + ArrayType.class.getSimpleName() + " annotation");

    return registry.declare(id).value(newArrayModel(registry, referrer, arrayType, minIterate, maxIterate, arrayType.elementIds(), annotationType.getAnnotations(), type, annotationType.getName()), referrer);
  }

  static Member referenceOrDeclare(final Registry registry, final Declarer declarer, final Class<? extends Annotation> cls) {
    final Id id = Id.named(cls);
    if (registry.isPending(id))
      return new Deferred<>(false, null, null, () -> registry.reference(registry.getModel(id), null));

    final ArrayModel model = (ArrayModel)registry.getModel(id);
    if (model != null)
      return registry.reference(model, null);

    final ArrayType arrayType = cls.getAnnotation(ArrayType.class);
    if (arrayType == null)
      throw new IllegalArgumentException("Class " + cls.getName() + " does not specify the @" + ArrayType.class.getSimpleName() + " annotation");

    return registry.declare(id).value(newArrayModel(registry, declarer, arrayType, arrayType.minIterate(), arrayType.maxIterate(), arrayType.elementIds(), cls.getAnnotations(), registry.getType(cls), cls.getName()), null);
  }

  static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final ArrayProperty property, final Method getMethod, final String fieldName) {
    final String declaringTypeName = getMethod.getDeclaringClass().getName() + "." + fieldName;
    if (!isAssignable(getMethod, true, List.class, false, property.nullable(), property.use()))
      throw new IllegalAnnotationException(property, declaringTypeName + ": @" + ArrayProperty.class.getSimpleName() + " can only be applied to fields of List<?> type with use=\"required\" or nullable=false, or of Optional<? extends List<?>> type with use=\"optional\" and nullable=true");

    return referenceOrDeclare(registry, referrer, property, fieldName, Classes.getAnnotations(getMethod), declaringTypeName);
  }

  private static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final ArrayProperty property, final String fieldName, final Annotation[] fieldAnnotations, final String declaringTypeName) {
    if (ArrayType.class.equals(property.type())) {
      final ArrayModel model = newArrayModel(registry, referrer, property, property.minIterate(), property.maxIterate(), property.elementIds(), fieldAnnotations, null, declaringTypeName);
      final Id id = model.id();

      final ArrayModel registered = (ArrayModel)registry.getModel(id);
      return new Reference(registry, referrer, property.name(), property.nullable(), property.use(), fieldName, model.typeBinding, registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
    }

    final Registry.Type type = registry.getType(property.type());
    final Id id = Id.named(type);

    if (registry.isPending(id))
      return Reference.defer(registry, referrer, property.name(), property.nullable(), property.use(), fieldName, null, () -> registry.getModel(id));

    final ArrayModel model = referenceOrDeclare(registry, referrer, property, property.minIterate(), property.maxIterate(), property.type(), id, type);
    return new Reference(registry, referrer, property.name(), property.nullable(), property.use(), fieldName, model.typeBinding, model);
  }

  private static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final ArrayElement arrayElement, final Map<Integer,Annotation> idToElement, final String declaringTypeName) {
    if (ArrayType.class.equals(arrayElement.type())) {
      final ArrayModel model = new ArrayModel(registry, referrer, arrayElement, idToElement, declaringTypeName);
      final Id id = model.id();

      final ArrayModel registered = (ArrayModel)registry.getModel(id);
      return new Reference(registry, referrer, arrayElement.nullable(), arrayElement.minOccurs(), arrayElement.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
    }

    final Registry.Type type = registry.getType(arrayElement.type());
    final Id id = Id.named(type);

    if (registry.isPending(id))
      return Reference.defer(registry, referrer, arrayElement.nullable(), arrayElement.minOccurs(), arrayElement.maxOccurs(), () -> registry.getModel(id));

    final ArrayModel model = referenceOrDeclare(registry, referrer, arrayElement, arrayElement.minIterate(), arrayElement.maxIterate(), arrayElement.type(), id, type);
    return new Reference(registry, referrer, arrayElement.nullable(), arrayElement.minOccurs(), arrayElement.maxOccurs(), model);
  }

  static ArrayModel reference(final Registry registry, final Referrer<?> referrer, final $Array.Array xsb) {
    return registry.reference(new ArrayModel(registry, referrer, xsb), referrer);
  }

  static ArrayModel reference(final Registry registry, final Referrer<?> referrer, final $Array xsb) {
    return registry.reference(new ArrayModel(registry, referrer, xsb, getBinding(xsb.getBinding())), referrer);
  }

  private void writeElementIdsClause(final AttributeMap attributes, final int[] indices) {
    writeIterateClauses(attributes);
    attributes.put("elementIds", indices.length == 0 ? "{}" : "{" + ArrayUtil.toString(indices, ", ") + "}");
  }

  private static ArrayList<Member> parseMembers(final Registry registry, final ArrayModel referrer, final $ArrayMember xsb) {
    final ArrayList<Member> members = new ArrayList<>();
    final Iterator<? super $Member> iterator = Iterators.filter(xsb.elementIterator(), m -> m instanceof $Member);
    while (iterator.hasNext()) {
      final $Member member = ($Member)iterator.next();
      if (member instanceof $Array.Any) {
        members.add(AnyModel.reference(registry, referrer, ($Array.Any)member));
      }
      else if (member instanceof $Array.Array) {
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
        members.add(Reference.defer(registry, referrer, reference, () -> {
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

  private static ArrayList<Member> parseMembers(final Registry registry, final ArrayModel referrer, final Annotation annotation, final int[] elementIds, final Map<Integer,Annotation> idToElement, final String declaringTypeName) {
    final ArrayList<Member> members = new ArrayList<>();
    for (final int elementId : elementIds) { // [A]
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

  final ArrayList<Member> members;
  final Integer minIterate;
  final Integer maxIterate;

  private static Integer parseIterate(final BigInteger minIterate) {
    return minIterate == null ? null : parseIterate(minIterate.intValue());
  }

  private static Integer parseIterate(final Integer minIterate) {
    return minIterate == null || minIterate == 1 ? null : minIterate;
  }

  private ArrayModel(final Registry registry, final Declarer declarer, final Schema.Array xsb) {
    super(registry, declarer, registry.getType(Registry.Kind.ANNOTATION, registry.packageName, registry.classPrefix + JsdUtil.flipName(xsb.getName$().text())), xsb.getDoc$(), xsb.getName$().text());
    this.members = parseMembers(registry, this, xsb);
    final MinIterate$ minIterate$ = xsb.getMinIterate$();
    this.minIterate = parseIterate(minIterate$.text());
    this.maxIterate = parseIterate(parseMaxCardinality(minIterate$.text(), xsb.getMaxIterate$(), "Iterate", 1));

    validateTypeBinding();
  }

  private ArrayModel(final Registry registry, final Declarer declarer, final $Array xsb, final $FieldBinding binding) {
    super(registry, declarer, xsb.getDoc$(), xsb.getName$(), xsb.getNullable$(), xsb.getUse$(), null, binding == null ? null : binding.getField$(), null);
    this.members = parseMembers(registry, this, xsb);
    final MinIterate$ minIterate$ = xsb.getMinIterate$();
    this.minIterate = parseIterate(minIterate$.text());
    this.maxIterate = parseIterate(parseMaxCardinality(minIterate$.text(), xsb.getMaxIterate$(), "Iterate", 1));

    validateTypeBinding();
  }

  private ArrayModel(final Registry registry, final Declarer declarer, final $Array.Array xsb) {
    super(registry, declarer, xsb.getDoc$(), xsb.getNullable$(), xsb.getMinOccurs$(), xsb.getMaxOccurs$(), null);
    if (this.maxOccurs.get != null && this.minOccurs.get != null && this.minOccurs.get > this.maxOccurs.get)
      throw new ValidationException(Bindings.getXPath(xsb, elementXPath) + ": minOccurs=\"" + this.minOccurs.get + "\" > maxOccurs=\"" + this.maxOccurs.get + "\"");

    this.members = parseMembers(registry, this, xsb);
    this.minIterate = parseIterate(xsb.getMinIterate$().text());
    this.maxIterate = parseIterate(parseMaxCardinality(xsb.getMinIterate$().text(), xsb.getMaxIterate$(), "Iterate", 1));

    validateTypeBinding();
  }

  private static LinkedHashMap<Integer,Annotation> parseIdToElement(final Annotation[] annotations, final String declaringTypeName) {
    final HashMap<Integer,Annotation> idToElement = new HashMap<>();
    final StrictDigraph<Integer> digraph = new StrictDigraph<>("Element cannot include itself as a member");
    for (final Annotation elementAnnotation : JsdUtil.flatten(annotations)) { // [A]
      if (elementAnnotation instanceof ArrayProperty || elementAnnotation instanceof ArrayType || elementAnnotation instanceof Retention)
        continue;

      final int id;
      if (elementAnnotation instanceof AnyElement) {
        id = ((AnyElement)elementAnnotation).id();
      }
      else if (elementAnnotation instanceof ArrayElement) {
        id = ((ArrayElement)elementAnnotation).id();
        for (final Integer elementId : ((ArrayElement)elementAnnotation).elementIds()) // [A]
          digraph.add(id, elementId);
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

      digraph.add(id);
      idToElement.put(id, elementAnnotation);
    }

    final List<Integer> cycle = digraph.getCycle();
    if (cycle != null)
      throw new ValidationException("Cycle detected in element index dependency graph: " + CollectionUtil.toString(digraph.getCycle(), " -> "));

    final LinkedHashMap<Integer,Annotation> topologicalOrder = new LinkedHashMap<>(idToElement.size());
    final ArrayList<Integer> elementIds = digraph.getTopologicalOrder();
    for (int i = 0, i$ = elementIds.size(); i < i$; ++i) { // [RA]
      final Integer elementId = elementIds.get(i);
      topologicalOrder.put(elementId, idToElement.get(elementId));
    }

    return topologicalOrder;
  }

  private static ArrayModel newArrayModel(final Registry registry, final Declarer declarer, final Annotation arrayAnnotation, final int minIterate, final int maxIterate, final int[] elementIds, final Annotation[] annotations, final Registry.Type type, final String declaringTypeName) {
    final LinkedHashMap<Integer,Annotation> topologicalOrder = parseIdToElement(annotations, declaringTypeName);
    // FIXME: Is this correct?!!...
    final Id id = Id.named(type);

    final ArrayModel registered = (ArrayModel)registry.getModel(id);
    return registered != null ? registered : new ArrayModel(registry, declarer, id, type, arrayAnnotation, minIterate, maxIterate, elementIds, topologicalOrder, declaringTypeName);
  }

  private ArrayModel(final Registry registry, final Declarer declarer, final Id id, final Registry.Type type, final Annotation arrayAnnotation, final int minIterate, final int maxIterate, final int[] elementIds, final LinkedHashMap<Integer,Annotation> topologicalOrder, final String declaringTypeName) {
    super(registry, declarer, id, type);
    this.members = parseMembers(registry, this, arrayAnnotation, elementIds, topologicalOrder, declaringTypeName);
    this.minIterate = parseIterate(minIterate);
    this.maxIterate = parseIterate(maxIterate);

    validateTypeBinding();
  }

  private ArrayModel(final Registry registry, final Declarer declarer, final ArrayElement arrayElement, final Map<Integer,Annotation> idToElement, final String declaringTypeName) {
    super(registry, declarer, arrayElement.nullable(), null, null, null);
    if (arrayElement.type() != ArrayType.class)
      throw new IllegalArgumentException("This constructor is only for elementIds");

    this.members = parseMembers(registry, this, arrayElement, arrayElement.elementIds(), idToElement, declaringTypeName);
    this.minIterate = parseIterate(arrayElement.minIterate());
    this.maxIterate = parseIterate(arrayElement.maxIterate());

    validateTypeBinding();
  }

  @Override
  void resolveOverrides() {
  }

  @Override
  Registry.Type typeDefault() {
    // type can be null if there is a loop in the type graph
    final Registry.Type type = getGreatestCommonSuperType(members);
    return registry.getType(defaultClass(), (type == null ? registry.OBJECT : !type.isArray() && type.isPrimitive() ? type.getWrapper() : type).asGeneric());
  }

  @Override
  String isValid(final Binding.Type typeBinding) {
    return typeBinding.type == null ? null : "Cannot override the type for \"array\"";
  }

  @Override
  public String elementName() {
    return "array";
  }

  @Override
  Class<?> defaultClass() {
    return List.class;
  }

  @Override
  Class<? extends Annotation> propertyAnnotation() {
    return ArrayProperty.class;
  }

  @Override
  Class<? extends Annotation> elementAnnotation() {
    return ArrayElement.class;
  }

  private boolean referencesResolved;

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
  void getDeclaredTypes(final Set<? super Registry.Type> types) {
    if (classType() != null)
      types.add(classType());

    if (members != null)
      for (int i = 0, i$ = members.size(); i < i$; ++i) // [RA]
        members.get(i).getDeclaredTypes(types);
  }

  @Override
  Map<String,Object> toXmlAttributes(final Element owner, final String packageName) {
    final Map<String,Object> attributes = super.toXmlAttributes(owner, packageName);
    if (owner instanceof SchemaElement)
      attributes.put("name", classType() != null ? JsdUtil.flipName(classType().getSubName(packageName)) : id().toString());

    if (minIterate != null)
      attributes.put("minIterate", String.valueOf(minIterate));

    if (maxIterate != null)
      attributes.put("maxIterate", maxIterate == Integer.MAX_VALUE ? "unbounded" : String.valueOf(maxIterate));

    return attributes;
  }

  @Override
  XmlElement toXml(final Settings settings, final Element owner, final String packageName) {
    final XmlElement element = super.toXml(settings, owner, packageName);
    if (members.size() == 0)
      return element;

    final ArrayList<XmlElement> elements = new ArrayList<>();
    for (int i = 0, i$ = members.size(); i < i$; ++i) // [RA]
      elements.add(members.get(i).toXml(settings, this, packageName));

    if (element.getElements() != null)
      elements.addAll(element.getElements());

    element.setElements(elements);
    return element;
  }

  @Override
  Map<String,Object> toJson(final Settings settings, final Element owner, final String packageName) {
    final Map<String,Object> element = super.toJson(settings, owner, packageName);
    if (members.size() == 0)
      return element;

    final ArrayList<Object> elements = new ArrayList<>();
    for (int i = 0, i$ = members.size(); i < i$; ++i) // [RA]
      elements.add(members.get(i).toJson(settings, this, packageName));

    element.put("elements", elements);
    return element;
  }

  private void renderAnnotations(final AttributeMap attributes, final List<? super AnnotationType> annotations) {
    final int len = members.size();
    final int[] indices = new int[len];
    for (int i = 0, index = 0; i < len; ++i) { // [RA]
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

  private static int writeElementAnnotations(final List<? super AnnotationType> annotations, final Member member, final int index, final Member owner) {
    final AttributeMap attributes = new AttributeMap();
    attributes.put("id", index);

    final AnnotationType annotationSpec = new AnnotationType(member.elementAnnotation(), attributes);
    final Member reference = member instanceof Reference ? ((Reference)member).model : member;
    if (reference instanceof ArrayModel) {
      final ArrayModel arrayModel = (ArrayModel)reference;
      int offset = 1;
      final List<AnnotationType> inner = new ArrayList<>();
      if (arrayModel.classType() == null) {
        final ArrayList<Member> members = arrayModel.members;
        final int size = members.size();
        final int[] indices = new int[size];
        for (int i = 0; i < size; ++i) { // [RA]
          indices[i] = index + offset;
          offset += writeElementAnnotations(inner, members.get(i), index + offset, owner);
        }

        // FIXME: Can this be abstracted better? minOccurs, maxOccurs and nullable are rendered here and in Member#toAnnotationAttributes()
        arrayModel.toAnnotationAttributes(attributes, indices, owner);
      }
      else {
        arrayModel.toAnnotationAttributes(attributes, owner);
      }

      if (member.minOccurs.get != null)
        attributes.put("minOccurs", String.valueOf(member.minOccurs.get));

      if (member.maxOccurs.get != null)
        attributes.put("maxOccurs", String.valueOf(member.maxOccurs.get));

      if (member.nullable.get != null)
        attributes.put("nullable", member.nullable.get);

      inner.add(annotationSpec);
      annotations.addAll(0, inner);
      return offset;
    }

    member.toAnnotationAttributes(attributes, owner);
    annotations.add(0, annotationSpec);
    return 1;
  }

  @Override
  List<AnnotationType> toElementAnnotations() {
    if (classType() != null)
      return null;

    final ArrayList<AnnotationType> annotations = new ArrayList<>();
    int index = 0;
    for (int i = 0, i$ = members.size(); i < i$; ++i) // [RA]
      index += writeElementAnnotations(annotations, members.get(i), index, this);

    return annotations;
  }

  @Override
  ArrayList<AnnotationType> getClassAnnotation() {
    final AttributeMap attributes = new AttributeMap();
    final ArrayList<AnnotationType> annotations = new ArrayList<>();
    renderAnnotations(attributes, annotations);
    annotations.add(new AnnotationType(ArrayType.class, attributes));
    return annotations;
  }

  @Override
  String toSource(final Settings settings) {
    return null;
  }
}