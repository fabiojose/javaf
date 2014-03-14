package bradesco.tf.visitor;

import bradesco.tf.UtilIdentify;

import com.javaf.pattern.Visitor;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <T>
 * @param <R>
 */
public abstract class TFVisitor<T, R> implements Visitor<T, R> {
	
	protected final UtilIdentify identify = UtilIdentify.getInstance();
	
}
