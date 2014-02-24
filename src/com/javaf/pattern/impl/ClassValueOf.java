package com.javaf.pattern.impl;

import java.util.ArrayList;
import java.util.List;

import com.javaf.javase.lang.UtilClass;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.pattern.ValueOf;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class ClassValueOf<T> implements ValueOf<String, Class<T>> {
	
	private final List<ClassLoader> loaders = new ArrayList<ClassLoader>();
	{
		loaders.add(ClassValueOf.class.getClassLoader());
	}
	
	private final ILogging logging = Logging.loggerOf(getClass());
	private final UtilClass clazz  = UtilClass.getInstance();
	public ClassValueOf(){
		logging.debug("LOADING CLASSES USING DEFAULT CLASS LOADER.");
	}
	
	public ClassValueOf(final Class<?> classClassLoader){
		loaders.add(classClassLoader.getClassLoader());
		
		logging.debug("LOADING CLASSES USING DEFAULT CLASS LOADER AND >" + classClassLoader + "<");
	}
	
	public ClassValueOf(final ClassLoader classLoader){
		loaders.add(classLoader);
		
		logging.debug("LOADING CLASSES USING DEFAULT CLASS LOADER AND >" + classLoader + "<");
	}

	@SuppressWarnings("unchecked")
	public Class<T> valueOf(final String string) {
		Class<T> _result = null;
		
		_result = (Class<T>)clazz.load(string, loaders);
		logging.debug("LOADED CLASS >" + string + "<");
		
		return _result;
	}

}
