package com.javaf.javase.lang.reflect;

/**
 * 
 * @author fabiojm - Fabio Jose de Moraes
 *
 */
public class MethodNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 4308627989260195618L;

	public MethodNotFoundException(){
		
	}
	
	public MethodNotFoundException(final String message){
		super(message);
	}
	
	public MethodNotFoundException(final Throwable cause){
		super(cause);
	}
	
	public MethodNotFoundException(final String message, final Throwable cause){
		super(message, cause);
	}
}
