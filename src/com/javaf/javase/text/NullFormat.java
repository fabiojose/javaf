package com.javaf.javase.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class NullFormat extends Format {
	private static final long serialVersionUID = -4642308249171076356L;

	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		return toAppendTo;
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		return null;
	}

}
