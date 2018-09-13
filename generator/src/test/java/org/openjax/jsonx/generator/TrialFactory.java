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

package org.openjax.jsonx.generator;

import static org.junit.Assert.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.fastjax.math.BigDecimals;
import org.openjax.jsonx.runtime.ArrayElement;
import org.openjax.jsonx.runtime.ArrayProperty;
import org.openjax.jsonx.runtime.ArrayType;
import org.openjax.jsonx.runtime.BooleanElement;
import org.openjax.jsonx.runtime.BooleanProperty;
import org.openjax.jsonx.runtime.DecodeException;
import org.openjax.jsonx.runtime.EncodeException;
import org.openjax.jsonx.runtime.Form;
import org.openjax.jsonx.runtime.IdToElement;
import org.openjax.jsonx.runtime.JsonxUtil;
import org.openjax.jsonx.runtime.NumberElement;
import org.openjax.jsonx.runtime.NumberProperty;
import org.openjax.jsonx.runtime.ObjectElement;
import org.openjax.jsonx.runtime.ObjectProperty;
import org.openjax.jsonx.runtime.StringElement;
import org.openjax.jsonx.runtime.StringProperty;
import org.openjax.jsonx.runtime.Use;

public class TrialFactory {
  private static void processArrayElements(final List<Trial<?>> trials, final Object root, final Field field, final Object target, final Annotation[] annotations, IdToElement idToAnnotation) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException {
    for (final Annotation annotation : annotations) {
      if (annotation instanceof ArrayElement) {
        final ArrayElement element = (ArrayElement)annotation;
        if (element.type() != ArrayType.class)
          processArrayElements(trials, root, field, target, element.type().getAnnotations(), idToAnnotation);
        else
          createArray(trials, root, field, target, idToAnnotation.get(element.elementIds()));
      }
      else if (annotation instanceof BooleanElement) {
        final BooleanElement element = (BooleanElement)annotation;
        createBoolean(trials, root, field, target, null, element.minOccurs(), element.maxOccurs(), element.nullable());
      }
      else if (annotation instanceof NumberElement) {
        final NumberElement element = (NumberElement)annotation;
        createNumber(trials, root, field, target, element.form(), element.range(), null, element.minOccurs(), element.maxOccurs(), element.nullable());
      }
      else if (annotation instanceof ObjectElement) {
        final ObjectElement element = (ObjectElement)annotation;
        createObject(trials, root, field, target, element.type(), null, element.minOccurs(), element.maxOccurs(), element.nullable());
      }
      else if (annotation instanceof StringElement) {
        final StringElement element = (StringElement)annotation;
        createString(trials, root, field, target, element.pattern(), element.urlDecode(), element.urlEncode(), null, element.minOccurs(), element.maxOccurs(), element.nullable());
      }
    }
  }

  private static void createArray(final List<Trial<?>> trials, final Object root, final Field field, final Object target, Annotation[] annotations) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException {
    annotations = JsonxUtil.flatten(annotations);
    final IdToElement idToElement = new IdToElement();
    JsonxUtil.fillIdToElement(idToElement, annotations);
    processArrayElements(trials, root, field, target, annotations, idToElement);
  }

  private static void createBoolean(final List<Trial<?>> trials, final Object root, final Field field, final Object target, final Use use, final int minOccurs, final int maxOccurs, final boolean nullable) {
    Trial.addTrial(trials, root, field, target, true, null, use, minOccurs, maxOccurs, nullable);
  }

  private static void createNumber(final List<Trial<?>> trials, final Object root, final Field field, final Object target, final Form form, final String range, final Use use, final int minOccurs, final int maxOccurs, final boolean nullable) {
    if (form != null) {
      Trial.addTrial(trials, root, field, target, BigInteger.TWO, new Trial<Number>(root, field, BigDecimals.PI) {
        @Override
        public void onEncode(final String json, final Exception e) {
          assertNotNull(e);
          assertEquals(EncodeException.class, e.getClass());
          assertTrue(e.getMessage().contains("...does not match form..."));
        }

        @Override
        public void onDecode(final Number value, final Exception e) {
          assertNotNull(e);
          assertEquals(DecodeException.class, e.getClass());
          assertTrue(e.getMessage().contains("...does not match form..."));
        }
      }, use, minOccurs, maxOccurs, nullable);
    }

    if (range != null) {
      Trial.addTrial(trials, root, field, target, BigInteger.TWO, new Trial<Number>(root, field, BigInteger.TEN) {
        @Override
        public void onEncode(final String json, final Exception e) {
          assertNotNull(e);
          assertEquals(EncodeException.class, e.getClass());
          assertTrue(e.getMessage().contains("...violates range..."));
        }

        @Override
        public void onDecode(final Number value, final Exception e) {
          assertNotNull(e);
          assertEquals(DecodeException.class, e.getClass());
          assertTrue(e.getMessage().contains("...violates range..."));
        }
      }, use, minOccurs, maxOccurs, nullable);
    }
  }

  private static void createObject(final List<Trial<?>> trials, final Object root, final Field field, final Object target, final Class<?> cls, final Use use, final int minOccurs, final int maxOccurs, final boolean nullable) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException {
    final Object object = cls.getDeclaredConstructor().newInstance();
    Trial.addTrial(trials, root, field, target, object, null, use, minOccurs, maxOccurs, nullable);
    createObjectFields(trials, root, cls, object);
  }

