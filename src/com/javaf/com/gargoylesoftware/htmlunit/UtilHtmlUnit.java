package com.javaf.com.gargoylesoftware.htmlunit;

import java.net.PasswordAuthentication;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.DefaultCredentialsProvider;
import com.gargoylesoftware.htmlunit.WebClient;
import com.javaf.Application;
import com.javaf.Bagman;
import com.javaf.Utility;
import com.javaf.Constants.APPLICATION;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.pattern.factory.Factory;
import com.javaf.pattern.factory.FactoryException;
import com.javaf.pattern.factory.IFactory;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class UtilHtmlUnit implements Utility {
	private static final IFactory FACTORY = new Factory(){

		public Object newInstance() throws FactoryException {
			return new UtilHtmlUnit();
		}
		
	};
	
	private final Application application = Application.getInstance();
	private final ILogging logging        = Logging.loggerOf(getClass());
	private UtilHtmlUnit(){
		
	}
	
	public static final synchronized UtilHtmlUnit getInstance(){
		return Bagman.utilOf(UtilHtmlUnit.class, FACTORY);
	}
	
	public WebClient newClient(){
		WebClient _result = null;
		
		//verificar se proxy de rede está ativo
		final Boolean _on = application.valueOf(Boolean.class, APPLICATION.NETWORK_PROXY_ON_PROPERTY);
		if(_on){
			logging.debug("NETWORK PROXY IS TURNED **ON**");
			//proxy ligado
			final String _host                 = application.valueOf(String.class, APPLICATION.NETWORK_PROXY_HOST_PROPERTY);
			final Integer _port                = application.valueOf(Integer.class, APPLICATION.NETWORK_PROXY_PORT_PROPERTY);
			final PasswordAuthentication _auth = application.valueOf(PasswordAuthentication.class, APPLICATION.NETWORK_PROXY_AUTHENTICATION_PROPERTY);
			
			_result = new WebClient(BrowserVersion.FIREFOX_24, _host, _port);
			final DefaultCredentialsProvider _provider = (DefaultCredentialsProvider)_result.getCredentialsProvider();
			_provider.addCredentials(_auth.getUserName(), new String(_auth.getPassword()));
			
		}else {
			logging.debug("NETWORK PROXY IS TURNED **OFF**");
			//proxy desligado
			_result = new WebClient();
		}
		
		return _result;
	}
}
