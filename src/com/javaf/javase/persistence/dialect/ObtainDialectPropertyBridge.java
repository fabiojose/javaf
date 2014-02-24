package com.javaf.javase.persistence.dialect;

import com.javaf.javase.persistence.INativeSQL;
import com.javaf.javase.persistence.dialect.generic.GenericSQLDialect;
import com.javaf.pattern.Bridge;


public class ObtainDialectPropertyBridge implements Bridge<Object, Object> {

	public Object cross(final Object property) {
		Object _result = null;
		
		if(INativeSQL.class.equals(property)){
			_result = GenericSQLDialect.getNative();
		}
		
		return _result;
	}

}
