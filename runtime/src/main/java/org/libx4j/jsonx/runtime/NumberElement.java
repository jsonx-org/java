package org.libx4j.jsonx.runtime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Repeatable(NumberElements.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NumberElement {
  int id();
  boolean nullable() default true;
  Form form() default Form.REAL;
  String min() default "";
  String max() default "";
  int minOccurs() default 0;
  int maxOccurs() default Integer.MAX_VALUE;
}