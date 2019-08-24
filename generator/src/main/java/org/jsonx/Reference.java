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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.jsonx.www.schema_0_3.xL0gluGCXAA;
import org.jsonx.www.schema_0_3.xL0gluGCXAA.$Documented;
import org.openjax.xml.api.XmlElement;
import org.w3.www._2001.XMLSchema.yAA.$Boolean;
import org.w3.www._2001.XMLSchema.yAA.$NonNegativeInteger;

final class Reference extends Member {
  private static xL0gluGCXAA.$ArrayMember.Reference element(final schema.ReferenceElement jsd) {
    final xL0gluGCXAA.$ArrayMember.Reference xsb = new xL0gluGCXAA.$ArrayMember.Reference();

    if (jsd.getNullable() != null)
      xsb.setNullable$(new xL0gluGCXAA.$ArrayMember.Reference.Nullable$(jsd.getNullable()));

    if (jsd.getMinOccurs() != null)
      xsb.setMinOccurs$(new xL0gluGCXAA.$ArrayMember.Reference.MinOccurs$(new BigInteger(jsd.getMinOccurs())));

    if (jsd.getMaxOccurs() != null)
      xsb.setMaxOccurs$(new xL0gluGCXAA.$ArrayMember.Reference.MaxOccurs$(jsd.getMaxOccurs()));

    return xsb;
  }

