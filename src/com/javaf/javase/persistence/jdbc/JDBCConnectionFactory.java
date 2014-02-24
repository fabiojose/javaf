package com.javaf.javase.persistence.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.javaf.Constants.PERSISTENCE;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.persistence.IConnectionFactory;
import com.javaf.javase.persistence.PersistenceException;



public class JDBCConnectionFactory implements IConnectionFactory {
	
	private static final ILogging LOGGING = Logging.loggerOf(JDBCConnectionFactory.class);
	
	private Properties properties = new Properties();
	
	public Connection newConection() {
		Connection _result = null;
		
		final String _driver   = properties.getProperty(PERSISTENCE.DRIVER_PROPERTY);
		final String _url      = properties.getProperty(PERSISTENCE.URL_PROPERTY);
		final String _username = properties.getProperty(PERSISTENCE.USERNAME_PROPERTY);
		final String _password = properties.getProperty(PERSISTENCE.PASSWORD_PROPERTY);
		
		try{
			Class.forName(_driver);
			
			LOGGING.info("CONNECTION DRIVER..: >" + _driver + "<");
			LOGGING.info("CONNECTION URL.....: >" + _url + "<");
			LOGGING.info("CONNECTION USERNAME: >" + _username + "<");
			LOGGING.info("CONNECTION PASSWORD: >" + _password + "<");
			
			_result = DriverManager.getConnection(_url, _username, _password);
			LOGGING.info(" ################# OPEN CONNECTION " + _result);
			
		}catch(ClassNotFoundException _e){
			LOGGING.error(_e.getMessage(), _e);
			throw new PersistenceException(_e.getMessage(), _e);
			
		}catch(SQLException _e){
			
			LOGGING.error(_e.getMessage(), _e);
			throw new PersistenceException(_e.getMessage(), _e);
		}
		
		return _result;
	}

	public void setProperty(final String key, final Object value) {
		properties.put(key, value);
	}

}
