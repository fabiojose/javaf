package com.javaf.unhook;

import java.lang.reflect.Proxy;

import com.javaf.javase.lang.UtilClass;
import com.javaf.javase.lang.reflect.InvokeException;
import com.javaf.javase.lang.reflect.MethodNotFoundException;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.pattern.factory.IFactory;

/**
 * 
 * @author fabiojm - Fabio Jose de Moraes
 *
 * @param <T>
 */
public class Unhook<T> {

	@SuppressWarnings("unused")
	private String qualified;
	private Class<T> inface;
	private IFactory factory; 
	
	private Class<? extends T> clazz;
	private T target;
	private T proxy;
	
	private UnhookHandler handler;
	
	@SuppressWarnings("unchecked")
	public Unhook(final String className, final Class<T> inface, final IFactory factory) throws TypeNotPresentException {
		if(null== className){
			throw new NullPointerException("arg1 is null!");
		}
		
		if(null== inface){
			throw new NullPointerException("arg2 is null!");
		}
		
		if(null== factory){
			throw new NullPointerException("arg3 is null!");
		}
		
		try{
			clazz = (Class<? extends T>)Class.forName(className);
		}catch(ClassNotFoundException _e){
			throw new TypeNotPresentException(_e.getMessage(), _e);
		}
		
		this.qualified = className;
		this.inface    = inface;
		this.factory   = factory;
	}
	
	public Unhook(final String className, final Class<T> inface, final IFactory factory, final UnhookHandler handler) throws TypeNotPresentException {
		this(className, inface, factory);
		
		if(null== handler){
			throw new NullPointerException("arg4 is null!");
		}
		
		this.handler = handler;
	}
	
	private UnhookHandler getHandler(final Object target){
		if(null== handler){
			handler = new UnhookHandler(target);
		} else {
			handler.setTarget(target);
		}
		
		return handler;
	}
	
	protected void setHandler(final UnhookHandler handler){
		this.handler = handler;
	}
	
	@SuppressWarnings("unchecked")
	private void bootstrap(final Object...arguments){
		
		//target = (T)factory.newInstance(arguments);
		target = (T)factory.newInstance();
		proxy  = (T)Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{inface}, getHandler(target));
	}
	
	public T hook(final Object...arguments){
		if(null== target){
			bootstrap(arguments);
		}
		
		return proxy;
	}
	
	public static Object invoke(final String className, final String signature, final Object...arguments) throws TypeNotPresentException, MethodNotFoundException, InvokeException{
		Object _result = null;
		
		final Class<?> _class = UtilClass.getInstance().load(className);
		_result = UtilReflection.getInstance().invoke(_class, signature, arguments);
		
		return _result;
	}
	
	/*public static void main(String...args){
		final String _className = "org.apache.log4j.Logger";
		final IFactory _factory = new UnhookTargetFactory(_className, FactoryType.METHOD, "getLogger(java.lang.Class)");
		
		//to execute a static method
		Unhook.invoke("org.apache.log4j.BasicConfigurator", "configure");
		
		try{
			final Unhook _unhook = new Unhook(_className, ILogger.class, _factory);
			final ILogger _logger         = _unhook.hook(Unhook.class);
			_logger.info(_logger);
			_logger.debug("Fabio Jose de Moraes");
			_logger.info("30 year old");
		}catch(TypeNotPresentException _e){
			_e.printStackTrace();
		}
	}*/
}
