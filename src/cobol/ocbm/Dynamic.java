package cobol.ocbm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Configura uma indicador dinâmico
 * @author fabiojm - Fábio José de Moraes
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Dynamic {

	/**
	 * Nome do atributo dinâmico
	 * @return
	 */
	String nome();
	
	/**
	 * Tipo do atributo dinâmico
	 * @return
	 */
	Class<?> type();
	
}
