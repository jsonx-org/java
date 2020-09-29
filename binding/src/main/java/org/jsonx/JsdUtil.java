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

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;

import org.libj.lang.Annotations;
import org.libj.lang.Classes;
import org.libj.lang.Identifiers;
import org.libj.lang.Strings;
import org.libj.util.FixedOrderComparator;

final class JsdUtil {
  static final FixedOrderComparator<String> ATTRIBUTES = new FixedOrderComparator<>("id", "name", "names", "xsi:type", "abstract", "extends", "lang", "type", "field", "types", "booleans", "numbers", "objects", "strings", "elementIds", "scale", "range", "pattern", "use", "minIterate", "maxIterate", "minOccurs", "maxOccurs", "nullable", "decode", "encode");
  private static final char prefix = '_';
  private static final Function<Character,String> classSubs = c -> c == null ? "_" : c != '_' ? Integer.toHexString(c) : "__";
  private static final Function<Character,String> camelSubs = c -> c == null ? "_" : c == '-' ? "-" : c != '_' ? Integer.toHexString(c) : "__";
  private static final String[] reservedWords = {"java", "org"}; // FIXME: This does not consider root package names of types declared in <binding> tags

  /**
   * Returns the name of this member as a valid Java Identifier in:
   * <ul>
   * <li>Class-Case: upper-CamelCase</li>
   * <li>Instance-Case: lower-camelCase</li>
   * <li>{@code name.length() == 0}: "_$"</li>
   * </ul>
   *
   * @param classCase Whether to return class-case (true), instance-case
   *          (false), or with case unchanged (null).
   * @return The name of this member as a valid Java Identifier.
   */
  private static String toIdentifier(final String name, final Boolean classCase) {
    if (name.length() == 0)
      return "_$";

    if (classCase == null)
      return Identifiers.toCamelCase(name, prefix, camelSubs);

    if (classCase) {
      final String str = Identifiers.toClassCase(name, prefix, classSubs);
      return str.charAt(0) == prefix && name.charAt(0) != prefix ? str.substring(1) : str;
    }

    return Identifiers.toInstanceCase(name, prefix, classSubs);
  }

  static String toIdentifier(final String name) {
    return toIdentifier(name, null);
  }

  static String toClassName(final String name) {
    return toIdentifier(name, true);
  }

  static String toInstanceName(String name) {
    name = toIdentifier(name, false);
    return Arrays.binarySearch(reservedWords, name) < 0 ? name : "_" + name;
  }

  static String getFieldName(final Method getMethod) {
    return Identifiers.toInstanceCase(getMethod.getName().substring(3));
  }

  static Class<?> getRealType(final Method getMethod) {
    return getMethod.getReturnType() == Optional.class ? Classes.getGenericParameters(getMethod)[0] : getMethod.getReturnType();
  }

  static String flipName(String name) {
    int i = name.lastIndexOf('$');
    if (i != -1)
      name = name.replace('$', '-');
    else if ((i = name.lastIndexOf('-')) != -1)
      name = name.replace('-', '$');
    else
      i = name.lastIndexOf('.');

    return i == -1 ? Strings.flipFirstCap(name) : name.substring(0, i + 1) + Strings.flipFirstCap(name.substring(i + 1));
  }

  static String fixReserved(final String name) {
    return "Class".equalsIgnoreCase(name) ? "0lass" : name;
  }

  static String getFullyQualifiedMethodName(final Method getMethod) {
    return getMethod.getDeclaringClass().getName() + "." + getMethod.getName() + "()";
  }

  static Method findSetMethod(final Method[] methods, final Method getMethod) {
    final String getMethodName = getMethod.getName();
    final String setMethodName = getMethodName.startsWith("get") ? "set" + getMethodName.substring(3) : getMethodName;
    for (final Method method : methods)
      if (method.getParameterCount() == 1 && method.getParameterTypes()[0] == getMethod.getReturnType() && setMethodName.equals(method.getName()))
        return method;

    return null;
  }

  static int[] digest(final Method getMethod, final IdToElement idToElement) {
    final ArrayProperty property = getMethod.getAnnotation(ArrayProperty.class);
    if (property == null)
      throw new IllegalArgumentException("@" + ArrayProperty.class.getSimpleName() + " not found on: " + getFullyQualifiedMethodName(getMethod));

    if (property.type() != ArrayType.class)
      return digest(property.type().getAnnotations(), property.type().getName(), idToElement);

    return digest(getMethod.getAnnotations(), getFullyQualifiedMethodName(getMethod), idToElement);
  }

