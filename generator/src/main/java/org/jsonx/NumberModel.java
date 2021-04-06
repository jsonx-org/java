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
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import org.jaxsb.runtime.Bindings;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Array;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$ArrayMember;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Binding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Documented;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$FieldIdentifier;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Member;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Number;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$NumberMember;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$ObjectMember;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$TypeBinding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$TypeFieldBinding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.Schema;
import org.libj.lang.Classes;
import org.libj.lang.IllegalAnnotationException;
import org.libj.lang.Numbers;
import org.libj.lang.ParseException;
import org.w3.www._2001.XMLSchema.yAA.$AnySimpleType;

final class NumberModel extends Model {
  private static $TypeBinding typeBinding(final schema.TypeBinding jsd) {
    final $TypeBinding xsb = new $ArrayMember.Number.Binding();
    xsb.setLang$(new $Binding.Lang$(jsd.getLang()));
    if (jsd.getType() != null)
      xsb.setType$(new $TypeBinding.Type$(jsd.getType()));

    if (jsd.getDecode() != null)
      xsb.setDecode$(new $TypeBinding.Decode$(jsd.getDecode()));

    if (jsd.getEncode() != null)
      xsb.setEncode$(new $TypeBinding.Encode$(jsd.getEncode()));

    return xsb;
  }

  private static Schema.Number type(final schema.Number jsd, final String name) {
    final Schema.Number xsb = new Schema.Number();
    if (name != null)
      xsb.setName$(new Schema.Number.Name$(name));

    if (jsd.getBindings() != null)
      for (final schema.TypeBinding binding : jsd.getBindings())
        xsb.addBinding(typeBinding(binding));

    return xsb;
  }

  private static $Number property(final schema.NumberProperty jsd, final String name) {
    final $Number xsb = new $Number() {
      private static final long serialVersionUID = -346722555715286411L;

      @Override
      protected $Member inherits() {
        return new $ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setName$(new $Number.Name$(name));

    if (jsd.getNullable() != null)
      xsb.setNullable$(new $Number.Nullable$(jsd.getNullable()));

    if (jsd.getUse() != null)
      xsb.setUse$(new $Number.Use$($Number.Use$.Enum.valueOf(jsd.getUse())));

    if (jsd.getBindings() != null) {
      for (final schema.TypeBinding element : jsd.getBindings()) {
        final schema.TypeFieldBinding binding = (schema.TypeFieldBinding)element;
        final $TypeFieldBinding bind = new $Number.Binding();
        bind.setLang$(new $Binding.Lang$(binding.getLang()));
        if (binding.getType() != null)
          bind.setType$(new $TypeBinding.Type$(binding.getType()));

        if (binding.getDecode() != null)
          bind.setDecode$(new $TypeBinding.Decode$(binding.getDecode()));

        if (binding.getEncode() != null)
          bind.setEncode$(new $TypeBinding.Encode$(binding.getEncode()));

        if (binding.getField() != null)
          bind.setField$(new $TypeFieldBinding.Field$(binding.getField()));

        xsb.addBinding(bind);
      }
    }

    return xsb;
  }

  private static $ArrayMember.Number element(final schema.NumberElement jsd) {
    final $ArrayMember.Number xsb = new $ArrayMember.Number();

    if (jsd.getNullable() != null)
      xsb.setNullable$(new $ArrayMember.Number.Nullable$(jsd.getNullable()));

    if (jsd.getMinOccurs() != null)
      xsb.setMinOccurs$(new $ArrayMember.Number.MinOccurs$(new BigInteger(jsd.getMinOccurs())));

    if (jsd.getMaxOccurs() != null)
      xsb.setMaxOccurs$(new $ArrayMember.Number.MaxOccurs$(jsd.getMaxOccurs()));


    if (jsd.getBindings() != null)
      for (final schema.TypeBinding binding : jsd.getBindings())
        xsb.addBinding(typeBinding(binding));

    return xsb;
  }

  static $NumberMember jsdToXsb(final schema.Number jsd, final String name) {
    final $NumberMember xsb;
    if (jsd instanceof schema.NumberProperty)
      xsb = property((schema.NumberProperty)jsd, name);
    else if (jsd instanceof schema.NumberElement)
      xsb = element((schema.NumberElement)jsd);
    else if (name != null)
      xsb = type(jsd, name);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsd.getClass().getName());

    if (jsd.getDoc() != null && jsd.getDoc().length() > 0)
      xsb.setDoc$(new $Documented.Doc$(jsd.getDoc()));

    if (jsd.getScale() != null)
      xsb.setScale$(new $NumberMember.Scale$(jsd.getScale()));

    if (jsd.getRange() != null)
      xsb.setRange$(new $NumberMember.Range$(jsd.getRange()));

    return xsb;
  }

  static NumberModel declare(final Registry registry, final Declarer declarer, final Schema.Number xsb) {
    return registry.declare(xsb).value(new NumberModel(registry, declarer, xsb), null);
  }

  static NumberModel reference(final Registry registry, final Referrer<?> referrer, final $Number xsb) {
    try {
      return registry.reference(newNumberModel(registry, referrer, xsb), referrer);
    }
    catch (final ParseException e) {
      throw createValidationException(xsb, xsb.getRange$().text(), e);
    }
  }

  static NumberModel reference(final Registry registry, final Referrer<?> referrer, final $Array.Number xsb) {
    try {
      return registry.reference(new NumberModel(registry, referrer, xsb, getBinding(registry, xsb.getBinding())), referrer);
    }
    catch (final ParseException e) {
      throw createValidationException(xsb, xsb.getRange$().text(), e);
    }
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final NumberProperty property, final Method getMethod, final String fieldName) {
    try {
      final NumberModel model = newNumberModel(registry, referrer, property, getMethod, fieldName);
      final Id id = model.id();

      final NumberModel registered = (NumberModel)registry.getModel(id);
      return new Reference(registry, referrer, property.name(), property.nullable(), property.use(), fieldName, model.typeBinding, registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
    }
    catch (final ParseException e) {
      throw createValidationException(property.annotationType(), property.range(), e);
    }
  }

  static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final NumberElement element) {
    try {
      final NumberModel model = new NumberModel(registry, referrer, element.nullable(), element.scale(), element.range(), Binding.Type.from(registry, element.type(), element.decode(), element.encode(), getDefaultClass(element.scale()), Number.class));
      final Id id = model.id();

      final NumberModel registered = (NumberModel)registry.getModel(id);
      return new Reference(registry, referrer, element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
    }
    catch (final ParseException e) {
      throw createValidationException(element.annotationType(), element.range(), e);
    }
  }

  static NumberModel referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final NumberType type) {
    try {
      // Note: Explicitly setting nullable=false, because nullable for *Type annotations is set at the AnyElement/AnyProperty level
      final NumberModel model = new NumberModel(registry, referrer, false, type.scale(), type.range(), Binding.Type.from(registry, type.type(), type.decode(), type.encode(), getDefaultClass(type.scale()), Number.class));
      final Id id = model.id();

      final NumberModel registered = (NumberModel)registry.getModel(id);
      return registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer);
    }
    catch (final ParseException e) {
      throw createValidationException(type.annotationType(), type.range(), e);
    }
  }

