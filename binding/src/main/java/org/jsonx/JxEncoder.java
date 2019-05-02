/* Copyright (c) 2018 Jsonx
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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jsonx.ArrayValidator.Relation;
import org.jsonx.ArrayValidator.Relations;
import org.openjax.util.Classes;
import org.openjax.util.FastArrays;
import org.openjax.util.function.TriObjBiIntConsumer;

/**
 * Encoder that serializes Jx objects (that extend {@link JxObject}) and Jx
 * arrays (with a provided annotation class that declares an {@link ArrayType}
 * annotation) to JSON documents.
 */
public class JxEncoder {
  private static final HashMap<Integer,JxEncoder> instances = new HashMap<>();

  /** {@code JxEncoder} that does not indent JSON values */
  public static final JxEncoder _0 = get(0);

  /** {@code JxEncoder} that indents JSON values with 1 space */
  public static final JxEncoder _1 = get(1);

  /** {@code JxEncoder} that indents JSON values with 2 spaces */
  public static final JxEncoder _2 = get(2);

  /** {@code JxEncoder} that indents JSON values with 3 spaces */
  public static final JxEncoder _3 = get(3);

  /** {@code JxEncoder} that indents JSON values with 4 spaces */
  public static final JxEncoder _4 = get(4);

  /** {@code JxEncoder} that indents JSON values with 8 spaces */
  public static final JxEncoder _8 = get(8);

  private static JxEncoder global = _0;

  /**
   * Returns the {@code JxEncoder} for the specified number of spaces to be used
   * when indenting values during serialization to JSON documents.
   *
   * @param indent The number of spaces to be used when indenting values during
   *          serialization to JSON documents.
   * @return The {@code JxEncoder} for the specified number of spaces to be used
   *         when indenting values during serialization to JSON documents.
   * @throws IllegalArgumentException If {@code indent < 0}.
   */
  public static JxEncoder get(final int indent) {
    if (indent < 0)
      throw new IllegalArgumentException("Indent must be a non-negative: " + indent);

    JxEncoder encoder = instances.get(indent);
    if (encoder == null)
      instances.put(indent, encoder = new JxEncoder(indent));

    return encoder;
  }

  /**
   * @return The global {@code JxEncoder}.
   * @see #set(JxEncoder)
   */
  public static JxEncoder get() {
    return global;
  }

  /**
   * Set the global {@code JxEncoder}.
   *
   * @param encoder The {@code JxEncoder}.
   * @see #get()
   */
  public static void set(final JxEncoder encoder) {
    global = encoder;
  }

  final int indent;
  private final String comma;
  private final String colon;
  private final boolean validate;

  /**
   * Creates a new {@code JxEncoder} for the specified number of spaces to be
   * used when indenting values during serialization to JSON documents.
   *
   * @param indent The number of spaces to be used when indenting values during
   *          serialization to JSON documents.
   * @throws IllegalArgumentException If {@code indent < 0}.
   */
  protected JxEncoder(final int indent) {
    this(indent, true);
  }

