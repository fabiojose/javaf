package com.javaf.javase.persistence.dialect;

import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.persistence.Context;
import com.javaf.javase.persistence.dialect.generic.GenericSQLDialect;


public abstract class Dialect<T> implements IDialect<T> {
	
	protected final ILogging logging;
	
	public Dialect(){
		logging = Logging.loggerOf(getClass());
	}

	public static synchronized SQLDialect newInstance(final Context context){
		SQLDialect _result = null;
		
		_result = new GenericSQLDialect(context);
		
		return _result;
	}
}
