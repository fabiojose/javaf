package com.javaf.pattern;

/**
 * Design Pattern Visitor.
 * 
 * @author fabiojm - Fabio Jose de Moraes
 *
 * @param <I> Tipo da instância que será visitada.
 * @param <O> Tipo da instância retorna como resultado da visita.
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
