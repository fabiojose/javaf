package bradesco.tf.mediator;

import bradesco.tf.layout.UtilLayout;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <T>
 */
public abstract class AbstractLayoutEventMediatorTF<T> implements ILayoutEventMediatorTF<T> {

	final protected UtilLayout layout = UtilLayout.getInstance();

}
