package bradesco.tf.mediator;

import java.util.HashMap;
import java.util.Map;

import com.javaf.javase.lang.reflect.UtilReflection;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public abstract class Mediatorbag<T> {
	private static final Map<String, Class<?>> MEDIATORS_CLASSES = new HashMap<String, Class<?>>();
	
	private final UtilReflection reflection = UtilReflection.getInstance();
	private final Map<String, T> mediators  = new HashMap<String, T>();
	
	public static final <T extends IEventMediatorTF> void register(final String name, final Class<T> mediator){
		MEDIATORS_CLASSES.put(name, mediator);
	}
	
	@SuppressWarnings("unchecked")
	public T mediatorOf(final String name){
		T _result = mediators.get(name);
		
		if(null== _result){
			final Class<?> _class = MEDIATORS_CLASSES.get(name);
			_result = (T)reflection.newInstance(_class);	
			
			mediators.put(name, _result);
		}
		
		return _result;
	}
}
