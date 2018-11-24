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

package org.openjax.jsonx.runtime;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ArraySpec extends Spec {
  private final Annotation[] annotations;
  private final IdToElement idToElement = new IdToElement();

  public ArraySpec(final ArrayProperty property, final Field field) {
    super(field, property.name(), property.use());
    final int[] elementIds = JsonxUtil.digest(field, idToElement);
    this.annotations = idToElement.get(elementIds);
  }

  public Annotation[] getAnnotations() {
    return annotations;
  }

  public IdToElement getIdToElement() {
    return this.idToElement;
  }

  @Override
  public String elementName() {
    return "array";
  }
}