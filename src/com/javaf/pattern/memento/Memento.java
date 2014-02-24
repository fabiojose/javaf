package com.javaf.pattern.memento;

import com.javaf.javase.lang.reflect.UtilReflection;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <T>
 */
public class Memento<T> implements IMemento<T> {
	private final UtilReflection reflection = UtilReflection.getInstance();
	
	private T savedState;
	
	public Memento(final T toSave){
		this.savedState = reflection.clone(toSave);
	}
	
	public T getSavedState() {
		return savedState;
	}



}
