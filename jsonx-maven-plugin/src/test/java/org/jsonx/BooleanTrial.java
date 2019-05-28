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

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

class BooleanTrial extends PropertyTrial<Boolean> {
  static void add(final List<PropertyTrial<?>> trials, final Field field, final Object object, final BooleanProperty property) {
    logger.debug("Adding: " + field.getDeclaringClass() + "#" + field.getName());
    trials.add(new BooleanTrial(ValidCase.CASE, field, object, createValid(), property));
    if (property.use() == Use.REQUIRED) {
      trials.add(new BooleanTrial(getNullableCase(property.nullable()), field, object, null, property));
    }
    else if (property.nullable()) {
      trials.add(new BooleanTrial(OptionalNullableCase.CASE, field, object, null, property));
      trials.add(new BooleanTrial(OptionalNullableCase.CASE, field, object, Optional.ofNullable(null), property));
    }
    else {
      trials.add(new BooleanTrial(OptionalNotNullableCase.CASE, field, object, null, property));
    }
  }

  static Object createValid() {
    return Math.random() < 0.5 ? Boolean.TRUE : Boolean.FALSE;
  }

  private BooleanTrial(final Case<? extends PropertyTrial<? super Boolean>> kase, final Field field, final Object object, final Object value, final BooleanProperty property) {
    super(kase, field, object, value, property.name(), property.use());
  }
}