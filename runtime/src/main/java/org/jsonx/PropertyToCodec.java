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

import java.util.HashMap;

class PropertyToCodec {
  private final AnyCodec[] anyCodecs;
  private final HashMap<String,Codec> nameToCodec = new HashMap<>();
  final Codec[] allCodecs;

  PropertyToCodec(final int allTotal, final int anyTotal) {
    this.allCodecs = new Codec[allTotal];
    this.anyCodecs = new AnyCodec[anyTotal];
  }

  void set(final Codec codec, final int allIndex, final int anyIndex) {
    if (codec instanceof AnyCodec)
      anyCodecs[anyIndex] = (AnyCodec)codec;
    else if (nameToCodec.put(codec.name, codec) != null)
      throw new IllegalStateException();

    allCodecs[allIndex] = codec;
  }

  Codec get(final String name) {
    final Codec codec = nameToCodec.get(name);
    if (codec != null)
      return codec;

    for (final AnyCodec anyCodec : anyCodecs) // [A]
      if (anyCodec.pattern().matcher(name).matches())
        return anyCodec;

    return null;
  }
}