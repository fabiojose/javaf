package cobol.ocbm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.Format;

import com.javaf.Constants.STRING;
import com.javaf.javase.text.NullFormat;



/**
 * Configura um determinado campo do book em atributo de classe.
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Field {

	/**
	 * Nome do campo.
	 * @see OCBM#AUSENTE
	 * @return
	 */
	String nome();
	String defaultValue() default STRING.EMPTY;
	Class<? extends Format > formatter() default NullFormat.class;
}
