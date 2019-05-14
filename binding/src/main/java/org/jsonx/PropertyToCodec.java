/* Copyright (c) 2019 JSONx
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
import java.util.HashMap;
import java.util.Map;

class PropertyToCodec {
  private final Map<String,Codec> anyToCodec = new HashMap<>();
  private final Map<String,Codec> nameToCodec = new HashMap<>();
  final Map<Field,Codec> fieldToCodec = new HashMap<>();

  void add(final Codec codec) {
    (codec instanceof AnyCodec ? anyToCodec : nameToCodec).put(JsdUtil.getName(codec.name, codec.field), codec);
    fieldToCodec.put(codec.field, codec);
  }

  Codec get(final String name) {
    Codec codec = nameToCodec.get(name);
    if (codec != null)
      return codec;

    for (final Map.Entry<String,Codec> entry : anyToCodec.entrySet())
      if (name.matches(entry.getKey()))
        return entry.getValue();

    return null;
  }
}