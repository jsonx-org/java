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
import java.lang.reflect.Method;
import java.util.List;

import org.libj.util.function.PentaConsumer;

final class AnyTrial extends PropertyTrial<Object> {
  static String createName(final AnyProperty property) {
    final boolean isRegex = Member.isMultiRegex(property.name());
    return isRegex ? (String)StringTrial.createValid(String.class, null, property.name()) : property.name();
  }

  static void createValid(final AnyProperty property, final PentaConsumer<Object,Class<?>,String,String,Annotation> typeDecode) {
    createValid(property, property.types(), typeDecode);
  }

  static void createValid(final AnyElement element, final PentaConsumer<Object,Class<?>,String,String,Annotation> typeDecode) {
    createValid(element, element.types(), typeDecode);
  }

  private static void createValid(final Annotation annotation, final t[] types, final PentaConsumer<Object,Class<?>,String,String,Annotation> typeDecode) {
    if (types.length == 0) {
      createValid(annotation, AnyType.all, typeDecode);
      return;
    }

    final t type = types[(int)(Math.random() * types.length)];
    if (AnyType.isEnabled(type.objects())) {
      final Object value = ObjectTrial.createValid(type.objects());
      typeDecode.accept(value, null, null, null, null);
    }
    else if (AnyType.isEnabled(type.arrays())) {
      final Object value = ArrayTrial.createValid(type.arrays(), -1, -1, null, null);
      typeDecode.accept(value, null, null, null, null);
    }
    else if (AnyType.isEnabled(type.booleans())) {
      final Object value = BooleanTrial.createValid(type.booleans().type(), type.booleans().decode());
      typeDecode.accept(value, type.booleans().type(), type.booleans().decode(), type.booleans().encode(), type.booleans());
    }
    else if (AnyType.isEnabled(type.numbers())) {
      final Object value = NumberTrial.createValid(type.numbers().type(), type.numbers().decode(), type.numbers().range(), type.numbers().scale());
      typeDecode.accept(value, type.numbers().type(), type.numbers().decode(), type.numbers().encode(), type.numbers());
    }
    else if (AnyType.isEnabled(type.strings())) {
      final Object value = StringTrial.createValid(type.strings().type(), type.strings().decode(), type.strings().pattern());
      typeDecode.accept(value, type.strings().type(), type.strings().decode(), type.strings().encode(), type.strings());
    }
    else {
      createValid(annotation, AnyType.all, typeDecode);
    }
  }

  static void add(final List<? super PropertyTrial<?>> trials, final Method getMethod, final Method setMethod, final Object object, final AnyProperty property) {
    if (logger.isDebugEnabled()) { logger.debug("Adding: " + getMethod.getDeclaringClass() + "." + getMethod.getName() + "()"); }
    final PentaConsumer<Object,Class<?>,String,String,Annotation> typeDecode = new PentaConsumer<Object,Class<?>,String,String,Annotation>() {
      @Override
      public void accept(final Object value, final Class<?> type, final String decode, final String encode, final Annotation typeAnnotation) {
        trials.add(new AnyTrial(ValidCase.CASE, getMethod, setMethod, object, value, type, decode, encode, typeAnnotation != null && typeAnnotation.annotationType() == StringType.class, property));
        if (getMethod.getReturnType().isPrimitive())
          return;

        if (property.nullable()) {
          trials.add(new AnyTrial(OptionalNullableCase.CASE, getMethod, setMethod, object, null, null, null, null, typeAnnotation != null && typeAnnotation.annotationType() == StringType.class, property));
        }
        else if (property.use() == Use.OPTIONAL) {
          trials.add(new AnyTrial(OptionalNotNullableCase.CASE, getMethod, setMethod, object, null, null, null, null, typeAnnotation != null && typeAnnotation.annotationType() == StringType.class, property));
        }
      }
    };

    createValid(property, typeDecode);
  }

  private AnyTrial(final Case<? extends PropertyTrial<? super Object>> kase, final Method getMethod, final Method setMethod, final Object object, final Object value, final Class<?> type, final String decode, final String encode, final boolean isStringDefaultType, final AnyProperty property) {
    super(kase, type, getMethod, setMethod, object, value, createName(property), property.use(), decode, encode, isStringDefaultType);
  }
}