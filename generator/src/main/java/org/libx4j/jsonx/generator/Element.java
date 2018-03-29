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
import org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.$Element;
import org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.$MaxCardinality;
import org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.$Object;
import org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.Jsonx;
import org.libx4j.jsonx.runtime.ArrayProperty;
import org.libx4j.jsonx.runtime.BooleanProperty;
import org.libx4j.jsonx.runtime.NumberProperty;
import org.libx4j.jsonx.runtime.ObjectProperty;
import org.libx4j.jsonx.runtime.StringProperty;
import org.libx4j.xsb.runtime.Binding;
import org.libx4j.xsb.runtime.Bindings;
import org.w3.www._2001.XMLSchema.yAA.$NonNegativeInteger;

abstract class Element extends Factor implements Annullable, Nameable, Recurrable, Requirable {
  protected static Element toElement(final Schema schema, final Registry registry, final ComplexModel declarer, final Field field, final Object object) {
    final BooleanProperty booleanProperty = field.getDeclaredAnnotation(BooleanProperty.class);
    if (booleanProperty != null)
      return BooleanModel.referenceOrDeclare(schema, registry, declarer, booleanProperty, field);

    final NumberProperty numberProperty = field.getDeclaredAnnotation(NumberProperty.class);
    if (numberProperty != null)
      return NumberModel.referenceOrDeclare(schema, registry, declarer, numberProperty, field);

    final StringProperty stringProperty = field.getDeclaredAnnotation(StringProperty.class);
    if (stringProperty != null)
      return StringModel.referenceOrDeclare(schema, registry, declarer, stringProperty, field);

    final ObjectProperty objectProperty = field.getDeclaredAnnotation(ObjectProperty.class);
    if (objectProperty != null)
      return ObjectModel.referenceOrDeclare(schema, registry, declarer, objectProperty, field);

    final ArrayProperty arrayProperty = field.getDeclaredAnnotation(ArrayProperty.class);
    if (arrayProperty != null)
      return ArrayModel.referenceOrDeclare(schema, registry, declarer, arrayProperty, field);

    return null;
  }

  protected static String getName(final String name, final Field field) {
    return name.length() > 0 ? name : field.getName();
  }

  protected static String getShortName(final String longName, final String pacakgeName) {
    return pacakgeName != null && longName.startsWith(pacakgeName) ? longName.substring(pacakgeName.length() + 1) : longName;
  }

  protected static final Function<Binding,String> elementXPath = new Function<Binding,String>() {
    @Override
    public String apply(final Binding t) {
      final String name;
      if (t instanceof Jsonx.Boolean)
        name = ((Jsonx.Boolean)t).getName$().text();
      else if (t instanceof Jsonx.Number)
        name = ((Jsonx.Number)t).getName$().text();
      else if (t instanceof Jsonx.String)
        name = ((Jsonx.String)t).getName$().text();
      else if (t instanceof Jsonx.Array)
        name = ((Jsonx.Array)t).getName$().text();
      else if (t instanceof Jsonx.Object)
        name = ((Jsonx.Object)t).getClass$().text();
      else if (t instanceof $Object.Boolean)
        name = (($Object.Boolean)t).getName$().text();
      else if (t instanceof $Object.Number)
        name = (($Object.Number)t).getName$().text();
      else if (t instanceof $Object.String)
        name = (($Object.String)t).getName$().text();
      else if (t instanceof $Object.Array)
        name = (($Object.Array)t).getName$().text();
      else if (t instanceof $Object.Object)
        name = (($Object.Object)t).getName$().text();
      else if (t instanceof $Object.Ref)
        name = (($Object.Ref)t).getName$().text();
      else
        name = null;

      return name != null ? t.name().getLocalPart() + "[@" + (t instanceof $Object ? "class" : "name=") + name + "]" : t.name().getLocalPart();
    }
  };

  private static Integer parseMaxCardinality(final int minCardinality, final $MaxCardinality maxCardinality) {
    final Integer max = "unbounded".equals(maxCardinality.text()) ? null : Integer.parseInt(maxCardinality.text());
    if (max != null && minCardinality > max)
      throw new ValidationException(Bindings.getXPath(((org.libx4j.xsb.runtime.Attribute)maxCardinality).owner(), elementXPath) + ": minOccurs=\"" + minCardinality + "\" > maxOccurs=\"" + max + "\"");

    return max;
  }

  private final Schema schema;
  private final String name;
  private final Boolean required;
  private final Boolean nullable;
  private final Integer minOccurs;
  private final Integer maxOccurs;
  private final String doc;

  public Element(final Schema schema, final String name, final Boolean required, final Boolean nullable, final Integer minOccurs, final Integer maxOccurs, final String doc) {
    this.schema = schema;
    this.name = name;
    this.required = required;
    this.nullable = nullable;
    this.minOccurs = minOccurs;
    this.maxOccurs = maxOccurs == null || maxOccurs == Integer.MAX_VALUE ? null : maxOccurs;
    this.doc = doc;
  }

