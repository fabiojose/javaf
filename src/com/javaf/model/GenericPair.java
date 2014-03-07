package com.javaf.model;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <V1>
 * @param <V2>
 */
public class GenericPair<V1, V2> extends AbstractDynamic {
	private static final long serialVersionUID = -1793288576119432639L;

	private V1 value1;
	private V2 value2;
	
	public V1 getValue1() {
		return value1;
	}
	public void setValue1(V1 value1) {
		this.value1 = value1;
	}
	public V2 getValue2() {
		return value2;
	}
	public void setValue2(V2 value2) {
		this.value2 = value2;
	}
	
}
