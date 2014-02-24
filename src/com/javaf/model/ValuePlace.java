package com.javaf.model;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 * @param <V> Tipo gen�rico do valor armazenado.
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
