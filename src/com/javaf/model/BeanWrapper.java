package com.javaf.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class BeanWrapper extends HashMap<String, Object> implements Serializable {
	private static final long serialVersionUID = 182539026608392023L;
	
	public BeanWrapper(){

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object put(final String key, final Object value){
		return super.put(key, value);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void putAll(final Map<? extends String, ? extends Object> all){
		super.putAll(all);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object remove(final Object key){
		final Object _result = super.remove(key);

		return _result;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(final Object key, final Class<T> type) throws ClassCastException {
		
		return (T)get(key);
	}
	
	public <T> T get(final Object key, final Class<T> type, final T onNull) throws ClassCastException {
		T _result = get(key, type);
		
		if(null== _result){
			_result = onNull;
		}
		
		return _result;
	}
}
