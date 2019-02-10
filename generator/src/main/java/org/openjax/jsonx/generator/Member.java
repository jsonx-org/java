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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc.$Array;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc.$Boolean;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc.$MaxOccurs;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc.$Number;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc.$Object;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc.$Reference;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc.$String;
import org.openjax.jsonx.runtime.ArrayProperty;
import org.openjax.jsonx.runtime.BooleanProperty;
import org.openjax.jsonx.runtime.NumberProperty;
import org.openjax.jsonx.runtime.ObjectProperty;
import org.openjax.jsonx.runtime.StringProperty;
import org.openjax.jsonx.runtime.Use;
import org.openjax.jsonx.runtime.ValidationException;
import org.openjax.standard.util.FastCollections;
import org.openjax.standard.util.Identifiers;
import org.openjax.standard.xml.datatypes_0_9_2.xNxQJbgwJdgwJcA.$Identifier;
import org.openjax.xsb.runtime.Attribute;
import org.openjax.xsb.runtime.Binding;
import org.openjax.xsb.runtime.Bindings;
import org.w3.www._2001.XMLSchema.yAA;

abstract class Member extends Element {
  static Member toMember(final Registry registry, final Referrer<?> referrer, final Field field) {
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

  static final Function<Binding,String> elementXPath = new Function<Binding,String>() {
    @Override
    public String apply(final Binding t) {
      final String name;
      if (t instanceof $Array)
        name = (($Array)t).getName$().text();
      else if (t instanceof $Boolean)
        name = (($Boolean)t).getName$().text();
      else if (t instanceof $Number)
        name = (($Number)t).getName$().text();
      else if (t instanceof $Object)
        name = (($Object)t).getName$().text();
      else if (t instanceof $String)
        name = (($String)t).getName$().text();
      else if (t instanceof $Reference)
        name = (($Reference)t).getName$().text();
      else if (t instanceof xL4gluGCXYYJc.Schema.ArrayType)
        name = ((xL4gluGCXYYJc.Schema.ArrayType)t).getName$().text();
      else if (t instanceof xL4gluGCXYYJc.Schema.BooleanType)
        name = ((xL4gluGCXYYJc.Schema.BooleanType)t).getName$().text();
      else if (t instanceof xL4gluGCXYYJc.Schema.NumberType)
        name = ((xL4gluGCXYYJc.Schema.NumberType)t).getName$().text();
      else if (t instanceof xL4gluGCXYYJc.Schema.ObjectType)
        name = ((xL4gluGCXYYJc.Schema.ObjectType)t).getName$().text();
      else if (t instanceof xL4gluGCXYYJc.Schema.StringType)
        name = ((xL4gluGCXYYJc.Schema.StringType)t).getName$().text();
      else
        name = null;

      if (name == null)
        return t.name().getLocalPart();

      final StringBuilder builder = new StringBuilder();
      builder.append(t.name().getLocalPart());
      builder.append("[@").append("name=\"").append(name).append("\"]");
      return builder.toString();
    }
  };

  static Integer parseMaxCardinality(final int minCardinality, final $MaxOccurs maxCardinality, final String name, final Integer dflt) {
    final Integer max = "unbounded".equals(maxCardinality.text()) ? Integer.MAX_VALUE : Integer.parseInt(maxCardinality.text());
    if (minCardinality > max)
      throw new ValidationException("min" + name + "=\"" + minCardinality + "\" > max" + name + "=\"" + max + "\"\n" + Bindings.getXPath(((Attribute)maxCardinality).owner(), elementXPath) + "[@min" + name + "=" + minCardinality + " and @max" + name + "=" + maxCardinality.text() + "]");

    return max == dflt ? null : max;
  }

  private static void checkMinMaxOccurs(final String source, final Integer minOccurs, final Integer maxOccurs) {
    if (minOccurs != null && maxOccurs != null && minOccurs > maxOccurs)
      throw new ValidationException(source + ": minOccurs=\"" + minOccurs + "\" > maxOccurs=\"" + maxOccurs + "\"");
  }

  final Registry registry;
  final String name;
  final Boolean nullable;
  final Use use;
  final Integer minOccurs;
  final Integer maxOccurs;

  Member(final Registry registry, final String name, final Boolean nullable, final Use use, final Integer minOccurs, final Integer maxOccurs) {
    this.registry = registry;
    this.name = name;
    this.nullable = nullable == null || nullable ? null : nullable;
    this.use = use == Use.REQUIRED ? null : use;
    this.minOccurs = minOccurs == null || minOccurs == 1 ? null : minOccurs;
    this.maxOccurs = maxOccurs == null || maxOccurs == Integer.MAX_VALUE ? null : maxOccurs;
    checkMinMaxOccurs(name, minOccurs, maxOccurs);
  }

  Member(final Registry registry, final yAA.$Boolean nullable, final yAA.$NonNegativeInteger minOccurs, final $MaxOccurs maxOccurs) {
    this(registry, null, nullable == null ? null : nullable.text(), null, minOccurs.text().intValue(), parseMaxCardinality(minOccurs.text().intValue(), maxOccurs, "Occurs", Integer.MAX_VALUE));
  }

  Member(final Registry registry, final $Identifier name, final yAA.$Boolean nullable, final yAA.$String use) {
    this(registry, name.text(), nullable == null ? null : nullable.text(), use == null ? null : Use.valueOf(use.text().toUpperCase()), null, null);
  }

  final String instanceName() {
    return Identifiers.toInstanceCase(name);
  }

  @Override
  Map<String,String> toXmlAttributes(final Element owner, final String packageName) {
    final Map<String,String> attributes = super.toXmlAttributes(owner, packageName);
    if (name != null)
      attributes.put("name", name);
    else if (owner instanceof Schema && !(this instanceof Referrer))
      attributes.put("name", id().toString());

    if (!(owner instanceof Schema)) {
      if (nullable != null)
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
   * the target {@code attributes} parameter with annotation attributes
   * pertaining to this {@code Member}.
   *
   * @param attributes The target {@code attributes} parameter.
   * @param owner The {@code Member} that owns {@code this}.
   */
  void toAnnotationAttributes(final AttributeMap attributes, final Member owner) {
    if (nullable != null)
      attributes.put("nullable", nullable);

    if (use != null)
      attributes.put("use", Use.class.getName() + "." + use);

    if (minOccurs != null)
      attributes.put("minOccurs", minOccurs);

    if (maxOccurs != null)
      attributes.put("maxOccurs", maxOccurs);
  }

  final String toInstanceName() {
    return Identifiers.toInstanceCase(name);
  }

  final String toField() {
    final StringBuilder builder = new StringBuilder();
    final List<AnnotationSpec> elementAnnotations = toElementAnnotations();
    if (elementAnnotations != null && elementAnnotations.size() > 0)
      builder.append(FastCollections.toString(elementAnnotations, '\n')).append('\n');

    final AttributeMap attributes = new AttributeMap();
    toAnnotationAttributes(attributes, this);
    final String instanceName = toInstanceName();
    if (!name.equals(instanceName) && !attributes.containsKey("name"))
      attributes.put("name", "\"" + name + "\"");

    String classCase = Identifiers.toClassCase(name);
    if ("Class".equals(classCase))
      classCase = "0lass";

    final String type = nullable == null && use == Use.OPTIONAL ? Optional.class.getName() + "<" + type().toCanonicalString() + ">" : type().toCanonicalString();

    builder.append(new AnnotationSpec(propertyAnnotation(), attributes));
    builder.append("\nprivate ").append(type).append(' ').append(instanceName).append(';');
    builder.append("\n\npublic void set").append(classCase).append("(final ").append(type).append(" ").append(instanceName).append(") {");
    builder.append("\n  this.").append(instanceName).append(" = ").append(instanceName).append(';');
    builder.append("\n}");
    builder.append("\n\npublic ").append(type).append(" get").append(classCase).append("() {");
    builder.append("\n  return ").append(instanceName).append(';');
    builder.append("\n}");
    return builder.toString();
  }

  List<AnnotationSpec> toElementAnnotations() {
    return null;
  }

  /**
   * Intended to be overridden by each concrete subclass, this method collects
   * all {@code Registry.Type} declarations of elements that are members of
   * {@code this} element.
   *
   * @param types The {@code Set} into which the {@code Registry.Type}
   *          declarations must be added.
   */
  void getDeclaredTypes(final Set<Registry.Type> types) {
  }

  abstract Id id();
  abstract Registry.Type type();
  abstract String elementName();
  abstract Class<? extends Annotation> propertyAnnotation();
  abstract Class<? extends Annotation> elementAnnotation();
}