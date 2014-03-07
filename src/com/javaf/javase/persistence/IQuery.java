package com.javaf.javase.persistence;

public interface IQuery {

	/**
	 * @throws PersistenceException
	 * @throws IllegalStateException Caso a consulta já tenha sido executada.
	 */
	IResults execute();
	
}
