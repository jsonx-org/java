/* Copyright (c) 2017 lib4j
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

package org.libx4j.jsonx.generator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import org.lib4j.lang.IllegalAnnotationException;
import org.lib4j.lang.Strings;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Array;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$String;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.Jsonx;
import org.libx4j.jsonx.runtime.StringElement;
import org.libx4j.jsonx.runtime.StringProperty;

class StringModel extends SimpleModel {
  public static StringModel declare(final Registry registry, final Jsonx.String binding) {
    return registry.declare(binding).value(new StringModel(binding), null);
  }

  public static Element referenceOrDeclare(final Registry registry, final ComplexModel referrer, final StringProperty property, final Field field) {
    final StringModel model = new StringModel(property, field);
    final Id id = model.id();

    final StringModel registered = (StringModel)registry.getElement(id);
    return new Template(getName(property.name(), field), property.nullable(), property.required(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  public static Element referenceOrDeclare(final Registry registry, final ComplexModel referrer, final StringElement element) {
    final StringModel model = new StringModel(element);
    final Id id = model.id();

    final StringModel registered = (StringModel)registry.getElement(id);
    return new Template(element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  public static StringModel reference(final Registry registry, final ComplexModel referrer, final $Array.String binding) {
    return registry.reference(new StringModel(binding), referrer);
  }

  public static StringModel reference(final Registry registry, final ComplexModel referrer, final $String binding) {
    return registry.reference(new StringModel(binding), referrer);
  }

  private static String parsePattern(final String pattern) {
    return pattern.length() == 0 ? null : pattern;
  }

  private final Id id;
  private final String pattern;
  private final boolean urlEncode;
  private final boolean urlDecode;

  private StringModel(final Jsonx.String binding) {
    super(binding.getNullable$().text(), null, null, null);
    this.pattern = binding.getPattern$() == null ? null : binding.getPattern$().text();
    this.urlEncode = binding.getUrlEncode$().text();
    this.urlDecode = binding.getUrlDecode$().text();
    this.id = new Id(binding.getTemplate$().text());
  }

  private StringModel(final $String binding) {
    super(binding.getName$().text(), binding.getNullable$().text(), binding.getRequired$().text());
    this.pattern = binding.getPattern$() == null ? null : binding.getPattern$().text();
    this.urlEncode = binding.getUrlEncode$().text();
    this.urlDecode = binding.getUrlDecode$().text();
    this.id = new Id(this);
  }

  private StringModel(final $Array.String binding) {
    super(binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.pattern = binding.getPattern$() == null ? null : binding.getPattern$().text();
    this.urlEncode = binding.getUrlEncode$().text();
    this.urlDecode = binding.getUrlDecode$().text();
    this.id = new Id(this);
  }

  private StringModel(final StringProperty property, final Field field) {
    super(property.nullable(), null, null, null);
    if (field.getType() != String.class)
      throw new IllegalAnnotationException(property, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + StringProperty.class.getSimpleName() + " can only be applied to fields of String type.");

    this.pattern = parsePattern(property.pattern());
    this.urlEncode = property.urlEncode();
    this.urlDecode = property.urlDecode();
    this.id = new Id(this);
  }

  private StringModel(final StringElement element) {
    super(element.nullable(), null, null, null);
    this.pattern = parsePattern(element.pattern());
    this.urlEncode = element.urlEncode();
    this.urlDecode = element.urlDecode();
    this.id = new Id(this);
  }

  @Override
  public final Id id() {
    return id;
  }

  public final String pattern() {
    return this.pattern;
  }

  public final boolean urlEncode() {
    return this.urlEncode;
  }

  public final boolean urlDecode() {
    return this.urlDecode;
  }

  @Override
  protected final Type type() {
    return Type.get(String.class);
  }

  @Override
  protected final Class<? extends Annotation> propertyAnnotation() {
    return StringProperty.class;
  }

  @Override
  protected final Class<? extends Annotation> elementAnnotation() {
    return StringElement.class;
  }

  @Override
  protected final String toJSON(final String packageName) {
    final StringBuilder builder = new StringBuilder(super.toJSON(packageName));
    if (builder.length() > 0)
      builder.insert(0, ",\n");

    if (pattern != null)
      builder.append(",\n  pattern: \"").append(pattern).append('"');

    builder.append(",\n  urlEncode: ").append(urlEncode);
    builder.append(",\n  urlDecode: ").append(urlDecode);
    return "{\n" + (builder.length() > 0 ? builder.substring(2) : builder.toString()) + "\n}";
  }

  @Override
  protected final String toJSONX(final Registry registry, final Member owner, final String packageName) {
    final StringBuilder builder;
    if (owner instanceof ObjectModel) {
      builder = new StringBuilder("<property xsi:type=\"");
      if (registry.hasRegistry(id()))
        builder.append("template\" reference=\"").append(id()).append("\"");
      else
        builder.append("string\"");
    }
    else {
      builder = new StringBuilder("<string");
      if (pattern != null)
        builder.append(" pattern=\"").append(pattern).append('"');

      if (urlEncode)
        builder.append(" urlEncode=\"").append(urlEncode).append('"');

      if (urlDecode)
        builder.append(" urlDecode=\"").append(urlDecode).append('"');
    }

    return builder.append(super.toJSONX(registry, owner, packageName)).append("/>").toString();
  }

  @Override
  protected final void toAnnotation(final Attributes attributes, final String packageName) {
    super.toAnnotation(attributes, packageName);
    if (pattern != null)
      attributes.put("pattern", "\"" + Strings.escapeForJava(pattern) + "\"");

    if (urlEncode)
      attributes.put("urlEncode", urlEncode);

    if (urlDecode)
      attributes.put("urlDecode", urlDecode);
  }
}