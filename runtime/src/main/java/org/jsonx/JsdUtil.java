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
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import org.libj.lang.Annotations;
import org.libj.lang.Classes;
import org.libj.lang.Identifiers;
import org.libj.lang.Strings;
import org.libj.util.ArrayUtil;
import org.libj.util.Comparators;

final class JsdUtil {
  static final Comparator<String> ATTRIBUTES = Comparators.newFixedOrderComparator("jx:ns", "jx:type", "id", "name", "names", "xsi:type", "abstract", "extends", "lang", "type", "field", "types", "booleans", "numbers", "objects", "strings", "elementIds", "scale", "range", "pattern", "use", "minIterate", "maxIterate", "minOccurs", "maxOccurs", "nullable", "decode", "encode");
  private static final char prefix = '_';
  private static final Function<Character,String> classSubs = (final Character c) -> c == null ? "_" : c != '_' ? Integer.toHexString(c) : "__";
  private static final String[] reservedWords = {"java", "org"}; // FIXME: This does not consider root package names of types declared in <binding> tags

  /**
   * Returns the name of this member as a valid Java Identifier in:
   * <ul>
   * <li>Class-Case: upper-CamelCase</li>
   * <li>Instance-Case: lower-camelCase</li>
   * <li>{@code name.length() == 0}: "_$"</li>
   * </ul>
   *
   * @param classCase Whether to return class-case (true), instance-case (false), or with case unchanged (null).
   * @return The name of this member as a valid Java Identifier.
   */
  private static String toIdentifier(final String name, final Boolean classCase) {
    if (name.length() == 0)
      return "_$";

    if (classCase == null)
      return Identifiers.toCamelCase(name, prefix, classSubs);

    if (classCase) {
      final String str = Identifiers.toClassCase(name, prefix, classSubs);
      return str.charAt(0) == prefix && name.charAt(0) != prefix ? str.substring(1) : str;
    }

    return getFieldName(name);
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

  static String getFieldName(final String name) {
    return Identifiers.toInstanceCase(name, prefix, classSubs);
  }

  static String getFieldName(final Method getMethod) {
    return getFieldName(getMethod.getName().substring(3));
  }

  static Class<?> getRealType(final Method getMethod) {
    final Class<?> returnType = getMethod.getReturnType();
    return returnType == Optional.class ? Classes.getGenericParameters(getMethod)[0] : returnType;
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
    final int len0 = getMethodName.length();
    final int len1 = len0 - 1;
    char firstChar = getMethodName.charAt(0);
    final char ch3;
    if (len1 > 2 && firstChar == 'g' && getMethodName.charAt(1) == 'e' && getMethodName.charAt(2) == 't' && (Character.isUpperCase(ch3 = getMethodName.charAt(3)) || !Character.isAlphabetic(ch3)))
      firstChar = 's';

    final Class<?> returnType = getMethod.getReturnType();
    String setMethodName;
    for (final Method method : methods) // [A]
      if (method.getParameterCount() == 1 && method.getParameterTypes()[0] == returnType && (setMethodName = method.getName()).length() == len0 && setMethodName.charAt(0) == firstChar && setMethodName.regionMatches(1, getMethodName, 1, len1))
        return method;

    return null;
  }

  static int[] digest(final Method getMethod, final IdToElement idToElement) {
    final ArrayProperty property = getMethod.getAnnotation(ArrayProperty.class);
    if (property == null)
      throw new IllegalArgumentException("@" + ArrayProperty.class.getSimpleName() + " not found on: " + getFullyQualifiedMethodName(getMethod));

    final Class<? extends Annotation> type = property.type();
    if (type != ArrayType.class)
      return digest(type.getAnnotations(), type.getName(), idToElement);

    return digest(Classes.getAnnotations(getMethod), getFullyQualifiedMethodName(getMethod), idToElement);
  }

  private static int[] checkElementIds(final int[] elementIds, final Annotation annotation, final String declarerName) {
    if (elementIds.length == 0)
      throw new ValidationException("elementIds property cannot be empty: " + declarerName + ": " + Annotations.toSortedString(annotation, ATTRIBUTES, true));

    return elementIds;
  }

  static int[] digest(Annotation[] annotations, final String declarerName, final IdToElement idToElement) {
    idToElement.putAll(annotations);
    final int[] elementIds = (int[])JsdUtil.forEach(annotations, (final Annotation annotation) -> {
      if (annotation instanceof ArrayProperty) {
        final ArrayProperty arrayProperty = (ArrayProperty)annotation;
        idToElement.setMinIterate(arrayProperty.minIterate());
        idToElement.setMaxIterate(arrayProperty.maxIterate());
        return checkElementIds(arrayProperty.elementIds(), annotation, declarerName);
      }

      if (annotation instanceof ArrayType) {
        final ArrayType arrayType = (ArrayType)annotation;
        idToElement.setMinIterate(arrayType.minIterate());
        idToElement.setMaxIterate(arrayType.maxIterate());
        return checkElementIds(arrayType.elementIds(), annotation, declarerName);
      }

      return null;
    });

    if (elementIds == null)
      throw new ValidationException(declarerName + " does not declare @" + ArrayType.class.getSimpleName() + " or @" + ArrayProperty.class.getSimpleName());

    return elementIds;
  }

  static boolean isNullable(final Annotation annotation) {
    if (annotation instanceof StringElement)
      return ((StringElement)annotation).nullable();

    if (annotation instanceof NumberElement)
      return ((NumberElement)annotation).nullable();

    if (annotation instanceof ObjectElement)
      return ((ObjectElement)annotation).nullable();

    if (annotation instanceof ArrayElement)
      return ((ArrayElement)annotation).nullable();

    if (annotation instanceof BooleanElement)
      return ((BooleanElement)annotation).nullable();

    if (annotation instanceof AnyElement)
      return ((AnyElement)annotation).nullable();

    throw new UnsupportedOperationException("Unsupported annotation type: " + annotation.annotationType().getName());
  }

  static int getId(final Annotation annotation) {
    if (annotation instanceof StringElement)
      return ((StringElement)annotation).id();

    if (annotation instanceof NumberElement)
      return ((NumberElement)annotation).id();

    if (annotation instanceof ObjectElement)
      return ((ObjectElement)annotation).id();

    if (annotation instanceof ArrayElement)
      return ((ArrayElement)annotation).id();

    if (annotation instanceof BooleanElement)
      return ((BooleanElement)annotation).id();

    if (annotation instanceof AnyElement)
      return ((AnyElement)annotation).id();

    throw new UnsupportedOperationException("Unsupported annotation type: " + annotation.annotationType().getName());
  }

  static int getMinOccurs(final Annotation annotation) {
    if (annotation instanceof StringElement)
      return ((StringElement)annotation).minOccurs();

    if (annotation instanceof NumberElement)
      return ((NumberElement)annotation).minOccurs();

    if (annotation instanceof ObjectElement)
      return ((ObjectElement)annotation).minOccurs();

    if (annotation instanceof ArrayElement)
      return ((ArrayElement)annotation).minOccurs();

    if (annotation instanceof BooleanElement)
      return ((BooleanElement)annotation).minOccurs();

    if (annotation instanceof AnyElement)
      return ((AnyElement)annotation).minOccurs();

    throw new UnsupportedOperationException("Unsupported annotation type: " + annotation.annotationType().getName());
  }

  static int getMaxOccurs(final Annotation annotation) {
    if (annotation instanceof StringElement)
      return ((StringElement)annotation).maxOccurs();

    if (annotation instanceof NumberElement)
      return ((NumberElement)annotation).maxOccurs();

    if (annotation instanceof ObjectElement)
      return ((ObjectElement)annotation).maxOccurs();

    if (annotation instanceof ArrayElement)
      return ((ArrayElement)annotation).maxOccurs();

    if (annotation instanceof BooleanElement)
      return ((BooleanElement)annotation).maxOccurs();

    if (annotation instanceof AnyElement)
      return ((AnyElement)annotation).maxOccurs();

    throw new UnsupportedOperationException("Unsupported annotation type: " + annotation.annotationType().getName());
  }

  static String getEncode(final Annotation annotation) {
    if (annotation instanceof StringElement)
      return ((StringElement)annotation).encode();

    if (annotation instanceof NumberElement)
      return ((NumberElement)annotation).encode();

    if (annotation instanceof BooleanElement)
      return ((BooleanElement)annotation).encode();

    return null;
  }

  static String getName(final Method getMethod) {
    for (final Annotation annotation : Classes.getAnnotations(getMethod)) { // [A]
      if (annotation instanceof StringProperty)
        return ((StringProperty)annotation).name();

      if (annotation instanceof NumberProperty)
        return ((NumberProperty)annotation).name();

      if (annotation instanceof ObjectProperty)
        return ((ObjectProperty)annotation).name();

      if (annotation instanceof ArrayProperty)
        return ((ArrayProperty)annotation).name();

      if (annotation instanceof BooleanProperty)
        return ((BooleanProperty)annotation).name();

      if (annotation instanceof AnyProperty)
        return ((AnyProperty)annotation).name();
    }

    return null;
  }

  private static final Class<?>[] elementAnnotationClasses = {StringElement.class, NumberElement.class, ObjectElement.class, ArrayElement.class, BooleanElement.class, AnyElement.class};
  private static final Class<?>[] propertyAnnotationClasses = {StringProperty.class, NumberProperty.class, ObjectProperty.class, ArrayProperty.class, BooleanProperty.class, AnyProperty.class};
  private static final Class<?>[] typeAnnotationClasses = {StringType.class, NumberType.class, ArrayType.class, BooleanType.class, AnyType.class};

  private static Object forEach0(final Annotation[] annotations, final Function<Annotation,Object> function) {
    for (final Annotation annotation : annotations) { // [A]
      final Object obj = function.apply(annotation);
      if (obj != null)
        return obj;
    }

    return null;
  }

  private static Object forEach(final Annotation[] annotations, final Function<Annotation,Object> function) {
    for (final Annotation annotation : annotations) { // [A]
      final Class<? extends Annotation> annotationType = annotation.annotationType();
      if (StringElements.class.equals(annotationType))
        forEach0(((StringElements)annotation).value(), function);
      else if (NumberElements.class.equals(annotationType))
        forEach0(((NumberElements)annotation).value(), function);
      else if (ObjectElements.class.equals(annotationType))
        forEach0(((ObjectElements)annotation).value(), function);
      else if (ArrayElements.class.equals(annotationType))
        forEach0(((ArrayElements)annotation).value(), function);
      else if (BooleanElements.class.equals(annotationType))
        forEach0(((BooleanElements)annotation).value(), function);
      else if (AnyElements.class.equals(annotationType))
        forEach0(((AnyElements)annotation).value(), function);
      else if (ArrayUtil.contains(elementAnnotationClasses, annotationType) || ArrayUtil.contains(propertyAnnotationClasses, annotationType) || ArrayUtil.contains(typeAnnotationClasses, annotationType)) {
        final Object obj = function.apply(annotation);
        if (obj != null)
          return obj;
      }
    }

    return null;
  }

  private static void forEach0(final Annotation[] annotations, final Consumer<Annotation> consumer) {
    for (final Annotation annotation : annotations) // [A]
      consumer.accept(annotation);
  }

  static void forEach(final Annotation[] annotations, final Consumer<Annotation> consumer) {
    for (final Annotation annotation : annotations) { // [A]
      final Class<? extends Annotation> annotationType = annotation.annotationType();
      if (StringElements.class.equals(annotationType))
        forEach0(((StringElements)annotation).value(), consumer);
      else if (NumberElements.class.equals(annotationType))
        forEach0(((NumberElements)annotation).value(), consumer);
      else if (ObjectElements.class.equals(annotationType))
        forEach0(((ObjectElements)annotation).value(), consumer);
      else if (ArrayElements.class.equals(annotationType))
        forEach0(((ArrayElements)annotation).value(), consumer);
      else if (BooleanElements.class.equals(annotationType))
        forEach0(((BooleanElements)annotation).value(), consumer);
      else if (AnyElements.class.equals(annotationType))
        forEach0(((AnyElements)annotation).value(), consumer);
      else if (ArrayUtil.contains(elementAnnotationClasses, annotationType) || ArrayUtil.contains(propertyAnnotationClasses, annotationType) || ArrayUtil.contains(typeAnnotationClasses, annotationType)) {
        consumer.accept(annotation);
      }
    }
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
      final Throwable cause = e.getCause();
      if (cause instanceof RuntimeException)
        throw (RuntimeException)cause;

      throw new RuntimeException(cause);
    }
  }

  private JsdUtil() {
  }
}