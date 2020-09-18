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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

import org.libj.lang.Annotations;
import org.libj.lang.Numbers;
import org.libj.lang.Throwables;
import org.libj.util.DelegateList;
import org.libj.util.function.TriPredicate;

final class ArrayValidator {
  static class Relation {
    final Object member;
    final Annotation annotation;

    Relation(final Object member, final Annotation annotation) {
      this.member = member;
      this.annotation = Objects.requireNonNull(annotation);
    }

    private Object deflate() {
      return member instanceof Relations ? ((Relations)member).deflate() : member;
    }

    @Override
    public String toString() {
      return String.valueOf(JsdUtil.getId(annotation));
    }
  }

  static class Relations extends DelegateList<Relation> {
    Relations() {
      super(new ArrayList<>());
    }

    private Relations(final List<Relation> target) {
      super(target);
    }

    @Override
    public Relation set(final int index, final Relation element) {
      if (index == size())
        super.add(element);
      else
        super.set(index, element);

      return element;
    }

    List<Object> deflate() {
      for (int i = 0; i < size(); ++i)
        target.set(i, get(i).deflate());

      return target;
    }

    @Override
    public Relations subList(final int fromIndex, final int toIndex) {
      return new Relations(target.subList(fromIndex, toIndex));
    }

    @Override
    public String toString() {
      if (size() == 0)
        return "[]";

      final StringBuilder builder = new StringBuilder("[");
      final Iterator<Relation> iterator = iterator();
      for (int i = 0; iterator.hasNext(); ++i) {
        if (i > 0)
          builder.append(", ");

        builder.append(iterator.next());
      }

      return builder.append(']').toString();
    }
  }

  private static int getNextRequiredElement(final Annotation[] annotations, final int a, int occurrence) {
    for (int i = a; i < annotations.length; ++i) {
      final Annotation annotation = annotations[i];
      final int minOccurs = JsdUtil.getMinOccurs(annotation);
      if (occurrence <= minOccurs)
        return i;

      occurrence = 1;
    }

    return -1;
  }

  /**
   * Creates a signature of the start of an iteration of element attributes at
   * the specified member index, annotation index, and annotation occurrence.
   * <p>
   * <b>Note:</b> For performance reasons, each of the arguments are converted
   * to an unsigned short, which reduces their max value to 65535. This value
   * represents the maximum number of members, annotations and occurrences that
   * are supported by the {@link ArrayValidator}.
   *
   * @param index The index of the member.
   * @param a The index of the annotation.
   * @param occurrence The occurrence of annotation.
   * @return a signature of the start of an iteration of element attributes at
   *         the specified indices.
   */
  private static long sign(final int index, final int a, final int occurrence) {
    return Numbers.Compound.encode((short)(Short.MIN_VALUE + index), (short)(Short.MIN_VALUE + a), (short)(Short.MIN_VALUE + occurrence), (short)0);
  }

