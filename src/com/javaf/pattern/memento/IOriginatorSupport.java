package com.javaf.pattern.memento;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <T>
 */
public interface IOriginatorSupport<T> {

	void save(T source);
	T restore(T target);
	
}
