package com.javaf.pattern;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <T1>
 * @param <T2>
 */
public interface Deen<T1, T2> {

	T1 decode(T2 t);
	T2 encode(T1 t);
	
}
