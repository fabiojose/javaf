package com.javaf.javase.lang.reflect;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class MemberNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -749506387827336258L;

	public MemberNotFoundException(){
		
	}
	
	public MemberNotFoundException(final String message){
		super(message);
	}
	
	public MemberNotFoundException(final Throwable cause){
		super(cause);
	}
	
	public MemberNotFoundException(final String message, final Throwable cause){
		super(message, cause);
	}
}
