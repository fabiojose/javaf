package bradesco.tf.outside;

import com.javaf.Constants;
import com.javaf.Constants.INTEGER;

import br.com.bradesco.core.aq.service.IEventsServiceProvider;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class SOCommandCalling {
	
	public int invoke(final String command, final IEventsServiceProvider provider, final boolean wait){
		int _result = INTEGER._1NEG;
		
		final Process _process = provider.getArquitecturalActions().executeExternalProcess(command);
		
		if(null!= _process){
			while(wait && Boolean.TRUE){
				try{
					_result = _process.exitValue();
					break;
					
				}catch(IllegalThreadStateException _e){
				}
			}
			
		} else {
			
			_result = Constants.DEFAULT.RESULT_NOT_FOUND;
			
		}
		
		return _result;
	}
	
	public int invoke(final String command, final IEventsServiceProvider provider){
		return invoke(command, provider, Boolean.TRUE);
	}
	
}
