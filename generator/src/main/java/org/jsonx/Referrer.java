/* Copyright (c) 2018 JSONx
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

import static org.libj.lang.Assertions.*;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Set;

import org.jaxsb.runtime.Binding;
import org.jsonx.www.binding_0_5.xL1gluGCXAA.$FieldBinding;
import org.jsonx.www.binding_0_5.xL1gluGCXAA.$FieldIdentifier;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Documented;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$MaxOccurs;
import org.w3.www._2001.XMLSchema.yAA.$AnySimpleType;
import org.w3.www._2001.XMLSchema.yAA.$AnyType;
import org.w3.www._2001.XMLSchema.yAA.$Boolean;
import org.w3.www._2001.XMLSchema.yAA.$NonNegativeInteger;
import org.w3.www._2001.XMLSchema.yAA.$String;

abstract class Referrer<T extends Referrer<?>> extends Model implements Declarer {
  private static final ThreadLocal<Integer> count = ThreadLocal.withInitial(() -> 0);
  private static final ThreadLocal<Set<Member>> visited = ThreadLocal.withInitial(HashSet::new);

  static $FieldBinding.Field$ getField(final IdentityHashMap<$AnyType<?>,$FieldBinding> xsbToBinding, final Binding xsb) {
    if (xsbToBinding == null)
      return null;

    final $FieldBinding binding = xsbToBinding.get(xsb);
    return binding == null ? null : binding.getField$();
  }

  static Registry.Type getGreatestCommonSuperType(final Member[] members) {
    final int i$ = assertPositive(members.length);

    boolean isObjectModel = true;
    final Set<Member> visited = Referrer.visited.get();
    try {
      count.set(count.get() + 1);
      int start = 0;
      final Member member0 = members[0];
      isObjectModel &= member0 instanceof ObjectModel;
      if (!visited.add(member0))
        start = 1;

      if (i$ == start)
        return null;

      int i = start + 1;
      if (i$ == i)
        return members[start].type();

      Registry.Type gct = members[start].type();
      do { // [A]
        final Member member = members[i];
        isObjectModel &= member instanceof ObjectModel;
        gct = getGreatestCommonSuperType(gct, member);
      }
      while (++i < i$);
      return gct.filterJxObjectType(isObjectModel);
    }
    finally {
      final int count = Referrer.count.get() - 1;
      Referrer.count.set(count);
      if (count == 0)
        visited.clear();
    }
  }

  private static Registry.Type getGreatestCommonSuperType(final Registry.Type gct, final Member member) {
    final Set<Member> visited = Referrer.visited.get();
    if (visited.contains(member))
      return gct;

    visited.add(member);
    return gct.getGreatestCommonSuperType(member.type());
  }

  private final Registry.Type type;

  Referrer(final Registry registry, final Declarer declarer, final $Documented.Doc$ doc, final $AnySimpleType<?> name, final $Boolean nullable, final $String use, final Registry.Type type, final $FieldIdentifier fieldName, final Bind.Type typeBinding) {
    super(registry, declarer, Id.named(type), doc, name, nullable, use, fieldName, typeBinding);
    this.type = type;
  }

  Referrer(final Registry registry, final Declarer declarer, final $Documented.Doc$ doc, final $Boolean nullable, final $NonNegativeInteger minOccurs, final $MaxOccurs maxOccurs, final Registry.Type type) {
    super(registry, declarer, Id.named(type), doc, nullable, minOccurs, maxOccurs, null);
    this.type = type;
  }

  Referrer(final Registry registry, final Declarer declarer, final Registry.Type type, final $Documented.Doc$ doc, final String name, final Bind.Type typeBinding) {
    super(registry, declarer, Id.named(type), doc, name, typeBinding);
    this.type = type;
  }

  Referrer(final Registry registry, final Declarer declarer, final Id id, final Registry.Type type) {
    super(registry, declarer, id, null, null, null, null);
    this.type = type;
  }

  Referrer(final Registry registry, final Declarer declarer, final Boolean nullable, final Use use, final String fieldName, final Registry.Type type, final Bind.Type typeBinding) {
    super(registry, declarer, Id.named(type), nullable, use, fieldName, typeBinding);
    this.type = type;
  }

  private Member getReference(final Id id, final boolean isFromSchema) {
    if (registry.isPending(id))
      return new Deferred<>(isFromSchema, null, null, () -> registry.getModel(id));

    final Model model = registry.getModel(id);
    if (model == null)
      return new Deferred<>(isFromSchema, null, null, () -> registry.getModel(id));

    return model;
  }

  final Member getReference(final $String type) {
    return type == null ? null : getReference(Id.named(type), true);
  }

  @Override
  public final Registry.Type classType() {
    return type;
  }

  @Override
  Class<? extends Annotation> typeAnnotation() {
    throw new UnsupportedOperationException();
  }

  abstract ArrayList<AnnotationType> getClassAnnotation();
  abstract String toSource(Settings settings);
  abstract void resolveReferences();
  abstract void resolveOverrides();
}