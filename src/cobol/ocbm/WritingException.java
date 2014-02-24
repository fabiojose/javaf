package cobol.ocbm;

import com.javaf.ExecutingException;

public class WritingException extends ExecutingException {
	private static final long serialVersionUID = 1680613455178245632L;

	public WritingException(){
		
	}
	
	public WritingException(final String message){
		super(message);
	}
	
	public WritingException(final Throwable cause){
		super(cause);
	}
	
	public WritingException(final String message, final Throwable cause){
		super(message, cause);
	}
}
