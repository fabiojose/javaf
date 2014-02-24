package cobol.ocbm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cobol.CobolConstants.OCBM;

/**
 * Mapeamento de ocorrencias.
 * @author fabiojm - F�bio Jos� de Moraes
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OccursMapping {

	Occurs input()  default @Occurs(area = OCBM.AUSENTE);
	Occurs output() default @Occurs(area = OCBM.AUSENTE);
	
}
