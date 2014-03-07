package com.javaf.pattern;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <P>
 * @param <O>
 */
public interface Bridge<P, O> {

	O cross(P property);
	
}
