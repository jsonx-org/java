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
import java.math.BigDecimal;
import java.math.BigInteger;

import org.lib4j.lang.IllegalAnnotationException;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Array;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Number;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.Jsonx;
import org.libx4j.jsonx.runtime.Form;
import org.libx4j.jsonx.runtime.NumberElement;
import org.libx4j.jsonx.runtime.NumberProperty;

class NumberModel extends SimpleModel {
  public static NumberModel declare(final Registry registry, final Jsonx.Number binding) {
    return registry.declare(binding).value(new NumberModel(binding), null);
  }

  public static NumberModel referenceOrDeclare(final NumberProperty numberProperty, final Field field) {
    return new NumberModel(numberProperty, field);
  }

  public static NumberModel referenceOrDeclare(final NumberElement numberElement) {
    return new NumberModel(numberElement);
  }

  public static NumberModel reference(final $Array.Number binding) {
    return new NumberModel(binding);
  }

  public static NumberModel reference(final $Number binding) {
    return new NumberModel(binding);
  }

  private final Form form;
  private final BigDecimal min;
  private final BigDecimal max;

  private NumberModel(final Jsonx.Number binding) {
    super(binding.getTemplate$().text(), binding.getNullable$().text(), null, null, null, binding.getDoc$() == null ? null : binding.getDoc$().text());
    this.form = Form.valueOf(binding.getForm$().text().toUpperCase());
    this.min = binding.getMin$() == null ? null : binding.getMin$().text();
    this.max = binding.getMax$() == null ? null : binding.getMax$().text();
  }

  private NumberModel(final $Number binding) {
    super(binding, binding.getName$().text(), binding.getNullable$().text(), binding.getRequired$().text());
    this.form = Form.valueOf(binding.getForm$().text().toUpperCase());
    this.min = binding.getMin$() == null ? null : binding.getMin$().text();
    this.max = binding.getMax$() == null ? null : binding.getMax$().text();
  }

  private NumberModel(final $Array.Number binding) {
    super(binding, binding.getNullable$().text(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.form = Form.valueOf(binding.getForm$().text().toUpperCase());
    this.min = binding.getMin$() == null ? null : binding.getMin$().text();
    this.max = binding.getMax$() == null ? null : binding.getMax$().text();
  }

  private NumberModel(final NumberProperty numberProperty, final Field field) {
    super(getName(numberProperty.name(), field), numberProperty.nullable(), numberProperty.required(), null, null, numberProperty.doc());
    if (!Number.class.isAssignableFrom(field.getType()) && (!field.getType().isPrimitive() || field.getType() == char.class || numberProperty.nullable()))
      throw new IllegalAnnotationException(numberProperty, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + NumberProperty.class.getSimpleName() + " can only be applied to fields of Number subtypes or primitive numeric non-nullable types.");

    this.form = numberProperty.form();
    this.min = numberProperty.min().length() == 0 ? null : new BigDecimal(numberProperty.min());
    this.max = numberProperty.max().length() == 0 ? null : new BigDecimal(numberProperty.max());
  }

  private NumberModel(final NumberElement numberElement) {
    super(null, numberElement.nullable(), null, numberElement.minOccurs(), numberElement.maxOccurs(), numberElement.doc());
    this.form = numberElement.form();
    this.min = numberElement.min().length() == 0 ? null : new BigDecimal(numberElement.min());
    this.max = numberElement.max().length() == 0 ? null : new BigDecimal(numberElement.max());
  }

  private NumberModel(final Element element, final NumberModel copy) {
    super(element);
    this.form = copy.form;
    this.min = copy.min;
    this.max = copy.max;
  }

  public final Form form() {
    return this.form;
  }

  public final BigDecimal min() {
    return this.min;
  }

  public final BigDecimal max() {
    return this.max;
  }

  @Override
  protected Type type() {
    return Type.get(form == Form.INTEGER ? BigInteger.class : BigDecimal.class);
  }

  @Override
  protected final Class<? extends Annotation> propertyAnnotation() {
    return NumberProperty.class;
  }

  @Override
  protected final Class<? extends Annotation> elementAnnotation() {
    return NumberElement.class;
  }

  @Override
  protected NumberModel merge(final Template reference) {
    return new NumberModel(reference, this);
  }

  @Override
  protected NumberModel normalize(final Registry registry) {
    return this;
  }

  @Override
  protected String toJSON(final String pacakgeName) {
    final StringBuilder builder = new StringBuilder(super.toJSON(pacakgeName));
    if (builder.length() > 0)
      builder.insert(0, ",\n");

    if (form != null)
      builder.append(",\n  form: \"").append(form.toString().toLowerCase()).append('"');

    if (min != null)
      builder.append(",\n  min: ").append(min);

    if (max != null)
      builder.append(",\n  max: ").append(max);

    return "{\n" + (builder.length() > 0 ? builder.substring(2) : builder.toString()) + "\n}";
  }

  @Override
  protected final String toJSONX(final Member owner, final String pacakgeName) {
    final StringBuilder builder = new StringBuilder(owner instanceof ObjectModel ? "<property xsi:type=\"number\"" : "<number");
    if (form != Form.REAL)
      builder.append(" form=\"").append(form.toString().toLowerCase()).append('"');

    if (min != null)
      builder.append(" min=\"").append(min).append('"');

    if (max != null)
      builder.append(" max=\"").append(max).append('"');

    return builder.append(super.toJSONX(owner, pacakgeName)).append("/>").toString();
  }

  @Override
  protected String toAnnotation(final boolean full) {
    final StringBuilder builder = full ? new StringBuilder(super.toAnnotation(full)) : new StringBuilder();
    if (form != Form.REAL)
      builder.append((builder.length() > 0 ? ", " : "") + "form=").append(Form.class.getName()).append('.').append(form);

    if (min != null)
      builder.append((builder.length() > 0 ? ", " : "") + "min=\"").append(min).append('"');

    if (max != null)
      builder.append((builder.length() > 0 ? ", " : "") + "max=\"").append(max).append('"');

    return builder.toString();
  }
}