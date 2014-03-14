package bradesco.tf.mediator;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class MediationException extends RuntimeException {
	private static final long serialVersionUID = 82049739996930694L;

	public MediationException(){
		
	}
	
	public MediationException(final String message){
		super(message);
	}
	
	public MediationException(final Throwable cause){
		super(cause);
	}
	
	public MediationException(final String message, final Throwable cause){
		super(message, cause);
	}
}
