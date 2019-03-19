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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.openjax.jsonx.runtime.JxUtil;
import org.openjax.jsonx.runtime.Use;
import org.openjax.jsonx.runtime.ValidationException;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc;
import org.openjax.standard.util.FastCollections;
import org.openjax.standard.util.Identifiers;
import org.openjax.standard.util.Strings;
import org.openjax.xsb.runtime.Attribute;
import org.openjax.xsb.runtime.Binding;
import org.openjax.xsb.runtime.Bindings;
import org.w3.www._2001.XMLSchema.yAA;

abstract class Member extends Element {
  static final Function<Binding,String> elementXPath = new Function<Binding,String>() {
    @Override
    public String apply(final Binding t) {
      final String name;
      if (t instanceof xL4gluGCXYYJc.$Array)
        name = ((xL4gluGCXYYJc.$Array)t).getName$().text();
      else if (t instanceof xL4gluGCXYYJc.$Boolean)
        name = ((xL4gluGCXYYJc.$Boolean)t).getName$().text();
      else if (t instanceof xL4gluGCXYYJc.$Number)
        name = ((xL4gluGCXYYJc.$Number)t).getName$().text();
      else if (t instanceof xL4gluGCXYYJc.$Object)
        name = ((xL4gluGCXYYJc.$Object)t).getName$().text();
      else if (t instanceof xL4gluGCXYYJc.$String)
        name = ((xL4gluGCXYYJc.$String)t).getName$().text();
      else if (t instanceof xL4gluGCXYYJc.$Reference)
        name = ((xL4gluGCXYYJc.$Reference)t).getName$().text();
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
      builder.append("[@").append("name=\"").append(Strings.escapeForJava(name)).append("\"]");
      return builder.toString();
    }
  };

  static Integer parseMaxCardinality(final int minCardinality, final xL4gluGCXYYJc.$MaxOccurs maxCardinality, final String name, final Integer dflt) {
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
  final Id id;
  final String name;
  final boolean isRegex;
  final Boolean nullable;
  final Use use;
  final Integer minOccurs;
  final Integer maxOccurs;

  private static boolean isRegex(final String name) {
    return true;
  }

  Member(final Registry registry, final Id id, final String name, final Boolean nullable, final Use use, final Integer minOccurs, final Integer maxOccurs) {
    this.registry = registry;
    this.id = id;
    this.name = name;
    this.isRegex = isRegex(name);
    this.nullable = nullable == null || nullable ? null : nullable;
    this.use = use == Use.REQUIRED ? null : use;
    this.minOccurs = minOccurs == null || minOccurs == 1 ? null : minOccurs;
    this.maxOccurs = maxOccurs == null || maxOccurs == Integer.MAX_VALUE ? null : maxOccurs;
    checkMinMaxOccurs(name, minOccurs, maxOccurs);
  }

  Member(final Registry registry, final Id id, final yAA.$Boolean nullable, final yAA.$NonNegativeInteger minOccurs, final xL4gluGCXYYJc.$MaxOccurs maxOccurs) {
    this(registry, id, null, nullable == null ? null : nullable.text(), null, minOccurs.text().intValue(), parseMaxCardinality(minOccurs.text().intValue(), maxOccurs, "Occurs", Integer.MAX_VALUE));
  }

  Member(final Registry registry, final Id id, final yAA.$AnySimpleType name, final yAA.$Boolean nullable, final yAA.$String use) {
    this(registry, id, (String)name.text(), nullable == null ? null : nullable.text(), use == null ? null : Use.valueOf(use.text().toUpperCase()), null, null);
  }

  @Override
  Map<String,Object> toAttributes(final Element owner, final String packageName) {
    final Map<String,Object> attributes = super.toAttributes(owner, packageName);
    if (name != null)
      attributes.put("name", name);
    else if (owner instanceof Schema && !(this instanceof Referrer))
      attributes.put("name", id.toString());

    if (!(owner instanceof Schema)) {
      if (nullable != null)
        attributes.put("nullable", nullable);

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
      attributes.put("minOccurs", String.valueOf(minOccurs));

    if (maxOccurs != null)
      attributes.put("maxOccurs", String.valueOf(maxOccurs));
  }

  /**
   * @return The name of this member as a valid Java Identifier in
   *         lower-camelCase.
   */
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

    final String classCase = JxUtil.fixReserved(Identifiers.toClassCase(name));
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

  /**
   * Intended to be overridden by each concrete subclass, this method returns a
   * list of {@link AnnotationSpec} objects representing the annotations of
   * {@code this} element.
   */
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

  abstract Registry.Type type();
  abstract String elementName();
  abstract Class<? extends Annotation> propertyAnnotation();
  abstract Class<? extends Annotation> elementAnnotation();
}