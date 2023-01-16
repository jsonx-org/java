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
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import org.jsonx.ArrayValidator.Relations;
import org.libj.lang.Classes;
import org.libj.lang.Strings;
import org.libj.net.MemoryURLStreamHandler;
import org.openjax.json.JsonReader;
import org.xml.sax.SAXException;

class ClassTrial extends Trial {
  private static final JxEncoder validEncoder = JxEncoder.get();
  private static final JxEncoder invalidEncoder = new JxEncoder(validEncoder.indent, false);

  private final ArrayList<PropertyTrial<?>> trials = new ArrayList<>();
  private final JxObject binding;

  private int count = 0;

  ClassTrial(final Class<? extends JxObject> cls) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException {
    if (cls.isAnnotation() || Modifier.isAbstract(cls.getModifiers()))
      throw new IllegalArgumentException();

    this.binding = cls.getDeclaredConstructor().newInstance();
    createObjectFields(binding);
  }

  private void createObjectFields(final Object target) {
    final Method[] methods = target.getClass().getDeclaredMethods();
    Classes.sortDeclarativeOrder(methods);
    for (final Method getMethod : methods) { // [A]
      if (!Modifier.isPublic(getMethod.getModifiers()) || getMethod.isSynthetic() || getMethod.getReturnType() == void.class || getMethod.getParameterCount() > 0)
        continue;

      final AnyProperty anyProperty = getMethod.getAnnotation(AnyProperty.class);
      if (anyProperty != null) {
        AnyTrial.add(trials, getMethod, JsdUtil.findSetMethod(methods, getMethod), binding, anyProperty);
        continue;
      }

      final ArrayProperty arrayProperty = getMethod.getAnnotation(ArrayProperty.class);
      if (arrayProperty != null) {
        ArrayTrial.add(trials, getMethod, JsdUtil.findSetMethod(methods, getMethod), binding, arrayProperty);
        continue;
      }

      final BooleanProperty booleanProperty = getMethod.getAnnotation(BooleanProperty.class);
      if (booleanProperty != null) {
        BooleanTrial.add(trials, getMethod, JsdUtil.findSetMethod(methods, getMethod), binding, booleanProperty);
        continue;
      }

      final NumberProperty numberProperty = getMethod.getAnnotation(NumberProperty.class);
      if (numberProperty != null) {
        NumberTrial.add(trials, getMethod, JsdUtil.findSetMethod(methods, getMethod), binding, numberProperty);
        continue;
      }

      final ObjectProperty objectProperty = getMethod.getAnnotation(ObjectProperty.class);
      if (objectProperty != null) {
        ObjectTrial.add(trials, getMethod, JsdUtil.findSetMethod(methods, getMethod), target, objectProperty);
        continue;
      }

      final StringProperty stringProperty = getMethod.getAnnotation(StringProperty.class);
      if (stringProperty != null) {
        StringTrial.add(trials, getMethod, JsdUtil.findSetMethod(methods, getMethod), target, stringProperty);
        continue;
      }
    }
  }

  public int run() throws Exception {
    for (int i = 0, i$ = trials.size(); i < i$; ++i) { // [RA]
      final PropertyTrial<?> trial = trials.get(i);
      if (trial.kase instanceof ValidCase)
        PropertyTrial.setField(trial.getMethod, trial.setMethod, trial.object, trial.name, trial.rawValue());
    }

    for (int i = 0, i$ = trials.size(); i < i$; ++i) // [RA]
      invoke(trials.get(i));

    return count;
  }

  static void testJsonx(final String json) throws IOException, SAXException {
    final String jsonx = JxConverter.jsonToJsonx(new JsonReader(json));
    final URL url = MemoryURLStreamHandler.createURL(jsonx.getBytes());
    try (final InputStream in = url.openStream()) {
      final String json2 = JxConverter.jsonxToJson(in, false);
      assertEquals(json, json2);
    }
    catch (final SAXException e) {
      System.err.println(jsonx);
      throw e;
    }
  }

  private class Bounds {
    private Method getMethod;
    private String name;
    private int start;
    private int end;
    private Bounds(final Method getMethod, final String name, final int start, final int end) {
      this.getMethod = getMethod;
      this.name = name;
      this.start = start;
      this.end = end;
    }
  }

