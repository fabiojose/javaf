package com.javaf.pattern;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 * @param <I>
 * @param <O>
 */
public interface Builder<I, O> {

	O build(I i);
	
}
