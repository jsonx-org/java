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
import java.util.Map;

import org.lib4j.lang.IllegalAnnotationException;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Array;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Boolean;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.Jsonx;
import org.libx4j.jsonx.runtime.BooleanElement;
import org.libx4j.jsonx.runtime.BooleanProperty;

class BooleanModel extends SimpleModel {
  public static BooleanModel declare(final Registry registry, final Jsonx.Boolean binding) {
    return registry.declare(binding).value(new BooleanModel(registry, binding), null);
  }

  public static Member referenceOrDeclare(final Registry registry, final ComplexModel referrer, final BooleanProperty property, final Field field) {
    final BooleanModel model = new BooleanModel(registry, property, field);
    final Id id = model.id();

    final BooleanModel registered = (BooleanModel)registry.getElement(id);
    return new Template(registry, getName(property.name(), field), property.nullable(), property.required(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  public static Member referenceOrDeclare(final Registry registry, final ComplexModel referrer, final BooleanElement element) {
    final BooleanModel model = new BooleanModel(registry, element);
    final Id id = model.id();

    final BooleanModel registered = (BooleanModel)registry.getElement(id);
    return new Template(registry, element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  public static BooleanModel reference(final Registry registry, final ComplexModel referrer, final $Array.Boolean binding) {
    return registry.reference(new BooleanModel(registry, binding), referrer);
  }

  public static BooleanModel reference(final Registry registry, final ComplexModel referrer, final $Boolean binding) {
    return registry.reference(new BooleanModel(registry, binding), referrer);
  }

  private final Id id;

  private BooleanModel(final Registry registry, final Jsonx.Boolean binding) {
    super(registry, null);
    this.id = new Id(binding.getTemplate$());
  }

  private BooleanModel(final Registry registry, final $Boolean binding) {
    super(registry, binding.getName$(), binding.getNullable$(), binding.getRequired$());
    this.id = new Id(this);
  }

  private BooleanModel(final Registry registry, final $Array.Boolean binding) {
    super(registry, binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.id = new Id(this);
  }

  private BooleanModel(final Registry registry, final BooleanProperty property, final Field field) {
    super(registry, property.nullable());
    if (field.getType() != Boolean.class && (field.getType() != boolean.class || property.nullable()))
      throw new IllegalAnnotationException(property, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + BooleanProperty.class.getSimpleName() + " can only be applied to fields of Boolean type or non-nullable boolean type.");

    this.id = new Id(this);
  }

  private BooleanModel(final Registry registry, final BooleanElement element) {
    super(registry, element.nullable());
    this.id = new Id(this);
  }

  @Override
  public final Id id() {
    return id;
  }

  @Override
  protected Registry.Type type() {
    return registry.getType(Boolean.class);
  }

  @Override
  protected final Class<? extends Annotation> propertyAnnotation() {
    return BooleanProperty.class;
  }

  @Override
  protected final Class<? extends Annotation> elementAnnotation() {
    return BooleanElement.class;
  }

  @Override
  protected final org.lib4j.xml.Element toXml(final Element owner, final String packageName) {
    final Map<String,String> attributes = super.toAttributes(owner, packageName);
    if (!(owner instanceof ObjectModel))
      return new org.lib4j.xml.Element("boolean", attributes, null);

    if (registry.isRegistered(id())) {
      attributes.put("xsi:type", "template");
      attributes.put("reference", id().toString());
    }
    else {
      attributes.put("xsi:type", "boolean");
    }

    return new org.lib4j.xml.Element("property", attributes, null);
  }
}