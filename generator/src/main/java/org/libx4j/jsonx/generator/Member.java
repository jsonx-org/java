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
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.lib4j.lang.JavaIdentifiers;
import org.lib4j.util.Collections;
import org.lib4j.xml.datatypes_1_0_4.xL3gluGCXYYJc.$JavaIdentifier;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Array;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Boolean;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$MaxCardinality;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Number;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Object;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$String;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Reference;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.Jsonx;
import org.libx4j.jsonx.runtime.ArrayProperty;
import org.libx4j.jsonx.runtime.BooleanProperty;
import org.libx4j.jsonx.runtime.NumberProperty;
import org.libx4j.jsonx.runtime.ObjectProperty;
import org.libx4j.jsonx.runtime.StringProperty;
import org.libx4j.jsonx.runtime.Use;
import org.libx4j.jsonx.runtime.ValidationException;
import org.libx4j.xsb.runtime.Attribute;
import org.libx4j.xsb.runtime.Binding;
import org.libx4j.xsb.runtime.Bindings;
import org.w3.www._2001.XMLSchema.yAA;

abstract class Member extends Element {
  protected static Member toMember(final Registry registry, final Referrer<?> referrer, final Field field) {
    Member declaredMember = null;
    for (final Annotation annotation : field.getAnnotations()) {
      Member member = null;
      if (ArrayProperty.class.equals(annotation.annotationType()))
        member = ArrayModel.referenceOrDeclare(registry, referrer, (ArrayProperty)annotation, field);
      else if (BooleanProperty.class.equals(annotation.annotationType()))
        member = BooleanModel.referenceOrDeclare(registry, referrer, (BooleanProperty)annotation, field);
      else if (NumberProperty.class.equals(annotation.annotationType()))
        member = NumberModel.referenceOrDeclare(registry, referrer, (NumberProperty)annotation, field);
      else if (ObjectProperty.class.equals(annotation.annotationType()))
        member = ObjectModel.referenceOrDeclare(registry, referrer, (ObjectProperty)annotation, field);
      else if (StringProperty.class.equals(annotation.annotationType()))
        member = StringModel.referenceOrDeclare(registry, referrer, (StringProperty)annotation, field);

      if (declaredMember == null)
        declaredMember = member;
      else if (member != null)
        throw new ValidationException(field.getDeclaringClass().getName() + "." + field.getName() + " specifies multiple parameter annotations: [" + declaredMember.elementName() + ", " + member.elementName() + "]");
    }

    return declaredMember;
  }

  protected static final Function<Binding,String> elementXPath = new Function<>() {
    @Override
    public String apply(final Binding t) {
      final String name;
      if (t instanceof $Array)
        name = "name=\"" + (($Array)t).getName$().text();
      else if (t instanceof $Boolean)
        name = "name=\"" + (($Boolean)t).getName$().text();
      else if (t instanceof $Number)
        name = "name=\"" + (($Number)t).getName$().text();
      else if (t instanceof $Object)
        name = "name=\"" + (($Object)t).getName$().text();
      else if (t instanceof $String)
        name = "name=\"" + (($String)t).getName$().text();
      else if (t instanceof $Reference)
        name = "name=\"" + (($Reference)t).getName$().text();
      else if (t instanceof Jsonx.Array)
        name = ((Jsonx.Array)t).getClass$() != null ? "class=\"" + ((Jsonx.Array)t).getClass$().text() : "template=\"" + ((Jsonx.Array)t).getTemplate$().text();
      else if (t instanceof Jsonx.Boolean)
        name = "template=\"" + ((Jsonx.Boolean)t).getTemplate$().text();
      else if (t instanceof Jsonx.Number)
        name = "template=\"" + ((Jsonx.Number)t).getTemplate$().text();
      else if (t instanceof Jsonx.Object)
        name = "class=\"" + ((Jsonx.Object)t).getClass$().text();
      else if (t instanceof Jsonx.String)
        name = "template=\"" + ((Jsonx.String)t).getTemplate$().text();
      else
        name = null;

      if (name == null)
        return t.name().getLocalPart();

      final StringBuilder builder = new StringBuilder();
      builder.append(t.name().getLocalPart());
      builder.append("[@").append(name).append("\"]");
      return builder.toString();
    }
  };

  private static Integer parseMaxCardinality(final int minCardinality, final $MaxCardinality maxCardinality) {
    final Integer max = "unbounded".equals(maxCardinality.text()) ? null : Integer.parseInt(maxCardinality.text());
    if (max != null && minCardinality > max)
      throw new ValidationException("minOccurs=\"" + minCardinality + "\" > maxOccurs=\"" + max + "\"\n" + Bindings.getXPath(((Attribute)maxCardinality).owner(), elementXPath) + "[@minOccurs=" + minCardinality + " and @maxOccurs=" + maxCardinality.text() + "]");

    return max;
  }

  private static void checkMinMaxOccurs(final String source, final Integer minOccurs, final Integer maxOccurs) {
    if (minOccurs != null && maxOccurs != null && minOccurs > maxOccurs)
      throw new ValidationException(source + ": minOccurs=\"" + minOccurs + "\" > maxOccurs=\"" + maxOccurs + "\"");
  }

