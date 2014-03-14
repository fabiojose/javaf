package bradesco.tf.outside.cadu;

import com.javaf.javase.lang.reflect.InvokeException;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class CaduV2CallingNotIdentifiedException extends InvokeException {
	private static final long serialVersionUID = 6252186436633342950L;

	public CaduV2CallingNotIdentifiedException(){
		
	}
	
	public CaduV2CallingNotIdentifiedException(final String message){
		super(message);
	}
	
	public CaduV2CallingNotIdentifiedException(final Throwable cause){
		super(cause);
	}
	
	public CaduV2CallingNotIdentifiedException(final String message, final Throwable cause){
		super(message, cause);
	}
}
