package org.libx4j.jsonx.runtime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NumberProperty {
  String name() default "";
  boolean required() default true;
  boolean nullable() default true;
  Form form() default Form.REAL;
  String min() default "";
  String max() default "";
}