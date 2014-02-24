package com.javaf.pattern;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 * @param <I> Tipo gen�rico de <i>Input</i> - entrada
 * @param <O> Tipo gen�rico de <i>Output</i> - sa�da
 */
public interface Acceptable<I> {

	<O> O accept(Visitor<I, O> visitor);

}
