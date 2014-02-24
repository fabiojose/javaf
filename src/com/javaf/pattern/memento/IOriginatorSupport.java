package com.javaf.pattern.memento;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 * @param <T>
 */
public interface IOriginatorSupport<T> {

	void save(T source);
	T restore(T target);
	
}
