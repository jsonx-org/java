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

import org.lib4j.lang.IllegalAnnotationException;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Array;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Boolean;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.Jsonx;
import org.libx4j.jsonx.runtime.BooleanElement;
import org.libx4j.jsonx.runtime.BooleanProperty;

class BooleanModel extends SimpleModel {
  public static BooleanModel declare(final Registry registry, final Jsonx.Boolean binding) {
    return registry.declare(binding).value(new BooleanModel(binding), null);
  }

  public static Element referenceOrDeclare(final Registry registry, final ComplexModel referrer, final BooleanProperty property, final Field field) {
    final BooleanModel model = new BooleanModel(property, field);
    final Id id = model.id();

    final BooleanModel registered = (BooleanModel)registry.getElement(id);
    return new Template(getName(property.name(), field), property.nullable(), property.required(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  public static Element referenceOrDeclare(final Registry registry, final ComplexModel referrer, final BooleanElement element) {
    final BooleanModel model = new BooleanModel(element);
    final Id id = model.id();

    final BooleanModel registered = (BooleanModel)registry.getElement(id);
    return new Template(element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  public static BooleanModel reference(final Registry registry, final ComplexModel referrer, final $Array.Boolean binding) {
    return registry.reference(new BooleanModel(binding), referrer);
  }

  public static BooleanModel reference(final Registry registry, final ComplexModel referrer, final $Boolean binding) {
    return registry.reference(new BooleanModel(binding), referrer);
  }

  private final Id id;

  private BooleanModel(final Jsonx.Boolean binding) {
    super(null, null, null, null);
    this.id = new Id(binding.getTemplate$());
  }

  private BooleanModel(final $Boolean binding) {
    super(binding.getName$().text(), binding.getNullable$().text(), binding.getRequired$().text());
    this.id = new Id(this);
  }

  private BooleanModel(final $Array.Boolean binding) {
    super(binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.id = new Id(this);
  }

  private BooleanModel(final BooleanProperty property, final Field field) {
    super(property.nullable(), null, null, null);
    if (field.getType() != Boolean.class && (field.getType() != boolean.class || property.nullable()))
      throw new IllegalAnnotationException(property, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + BooleanProperty.class.getSimpleName() + " can only be applied to fields of Boolean type or non-nullable boolean type.");

    this.id = new Id(this);
  }

  private BooleanModel(final BooleanElement element) {
    super(element.nullable(), null, null, null);
    this.id = new Id(this);
  }

  @Override
  public final Id id() {
    return id;
  }

  @Override
  protected Type type() {
    return Type.get(Boolean.class);
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
  protected final String toJSON(final String packageName) {
    return "{\n" + super.toJSON(packageName) + "\n}";
  }

  @Override
  protected final String toJSONX(final Registry registry, final Member owner, final String packageName) {
    return new StringBuilder(owner instanceof ObjectModel ? "<property xsi:type=\"" + (registry.hasRegistry(id()) ? "template\" reference=\"" + id() + "\"" : "boolean\"") : "<boolean").append(super.toJSONX(registry, owner, packageName)).append("/>").toString();
  }
}