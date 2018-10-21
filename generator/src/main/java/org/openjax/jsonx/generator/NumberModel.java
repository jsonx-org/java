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
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import org.fastjax.lang.IllegalAnnotationException;
import org.openjax.jsonx.jsonx_0_9_8.xL3gluGCXYYJc.$Array;
import org.openjax.jsonx.jsonx_0_9_8.xL3gluGCXYYJc.$Number;
import org.openjax.jsonx.jsonx_0_9_8.xL3gluGCXYYJc.Jsonx;
import org.openjax.jsonx.runtime.Form;
import org.openjax.jsonx.runtime.JsonxUtil;
import org.openjax.jsonx.runtime.NumberElement;
import org.openjax.jsonx.runtime.NumberProperty;
import org.openjax.jsonx.runtime.ParseException;
import org.openjax.jsonx.runtime.Range;
import org.openjax.jsonx.runtime.Use;
import org.openjax.jsonx.runtime.ValidationException;
import org.openjax.xsb.runtime.Binding;
import org.openjax.xsb.runtime.Bindings;

final class NumberModel extends Model {
  public static NumberModel declare(final Registry registry, final Jsonx.Number binding) {
    return registry.declare(binding).value(new NumberModel(registry, binding), null);
  }

  public static NumberModel reference(final Registry registry, final Referrer<?> referrer, final $Number binding) {
    return registry.reference(new NumberModel(registry, binding), referrer);
  }

  public static NumberModel reference(final Registry registry, final Referrer<?> referrer, final $Array.Number binding) {
    return registry.reference(new NumberModel(registry, binding), referrer);
  }

  public static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final NumberProperty property, final Field field) {
    final NumberModel model = new NumberModel(registry, property, field);
    final Id id = model.id();

    final NumberModel registered = (NumberModel)registry.getModel(id);
    return new Reference(registry, JsonxUtil.getName(property.name(), field), property.use(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  public static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final NumberElement element) {
    final NumberModel model = new NumberModel(registry, element);
    final Id id = model.id();

    final NumberModel registered = (NumberModel)registry.getModel(id);
    return new Reference(registry, element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  private static ValidationException createValidationException(final Binding binding, final String range, final ParseException e) {
    return new ValidationException(Bindings.getXPath(binding, elementXPath) + "/@range=" + range, e);
  }

  private static ValidationException createValidationException(final Class<?> cls, final String range, final ParseException e) {
    throw new ValidationException(cls.getName() + " Invalid range=\"" + range + "\"", e);
  }

  private final Id id;
  private final Form form;
  private final Range range;

  private NumberModel(final Registry registry, final Jsonx.Number binding) {
    super(registry);
    this.form = binding.getForm$().isDefault() ? null : Form.valueOf(binding.getForm$().text().toUpperCase());
    try {
      this.range = binding.getRange$() == null ? null : new Range(binding.getRange$().text());
    }
    catch (final ParseException e) {
      throw createValidationException(binding, binding.getRange$().text(), e);
    }

    this.id = new Id(binding.getTemplate$());
  }

  private NumberModel(final Registry registry, final $Number binding) {
    super(registry, binding.getName$(), binding.getUse$());
    this.form = binding.getForm$().isDefault() ? null : Form.valueOf(binding.getForm$().text().toUpperCase());
    try {
      this.range = binding.getRange$() == null ? null : new Range(binding.getRange$().text());
    }
    catch (final ParseException e) {
      throw createValidationException(binding, binding.getRange$().text(), e);
    }

    this.id = new Id(this);
  }

  private NumberModel(final Registry registry, final $Array.Number binding) {
    super(registry, binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.form = binding.getForm$().isDefault() ? null : Form.valueOf(binding.getForm$().text().toUpperCase());
    try {
      this.range = binding.getRange$() == null ? null : new Range(binding.getRange$().text());
    }
    catch (final ParseException e) {
      throw createValidationException(binding, binding.getRange$().text(), e);
    }

    this.id = new Id(this);
  }

  private NumberModel(final Registry registry, final NumberProperty property, final Field field) {
    super(registry, null, property.use());
    if (!Number.class.isAssignableFrom(field.getType()) && (!field.getType().isPrimitive() || field.getType() == char.class || property.use() == Use.OPTIONAL))
      throw new IllegalAnnotationException(property, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + NumberProperty.class.getSimpleName() + " can only be applied to fields of Number subtypes or primitive numeric non-nullable types");

    this.form = property.form() == Form.REAL ? null : property.form();
    try {
      this.range = property.range().length() == 0 ? null : new Range(property.range());
    }
    catch (final ParseException e) {
      throw createValidationException(property.annotationType(), property.range(), e);
    }

    this.id = new Id(this);
  }

  private NumberModel(final Registry registry, final NumberElement element) {
    super(registry, element.nullable(), null);
    this.form = element.form() == Form.REAL ? null : element.form();
    try {
      this.range = element.range().length() == 0 ? null : new Range(element.range());
    }
    catch (final ParseException e) {
      throw createValidationException(element.annotationType(), element.range(), e);
    }

    this.id = new Id(this);
  }

  @Override
  protected Id id() {
    return id;
  }

  public Form form() {
    return this.form;
  }

  public Range range() {
    return this.range;
  }

  @Override
  protected Registry.Type type() {
    return registry.getType(form == Form.INTEGER ? BigInteger.class : BigDecimal.class);
  }

  @Override
  protected String elementName() {
    return "number";
  }

  @Override
  protected Class<? extends Annotation> propertyAnnotation() {
    return NumberProperty.class;
  }

  @Override
  protected Class<? extends Annotation> elementAnnotation() {
    return NumberElement.class;
  }

  @Override
  protected Map<String,String> toXmlAttributes(final Element owner, final String packageName) {
    final Map<String,String> attributes = super.toXmlAttributes(owner, packageName);
    if (form != null)
      attributes.put("form", form.toString().toLowerCase());

    if (range != null)
      attributes.put("range", range.toString());

    return attributes;
  }

  @Override
  protected void toAnnotationAttributes(final AttributeMap attributes, final Member owner) {
    super.toAnnotationAttributes(attributes, owner);
    if (form != null)
      attributes.put("form", Form.class.getName() + "." + form);

    if (range != null)
      attributes.put("range", "\"" + range.toString() + "\"");
  }
}