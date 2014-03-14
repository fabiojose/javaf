package bradesco.tf.visitor;

import bradesco.tf.TFConstants.EVENT;

import com.javaf.ObjectPool;
import com.javaf.pattern.Visitor;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class PesquisarPlainVisitor implements Visitor<ObjectPool, Void> {

	public Void visit(final ObjectPool opool) {

		opool.put(EVENT.EXECUTE_ON_INVALID, Boolean.FALSE);
		opool.put(EVENT.SHOW_MESSAGE,       Boolean.FALSE);
		
		return null;
	}

}
