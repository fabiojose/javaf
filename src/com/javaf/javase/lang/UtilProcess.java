package com.javaf.javase.lang;

import java.io.IOException;

import com.javaf.Bagman;
import com.javaf.ExecutingException;
import com.javaf.Utility;
import com.javaf.Constants.INTEGER;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.pattern.factory.Factory;
import com.javaf.pattern.factory.FactoryException;
import com.javaf.pattern.factory.IFactory;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public final class UtilProcess implements Utility {
	private static final IFactory FACTORY = new Factory(){

		public Object newInstance() throws FactoryException {
			return new UtilProcess();
		}
		
	};
	
	private final ILogging logging = Logging.loggerOf(getClass());
	private UtilProcess(){
		
	}
	
	public static final synchronized UtilProcess getInstance(){
		return Bagman.utilOf(UtilProcess.class, FACTORY);
	}
	
	public int execute(final String command) throws ExecutingException{
		int _result = INTEGER._0;
		
		try{
			logging.debug("COMMAND TO EXECUTE: >" + command + "<");
			final Process _process = Runtime.getRuntime().exec(command);
			
			_result = _process.waitFor();
			logging.debug("COMMAND EXECUTED WHIT THIS EXIT VALUE: >" + _result + "<");
			
		}catch(IOException _e){
			logging.error(_e.getMessage(), _e);
			
			throw new ExecutingException(_e.getMessage(), _e);
			
		}catch(InterruptedException _e){
			logging.error(_e.getMessage(), _e);
			
			throw new ExecutingException(_e.getMessage(), _e);
		}
		
		return _result;
	}
}
