package com.javaf.javase.persistence;

import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.persistence.dialect.IDialect;
import com.javaf.javase.persistence.dialect.INativeExecuter;


public abstract class Manager implements IManager {
	
	private final ILogging logging = Logging.loggerOf(getClass());

	protected IDialect<String> dialect;
	
	public Manager(final IDialect<String> dialect){
		if(null== dialect){
			throw new NullPointerException("arg1 is null!");
		}
		
		this.dialect = dialect;
	}
	
	public <T> T find(Class<?> typeOfEntity, Object pk) {

		return null;
	}

	public void persist(final Object entity) {
		logging.trace("PERSIST ENTITY: " + entity);
		
		getExecuter().create(entity);
		
	}

	public void refresh(final Object entity) {
		logging.trace("REFRESH ENTITY: " + entity);

		getExecuter().update(entity);
		
	}

	public void remove(final Object entity) {
		logging.trace("REMOVE ENTITY: " + entity);

		getExecuter().delete(entity);
		
	}

	protected abstract INativeExecuter getExecuter();
}
