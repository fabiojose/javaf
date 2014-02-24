package com.javaf.javase.lang.reflect;

/**
 * 
 * @author fabiojm - Fabio Jose de Moraes
 *
 */
public class InstanceNotCreatedException extends RuntimeException {
	private static final long serialVersionUID = -2397250580345367916L;

	public InstanceNotCreatedException(){
		
	}
	
	public InstanceNotCreatedException(final String message){
		super(message);
	}
	
	public InstanceNotCreatedException(final Throwable cause){
		super(cause);
	}
	
	public InstanceNotCreatedException(final String message, final Throwable cause){
		super(message, cause);
	}
	
}
