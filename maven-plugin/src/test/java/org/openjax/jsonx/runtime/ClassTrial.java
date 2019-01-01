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

import static org.junit.Assert.*;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import org.openjax.classic.json.JsonReader;
import org.openjax.jsonx.runtime.ArrayValidator.Relations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ClassTrial {
  private static final Logger logger = LoggerFactory.getLogger(ClassTrial.class);

  private static final JxEncoder validEncoder = new JxEncoder(2, true);
  private static final JxEncoder invalidEncoder = new JxEncoder(2, false);

  private final JxObject binding;
  private final List<PropertyTrial<?>> trials = new ArrayList<>();

  ClassTrial(final Class<? extends JxObject> cls) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException {
    if (cls.isAnnotation() || Modifier.isAbstract(cls.getModifiers()))
      throw new IllegalArgumentException();

    this.binding = cls.getDeclaredConstructor().newInstance();
    createObjectFields(binding);
  }

  private void createObjectFields(final Object target) {
    for (final Field field : target.getClass().getDeclaredFields()) {
      final ArrayProperty arrayProperty = field.getAnnotation(ArrayProperty.class);
      if (arrayProperty != null) {
        ArrayTrial.add(trials, field, binding, arrayProperty);
        continue;
      }

      final BooleanProperty booleanProperty = field.getAnnotation(BooleanProperty.class);
      if (booleanProperty != null) {
        BooleanTrial.add(trials, field, binding, booleanProperty);
        continue;
      }

      final NumberProperty numberProperty = field.getAnnotation(NumberProperty.class);
      if (numberProperty != null) {
        NumberTrial.add(trials, field, binding, numberProperty);
        continue;
      }

      final ObjectProperty objectProperty = field.getAnnotation(ObjectProperty.class);
      if (objectProperty != null) {
        ObjectTrial.add(trials, field, target, objectProperty);
        continue;
      }

      final StringProperty stringProperty = field.getAnnotation(StringProperty.class);
      if (stringProperty != null) {
        StringTrial.add(trials, field, target, stringProperty);
        continue;
      }
    }
  }

  public int invoke() throws Exception {
    int count = 0;
    for (final PropertyTrial<?> trial : trials)
      if (trial.kase instanceof ValidCase)
        trial.setField(trial.rawValue());

    for (final PropertyTrial<?> trial : trials)
      count += invoke(trial);

    return count;
  }

  private int invoke(final PropertyTrial<?> trial) throws Exception {
    int count = 0;
    final Object before = trial.field.get(trial.object);
    trial.setField(trial.rawValue());

    String json = null;
    String value = null;
    Exception exception = null;
    final Relations[] relations = new Relations[1];
    try {
      final int[] bounds = new int[2];
      json = validEncoder.marshal(binding, (f,r,s,e) -> {
        if (f.equals(trial.field)) {
          if (r != null)
            relations[0] = r;

          bounds[0] = s;
          bounds[1] = e;
        }
      });

      value = bounds[0] == -1 && bounds[1] == -1 ? null : json.substring(bounds[0], bounds[1]);
    }
    catch (final Exception e) {
      exception = e;
      json = invalidEncoder.marshal(binding);
    }

    try {
      onEncode(trial, relations[0], value, exception);
      ++count;
    }
    catch (final Throwable t) {
      logger.info(String.format("%06d", count) + " onEncode(" + trial.field.getDeclaringClass().getSimpleName() + "#" + trial.field.getName() + ", " + trial.kase.getClass().getSimpleName() + ")");
      logger.error("  Value: " + String.valueOf(value).replace("\n", "\n  "));
      logger.error("  JSON: " + String.valueOf(json).replace("\n", "\n  "));
      logger.error(t.getMessage(), t);
      throw t;
    }

    final Object[] object = new Object[1];
    exception = null;
    try {
      JxDecoder.parseObject(binding.getClass(), new JsonReader(new StringReader(json)), (o,p,v) -> {
        if (p.equals(trial.name))
          object[0] = v;

        return true;
      });
    }
    catch (final Exception e) {
      exception = e;
    }

    try {
      onDecode(trial, relations[0], object[0], exception);
      ++count;
    }
    catch (final Throwable t) {
      logger.info(String.format("%06d", count) + " onDecode(" + trial.field.getDeclaringClass().getSimpleName() + "#" + trial.field.getName() + ", " + trial.kase.getClass().getSimpleName() + ")");
      logger.error("  Value: " + String.valueOf(object[0]).replace("\n", "\n  "));
      logger.error("  JSON: " + String.valueOf(json).replace("\n", "\n  "));
      logger.error(t.getMessage(), t);
      throw t;
    }

    trial.field.set(trial.object, before);
    return count;
  }

  @SuppressWarnings("unchecked")
  private static <T>void onEncode(final PropertyTrial<T> trial, final Relations relations, final String value, final Exception e) throws Exception {
    if (trial.kase instanceof SuccessCase) {
      if (e != null)
        throw e;

      ((SuccessCase<PropertyTrial<T>>)trial.kase).onEncode(trial, relations, value);
    }
    else if (trial.kase instanceof FailureCase) {
      if (e == null)
        System.out.println();
      assertNotNull(e);
      if (!(e instanceof EncodeException))
        throw e;

      ((FailureCase<PropertyTrial<T>>)trial.kase).onEncode(trial, (EncodeException)e);
    }
    else {
      throw new UnsupportedOperationException("Unsupported " + Case.class.getSimpleName() + " type: " + trial.kase.getClass().getName());
    }
  }

  @SuppressWarnings("unchecked")
  private static <T>void onDecode(final PropertyTrial<T> trial, final Relations relations, final Object value, final Exception e) throws Exception {
    if (trial.kase instanceof SuccessCase) {
      if (e != null)
        throw e;

      ((SuccessCase<PropertyTrial<T>>)trial.kase).onDecode(trial, relations, value);
    }
    else if (trial.kase instanceof FailureCase) {
      assertNotNull(e);
      if (!(e instanceof DecodeException))
        throw e;

      ((FailureCase<PropertyTrial<T>>)trial.kase).onDecode(trial, (DecodeException)e);
    }
    else {
      throw new UnsupportedOperationException("Unsupported " + Case.class.getSimpleName() + " type: " + trial.kase.getClass().getName());
    }
  }
}