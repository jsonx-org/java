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

import java.util.HashSet;
import java.util.Set;

import org.lib4j.xml.Attribute;

abstract class Element {
  protected void getDeclaredTypes(final Set<Registry.Type> types) {
  }

  protected abstract String toJSON(final String packageName);

  protected Set<Attribute> toAttributes(final Element owner, final String packageName) {
    return new HashSet<Attribute>();
  }

  protected abstract org.lib4j.xml.Element toJSONX(final Element owner, final String packageName);

  protected abstract String toJSON();
  protected abstract org.lib4j.xml.Element toJSONX();
}