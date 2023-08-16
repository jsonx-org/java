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
import java.util.RandomAccess;

import org.jsonx.schema.TypeBinding;
import org.jsonx.www.binding_0_4.xL1gluGCXAA;
import org.jsonx.www.binding_0_4.xL1gluGCXAA.$FieldIdentifier;
import org.jsonx.www.binding_0_4.xL1gluGCXAA.$TypeFieldBinding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Array;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$ArrayMember;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Binding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Boolean;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$BooleanMember;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Documented;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Member;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$ObjectMember;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$TypeBinding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.Schema;
import org.libj.lang.IllegalAnnotationException;

final class BooleanModel extends Model {
  private static $TypeBinding typeBinding(final schema.TypeBinding jsd) {
    final $TypeBinding xsb = new $ArrayMember.Boolean.Binding();
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

  private static Schema.Boolean type(final schema.Boolean jsd, final String name) {
    final Schema.Boolean xsb = new Schema.Boolean();
    if (name != null)
      xsb.setName$(new Schema.Boolean.Name$(name));

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

  private static $Boolean property(final schema.BooleanProperty jsd, final String name) {
    final $Boolean xsb = new $Boolean() {
      @Override
      protected $Member inherits() {
        return new $ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setName$(new $Boolean.Name$(name));

    final Boolean nullable = jsd.getNullable();
    if (nullable != null)
      xsb.setNullable$(new $Boolean.Nullable$(nullable));

    final String use = jsd.getUse();
    if (use != null)
      xsb.setUse$(new $Boolean.Use$($Boolean.Use$.Enum.valueOf(use)));

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

  private static void addBinding(final $Boolean xsb, final schema.TypeFieldBinding binding) {
    final $TypeFieldBinding bind = new $Boolean.Binding();
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

  private static $ArrayMember.Boolean element(final schema.BooleanElement jsd) {
    final $ArrayMember.Boolean xsb = new $ArrayMember.Boolean();

    final Boolean nullable = jsd.getNullable();
    if (nullable != null)
      xsb.setNullable$(new $ArrayMember.Boolean.Nullable$(nullable));

    final String minOccurs = jsd.getMinOccurs();
    if (minOccurs != null)
      xsb.setMinOccurs$(new $ArrayMember.Boolean.MinOccurs$(new BigInteger(minOccurs)));

    final String maxOccurs = jsd.getMaxOccurs();
    if (maxOccurs != null)
      xsb.setMaxOccurs$(new $ArrayMember.Boolean.MaxOccurs$(maxOccurs));

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

  static $BooleanMember jsdToXsb(final schema.Boolean jsd, final String name) {
    final $BooleanMember xsb;
    if (jsd instanceof schema.BooleanProperty)
      xsb = property((schema.BooleanProperty)jsd, name);
    else if (jsd instanceof schema.BooleanElement)
      xsb = element((schema.BooleanElement)jsd);
    else if (name != null)
      xsb = type(jsd, name);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsd.getClass().getName());

    final String doc = jsd.getDoc();
    if (doc != null && doc.length() > 0)
      xsb.setDoc$(new $Documented.Doc$(doc));

    return xsb;
  }

  static BooleanModel declare(final Registry registry, final Declarer declarer, final Schema.Boolean xsb, final xL1gluGCXAA.$TypeFieldBinding binding) {
    return registry.declare(xsb).value(new BooleanModel(registry, declarer, xsb, binding), null);
  }

  static BooleanModel referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final Schema.Boolean xsb, final xL1gluGCXAA.$TypeFieldBinding binding) {
    final BooleanModel model = new BooleanModel(registry, referrer, xsb, binding);
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

  static BooleanModel reference(final Registry registry, final Referrer<?> referrer, final $Array.Boolean xsb, final xL1gluGCXAA.$TypeFieldBinding binding) {
    return registry.reference(new BooleanModel(registry, referrer, xsb, getBinding(registry, binding)), referrer);
  }

  static BooleanModel reference(final Registry registry, final Referrer<?> referrer, final $Boolean xsb, final xL1gluGCXAA.$TypeFieldBinding binding) {
    return registry.reference(newBooleanModel(registry, referrer, xsb, binding), referrer);
  }

  private BooleanModel(final Registry registry, final Declarer declarer, final Schema.Boolean xsb, final xL1gluGCXAA.$TypeFieldBinding binding) {
    super(registry, declarer, Id.named(xsb.getName$()), xsb.getDoc$(), xsb.getName$().text(), getBinding(registry, binding));
    validateTypeBinding();
  }

  private static BooleanModel newBooleanModel(final Registry registry, final Declarer declarer, final $Boolean xsb, final xL1gluGCXAA.$TypeFieldBinding binding) {
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
    if (!isAssignable(getMethod, true, hasDecode ? null : defaultClass(), false, property.nullable(), property.use()) && !isAssignable(getMethod, true, CharSequence.class, false, property.nullable(), property.use()) || getMethod.getReturnType().isPrimitive() && (property.use() == Use.OPTIONAL || property.nullable()))
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
}