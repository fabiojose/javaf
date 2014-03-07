package com.javaf.pattern.memento;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <T>
 */
public interface IPropertyOriginatorSupport<T> {

	void save(T source, String property);
	void restore(T target, String property);
	
}
