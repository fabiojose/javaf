package com.javaf.unhook;

import com.javaf.Constants.INTEGER;
import com.javaf.javase.lang.UtilClass;
import com.javaf.javase.lang.reflect.InstanceNotCreatedException;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.pattern.factory.IFactory;

/**
 * 
 * @author fabiojm - Fabio Jose de Moraes
 *
 * @param <T>
 */
public final class UnhookTargetFactory implements IFactory {
	
	private final UtilClass clazz           = UtilClass.getInstance();
	private final UtilReflection reflection = UtilReflection.getInstance();
	
	private String target;
	private String signature;
	private Object[] arguments;
	
	public UnhookTargetFactory(final String className/*, final FactoryType tipo*/, final String signature, final Object...arguments){
		if(null== className){
			throw new NullPointerException("arg1 is null!");
		}
		
		if(INTEGER._0== className.length()){
			throw new IllegalArgumentException("arg1 is empty!");
		}
		
		/*if(null== tipo){
			throw new NullPointerException("arg1 is null!");
		}*/
		
		if(null== signature){
			throw new NullPointerException("arg3 is null!");
		}
		
		if(0== signature.length()){
			throw new IllegalArgumentException("arg3 is empty!");
		}
	
		this.target    = className;
		//this.tipo      = tipo;
		this.signature = signature;
		this.arguments = arguments;
	}
	
	@SuppressWarnings("unchecked")
	public Object newInstance(/*final Object...args*/) throws InstanceNotCreatedException, IllegalStateException, TypeNotPresentException {
		Object _result = null;
		
		/*Object[] _arguments = arguments;
		if(null== arguments || INTEGER._0== arguments.length){
			_arguments = args;
		}
		
		final Class<?> _target = clazz.load(target);
		
		if(FactoryType.METHOD.equals(tipo)){
			try{
				_result = reflection.invoke(_target, signature, _arguments);
				
			}catch(MethodNotFoundException _e){
				
				throw new InstanceNotCreatedException(_e.getMessage(), _e);
				
			} catch(InvokeException _e){
				
				throw new InstanceNotCreatedException(_e.getMessage(), _e);
				
			}
			
		} else if(FactoryType.CONSTRUCTOR.equals(tipo)){
			
			throw new UnsupportedOperationException(FactoryType.CONSTRUCTOR.toString());
			
		} else {
			throw new IllegalStateException("FactoryType not supported: " + tipo);
		}*/
		
		return _result;
	}

	/*public static void main(String...args){
				
		final UnhookTargetFactory _factory0 = new UnhookTargetFactory("java.util.logging.Logger", FactoryType.METHOD, "getLogger(java.lang.String)", Unhook.class.getName());
		
		final Object _instance0 = _factory0.newInstance();
		System.out.println(_instance0);
		
		final UnhookTargetFactory _factory = new UnhookTargetFactory("org.apache.log4j.Logger", FactoryType.METHOD, "getLogger(java.lang.Class)", Unhook.class);
		
		final Object _instance = _factory.newInstance();
		System.out.println(_instance);
		
	}*/
}
