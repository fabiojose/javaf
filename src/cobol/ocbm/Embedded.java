package cobol.ocbm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anota um m�todo get como sendo embarcado em uma outra classe.
 * @author fabiojm - F�bio Jos� de Moraes
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Embedded {

}
