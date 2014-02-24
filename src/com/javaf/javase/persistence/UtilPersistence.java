package com.javaf.javase.persistence;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.javaf.Constants.PERSISTENCE;
import com.javaf.Constants.SQL;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.persistence.dialect.ObtainDialectPropertyBridge;
import com.javaf.pattern.Bridge;



public final class UtilPersistence {
	private static final UtilPersistence INSTANCE = new UtilPersistence();
	private static final ILogging LOGGING         = Logging.loggerOf(UtilPersistence.class);
	
	private static final Bridge<Object, Object> OBTAIN = new ObtainDialectPropertyBridge();
	
	private static IConnectionFactory CONNECTION_FACTORY;
	
	private static final Properties PROPERTIES = new Properties();
	static{
		try{
			PROPERTIES.load(UtilPersistence.class.getResourceAsStream("persistence.properties"));
			
		}catch(IOException _e){
			throw new RuntimeException(_e.getMessage(), _e);
		}
	}
	
	private final UtilReflection reflection = UtilReflection.getInstance();;
	private UtilPersistence(){
		
	}
	
	public static final synchronized UtilPersistence getInstance(){
		return INSTANCE;
	}
	
	public static final String getProperty(final String property){
		return PROPERTIES.getProperty(property);
	}
	
	public static final Properties propertiesOf(final String prefix){
		return propertiesOf(prefix, true);
	}
	
	public static final Properties propertiesOf(final String prefix, boolean removePrefix){
		final Properties _result = new Properties();
		
		final Set<Object> _keys = PROPERTIES.keySet();
		for(Object _key : _keys){
			if(_key.toString().startsWith(prefix)){
				if(removePrefix){
					_result.put(_key.toString().substring(prefix.length()), PROPERTIES.get(_key));
				} else {
					_result.put(_key, PROPERTIES.get(_key));
				}
			}
		}
		
		return _result;
	}
	
	/**
	 * 
	 * @throws PersistenceException
	 */
	public static final IConnectionFactory getConnectionFactory(){
		
		if(null== CONNECTION_FACTORY){
			final String _sfactory = getProperty(PERSISTENCE.CONNECTION_FACTORY_PROPERTY);
			LOGGING.info("CONNECTION FACTORY: " + _sfactory);
			
			try{				
				final Class<?> _factory = Class.forName(_sfactory);
				CONNECTION_FACTORY = (IConnectionFactory)_factory.newInstance();
				
				final Properties _properties = propertiesOf(PERSISTENCE.CONNECTION_PREFIX, false);
				final Set<Object> _keys      = _properties.keySet();
				for(Object _key : _keys){
					CONNECTION_FACTORY.setProperty(_key.toString(), getProperty(_key.toString()));
				}
				
				LOGGING.info("INSTANCE OF CONNECTION FACTORY WAS CREATED: " + CONNECTION_FACTORY);
				
			}catch(ClassNotFoundException _e){
				LOGGING.error("CONNECTION FACTORY CLASS NOT FOUND: " + _e.getMessage(), _e);
				throw new PersistenceException(_e.getMessage(), _e);
				
			}catch(InstantiationException _e){
				LOGGING.error(_e.getMessage(), _e);
				throw new PersistenceException(_e.getMessage(), _e);
				
			}catch(IllegalAccessException _e){
				LOGGING.error(_e.getMessage(), _e);
				throw new PersistenceException(_e.getMessage(), _e);
				
			}catch(ClassCastException _e){
				
				LOGGING.error("CONNECTION FACTORY DO NOT IMPLEMENTS " + IConnectionFactory.class.getName() + ": " + _e.getMessage(), _e);
				throw new PersistenceException(_e.getMessage(), _e);
			}
		}
		
		return CONNECTION_FACTORY;
	}
	
	public String nameOf(final Class<? extends Table> table){
		String _result = null;
		
		final Field _name = reflection.fieldOf(table, PERSISTENCE.TABLE_NAME_FIELD);
		_result = reflection.valueOf(null, _name, String.class);
		
		return _result;
	}
	
	public List<Column<?>> columnsOf(final Class<? extends Table> table){
		final List<Column<?>> _result = new ArrayList<Column<?>>();
		
		final List<Field> _columns = reflection.fieldsOf(table, Column.class);
		for(Field _column : _columns){
			_result.add(reflection.valueOf(null, _column, Column.class));
		}
		
		return _result;
	}
	
	public Id idOf(final Class<? extends Table> table){
		Id _result = null;
		
		final Field _id = reflection.fieldOf(table, PERSISTENCE.TABLE_ID_FIELD);
		_result = reflection.valueOf(null, _id, Id.class);
		
		return _result;
	}

	public Date timestampOf(final IManager manager){
		Date _result = null;
		
		final INativeSQL _native = (INativeSQL)OBTAIN.cross(INativeSQL.class);
		
		final IQuery _query     = manager.createNativeQuery(SQL.VALUES + " " + _native.nativeOf(NativeType.NOW));
		final IResults _results = _query.execute();
		final IResult _current  = _results.next();
		
		if(null!= _current){
			_result = _current.get(SQL.CURRENT_TIMESTAMP);
			
			LOGGING.trace(_native.nativeOf(NativeType.NOW) + " = " + _result);
		}
		
		return _result;
	}
}
