package com.javaf.javase.persistence.annotation;

/**
 * Regra para determinar o quanto da classe de retorno do getter ou argumento do setter será transiente.
 * @author fabiojm - Fábio José de Moraes
 */
public enum TransientPolicy {

	READ,
	WRITE,
	READ_WRITE;
	
}
