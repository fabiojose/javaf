package com.javaf.javase.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.javaf.Bagman;
import com.javaf.Utility;
import com.javaf.Constants.INTEGER;
import com.javaf.Constants.STRING;
import com.javaf.javase.lang.reflect.UtilReflection;
import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.pattern.ValueOf;
import com.javaf.pattern.factory.Factory;
import com.javaf.pattern.factory.FactoryException;
import com.javaf.pattern.factory.IFactory;



/**
 * 
 * @author fabiojm - Fabio Jose de Moraes
 *
 */
public final class UtilCollection implements Utility {	
	private static final IFactory FACTORY = new Factory(){

		public Object newInstance() throws FactoryException {
			return new UtilCollection();
		}
		
	};
	
	private static final ILogging LOGGING = Logging.loggerOf(UtilCollection.class);
	
	private UtilCollection(){
		
	}
	
	public static final synchronized UtilCollection getInstance(){
		return Bagman.utilOf(UtilCollection.class, FACTORY);
	}
	
	public <T> void copy(final List<T> from, final T[] to){
		if(null== from){
			throw new NullPointerException("arg1 is null");
		}
		
		if(null== to){
			throw new NullPointerException("arg2 is null");
		}
		
		if(to.length >= from.size()){
			for(int _index = INTEGER._0; _index < from.size(); _index++){
				to[_index] = from.get(_index);
			}
		} else {
			throw new IllegalArgumentException("arg1.size is greater than arg2.length");
		}
	}
	
	public Class<?>[] joinAsArray(final Class<?>[] array1, final Class<?>[] array2){
		final Class<?>[] _result = new Class<?>[array1.length + array2.length];
		
		int _index = INTEGER._0;
		for(Class<?> _item : array1){
			_result[_index++] = _item;
		}
		
		for(Class<?> _item : array2){
			_result[_index++] = _item;
		}
		
		return _result;
	}
	
	public Object[] joinAsArray(final Object[] array1, final Object[] array2){
		final Object[] _result = new Object[array1.length + array2.length];
		
		int _index = INTEGER._0;
		for(Object _item : array1){
			_result[_index++] = _item;
		}
		
		for(Object _item : array2){
			_result[_index++] = _item;
		}
		
		return _result;
	}

	public <T, V> boolean contains(final List<T> list, final String property, final V value){
		
		final UtilReflection _reflection = UtilReflection.getInstance();
		boolean _result = Boolean.FALSE;
		for(T _t : list){
			final Object _got = _reflection.valueOf(_t, property);
			
			if(value.equals(_got)){
				_result = Boolean.TRUE;
				break;
			}
		}
		return _result;
	}
	
	public <K, V> Map<K, V> clone(final Map<K, V> source){
		return new HashMap<K, V>(source);
	}
	
	/**
	 * Carregar os valores de um arquivo Properties, que obeça o seguinte padrão:<br>
	 * <pre>
	 *  0 = value 0
	 *  1 = value 1
	 *  2 = value 2
	 *  ...
	 *  n = value n
	 * </pre>
	 * Ou seja, as chaves são números de 0 até n.
	 * 
	 * @param properties
	 * @return Uma lista contendo os valores de cada chave numérica ou uma lista vazia caso ocorra alguma exceção de I/O.
	 */
	public List<String> listOf(final InputStream properties){
		final List<String> _result = new ArrayList<String>();
		
		try{
			final Properties _properties = new Properties();
			_properties.load(properties);
			
			for(int _index = INTEGER._0; true; _index++){
				
				String _value = _properties.getProperty(String.valueOf(_index));
				if(null!= _value){
					_result.add(_value);
				}else {
					break;
				}
			}
			
		}catch(IOException _e){
			LOGGING.error(_e.getMessage(), _e);
		}
		
		return _result;
	}
	
	/**
	 * Carregar os valores de um arquivo Properties, que obeça o seguinte padrão:<br>
	 * <pre>
	 *  0.key   = key 0
	 *  0.value = value 0
	 *  
	 *  1.key   = key 1
	 *  1.value = value 1
	 *  
	 *  2.key   = key 2
	 *  2.value = value 2
	 *  ...
	 *  n.key   = key n
	 *  n.value = value n
	 * </pre>
	 * Ou seja, as chaves são números de 0 até n cada qual com uma propriedade key e outra value
	 * 
	 * @param properties
	 * @return Um mapa contendo os valores de cada chave numérica ou um mapa vazio caso ocorra alguma exceção de I/O.
	 */
	public <K, V> Map<K, V> mapOf(final InputStream properties, final ValueOf<String, K> keyValueOf, final ValueOf<String, V> valueOf){
		final Map<K, V> _result = new HashMap<K, V>();
		
		try{
			final Properties _properties = new Properties();
			_properties.load(properties);
			
			for(int _index = INTEGER._0; true; _index++){
				
				String _key   = _properties.getProperty(String.valueOf(_index) + STRING.DOT + "key");
				String _value = _properties.getProperty(String.valueOf(_index) + STRING.DOT + "value");
				
				if(null!= _key && null!=_value){
					LOGGING.trace("MAPOF - READY ENTRY: KEY >" + _key + " VALUE >" + _value + "<");
					_result.put(keyValueOf.valueOf(_key), valueOf.valueOf(_value));
				} else {
					break;
				}
				
			}
			
		}catch(IOException _e){
			LOGGING.error(_e.getMessage(), _e);
		}
		
		return _result;
	}
}
