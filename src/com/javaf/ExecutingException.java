package com.javaf;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class ExecutingException extends RuntimeException {
	private static final long serialVersionUID = 7746201118936602985L;

	public ExecutingException(){
		
	}
	
	public ExecutingException(final String message){
		super(message);
	}
	
	public ExecutingException(final Throwable cause){
		super(cause);
	}
	
	public ExecutingException(final String message, final Throwable cause){
		super(message, cause);
	}
}
