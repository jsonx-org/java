/* Copyright (c) 2019 JSONx
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
import java.util.List;

import org.libj.lang.Strings;

final class AnyTrial extends PropertyTrial<Object> {
  static String createName(final AnyProperty property) {
    return Strings.isRegex(property.name()) ? StringTrial.createValid(property.name()) : property.name();
  }

  static Object createValid(final AnyProperty property) {
    return createValid(property, property.types());
  }

  static Object createValid(final AnyElement element) {
    return createValid(element, element.types());
  }

  private static Object createValid(final Annotation annotation, final t[] types) {
    if (types.length == 0)
      return createValid(annotation, AnyType.all);

    final t type = types[(int)(Math.random() * types.length)];
    if (AnyType.isEnabled(type.arrays()))
      return ArrayTrial.createValid(type.arrays(), -1, -1, null, null);

    if (type.booleans())
      return BooleanTrial.createValid();

    if (AnyType.isEnabled(type.numbers()))
      return NumberTrial.createValid(type.numbers().range(), type.numbers().scale());

    if (AnyType.isEnabled(type.objects()))
      return ObjectTrial.createValid(type.objects());

    if (AnyType.isEnabled(type.strings()))
      return StringTrial.createValid(type.strings());

    return createValid(annotation, AnyType.all);
  }

  static void add(final List<? super PropertyTrial<?>> trials, final Field field, final Object object, final AnyProperty property) {
    logger.debug("Adding: " + field.getDeclaringClass() + "#" + field.getName());
    trials.add(new AnyTrial(ValidCase.CASE, field, object, createValid(property), property));
    if (property.nullable()) {
      trials.add(new AnyTrial(OptionalNullableCase.CASE, field, object, null, property));
    }
    else if (property.use() == Use.OPTIONAL) {
      trials.add(new AnyTrial(OptionalNotNullableCase.CASE, field, object, null, property));
    }
  }

  private AnyTrial(final Case<? extends PropertyTrial<? super Object>> kase, final Field field, final Object object, final Object value, final AnyProperty property) {
    super(kase, field, object, value, createName(property), property.use());
  }
}