package com.javaf.javase.lang;

import java.util.Map;

import com.javaf.Bagman;
import com.javaf.Utility;
import com.javaf.Constants.INTEGER;
import com.javaf.Constants.REGEX;
import com.javaf.Constants.STRING;
import com.javaf.pattern.Visitor;
import com.javaf.pattern.factory.Factory;
import com.javaf.pattern.factory.FactoryException;
import com.javaf.pattern.factory.IFactory;

/**
 * Implementa métodos facilitadores para manipulação de String.
 * @author fabiojm - Fábio José de Moraes
 */
public class UtilString implements Utility {
	private static final IFactory FACTORY = new Factory(){

		public Object newInstance() throws FactoryException {
			return new UtilString();
		}
		
	};
	
	private UtilString(){
		
	}
	
	/**
	 * Obter uma instância de trabalho.
	 */
	public static synchronized UtilString getInstance(){
		return Bagman.utilOf(UtilString.class, FACTORY);
	}

	private String[] split(final String string, final String regex) {
		return string.split(regex);
	}
	
	/**
	 * Fragmentar uma string em uma quantidade limitada de palavras por linha
	 * 
	 * @param string String de entrada para fragmentar em palavras
	 * @param palavras Quantidade máxima de palavras por linha
	 * @return String com o caratecre <code>\n</code> separando as linhas 
	 */
	public String split(final String string, final int palavras) {

		String _result = STRING.EMPTY;
		final String[] _linhas = split(string, STRING.NEW_LINE);
		for (String _linha : _linhas) {
			final String[] _palavras = split(_linha, STRING.SPACE1);

			if (_palavras.length > palavras) {
				int _counter = INTEGER._0;
				for (String _palavra : _palavras) {
					_result += _palavra + (_counter == INTEGER._8 ? STRING.NEW_LINE : STRING.SPACE1);
					_counter = (_counter == INTEGER._8 ? INTEGER._0 : ++_counter);
				}
			} else {
				_result += _linha;
			}

			_result += STRING.NEW_LINE;
		}

		return _result;
	}
	
	/**
	 * Previne lançamento de <code>NullPointerException</code> e aplicar trim na string
	 * 
	 * @param string Instância para aplicar trim
	 * @return Instância com o trim aplicado ou <code>STRINGS.EMPTY</code> se string for <code>null</code>
	 */
	public String trim(final String string){
    	if(null!= string){
    		return string.trim();
    	}
    	
    	return STRING.EMPTY;
    }
	
	public String trim(final String source, final char trimChar){
		String _result = source;
		
		int _begin = INTEGER._1NEG;
		int _end   = INTEGER._1NEG;
		
		if(null!= source){
			_end = source.length();
			
			char _char = trimChar;
			 
			for(int _index = INTEGER._0; 
					_index < source.length();
					_index++){
				
				_char = source.charAt(_index);
				if(_char == trimChar){
					_begin++;
				} else {
					break;
				}
			}
			
			for(int _index = source.length() - INTEGER._1; 
					_index > INTEGER._0; 
					_index--){
				
				_char = source.charAt(_index);
				if(_char == trimChar){
					_end--;
				} else {
					break;
				}
			}
			
		}
		
		if(null!= source && 
				(_begin > INTEGER._1NEG || _end > INTEGER._1NEG)){
			final int __begin = (_begin > INTEGER._1NEG ? _begin + INTEGER._1 : INTEGER._0);
			final int __end   = (_end   > INTEGER._1NEG ? _end                    : source.length());
			
			_result = source.substring(__begin, __end); 
		}
		
		
		return _result;
	}
	
	public String remove(final String source, final String...toRemove){
		return remove(source, RemovePolicy.ALL, toRemove);
	}
	
	public String remove(final String source, final RemovePolicy policy, final String...toRemove){
		
		String _result = source;
		if(null!= toRemove && null!= _result && null!= policy){
			for(String _remove : toRemove){
				if(RemovePolicy.FIRST.equals(policy)){
					_result = _result.replaceFirst(_remove, STRING.EMPTY);
					
				} else if(RemovePolicy.ALL.equals(policy)){
					
					_result = _result.replace(_remove, STRING.EMPTY);
				}
			}
		}
		
		return _result;
	}
	
	public boolean hasHolder(final String source){
		boolean _result = Boolean.FALSE;
		
		if(null!= source){
			_result = source.matches(REGEX.LABEL_HOLDER);
		}
		
		return _result;
	}
	
	public String holderOf(final String key){
		String _result = key;
		
		if(null!= key && !key.matches(REGEX.HOLDER)){
			_result = STRING.COLCHETES_ABRE + key + STRING.COLCHETES_FECHA;
		}
		
		return _result;
	}
	
	public String substring(final String source, final String start, final String end){
		String _result = source;
		
		final int _start = source.indexOf(start) + start.length();
		final int _end   = source.indexOf(end, _start);
		
		_result = source.substring(_start, _end);
		
		return _result;
	}
	
	public String named(final String source, final Map<String, Object> parameters) throws IllegalArgumentException {
		String _result = source;
		
		final Visitor<String, String> _visitor = new StringNamedParameterVisitor(parameters);
		_result = _visitor.visit(source);
		
		return _result;
	}

	public String dots(final String...id){
		return join(STRING.DOT, id);
	}
	
	public String join(final String separator, final String...id){
		final StringBuilder _result = new StringBuilder();
		
		for(int _index = INTEGER._0; _index < id.length; ){
			final String _id = id[_index];
			
			_index++;
			_result.append(_id).append( (_index < id.length ? separator : STRING.EMPTY) );
		}
		
		return _result.toString();
	}
}
