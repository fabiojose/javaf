package com.javaf.pattern;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <I>
 * @param <O>
 */
public interface Strategy<I, O> {

	O execute(I i);
	
}
