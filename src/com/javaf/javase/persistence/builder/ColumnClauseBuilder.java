package com.javaf.javase.persistence.builder;

import java.util.Iterator;
import java.util.Set;

import com.javaf.javase.persistence.Column;
import com.javaf.pattern.Builder;



public class ColumnClauseBuilder implements Builder<Set<Column<?>>, StringBuilder> {

	public StringBuilder build(final Set<Column<?>> input) {
		final StringBuilder _result = new StringBuilder();
		
		final Iterator<Column<?>> _iterator = input.iterator();
		while(_iterator.hasNext()){
			final Column<?> _column = _iterator.next();
			
			_result.append(_column.getName());
			if(_iterator.hasNext()){
				_result.append(", ");
			}
		}
		
		return _result;
	}

}
