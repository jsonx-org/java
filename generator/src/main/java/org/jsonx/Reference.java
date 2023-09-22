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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.jsonx.Registry.Type;
import org.jsonx.schema.FieldBinding;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Array;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$ArrayMember;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Binding;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Documented;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$FieldBinding;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$MaxOccurs;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Member;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$ObjectMember;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Reference;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$ReferenceMember;
import org.libj.util.CollectionUtil;
import org.libj.util.Iterators;
import org.openjax.xml.api.XmlElement;
import org.w3.www._2001.XMLSchema.yAA.$Boolean;
import org.w3.www._2001.XMLSchema.yAA.$NonNegativeInteger;

final class Reference extends Member {
  private static $ArrayMember.Reference element(final schema.ReferenceElement jsd) {
    final $ArrayMember.Reference xsb = new $ArrayMember.Reference();

    final Boolean nullable = jsd.getNullable();
    if (nullable != null)
      xsb.setNullable$(new $ArrayMember.Reference.Nullable$(nullable));

    final String minOccurs = jsd.getMinOccurs();
    if (minOccurs != null)
      xsb.setMinOccurs$(new $ArrayMember.Reference.MinOccurs$(new BigInteger(minOccurs)));

    final String maxOccurs = jsd.getMaxOccurs();
    if (maxOccurs != null)
      xsb.setMaxOccurs$(new $ArrayMember.Reference.MaxOccurs$(maxOccurs));

    // There is no element binding for "array"

    return xsb;
  }