  /**
   * Creates a new {@code JxEncoder} for the specified number of spaces to be
   * used when indenting values during serialization to JSON documents.
   *
   * @param indent The number of spaces to be used when indenting values during
   *          serialization to JSON documents.
   * @param validate If {@code true}, the produced JSON is validated; if
   *          {@code false}, the produced JSON is not validated.
   * @throws IllegalArgumentException If {@code indent < 0}.
   */
  JxEncoder(final int indent, final boolean validate) {
    if (indent < 0)
      throw new IllegalArgumentException("Indent must be a non-negative: " + indent);

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

  private static Object getValue(final Object object, final String propertyName, final Use use) {
    final Method method = JsdUtil.getGetMethod(object.getClass(), propertyName);
    try {
      if (method != null)
        return method.invoke(object);

      Map<?,?> optionalMap = null;
      Map<?,?> map = null;
      for (final Field field : object.getClass().getFields()) {
        if (!Map.class.isAssignableFrom(field.getType()))
          continue;

        final AnyProperty property = field.getAnnotation(AnyProperty.class);
        if (property == null)
          continue;

        if (!propertyName.equals(property.name()))
          continue;

        map = (Map<?,?>)field.get(object);
        for (final Map.Entry<?,?> entry : map.entrySet())
          if (entry.getKey() instanceof String && ((String)entry.getKey()).matches(propertyName))
            return map;

        if (use == Use.OPTIONAL)
          optionalMap = map;
      }

      return optionalMap;
    }
    catch (final IllegalAccessException | InvocationTargetException e) {
      throw new IllegalStateException(e);
    }
  }

  private Error encodeNonArray(final boolean isProperty, final Field field, final Annotation annotation, final Object object, final StringBuilder builder, final int depth) {
    if (field == null && object == null) {
      if (validate && !JsdUtil.isNullable(annotation))
        return Error.MEMBER_NOT_NULLABLE(annotation);

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
        type = (isOptional = Optional.class.isAssignableFrom(field.getType())) ? Classes.getGenericClasses(field)[0] : field.getType();
      }

      final Object value = object == null ? null : isOptional ? ((Optional<?>)object).orElse(null) : object;
      if (String.class.isAssignableFrom(type)) {
        final Object encoded = StringCodec.encodeObject(annotation, isProperty ? ((StringProperty)annotation).pattern() : ((StringElement)annotation).pattern(), (String)value, validate);
        if (encoded instanceof Error)
          return (Error)encoded;

        builder.append(encoded);
      }
      else if (Boolean.class.isAssignableFrom(type) && (annotation instanceof BooleanProperty || annotation instanceof BooleanElement)) {
        builder.append(BooleanCodec.encodeObject((Boolean)value));
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

        final Object encoded = NumberCodec.encodeObject(annotation, form, range, (Number)value, validate);
        if (encoded instanceof Error)
          return (Error)encoded;

        builder.append(encoded);
      }
      else if (JxObject.class.isAssignableFrom(type)) {
        final Error error = marshal((JxObject)value, null, builder, depth + 1);
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
  private Error encodeProperty(final Field field, final Annotation annotation, final String name, final Object object, final TriObjBiIntConsumer<Field,String,Relations> onFieldEncode, final StringBuilder builder, final int depth) {
    try {
      if (annotation instanceof ArrayProperty) {
        final Object encoded = ArrayCodec.encodeObject(field, object instanceof Optional ? ((Optional<List<Object>>)object).orElse(null) : (List<Object>)object, validate);
        if (encoded instanceof Error)
          return (Error)encoded;

        final Relations relations = (Relations)encoded;
        if (onFieldEncode != null)
          onFieldEncode.accept(field, name, relations, -1, -1);

        final Error error = encodeArray(relations, builder, depth);
        if (error != null)
          return error;
      }
      else if (annotation instanceof AnyProperty) {
        final Object encoded = AnyCodec.encodeObject(annotation, ((AnyProperty)annotation).types(), object instanceof Optional ? ((Optional<?>)object).orElse(null) : object, this, depth, validate);
        if (encoded instanceof Error)
          return (Error)encoded;

        if (encoded instanceof Relations) {
          final Relations relations = (Relations)encoded;
          if (onFieldEncode != null)
            onFieldEncode.accept(field, name, relations, -1, -1);

          final Error error = encodeArray(relations, builder, depth);
          if (error != null)
            return error;
        }
        else {
          builder.append(encoded);
        }
      }
      else {
        final Error error = encodeNonArray(true, field, annotation, object, builder, depth);
        if (error != null)
          return error;
      }
    }
    catch (final ValidationException e) {
      throw e;
    }
    catch (final Exception e) {
      throw new ValidationException("Invalid field: " + JsdUtil.getFullyQualifiedFieldName(field), e);
    }

    return null;
  }

  private Error encodeArray(final Relations relations, final StringBuilder builder, final int depth) {
    builder.append('[');
    for (int i = 0; i < relations.size(); ++i) {
      if (i > 0)
        builder.append(comma);

      final Relation relation = relations.get(i);
      final Error error;
      if (relation.annotation instanceof ArrayElement || relation.annotation instanceof ArrayType || relation.annotation instanceof AnyElement && relation.member instanceof Relations) {
        error = encodeArray((Relations)relation.member, builder, depth);
      }
      else if (relation.annotation instanceof AnyElement) {
        final Object encoded = AnyCodec.encodeObject(relation.annotation, ((AnyElement)relation.annotation).types(), relation.member, this, depth, validate);
        if (encoded instanceof Error) {
          error = (Error)encoded;
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

  Error marshal(final JxObject object, final TriObjBiIntConsumer<Field,String,Relations> onFieldEncode, final StringBuilder builder, final int depth) {
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
          name = JsdUtil.getName(property.name(), field);
          nullable = property.nullable();
          use = property.use();
          break;
        }

        if (annotation instanceof ArrayProperty) {
          final ArrayProperty property = (ArrayProperty)annotation;
          name = JsdUtil.getName(property.name(), field);
          nullable = property.nullable();
          use = property.use();
          break;
        }

        if (annotation instanceof ObjectProperty) {
          final ObjectProperty property = (ObjectProperty)annotation;
          name = JsdUtil.getName(property.name(), field);
          nullable = property.nullable();
          use = property.use();
          break;
        }

        if (annotation instanceof BooleanProperty) {
          final BooleanProperty property = (BooleanProperty)annotation;
          name = JsdUtil.getName(property.name(), field);
          nullable = property.nullable();
          use = property.use();
          break;
        }

        if (annotation instanceof NumberProperty) {
          final NumberProperty property = (NumberProperty)annotation;
          name = JsdUtil.getName(property.name(), field);
          nullable = property.nullable();
          use = property.use();
          break;
        }

        if (annotation instanceof StringProperty) {
          final StringProperty property = (StringProperty)annotation;
          name = JsdUtil.getName(property.name(), field);
          nullable = property.nullable();
          use = property.use();
          break;
        }
      }

      if (nullable && use == Use.OPTIONAL && !Optional.class.equals(field.getType()))
        throw new ValidationException("Invalid field: " + JsdUtil.getFullyQualifiedFieldName(field) + ": Field with (nullable=true & use=Use.OPTIONAL) must be of type: " + Optional.class.getName());

      if (name == null)
        continue;

      final Object value = getValue(object, name, use);
      if (value != null || nullable && use == Use.REQUIRED) {
        if (value instanceof Map) {
          final Map<?,?> map = (Map<?,?>)value;
          for (final Map.Entry<?,?> entry : map.entrySet()) {
            if (validate && !nullable && use == Use.OPTIONAL && entry.getValue() == null)
              return Error.PROPERTY_NOT_NULLABLE((String)entry.getKey(), annotation);

            if (hasProperties)
              builder.append(',');

            final Error error = appendValue(builder, (String)entry.getKey(), entry.getValue(), field, annotation, onFieldEncode, depth);
            if (error != null)
              return error;

            hasProperties = true;
          }
        }
        else {
          if (hasProperties)
            builder.append(',');

          final Error error = appendValue(builder, name, value, field, annotation, onFieldEncode, depth);
          if (error != null)
            return error;

          hasProperties = true;
        }
      }
      else if (validate && use == Use.REQUIRED) {
        return Error.PROPERTY_REQUIRED(name, field);
      }
      else if (onFieldEncode != null) {
        onFieldEncode.accept(field, null, null, -1, -1);
      }
    }

    if (indent > 0)
      builder.append('\n').append(FastArrays.createRepeat(' ', (depth - 1) * 2));

    builder.append('}');
    return null;
  }

  private Error appendValue(final StringBuilder builder, final String name, final Object value, final Field field, final Annotation annotation, final TriObjBiIntConsumer<Field,String,Relations> onFieldEncode, final int depth) {
    if (indent > 0)
      builder.append('\n').append(FastArrays.createRepeat(' ', depth * 2));

    builder.append('"').append(name).append('"').append(colon);
    final int start = builder.length();
    if (value == null || Optional.empty().equals(value)) {
      builder.append("null");
    }
    else {
      final Error error = encodeProperty(field, annotation, name, value, onFieldEncode, builder, depth);
      if (error != null)
        return error;
    }

    if (onFieldEncode != null)
      onFieldEncode.accept(field, name, null, start, builder.length());

    return null;
  }

  /**
   * Marshals the specified {@code JxObject}, performing callbacks to the
   * provided {@code TriObjBiIntConsumer} for each encoded field.
   *
   * @param object The {@code JxObject}.
   * @param onFieldEncode The {@code TriObjBiIntConsumer} to be called for each
   *          encoded field.
   * @return A JSON document from the marshaled {@code JxObject}.
   * @throws EncodeException If an encode error has occurred.
   */
  String marshal(final JxObject object, final TriObjBiIntConsumer<Field,String,Relations> onFieldEncode) {
    final StringBuilder builder = new StringBuilder();
    final Error error = marshal(object, onFieldEncode, builder, 1);
    if (validate && error != null)
      throw new EncodeException(error.toString());

    return builder.toString();
  }

  /**
   * Marshals the specified {@code JxObject}.
   *
   * @param object The {@code JxObject}.
   * @return A JSON document from the marshaled {@code JxObject}.
   * @throws EncodeException If an encode error has occurred.
   */
  public String marshal(final JxObject object) {
    return marshal(object, null);
  }

  /**
   * Marshals the supplied {@code list} to the specification of the provided
   * annotation type. The provided annotation type must declare an annotation of
   * type {@link ArrayType} that specifies the model of the list being
   * marshaled.
   *
   * @param list The {@code List}.
   * @param arrayAnnotationType The annotation type that declares an
   *          {@link ArrayType} annotation.
   * @return A JSON document from the marshaled {@code List}.
   * @throws EncodeException If an encode error has occurred.
   */
  public String marshal(final List<?> list, final Class<? extends Annotation> arrayAnnotationType) {
    final StringBuilder builder = new StringBuilder();
    final Relations relations = new Relations();
    final Error error = ArrayValidator.validate(arrayAnnotationType, list, relations, validate, null);
    if (validate && error != null)
      throw new EncodeException(error);

    encodeArray(relations, builder, 0);
    return builder.toString();
  }
}