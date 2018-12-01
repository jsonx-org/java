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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.fastjax.json.JsonStrings;
import org.fastjax.math.BigDecimals;
import org.fastjax.net.URIComponent;
import org.openjax.jsonx.runtime.Trial.Label;

public class TrialFactory {
  private static void createBoolean(final List<Trial> trials, final Object binding, final Field field, final Object target, final Use use) {
    Trial.addTrial(trials, binding, field, target, Boolean.TRUE, null, use);
  }

  private static void createNumber(final List<Trial> trials, final Object binding, final Field field, final Class<?> numberType, final Object target, final Form form, final String range, final Use use) {
    BigDecimal pass = BigDecimals.TWO;
    if (range != null && range.length() > 0) {
      final Range r = new Range(range);
      pass = (r.getMax() != null ? r.getMax() : BigDecimal.valueOf(987)).subtract(r.getMin() != null ? r.getMin() : BigDecimal.valueOf(345));
      final BigDecimal fail = r.getMin() != null ? r.getMin().subtract(BigDecimal.ONE) : r.getMax().add(BigDecimal.ONE);
      Trial.addTrial(trials, binding, field, target, null, new Trial(Label.RANGE, binding, field, target, numberType == BigInteger.class ? fail.toBigInteger() : fail) {
        @Override
        public void onEncode(final String json, final String name, final String value, final Exception e) throws Exception {
          assertNotNull(name + ": " + json, e);
          if (!(e instanceof EncodeException))
            throw e;

          assertEquals(name + ": " + json, "Range is not matched: " + range, e.getMessage());
        }

        @Override
        public void onDecode(final String json, final String name, final Object value, final Exception e) throws Exception {
          assertNotNull(name + ": " + json, e);
          if (!(e instanceof DecodeException))
            throw e;

          assertTrue(name + ": " + json, e.getMessage().startsWith("Range (" + range + ") is not matched: "));
        }
      }, use);
    }

    if (form != Form.REAL && field.getType() == BigDecimal.class) {
      Trial.addTrial(trials, binding, field, target, null, new Trial(Label.FORM, binding, field, target, BigDecimals.PI) {
        @Override
        public void onEncode(final String json, final String name, final String value, final Exception e) throws Exception {
          assertNotNull(name + ": " + json, e);
          if (!(e instanceof EncodeException))
            throw e;

          assertTrue(name + ": " + json, e.getMessage().contains("...does not match form..."));
        }

        @Override
        public void onDecode(final String json, final String name, final Object value, final Exception e) throws Exception {
          assertNotNull(name + ": " + json, e);
          if (!(e instanceof DecodeException))
            throw e;

          assertTrue(name + ": " + json, e.getMessage().contains("...does not match form..."));
        }
      }, use);
    }

    Trial.addTrial(trials, binding, field, target, field.getType() == BigInteger.class ? pass.toBigInteger() : pass, null, use);
  }

  private static void createObject(final List<Trial> trials, final Object binding, final Field field, final Object target, final Class<?> cls, final Use use) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException {
    final Object object = cls.getDeclaredConstructor().newInstance();
    Trial.addTrial(trials, binding, field, target, object, null, use);
    createObjectFields(trials, binding, cls, object);
  }

  private static void createObjectFields(final List<Trial> trials, final Object root, final Class<?> cls, final Object target) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException {
    for (final Field field : cls.getDeclaredFields()) {
      final ArrayProperty arrayProperty = field.getAnnotation(ArrayProperty.class);
      if (arrayProperty != null)
        throw new UnsupportedOperationException("Arrays are unsupported by this test");

      final BooleanProperty booleanProperty = field.getAnnotation(BooleanProperty.class);
      if (booleanProperty != null) {
        createBoolean(trials, root, field, target, booleanProperty.use());
        continue;
      }

      final NumberProperty numberProperty = field.getAnnotation(NumberProperty.class);
      if (numberProperty != null) {
        createNumber(trials, root, field, field.getType(), target, numberProperty.form(), numberProperty.range(), numberProperty.use());
        continue;
      }

      final ObjectProperty objectProperty = field.getAnnotation(ObjectProperty.class);
      if (objectProperty != null) {
        createObject(trials, root, field, target, field.getType(), objectProperty.use());
        continue;
      }

      final StringProperty stringProperty = field.getAnnotation(StringProperty.class);
      if (stringProperty != null) {
        createString(trials, root, field, target, stringProperty.pattern().length() == 0 ? null : stringProperty.pattern(), stringProperty.urlDecode(), stringProperty.urlEncode(), stringProperty.use());
        continue;
      }
    }
  }

