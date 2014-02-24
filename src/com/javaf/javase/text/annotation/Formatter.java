package com.javaf.javase.text.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.Format;

import com.javaf.javase.text.NullFormat;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Formatter {
	
	String pattern() default "";
	Class<? extends Format> format() default NullFormat.class;
}
