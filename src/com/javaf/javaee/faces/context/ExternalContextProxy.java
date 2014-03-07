package com.javaf.javaee.faces.context;

import java.lang.reflect.Method;

import com.javaf.javaee.servlet.ServletContextProxy;
import com.javaf.javase.lang.reflect.UtilReflection;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class ExternalContextProxy {
	
	private static final UtilReflection REFLECTION = UtilReflection.getInstance();

	private ServletContextProxy servlet;
	private final Object external;
	ExternalContextProxy(final Object external){
		this.external = external;
	}
	
	public ServletContextProxy getContext(){
		if(null== servlet){
			final Method _getContext = REFLECTION.methodOf(external.getClass(), "getContext");
			
			servlet = new ServletContextProxy(REFLECTION.invoke(external, _getContext));
		}
		
		return servlet;
	}
}
