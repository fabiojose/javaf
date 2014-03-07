package com.javaf.pattern.factory;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class FactoryException extends RuntimeException {
	private static final long serialVersionUID = -2778450089468032443L;

	public FactoryException(){
		
	}
	
	public FactoryException(final String message){
		super(message);
	}
	
	public FactoryException(final Throwable cause){
		super(cause);
	}
	
	public FactoryException(final String message, final Throwable cause){
		super(message, cause);
	}
}
