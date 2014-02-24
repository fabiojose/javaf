package com.javaf.javase.persistence.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.persistence.IQuery;
import com.javaf.javase.persistence.IResults;
import com.javaf.javase.persistence.PersistenceException;
import com.javaf.javase.persistence.Read;
import com.javaf.javase.persistence.dialect.INativeExecuter;



public class JDBCQuery implements IQuery {

	private final ILogging logging = Logging.loggerOf(getClass());
	
	private PreparedStatement prepared;

	private final Read read;
	private final INativeExecuter executer;
	
	JDBCQuery(final Read read, final INativeExecuter executer){
		this.read     = read;
		this.executer = executer;
	}
	
	public IResults execute() {
		IResults _result = null;
		
		if(null== prepared){
			try{
				prepared = executer.read(read);
				final ResultSet _rs = prepared.executeQuery();
				
				_result = new JDBCResult(_rs);
				
			}catch(SQLException _e){
				logging.trace("EXECUTE QUERY - SQL EXCEPTION: " + _e.getMessage(), _e);
				throw new PersistenceException(_e.getMessage(), _e);
			}
			
		} else {
			logging.trace("EXECUTE QUERY: illegal state");
			throw new IllegalStateException();
		}
		
		return _result;
	}


}
