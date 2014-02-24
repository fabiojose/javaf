package com.javaf.unhook.logging;

import java.lang.reflect.Method;

import com.javaf.Constants.INTEGER;
import com.javaf.unhook.UnhookHandler;

/**
 * 
 * @author fabiojm - Fabio Jose de Moraes
 *
 */
public class LoggingHandler extends UnhookHandler {

	private String verbose;
	private int index;
	
	public LoggingHandler(final String verbose){
		this(verbose, INTEGER._0);
	}
	
	public LoggingHandler(final String verbose, final int index) {
		this.verbose = verbose;
		this.index   = index;
	}

	@Override
	public Object invoke(final Object o, final Method method, final Object[] arguments) throws Throwable {
		
		if(null!= verbose 
				&& null!= arguments
				&& INTEGER._0 <= index
				&& arguments[index] instanceof String){
			
				arguments[index] = verbose + arguments[index];
		}
		
		return super.invoke(o, method, arguments);
	}
}
