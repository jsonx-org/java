/* Copyright (c) 2018 JSONx
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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.jsonx.ArrayValidator.Relation;
import org.jsonx.ArrayValidator.Relations;
import org.libj.lang.Classes;
import org.libj.util.ArrayUtil;
import org.libj.util.Patterns;

/**
 * Encoder that serializes Jx objects (that extend {@link JxObject}) and Jx
 * arrays (with a provided annotation class that declares an {@link ArrayType}
 * annotation) to JSON documents.
 */
public class JxEncoder {
  private static final HashMap<Integer,JxEncoder> instances = new HashMap<>();

  public static final class VALIDATING {
    /** Validating {@link JxEncoder} that does not indent JSON values */
    public static final JxEncoder _0 = get(0);

    /** Validating {@link JxEncoder} that indents JSON values with 1 space */
    public static final JxEncoder _1 = get(1);

    /** Validating {@link JxEncoder} that indents JSON values with 2 spaces */
    public static final JxEncoder _2 = get(2);

    /** Validating {@link JxEncoder} that indents JSON values with 3 spaces */
    public static final JxEncoder _3 = get(3);

    /** Validating {@link JxEncoder} that indents JSON values with 4 spaces */
    public static final JxEncoder _4 = get(4);

    /** Validating {@link JxEncoder} that indents JSON values with 8 spaces */
    public static final JxEncoder _8 = get(8);

    /**
     * Returns the validating {@link JxEncoder} for the specified number of
     * spaces to be used when indenting values during serialization to JSON
     * documents.
     *
     * @param indent The number of spaces to be used when indenting values
     *          during serialization to JSON documents.
     * @return The validating {@link JxEncoder} for the specified number of
     *         spaces to be used when indenting values during serialization to
     *         JSON documents.
     * @throws IllegalArgumentException If {@code indent < 0}.
     */
    public static JxEncoder get(final int indent) {
      return JxEncoder.get(indent, true);
    }

    private VALIDATING() {
    }
  }

  public static final class NON_VALIDATING {
    /** Non-validating {@link JxEncoder} that does not indent JSON values */
    public static final JxEncoder _0 = get(0);

    /** Non-validating {@link JxEncoder} that indents JSON values with 1 space */
    public static final JxEncoder _1 = get(1);

    /** Non-validating {@link JxEncoder} that indents JSON values with 2 spaces */
    public static final JxEncoder _2 = get(2);

    /** Non-validating {@link JxEncoder} that indents JSON values with 3 spaces */
    public static final JxEncoder _3 = get(3);

    /** Non-validating {@link JxEncoder} that indents JSON values with 4 spaces */
    public static final JxEncoder _4 = get(4);

    /** Non-validating {@link JxEncoder} that indents JSON values with 8 spaces */
    public static final JxEncoder _8 = get(8);

    /**
     * Returns the non-validating {@link JxEncoder} for the specified number of
     * spaces to be used when indenting values during serialization to JSON
     * documents.
     *
     * @param indent The number of spaces to be used when indenting values
     *          during serialization to JSON documents.
     * @return The non-validating {@link JxEncoder} for the specified number of
     *         spaces to be used when indenting values during serialization to
     *         JSON documents.
     * @throws IllegalArgumentException If {@code indent < 0}.
     */
    public static JxEncoder get(final int indent) {
      return JxEncoder.get(indent, false);
    }

    private NON_VALIDATING() {
    }
  }

  private static JxEncoder global = VALIDATING._0;

  /**
   * Returns the validating or non-validating {@link JxEncoder} for the
   * specified number of spaces to be used when indenting values during
   * serialization to JSON documents.
   *
   * @param indent The number of spaces to be used when indenting values during
   *          serialization to JSON documents.
   * @param validate Whether the {@link JxEncoder} is to perform validation.
   * @return The validating or non-validating {@link JxEncoder} for the
   *         specified number of spaces to be used when indenting values during
   *         serialization to JSON documents.
   * @throws IllegalArgumentException If {@code indent < 0}.
   */
  public static JxEncoder get(final int indent, final boolean validate) {
    if (indent < 0)
      throw new IllegalArgumentException("Indent must be a non-negative: " + indent);

    final Integer key = validate ? indent : -1 - indent;
    JxEncoder encoder = instances.get(key);
    if (encoder == null)
      instances.put(key, encoder = new JxEncoder(indent, validate));

    return encoder;
  }

