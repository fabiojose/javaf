package com.javaf.pattern.factory;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class DefaultConstructorFactory implements IFactory {

	private Class<?> theClazz;
	
	public DefaultConstructorFactory(final String clazz){

		try{
			theClazz = Class.forName(clazz);
		}catch(ClassNotFoundException _e){
			throw new RuntimeException(_e);
		}
	}
	
	public Object newInstance() {

		try{
			return theClazz.newInstance();
			 
		}catch(InstantiationException _e){
			throw new RuntimeException(_e);
		}catch(IllegalAccessException _e){
			throw new RuntimeException(_e);
		}
	}

}
