package bradesco.tf.mediator;

import java.util.HashMap;
import java.util.Map;

import com.javaf.javase.lang.reflect.UtilReflection;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class BusinessMediator {
	private static final BusinessMediator INSTANCE = new BusinessMediator();
	
	private static final Map<String, Class<? extends IBusinessMediatorTF>> MEDIATORS_CLASSES = new HashMap<String, Class<? extends IBusinessMediatorTF>>();
	
	private final UtilReflection reflection = UtilReflection.getInstance();
	private final Map<String, IBusinessMediatorTF> mediators = new HashMap<String, IBusinessMediatorTF>();
	private BusinessMediator(){
		
	}
	
	public static final synchronized BusinessMediator getInstance(){
		return INSTANCE;
	}
	
	public static final <T extends IBusinessMediatorTF> void register(final String name, final Class<T> mediator){
		MEDIATORS_CLASSES.put(name, mediator);
	}
	
	public IBusinessMediatorTF mediatorOf(final String name){
		IBusinessMediatorTF _result = mediators.get(name);
		
		if(null== _result){
			final Class<? extends IBusinessMediatorTF> _class = MEDIATORS_CLASSES.get(name);
			_result = reflection.newInstance(_class);
			
			mediators.put(name, _result);
		}
		
		return _result;
	}
}
