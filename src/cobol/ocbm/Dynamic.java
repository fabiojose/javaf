package cobol.ocbm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Configura uma indicador din�mico
 * @author fabiojm - F�bio Jos� de Moraes
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Dynamic {

	/**
	 * Nome do atributo din�mico
	 * @return
	 */
	String nome();
	
	/**
	 * Tipo do atributo din�mico
	 * @return
	 */
	Class<?> type();
	
}
