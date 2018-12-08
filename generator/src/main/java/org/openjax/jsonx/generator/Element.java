/* Copyright (c) 2017 OpenJAX
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

import java.util.Map;
import java.util.Set;

abstract class Element {
  /**
   * Intended to be overridden by each concrete subclass, this method collects
   * all {@code Registry.Type} declarations of elements that are members of
   * {@code this} element.
   *
   * @param types The {@code Set} into which the {@code Registry.Type}
   *          declarations must be added.
   */
  void getDeclaredTypes(final Set<Registry.Type> types) {
  }

  /**
   * Intended to be overridden by each concrete subclass, this method returns a
   * {@code Map<String,String>} of name/value attributes that define the spec of
   * {@code this} element's {@code *Property} or {@code *Element} declaration.
   *
   * @param owner The {@code Element} that owns (contains) {@code this} element.
   * @param packageName The package name declared in the schema element.
   * @return The non-null {@code Map<String,String>} of name/value attributes.
   */
  Map<String,String> toXmlAttributes(final Element owner, final String packageName) {
    return new AttributeMap();
  }

  abstract org.fastjax.xml.Element toXml(final Settings settings, final Element owner, final String packageName);
}