package com.javaf;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.javaf.Constants.APPLICATION;
import com.javaf.pattern.factory.IFactory;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class Bagman {
	
	private static final Application APP = Application.getInstance();

	private static final Map<Class<?>, Object>              BY_CLASS  = new Hashtable<Class<?>, Object>();
	private static final ThreadLocal<Map<Class<?>, Object>> BY_THREAD = new ThreadLocal<Map<Class<?>,Object>>();
	
	private Bagman(){
		
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T byClass(final Class<T> clazz, final IFactory factory){
		T _result = (T)BY_CLASS.get(clazz);
		
		if(null== _result){
			_result = (T)factory.newInstance();
			BY_CLASS.put(clazz, _result);
		}
		
		return _result;
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T byThread(final Class<T> clazz, final IFactory factory){
		T _result = null;
		
		Map<Class<?>, Object> _bythread = BY_THREAD.get();
		if(null== _bythread){
			_bythread = new HashMap<Class<?>, Object>();
			BY_THREAD.set(_bythread);
		}
	
		_result = (T)_bythread.get(clazz);
		
		if(null== _result){
			_result = (T)factory.newInstance();
			_bythread.put(clazz, _result);
		}
		
		return _result;
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T byObject(final Class<T> clazz, final IFactory factory){
		T _result = null;
		
		_result = (T)factory.newInstance();
		
		return _result;
	}
	
	private static final <T> T instanceOf(final Class<T> clazz, final InstancePolicy policy, final IFactory factory){
		T _result = null;
		
		if(InstancePolicy.BY_CLASS.equals(policy)){
			
			_result = byClass(clazz, factory);
			
		} else if(InstancePolicy.BY_THREAD.equals(policy)){
			
			_result = byThread(clazz, factory);
			
		} else {//default: sempre BY_OBJECT
			
			_result = byObject(clazz, factory);
			
		}
		
		return _result;
	}
	
	/**
	 * Obter instância de classes de Utilidades.
	 * @param <T> Tipo genérico de implementação de <code>Utility</code>
	 * @param clazz Tipo da classe de utilidades
	 * @param factory Fábrica de instância da classe de utilidades.
	 * @return Instância obedecendo a política de criação.
	 * @see APPLICATION#UTIL_INSTANCIES_POLICY
	 * @see Application#valueOf(Class, Object)
	 * @see Application#register(Object, Object)
	 */
	public static final <T extends Utility> T utilOf(final Class<T> clazz, final IFactory factory){
		T _result = null;
		
		final InstancePolicy _util = APP.valueOf(InstancePolicy.class, APPLICATION.UTIL_INSTANCIES_POLICY);
		_result = instanceOf(clazz, _util, factory);
		
		return _result;
	}
}
