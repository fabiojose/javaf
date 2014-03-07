package com.javaf.javase.persistence.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marcar um determinado método, get ou set, como sendo transiente para o processo de persistencia.
 * @author fabiojm - Fábio José de Moraes
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Transient {

	TransientPolicy value() default TransientPolicy.READ_WRITE;
	
}
