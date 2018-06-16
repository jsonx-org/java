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

  public static BooleanModel referenceOrDeclare(final BooleanProperty booleanProperty, final Field field) {
    return new BooleanModel(booleanProperty, field);
  }

  public static BooleanModel referenceOrDeclare(final BooleanElement booleanElement) {
    return new BooleanModel(booleanElement);
  }

  public static BooleanModel reference(final $Array.Boolean binding) {
    return new BooleanModel(binding);
  }

  public static BooleanModel reference(final $Boolean binding) {
    return new BooleanModel(binding);
  }

  private BooleanModel(final Jsonx.Boolean binding) {
    super(binding.getTemplate$().text(), binding.getNullable$().text(), null, null, null, binding.getDoc$() == null ? null : binding.getDoc$().text());
  }

  private BooleanModel(final $Boolean binding) {
    super(binding, binding.getName$().text(), binding.getNullable$().text(), binding.getRequired$().text());
  }

  private BooleanModel(final $Array.Boolean binding) {
    super(binding, binding.getNullable$().text(), binding.getMinOccurs$(), binding.getMaxOccurs$());
  }

  private BooleanModel(final BooleanProperty booleanProperty, final Field field) {
    super(getName(booleanProperty.name(), field), booleanProperty.nullable(), booleanProperty.required(), null, null, booleanProperty.doc());
    if (field.getType() != Boolean.class && (field.getType() != boolean.class || booleanProperty.nullable()))
      throw new IllegalAnnotationException(booleanProperty, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + BooleanProperty.class.getSimpleName() + " can only be applied to fields of Boolean type or non-nullable boolean type.");
  }

  private BooleanModel(final BooleanElement booleanElement) {
    super(null, booleanElement.nullable(), null, booleanElement.minOccurs(), booleanElement.maxOccurs(), booleanElement.doc());
  }

  private BooleanModel(final Element element) {
    super(element);
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
  protected BooleanModel merge(final Template reference) {
    return new BooleanModel(reference);
  }

  @Override
  protected BooleanModel normalize(final Registry registry) {
    return this;
  }

  @Override
  protected final String toJSON(final String packageName) {
    return "{\n" + super.toJSON(packageName) + "\n}";
  }

  @Override
  protected final String toJSONX(final Member owner, final String packageName) {
    return new StringBuilder(owner instanceof ObjectModel ? "<property xsi:type=\"boolean\"" : "<boolean").append(super.toJSONX(owner, packageName)).append("/>").toString();
  }
}