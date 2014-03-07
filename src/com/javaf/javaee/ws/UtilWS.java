package com.javaf.javaee.ws;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import com.javaf.Bagman;
import com.javaf.Utility;
import com.javaf.Constants.STRING;
import com.javaf.pattern.factory.Factory;
import com.javaf.pattern.factory.FactoryException;
import com.javaf.pattern.factory.IFactory;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class UtilWS implements Utility {
	private static final IFactory FACTORY = new Factory(){

		public Object newInstance() throws FactoryException {
			return new UtilWS();
		}
		
	};
	
	private UtilWS(){
		
	}
	
	public static final synchronized UtilWS getInstance(){
		return Bagman.utilOf(UtilWS.class, FACTORY);
	}
	
	public String hostOf(final WebServiceContext context){
		String _result = STRING.EMPTY;

		final MessageContext _mcontext    = context.getMessageContext();
		final HttpServletRequest _request = (HttpServletRequest)_mcontext.get(MessageContext.SERVLET_REQUEST);
		_result = _request.getRemoteAddr() + STRING.SPACE1 + STRING.PARENTESES_ABRE + _request.getRemoteHost() + STRING.PARENTESES_FECHA;
		
		return _result;
	}
}
