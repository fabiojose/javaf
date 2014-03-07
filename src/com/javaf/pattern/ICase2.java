package com.javaf.pattern;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <I> Tipo genérico de entrada <code>input</code> para executar o caso
 * @param <O> Tipo genérico de saída <code>output</code> da execução do caso
 */
public interface ICase2<I, O> {

	O execute(I input);
	
}
