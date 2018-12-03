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

import org.fastjax.json.JsonStrings;
import org.fastjax.net.URIComponent;
import org.openjax.jsonx.runtime.ArrayValidator.Relations;

public class UrlCodecCase extends SuccessCase<StringTrial> {
  static final UrlCodecCase CASE = new UrlCodecCase();

  @Override
  void onEncode(final StringTrial trial, final Relations relations, final String value) {
    assertEquals(trial.urlEncode ? URIComponent.encode(trial.value) : trial.value, JsonStrings.unescape(value.substring(1, value.length() - 1)));
  }

  @Override
  void onDecode(final StringTrial trial, final Relations relations, final Object value) {
    assertEquals(trial.urlDecode && !trial.urlEncode ? URIComponent.decode(trial.value) : trial.urlEncode && !trial.urlDecode ? URIComponent.encode(trial.value) : trial.value, value);
  }

  private UrlCodecCase() {
  }
}