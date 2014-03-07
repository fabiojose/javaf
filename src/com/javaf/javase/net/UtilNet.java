package com.javaf.javase.net;

import java.net.URL;

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
public final class UtilNet implements Utility {
	private static final IFactory FACTORY = new Factory(){

		public Object newInstance() throws FactoryException {
			return new UtilNet();
		}
		
	};
	
	private UtilNet(){
		
	}
	
	public static final synchronized UtilNet getInstance(){
		return Bagman.utilOf(UtilNet.class, FACTORY);
	}
	
	public String contextOf(final URL url){
		String _result = null;
		
		_result = url.getProtocol() + "://" + url.getHost();
		
		return _result;
	}
}
