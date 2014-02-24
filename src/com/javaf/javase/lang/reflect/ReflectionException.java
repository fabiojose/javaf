package com.javaf.javase.lang.reflect;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class ReflectionException extends RuntimeException {
	private static final long serialVersionUID = 6640624869466222987L;

	public ReflectionException(){
		
	}
	
	public ReflectionException(final String message){
		super(message);
	}
	
	public ReflectionException(final Throwable cause){
		super(cause);
	}
	
	public ReflectionException(final String message, final Throwable cause){
		super(message, cause);
	}
}
