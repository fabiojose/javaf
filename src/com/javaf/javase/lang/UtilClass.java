package com.javaf.javase.lang;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.javaf.Bagman;
import com.javaf.Utility;
import com.javaf.Constants.INTEGER;
import com.javaf.Constants.STRING;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.pattern.factory.Factory;
import com.javaf.pattern.factory.FactoryException;
import com.javaf.pattern.factory.IFactory;


/**
 * 
 * @author fabiojm - Fabio Jose de Moraes
 *
 */
public final class UtilClass implements Utility {
	private static final IFactory FACTORY = new Factory(){

		public Object newInstance() throws FactoryException {
			return new UtilClass();
		}
		
	};
	
	private static final ILogging LOGGING = Logging.loggerOf(UtilClass.class);
	
	private final List<Class<?>> loaders = new ArrayList<Class<?>>(); 
	private UtilClass(){
		loaders.add(getClass());
	}
	
	public static final synchronized UtilClass getInstance(){
		return Bagman.utilOf(UtilClass.class, FACTORY);
	}
	
	/**
	 * Adicionar na lista de carregadores uma determinada classe.
	 * @param loader Carregador de recursos
	 * @see #resourceOf(String)
	 */
	public void add(final Class<?> loader){
		loaders.add(loader);
	}
	
	public boolean remove(final Class<?> loader){
		return loaders.remove(loader);
	}
	
	public Class<?> load(final String name) throws TypeNotPresentException {
		Class<?> _result = null;
		
		try{
			_result = Class.forName(name);
			
		}catch(ClassNotFoundException _e){
			LOGGING.trace("TIPO NAO FOI ENCONTRADO.", _e);
			throw new TypeNotPresentException(_e.getMessage(), _e);
		}
		
		return _result;
	}
	
	public Class<?> load(final String name, final List<ClassLoader> loaders) throws TypeNotPresentException {
		Class<?> _result = null;
		
		for(int _index = INTEGER._0; _index < loaders.size(); _index++){
			final ClassLoader _loader = loaders.get(_index);
			
			try{
				
				_result = Class.forName(name, true, _loader);
				break;
				
			}catch(ClassNotFoundException _e){
				//não existem mais loaders para tentar
				if( (_index + INTEGER._1) == loaders.size() ){
					LOGGING.trace("TIPO NAO FOI ENCONTRADO.", _e);
					throw new TypeNotPresentException(_e.getMessage(), _e);
				}
			}
		}
		
		return _result;
	}
	
	public Class<?> typeOf(final Object o){
		Class<?> _result = null;
		
		if(null!= o){
			_result = o.getClass();
		}
		
		return _result;
	}
	
	public ClassLoader rootOf(final ClassLoader cl){
		ClassLoader _result = cl;
		
		if(null!= cl){
			System.out.println(cl);
			ClassLoader _returned = rootOf(cl.getParent());
			if(null!= _returned){
				_result = _returned;
			}
		}
		
		return _result;
	}
	
	public String locationOf(final Class<?> clazz){
		String _result = STRING.EMPTY;
		
		if(null!= clazz){
			URL _url = clazz.getClassLoader().getResource(clazz.getSimpleName() + ".class");
			
			if(null== _url){
				_url = clazz.getResource(clazz.getSimpleName() + ".class");
			}
			
			if(null!= _url){
				_result = _url.toString();
			}
		}
		
		return _result;
	}
	
	public InputStream resourceOf(final String resource){
		InputStream _result = null;
		
		for(Class<?> _loader : loaders){
			_result = _loader.getResourceAsStream(resource);
			
			if(null!= _result){
				break;
			}
		}
		
		return _result;
	}
	
	public <T> T cast(final Object toCast, final Class<T> castTo) throws ClassCastException{ 
		
		return castTo.cast(toCast);
	}
}
