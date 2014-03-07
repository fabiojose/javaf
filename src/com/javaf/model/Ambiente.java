package com.javaf.model;

import java.util.Map;
import java.util.Properties;

import com.javaf.pattern.Strategy;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class Ambiente {

	private String codigo;
	private Properties properties;
	
	public Ambiente(){
		
	}

	public void setCodigo(final String codigo){
		this.codigo = codigo;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public Properties getProperties() {
		if(null== properties){
			properties = new Properties();
		}
		return properties;
	}
	
	public String getProperty(final String property){
		return getProperties().getProperty(property);
	}
	
	@SuppressWarnings("unchecked")
	public Object getProperty(final Strategy<Map<String, Object>, Object> strategy){
		Object _result = null;
		if(null!= strategy){
			_result = strategy.execute((Map)getProperties());
		}
		return _result;
	}
}
