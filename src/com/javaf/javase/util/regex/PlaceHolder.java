package com.javaf.javase.util.regex;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.javaf.Constants.REGEX;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class PlaceHolder {

	private final ILogging logging           = Logging.loggerOf(getClass());
	private final UtilRegex regex            = UtilRegex.getInstance();
	private final Map<String, String> places = new HashMap<String, String>();
	public PlaceHolder(){
		
	}
	
	public String register(final String place, final String value){
		String _result = null;
		
		final String _old = places.put(place, value);
		if(null!= _old){
			logging.trace(place + " **OLD VALUE** >" + _old + "<");
		}
		
		logging.trace(place + " >" + value + "<");
		
		return _result;
	}
	
	public boolean contains(final String place){
		return places.containsKey(place);
	}
	
	/**
	 * 
	 * @see REGEX#PLACEHOLDER
	 * @param source
	 * @return
	 */
	public String process(final String source){
		String _result = source;
		
		if(source.matches(REGEX.PLACEHOLDER_CONTAINS)){
			final Set<String> _places = regex.valueOf(source, REGEX.PLACEHOLDER);
			for(String _place : _places){
				final String _key = regex.placeholderOf(_place);
				
				logging.trace("*BEFORE* >" + _result + "<");
				_result = _result.replace(_place, places.get(_key));
				logging.trace("*AFTER*  >" + _result + "<");
			}
		}
		
		return _result;
	}
	
}
