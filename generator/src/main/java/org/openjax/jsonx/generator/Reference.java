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
import java.util.List;
import java.util.Map;

import org.openjax.jsonx.jsonx_0_9_8.xL3gluGCXYYJc.$Array;
import org.openjax.jsonx.jsonx_0_9_8.xL3gluGCXYYJc.$MaxOccurs;
import org.openjax.jsonx.jsonx_0_9_8.xL3gluGCXYYJc.$Reference;
import org.openjax.jsonx.runtime.Use;
import org.w3.www._2001.XMLSchema.yAA.$Boolean;
import org.w3.www._2001.XMLSchema.yAA.$NonNegativeInteger;

final class Reference extends Member {
  final Member model;
  private final Id id;

  Reference(final Registry registry, final $Array.Reference binding, final Member model) {
    super(registry, binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.model = model;
    this.id = new Id(this);
  }

  Reference(final Registry registry, final $Reference binding, final Model model) {
    super(registry, binding.getName$(), binding.getNullable$(), binding.getUse$());
    this.model = model;
    this.id = new Id(this);
  }

  Reference(final Registry registry, final String name, final Boolean nullable, final Use use, final Model model) {
    super(registry, name, nullable, use, null, null);
    this.model = model;
    this.id = new Id(this);
  }

  Reference(final Registry registry, final boolean nullable, final Integer minOccurs, final Integer maxOccurs, final Model model) {
    super(registry, null, nullable, null, minOccurs, maxOccurs);
    this.model = model;
    this.id = new Id(this);
  }

  Reference(final Registry registry, final $Boolean nullable, final $NonNegativeInteger minOccurs, final $MaxOccurs maxOccurs, final Model model) {
    super(registry, nullable, minOccurs, maxOccurs);
    this.model = model;
    this.id = new Id(this);
  }

  @Override
  Id id() {
    return id;
  }

  @Override
  Registry.Type type() {
    return model.type();
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
  org.openjax.classic.xml.api.Element toXml(final Settings settings, final Element owner, final String packageName) {
    final Map<String,String> attributes = super.toXmlAttributes(owner, packageName);
    if (!registry.isRootMember(model, settings)) {
      final org.openjax.classic.xml.api.Element element = model.toXml(settings, owner, packageName);
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
      final String subName = Registry.getSubName(model.id().toString(), packageName);
      attributes.put("type", subName);
    }

    if (!(owner instanceof ObjectModel))
      return new org.openjax.classic.xml.api.Element(elementName(), attributes, null);

    attributes.put("xsi:type", elementName());
    return new org.openjax.classic.xml.api.Element("property", attributes, null);
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