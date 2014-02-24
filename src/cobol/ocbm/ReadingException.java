package cobol.ocbm;

import com.javaf.ExecutingException;

public class ReadingException extends ExecutingException {
	private static final long serialVersionUID = 2196460715414288788L;

	public ReadingException(){
		
	}
	
	public ReadingException(final String message){
		super(message);
	}
	
	public ReadingException(final Throwable cause){
		super(cause);
	}
	
	public ReadingException(final String message, final Throwable cause){
		super(message, cause);
	}
}
