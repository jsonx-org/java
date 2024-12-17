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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class PropertyToCodec {
  private static final Logger logger = LoggerFactory.getLogger(PropertyToCodec.class);

  private final AnyCodec[] anyCodecs;
  private final HashMap<String,Codec> nameToCodec = new HashMap<>();
  final Codec[] allCodecs;

  PropertyToCodec(final int allTotal, final int anyTotal) {
    this.allCodecs = new Codec[allTotal];
    this.anyCodecs = new AnyCodec[anyTotal];
  }

  void set(final Codec codec, final int allIndex, final int anyIndex) {
    if (codec instanceof AnyCodec) {
      anyCodecs[anyIndex] = (AnyCodec)codec;
    }
    else {
      final Codec other = nameToCodec.put(codec.name, codec);
      if (other != null)
        if (logger.isWarnEnabled()) { logger.warn("Property \"" + codec.name + "\" in \"" + codec.getMethod.getDeclaringClass().getName() + "\" is masking property \"" + codec.name + "\" in \"" + other.getMethod.getDeclaringClass().getName() + "\""); }
    }

    allCodecs[allIndex] = codec;
  }

  Codec get(final String name) {
    final Codec codec = nameToCodec.get(name);
    if (codec != null)
      return codec;

    for (int i = anyCodecs.length - 1; i >= 0; --i) { // [A]
      final AnyCodec anyCodec = anyCodecs[i];
      if (anyCodec.pattern().matcher(name).matches())
        return anyCodec;
    }

    return null;
  }
}