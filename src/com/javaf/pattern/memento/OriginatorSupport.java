package com.javaf.pattern.memento;

import java.util.ArrayList;
import java.util.List;

import com.javaf.Constants.INTEGER;
import com.javaf.javase.lang.reflect.CopyPolicyType;
import com.javaf.javase.lang.reflect.UtilReflection;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 * @param <T>
 */
public class OriginatorSupport<T> implements IOriginatorSupport<T> {
	private final UtilReflection reflection = UtilReflection.getInstance();
	
	private List<IMemento<T>> savedStates;
	
	protected List<IMemento<T>> getSavedStates(){
		if(null== savedStates){
			savedStates = new ArrayList<IMemento<T>>();
		}
			
		return savedStates;
	}
	
	public void save(final T source) {
		final IMemento<T> _memento = new Memento<T>(source);
		
		//empilhando
		getSavedStates().add(_memento);
	}

	private T restore() {
		T _result = null;
		
		final List<IMemento<T>> _savedStates = getSavedStates();
		if(!_savedStates.isEmpty()){
			final int _index = _savedStates.size() - INTEGER._1;
			final IMemento<T> _memento = _savedStates.get(_index);
			
			//desempilhando
			_savedStates.remove(_index);
			
			//obter saved state
			_result = _memento.getSavedState();
		}
		
		return _result;
	}

	public T restore(final T target){
		final T _toRestore = restore();
		
		if(null!= _toRestore && null!= target){
			reflection.copy(_toRestore, target, CopyPolicyType.DEEP);
		}
		
		return _toRestore;
	}
}
