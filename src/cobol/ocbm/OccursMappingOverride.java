package cobol.ocbm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Sobrescrever mapeamento de occurrs
 * @author fabiojm - Fábio José de Moraes
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
public @interface OccursMappingOverride {

	/**
	 * Nome do atributo do tipo java.util.List
	 * @return
	 */
	String nome();
	
	Occurs input();
	Occurs output();
}
