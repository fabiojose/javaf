package bradesco.tf.mediator;

import java.util.HashMap;
import java.util.Map;

import com.javaf.javase.lang.reflect.UtilReflection;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class EventMediator {
	private static final EventMediator INSTANCE = new EventMediator();
	
	private static final Map<String, Class<? extends IEventMediatorTF>> MEDIATORS_CLASSES = new HashMap<String, Class<? extends IEventMediatorTF>>();
	
	private final UtilReflection reflection = UtilReflection.getInstance();
	private final Map<String, IEventMediatorTF> mediators = new HashMap<String, IEventMediatorTF>();
	private EventMediator(){
		
	}
	
	public static final synchronized EventMediator getInstance(){
		return INSTANCE;
	}
	
	public static final <T extends IEventMediatorTF> void register(final String name, final Class<T> mediator){
		MEDIATORS_CLASSES.put(name, mediator);
	}
	
	public IEventMediatorTF mediatorOf(final String name){
		IEventMediatorTF _result = mediators.get(name);
		
		if(null== _result){
			final Class<? extends IEventMediatorTF> _class = MEDIATORS_CLASSES.get(name);
			_result = reflection.newInstance(_class);
			
			mediators.put(name, _result);
		}
		
		return _result;
	}
}
