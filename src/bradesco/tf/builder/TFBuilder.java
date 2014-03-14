package bradesco.tf.builder;

import br.com.bradesco.core.aq.service.IServiceProvider;
import br.com.bradesco.core.serviceImpl.ServiceProvider;

import com.javaf.pattern.Builder;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class TFBuilder implements Builder<BuilderInput, Object> {
	private static final TFBuilder INSTANCE = new TFBuilder();
	
	@SuppressWarnings("unused")
	private final IServiceProvider provider = ServiceProvider.getInstance();
	
	private TFBuilder(){
		
	}
	
	public static synchronized final TFBuilder getInstance(final Object...arguments){
		return INSTANCE;
	}
	
	public synchronized Object build(final BuilderInput input) {
		Object _result = null;
		
		return _result;
	}

}