  static Error validate(final ArrayIterator iterator, int occurrence, final boolean iterating, final Annotation[] annotations, final int a, final int minIterate, final int maxIterate, final int iteration, final IdToElement idToElement, final Relations relations, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode, final long lastIterSig) throws IOException {
    while (true) {
      final int index = iterator.nextIndex();
      if (!iterator.hasNext()) {
        if (minIterate == 0 && iteration == 1 && index == 0)
          return null;

        int nextRequiredIndex = getNextRequiredElement(annotations, a, occurrence);
        if (nextRequiredIndex == -1) {
          if (iteration < minIterate)
            nextRequiredIndex = 0;
          else
            return null;
        }

        return index == 0 ? Error.INVALID_CONTENT_IN_EMPTY_NOT_COMPLETE(annotations[nextRequiredIndex]) : Error.INVALID_CONTENT_NOT_COMPLETE(index - 1, annotations[nextRequiredIndex]);
      }

      if (a == annotations.length) {
        if (iteration < maxIterate) {
          final long nextIterSig = sign(index, occurrence, a);
          if (nextIterSig == lastIterSig)
            return Error.LOOP();

          return validate(iterator, 1, false, annotations, 0, minIterate, maxIterate, iteration + 1, idToElement, relations, validate, onPropertyDecode, nextIterSig);
        }

        iterator.next();
        final Object object = ArrayIterator.preview(iterator.current);
        iterator.previous();
        return Error.INVALID_CONTENT_MEMBERS_NOT_EXPECTED(index, annotations[a - 1], object);
      }

      final int minOccurs;
      final int maxOccurs;
      final boolean nullable;
      final Class<? extends Codec> codecType;
      final Annotation annotation = annotations[a];
      if (annotation instanceof AnyElement) {
        final AnyElement element = (AnyElement)annotation;
        minOccurs = element.minOccurs();
        maxOccurs = element.maxOccurs();
        nullable = element.nullable();
        codecType = AnyCodec.class;
      }
      else if (annotation instanceof ArrayElement) {
        final ArrayElement element = (ArrayElement)annotation;
        minOccurs = validate ? element.minOccurs() : 0;
        maxOccurs = validate ? element.maxOccurs() : Integer.MAX_VALUE;
        nullable = element.nullable();
        codecType = ArrayCodec.class;
      }
      else if (annotation instanceof BooleanElement) {
        final BooleanElement element = (BooleanElement)annotation;
        minOccurs = validate ? element.minOccurs() : 0;
        maxOccurs = validate ? element.maxOccurs() : Integer.MAX_VALUE;
        nullable = element.nullable();
        codecType = BooleanCodec.class;
      }
      else if (annotation instanceof NumberElement) {
        final NumberElement element = (NumberElement)annotation;
        minOccurs = validate ? element.minOccurs() : 0;
        maxOccurs = validate ? element.maxOccurs() : Integer.MAX_VALUE;
        nullable = element.nullable();
        codecType = NumberCodec.class;
      }
      else if (annotation instanceof ObjectElement) {
        final ObjectElement element = (ObjectElement)annotation;
        minOccurs = validate ? element.minOccurs() : 0;
        maxOccurs = validate ? element.maxOccurs() : Integer.MAX_VALUE;
        nullable = element.nullable();
        codecType = ObjectCodec.class;
      }
      else if (annotation instanceof StringElement) {
        final StringElement element = (StringElement)annotation;
        minOccurs = validate ? element.minOccurs() : 0;
        maxOccurs = validate ? element.maxOccurs() : Integer.MAX_VALUE;
        nullable = element.nullable();
        codecType = StringCodec.class;
      }
      else {
        throw new UnsupportedOperationException("Unsupported annotation type: " + annotation.annotationType().getName());
      }

      if (minOccurs < 0)
        throw new ValidationException("minOccurs must be a non-negative integer: " + Annotations.toSortedString(annotation, JsdUtil.ATTRIBUTES, true));

      if (maxOccurs < minOccurs)
        throw new ValidationException("minOccurs must be less than or equal to maxOccurs: " + Annotations.toSortedString(annotation, JsdUtil.ATTRIBUTES, true));

      Error error;
      if (iterator.nextIsNull()) {
        if (nullable || !validate) {
          error = null;
          relations.set(index, iterator.currentRelate(annotation));
        }
        else {
          error = Error.ILLEGAL_VALUE_NULL();
        }
      }
      else {
        error = iterator.validate(annotation, index, relations, idToElement, codecType, validate, onPropertyDecode);
      }

  //    System.err.println("m[" + index + "], a[" + a + "], o(" + minOccurs + ", " + maxOccurs + ")[" + occurrence + "], i(" + minIterate + ", " + maxIterate + ")[" + iteration + "]");

      try {
    //    int before;
        if (error != null) {
          error = Error.INVALID_CONTENT_WAS_FOUND(index, annotation).append(error);
        }
        else if (occurrence < minOccurs) {
          // If `minOccurs` is not yet satisfied, require increment `occurrence`
          return rewind(iterator, iterator.nextIndex(), validate(iterator, occurrence + 1, false, annotations, a, minIterate, maxIterate, iteration, idToElement, relations, validate, onPropertyDecode, lastIterSig));
        }
        else {
    //      before = iterator.nextIndex();
          // If `occurrence` is under `maxOccurs`, optional increment `occurrence`.
          if (occurrence < maxOccurs) {
            if (iterating) {
              ++occurrence;
              continue;
            }

            final boolean shouldIterate = a + 1 == annotations.length;
            final int nextIndex = iterator.nextIndex();
            error = validate(iterator, occurrence + 1, shouldIterate, annotations, a, minIterate, maxIterate, iteration, idToElement, relations, validate, onPropertyDecode, lastIterSig);
            error = rewind(iterator, nextIndex, error);
            if (error == null)
              return null;
          }
    //      assertIndex(before, iterator);
        }

        // If `minOccurs` has already been satisfied, then let's first try to skip the next member
        if (minOccurs < occurrence) {
    //      before = iterator.nextIndex();
          iterator.previous();
          final Relation rollback = error != null ? null : relations.get(index);
          if (rewind(iterator, iterator.nextIndex(), validate(iterator, 1, false, annotations, a + 1, minIterate, maxIterate, iteration, idToElement, relations, validate, onPropertyDecode, lastIterSig)) == null)
            return null;

          if (rollback != null)
            relations.set(index, rollback);

          iterator.next();
    //      assertIndex(before, iterator);
        }

        return error != null ? error : rewind(iterator, iterator.nextIndex(), validate(iterator, 1, false, annotations, a + 1, minIterate, maxIterate, iteration, idToElement, relations, validate, onPropertyDecode, lastIterSig));
      }
      catch (final StackOverflowError e) {
        throw e.getMessage() == null ? Throwables.copy(e, new StackOverflowError(String.valueOf(occurrence))) : e;
      }
    }
  }

  private static Error rewind(final ArrayIterator iterator, final int index, final Error error) throws IOException {
    if (error == null)
      return null;

    for (int i = iterator.nextIndex(); i > index; --i)
      iterator.previous();

    return error;
  }

//  private static void assertIndex(final int before, final ArrayIterator iterator) throws IOException {
//    if (iterator.nextIndex() != before)
//      throw new IllegalStateException(iterator.nextIndex() + " != " + before);
//  }

  @SuppressWarnings("unchecked")
  static Error validate(final List<?> members, final IdToElement idToElement, final int[] elementIds, final Relations relations, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) {
    final Annotation[] annotations = idToElement.get(elementIds);
    try {
      return validate(new ArrayEncodeIterator((ListIterator<Object>)members.listIterator()), 1, false, annotations, 0, idToElement.getMinIterate(), idToElement.getMaxIterate(), 1, idToElement, relations, validate, onPropertyDecode, -1);
    }
    catch (final IOException e) {
      throw new IllegalStateException("Should not happen, as this method is only called for encode", e);
    }
  }

  static Error validate(final Class<? extends Annotation> annotationType, final List<?> members, final Relations relations, final boolean validate, final TriPredicate<JxObject,String,Object> onPropertyDecode) {
    final IdToElement idToElement = new IdToElement();
    final int[] elementIds = JsdUtil.digest(annotationType.getAnnotations(), annotationType.getName(), idToElement);
    return validate(members, idToElement, elementIds, relations, validate, onPropertyDecode);
  }

  private ArrayValidator() {
  }
}