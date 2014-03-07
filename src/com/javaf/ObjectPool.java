package com.javaf;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.javaf.javase.logging.ILogging;
import com.javaf.javase.logging.Logging;
import com.javaf.pattern.factory.FactoryException;
import com.javaf.pattern.factory.IFactory;



/**
 * Pool de objetos.
 * 
 * @author fabiojm
 */
public final class ObjectPool {

	private static final ThreadLocal<ObjectPool> POOL = new ThreadLocal<ObjectPool>();
	
	private static final ILogging LOGGING = Logging.loggerOf(ObjectPool.class);
	
	private final Map<Object, Object> objects;
	private ObjectPool(){
		objects = new HashMap<Object, Object>();
	}
	
	/**
	 * Obter o pool de trabalho no contexto da Thread corrente.
	 * 
	 * @return Pool de objetos
	 */
	public static synchronized ObjectPool getPool(){
		
		ObjectPool _pool = POOL.get();
		if(null== _pool){
			_pool = new ObjectPool();
			POOL.set(_pool);
		}
		
		return _pool;
	}
	
	/**
	 * Cria uma instância de ObjectPool para utilizar em contextos personalizados.
	 * 
	 * @return Nova instância de ObjectPool
	 */
	public static synchronized ObjectPool newPool(){
		return new ObjectPool();
	}
	
	/**
	 * Obter ou criar um instância caso não esteja no pool ainda.
	 * 
	 * @param <T> Tipo do objeto
	 * @param name Nome do objeto dentro do pool
	 * @param type Tipo do objeto
	 * @return Instância do pool, nova instância ou <code>null</code> se houver erro na instanciação
	 */
	@SuppressWarnings("unchecked")
	public <T> T getOrCreate(final Object name, final Class<T> type){
		
		T _result = null;
		Object _object = objects.get(name);
		if(null!= _object){
			_result = (T)_object;
		} else {
			//tentar instanciar um objeto
			try{
				_result = type.newInstance();
				objects.put(name, _result);
				
			}catch(InstantiationException _e){
				LOGGING.error(_e.getMessage(), _e);
				
			}catch(IllegalAccessException _e){
				LOGGING.error(_e.getMessage(), _e);
			}
		}
		
		return _result;
	}
	
	/**
	 * Obter ou criar um instância com uma Factory caso não esteja no pool ainda.
	 * 
	 * @param <T> Tipo do objeto
	 * @param name Nome do objeto dentro do pool
	 * @param type Tipo do objeto
	 * @param factory Fábrica do objeto, caso ele não esteja no pool
	 * @return Instância do pool, nova instância ou <code>null</code> se houver erro na instanciação
	 */
	@SuppressWarnings("unchecked")
	public <T> T getOrCreate(final Object name, final Class<T> type, final IFactory factory){
		
		T _result = null;
		Object _object = get(name, type);
		if(null!= _object){
			_result = (T)_object;
			
		} else {
			try{
				_object = factory.newInstance();
				_result = (T)_object;
				objects.put(name, _result);
				
			}catch(FactoryException _e){
				LOGGING.error(_e.getMessage(), _e);
			}
		}
		return _result;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(final Object name, final Class<T> type){
		
		return (T)objects.get(name);
	}
	
	/**
	 * O valor padrão para retorno quando null não será armazenado no pool
	 * @param <T>
	 * @param name
	 * @param type
	 * @param forNull Valor que será retornado se objeto não estiver no pool
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(final Object name, final Class<T> type, final T forNull){
		
		T _result = (T)objects.get(name);
		if(null== _result){
			_result = forNull;
		}
		return _result;
	}
	
	public Object get(final Object name){
		return objects.get(name);
	}
	
	public boolean has(final Object name){
		return objects.containsKey(name);
	}
	
	public void put(final Object name, final Object value){
		objects.put(name, value);
	}
	
	public Set<Object> keySet(){
		return objects.keySet();
	}
	
	public Object remove(final Object name){
		return objects.remove(name);
	}
	
	public Object remove(final Object name, final Object onNull){
		
		Object _result = remove(name);
		if(null== _result){
			_result = onNull;
		}
		
		return _result;
	}

}