  private String testEncode(final PropertyTrial<?> trial, final Relations[] relations) throws Exception {
    PropertyTrial.setField(trial.getMethod, trial.setMethod, trial.object, trial.name, trial.rawValue());

    String json = null;
    String value = null;
    Exception exception = null;
    try {
      final AtomicReference<Bounds> bounds = new AtomicReference<>();
      json = validEncoder.toString(binding, (g, n, r, s, e) -> {
        if (g.equals(trial.getMethod) && trial.name.equals(n)) {
          if (bounds.get() != null)
            throw new IllegalStateException(String.valueOf(bounds.get()));

          if (r != null) {
            if (relations[0] != null)
              throw new IllegalStateException();

            relations[0] = r;
          }

          if (s != -1)
            bounds.set(new Bounds(g, n, s, e));
        }
      });

      testJsonx(json);

      value = bounds.get() == null ? null : json.substring(bounds.get().start, bounds.get().end);
    }
    catch (final Exception e) {
      exception = e;
      json = invalidEncoder.toString(binding);
    }

    assertFalse(value != null && value.startsWith("[") && relations[0] == null);

    try {
      onEncode(binding, trial, relations[0], value, exception);
      ++count;
    }
    catch (final Throwable t) {
//      invoke(trial);
      if (logger.isInfoEnabled()) logger.info(String.format("%06d", count) + " onEncode(" + trial.getMethod.getDeclaringClass().getSimpleName() + "." + trial.getMethod.getName() + "(), " + trial.kase.getClass().getSimpleName() + ")");
      if (logger.isErrorEnabled()) {
        logger.error("  Value: " + Strings.indent(String.valueOf(value), 2));
        logger.error("  JSON: " + Strings.indent(String.valueOf(json), 2));
        logger.error(t.getMessage(), t);
      }

      throw t;
    }

    if (json.contains("\"-")) {
      if (exception != null)
        invalidEncoder.toString(binding);
      else
        validEncoder.toString(binding);
    }

    return json;
  }

  private JxObject testDecode(final PropertyTrial<?> trial, final Relations[] relations, final String json) throws Exception {
    final Object[] object = {null};
    Exception exception = null;
    JxObject decoded = null;
    try {
      decoded = JxDecoder.VALIDATING.parseObject(binding.getClass(), new JsonReader(json), (o, n, v) -> {
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
        for (int i = 0; i < 10; ++i) // [N]
          invoke(trial);

      ++count;
    }
    catch (final Throwable t) {
      if (logger.isInfoEnabled()) logger.info(String.format("%06d", count) + " onDecode(" + trial.getMethod.getDeclaringClass().getSimpleName() + "." + trial.getMethod.getName() + "(), " + trial.kase.getClass().getSimpleName() + ")");
      logger.error("  Value: " + Strings.indent(String.valueOf(object[0]), 2));
      logger.error("  JSON: " + Strings.indent(String.valueOf(json), 2));
      logger.error(t.getMessage(), t);
      throw t;
    }

    return decoded;
  }

  private int invoke(final PropertyTrial<?> trial) throws Exception {
    final Object before = trial.getMethod.invoke(trial.object);

    final Relations[] relations = {null};
    final String json = testEncode(trial, relations);
    final JxObject decoded = testDecode(trial, relations, json);
    if (decoded != null)
      assertEquals(binding, decoded);

    trial.setMethod.invoke(trial.object, before);
    return count;
  }

  @SuppressWarnings("unchecked")
  private static <T>void onEncode(final JxObject binding, final PropertyTrial<T> trial, final Relations relations, final String value, final Exception e) throws Exception {
    if (trial.kase instanceof SuccessCase) {
      if (e != null)
        throw e;

      ((SuccessCase<PropertyTrial<T>>)trial.kase).onEncode(trial, relations, value);
    }
    else if (trial.kase instanceof FailureCase) {
      if (e == null)
        assertNotNull(trial.kase.getClass().getSimpleName() + " " + trial.name + " " + value, e);
      else if (!(e instanceof EncodeException))
        throw e;

      ((FailureCase<PropertyTrial<T>>)trial.kase).onEncode(binding, trial, (EncodeException)e);
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
      assertNotNull(trial.kase.getClass().getSimpleName() + " " + trial.name + " " + value, e);
      if (!(e instanceof DecodeException))
        throw e;

      return ((FailureCase<PropertyTrial<T>>)trial.kase).onDecode(trial, (DecodeException)e);
    }
    else {
      throw new UnsupportedOperationException("Unsupported " + Case.class.getSimpleName() + " type: " + trial.kase.getClass().getName());
    }
  }
}