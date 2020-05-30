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

import java.util.Map;
import java.util.Optional;

import org.jsonx.ArrayValidator.Relations;

final class OptionalNullableCase extends SuccessCase<PropertyTrial<? super Object>> {
  static final OptionalNullableCase CASE = new OptionalNullableCase();

  @Override
  void onEncode(final PropertyTrial<? super Object> trial, final Relations relations, final String value) throws Exception {
    if (Map.class.isAssignableFrom(trial.getMethod.getReturnType()) || trial.rawValue() instanceof Optional)
      assertEquals("null", value);
    else if (trial.rawValue() == null)
      assertNull(value);
    else
      throw new IllegalStateException(OptionalNullableCase.class.getSimpleName() + " must be used with null or Optional.empty() value");
  }

  @Override
  boolean onDecode(final PropertyTrial<? super Object> trial, final Relations relations, final Object value) throws Exception {
    if (trial.value() != null)
      throw new IllegalStateException(OptionalNullableCase.class.getSimpleName() + " must be used with null or Optional.empty() value");

    assertEquals(trial.rawValue(), value);
    return true;
  }

  private OptionalNullableCase() {
  }
}