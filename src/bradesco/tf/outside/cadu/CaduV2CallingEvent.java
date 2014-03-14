package bradesco.tf.outside.cadu;

import br.com.bradesco.core.aq.dataservice.IDataServiceNode;
import br.com.bradesco.core.aq.exceptions.EventException;
import br.com.bradesco.core.aq.service.IEvent;
import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import br.com.bradesco.core.aq.service.IOperation;
import bradesco.tf.ShowMessage;
import bradesco.tf.TFConstants.EVENT;

import com.javaf.Constants.I18N;
import com.javaf.Constants.STRING;
import com.javaf.javase.lang.reflect.InvokeException;
import com.javaf.javase.logging.Logging;

@SuppressWarnings("deprecation")
public class CaduV2CallingEvent implements IEvent {

	private final ShowMessage show = ShowMessage.getInstance();
	
	public String event(final IEventsServiceProvider provider, final IOperation operation, final IDataServiceNode node) throws EventException {

		String _result = null;
		final CaduV2Calling _cadu = new CaduV2Calling();
		
		try{
			final CaduV2CallingDTO _returned = _cadu.invoke(provider, node, operation);
			
			Logging.loggerOf(getClass()).debug(_returned.toString());
			
			_result = EVENT.EXECUTADO;
		}catch(InvokeException _e){
			_result = EVENT.EXCEPTION;
			show.error(_e, STRING.EMPTY, I18N.CADU_CHAMADA_V2, node);
		}
		
		return _result;
	}

}