  private static ValidationException createValidationException(final $AnySimpleType xsb, final String range, final ParseException e) {
    return new ValidationException(Bindings.getXPath(xsb, elementXPath) + "/@range=" + range, e);
  }

  private static ValidationException createValidationException(final Class<?> cls, final String range, final ParseException e) {
    throw new ValidationException(cls.getName() + " Invalid range=\"" + range + "\"", e);
  }

  private static int parseScale(final $NumberMember.Scale$ scale) {
    return scale == null || scale.text() == null ? Integer.MAX_VALUE : scale.text().intValue();
  }

  private static Range parseRange(final String range) throws ParseException {
    return range == null || range.length() == 0 ? null : new Range(range);
  }

  private static Range parseRange(final $NumberMember.Range$ range) throws ParseException {
    return range == null ? null : parseRange(range.text());
  }

  private static Class<?> getDefaultClass(final int scale) {
    return scale == 0 ? BigInteger.class : BigDecimal.class;
  }

  final int scale;
  final Range range;

  private NumberModel(final Registry registry, final Declarer declarer, final Schema.Number xsb) {
    super(registry, declarer, Id.named(xsb.getName$()), xsb.getDoc$(), xsb.getName$().text(), getBinding(registry, xsb.getBinding()));
    this.scale = parseScale(xsb.getScale$());
    try {
      this.range = parseRange(xsb.getRange$());
    }
    catch (final ParseException e) {
      throw createValidationException(xsb, xsb.getRange$().text(), e);
    }

    validateTypeBinding();
  }

  private static NumberModel newNumberModel(final Registry registry, final Declarer declarer, final $Number xsb) throws ParseException {
    final $TypeFieldBinding binding = getBinding(xsb.getBinding());
    if (binding == null)
      return new NumberModel(registry, declarer, xsb, null, null);

    return new NumberModel(registry, declarer, xsb, binding.getField$(), Binding.Type.from(registry, binding.getType$(), binding.getDecode$(), binding.getEncode$()));
  }

