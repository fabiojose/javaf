package com.javaf.javase.persistence;

public interface IManager {
	
	/**
	 * @throws PersistenceException
	 */
	public <T> T find(Class<?> typeOfEntity, Object pk);

	/**
	 * @throws PersistenceException
	 */
	public void persist(Object entity);

	/**
	 * @throws PersistenceException
	 */
	public void remove(Object entity);
	
	/**
	 * @throws PersistenceException
	 */
	public void refresh(Object entity);
	
	/**
	 * @throws PersistenceException
	 */
	public ITransaction getTransaction();
	
	/**
	 * @throws PersistenceException
	 */
	public boolean isOpen();
	
	/**
	 * @throws PersistenceException
	 */
	public void close();
	
	/**
	 * @throws PersistenceException
	 */
	public IQuery createQuery(Read read);
	
	/**
	 * @throws PersistenceException
	 */
	public IQuery createNativeQuery(String nativeQuery);
	
	
}
