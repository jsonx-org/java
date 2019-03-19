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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.openjax.jsonx.runtime.Use;
import org.openjax.jsonx.schema_0_9_8.xL4gluGCXYYJc.$MaxOccurs;
import org.openjax.standard.util.Classes;
import org.openjax.standard.xml.api.XmlElement;
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

  Model(final Registry registry, final Id id, final yAA.$AnySimpleType name, final yAA.$Boolean nullable, final $String use) {
    super(registry, id, name, nullable, use);
  }

  Model(final Registry registry, final Id id, final $Boolean nullable, final $NonNegativeInteger minOccurs, final $MaxOccurs maxOccurs) {
    super(registry, id, nullable, minOccurs, maxOccurs);
  }

  Model(final Registry registry, final Id id) {
    super(registry, id, null, null, null, null, null);
  }

  Model(final Registry registry, final Id id, final Boolean nullable, final Use use) {
    super(registry, id, null, nullable, use, null, null);
  }

  String sortKey() {
    return getClass().getSimpleName() + id;
  }

  @Override
  public final int compareTo(final Model o) {
    return sortKey().compareTo(o.sortKey());
  }

  @Override
  Map<String,Object> toAttributes(final Element owner, final String packageName) {
    final Map<String,Object> attributes = super.toAttributes(owner, packageName);
    if (owner instanceof ObjectModel)
      attributes.put("xsi:type", elementName());

    return attributes;
  }

  @Override
  XmlElement toXml(final Settings settings, final Element owner, final String packageName) {
    final Map<String,Object> attributes = toAttributes(owner, packageName);
    return new XmlElement(owner instanceof ObjectModel ? "property" : owner instanceof ArrayModel ? elementName() : (elementName() + "Type"), attributes, null);
  }

  @Override
  Map<String,Object> toJson(final Settings settings, final Element owner, final String packageName) {
    final Map<String,Object> properties = new LinkedHashMap<>();
    properties.put("class", elementName());

    final Map<String,Object> attributes = toAttributes(owner, packageName);
    attributes.remove("xsi:type");

    properties.putAll(attributes);
    return properties;
  }
}