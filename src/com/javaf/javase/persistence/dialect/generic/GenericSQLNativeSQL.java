package com.javaf.javase.persistence.dialect.generic;

import java.util.HashMap;
import java.util.Map;

import com.javaf.javase.persistence.INativeSQL;
import com.javaf.javase.persistence.NativeType;



public class GenericSQLNativeSQL implements INativeSQL {
	
	private static final Map<NativeType, String> NATIVE = new HashMap<NativeType, String>();
	static{
		//NATIVE.put(NativeType.NOW, "CURRENT TIMESTAMP"); //db2
		NATIVE.put(NativeType.NOW, "CURRENT_TIMESTAMP"); //hsql
		NATIVE.put(NativeType.NULL, null);
	}
	
	public String nativeOf(final NativeType type) {
		return NATIVE.get(type);
	}

}
