/* Copyright (c) 2017 Jsonx
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

import java.util.Map;

import org.openjax.ext.xml.api.XmlElement;

abstract class Element {
  /**
   * Intended to be overridden by each concrete subclass, this method returns a
   * {@code Map<String,String>} of name/value attributes that define the spec of
   * {@code this} element's {@code *Property} or {@code *Element} declaration.
   *
   * @param owner The {@code Element} that owns (contains) {@code this} element.
   * @param prefix The prefix for key names.
   * @param packageName The package name declared in the schema element.
   * @return The non-null {@code Map<String,String>} of name/value attributes.
   */
  Map<String,Object> toAttributes(final Element owner, final String prefix, final String packageName) {
    return new AttributeMap();
  }

  abstract XmlElement toXml(Settings settings, Element owner, final String prefix, String packageName);
  abstract Object toJson(Settings settings, Element owner, String packageName);
}