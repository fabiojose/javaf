package bradesco.tf.mediator;

import br.com.bradesco.core.aq.dataservice.IDataServiceNode;
import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import br.com.bradesco.core.aq.service.IOperation;

import com.javaf.pattern.ICase;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <T>
 */
@SuppressWarnings("deprecation")
public interface ILayoutEventMediatorTF<T> {

	void mediate(IEventsServiceProvider provider, IOperation operation, IDataServiceNode node, ICase<T> theCase) throws MediationException;
	
}
