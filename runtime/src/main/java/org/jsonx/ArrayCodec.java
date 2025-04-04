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

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.util.List;

import org.jsonx.ArrayValidator.Relations;
import org.libj.lang.Numbers.Composite;
import org.libj.util.function.TriPredicate;
import org.openjax.json.JsonReader;

class ArrayCodec extends Codec {
  static Object decodeArray(final ArrayElement element, final Class<? extends Annotation> type, IdToElement idToElement, final String token, final JsonReader reader, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException {
    if (!"[".equals(token))
      return NULL;

    final int[] elementIds;
    final int minIterate;
    final int maxIterate;
    if (type != ArrayType.class) {
      elementIds = JsdUtil.digest(type.getAnnotations(), type.getName(), idToElement = new IdToElement());
      minIterate = idToElement.getMinIterate();
      maxIterate = idToElement.getMaxIterate();
    }
    else if (element != null) {
      minIterate = element.minIterate();
      maxIterate = element.maxIterate();
      elementIds = element.elementIds();
    }
    else {
      throw new IllegalStateException();
    }

    return ArrayCodec.decodeObject(idToElement.get(elementIds), minIterate, maxIterate, idToElement, reader, validate, onPropertyDecode);
  }

  static Object decodeObject(final Annotation[] annotations, final int minIterate, final int maxIterate, final IdToElement idToElement, final JsonReader reader, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException {
    final ArrayDecodeIterator iterator = new ArrayDecodeIterator(reader);
    final Relations relations = new Relations();
    final Error error = ArrayValidator.validate(iterator, 1, false, annotations, 0, minIterate, maxIterate, 1, idToElement, relations, validate, onPropertyDecode, -1);
    if (error != null)
      return error;

    final long offLen = reader.readToken();
    final int off = Composite.decodeInt(offLen, 0);
    final char c0 = reader.bufToChar(off);
    if (c0 != ']')
      return Error.EXPECTED_ARRAY(reader.bufToString(off, Composite.decodeInt(offLen, 1)), reader);

    return relations.deflate();
  }

  static Error encodeArray(final Annotation annotation, final Class<? extends Annotation> type, final Object object, final int index, final Relations relations, IdToElement idToElement, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) {
    if (!(object instanceof List))
      return Error.CONTENT_NOT_EXPECTED(object, null, null);

    final int[] elementIds;
    if (type == ArrayType.class) {
      final ArrayElement element = (ArrayElement)annotation;
      elementIds = element.elementIds();
      idToElement.setMinIterate(element.minIterate());
      idToElement.setMaxIterate(element.maxIterate());
    }
    else {
      elementIds = JsdUtil.digest(type.getAnnotations(), type.getName(), idToElement = new IdToElement());
    }

    // Special case when annotation == null, which happens only from AnyCodec
    final Relations subRelations = annotation == null ? relations : new Relations();
    final Error subError = ArrayValidator.encode((List<?>)object, idToElement, elementIds, subRelations, validate, onPropertyDecode);
    if (validate && subError != null)
      return subError;

    if (annotation != null)
      relations.set(index, subRelations, annotation);

    return null;
  }

  static Error encodeObject(final JxEncoder encoder, final Appendable out, final Relations relations, final String name, final Method getMethod, final List<Object> value, final OnEncode onEncode, final int depth, final boolean validate) throws EncodeException, IOException {
    final IdToElement idToElement = new IdToElement();
    final int[] elementIds = JsdUtil.digest(getMethod, idToElement);
    Error error = ArrayValidator.encode(value, idToElement, elementIds, relations, validate, null);
    if (validate && error != null)
      return Error.INVALID_FIELD(getMethod, error);

    if (onEncode != null)
      onEncode.accept(getMethod, name, relations, -1, -1);

    return encoder.encodeArray(out, getMethod, relations, onEncode, depth);
  }

  final Annotation[] annotations;
  final IdToElement idToElement = new IdToElement();

  ArrayCodec(final ArrayProperty property, final Method getMethod, final Method setMethod) {
    super(getMethod, setMethod, property.name(), property.nullable(), property.use());
    final int[] elementIds = JsdUtil.digest(getMethod, idToElement);
    this.annotations = idToElement.get(elementIds);
  }

  @Override
  Class<?> type() {
    return null;
  }

  @Override
  Executable decode() {
    return null;
  }

  @Override
  String elementName() {
    return "array";
  }
}