/* Copyright (c) 2018 OpenJAX
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

package org.openjax.jsonx.runtime;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import org.fastjax.util.Classes;
import org.fastjax.util.FastArrays;
import org.fastjax.util.JavaIdentifiers;
import org.fastjax.util.function.BiObjBiIntConsumer;
import org.openjax.jsonx.runtime.ArrayValidator.Relation;
import org.openjax.jsonx.runtime.ArrayValidator.Relations;

public class JxEncoder {
  private final int indent;
  private final String comma;
  private final String colon;
  private final boolean validate;

  public JxEncoder(final int indent) {
    this(indent, true);
  }

  public JxEncoder() {
    this(0, true);
  }

  JxEncoder(final int indent, final boolean validate) {
    if (indent < 0)
      throw new IllegalArgumentException("spaces < 0: " + indent);

    this.indent = indent;
    this.validate = validate;
    if (indent == 0) {
      this.comma = ",";
      this.colon = ":";
    }
    else {
      this.comma = ", ";
      this.colon = ": ";
    }
  }

  private static Object getValue(final Object object, final String propertyName) {
    final Method method = JxUtil.getGetMethod(object.getClass(), propertyName);
    try {
      if (method == null)
        throw new ValidationException("Method get" + JavaIdentifiers.toClassCase(propertyName) + "() does not exist for " + object.getClass().getSimpleName() + "." + propertyName);

      return method.invoke(object);
    }
    catch (final IllegalAccessException | InvocationTargetException e) {
      throw new EncodeException(e);
    }
  }

  private void encodeNonArray(final Field field, final Annotation annotation, final Object object, final BiObjBiIntConsumer<Field,Relations> onFieldEncode, final StringBuilder builder, final int depth) {
    if (field == null && object == null) {
      if (validate && !JxUtil.isNullable(annotation))
        throw new EncodeException("field is not nullable");

      builder.append("null");
    }
    else {
      final Class<?> type;
      final boolean isOptional;
      if (field == null) {
        isOptional = false;
        type = object.getClass();
      }
      else {
        type = (isOptional = Optional.class.isAssignableFrom(field.getType())) ? Classes.getGenericTypes(field)[0] : field.getType();
      }

      final Object value = object == null ? null : isOptional ? ((Optional<?>)object).orElse(null) : object;
      if (String.class.isAssignableFrom(type)) {
        builder.append(StringCodec.encode(annotation, (String)value, validate));
      }
      else if (Boolean.class.isAssignableFrom(type)) {
        builder.append(BooleanCodec.encode(annotation, (Boolean)value));
      }
      else if (Number.class.isAssignableFrom(type)) {
        builder.append(NumberCodec.encode(annotation, (Number)value, validate));
      }
      else if (JxObject.class.isAssignableFrom(type)) {
        marshal((JxObject)value, onFieldEncode, builder, depth + 1);
      }
      else {
        throw new UnsupportedOperationException("Unsupported object type: " + type.getName());
      }
    }
  }

  @SuppressWarnings("unchecked")
  private void encodeProperty(final Field field, final Annotation annotation, final Object object, final BiObjBiIntConsumer<Field,Relations> onFieldEncode, final StringBuilder builder, final int depth) {
    try {
      boolean isOptional = false;
      if (object instanceof List || (isOptional = object instanceof Optional) && List.class.isAssignableFrom(Classes.getGenericTypes(field)[0])) {
        final Relations relations = ArrayCodec.encode(field, isOptional ? ((Optional<List<Object>>)object).orElse(null) : (List<Object>)object, validate);
        if (onFieldEncode != null)
          onFieldEncode.accept(field, relations, -1, -1);

        encodeArray(relations, onFieldEncode, builder, depth);
      }
      else {
        encodeNonArray(field, annotation, object, onFieldEncode, builder, depth);
      }
    }
    catch (final EncodeException | ValidationException e) {
      throw e;
    }
    catch (final Exception e) {
      throw new ValidationException("Invalid field: " + JxUtil.getFullyQualifiedFieldName(field), e);
    }
  }

  private void encodeArray(final Relations relations, final BiObjBiIntConsumer<Field,Relations> onFieldEncode, final StringBuilder builder, final int depth) {
    builder.append('[');
    for (int i = 0; i < relations.size(); ++i) {
      if (i > 0)
        builder.append(comma);

      final Relation relation = relations.get(i);
      if (relation.annotation instanceof ArrayElement)
        encodeArray((Relations)relation.member, onFieldEncode, builder, depth);
      else
        encodeNonArray(null, relation.annotation, relation.member, onFieldEncode, builder, depth);
    }

    builder.append(']');
  }

  private void marshal(final JxObject object, final BiObjBiIntConsumer<Field,Relations> onFieldEncode, final StringBuilder builder, final int depth) {
    builder.append('{');
    boolean hasProperties = false;
    final Field[] fields = Classes.getDeclaredFieldsDeep(object.getClass());
    for (int i = 0; i < fields.length; ++i) {
      final Field field = fields[i];
      Annotation annotation = null;
      String name = null;
      boolean nullable = false;
      Use use = null;
      final Annotation[] annotations = field.getAnnotations();
      for (int j = 0; j < annotations.length; ++j) {
        annotation = annotations[j];
        if (annotation instanceof ArrayProperty) {
          final ArrayProperty property = (ArrayProperty)annotation;
          name = JxUtil.getName(property.name(), field);
          nullable = property.nullable();
          use = property.use();
          break;
        }

        if (annotation instanceof ObjectProperty) {
          final ObjectProperty property = (ObjectProperty)annotation;
          name = JxUtil.getName(property.name(), field);
          nullable = property.nullable();
          use = property.use();
          break;
        }

        if (annotation instanceof BooleanProperty) {
          final BooleanProperty property = (BooleanProperty)annotation;
          name = JxUtil.getName(property.name(), field);
          nullable = property.nullable();
          use = property.use();
          break;
        }

        if (annotation instanceof NumberProperty) {
          final NumberProperty property = (NumberProperty)annotation;
          name = JxUtil.getName(property.name(), field);
          nullable = property.nullable();
          use = property.use();
          break;
        }

        if (annotation instanceof StringProperty) {
          final StringProperty property = (StringProperty)annotation;
          name = JxUtil.getName(property.name(), field);
          nullable = property.nullable();
          use = property.use();
          break;
        }
      }

      if (nullable && use == Use.OPTIONAL && !Optional.class.equals(field.getType()))
        throw new ValidationException("Invalid field: " + JxUtil.getFullyQualifiedFieldName(field) + ": Field with (nullable=true & use=Use.OPTIONAL) must be of type: " + Optional.class.getName());

      if (name == null)
        continue;

      final Object value = getValue(object, name);
      if (value != null || nullable && use == Use.REQUIRED) {
        if (hasProperties)
          builder.append(',');

        if (indent > 0)
          builder.append('\n').append(FastArrays.createRepeat(' ', depth * 2));

        builder.append('"').append(name).append('"').append(colon);
        final int start = builder.length();
        if (value == null || Optional.empty().equals(value))
          builder.append("null");
        else
          encodeProperty(field, annotation, value, onFieldEncode, builder, depth);

        if (onFieldEncode != null)
          onFieldEncode.accept(field, null, start, builder.length());

        hasProperties = true;
      }
      else if (validate && use == Use.REQUIRED) {
        throw new EncodeException(object.getClass().getName() + "#" + name + " is required");
      }
      else if (onFieldEncode != null) {
        onFieldEncode.accept(field, null, -1, -1);
      }
    }

    if (indent > 0)
      builder.append('\n').append(FastArrays.createRepeat(' ', (depth - 1) * 2));

    builder.append('}');
  }

  String encode(final List<?> list, final Class<? extends Annotation> arrayAnnotationType, final BiObjBiIntConsumer<Field,Relations> onFieldEncode) {
    final StringBuilder builder = new StringBuilder();
    final Relations relations = new Relations();
    final String error = ArrayValidator.validate(arrayAnnotationType, list, relations, validate, null);
    if (validate && error != null)
      throw new EncodeException(error);

    encodeArray(relations, onFieldEncode, builder, 0);
    return builder.toString();
  }

  public String marshal(final List<?> list, final Class<? extends Annotation> arrayAnnotationType) {
    return encode(list, arrayAnnotationType, null);
  }

  String marshal(final JxObject object, final BiObjBiIntConsumer<Field,Relations> onFieldEncode) {
    final StringBuilder builder = new StringBuilder();
    marshal(object, onFieldEncode, builder, 1);
    return builder.toString();
  }

  public String marshal(final JxObject object) {
    return marshal(object, null);
  }
}