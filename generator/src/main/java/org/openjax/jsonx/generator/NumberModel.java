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

import org.openjax.jsonx.schema;
import org.openjax.jsonx.runtime.Form;
import org.openjax.jsonx.runtime.JxUtil;
import org.openjax.jsonx.runtime.NumberElement;
import org.openjax.jsonx.runtime.NumberProperty;
import org.openjax.jsonx.runtime.NumberType;
import org.openjax.jsonx.runtime.ParseException;
import org.openjax.jsonx.runtime.Range;
import org.openjax.jsonx.runtime.Use;
import org.openjax.jsonx.runtime.ValidationException;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc;
import org.openjax.standard.lang.IllegalAnnotationException;
import org.openjax.xsb.runtime.Binding;
import org.openjax.xsb.runtime.Bindings;

final class NumberModel extends Model {
  private static xL4gluGCXYYJc.Schema.Number type(final String name) {
    final xL4gluGCXYYJc.Schema.Number xsb = new xL4gluGCXYYJc.Schema.Number();
    if (name != null)
      xsb.setName$(new xL4gluGCXYYJc.Schema.Number.Name$(name));

    return xsb;
  }

  private static xL4gluGCXYYJc.$Number property(final schema.NumberProperty jsonx, final String name) {
    final xL4gluGCXYYJc.$Number xsb = new xL4gluGCXYYJc.$Number() {
      private static final long serialVersionUID = -346722555715286411L;

      @Override
      protected xL4gluGCXYYJc.$Member inherits() {
        return new xL4gluGCXYYJc.$ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setName$(new xL4gluGCXYYJc.$Number.Name$(name));

    if (jsonx.getNullable() != null)
      xsb.setNullable$(new xL4gluGCXYYJc.$Number.Nullable$(jsonx.getNullable()));

    if (jsonx.getUse() != null)
      xsb.setUse$(new xL4gluGCXYYJc.$Number.Use$(xL4gluGCXYYJc.$Number.Use$.Enum.valueOf(jsonx.getUse())));

    return xsb;
  }

  private static xL4gluGCXYYJc.$ArrayMember.Number element(final schema.NumberElement jsonx) {
    final xL4gluGCXYYJc.$ArrayMember.Number xsb = new xL4gluGCXYYJc.$ArrayMember.Number();

    if (jsonx.getNullable() != null)
      xsb.setNullable$(new xL4gluGCXYYJc.$ArrayMember.Number.Nullable$(jsonx.getNullable()));

    if (jsonx.getMinOccurs() != null)
      xsb.setMinOccurs$(new xL4gluGCXYYJc.$ArrayMember.Number.MinOccurs$(Integer.parseInt(jsonx.getMinOccurs())));

    if (jsonx.getMaxOccurs() != null)
      xsb.setMaxOccurs$(new xL4gluGCXYYJc.$ArrayMember.Number.MaxOccurs$(jsonx.getMaxOccurs()));

    return xsb;
  }

  static xL4gluGCXYYJc.$NumberMember jsonxToXsb(final schema.Number jsonx, final String name) {
    final xL4gluGCXYYJc.$NumberMember xsb;
    if (jsonx instanceof schema.NumberProperty)
      xsb = property((schema.NumberProperty)jsonx, name);
    else if (jsonx instanceof schema.NumberElement)
      xsb = element((schema.NumberElement)jsonx);
    else if (name != null)
      xsb = type(name);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsonx.getClass().getName());

    if (jsonx.getForm() != null)
      xsb.setForm$(new xL4gluGCXYYJc.$NumberMember.Form$(xL4gluGCXYYJc.$NumberMember.Form$.Enum.valueOf(jsonx.getForm())));

    if (jsonx.getRange() != null)
      xsb.setRange$(new xL4gluGCXYYJc.$NumberMember.Range$(jsonx.getRange()));

    return xsb;
  }

  static NumberModel declare(final Registry registry, final xL4gluGCXYYJc.Schema.Number binding) {
    return registry.declare(binding).value(new NumberModel(registry, binding), null);
  }

  static NumberModel reference(final Registry registry, final Referrer<?> referrer, final xL4gluGCXYYJc.$Number binding) {
    return registry.reference(new NumberModel(registry, binding), referrer);
  }

  static NumberModel reference(final Registry registry, final Referrer<?> referrer, final xL4gluGCXYYJc.$Array.Number binding) {
    return registry.reference(new NumberModel(registry, binding), referrer);
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final NumberProperty property, final Field field) {
    final NumberModel model = new NumberModel(registry, property, field);
    final Id id = model.id;

    final NumberModel registered = (NumberModel)registry.getModel(id);
    return new Reference(registry, JxUtil.getName(property.name(), field), property.nullable(), property.use(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final NumberElement element) {
    final NumberModel model = new NumberModel(registry, element.annotationType(), element.nullable(), element.form(), element.range());
    final Id id = model.id;

    final NumberModel registered = (NumberModel)registry.getModel(id);
    return new Reference(registry, element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static NumberModel referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final NumberType type) {
    final NumberModel model = new NumberModel(registry, type.annotationType(), null, type.form(), type.range());
    final Id id = model.id;

    final NumberModel registered = (NumberModel)registry.getModel(id);
    return registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer);
  }

  private static ValidationException createValidationException(final Binding binding, final String range, final ParseException e) {
    return new ValidationException(Bindings.getXPath(binding, elementXPath) + "/@range=" + range, e);
  }

  private static ValidationException createValidationException(final Class<?> cls, final String range, final ParseException e) {
    throw new ValidationException(cls.getName() + " Invalid range=\"" + range + "\"", e);
  }

  private static Form parseForm(final Form form) {
    return form == Form.REAL ? null : form;
  }

  private static Range parseRange(final String range) {
    return range == null || range.length() == 0 ? null : new Range(range);
  }

  private static Range parseRange(final xL4gluGCXYYJc.$NumberMember.Range$ range) {
    return range == null ? null : parseRange(range.text());
  }

  final Form form;
  final Range range;

  private NumberModel(final Registry registry, final xL4gluGCXYYJc.Schema.Number binding) {
    super(registry, Id.named(binding.getName$()));
    this.form = parseForm(Form.valueOf(binding.getForm$().text().toUpperCase()));
    try {
      this.range = parseRange(binding.getRange$());
    }
    catch (final ParseException e) {
      throw createValidationException(binding, binding.getRange$().text(), e);
    }
  }

  private NumberModel(final Registry registry, final xL4gluGCXYYJc.$Number binding) {
    super(registry, Id.hashed("n", parseForm(Form.valueOf(binding.getForm$().text().toUpperCase())), parseRange(binding.getRange$())), binding.getName$(), binding.getNullable$(), binding.getUse$());
    this.form = parseForm(Form.valueOf(binding.getForm$().text().toUpperCase()));
    try {
      this.range = parseRange(binding.getRange$());
    }
    catch (final ParseException e) {
      throw createValidationException(binding, binding.getRange$().text(), e);
    }
  }

  private NumberModel(final Registry registry, final xL4gluGCXYYJc.$Array.Number binding) {
    super(registry, Id.hashed("n", parseForm(Form.valueOf(binding.getForm$().text().toUpperCase())), parseRange(binding.getRange$())), binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.form = parseForm(Form.valueOf(binding.getForm$().text().toUpperCase()));
    try {
      this.range = parseRange(binding.getRange$());
    }
    catch (final ParseException e) {
      throw createValidationException(binding, binding.getRange$().text(), e);
    }
  }

  private NumberModel(final Registry registry, final NumberProperty property, final Field field) {
    super(registry, Id.hashed("n", parseForm(property.form()), parseRange(property.range())), property.nullable(), property.use());
    if (!isAssignable(field, Number.class, false, property.nullable(), property.use()) && (!field.getType().isPrimitive() || field.getType() == char.class || property.use() == Use.OPTIONAL))
      throw new IllegalAnnotationException(property, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + NumberProperty.class.getSimpleName() + " can only be applied to required or not-nullable fields of Number subtype, or non-nullable fields of primitive numeric type, or optional and nullable fields of Optional<? extends Number> type");

    this.form = parseForm(property.form());
    try {
      this.range = parseRange(property.range());
    }
    catch (final ParseException e) {
      throw createValidationException(property.annotationType(), property.range(), e);
    }
  }

  private NumberModel(final Registry registry, final Class<? extends Annotation> annotationType, final Boolean nullable, final Form form, final String range) {
    super(registry, Id.hashed("n", parseForm(form), parseRange(range)), nullable, null);
    this.form = parseForm(form);
    try {
      this.range = parseRange(range);
    }
    catch (final ParseException e) {
      throw createValidationException(annotationType, range, e);
    }
  }

  @Override
  Registry.Type type() {
    return registry.getType(form == Form.INTEGER ? BigInteger.class : BigDecimal.class);
  }

  @Override
  String elementName() {
    return "number";
  }

  @Override
  Class<? extends Annotation> propertyAnnotation() {
    return NumberProperty.class;
  }

  @Override
  Class<? extends Annotation> elementAnnotation() {
    return NumberElement.class;
  }

  @Override
  Map<String,Object> toAttributes(final Element owner, final String packageName) {
    final Map<String,Object> attributes = super.toAttributes(owner, packageName);
    if (form != null)
      attributes.put("form", form.toString().toLowerCase());

    if (range != null)
      attributes.put("range", range.toString());

    return attributes;
  }

  @Override
  void toAnnotationAttributes(final AttributeMap attributes, final Member owner) {
    super.toAnnotationAttributes(attributes, owner);
    if (form != null)
      attributes.put("form", Form.class.getName() + "." + form);

    if (range != null)
      attributes.put("range", "\"" + range.toString() + "\"");
  }
}