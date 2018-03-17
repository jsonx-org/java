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
import org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.$Array;
import org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.$Object;
import org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx;
import org.libx4j.jsonx.runtime.BooleanElement;
import org.libx4j.jsonx.runtime.BooleanProperty;

class BooleanModel extends SimpleModel {
  public static BooleanModel reference(final Registry registry, final ComplexModel referrer, final $Array.Boolean binding) {
    return new BooleanModel(binding);
  }

  // Annullable, Recurrable
  private BooleanModel(final $Array.Boolean binding) {
    super(binding, binding.getNullable$().text(), binding.getMinOccurs$(), binding.getMaxOccurs$());
  }

  public static BooleanModel reference(final Registry registry, final ComplexModel referrer, final $Object.Boolean binding) {
    return new BooleanModel(binding);
  }

  // Nameable, Annullable
  private BooleanModel(final $Object.Boolean binding) {
    super(binding, binding.getName$().text(), binding.getRequired$().text(), binding.getNullable$().text());
  }

  public static BooleanModel declare(final Registry registry, final Jsonx.Boolean binding) {
    return registry.declare(binding).value(new BooleanModel(binding), null);
  }

  // Nameable
  private BooleanModel(final Jsonx.Boolean binding) {
    super(binding.getName$().text(), null, null, null, null);
  }

  public static BooleanModel referenceOrDeclare(final Registry registry, final ComplexModel referrer, final BooleanProperty booleanProperty, final Field field) {
    return new BooleanModel(booleanProperty, field);
  }

  private BooleanModel(final BooleanProperty booleanProperty, final Field field) {
    super(getName(booleanProperty.name(), field), booleanProperty.required(), booleanProperty.nullable(), null, null);
    if (field.getType() != Boolean.class && (field.getType() != boolean.class || booleanProperty.nullable()))
      throw new IllegalAnnotationException(booleanProperty, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + BooleanProperty.class.getSimpleName() + " can only be applied to fields of Boolean type or non-nullable boolean type.");
  }

  public static BooleanModel referenceOrDeclare(final Registry registry, final ComplexModel referrer, final BooleanElement booleanElement) {
    return new BooleanModel(booleanElement);
  }

  private BooleanModel(final BooleanElement booleanElement) {
    super(null, null, booleanElement.nullable(), booleanElement.minOccurs(), booleanElement.maxOccurs());
  }

  private BooleanModel(final Element element) {
    super(element);
  }

  @Override
  protected String className() {
    return Boolean.class.getName();
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
  protected BooleanModel merge(final RefElement propertyElement) {
    return new BooleanModel(propertyElement);
  }

  @Override
  protected BooleanModel normalize(final Registry registry) {
    return this;
  }

  @Override
  protected final String toJSON(final String pacakgeName) {
    return "{\n" + super.toJSON(pacakgeName) + "\n}";
  }

  @Override
  protected final String toJSONX(final String pacakgeName) {
    return new StringBuilder("<boolean").append(super.toJSONX(pacakgeName)).append("/>").toString();
  }
}