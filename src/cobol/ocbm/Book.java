package cobol.ocbm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.javaf.Constants.STRING;


/**
 * Configura o Nome do Book e qual sua área de dados.
 * @author fabiojm - Fábio José de Moraes
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Book {

	String nome();
	String area();
	
	/**
	 * Prefixo padrão utilizado em cada campo do book.
	 * @return
	 */
	String prefixo() default STRING.EMPTY;
	
	/**
	 * Campos padronizados para a área de entrada.<br/>
	 * Estes campos não devem estar mapeados no Java Bean.<br/>
	 * Todos os campos configurados devem obrigatoriamente possuir o atributo defaultValue preenchido.
	 * @return
	 */
	Field[] defaults() default {};
	
	/**
	 * Areas do book que devem ser limpas antes da gravacao
	 * @return
	 */
	String[] toClean() default {};
}
