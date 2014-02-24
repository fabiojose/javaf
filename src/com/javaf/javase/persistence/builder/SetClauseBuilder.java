package com.javaf.javase.persistence.builder;

import java.util.Iterator;
import java.util.Map;

import com.javaf.javase.persistence.Column;
import com.javaf.javase.persistence.INativeSQL;
import com.javaf.javase.persistence.NativeType;
import com.javaf.pattern.Builder;



public class SetClauseBuilder implements Builder<Map<Column<?>, Object>, StringBuilder> {
	
	private INativeSQL sqln;
	
	public SetClauseBuilder(final INativeSQL sqln){
		this.sqln = sqln;
	}

	public StringBuilder build(final Map<Column<?>, Object> input) {
		final StringBuilder _result = new StringBuilder();
		
		final Iterator<Column<?>> _columns       = input.keySet().iterator();
		
		while(_columns.hasNext()){
			final Column<?> _column = _columns.next();
			final Object _value     = input.get(_column);
			_result.append(_column.getName());
			_result.append(" = ");
			
			if( !(_value instanceof NativeType) ){
				
				_result.append("?");
				
			} else {
				
				_result.append(sqln.nativeOf((NativeType)_value));
				
			}
			
			if(_columns.hasNext()){
				_result.append(", ");
			}
		}
		
		return _result;
	}

}
