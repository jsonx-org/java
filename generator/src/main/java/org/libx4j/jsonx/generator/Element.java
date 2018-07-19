/* Copyright (c) 2017 lib4j
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

package org.libx4j.jsonx.generator;

import java.util.Map;
import java.util.Set;

abstract class Element {
  /**
   * Intended to be overridden by each concrete subclass, this method collects
   * all <code>Registry.Type</code> declarations of elements that are members
   * of <code>this</code> element.
   *
   * @param types The <code>Set</code> into which the <code>Registry.Type</code>
   *          declarations must be added.
   */
  protected void getDeclaredTypes(final Set<Registry.Type> types) {
  }

  /**
   * Intended to be overridden by each concrete subclass, this method returns a
   * <code>Map<String,String></code> of name/value attributes that define the
   * spec of <code>this</code> element's <code>*Property</code> or <code>*Element</code>
   * declaration.
   *
   * @param owner
   * @param packageName
   * @return The non-null <code>Map<String,String></code> of name/value attributes.
   */
  protected Map<String,String> toAnnotationAttributes(final Element owner, final String packageName) {
    return new AttributeMap();
  }

  protected abstract org.lib4j.xml.Element toXml(final Settings settings, final Element owner, final String packageName);
}