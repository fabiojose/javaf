package com.javaf.javase.persistence;

import java.util.LinkedHashMap;
import java.util.Map;

public class Update extends DML {

	private Class<? extends Table> table;
	private final Map<Column<?>, Object> values = new LinkedHashMap<Column<?>, Object>();
	
	private Criteria criteria;
	
	public Update(){
		
	}
	
	public Class<? extends Table> table(){
		return table;
	}
	
	public Map<Column<?>, Object> values(){
		return values;
	}
	
	public Criteria criteria(){
		return criteria;
	}
	
	public Update update(final Class<? extends Table> table){
		this.table = table;
		
		logging.trace(" - - - - UPDATE - - - - " + table.getSimpleName());
		
		return this;
	}
	
	public <T> Update set(final Column<T> column, final T value){
		values.put(column, value);
		
		logging.trace(" - - - - UPDATE - - - - SET " + column + " = >" + value + "<");
		
		return this;
	}
	
	public <T> Update set(final Column<T> column, final NativeType type){
		values.put(column, type);
		
		logging.trace(" - - - - UPDATE - - - - SET " + column + " = >" + type + "<");
		
		return this;
	}
	
	public <T> Criteria where(final Column<T> column){
		criteria = new Criteria(column, this);
		
		return criteria;
	}
	
}
