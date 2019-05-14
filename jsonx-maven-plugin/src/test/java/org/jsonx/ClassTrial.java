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

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openjax.json.JsonReader;
import org.jsonx.ArrayValidator.Relations;
import org.libj.net.MemoryURLStreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

class ClassTrial {
  private static final Logger logger = LoggerFactory.getLogger(ClassTrial.class);

  private static final JxEncoder validEncoder = JxEncoder.get();
  private static final JxEncoder invalidEncoder = new JxEncoder(validEncoder.indent, false);

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
      final AnyProperty anyProperty = field.getAnnotation(AnyProperty.class);
      if (anyProperty != null) {
        AnyTrial.add(trials, field, binding, anyProperty);
        continue;
      }

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

  public int run() throws Exception {
    int count = 0;
    for (final PropertyTrial<?> trial : trials)
      if (trial.kase instanceof ValidCase)
        PropertyTrial.setField(trial.field, trial.object, trial.name, trial.rawValue());

    for (final PropertyTrial<?> trial : trials)
      count += invoke(trial);

    return count;
  }

  private static void testJsonx(final String json) throws IOException, SAXException {
    final String jsonx = JxConverter.jsonToJsonx(new JsonReader(new StringReader(json)));
    final URL url = MemoryURLStreamHandler.createURL(jsonx.getBytes());
    try {
      final String json2 = JxConverter.jsonxToJson(url.openStream(), false);
      assertEquals(json, json2);
    }
    catch (final SAXException e) {
      System.err.println(jsonx);
      throw e;
    }
  }

  private int invoke(final PropertyTrial<?> trial) throws Exception {
    int count = 0;
    final Object before = trial.field.get(trial.object);
    PropertyTrial.setField(trial.field, trial.object, trial.name, trial.rawValue());

    String json = null;
    String value = null;
    Exception exception = null;
    final Relations[] relations = {null};
    try {
      final int[] bounds = {-1, -1};
      json = validEncoder.marshal(binding, (f,n,r,s,e) -> {
        if (f.equals(trial.field) && trial.name.equals(n)) {
          if (bounds[0] != -1)
            throw new IllegalStateException();

          if (r != null) {
            if (relations[0] != null)
              throw new IllegalStateException();

            relations[0] = r;
          }

          bounds[0] = s;
          bounds[1] = e;
        }
      });

      testJsonx(json);

      value = bounds[0] == -1 && bounds[1] == -1 ? null : json.substring(bounds[0], bounds[1]);
    }
    catch (final Exception e) {
      exception = e;
      json = invalidEncoder.marshal(binding);
    }

    assertFalse(value != null && value.startsWith("[") && relations[0] == null);

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

    final Object[] object = {null};
    exception = null;
    try {
      JxDecoder.parseObject(binding.getClass(), new JsonReader(new StringReader(json)), (o,n,v) -> {
        if (n.equals(trial.name)) {
          if (object[0] != null)
            throw new IllegalStateException();

          object[0] = v;
          return true;
        }

        return false;
      });
    }
    catch (final Exception e) {
      exception = e;
    }

    try {
      if (!onDecode(trial, relations[0], object[0], exception))
        for (int i = 0; i < 10; ++i)
          invoke(trial);
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
        assertNotNull(trial.getClass().getSimpleName(), e);

      if (!(e instanceof EncodeException))
        throw e;

      ((FailureCase<PropertyTrial<T>>)trial.kase).onEncode(trial, (EncodeException)e);
    }
    else {
      throw new UnsupportedOperationException("Unsupported " + Case.class.getSimpleName() + " type: " + trial.kase.getClass().getName());
    }
  }

  @SuppressWarnings("unchecked")
  private static <T>boolean onDecode(final PropertyTrial<T> trial, final Relations relations, final Object value, final Exception e) throws Exception {
    if (trial.kase instanceof SuccessCase) {
      if (e != null)
        throw e;

      return ((SuccessCase<PropertyTrial<T>>)trial.kase).onDecode(trial, relations, value);
    }
    else if (trial.kase instanceof FailureCase) {
      assertNotNull(e);
      if (!(e instanceof DecodeException))
        throw e;

      return ((FailureCase<PropertyTrial<T>>)trial.kase).onDecode(trial, (DecodeException)e);
    }
    else {
      throw new UnsupportedOperationException("Unsupported " + Case.class.getSimpleName() + " type: " + trial.kase.getClass().getName());
    }
  }
}