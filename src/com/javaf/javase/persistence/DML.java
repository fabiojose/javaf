package com.javaf.javase.persistence;

import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;

public abstract class DML implements IDML {

	protected final ILogging logging;
	public DML(){
		logging = Logging.loggerOf(getClass());
	}

}
