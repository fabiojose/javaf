package cobol.ocbm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * Anota uma subclasse para herdar os mapeamentos da superclasse direta.<br/>
 * Ela também aponta que possíveis sobrescritas de mapeamentos estão diretamente ligados à superclasse direta.
 * @author fabiojm - Fábio José de Moraes
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Inheritor {

}
