package com.javaf.javase.text;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class ParsingException extends RuntimeException {
	private static final long serialVersionUID = -6935024619572981601L;

	public ParsingException(){
		
	}
	
	public ParsingException(final String message){
		super(message);
	}
	
	public ParsingException(final Throwable cause){
		super(cause);
	}
	
	public ParsingException(final String message, final Throwable cause){
		super(message, cause);
	}
}
