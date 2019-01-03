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

import org.openjax.jsonx.jsonx_0_9_8.xL3gluGCXYYJc.$MaxOccurs;
import org.openjax.jsonx.runtime.Use;
import org.openjax.standard.xml.datatypes_0_9_2.xNxQJbgwJdgwJcA.$Identifier;
import org.w3.www._2001.XMLSchema.yAA;
import org.w3.www._2001.XMLSchema.yAA.$Boolean;
import org.w3.www._2001.XMLSchema.yAA.$NonNegativeInteger;
import org.w3.www._2001.XMLSchema.yAA.$String;

abstract class Referrer<T extends Referrer<?>> extends Model {
  Referrer(final Registry registry, final $Identifier name, final yAA.$Boolean nullable, final $String use) {
    super(registry, name, nullable, use);
  }

  Referrer(final Registry registry, final $Boolean nullable, final $NonNegativeInteger minOccurs, final $MaxOccurs maxOccurs) {
    super(registry, nullable, minOccurs, maxOccurs);
  }

  Referrer(final Registry registry) {
    super(registry);
  }

  Referrer(final Registry registry, final Boolean nullable, final Use use) {
    super(registry, nullable, use);
  }

  @SuppressWarnings("unchecked")
  T getReference(final $String className) {
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

  abstract Registry.Type classType();
  abstract List<AnnotationSpec> getClassAnnotation();
  abstract String toSource(final Settings settings);
}