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

import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Documented;
import org.openjax.xml.api.XmlElement;

abstract class Element {
  private final String name;
  final String doc;

  Element(final String name, final $Documented.Doc$ doc) {
    this.name = name;
    String str;
    this.doc = doc == null || (str = doc.text()) == null || (str = str.trim()).length() == 0 ? null : str;
  }

  abstract XmlElement toXml(Element owner, String packageName, JsonPath.Cursor cursor, StrictPropertyMap<AttributeMap> pathToBinding);
  abstract Object toJson(Element owner, String packageName, JsonPath.Cursor cursor, StrictPropertyMap<AttributeMap> pathToBinding);

  static String toJx(final boolean toJx, final String name) {
    return toJx ? "@" + name : name;
  }

  /**
   * Intended to be overridden by each concrete subclass, this method returns a {@code Map<String,String>} of name/value attributes
   * that define the spec of {@code this} element's {@code *Property} or {@code *Element} declaration.
   *
   * @param owner The {@link Element} that owns (contains) {@code this} element.
   * @param packageName The package name declared in the schema element.
   * @return The non-null {@code Map<String,String>} of name/value attributes.
   */
  AttributeMap toSchemaAttributes(final Element owner, final String packageName, final boolean toJx) {
    final AttributeMap attributes = new AttributeMap();
    if (doc != null)
      attributes.put(toJx(toJx, "doc"), doc);

    return attributes;
  }

  public final String name() {
    return name;
  }
}