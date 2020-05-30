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
import java.util.Map;

import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Array;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$ArrayMember;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Binding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Documented;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$FieldIdentifier;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Member;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$ObjectMember;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$String;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$StringMember;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$TypeBinding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$TypeFieldBinding;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.Schema;
import org.libj.lang.IllegalAnnotationException;
import org.libj.lang.Strings;
import org.w3.www._2001.XMLSchema.yAA;

final class StringModel extends Model {
  private static $TypeBinding typeBinding(final schema.TypeBinding jsd) {
    final $TypeBinding xsb = new $ArrayMember.String.Binding();
    xsb.setLang$(new $Binding.Lang$(jsd.getLang()));
    if (jsd.getType() != null)
      xsb.setType$(new $TypeBinding.Type$(jsd.getType()));

    if (jsd.getDecode() != null)
      xsb.setDecode$(new $TypeBinding.Decode$(jsd.getDecode()));

    if (jsd.getEncode() != null)
      xsb.setEncode$(new $TypeBinding.Encode$(jsd.getEncode()));

    return xsb;
  }

  private static Schema.String type(final schema.String jsd, final String name) {
    final Schema.String xsb = new Schema.String();
    if (name != null)
      xsb.setName$(new Schema.String.Name$(name));

    if (jsd.getBindings() != null)
      for (final schema.TypeBinding binding : jsd.getBindings())
        xsb.addBinding(typeBinding(binding));

    return xsb;
  }

