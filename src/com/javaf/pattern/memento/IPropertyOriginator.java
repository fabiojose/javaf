package com.javaf.pattern.memento;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public interface IPropertyOriginator {

	void save(String property);
	void restore(String property);
	
}
