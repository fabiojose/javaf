package bradesco.tf;

import java.util.Map;

import bradesco.tf.TFConstants.IDENTIFY;

import com.javaf.model.IDynamic;

/**
 * Implementa a manipulação de instâncias da interface {@link IDynamic}.
 * @author fabiojm - Fábio José de Moraes
 * @see IDynamic
 */
public final class UtilDynamic {
	private static final UtilDynamic INSTANCE = new UtilDynamic();
	
	private UtilDynamic(){
		
	}
	
	public static synchronized UtilDynamic getInstance(){
		return INSTANCE;
	}
	
	public void clearSuccess(final IDynamic dynamic){
		
		if(null!= dynamic){
			final boolean _before = dynamic.isExceptions();
			
			dynamic.setExceptions(Boolean.FALSE);
			
			final Map<?, ?> _success = dynamic.get(IDENTIFY.SUCCESS, Map.class);
			if(null!= _success){
				_success.clear();
			}
			
			dynamic.setExceptions(_before);
		}
	}
}