  private static void createObjectFields(final List<Trial<?>> trials, final Object root, final Class<?> cls, final Object target) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException {
    for (final Field field : cls.getDeclaredFields()) {
      final ArrayProperty arrayProperty = field.getAnnotation(ArrayProperty.class);
      if (arrayProperty != null) {
        if (arrayProperty.type() != ArrayType.class) {
          final ArrayType arrayType = arrayProperty.type().getAnnotation(ArrayType.class);
          final IdToElement idToElement = new IdToElement();
          JsonxUtil.fillIdToElement(idToElement, arrayProperty.type().getAnnotations());
          processArrayElements(trials, root, field, target, idToElement.get(arrayType.elementIds()), idToElement);
        }
        else {
          final IdToElement idToElement = new IdToElement();
          JsonxUtil.fillIdToElement(idToElement, arrayProperty.type().getAnnotations());
          processArrayElements(trials, root, field, target, idToElement.get(arrayProperty.elementIds()), idToElement);
        }

        continue;
      }

      final BooleanProperty booleanProperty = field.getAnnotation(BooleanProperty.class);
      if (booleanProperty != null) {
        createBoolean(trials, root, field, target, booleanProperty.use(), 0, 0, true);
        continue;
      }

      final NumberProperty numberProperty = field.getAnnotation(NumberProperty.class);
      if (numberProperty != null) {
        createNumber(trials, root, field, target, numberProperty.form(), numberProperty.range(), numberProperty.use(), 0, 0, true);
        continue;
      }

      final ObjectProperty objectProperty = field.getAnnotation(ObjectProperty.class);
      if (objectProperty != null) {
        createObject(trials, root, field, target, field.getType(), objectProperty.use(), 0, 0, true);
        continue;
      }

      final StringProperty stringProperty = field.getAnnotation(StringProperty.class);
      if (stringProperty != null) {
        createString(trials, root, field, target, stringProperty.pattern(), stringProperty.urlDecode(), stringProperty.urlEncode(), stringProperty.use(), 0, 0, true);
        continue;
      }
    }
  }

  private static void createString(final List<Trial<?>> trials, final Object root, final Field field, final Object target, final String pattern, final boolean urlDecode, final boolean urlEncode, final Use use, final int minOccurs, final int maxOccurs, final boolean nullable) {
    if (pattern != null) {
      Trial.addTrial(trials, root, field, target, "%25", new Trial<>(root, field, target, "~~~") {
        @Override
        public void onEncode(final String json, final Exception e) {
          assertNotNull(e);
          assertEquals(EncodeException.class, e.getClass());
          assertTrue(e.getMessage().contains("...violates range..."));
        }

        @Override
        public void onDecode(final String value, final Exception e) {
          assertNotNull(e);
          assertEquals(DecodeException.class, e.getClass());
          assertTrue(e.getMessage().contains("...violates range..."));
        }
      }, use, minOccurs, maxOccurs, nullable);
    }

    if (urlDecode) {
      Trial.<String>addTrial(trials, root, field, target, new Trial<>(root, field, target, "%25") {
        @Override
        public void onEncode(final String json, final Exception e) {
          assertNull(e);
          assertEquals("%", json);
        }

        @Override
        public void onDecode(final String value, final Exception e) {
          assertNull(e);
          assertEquals("%25", value);
        }
      }, null, use, minOccurs, maxOccurs, nullable);
    }

    if (urlEncode) {
      Trial.<String>addTrial(trials, root, field, target, new Trial<>(root, field, target, "%") {
        @Override
        public void onEncode(final String json, final Exception e) {
          assertNotNull(e);
          assertEquals(EncodeException.class, e.getClass());
          assertTrue(e.getMessage().contains("...violates range..."));
        }

        @Override
        public void onDecode(final String value, final Exception e) {
          assertNotNull(e);
          assertEquals(DecodeException.class, e.getClass());
          assertTrue(e.getMessage().contains("...violates range..."));
        }
      }, null, use, minOccurs, maxOccurs, nullable);
    }
  }

  public static List<Trial<?>> createTrials(final Class<?>[] classes) throws IllegalAccessException, IllegalArgumentException, InstantiationException, InvocationTargetException, NoSuchMethodException {
    final List<Trial<?>> trials = new ArrayList<>();
    for (final Class<?> cls : classes) {
      if (cls.isAnnotation() || Modifier.isAbstract(cls.getModifiers()))
        continue;

      final Object target = cls.getDeclaredConstructor().newInstance();
      for (final Field field : cls.getDeclaredFields()) {
        if (List.class.equals(field.getType())) {
          TrialFactory.createArray(trials, target, field, target, field.getAnnotations());
        }
        else if (Boolean.class.equals(field.getType())) {
          final BooleanProperty property = field.getAnnotation(BooleanProperty.class);
          TrialFactory.createBoolean(trials, target, field, target, property.use(), 0, 0, true);
        }
        else if (Number.class.isAssignableFrom(field.getType())) {
          final NumberProperty property = field.getAnnotation(NumberProperty.class);
          TrialFactory.createNumber(trials, target, field, target, property.form(), property.range(), property.use(), 0, 0, true);
        }
        else if (String.class.isAssignableFrom(field.getType())) {
          final StringProperty property = field.getAnnotation(StringProperty.class);
          TrialFactory.createString(trials, target, field, target, property.pattern(), property.urlDecode(), property.urlEncode(), property.use(), 0, 0, true);
        }
        else {
          final ObjectProperty property = field.getAnnotation(ObjectProperty.class);
          TrialFactory.createObject(trials, target, field, target, field.getType(), property.use(), 0, 0, true);
        }
      }
    }

    return trials;
  }
}