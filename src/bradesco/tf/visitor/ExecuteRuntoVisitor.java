package bradesco.tf.visitor;

import bradesco.tf.TFConstants.EVENT;

import com.javaf.ObjectPool;
import com.javaf.pattern.Visitor;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class ExecuteRuntoVisitor implements Visitor<ObjectPool, Boolean> {

	public Boolean visit(ObjectPool opool) {
		Boolean _result = Boolean.TRUE;
		
		if(null!= opool){
			//evitar propagacao
			_result = (Boolean)opool.remove(EVENT.RUN_TO, Boolean.TRUE);
		}
		
		return _result;
	}

}
