package com.javaf.javase.persistence.dialect.generic;

import java.util.Map;

import com.javaf.javase.lang.StringBuilderWrapper;
import com.javaf.javase.persistence.Column;
import com.javaf.javase.persistence.Context;
import com.javaf.javase.persistence.Criteria;
import com.javaf.javase.persistence.Update;
import com.javaf.javase.persistence.dialect.DMLDialect;
import com.javaf.pattern.Builder;



public class GenericSQLUpdate extends DMLDialect<Update> {
	
	private final Builder<Map<Column<?>, Object>, StringBuilder> set;
	private final Builder<Criteria, StringBuilder> where;

	GenericSQLUpdate(final Context context, final Builder<Map<Column<?>, Object>, StringBuilder> set, final Builder<Criteria, StringBuilder> where){
		super(context);
		this.set   = set;
		this.where = where;
	}
	
	public String sql(final Update update){
		final StringBuilderWrapper _result = new StringBuilderWrapper(getSeparator());
		
		_result.append("UPDATE ");
		_result.append(table.build(update));
		_result.append(" ");
		
		_result.append("SET ");
		_result.append(set.build(update.values()));
		_result.append(" ");
		_result.append("WHERE ");
		_result.append(where.build(update.criteria()));
		
		logging.debug("UPDATE: \n" + _result.toString());
		
		return _result.toString();
	}
}
