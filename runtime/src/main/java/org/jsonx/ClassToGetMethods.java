/* Copyright (c) 2023 JSONx
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
import java.util.HashMap;
import java.util.function.Predicate;

abstract class ClassToGetMethods extends HashMap<Class<? extends JxObject>,Method[]> implements Predicate<Method> {
  private static final Method[] emptyMethods = {};

  private Method[] getGetMethods(final Method[] methods, final int length, final int index, final int depth) {
    if (index == length)
      return depth == 0 ? emptyMethods : new Method[depth];

    final Method method = methods[index];
    if (!test(method))
      return getGetMethods(methods, length, index + 1, depth);

    final Method[] getMethods = getGetMethods(methods, length, index + 1, depth + 1);
    getMethods[depth] = method;
    return getMethods;
  }

  abstract Method[] getMethods(Class<? extends JxObject> cls);

  @Override
  @SuppressWarnings("unchecked")
  public Method[] get(final Object key) {
    Method[] methods = super.get(key);
    if (methods != null)
      return methods;

    final Class<? extends JxObject> cls = (Class<? extends JxObject>)key;
    methods = getMethods(cls);
    methods = getGetMethods(methods, methods.length, 0, 0);
    beforePut(methods);
    super.put(cls, methods);
    return methods;
  }

  /**
   * Called immediately before {@link HashMap#put(Object,Object)} is invoked from {@link ClassToGetMethods#get(Object)}.
   *
   * @param methods The {@link Method}s to be put.
   */
  void beforePut(final Method[] methods) {
  }
}