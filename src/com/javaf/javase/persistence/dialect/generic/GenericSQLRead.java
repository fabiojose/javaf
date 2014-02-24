package com.javaf.javase.persistence.dialect.generic;

import java.util.Set;

import com.javaf.javase.lang.StringBuilderWrapper;
import com.javaf.javase.persistence.Column;
import com.javaf.javase.persistence.Context;
import com.javaf.javase.persistence.Criteria;
import com.javaf.javase.persistence.Read;
import com.javaf.javase.persistence.builder.ColumnClauseBuilder;
import com.javaf.javase.persistence.dialect.DMLDialect;
import com.javaf.pattern.Builder;



public class GenericSQLRead extends DMLDialect<Read> {

	private final Builder<Criteria, StringBuilder> where;
	private final Builder<Set<Column<?>>, StringBuilder> columns = new ColumnClauseBuilder();
	
	GenericSQLRead(final Context context, final Builder<Criteria, StringBuilder> where) {
		super(context);
		this.where = where;
	}

	public String sql(final Read r){
		final StringBuilderWrapper _result = new StringBuilderWrapper(getSeparator());
		
		_result.append("SELECT ");
		_result.append(columns.build(r.columns()));
		_result.append(" ");
		_result.append("FROM ");
		_result.append(table.build(r));
		_result.append(" ");
		_result.append("WHERE ");
		_result.append(where.build(r.criteria()));
		
		logging.debug("READ: \n" + _result.toString());
		
		return _result.toString();
	}
}
