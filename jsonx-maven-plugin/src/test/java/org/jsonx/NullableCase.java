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

final class NullableCase extends FailureCase<ArrayTrial<Object>> {
  static final NullableCase CASE = new NullableCase();

  @Override
  void onEncode(final JxObject binding, final ArrayTrial<Object> trial, final EncodeException e) throws Exception {
    assertTrue(e.getMessage().endsWith("Illegal value: null"));
  }

  @Override
  boolean onDecode(final ArrayTrial<Object> trial, final DecodeException e) throws Exception {
    assertTrue(e.getMessage(), e.getMessage().contains("Illegal value: null"));
    return true;
  }

  private NullableCase() {
  }
}