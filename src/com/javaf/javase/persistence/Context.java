package com.javaf.javase.persistence;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.javaf.Constants.PERSISTENCE;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;



public class Context {
	
	private final static ILogging LOGGING = Logging.loggerOf(Context.class);

	private final Map<Object, Object> properties = new HashMap<Object, Object>();
	private Connection connection;
	
	private final Set<Statement> toClose;
	
	private Context(){
		toClose = new HashSet<Statement>();
	}
	
	/**
	 * @throws PersistenceException
	 */
	public static final synchronized Context newContext(){
		final Context _result = new Context();
		
		LOGGING.info("CREATE A NEW CONTEXT . . .");
		
		_result.properties.putAll(UtilPersistence.propertiesOf(PERSISTENCE.PROPERTY_PREFIX));
		
		LOGGING.info("CONTEXT CREATED: " + _result);
			
		return _result;
	}
	
	public Connection connection(){
		if(null== connection){
			LOGGING.info("CREATE A NEW CONNECTION . . .");
			
			connection = UtilPersistence.getConnectionFactory().newConection();
			
			LOGGING.info("CONNECTION CREATED: " + connection);
		}
		
		return connection;
	}
	
	public void toClose(final Statement statement){
		toClose.add(statement);
	}
	
	public Set<Statement> getToClose(){
		return toClose;
	}
	
	public void put(final Object key, final Object value){
		properties.put(key, value);
	}         
	
	public Object get(final Object key){
		return properties.get(key);
	}
	
	public void clean(){
		
		toClose.clear();
		connection = null;
		
		LOGGING.debug("CONTEXT WAS CLEAN.");
	}
}