  protected final Registry registry;
  private final String name;
  private final Boolean nullable;
  private final Use use;
  private final Integer minOccurs;
  private final Integer maxOccurs;

  public Member(final Registry registry, final String name, final Boolean nullable, final Use use, final Integer minOccurs, final Integer maxOccurs) {
    this.registry = registry;
    this.name = name;
    this.nullable = nullable != null && !nullable ? null : nullable;
    this.use = use == Use.REQUIRED ? null : use;
    this.minOccurs = minOccurs == null || minOccurs == 1 ? null : minOccurs;
    this.maxOccurs = maxOccurs == null || maxOccurs == Integer.MAX_VALUE ? null : maxOccurs;
    checkMinMaxOccurs(name, minOccurs, maxOccurs);
  }

  public Member(final Registry registry, final yAA.$Boolean nullable, final yAA.$NonNegativeInteger minOccurs, final $MaxCardinality maxOccurs) {
    this(registry, null, nullable == null ? null : nullable.text(), null, minOccurs.text().intValue(), parseMaxCardinality(minOccurs.text().intValue(), maxOccurs));
  }

  public Member(final Registry registry, final $JavaIdentifier name, final yAA.$String use) {
    this(registry, name.text(), null, use == null ? null : Use.valueOf(use.text().toUpperCase()), null, null);
  }

  public final String name() {
    return this.name;
  }

  public final String instanceName() {
    return JavaIdentifiers.toInstanceCase(name);
  }

  public final Boolean nullable() {
    return nullable;
  }

  public final Use use() {
    return use;
  }

  public final Integer minOccurs() {
    return minOccurs;
  }

  public final Integer maxOccurs() {
    return maxOccurs;
  }

  @Override
  protected Map<String,String> toXmlAttributes(final Element owner, final String packageName) {
    final Map<String,String> attributes = super.toXmlAttributes(owner, packageName);
    if (name != null)
      attributes.put("name", name);
    else if (owner instanceof Schema && !(this instanceof Referrer))
      attributes.put("template", id().toString());

    if (!(owner instanceof Schema)) {
      if (nullable != null && !nullable)
        attributes.put("nullable", String.valueOf(nullable));

      if (use != null)
        attributes.put("use", use.toString().toLowerCase());
    }

    if (minOccurs != null)
      attributes.put("minOccurs", String.valueOf(minOccurs));

    if (maxOccurs != null)
      attributes.put("maxOccurs", String.valueOf(maxOccurs));

    return attributes;
  }

  /**
   * Intended to be overridden by each concrete subclass, this method populates
   * the target <code>attributes</code> parameter with annotation attributes
   * pertaining to this <code>Member</code>.
   *
   * @param attributes The target <code>attributes</code> parameter.
   * @param owner The <code>Member</code> that owns <code>this</code>.
   */
  protected void toAnnotationAttributes(final AttributeMap attributes, final Member owner) {
    if (nullable != null)
      attributes.put("nullable", nullable);

    if (use != null)
      attributes.put("use", Use.class.getName() + "." + use);

    if (minOccurs != null)
      attributes.put("minOccurs", minOccurs);

    if (maxOccurs != null)
      attributes.put("maxOccurs", maxOccurs);
  }

  protected final String toField() {
    final StringBuilder builder = new StringBuilder();
    final List<AnnotationSpec> elementAnnotations = toElementAnnotations();
    if (elementAnnotations != null && elementAnnotations.size() > 0)
      builder.append(Collections.toString(elementAnnotations, '\n')).append('\n');

    final AttributeMap attributes = new AttributeMap();
    toAnnotationAttributes(attributes, this);
    final String instanceCase = JavaIdentifiers.toInstanceCase(name);
    if (!name.equals(instanceCase) && !attributes.containsKey("name"))
      attributes.put("name", "\"" + name + "\"");

    String classCase = JavaIdentifiers.toClassCase(name);
    if ("Class".equals(classCase))
      classCase = "0lass";

    builder.append(new AnnotationSpec(propertyAnnotation(), attributes));
    builder.append("\nprivate ").append(type().toCanonicalString()).append(' ').append(instanceCase).append(';');
    builder.append("\n\npublic void set").append(classCase).append("(final ").append(type().toCanonicalString()).append(" ").append(instanceCase).append(") {");
    builder.append("\n  this.").append(instanceCase).append(" = ").append(instanceCase).append(';');
    builder.append("\n}");
    builder.append("\n\npublic ").append(type().toCanonicalString()).append(" get").append(classCase).append("() {");
    builder.append("\n  return ").append(instanceCase).append(';');
    builder.append("\n}");
    return builder.toString();
  }

  protected List<AnnotationSpec> toElementAnnotations() {
    return null;
  }

  protected abstract Id id();
  protected abstract Registry.Type type();
  protected abstract String elementName();
  protected abstract Class<? extends Annotation> propertyAnnotation();
  protected abstract Class<? extends Annotation> elementAnnotation();
}