  static int[] digest(Annotation[] annotations, final String declarerName, final IdToElement idToElement) {
    annotations = JsdUtil.flatten(annotations);
    JsdUtil.fillIdToElement(idToElement, annotations);
    Annotation annotation = null;
    int[] elementIds = null;
    for (int i = 0; i < annotations.length; ++i) {
      if (annotations[i] instanceof ArrayType) {
        final ArrayType arrayType = (ArrayType)annotations[i];
        elementIds = arrayType.elementIds();
        idToElement.setMinIterate(arrayType.minIterate());
        idToElement.setMaxIterate(arrayType.maxIterate());
        annotation = arrayType;
        break;
      }

      if (annotations[i] instanceof ArrayProperty) {
        final ArrayProperty arrayProperty = (ArrayProperty)annotations[i];
        elementIds = arrayProperty.elementIds();
        idToElement.setMinIterate(arrayProperty.minIterate());
        idToElement.setMaxIterate(arrayProperty.maxIterate());
        annotation = arrayProperty;
        break;
      }
    }

    if (annotation == null)
      throw new ValidationException(declarerName + " does not declare @" + ArrayType.class.getSimpleName() + " or @" + ArrayProperty.class.getSimpleName());

    if (elementIds == null || elementIds.length == 0)
      throw new ValidationException("elementIds property cannot be empty: " + declarerName + ": " + Annotations.toSortedString(annotation, ATTRIBUTES, true));

    return elementIds;
  }

  static boolean isNullable(final Annotation annotation) {
    if (annotation instanceof AnyElement)
      return ((AnyElement)annotation).nullable();

    if (annotation instanceof ArrayElement)
      return ((ArrayElement)annotation).nullable();

    if (annotation instanceof BooleanElement)
      return ((BooleanElement)annotation).nullable();

    if (annotation instanceof NumberElement)
      return ((NumberElement)annotation).nullable();

    if (annotation instanceof ObjectElement)
      return ((ObjectElement)annotation).nullable();

    if (annotation instanceof StringElement)
      return ((StringElement)annotation).nullable();

    throw new UnsupportedOperationException("Unsupported annotation type: " + annotation.annotationType().getName());
  }

  static int getId(final Annotation annotation) {
    if (annotation instanceof AnyElement)
      return ((AnyElement)annotation).id();

    if (annotation instanceof ArrayElement)
      return ((ArrayElement)annotation).id();

    if (annotation instanceof BooleanElement)
      return ((BooleanElement)annotation).id();

    if (annotation instanceof NumberElement)
      return ((NumberElement)annotation).id();

    if (annotation instanceof ObjectElement)
      return ((ObjectElement)annotation).id();

    if (annotation instanceof StringElement)
      return ((StringElement)annotation).id();

    throw new UnsupportedOperationException("Unsupported annotation type: " + annotation.annotationType().getName());
  }

  static int getMinOccurs(final Annotation annotation) {
    if (annotation instanceof AnyElement)
      return ((AnyElement)annotation).minOccurs();

    if (annotation instanceof ArrayElement)
      return ((ArrayElement)annotation).minOccurs();

    if (annotation instanceof BooleanElement)
      return ((BooleanElement)annotation).minOccurs();

    if (annotation instanceof NumberElement)
      return ((NumberElement)annotation).minOccurs();

    if (annotation instanceof ObjectElement)
      return ((ObjectElement)annotation).minOccurs();

    if (annotation instanceof StringElement)
      return ((StringElement)annotation).minOccurs();

    throw new UnsupportedOperationException("Unsupported annotation type: " + annotation.annotationType().getName());
  }

  static int getMaxOccurs(final Annotation annotation) {
    if (annotation instanceof AnyElement)
      return ((AnyElement)annotation).maxOccurs();

    if (annotation instanceof ArrayElement)
      return ((ArrayElement)annotation).maxOccurs();

    if (annotation instanceof BooleanElement)
      return ((BooleanElement)annotation).maxOccurs();

    if (annotation instanceof NumberElement)
      return ((NumberElement)annotation).maxOccurs();

    if (annotation instanceof ObjectElement)
      return ((ObjectElement)annotation).maxOccurs();

    if (annotation instanceof StringElement)
      return ((StringElement)annotation).maxOccurs();

    throw new UnsupportedOperationException("Unsupported annotation type: " + annotation.annotationType().getName());
  }

  static String getEncode(final Annotation annotation) {
    if (annotation instanceof BooleanElement)
      return ((BooleanElement)annotation).encode();

    if (annotation instanceof NumberElement)
      return ((NumberElement)annotation).encode();

    if (annotation instanceof StringElement)
      return ((StringElement)annotation).encode();

    return null;
  }

  static String getName(final Method getMethod) {
    for (final Annotation annotation : getMethod.getAnnotations()) {
      if (annotation instanceof AnyProperty)
        return ((AnyProperty)annotation).name();

      if (annotation instanceof ArrayProperty)
        return ((ArrayProperty)annotation).name();

      if (annotation instanceof BooleanProperty)
        return ((BooleanProperty)annotation).name();

      if (annotation instanceof NumberProperty)
        return ((NumberProperty)annotation).name();

      if (annotation instanceof ObjectProperty)
        return ((ObjectProperty)annotation).name();

      if (annotation instanceof StringProperty)
        return ((StringProperty)annotation).name();
    }

    return null;
  }

