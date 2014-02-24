package com.javaf.pattern.memento;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 */
public interface IPropertyOriginator {

	void save(String property);
	void restore(String property);
	
}
