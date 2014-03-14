package bradesco.tf.mediator;

import br.com.bradesco.core.aq.dataservice.IDataServiceNode;
import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import br.com.bradesco.core.aq.service.IOperation;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
@SuppressWarnings("deprecation")
public interface IEventMediatorTF {

	void mediate(IEventsServiceProvider provider, IOperation operation, IDataServiceNode node) throws MediationException;
	
}
