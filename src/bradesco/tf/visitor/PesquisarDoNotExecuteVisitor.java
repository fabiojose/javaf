package bradesco.tf.visitor;

import bradesco.tf.TFConstants.EVENT;

import com.javaf.ObjectPool;
import com.javaf.pattern.Visitor;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class PesquisarDoNotExecuteVisitor implements Visitor<ObjectPool, Void> {

	public Void visit(final ObjectPool opool) {
		
		if(null!= opool){
			opool.put(EVENT.EXECUTE_PESQUISAR, Boolean.FALSE);
		}
		
		return null;
	}

}
