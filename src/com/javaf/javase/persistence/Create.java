package com.javaf.javase.persistence;

import java.util.LinkedHashMap;
import java.util.Map;

public class Create extends DML {

	private Class<? extends Table> into;
	private final Map<Column<?>, Object> values = new LinkedHashMap<Column<?>, Object>();
	
	public Create(){

	}
	
	public Create into(final Class<? extends Table> into){
		if(null== into){
			throw new NullPointerException("arg1 is null!");
		}
		
		this.into = into;
		
		logging.trace(" - - - - CREATE - - - - INTO " + into.getSimpleName());
		
		return this;
	}
	
	public <T> Create value(final Column<T> column, T value){
		values.put(column, value);
		
		logging.trace(" - - - - CREATE - - - - VALUE " + column + " = >" + value + "<");
		
		return this;
	}
	
	public <T> Create value(final Column<T> column, final NativeType type){
		values.put(column, type);
		
		logging.trace(" - - - - CREATE - - - - NATIVE SQL " + column + " = >" + type + "<");
		
		return this;
	}

	public Class<? extends Table> table() {
		return into;
	}

	public Map<Column<?>, Object> values() {
		return values;
	}

}
