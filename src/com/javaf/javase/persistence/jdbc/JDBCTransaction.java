package com.javaf.javase.persistence.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.persistence.Context;
import com.javaf.javase.persistence.ITransaction;
import com.javaf.javase.persistence.PersistenceException;



public class JDBCTransaction implements ITransaction {

	private final ILogging logging;
	
	private Context context;
	
	/**
	 * 
	 * @param context
	 * @throws PersistenceException
	 */
	JDBCTransaction(final Context context){
		if(null== context){
			throw new NullPointerException("arg1 is null!");
		}
		
		this.context = context;
		this.logging = Logging.loggerOf(getClass());
	}
	
	public void begin() {
		final Connection _connection = context.connection();
		
		try{
			logging.debug(" >>>>>>>>>>>>>>>>> BEGIN TRANSACTION AT " + _connection);
			_connection.setAutoCommit(Boolean.FALSE);
		
		}catch(SQLException _e){
			logging.debug(_e.getMessage(), _e);
			
			throw new PersistenceException(_e.getMessage(), _e);
		}
	}
	
	private void close() throws SQLException {
		
		logging.debug(" ################# CLOSING STATEMENTS . . .");
		//fechar todos os statements na lista para fechamento
		for(Statement _statement : context.getToClose()){
			_statement.close();
			logging.debug(" ################# STATEMENT CLOSED: " + _statement);
		}
		
		context.getToClose().clear();
	}

	public void commit() {
		final Connection _connection = context.connection();
		
		try{
			close();
			
			logging.debug(" >>>>>>>>>>>>>>>>> COMMIT TRANSACTION AT " + _connection);
			_connection.commit();
			
		}catch(SQLException _e){
			logging.debug(" ---------------- TRANSACTION TRANSACTION COMMIT: " + _e.getMessage(), _e);
			throw new PersistenceException(_e.getMessage(), _e);
		}
	}

	public void rollback() {
		final Connection _connection = context.connection();
		
		try{
			close();
			
			logging.debug(" >>>>>>>>>>>>>>>>> ROLLBACK TRANSACTION AT " + _connection);
			_connection.rollback();
			
		}catch(SQLException _e){
			logging.debug(" ---------------- TRANSACTION TRANSACTION ROLLBACK: " + _e.getMessage(), _e);
			throw new PersistenceException(_e.getMessage(), _e);
		}
	}

}
