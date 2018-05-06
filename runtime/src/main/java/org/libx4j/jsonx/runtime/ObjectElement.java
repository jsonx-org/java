package org.libx4j.jsonx.runtime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Repeatable(ObjectElements.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ObjectElement {
  int id();
  Class<?> type();
  boolean nullable() default true;
  int minOccurs() default 0;
  int maxOccurs() default Integer.MAX_VALUE;
  String doc() default "";
}