  private static xL0gluGCXAA.$Reference property(final schema.ReferenceProperty jsd, final String name) {
    final xL0gluGCXAA.$Reference xsb = new xL0gluGCXAA.$Reference() {
      private static final long serialVersionUID = 9188863837584292929L;

      @Override
      protected xL0gluGCXAA.$Member inherits() {
        return new xL0gluGCXAA.$ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setName$(new xL0gluGCXAA.$Reference.Name$(name));

    if (jsd.getNullable() != null)
      xsb.setNullable$(new xL0gluGCXAA.$Reference.Nullable$(jsd.getNullable()));

    if (jsd.getUse() != null)
      xsb.setUse$(new xL0gluGCXAA.$Reference.Use$(xL0gluGCXAA.$Reference.Use$.Enum.valueOf(jsd.getUse())));

    return xsb;
  }

  static xL0gluGCXAA.$ReferenceMember jsdToXsb(final schema.Reference jsd, final String name) {
    final xL0gluGCXAA.$ReferenceMember xsb;
    if (jsd instanceof schema.ReferenceElement)
      xsb = element((schema.ReferenceElement)jsd);
    else if (jsd instanceof schema.ReferenceProperty)
      xsb = property((schema.ReferenceProperty)jsd, name);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsd.getClass().getName());

    if (jsd.getDoc() != null && jsd.getDoc().length() > 0)
      xsb.setDoc$(new xL0gluGCXAA.$Documented.Doc$(jsd.getDoc()));

    if (jsd.getType() != null)
      xsb.setType$(new xL0gluGCXAA.$ReferenceMember.Type$(jsd.getType()));

    return xsb;
  }

  final Model model;

  static Deferred<Reference> defer(final Registry registry, final Declarer declarer, final xL0gluGCXAA.$Array.Reference binding, final Supplier<Model> model) {
    return new Deferred<>(null, () -> new Reference(registry, declarer, binding, model.get()));
  }

  private Reference(final Registry registry, final Declarer declarer, final xL0gluGCXAA.$Array.Reference binding, final Model model) {
    super(registry, declarer, Id.hashed("r", model.id, model.minOccurs, model.maxOccurs, model.nullable, model.use), binding.getDoc$(), binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.model = model;
  }

  static Deferred<Reference> defer(final Registry registry, final Declarer declarer, final xL0gluGCXAA.$Reference binding, final Supplier<Model> model) {
    return new Deferred<>(null, () -> new Reference(registry, declarer, binding, model.get()));
  }

  private Reference(final Registry registry, final Declarer declarer, final xL0gluGCXAA.$Reference binding, final Model model) {
    super(registry, declarer, Id.hashed("r", model.id, model.minOccurs, model.maxOccurs, model.nullable, model.use), binding.getDoc$(), binding.getName$(), binding.getNullable$(), binding.getUse$());
    this.model = model;
  }

  static Deferred<Reference> defer(final Registry registry, final Declarer declarer, final $Documented.Doc$ doc, final $Boolean nullable, final $NonNegativeInteger minOccurs, final xL0gluGCXAA.$MaxOccurs maxOccurs, final Supplier<Model> model) {
    return new Deferred<>(null, () -> new Reference(registry, declarer, doc, nullable, minOccurs, maxOccurs, model.get()));
  }

  private Reference(final Registry registry, final Declarer declarer, final $Documented.Doc$ doc, final $Boolean nullable, final $NonNegativeInteger minOccurs, final xL0gluGCXAA.$MaxOccurs maxOccurs, final Model model) {
    super(registry, declarer, Id.hashed("r", model.id, model.minOccurs, model.maxOccurs, model.nullable, model.use), doc, nullable, minOccurs, maxOccurs);
    this.model = model;
  }

  static Deferred<Reference> defer(final Registry registry, final Declarer declarer, final String name, final Boolean nullable, final Use use, final Supplier<Model> model) {
    return new Deferred<>(name, () -> new Reference(registry, declarer, name, nullable, use, model.get()));
  }

  Reference(final Registry registry, final Declarer declarer, final String name, final Boolean nullable, final Use use, final Model model) {
    super(registry, declarer, Id.hashed("r", model.id, model.minOccurs, model.maxOccurs, model.nullable, model.use), null, name, nullable, use, null, null);
    this.model = model;
  }

  static Deferred<Reference> defer(final Registry registry, final Declarer declarer, final boolean nullable, final Integer minOccurs, final Integer maxOccurs, final Supplier<Model> model) {
    return new Deferred<>(null, () -> new Reference(registry, declarer, nullable, minOccurs, maxOccurs, model.get()));
  }

  Reference(final Registry registry, final Declarer declarer, final boolean nullable, final Integer minOccurs, final Integer maxOccurs, final Model model) {
    super(registry, declarer, Id.hashed("r", model.id, model.minOccurs, model.maxOccurs, model.nullable, model.use), null, null, nullable, null, minOccurs, maxOccurs);
    this.model = model;
  }

  @Override
  Registry.Type type() {
    return model.type();
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
  Class<? extends Annotation> propertyAnnotation() {
    return model.propertyAnnotation();
  }

  @Override
  Class<? extends Annotation> elementAnnotation() {
    return model.elementAnnotation();
  }

  @Override
  XmlElement toXml(final Settings settings, final Element owner, final String packageName) {
    final Map<String,Object> attributes = toAttributes(owner, packageName);
    if (!registry.isRootMember(model, settings)) {
      final XmlElement element = model.toXml(settings, owner, packageName);
      // It is necessary to remove the nullable, use, minOccurs and maxOccurs attributes,
      // because the template object is responsible for these attributes, and it may have happened
      // that when the reflection mechanism constructed the model, it used a declaration that had
      // these attributes set as well
      element.getAttributes().remove("minOccurs");
      element.getAttributes().remove("maxOccurs");
      element.getAttributes().remove("nullable");
      element.getAttributes().remove("use");
      element.getAttributes().putAll(attributes);
      return element;
    }

    if (model != null) {
      final String subName = Registry.getSubName(model.id.toString(), packageName);
      attributes.put("type", subName);
    }

    if (!(owner instanceof ObjectModel))
      return new XmlElement(elementName(), attributes, null);

    attributes.put("xsi:type", elementName());
    return new XmlElement("property", attributes, null);
  }

  @Override
  Map<String,Object> toJson(final Settings settings, final Element owner, final String packageName) {
    final Map<String,Object> properties = new LinkedHashMap<>();
    properties.put("jx:type", elementName());

    final Map<String,Object> attributes = toXml(settings, owner, packageName).getAttributes();
    attributes.remove(nameName());
    attributes.remove("xsi:type");

    properties.putAll(attributes);
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
}