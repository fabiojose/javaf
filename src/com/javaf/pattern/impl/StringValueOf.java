package com.javaf.pattern.impl;

import com.javaf.pattern.ValueOf;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class StringValueOf implements ValueOf<String, String> {

	public String valueOf(final String string) {
		return string;
	}

}
