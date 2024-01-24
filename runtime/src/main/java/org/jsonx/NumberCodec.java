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
import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.jsonx.ArrayValidator.Relations;
import org.libj.lang.Annotations;
import org.libj.lang.Classes;
import org.libj.lang.Numbers;
import org.libj.lang.ParseException;
import org.openjax.json.JsonParseException;
import org.openjax.json.JsonReader;
import org.openjax.json.JsonUtil;

class NumberCodec extends PrimitiveCodec {
  static final ThreadLocal<DecimalFormat> decimalFormatLocal = new ThreadLocal<DecimalFormat>() {
    @Override
    protected DecimalFormat initialValue() {
      final DecimalFormat decimalFormat = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
      decimalFormat.setMaximumFractionDigits(309);
      return decimalFormat;
    }
  };

  static String format(final Object object) {
    return object instanceof Double ? format((double)object) : object instanceof Float ? format((float)object) : object.toString();
  }

  /**
   * Returns the provided provided {@code double} as a string.
   *
   * @param value The {@code double} to return as a string.
   * @return The provided provided {@code double} as a string.
   * @implNote This method mimicks JavaScript's Double.toString() algorithm.
   */
  static String format(final double value) {
    return 0.000001 <= value && value <= 9.999999999999999E20 ? decimalFormatLocal.get().format(value) : Numbers.stripTrailingZeros(new StringBuilder(String.valueOf(value))).toString();
  }

  static Class<? extends Number> getDefaultClass(final int scale) {
    return scale == 0 ? Long.class : Double.class;
  }

  private static Number parseDefaultNumber(final int scale, final String json, final boolean strict) {
    try {
      return JsonUtil.parseNumber(getDefaultClass(scale), json, strict);
    }
    catch (final JsonParseException | NumberFormatException e) {
      return null;
    }
  }

  private static Number parseNumber(final Class<? extends Number> type, final int scale, final String json, final boolean strict) {
    try {
      return type.isPrimitive() || !Modifier.isAbstract(type.getModifiers()) ? JsonUtil.parseNumber(type, json, strict) : parseDefaultNumber(scale, json, strict);
    }
    catch (final JsonParseException | NumberFormatException e) {
      return null;
    }
  }

  @SuppressWarnings("unchecked")
  private static Object decodeObject(final Class<?> type, final int scale, final Executable decode, final String json, final boolean strict) {
    return decode != null ? JsdUtil.invoke(decode, json) : type.isAssignableFrom(String.class) ? json : parseNumber((Class<? extends Number>)type, scale, json, strict);
  }

  static Object decodeArray(final Class<?> type, final int scale, final String decode, final String token, final boolean strict) {
    final char ch = token.charAt(0);
    if (ch != '-' && (ch < '0' || '9' < ch))
      return null;

    return decodeObject(type, scale, getMethod(decodeToMethod, decode, String.class), token, strict);
  }

  static Error encodeArray(final Annotation annotation, final int scale, final String range, final Class<?> type, final String encode, final Object object, final int index, final Relations relations, final boolean validate) {
    if (!Classes.isInstance(type, object))
      return Error.CONTENT_NOT_EXPECTED(object, null, null);

    final Class<?> cls = object.getClass();
    if (validate && (scale != Integer.MAX_VALUE || (range != null && range.length() > 0))) {
      if (!Classes.isAssignableFrom(Number.class, cls))
        throw new ValidationException("Invalid array member: " + annotation + ": Value conditions can only be defined if \"type\" is a subclass of: " + Number.class.getName());

      final Error error = NumberCodec.validate(annotation, (Number)object, scale, range, type);
      if (error != null)
        return error;
    }

    final Executable method = getMethod(encodeToMethod, encode, cls);
    // The logic here is a it mixed. Double and Float objects are converted to
    // String, and everything else stays as is (to be turned into String later).
    // I kept it like this to satisfy ArrayCodecTest
    relations.set(index, method != null ? JsdUtil.invoke(method, object) : object instanceof Double ? format((double)object) : object instanceof Float ? format((float)object) : object, annotation);
    return null;
  }

