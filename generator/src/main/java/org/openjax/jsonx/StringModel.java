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

package org.openjax.jsonx;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc;
import org.openjax.standard.lang.IllegalAnnotationException;
import org.openjax.standard.util.Strings;

final class StringModel extends Model {
  private static xL4gluGCXYYJc.Schema.String type(final String name) {
    final xL4gluGCXYYJc.Schema.String xsb = new xL4gluGCXYYJc.Schema.String();
    if (name != null)
      xsb.setName$(new xL4gluGCXYYJc.Schema.String.Name$(name));

    return xsb;
  }

  private static xL4gluGCXYYJc.$String property(final schema.StringProperty jsonx, final String name) {
    final xL4gluGCXYYJc.$String xsb = new xL4gluGCXYYJc.$String() {
      private static final long serialVersionUID = -8328022363685261988L;

      @Override
      protected xL4gluGCXYYJc.$Member inherits() {
        return new xL4gluGCXYYJc.$ObjectMember.Property();
      }
    };

    if (name != null)
      xsb.setName$(new xL4gluGCXYYJc.$String.Name$(name));

    if (jsonx.getNullable() != null)
      xsb.setNullable$(new xL4gluGCXYYJc.$String.Nullable$(jsonx.getNullable()));

    if (jsonx.getUse() != null)
      xsb.setUse$(new xL4gluGCXYYJc.$String.Use$(xL4gluGCXYYJc.$String.Use$.Enum.valueOf(jsonx.getUse())));

    return xsb;
  }

  private static xL4gluGCXYYJc.$ArrayMember.String element(final schema.StringElement jsonx) {
    final xL4gluGCXYYJc.$ArrayMember.String xsb = new xL4gluGCXYYJc.$ArrayMember.String();

    if (jsonx.getNullable() != null)
      xsb.setNullable$(new xL4gluGCXYYJc.$ArrayMember.String.Nullable$(jsonx.getNullable()));

    if (jsonx.getMinOccurs() != null)
      xsb.setMinOccurs$(new xL4gluGCXYYJc.$ArrayMember.String.MinOccurs$(Integer.parseInt(jsonx.getMinOccurs())));

    if (jsonx.getMaxOccurs() != null)
      xsb.setMaxOccurs$(new xL4gluGCXYYJc.$ArrayMember.String.MaxOccurs$(jsonx.getMaxOccurs()));

    return xsb;
  }

  static xL4gluGCXYYJc.$StringMember jsonxToXsb(final schema.String jsdx, final String name) {
    final xL4gluGCXYYJc.$StringMember xsb;
    if (jsdx instanceof schema.StringProperty)
      xsb  = property((schema.StringProperty)jsdx, name);
    else if (jsdx instanceof schema.StringElement)
      xsb  = element((schema.StringElement)jsdx);
    else if (name != null)
      xsb  = type(name);
    else
      throw new UnsupportedOperationException("Unsupported type: " + jsdx.getClass().getName());

    if (jsdx.getPattern() != null)
      xsb.setPattern$(new xL4gluGCXYYJc.$StringMember.Pattern$(jsdx.getPattern()));

    return xsb;
  }

  static StringModel declare(final Registry registry, final xL4gluGCXYYJc.Schema.String binding) {
    return registry.declare(binding).value(new StringModel(registry, binding), null);
  }

  static StringModel referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final xL4gluGCXYYJc.Schema.String binding) {
    final StringModel model = new StringModel(registry, binding);
    final Id id = model.id;

    final StringModel registered = (StringModel)registry.getModel(id);
    return registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer);
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final StringProperty property, final Field field) {
    final StringModel model = new StringModel(registry, property, field);
    final Id id = model.id;

    final StringModel registered = (StringModel)registry.getModel(id);
    return new Reference(registry, JsdUtil.getName(property.name(), field), property.nullable(), property.use(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static Reference referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final StringElement element) {
    final StringModel model = new StringModel(registry, element);
    final Id id = model.id;

    final StringModel registered = (StringModel)registry.getModel(id);
    return new Reference(registry, element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  static StringModel reference(final Registry registry, final Referrer<?> referrer, final xL4gluGCXYYJc.$Array.String binding) {
    return registry.reference(new StringModel(registry, binding), referrer);
  }

  static StringModel reference(final Registry registry, final Referrer<?> referrer, final xL4gluGCXYYJc.$String binding) {
    return registry.reference(new StringModel(registry, binding), referrer);
  }

  private static String parsePattern(final String pattern) {
    return pattern == null || pattern.length() == 0 ? null : pattern;
  }

  private static String parsePattern(final xL4gluGCXYYJc.$StringMember.Pattern$ pattern) {
    return pattern == null ? null : parsePattern(pattern.text());
  }

  final String pattern;

  private StringModel(final Registry registry, final xL4gluGCXYYJc.Schema.String binding) {
    super(registry, Id.named(binding.getName$()));
    this.pattern = parsePattern(binding.getPattern$());
  }

  private StringModel(final Registry registry, final xL4gluGCXYYJc.$String binding) {
    super(registry, Id.hashed("s", parsePattern(binding.getPattern$())), binding.getName$(), binding.getNullable$(), binding.getUse$());
    this.pattern = parsePattern(binding.getPattern$());
  }

  private StringModel(final Registry registry, final xL4gluGCXYYJc.$Array.String binding) {
    super(registry, Id.hashed("s", parsePattern(binding.getPattern$())), binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.pattern = parsePattern(binding.getPattern$());
  }

  private StringModel(final Registry registry, final StringProperty property, final Field field) {
    super(registry, Id.hashed("s", parsePattern(property.pattern())), property.nullable(), property.use());
    if (!isAssignable(field, String.class, false, property.nullable(), property.use()))
      throw new IllegalAnnotationException(property, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + StringProperty.class.getSimpleName() + " can only be applied to required or not-nullable fields of String type, or optional and nullable fields of Optional<String> type");

    this.pattern = parsePattern(property.pattern());
  }

  private StringModel(final Registry registry, final StringElement element) {
    super(registry, Id.hashed("s", parsePattern(element.pattern())), element.nullable(), null);
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