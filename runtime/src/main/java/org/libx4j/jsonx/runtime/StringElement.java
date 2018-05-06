package org.libx4j.jsonx.runtime;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Repeatable(StringElements.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface StringElement {
  int id();
  boolean nullable() default true;
  String pattern() default "";
  boolean urlEncode() default false;
  boolean urlDecode() default false;
  int minOccurs() default 0;
  int maxOccurs() default Integer.MAX_VALUE;
  String doc() default "";

}