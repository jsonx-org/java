/* Copyright (c) 2019 JSONx
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
import java.util.function.Supplier;

import org.openjax.xml.api.XmlElement;

final class Deferred<T extends Member> extends Member {
  private final Supplier<? extends T> supplier;

  Deferred(final boolean isFromSchema, final String name, final String fieldName, final Supplier<? extends T> supplier) {
    super(null, null, isFromSchema, null, null, name, null, null, null, null, fieldName, null);
    this.supplier = supplier;
  }

  T resolve() {
    return supplier.get();
  }

  @Override
  Registry.Type typeDefault(final boolean primitive) {
    throw new UnsupportedOperationException();
  }

  @Override
  String isValid(final Bind.Type typeBinding) {
    throw new UnsupportedOperationException();
  }

  @Override
  String elementName() {
    throw new UnsupportedOperationException();
  }

  @Override
  Class<?> defaultClass() {
    throw new UnsupportedOperationException();
  }

  @Override
  Class<? extends Annotation> propertyAnnotation() {
    throw new UnsupportedOperationException();
  }

  @Override
  Class<? extends Annotation> elementAnnotation() {
    throw new UnsupportedOperationException();
  }

  @Override
  Class<? extends Annotation> typeAnnotation() {
    throw new UnsupportedOperationException();
  }

  @Override
  XmlElement toXml(final Element owner, final String packageName, final JsonPath.Cursor cursor, final StrictPropertyMap<AttributeMap> pathToBinding) {
    throw new UnsupportedOperationException();
  }

  @Override
  StrictPropertyMap<Object> toJson(final Element owner, final String packageName, final JsonPath.Cursor cursor, final StrictPropertyMap<AttributeMap> pathToBinding) {
    throw new UnsupportedOperationException();
  }
}