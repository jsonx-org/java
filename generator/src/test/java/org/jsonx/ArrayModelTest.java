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

import static org.junit.Assert.*;

import java.util.Arrays;

import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Member;
import org.jsonx.www.schema_0_5.xL0gluGCXAA.$Number;
import org.junit.Test;

public class ArrayModelTest {
  private static class Number extends $Number {
    private Number() {
    }

    private Number(final $Number inherits) {
      super(inherits);
    }

    @Override
    protected $Member inherits() {
      return this;
    }
  }

  @Test
  public void testGreatestCommonSuperObject() {
    final Registry registry = new Registry(null, new Settings.Builder().withPrefix(getClass().getPackage().getName()).build());

    final $Number number1 = new Number();
    number1.setName$(new $Number.Name$("integer1"));
    number1.setScale$(new $Number.Scale$(0L));
    final NumberModel model1 = NumberModel.reference(registry, null, number1);

    final $Number number2 = new Number();
    number2.setName$(new $Number.Name$("integer2"));
    number2.setScale$(new $Number.Scale$(0L));
    final NumberModel model2 = NumberModel.reference(registry, null, number2);

    assertEquals(registry.getType(Long.class), ArrayModel.getGreatestCommonSuperType(Arrays.asList(model1, model2)));
  }
}