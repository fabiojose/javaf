package com.javaf.pattern.impl;

import java.net.MalformedURLException;
import java.net.URL;

import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.pattern.ValueOf;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class URLValueOf implements ValueOf<String, URL> {

	private final ILogging logging = Logging.loggerOf(getClass());
	
	public URL valueOf(String string) {
		URL _result = null;
		
		try{
			_result = new URL(string);
			logging.trace("LOADED URL >" + _result + "<" );
			
		}catch(MalformedURLException _e){
			logging.error("URL DID NOT LOAD: " + _e.getMessage(), _e);
		}
		
		return _result;
	}

}
