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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

class PropertyToCodec {
  private final ArrayList<AnyCodec> anyCodecs = new ArrayList<>();
  private final HashMap<String,Codec> nameToCodec = new HashMap<>();
  final HashMap<Method,Codec> getMethodToCodec = new HashMap<>();

  void add(final Codec codec) {
    if (codec instanceof AnyCodec)
      anyCodecs.add((AnyCodec)codec);
    else
      nameToCodec.put(codec.name, codec);

    getMethodToCodec.put(codec.getMethod, codec);
  }

  Codec get(final String name) {
    final Codec codec = nameToCodec.get(name);
    if (codec != null)
      return codec;

    for (int i = 0, i$ = anyCodecs.size(); i < i$; ++i) { // [RA]
      final AnyCodec anyCodec = anyCodecs.get(i);
      if (anyCodec.pattern().matcher(name).matches())
        return anyCodec;
    }

    return null;
  }
}