  // Annullable, Recurrable
  public Element(final Schema schema, final String name, final Boolean nullable, final Integer minOccurs, final Integer maxOccurs, final String doc) {
    this(schema, name, null, nullable, minOccurs, maxOccurs, doc);
  }

  public Element(final Schema schema, final $Element binding, final String name, final boolean nullable, final $NonNegativeInteger minOccurs, final $MaxCardinality maxOccurs) {
    this(schema, name, nullable, minOccurs.text().intValue(), parseMaxCardinality(minOccurs.text().intValue(), maxOccurs), binding.getDoc$() == null ? null : binding.getDoc$().text());
  }

  // Annullable, Nameable, Requirable
  public Element(final Schema schema, final String name, final boolean required, final boolean nullable, final String doc) {
    this(schema, name, required, nullable, null, null, doc);
  }

  // Nameable
  public Element(final Schema schema, final String name, final String doc) {
    this(schema, name, null, null, null, null, doc);
  }

  public Element(final Schema schema, final $Element binding, final String name) {
    this(schema, name, binding.getDoc$() == null ? null : binding.getDoc$().text());
  }

  public Element(final Element copy) {
    this.schema = copy.schema;
    this.name = copy.name;
    this.required = copy.required;
    this.nullable = copy.nullable;
    this.minOccurs = copy.minOccurs;
    this.maxOccurs = copy.maxOccurs;
    this.doc = copy.doc;
  }

  public final Schema getSchema() {
    return schema;
  }

  @Override
  public final String name() {
    return this.name;
  }

  public final String instanceName() {
    return JavaIdentifiers.toInstanceCase(name());
  }

  @Override
  public final Boolean required() {
    return required;
  }

  @Override
  public final Boolean nullable() {
    return nullable;
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
  protected String toJSON(final String pacakgeName) {
    final StringBuilder builder = new StringBuilder("  type: \"").append(getClass().getSimpleName().toLowerCase()).append('"');
    if (name != null)
      builder.append(",\n  name: \"").append(name).append('"');

    if (required != null)
      builder.append(",\n  required: ").append(required);

    if (nullable != null)
      builder.append(",\n  nullable: ").append(nullable);

    if (minOccurs != null)
      builder.append(",\n  minOccurs: ").append(minOccurs);

    if (maxOccurs != null)
      builder.append(",\n  maxOccurs: ").append(maxOccurs);

    if (doc != null)
      builder.append(",\n  doc: \"").append(doc.replace("\\", "\\\\").replace("\"", "\\\"")).append('"');

    return builder.toString();
  }

  @Override
  protected String toJSONX(final String pacakgeName) {
    final StringBuilder builder = new StringBuilder();
    if (name != null)
      builder.append(" name=\"").append(name).append('"');

    if (required != null && !required)
      builder.append(" required=\"").append(required).append('"');

    if (nullable != null && !nullable)
      builder.append(" nullable=\"").append(nullable).append('"');

    if (minOccurs != null && minOccurs != 0)
      builder.append(" minOccurs=\"").append(minOccurs).append('"');

    if (maxOccurs != null)
      builder.append(" maxOccurs=\"").append(maxOccurs).append('"');

    if (doc != null)
      builder.append(" doc=\"").append(doc.replace("&", "&amp;").replace("\"", "&quot;")).append('"');

    return builder.toString();
  }

  @Override
  public final String toJSON() {
    return toJSON(null);
  }

  @Override
  public final String toJSONX() {
    return toJSONX(null);
  }

  protected String toAnnotation(final boolean full) {
    final StringBuilder builder = new StringBuilder();
//    if (name != null)
//      string.append(", name=\"").append(name).append('"');

    if (required != null && !required)
      builder.append(", required=").append(required);

    if (nullable != null && !nullable)
      builder.append(", nullable=").append(nullable);

    if (minOccurs != null && minOccurs != 0)
      builder.append(", minOccurs=").append(minOccurs);

    if (maxOccurs != null)
      builder.append(", maxOccurs=").append(maxOccurs);

    return builder.length() == 0 ? "" : builder.substring(2);
  }

  protected String toField() {
    final StringBuilder builder = new StringBuilder();
    builder.append('@').append(propertyAnnotation().getName());
    final String annotation = toAnnotation(true);
    if (annotation.length() > 0)
      builder.append('(').append(annotation).append(')');

    builder.append('\n');
    builder.append("public ");
    builder.append(className());
    builder.append(' ').append(name());
    builder.append(';');
    return builder.toString();
  }

  protected abstract Type className();
  protected abstract Class<? extends Annotation> propertyAnnotation();
  protected abstract Class<? extends Annotation> elementAnnotation();
  protected abstract Element normalize(final Registry registry);
}