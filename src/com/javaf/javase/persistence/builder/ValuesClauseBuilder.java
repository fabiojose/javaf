package com.javaf.javase.persistence.builder;

import java.util.Collection;
import java.util.Map;

import com.javaf.javase.persistence.Column;
import com.javaf.pattern.Builder;



public class ValuesClauseBuilder implements Builder<Map<Column<?>, Object>, StringBuilder> {
	
	public StringBuilder build(final Map<Column<?>, Object> input) {
		final StringBuilder _result = new StringBuilder();
		
		final Collection<Object> _values = input.values();
		for(int _index = 0; _index < _values.size(); _index++){
			_result.append("?");
			
			if( (_index + 1) < _values.size()){
				_result.append(", ");
			}
		}
		
		return _result;
	}

}
