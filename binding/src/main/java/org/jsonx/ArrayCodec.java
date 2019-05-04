/* Copyright (c) 2018 Jsonx
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
import java.lang.reflect.Field;
import java.util.List;

import org.openjax.json.JsonReader;
import org.jsonx.ArrayValidator.Relation;
import org.jsonx.ArrayValidator.Relations;
import org.libj.util.function.TriPredicate;

class ArrayCodec extends Codec {
  static Object decodeArray(final ArrayElement element, final Class<? extends Annotation> type, final String token, final JsonReader reader, IdToElement idToElement, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException {
    if (!"[".equals(token))
      return null;

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

    return ArrayCodec.decodeObject(idToElement.get(elementIds), minIterate, maxIterate, idToElement, reader, onPropertyDecode);
  }

  static Object decodeObject(final Annotation[] annotations, final int minIterate, final int maxIterate, final IdToElement idToElement, final JsonReader reader, final TriPredicate<JxObject,String,Object> onPropertyDecode) throws IOException {
    final ArrayDecodeIterator iterator = new ArrayDecodeIterator(reader);
    final Relations relations = new Relations();
    final Error error = ArrayValidator.validate(iterator, 1, annotations, 0, minIterate, maxIterate, 1, idToElement, relations, true, onPropertyDecode, -1);
    if (error != null)
      return error;

    final String token = reader.readToken();
    if (!"]".equals(token))
      return Error.EXPECTED_ARRAY(token, reader.getPosition());

    return relations.deflate();
  }

  static Error encodeArray(final Annotation annotation, final Class<? extends Annotation> type, final Object object, final int index, final Relations relations, IdToElement idToElement, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) {
    if (!(object instanceof List))
      return Error.CONTENT_NOT_EXPECTED(object, -1);

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

    final Relations subRelations = new Relations();
    final Error subError = ArrayValidator.validate((List<?>)object, idToElement, elementIds, subRelations, validate, onPropertyDecode);
    if (validate && subError != null)
      return subError;

    if (annotation != null)
      relations.set(index, new Relation(subRelations, annotation));
    else if (subRelations.size() == 1)
      relations.set(index, subRelations.get(0));

    return null;
  }

  static Object encodeObject(final Field field, final List<Object> value, final boolean validate) throws EncodeException {
    final Relations relations = new Relations();
    final IdToElement idToElement = new IdToElement();
    final int[] elementIds = JsdUtil.digest(field, idToElement);
    final Error error = ArrayValidator.validate(value, idToElement, elementIds, relations, validate, null);
    if (validate && error != null)
      return Error.INVALID_FIELD(field, error);

    return relations;
  }

  final Annotation[] annotations;
  final IdToElement idToElement = new IdToElement();

  ArrayCodec(final ArrayProperty property, final Field field) {
    super(field, property.name(), property.nullable(), property.use());
    final int[] elementIds = JsdUtil.digest(field, idToElement);
    this.annotations = idToElement.get(elementIds);
  }

  @Override
  String elementName() {
    return "array";
  }
}