/* Copyright (c) 2018 OpenJAX
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

import java.util.List;

import org.lib4j.xml.datatypes_1_0_4.xL3gluGCXYYJc.$JavaIdentifier;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$MaxCardinality;
import org.openjax.jsonx.runtime.Use;
import org.w3.www._2001.XMLSchema.yAA.$Boolean;
import org.w3.www._2001.XMLSchema.yAA.$NonNegativeInteger;
import org.w3.www._2001.XMLSchema.yAA.$String;

abstract class Referrer<T extends Referrer<?>> extends Model {
  public Referrer(final Registry registry, final $JavaIdentifier name, final $String use) {
    super(registry, name, use);
  }

  public Referrer(final Registry registry, final $Boolean nullable, final $NonNegativeInteger minOccurs, final $MaxCardinality maxOccurs) {
    super(registry, nullable, minOccurs, maxOccurs);
  }

  public Referrer(final Registry registry) {
    super(registry);
  }

  public Referrer(final Registry registry, final Boolean nullable, final Use use) {
    super(registry, nullable, use);
  }

  @SuppressWarnings("unchecked")
  protected T getReference(final $String className) {
    if (className == null)
      return null;

    final T parent;
    try {
      parent = (T)registry.getModel(new Id(className));
    }
    catch (final ClassCastException e) {
      throw new IllegalStateException("Top-level " + elementName() + " \"" + className + "\" incorrect type");
    }

    if (parent == null)
      throw new IllegalStateException("Top-level " + elementName() + " \"" + className + "\" not found");

    return parent;
  }

  protected abstract Registry.Type classType();
  protected abstract List<AnnotationSpec> getClassAnnotation();
  protected abstract String toSource();
}