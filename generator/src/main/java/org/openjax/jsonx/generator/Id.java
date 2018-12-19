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

import java.util.Arrays;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import org.openjax.jsonx.runtime.JxUtil;
import org.w3.www._2001.XMLSchema.yAA.$String;

class Id {
  private static String hash(final Object ... variables) {
    final Checksum crc = new CRC32();
    final byte[] bytes = Arrays.toString(variables).getBytes();
    crc.update(bytes, 0, bytes.length);
    return Long.toString(crc.getValue(), 16);
  }

  private final String id;

  Id(final $String id) {
    this.id = id.text();
  }

  Id(final Registry.Type type) {
    this.id = JxUtil.flipName(type.getName());
  }

  Id(final Class<?> type) {
    this.id = JxUtil.flipName(type.getName());
  }

  Id(final ArrayModel model) {
    final Object[] variables = new Object[model.members.size()];
    for (int i = 0; i < variables.length; ++i) {
      final Member member = model.members.get(i);
      variables[i] = member.id().toString() + member.nullable;
    }

    this.id = "a" + hash(variables);
  }

  /**
   * Construct {@code Id} for a {@code BooleanModel} instance.
   *
   * @param model The {@code BooleanModel} instance.
   */
  Id(final BooleanModel model) {
    this.id = "b" + hash(model.nullable);
  }

  Id(final NumberModel model) {
    this.id = "n" + hash(model.form, model.range, model.nullable);
  }

  Id(final StringModel model) {
    this.id = "s" + hash(model.pattern, model.nullable);
  }

  Id(final ObjectModel model) {
    this(model.type());
  }

  Id(final Reference model) {
    this.id = "t" + hash(model.model.id().toString(), model.minOccurs, model.maxOccurs, model.nullable, model.use);
  }

  @Override
  public boolean equals(final Object obj) {
    return obj == this || obj instanceof Id && toString().equals(obj.toString());
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return id;
  }
}