package cobol.ocbm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cobol.CobolConstants.OCBM;

/**
 * Sobrescreve em um getter o mapeamento Field de uma determinada classe.
 * @author fabiojm - F�bio Jos� de Moraes
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface AttributeOverride {

	/**
	 * Nome do atributo na classe, que possui um respectivo getter padronizado.
	 * @return
	 */
	String nome() default OCBM.AUSENTE;

	/**
	 * Nome do atributo din�mico na classe, ou seja, algum que n�o exista
	 * @return
	 */
	Dynamic dynamic() default @Dynamic(nome = OCBM.AUSENTE, type = Object.class);
	
	/**
	 * Novo mapeamento que substitui o original.
	 * @return
	 */
	Mapping mapping();

}
