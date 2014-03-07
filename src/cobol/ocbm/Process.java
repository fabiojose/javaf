package cobol.ocbm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.javaf.Constants.STRING;

import cobol.CobolConstants.OCBM;

/**
 * Configura processo FWO em uma determinada classe
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Process {

	String fluxo();
	String funcionalidade() default STRING.EMPTY;
	
	Book entrada();
	Book saida() default @Book(nome = OCBM.AUSENTE, area = OCBM.AUSENTE);
	
}

