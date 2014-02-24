package com.javaf.javase.persistence;

import java.sql.Connection;

public interface IConnectionFactory {

	/**
	 * 
	 * @throws PersistenceException
	 */
	Connection newConection();
	
	void setProperty(String key, Object value);
	
}
