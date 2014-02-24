package com.javaf.javase.persistence.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.persistence.Context;
import com.javaf.javase.persistence.IQuery;
import com.javaf.javase.persistence.ITransaction;
import com.javaf.javase.persistence.Manager;
import com.javaf.javase.persistence.PersistenceException;
import com.javaf.javase.persistence.Read;
import com.javaf.javase.persistence.dialect.IDialect;
import com.javaf.javase.persistence.dialect.INativeExecuter;



public class JDBCManager extends Manager {
	
	private final ILogging logging = Logging.loggerOf(getClass());
	
	private Context context;	
	private ITransaction transaction;
	private INativeExecuter executer;
	
	public JDBCManager(final IDialect<String> dialect){
		super(dialect);
		this.context = dialect.context();
	}
	
	public ITransaction getTransaction() {
		if(null== transaction){
			transaction = new JDBCTransaction(context);
			
			logging.trace("TRANSACTION CREATED AT CONTEXT " + context);
		}
		
		return transaction;
	}

	public IQuery createNativeQuery(final String nativeQuery) {
		IQuery _result = null;
		
		_result = new JDBCNativeQuery(nativeQuery, getExecuter());
		
		return _result;
	}

	public IQuery createQuery(final Read read) {
		IQuery _result = null;
		
		_result = new JDBCQuery(read, getExecuter());
		
		return _result;
	}

	@Override
	protected INativeExecuter getExecuter() {
		if(null== executer){
			executer = new SQLExecuter(dialect);
		}
		
		return executer;
	}
	
	public void close() {
		final Connection _connection = dialect.context().connection();
		
		try{
			logging.debug(" ################# CLOSE CONNECTION " + _connection);
			_connection.close();
			
			//limpar contexto
			dialect.context().clean();
			
		}catch(SQLException _e){
			logging.debug(" ################# CONNECTION WAS NOT CLOSE: " + _e.getMessage(), _e);
			
			throw new PersistenceException(_e.getMessage(), _e);
		}
	}
	
	public boolean isOpen() {
		boolean _result = false;
		
		final Connection _connection = dialect.context().connection();
		try{
			_result = !_connection.isClosed();
			
		}catch(SQLException _e){
			throw new PersistenceException(_e.getMessage(), _e);
		}
		
		return _result;
	}
}
