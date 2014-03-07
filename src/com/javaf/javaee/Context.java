package com.javaf.javaee;

import java.util.HashMap;
import java.util.Map;

import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class Context {
	private static final Context INSTANCE = new Context();
	
	private static final ILogging LOGGING = Logging.loggerOf(Context.class);
	
	/**
	 * Chave onde se pode registrar o contexto aplicação Servlet.
	 * @see #put(Object, Object)
	 * @see #get(Object)
	 */
	public static final String SERVLET_CONTEXT = "SERVLET_CONTEXT";
	
	private final Map<Object, Object> registers;
	private Context(){
		registers = new HashMap<Object, Object>();
	}
	
	public static final synchronized Context getInstance(){
		return INSTANCE;
	}
	
	public synchronized void put(final Object key, final Object value){
		
		final boolean _contains = registers.containsKey(key);
		final Object _before    = registers.put(key, value);
		
		if(_contains){
			LOGGING.debug("CHANGE KEY >" + key + "<, NEW-VALUE >" + value + "<, OLD-VALUE >" + _before + "<");
		} else {
			LOGGING.debug("REGISTER KEY >" + key + "<, VALUE >" + value + "<");
		}
	}
	
	public Object get(final Object key){
		return registers.get(key);
	}
	
	public boolean contains(final Object key){
		return registers.containsKey(key);
	}
}
