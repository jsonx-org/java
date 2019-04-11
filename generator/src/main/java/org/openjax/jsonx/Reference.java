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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc;
import org.openjax.standard.xml.api.XmlElement;
import org.w3.www._2001.XMLSchema.yAA.$Boolean;
import org.w3.www._2001.XMLSchema.yAA.$NonNegativeInteger;

final class Reference extends Member {
  private static xL4gluGCXYYJc.$ArrayMember.Reference element(final schema.ReferenceElement jsonx) {
    final xL4gluGCXYYJc.$ArrayMember.Reference xsb = new xL4gluGCXYYJc.$ArrayMember.Reference();

    if (jsonx.getJsd_3aNullable() != null)
      xsb.setNullable$(new xL4gluGCXYYJc.$ArrayMember.Reference.Nullable$(jsonx.getJsd_3aNullable()));

    if (jsonx.getJsd_3aMinOccurs() != null)
      xsb.setMinOccurs$(new xL4gluGCXYYJc.$ArrayMember.Reference.MinOccurs$(Integer.parseInt(jsonx.getJsd_3aMinOccurs())));

    if (jsonx.getJsd_3aMaxOccurs() != null)
      xsb.setMaxOccurs$(new xL4gluGCXYYJc.$ArrayMember.Reference.MaxOccurs$(jsonx.getJsd_3aMaxOccurs()));

    return xsb;
  }

  private static xL4gluGCXYYJc.$Reference property(final schema.ReferenceProperty jsonx, final String name) {
    final xL4gluGCXYYJc.$Reference xsb = new xL4gluGCXYYJc.$Reference() {
      private static final long serialVersionUID = 9188863837584292929L;

      @Override
      protected xL4gluGCXYYJc.$Member inherits() {
        return new xL4gluGCXYYJc.$ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setName$(new xL4gluGCXYYJc.$Reference.Name$(name));

    if (jsonx.getJsd_3aNullable() != null)
      xsb.setNullable$(new xL4gluGCXYYJc.$Reference.Nullable$(jsonx.getJsd_3aNullable()));

    if (jsonx.getJsd_3aUse() != null)
      xsb.setUse$(new xL4gluGCXYYJc.$Reference.Use$(xL4gluGCXYYJc.$Reference.Use$.Enum.valueOf(jsonx.getJsd_3aUse())));

    return xsb;
  }

  static xL4gluGCXYYJc.$ReferenceMember jsdToXsb(final schema.Reference jsd, final String name) {
    final xL4gluGCXYYJc.$ReferenceMember xsb;
    if (jsd instanceof schema.ReferenceElement)
      xsb = element((schema.ReferenceElement)jsd);
    else if (jsd instanceof schema.ReferenceProperty)
      xsb = property((schema.ReferenceProperty)jsd, name);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsd.getClass().getName());

    if (jsd.getJsd_3aType() != null)
      xsb.setType$(new xL4gluGCXYYJc.$ReferenceMember.Type$(jsd.getJsd_3aType()));

    return xsb;
  }

  final Model model;

  static Deferred<Reference> defer(final Registry registry, final xL4gluGCXYYJc.$Array.Reference binding, final Supplier<Model> model) {
    return new Deferred<>(null, () -> new Reference(registry, binding, model.get()));
  }

  private Reference(final Registry registry, final xL4gluGCXYYJc.$Array.Reference binding, final Model model) {
    super(registry, Id.hashed("r", model.id, model.minOccurs, model.maxOccurs, model.nullable, model.use), binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.model = model;
  }

  static Deferred<Reference> defer(final Registry registry, final xL4gluGCXYYJc.$Reference binding, final Supplier<Model> model) {
    return new Deferred<>(null, () -> new Reference(registry, binding, model.get()));
  }

  private Reference(final Registry registry, final xL4gluGCXYYJc.$Reference binding, final Model model) {
    super(registry, Id.hashed("r", model.id, model.minOccurs, model.maxOccurs, model.nullable, model.use), binding.getName$(), binding.getNullable$(), binding.getUse$());
    this.model = model;
  }

  static Deferred<Reference> defer(final Registry registry, final $Boolean nullable, final $NonNegativeInteger minOccurs, final xL4gluGCXYYJc.$MaxOccurs maxOccurs, final Supplier<Model> model) {
    return new Deferred<>(null, () -> new Reference(registry, nullable, minOccurs, maxOccurs, model.get()));
  }

  private Reference(final Registry registry, final $Boolean nullable, final $NonNegativeInteger minOccurs, final xL4gluGCXYYJc.$MaxOccurs maxOccurs, final Model model) {
    super(registry, Id.hashed("r", model.id, model.minOccurs, model.maxOccurs, model.nullable, model.use), nullable, minOccurs, maxOccurs);
    this.model = model;
  }

  static Deferred<Reference> defer(final Registry registry, final String name, final Boolean nullable, final Use use, final Supplier<Model> model) {
    return new Deferred<>(name, () -> new Reference(registry, name, nullable, use, model.get()));
  }

  Reference(final Registry registry, final String name, final Boolean nullable, final Use use, final Model model) {
    super(registry, Id.hashed("r", model.id, model.minOccurs, model.maxOccurs, model.nullable, model.use), name, nullable, use, null, null);
    this.model = model;
  }

  static Deferred<Reference> defer(final Registry registry, final boolean nullable, final Integer minOccurs, final Integer maxOccurs, final Supplier<Model> model) {
    return new Deferred<>(null, () -> new Reference(registry, nullable, minOccurs, maxOccurs, model.get()));
  }

  Reference(final Registry registry, final boolean nullable, final Integer minOccurs, final Integer maxOccurs, final Model model) {
    super(registry, Id.hashed("r", model.id, model.minOccurs, model.maxOccurs, model.nullable, model.use), null, nullable, null, minOccurs, maxOccurs);
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
  XmlElement toXml(final Settings settings, final Element owner, final String prefix, final String packageName) {
    final Map<String,Object> attributes = toAttributes(owner, prefix, packageName);
    if (!registry.isRootMember(model, settings)) {
      final XmlElement element = model.toXml(settings, owner, prefix, packageName);
      // It is necessary to remove the nullable, use, minOccurs and maxOccurs attributes,
      // because the template object is responsible for these attributes, and it may have happened
      // that when the reflection mechanism constructed the model, it used a declaration that had
      // these attributes set as well
      element.getAttributes().remove(prefix + "minOccurs");
      element.getAttributes().remove(prefix + "maxOccurs");
      element.getAttributes().remove(prefix + "nullable");
      element.getAttributes().remove(prefix + "use");
      element.getAttributes().putAll(attributes);
      return element;
    }

    if (model != null) {
      final String subName = Registry.getSubName(model.id.toString(), packageName);
      attributes.put(prefix + "type", subName);
    }

    if (!(owner instanceof ObjectModel))
      return new XmlElement(elementName(), attributes, null);

    attributes.put("xsi:type", elementName());
    return new XmlElement("property", attributes, null);
  }

  @Override
  Map<String,Object> toJson(final Settings settings, final Element owner, final String packageName) {
    final Map<String,Object> properties = new LinkedHashMap<>();
    properties.put("jsd:class", elementName());

    final Map<String,Object> attributes = toXml(settings, owner, "jsd:", packageName).getAttributes();
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
  List<AnnotationSpec> toElementAnnotations() {
    return model.toElementAnnotations();
  }
}