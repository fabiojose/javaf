package com.javaf.javase.persistence.dialect;

import com.javaf.Constants.PERSISTENCE;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.javase.persistence.Context;
import com.javaf.javase.persistence.IDML;
import com.javaf.javase.persistence.builder.TableNameBuilder;
import com.javaf.pattern.Builder;


public abstract class DMLDialect<E> {
	
	protected final ILogging logging;
	
	protected final Builder<IDML, String> table = new TableNameBuilder();

	private Context context;
	public DMLDialect(final Context context){
		this.context = context;
		
		logging = Logging.loggerOf(getClass());
	}

	public Context getContext() {
		return context;
	}

	protected void setContext(Context context) {
		this.context = context;
	}
	
	public boolean isPretty(){
		boolean _result = false;
		
		_result = Boolean.parseBoolean( (String)context.get(PERSISTENCE.SQL_PRETTY) );
		
		return _result;
	}
	
	public String getSeparator(){
		String _result = "";
		
		if(isPretty()){
			_result = PERSISTENCE.LINE_SEPARATOR;
		}
		
		return _result;
	}
	
	public abstract String sql(E entity);
}
