package com.javaf.model;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <V> Tipo genérico do valor armazenado.
 */
public abstract class ValuePlace<V> {

	private String label;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public abstract V getValue();
	public abstract void reset();
	public abstract String getName();
}
