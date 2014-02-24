package com.javaf.javase.persistence;

public interface ITransaction {

	/**
	 * @throws PersistenceException
	 */
	void begin();
	
	/**
	 * @throws PersistenceException
	 */
	void commit();
	
	/**
	 * @throws PersistenceException
	 */
	void rollback();
	
}