  private static $String property(final schema.StringProperty jsd, final String name) {
    final $String xsb = new $String() {
      private static final long serialVersionUID = -8328022363685261988L;

      @Override
      protected $Member inherits() {
        return new $ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setName$(new $String.Name$(name));

    if (jsd.getNullable() != null)
      xsb.setNullable$(new $String.Nullable$(jsd.getNullable()));

    if (jsd.getUse() != null)
      xsb.setUse$(new $String.Use$($String.Use$.Enum.valueOf(jsd.getUse())));

    if (jsd.getBindings() != null) {
      for (final schema.TypeFieldBinding binding : jsd.getBindings()) {
        final $TypeFieldBinding bind = new $String.Binding();
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

  private static $ArrayMember.String element(final schema.StringElement jsd) {
    final $ArrayMember.String xsb = new $ArrayMember.String();

    if (jsd.getNullable() != null)
      xsb.setNullable$(new $ArrayMember.String.Nullable$(jsd.getNullable()));

    if (jsd.getMinOccurs() != null)
      xsb.setMinOccurs$(new $ArrayMember.String.MinOccurs$(new BigInteger(jsd.getMinOccurs())));

    if (jsd.getMaxOccurs() != null)
      xsb.setMaxOccurs$(new $ArrayMember.String.MaxOccurs$(jsd.getMaxOccurs()));

    if (jsd.getBindings() != null)
      for (final schema.TypeBinding binding : jsd.getBindings())
        xsb.addBinding(typeBinding(binding));

    return xsb;
  }

  static $StringMember jsdToXsb(final schema.String jsd, final String name) {
    final $StringMember xsb;
    if (jsd instanceof schema.StringProperty)
      xsb  = property((schema.StringProperty)jsd, name);
    else if (jsd instanceof schema.StringElement)
      xsb  = element((schema.StringElement)jsd);
    else if (name != null)
      xsb  = type(jsd, name);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsd.getClass().getName());

    if (jsd.getDoc() != null && jsd.getDoc().length() > 0)
      xsb.setDoc$(new $Documented.Doc$(jsd.getDoc()));

    if (jsd.getPattern() != null)
      xsb.setPattern$(new $StringMember.Pattern$(jsd.getPattern()));

    return xsb;
  }

  static StringModel declare(final Registry registry, final Declarer declarer, final Schema.String xsb) {
    return registry.declare(xsb).value(new StringModel(registry, declarer, xsb), null);
  }

  static StringModel referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final Schema.String xsb) {
    final StringModel model = new StringModel(registry, referrer, xsb);
    final Id id = model.id();

    final StringModel registered = (StringModel)registry.getModel(id);
    return registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer);
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final StringProperty property, final Method getMethod, final String fieldName) {
    final StringModel model = newStringModel(registry, referrer, property, getMethod, fieldName);
    final Id id = model.id();

    final StringModel registered = (StringModel)registry.getModel(id);
    return new Reference(registry, referrer, property.name(), property.nullable(), property.use(), fieldName, model.typeBinding, registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final StringElement element) {
    final StringModel model = new StringModel(registry, referrer, element.nullable(), element.pattern(), Binding.Type.from(registry, element.type(), element.decode(), element.encode(), String.class));
    final Id id = model.id();

    final StringModel registered = (StringModel)registry.getModel(id);
    return new Reference(registry, referrer, element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static StringModel referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final StringType type) {
    // Note: Explicitly setting nullable=false, because nullable for *Type annotations is set at the AnyElement/AnyProperty level
    final StringModel model = new StringModel(registry, referrer, false, type.pattern(), Binding.Type.from(registry, type.type(), type.decode(), type.encode(), String.class));
    final Id id = model.id();

    final StringModel registered = (StringModel)registry.getModel(id);
    return registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer);
  }

  static StringModel reference(final Registry registry, final Referrer<?> referrer, final $Array.String xsb) {
    return registry.reference(new StringModel(registry, referrer, xsb, getBinding(registry, xsb.getBinding())), referrer);
  }

  static StringModel reference(final Registry registry, final Referrer<?> referrer, final $String xsb) {
    return registry.reference(newStringModel(registry, referrer, xsb), referrer);
  }

  private static String parseString(final String str) {
    return str == null || str.length() == 0 ? null : str;
  }

  private static String parseString(final yAA.$String string) {
    return string == null ? null : parseString(string.text());
  }

  final String pattern;

  private StringModel(final Registry registry, final Declarer declarer, final Schema.String xsb) {
    super(registry, declarer, Id.named(xsb.getName$()), xsb.getDoc$(), getBinding(registry, xsb.getBinding()));
    this.pattern = parseString(xsb.getPattern$());

    validateTypeBinding();
  }

  private static StringModel newStringModel(final Registry registry, final Declarer declarer, final $String xsb) {
    final $TypeFieldBinding binding = getBinding(xsb.getBinding());
    if (binding == null)
      return new StringModel(registry, declarer, xsb, null, null);

    return new StringModel(registry, declarer, xsb, binding.getField$(), Binding.Type.from(registry, binding.getType$(), binding.getDecode$(), binding.getEncode$()));
  }

  private StringModel(final Registry registry, final Declarer declarer, final $String xsb, final $FieldIdentifier fieldName, final Binding.Type typeBinding) {
    super(registry, declarer, Id.hashed("s", typeBinding, parseString(xsb.getPattern$())), xsb.getDoc$(), xsb.getName$(), xsb.getNullable$(), xsb.getUse$(), fieldName, typeBinding);
    this.pattern = parseString(xsb.getPattern$());
    validateTypeBinding();
  }

  private StringModel(final Registry registry, final Declarer declarer, final $Array.String xsb, final Binding.Type typeBinding) {
    super(registry, declarer, Id.hashed("s", typeBinding, parseString(xsb.getPattern$())), xsb.getDoc$(), xsb.getNullable$(), xsb.getMinOccurs$(), xsb.getMaxOccurs$(), typeBinding);
    this.pattern = parseString(xsb.getPattern$());
    validateTypeBinding();
  }

  private static StringModel newStringModel(final Registry registry, final Declarer declarer, final StringProperty property, final Method getMethod, final String fieldName) {
    final Binding.Type typeBinding = Binding.Type.from(registry, getMethod, property.nullable(), property.use(), property.decode(), property.encode(), String.class);
    return new StringModel(registry, declarer, property, getMethod, fieldName, typeBinding);
  }

  private StringModel(final Registry registry, final Declarer declarer, final StringProperty property, final Method getMethod, final String fieldName, final Binding.Type typeBinding) {
    super(registry, declarer, Id.hashed("s", typeBinding, parseString(property.pattern())), property.nullable(), property.use(), fieldName, typeBinding);
    // If there is a "decode" spec, then skip the check to verify field<-->{CharSequence,char[]} compatibility
    final boolean hasDecode = typeBinding != null && typeBinding.decode != null;
    // TODO: Can this be parameterized and moved to Model#validateTypeBinding?
    if (!isAssignable(getMethod, true, hasDecode ? null : CharSequence.class, false, property.nullable(), property.use()) || getMethod.getReturnType().isPrimitive() && (property.use() == Use.OPTIONAL || property.nullable()))
      throw new IllegalAnnotationException(property, getMethod.getDeclaringClass().getName() + "." + getMethod.getName() + "(): @" + StringProperty.class.getSimpleName() + " can only be applied to fields of Object types with use=\"required\" or nullable=false, of Optional<Object> type with use=\"optional\" and nullable=true");

    this.pattern = parseString(property.pattern());
    validateTypeBinding();
  }

  private StringModel(final Registry registry, final Declarer declarer, final Boolean nullable, final String pattern, final Binding.Type typeBinding) {
    super(registry, declarer, Id.hashed("s", typeBinding, parseString(pattern)), nullable, null, null, typeBinding);
    this.pattern = parseString(pattern);
    validateTypeBinding();
  }

  @Override
  Registry.Type typeDefault() {
    return registry.getType(String.class);
  }

  @Override
  String isValid(final Binding.Type typeBinding) {
    return null;
  }

  @Override
  String elementName() {
    return "string";
  }

  @Override
  Class<?> defaultClass() {
    return String.class;
  }

  @Override
  Class<? extends Annotation> propertyAnnotation() {
    return StringProperty.class;
  }

  @Override
  Class<? extends Annotation> elementAnnotation() {
    return StringElement.class;
  }

  @Override
  Class<? extends Annotation> typeAnnotation() {
    return StringType.class;
  }

  @Override
  Map<String,Object> toXmlAttributes(final Element owner, final String packageName) {
    final Map<String,Object> attributes = super.toXmlAttributes(owner, packageName);
    if (pattern != null)
      attributes.put("pattern", pattern);

    return attributes;
  }

  @Override
  void toAnnotationAttributes(final AttributeMap attributes, final Member owner) {
    super.toAnnotationAttributes(attributes, owner);
    if (pattern != null)
      attributes.put("pattern", "\"" + Strings.escapeForJava(pattern) + "\"");
  }
}