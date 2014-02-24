package com.javaf.unhook.logging;

import com.javaf.pattern.factory.IFactory;
import com.javaf.unhook.Unhook;
import com.javaf.unhook.UnhookTargetFactory;

/**
 * 
 * @author fabiojm - Fabio Jose de Moraes
 *
 */
public class Logger extends Logging<ILogger> {

	private static final String CLASS_NAME           = "org.apache.log4j.Logger";
	private static final String FACTORY_METHOD_CLASS = "getLogger(java.lang.Class)";
	private static final String FACTORY_METHOD_NAME  = "getLogger(java.lang.String)";
	
	private static final String CONFIGURE_CLASS      = "org.apache.log4j.BasicConfigurator";
	private static final String CONFIGURE_METHOD     = "configure";
	
	private static final UnhookTargetFactory FACTORY_CLASS = new UnhookTargetFactory(CLASS_NAME/*, FactoryType.METHOD*/, FACTORY_METHOD_CLASS);
	private static final UnhookTargetFactory FACTORY_NAME  = new UnhookTargetFactory(CLASS_NAME/*, FactoryType.METHOD*/, FACTORY_METHOD_NAME);
	
	private Logger(String className, Class<ILogger> inface, IFactory factory, final String verbose) throws TypeNotPresentException {
		super(className, inface, factory, verbose);
	}
	
	public static final synchronized void configure(){
		Unhook.invoke(CONFIGURE_CLASS, CONFIGURE_METHOD);
	}

	public static final synchronized ILogger getLogger(final Class<?> clazz, final String verbose){
		final Unhook<ILogger> _unhook = new Logger(CLASS_NAME, ILogger.class, FACTORY_CLASS, verbose);
		
		return _unhook.hook(clazz);
	}
	
	public static final synchronized ILogger getLogger(final String name, final String verbose){
		final Unhook<ILogger> _unhook = new Logger(CLASS_NAME, ILogger.class, FACTORY_NAME, verbose);
		
		return _unhook.hook(name);
	}
	
	public static void main(String...args){
		
		final ILogger _logger = Logger.getLogger(String.class, "DESIGNF - MAIN - ");
		_logger.debug("Fabio Jose de Moraes");
		_logger.info("Age: 30");
	}
}
