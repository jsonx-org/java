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
import java.util.function.Function;

import org.lib4j.util.JavaIdentifiers;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Array;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$MaxCardinality;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Member;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Number;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Object;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$String;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Template;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.Jsonx;
import org.libx4j.jsonx.runtime.ArrayProperty;
import org.libx4j.jsonx.runtime.BooleanProperty;
import org.libx4j.jsonx.runtime.NumberProperty;
import org.libx4j.jsonx.runtime.ObjectProperty;
import org.libx4j.jsonx.runtime.StringProperty;
import org.libx4j.xsb.runtime.Binding;
import org.libx4j.xsb.runtime.Bindings;
import org.w3.www._2001.XMLSchema.yAA.$Boolean;
import org.w3.www._2001.XMLSchema.yAA.$NonNegativeInteger;

abstract class Element extends Member implements Annullable, Nameable, Recurrable, Requirable {
  static enum Kind {
    TEMPLATE,
    PROPERTY,
    MEMBER
  }

  protected static Element toElement(final Registry registry, final ComplexModel declarer, final Field field, final Object object) {
    final BooleanProperty booleanProperty = field.getDeclaredAnnotation(BooleanProperty.class);
    if (booleanProperty != null)
      return BooleanModel.referenceOrDeclare(registry, declarer, booleanProperty, field);

    final NumberProperty numberProperty = field.getDeclaredAnnotation(NumberProperty.class);
    if (numberProperty != null)
      return NumberModel.referenceOrDeclare(registry, declarer, numberProperty, field);

    final StringProperty stringProperty = field.getDeclaredAnnotation(StringProperty.class);
    if (stringProperty != null)
      return StringModel.referenceOrDeclare(registry, declarer, stringProperty, field);

    final ObjectProperty objectProperty = field.getDeclaredAnnotation(ObjectProperty.class);
    if (objectProperty != null)
      return ObjectModel.referenceOrDeclare(registry, declarer, objectProperty, field);

    final ArrayProperty arrayProperty = field.getDeclaredAnnotation(ArrayProperty.class);
    if (arrayProperty != null)
      return ArrayModel.referenceOrDeclare(registry, declarer, arrayProperty, field);

    return null;
  }

  protected static String getName(final String name, final Field field) {
    return name.length() > 0 ? name : field.getName();
  }

  protected static final Function<Binding,String> elementXPath = new Function<Binding,String>() {
    @Override
    public String apply(final Binding t) {
      final String name;
      if (t instanceof xL2gluGCXYYJc.$Boolean)
        name = ((xL2gluGCXYYJc.$Boolean)t).getName$().text();
      else if (t instanceof $Number)
        name = (($Number)t).getName$().text();
      else if (t instanceof $String)
        name = (($String)t).getName$().text();
      else if (t instanceof $Array)
        name = (($Array)t).getName$().text();
      else if (t instanceof Jsonx.Object)
        name = ((Jsonx.Object)t).getClass$().text();
      else if (t instanceof xL2gluGCXYYJc.$Boolean)
        name = ((xL2gluGCXYYJc.$Boolean)t).getName$().text();
      else if (t instanceof $Number)
        name = (($Number)t).getName$().text();
      else if (t instanceof $String)
        name = (($String)t).getName$().text();
      else if (t instanceof $Array)
        name = (($Array)t).getName$().text();
      else if (t instanceof $Template)
        name = (($Template)t).getName$().text();
      else if (t instanceof $Object)
        name = (($Object)t).getName$().text();
      else
        name = null;

      return name != null ? t.name().getLocalPart() + "[@" + (t instanceof $Object ? "class=" : "name=") + name + "]" : t.name().getLocalPart();
    }
  };

  private static Integer parseMaxCardinality(final int minCardinality, final $MaxCardinality maxCardinality) {
    final Integer max = "unbounded".equals(maxCardinality.text()) ? null : Integer.parseInt(maxCardinality.text());
    if (max != null && minCardinality > max)
      throw new ValidationException(Bindings.getXPath(((org.libx4j.xsb.runtime.Attribute)maxCardinality).owner(), elementXPath) + ": minOccurs=\"" + minCardinality + "\" > maxOccurs=\"" + max + "\"");

    return max;
  }

  private final String name;
  private final Boolean nullable;
  private final Boolean required;
  private final Integer minOccurs;
  private final Integer maxOccurs;

  // Templates
  public Element(final String name, final Boolean nullable, final Boolean required, final Integer minOccurs, final Integer maxOccurs) {
    this.name = name;
    this.nullable = nullable != null && nullable ? null : nullable;
    this.required = required != null && required ? null : required;
    this.minOccurs = minOccurs == null || minOccurs == 0 ? null : minOccurs;
    this.maxOccurs = maxOccurs == null || maxOccurs == Integer.MAX_VALUE ? null : maxOccurs;
  }

