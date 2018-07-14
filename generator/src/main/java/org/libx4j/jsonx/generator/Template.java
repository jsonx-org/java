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
import java.util.Set;

import org.lib4j.xml.Attribute;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Array;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$MaxCardinality;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Template;
import org.w3.www._2001.XMLSchema.yAA.$Boolean;
import org.w3.www._2001.XMLSchema.yAA.$NonNegativeInteger;

class Template extends Member {
  private final Member model;
  private final Id id;

  public Template(final Registry registry, final $Array.Template binding, final Member model) {
    super(registry, null, binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.model = model;
    this.id = new Id(this);
  }

  public Template(final Registry registry, final $Template binding, final Member model) {
    super(registry, binding.getName$(), binding.getNullable$(), binding.getRequired$());
    this.model = model;
    this.id = new Id(this);
  }

  public Template(final Registry registry, final String name, final boolean nullable, final boolean required, final Model model) {
    super(registry, name, nullable, required, null, null);
    this.model = model;
    this.id = new Id(this);
  }

  public Template(final Registry registry, final boolean nullable, final Integer minOccurs, final Integer maxOccurs, final Model model) {
    super(registry, nullable, minOccurs, maxOccurs);
    this.model = model;
    this.id = new Id(this);
  }

  public Template(final Registry registry, final $Boolean nullable, final $NonNegativeInteger minOccurs, final $MaxCardinality maxOccurs, final Model model) {
    super(registry, null, nullable, minOccurs, maxOccurs);
    this.model = model;
    this.id = new Id(this);
  }

  @Override
  public final Id id() {
    return id;
  }

  public final Member reference() {
    return this.model;
  }

  @Override
  protected final Registry.Type type() {
    return model.type();
  }

  @Override
  protected final Class<? extends Annotation> propertyAnnotation() {
    return model.propertyAnnotation();
  }

  @Override
  protected final Class<? extends Annotation> elementAnnotation() {
    return model.elementAnnotation();
  }

  @Override
  protected final String toJson(final String packageName) {
    final StringBuilder builder = new StringBuilder(super.toJson(packageName));
    if (builder.length() > 0)
      builder.insert(0, ",\n");

    if (model != null)
      builder.append(",\n  reference: \"").append(Registry.getSubName(model.id().toString(), packageName)).append('"');

    return "{\n" + (builder.length() > 0 ? builder.substring(2) : builder.toString()) + "\n}";
  }

  @Override
  protected final org.lib4j.xml.Element toXml(final Element owner, final String packageName) {
    final Set<Attribute> attributes = super.toAttributes(owner, packageName);
    if (model instanceof ObjectModel && registry.getNumReferrers(model) == 1) {
      // FIXME: This is where the nullable attribute collision _may happen_
      final org.lib4j.xml.Element element = model.toXml(owner, packageName);
      element.getAttributes().addAll(attributes);
      return element;
    }

    if (model != null)
      attributes.add(new Attribute("reference", Registry.getSubName(model.id().toString(), packageName)));

    if (!(owner instanceof ObjectModel))
      return new org.lib4j.xml.Element("template", attributes, null);

    attributes.add(new Attribute("xsi:type", "template"));
    return new org.lib4j.xml.Element("property", attributes, null);
  }

  @Override
  protected final void toAnnotation(final Attributes attributes) {
    super.toAnnotation(attributes);
    model.toAnnotation(attributes);
  }

  @Override
  protected String toElementAnnotations() {
    return model.toElementAnnotations();
  }
}