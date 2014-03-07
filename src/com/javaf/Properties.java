package com.javaf;

import java.io.IOException;
import java.util.Set;

import com.javaf.Constants.APPLICATION;
import com.javaf.Constants.STRING;
import com.javaf.javase.lang.UtilClass;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.util.IProperties;
import com.javaf.pattern.Visitor;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class Properties implements IProperties {
	private static final Properties INSTANCE = new Properties();
	
	private final ILogging logging = Logging.loggerOf(getClass());
	
	private final Application application = Application.getInstance();
	private final UtilClass clazz         = UtilClass.getInstance();
	private java.util.Properties properties;
	private Properties(){
		clazz.add(getClass());
	}
	
	private final java.util.Properties getProperties() throws IOException{
		if(null== properties){
			properties = new java.util.Properties();
			properties.load(clazz.resourceOf(application.valueOf(String.class, APPLICATION.PROPERTIES_PROPERTY)));
		}
		
		return properties;
	}
	
	public static final synchronized IProperties getInstance(){
		return INSTANCE;
	}

	public String getProperty(final String name) {
		String _result = null;
		
		try{
			_result = getProperties().getProperty(name);
			
			logging.debug("OBTENDO VALOR DO PROPERTIES DO PROJETO: " + name + STRING.IGUAL_SPACE + _result);
			
		}catch(IOException _e){
			logging.error(_e.getMessage(), _e);
			throw new RuntimeException(_e.getMessage(), _e);
		}
		
		return _result;
	}
	
	public String getProperty(final String name, final String forNull){
		String _result = getProperty(name);
		
		if(null== _result){
			_result = forNull;
		}
		
		return _result;
	}
	
	public Set<Object> keySet(){
		Set<Object> _result = null;
		
		try{
			_result = getProperties().keySet();
			
		}catch(IOException _e){
			logging.error(_e.getMessage(), _e);
			throw new RuntimeException(_e.getMessage(), _e);
		}
		
		return _result;
	}

	/**
	 * Aceitar execução de um Visitor.
	 */
	public <O> O accept(final Visitor<IProperties, O> visitor) {
		logging.debug("ACEITANDO EXECUÇÃO DE VISITOR: " + visitor);
		
		return visitor.visit(this);
	}

}
