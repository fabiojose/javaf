package bradesco.tf.outside.athic;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public abstract class ATHICInput {

	private Map<String, Object> parameters;
	
	public ATHICInput(){
		parameters = new HashMap<String, Object>();
	}
	
	public void put(final String key, final Object value){
		parameters.put(key, value);
	}
	
	public boolean contains(final String key){
		return parameters.containsKey(key);
	}
	
	Map<String, Object> getParameters(){
		return parameters;
	}
	
	public abstract void validate() throws IllegalArgumentException;
}
