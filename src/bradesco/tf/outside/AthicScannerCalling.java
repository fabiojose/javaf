package bradesco.tf.outside;

import br.com.bradesco.core.aq.service.IEventsServiceProvider;
import bradesco.tf.UnsuccessfulException;
import bradesco.tf.outside.athic.AGCCalling;
import bradesco.tf.outside.athic.AGCInput;
import bradesco.tf.outside.athic.AGCResult;
import bradesco.tf.outside.athic.ATHICCalling;
import bradesco.tf.outside.athic.ATHICInput;
import bradesco.tf.outside.athic.ATHICResult;
import bradesco.tf.outside.athic.BPOCalling;
import bradesco.tf.outside.athic.BPOInput;
import bradesco.tf.outside.athic.BPOResult;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class AthicScannerCalling {
	
	@SuppressWarnings("unchecked")
	public <R extends ATHICResult> R invoke(final IEventsServiceProvider provider, final ATHICInput input, final Class<R> type) throws UnsuccessfulException, IllegalArgumentException{
		R _result = null;
		
		ATHICCalling<? extends ATHICResult> _calling = null;
		
		if(BPOResult.class.isAssignableFrom(type)){
			
			_calling = new BPOCalling();
			
		} else if(AGCResult.class.isAssignableFrom(type)){
			
			_calling = new AGCCalling();
			
		} else if(ATHICResult.class== type) {
			
			throw new IllegalArgumentException("illegal type: " + type);
			
		} else {
			throw new IllegalArgumentException("unsupported type: " + type);
		}
		
		_result = (R)_calling.invoke(provider, input);
		
		return _result;
	}
	
	public AGCResult agc(final IEventsServiceProvider provider, final AGCInput input) throws UnsuccessfulException, IllegalArgumentException {
		return invoke(provider, input, AGCResult.class);
	}
	
	public BPOResult bpo(final IEventsServiceProvider provider, final BPOInput input) throws UnsuccessfulException, IllegalArgumentException {
		return invoke(provider, input, BPOResult.class);
	}
	
}
