package com.javaf.javase.persistence.jdbc;

import java.util.HashMap;
import java.util.Map;

import com.javaf.javase.persistence.Context;
import com.javaf.javase.persistence.IManager;
import com.javaf.javase.persistence.IProvider;
import com.javaf.javase.persistence.dialect.Dialect;
import com.javaf.javase.persistence.dialect.SQLDialect;



public class JDBCProvider implements IProvider {

	private static JDBCProvider INSTANCE = new JDBCProvider();

	private final Map<String, Object> attributes = new HashMap<String, Object>();
	
	JDBCProvider(){
		
	}
	
	JDBCProvider(final Map<String, Object> attributes){
		this.attributes.putAll(attributes);
	}
	
	public static synchronized JDBCProvider getInstance(){		
		return INSTANCE;
	}
	
	private Context getContext(){
		return Context.newContext();
	}
	
	private SQLDialect getDialect(){
		SQLDialect _result = null;
		
		final Context _context = getContext();
		_result = Dialect.newInstance(_context);
		
		return _result;
	}

	public IManager createManager() {
		IManager _result = null;
		
		_result = new JDBCManager(getDialect());
		
		return _result;
	}
	
}
