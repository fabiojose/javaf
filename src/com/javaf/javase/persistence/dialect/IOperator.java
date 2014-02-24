package com.javaf.javase.persistence.dialect;

import com.javaf.javase.persistence.OperatorType;

public interface IOperator {

	String operatorOf(OperatorType type);
	boolean isOperator(Object o);
	boolean isValue(Object o);
	
}
