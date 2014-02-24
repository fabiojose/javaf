package com.javaf.javase.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.javaf.Application;
import com.javaf.Constants.APPLICATION;
import com.javaf.Constants.INTEGER;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;



@SuppressWarnings("unchecked")
public class Localization implements ILocalization {
	private static final ILocalization INSTANCE = new Localization();
	
	private static final ILogging LOGGING = Logging.loggerOf(Localization.class);
	
	private final Application application    = Application.getInstance();
	private final Locale locale              = application.valueOf(Locale.class);
	private final Map<Object, Object> mapped = application.valueOf(Map.class, APPLICATION.I18N_MAPPER);
	
	private ResourceBundle bundle;
	private Localization(){
		
	}
	
	public static final synchronized ILocalization getInstance(){
		return INSTANCE;
	}
	
	private final ResourceBundle getBundle(){
		if(null== bundle){
			bundle = ResourceBundle.getBundle(application.valueOf(String.class, APPLICATION.BUNDLE_PROPERTY), locale);
		}
		
		return bundle;
	}
	
	private final String obtain(final Object key, final Object...arguments){
		String _result = key.toString();
		
		try{
			_result = getBundle().getString(_result);
			
		}catch(MissingResourceException _e){
			LOGGING.warn(_e.getMessage());
		}
		
		if(arguments.length > INTEGER._0){
			_result = MessageFormat.format(_result, arguments);
		}
		
		return _result;
	}

	public String localize(final Object key) {
		return obtain(key);
	}

	public String localize(final Object key, final Object...arguments) {
		return obtain(key, arguments);
	}
	
	public String mapped(final Object object){
		String _result = null;
		
		final Object _mapped = mapped.get(object);
		_result = localize(_mapped);
		
		return _result;
	}

	public Locale getLocale() {
		return locale;
	}

}
