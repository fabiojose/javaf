package com.javaf.javase.persistence.builder;

import com.javaf.javase.persistence.Column;
import com.javaf.javase.persistence.Criteria;
import com.javaf.javase.persistence.OperatorType;
import com.javaf.javase.persistence.dialect.IOperator;
import com.javaf.pattern.Builder;


public class WhereClauseBuilder implements Builder<Criteria, StringBuilder> {
	
	private IOperator operator;
	public WhereClauseBuilder(final IOperator operator){
		this.operator = operator;
	}
	
	private String asString(final Object item){
		String _result = null;
		
		if(item instanceof Column){
			final Column<?> _column = (Column<?>)item;
			_result = _column.getName();
			
		} else if(item instanceof OperatorType){
			final OperatorType _operator = (OperatorType)item;
			_result = operator.operatorOf(_operator);
			
		} else {
			_result = "?";
		}
		
		return _result;
	}
	
	public StringBuilder build(final Criteria criteria) {
		final StringBuilder _result = new StringBuilder();
		
		for(Object _item : criteria.stack()){
			_result.append(asString(_item));
			_result.append(" ");
		}
		
		return _result;
	}

}
