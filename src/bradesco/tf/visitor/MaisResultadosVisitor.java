package bradesco.tf.visitor;

import bradesco.tf.TransferBeanWrapper;
import bradesco.tf.TFConstants.FWO;

import com.javaf.pattern.Visitor;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class MaisResultadosVisitor implements Visitor<TransferBeanWrapper, Boolean> {

	public Boolean visit(final TransferBeanWrapper wrapper) {

		boolean _result = Boolean.FALSE;
		
		if(null!= wrapper){
			final Boolean _more = wrapper.get(FWO.MORE_DATA, Boolean.class);
			_result = (null!= _more && _more);
		}
		
		return _result;
	}

}
