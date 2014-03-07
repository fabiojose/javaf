package com.javaf.pattern;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <T> Tipo do object onde a implementação do filtro será aplicada.
 */
public interface IFilter<T> {

	boolean applyTo(T t);
	
}
