package com.javaf.javase.persistence.builder;

import java.util.ArrayList;
import java.util.List;

import com.javaf.javase.persistence.Column;
import com.javaf.javase.persistence.Criteria;
import com.javaf.javase.persistence.OperatorType;
import com.javaf.pattern.Builder;



public class WhereValueBuilder implements Builder<Criteria, List<Object>> {

	public WhereValueBuilder(){
	
	}
	
	private boolean isValue(final Object o){
		boolean _result = Boolean.FALSE;
		
		_result = !(o instanceof OperatorType) && !(o instanceof Column);
		
		return _result;
	}

	public List<Object> build(final Criteria criteria) {
		final List<Object> _result = new ArrayList<Object>();
		
		for(Object _item : criteria.stack()){
			if(isValue(_item)){
				_result.add(_item);
			}
		}
		
		return _result;
	}

}
