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
import java.math.BigInteger;
import java.util.IdentityHashMap;

import org.jaxsb.runtime.Bindings;
import org.jsonx.www.binding_0_5.xL1gluGCXAA.$CodecTypeFieldBinding;
import org.jsonx.www.binding_0_5.xL1gluGCXAA.$FieldBinding;
import org.jsonx.www.binding_0_5.xL1gluGCXAA.$FieldIdentifier;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Array;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$ArrayMember;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Documented;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Member;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Number;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$NumberMember;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$NumberMember.Range$;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$ObjectMember;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.Schema;
import org.libj.lang.Classes;
import org.libj.lang.IllegalAnnotationException;
import org.libj.lang.Numbers;
import org.libj.lang.ParseException;
import org.openjax.xml.api.XmlElement;
import org.w3.www._2001.XMLSchema.yAA.$AnyType;

final class NumberModel extends Model {
  private static Schema.Number type(final schema.Number jsd, final String name) {
    final Schema.Number xsb = new Schema.Number();
    if (name != null)
      xsb.setName$(new Schema.Number.Name$(name));

    return xsb;
  }

  private static $Number property(final schema.NumberProperty jsd, final String name) {
    final $Number xsb = new $Number() {
      @Override
      protected $Member inherits() {
        return new $ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setName$(new $Number.Name$(name));

    final Boolean nullable = jsd.get40nullable();
    if (nullable != null)
      xsb.setNullable$(new $Number.Nullable$(nullable));

    final String use = jsd.get40use();
    if (use != null)
      xsb.setUse$(new $Number.Use$($Number.Use$.Enum.valueOf(use)));

    return xsb;
  }

  private static $ArrayMember.Number element(final schema.NumberElement jsd) {
    final $ArrayMember.Number xsb = new $ArrayMember.Number();

    final Boolean nullable = jsd.get40nullable();
    if (nullable != null)
      xsb.setNullable$(new $ArrayMember.Number.Nullable$(nullable));

    final String minOccurs = jsd.get40minOccurs();
    if (minOccurs != null)
      xsb.setMinOccurs$(new $ArrayMember.Number.MinOccurs$(new BigInteger(minOccurs)));

    final String maxOccurs = jsd.get40maxOccurs();
    if (maxOccurs != null)
      xsb.setMaxOccurs$(new $ArrayMember.Number.MaxOccurs$(maxOccurs));

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

    final String doc = jsd.get40doc();
    if (doc != null && doc.length() > 0)
      xsb.setDoc$(new $Documented.Doc$(doc));

    final Long scale = jsd.get40scale();
    if (scale != null)
      xsb.setScale$(new $NumberMember.Scale$(scale));

    final String range = jsd.get40range();
    if (range != null)
      xsb.setRange$(new $NumberMember.Range$(range));

    return xsb;
  }

  static NumberModel declare(final Registry registry, final Declarer declarer, final Schema.Number xsb, final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding) {
    return registry.declare(xsb).value(new NumberModel(registry, declarer, xsb, xsbToBinding), null);
  }

  static NumberModel reference(final Registry registry, final Referrer<?> referrer, final $Number xsb, final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding) {
    try {
      return registry.reference(newNumberModel(registry, referrer, xsb, xsbToBinding == null ? null : ($CodecTypeFieldBinding)xsbToBinding.get(xsb)), referrer);
    }
    catch (final ParseException e) {
      throw createValidationException(xsb, xsb.getRange$().text(), e);
    }
  }

  static NumberModel reference(final Registry registry, final Referrer<?> referrer, final $Array.Number xsb, final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding) {
    try {
      return registry.reference(new NumberModel(registry, referrer, xsb, getBinding(registry, xsbToBinding == null ? null : ($CodecTypeFieldBinding)xsbToBinding.get(xsb))), referrer);
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
      final NumberModel model = new NumberModel(registry, referrer, element.nullable(), element.scale(), element.range(), Bind.Type.from(registry, element.type(), element.decode(), element.encode(), typeDefault(element.scale(), false), Number.class));
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
      final NumberModel model = new NumberModel(registry, referrer, false, type.scale(), type.range(), Bind.Type.from(registry, type.type(), type.decode(), type.encode(), typeDefault(type.scale(), false), Number.class));
      final Id id = model.id();
      final NumberModel registered = (NumberModel)registry.getModel(id);
      return registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer);
    }
    catch (final ParseException e) {
      throw createValidationException(type.annotationType(), type.range(), e);
    }
  }

  private static ValidationException createValidationException(final $AnyType<?> anyType, final String range, final ParseException e) {
    return new ValidationException(Bindings.getXPath(anyType, elementXPath) + "/@range=" + range, e);
  }

  private static ValidationException createValidationException(final Class<?> cls, final String range, final ParseException e) {
    throw new ValidationException(cls.getName() + " Invalid range=\"" + range + "\"", e);
  }

  private static int parseScale(final $NumberMember.Scale$ scale) {
    return scale == null || scale.text() == null ? Integer.MAX_VALUE : scale.text().intValue();
  }

  private static Range parseRange(final String range, final int scale, final Class<?> type) throws ParseException {
    return range == null || range.length() == 0 ? null : Range.from(range, scale, type);
  }

  private static Range parseRange(final $NumberMember.Range$ range, final int scale, final Class<?> type) throws ParseException {
    return range == null ? null : parseRange(range.text(), scale, type);
  }

  final int scale;
  final Range range;

  private NumberModel(final Registry registry, final Declarer declarer, final Schema.Number xsb, final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding) {
    super(registry, declarer, Id.named(xsb.getName$()), xsb.getDoc$(), xsb.getName$().text(), getBinding(registry, xsbToBinding == null ? null : ($CodecTypeFieldBinding)xsbToBinding.get(xsb)));
    this.scale = parseScale(xsb.getScale$());
    final Class<?> type = validateTypeBinding();
    final Range$ range$ = xsb.getRange$();
    try {
      this.range = parseRange(range$, scale, type);
    }
    catch (final ParseException e) {
      throw createValidationException(xsb, range$.text(), e);
    }
  }

  private static NumberModel newNumberModel(final Registry registry, final Declarer declarer, final $Number xsb, final $CodecTypeFieldBinding binding) throws ParseException {
    if (binding == null)
      return new NumberModel(registry, declarer, xsb, null, null);

    return new NumberModel(registry, declarer, xsb, binding.getField$(), Bind.Type.from(registry, binding.getType$(), binding.getDecode$(), binding.getEncode$()));
  }

  private NumberModel(final Registry registry, final Declarer declarer, final $Number xsb, final $FieldIdentifier fieldName, final Bind.Type typeBinding) throws ParseException {
    super(registry, declarer, Id.hashed("n", typeBinding, parseScale(xsb.getScale$()), parseRange(xsb.getRange$(), parseScale(xsb.getScale$()), null)), xsb.getDoc$(), xsb.getName$(), xsb.getNullable$(), xsb.getUse$(), fieldName, typeBinding);
    this.scale = parseScale(xsb.getScale$());
    final Class<?> type = validateTypeBinding();
    this.range = parseRange(xsb.getRange$(), scale, type);
  }

  private NumberModel(final Registry registry, final Declarer declarer, final $Array.Number xsb, final Bind.Type typeBinding) throws ParseException {
    super(registry, declarer, Id.hashed("n", typeBinding, parseScale(xsb.getScale$()), parseRange(xsb.getRange$(), parseScale(xsb.getScale$()), null)), xsb.getDoc$(), xsb.getNullable$(), xsb.getMinOccurs$(), xsb.getMaxOccurs$(), typeBinding);
    this.scale = parseScale(xsb.getScale$());
    final Class<?> type = validateTypeBinding();
    this.range = parseRange(xsb.getRange$(), scale, type);
  }

  private static NumberModel newNumberModel(final Registry registry, final Declarer declarer, final NumberProperty property, final Method getMethod, final String fieldName) throws ParseException {
    final Bind.Type typeBinding = Bind.Type.from(registry, getMethod, property.nullable(), property.use(), property.decode(), property.encode(), typeDefault(property.scale(), false));
    return new NumberModel(registry, declarer, property, getMethod, fieldName, typeBinding);
  }

  private NumberModel(final Registry registry, final Declarer declarer, final NumberProperty property, final Method getMethod, final String fieldName, final Bind.Type typeBinding) throws ParseException {
    super(registry, declarer, Id.hashed("n", typeBinding, property.scale(), parseRange(property.range(), property.scale(), null)), property.nullable(), property.use(), fieldName, typeBinding);
    // TODO: Can this be parameterized and moved to Model#validateTypeBinding?
    if (!isAssignable(getMethod, true, false, property.nullable(), property.use(), true, defaultClass()) && !isAssignable(getMethod, true, false, property.nullable(), property.use(), true, CharSequence.class) || getMethod.getReturnType().isPrimitive() && (property.use() == Use.OPTIONAL || property.nullable()))
      throw new IllegalAnnotationException(property, getMethod.getDeclaringClass().getName() + "." + getMethod.getName() + "(): @" + NumberProperty.class.getSimpleName() + " can only be applied to fields of Object type with use=\"required\" or nullable=false, or of Optional<Object> type with use=\"optional\" and nullable=true");

    this.scale = property.scale();
    final Class<?> type = validateTypeBinding();
    this.range = parseRange(property.range(), scale, type);
  }

  private NumberModel(final Registry registry, final Declarer declarer, final Boolean nullable, final int scale, final String range, final Bind.Type typeBinding) throws ParseException {
    super(registry, declarer, Id.hashed("n", typeBinding, scale, parseRange(range, scale, null)), nullable, null, null, typeBinding);
    this.scale = scale;
    final Class<?> type = validateTypeBinding();
    this.range = parseRange(range, scale, type);
  }

  private static Class<?> typeDefault(final int scale, final boolean primitive) {
    return primitive ? (scale == 0 ? long.class : double.class) : scale == 0 ? Long.class : Double.class;
  }

  @Override
  Registry.Type typeDefault(final boolean primitive) {
    return registry.getType(typeDefault(scale, primitive));
  }

  @Override
  @SuppressWarnings("unchecked")
  String isValid(final Bind.Type typeBinding) {
    if (typeBinding.type == null)
      return null;

    if ((scale != Integer.MAX_VALUE || range != null) && !registry.getType(Number.class).isAssignableFrom(typeBinding.type))
      return "Constraint definitions in \"" + (name() != null ? name() : id()) + "\" are not enforcable with type binding \"" + typeBinding.type + "\" for \"" + elementName() + "\"; either choose a type binding that is assignable to " + defaultClass() + ", or remove the constraint definitions";

    if (scale == 0)
      return null;

    final Class<?> cls = Classes.forNameOrNull(typeBinding.type.getNativeName(), false, getClass().getClassLoader());
    if (cls != null && defaultClass().isAssignableFrom(cls) && Numbers.isWholeNumberType((Class<? extends Number>)cls))
      return "The decimal \"number\" with scale=" + scale + " in \"" + (name() != null ? name() : id()) + "\" cannot be represented with the whole numeric type: " + typeBinding.type.name;

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
  XmlElement toXml(final Element owner, final String packageName, final JsonPath.Cursor cursor, final PropertyMap<AttributeMap> pathToBinding, final boolean isFromReference) {
    final XmlElement element = super.toXml(owner, packageName, cursor, pathToBinding, isFromReference);
    cursor.popName();
    return element;
  }

  @Override
  PropertyMap<Object> toJson(final Element owner, final String packageName, final JsonPath.Cursor cursor, final PropertyMap<AttributeMap> pathToBinding, final boolean isFromReference) {
    final PropertyMap<Object> properties = super.toJson(owner, packageName, cursor, pathToBinding, isFromReference);
    cursor.popName();
    return properties;
  }

  @Override
  AttributeMap toSchemaAttributes(final Element owner, final String packageName, final boolean jsd) {
    final AttributeMap attributes = super.toSchemaAttributes(owner, packageName, jsd);
    if (scale != Integer.MAX_VALUE)
      attributes.put(jsd(jsd, "scale"), scale);

    if (range != null)
      attributes.put(jsd(jsd, "range"), range.toString());

    return attributes;
  }

  @Override
  void toAnnotationAttributes(final AttributeMap attributes, final Member owner) {
    super.toAnnotationAttributes(attributes, owner);
    if (scale != Integer.MAX_VALUE)
      attributes.put("scale", scale);

    if (range != null)
      attributes.put("range", "\"" + range + "\"");

    final Registry.Type type = type();
    if (type != null && (owner instanceof AnyModel || owner instanceof ArrayModel))
      attributes.put("type", type + ".class");
  }
}