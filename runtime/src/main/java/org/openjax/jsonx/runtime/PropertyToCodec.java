package org.openjax.jsonx.runtime;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

class PropertyToCodec {
  final Map<String,Codec> nameToCodec = new HashMap<>();
  final Map<Field,Codec> fieldToCodec = new HashMap<>();

  public void add(final Codec codec) {
    nameToCodec.put(JsonxUtil.getName(codec.name, codec.field), codec);
    fieldToCodec.put(codec.field, codec);
  }
}
