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

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

import org.fastjax.util.Classes;
import org.fastjax.xml.datatypes_0_9_2.xL5gluGCXYYJc.$Identifier;
import org.openjax.jsonx.jsonx_0_9_8.xL3gluGCXYYJc.$MaxOccurs;
import org.openjax.jsonx.runtime.Use;
import org.w3.www._2001.XMLSchema.yAA;
import org.w3.www._2001.XMLSchema.yAA.$Boolean;
import org.w3.www._2001.XMLSchema.yAA.$NonNegativeInteger;
import org.w3.www._2001.XMLSchema.yAA.$String;

abstract class Model extends Member implements Comparable<Model> {
  static boolean isAssignable(final Field field, final Class<?> cls, final boolean nullable, final Use use) {
    if (use != Use.OPTIONAL || !nullable)
      return cls.isAssignableFrom(field.getType());

    if (!Optional.class.isAssignableFrom(field.getType()))
      return false;

    final Class<?>[] genericTypes = Classes.getGenericTypes(field);
    if (genericTypes.length == 0 || genericTypes[0] == null)
      return false;

    return cls.isAssignableFrom(genericTypes[0]);
  }

  Model(final Registry registry, final $Identifier name, final yAA.$Boolean nullable, final $String use) {
    super(registry, name, nullable, use);
  }

  Model(final Registry registry, final $Boolean nullable, final $NonNegativeInteger minOccurs, final $MaxOccurs maxOccurs) {
    super(registry, nullable, minOccurs, maxOccurs);
  }

  Model(final Registry registry) {
    super(registry, null, null, null, null, null);
  }

  Model(final Registry registry, final Boolean nullable, final Use use) {
    super(registry, null, nullable, use, null, null);
  }

  String sortKey() {
    return getClass().getSimpleName() + id();
  }

  @Override
  public final int compareTo(final Model o) {
    return sortKey().compareTo(o.sortKey());
  }

  @Override
  org.fastjax.xml.Element toXml(final Settings settings, final Element owner, final String packageName) {
    final Map<String,String> attributes;
    if (!(owner instanceof ObjectModel)) {
      attributes = toXmlAttributes(owner, packageName);
      return new org.fastjax.xml.Element(owner instanceof ArrayModel ? elementName() : (elementName() + "Type"), attributes, null);
    }

//    if (registry.isTemplateReference(this, settings)) {
//      attributes = super.toXmlAttributes(owner, packageName);
//      attributes.put("xsi:type", "template");
//      attributes.put("reference", id().toString());
//    }
//    else {
      attributes = toXmlAttributes(owner, packageName);
      attributes.put("xsi:type", elementName());
//    }

    return new org.fastjax.xml.Element("property", attributes, null);
  }
}