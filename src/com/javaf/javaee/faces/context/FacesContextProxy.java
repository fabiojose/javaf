package com.javaf.javaee.faces.context;

import java.lang.reflect.Method;

import com.javaf.javase.lang.UtilClass;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class FacesContextProxy {
	
	private static final String FACES_CONTEXT = "javax.faces.context.FacesContext";
	
	private static final UtilClass CLAZZ           = UtilClass.getInstance();
	private static final UtilReflection REFLECTION = UtilReflection.getInstance();
	private static final ILogging LOGGING          = Logging.loggerOf(FacesContextProxy.class);
	
	private static Class<?> FACES_CLASS;
	
	private ExternalContextProxy external;
	private final Object context;
	private FacesContextProxy(final Object context){
		this.context = context;
	}
	
	private static Class<?> getFacesClass(){
		if(null== FACES_CLASS){
			try{
				FACES_CLASS = CLAZZ.load(FACES_CONTEXT);
				
			}catch(TypeNotPresentException _e){
				LOGGING.debug("RUNNING OUT OF FACES APPLICATION.", _e);
			}
		}
		
		return FACES_CLASS;
	}

	public static FacesContextProxy getCurrentInstance(){
		FacesContextProxy _result = null;
		
		final Class<?> _facesClass = getFacesClass();
		if(null!= _facesClass){
			final Method _getCurrentInstance = REFLECTION.methodOf(_facesClass, com.javaf.Constants.REFLECTION.METHOD_GET_CURRENT_INSTANCE);
			final Object _context = REFLECTION.invoke(null, _getCurrentInstance);
			
			if(null!= _context){
				_result = new FacesContextProxy( _context );
				LOGGING.trace("FACES CONTEXT OBTIDO >" + _context + "<");
			} else {
				LOGGING.trace("RUNNING OUT OF FACES APPLICATION.");
			}
			
		}
		
		return _result;
	}
	
	public ExternalContextProxy getExternalContext(){
		if(null== external){
			final Class<?> _facesClass = getFacesClass();
			final Method _getExternalContext = REFLECTION.methodOf(_facesClass, com.javaf.Constants.REFLECTION.METHOD_GET_EXTERNAL_CONTEXT);
			final Object _context            = REFLECTION.invoke(context, _getExternalContext);
			
			if(null!= _context){
				external = new ExternalContextProxy(_context);
				LOGGING.trace("EXTERNAL CONTEXT: " + _context);
			} else {
				LOGGING.trace("**NO** EXTERNAL CONTEXT.");
			}
		}
		
		return external;
	}
}
