package com.javaf.javase.persistence.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.persistence.IQuery;
import com.javaf.javase.persistence.IResults;
import com.javaf.javase.persistence.PersistenceException;
import com.javaf.javase.persistence.dialect.INativeExecuter;



public class JDBCNativeQuery implements IQuery {
	
	private final ILogging logging = Logging.loggerOf(getClass());
	
	private PreparedStatement prepared;
	
	private final String dml;
	private final INativeExecuter executer;
	
	JDBCNativeQuery(final String dml, final INativeExecuter executer){
		this.dml      = dml;
		this.executer = executer;
	}

	public IResults execute() {
		IResults _result = null;
		
		if(null== prepared){
			logging.debug("**NATIVE** - CREATE NATIVE QUERY: " + dml);
			try{
				logging.trace("**NATIVE** - CREATE SQL STATEMENT . . .");
				prepared = executer.context().connection().prepareStatement(dml);
				
				logging.trace("**NATIVE** - EXECUTE QUERY . . .");
				executer.execute(prepared);
							
				_result = new JDBCResult(prepared.getResultSet());
				
			}catch(SQLException _e){
				logging.trace("**NATIVE** - SQL EXCEPTION: " + _e.getMessage(), _e);
				
				throw new PersistenceException(_e.getMessage(), _e);
			}
			
		} else {
			logging.trace("**NATIVE** - EXECUTE NATIVE QUERY: illegal state");
			throw new IllegalStateException();
		}
		
		return _result;
	}

}
