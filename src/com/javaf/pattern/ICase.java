package com.javaf.pattern;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 * @param <T> Tipo gen�rico de retorno do caso executado.
 */
public interface ICase<T> {

	T execute();
	
}
