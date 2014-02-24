package com.javaf.unhook;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.javaf.javase.lang.reflect.UtilReflection;

/**
 * 
 * @author fabiojm - Fabio Jose de Moraes
 *
 */
public class UnhookHandler implements InvocationHandler {
	
	protected final UtilReflection reflection = UtilReflection.getInstance();
	
	protected UnhookHandler(){
		
	}

	private Object target;
	public UnhookHandler(final Object target){
		if(null== target){
			throw new NullPointerException("arg1 is null!");
		}
		
		this.target = target;
	}
	
	final void setTarget(final Object target){
		if(null== target){
			throw new NullPointerException("arg1 is null!");
		}
		
		this.target = target;
	}
	
	protected final Object getTarget(){
		return target;
	}

	public Object invoke(final Object o, final Method method, final Object[] arguments) throws Throwable {
		
		final String _signature = reflection.signatureOf(method);
		final Method _method    = reflection.methodOf(target.getClass(), _signature);
		
		return _method.invoke(target, arguments);
	}

}
