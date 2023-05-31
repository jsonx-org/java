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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.RandomAccess;

import org.jaxsb.runtime.Bindings;
import org.jsonx.schema.TypeBinding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Array;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$ArrayMember;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Binding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Documented;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$FieldIdentifier;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Member;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Number;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$NumberMember;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$NumberMember.Range$;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$ObjectMember;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$TypeBinding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$TypeFieldBinding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.Schema;
import org.libj.lang.Classes;
import org.libj.lang.IllegalAnnotationException;
import org.libj.lang.Numbers;
import org.libj.lang.ParseException;
import org.w3.www._2001.XMLSchema.yAA.$AnyType;

final class NumberModel extends Model {
  private static $TypeBinding typeBinding(final schema.TypeBinding jsd) {
    final $TypeBinding xsb = new $ArrayMember.Number.Binding();
    xsb.setLang$(new $Binding.Lang$(jsd.getLang()));
    final String type = jsd.getType();
    if (type != null)
      xsb.setType$(new $TypeBinding.Type$(type));

    final String decode = jsd.getDecode();
    if (decode != null)
      xsb.setDecode$(new $TypeBinding.Decode$(decode));

    final String encode = jsd.getEncode();
    if (encode != null)
      xsb.setEncode$(new $TypeBinding.Encode$(encode));

    return xsb;
  }

  private static Schema.Number type(final schema.Number jsd, final String name) {
    final Schema.Number xsb = new Schema.Number();
    if (name != null)
      xsb.setName$(new Schema.Number.Name$(name));

    final List<TypeBinding> bindings = jsd.getBindings();
    final int i$;
    if (bindings != null && (i$ = bindings.size()) > 0) {
      if (bindings instanceof RandomAccess) {
        int i = 0; do // [RA]
          xsb.addBinding(typeBinding(bindings.get(i)));
        while (++i < i$);
      }
      else {
        final Iterator<schema.TypeBinding> i = bindings.iterator(); do // [I]
          xsb.addBinding(typeBinding(i.next()));
        while (i.hasNext());
      }
    }

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

    final Boolean nullable = jsd.getNullable();
    if (nullable != null)
      xsb.setNullable$(new $Number.Nullable$(nullable));

    final String use = jsd.getUse();
    if (use != null)
      xsb.setUse$(new $Number.Use$($Number.Use$.Enum.valueOf(use)));

    final List<TypeBinding> bindings = jsd.getBindings();
    final int i$;
    if (bindings != null && (i$ = bindings.size()) > 0) {
      if (bindings instanceof RandomAccess) {
        int i = 0; do // [RA]
          addBinding(xsb, (schema.TypeFieldBinding)bindings.get(i));
        while (++i < i$);
      }
      else {
        final Iterator<schema.TypeBinding> i = bindings.iterator(); do // [I]
          addBinding(xsb, (schema.TypeFieldBinding)i.next());
        while (i.hasNext());
      }
    }

    return xsb;
  }

  private static void addBinding(final $Number xsb, final schema.TypeFieldBinding binding) {
    final $TypeFieldBinding bind = new $Number.Binding();
    bind.setLang$(new $Binding.Lang$(binding.getLang()));

    final String type = binding.getType();
    if (type != null)
      bind.setType$(new $TypeBinding.Type$(type));

    final String decode = binding.getDecode();
    if (decode != null)
      bind.setDecode$(new $TypeBinding.Decode$(decode));

    final String encode = binding.getEncode();
    if (encode != null)
      bind.setEncode$(new $TypeBinding.Encode$(encode));

    final String field = binding.getField();
    if (field != null)
      bind.setField$(new $TypeFieldBinding.Field$(field));

    xsb.addBinding(bind);
  }

