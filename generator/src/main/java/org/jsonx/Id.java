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

import static org.libj.lang.Assertions.*;

import java.util.Arrays;
import java.util.zip.CRC32;

import org.libj.lang.Strings;
import org.w3.www._2001.XMLSchema.yAA.$String;

final class Id {
  static String hash(final Object ... variables) {
    final CRC32 crc = new CRC32();
    final byte[] bytes = Arrays.toString(variables).getBytes();
    crc.update(bytes, 0, bytes.length);
    return Long.toString(crc.getValue(), 16);
  }

  static Id hashed(final String designation, final Object ... variables) {
    if (!"!".equals(designation) && !"a".equals(designation) && variables != null && variables.length > 1 && variables[0] != null && !(variables[0] instanceof Bind.Type))
      throw new IllegalArgumentException("First variable expected to be Binding, but was: " + Arrays.toString(variables));

    return variables == null || variables.length == 0 ? new Id(designation) : new Id(designation + hash(variables));
  }

  static Id named(final Registry.Type type) {
    return new Id(type != null ? JsdUtil.flipName(type.getName()) : Strings.getRandomAlphaNumeric(6));
  }

  static Id named(final Class<?> type) {
    return new Id(JsdUtil.flipName(type.getName()));
  }

  static Id named(final $String name) {
    return new Id(name.text());
  }

  private final String id;

  private Id(final String id) {
    this.id = assertNotNull(id);
  }

  private Id(final char id) {
    this.id = String.valueOf(id);
  }

  String getPrefix() {
    final int c = id.indexOf(':');
    return c < 0 ? null : id.substring(0, c);
  }

  String getLocalName() {
    final int c = id.indexOf(':');
    return c < 0 ? id : id.substring(c + 1);
  }

  @Override
  public boolean equals(final Object obj) {
    return obj == this || obj instanceof Id && toString().equals(obj.toString());
  }

  @Override
  public int hashCode() {
    return 31 + id.hashCode();
  }

  @Override
  public String toString() {
    return id;
  }
}