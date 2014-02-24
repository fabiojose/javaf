package com.javaf.pattern.factory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class FactoryMethodFactory implements IFactory {

	private String clazz;
	private Class<?> theClazz;
	private String method;
	private Object[] args;
	private Class<?> [] argsType;
	
	public FactoryMethodFactory(final String clazz, final String method, final Object[] args) {
		this.method = method;
		this.args = args;

		this.clazz = clazz; 
		
		argsType = new Class<?>[args.length];
		int _index = 0;
		for(Object _arg : args){
			argsType[_index++] = _arg.getClass();
		}
	}
	
	private Class<?> getTheClazz() throws ClassNotFoundException {
		if(null== theClazz){
			theClazz = Class.forName(clazz);
		}
		
		return theClazz;
	}
	

	private Method getMethod() throws ClassNotFoundException, FactoryException{
		
		Method _result = null;
		try{
			_result = getTheClazz().getMethod(method, argsType);
			
		}catch(NoSuchMethodException _e){
			throw new FactoryException(_e);
		}
		
		return _result;
	}
	
	public Object newInstance() throws FactoryException {

		Object _result = null;
		
		try{
			final Method _factorym = getMethod();
			
			try{
				_result = _factorym.invoke(null, args);
				
			}catch(InvocationTargetException _e){
				throw new FactoryException(_e);
			}catch(IllegalAccessException _e){
				throw new FactoryException(_e);
			}
		}catch(ClassNotFoundException _e){
			throw new FactoryException(_e.getMessage(), _e);
		}
		
		return _result;
	}

}
