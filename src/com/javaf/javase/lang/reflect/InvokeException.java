package com.javaf.javase.lang.reflect;

/**
 * 
 * @author fabiojm - Fabio Jose de Moraes
 *
 */
public class InvokeException extends RuntimeException {
	private static final long serialVersionUID = -1501376264641375124L;

	public InvokeException(){
		
	}
	
	public InvokeException(final String message){
		super(message);
	}
	
	public InvokeException(final Throwable cause){
		super(cause);
	}
	
	public InvokeException(final String message, final Throwable cause){
		super(message, cause);
	}
	
}
