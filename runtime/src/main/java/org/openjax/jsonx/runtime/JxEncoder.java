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

import org.fastjax.net.URIComponent;
import org.fastjax.util.Annotations;
import org.fastjax.util.FastArrays;
import org.fastjax.util.Classes;
import org.fastjax.util.Strings;
import org.openjax.jsonx.runtime.ArrayValidator.Relation;
import org.openjax.jsonx.runtime.ArrayValidator.Relations;

public class JxEncoder {
  private final int indent;
  private final String comma;
  private final String colon;

  public JxEncoder(final int indent) {
    if (indent < 0)
      throw new IllegalArgumentException("spaces < 0: " + indent);

    this.indent = indent;
    if (indent == 0) {
      this.comma = ",";
      this.colon = ":";
    }
    else {
      this.comma = ", ";
      this.colon = ": ";
    }
  }

  public JxEncoder() {
    this(0);
  }

  private static Object getValue(final Object object, final String propertyName) {
    final Method method = JsonxUtil.getGetMethod(object.getClass(), propertyName);
    try {
      return method.invoke(object);
    }
    catch (final IllegalAccessException | InvocationTargetException e) {
      throw new UnsupportedOperationException(e);
    }
  }

  private void encodeProperty(final Annotation annotation, final Object object, final StringBuilder builder, final int depth) {
    if (object == null) {
      if (!JsonxUtil.isNullable(annotation))
        throw new EncodeException("field is not nullable");

      builder.append("null");
    }
    else if (object instanceof String) {
      final boolean urlEncode;
      final String pattern;
      if (annotation instanceof StringProperty) {
        final StringProperty property = (StringProperty)annotation;
        pattern = property.pattern();
        urlEncode = property.urlEncode();
      }
      else if (annotation instanceof StringElement) {
        final StringElement element = (StringElement)annotation;
        pattern = element.pattern();
        urlEncode = element.urlEncode();
      }
      else {
        throw new UnsupportedOperationException("Unsupported annotation type " + annotation.annotationType().getName());
      }

      final String string = (String)object;
      if (pattern.length() > 0 && !string.matches(pattern))
        throw new EncodeException(Annotations.toSortedString(annotation, AttributeComparator.instance) + ": pattern is not matched: \"" + Strings.truncate(string, 16) + "\"");

      final String escaped = Strings.escapeForJava(string);
      builder.append('"').append(urlEncode ? URIComponent.encode(escaped) : escaped).append('"');
    }
    else if (object instanceof Boolean) {
      builder.append(object.toString());
    }
    else if (object instanceof Number) {
      final Form form;
      final String range;
      if (annotation instanceof NumberProperty) {
        final NumberProperty property = (NumberProperty)annotation;
        form = property.form();
        range = property.range();
      }
      else if (annotation instanceof NumberElement) {
        final NumberElement element = (NumberElement)annotation;
        form = element.form();
        range = element.range();
      }
      else {
        throw new UnsupportedOperationException("Unsupported annotation type " + annotation.annotationType().getName());
      }

      final Number number = (Number)object;
      if (form == Form.INTEGER && number.longValue() != number.doubleValue())
        throw new EncodeException("Illegal non-INTEGER value: " + Strings.truncate(String.valueOf(number), 16));

      if (range.length() > 0) {
        try {
          if (!new Range(range).isValid(number))
            throw new EncodeException("Range is not matched: " + Strings.truncate(range, 16));
        }
        catch (final ParseException e) {
          throw new ValidationException("Invalid range attribute: " + Annotations.toSortedString(annotation, AttributeComparator.instance));
        }
      }

      builder.append(number);
    }
    else {
      toString(object, builder, depth + 1);
    }
  }

  @SuppressWarnings("unchecked")
  private void encodeProperty(final Field field, final Annotation annotation, final Object object, final StringBuilder builder, final int depth) {
    if (object instanceof List) {
      final List<Object> list = (List<Object>)object;
      final Relations relations = new Relations();
      final String error = ArrayValidator.validate(field, list, relations);
      if (error != null)
        throw new EncodeException(error);

      encodeArray(relations, builder, depth);
    }
    else {
      encodeProperty(annotation, object, builder, depth);
    }
  }

  private void encodeArray(final Relations relations, final StringBuilder builder, final int depth) {
    builder.append('[');
    for (int i = 0; i < relations.size(); ++i) {
      if (i > 0)
        builder.append(comma);

      final Relation relation = relations.get(i);
      if (relation.annotation instanceof ArrayElement)
        encodeArray((Relations)relation.member, builder, depth);
      else
        encodeProperty(relation.annotation, relation.member, builder, depth);
    }

    builder.append(']');
  }

  private void toString(final Object object, final StringBuilder builder, final int depth) {
    builder.append('{');
    final Field[] fields = Classes.getDeclaredFieldsDeep(object.getClass());
    for (int i = 0; i < fields.length; ++i) {
      final Field field = fields[i];
      Annotation annotation = null;
      String name = null;
      Use use = null;
      final Annotation[] annotations = field.getAnnotations();
      for (int j = 0; j < annotations.length; ++j) {
        annotation = annotations[j];
        if (annotation instanceof ArrayProperty) {
          final ArrayProperty property = (ArrayProperty)annotation;
          name = JsonxUtil.getName(property.name(), field);
          use = property.use();
          break;
        }

        if (annotation instanceof ObjectProperty) {
          final ObjectProperty property = (ObjectProperty)annotation;
          name = JsonxUtil.getName(property.name(), field);
          use = property.use();
          break;
        }

        if (annotation instanceof BooleanProperty) {
          final BooleanProperty property = (BooleanProperty)annotation;
          name = JsonxUtil.getName(property.name(), field);
          use = property.use();
          break;
        }

        if (annotation instanceof NumberProperty) {
          final NumberProperty property = (NumberProperty)annotation;
          name = JsonxUtil.getName(property.name(), field);
          use = property.use();
          break;
        }

        if (annotation instanceof StringProperty) {
          final StringProperty property = (StringProperty)annotation;
          name = JsonxUtil.getName(property.name(), field);
          use = property.use();
          break;
        }
      }

      if (name == null)
        continue;

      final Object value = getValue(object, name);
      if (value != null) {
        if (i > 0)
          builder.append(',');

        if (indent > 0)
          builder.append('\n').append(FastArrays.createRepeat(' ', depth * 2));

        builder.append('"').append(name).append('"').append(colon);
        encodeProperty(field, annotation, value, builder, depth);
      }
      else if (use == Use.REQUIRED) {
        throw new EncodeException(object.getClass().getName() + "." + name + " is required");
      }
    }

    if (indent > 0)
      builder.append('\n').append(FastArrays.createRepeat(' ', (depth - 1) * 2));

    builder.append('}');
  }

  public String toString(final List<?> list, final Class<? extends Annotation> arrayAnnotationType) {
    final StringBuilder builder = new StringBuilder();
    final Relations relations = new Relations();
    final String error = ArrayValidator.validate(arrayAnnotationType, list, relations);
    if (error != null)
      throw new EncodeException(error);

    encodeArray(relations, builder, 0);
    return builder.toString();
  }

  public String toString(final Object object) {
    final StringBuilder builder = new StringBuilder();
    toString(object, builder, 1);
    return builder.toString();
  }
}