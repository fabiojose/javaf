package bradesco.tf;

import com.javaf.Application;
import com.javaf.Constants.STRING;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class TFApplication extends Application {
	private static final TFApplication INSTANCE = new TFApplication();
	
	private TFApplication(){
		
	}

	public static synchronized TFApplication getInstance(){
		return INSTANCE;
	}
	
	public void register(final Object name, final OPERATION value){
		OPERATION _previous = TFConstants.APPLICATION.OPERATIONS.get(name);
		
		if(null== _previous){
			logging.info("REGISTRANDO VALOR PARA PROPRIEDADE DE APLICAÇÃO " + name + ": >" + value + "<");
		} else {
			logging.info("REGISTRANDO *NOVO* VALOR PARA PROPRIEDADE DE APLICAÇÃO " + name + ": >" + value + "<, antigo: >" + _previous + "<");
		}
	}
	
	public OPERATION operationOf(final Object name){
		OPERATION _result = null;
		
		_result = TFConstants.APPLICATION.OPERATIONS.get(name);
		logging.debug(name + STRING.IGUAL_SPACE + _result);
		
		return _result;
	}
}