  private static $Reference property(final schema.ReferenceProperty jsd, final String name) {
    final $Reference xsb = new $Reference() {
      @Override
      protected $Member inherits() {
        return new $ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setName$(new $Reference.Name$(name));

    final Boolean nullable = jsd.getNullable();
    if (nullable != null)
      xsb.setNullable$(new $Reference.Nullable$(nullable));

    final String use = jsd.getUse();
    if (use != null)
      xsb.setUse$(new $Reference.Use$($Reference.Use$.Enum.valueOf(use)));

    final List<FieldBinding> bindings = jsd.getBindings();
    final int i$;
    if (bindings != null && (i$ = bindings.size()) > 0) {
      if (CollectionUtil.isRandomAccess(bindings)) {
        int i = 0; do // [RA]
          addBinding(xsb, bindings.get(i));
        while (++i < i$);
      }
      else {
        final Iterator<schema.FieldBinding> it = bindings.iterator(); do // [I]
          addBinding(xsb, it.next());
        while (it.hasNext());
      }
    }

    return xsb;
  }

  private static void addBinding(final $Reference xsb, final schema.FieldBinding binding) {
    final $Reference.Binding bin = new $Reference.Binding();
    bin.setLang$(new $Binding.Lang$(binding.getLang()));
    bin.setField$(new $Reference.Binding.Field$(binding.getField()));
    xsb.addBinding(bin);
  }

  static $ReferenceMember jsdToXsb(final schema.Reference jsd, final String name) {
    final $ReferenceMember xsb;
    if (jsd instanceof schema.ReferenceElement)
      xsb = element((schema.ReferenceElement)jsd);
    else if (jsd instanceof schema.ReferenceProperty)
      xsb = property((schema.ReferenceProperty)jsd, name);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsd.getClass().getName());

    final String doc = jsd.getDoc();
    if (doc != null && doc.length() > 0)
      xsb.setDoc$(new $Documented.Doc$(doc));

    final String type = jsd.getType();
    if (type != null)
      xsb.setType$(new $ReferenceMember.Type$(type));

    return xsb;
  }

  final Model model;

  static Deferred<Reference> defer(final Registry registry, final Declarer declarer, final $Array.Reference xsb, final Supplier<? extends Model> model) {
    return new Deferred<>(true, null, null, () -> new Reference(registry, declarer, xsb, model.get()));
  }

  private Reference(final Registry registry, final Declarer declarer, final $Array.Reference xsb, final Model model) {
    super(registry, declarer, true, Id.hashed("r", model.typeBinding, model.id(), model.minOccurs.get, model.maxOccurs.get, model.nullable.get, model.use.get), xsb.getDoc$(), xsb.getNullable$(), xsb.getMinOccurs$(), xsb.getMaxOccurs$(), model.typeBinding);
    this.model = model;
  }

  static Deferred<Reference> defer(final Registry registry, final Declarer declarer, final $Reference xsb, final Supplier<? extends Model> model) {
    return new Deferred<>(true, null, null, () -> new Reference(registry, declarer, xsb, model.get(), getBinding(xsb.getBinding())));
  }

  private Reference(final Registry registry, final Declarer declarer, final $Reference xsb, final Model model, final $FieldBinding binding) {
    super(registry, declarer, true, Id.hashed("r", model.typeBinding, model.id(), model.minOccurs.get, model.maxOccurs.get, model.nullable.get, model.use.get), xsb.getDoc$(), xsb.getName$(), xsb.getNullable$(), xsb.getUse$(), binding == null ? null : binding.getField$(), model.typeBinding);
    this.model = model;
    validateTypeBinding();
  }

  static Deferred<Reference> defer(final Registry registry, final Declarer declarer, final $Documented.Doc$ doc, final $Boolean nullable, final $NonNegativeInteger minOccurs, final $MaxOccurs maxOccurs, final Supplier<? extends Model> model) {
    return new Deferred<>(true, null, null, () -> new Reference(registry, declarer, doc, nullable, minOccurs, maxOccurs, model.get()));
  }

  private Reference(final Registry registry, final Declarer declarer, final $Documented.Doc$ doc, final $Boolean nullable, final $NonNegativeInteger minOccurs, final $MaxOccurs maxOccurs, final Model model) {
    super(registry, declarer, true, Id.hashed("r", model.typeBinding, model.id(), model.minOccurs.get, model.maxOccurs.get, model.nullable.get, model.use.get), doc, nullable, minOccurs, maxOccurs, model.typeBinding);
    this.model = model;
  }

  static Deferred<Reference> defer(final Registry registry, final Declarer declarer, final String name, final Boolean nullable, final Use use, final String fieldName, final Bind.Type typeBinding, final Supplier<? extends Model> model) {
    return new Deferred<>(false, name, fieldName, () -> new Reference(registry, declarer, name, nullable, use, fieldName, typeBinding, model.get()));
  }

  Reference(final Registry registry, final Declarer declarer, final String name, final Boolean nullable, final Use use, final String fieldName, final Bind.Type typeBinding, final Model model) {
    super(registry, declarer, false, Id.hashed("r", typeBinding, model.id(), model.minOccurs.get, model.maxOccurs.get, model.nullable.get, model.use.get), null, name, nullable, use, null, null, fieldName, typeBinding);
    this.model = model;
  }

  static Deferred<Reference> defer(final Registry registry, final Declarer declarer, final boolean nullable, final Integer minOccurs, final Integer maxOccurs, final Supplier<? extends Model> model) {
    return new Deferred<>(false, null, null, () -> new Reference(registry, declarer, nullable, minOccurs, maxOccurs, model.get()));
  }

  Reference(final Registry registry, final Declarer declarer, final boolean nullable, final Integer minOccurs, final Integer maxOccurs, final Model model) {
    super(registry, declarer, false, Id.hashed("r", model.typeBinding, model.id(), model.minOccurs.get, model.maxOccurs.get, model.nullable.get, model.use.get), null, null, nullable, null, minOccurs, maxOccurs, null, model.typeBinding);
    this.model = model;
  }

  @Override
  Registry.Type typeDefault(final boolean primitive) {
    return model.type();
  }

  @Override
  String isValid(final Bind.Type typeBinding) {
    return model.isValid(typeBinding);
  }

  @Override
  String nameName() {
    return model.nameName();
  }

  @Override
  String elementName() {
    return "reference";
  }

  @Override
  public String displayName() {
    return name() != null ? name() : model != null ? model.displayName() : elementName();
  }

  @Override
  Class<?> defaultClass() {
    return model.defaultClass();
  }

  @Override
  Class<? extends Annotation> propertyAnnotation() {
    return model.propertyAnnotation();
  }

  @Override
  Class<? extends Annotation> elementAnnotation() {
    return model.elementAnnotation();
  }

  @Override
  Class<? extends Annotation> typeAnnotation() {
    return model.typeAnnotation();
  }

  private Map<String,Object> getBindingAttributes(final Element owner) {
    final Map<String,Object> bindingAttributes = Bind.toXmlAttributes(owner, typeBinding, fieldBinding);
    if (bindingAttributes == null)
      return null;

    bindingAttributes.remove("type");
    bindingAttributes.remove("decode");
    bindingAttributes.remove("encode");
    // If there's only lang="java", then remove the binding element
    return bindingAttributes.size() <= 1 ? null : bindingAttributes;
  }

  @Override
  @SuppressWarnings("unchecked")
  XmlElement toXml(final Element owner, final String packageName) {
    final Map<String,Object> attributes = toXmlAttributes(owner, packageName);
    final Map<String,Object> bindingAttributes = getBindingAttributes(owner);
    if (!registry.isRootMember(model)) {
      final XmlElement element = model.toXml(owner, packageName);
      if (element.getElements() != null) {
        final XmlElement lastElement = Iterators.<XmlElement>lastElement(element.getElements().iterator());
        if ("binding".equals(lastElement.getName())) {
          // Remove "field", because the type of the reference cannot define a "field"
          lastElement.getAttributes().remove("field");
          if (bindingAttributes != null)
            lastElement.getAttributes().putAll(bindingAttributes);
        }
        else if (bindingAttributes != null) {
          element.getElements().add(new XmlElement("binding", bindingAttributes));
        }
      }
      else if (bindingAttributes != null) {
        element.setElements(CollectionUtil.asCollection(new ArrayList<>(), new XmlElement("binding", bindingAttributes)));
      }

      // It is necessary to remove the nullable, use, minOccurs and maxOccurs attributes,
      // because the template object is responsible for these attributes, and it may have happened
      // that when the reflection mechanism constructed the model, it used a declaration that had
      // these attributes set as well
      final Map<String,Object> attrs = element.getAttributes();
      attrs.remove("minOccurs");
      attrs.remove("maxOccurs");
      attrs.remove("nullable");
      attrs.remove("use");
      attrs.putAll(attributes);

      return element;
    }

    if (model != null) {
      final String subName = Registry.getSubName(model.id().toString(), packageName);
      attributes.put("type", subName);
    }

    final List<XmlElement> bindingXmls = bindingAttributes == null ? null : CollectionUtil.asCollection(new ArrayList<>(), new XmlElement("binding", bindingAttributes));
    if (!(owner instanceof ObjectModel))
      return new XmlElement(elementName(), attributes, bindingXmls);

    attributes.put("xsi:type", elementName());
    return new XmlElement("property", attributes, bindingXmls);
  }

  @Override
  Map<String,Object> toJson(final Element owner, final String packageName) {
    final LinkedHashMap<String,Object> properties = new LinkedHashMap<>();
    properties.put("jx:type", elementName());

    final Map<String,Object> attributes = toXml(owner, packageName).getAttributes();
    attributes.remove(nameName());
    attributes.remove("xsi:type");

    properties.putAll(attributes);
    final Map<String,Object> bindingAttributes = getBindingAttributes(owner);
    if (bindingAttributes != null)
      properties.put("bindings", Collections.singletonList(bindingAttributes));

    return properties;
  }

  @Override
  void toAnnotationAttributes(final AttributeMap attributes, final Member owner) {
    super.toAnnotationAttributes(attributes, owner);
    model.toAnnotationAttributes(attributes, owner);
  }

  @Override
  List<AnnotationType> toElementAnnotations() {
    return model.toElementAnnotations();
  }

  @Override
  Type typeBinding() {
    return model.typeBinding();
  }
}