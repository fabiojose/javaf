package com.javaf.javase.lang;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.javaf.Constants.REGEX;
import com.javaf.javase.text.UtilFormat;
import com.javaf.javase.util.regex.UtilRegex;
import com.javaf.pattern.Visitor;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class StringNamedParameterVisitor implements Visitor<String, String> {
	
	private final UtilRegex regex   = UtilRegex.getInstance();
	private final UtilFormat format = UtilFormat.getInstance();

	private Map<String, Object> named;	
	public StringNamedParameterVisitor(final Map<String, Object> named){
		if(null== named){
			throw new NullPointerException("arg1 is null!");
		}
		
		if(named.isEmpty()){
			throw new IllegalArgumentException("arg1 is empty!");
		}
		
		this.named = named;
	}
	
	private String named(final String source, final Set<String> namedInSource) throws IllegalArgumentException {
		String _result = source;
		
		for(String _named : namedInSource){
			final String _parameter = regex.namedParameterOf(_named);
			
			if(named.containsKey(_parameter)){
				_result = _result.replace(_named, format.toString(named.get(_parameter)));
			} else {
				throw new IllegalArgumentException("value of named parameter not found: " + _parameter);
			}
		}
		
		return _result;
	}
	 
	public String visit(final String source) throws IllegalArgumentException {
		String _result = source;
		
		if(source.matches(REGEX.NAMED_PARAMETER_CONTAINS)){
			final Set<String> _named = regex.valueOf(source, REGEX.NAMED_PARAMETER);
			
			_result = named(source, _named);
			
		}
		
		return _result;
	}

	public static void main(String...args){
		final Map<String, Object> _named = new HashMap<String, Object>();
		_named.put("cliente.nome", "Fábio José de Moraes");
		_named.put("cliente.idade", 30);
		_named.put("cliente.celular", null);
		
		final String _source = "SELECT * FROM tcliente WHERE tcliente.nome = :cliente.nome: AND tcliente.idade = :cliente.idade: AND tclient.telefone = :cliente.celular: :cliente.endereco:";
		String _returned = new StringNamedParameterVisitor(_named).visit(_source);
		System.out.println(_returned);
	}
}
