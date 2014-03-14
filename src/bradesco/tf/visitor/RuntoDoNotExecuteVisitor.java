package bradesco.tf.visitor;

import bradesco.tf.TFConstants.EVENT;

import com.javaf.ObjectPool;
import com.javaf.pattern.Visitor;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class RuntoDoNotExecuteVisitor implements Visitor<ObjectPool, Void> {

	public Void visit(final ObjectPool opool) {
		
		if(null!= opool){
			opool.put(EVENT.RUN_TO, Boolean.FALSE);
		}
		
		return null;
	}

}
