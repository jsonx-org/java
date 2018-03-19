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
import org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.$Array;
import org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.$Object;
import org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx;
import org.libx4j.jsonx.runtime.StringElement;
import org.libx4j.jsonx.runtime.StringProperty;

class StringModel extends SimpleModel {
  private static String parsePattern(final String pattern) {
    return pattern.length() == 0 ? null : pattern;
  }

  private final String pattern;
  private final boolean urlEncode;
  private final boolean urlDecode;

  public static StringModel reference(final Registry registry, final ComplexModel referrer, final $Array.String binding) {
    return new StringModel(binding);
  }

  // Annullable, Recurrable
  public StringModel(final $Array.String binding) {
    super(binding, binding.getNullable$().text(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.pattern = binding.getPattern$() == null ? null : binding.getPattern$().text();
    this.urlEncode = binding.getUrlEncode$().text();
    this.urlDecode = binding.getUrlDecode$().text();
  }

  public static StringModel reference(final Registry registry, final ComplexModel referrer, final $Object.String binding) {
    return new StringModel(binding);
  }

  // Nameable, Annullable
  public StringModel(final $Object.String binding) {
    super(binding, binding.getName$().text(), binding.getRequired$().text(), binding.getNullable$().text());
    this.pattern = binding.getPattern$() == null ? null : binding.getPattern$().text();
    this.urlEncode = binding.getUrlEncode$().text();
    this.urlDecode = binding.getUrlDecode$().text();
  }

  public static StringModel declare(final Registry registry, final Jsonx.String binding) {
    return registry.declare(binding).value(new StringModel(binding), null);
  }

  // Nameable
  public StringModel(final Jsonx.String binding) {
    super(binding.getName$().text(), null, null, null, null, binding.getDoc$() == null ? null : binding.getDoc$().text());
    this.pattern = binding.getPattern$().text();
    this.urlEncode = binding.getUrlEncode$().text();
    this.urlDecode = binding.getUrlDecode$().text();
  }

  public static StringModel referenceOrDeclare(final Registry registry, final ComplexModel referrer, final StringProperty stringProperty, final Field field) {
    return new StringModel(stringProperty, field);
  }

  public StringModel(final StringProperty stringProperty, final Field field) {
    // FIXME: Can we get doc comments from code?
    super(getName(stringProperty.name(), field), stringProperty.required(), stringProperty.nullable(), null, null, null);
    if (field.getType() != String.class)
      throw new IllegalAnnotationException(stringProperty, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + StringProperty.class.getSimpleName() + " can only be applied to fields of String type.");

    this.pattern = parsePattern(stringProperty.pattern());
    this.urlEncode = stringProperty.urlEncode();
    this.urlDecode = stringProperty.urlDecode();
  }

  public static StringModel referenceOrDeclare(final Registry registry, final ComplexModel referrer, final StringElement stringElement) {
    return new StringModel(stringElement);
  }

  public StringModel(final StringElement stringElement) {
    // FIXME: Can we get doc comments from code?
    super(null, null, stringElement.nullable(), stringElement.minOccurs(), stringElement.maxOccurs(), null);
    this.pattern = parsePattern(stringElement.pattern());
    this.urlEncode = stringElement.urlEncode();
    this.urlDecode = stringElement.urlDecode();
  }

  private StringModel(final Element element, final StringModel copy) {
    super(element);
    this.pattern = copy.pattern;
    this.urlEncode = copy.urlEncode;
    this.urlDecode = copy.urlDecode;
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
  protected final String className() {
    return String.class.getName();
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
  protected StringModel merge(final RefElement propertyElement) {
    return new StringModel(propertyElement, this);
  }

  @Override
  protected final StringModel normalize(final Registry registry) {
    return this;
  }

  @Override
  protected final String toJSON(final String pacakgeName) {
    final StringBuilder builder = new StringBuilder(super.toJSON(pacakgeName));
    if (builder.length() > 0)
      builder.insert(0, ",\n");

    if (pattern != null)
      builder.append(",\n  pattern: \"").append(pattern).append('"');

    builder.append(",\n  urlEncode: ").append(urlEncode);
    builder.append(",\n  urlDecode: ").append(urlDecode);
    return "{\n" + (builder.length() > 0 ? builder.substring(2) : builder.toString()) + "\n}";
  }

  @Override
  protected final String toJSONX(final String pacakgeName) {
    final StringBuilder builder = new StringBuilder("<string");
    if (pattern != null)
      builder.append(" pattern=\"").append(pattern).append('"');

    if (urlEncode)
      builder.append(" urlEncode=\"").append(urlEncode).append('"');

    if (urlDecode)
      builder.append(" urlDecode=\"").append(urlDecode).append('"');

    return builder.append(super.toJSONX(pacakgeName)).append("/>").toString();
  }

  @Override
  protected final String toAnnotation(final boolean full) {
    final StringBuilder builder = full ? new StringBuilder(super.toAnnotation(full)) : new StringBuilder();
    if (pattern != null)
      builder.append(", pattern=\"").append(Strings.escapeForJava(pattern)).append('"');

    if (urlEncode)
      builder.append(", urlEncode=").append(urlEncode);

    if (urlDecode)
      builder.append(", urlDecode=").append(urlDecode);

    return builder.toString();
  }
}