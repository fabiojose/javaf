package com.javaf.javase.persistence;

import java.util.Iterator;

public interface IResults extends Iterator<IResult> {

	/**
	 * @throws PersistenceException
	 */
	void close();
	
}
