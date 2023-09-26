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
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.libj.lang.Classes;
import org.libj.util.function.PentaConsumer;

final class ObjectTrial extends PropertyTrial<Object> {
  private static final ClassToGetMethods classToOrderedMethods = new ClassToGetMethods() {
    @Override
    Method[] getMethods(final Class<? extends JxObject> cls) {
      return cls.getMethods();
    }

    @Override
    public boolean test(final Method method) {
      return true;
    }

    @Override
    void beforePut(final Method[] methods) {
      try {
        Classes.sortDeclarativeOrder(methods, true);
      }
      catch (final ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
    }
  };

  static Object createValid(final Class<?> type) {
    try {
      final Object object = type.getDeclaredConstructor().newInstance();
      final Method[] methods = classToOrderedMethods.get(object.getClass());
      for (final Method getMethod : methods) { // [A]
        if (!Modifier.isPublic(getMethod.getModifiers()) || getMethod.isSynthetic() || getMethod.getReturnType() == void.class || getMethod.getParameterCount() > 0)
          continue;

        final AnyProperty anyProperty = getMethod.getAnnotation(AnyProperty.class);
        if (anyProperty != null) {
          if (anyProperty.use() == Use.REQUIRED || Math.random() < 0.4) {
            final AtomicReference<Object> ref = new AtomicReference<>();
            AnyTrial.createValid(anyProperty, new PentaConsumer<Object,Class<?>,String,String,Annotation>() {
              @Override
              public void accept(final Object value, final Class<?> type, final String decode, final String encode, final Annotation typeAnnotation) {
                ref.set(value);
              }
            });
            setField(getMethod, JsdUtil.findSetMethod(methods, getMethod), object, AnyTrial.createName(anyProperty), ref.get());
          }

          continue;
        }

        final String name = JsdUtil.getName(getMethod);
        final ArrayProperty arrayProperty = getMethod.getAnnotation(ArrayProperty.class);
        if (arrayProperty != null) {
          if (arrayProperty.use() == Use.REQUIRED || Math.random() < 0.4) {
            final IdToElement idToElement;
            final int[] elementIds;
            if (arrayProperty.type() == ArrayType.class) {
              idToElement = new IdToElement();
              elementIds = JsdUtil.digest(getMethod, idToElement);
            }
            else {
              idToElement = null;
              elementIds = null;
            }

            setField(getMethod, JsdUtil.findSetMethod(methods, getMethod), object, name, ArrayTrial.createValid(arrayProperty.type(), arrayProperty.minIterate(), arrayProperty.maxIterate(), elementIds, idToElement));
          }

          continue;
        }

        final BooleanProperty booleanProperty = getMethod.getAnnotation(BooleanProperty.class);
        if (booleanProperty != null) {
          if (booleanProperty.use() == Use.REQUIRED || Math.random() < 0.5)
            setField(getMethod, JsdUtil.findSetMethod(methods, getMethod), object, name, BooleanTrial.createValid(JsdUtil.getRealType(getMethod), booleanProperty.decode()));

          continue;
        }

        final NumberProperty numberProperty = getMethod.getAnnotation(NumberProperty.class);
        if (numberProperty != null) {
          if (numberProperty.use() == Use.REQUIRED || Math.random() < 0.5)
            setField(getMethod, JsdUtil.findSetMethod(methods, getMethod), object, name, NumberTrial.createValid(JsdUtil.getRealType(getMethod), numberProperty.decode(), numberProperty.range(), numberProperty.scale()));

          continue;
        }

        final ObjectProperty objectProperty = getMethod.getAnnotation(ObjectProperty.class);
        if (objectProperty != null) {
          if (objectProperty.use() == Use.REQUIRED || Math.random() < 0.4)
            setField(getMethod, JsdUtil.findSetMethod(methods, getMethod), object, name, ObjectTrial.createValid(JsdUtil.getRealType(getMethod)));

          continue;
        }

        final StringProperty stringProperty = getMethod.getAnnotation(StringProperty.class);
        if (stringProperty != null) {
          if (stringProperty.use() == Use.REQUIRED || Math.random() < 0.5)
            setField(getMethod, JsdUtil.findSetMethod(methods, getMethod), object, name, StringTrial.createValid(JsdUtil.getRealType(getMethod), stringProperty.decode(), stringProperty.pattern()));

          continue;
        }
      }

      return object;
    }
    catch (final IllegalAccessException | InstantiationException | NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
    catch (final InvocationTargetException e) {
      final Throwable cause = e.getCause();
      if (cause instanceof RuntimeException)
        throw (RuntimeException)cause;

      throw new RuntimeException(cause);
    }
  }

  static void add(final List<? super PropertyTrial<?>> trials, final Method getMethod, final Method setMethod, final Object object, final ObjectProperty property) {
    if (logger.isDebugEnabled()) { logger.debug("Adding: " + getMethod.getDeclaringClass() + "." + getMethod.getName() + "()"); }
    trials.add(new ObjectTrial(ValidCase.CASE, getMethod, setMethod, object, createValid(JsdUtil.getRealType(getMethod)), property));
    if (getMethod.getReturnType().isPrimitive())
      return;

    if (property.use() == Use.REQUIRED) {
      trials.add(new ObjectTrial(getNullableCase(property.nullable()), getMethod, setMethod, object, null, property));
    }
    else if (property.nullable()) {
      trials.add(new ObjectTrial(OptionalNullableCase.CASE, getMethod, setMethod, object, null, property));
      trials.add(new ObjectTrial(OptionalNullableCase.CASE, getMethod, setMethod, object, Optional.ofNullable(null), property));
    }
    else {
      trials.add(new ObjectTrial(OptionalNotNullableCase.CASE, getMethod, setMethod, object, null, property));
    }
  }

  private ObjectTrial(final Case<? extends PropertyTrial<? super Object>> kase, final Method getMethod, final Method setMethod, final Object object, final Object value, final ObjectProperty property) {
    super(kase, JsdUtil.getRealType(getMethod), getMethod, setMethod, object, value, property.name(), property.use(), null, null, false);
  }
}