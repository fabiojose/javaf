package com.javaf.pattern;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 * @param <I> Tipo gen�rico de entrada <code>input</code> para executar o caso
 * @param <O> Tipo gen�rico de sa�da <code>output</code> da execu��o do caso
 */
public interface ICase2<I, O> {

	O execute(I input);
	
}
