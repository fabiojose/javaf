package com.javaf.javase.persistence.builder;

import com.javaf.Constants.PERSISTENCE;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.persistence.IDML;
import com.javaf.pattern.Builder;


public class TableNameBuilder implements Builder<IDML, String> {

	private final UtilReflection reflection = UtilReflection.getInstance();
	
	public String build(final IDML input) {
		return reflection.valueOf(input.table(), PERSISTENCE.TABLE_NAME_FIELD, String.class);
	}

}
