package com.javaf.javase.persistence.dialect.generic;

import com.javaf.javase.lang.StringBuilderWrapper;
import com.javaf.javase.persistence.Context;
import com.javaf.javase.persistence.Criteria;
import com.javaf.javase.persistence.Delete;
import com.javaf.javase.persistence.dialect.DMLDialect;
import com.javaf.pattern.Builder;


public final class GenericSQLDelete extends DMLDialect<Delete> {
	
	private final Builder<Criteria, StringBuilder> where;

	GenericSQLDelete(final Context context, final Builder<Criteria, StringBuilder> where) {
		super(context);
		
		this.where = where;
	}

	public String sql(final Delete delete){
		final StringBuilderWrapper _result = new StringBuilderWrapper(getSeparator());
		
		_result.append("DELETE ");
		_result.append("FROM ");
		_result.append(table.build(delete));
		_result.append(" ");
		_result.append("WHERE ");
		_result.append(where.build(delete.criteria()));
		
		logging.debug("DELETE: \n" + _result.toString());
		
		return _result.toString();
	}
}
