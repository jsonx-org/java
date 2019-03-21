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

class RequiredNotNullableCase extends FailureCase<PropertyTrial<? super Object>> {
  static final RequiredNotNullableCase CASE = new RequiredNotNullableCase();

  @Override
  void onEncode(final PropertyTrial<? super Object> trial, final EncodeException e) throws Exception {
    assertEquals("Property \"" + trial.name + "\" is required: " + trial.field.getDeclaringClass().getName() + "#" + trial.field.getName(), e.getMessage());
  }

  @Override
  void onDecode(final PropertyTrial<? super Object> trial, final DecodeException e) throws Exception {
    assertTrue(e.getMessage(), e.getMessage().contains("Property \"" + trial.name + "\" is required"));
  }

  private RequiredNotNullableCase() {
  }
}