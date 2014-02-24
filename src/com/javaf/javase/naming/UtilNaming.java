package com.javaf.javase.naming;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.javaf.Bagman;
import com.javaf.Utility;
import com.javaf.pattern.factory.Factory;
import com.javaf.pattern.factory.FactoryException;
import com.javaf.pattern.factory.IFactory;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class UtilNaming implements Utility {
	private static final IFactory FACTORY = new Factory(){

		public Object newInstance() throws FactoryException {
			return new UtilNaming();
		}
		
	};
	
	private UtilNaming(){
		
	}
	
	public static final synchronized UtilNaming getInstance(){
		return Bagman.utilOf(UtilNaming.class, FACTORY);
	}
	
	private Context context;
	public Context getContext() throws NamingException {
		if(null== context){
			context = new InitialContext();
		}
		
		return context;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T lookup(final String name, final Class<T> type) throws NamingException {
		T _result = null;
		
		_result = (T)getContext().lookup(name);
		
		return _result;
	}
	
}
