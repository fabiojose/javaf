package com.javaf.javase.persistence;

public class PersistenceException extends RuntimeException {
	private static final long serialVersionUID = -1845490641124132514L;

	public PersistenceException(){
		
	}
	
	public PersistenceException(final String message){
		super(message);
	}
	
	public PersistenceException(final Throwable cause){
		super(cause);
	}
	
	public PersistenceException(final String message, final Throwable cause){
		super(message, cause);
	}
}
