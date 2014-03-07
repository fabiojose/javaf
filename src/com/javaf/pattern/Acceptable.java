package com.javaf.pattern;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <I> Tipo genérico de <i>Input</i> - entrada
 * @param <O> Tipo genérico de <i>Output</i> - saída
 */
public interface Acceptable<I> {

	<O> O accept(Visitor<I, O> visitor);

}
