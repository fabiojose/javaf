package com.javaf.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class Textual {

	private Map<String, Object> mapping;
	
	public Textual(){
		mapping = new HashMap<String, Object>();
	}
	
	public void put(final String key, final Object value){
		mapping.put(key, value);
	}
	
	public Object get(final String key){
		return mapping.get(key);
	}
	
	public Set<String> keySet(){
		return mapping.keySet();
	}
}
