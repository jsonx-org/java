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
import org.jsonx.www.schema_0_2_3.xL0gluGCXYYJc;
import org.libj.lang.IllegalAnnotationException;

final class NumberModel extends Model {
  private static xL0gluGCXYYJc.Schema.Number type(final String name) {
    final xL0gluGCXYYJc.Schema.Number xsb = new xL0gluGCXYYJc.Schema.Number();
    if (name != null)
      xsb.setName$(new xL0gluGCXYYJc.Schema.Number.Name$(name));

    return xsb;
  }

  private static xL0gluGCXYYJc.$Number property(final schema.NumberProperty jsd, final String name) {
    final xL0gluGCXYYJc.$Number xsb = new xL0gluGCXYYJc.$Number() {
      private static final long serialVersionUID = -346722555715286411L;

      @Override
      protected xL0gluGCXYYJc.$Member inherits() {
        return new xL0gluGCXYYJc.$ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setName$(new xL0gluGCXYYJc.$Number.Name$(name));

    if (jsd.getJsd_3aNullable() != null)
      xsb.setNullable$(new xL0gluGCXYYJc.$Number.Nullable$(jsd.getJsd_3aNullable()));

    if (jsd.getJsd_3aUse() != null)
      xsb.setUse$(new xL0gluGCXYYJc.$Number.Use$(xL0gluGCXYYJc.$Number.Use$.Enum.valueOf(jsd.getJsd_3aUse())));

    return xsb;
  }

  private static xL0gluGCXYYJc.$ArrayMember.Number element(final schema.NumberElement jsd) {
    final xL0gluGCXYYJc.$ArrayMember.Number xsb = new xL0gluGCXYYJc.$ArrayMember.Number();

    if (jsd.getJsd_3aNullable() != null)
      xsb.setNullable$(new xL0gluGCXYYJc.$ArrayMember.Number.Nullable$(jsd.getJsd_3aNullable()));

    if (jsd.getJsd_3aMinOccurs() != null)
      xsb.setMinOccurs$(new xL0gluGCXYYJc.$ArrayMember.Number.MinOccurs$(Integer.parseInt(jsd.getJsd_3aMinOccurs())));

    if (jsd.getJsd_3aMaxOccurs() != null)
      xsb.setMaxOccurs$(new xL0gluGCXYYJc.$ArrayMember.Number.MaxOccurs$(jsd.getJsd_3aMaxOccurs()));

    return xsb;
  }

  static xL0gluGCXYYJc.$NumberMember jsdToXsb(final schema.Number jsd, final String name) {
    final xL0gluGCXYYJc.$NumberMember xsb;
    if (jsd instanceof schema.NumberProperty)
      xsb = property((schema.NumberProperty)jsd, name);
    else if (jsd instanceof schema.NumberElement)
      xsb = element((schema.NumberElement)jsd);
    else if (name != null)
      xsb = type(name);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsd.getClass().getName());

    if (jsd.getJsd_3aDoc() != null && jsd.getJsd_3aDoc().length() > 0)
      xsb.setDoc$(new xL0gluGCXYYJc.$Documented.Doc$(jsd.getJsd_3aDoc()));

    if (jsd.getJsd_3aScale() != null)
      xsb.setScale$(new xL0gluGCXYYJc.$NumberMember.Scale$(jsd.getJsd_3aScale().intValue()));

    if (jsd.getJsd_3aRange() != null)
      xsb.setRange$(new xL0gluGCXYYJc.$NumberMember.Range$(jsd.getJsd_3aRange()));

    return xsb;
  }

  static NumberModel declare(final Registry registry, final Declarer declarer, final xL0gluGCXYYJc.Schema.Number binding) {
    return registry.declare(binding).value(new NumberModel(registry, declarer, binding), null);
  }

  static NumberModel reference(final Registry registry, final Referrer<?> referrer, final xL0gluGCXYYJc.$Number binding) {
    return registry.reference(new NumberModel(registry, referrer, binding), referrer);
  }

  static NumberModel reference(final Registry registry, final Referrer<?> referrer, final xL0gluGCXYYJc.$Array.Number binding) {
    return registry.reference(new NumberModel(registry, referrer, binding), referrer);
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final NumberProperty property, final Field field) {
    final NumberModel model = new NumberModel(registry, referrer, property, field);
    final Id id = model.id;

    final NumberModel registered = (NumberModel)registry.getModel(id);
    return new Reference(registry, referrer, JsdUtil.getName(property.name(), field), property.nullable(), property.use(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final NumberElement element) {
    final NumberModel model = new NumberModel(registry, referrer, element.annotationType(), element.nullable(), element.scale(), element.range());
    final Id id = model.id;

    final NumberModel registered = (NumberModel)registry.getModel(id);
    return new Reference(registry, referrer, element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static NumberModel referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final NumberType type) {
    final NumberModel model = new NumberModel(registry, referrer, type.annotationType(), null, type.scale(), type.range());
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

  private static int parseScale(final xL0gluGCXYYJc.$NumberMember.Scale$ scale) {
    return scale == null || scale.text() == null ? Integer.MAX_VALUE : scale.text();
  }

  private static Range parseRange(final String range) {
    return range == null || range.length() == 0 ? null : new Range(range);
  }

  private static Range parseRange(final xL0gluGCXYYJc.$NumberMember.Range$ range) {
    return range == null ? null : parseRange(range.text());
  }

  final int scale;
  final Range range;

  private NumberModel(final Registry registry, final Declarer declarer, final xL0gluGCXYYJc.Schema.Number binding) {
    super(registry, declarer, Id.named(binding.getName$()), binding.getDoc$());
    this.scale = parseScale(binding.getScale$());
    try {
      this.range = parseRange(binding.getRange$());
    }
    catch (final ParseException e) {
      throw createValidationException(binding, binding.getRange$().text(), e);
    }
  }

  private NumberModel(final Registry registry, final Declarer declarer, final xL0gluGCXYYJc.$Number binding) {
    super(registry, declarer, Id.hashed("n", parseScale(binding.getScale$()), parseRange(binding.getRange$())), binding.getDoc$(), binding.getName$(), binding.getNullable$(), binding.getUse$());
    this.scale = parseScale(binding.getScale$());
    try {
      this.range = parseRange(binding.getRange$());
    }
    catch (final ParseException e) {
      throw createValidationException(binding, binding.getRange$().text(), e);
    }
  }

  private NumberModel(final Registry registry, final Declarer declarer, final xL0gluGCXYYJc.$Array.Number binding) {
    super(registry, declarer, Id.hashed("n", parseScale(binding.getScale$()), parseRange(binding.getRange$())), binding.getDoc$(), binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.scale = parseScale(binding.getScale$());
    try {
      this.range = parseRange(binding.getRange$());
    }
    catch (final ParseException e) {
      throw createValidationException(binding, binding.getRange$().text(), e);
    }
  }

  private NumberModel(final Registry registry, final Declarer declarer, final NumberProperty property, final Field field) {
    super(registry, declarer, Id.hashed("n", property.scale(), parseRange(property.range())), property.nullable(), property.use());
    if (!isAssignable(field, Number.class, false, property.nullable(), property.use()) && (!field.getType().isPrimitive() || field.getType() == char.class || property.use() == Use.OPTIONAL))
      throw new IllegalAnnotationException(property, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + NumberProperty.class.getSimpleName() + " can only be applied to required or not-nullable fields of Number subtype, or non-nullable fields of primitive numeric type, or optional and nullable fields of Optional<? extends Number> type");

    this.scale = property.scale();
    try {
      this.range = parseRange(property.range());
    }
    catch (final ParseException e) {
      throw createValidationException(property.annotationType(), property.range(), e);
    }
  }

  private NumberModel(final Registry registry, final Declarer declarer, final Class<? extends Annotation> annotationType, final Boolean nullable, final int scale, final String range) {
    super(registry, declarer, Id.hashed("n", scale, parseRange(range)), nullable, null);
    this.scale = scale;
    try {
      this.range = parseRange(range);
    }
    catch (final ParseException e) {
      throw createValidationException(annotationType, range, e);
    }
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
  Map<String,Object> toAttributes(final Element owner, final String prefix, final String packageName) {
    final Map<String,Object> attributes = super.toAttributes(owner, prefix, packageName);
    if (scale != Integer.MAX_VALUE)
      attributes.put(prefix + "scale", scale);

    if (range != null)
      attributes.put(prefix + "range", range.toString());

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