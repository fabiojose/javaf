package com.javaf;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 */
public enum InstancePolicy {

	/**
	 * Inst�ncias �nicas por classe, atributo de classe (<code>static</code>).
	 */
	BY_CLASS,
	
	/**
	 * Inst�ncias �nicas por Thread.
	 * @see ThreadLocal
	 */
	BY_THREAD,
	
	/**
	 * Inst�ncia �nicas por objeto, atributo de inst�ncia (n�o-est�tico).
	 */
	BY_OBJECT;
	
}
