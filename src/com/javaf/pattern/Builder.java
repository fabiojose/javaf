package com.javaf.pattern;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <I>
 * @param <O>
 */
public interface Builder<I, O> {

	O build(I i);
	
}
