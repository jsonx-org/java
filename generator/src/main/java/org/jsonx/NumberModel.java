/* Copyright (c) 2017 JSONx
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

package org.jsonx;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import org.jaxsb.runtime.Binding;
import org.jaxsb.runtime.Bindings;
import org.jsonx.www.schema_0_3.xL0gluGCXAA;
import org.libj.lang.IllegalAnnotationException;

final class NumberModel extends Model {
  private static xL0gluGCXAA.Schema.Number type(final String name) {
    final xL0gluGCXAA.Schema.Number xsb = new xL0gluGCXAA.Schema.Number();
    if (name != null)
      xsb.setName$(new xL0gluGCXAA.Schema.Number.Name$(name));

    return xsb;
  }

  private static xL0gluGCXAA.$Number property(final schema.NumberProperty jsd, final String name) {
    final xL0gluGCXAA.$Number xsb = new xL0gluGCXAA.$Number() {
      private static final long serialVersionUID = -346722555715286411L;

      @Override
      protected xL0gluGCXAA.$Member inherits() {
        return new xL0gluGCXAA.$ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setName$(new xL0gluGCXAA.$Number.Name$(name));

    if (jsd.getNullable() != null)
      xsb.setNullable$(new xL0gluGCXAA.$Number.Nullable$(jsd.getNullable()));

    if (jsd.getUse() != null)
      xsb.setUse$(new xL0gluGCXAA.$Number.Use$(xL0gluGCXAA.$Number.Use$.Enum.valueOf(jsd.getUse())));

    return xsb;
  }

  private static xL0gluGCXAA.$ArrayMember.Number element(final schema.NumberElement jsd) {
    final xL0gluGCXAA.$ArrayMember.Number xsb = new xL0gluGCXAA.$ArrayMember.Number();

    if (jsd.getNullable() != null)
      xsb.setNullable$(new xL0gluGCXAA.$ArrayMember.Number.Nullable$(jsd.getNullable()));

    if (jsd.getMinOccurs() != null)
      xsb.setMinOccurs$(new xL0gluGCXAA.$ArrayMember.Number.MinOccurs$(Integer.parseInt(jsd.getMinOccurs())));

    if (jsd.getMaxOccurs() != null)
      xsb.setMaxOccurs$(new xL0gluGCXAA.$ArrayMember.Number.MaxOccurs$(jsd.getMaxOccurs()));

    return xsb;
  }

  static xL0gluGCXAA.$NumberMember jsdToXsb(final schema.Number jsd, final String name) {
    final xL0gluGCXAA.$NumberMember xsb;
    if (jsd instanceof schema.NumberProperty)
      xsb = property((schema.NumberProperty)jsd, name);
    else if (jsd instanceof schema.NumberElement)
      xsb = element((schema.NumberElement)jsd);
    else if (name != null)
      xsb = type(name);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsd.getClass().getName());

    if (jsd.getDoc() != null && jsd.getDoc().length() > 0)
      xsb.setDoc$(new xL0gluGCXAA.$Documented.Doc$(jsd.getDoc()));

    if (jsd.getScale() != null)
      xsb.setScale$(new xL0gluGCXAA.$NumberMember.Scale$(jsd.getScale().intValue()));

    if (jsd.getRange() != null)
      xsb.setRange$(new xL0gluGCXAA.$NumberMember.Range$(jsd.getRange()));

    return xsb;
  }

  static NumberModel declare(final Registry registry, final Declarer declarer, final xL0gluGCXAA.Schema.Number binding) {
    return registry.declare(binding).value(new NumberModel(registry, declarer, binding), null);
  }

  static NumberModel reference(final Registry registry, final Referrer<?> referrer, final xL0gluGCXAA.$Number binding) {
    try {
      return registry.reference(new NumberModel(registry, referrer, binding), referrer);
    }
    catch (final ParseException e) {
      throw createValidationException(binding, binding.getRange$().text(), e);
    }
  }

  static NumberModel reference(final Registry registry, final Referrer<?> referrer, final xL0gluGCXAA.$Array.Number binding) {
    try {
      return registry.reference(new NumberModel(registry, referrer, binding), referrer);
    }
    catch (final ParseException e) {
      throw createValidationException(binding, binding.getRange$().text(), e);
    }
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final NumberProperty property, final Field field) {
    try {
      final NumberModel model = new NumberModel(registry, referrer, property, field);
      final Id id = model.id;

      final NumberModel registered = (NumberModel)registry.getModel(id);
      return new Reference(registry, referrer, JsdUtil.getName(property.name(), field), property.nullable(), property.use(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
    }
    catch (final ParseException e) {
      throw createValidationException(property.annotationType(), property.range(), e);
    }
  }

  static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final NumberElement element) {
    try {
      final NumberModel model = new NumberModel(registry, referrer, element.nullable(), element.scale(), element.range());
      final Id id = model.id;

      final NumberModel registered = (NumberModel)registry.getModel(id);
      return new Reference(registry, referrer, element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
    }
    catch (final ParseException e) {
      throw createValidationException(element.annotationType(), element.range(), e);
    }
  }

  static NumberModel referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final NumberType type) {
    try {
      final NumberModel model = new NumberModel(registry, referrer, null, type.scale(), type.range());
      final Id id = model.id;

      final NumberModel registered = (NumberModel)registry.getModel(id);
      return registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer);
    }
    catch (final ParseException e) {
      throw createValidationException(type.annotationType(), type.range(), e);
    }
  }

  private static ValidationException createValidationException(final Binding binding, final String range, final ParseException e) {
    return new ValidationException(Bindings.getXPath(binding, elementXPath) + "/@range=" + range, e);
  }

  private static ValidationException createValidationException(final Class<?> cls, final String range, final ParseException e) {
    throw new ValidationException(cls.getName() + " Invalid range=\"" + range + "\"", e);
  }

  private static int parseScale(final xL0gluGCXAA.$NumberMember.Scale$ scale) {
    return scale == null || scale.text() == null ? Integer.MAX_VALUE : scale.text();
  }

  private static Range parseRange(final String range) throws ParseException {
    return range == null || range.length() == 0 ? null : new Range(range);
  }

  private static Range parseRange(final xL0gluGCXAA.$NumberMember.Range$ range) throws ParseException {
    return range == null ? null : parseRange(range.text());
  }

  final int scale;
  final Range range;

  private NumberModel(final Registry registry, final Declarer declarer, final xL0gluGCXAA.Schema.Number binding) {
    super(registry, declarer, Id.named(binding.getName$()), binding.getDoc$());
    this.scale = parseScale(binding.getScale$());
    try {
      this.range = parseRange(binding.getRange$());
    }
    catch (final ParseException e) {
      throw createValidationException(binding, binding.getRange$().text(), e);
    }
  }

  private NumberModel(final Registry registry, final Declarer declarer, final xL0gluGCXAA.$Number binding) throws ParseException {
    super(registry, declarer, Id.hashed("n", parseScale(binding.getScale$()), parseRange(binding.getRange$())), binding.getDoc$(), binding.getName$(), binding.getNullable$(), binding.getUse$());
    this.scale = parseScale(binding.getScale$());
    this.range = parseRange(binding.getRange$());
  }

  private NumberModel(final Registry registry, final Declarer declarer, final xL0gluGCXAA.$Array.Number binding) throws ParseException {
    super(registry, declarer, Id.hashed("n", parseScale(binding.getScale$()), parseRange(binding.getRange$())), binding.getDoc$(), binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.scale = parseScale(binding.getScale$());
    this.range = parseRange(binding.getRange$());
  }

  private NumberModel(final Registry registry, final Declarer declarer, final NumberProperty property, final Field field) throws ParseException {
    super(registry, declarer, Id.hashed("n", property.scale(), parseRange(property.range())), property.nullable(), property.use());
    if (!isAssignable(field, Number.class, false, property.nullable(), property.use()) || field.getType() == char.class || field.getType() == boolean.class || field.getType().isPrimitive() && (property.use() == Use.OPTIONAL || property.nullable()))
      throw new IllegalAnnotationException(property, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + NumberProperty.class.getSimpleName() + " can only be applied to fields of Number type with use=\"required\" or nullable=false, or of primitive numeric type with use=\"required\" and nullable=false, or of Optional<? extends Number> type with use=\"optional\" and nullable=true");

    this.scale = property.scale();
    this.range = parseRange(property.range());
  }

  private NumberModel(final Registry registry, final Declarer declarer, final Boolean nullable, final int scale, final String range) throws ParseException {
    super(registry, declarer, Id.hashed("n", scale, parseRange(range)), nullable, null);
    this.scale = scale;
    this.range = parseRange(range);
  }

  @Override
  Registry.Type type() {
    return registry.getType(scale == 0 ? BigInteger.class : BigDecimal.class);
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
    if (scale != Integer.MAX_VALUE)
      attributes.put("scale", scale);

    if (range != null)
      attributes.put("range", range.toString());

    return attributes;
  }

  @Override
  void toAnnotationAttributes(final AttributeMap attributes, final Member owner) {
    super.toAnnotationAttributes(attributes, owner);
    if (scale != Integer.MAX_VALUE)
      attributes.put("scale", scale);

    if (range != null)
      attributes.put("range", "\"" + range.toString() + "\"");
  }
}