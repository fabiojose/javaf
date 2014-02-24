package com.javaf.pattern;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 * @param <T> Tipo do object onde a implementa��o do filtro ser� aplicada.
 */
public interface IFilter<T> {

	boolean applyTo(T t);
	
}
