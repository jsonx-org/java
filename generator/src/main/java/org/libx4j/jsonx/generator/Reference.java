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

import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$ArrayMember;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$Reference;

class Reference extends Element {
  private final Model model;

  public Reference(final Member owner, final $ArrayMember.Reference binding, final Model model) {
    super(owner, binding, null, null, binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.model = model;
  }

  public Reference(final Member owner, final $Reference binding, final Model model) {
    super(owner, binding.getName$().text(), null, binding.getRequired$().text(), binding.getDoc$() == null ? null : binding.getDoc$().text());
    this.model = model;
  }

  public Reference(final Member owner, final String name, final boolean required, final Model model, final String doc) {
    super(owner, name, null, required, doc);
    this.model = model;
  }

  public Reference(final Member owner, final Integer minOccurs, final Integer maxOccurs, final Model model, final String doc) {
    super(owner, null, null, minOccurs, maxOccurs, doc);
    this.model = model;
  }

  @Override
  public final Boolean nullable() {
    return model.nullable();
  }

  public final Model template() {
    return this.model;
  }

  @Override
  protected final Type type() {
    return model.type();
  }

  @Override
  protected final Class<? extends Annotation> propertyAnnotation() {
    return model.propertyAnnotation();
  }

  @Override
  protected final Class<? extends Annotation> elementAnnotation() {
    return model.elementAnnotation();
  }

  @Override
  protected final Element normalize(final Registry registry) {
    return registry.getNumReferrers(template()) != 1 || template() instanceof ObjectModel && ((ObjectModel)template()).isAbstract() ? this : template().merge(this);
  }

  @Override
  protected final String toJSON(final String pacakgeName) {
    final StringBuilder builder = new StringBuilder(super.toJSON(pacakgeName));
    if (builder.length() > 0)
      builder.insert(0, ",\n");

    if (model != null)
      builder.append(",\n  template: \"").append(getShortName(model.reference(), pacakgeName)).append('"');

    return "{\n" + (builder.length() > 0 ? builder.substring(2) : builder.toString()) + "\n}";
  }

  @Override
  protected final String toJSONX(final String pacakgeName) {
    final StringBuilder builder = new StringBuilder(kind() == Kind.PROPERTY ? "<property xsi:type=\"reference\"" : "<reference");
    if (model != null)
      builder.append(" template=\"").append(getShortName(model.reference(), pacakgeName)).append('"');

    return builder.append(super.toJSONX(pacakgeName)).append("/>").toString();
  }

  @Override
  protected final String toAnnotation(final boolean full) {
    final StringBuilder builder = new StringBuilder(super.toAnnotation(true));
    final String annotation = model.toAnnotation(false);
    if (builder.length() > 0 && annotation.length() > 0)
      builder.append(", ");

    return builder.append(annotation).toString();
  }
}