  private static $ArrayMember.Number element(final schema.NumberElement jsd) {
    final $ArrayMember.Number xsb = new $ArrayMember.Number();

    final Boolean nullable = jsd.getNullable();
    if (nullable != null)
      xsb.setNullable$(new $ArrayMember.Number.Nullable$(nullable));

    final String minOccurs = jsd.getMinOccurs();
    if (minOccurs != null)
      xsb.setMinOccurs$(new $ArrayMember.Number.MinOccurs$(new BigInteger(minOccurs)));

    final String maxOccurs = jsd.getMaxOccurs();
    if (maxOccurs != null)
      xsb.setMaxOccurs$(new $ArrayMember.Number.MaxOccurs$(maxOccurs));

    final List<TypeBinding> bindings = jsd.getBindings();
    final int i$;
    if (bindings != null && (i$ = bindings.size()) > 0) {
      if (bindings instanceof RandomAccess) {
        int i = 0; do // [RA]
          xsb.addBinding(typeBinding(bindings.get(i)));
        while (++i < i$);
      }
      else {
        final Iterator<schema.TypeBinding> i = bindings.iterator(); do // [I]
          xsb.addBinding(typeBinding(i.next()));
        while (i.hasNext());
      }
    }

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

    final String doc = jsd.getDoc();
    if (doc != null && doc.length() > 0)
      xsb.setDoc$(new $Documented.Doc$(doc));

    final Long scale = jsd.getScale();
    if (scale != null)
      xsb.setScale$(new $NumberMember.Scale$(scale));

    final String range = jsd.getRange();
    if (range != null)
      xsb.setRange$(new $NumberMember.Range$(range));

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
      final NumberModel model = new NumberModel(registry, referrer, element.nullable(), element.scale(), element.range(), Binding.Type.from(registry, element.type(), element.decode(), element.encode(), typeDefault(element.scale(), false, registry.settings), Number.class));
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
      final NumberModel model = new NumberModel(registry, referrer, false, type.scale(), type.range(), Binding.Type.from(registry, type.type(), type.decode(), type.encode(), typeDefault(type.scale(), false, registry.settings), Number.class));
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

  private static Range parseRange(final String range, final Class<?> type) throws ParseException {
    return range == null || range.length() == 0 ? null : Range.from(range, type);
  }

  private static Range parseRange(final $NumberMember.Range$ range, final Class<?> type) throws ParseException {
    return range == null ? null : parseRange(range.text(), type);
  }

  final int scale;
  final Range range;

  private NumberModel(final Registry registry, final Declarer declarer, final Schema.Number xsb) {
    super(registry, declarer, Id.named(xsb.getName$()), xsb.getDoc$(), xsb.getName$().text(), getBinding(registry, xsb.getBinding()));
    this.scale = parseScale(xsb.getScale$());
    final Class<?> type = validateTypeBinding();
    final Range$ range$ = xsb.getRange$();
    try {
      this.range = parseRange(range$, type);
    }
    catch (final ParseException e) {
      throw createValidationException(xsb, range$.text(), e);
    }
  }

  private static NumberModel newNumberModel(final Registry registry, final Declarer declarer, final $Number xsb) throws ParseException {
    final $TypeFieldBinding binding = getBinding(xsb.getBinding());
    if (binding == null)
      return new NumberModel(registry, declarer, xsb, null, null);

    return new NumberModel(registry, declarer, xsb, binding.getField$(), Binding.Type.from(registry, binding.getType$(), binding.getDecode$(), binding.getEncode$()));
  }

  private NumberModel(final Registry registry, final Declarer declarer, final $Number xsb, final $FieldIdentifier fieldName, final Binding.Type typeBinding) throws ParseException {
    super(registry, declarer, Id.hashed("n", typeBinding, parseScale(xsb.getScale$()), parseRange(xsb.getRange$(), null)), xsb.getDoc$(), xsb.getName$(), xsb.getNullable$(), xsb.getUse$(), fieldName, typeBinding);
    this.scale = parseScale(xsb.getScale$());
    final Class<?> type = validateTypeBinding();
    this.range = parseRange(xsb.getRange$(), type);
  }

  private NumberModel(final Registry registry, final Declarer declarer, final $Array.Number xsb, final Binding.Type typeBinding) throws ParseException {
    super(registry, declarer, Id.hashed("n", typeBinding, parseScale(xsb.getScale$()), parseRange(xsb.getRange$(), null)), xsb.getDoc$(), xsb.getNullable$(), xsb.getMinOccurs$(), xsb.getMaxOccurs$(), typeBinding);
    this.scale = parseScale(xsb.getScale$());
    final Class<?> type = validateTypeBinding();
    this.range = parseRange(xsb.getRange$(), type);
  }

  private static NumberModel newNumberModel(final Registry registry, final Declarer declarer, final NumberProperty property, final Method getMethod, final String fieldName) throws ParseException {
    final Binding.Type typeBinding = Binding.Type.from(registry, getMethod, property.nullable(), property.use(), property.decode(), property.encode(), typeDefault(property.scale(), false, registry.settings));
    return new NumberModel(registry, declarer, property, getMethod, fieldName, typeBinding);
  }

  private NumberModel(final Registry registry, final Declarer declarer, final NumberProperty property, final Method getMethod, final String fieldName, final Binding.Type typeBinding) throws ParseException {
    super(registry, declarer, Id.hashed("n", typeBinding, property.scale(), parseRange(property.range(), null)), property.nullable(), property.use(), fieldName, typeBinding);
    // TODO: Can this be parameterized and moved to Model#validateTypeBinding?
    if (!isAssignable(getMethod, true, defaultClass(), false, property.nullable(), property.use()) && !isAssignable(getMethod, true, CharSequence.class, false, property.nullable(), property.use()) || getMethod.getReturnType().isPrimitive() && (property.use() == Use.OPTIONAL || property.nullable()))
      throw new IllegalAnnotationException(property, getMethod.getDeclaringClass().getName() + "." + getMethod.getName() + "(): @" + NumberProperty.class.getSimpleName() + " can only be applied to fields of Object type with use=\"required\" or nullable=false, or of Optional<Object> type with use=\"optional\" and nullable=true");

    this.scale = property.scale();
    final Class<?> type = validateTypeBinding();
    this.range = parseRange(property.range(), type);
  }

  private NumberModel(final Registry registry, final Declarer declarer, final Boolean nullable, final int scale, final String range, final Binding.Type typeBinding) throws ParseException {
    super(registry, declarer, Id.hashed("n", typeBinding, scale, parseRange(range, null)), nullable, null, null, typeBinding);
    this.scale = scale;
    final Class<?> type = validateTypeBinding();
    this.range = parseRange(range, type);
  }

  private static Class<?> typeDefault(final int scale, final boolean primitive, final Settings settings) {
    return scale == 0 ? (primitive ? settings.getIntegerPrimitive() : settings.getIntegerObject()) : (primitive ? settings.getRealPrimitive() : settings.getRealObject());
  }

  @Override
  Registry.Type typeDefault(final boolean primitive) {
    return registry.getType(typeDefault(scale, primitive, registry.settings));
  }

  @Override
  @SuppressWarnings("unchecked")
  String isValid(final Binding.Type typeBinding) {
    if (typeBinding.type == null)
      return null;

    if ((scale != Integer.MAX_VALUE || range != null) && !registry.getType(Number.class).isAssignableFrom(typeBinding.type))
      return "Constraint definitions in \"" + (name() != null ? name() : id()) + "\" are not enforcable with type binding \"" + typeBinding.type + "\" for \"" + elementName() + "\"; either choose a type binding that is assignable to " + defaultClass() + ", or remove the constraint definitions";

    if (scale == 0)
      return null;

    final Class<?> cls = Classes.forNameOrNull(typeBinding.type.getNativeName(), false, getClass().getClassLoader());
    if (cls != null && defaultClass().isAssignableFrom(cls) && Numbers.isWholeType((Class<? extends Number>)cls))
      return "The decimal \"number\" with scale=" + scale + " in \"" + (name() != null ? name() : id()) + "\" cannot be represented with the whole numeric type: " + typeBinding.type.getName();

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
      attributes.put("range", "\"" + range + "\"");

    final Registry.Type type = type();
    if (type != null && (owner instanceof AnyModel || owner instanceof ArrayModel))
      attributes.put("type", type + ".class");
  }
}