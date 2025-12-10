package com.funcom.commons.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface InjectFromProperty {
  public static final String FIELD_NAME = "FIELD_NAME";
  
  String value() default "FIELD_NAME";
  
  boolean isRequired() default true;
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\configuration\InjectFromProperty.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */