package bradesco.tf.visitor;

import java.util.Properties;
import java.util.Set;

import com.javaf.Constants.PLACE_HOLDER;
import com.javaf.Constants.REGEX;
import com.javaf.javase.util.regex.UtilRegex;

/**
 * 
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class PropertiesSystemPlaceholderVisitor extends TFVisitor<Properties, Properties> {
	
	private final UtilRegex regex = UtilRegex.getInstance();

	private String system(final String propertyValue, final Set<String> placesInValue, final Properties properties){
		String _result = propertyValue;
		
		for(String _place : placesInValue){
			final String _property = regex.placeholderOf(_place).substring(PLACE_HOLDER.SYSTEM_PREFIX.length());
			
			_result = _result.replace(_place, System.getProperty(_property));
			
		}
		
		return _result;
	}
	
	public Properties visit(final Properties properties) {
		
		final Set<Object> _keys = properties.keySet();
		
		//processar properties que dependem de valores em System.getProperty()
		for(Object _key : _keys){
			final String _value = properties.getProperty(_key.toString());
			if(_value.matches(REGEX.SYSTEM_PROPERTY_PLACEHOLDER_CONTAINS)){
				final Set<String> _places = regex.valueOf(_value, REGEX.SYSTEM_PROPERTY_PLACEHOLDER);
				final String _newValue    = system(_value, _places, properties);
				
				//atualizar valor processado
				properties.setProperty(_key.toString(), _newValue);
			}
		}
		
		return properties;
	}
}