  static void fillIdToElement(final IdToElement idToElement, Annotation[] annotations) {
    annotations = JsdUtil.flatten(annotations);
    for (final Annotation annotation : annotations) {
      if (annotation instanceof AnyElement)
        idToElement.put(((AnyElement)annotation).id(), annotation);
      else if (annotation instanceof ArrayElement)
        idToElement.put(((ArrayElement)annotation).id(), annotation);
      else if (annotation instanceof BooleanElement)
        idToElement.put(((BooleanElement)annotation).id(), annotation);
      else if (annotation instanceof NumberElement)
        idToElement.put(((NumberElement)annotation).id(), annotation);
      else if (annotation instanceof ObjectElement)
        idToElement.put(((ObjectElement)annotation).id(), annotation);
      else if (annotation instanceof StringElement)
        idToElement.put(((StringElement)annotation).id(), annotation);
    }
  }

  static Annotation[] flatten(final Annotation[] annotations) {
    return flatten(annotations, 0, 0);
  }

  private static Annotation[] flatten(final Annotation[] annotations, final int index, final int depth) {
    if (index == annotations.length)
      return new Annotation[depth];

    final Annotation annotation = annotations[index];
    final Annotation[] repeatable;
    if (AnyElements.class.equals(annotation.annotationType()))
      repeatable = ((AnyElements)annotation).value();
    else if (ArrayElements.class.equals(annotation.annotationType()))
      repeatable = ((ArrayElements)annotation).value();
    else if (BooleanElements.class.equals(annotation.annotationType()))
      repeatable = ((BooleanElements)annotation).value();
    else if (NumberElements.class.equals(annotation.annotationType()))
      repeatable = ((NumberElements)annotation).value();
    else if (ObjectElements.class.equals(annotation.annotationType()))
      repeatable = ((ObjectElements)annotation).value();
    else if (StringElements.class.equals(annotation.annotationType()))
      repeatable = ((StringElements)annotation).value();
    else
      repeatable = null;

    if (repeatable == null) {
      final Annotation[] flattened = flatten(annotations, index + 1, depth + 1);
      flattened[depth] = annotation;
      return flattened;
    }

    final Annotation[] flattened = flatten(annotations, index + 1, depth + repeatable.length);
    System.arraycopy(repeatable, 0, flattened, depth, repeatable.length);
    return flattened;
  }

  static Executable parseExecutable(final String identifier, final Class<?> parameterType) {
    try {
      final int i = identifier.lastIndexOf('.');
      final String className = identifier.substring(0, i);
      final String methodName = identifier.substring(i + 1);
      final Executable method;
      if ("this".equals(className)) {
        method = Classes.getCompatibleMethod(parameterType, methodName);
        if (method != null && Modifier.isStatic(method.getModifiers()))
          throw new ValidationException("Method <T super " + parameterType.getName() + ">" + identifier + "(T) is static");
      }
      else {
        final Class<?> cls = Class.forName(className);
        if (methodName.equals("<init>")) {
          method = Classes.getCompatibleConstructor(cls, parameterType);
        }
        else {
          method = Classes.getCompatibleMethod(cls, methodName, parameterType);
          if (method != null && !Modifier.isStatic(method.getModifiers()))
            throw new ValidationException("Method <T super " + parameterType.getName() + ">" + identifier + "(T) is not static");
        }
      }

      if (method != null)
        return method;
    }
    catch (final ClassNotFoundException e) {
      throw new ValidationException("Method <T super " + parameterType.getName() + ">" + identifier + "(T) was not found", e);
    }

    throw new ValidationException("Method <T super " + parameterType.getName() + ">" + identifier + "(T) was not found");
  }

  static Class<?> getReturnType(final Executable executable) {
    if (executable instanceof Constructor)
      return ((Constructor<?>)executable).getDeclaringClass();

    return ((Method)executable).getReturnType();
  }

  static Object invoke(final Executable executable, final Object arg) {
    try {
      if (executable instanceof Constructor)
        return ((Constructor<?>)executable).newInstance(arg);

      final Method method = (Method)executable;
      if (Modifier.isStatic(method.getModifiers()))
        return method.invoke(null, arg);

      return method.invoke(arg);
    }
    catch (final IllegalAccessException | InstantiationException e) {
      throw new UnsupportedOperationException(e);
    }
    catch (final InvocationTargetException e) {
      if (e.getCause() instanceof RuntimeException)
        throw (RuntimeException)e.getCause();

      throw new RuntimeException(e.getCause());
    }
  }

  private JsdUtil() {
  }
}