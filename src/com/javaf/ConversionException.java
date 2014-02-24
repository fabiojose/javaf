package com.javaf;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 */
public class ConversionException extends RuntimeException {
	private static final long serialVersionUID = -2682057670763923555L;

	public ConversionException(){
		
	}
	
	public ConversionException(final String message){
		super(message);
	}
	
	public ConversionException(final Throwable cause){
		super(cause);
	}
	
	public ConversionException(final String message, final Throwable cause){
		super(message, cause);
	}
}
