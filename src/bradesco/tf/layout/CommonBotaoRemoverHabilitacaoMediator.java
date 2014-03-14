package bradesco.tf.layout;

import br.com.bradesco.core.aq.dataservice.IDataServiceNode;
import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import br.com.bradesco.core.aq.service.IOperation;
import bradesco.tf.TFConstants.LAYOUT;
import bradesco.tf.mediator.AbstractLayoutEventMediatorTF;
import bradesco.tf.mediator.MediationException;

import com.javaf.pattern.ICase;

/**
 * 
 * @author fabiojm - Fábioi José de Moraes
 *
 */
@SuppressWarnings("deprecation")
public class CommonBotaoRemoverHabilitacaoMediator extends AbstractLayoutEventMediatorTF<Boolean> {

	public void mediate(IEventsServiceProvider provider, IOperation operation, IDataServiceNode node, ICase<Boolean> theCase) throws MediationException {
	
		final Boolean _returned = theCase.execute();
		if(null!= _returned){
			layout.setEnabled(node, provider, !_returned, LAYOUT.BOTAO_REMOVER);
		}
	}
}
