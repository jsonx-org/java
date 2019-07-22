/* Copyright (c) 2017 JSONx
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

import org.jsonx.www.schema_0_3.xL0gluGCXAA.$Documented;
import org.openjax.xml.api.XmlElement;

abstract class Element {
  final String doc;

  Element(final $Documented.Doc$ doc) {
    this.doc = doc == null || doc.text() == null || doc.text().length() == 0 ? null : doc.text();
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
  Map<String,Object> toAttributes(final Element owner, final String packageName) {
    final AttributeMap attributes = new AttributeMap();
    if (doc != null)
      attributes.put("doc", doc);

    return attributes;
  }

  abstract XmlElement toXml(Settings settings, Element owner, String packageName);
  abstract Object toJson(Settings settings, Element owner, String packageName);
}