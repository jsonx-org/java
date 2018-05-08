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
  public static BooleanModel declare(final Schema schema, final Registry registry, final Jsonx.Boolean binding) {
    return registry.declare(binding).value(new BooleanModel(schema, binding), null);
  }

  public static BooleanModel referenceOrDeclare(final Member owner, final BooleanProperty booleanProperty, final Field field) {
    return new BooleanModel(owner, booleanProperty, field);
  }

  public static BooleanModel referenceOrDeclare(final Member owner, final BooleanElement booleanElement) {
    return new BooleanModel(owner, booleanElement);
  }

  public static BooleanModel reference(final Member owner, final $Array.Boolean binding) {
    return new BooleanModel(owner, binding);
  }

  public static BooleanModel reference(final Member owner, final $Boolean binding) {
    return new BooleanModel(owner, binding);
  }

  private BooleanModel(final Schema schema, final Jsonx.Boolean binding) {
    super(schema, binding.getTemplate$().text(), binding.getNullable$().text(), null, null, null, binding.getDoc$() == null ? null : binding.getDoc$().text());
  }

  private BooleanModel(final Member owner, final $Boolean binding) {
    super(owner, binding, binding.getName$().text(), binding.getNullable$().text(), binding.getRequired$().text());
  }

  private BooleanModel(final Member owner, final $Array.Boolean binding) {
    super(owner, binding, binding.getNullable$().text(), binding.getMinOccurs$(), binding.getMaxOccurs$());
  }

  private BooleanModel(final Member owner, final BooleanProperty booleanProperty, final Field field) {
    // FIXME: Can we get doc comments from code?
    super(owner, getName(booleanProperty.name(), field), booleanProperty.required(), booleanProperty.nullable(), null, null, null);
    if (field.getType() != Boolean.class && (field.getType() != boolean.class || booleanProperty.nullable()))
      throw new IllegalAnnotationException(booleanProperty, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + BooleanProperty.class.getSimpleName() + " can only be applied to fields of Boolean type or non-nullable boolean type.");
  }

  private BooleanModel(final Member owner, final BooleanElement booleanElement) {
    // FIXME: Can we get doc comments from code?
    super(owner, null, null, booleanElement.nullable(), booleanElement.minOccurs(), booleanElement.maxOccurs(), null);
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
  protected final String toJSON(final String pacakgeName) {
    return "{\n" + super.toJSON(pacakgeName) + "\n}";
  }

  @Override
  protected final String toJSONX(final String pacakgeName) {
    return new StringBuilder(kind() == Kind.PROPERTY ? "<property xsi:type=\"boolean\"" : "<boolean").append(super.toJSONX(pacakgeName)).append("/>").toString();
  }
}