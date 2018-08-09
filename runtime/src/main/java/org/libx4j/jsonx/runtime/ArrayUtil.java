/* Copyright (c) 2018 lib4j
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

package org.libx4j.jsonx.runtime;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lib4j.lang.Strings;

public class ArrayUtil {
  private static Pattern toPattern(final Annotation[] annotations) {
    final StringBuilder builder = new StringBuilder();
    builder.append('^');
    for (final Annotation annotation : annotations) {
      final int id;
      final int minOccurs;
      final int maxOccurs;
      final boolean nullable;
      final String label;
      if (annotation instanceof ArrayElement) {
        final ArrayElement element = (ArrayElement)annotation;
        id = element.id();
        minOccurs = element.minOccurs();
        maxOccurs = element.maxOccurs();
        nullable = element.nullable();
        label = "a";
      }
      else if (annotation instanceof BooleanElement) {
        final BooleanElement element = (BooleanElement)annotation;
        id = element.id();
        minOccurs = element.minOccurs();
        maxOccurs = element.maxOccurs();
        nullable = element.nullable();
        label = "b";
      }
      else if (annotation instanceof NumberElement) {
        final NumberElement element = (NumberElement)annotation;
        id = element.id();
        minOccurs = element.minOccurs();
        maxOccurs = element.maxOccurs();
        nullable = element.nullable();
        label = "n";
      }
      else if (annotation instanceof ObjectElement) {
        final ObjectElement element = (ObjectElement)annotation;
        id = element.id();
        minOccurs = element.minOccurs();
        maxOccurs = element.maxOccurs();
        nullable = element.nullable();
        label = "o";
      }
      else if (annotation instanceof StringElement) {
        final StringElement element = (StringElement)annotation;
        id = element.id();
        minOccurs = element.minOccurs();
        maxOccurs = element.maxOccurs();
        nullable = element.nullable();
        label = "s";
      }
      else {
        throw new UnsupportedOperationException("Unsupported annotation type " + annotation.annotationType().getName());
      }

      if (minOccurs < 0)
        throw new IllegalStateException("@" + annotation.annotationType().getSimpleName() + "(id=" + id + ", minOccurs=" + minOccurs + ") minOccurs must be a non-negative integer");

      if (maxOccurs < 0)
        throw new IllegalStateException("@" + annotation.annotationType().getSimpleName() + "(id=" + id + ", maxOccurs=" + maxOccurs + ") maxOccurs must be a non-negative integer");

      if (maxOccurs < minOccurs)
        throw new IllegalStateException("@" + annotation.annotationType().getSimpleName() + "(id=" + id + ", minOccurs=" + minOccurs + ", maxOccurs=" + maxOccurs + ") minOccurs must be less than or equal to maxOccurs");

      builder.append("(([");
      if (nullable)
        builder.append("N");

      builder.append(label).append("]\\d+){").append(minOccurs).append(",").append(maxOccurs).append("})?");
    }

//    builder.append('$');
    return Pattern.compile(builder.toString());
  }

  private static String encodeTestString(final Object ... list) {
    final StringBuilder builder = new StringBuilder();
    for (int i = 0; i < list.length; i++) {
      final Object member = list[i];
      if (member == null)
        builder.append('N');
      else if (member instanceof List)
        builder.append('a');
      else if (member instanceof Boolean)
        builder.append('b');
      else if (member instanceof Number)
        builder.append('n');
      else if (member instanceof String)
        builder.append('s');
      else
        builder.append('o');

      builder.append(i);
    }

    return builder.toString();
  }

  private static int getLastIndex(final String encoding) {
    int i;
    for (i = encoding.length() - 1; i >= 0; --i) {
      final char ch = encoding.charAt(i);
      if ('0' > ch || ch > '9')
        break;
    }

    return Integer.parseInt(encoding.substring(i + 1));
  }

  private static int getFirstIndex(final String encoding) {
    int i;
    for (i = 1; i < encoding.length(); ++i) {
      final char ch = encoding.charAt(i);
      if ('0' > ch || ch > '9')
        break;
    }

    return Integer.parseInt(encoding.substring(1, i));
  }

  private static final BiFunction<NumberElement,Object,String> verifyNumberElement = new BiFunction<>() {
    @Override
    public String apply(final NumberElement t, final Object u) {
      if (u == null)
        return t.nullable() ? null : "cannot be null";

      if (!(u instanceof Number))
        throw new UnsupportedOperationException("Unsupported number type: " + u.getClass().getName());

      final Number number = (Number)u;
      if (t.form() == Form.INTEGER && number.longValue() != number.doubleValue())
        return t.id() + ": " + u + " is not form=INTEGER";

      try {
        if (t.range().length() > 0 && !new Range(t.range()).isValid(number))
          return t.id() + ": " + u + " does not match range=\"" + t.range() + "\"";
      }
      catch (final ParseException e) {
        throw new IllegalStateException("@" + NumberElement.class.getSimpleName() + "(id=" + t.id() + ", range=\"" + t.range() + "\") range is invalid");
      }

      return null;
    }
  };

  private static final BiFunction<StringElement,Object,String> verifyStringElement = new BiFunction<>() {
    @Override
    public String apply(final StringElement t, final Object u) {
      if (u == null)
        return t.nullable() ? null : "cannot be null";

      if (!(u instanceof String))
        throw new UnsupportedOperationException("Unsupported number type: " + u.getClass().getName());

      final String string = (String)u;
      if (t.pattern().length() > 0 && !string.matches(t.pattern()))
        return t.id() + ": \"" + u + "\" does not match pattern=\"" + Strings.escapeForJava(t.pattern()) + "\"";

      return null;
    }
  };

  private static <T extends Annotation>List<String> verifyElement(final BiFunction<T,Object,String> function, final T annotation, final int start, final Object[] members) {
    final List<String> errors = new ArrayList<>();
    for (int i = 0; i < members.length; i++) {
      final String error = function.apply(annotation, members[i]);
      if (error != null)
        errors.add("Invalid content was found at member index=" + (start + i) + " for element id=" + error);
    }

    return errors;
  }

  private static Object[] getMembersInGroup(final String group, final Object[] members) {
    final int first = getFirstIndex(group);
    final int last = getLastIndex(group);
    final Object[] subset = new Object[last - first + 1];
    for (int i = 0; i < subset.length; ++i)
      subset[i] = members[i + first];

    return subset;
  }

  private static List<String> validate(final String encoding, final Pattern pattern, final Annotation[] annotations, final Object ... members) {
    final Matcher matcher = pattern.matcher(encoding);
    matcher.lookingAt();
    int matchedLength = 0;
    int unmatchedId = -1;
    final List<String> errors = new ArrayList<>();
    for (int i = 0; i < annotations.length; ++i) {
      final String group = matcher.group(1 + i * 2);
      if (group == null) {
        unmatchedId = i;
        break;
      }

      if (group.length() == 0)
        continue;

      final Annotation annotation = annotations[i];
      if (annotation instanceof NumberElement)
        errors.addAll(verifyElement(verifyNumberElement, (NumberElement)annotation, i, getMembersInGroup(group, members)));
      else if (annotation instanceof StringElement)
        errors.addAll(verifyElement(verifyStringElement, (StringElement)annotation, i, getMembersInGroup(group, members)));

      matchedLength += group.length();
    }

    if (matchedLength == encoding.length())
      return errors.size() == 0 ? null : errors;

    if (unmatchedId == -1)
      unmatchedId = (matcher.groupCount() - 1) / 2;

    errors.add("Invalid content was found starting with member index=" + getFirstIndex(encoding.substring(matchedLength)) + " for element id=" + unmatchedId);
    return errors;
  }

  private static List<String> validate(Annotation[] annotations, final String declarerName, final Class<? extends Annotation> arrayAnnotationType, final Object ... members) {
    final String encoding = ArrayUtil.encodeTestString(members);
    annotations = JsonxUtil.flatten(annotations);
    final IdToElement idToElement = JsonxUtil.idToAnnotation(annotations);
    final int[] elementIds = JsonxUtil.getElementIds(annotations);
    if (elementIds.length == 0)
      throw new IllegalStateException(declarerName + ": @" + arrayAnnotationType.getSimpleName() + "(elementIds={}) elementIds cannot be empty");

    annotations = idToElement.get(elementIds);
    final Pattern pattern = toPattern(annotations);
    final List<String> errors = validate(encoding, pattern, annotations, members);
    System.err.println(encoding + " -> " + pattern + ": " + errors);
    return errors;
  }

  protected static List<String> validate(final Field field, final Object[] members) {
    final ArrayProperty property = field.getAnnotation(ArrayProperty.class);
    if (property == null)
      throw new IllegalArgumentException(field.getDeclaringClass().getName() + "." + field.getName() + " does not declare @" + ArrayProperty.class.getSimpleName());

    if (property.type() != ArrayType.class)
      return validate(property.type().getAnnotations(), property.type().getName(), ArrayType.class, members);

    return validate(field.getAnnotations(), field.getDeclaringClass().getName() + "." + field.getName(), ArrayProperty.class, members);
  }

  protected static List<String> validate(final Class<? extends Annotation> annotationType, final Object[] members) {
    return validate(annotationType.getAnnotations(), annotationType.getName(), ArrayType.class, members);
  }

  private ArrayUtil() {
  }
}