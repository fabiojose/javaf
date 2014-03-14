package bradesco.tf.visitor;

import java.util.Properties;
import java.util.Set;

import com.javaf.Constants.REGEX;
import com.javaf.javase.util.regex.UtilRegex;

/**
 * Processar propriedades com placeholder que apontam para outras propriedades.
 * @author fabiojm - Fábio José de Moraes
 *
 */
public class PropertiesPropertyPlaceholderVisitor extends TFVisitor<Properties, Properties> {
	
	private final UtilRegex regex = UtilRegex.getInstance();
	
	private String property(final String propertyValue, final Set<String> placesInValue, final Properties properties){
		String _result = propertyValue;
		
		for(String _place : placesInValue){
			final String _property = regex.placeholderOf(_place);
			
			_result = _result.replace(_place, properties.getProperty(_property));
			
		}
		
		return _result;
	}

	public Properties visit(final Properties properties) {

		final Set<Object> _keys = properties.keySet();
		for(Object _key : _keys){
			final String _value = properties.getProperty(_key.toString());
			if(_value.matches(REGEX.PLACEHOLDER_CONTAINS)){
				final Set<String> _places = regex.valueOf(_value, REGEX.PLACEHOLDER);
				final String _newValue    = property(_value, _places, properties);
				
				properties.setProperty(_key.toString(), _newValue);
				
			}
		}
		
		return properties;
	}

}
