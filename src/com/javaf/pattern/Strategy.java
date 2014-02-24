package com.javaf.pattern;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 * @param <I>
 * @param <O>
 */
public interface Strategy<I, O> {

	O execute(I i);
	
}
