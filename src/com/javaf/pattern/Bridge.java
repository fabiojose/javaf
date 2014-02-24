package com.javaf.pattern;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 * @param <P>
 * @param <O>
 */
public interface Bridge<P, O> {

	O cross(P property);
	
}