  /**
   * Returns the global {@link JxEncoder}.
   *
   * @return The global {@link JxEncoder}.
   * @see #set(JxEncoder)
   */
  public static JxEncoder get() {
    return global;
  }

  /**
   * Set the global {@link JxEncoder}.
   *
   * @param encoder The {@link JxEncoder}.
   * @return The provided {@link JxEncoder}.
   * @see #get()
   */
  public static JxEncoder set(final JxEncoder encoder) {
    return global = encoder;
  }

  final int indent;
  private final String comma;
  private final String colon;
  private final boolean validate;

  /**
   * Creates a new {@link JxEncoder} for the specified number of spaces to be
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
   * Creates a new {@link JxEncoder} for the specified number of spaces to be
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

  private static Object getValue(final Object object, final Method getMethod, final Annotation annotation, final String propertyName, final Use use) {
    try {
      if (annotation instanceof AnyProperty && Map.class.isAssignableFrom(getMethod.getReturnType())) {
        final Map<?,?> map = (Map<?,?>)getMethod.invoke(object);
        if (map == null)
          return null;

        final Pattern pattern = Patterns.compile(propertyName);
        for (final Object key : map.keySet())
          if (key instanceof String && pattern.matcher((String)key).matches())
            return map;

        return use == Use.OPTIONAL ? map : null;
      }

      return getMethod.invoke(object);
    }
    catch (final IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    catch (final InvocationTargetException e) {
      if (e.getCause() instanceof RuntimeException)
        throw (RuntimeException)e.getCause();

      throw new RuntimeException(e.getCause());
    }
  }

  private Error encodeNonArray(final Method getMethod, final Annotation annotation, final Object object, final StringBuilder builder, final int depth) {
    if (getMethod == null && object == null) {
      if (validate && !JsdUtil.isNullable(annotation))
        return Error.MEMBER_NOT_NULLABLE(annotation);

      builder.append("null");
      return null;
    }

    if (annotation instanceof StringElement || annotation instanceof BooleanElement || annotation instanceof NumberElement) {
      builder.append(object);
      return null;
    }

    final Class<?> type;
    final boolean isOptional;
    if (getMethod == null) {
      isOptional = false;
      type = object.getClass();
    }
    else {
      isOptional = getMethod.getReturnType() == Optional.class;
      type = isOptional ? Classes.getGenericParameters(getMethod)[0] : getMethod.getReturnType();
    }

    final Object value = object == null ? null : isOptional ? ((Optional<?>)object).orElse(null) : object;

    if (annotation instanceof ObjectProperty || annotation instanceof ObjectElement) {
      return toString((JxObject)value, null, builder, depth + 1);
    }

    final Object encoded;
    if (annotation instanceof BooleanProperty) {
      final BooleanProperty property = (BooleanProperty)annotation;
      encoded = BooleanCodec.encodeObject(JsdUtil.getRealType(getMethod), property.encode(), value);
    }
    else if (annotation instanceof NumberProperty) {
      final NumberProperty property = (NumberProperty)annotation;
      encoded = NumberCodec.encodeObject(annotation, property.scale(), property.range(), JsdUtil.getRealType(getMethod), property.encode(), value, validate);
    }
    else if (annotation instanceof StringProperty) {
      final StringProperty property = (StringProperty)annotation;
      encoded = StringCodec.encodeObject(annotation, getMethod, property.pattern(), JsdUtil.getRealType(getMethod), property.encode(), value, validate);
    }
    else {
      throw new UnsupportedOperationException("Unsupported type: " + type.getName());
    }

    if (encoded instanceof Error)
      return (Error)encoded;

    builder.append(encoded);
    return null;
  }

  @SuppressWarnings("unchecked")
  private Error encodeProperty(final Method getMethod, final Annotation annotation, final String name, final Object object, final OnEncode onEncode, final StringBuilder builder, final int depth) {
    try {
      if (annotation instanceof ArrayProperty) {
        final Object encoded = ArrayCodec.encodeObject(getMethod, object instanceof Optional ? ((Optional<List<Object>>)object).orElse(null) : (List<Object>)object, validate);
        if (encoded instanceof Error)
          return (Error)encoded;

        final Relations relations = (Relations)encoded;
        if (onEncode != null)
          onEncode.accept(getMethod, name, relations, -1, -1);

        final Error error = encodeArray(getMethod, relations, builder, depth);
        if (error != null)
          return error;
      }
      else if (annotation instanceof AnyProperty) {
        final Object encoded = AnyCodec.encodeObject(annotation, getMethod, ((AnyProperty)annotation).types(), object instanceof Optional ? ((Optional<?>)object).orElse(null) : object, this, depth, validate);
        if (encoded instanceof Error)
          return (Error)encoded;

        if (encoded instanceof Relations) {
          final Relations relations = (Relations)encoded;
          if (onEncode != null)
            onEncode.accept(getMethod, name, relations, -1, -1);

          final Error error = encodeArray(getMethod, relations, builder, depth);
          if (error != null)
            return error;
        }
        else {
          builder.append(encoded);
        }
      }
      else {
        final Error error = encodeNonArray(getMethod, annotation, object, builder, depth);
        if (error != null)
          return error;
      }
    }
    catch (final EncodeException | ValidationException e) {
      throw e;
    }
    catch (final Exception e) {
      throw new ValidationException("Invalid method: " + JsdUtil.getFullyQualifiedMethodName(getMethod), e);
    }

    return null;
  }

  private Error encodeArray(final Method getMethod, final Relations relations, final StringBuilder builder, final int depth) {
    builder.append('[');
    for (int i = 0, len = relations.size(); i < len; ++i) {
      if (i > 0)
        builder.append(comma);

      final Relation relation = relations.get(i);
      final Annotation annotation = relation.annotation;
      final Object member = relation.member;
      final Error error;
      if (annotation instanceof ArrayElement || annotation instanceof ArrayType || annotation instanceof AnyElement && member instanceof Relations) {
        error = encodeArray(getMethod, (Relations)member, builder, depth);
      }
      else if (annotation instanceof AnyElement) {
        error = null;
        builder.append(member);
      }
      else {
        error = encodeNonArray(null, annotation, member, builder, depth);
      }

      if (error != null)
        return error;
    }

    builder.append(']');
    return null;
  }

  Error toString(final JxObject object, final OnEncode onEncode, final StringBuilder builder, final int depth) {
    builder.append('{');
    boolean hasProperties = false;
    final Method[] methods = object.getClass().getMethods();
    Classes.sortDeclarativeOrder(methods);
    for (final Method getMethod : methods) {
      if (getMethod.isSynthetic() || getMethod.getReturnType() == void.class || getMethod.getParameterCount() > 0)
        continue;

      Annotation annotation = null;
      String name = null;
      boolean nullable = false;
      Use use = null;
      final Annotation[] annotations = getMethod.getAnnotations();
      for (int j = 0; j < annotations.length; ++j) {
        annotation = annotations[j];
        if (annotation instanceof AnyProperty) {
          final AnyProperty property = (AnyProperty)annotation;
          name = property.name();
          nullable = property.nullable();
          use = property.use();
          break;
        }
        else if (annotation instanceof ArrayProperty) {
          final ArrayProperty property = (ArrayProperty)annotation;
          name = property.name();
          nullable = property.nullable();
          use = property.use();
          break;
        }
        else if (annotation instanceof ObjectProperty) {
          final ObjectProperty property = (ObjectProperty)annotation;
          name = property.name();
          nullable = property.nullable();
          use = property.use();
          break;
        }
        else if (annotation instanceof BooleanProperty) {
          final BooleanProperty property = (BooleanProperty)annotation;
          name = property.name();
          nullable = property.nullable();
          use = property.use();
          break;
        }
        else if (annotation instanceof NumberProperty) {
          final NumberProperty property = (NumberProperty)annotation;
          name = property.name();
          nullable = property.nullable();
          use = property.use();
          break;
        }
        else if (annotation instanceof StringProperty) {
          final StringProperty property = (StringProperty)annotation;
          name = property.name();
          nullable = property.nullable();
          use = property.use();
          break;
        }
      }

      if (nullable && use == Use.OPTIONAL && getMethod.getReturnType() != Optional.class)
        throw new ValidationException("Invalid field: " + JsdUtil.getFullyQualifiedMethodName(getMethod) + ": Field with (nullable=true && use=Use.OPTIONAL) must be of type: " + Optional.class.getName());

      if (getMethod.getReturnType().isPrimitive() && (nullable || use == Use.OPTIONAL))
        throw new ValidationException("Invalid field: " + JsdUtil.getFullyQualifiedMethodName(getMethod) + ": Field with (nullable=true || use=Use.OPTIONAL) cannot be primitive type: " + getMethod.getReturnType());

      if (name == null)
        continue;

      final Object value = getValue(object, getMethod, annotation, name, use);
      if (value != null || nullable && use == Use.REQUIRED) {
        if (!Map.class.isAssignableFrom(getMethod.getReturnType())) {
          if (hasProperties)
            builder.append(comma);

          final Error error = appendValue(builder, name, value, getMethod, annotation, onEncode, depth);
          if (error != null)
            return error;

          hasProperties = true;
        }
        else if (value != null) {
          final Map<?,?> map = (Map<?,?>)value;
          for (final Map.Entry<?,?> entry : map.entrySet()) {
            if (validate && !nullable && use == Use.OPTIONAL && entry.getValue() == null)
              return Error.PROPERTY_NOT_NULLABLE((String)entry.getKey(), annotation);

            if (hasProperties)
              builder.append(comma);

            final Error error = appendValue(builder, (String)entry.getKey(), entry.getValue(), getMethod, annotation, onEncode, depth);
            if (error != null)
              return error;

            hasProperties = true;
          }
        }
      }
      else if (validate && use == Use.REQUIRED) {
        return Error.PROPERTY_REQUIRED(name, getMethod);
      }
      else if (onEncode != null) {
        onEncode.accept(getMethod, null, null, -1, -1);
      }
    }

    if (indent > 0)
      builder.append('\n').append(ArrayUtil.createRepeat(' ', (depth - 1) * 2));

    builder.append('}');
    return null;
  }

  private Error appendValue(final StringBuilder builder, final String name, final Object value, final Method getMethod, final Annotation annotation, final OnEncode onEncode, final int depth) {
    if (indent > 0)
      builder.append('\n').append(ArrayUtil.createRepeat(' ', depth * 2));

    builder.append('"').append(name).append('"').append(colon);
    final int start = builder.length();
    if (value == null || Optional.empty().equals(value)) {
      builder.append("null");
    }
    else {
      final Error error = encodeProperty(getMethod, annotation, name, value, onEncode, builder, depth);
      if (error != null)
        return error;
    }

    if (onEncode != null)
      onEncode.accept(getMethod, name, null, start, builder.length());

    return null;
  }

  /**
   * Marshals the specified {@link JxObject} to a {@link String}, performing
   * callbacks to the provided {@link OnEncode} for each encoded field.
   *
   * @param object The {@link JxObject}.
   * @param onEncode The {@link OnEncode} to be called for each encoded
   *          property.
   * @return The {@link String} form of the marshaled {@link JxObject} JSON
   *         document.
   * @throws EncodeException If an encode error has occurred.
   */
  String toString(final JxObject object, final OnEncode onEncode) {
    final StringBuilder builder = new StringBuilder();
    final Error error = toString(object, onEncode, builder, 1);
    if (validate && error != null)
      throw new EncodeException(error.toString());

    return builder.toString();
  }

