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
@Target(ElementType.METHOD)
public @interface Mapping {

	Field input()  default @Field(nome = OCBM.AUSENTE);
	Field output() default @Field(nome = OCBM.AUSENTE);
	
}
