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

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import org.jsonx.www.binding_0_4.xL1gluGCXAA.$FieldIdentifier;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$Documented;
import org.jsonx.www.schema_0_4.xL0gluGCXAA.$MaxOccurs;
import org.libj.lang.Classes;
import org.openjax.xml.api.XmlElement;
import org.w3.www._2001.XMLSchema.yAA;

abstract class Model extends Member implements Comparable<Model> {
  static Class<?> toClass(final Type type) {
    if (type instanceof Class)
      return (Class<?>)type;

    if (type instanceof WildcardType)
      return (Class<?>)((WildcardType)type).getUpperBounds()[0];

    return null;
  }

  static boolean isAssignable(final Method getMethod, final boolean canWrap, final Class<?> cls, final boolean isRegex, final boolean nullable, final Use use) {
    final Class<?> type = canWrap && getMethod.getReturnType().isPrimitive() ? Classes.box(getMethod.getReturnType()) : getMethod.getReturnType();
    if (isRegex) {
      if (!Map.class.isAssignableFrom(type))
        return false;

      final Type genericType = getMethod.getGenericReturnType();
      if (!(genericType instanceof ParameterizedType))
        return false;

      final Type[] genericTypes = ((ParameterizedType)genericType).getActualTypeArguments();
      if (genericTypes.length != 2)
        return false;

      if (!(genericTypes[1] instanceof ParameterizedType))
        return cls == null || toClass(genericTypes[1]).isAssignableFrom(cls);

      final Class<?> valueType = (Class<?>)((ParameterizedType)genericTypes[1]).getRawType();
      if (valueType != Optional.class)
        return false;

      final Type[] args = ((ParameterizedType)genericTypes[1]).getActualTypeArguments();
      return args.length == 1 && (cls == null || cls.isAssignableFrom(args[0] instanceof ParameterizedType ? (Class<?>)((ParameterizedType)args[0]).getRawType() : toClass(args[0])));
    }

    if (use != Use.OPTIONAL || !nullable)
      return cls == null || cls.isAssignableFrom(type);

    if (type != Optional.class)
      return false;

    final Class<?>[] genericTypes = Classes.getGenericParameters(getMethod);
    if (genericTypes.length == 0 || genericTypes[0] == null)
      return false;

    return cls == null || cls.isAssignableFrom(genericTypes[0]);
  }

  Model(final Registry registry, final Declarer declarer, final Id id, final $Documented.Doc$ doc, final yAA.$AnySimpleType<?> name, final yAA.$Boolean nullable, final yAA.$String use, final $FieldIdentifier fieldName, final Bind.Type typeBinding) {
    super(registry, declarer, true, id, doc, name, nullable, use, fieldName, typeBinding);
  }

  Model(final Registry registry, final Declarer declarer, final Id id, final $Documented.Doc$ doc, final yAA.$Boolean nullable, final yAA.$NonNegativeInteger minOccurs, final $MaxOccurs maxOccurs, final Bind.Type typeBinding) {
    super(registry, declarer, true, id, doc, nullable, minOccurs, maxOccurs, typeBinding);
  }

  Model(final Registry registry, final Declarer declarer, final Id id, final $Documented.Doc$ doc, final String name, final Bind.Type typeBinding) {
    super(registry, declarer, true, id, doc, name, null, null, null, null, null, typeBinding);
  }

  Model(final Registry registry, final Declarer declarer, final Id id, final Boolean nullable, final Use use, final String fieldName, final Bind.Type typeBinding) {
    super(registry, declarer, false, id, null, null, nullable, use, null, null, fieldName, typeBinding);
  }

  String sortKey() {
    return getClass().getSimpleName() + id();
  }

  @Override
  public final int compareTo(final Model o) {
    return sortKey().compareTo(o.sortKey());
  }

  @Override
  Map<String,Object> toXmlAttributes(final Element owner, final String packageName) {
    final Map<String,Object> attributes = super.toXmlAttributes(owner, packageName);
    if (owner instanceof ObjectModel)
      attributes.put("xsi:type", elementName());

    return attributes;
  }

  @Override
  XmlElement toXml(final Element owner, final String packageName, final JsonPath.Cursor cursor, final HashMap<String,Map<String,Object>> pathToBinding) {
    final Map<String,Object> attributes = toXmlAttributes(owner, packageName);
    cursor.pushName((String)attributes.get("name"));
    final XmlElement element = new XmlElement(owner instanceof ObjectModel ? "property" : elementName(), attributes, null);
    final Map<String,Object> bindingAttributes = Bind.toXmlAttributes(elementName(), owner, typeBinding, fieldBinding);
    if (bindingAttributes != null)
      pathToBinding.put(cursor.toString(), bindingAttributes);

    return element;
  }

  @Override
  Map<String,Object> toJson(final Element owner, final String packageName, final JsonPath.Cursor cursor, final HashMap<String,Map<String,Object>> pathToBinding) {
    final Map<String,Object> properties = new LinkedHashMap<>();
    properties.put("jx:type", elementName());

    final Map<String,Object> attributes = toXmlAttributes(owner, packageName);
    cursor.pushName((String)attributes.get("name"));
    attributes.remove(nameName());
    attributes.remove("xsi:type");

    properties.putAll(attributes);
    final Map<String,Object> bindingAttributes = Bind.toXmlAttributes(elementName(), owner, typeBinding, fieldBinding);
    if (bindingAttributes != null)
      pathToBinding.put(cursor.toString(), bindingAttributes);

    return properties;
  }
}