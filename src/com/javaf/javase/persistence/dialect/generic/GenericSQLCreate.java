package com.javaf.javase.persistence.dialect.generic;

import java.util.Map;
import java.util.Set;

import com.javaf.javase.lang.StringBuilderWrapper;
import com.javaf.javase.persistence.Column;
import com.javaf.javase.persistence.Context;
import com.javaf.javase.persistence.Create;
import com.javaf.javase.persistence.builder.ColumnClauseBuilder;
import com.javaf.javase.persistence.dialect.DMLDialect;
import com.javaf.pattern.Builder;



public final class GenericSQLCreate extends DMLDialect<Create> {
	
	private final Builder<Map<Column<?>, Object>, StringBuilder> values;
	private final Builder<Set<Column<?>>, StringBuilder> clause = new ColumnClauseBuilder();
	
	GenericSQLCreate(final Context context, final Builder<Map<Column<?>, Object>, StringBuilder> values){
		super(context);
		
		this.values  = values;
	}

	public String sql(final Create create){
		final StringBuilderWrapper _result = new StringBuilderWrapper(getSeparator());
		
		final Set<Column<?>> _columns = create.values().keySet();
		
		_result.append("INSERT INTO ");
		_result.append(table.build(create));
		_result.append(" ");
		
		_result.append("(");
		_result.append(clause.build(_columns));
		_result.append(")");
		_result.append(" ");
		
		_result.append("VALUES (");
		_result.append(values.build(create.values()));
		_result.append(")");
		
		logging.debug("CREATE: \n" + _result.toString());
		
		return _result.toString();
	}
}
