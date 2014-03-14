package bradesco.tf;

import br.com.bradesco.core.aq.dataservice.IContainerObject;
import br.com.bradesco.core.aq.dataservice.IListObject;
import br.com.bradesco.core.aq.dataservice.ISimpleObject;
import bradesco.tf.TFConstants.DSN;
import bradesco.tf.TFConstants.IDENTIFY;

import com.javaf.Constants.INTEGER;
import com.javaf.Constants.REFLECTION;
import com.javaf.Constants.STRING;
import com.javaf.javase.lang.reflect.InvokeException;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
@SuppressWarnings("deprecation")
public final class UtilFWO {
	private static final UtilFWO INSTANCE = new UtilFWO();
	
	final private UtilReflection reflection;
	final private ILogging logging;
	private UtilFWO(){
		reflection = UtilReflection.getInstance();
		logging    = Logging.loggerOf(getClass());
	}
	
	public static synchronized UtilFWO getInstance(){
		return INSTANCE;
	}
	
	public String codeOf(final IContainerObject area){
		String __result = STRING.EMPTY;
		
		final IContainerObject _result = area.getContainerObject(DSN.FWO_RESULT);
		if(null!= _result){			
			final IListObject _messages = _result.getListObject(DSN.FWO_MESSAGES);
			if(null!= _messages && _messages.getAllDataObjects().size() > INTEGER._0){
				final IContainerObject _message = _messages.getContainerObject(INTEGER._0);
				final ISimpleObject _code = _message.getSimpleObject(DSN.FWO_MESSAGES_CODE);
				if(null!= _code){
					__result += _code.getValue();
				}
			}
		}
		
		return __result;
	}
	
	public String resultOf(final IContainerObject area){
		
		String _exception = STRING.EMPTY;
		
		//nome do fluxo
		_exception = STRING.COLCHETES_ABRE + area.getKey();
		
		final IContainerObject _result = area.getContainerObject(DSN.FWO_RESULT);
		if(null!= _result){
			final ISimpleObject _response = _result.getSimpleObject(DSN.FWO_RESPONSE_CODE);
			if(null!= _response){
				_exception += STRING.VIRGULA + STRING.SPACE1 + _response.getValue();
			}
			
			final IListObject _messages = _result.getListObject(DSN.FWO_MESSAGES);
			if(null!= _messages && _messages.getAllDataObjects().size() > INTEGER._0){
				final IContainerObject _message = _messages.getContainerObject(INTEGER._0);
				final ISimpleObject _code = _message.getSimpleObject(DSN.FWO_MESSAGES_CODE);
				if(null!= _code){
					_exception += STRING.VIRGULA + STRING.SPACE1 + _code.getValue() + STRING.COLCHETES_FECHA + STRING.SPACE1;
				}
				
				final ISimpleObject _text = _message.getSimpleObject(DSN.FWO_MESSAGES_TEXT);
				if(null!= _text){
					_exception += _text.getValue();
				}
			}
		}
		
		return _exception;
	}
	
	public void normalize(final IListObject list, final int index){
		try{
			reflection.invoke(list, REFLECTION.METHOD_SET_FW_TYPE, new Object[]{IDENTIFY.LIST});
			reflection.invoke(list, REFLECTION.METHOD_SET_IDX,     new Object[]{String.valueOf(index)});
		} catch(InvokeException _e){
			logging.warn(_e.getMessage(), _e);
		}
	}
	
	public void normalize(final IContainerObject container, final int index){
		try{
			reflection.invoke(container, REFLECTION.METHOD_SET_FW_TYPE, new Object[]{IDENTIFY.REGISTER});
			reflection.invoke(container, REFLECTION.METHOD_SET_IDX,     new Object[]{String.valueOf(index)});
		} catch(InvokeException _e){
			logging.warn(_e.getMessage(), _e);
		}
	}
	
	public void normalize(final ISimpleObject simple, final int index){
		try{
			reflection.invoke(simple, REFLECTION.METHOD_SET_FW_TYPE, new Object[]{IDENTIFY.FIELD});
			reflection.invoke(simple, REFLECTION.METHOD_SET_IDX,     new Object[]{String.valueOf(index)});
		}catch(InvokeException _e){
			logging.warn(_e.getMessage(), _e);
		}
	}
}
