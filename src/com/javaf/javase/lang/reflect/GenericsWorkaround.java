package com.javaf.javase.lang.reflect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marcador para contornar o apagamento de tipos genéricos pelo compilador.<br/>
 * Tem o objetivo o suporte à classe UtilReflection.
 * 
 * @author fabiojm - Fábio José de Moraes
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface GenericsWorkaround {

	Class<?> value();
	
	/**
	 * Obrigatorio quando anotar classes
	 * @return
	 */
	String property() default "";
}
