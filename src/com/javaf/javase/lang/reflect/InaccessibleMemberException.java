package com.javaf.javase.lang.reflect;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class InaccessibleMemberException extends RuntimeException {
	private static final long serialVersionUID = 6982001479137699519L;

	public InaccessibleMemberException(){
		
	}
	
	public InaccessibleMemberException(final String message){
		super(message);
	}
	
	public InaccessibleMemberException(final Throwable cause){
		super(cause);
	}
	
	public InaccessibleMemberException(final String message, final Throwable cause){
		super(message, cause);
	}
}
