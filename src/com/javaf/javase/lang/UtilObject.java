package com.javaf.javase.lang;

import com.javaf.Bagman;
import com.javaf.ObjectPool;
import com.javaf.Utility;
import com.javaf.Constants.I18N;
import com.javaf.Constants.INTEGER;
import com.javaf.javase.lang.reflect.InvokeException;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.util.Localization;
import com.javaf.pattern.factory.Factory;
import com.javaf.pattern.factory.FactoryException;
import com.javaf.pattern.factory.IFactory;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class UtilObject implements Utility {
	private static final IFactory FACTORY = new Factory(){

		public Object newInstance() throws FactoryException {
			return new UtilObject();
		}
		
	};
	
	private final UtilReflection reflection = UtilReflection.getInstance();
	private final ObjectPool opool          = ObjectPool.getPool();
	private UtilObject(){
		
	}
	
	public static final synchronized UtilObject getInstance(){
		return Bagman.utilOf(UtilObject.class, FACTORY);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T implementOf(final Class<T> type, final Class<?> clazz) throws IllegalArgumentException {
		T _result = (T)opool.get(clazz);
		
		if(null== _result){
			if(type.isAssignableFrom(clazz)){
				if(clazz.isEnum()){
					final Object _constant = clazz.getEnumConstants()[INTEGER._0];
					_result = (T)_constant;
					
				} else {
					try{
						_result = (T)reflection.newInstance(clazz);
					}catch(InvokeException _e){
						throw new IllegalArgumentException(Localization.getInstance().localize(I18N.ARGUMENTO_NAO_PODE_SER_INSTANCIADO, _e.getMessage()), _e);
					}
				}
			} else {
				throw new IllegalArgumentException(Localization.getInstance().localize(I18N.ARGUMENTO_NAO_SUBCLASSE, type));
			}
			
			opool.put(clazz, _result);
		}
		
		return _result;
	}
}
