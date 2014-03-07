package cobol.ocbm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cobol.CobolConstants.OCBM;

/**
 * Configura uma área de ocorrencias com seu nome e tamanho
 * @author fabiojm - Fábio José de Moraes
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Occurs {

	String area();
	
	/**
	 * Case offset esteja configurado como true, esse sera o valor maximo de ocorrencias vazias que serão geradas.
	 * @return
	 */
	int size() default 0;
	
	/**
	 * Construir offset com o tamanho de size.
	 * @return
	 */
	boolean offset() default false;
	
	/**
	 * Sobrescrita de atributos dos objetos na java.util.List
	 * @return
	 */
	FieldOverrides overrides() default @FieldOverrides({});
	
	/**
	 * Campo cuja a quantidade de ocorrencias dependende.
	 * @return
	 */
	String dependsOn() default OCBM.AUSENTE;
}
