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

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

import org.openjax.standard.json.JsonReader;
import org.openjax.standard.util.function.TriPredicate;
import org.openjax.jsonx.runtime.ArrayValidator.Relations;

class ArrayCodec extends Codec {
  static Relations encode(final Field field, final List<Object> value, final boolean validate) throws EncodeException {
    final Relations relations = new Relations();
    final IdToElement idToElement = new IdToElement();
    final int[] elementIds = JxUtil.digest(field, idToElement);
    final StringBuilder error = ArrayValidator.validate(value, idToElement, elementIds, relations, validate, null);
    if (validate && error != null)
      throw new EncodeException(field.getDeclaringClass().getName() + "#" + field.getName() + ": " + error.toString());

    return relations;
  }

  static Object decode(final Annotation[] annotations, final int minIterate, final int maxIterate, final IdToElement idToElement, final JsonReader reader, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException {
    final ArrayDecodeIterator iterator = new ArrayDecodeIterator(reader);
    final Relations relations = new Relations();
    final StringBuilder error = ArrayValidator.validate(iterator, 1, annotations, 0, minIterate, maxIterate, 1, idToElement, relations, true, onPropertyDecode);
    if (error != null)
      return error;

    final String token = reader.readToken();
    if (!"]".equals(token))
      return "Expected ']', but got '" + token + "'";

    return relations.deflate();
  }

  private final Annotation[] annotations;
  private final IdToElement idToElement = new IdToElement();

  ArrayCodec(final ArrayProperty property, final Field field) {
    super(field, property.name(), property.nullable(), property.use());
    final int[] elementIds = JxUtil.digest(field, idToElement);
    this.annotations = idToElement.get(elementIds);
  }

  Annotation[] getAnnotations() {
    return annotations;
  }

  IdToElement getIdToElement() {
    return this.idToElement;
  }

  @Override
  String elementName() {
    return "array";
  }
}