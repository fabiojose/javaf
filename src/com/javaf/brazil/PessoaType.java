package com.javaf.brazil;

import com.javaf.Constants;
import com.javaf.javase.text.UtilFormat;
import com.javaf.pattern.DeenString;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public enum PessoaType implements DeenString<PessoaType> {

	FISICA,
	JURIDICA;

	private UtilFormat format = UtilFormat.getInstance();
	public PessoaType decode(final String tipo) {
		
		final String _key = format.toString(tipo).toUpperCase();
		final Object _value = Constants.DEFAULT.PESSOAS_TIPO.get(_key);
		
		if(Constants.DEFAULT.PESSOA_JURIDICA.equals(_value)){
			return JURIDICA;
		}
		
		return FISICA;
	}
	
	public String encode(final PessoaType tipo){
		
		String _result = null;
		if(PessoaType.JURIDICA.equals(tipo)){
			_result = Constants.DEFAULT.PESSOA_JURIDICA;
			
		} else {
			_result = Constants.DEFAULT.PESSOA_FISICA;
		}
		
		return _result;
	}
}