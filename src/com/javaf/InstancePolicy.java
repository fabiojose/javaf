package com.javaf;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public enum InstancePolicy {

	/**
	 * Instâncias únicas por classe, atributo de classe (<code>static</code>).
	 */
	BY_CLASS,
	
	/**
	 * Instâncias únicas por Thread.
	 * @see ThreadLocal
	 */
	BY_THREAD,
	
	/**
	 * Instância únicas por objeto, atributo de instância (não-estático).
	 */
	BY_OBJECT;
	
}
