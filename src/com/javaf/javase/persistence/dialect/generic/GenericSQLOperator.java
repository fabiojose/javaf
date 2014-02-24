package com.javaf.javase.persistence.dialect.generic;

import java.util.HashMap;
import java.util.Map;

import com.javaf.javase.persistence.Column;
import com.javaf.javase.persistence.OperatorType;
import com.javaf.javase.persistence.dialect.IOperator;



public class GenericSQLOperator implements IOperator {
	
	private static final Map<OperatorType, String> OPERATORS = new HashMap<OperatorType, String>();
	static{
		OPERATORS.put(OperatorType.EQUAL,            "=");
		OPERATORS.put(OperatorType.LESS,             "<");
		OPERATORS.put(OperatorType.GREATER,          ">");
		OPERATORS.put(OperatorType.LESS_OR_EQUALS,   "<=");
		OPERATORS.put(OperatorType.GREATER_OR_EQUAL, ">=");
		OPERATORS.put(OperatorType.DIFFERENT,        "!=");
		OPERATORS.put(OperatorType.AND,              "AND");
		OPERATORS.put(OperatorType.OR,               "OR");
	}

	public String operatorOf(final OperatorType operator){
		return OPERATORS.get(operator);
	}

	public boolean isOperator(Object o) {
		return (o instanceof OperatorType);
	}

	public boolean isValue(Object o) {
		return ( !isOperator(o) && !(o instanceof Column) );
	}
	
}
