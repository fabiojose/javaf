package com.javaf.javase.persistence;

import com.javaf.javase.persistence.jdbc.JDBCProvider;


public class ManagerFactory {
	private static final ManagerFactory INSTANCE = new ManagerFactory();
	
	private final IProvider provider = JDBCProvider.getInstance();
	
	private ManagerFactory(){
		
	}
	
	public static final synchronized ManagerFactory getInstance(){
		return INSTANCE;
	}
	
	/**
	 * @throws PersistenceException
	 */
	public IManager createManager(){
		IManager _result = provider.createManager();
		
		return _result;
	}
}
