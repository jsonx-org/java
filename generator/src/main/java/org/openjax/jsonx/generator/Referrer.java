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

import org.openjax.jsonx.runtime.Use;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc.$MaxOccurs;
import org.w3.www._2001.XMLSchema.yAA;
import org.w3.www._2001.XMLSchema.yAA.$Boolean;
import org.w3.www._2001.XMLSchema.yAA.$NonNegativeInteger;
import org.w3.www._2001.XMLSchema.yAA.$String;

abstract class Referrer<T extends Referrer<?>> extends Model {
  private final Registry.Type type;

  Referrer(final Registry registry, final yAA.$AnySimpleType name, final yAA.$Boolean nullable, final $String use, final Registry.Type type) {
    super(registry, Id.named(type), name, nullable, use);
    this.type = type;
  }

  Referrer(final Registry registry, final $Boolean nullable, final $NonNegativeInteger minOccurs, final $MaxOccurs maxOccurs, final Registry.Type type) {
    super(registry, Id.named(type), nullable, minOccurs, maxOccurs);
    this.type = type;
  }

  Referrer(final Registry registry, final Registry.Type type) {
    super(registry, Id.named(type));
    this.type = type;
  }

  Referrer(final Registry registry, final Boolean nullable, final Use use, final Registry.Type type) {
    super(registry, Id.named(type), nullable, use);
    this.type = type;
  }

  Member getReference(final $String type) {
    if (type == null)
      return null;

    final Id id = Id.named(type);
    if (registry.isPending(id))
      return new Deferred<>(null, () -> registry.getModel(id));

    final Model model = registry.getModel(id);
    if (model == null)
      throw new IllegalStateException("Top-level " + elementName() + " \"" + type + "\" not found");

    return model;
  }

  final Registry.Type classType() {
    return type;
  }

  abstract List<AnnotationSpec> getClassAnnotation();
  abstract String toSource(Settings settings);
  abstract void resolveReferences();
}