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

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

import org.jsonx.ArrayValidator.Relation;
import org.jsonx.ArrayValidator.Relations;
import org.libj.lang.Classes;
import org.libj.lang.CountingAppendable;
import org.libj.util.Patterns;

/**
 * Encoder that serializes Jx objects (that extend {@link JxObject}) and Jx arrays (with a provided annotation class that declares
 * an {@link ArrayType} annotation) to JSON documents.
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
     * Returns the validating {@link JxEncoder} for the specified number of spaces to be used when indenting values during serialization
     * to JSON documents.
     *
     * @param indent The number of spaces to be used when indenting values during serialization to JSON documents.
     * @return The validating {@link JxEncoder} for the specified number of spaces to be used when indenting values during serialization
     *         to JSON documents.
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
     * Returns the non-validating {@link JxEncoder} for the specified number of spaces to be used when indenting values during
     * serialization to JSON documents.
     *
     * @param indent The number of spaces to be used when indenting values during serialization to JSON documents.
     * @return The non-validating {@link JxEncoder} for the specified number of spaces to be used when indenting values during
     *         serialization to JSON documents.
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
   * Returns the validating or non-validating {@link JxEncoder} for the specified number of spaces to be used when indenting values
   * during serialization to JSON documents.
   *
   * @param indent The number of spaces to be used when indenting values during serialization to JSON documents.
   * @param validate Whether the {@link JxEncoder} is to perform validation.
   * @return The validating or non-validating {@link JxEncoder} for the specified number of spaces to be used when indenting values
   *         during serialization to JSON documents.
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
   * Creates a new {@link JxEncoder} for the specified number of spaces to be used when indenting values during serialization to JSON
   * documents.
   *
   * @param indent The number of spaces to be used when indenting values during serialization to JSON documents.
   * @throws IllegalArgumentException If {@code indent < 0}.
   */
  protected JxEncoder(final int indent) {
    this(indent, true);
  }

  /**
   * Creates a new {@link JxEncoder} for the specified number of spaces to be used when indenting values during serialization to JSON
   * documents.
   *
   * @param indent The number of spaces to be used when indenting values during serialization to JSON documents.
   * @param validate If {@code true}, the produced JSON is validated; if {@code false}, the produced JSON is not validated.
   * @throws IllegalArgumentException If {@code indent < 0}.
   */
  JxEncoder(final int indent, final boolean validate) {
    if (indent < 0)
      throw new IllegalArgumentException("Indent must be non-negative: " + indent);

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
      final Object value = getMethod.invoke(object);
      if (value == null)
        return null;

      if (!(annotation instanceof AnyProperty) || !Map.class.isAssignableFrom(getMethod.getReturnType()))
        return value;

      final Map<?,?> map = (Map<?,?>)value;
      if (map.size() > 0) {
        final Pattern pattern = Patterns.compile(propertyName, Pattern.DOTALL);
        for (final Object key : map.keySet()) // [S]
          if (key instanceof String && pattern.matcher((String)key).matches())
            return map;
      }

      return use == Use.OPTIONAL ? map : null;
    }
    catch (final IllegalAccessException e) {
      throw new RuntimeException(e);
    }
    catch (final InvocationTargetException e) {
      final Throwable cause = e.getCause();
      if (cause instanceof RuntimeException)
        throw (RuntimeException)cause;

      throw new RuntimeException(cause);
    }
  }

  private Error encodeNonArray(final Appendable out, final Method getMethod, final Annotation annotation, final Object object, final int depth) throws IOException {
    if (getMethod == null && object == null) {
      if (validate && !JsdUtil.isNullable(annotation))
        return Error.MEMBER_NOT_NULLABLE(annotation);

      out.append("null");
      return null;
    }

    if (annotation instanceof StringElement || annotation instanceof BooleanElement || annotation instanceof NumberElement) {
      out.append(String.valueOf(object));
      return null;
    }

    final Object value = object == null ? null : (getMethod != null && getMethod.getReturnType() == Optional.class) ? ((Optional<?>)object).orElse(null) : object;

    if (annotation instanceof ObjectProperty || annotation instanceof ObjectElement) {
      return encodeObject(out, (JxObject)value, null, depth + 1);
    }

    if (annotation instanceof BooleanProperty) {
      final BooleanProperty property = (BooleanProperty)annotation;
      return BooleanCodec.encodeObject(out, JsdUtil.getRealType(getMethod), property.encode(), value);
    }

    if (annotation instanceof NumberProperty) {
      final NumberProperty property = (NumberProperty)annotation;
      return NumberCodec.encodeObject(out, annotation, property.scale(), property.range(), JsdUtil.getRealType(getMethod), property.encode(), value, validate);
    }

    if (annotation instanceof StringProperty) {
      final StringProperty property = (StringProperty)annotation;
      return StringCodec.encodeObject(out, annotation, getMethod, property.pattern(), JsdUtil.getRealType(getMethod), property.encode(), value, validate);
    }

    @SuppressWarnings("null")
    final Class<?> type = getMethod == null ? object.getClass() : getMethod.getReturnType() == Optional.class ? Classes.getGenericParameters(getMethod)[0] : getMethod.getReturnType();
    throw new UnsupportedOperationException("Unsupported type: " + type.getName());
  }

  @SuppressWarnings("unchecked")
  private Error encodeProperty(final Appendable out, final Method getMethod, final Annotation annotation, final String name, final Object object, final OnEncode onEncode, final int depth) throws IOException {
    try {
      if (annotation instanceof ArrayProperty)
        return ArrayCodec.encodeObject(this, out, new Relations(), name, getMethod, object instanceof Optional ? ((Optional<List<Object>>)object).orElse(null) : (List<Object>)object, onEncode, depth, validate);

      if (annotation instanceof AnyProperty)
        return AnyCodec.encodeObject(this, out, annotation, name, getMethod, ((AnyProperty)annotation).types(), object instanceof Optional ? ((Optional<?>)object).orElse(null) : object, onEncode, depth, validate);

      return encodeNonArray(out, getMethod, annotation, object, depth);
    }
    catch (final EncodeException | IOException | ValidationException e) {
      throw e;
    }
    catch (final Exception e) {
      throw new ValidationException("Invalid method: " + JsdUtil.getFullyQualifiedMethodName(getMethod), e);
    }
  }

  Error encodeArray(final Appendable out, final Method getMethod, final Relations relations, final int depth) throws IOException {
    out.append('[');
    for (int i = 0, i$ = relations.size(); i < i$; ++i) { // [RA]
      if (i > 0)
        out.append(comma);

      final Relation relation = relations.get(i);
      final Annotation annotation = relation.annotation;
      final Object member = relation.member;
      final Error error;
      if (annotation instanceof ArrayElement || annotation instanceof ArrayType || annotation instanceof AnyElement && member instanceof Relations) {
        error = encodeArray(out, getMethod, (Relations)member, depth);
        if (error != null)
          return error;
      }
      else if (annotation instanceof AnyElement) {
        error = null;
        out.append(String.valueOf(member));
      }
      else {
        error = encodeNonArray(out, null, annotation, member, depth);
        if (error != null)
          return error;
      }
    }

    out.append(']');
    return null;
  }

  private static final ClassToGetMethods classToOrderedMethods = new ClassToGetMethods() {
    @Override
    Method[] getMethods(final Class<? extends JxObject> cls) {
      return Classes.getDeclaredMethodsDeep(cls);
    }

    @Override
    public boolean test(final Method method) {
      if (method.isBridge() || method.isSynthetic() || method.getReturnType() == void.class || method.getParameterCount() > 0)
        return false;

      if (!Modifier.isPublic(method.getModifiers())) {
        if ("clone".equals(method.getName()))
          return false;

        if (!method.isAccessible())
          method.setAccessible(true);
      }

      return true;
    }

    @Override
    void beforePut(final Method[] methods) {
      try {
        Classes.sortDeclarativeOrder(methods, true);
      }
      catch (final ClassNotFoundException e) {
      }
    }
  };

  Error encodeObject(final Appendable out, final JxObject object, final OnEncode onEncode, final int depth) throws IOException {
    out.append('{');
    boolean hasProperties = false;
    Annotation[] annotations;
    Annotation annotation = null;
    String name;
    boolean nullable = false;
    Use use = null;
    OUT:
    for (Method getMethod : classToOrderedMethods.get(object.getClass())) { // [A]
      IN:
      while (true) {
        annotations = Classes.getAnnotations(getMethod);
        name = null;
        for (int i = annotations.length - 1; i >= 0; --i) { // [A]
          annotation = annotations[i];
          if (annotation instanceof StringProperty) {
            final StringProperty property = (StringProperty)annotation;
            name = property.name();
            nullable = property.nullable();
            use = property.use();
            break IN;
          }
          else if (annotation instanceof NumberProperty) {
            final NumberProperty property = (NumberProperty)annotation;
            name = property.name();
            nullable = property.nullable();
            use = property.use();
            break IN;
          }
          else if (annotation instanceof ObjectProperty) {
            final ObjectProperty property = (ObjectProperty)annotation;
            name = property.name();
            nullable = property.nullable();
            use = property.use();
            break IN;
          }
          else if (annotation instanceof ArrayProperty) {
            final ArrayProperty property = (ArrayProperty)annotation;
            name = property.name();
            nullable = property.nullable();
            use = property.use();
            break IN;
          }
          else if (annotation instanceof BooleanProperty) {
            final BooleanProperty property = (BooleanProperty)annotation;
            name = property.name();
            nullable = property.nullable();
            use = property.use();
            break IN;
          }
          else if (annotation instanceof AnyProperty) {
            final AnyProperty property = (AnyProperty)annotation;
            name = property.name();
            nullable = property.nullable();
            use = property.use();
            break IN;
          }
        }

        final Class<?> superClass = getMethod.getDeclaringClass().getSuperclass();
        if (superClass == null || (getMethod = Classes.getDeclaredMethod(superClass, getMethod.getName(), getMethod.getParameterTypes())) == null)
          continue OUT;
      }

      final Class<?> returnType = getMethod.getReturnType();
      final boolean isNotMap = !Map.class.isAssignableFrom(returnType);
      if (isNotMap && nullable && use == Use.OPTIONAL && returnType != Optional.class)
        throw new ValidationException("Invalid field: " + JsdUtil.getFullyQualifiedMethodName(getMethod) + ": Field with (nullable=true && use=OPTIONAL) must be of type: " + Optional.class.getName());

      if ((nullable || use == Use.OPTIONAL) && returnType.isPrimitive())
        throw new ValidationException("Invalid field: " + JsdUtil.getFullyQualifiedMethodName(getMethod) + ": Field with (nullable=true || use=OPTIONAL) cannot be primitive type: " + returnType);

      final Object value = getValue(object, getMethod, annotation, name, use);
      if (value != null || nullable && use == Use.REQUIRED) {
        if (isNotMap) {
          if (hasProperties)
            out.append(comma);

          final Error error = appendValue(out, name, value, getMethod, annotation, onEncode, depth);
          if (error != null)
            return error;

          hasProperties = true;
        }
        else if (value != null) {
          final Map<?,?> map = (Map<?,?>)value;
          if (map.size() > 0) {
            for (final Map.Entry<?,?> entry : map.entrySet()) { // [S]
              if (validate && !nullable && use == Use.OPTIONAL && entry.getValue() == null)
                return Error.PROPERTY_NOT_NULLABLE((String)entry.getKey(), annotation);

              if (hasProperties)
                out.append(comma);

              final Error error = appendValue(out, (String)entry.getKey(), entry.getValue(), getMethod, annotation, onEncode, depth);
              if (error != null)
                return error;

              hasProperties = true;
            }
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

    if (indent > 0) {
      out.append('\n');
      for (int i = 0, i$ = (depth - 1) * 2; i < i$; ++i) // [N]
        out.append(' ');
    }

    out.append('}');
    return null;
  }

  private Error appendValue(final Appendable out, final String name, final Object value, final Method getMethod, final Annotation annotation, final OnEncode onEncode, final int depth) throws IOException {
    if (indent > 0) {
      out.append('\n');
      for (int i = 0, i$ = depth * 2; i < i$; ++i) // [N]
        out.append(' ');
    }

    out.append('"').append(name).append('"').append(colon);
    final long start = onEncode != null ? ((CountingAppendable)out).getCount() : 0;
    if (value == null || Optional.empty().equals(value)) {
      out.append("null");
    }
    else {
      final Error error = encodeProperty(out, getMethod, annotation, name, value, onEncode, depth);
      if (error != null)
        return error;
    }

    if (onEncode != null)
      onEncode.accept(getMethod, name, null, start, ((CountingAppendable)out).getCount());

    return null;
  }

  /**
   * Marshals the specified {@link JxObject} to the given {@link Appendable}, invoking callbacks to the provided {@link OnEncode} for
   * each encoded field.
   *
   * @param out The {@link Appendable} to which output is to be appended.
   * @param object The {@link JxObject} to marshal.
   * @param onEncode The {@link OnEncode} to be called for each encoded property.
   * @throws IOException If an I/O error has occurred.
   * @throws EncodeException If an encode error has occurred.
   */
  void encodeObject(Appendable out, final JxObject object, final OnEncode onEncode) throws IOException {
    if (onEncode != null)
      out = new CountingAppendable(out);

    final Error error = encodeObject(out, object, onEncode, 1);
    if (validate && error != null)
      throw new EncodeException(error.toString());
  }

  /**
   * Marshals the specified {@link JxObject} to a {@link String}.
   *
   * @param object The {@link JxObject} to marshal.
   * @return The {@link String} form of the marshaled {@link JxObject} JSON document.
   * @throws EncodeException If an encode error has occurred.
   */
  public String toString(final JxObject object) {
    final StringBuilder b = new StringBuilder();
    try {
      encodeObject(b, object, null);
    }
    catch (final IOException e) {
      throw new UncheckedIOException(e);
    }

    return b.toString();
  }

  /**
   * Marshals the specified {@link JxObject} to the given {@link Appendable}.
   *
   * @param out The {@link Appendable} to which the marshaled output is to be appended.
   * @param object The {@link JxObject}.
   * @throws IOException If an I/O error has occurred.
   * @throws EncodeException If an encode error has occurred.
   */
  public void toStream(final Appendable out, final JxObject object) throws IOException {
    encodeObject(out, object, null);
  }

  /**
   * Marshals the given {@link List list} to a {@link String}, based on the specification of the provided annotation type. The
   * provided annotation type must declare an annotation of type {@link ArrayType} that specifies the model of the list being
   * marshaled.
   *
   * @param list The {@link List} to marshal.
   * @param arrayAnnotationType The annotation type that declares an {@link ArrayType} annotation.
   * @return The {@link String} form of the marshaled {@link JxObject} JSON document.
   * @throws EncodeException If an encode error has occurred.
   */
  public String toString(final List<?> list, final Class<? extends Annotation> arrayAnnotationType) {
    final Relations relations = new Relations();
    Error error = ArrayValidator.encode(arrayAnnotationType, list, relations, validate, null);
    if (validate && error != null)
      throw new EncodeException(error);

    final StringBuilder b = new StringBuilder();
    try {
      error = encodeArray(b, null, relations, 0);
    }
    catch (final IOException e) {
      throw new UncheckedIOException(e);
    }

    if (validate && error != null)
      throw new EncodeException(error);

    return b.toString();
  }

  /**
   * Marshals the given {@link List list} to the given {@link Appendable}, based on the specification of the provided annotation type.
   * The provided annotation type must declare an annotation of type {@link ArrayType} that specifies the model of the list being
   * marshaled.
   *
   * @param out The {@link Appendable} to which the marshaled output is to be appended.
   * @param list The {@link List} to marshal.
   * @param arrayAnnotationType The annotation type that declares an {@link ArrayType} annotation.
   * @throws IOException If an I/O error has occurred.
   * @throws EncodeException If an encode error has occurred.
   */
  public void toStream(final Appendable out, final List<?> list, final Class<? extends Annotation> arrayAnnotationType) throws IOException {
    final Relations relations = new Relations();
    Error error = ArrayValidator.encode(arrayAnnotationType, list, relations, validate, null);
    if (validate && error != null)
      throw new EncodeException(error);

    error = encodeArray(out, null, relations, 0);
    if (validate && error != null)
      throw new EncodeException(error);
  }
}