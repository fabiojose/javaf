package com.javaf.javaee.servlet;

import java.lang.reflect.Method;

import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class ServletContextProxy {
	
	private static final UtilReflection REFLECTION = UtilReflection.getInstance();
	private static final ILogging LOGGING          = Logging.loggerOf(ServletContextProxy.class);
	
	private Method getRealPath;
	private final Object context;
	public ServletContextProxy(final Object context){
		this.context = context;
	}
	
	private Method getMethod(){
		if(null== getRealPath){
			getRealPath = REFLECTION.methodOf(context.getClass(), "getRealPath(java.lang.String)");
		}
		
		return getRealPath;
	}
	
	public String getRealPath(final String resource){
		String _result = resource;
		
		LOGGING.trace("RESOLVING REAL PATH OF >" + resource + "< . . .");
		_result = (String)REFLECTION.invoke(context, getMethod(), new Object[]{resource});
		
		if(null== _result){
			LOGGING.trace("REAL PATH OF >" + resource + "< NOT FOUND!");
		}
		
		return _result;
	}
}
