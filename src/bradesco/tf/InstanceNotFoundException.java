package bradesco.tf;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class InstanceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -4250059359826923008L;

	public InstanceNotFoundException(){
		
	}
	
	public InstanceNotFoundException(final String message){
		super(message);
	}
	
	public InstanceNotFoundException(final Throwable cause){
		super(cause);
	}
	
	public InstanceNotFoundException(final String message, final Throwable cause){
		super(message, cause);
	}
}
