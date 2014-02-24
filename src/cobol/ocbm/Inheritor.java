package cobol.ocbm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * Anota uma subclasse para herdar os mapeamentos da superclasse direta.<br/>
 * Ela tamb�m aponta que poss�veis sobrescritas de mapeamentos est�o diretamente ligados � superclasse direta.
 * @author fabiojm - F�bio Jos� de Moraes
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Inheritor {

}