  static Error encodeObject(final Appendable out, final Annotation annotation, final int scale, final String range, final Class<?> type, final String encode, final Object object, final boolean validate) throws EncodeException, IOException, ValidationException {
    return Classes.isAssignableFrom(type, object.getClass(), true) ? encodeObjectUnsafe(out, annotation, scale, range, type, encode, object, validate) : Error.CONTENT_NOT_EXPECTED(object, null, null);
  }

  static Error encodeObjectUnsafe(final Appendable out, final Annotation annotation, final int scale, final String range, final Class<?> type, final String encode, final Object object, final boolean validate) throws EncodeException, IOException, ValidationException {
    if (validate && object instanceof Number) {
      final Error error = validate(annotation, (Number)object, scale, range, type);
      if (error != null)
        return error;
    }

    final Executable method = getMethod(encodeToMethod, encode, object.getClass());
    out.append(method != null ? String.valueOf(JsdUtil.invoke(method, object)) : format(object));
    return null;
  }

  private static Error validate(final Annotation annotation, final Number object, final int scale, final String range, final Class<?> type) {
    if (scale != 0) {
      final Error error = isScaleValid(object.toString(), scale, type, null, annotation);
      if (error != null)
        return error;
    }
    else if (object.longValue() != object.doubleValue()) {
      return Error.SCALE_NOT_VALID(scale, object, null);
    }

    if (range.length() > 0) {
      try {
        if (!Range.from(range, scale, type).isValid(object))
          return Error.RANGE_NOT_MATCHED(range, object, null);
      }
      catch (final ParseException e) {
        throw new ValidationException("Invalid range attribute: " + Annotations.toSortedString(annotation, JsdUtil.ATTRIBUTES, true), e);
      }
    }

    return null;
  }

  private final int scale;
  private final Range range;

  NumberCodec(final NumberProperty property, final Method getMethod, final Method setMethod) {
    super(getMethod, setMethod, property.name(), property.nullable(), property.use(), property.decode());
    this.scale = property.scale();
    final String range = property.range();
    if (range.length() == 0) {
      this.range = null;
    }
    else {
      try {
        this.range = Range.from(range, scale, JsdUtil.getRealType(getMethod));
      }
      catch (final ParseException e) {
        throw new ValidationException("Invalid range attribute: " + Annotations.toSortedString(property, JsdUtil.ATTRIBUTES, true), e);
      }
    }

    if (this.scale != Integer.MAX_VALUE && this.range != null && !Classes.isAssignableFrom(Number.class, type()))
      throw new ValidationException("Invalid property: " + property + ": Conditions can only be defined if return type of " + getMethod + " is a subclass of: " + Number.class.getName());
  }

  @Override
  boolean test(final char firstChar) {
    return firstChar == '-' || '0' <= firstChar && firstChar <= '9';
  }

  private static Error isScaleValid(final String value, final int scale, final Class<?> type, final JsonReader reader, final Object source) {
    if (scale == Integer.MAX_VALUE)
      return null;

    if (scale > 0 && (type.isPrimitive() ? type == byte.class || type == short.class || type == int.class || type == long.class : type == Byte.class || type == Short.class || type == Integer.class || type == Long.class))
      throw new ValidationException("Type \"" + type.getName() + "\" is not compatible with scale=" + scale + ": " + (source instanceof Annotation ? Annotations.toSortedString((Annotation)source, JsdUtil.ATTRIBUTES, true) : JsdUtil.getFullyQualifiedMethodName((Method)source)));

    final int dot = value.indexOf('.');
    if (dot == -1 || scale != 0 && value.length() - 1 - dot <= scale)
      return null;

    return Error.SCALE_NOT_VALID(scale, value, reader);
  }

  @Override
  Error validate(final String json, final JsonReader reader) {
    final Error error = isScaleValid(json, scale, type(), reader, getMethod);
    if (error != null)
      return error;

    if (range != null && !range.isValid(parseDefaultNumber(scale, json, reader.isStrict())))
      return Error.RANGE_NOT_MATCHED(range, json, reader);

    return null;
  }

  @Override
  Object parseValue(final String json, final boolean strict) {
    return decodeObject(type(), scale, decode(), json, strict);
  }

  @Override
  String elementName() {
    return "number";
  }
}