package com.javaf.pattern.factory;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public interface IFactory {

	Object newInstance() throws FactoryException;
	
}
