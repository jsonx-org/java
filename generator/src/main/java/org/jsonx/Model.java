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

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.jsonx.www.schema_0_2_3.xL0gluGCXYYJc.$Documented;
import org.jsonx.www.schema_0_2_3.xL0gluGCXYYJc.$MaxOccurs;
import org.libj.util.Classes;
import org.openjax.xml.api.XmlElement;
import org.w3.www._2001.XMLSchema.yAA.$AnySimpleType;
import org.w3.www._2001.XMLSchema.yAA.$Boolean;
import org.w3.www._2001.XMLSchema.yAA.$NonNegativeInteger;
import org.w3.www._2001.XMLSchema.yAA.$String;

abstract class Model extends Member implements Comparable<Model> {
  static boolean isAssignable(final Field field, final Class<?> cls, final boolean isRegex, final boolean nullable, final Use use) {
    if (isRegex) {
      if (!Map.class.isAssignableFrom(field.getType()))
        return false;

      final Type genericType = field.getGenericType();
      if (!(genericType instanceof ParameterizedType))
        return false;

      final Type[] genericTypes = ((ParameterizedType)genericType).getActualTypeArguments();
      if (genericTypes.length != 2)
        return false;

      if (!(genericTypes[1] instanceof ParameterizedType))
        return cls.isAssignableFrom((Class<?>)genericTypes[1]);

      final Class<?> valueType = (Class<?>)((ParameterizedType)genericTypes[1]).getRawType();
      if (!Optional.class.isAssignableFrom(valueType))
        return false;

      final Type[] args = ((ParameterizedType)genericTypes[1]).getActualTypeArguments();
      return args.length == 1 && cls.isAssignableFrom(args[0] instanceof ParameterizedType ? (Class<?>)((ParameterizedType)args[0]).getRawType() : (Class<?>)args[0]);
    }

    if (use != Use.OPTIONAL || !nullable)
      return cls.isAssignableFrom(field.getType());

    if (!Optional.class.isAssignableFrom(field.getType()))
      return false;

    final Class<?>[] genericTypes = Classes.getGenericClasses(field);
    if (genericTypes.length == 0 || genericTypes[0] == null)
      return false;

    return cls.isAssignableFrom(genericTypes[0]);
  }

  Model(final Registry registry, final Declarer declarer, final Id id, final $Documented.Doc$ doc, final $AnySimpleType name, final $Boolean nullable, final $String use) {
    super(registry, declarer, id, doc, name, nullable, use);
  }

  Model(final Registry registry, final Declarer declarer, final Id id, final $Documented.Doc$ doc, final $Boolean nullable, final $NonNegativeInteger minOccurs, final $MaxOccurs maxOccurs) {
    super(registry, declarer, id, doc, nullable, minOccurs, maxOccurs);
  }

  Model(final Registry registry, final Declarer declarer, final Id id, final $Documented.Doc$ doc) {
    super(registry, declarer, id, doc, null, null, null, null, null);
  }

  Model(final Registry registry, final Declarer declarer, final Id id, final Boolean nullable, final Use use) {
    super(registry, declarer, id, null, null, nullable, use, null, null);
  }

  String sortKey() {
    return getClass().getSimpleName() + id;
  }

  @Override
  public final int compareTo(final Model o) {
    return sortKey().compareTo(o.sortKey());
  }

  @Override
  Map<String,Object> toAttributes(final Element owner, final String prefix, final String packageName) {
    final Map<String,Object> attributes = super.toAttributes(owner, prefix, packageName);
    if (owner instanceof ObjectModel)
      attributes.put("xsi:type", elementName());

    return attributes;
  }

  @Override
  XmlElement toXml(final Settings settings, final Element owner, final String prefix, final String packageName) {
    final Map<String,Object> attributes = toAttributes(owner, prefix, packageName);
    return new XmlElement(owner instanceof ObjectModel ? "property" : elementName(), attributes, null);
  }

  @Override
  Map<String,Object> toJson(final Settings settings, final Element owner, final String packageName) {
    final Map<String,Object> properties = new LinkedHashMap<>();
    properties.put("jsd:class", elementName());

    final Map<String,Object> attributes = toAttributes(owner, "jsd:", packageName);
    attributes.remove(nameName());
    attributes.remove("xsi:type");

    properties.putAll(attributes);
    return properties;
  }
}