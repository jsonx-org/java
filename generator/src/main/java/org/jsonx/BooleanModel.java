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

import org.jsonx.www.binding_0_5.xL1gluGCXAA.$FieldBinding;
import org.jsonx.www.binding_0_5.xL1gluGCXAA.$FieldIdentifier;
import org.jsonx.www.binding_0_5.xL1gluGCXAA.$CodecTypeFieldBinding;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Array;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$ArrayMember;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Boolean;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$BooleanMember;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Documented;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Member;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$ObjectMember;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.Schema;
import org.libj.lang.IllegalAnnotationException;
import org.openjax.xml.api.XmlElement;
import org.w3.www._2001.XMLSchema.yAA.$AnyType;

final class BooleanModel extends Model {
  private static Schema.Boolean type(final String name) {
    final Schema.Boolean xsb = new Schema.Boolean();
    if (name != null)
      xsb.setName$(new Schema.Boolean.Name$(name));

    return xsb;
  }

  private static $Boolean property(final schema.BooleanProperty jsd, final String name) {
    final $Boolean xsb = new $Boolean() {
      @Override
      protected $Member inherits() {
        return new $ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setName$(new $Boolean.Name$(name));

    final Boolean nullable = jsd.get40nullable();
    if (nullable != null)
      xsb.setNullable$(new $Boolean.Nullable$(nullable));

    final String use = jsd.get40use();
    if (use != null)
      xsb.setUse$(new $Boolean.Use$($Boolean.Use$.Enum.valueOf(use)));

    return xsb;
  }

  private static $ArrayMember.Boolean element(final schema.BooleanElement jsd) {
    final $ArrayMember.Boolean xsb = new $ArrayMember.Boolean();

    final Boolean nullable = jsd.get40nullable();
    if (nullable != null)
      xsb.setNullable$(new $ArrayMember.Boolean.Nullable$(nullable));

    final String minOccurs = jsd.get40minOccurs();
    if (minOccurs != null)
      xsb.setMinOccurs$(new $ArrayMember.Boolean.MinOccurs$(new BigInteger(minOccurs)));

    final String maxOccurs = jsd.get40maxOccurs();
    if (maxOccurs != null)
      xsb.setMaxOccurs$(new $ArrayMember.Boolean.MaxOccurs$(maxOccurs));

    return xsb;
  }

  static $BooleanMember jsdToXsb(final schema.Boolean jsd, final String name) {
    final $BooleanMember xsb;
    if (jsd instanceof schema.BooleanProperty)
      xsb = property((schema.BooleanProperty)jsd, name);
    else if (jsd instanceof schema.BooleanElement)
      xsb = element((schema.BooleanElement)jsd);
    else if (name != null)
      xsb = type(name);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsd.getClass().getName());

    final String doc = jsd.get40doc();
    if (doc != null && doc.length() > 0)
      xsb.setDoc$(new $Documented.Doc$(doc));

    return xsb;
  }

  static BooleanModel declare(final Registry registry, final Declarer declarer, final Schema.Boolean xsb, final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding) {
    return registry.declare(xsb).value(new BooleanModel(registry, declarer, xsb, xsbToBinding), null);
  }

  static BooleanModel referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final Schema.Boolean xsb, final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding) {
    final BooleanModel model = new BooleanModel(registry, referrer, xsb, xsbToBinding);
    final Id id = model.id();
    final BooleanModel registered = (BooleanModel)registry.getModel(id);
    return registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer);
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final BooleanProperty property, final Method getMethod, final String fieldName) {
    final BooleanModel model = newBooleanModel(registry, referrer, property, getMethod, fieldName);
    final Id id = model.id();
    final BooleanModel registered = (BooleanModel)registry.getModel(id);
    return new Reference(registry, referrer, property.name(), property.nullable(), property.use(), fieldName, model.typeBinding, registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final BooleanElement element) {
    final BooleanModel model = new BooleanModel(registry, referrer, element.nullable(), Bind.Type.from(registry, element.type(), element.decode(), element.encode(), Boolean.class));
    final Id id = model.id();
    final BooleanModel registered = (BooleanModel)registry.getModel(id);
    return new Reference(registry, referrer, element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static BooleanModel referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final BooleanType type) {
    // Note: Explicitly setting nullable=false, because nullable for *Type annotations is set at the AnyElement/AnyProperty level
    final BooleanModel model = new BooleanModel(registry, referrer, false, Bind.Type.from(registry, type.type(), type.decode(), type.encode(), Boolean.class));
    final Id id = model.id();
    final BooleanModel registered = (BooleanModel)registry.getModel(id);
    return registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer);
  }

  static BooleanModel reference(final Registry registry, final Referrer<?> referrer, final $Array.Boolean xsb, final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding) {
    return registry.reference(new BooleanModel(registry, referrer, xsb, getBinding(registry, xsbToBinding == null ? null : ($CodecTypeFieldBinding)xsbToBinding.get(xsb))), referrer);
  }

  static BooleanModel reference(final Registry registry, final Referrer<?> referrer, final $Boolean xsb, final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding) {
    return registry.reference(newBooleanModel(registry, referrer, xsb, xsbToBinding == null ? null : ($CodecTypeFieldBinding)xsbToBinding.get(xsb)), referrer);
  }

  private BooleanModel(final Registry registry, final Declarer declarer, final Schema.Boolean xsb, final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding) {
    super(registry, declarer, Id.named(xsb.getName$()), xsb.getDoc$(), xsb.getName$().text(), getBinding(registry, xsbToBinding == null ? null : ($CodecTypeFieldBinding)xsbToBinding.get(xsb)));
    validateTypeBinding();
  }

  private static BooleanModel newBooleanModel(final Registry registry, final Declarer declarer, final $Boolean xsb, final $CodecTypeFieldBinding binding) {
    if (binding == null)
      return new BooleanModel(registry, declarer, xsb, null, null);

    return new BooleanModel(registry, declarer, xsb, binding.getField$(), Bind.Type.from(registry, binding.getType$(), binding.getDecode$(), binding.getEncode$()));
  }

  private BooleanModel(final Registry registry, final Declarer declarer, final $Boolean xsb, final $FieldIdentifier fieldName, final Bind.Type typeBinding) {
    super(registry, declarer, Id.hashed("b", typeBinding), xsb.getDoc$(), xsb.getName$(), xsb.getNullable$(), xsb.getUse$(), fieldName, typeBinding);
    validateTypeBinding();
  }

  private BooleanModel(final Registry registry, final Declarer declarer, final $Array.Boolean xsb, final Bind.Type typeBinding) {
    super(registry, declarer, Id.hashed("b", typeBinding), xsb.getDoc$(), xsb.getNullable$(), xsb.getMinOccurs$(), xsb.getMaxOccurs$(), typeBinding);
    validateTypeBinding();
  }

  private static BooleanModel newBooleanModel(final Registry registry, final Declarer declarer, final BooleanProperty property, final Method getMethod, final String fieldName) {
    final Bind.Type typeBinding = Bind.Type.from(registry, getMethod, property.nullable(), property.use(), property.decode(), property.encode(), Boolean.class);
    return new BooleanModel(registry, declarer, property, getMethod, fieldName, typeBinding);
  }

  private BooleanModel(final Registry registry, final Declarer declarer, final BooleanProperty property, final Method getMethod, final String fieldName, final Bind.Type typeBinding) {
    super(registry, declarer, Id.hashed("b", typeBinding), property.nullable(), property.use(), fieldName, typeBinding);
    // If there is a "decode" spec, then skip the check to verify field<-->{CharSequence,char[]} compatibility
    final boolean hasDecode = typeBinding != null && typeBinding.decode != null;
    // TODO: Can this be parameterized and moved to Model#validateTypeBinding?
    if (!isAssignable(getMethod, true, false, property.nullable(), property.use(), true, hasDecode ? null : defaultClass()) && !isAssignable(getMethod, true, false, property.nullable(), property.use(), true, CharSequence.class) || getMethod.getReturnType().isPrimitive() && (property.use() == Use.OPTIONAL || property.nullable()))
      throw new IllegalAnnotationException(property, getMethod.getDeclaringClass().getName() + "." + getMethod.getName() + "(): @" + BooleanProperty.class.getSimpleName() + " can only be applied to fields of Object type with use=\"required\" or nullable=false, or of Optional<Object> type with use=\"optional\" and nullable=true");

    validateTypeBinding();
  }

  private BooleanModel(final Registry registry, final Declarer declarer, final Boolean nullable, final Bind.Type typeBinding) {
    super(registry, declarer, Id.hashed("b", typeBinding), nullable, null, null, typeBinding);
    validateTypeBinding();
  }

  @Override
  Registry.Type typeDefault(final boolean primitive) {
    return registry.getType(primitive ? boolean.class : Boolean.class);
  }

  @Override
  String isValid(final Bind.Type typeBinding) {
    return null;
  }

  @Override
  String elementName() {
    return "boolean";
  }

  @Override
  Class<?> defaultClass() {
    return Boolean.class;
  }

  @Override
  Class<? extends Annotation> propertyAnnotation() {
    return BooleanProperty.class;
  }

  @Override
  Class<? extends Annotation> elementAnnotation() {
    return BooleanElement.class;
  }

  @Override
  Class<? extends Annotation> typeAnnotation() {
    return BooleanType.class;
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
}