/* Copyright (c) 2017 Jsonx
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import org.jsonx.www.schema_0_2_2.xL0gluGCXYYJc;
import org.libj.util.FastCollections;
import org.libj.util.Strings;
import org.jaxsb.runtime.Attribute;
import org.jaxsb.runtime.Binding;
import org.jaxsb.runtime.Bindings;
import org.w3.www._2001.XMLSchema.yAA.$AnySimpleType;
import org.w3.www._2001.XMLSchema.yAA.$Boolean;
import org.w3.www._2001.XMLSchema.yAA.$NonNegativeInteger;
import org.w3.www._2001.XMLSchema.yAA.$String;

abstract class Member extends Element {
  static final Function<Binding,String> elementXPath = new Function<Binding,String>() {
    @Override
    public String apply(final Binding t) {
      final String name;
      if (t instanceof xL0gluGCXYYJc.$Array)
        name = ((xL0gluGCXYYJc.$Array)t).getName$().text();
      else if (t instanceof xL0gluGCXYYJc.$Boolean)
        name = ((xL0gluGCXYYJc.$Boolean)t).getName$().text();
      else if (t instanceof xL0gluGCXYYJc.$Number)
        name = ((xL0gluGCXYYJc.$Number)t).getName$().text();
      else if (t instanceof xL0gluGCXYYJc.$Object)
        name = ((xL0gluGCXYYJc.$Object)t).getName$().text();
      else if (t instanceof xL0gluGCXYYJc.$String)
        name = ((xL0gluGCXYYJc.$String)t).getName$().text();
      else if (t instanceof xL0gluGCXYYJc.$Reference)
        name = ((xL0gluGCXYYJc.$Reference)t).getName$().text();
      else if (t instanceof xL0gluGCXYYJc.Schema.Array)
        name = ((xL0gluGCXYYJc.Schema.Array)t).getName$().text();
      else if (t instanceof xL0gluGCXYYJc.Schema.Boolean)
        name = ((xL0gluGCXYYJc.Schema.Boolean)t).getName$().text();
      else if (t instanceof xL0gluGCXYYJc.Schema.Number)
        name = ((xL0gluGCXYYJc.Schema.Number)t).getName$().text();
      else if (t instanceof xL0gluGCXYYJc.Schema.Object)
        name = ((xL0gluGCXYYJc.Schema.Object)t).getName$().text();
      else if (t instanceof xL0gluGCXYYJc.Schema.String)
        name = ((xL0gluGCXYYJc.Schema.String)t).getName$().text();
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

  static Integer parseMaxCardinality(final int minCardinality, final xL0gluGCXYYJc.$MaxOccurs maxCardinality, final String name, final Integer dflt) {
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
  final Boolean nullable;
  final Use use;
  final Integer minOccurs;
  final Integer maxOccurs;

  Member(final Registry registry, final Id id, final String name, final Boolean nullable, final Use use, final Integer minOccurs, final Integer maxOccurs) {
    this.registry = registry;
    this.id = id;
    this.name = name;
    this.nullable = nullable == null || nullable ? null : nullable;
    this.use = use == Use.REQUIRED ? null : use;
    this.minOccurs = minOccurs == null || minOccurs == 1 ? null : minOccurs;
    this.maxOccurs = maxOccurs == null || maxOccurs == Integer.MAX_VALUE ? null : maxOccurs;
    checkMinMaxOccurs(name, minOccurs, maxOccurs);
  }

  Member(final Registry registry, final Id id, final $Boolean nullable, final $NonNegativeInteger minOccurs, final xL0gluGCXYYJc.$MaxOccurs maxOccurs) {
    this(registry, id, null, nullable == null ? null : nullable.text(), null, minOccurs.text().intValue(), parseMaxCardinality(minOccurs.text().intValue(), maxOccurs, "Occurs", Integer.MAX_VALUE));
  }

  Member(final Registry registry, final Id id, final $AnySimpleType name, final $Boolean nullable, final $String use) {
    this(registry, id, (String)name.text(), nullable == null ? null : nullable.text(), use == null ? null : Use.valueOf(use.text().toUpperCase()), null, null);
  }

  @Override
  Map<String,Object> toAttributes(final Element owner, final String prefix, final String packageName) {
    final Map<String,Object> attributes = super.toAttributes(owner, prefix, packageName);
    if (name != null)
      attributes.put(nameName(), name);
    else if (owner instanceof SchemaElement && !(this instanceof Referrer))
      attributes.put(nameName(), id.toString());

    if (!(owner instanceof SchemaElement)) {
      if (nullable != null)
        attributes.put(prefix + "nullable", nullable);

      if (use != null)
        attributes.put(prefix + "use", use.toString().toLowerCase());
    }

    if (minOccurs != null)
      attributes.put(prefix + "minOccurs", String.valueOf(minOccurs));

    if (maxOccurs != null)
      attributes.put(prefix + "maxOccurs", String.valueOf(maxOccurs));

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

  final String toField() {
    final StringBuilder builder = new StringBuilder();
    final List<AnnotationSpec> elementAnnotations = toElementAnnotations();
    if (elementAnnotations != null && elementAnnotations.size() > 0)
      builder.append(FastCollections.toString(elementAnnotations, '\n')).append('\n');

    final AttributeMap attributes = new AttributeMap();
    toAnnotationAttributes(attributes, this);
    final String instanceName = JsdUtil.toInstanceName(name);
    if (!name.equals(instanceName) && !attributes.containsKey("name"))
      attributes.put("name", "\"" + Strings.escapeForJava(name) + "\"");

    final boolean isRegex = this instanceof AnyModel && Strings.isRegex(name);
    final Registry.Type type;
    if (isRegex)
      type = registry.getType(LinkedHashMap.class, registry.getType(String.class), type());
    else
      type = nullable == null && use == Use.OPTIONAL ? registry.getType(Optional.class, type()) : type();

    final String typeName = type.toCanonicalString();
    final String classCase = JsdUtil.fixReserved(JsdUtil.toClassName(name));
    builder.append(new AnnotationSpec(propertyAnnotation(), attributes));
    if (isRegex) {
      builder.append("\npublic final ").append(typeName).append(' ').append(instanceName).append(" = new ").append(LinkedHashMap.class.getName()).append("<>();");
    }
    else {
      builder.append("\nprivate ").append(typeName).append(' ').append(instanceName).append(';');
      builder.append("\n\npublic void set").append(classCase).append("(final ").append(typeName).append(' ').append(instanceName).append(") {");
      builder.append("\n  this.").append(instanceName).append(" = ").append(instanceName).append(';');
      builder.append("\n}");
      builder.append("\n\npublic ").append(typeName).append(" get").append(classCase).append("() {");
      builder.append("\n  return ").append(instanceName).append(';');
      builder.append("\n}");
    }

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

  String nameName() {
    return "name";
  }

  abstract Registry.Type type();
  abstract String elementName();
  abstract Class<? extends Annotation> propertyAnnotation();
  abstract Class<? extends Annotation> elementAnnotation();
}