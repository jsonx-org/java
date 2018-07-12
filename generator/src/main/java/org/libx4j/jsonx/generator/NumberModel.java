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
    return registry.declare(binding).value(new NumberModel(registry, binding), null);
  }

  public static NumberModel reference(final Registry registry, final ComplexModel referrer, final $Number binding) {
    return registry.reference(new NumberModel(registry, binding), referrer);
  }

  public static NumberModel reference(final Registry registry, final ComplexModel referrer, final $Array.Number binding) {
    return registry.reference(new NumberModel(registry, binding), referrer);
  }

  public static Element referenceOrDeclare(final Registry registry, final ComplexModel referrer, final NumberProperty property, final Field field) {
    final NumberModel model = new NumberModel(registry, property, field);
    final Id id = model.id();

    final NumberModel registered = (NumberModel)registry.getElement(id);
    return new Template(registry, getName(property.name(), field), property.nullable(), property.required(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  public static Element referenceOrDeclare(final Registry registry, final ComplexModel referrer, final NumberElement element) {
    final NumberModel model = new NumberModel(registry, element);
    final Id id = model.id();

    final NumberModel registered = (NumberModel)registry.getElement(id);
    return new Template(registry, element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  private final Id id;
  private final Form form;
  private final BigDecimal min;
  private final BigDecimal max;

  private NumberModel(final Registry registry, final Jsonx.Number binding) {
    super(registry, null);
    this.form = binding.getForm$().isDefault() ? null : Form.valueOf(binding.getForm$().text().toUpperCase());
    this.min = binding.getMin$() == null ? null : binding.getMin$().text();
    this.max = binding.getMax$() == null ? null : binding.getMax$().text();
    this.id = new Id(binding.getTemplate$());
  }

  private NumberModel(final Registry registry, final $Number binding) {
    super(registry, binding.getName$(), binding.getNullable$(), binding.getRequired$());
    this.form = binding.getForm$().isDefault() ? null : Form.valueOf(binding.getForm$().text().toUpperCase());
    this.min = binding.getMin$() == null ? null : binding.getMin$().text();
    this.max = binding.getMax$() == null ? null : binding.getMax$().text();
    this.id = new Id(this);
  }

  private NumberModel(final Registry registry, final $Array.Number binding) {
    super(registry, binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.form = binding.getForm$().isDefault() ? null : Form.valueOf(binding.getForm$().text().toUpperCase());
    this.min = binding.getMin$() == null ? null : binding.getMin$().text();
    this.max = binding.getMax$() == null ? null : binding.getMax$().text();
    this.id = new Id(this);
  }

  private NumberModel(final Registry registry, final NumberProperty property, final Field field) {
    super(registry, property.nullable());
    if (!Number.class.isAssignableFrom(field.getType()) && (!field.getType().isPrimitive() || field.getType() == char.class || property.nullable()))
      throw new IllegalAnnotationException(property, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + NumberProperty.class.getSimpleName() + " can only be applied to fields of Number subtypes or primitive numeric non-nullable types.");

    this.form = property.form() == Form.REAL ? null : property.form();
    this.min = property.min().length() == 0 ? null : new BigDecimal(property.min());
    this.max = property.max().length() == 0 ? null : new BigDecimal(property.max());
    this.id = new Id(this);
  }

  private NumberModel(final Registry registry, final NumberElement element) {
    super(registry, element.nullable());
    this.form = element.form() == Form.REAL ? null : element.form();
    this.min = element.min().length() == 0 ? null : new BigDecimal(element.min());
    this.max = element.max().length() == 0 ? null : new BigDecimal(element.max());
    this.id = new Id(this);
  }

  @Override
  public final Id id() {
    return id;
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
  protected Registry.Type type() {
    return registry.getType(form == Form.INTEGER ? BigInteger.class : BigDecimal.class);
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
  protected String toJSON(final String packageName) {
    final StringBuilder builder = new StringBuilder(super.toJSON(packageName));
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
  protected final String toJSONX(final Registry registry, final Member owner, final String packageName) {
    final boolean skipMembers;
    final StringBuilder builder;
    if (owner instanceof ObjectModel) {
      builder = new StringBuilder("<property xsi:type=\"");
      if (skipMembers = registry.isRegistered(id()))
        builder.append("template\" reference=\"").append(id()).append("\"");
      else
        builder.append("number\"");
    }
    else {
      builder = new StringBuilder("<number");
      skipMembers = false;
    }

    if (!skipMembers) {
      if (form != null)
        builder.append(" form=\"").append(form.toString().toLowerCase()).append('"');

      if (min != null)
        builder.append(" min=\"").append(min).append('"');

      if (max != null)
        builder.append(" max=\"").append(max).append('"');
    }

    return builder.append(super.toJSONX(registry, owner, packageName)).append("/>").toString();
  }

  @Override
  protected void toAnnotation(final Attributes attributes) {
    super.toAnnotation(attributes);
    if (form != null)
      attributes.put("form", Form.class.getName() + "." + form);

    if (min != null)
      attributes.put("min", "\"" + min + "\"");

    if (max != null)
      attributes.put("max", "\"" + max + "\"");
  }
}