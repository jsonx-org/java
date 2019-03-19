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

import org.openjax.jsonx.runtime.ArrayValidator.Relation;
import org.openjax.jsonx.runtime.ArrayValidator.Relations;
import org.openjax.standard.util.Classes;
import org.openjax.standard.util.FastArrays;
import org.openjax.standard.util.Identifiers;
import org.openjax.standard.util.function.BiObjBiIntConsumer;

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
        throw new ValidationException("Method get" + Identifiers.toClassCase(propertyName) + "() does not exist for " + object.getClass().getSimpleName() + "." + propertyName);

      return method.invoke(object);
    }
    catch (final IllegalAccessException | InvocationTargetException e) {
      throw new IllegalStateException(e);
    }
  }

  private StringBuilder encodeNonArray(final boolean isProperty, final Field field, final Annotation annotation, final Object object, final StringBuilder builder, final int depth) {
    if (field == null && object == null) {
      if (validate && !JxUtil.isNullable(annotation))
        return new StringBuilder("field is not nullable");

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
        final Object encoded = StringCodec.encode(annotation, isProperty ? ((StringProperty)annotation).pattern() : ((StringElement)annotation).pattern(), (String)value, validate);
        if (encoded instanceof StringBuilder)
          return (StringBuilder)encoded;

        builder.append(encoded);
      }
      else if (Boolean.class.isAssignableFrom(type) && (annotation instanceof BooleanProperty || annotation instanceof BooleanElement)) {
        builder.append(BooleanCodec.encode((Boolean)value));
      }
      else if (Number.class.isAssignableFrom(type)) {
        final Form form;
        final String range;
        if (isProperty) {
          final NumberProperty property = (NumberProperty)annotation;
          form = property.form();
          range = property.range();
        }
        else {
          final NumberElement element = (NumberElement)annotation;
          form = element.form();
          range = element.range();
        }

        final Object encoded = NumberCodec.encode(annotation, form, range, (Number)value, validate);
        if (encoded instanceof StringBuilder)
          return (StringBuilder)encoded;

        builder.append(encoded);
      }
      else if (JxObject.class.isAssignableFrom(type)) {
        final StringBuilder error = marshal((JxObject)value, null, builder, depth + 1);
        if (error != null)
          return error;
      }
      else {
        throw new UnsupportedOperationException("Unsupported object type: " + type.getName());
      }
    }

    return null;
  }

  @SuppressWarnings("unchecked")
  private StringBuilder encodeProperty(final Field field, final Annotation annotation, final Object object, final BiObjBiIntConsumer<Field,Relations> onFieldEncode, final StringBuilder builder, final int depth) {
    try {
      if (annotation instanceof ArrayProperty) {
        final Object encoded = ArrayCodec.encode(field, object instanceof Optional ? ((Optional<List<Object>>)object).orElse(null) : (List<Object>)object, validate);
        if (encoded instanceof StringBuilder)
          return (StringBuilder)encoded;

        final Relations relations = (Relations)encoded;
        if (onFieldEncode != null)
          onFieldEncode.accept(field, relations, -1, -1);

        final StringBuilder error = encodeArray(relations, builder, depth);
        if (error != null)
          return error;
      }
      else if (annotation instanceof AnyProperty) {
        final Object encoded = AnyCodec.encode(annotation, ((AnyProperty)annotation).types(), object instanceof Optional ? ((Optional<?>)object).orElse(null) : object, validate, this, depth);
        if (encoded instanceof StringBuilder)
          return (StringBuilder)encoded;

        if (encoded instanceof Relations) {
          final Relations relations = (Relations)encoded;
          if (onFieldEncode != null)
            onFieldEncode.accept(field, relations, -1, -1);

          final StringBuilder error = encodeArray(relations, builder, depth);
          if (error != null)
            return error;
        }
        else {
          builder.append(encoded);
        }
      }
      else {
        final StringBuilder error = encodeNonArray(true, field, annotation, object, builder, depth);
        if (error != null)
          return error;
      }
    }
    catch (final ValidationException e) {
      throw e;
    }
    catch (final Exception e) {
      throw new ValidationException("Invalid field: " + JxUtil.getFullyQualifiedFieldName(field), e);
    }

    return null;
  }

  private StringBuilder encodeArray(final Relations relations, final StringBuilder builder, final int depth) {
    builder.append('[');
    for (int i = 0; i < relations.size(); ++i) {
      if (i > 0)
        builder.append(comma);

      final Relation relation = relations.get(i);
      final StringBuilder error;
      if (relation.annotation instanceof ArrayElement || relation.annotation instanceof ArrayType || relation.annotation instanceof AnyElement && relation.member instanceof Relations) {
        error = encodeArray((Relations)relation.member, builder, depth);
      }
      else if (relation.annotation instanceof AnyElement) {
        final Object encoded = AnyCodec.encode(relation.annotation, ((AnyElement)relation.annotation).types(), relation.member, validate, this, depth);
        if (encoded instanceof StringBuilder) {
          error = (StringBuilder)encoded;
        }
        else if (encoded instanceof Relations) {
          error = encodeArray((Relations)encoded, builder, depth);
        }
        else {
          error = null;
          builder.append(encoded);
        }
      }
      else {
        error = encodeNonArray(false, null, relation.annotation, relation.member, builder, depth);
      }

      if (error != null)
        return error;
    }

    builder.append(']');
    return null;
  }

  StringBuilder marshal(final JxObject object, final BiObjBiIntConsumer<Field,Relations> onFieldEncode, final StringBuilder builder, final int depth) {
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
        if (annotation instanceof AnyProperty) {
          final AnyProperty property = (AnyProperty)annotation;
          name = JxUtil.getName(property.name(), field);
          nullable = property.nullable();
          use = property.use();
          break;
        }

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
        if (value == null || Optional.empty().equals(value)) {
          builder.append("null");
        }
        else {
          final StringBuilder error = encodeProperty(field, annotation, value, onFieldEncode, builder, depth);
          if (error != null)
            return error;
        }

        if (onFieldEncode != null)
          onFieldEncode.accept(field, null, start, builder.length());

        hasProperties = true;
      }
      else if (validate && use == Use.REQUIRED) {
        return new StringBuilder(object.getClass().getName() + "#" + name + " is required");
      }
      else if (onFieldEncode != null) {
        onFieldEncode.accept(field, null, -1, -1);
      }
    }

    if (indent > 0)
      builder.append('\n').append(FastArrays.createRepeat(' ', (depth - 1) * 2));

    builder.append('}');
    return null;
  }

  String encode(final List<?> list, final Class<? extends Annotation> arrayAnnotationType) {
    final StringBuilder builder = new StringBuilder();
    final Relations relations = new Relations();
    final String error = ArrayValidator.validate(arrayAnnotationType, list, relations, validate, null);
    if (validate && error != null)
      throw new EncodeException(error);

    encodeArray(relations, builder, 0);
    return builder.toString();
  }

  public String marshal(final List<?> list, final Class<? extends Annotation> arrayAnnotationType) {
    return encode(list, arrayAnnotationType);
  }

  String marshal(final JxObject object, final BiObjBiIntConsumer<Field,Relations> onFieldEncode) {
    final StringBuilder builder = new StringBuilder();
    final StringBuilder error = marshal(object, onFieldEncode, builder, 1);
    if (error != null)
      throw new EncodeException(error.toString());

    return builder.toString();
  }

  public String marshal(final JxObject object) {
    return marshal(object, null);
  }
}