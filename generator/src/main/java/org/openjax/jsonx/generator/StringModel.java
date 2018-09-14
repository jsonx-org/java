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

package org.openjax.jsonx.generator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

import org.fastjax.lang.IllegalAnnotationException;
import org.fastjax.util.Strings;
import org.openjax.jsonx.jsonx_0_9_8.xL3gluGCXYYJc.$Array;
import org.openjax.jsonx.jsonx_0_9_8.xL3gluGCXYYJc.$String;
import org.openjax.jsonx.jsonx_0_9_8.xL3gluGCXYYJc.Jsonx;
import org.openjax.jsonx.runtime.JsonxUtil;
import org.openjax.jsonx.runtime.StringElement;
import org.openjax.jsonx.runtime.StringProperty;

final class StringModel extends Model {
  public static StringModel declare(final Registry registry, final Jsonx.String binding) {
    return registry.declare(binding).value(new StringModel(registry, binding), null);
  }

  public static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final StringProperty property, final Field field) {
    final StringModel model = new StringModel(registry, property, field);
    final Id id = model.id();

    final StringModel registered = (StringModel)registry.getModel(id);
    return new Reference(registry, JsonxUtil.getName(property.name(), field), property.use(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  public static Member referenceOrDeclare(final Registry registry, final Referrer<?> referrer, final StringElement element) {
    final StringModel model = new StringModel(registry, element);
    final Id id = model.id();

    final StringModel registered = (StringModel)registry.getModel(id);
    return new Reference(registry, element.nullable(), element.minOccurs(), element.maxOccurs(), registered == null ? registry.declare(id).value(model, referrer) : registry.reference(registered, referrer));
  }

  public static StringModel reference(final Registry registry, final Referrer<?> referrer, final $Array.String binding) {
    return registry.reference(new StringModel(registry, binding), referrer);
  }

  public static StringModel reference(final Registry registry, final Referrer<?> referrer, final $String binding) {
    return registry.reference(new StringModel(registry, binding), referrer);
  }

  private static String parsePattern(final String pattern) {
    return pattern.length() == 0 ? null : pattern;
  }

  private final Id id;
  private final String pattern;
  private final boolean urlEncode;
  private final boolean urlDecode;

  private StringModel(final Registry registry, final Jsonx.String binding) {
    super(registry);
    this.pattern = binding.getPattern$() == null ? null : binding.getPattern$().text();
    this.urlEncode = binding.getUrlEncode$().text();
    this.urlDecode = binding.getUrlDecode$().text();
    this.id = new Id(binding.getTemplate$());
  }

  private StringModel(final Registry registry, final $String binding) {
    super(registry, binding.getName$(), binding.getUse$());
    this.pattern = binding.getPattern$() == null ? null : binding.getPattern$().text();
    this.urlEncode = binding.getUrlEncode$().text();
    this.urlDecode = binding.getUrlDecode$().text();
    this.id = new Id(this);
  }

  private StringModel(final Registry registry, final $Array.String binding) {
    super(registry, binding.getNullable$(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.pattern = binding.getPattern$() == null ? null : binding.getPattern$().text();
    this.urlEncode = binding.getUrlEncode$().text();
    this.urlDecode = binding.getUrlDecode$().text();
    this.id = new Id(this);
  }

  private StringModel(final Registry registry, final StringProperty property, final Field field) {
    super(registry, null, property.use());
    if (field.getType() != String.class)
      throw new IllegalAnnotationException(property, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + StringProperty.class.getSimpleName() + " can only be applied to fields of String type.");

    this.pattern = parsePattern(property.pattern());
    this.urlEncode = property.urlEncode();
    this.urlDecode = property.urlDecode();
    this.id = new Id(this);
  }

  private StringModel(final Registry registry, final StringElement element) {
    super(registry, element.nullable(), null);
    this.pattern = parsePattern(element.pattern());
    this.urlEncode = element.urlEncode();
    this.urlDecode = element.urlDecode();
    this.id = new Id(this);
  }

  @Override
  protected Id id() {
    return id;
  }

  public String pattern() {
    return this.pattern;
  }

  public boolean urlEncode() {
    return this.urlEncode;
  }

  public boolean urlDecode() {
    return this.urlDecode;
  }

  @Override
  protected Registry.Type type() {
    return registry.getType(String.class);
  }

  @Override
  protected String elementName() {
    return "string";
  }

  @Override
  protected Class<? extends Annotation> propertyAnnotation() {
    return StringProperty.class;
  }

  @Override
  protected Class<? extends Annotation> elementAnnotation() {
    return StringElement.class;
  }

  @Override
  protected Map<String,String> toXmlAttributes(final Element owner, final String packageName) {
    final Map<String,String> attributes = super.toXmlAttributes(owner, packageName);
    if (pattern != null)
      attributes.put("pattern", pattern);

    if (urlEncode)
      attributes.put("urlEncode", String.valueOf(urlEncode));

    if (urlDecode)
      attributes.put("urlDecode", String.valueOf(urlDecode));

    return attributes;
  }

  @Override
  protected void toAnnotationAttributes(final AttributeMap attributes, final Member owner) {
    super.toAnnotationAttributes(attributes, owner);
    if (pattern != null)
      attributes.put("pattern", "\"" + Strings.escapeForJava(pattern) + "\"");

    if (urlEncode)
      attributes.put("urlEncode", urlEncode);

    if (urlDecode)
      attributes.put("urlDecode", urlDecode);
  }
}