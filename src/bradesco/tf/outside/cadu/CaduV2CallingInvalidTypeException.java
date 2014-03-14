package bradesco.tf.outside.cadu;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class CaduV2CallingInvalidTypeException extends RuntimeException {
	private static final long serialVersionUID = 3891484125275563692L;

	public CaduV2CallingInvalidTypeException(){
		
	}
	
	public CaduV2CallingInvalidTypeException(final String message){
		super(message);
	}
	
	public CaduV2CallingInvalidTypeException(final Throwable cause){
		super(cause);
	}
	
	public CaduV2CallingInvalidTypeException(final String message, final Throwable cause){
		super(message, cause);
	}
}
