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

import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.List;

import org.fastjax.json.JsonReader;
import org.openjax.jsonx.runtime.DecodeException;
import org.openjax.jsonx.runtime.EncodeException;
import org.openjax.jsonx.runtime.JxDecoder;
import org.openjax.jsonx.runtime.JxEncoder;
import org.openjax.jsonx.runtime.Use;

public abstract class Trial<T> {
  public static <T>void addTrial(final List<Trial<?>> trials, final Object root, final Field field, final Object target, final T valid, final Trial<T> invalid, final Use use, final int minOccurs, final int maxOccurs, final boolean nullable) {
    addTrial(trials, root, field, target, new Trial<>(root, field, target, valid) {
      @Override
      public void onEncode(final String json, final Exception e) {
        assertNull(e);
        assertEquals(valid, json);
      }

      @Override
      public void onDecode(final T value, final Exception e) {
        assertNull(e);
        assertEquals(valid, value);
      }
    }, invalid, use, minOccurs, maxOccurs, nullable);
  }

  public static <T>void addTrial(final List<Trial<?>> trials, final Object root, final Field field, final Object target, final Trial<T> valid, final Trial<T> invalid, final Use use, final int minOccurs, final int maxOccurs, final boolean nullable) {
    trials.add(valid);

    if (invalid != null)
      trials.add(invalid);

    if (use == Use.REQUIRED) {
      trials.add(new Trial<T>(root, field, null) {
        @Override
        public void onEncode(final String json, final Exception e) {
          assertNotNull(e);
          assertEquals(EncodeException.class, e.getClass());
          assertTrue(e.getMessage().contains("...is required..."));
        }

        @Override
        public void onDecode(final T value, final Exception e) {
          assertNotNull(e);
          assertEquals(DecodeException.class, e.getClass());
          assertTrue(e.getMessage().contains("...is required..."));
        }
      });
    }

    if (!nullable) {
      trials.add(new Trial<T>(root, field, null) {
        @Override
        public void onEncode(final String json, final Exception e) {
          assertNotNull(e);
          assertEquals(EncodeException.class, e.getClass());
          assertTrue(e.getMessage().contains("...cannot be null..."));
        }

        @Override
        public void onDecode(final T value, final Exception e) {
          assertNotNull(e);
          assertEquals(DecodeException.class, e.getClass());
          assertTrue(e.getMessage().contains("...cannot be null..."));
        }
      });
    }

    if (minOccurs == 1) {
      trials.add(new Trial<T>(root, field, target) {
        @Override
        public void onEncode(final String json, final Exception e) {
          assertNotNull(e);
          assertEquals(EncodeException.class, e.getClass());
          assertTrue(e.getMessage().contains("...violates minOccurs..."));
        }

        @Override
        public void onDecode(final T value, final Exception e) {
          assertNotNull(e);
          assertEquals(DecodeException.class, e.getClass());
          assertTrue(e.getMessage().contains("...violates minOccurs..."));
        }
      });
    }

    if (maxOccurs == 1) {
      trials.add(new Trial<>(root, field, target, new Object[] {valid, valid}) {
        @Override
        public void onEncode(final String json, final Exception e) {
          assertNotNull(e);
          assertEquals(EncodeException.class, e.getClass());
          assertTrue(e.getMessage().contains("...violates maxOccurs..."));
        }

        @Override
        public void onDecode(final Object[] value, final Exception e) {
          assertNotNull(e);
          assertEquals(DecodeException.class, e.getClass());
          assertTrue(e.getMessage().contains("...violates maxOccurs..."));
        }
      });
    }
  }

  private final Object root;
  private final Field field;
  private final Object target;
  private final boolean setValue;
  private final T value;

  public Trial(final Object root, final Field field, final Object target, final T value) {
    this.root = root;
    this.field = field;
    this.target = target;
    this.setValue = true;
    this.value = value;
    field.setAccessible(true);
  }

  public Trial(final Object root, final Field field, final Object target) {
    this.root = root;
    this.field = field;
    this.target = target;
    this.setValue = false;
    this.value = null;
    field.setAccessible(true);
  }

  public abstract void onEncode(final String json, final Exception e);
  public abstract void onDecode(final T value, final Exception e);
  private static final JxEncoder encoder = new JxEncoder(2);

  public void invoke() throws IllegalAccessException, IllegalArgumentException {
    final Object before = field.get(target);
    field.set(target, value);
    String json = null;
    Exception exception = null;
    try {
      // Need to focus only on the part of the encoded field
      json = encoder.toString(root);
    }
    catch (final Exception e) {
      exception = e;
    }
    onEncode(json, exception);

    T binding = null;
    exception = null;
    try {
      // Need to focus only on the decoded field
      binding = (T)JxDecoder.parseObject(root.getClass(), new JsonReader(new StringReader(json)));
    }
    catch (final Exception e) {
      exception = e;
    }
    onDecode(binding, exception);

    field.set(target, before);
  }
}