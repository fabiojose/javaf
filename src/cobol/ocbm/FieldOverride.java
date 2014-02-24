package cobol.ocbm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cobol.CobolConstants.OCBM;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface FieldOverride {

	String nome() default OCBM.AUSENTE;
	
	Dynamic dynamic() default @Dynamic(nome = OCBM.AUSENTE, type = Object.class);
	
	Field value();
	
}
