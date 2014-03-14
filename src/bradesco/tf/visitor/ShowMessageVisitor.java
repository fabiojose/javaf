package bradesco.tf.visitor;

import bradesco.tf.TFConstants.EVENT;

import com.javaf.ObjectPool;
import com.javaf.pattern.Visitor;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class ShowMessageVisitor implements Visitor<ObjectPool, Boolean> {

	public Boolean visit(final ObjectPool opool) {
		boolean _result = Boolean.TRUE;
		
		if(null!= opool){
			//evita a propagacao
			_result = (Boolean)opool.remove(EVENT.SHOW_MESSAGE, Boolean.TRUE);
		}
		
		return _result;
	}

}
