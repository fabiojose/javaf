package com.javaf.javase.util.regex;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.javaf.Bagman;
import com.javaf.Utility;
import com.javaf.Constants.NAMED_PARAMETER;
import com.javaf.Constants.PLACE_HOLDER;
import com.javaf.Constants.REGEX;
import com.javaf.javase.lang.UtilString;
import com.javaf.pattern.factory.Factory;
import com.javaf.pattern.factory.FactoryException;
import com.javaf.pattern.factory.IFactory;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class UtilRegex implements Utility {
	private static final IFactory FACTORY = new Factory(){

		public Object newInstance() throws FactoryException {
			return new UtilRegex();
		}
		
	};
	
	private final UtilString string = UtilString.getInstance();
	private UtilRegex(){
		
	}
	
	public static final synchronized UtilRegex getInstance(){
		return Bagman.utilOf(UtilRegex.class, FACTORY);
	}
	
	public Set<String> valueOf(final String source, final String regex){
		final Set<String> _result = new HashSet<String>();
		
		final Pattern _pattern = Pattern.compile(regex);
		final Matcher _matcher = _pattern.matcher(source);
		while(_matcher.find()){
			_result.add(_matcher.group());
		}
		
		return _result;
	}
	
	public String placeholderOf(final String place){
		String _result = place;
		
		if(place.matches(REGEX.PLACEHOLDER)){
			_result = string.substring(place, PLACE_HOLDER.OPEN, PLACE_HOLDER.CLOSE);
		}
		
		return _result;
	}
	
	public Set<String> placeholderOf(final Set<String> places){
		final Set<String> _result = new HashSet<String>();
		
		for(String _place : places){
			_result.add(placeholderOf(_place));
		}
		
		return _result;
	}

	public String namedParameterOf(final String named){
		String _result = named;
		
		if(named.matches(REGEX.NAMED_PARAMETER)){
			_result = string.substring(named, NAMED_PARAMETER.OPEN, NAMED_PARAMETER.CLOSE);
		}
		
		return _result;
	}
}
