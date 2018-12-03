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

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.fastjax.util.Classes;

class ObjectTrial extends PropertyTrial<Object> {
  static Object createValid(final Class<?> type) {
    try {
      final Object object = type.getDeclaredConstructor().newInstance();
      final Field[] fields = Classes.getDeclaredFieldsDeep(object.getClass());
      for (int i = 0; i < fields.length; ++i) {
        final Field field = fields[i];
        field.setAccessible(true);
        final ArrayProperty arrayProperty = field.getAnnotation(ArrayProperty.class);
        if (arrayProperty != null) {
          if (arrayProperty.use() == Use.REQUIRED || Math.random() < 0.5) {
            final IdToElement idToElement;
            if (arrayProperty.type() == ArrayType.class) {
              idToElement = new IdToElement();
              JsonxUtil.digest(field, idToElement);
            }
            else {
              idToElement = null;
            }

            field.set(object, ArrayTrial.createValid(arrayProperty.type(), arrayProperty.elementIds(), idToElement));
          }

          continue;
        }

        final BooleanProperty booleanProperty = field.getAnnotation(BooleanProperty.class);
        if (booleanProperty != null) {
          if (booleanProperty.use() == Use.REQUIRED || Math.random() < 0.5)
            field.set(object, Math.random() < 0.5 ? Boolean.TRUE : Boolean.FALSE);

          continue;
        }

        final NumberProperty numberProperty = field.getAnnotation(NumberProperty.class);
        if (numberProperty != null) {
          if (numberProperty.use() == Use.REQUIRED || Math.random() < 0.5)
            field.set(object, NumberTrial.toProperForm(field, numberProperty.form(), NumberTrial.makeValid(numberProperty.range().length() == 0 ? null : new Range(numberProperty.range()))));

          continue;
        }

        final ObjectProperty objectProperty = field.getAnnotation(ObjectProperty.class);
        if (objectProperty != null) {
          if (objectProperty.use() == Use.REQUIRED || Math.random() < 0.5)
            field.set(object, ObjectTrial.createValid(field.getType()));

          continue;
        }

        final StringProperty stringProperty = field.getAnnotation(StringProperty.class);
        if (stringProperty != null) {
          if (stringProperty.use() == Use.REQUIRED || Math.random() < 0.5)
            field.set(object, StringTrial.createValid(stringProperty.pattern(), stringProperty.urlDecode(), stringProperty.urlEncode()));

          continue;
        }
      }

      return object;
    }
    catch (final IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException | ParseException e) {
      throw new UnsupportedOperationException(e);
    }
  }

  static void add(final List<PropertyTrial<?>> trials, final Field field, final Object object, final ObjectProperty property) {
    trials.add(new ObjectTrial(ValidCase.CASE, field, object, createValid(field.getType()), property));
    if (property.use() == Use.REQUIRED)
      trials.add(new ObjectTrial(UseCase.CASE, field, object, null, property));
  }

  private ObjectTrial(final Case<? extends PropertyTrial<? super Object>> kase, final Field field, final Object object, final Object value, final ObjectProperty property) {
    super(kase, field, object, value, property.name(), property.use());
  }
}