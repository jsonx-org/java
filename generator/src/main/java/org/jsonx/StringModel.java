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
import java.util.Map;

import org.jsonx.www.schema_0_3.xL0gluGCXAA;
import org.libj.lang.IllegalAnnotationException;
import org.libj.util.Strings;

final class StringModel extends Model {
  private static xL0gluGCXAA.Schema.String type(final String name) {
    final xL0gluGCXAA.Schema.String xsb = new xL0gluGCXAA.Schema.String();
    if (name != null)
      xsb.setName$(new xL0gluGCXAA.Schema.String.Name$(name));

    return xsb;
  }

  private static xL0gluGCXAA.$String property(final schema.StringProperty jsd, final String name) {
    final xL0gluGCXAA.$String xsb = new xL0gluGCXAA.$String() {
      private static final long serialVersionUID = -8328022363685261988L;

      @Override
      protected xL0gluGCXAA.$Member inherits() {
        return new xL0gluGCXAA.$ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setName$(new xL0gluGCXAA.$String.Name$(name));

    if (jsd.getNullable() != null)
      xsb.setNullable$(new xL0gluGCXAA.$String.Nullable$(jsd.getNullable()));

    if (jsd.getUse() != null)
      xsb.setUse$(new xL0gluGCXAA.$String.Use$(xL0gluGCXAA.$String.Use$.Enum.valueOf(jsd.getUse())));

    return xsb;
  }

  private static xL0gluGCXAA.$ArrayMember.String element(final schema.StringElement jsd) {
    final xL0gluGCXAA.$ArrayMember.String xsb = new xL0gluGCXAA.$ArrayMember.String();

    if (jsd.getNullable() != null)
      xsb.setNullable$(new xL0gluGCXAA.$ArrayMember.String.Nullable$(jsd.getNullable()));

    if (jsd.getMinOccurs() != null)
      xsb.setMinOccurs$(new xL0gluGCXAA.$ArrayMember.String.MinOccurs$(Integer.parseInt(jsd.getMinOccurs())));

    if (jsd.getMaxOccurs() != null)
      xsb.setMaxOccurs$(new xL0gluGCXAA.$ArrayMember.String.MaxOccurs$(jsd.getMaxOccurs()));

    return xsb;
  }

  static xL0gluGCXAA.$StringMember jsdToXsb(final schema.String jsd, final String name) {
    final xL0gluGCXAA.$StringMember xsb;
    if (jsd instanceof schema.StringProperty)
      xsb  = property((schema.StringProperty)jsd, name);
    else if (jsd instanceof schema.StringElement)
      xsb  = element((schema.StringElement)jsd);
    else if (name != null)
      xsb  = type(name);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsd.getClass().getName());

    if (jsd.getDoc() != null && jsd.getDoc().length() > 0)
      xsb.setDoc$(new xL0gluGCXAA.$Documented.Doc$(jsd.getDoc()));

    if (jsd.getPattern() != null)
      xsb.setPattern$(new xL0gluGCXAA.$StringMember.Pattern$(jsd.getPattern()));

    return xsb;
  }

  static StringModel declare(final Registry registry, final Declarer declarer, final xL0gluGCXAA.Schema.String binding) {
    return registry.declare(binding).value(new StringModel(registry, declarer, binding), null);
  }

  static StringModel referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final xL0gluGCXAA.Schema.String binding) {
    final StringModel model = new StringModel(registry, referrer, binding);
    final Id id = model.id;

    final StringModel registered = (StringModel)registry.getModel(id);
    return registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer);
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final StringProperty property, final Field field) {
    final StringModel model = new StringModel(registry, referrer, property, field);
    final Id id = model.id;

    final StringModel registered = (StringModel)registry.getModel(id);
    return new Reference(registry, referrer, JsdUtil.getName(property.name(), field), property.nullable(), property.use(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final StringElement element) {
    final StringModel model = new StringModel(registry, referrer, element);
    final Id id = model.id;

    final StringModel registered = (StringModel)registry.getModel(id);
    return new Reference(registry, referrer, element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static StringModel reference(final Registry registry, final Referrer<?> referrer, final xL0gluGCXAA.$Array.String binding) {
    return registry.reference(new StringModel(registry, referrer, binding), referrer);
  }

  static StringModel reference(final Registry registry, final Referrer<?> referrer, final xL0gluGCXAA.$String binding) {
    return registry.reference(new StringModel(registry, referrer, binding), referrer);
  }

  private static String parsePattern(final String pattern) {
    return pattern == null || pattern.length() == 0 ? null : pattern;
  }

  private static String parsePattern(final xL0gluGCXAA.$StringMember.Pattern$ pattern) {
    return pattern == null ? null : parsePattern(pattern.text());
  }

  final String pattern;

  private StringModel(final Registry registry, final Declarer declarer, final xL0gluGCXAA.Schema.String binding) {
    super(registry, declarer, Id.named(binding.getName$()), binding.getDoc$());
    this.pattern = parsePattern(binding.getPattern$());
  }

  private StringModel(final Registry registry, final Declarer declarer, final xL0gluGCXAA.$String binding) {
    super(registry, declarer, Id.hashed("s", parsePattern(binding.getPattern$())), binding.getDoc$(), binding.getName$(), binding.getNullable$(), binding.getUse$());
    this.pattern = parsePattern(binding.getPattern$());
  }

  private StringModel(final Registry registry, final Declarer declarer, final xL0gluGCXAA.$Array.String binding) {
    super(registry, declarer, Id.hashed("s", parsePattern(binding.getPattern$())), binding.getDoc$(), binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.pattern = parsePattern(binding.getPattern$());
  }

  private StringModel(final Registry registry, final Declarer declarer, final StringProperty property, final Field field) {
    super(registry, declarer, Id.hashed("s", parsePattern(property.pattern())), property.nullable(), property.use());
    if (!isAssignable(field, String.class, false, property.nullable(), property.use()))
      throw new IllegalAnnotationException(property, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + StringProperty.class.getSimpleName() + " can only be applied to fields of String type with use=\"required\" or nullable=false, of Optional<String> type with use=\"optional\" and nullable=true");

    this.pattern = parsePattern(property.pattern());
  }

  private StringModel(final Registry registry, final Declarer declarer, final StringElement element) {
    super(registry, declarer, Id.hashed("s", parsePattern(element.pattern())), element.nullable(), null);
    this.pattern = parsePattern(element.pattern());
  }

  @Override
  Registry.Type type() {
    return registry.getType(String.class);
  }

  @Override
  String elementName() {
    return "string";
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
  Map<String,Object> toAttributes(final Element owner, final String packageName) {
    final Map<String,Object> attributes = super.toAttributes(owner, packageName);
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