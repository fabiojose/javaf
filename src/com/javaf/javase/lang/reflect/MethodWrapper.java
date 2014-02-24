package com.javaf.javase.lang.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodWrapper {

	private Method method;
	private Invokable invokable;
	
	public MethodWrapper(final Method method){
		if(null== method){
			throw new NullPointerException();
		}
		
		this.method = method;
	}
	
	public MethodWrapper(final Invokable invokable){
		if(null== invokable){
			throw new NullPointerException();
		}
		
		this.invokable = invokable;
	}
	
	public Object invoke(final Object target, final Object[] args) throws InvocationTargetException, IllegalAccessException {
		Object _result = null;
		
		if(null!= method){
			_result = method.invoke(target, args);
		} else {
			_result = invokable.invoke(target, args);
		}
		
		return _result;
	}
	
	public String toString(){
		String _result = super.toString();
		if(null!= method){
			_result = method.toString();
			
		} else if(null!= invokable) {
			_result = invokable.toString();
			
		}
		return _result;
	}
}