  // Members
  public Element(final String name, final $Boolean nullable, final $NonNegativeInteger minOccurs, final $MaxCardinality maxOccurs) {
    this(name, nullable == null ? null : nullable.text(), minOccurs.isDefault() ? null : minOccurs.text().intValue(), parseMaxCardinality(minOccurs.text().intValue(), maxOccurs));
  }

  // Members
  public Element(final String name, final Boolean nullable, final Integer minOccurs, final Integer maxOccurs) {
    this(name, nullable, null, minOccurs, maxOccurs);
  }

  // Properties
  public Element(final String name, final Boolean nullable, final Boolean required) {
    this(name, nullable, required, null, null);
  }

  public Element(final String name) {
    this(name, null, null, (Integer)null);
  }

  public Element(final $Member binding, final String name) {
    this(name);
  }

  protected String key() {
    return id().toString();
  }

  public abstract Id id();

  @Override
  public final String name() {
    return this.name;
  }

  public final String instanceName() {
    return JavaIdentifiers.toInstanceCase(name());
  }

  @Override
  public final Boolean nullable() {
    return nullable;
  }

  @Override
  public final Boolean required() {
    return required;
  }

  @Override
  public final Integer minOccurs() {
    return minOccurs;
  }

  @Override
  public final Integer maxOccurs() {
    return maxOccurs;
  }

  @Override
  protected String toJSON(final String packageName) {
    final StringBuilder builder = new StringBuilder("  type: \"").append(getClass().getSimpleName().toLowerCase()).append('"');
    if (name != null)
      builder.append(",\n  name: \"").append(name).append('"');

    if (nullable != null)
      builder.append(",\n  nullable: ").append(nullable);

    if (required != null)
      builder.append(",\n  required: ").append(required);

    if (minOccurs != null)
      builder.append(",\n  minOccurs: ").append(minOccurs);

    if (maxOccurs != null)
      builder.append(",\n  maxOccurs: ").append(maxOccurs);

    return builder.toString();
  }

  @Override
  protected String toJSONX(final Registry registry, final Member owner, final String packageName) {
    final StringBuilder builder = new StringBuilder();
    if (name != null)
      builder.append(" name=\"").append(name).append('"');
    else if (id() != null && !(this instanceof Template))
      builder.append(" template=\"").append(id()).append('"');

    final boolean shouldWriteNullable = owner instanceof Schema || !(this instanceof Template) && id() != null && !registry.hasRegistry(id());
    if (shouldWriteNullable && nullable != null && !nullable)
      builder.append(" nullable=\"").append(nullable).append('"');

    if (required != null && !required)
      builder.append(" required=\"").append(required).append('"');

    if (minOccurs != null && minOccurs != 0)
      builder.append(" minOccurs=\"").append(minOccurs).append('"');

    if (maxOccurs != null)
      builder.append(" maxOccurs=\"").append(maxOccurs).append('"');

    return builder.toString();
  }

  @Override
  protected final String toJSON() {
    throw new UnsupportedOperationException();
  }

  @Override
  protected final String toJSONX() {
    throw new UnsupportedOperationException();
  }

  protected void toAnnotation(final Attributes attributes, final String packageName) {
//    if (name != null)
//      string.append(", name=\"").append(name).append('"');

    if (nullable != null)
      attributes.put("nullable", nullable);

    if (required != null)
      attributes.put("required", required);

    if (minOccurs != null)
      attributes.put("minOccurs", minOccurs);

    if (maxOccurs != null)
      attributes.put("maxOccurs", maxOccurs);
  }

  protected final String toField(final String packageName) {
    final StringBuilder builder = new StringBuilder();
    final String elementAnnotations = toElementAnnotations(packageName);
    if (elementAnnotations != null)
      builder.append(elementAnnotations);

    builder.append('@').append(propertyAnnotation().getName());
    final Attributes attributes = new Attributes();
    toAnnotation(attributes, packageName);
    if (attributes.size() > 0)
      builder.append('(').append(attributes.toAnnotation()).append(')');

    builder.append('\n').append("public ").append(type().toCanonicalString(packageName)).append(' ').append(name()).append(';');
    return builder.toString();
  }

  protected String toElementAnnotations(final String packageName) {
    return null;
  }

  protected boolean shouldWriteNullable(final Registry registry) {
    return id() != null && !registry.hasRegistry(id());
  }

  protected abstract Type type();
  protected abstract Class<? extends Annotation> propertyAnnotation();
  protected abstract Class<? extends Annotation> elementAnnotation();
}