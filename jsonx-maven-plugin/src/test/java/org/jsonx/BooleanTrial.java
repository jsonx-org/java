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

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

final class BooleanTrial extends PropertyTrial<Boolean> {
  static void add(final List<? super PropertyTrial<?>> trials, final Method getMethod, final Method setMethod, final Object object, final BooleanProperty property) {
    if (logger.isDebugEnabled()) { logger.debug("Adding: " + getMethod.getDeclaringClass() + "." + getMethod.getName() + "()"); }
    trials.add(new BooleanTrial(ValidCase.CASE, getMethod, setMethod, object, createValid(JsdUtil.getRealType(getMethod), property.decode()), property));
    if (getMethod.getReturnType().isPrimitive())
      return;

    if (property.use() == Use.REQUIRED) {
      trials.add(new BooleanTrial(getNullableCase(property.nullable()), getMethod, setMethod, object, null, property));
    }
    else if (property.nullable()) {
      trials.add(new BooleanTrial(OptionalNullableCase.CASE, getMethod, setMethod, object, null, property));
      trials.add(new BooleanTrial(OptionalNullableCase.CASE, getMethod, setMethod, object, Optional.ofNullable(null), property));
    }
    else {
      trials.add(new BooleanTrial(OptionalNotNullableCase.CASE, getMethod, setMethod, object, null, property));
    }
  }

  static Object createValid(final Class<?> type, final String decode) {
    final Boolean value = Math.random() < 0.5 ? Boolean.TRUE : Boolean.FALSE;
    if (decode != null && decode.length() > 0)
      return JsdUtil.invoke(JsdUtil.parseExecutable(decode, String.class), String.valueOf(value));

    if (type.isAssignableFrom(String.class))
      return value.toString();

    return value;
  }

  private BooleanTrial(final Case<? extends PropertyTrial<? super Boolean>> kase, final Method getMethod, final Method setMethod, final Object object, final Object value, final BooleanProperty property) {
    super(kase, JsdUtil.getRealType(getMethod), getMethod, setMethod, object, value, property.name(), property.use(), property.decode(), property.encode(), false);
  }
}