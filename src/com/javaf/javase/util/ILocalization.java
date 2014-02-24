package com.javaf.javase.util;

import java.util.Locale;

/**
 * 
 * @author fabiojm - F�bio Jos� de Moraes
 *
 */
public interface ILocalization {

	String localize(Object key);
	String localize(Object key, Object... arguments);
	String mapped(Object object);
	Locale getLocale();
	
}
