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
import java.util.Arrays;
import java.util.List;

import org.fastjax.json.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class Trial {
  private static final Logger logger = LoggerFactory.getLogger(Trial.class);

  enum Label {
    FORM,
    MAX_OCCURS,
    MIN_OCCURS,
    NULLABLE,
    PATTERN,
    RANGE,
    URL_CODEC,
    USE,
    VALID
  }

  private static Trial createValidTrial(final Object binding, final Field field, final Object target, final Object valid) {
    return new Trial(Label.VALID, binding, field, target, valid) {
      @Override
      public void onEncode(final String json, final String name, final String value, final Exception e) {
        assertNull(name + ": " + json, e);
        assertEquals(json, valid == null ? null : String.valueOf(valid), value);
      }

      @Override
      public void onDecode(final String json, final String name, final Object value, final Exception e) {
        assertNull(name + ": " + json, e);
        assertEquals(json, valid, value);
      }
    };
  }

  public static void addTrial(final List<Trial> trials, final Object binding, final Field field, final Object target, final Object valid, final Trial invalid, final Use use) {
    addTrial(trials, binding, field, target, createValidTrial(binding, field, target, valid), invalid, use);
  }

  public static <T>void addTrial(final List<Trial> trials, final Object binding, final Field field, final Object target, final Trial valid, final Trial invalid, final Use use) {
    if (use == Use.REQUIRED) {
      trials.add(new Trial(Label.USE, binding, field, target, null) {
        @Override
        public void onEncode(final String json, final String name, final String value, final Exception e) throws Exception {
          assertNotNull(e);
          if (!(e instanceof EncodeException))
            throw e;

          assertEquals(json, field.getDeclaringClass().getName() + "#" + field.getName() + " is required", e.getMessage());
        }

        @Override
        public void onDecode(final String json, final String name, final Object value, final Exception e) throws Exception {
          assertNotNull(e);
          if (!(e instanceof DecodeException))
            throw e;

          assertTrue(e.getMessage() + " " + json, e.getMessage().startsWith("Property \"" + name + "\" is required"));
        }
      });
    }

    if (invalid != null)
      trials.add(invalid);

    if (valid != null)
      trials.add(valid);
  }

  public static void invoke(final List<Trial> trials) throws Exception {
    for (final Trial trial : trials)
      if (trial.label == Label.VALID)
        trial.setValue(trial.value);

    for (final Trial trial : trials)
      if (trial.label != Label.VALID)
        trial.invoke();
  }

  private final Label label;
  private final Object binding;
  private final Field field;
  private final String name;
  private final Object target;
  private final boolean setValue;
  private final Object value;

  public Trial(final Label label, final Object binding, final Field field, final Object target, final Object value) {
    this.label = label;
    this.binding = binding;
    this.field = field;
    this.name = JsonxUtil.getName(field);
    this.target = target;
    this.setValue = true;
    this.value = value;
    field.setAccessible(true);
  }

  public Trial(final Label label, final Object binding, final Field field, final Object target) {
    this.label = label;
    this.binding = binding;
    this.field = field;
    this.name = JsonxUtil.getName(field);
    this.target = target;
    this.setValue = false;
    this.value = null;
    field.setAccessible(true);
  }

  public abstract void onEncode(final String json, final String name, final String value, final Exception e) throws Exception;
  public abstract void onDecode(final String json, final String name, final Object value, final Exception e) throws Exception;
  private static final JxEncoder validEncoder = new JxEncoder(2, true);
  private static final JxEncoder invalidEncoder = new JxEncoder(2, false);

  private void setValue(final Object value) throws IllegalAccessException {
    field.set(target, List.class.isAssignableFrom(field.getType()) && value.getClass().isArray() ? Arrays.asList((Object[])value) : value);
  }

  public void invoke() throws Exception {
    logger.info("Testing " + label + " on " + field.getDeclaringClass().getSimpleName() + "#" + field.getName());
    final Object before = field.get(target);
    if (setValue)
      setValue(value);

    String json = null;
    String value = null;
    Exception exception = null;
    try {
      final int[] bounds = new int[2];
      json = validEncoder.encode(binding, (f,s,e) -> {
        if (f.equals(field)) {
          bounds[0] = s;
          bounds[1] = e;
        }
      });

      value = json.substring(bounds[0], bounds[1]);
    }
    catch (final Exception e) {
      exception = e;
      json = invalidEncoder.encode(binding);
    }

    try {
      onEncode(json, name, value, exception);

      final Object[] object = new Object[1];
      exception = null;
      try {
        JxDecoder.parseObject(binding.getClass(), new JsonReader(new StringReader(json)), (f,o) -> {
          if (f.equals(field))
            object[0] = o;
        });
      }
      catch (final Exception e) {
        exception = e;
      }
      onDecode(json, name, object[0], exception);
    }
    catch (final Exception e) {
      System.err.println(json);
      throw e;
    }

    field.set(target, before);
  }
}