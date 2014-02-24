package com.javaf.javase.persistence.dialect.generic;

import java.util.Map;

import com.javaf.javase.persistence.Column;
import com.javaf.javase.persistence.Context;
import com.javaf.javase.persistence.Create;
import com.javaf.javase.persistence.Criteria;
import com.javaf.javase.persistence.Delete;
import com.javaf.javase.persistence.INativeSQL;
import com.javaf.javase.persistence.Read;
import com.javaf.javase.persistence.Update;
import com.javaf.javase.persistence.builder.SetClauseBuilder;
import com.javaf.javase.persistence.builder.ValuesClauseBuilder;
import com.javaf.javase.persistence.builder.WhereClauseBuilder;
import com.javaf.javase.persistence.dialect.DMLDialect;
import com.javaf.javase.persistence.dialect.SQLDialect;
import com.javaf.pattern.Builder;



public class GenericSQLDialect implements SQLDialect {
	
	private Builder<Map<Column<?>, Object>, StringBuilder> values = new ValuesClauseBuilder();
	private static final INativeSQL NATIVE                        = new GenericSQLNativeSQL();
	private Builder<Map<Column<?>, Object>, StringBuilder> sets   = new SetClauseBuilder(NATIVE);
	private Builder<Criteria, StringBuilder> criteria             = new WhereClauseBuilder(new GenericSQLOperator());
	
	private Context context;
	
	private DMLDialect<Create> create;
	private DMLDialect<Read>   read;
	private DMLDialect<Update> update;
	private DMLDialect<Delete> delete;
	
	public GenericSQLDialect(final Context context){
		if(null== context){
			throw new NullPointerException("arg1 is null!");
		}
		
		this.context = context;
		
		this.create  = new GenericSQLCreate(this.context, this.values);
		this.update  = new GenericSQLUpdate(this.context, this.sets, this.criteria);
		this.read    = new GenericSQLRead(this.context, this.criteria);
		this.delete  = new GenericSQLDelete(this.context, this.criteria);
		
	}
	
	public static INativeSQL getNative(){
		return NATIVE;
	}

	public <E> String create(E entity) {
		return create.sql((Create)entity);
	}
	
	public <E> String read(E entity) {
		return read.sql((Read)entity);
	}
	
	public <E> String update(E entity) {
		return update.sql((Update)entity);
	}

	public <E> String delete(E entity) {
		return delete.sql((Delete)entity);
	}

	public Context context() {
		return context;
	}

	
}
