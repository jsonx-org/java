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

import org.fastjax.xml.datatypes_1_0_4.xL3gluGCXYYJc.$JavaIdentifier;
import org.libx4j.jsonx.jsonx_0_9_8.xL2gluGCXYYJc.$MaxCardinality;
import org.libx4j.jsonx.runtime.Use;
import org.w3.www._2001.XMLSchema.yAA.$Boolean;
import org.w3.www._2001.XMLSchema.yAA.$NonNegativeInteger;
import org.w3.www._2001.XMLSchema.yAA.$String;

abstract class Model extends Member {
  public Model(final Registry registry, final $JavaIdentifier name, final $String use) {
    super(registry, name, use);
  }

  public Model(final Registry registry, final $Boolean nullable, final $NonNegativeInteger minOccurs, final $MaxCardinality maxOccurs) {
    super(registry, nullable, minOccurs, maxOccurs);
  }

  public Model(final Registry registry) {
    super(registry, null, null, null, null, null);
  }

  public Model(final Registry registry, final Boolean nullable, final Use use) {
    super(registry, null, nullable, use, null, null);
  }

  @Override
  protected org.fastjax.xml.Element toXml(final Settings settings, final Element owner, final String packageName) {
    final Map<String,String> attributes;
    if (!(owner instanceof ObjectModel)) {
      attributes = toXmlAttributes(owner, packageName);
      return new org.fastjax.xml.Element(elementName(), attributes, null);
    }

    if (registry.isTemplateReference(this, settings)) {
      attributes = super.toXmlAttributes(owner, packageName);
      attributes.put("xsi:type", "template");
      attributes.put("reference", id().toString());
    }
    else {
      attributes = toXmlAttributes(owner, packageName);
      attributes.put("xsi:type", elementName());
    }

    return new org.fastjax.xml.Element("property", attributes, null);
  }
}