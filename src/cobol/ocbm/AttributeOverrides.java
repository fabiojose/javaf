package cobol.ocbm;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Conjunto de redefini��es.
 * @author fabiojm - F�bio Jos� de Moraes
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AttributeOverrides {

	AttributeOverride[] value();
	
}
