package com.javaf.pattern.memento;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <T>
 */
public interface IMemento<T> {

	T getSavedState();
	
}
