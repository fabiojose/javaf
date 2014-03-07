package com.javaf.model;

import java.io.Serializable;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public interface IDynamic extends Serializable {

	Object get(String property) throws PropertyNotFoundException;
	
	<T> T get(String property, Class<T> type) throws PropertyNotFoundException;
	void set(String property, Object value) throws PropertyNotFoundException;
	
	void setExceptions(boolean exceptions);
	boolean isExceptions();
	
}
