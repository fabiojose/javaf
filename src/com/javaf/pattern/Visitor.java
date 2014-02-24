package com.javaf.pattern;

/**
 * Design Pattern Visitor.
 * 
 * @author fabiojm - Fabio Jose de Moraes
 *
 * @param <I> Tipo da inst�ncia que ser� visitada.
 * @param <O> Tipo da inst�ncia retorna como resultado da visita.
 * @version 12/2013
 */
public interface Visitor<I, O> {

	/**
	 * Visitar.
	 * @param toVisit
	 * @return
	 */
	O visit(I toVisit);
	
}
