package com.javaf.javase.persistence.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.persistence.Column;
import com.javaf.javase.persistence.IResult;
import com.javaf.javase.persistence.IResults;
import com.javaf.javase.persistence.PersistenceException;



public class JDBCResult implements IResults, IResult {
	
	private static final ILogging LOGGING = Logging.loggerOf(JDBCResult.class);
	
	private ResultSet rs;
	JDBCResult(final ResultSet rs){
		this.rs = rs;
	}

	/**
	 * @throws PersistenceException
	 */
	public boolean hasNext() {
		boolean _result = false;
		
		try{
			_result = !rs.isLast();
			
			if(!_result){
				LOGGING.trace("HAS NO NEXT.");
			}
		}catch(SQLException _e){
			throw new PersistenceException(_e.getMessage(), _e);
		}
		
		return _result;
	}

	/**
	 * @return <code>null</code> Quando não hourem resultados.
	 * @throws PersistenceException
	 */
	public IResult next() {
		IResult _result = null;
		
		try{
			if(rs.next()){
				_result = this;
				
				LOGGING.trace("ROLLING TO NEXT RESULT . . .");
				
			} else {
				LOGGING.trace("NO MORE RESULTS.");
			}
			
		}catch(SQLException _e){
			throw new PersistenceException(_e.getMessage(), _e);
		}
		
		return _result;
	}
	
	public <T> T get(Column<T> column) {
		return get(column, null);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(final Column<T> column, final T forNull){
		T _result = null;
		
		try{
			if(-1== column.getIndex()){
				_result = (T)rs.getObject(column.getName());
			} else {
				_result = (T)rs.getObject(column.getIndex());
			}
			
			if(null== _result){
				_result = forNull;
				
				LOGGING.trace("**DEFAULT** RESULT OF COLUMN *--" + column + "--* >" + _result + "<");
				
			} else {
				LOGGING.trace("RESULT OF COLUMN *--" + column + "--* >" + _result + "<");
			}
			
		}catch(SQLException _e){
			throw new PersistenceException(_e.getMessage(), _e);
		}
		
		return _result;
	}

	public void remove() {
		LOGGING.trace("UNSUPPORTED OPERATION.");
		
		throw new UnsupportedOperationException();
	}

	public void close() {

		try{
			rs.close();
			
			LOGGING.trace("RESULT SET CLOSED.");
			
		}catch(SQLException _e){
			throw new PersistenceException(_e.getMessage(), _e);
		}
	}

}
