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
// * program. If not, see <http://opensource.org/licenses/MIT/>.
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
  public static StringModel declare(final Schema schema, final Registry registry, final Jsonx.String binding) {
    return registry.declare(binding).value(new StringModel(schema, binding), null);
  }

  public static StringModel referenceOrDeclare(final Member owner, final StringProperty stringProperty, final Field field) {
    return new StringModel(owner, stringProperty, field);
  }

  public static StringModel referenceOrDeclare(final Member owner, final StringElement stringElement) {
    return new StringModel(owner, stringElement);
  }

  public static StringModel reference(final Member owner, final $Array.String binding) {
    return new StringModel(owner, binding);
  }

  public static StringModel reference(final Member owner, final $String binding) {
    return new StringModel(owner, binding);
  }

  private static String parsePattern(final String pattern) {
    return pattern.length() == 0 ? null : pattern;
  }

  private final String pattern;
  private final boolean urlEncode;
  private final boolean urlDecode;

  private StringModel(final Schema schema, final Jsonx.String binding) {
    super(schema, binding.getTemplate$().text(), binding.getNullable$().text(), null, null, null, binding.getDoc$() == null ? null : binding.getDoc$().text());
    this.pattern = binding.getPattern$().text();
    this.urlEncode = binding.getUrlEncode$().text();
    this.urlDecode = binding.getUrlDecode$().text();
  }

  private StringModel(final Member owner, final $String binding) {
    super(owner, binding, binding.getName$().text(), binding.getNullable$().text(), binding.getRequired$().text());
    this.pattern = binding.getPattern$() == null ? null : binding.getPattern$().text();
    this.urlEncode = binding.getUrlEncode$().text();
    this.urlDecode = binding.getUrlDecode$().text();
  }

  private StringModel(final Member owner, final $Array.String binding) {
    super(owner, binding, binding.getNullable$().text(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.pattern = binding.getPattern$() == null ? null : binding.getPattern$().text();
    this.urlEncode = binding.getUrlEncode$().text();
    this.urlDecode = binding.getUrlDecode$().text();
  }

  private StringModel(final Member owner, final StringProperty stringProperty, final Field field) {
    // FIXME: Can we get doc comments from code?
    super(owner, getName(stringProperty.name(), field), stringProperty.required(), stringProperty.nullable(), null, null, null);
    if (field.getType() != String.class)
      throw new IllegalAnnotationException(stringProperty, field.getDeclaringClass().getName() + "." + field.getName() + ": @" + StringProperty.class.getSimpleName() + " can only be applied to fields of String type.");

    this.pattern = parsePattern(stringProperty.pattern());
    this.urlEncode = stringProperty.urlEncode();
    this.urlDecode = stringProperty.urlDecode();
  }

  private StringModel(final Member owner, final StringElement stringElement) {
    // FIXME: Can we get doc comments from code?
    super(owner, null, null, stringElement.nullable(), stringElement.minOccurs(), stringElement.maxOccurs(), null);
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
  protected StringModel merge(final Reference reference) {
    return new StringModel(reference, this);
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
    final StringBuilder builder = new StringBuilder(kind() == Kind.PROPERTY ? "<property xsi:type=\"string\"" : "<string");
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
      builder.append((builder.length() > 0 ? ", " : "") + "pattern=\"").append(Strings.escapeForJava(pattern)).append('"');

    if (urlEncode)
      builder.append((builder.length() > 0 ? ", " : "") + "urlEncode=").append(urlEncode);

    if (urlDecode)
      builder.append((builder.length() > 0 ? ", " : "") + "urlDecode=").append(urlDecode);

    return builder.toString();
  }
}