  /**
   * Marshals the specified {@link JxObject} to a {@link String}.
   *
   * @param object The {@link JxObject}.
   * @return The {@link String} form of the marshaled {@link JxObject} JSON
   *         document.
   * @throws EncodeException If an encode error has occurred.
   */
  public String toString(final JxObject object) {
    return toString(object, null);
  }

  /**
   * Marshals the given {@link List list} to a {@link String}, based on the
   * specification of the provided annotation type. The provided annotation type
   * must declare an annotation of type {@link ArrayType} that specifies the
   * model of the list being marshaled.
   *
   * @param list The {@link List}.
   * @param arrayAnnotationType The annotation type that declares an
   *          {@link ArrayType} annotation.
   * @return The {@link String} form of the marshaled {@link JxObject} JSON
   *         document.
   * @throws EncodeException If an encode error has occurred.
   */
  public String toString(final List<?> list, final Class<? extends Annotation> arrayAnnotationType) {
    final StringBuilder builder = new StringBuilder();
    final Relations relations = new Relations();
    Error error = ArrayValidator.validate(arrayAnnotationType, list, relations, validate, null);
    if (validate && error != null)
      throw new EncodeException(error);

    error = encodeArray(null, relations, builder, 0);
    if (validate && error != null)
      throw new EncodeException(error);

    return builder.toString();
  }
}