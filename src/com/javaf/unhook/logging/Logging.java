package com.javaf.unhook.logging;

import com.javaf.pattern.factory.IFactory;
import com.javaf.unhook.Unhook;

/**
 * 
 * @author fabiojm - Fabio Jose de Moraes
 *
 * @param <T>
 */
public abstract class Logging<T> extends Unhook<T> {
	
	public Logging(final String className, final Class<T> inface, final IFactory factory, final String verbose) throws TypeNotPresentException {
		super(className, inface, factory, new LoggingHandler(verbose));
	}

}
