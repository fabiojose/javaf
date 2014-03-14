package bradesco.tf.mediator;

import br.com.bradesco.core.aq.exceptions.BusinessRuleException;
import br.com.bradesco.core.aq.exceptions.RollbackException;
import br.com.bradesco.core.aq.persistence.BradescoPersistenceException;
import br.com.bradesco.core.aq.service.IBusinessRuleServiceProvider;
import bradesco.tf.TransferBeanWrapper;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public interface IBusinessMediatorTF {

	void mediate(final IBusinessRuleServiceProvider provider, final TransferBeanWrapper wrapper) throws BusinessRuleException, RollbackException, BradescoPersistenceException;
	
}
