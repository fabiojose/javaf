package com.javaf.model;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 */
public class PropertyNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 6894797288812747482L;

	public PropertyNotFoundException(){
		
	}
	
	public PropertyNotFoundException(final String message){
		super(message);
	}
	
	public PropertyNotFoundException(final Throwable cause){
		super(cause);
	}
	
	public PropertyNotFoundException(final String message, final Throwable cause){
		super(message, cause);
	}
}
