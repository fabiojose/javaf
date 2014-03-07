package com.javaf.pattern;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <T> Tipo genérico de retorno do caso executado.
 */
public interface ICase<T> {

	T execute();
	
}
