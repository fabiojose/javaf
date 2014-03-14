package bradesco.tf;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class ResourceException extends RuntimeException {
	private static final long serialVersionUID = -1449440233750603520L;

	public ResourceException(){
		
	}
	
	public ResourceException(final String message){
		super(message);
	}
	
	public ResourceException(final Throwable cause){
		super(cause);
	}
	
	public ResourceException(final String message, final Throwable cause){
		super(message, cause);
	}
}
