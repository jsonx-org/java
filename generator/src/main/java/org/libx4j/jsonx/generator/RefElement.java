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

import org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.$Array;
import org.libx4j.jsonx.jsonx_0_9_7.xL2gluGCXYYJc.$Object;

class RefElement extends Element {
  private final Model ref;

  // Annullable, Recurrable
  public RefElement(final $Array.Ref binding, final Model ref) {
    super(binding, null, binding.getNullable$().text(), binding.getMinOccurs$(), binding.getMaxOccurs$());
    this.ref = ref;
  }

  // Nameable, Annullable
  public RefElement(final $Object.Ref binding, final Model ref) {
    super(binding.getName$().text(), binding.getRequired$().text(), binding.getNullable$().text(), binding.getDoc$() == null ? null : binding.getDoc$().text());
    this.ref = ref;
  }

  public RefElement(final String name, final boolean required, final boolean nullable, final Model ref, final String doc) {
    super(name, required, nullable, doc);
    this.ref = ref;
  }

  public RefElement(final boolean nullable, final Integer minOccurs, final Integer maxOccurs, final Model ref, final String doc) {
    super(null, nullable, minOccurs, maxOccurs, doc);
    this.ref = ref;
  }

  public final Model ref() {
    return this.ref;
  }

  @Override
  protected final String className() {
    return ref.className();
  }

  @Override
  protected final Class<? extends Annotation> propertyAnnotation() {
    return ref.propertyAnnotation();
  }

  @Override
  protected final Class<? extends Annotation> elementAnnotation() {
    return ref.elementAnnotation();
  }

  @Override
  protected final Element normalize(final Registry registry) {
    return registry.getNumReferrers(ref()) != 1 || ref() instanceof ObjectModel && ((ObjectModel)ref()).isAbstract() ? this : ref().merge(this);
  }

  @Override
  protected final String toJSON(final String pacakgeName) {
    final StringBuilder builder = new StringBuilder(super.toJSON(pacakgeName));
    if (builder.length() > 0)
      builder.insert(0, ",\n");

    if (ref != null)
      builder.append(",\n  ref: \"").append(getShortName(ref.ref(), pacakgeName)).append('"');

    return "{\n" + (builder.length() > 0 ? builder.substring(2) : builder.toString()) + "\n}";
  }

  @Override
  protected final String toJSONX(final String pacakgeName) {
    final StringBuilder builder = new StringBuilder("<ref");
    if (ref != null)
      builder.append(" property=\"").append(getShortName(ref.ref(), pacakgeName)).append('"');

    return builder.append(super.toJSONX(pacakgeName)).append("/>").toString();
  }

  @Override
  protected final String toAnnotation(final boolean full) {
    final StringBuilder builder = new StringBuilder(super.toAnnotation(true));
    final String annotation = ref.toAnnotation(false);
    if (builder.length() > 0 && annotation.length() > 0)
      builder.append(", ");

    return builder.append(annotation).toString();
  }
}