  private static void createString(final List<Trial> trials, final Object binding, final Field field, final Object target, final String pattern, final boolean urlDecode, final boolean urlEncode, final Use use) {
    final String pass = Util.createPassString(pattern, urlDecode, urlEncode);
    if (pattern != null) {
      final String fail = Util.createFailString(pattern, urlEncode, pass);
      Trial.addTrial(trials, binding, field, target, null, new Trial(Label.PATTERN, binding, field, target, fail) {
        @Override
        public void onEncode(final String json, final String name, final String value, final Exception e) throws Exception {
          assertNotNull(name + "(pattern=" + pattern + "): " + fail + " " + value + " " + json, e);
          if (!(e instanceof EncodeException))
            throw e;

          assertTrue(name + ": " + json, e.getMessage().contains(" pattern is not matched: "));
        }

        @Override
        public void onDecode(final String json, final String name, final Object value, final Exception e) throws Exception {
          assertNotNull(name + "(pattern=" + pattern + "): " + fail + " " + value + " " + json, e);
          if (!(e instanceof DecodeException))
            throw e;

          assertTrue(name + ": " + json, e.getMessage().startsWith("Pattern (" + pattern + ") is not matched: "));
        }
      }, use);
    }

    if (urlDecode || urlEncode) {
      Trial.addTrial(trials, binding, field, target, new Trial(Label.URL_CODEC, binding, field, target, pass) {
        @Override
        public void onEncode(final String json, final String name, final String value, final Exception e) {
          assertNull(name + ": " + json, e);
          assertEquals(name + " " + pass + ": " + json, urlEncode ? URIComponent.encode(pass) : pass, JsonStrings.unescape(value.substring(1, value.length() - 1)));
        }

        @Override
        public void onDecode(final String json, final String name, final Object value, final Exception e) {
          assertNull(name + ": " + json, e);
          assertEquals(name + " " + pass + ": " + json, urlDecode && !urlEncode ? URIComponent.decode(pass) : urlEncode && !urlDecode ? URIComponent.encode(pass) : pass, value);
        }
      }, null, use);
    }

    Trial.addTrial(trials, binding, field, target, pass, null, use);
  }

  public static List<Trial> createTrials(final Class<?>[] classes) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException {
    final List<Trial> trials = new ArrayList<>();
    for (final Class<?> cls : classes) {
      if (cls.isAnnotation() || Modifier.isAbstract(cls.getModifiers()))
        continue;

      final Object binding = cls.getDeclaredConstructor().newInstance();
      for (final Field field : cls.getDeclaredFields()) {
        if (List.class.equals(field.getType()))
          throw new UnsupportedOperationException("Arrays are not supported");

        if (Boolean.class.equals(field.getType())) {
          final BooleanProperty property = field.getAnnotation(BooleanProperty.class);
          createBoolean(trials, binding, field, binding, property.use());
        }
        else if (Number.class.isAssignableFrom(field.getType())) {
          final NumberProperty property = field.getAnnotation(NumberProperty.class);
          createNumber(trials, binding, field, field.getType(), binding, property.form(), property.range(), property.use());
        }
        else if (String.class.isAssignableFrom(field.getType())) {
          final StringProperty property = field.getAnnotation(StringProperty.class);
          createString(trials, binding, field, binding, property.pattern().length() == 0 ? null : property.pattern(), property.urlDecode(), property.urlEncode(), property.use());
        }
        else {
          final ObjectProperty property = field.getAnnotation(ObjectProperty.class);
          createObject(trials, binding, field, binding, field.getType(), property.use());
        }
      }
    }

    return trials;
  }
}