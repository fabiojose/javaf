package bradesco.tf.visitor;

import br.com.bradesco.core.aq.service.IOperation;
import bradesco.tf.TransferBeanWrapper;
import bradesco.tf.TFConstants.BUSINESS;
import bradesco.tf.outside.FWOCalling;

import com.javaf.ObjectPool;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class DatastoreReadHasMoreDataVisitor extends TFVisitor<TransferBeanWrapper, Boolean> {

	public Boolean visit(final TransferBeanWrapper wrapper) {
		boolean _result = Boolean.FALSE;
		
		if(null!= wrapper){
			final ObjectPool _opool     = wrapper.get(BUSINESS.OBJECT_POOL, ObjectPool.class);
			final IOperation _operation = wrapper.get(BUSINESS.OPERATION, IOperation.class);
			final Class<?> _caller      = wrapper.get(BUSINESS.CALLER, Class.class);
			
			final String _fwoID         = identify.doFWOCalling(_operation, _caller);
			
			_result = (null!= _opool.get(_fwoID, FWOCalling.class));
		}
		
		return _result;
	}

}
