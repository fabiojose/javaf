package bradesco.tf.visitor;

import bradesco.tf.TFConstants.EVENT;

import com.javaf.ObjectPool;
import com.javaf.pattern.Visitor;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class ExecutePesquisarVisitor implements Visitor<ObjectPool, Boolean> {

	public Boolean visit(ObjectPool opool) {
		Boolean _result = Boolean.TRUE;
		
		if(null!= opool){
			//evita a propagacao
			_result = (Boolean)opool.remove(EVENT.EXECUTE_PESQUISAR, Boolean.TRUE);
		}
		
		return _result;
	}

}