  private NumberModel(final Registry registry, final Declarer declarer, final $Number xsb, final $FieldIdentifier fieldName, final Binding.Type typeBinding) throws ParseException {
    super(registry, declarer, Id.hashed("n", typeBinding, parseScale(xsb.getScale$()), parseRange(xsb.getRange$())), xsb.getDoc$(), xsb.getName$(), xsb.getNullable$(), xsb.getUse$(), fieldName, typeBinding);
    this.scale = parseScale(xsb.getScale$());
    this.range = parseRange(xsb.getRange$());

    validateTypeBinding();
  }

  private NumberModel(final Registry registry, final Declarer declarer, final $Array.Number xsb, final Binding.Type typeBinding) throws ParseException {
    super(registry, declarer, Id.hashed("n", typeBinding, parseScale(xsb.getScale$()), parseRange(xsb.getRange$())), xsb.getDoc$(), xsb.getNullable$(), xsb.getMinOccurs$(), xsb.getMaxOccurs$(), typeBinding);
    this.scale = parseScale(xsb.getScale$());
    this.range = parseRange(xsb.getRange$());

    validateTypeBinding();
  }

  private static NumberModel newNumberModel(final Registry registry, final Declarer declarer, final NumberProperty property, final Method getMethod, final String fieldName) throws ParseException {
    final Binding.Type typeBinding = Binding.Type.from(registry, getMethod, property.nullable(), property.use(), property.decode(), property.encode(), getDefaultClass(property.scale()));
    return new NumberModel(registry, declarer, property, getMethod, fieldName, typeBinding);
  }

  private NumberModel(final Registry registry, final Declarer declarer, final NumberProperty property, final Method getMethod, final String fieldName, final Binding.Type typeBinding) throws ParseException {
    super(registry, declarer, Id.hashed("n", typeBinding, property.scale(), parseRange(property.range())), property.nullable(), property.use(), fieldName, typeBinding);
    // TODO: Can this be parameterized and moved to Model#validateTypeBinding?
    if (!isAssignable(getMethod, true, defaultClass(), false, property.nullable(), property.use()) && !isAssignable(getMethod, true, CharSequence.class, false, property.nullable(), property.use()) || getMethod.getReturnType().isPrimitive() && (property.use() == Use.OPTIONAL || property.nullable()))
      throw new IllegalAnnotationException(property, getMethod.getDeclaringClass().getName() + "." + getMethod.getName() + "(): @" + NumberProperty.class.getSimpleName() + " can only be applied to fields of Object type with use=\"required\" or nullable=false, or of Optional<Object> type with use=\"optional\" and nullable=true");

    this.scale = property.scale();
    this.range = parseRange(property.range());

    validateTypeBinding();
  }

  private NumberModel(final Registry registry, final Declarer declarer, final Boolean nullable, final int scale, final String range, final Binding.Type typeBinding) throws ParseException {
    super(registry, declarer, Id.hashed("n", typeBinding, scale, parseRange(range)), nullable, null, null, typeBinding);
    this.scale = scale;
    this.range = parseRange(range);

    validateTypeBinding();
  }

  @Override
  Registry.Type typeDefault() {
    return registry.getType(getDefaultClass(scale));
  }

  @Override
  @SuppressWarnings("unchecked")
  String isValid(final Binding.Type typeBinding) {
    if (typeBinding.type == null)
      return null;

    if ((scale != Integer.MAX_VALUE || range != null) && !registry.getType(Number.class).isAssignableFrom(typeBinding.type))
      return "Constraint definitions in \"" + (name() != null ? name() : id().toString()) + "\" are not enforcable with type binding \"" + typeBinding.type + "\" for \"" + elementName() + "\"; either choose a type binding that is assignable to " + defaultClass() + ", or remove the constraint definitions";

    if (scale == 0)
      return null;

    final Class<?> cls = Classes.forNameOrNull(typeBinding.type.getNativeName(), false, getClass().getClassLoader());
    if (cls != null && defaultClass().isAssignableFrom(cls) && Numbers.isWholeType((Class<? extends Number>)cls))
      return "The decimal \"number\" with scale=" + scale + " in \"" + (name() != null ? name() : id().toString()) + "\" cannot be represented with the whole numeric type: " + typeBinding.type.getName();

    return null;
  }

  @Override
  String elementName() {
    return "number";
  }

  @Override
  Class<?> defaultClass() {
    return Number.class;
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
  Class<? extends Annotation> typeAnnotation() {
    return NumberType.class;
  }

  @Override
  Map<String,Object> toXmlAttributes(final Element owner, final String packageName) {
    final Map<String,Object> attributes = super.toXmlAttributes(owner, packageName);
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

    final Registry.Type type = type();
    if (type != null && (owner instanceof AnyModel || owner instanceof ArrayModel))
      attributes.put("type", type + ".class");
  }
}