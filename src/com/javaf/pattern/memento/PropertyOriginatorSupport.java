package com.javaf.pattern.memento;

import java.util.HashMap;
import java.util.Map;

import com.javaf.javase.lang.reflect.UtilReflection;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class PropertyOriginatorSupport implements IPropertyOriginatorSupport<Object> {
	private final UtilReflection reflection = UtilReflection.getInstance();
	
	private Map<String, IOriginatorSupport<Object>> originators;
	
	private Map<String, IOriginatorSupport<Object>> getOriginators(){
		if(null== originators){
			originators = new HashMap<String, IOriginatorSupport<Object>>();
		}
		
		return originators;
	}

	public void save(final Object source, final String property) {
		final Map<String, IOriginatorSupport<Object>> _originators = getOriginators();
		IOriginatorSupport<Object> _originator = _originators.get(property);
		if(null== _originator){
			_originator = new OriginatorSupport<Object>();
			_originators.put(property, _originator);
		}
		
		final Object _value = reflection.valueOf(source, property);
		_originator.save(_value);
	}

	public void restore(final Object source, final String property) {
		final Map<String, IOriginatorSupport<Object>> _originators = getOriginators();
		final IOriginatorSupport<Object> _originator = _originators.get(property);
		
		if(null!= _originator){
			final Object _value = _originator.restore(null);
			
			if(null!= _value){
				reflection.setValue(source, property, _value);
			}
		}
	}

}
