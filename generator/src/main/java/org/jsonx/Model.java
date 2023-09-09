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
    return type instanceof Class ? (Class<?>)type : type instanceof WildcardType ? (Class<?>)((WildcardType)type).getUpperBounds()[0] : null;
  }

  static boolean isAssignable(final Method getMethod, final boolean canWrap, final Class<?> cls, final boolean isRegex, final boolean nullable, final Use use) {
    final Class<?> returnType = getMethod.getReturnType();
    final Class<?> type = canWrap && returnType.isPrimitive() ? Classes.box(returnType) : returnType;
    if (isRegex) {
      if (!Map.class.isAssignableFrom(type))
        return false;

      final Type genericReturnType = getMethod.getGenericReturnType();
      if (!(genericReturnType instanceof ParameterizedType))
        return false;

      final Type[] genericTypes = ((ParameterizedType)genericReturnType).getActualTypeArguments();
      if (genericTypes.length != 2)
        return false;

      final Type genericType = genericTypes[1];
      if (!(genericType instanceof ParameterizedType))
        return cls == null || toClass(genericType).isAssignableFrom(cls);

      final ParameterizedType parameterizedType = (ParameterizedType)genericType;
      if (parameterizedType.getRawType() != Optional.class)
        return false;

      final Type[] args = parameterizedType.getActualTypeArguments();
      final Type arg;
      return args.length == 1 && (cls == null || cls.isAssignableFrom((arg = args[0]) instanceof ParameterizedType ? (Class<?>)((ParameterizedType)arg).getRawType() : toClass(arg)));
    }

    if (use != Use.OPTIONAL || !nullable)
      return cls == null || cls.isAssignableFrom(type);

    if (type != Optional.class)
      return false;

    final Class<?>[] genericTypes = Classes.getGenericParameters(getMethod);
    return genericTypes.length != 0 && genericTypes[0] != null && (cls == null || cls.isAssignableFrom(genericTypes[0]));
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
  XmlElement toXml(final Element owner, final String packageName, final JsonPath.Cursor cursor, final PropertyMap<AttributeMap> pathToBinding) {
    final AttributeMap attributes = toSchemaAttributes(owner, packageName, false);
    cursor.pushName((String)attributes.get("name"));

    final XmlElement element = new XmlElement(owner instanceof ObjectModel ? "property" : elementName(), attributes, null);
    final AttributeMap bindingAttributes = Bind.toBindingAttributes(elementName(), owner, typeBinding, fieldBinding, attributes, false);
    if (bindingAttributes != null)
      pathToBinding.put(cursor.toString(), bindingAttributes);

    return element;
  }

  @Override
  PropertyMap<Object> toJson(final Element owner, final String packageName, final JsonPath.Cursor cursor, final PropertyMap<AttributeMap> pathToBinding) {
    final PropertyMap<Object> properties = new PropertyMap<>();
    properties.put("@", elementName());

    final AttributeMap attributes = toSchemaAttributes(owner, packageName, true);
    cursor.pushName((String)attributes.get("name"));
    final AttributeMap bindingAttributes = Bind.toBindingAttributes(elementName(), owner, typeBinding, fieldBinding, attributes, true);
    if (bindingAttributes != null)
      pathToBinding.put(cursor.toString(), bindingAttributes);

    attributes.remove(nameName());

    properties.putAll(attributes);
    return properties;
  }

  @Override
  AttributeMap toSchemaAttributes(final Element owner, final String packageName, final boolean toJx) {
    final AttributeMap attributes = super.toSchemaAttributes(owner, packageName, toJx);
    if (owner instanceof ObjectModel)
      attributes.put(toJx ? "@" : "xsi:type", elementName());

    return attributes;
  }
}