package com.javaf.javase.persistence.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.javaf.javase.lang.UtilClass;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.persistence.Column;
import com.javaf.javase.persistence.Context;
import com.javaf.javase.persistence.Create;
import com.javaf.javase.persistence.Criteria;
import com.javaf.javase.persistence.Delete;
import com.javaf.javase.persistence.NativeType;
import com.javaf.javase.persistence.PersistenceException;
import com.javaf.javase.persistence.Read;
import com.javaf.javase.persistence.Update;
import com.javaf.javase.persistence.builder.WhereValueBuilder;
import com.javaf.javase.persistence.dialect.IDialect;
import com.javaf.javase.persistence.dialect.INativeExecuter;
import com.javaf.pattern.Builder;



public class SQLExecuter implements INativeExecuter {
	
	private final ILogging logging = Logging.loggerOf(getClass());
	private final UtilClass clazz = UtilClass.getInstance();

	private final IDialect<String> dialect;
	private final Context context;
	
	private final Builder<Criteria, List<Object>> criteria = new WhereValueBuilder();
	
	public SQLExecuter(final IDialect<String> decorated){
		this.dialect = decorated;
		this.context = decorated.context();
	}
	
	private java.sql.PreparedStatement prepare(final String dml){
		java.sql.PreparedStatement _result = null;
		
		try{
			_result = context.connection().prepareStatement(dml);
			
		}catch(SQLException _e){
			throw new PersistenceException(_e.getMessage(), _e);
		}
		
		return _result;
	}
	
	private void apply(final java.sql.PreparedStatement prepared, final Map<Column<?>, Object> values){
		
		try{
			final Set<Column<?>> _columns = values.keySet();
			int _index = 1;
			for(Column<?> _column : _columns){
				final Object _value = values.get(_column);
				
				if( !(_value instanceof NativeType) ){
					prepared.setObject(_index, _value);
					
					logging.trace("parameter [" + _index + "] = " + _value + " (value type=" + clazz.typeOf(_value) + ", column type=" + _column.getType() + ")");
					
					_index++;
					
				} else {
					if(null!= _value){
						
						logging.trace("native sql = " + _value);
						
					} else {
						
						prepared.setNull(_index, Types.NULL);
						
						logging.trace("parameter [" + _index + "] NULL");
						
						_index++;
						
					}
				}
				
				
			}
			
		}catch(SQLException _e){
			throw new PersistenceException(_e.getMessage(), _e);
		}
	}
	
	private int offset(final Map<Column<?>, Object> values){
		int _result = 0;
		
		final Set<Column<?>> _columns = values.keySet();
		for(Column<?> _column : _columns){
			if( !(values.get(_column) instanceof NativeType) ){
				_result++;
			}
		}
		
		return _result;
	}
	
	private void apply(final java.sql.PreparedStatement prepared, final Map<Column<?>, Object> values, final List<Object> criteria){
		
		try{
			apply(prepared, values);
			
			int _index = offset(values) + 1;
			for(Object _value : criteria){
				if( !(_value instanceof NativeType) ){
					prepared.setObject(_index, _value);
					
					logging.trace("criteria parameter [" + _index + "] = " + _value + " (" + clazz.typeOf(_value) + ")");
					
					_index++;
				} else {
					logging.trace("criteria native sql " + _value);
				}
			}
			
		}catch(SQLException _e){
			throw new PersistenceException(_e.getMessage(), _e);
		}
	}
	
	/**
	 * 
	 * @param statement
	 * @return <code>true</code> if the first result is a ResultSet object; <code>false</false> if the first result is an update count or there is no result
	 * @throws PersistenceException
	 */
	public boolean execute(final PreparedStatement statement){
		boolean _result = false;
		
		try{
			logging.debug("EXECUTING STATEMENT: " + statement);
			_result = statement.execute();
			
			if(!_result){
				logging.debug("STATEMENT COUNT: >" + statement.getUpdateCount() + "<");
			}

			//adicionar à lista para fechamento
			context.toClose(statement);
			
		}catch(SQLException _e){
			throw new PersistenceException(_e.getMessage(), _e);
		}
		
		return _result;
	}
	
	public Context context() {
		return context;
	}
	
	public <E> PreparedStatement create(final E entity) { 
		java.sql.PreparedStatement _result = null;
		
		final Create _create = (Create)entity;
		
		final String _dml = dialect.create(_create);
		
		_result = prepare(_dml);
		
		apply(_result, _create.values());
		
		logging.debug("PREPARED CREATE");
		
		//executar
		execute(_result);
		
		return _result;
	}

	public <E> PreparedStatement delete(E entity) {
		PreparedStatement _result = null;
		
		final Delete _delete = (Delete)entity;
		
		final String _dml = dialect.delete(_delete);
		
		_result = prepare(_dml);
		
		final List<Object> _criteria = criteria.build(_delete.criteria());
		apply(_result, new HashMap<Column<?>, Object>(), _criteria);
		
		logging.debug("PREPARED DELETE");
		
		//executar
		execute(_result);
		
		return _result;
	}

	public <E> PreparedStatement read(E entity) {
		PreparedStatement _result = null;
		
		final Read _read = (Read)entity;
		
		final String _dml = dialect.read(_read);
		
		_result = prepare(_dml);
		
		final List<Object> _criteria = criteria.build(_read.criteria());
		apply(_result, new HashMap<Column<?>, Object>(), _criteria);
 		
		logging.debug("PREPARED READ");
		
		//executar
		execute(_result);
		
		return _result;
	}

	public <E> PreparedStatement update(final E entity) {
		PreparedStatement _result = null;
		
		final Update _update = (Update)entity;
		
		final String _dml = dialect.update(_update);
		
		_result = prepare(_dml);
		
		final List<Object> _criteria = criteria.build(_update.criteria());
		apply(_result, _update.values(), _criteria);
		
		logging.debug("PREPARED UPDATE");
		
		//executar
		execute(_result);
		
		return _result;
	}

}
